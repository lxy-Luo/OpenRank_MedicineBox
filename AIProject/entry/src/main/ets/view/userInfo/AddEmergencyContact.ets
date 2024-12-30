/**
 * @author 夏俊杰
 * @date 2024/4/16 21:43
 * @description  添加紧急联系人
 *
 */

import axios from '@ohos/axios'
import promptAction from '@ohos.promptAction'
import PreferenceUtil from '../../common/utils/PreferenceUtil'
@Extend(TextInput) function textStyle(){
  .height('20%')
  .width('100%')
  .fontSize(20)
  .padding({left:10})
  .fontColor('#1E1E1E')
  .backgroundColor('#61bc84')
  .margin({left:5,right:5})
}


@CustomDialog
export default struct AddEmergencyContact {

  controller: CustomDialogController
  @State name:string=''
  @State phone:string=''
  @State email:string=''

  async addEmergencyInfo(){
    // 后端发请求
    let ecId = await PreferenceUtil.getPreferenceValue("ecId",1);
    if(this.name==''||this.phone==''||this.email==''){
      promptAction.showToast({message:$r('app.string.info_not_enough')})
      return
    }
    axios.get('http://123.56.84.181:81/sys/emergencycontact/update',{
      params:{
        ecId:ecId,
        ecName:this.name,
        ecPhone:this.phone,
        ecEmail:this.email,
        ecDesc:''
      }
    })
      .then(res=>{
        this.controller.close()
        promptAction.showToast({message:$r('app.string.async_cloud')})
      })
      .catch((err)=>{
        promptAction.showToast({message:$r('app.string.networkOrServiceError')})
      })
  }

  build() {
    Column({space:6}){
      TextInput({placeholder:$r('app.string.contact_name'),text:this.name})
        .onChange((name:string)=>{
          this.name = name
        })
        .textStyle()
      TextInput({placeholder:$r('app.string.contact_phone'),text:this.phone})
        .onChange((phone:string)=>{
          this.phone = phone
        })
        .type(InputType.PhoneNumber)
        .textStyle()
      TextInput({placeholder:$r('app.string.contact_email'),text:this.email})
        .onChange((email:string)=>{
          this.email = email
        })
        .type(InputType.Email)
        .textStyle()
      Row(){
        Button($r('app.string.yes'))
          .height('50%')
          .width('40%')
          .onClick(()=>{
            this.addEmergencyInfo()

          })
          .backgroundColor('#2E8B57')
        Button($r('app.string.cancel'))
          .height('50%')
          .width('40%')
          .onClick(()=>{
            this.controller.close()
          })
          .fontColor(Color.Red)
          .backgroundColor('#e0e0e0')
      }
      .justifyContent(FlexAlign.SpaceEvenly)
      .padding({left:5,right:5})
      .height('25%')
      .width('100%')
    }
    .padding({top:10})
    .backgroundColor("#2d2d2d")
    .height('40%')
    .width('100%')
  }
}