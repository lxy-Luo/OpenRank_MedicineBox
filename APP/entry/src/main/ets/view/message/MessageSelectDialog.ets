
import axios from '@ohos/axios'
import router from '@ohos.router'
import Message from '../../viewmodel/message/Message'
import prompt from '@ohos.promptAction'

@Extend(Text)function TextStyle(){
  .width('94%')
  .height(50)
  .fontColor('#FFFFFF')
  .textAlign(TextAlign.Center)
  .textOverflow({ overflow: TextOverflow.Ellipsis })
}

@CustomDialog
export default struct MessageSelectDialog{

  controller: CustomDialogController
  @Prop optId : number
  @Link messages: Message[]

  // 标为已读未读
  markReadStatus(){
    let message = this.messages[this.optId]
    axios.get('http://123.56.84.181:81/sys/message/update/'+message.id+'/'+(message.isRead?0:1),{
    })
    this.messages[this.optId].isRead = !message.isRead
    // 这里用来重新渲染列表
    this.messages.push(null)
    this.controller.close()
  }

  // 删除消息
  delete(){
    axios.get('http://123.56.84.181:81/sys/message/deleteOne/'
    +this.messages[this.optId].id).then((res)=>{
      if(res.data.code===0){
        prompt.showToast({message:$r('app.string.async_cloud')})
      }else{
        prompt.showToast({message:res.data.msg})
      }
    })
      .catch(()=>{
        prompt.showToast({
          message: $r('app.string.networkOrServiceError')
        })
      })
    this.messages.splice(this.optId,1)
    this.controller.close()
  }

  @State isRead: boolean = this.messages[this.optId].isRead

  build(){
    Column({space:6}){
      Text(this.isRead?$r('app.string.mark_unread'):$r('app.string.mark_read'))
        .TextStyle()
        .onClick(()=>{
          this.markReadStatus()
          this.controller.close()
        })
      Divider()
        .width('100%')
      Text($r('app.string.view_details'))
        .TextStyle()
        .onClick(()=>{
          router.pushUrl({url:'pages/MessageInfoPage',params:{
            message: this.messages[this.optId]
          }})
          if(!this.isRead){
            this.messages[this.optId].isRead = true
            this.messages.push(null)
            this.messages.pop()
          }
          this.controller.close()
        })
      Divider()
        .width('100%')
      Text($r('app.string.delete_selected'))
        .onClick(()=>{
          this.delete()
          this.controller.close()
        })
        .TextStyle()
    }
    .backgroundColor('#1E1E1E')
    .alignItems(HorizontalAlign.Center)
    .borderRadius(10)
    .height(180)
    .width('100%')
  }
}