import BreakpointType from '../../common/bean/BreanpointType'
import BreakpointConstants from '../../common/constants/BreakpointConstans'
import { CommonConstants } from '../../common/constants/CommonConstants'
import DateUtil from '../../common/utils/DateUtil' //日期工作包
import RecordService from '../../service/RecordService'
import RecordVO from '../../viewmodel/RecordVO'
import StatsInfo from '../../viewmodel/StatsInfo'
import CalorieStats from './CalorieStats'
import  DatePickerDialog  from './DatePickDialog' //日期选择器组件
import NutrientStats from './NutrientStats'

/*
 * 首页日期组件*/

@Component
export default struct StatsCard {
  //获取全局存储的时间值，无则选取今天
  // (这里的时间只有年月日，没有时分，存入时也是这样，在重新赋值时可以调用自己写的时间工具类，将一天的时间去掉时分部分来获取毫秒值)
  @StorageProp('selectedDate') selectedDate: number = DateUtil.beginTimeOfDay(new Date())
  //获取屏幕型号状态量
  @StorageProp('currentBreakpoint') currentBreakpoint: string = BreakpointConstants.BREAKPOINT_SM

  //@Consume @Watch('handleRecordsChange') records: RecordVO[]    //接收父组件传递的RecordVO查询总体数据
  @State info: StatsInfo = new StatsInfo()
  // handleRecordsChange(){
  //   this.info = RecordService.calculateStatsInfo(this.records)
  // }

  controller: CustomDialogController = new CustomDialogController({
    //传入当时日期保持回显
    builder: DatePickerDialog({selectedDate: new Date(this.selectedDate)})
  })
  build() {
    Column(){
      //1.日期信息
      Row(){
        Text(DateUtil.formatDate(this.selectedDate))
          .fontColor($r('app.color.secondary_color'))
        Image($r("app.media.ic_public_spinner"))
          .width(20)
          .fillColor($r('app.color.secondary_color'))
      }
      .padding(CommonConstants.SPACE_12)
      .onClick(() => this.controller.open())
      //2.统计信息
      Swiper(){
        Image($r('app.media.drdfig1'))
        Image($r('app.media.drdfig2'))
        Image($r('app.media.drdfig3'))
        //热量统计
        CalorieStats({intake:this.info.intake ,expend: this.info.expend})
        //营养素统计
        NutrientStats({carbon:this.info.carbon,protein:this.info.protein,fat:this.info.fat})
      }
      .width('100%')
      .backgroundColor($r('app.color.hei1'))
      .borderRadius(CommonConstants.DEFAULT_18)
      .indicatorStyle({selectedColor: $r('app.color.primary_color')})  //选中导航点的颜色
      .displayCount(new BreakpointType({
        sm: 1,
        md: 1,
        lg: 2
      }).getValue(this.currentBreakpoint))   //轮播库每页展示组件数量（根据屏幕型号量决定）

    }
    .width(CommonConstants.THOUSANDTH_940)
    .backgroundColor($r('app.color.hei2'))
    .borderRadius(CommonConstants.DEFAULT_18)
  }
}