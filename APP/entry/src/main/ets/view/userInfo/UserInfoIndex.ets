/**
 * @author 夏俊杰
 * @date 2024年3月28日20:56:05
 * @description “我的“ 页面组件
 */
import PreferenceUtil from '../../common/utils/PreferenceUtil'
import ItemData from '../../viewmodel/ItemData'
import LogoutDialog from './LogoutDialog'
import router from '@ohos.router'
import EmergencyContact from './EmergencyContact'
import axios from '@ohos/axios'
import promptAction from '@ohos.promptAction'

@Observed
export class EmergencyContactInfo{
  name: string
  phone:string
  email:string
  ecIsValid:number

  constructor(name:string,phone:string,email:string,ecIsValid:number) {
    this.name = name
    this.phone = phone
    this.email = email
    this.ecIsValid = ecIsValid
  }
}


/**
 * Setting tab content
 */
@Component
export default struct UserInfoIndex {

  @State userName : string=''
  @State uemail: string=''

  controllerContact: CustomDialogController = new CustomDialogController({
    builder:EmergencyContact({
      emergencyContactInfo:$emergencyContactInfo
    })
  })

  @State emergencyContactInfo:EmergencyContactInfo = null

  async getEmergencyInfo(){
    // 后端发请求
    let ecId = await PreferenceUtil.getPreferenceValue("ecId",1)
    axios.get('http://123.56.84.181:81/sys/emergencycontact/info/'+ecId)
      .then(res=>{
        if(res.data.emergencyContact!==null){
          this.emergencyContactInfo = new EmergencyContactInfo(
            res.data.emergencyContact.ecName,
            res.data.emergencyContact.ecPhone,
            res.data.emergencyContact.ecEmail,
            res.data.emergencyContact.ecIsValid
          )
        }
      })
      .catch((err)=>{
        promptAction.showToast({message:$r('app.string.networkOrServiceError')})
      })
  }

  ItemDataS: ItemData[] = [
    new ItemData($r('app.string.setting_list_news'), $r('app.media.news'), $r("app.string.setting_toggle")),
    new ItemData($r('app.string.setting_list_data'), $r('app.media.data')),
    new ItemData($r('app.string.setting_list_menu'), $r('app.media.menu')),
    new ItemData($r('app.string.setting_list_about'), $r('app.media.about')),
    new ItemData($r('app.string.setting_list_storage'), $r('app.media.storage')),
  ];
  controller: CustomDialogController = new CustomDialogController({
    builder:LogoutDialog()
  })

  async aboutToAppear(){
    console.log('sda')
    this.uemail = await PreferenceUtil.getPreferenceValue("email",'') as string
    this.userName = await PreferenceUtil.getPreferenceValue("uname",'user') as string
  }

  @Builder settingCell(item: ItemData) {
    Row() {
      Row({ space: 12 }) {
        Image(item.img)
          .width(22)
          .height(22)
        Text(item.title)
          .fontColor($r('app.color.qian1'))
          .fontSize(16)
      }

      if (item.others === null) {
        Image($r('app.media.right_grey'))
          .width(12)
          .height(24)
      } else {
        Toggle({ type: ToggleType.Switch, isOn: false })
      }
    }
    .justifyContent(FlexAlign.SpaceBetween)
    .width("100%")
    .padding({
      left: 8,
      right: 22
    })
  }

  build() {
    Scroll() {
      Column({ space: 12 }) {
        Row() {
          Image($r('app.media.account'))
            .width(48)
            .height(48)
          Column() {
            if((PreferenceUtil.getPreferenceValue("uname",'李先生')
              .then((res)=>{
                this.userName = res.valueOf() as string
              })) !==null || true){
              Text(this.userName)
                .fontSize(20)
            }
            if((PreferenceUtil.getPreferenceValue("email",'abcdefg@123.com')
              .then(res=>{
                this.uemail = res.valueOf() as string
              }))==null || true){
              Text(this.uemail)
                .fontSize(12)
                .margin({ top: 4 })
            }
          }
          .alignItems(HorizontalAlign.Start)
          .margin({ left: 24 })
        }
        .margin({ top: 24 })
        .alignItems(VerticalAlign.Center)
        .width('100%')
        .height(96)
        .backgroundColor($r('app.color.lv3'))
        .padding({ left: 24})
        .borderRadius(16)

        List() {
          ForEach(this.ItemDataS, (item: ItemData) => {
            ListItem() {
              this.settingCell(item)
            }
            .height(48)
          }, (item:ItemData) => JSON.stringify(item))
          ListItem(){
            this.settingCell(new ItemData($r('app.string.emergency_contact'),$r('app.media.emergency_contact')))
          }.onClick(()=>{
            this.getEmergencyInfo()
            this.controllerContact.open()
          })
          ListItem(){
            this.settingCell(new ItemData($r('app.string.setting_list_privacy'), $r('app.media.privacy')))
          }.onClick(() =>{
            router.pushUrl(
              {
                url: 'pages/UserAgreement',
              },
              router.RouterMode.Single,
              err => {
                if(err){
                  console.log('路由失败')
                }
              }
            )
          })
        }
        .backgroundColor($r('app.color.hei2'))
        .width('100%')
        .height('65%')
        .divider({
          strokeWidth: 0.25,
          color: Color.Grey,
          startMargin: 42,
          endMargin: 24
        })
        .borderRadius(16)
        .padding({ top: 4, bottom: 4 })

        Blank()

        Button($r('app.string.setting_button'), { type: ButtonType.Capsule })
          .width('90%')
          .height(40)
          .fontSize(16)
          .fontColor($r('app.color.qian1'))
          .fontWeight(FontWeight.Medium)
          .backgroundColor($r('app.color.lv1'))
          .margin({ bottom: 55})
          .onClick(() => {
            this.controller.open()
          })
      }
      .backgroundColor($r('app.color.hei1'))
      .height('100%')
      .width('100%')
    }
  }
}







