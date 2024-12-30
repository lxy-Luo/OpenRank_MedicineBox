
import { CommonConstants } from '../../common/constants/CommonConstants'
import RecordService from '../../service/RecordService'
import GroupInfo from '../../viewmodel/GroupInfo'
import RecordType from '../../viewmodel/RecordType'
import RecordVO from '../../viewmodel/RecordVO'
import router from '@ohos.router'

//建议文本样式(淡灰色)
@Extend(Text) function grayText(){
  .fontSize(14)
  .fontColor($r('app.color.light_gray'))
}
@Component
export default struct RecordList {
 // @Consume @Watch('handleRecordsChange') records: RecordVO[]    //接收父组件传递的RecordVO查询总体数据
  @State groups: GroupInfo<RecordType,RecordVO>[] = []
  // handleRecordsChange(){
  //   //调用RecordService里的方法calculateGroupInfo将RecordVO类型转换为GroupInfo<RecordType,RecordVO>[]类型
  //   this.groups = RecordService.calculateGroupInfo(this.records)
  // }
  build(){
    List({space: CommonConstants.SPACE_10}){
      ForEach(this.groups,(group: GroupInfo<RecordType,RecordVO>) => {
        ListItem(){
          Column(){
            //分组的标题
            Row(){
              Image(group.type.icon).width(24)
              Text(group.type.name).fontSize(18).fontWeight(CommonConstants.FONT_WEIGHT_700)
              Text('建议'+group.type.min+'~'+group.type.max+'千卡').grayText()
              Blank()
              Text(group.calorie.toFixed(0)+'').fontSize(14).fontColor($r('app.color.primary_color'))
              Text('千卡').grayText()
              Image($r('app.media.ic_public_add_norm_filled'))
                .width(20)
                .fillColor($r('app.color.hei1'))
            }
            .backgroundColor(Color.White)
            .width('100%')
            .onClick(() => {
              router.pushUrl({
                url:'pages/ItemIndex',
                params: {type: group.type}   //跳转时传入对应分组信息，直接跳转至相关list 例如：当前是运动的Row就跳转运动的list，再去添加相关的运动信息
              })
            })
            //组内记录列表
            List(){
              ForEach(group.items,(item:RecordVO) => {
                ListItem(){
                  Row({space: CommonConstants.SPACE_6}){
                    Image(item.recordItem.image).width(50).backgroundColor($r('app.color.lv3')).margin({left: 15})
                    Column(){
                      Text(item.recordItem.name).fontWeight(CommonConstants.FONT_WEIGHT_500)
                      Text(item.amount+''+item.recordItem.unit).grayText()
                    }
                    .backgroundColor($r('app.color.lv3'))
                    Blank()
                    Text(item.calorie.toFixed(0)+'千卡').grayText().backgroundColor($r('app.color.lv3'))
                  }
                  .borderRadius(30)
                  .backgroundColor($r('app.color.lv3'))
                  .width('100%')
                  .padding(CommonConstants.SPACE_6)
                }
                .borderRadius(30)
                .backgroundColor($r('app.color.lv3'))
                .swipeAction({end: this.deleteButton(item)})
              })
            }
            .width('100%')
          }
          .width('100%')
          .backgroundColor(Color.White)
          .borderRadius(CommonConstants.DEFAULT_18)
          .padding(CommonConstants.SPACE_12)
        }
      })
    }
    .width(CommonConstants.THOUSANDTH_940)
    .margin({top: 10})
    .height('100%')
  }

  @Builder deleteButton(item:RecordVO) {
    Image($r('app.media.ic_public_delete_filled'))
      .width(20)
      .fillColor(Color.Red)
      .margin(5)
      .onClick(() => {
        //删除这条记录数据，并刷新页面
        console.log('删除了一个计划内容,id为'+item.id+'类型为：'+item.typeId)
        RecordService.deleteById(item.id)
        router.pushUrl({url: 'pages/RefreshTransit'})
      })
  }
}