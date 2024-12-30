/**
 *  @author 夏俊杰
 *  @date 2024年3月28日20:55:25
 *  @description 退出登录弹窗
 */

import PreferenceUtil from '../../common/utils/PreferenceUtil'
import router from '@ohos.router'

@Extend(Button)
function buttonStyle(){
  .type(ButtonType.Capsule)
  .width('40%')
  .height(50)
}

@CustomDialog
export default struct LogoutDialog {
  controller : CustomDialogController
  build(){
    Column({space:6}){
      Row(){
        Text($r('app.string.isExit'))
          .height(30)
          .fontSize(25)
      }
      .padding({top: 30 })
      .justifyContent(FlexAlign.SpaceEvenly)
      .width('100%')
      .height(50)
      Text($r('app.string.isExitInfo'))
        .fontSize(12)
        .fontColor(Color.Gray)
        .padding({top:10})
      Blank()
      Row(){
        Button($r('app.string.yes'))
          .buttonStyle()
          .onClick(()=>{
            router.replaceUrl({url: 'pages/LoginPage'})
            this.controller.close()
            PreferenceUtil.deletePreferenceValue("password")
          })
        Button($r('app.string.cancel'))
          .buttonStyle()
          .fontColor("#FA2A2D")
          .fontWeight(FontWeight.Medium)
          .backgroundColor("#E5E8EA")
          .onClick(()=>{
            this.controller.close()

          })
      }
      .padding({bottom:30})
      .justifyContent(FlexAlign.SpaceEvenly)
      .width('100%')
      .height(50)
    }
    .width('100%')
    .height('30%')
  }
}