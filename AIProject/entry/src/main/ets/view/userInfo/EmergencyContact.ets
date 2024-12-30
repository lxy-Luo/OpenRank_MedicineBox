/**
 * @author 夏俊杰
 * @date 2024/4/16 21:43
 * @description 紧急联系人
 *
 */
import  {EmergencyContactInfo} from './UserInfoIndex'
import AddEmergencyContact from './AddEmergencyContact'
@Extend(Text) function textStyle(){
  .height('25%')
  .width('100%')
  .fontSize(20)
  .padding({left:10})
  .fontColor('#c6ffe6')
}

@CustomDialog
export default struct EmergencyContact {

  addController: CustomDialogController = new CustomDialogController({
    builder:AddEmergencyContact(),
  })
  @Link emergencyContactInfo: EmergencyContactInfo
  controller: CustomDialogController

  build() {
    Column(){
      if(this.emergencyContactInfo!==null && this.emergencyContactInfo.ecIsValid===1 ){
        Text($r('app.string.contact_info'))
          .textStyle()
          .textAlign(TextAlign.Center)
        Line().width('100%').height(1).backgroundColor('#1E1E1E')
        Text("姓名："+this.emergencyContactInfo.name)
          .textStyle()
        Line().width('100%').height(1).backgroundColor('#1E1E1E')
        Text("手机号："+this.emergencyContactInfo.phone)
          .textStyle()
        Line().width('100%').height(1).backgroundColor('#1E1E1E')
        Text("邮箱："+this.emergencyContactInfo.email)
          .textStyle()
      }else{
        Text($r('app.string.no_contact'))
          .fontSize(30)
          .textAlign(TextAlign.Center)
          .height('50%')
          .fontColor('#c6ffe6')
          .margin('10%')
        Button($r('app.string.go_to_add'))
          .onClick(()=>{
            this.controller.close()
            this.addController.open()
          })
          .fontSize(20)
          .backgroundColor('#61bc84s')
          .height('20%')

      }
    }
    .backgroundColor("#2d2d2d")
    .height('40%')
    .width('100%')
  }
}