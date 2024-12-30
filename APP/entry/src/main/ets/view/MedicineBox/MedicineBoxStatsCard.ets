/*
 *@Author: Dairundong
 *@Date: 2024/3/28 13:00
 * 药箱模块主页面下的统计卡片组件
 * */
import { CommonConstants } from '../../common/constants/CommonConstants'
import DateUtil from '../../common/utils/DateUtil'
import  DatePickerDialog  from '../record/DatePickDialog' //日期选择器组件
import mainViewModel from '../../viewmodel/MainViewModel'
import ItemData from '../../viewmodel/ItemData'


@Extend(Text) function text1(){
  .fontSize(35)
  .fontColor($r('app.color.qian1'))
}
@Extend(Text) function text2(){
  .margin({top:20})
  .fontSize(20)
  .fontColor($r('app.color.qian1'))
}
@Component
export default struct MedicineBoxStatsCard {
  //从全局存储获取当前日期
  @StorageProp('selectedDate') selectedDate: number = DateUtil.beginTimeOfDay(new Date())
  controller: CustomDialogController = new CustomDialogController({
    //传入当时日期保持回显
    builder: DatePickerDialog({selectedDate: new Date(this.selectedDate)})
  })


  @Link gross: number //当日计划总药量
  @Link eatTotal: number  //今日已服用
  @Link unfinished: number  //剩余量


  build() {
    Column(){
      //日期信息
      Row(){
        Text(DateUtil.formatDate(this.selectedDate))
          .fontColor($r('app.color.secondary_color'))
        Image($r("app.media.ic_public_spinner"))
          .width(20)
          .fillColor($r('app.color.secondary_color'))

      }
      .backgroundColor($r('app.color.hei1'))
      .padding(CommonConstants.SPACE_2)
      .onClick(() => this.controller.open())
      Row({space: CommonConstants.SPACE_6}){
        Column({space:CommonConstants.SPACE_4}){
          Text('今日已服用').text2()
          Text(this.eatTotal+'').text1()
        }
        .height('50%')
        .width('30%')
        .backgroundColor($r('app.color.hei1'))
        .borderRadius(10)
        Column(){
          Text('今日计划量').text2()
          Text(this.gross+'').text1()
        }
        .height('50%')
        .width('30%')
        .borderRadius(10)
        .backgroundColor($r('app.color.hei1'))
        Column(){
          Text('未服用').margin({top:20}).text2()
          Text(this.unfinished+'').text1()
        }
        .height('50%')
        .width('30%')
        .borderRadius(10)
        .backgroundColor($r('app.color.hei1'))
      }
      Row(){
        Progress({
          value: this.eatTotal,
          total:this.gross,
          type:ProgressType.Linear
        })
          .width('80%')
          .style({strokeWidth:CommonConstants.DEFAULT_10})
          .color($r('app.color.primary_color'))
      }
      Grid() {
        ForEach(mainViewModel.getFirstGridData(), (item: ItemData) => {
          GridItem() {
            Column() {
              Image(item.img)
                .width(24)
                .height(24)
              Text(item.title)
                .fontColor($r('app.color.qian1'))
                .fontSize(12)
                .margin({ top: 4 })
            }
          }
        }, (item: ItemData) => JSON.stringify(item))
      }
      .scrollBar(BarState.On)
      //.columnsTemplate('1fr 1fr 1fr 1fr 1fr 1fr 1fr 1fr')
      .rowsTemplate('1fr')
      .columnsGap(50)
      .padding({ top: 12, bottom: 12,left:5,right:4})
      .height(60)
      .width("90%")
      .scrollBar(BarState.Off)
      .backgroundColor($r('app.color.hei1'))
      .borderRadius(24)

    }
    .backgroundColor($r('app.color.hei1'))
    .justifyContent(FlexAlign.Center)
  }
}