//已废弃
//使用medicalmodel中的RecordModel


// import relationalStore from '@ohos.data.relationalStore'
// import { ColumnInfo, ColumnType } from '../common/bean/ColumnInfo'
// import RecordPO from '../common/bean/RecordPO'
// import DbUtil from '../common/utils/DbUtil'
// //import DbUtil from '../common/utils/DbUtil'
//
// /**
//  * 数据库建表语句
//  */
// const CREATE_TABLE_SQL: string = `
//  CREATE TABLE IF NOT EXISTS record (
//    id INTEGER PRIMARY KEY AUTOINCREMENT,
//    type_id INTEGER NOT NULL,
//    item_id INTEGER NOT NULL,
//    amount DOUBLE NOT NULL,
//    create_time INTEGER NOT NULL
//  )
//  `
// /*
//  *数据库对应字段*/
// const COLUMNS: ColumnInfo[] = [
//   {name: 'id', columnName: 'id', type: ColumnType.LONG},
//   {name: 'typeId', columnName: 'type_id', type: ColumnType.LONG},
//   {name: 'itemId', columnName: 'item_id', type: ColumnType.LONG},
//   {name: 'amount', columnName: 'amount', type: ColumnType.DOUBLE},
//   {name: 'createTime', columnName: 'create_time', type: ColumnType.LONG}
// ]
//
// const TABLE_NAME = 'record'
// const ID_COLUMN = 'id'
// const DATE_COLUMN = 'create_time'
//
// class RecordModel {
//   //获奖建表sql语句
//   getCreateTableSql(): string {
//     return CREATE_TABLE_SQL
//   }
//
//   insert(record: RecordPO): Promise<number>{
//     return DbUtil.insert(TABLE_NAME, record, COLUMNS)
//   }
//
//   deleteById(id: number): Promise<number>{
//     // 1.删除条件（有点类似于MP里面的QueryWAPPER）
//     let predicates = new relationalStore.RdbPredicates(TABLE_NAME)
//     predicates.equalTo(ID_COLUMN, id)
//     // 2.删除
//     return DbUtil.delete(predicates)
//   }
//
//   //根据创建时间查询
//   listByDate(date: number): Promise<RecordPO[]>{
//     // 1.查询条件
//     let predicates = new relationalStore.RdbPredicates(TABLE_NAME)
//     predicates.equalTo(DATE_COLUMN, date)
//     // 2.查询
//     return DbUtil.queryForList(predicates, COLUMNS)
//   }
// }
//
// let recordModel = new RecordModel()
//
// export default recordModel as RecordModel