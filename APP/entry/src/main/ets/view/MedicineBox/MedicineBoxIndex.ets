/*
 *@Author: Dairundong
 *@Date: 2024/3/28 12:50
 * 药箱模块主页面组件
 * */
import MedicalListVO from '../../viewmodel/medical/MedicalListVO'
import MedicalRecordItem from '../../viewmodel/medical/MedicalRecordItem'
import MedicalRecordVO from '../../viewmodel/medical/MedicalRecordVO'
import SearchHeader from '../record/SearchHeader'
import MedicineBoxList from './MedicineBoxList'
import MedicineBoxStatsCard from './MedicineBoxStatsCard'
@Component
export default struct MedicineBoxIndex {
  //已经服用药品的数据
  @Consume todayMedicalList: MedicalRecordVO[]
  //今日计划的数据
  @Consume record: MedicalListVO[]
  //今日已经服用药品的数量
  @Link eatTotal: number
  //今日计划量
  @Link TotalNumber: number
  //今日未服用
  @Link unfinished: number

  build() {
    Column(){
      //搜索栏
      SearchHeader().backgroundColor($r('app.color.lv3')).borderRadius(20).margin(5)

      //药箱统计卡片
      MedicineBoxStatsCard({eatTotal: $eatTotal,gross:$TotalNumber,unfinished:$unfinished})
        .height("25%")
        .backgroundColor($r('app.color.hei1'))
        .margin({bottom:10})
      //主要列表信息
      MedicineBoxList().margin({top:10}).backgroundColor($r('app.color.hei2'))
    }
    .width('100%')
    .height('100%')
    .backgroundColor($r('app.color.hei1'))
  }
}