import router from '@ohos.router'
import axios from '@ohos/axios'
import Message from '../viewmodel/message/Message'



@Entry
@Component
struct MessageInfoPage {

  // 接受消息
  @State message: Message = null
  // 接受路由参数，判断是否需要改变状态
  aboutToAppear(){
    this.message = router.getParams()['message']
    if(!this.message.isRead){
      console.log(this.message.id.toString())
      axios.get('http://123.56.84.181:81/sys/message/update/'+this.message.id+'/1')
    }
  }


  build() {
    Column({space:20}){
      Row({space:90}){
        Image($r('app.media.ic_public_back'))
          .width(40)
          .height(40)
          .onClick(()=>{
            router.back()
          })
          .borderRadius(10)
          .backgroundColor('#454545')
        Text(this.message.sender)
          .fontSize(30)
          .fontColor('#c6ffe6')
          .textAlign(TextAlign.Center)
          .fontWeight(FontWeight.Bold)
      }
      .margin({top:15})
      .width('94%')
      .height(50)
      Column(){
        Row(){
          Text(this.message.content)
            .lineHeight(30)
            .textAlign(TextAlign.Start)
            .fontSize(20)
            .fontWeight(FontWeight.Regular)
            .margin(10)
            .fontColor('#FFFFFF')
        }
        .alignItems(VerticalAlign.Top)
        .layoutWeight(1)
        Divider()
          .width('100%')
        Column({space:8}){
          Text(this.message.time)
            .fontColor('#e0e0e0')
          Text(this.message.date)
            .fontColor('#e0e0e0')
        }
        .margin(10)
        .alignItems(HorizontalAlign.End)
        .width('80%')
        .height(50)
      }
      .justifyContent(FlexAlign.SpaceBetween)
      .borderRadius(20)
      .backgroundColor('#2d2d2d')
      .width('94%')
      .height('75%')
    }
    .backgroundColor('#1E1E1E')
    .width('100%')
    .height('100%')
  }
}