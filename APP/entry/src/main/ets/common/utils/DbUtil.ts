import common from '@ohos.app.ability.common';
import relationalStore from '@ohos.data.relationalStore';
import { ColumnInfo, ColumnType } from '../bean/ColumnInfo';
import Logger from './Logger';

const DB_FILENAME: string = 'HeiMaHealthy.db' //数据库文件名称

/*
 * 关系型数据库的操作工具类*/
class DbUtil {
  rdbStore: relationalStore.RdbStore

  //数据库初始化函数（项目启动时调用）（异步操作使用Promise进行了封装）
  initDB(context: common.UIAbilityContext): Promise<void> {
    let config: relationalStore.StoreConfig = {
      name: DB_FILENAME, //数据库文件名称
      securityLevel: relationalStore.SecurityLevel.S1
    }
    return new Promise<void>((resolve, reject) => {
      relationalStore.getRdbStore(context, config)
        .then(rdbStore => {
          this.rdbStore = rdbStore
          Logger.debug('rdbStore 初始化完成！')
          resolve()
        })
        .catch(reason => {
          Logger.debug('rdbStore 初始化异常', JSON.stringify(reason))
          reject(reason)
        })
    })
  }
  //创建数据库表（异步操作使用Promise进行了封装）
  createTable(createSQL: string): Promise<void> {
    return new Promise((resolve, reject) => {
      this.rdbStore.executeSql(createSQL)
        .then(() => {
          Logger.debug('创建表成功', createSQL)
          resolve()
        })
        .catch(err => {
          Logger.error('创建表失败,' + err.message, JSON.stringify(err))
          reject(err)
        })
    })
  }
  /*
   *新增 */
  insert(tableName: string, obj: any, columns: ColumnInfo[]): Promise<number> {
    //返回时内部的用Promise封装


    return new Promise((resolve, reject) => {
      // 1.构建新增数据
      let value = this.buildValueBucket(obj, columns)
      // 2.新增
      this.rdbStore.insert(tableName, value, (err, id) => {
        if (err) {
          console.log('新增失败！', JSON.stringify(err))
          reject(err)
        } else {
                        console.log('新增成功！新增id：', id.toString())
          resolve(id)
        }
      })
    })
  }
  /*
   * 删除数据*/
  delete(predicates: relationalStore.RdbPredicates): Promise<number> {
    return new Promise((resolve, reject) => {
      //返回的row是行数，表示删除了几行
      this.rdbStore.delete(predicates, (err, rows) => {
        if (err) {
          Logger.error('删除失败！', JSON.stringify(err))
          reject(err)
        } else {
          Logger.debug('删除成功！删除行数：', rows.toString())
          resolve(rows)
        }
      })
    })
  }
  /*
   * 查询*/
  queryForList<T>(predicates: relationalStore.RdbPredicates, columns: ColumnInfo[]): Promise<T[]> {
    return new Promise((resolve, reject) => {
      this.rdbStore.query(predicates, columns.map(info => info.columnName), (err, result) => {
        if (err) {
          Logger.error('查询失败！', JSON.stringify(err))
          reject(err)
        } else {
           Logger.debug('查询成功！查询行数：', result.rowCount.toString())
          resolve(this.parseResultSet(result, columns))
        }
      })
    })
  }

  parseResultSet<T> (result: relationalStore.ResultSet, columns: ColumnInfo[]): T[] {
    // 1.声明最终返回的结果
    let arr = []
    // 2.判断是否有结果
    if (result.rowCount <= 0) {
      return arr
    }
    // 3.处理结果
    while (!result.isAtLastRow) {
      // 3.1.去下一行
      result.goToNextRow()
      // 3.2.解析这行数据，转为对象
      let obj = {}
      columns.forEach(info => {
        let val = null
        //判断数据类型
        switch (info.type) {
          case ColumnType.LONG:
            val = result.getLong(result.getColumnIndex(info.columnName))
            break
          case ColumnType.DOUBLE:
            val = result.getDouble(result.getColumnIndex(info.columnName))
            break
          case ColumnType.STRING:
            val = result.getString(result.getColumnIndex(info.columnName))
            break
          case ColumnType.BLOB:
            val = result.getBlob(result.getColumnIndex(info.columnName))
            break
        }
        obj[info.name] = val
      })
      // 3.3.将对象填入结果数组
      arr.push(obj)
      Logger.debug('查询到数据：', JSON.stringify(obj))
    }
    return arr
  }

  buildValueBucket(obj: any, columns: ColumnInfo[]): relationalStore.ValuesBucket {
    let value = {}
    columns.forEach(info => {
      let val = obj[info.name]
      //当不为空时附上数据库的值
      if (typeof val !== 'undefined') {
        value[info.columnName] = val
      }
    })
    return value
  }
}


let dbUtil: DbUtil = new DbUtil();

export default dbUtil as DbUtil