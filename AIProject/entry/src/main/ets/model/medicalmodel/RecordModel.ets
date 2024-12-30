import relationalStore from '@ohos.data.relationalStore'
import { ColumnInfo, ColumnType } from '../../common/bean/ColumnInfo'
import MedicalRecordPO from '../../common/bean/medical/MedicalRecordPO'
import RecordPO from '../../common/bean/RecordPO'
import DbUtil from '../../common/utils/DbUtil'
//import DbUtil from '../common/utils/DbUtil'

/**
 * 数据库建表语句
 */
const CREATE_TABLE_SQL: string = `
 CREATE TABLE IF NOT EXISTS record (
   id INTEGER PRIMARY KEY AUTOINCREMENT,
   item_id INTEGER NOT NULL,
   amount DOUBLE NOT NULL
 )
 `
/*
 *数据库对应字段*/
const COLUMNS: ColumnInfo[] = [
  {name: 'id', columnName: 'id', type: ColumnType.LONG},
  {name: 'itemId', columnName: 'item_id', type: ColumnType.LONG},
  {name: 'amount', columnName: 'amount', type: ColumnType.DOUBLE}
]

const TABLE_NAME = 'record'
const ID_COLUMN = 'id'

class RecordModel {
  //获奖建表sql语句
  getCreateTableSql(): string {
    return CREATE_TABLE_SQL
  }

  insert(record: MedicalRecordPO): Promise<number>{
    //判断添加药品是否已经在计划中,就先删了再添加
    if(this.listById(record.id)){
      this.deleteById(record.id)
    }
    return DbUtil.insert(TABLE_NAME, record, COLUMNS)
  }

  deleteById(id: number): Promise<number>{
    // 1.删除条件（有点类似于MP里面的QueryWAPPER）
    let predicates = new relationalStore.RdbPredicates(TABLE_NAME)
    predicates.equalTo(ID_COLUMN, id)
    // 2.删除
    return DbUtil.delete(predicates)
  }

  //查询所有
  listAll(): Promise<MedicalRecordPO[]>{
    // 1.查询条件
    let predicates = new relationalStore.RdbPredicates(TABLE_NAME)
    predicates.isNotNull(ID_COLUMN)
    // 2.查询
    return DbUtil.queryForList(predicates, COLUMNS)
  }

  //根据id查询
  listById(id: number): Promise<MedicalRecordPO[]>{
    let predicates = new relationalStore.RdbPredicates(TABLE_NAME)
    predicates.equalTo(ID_COLUMN,id)
    return DbUtil.queryForList(predicates, COLUMNS)
  }




}

let recordModel = new RecordModel()

export default recordModel as RecordModel