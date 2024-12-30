/*
 *@Author: 罗骁阳
 * 2024/4/5 19:45
 *
 * */
import relationalStore from '@ohos.data.relationalStore'
import { ColumnInfo, ColumnType } from '../../common/bean/ColumnInfo'
import MedicalRecordPO from '../../common/bean/medical/MedicalRecordPO'
import RecordPO from '../../common/bean/RecordPO'
import DbUtil from '../../common/utils/DbUtil'
import MedicalRecordItem from '../../viewmodel/medical/MedicalRecordItem'
//已废弃
//见RecordModel



/**
 * 数据库建表语句
 * id: 数据库中存放的id
 * item_id: 药品的id
 * amount： 药品的数量
 * create_time: 创建的时间
 */
const CREATE_TABLE_SQL: string = `
 CREATE TABLE IF NOT EXISTS medical_record (
   id INTEGER PRIMARY KEY AUTOINCREMENT,
   item_id INTEGER NOT NULL,
   amount DOUBLE NOT NULL
 )
 `

/*
 *数据库对应字段
 */
const COLUMNS: ColumnInfo[] = [
  {name: 'id', columnName: 'id', type: ColumnType.LONG},
  {name: 'itemId', columnName: 'item_id', type: ColumnType.LONG},
  {name: 'amount', columnName: 'amount', type: ColumnType.DOUBLE},
]

const TABLE_NAME = 'medical_record'
const ID_COLUMN = 'id'

class MedicalRecordModel {
  //获奖建表sql语句
  getCreateTableSql(): string {
    return CREATE_TABLE_SQL
  }


  insert(record: MedicalRecordPO): Promise<number>{
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

}

let medicalRecordModel = new MedicalRecordModel()

export default medicalRecordModel as MedicalRecordModel