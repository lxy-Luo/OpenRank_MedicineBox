import DateUtil from '../../common/utils/DateUtil'
import RecordService from '../../service/RecordService'
import RecordVO from '../../viewmodel/RecordVO'
import RecordList from './RecordList'
import SearchHeader from './SearchHeader'
import StatsCard from './StatsCard'

/*
 * 首页界面*/

@Component
export default struct RecordIndex {
  //@Prop @Watch('handlePageShow') isPageShow: boolean
  // handlePageShow(){
  //   if(this.isPageShow){
  //     this.aboutToAppear()
  //   }
  // }
  @StorageProp('selectedDate')
 // @Watch('aboutToAppear')     //监控日期变更触发aboutToAppear函数
  selectedDate: number = DateUtil.beginTimeOfDay(new Date())
  @Provide records: RecordVO[] = []
  // async aboutToAppear(){
  //   this.records =await RecordService.queryRecordByDate(this.selectedDate)
  // }

  build() {
    Column(){
      //1.头部搜索栏
      SearchHeader().backgroundColor($r('app.color.lv3')).borderRadius(30).margin({top: 17,bottom:10})
      //2.统计卡片
      StatsCard()
      //3.记录列表
      RecordList()
        .layoutWeight(1)
    }
    .width('100%')
    .height('100%')
    .backgroundColor($r('app.color.hei1'))
  }
}