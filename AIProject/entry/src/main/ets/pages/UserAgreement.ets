import router from '@ohos.router'
import { CommonConstants } from '../common/constants/CommonConstants'
@Entry
@Component
struct UserAgreement {
  build() {
    Column(){
      this.Header()
      Text("智能药箱app用户协议").fontSize(30).margin({top:15})
      List(){
        ListItem(){
          this.Texts('1.导言','欢迎你使用“智慧药箱app”软件及相关服务！    “智能药箱app”软件及相关服务，系指星启计算科技公司以智能药箱应用程序（含不同版本）、智能药箱小程序、应用程序编程接口（API）以及随技术发展出现的创新形态方式向你提供的产品与服务，包括但不限于个性化推荐、发布信息、互动交流、搜索查询等核心功能以及其他功能，为创作与交流的平台。《智能药箱app用户协议》（以下简称“本协议”）是你与公司之间就你下载、安装、注册、登录、使用（以下统称“使用”）“智能药箱app”软件及相关服务所订立的协议。')
        }
        ListItem(){
          this.Texts('2.“智能药箱”软件与相关服务','2.1 你使用“智能药箱app”软件及相关服务，可以通过预装、公司已授权的第三方下载等方式获取“智能药箱app”客户端应用程序若你并非从公司或经公司授权的第三方获取本软件的，公司无法保证非官方版本的“智能药箱app”软件能够正常使用，你因此遭受的损失与公司无关。 2.2 公司可能为不同的终端设备开发不同的应用程序软件版本，你应当根据实际设备状况获取、下载、安装合适的版本。如你不再使用“智能药箱app”软件及相关服务，你也可自行卸载相应的应用程序软件。2.3 为更好的提升用户体验及服务，公司将不定期提供“智能药箱app”软件及相关服务的更新或改变（包括但不限于软件修改、升级、功能强化、开发新服务、软件替换等），你可根据需要自行选择是否更新相应的版本。公司可能出于安全保障和服务提升的目的直接对“智能药箱app”软件及相关服务进行部分或全部更新。你也可通过点击此处关闭自动更新功能，当你关闭自动更新功能后，我们仍然会在可行的情况下以适当的方式（包括但不限于系统提示、公告、站内信等）提示你，你有权选择接受更新后的版本；如你选择不作更新，“智能药箱app”软件及相关服务的部分功能将受到限制或不能正常使用。 2.4 除非得到公司事先明示书面授权，你不得以任何形式对“智能药箱app”软件及相关服务进行包括但不限于改编、复制、传播、垂直搜索、镜像或交易等未经授权的访问或使用。'
          +'2.5 你理解，使用“智能药箱app”软件及相关服务需自行准备与软件及相关服务有关的终端设备（如电脑、手机等），一旦你在终端设备中打开“智能药箱app”软件即视为你使用“智能药箱app”软件及相关服务。为充分实现“智能药箱app”的全部功能，你可能需要将终端设备联网，你理解由你承担所需要的费用（如流量费、上网费等）。')
        }
        ListItem(){
          this.Texts('3.关于账号',
            '3.1 “智能药箱app”软件及相关服务为你提供了注册通道，你有权选择合法的字符组合作为自己的账号，并自行设置符合安全要求的密码。用户设置的账号、密码是用户用以登录并以注册用户身份使用“智能药箱app”软件及相关服务的凭证。3.2账号注销'+'在需要终止使用“智能药箱app”账号服务时，符合以下条件的，你可以申请注销你的“智能药箱app”账号：'
            +'（1）你仅能申请注销你本人的账号，并依照“智能药箱app”的流程进行注销；'
            +'（2）你仍应对你在注销账号前且使用“智能药箱app”服务期间的行为承担相应责任；（3）注销成功后，账号记录、功能等将无法恢复或提供。'
            +'如你需要注销你的“智能药箱app”账号，请打开今日头条客户端，在【我的】-【设置】-【账号与安全】中的账号注销处，按提示进行注销。'
          )
        }
      }.height("80%")
      Row(){
        Button('已同意')
          .width('30%').height(40).margin({right:40})
        Button('取消同意')
          .width('30%').height(40).fontColor(Color.Black).backgroundColor(Color.White)
      }
    }
    .backgroundColor(Color.White)
    .height('100%')
    .width('100%')
  }
  @Builder Texts(text1: string , text2:string){
    Column(){
      Text(text1).fontSize(20).fontWeight(15).margin({left:10})
      Text(text2)
        .margin({left:15})
    }
    .alignItems(HorizontalAlign.Start)
  }

  @Builder Header(){
    Row(){
      Image($r('app.media.ic_public_back'))
        .backgroundColor(Color.White)
        .width(24)
        .onClick(() => router.back())
      Blank()
      Text('用户协议').fontSize(18).fontWeight(CommonConstants.FONT_WEIGHT_600).backgroundColor(Color.White)
    }
    .width(CommonConstants.THOUSANDTH_940)
    .height(32)
  }
}