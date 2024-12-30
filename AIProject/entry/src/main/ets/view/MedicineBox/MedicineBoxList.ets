import BreakpointType from '../../common/bean/BreanpointType'
import BreakpointConstants from '../../common/constants/BreakpointConstans'
import { CommonConstants } from '../../common/constants/CommonConstants'
import MedicalListVO from '../../viewmodel/medical/MedicalListVO'
import MedicalRecordItem from '../../viewmodel/medical/MedicalRecordItem'
import MedicalRecordVO from '../../viewmodel/medical/MedicalRecordVO'
import EatenList from './EatenList'
import PlanList from './PlanList'
@Component
export default struct MedicineBoxList {
  @Consume todayMedicalList:MedicalRecordVO[]
  @Consume record: MedicalListVO[]
  //获取屏幕型号状态量
  @StorageProp('currentBreakpoint') currentBreakpoint: string = BreakpointConstants.BREAKPOINT_SM
  //@Link record: MedicalRecordVO[]
  build() {
    Column(){
      Swiper(){
        //当日日服药信息
        EatenList()
        //计划列表
        PlanList()
      }
      .width('100%')
      .height("60%")
      .backgroundColor($r('app.color.hei1'))
      .borderRadius(CommonConstants.DEFAULT_18)
      .indicatorStyle({selectedColor: $r('app.color.primary_color')})  //选中导航点的颜色
      .displayCount(new BreakpointType({
        sm: 1,
        md: 1,
        lg: 2
      }).getValue(this.currentBreakpoint))   //轮播库每页展示组件数量（根据屏幕型号量决定）
    }.backgroundColor($r('app.color.hei2'))
  }
}