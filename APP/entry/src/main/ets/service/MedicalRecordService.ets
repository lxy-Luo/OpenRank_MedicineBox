/*
 *@Author: 罗晓阳
 * @upDate 2024/4/5 19:45
 * 今日使用药品，记录的数据库对应类型
 * */
import MedicalRecordPO from '../common/bean/medical/MedicalRecordPO'
import RecordPO from '../common/bean/RecordPO'
import DateUtil from '../common/utils/DateUtil'
import ItemModel from '../model/ItemModel'
import MedicalRecordModel from '../model/medicalModel/MedicalRecordModel'
import { RecordTypeEnum, RecordTypes } from '../model/RecordTypeModel'
import MedicalItem from '../view/MedicineBox/MedicalItem'
import GroupInfo from '../viewmodel/GroupInfo'
import MedicalRecordItem from '../viewmodel/medical/MedicalRecordItem'
import MedicalRecordVO from '../viewmodel/medical/MedicalRecordVO'
import RecordType from '../viewmodel/RecordType'
import RecordVO from '../viewmodel/RecordVO'
import StatsInfo from '../viewmodel/StatsInfo'
class MedicalRecordService {
  /*
   * 插入数据*/
  insert(itemId: number, amount: number){
    //id设置为itemId
    let id= itemId
    return MedicalRecordModel.insert({id,itemId,amount})
  }
  /*
   * 根据id删除*/
  deleteById(id: number){
    return MedicalRecordModel.deleteById(id)
  }

  /*
   * 查询所有
   */
  async queryMedicalRecord(): Promise<MedicalRecordVO[]>{
    //查询数据库的MedicalRecordPO
    let rps = await MedicalRecordModel.listAll()
    //通过map映射，将MedicalRecordPO转为MedicalRecordVO
    return rps.map(rp => {
      //获取po中的基础属性
      let rv = {id: rp.id,DrugId: rp.itemId,All_number:rp.amount} as MedicalRecordVO
      return rv
    })
  }
}
let medicalRecordService = new MedicalRecordService()
export default medicalRecordService as MedicalRecordService