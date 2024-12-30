import axios from '@ohos/axios'
import prompt from '@ohos.promptAction';
import PreferenceUtil from '../../common/utils/PreferenceUtil'
import Message from '../../viewmodel/message/Message'

@Extend(Text)function TextStyle(){
  .width('94%')
  .height(50)
  .fontColor('#e0e0e0')
  .textAlign(TextAlign.Center)
  .textOverflow({ overflow: TextOverflow.Ellipsis })
}


@CustomDialog
export default struct MessageMenuDialog{

  controller:CustomDialogController

  @Link isSelected:boolean
  @Link messages:Message[]
  getMessage:()=>void

  //全部已读
  async readAll(){
    let userId = await PreferenceUtil.getPreferenceValue("userId",1)
    axios.get('http://123.56.84.181:81/sys/message/update/'+ userId)
      .then((res)=>{
        if(res.data.code===0){
          prompt.showToast({message:$r('app.string.async_cloud')})
        }else{
          prompt.showToast({message:$r('app.string.async_cloud_failed')})
        }
      }).catch((err)=>{
          prompt.showToast({message:$r('app.string.networkOrServiceError')})
      })
    this.messages.forEach((message:Message)=>message.isRead=true)
    this.messages.push(null)
    this.controller.close()
  }

  //全部删除
  async deleteAll(){
    let userId = await PreferenceUtil.getPreferenceValue("userId",1)
    axios.get('http://123.56.84.181:81/sys/message/deleteAll'+userId)
      .then((res)=>{
        if(res.data.code===0){
          prompt.showToast({message:$r('app.string.async_cloud')})
        }else{
          prompt.showToast({message:$r('app.string.async_cloud_failed')})
        }
      })
      .catch((err)=>{
        prompt.showToast({message:$r('app.string.networkOrServiceError')})
      })
  }

  build(){
    Column({space:6}){
      Text($r('app.string.read_all'))
        .TextStyle()
        .onClick(async ()=>{
          this.readAll()
        })
      Divider()
        .width('100%')
      Text($r('app.string.delete_all'))
        .TextStyle()
        .onClick(()=>{
          // 全部删除请求
          this.messages = []
          this.deleteAll()
          this.controller.close()
        })
      Divider()
        .width('100%')
      Text($r('app.string.select'))
        .onClick(()=>{
          this.isSelected = true
          this.controller.close()
        })
        .TextStyle()
      Text($r('app.string.flush'))
        .onClick(()=>{
          this.getMessage()
          prompt.showToast({message:'刷新成功'})
          this.controller.close()
        })
        .TextStyle()
    }
    .alignItems(HorizontalAlign.Center)
    .borderRadius(10)
    .height(240)
    .width(200)
    .backgroundColor('#1E1E1E')
  }
}