
import { CommonConstants } from '../../common/constants/CommonConstants'

@Preview   //实现预览组件功能(编译器bug,暂时看不了)
@CustomDialog
export default struct UserPrivacyDialog {
  //声明弹窗控制器
  controller: CustomDialogController
  //使用弹窗组件调用的方法（需要传递）
  confirm: () => void
  cancel: () => void

  build() {
    Column(){
      //标题
      Text($r('app.string.user_privacy_title'))
      //内容
      Text($r('app.string.user_privacy_content'))
        .fontSize(20)
        .fontWeight(CommonConstants.FONT_WEIGHT_700)
      //按钮
      Button($r('app.string.agree_label'))
        .width(150)
        .backgroundColor($r('app.color.primary_color'))
        .onClick(() => {
          this.confirm()
          this.controller.close()   //关闭对话框
        })
      Button($r('app.string.refuse_label'))
        .width(150)
        .backgroundColor($r('app.color.light_gray'))
        .onClick(() => {
          this.cancel()
          this.controller.close()   //关闭对话框
        })

    }
    .width('100%')
    .padding(10)
  }
}