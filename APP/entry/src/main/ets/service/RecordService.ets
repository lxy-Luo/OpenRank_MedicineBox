import RecordPO from '../common/bean/RecordPO'
import DateUtil from '../common/utils/DateUtil'
import ItemModel from '../model/ItemModel'
import RecordModel from '../model/medicalmodel/RecordModel'
// import RecordModel from '../model/RecordModel'
import { RecordTypeEnum, RecordTypes } from '../model/RecordTypeModel'
import GroupInfo from '../viewmodel/GroupInfo'
import MedicalListVO from '../viewmodel/medical/MedicalListVO'
import MedicalRecordVO from '../viewmodel/medical/MedicalRecordVO'
import RecordType from '../viewmodel/RecordType'
import RecordVO from '../viewmodel/RecordVO'
import StatsInfo from '../viewmodel/StatsInfo'
class RecordService {
  /*
   * 插入数据*/
  insert(itemId: number, amount: number){
    //调用对应数据模型对数据库的操作方法
    //1.获取时间(从全局存储中获取对应时间)
    //let createTime =(AppStorage.Get('selectedDate')|| DateUtil.beginTimeOfDay(new Date())) as number
    //id是可选项，自增,直接不传
    let id=itemId;
    return RecordModel.insert({id,itemId,amount})
  }
  /*
   * 根据id删除*/
  deleteById(id: number){
    return RecordModel.deleteById(id)
  }

  /*
 * 查询所有
 */
  async queryMedicalRecord(): Promise<MedicalListVO[]>{
    //查询数据库的MedicalRecordPO
    let rps = await RecordModel.listAll()
    //通过map映射，将MedicalRecordPO转为MedicalRecordVO
    return rps.map(rp => {
      //获取po中的基础属性
      let rv = {id: rp.id,DrugId: rp.itemId,All_number:rp.amount} as MedicalListVO
      return rv
    })
  }

}
let recordService = new RecordService()
export default recordService as RecordService