
import preferenceUtil from '../common/utils/PreferenceUtil'
import router from '@ohos.router'
import prompt from '@ohos.prompt'
import axios from '@ohos/axios'


//透明文本样式封装
@Extend(Text) function opacityWhiteText(opacity: number, fontSize: number = 10){
  .fontSize(fontSize)
  .opacity(opacity)
  .fontColor(Color.White)
}

//首选项存储用户账号信息的键值
const USER_NAME = 'userName'
const USER_PASSWORD = 'password'
const SERVICE_ADDRESS = 'http://123.56.84.181:81'

@Entry
@Component
struct WelcomePage {
  isJump : boolean = false
  //上下文api
  //context  = getContext(this) as common.UIAbilityContext
  //声明对话框控制器
/*  controller:CustomDialogController = new CustomDialogController({
    builder: UserPrivacyDialog({
      confirm: () => this.onConfirm(),
      cancel: () => this.exitApp()
    })
  })*/
  async login(userName:string,password: string){
    // 这里发请求给后端校验
      if(userName&&password){
        await axios.get(
          `${SERVICE_ADDRESS}/smb/user/logIn/get/${userName}/${password}`, // 服务器地址
          {
            params:{
              uusername: userName,
              upassword: password
            },
            timeout: 3000
          }).then((res)=>{
          //登录成功状态码校验
          if(res.data.code === 0) {
            router.replaceUrl({url:'pages/Index'})
            this.isJump = true
          }else {
            prompt.showToast({
              message: '用户信息发生更改，请重新登录!'
            })
          }
        })
          .catch((err)=>{
            prompt.showToast({
              message: '网络不可用或服务器内部异常!'
            })
            console.log(`出现错误${err.code},${err.message}`)
          })
      }
      if(!this.isJump){
        router.replaceUrl({ url: 'pages/LoginPage' })
      }

  }

  // 这里的延迟函数可以去掉，加这个延迟函数可以让后台校验效果更明显
  // 在登陆时后台进行身份校验
  aboutToAppear() {
    setTimeout(async () => {
      let userName = await  preferenceUtil.getPreferenceValue(USER_NAME, '')
      let password = await preferenceUtil.getPreferenceValue(USER_PASSWORD, '')
      this.login(userName.toString(), password.toString())
    }, 1000)
  }
    //判断
/*    if(isAgree){
      //跳转首页
      this.jumpToIndex()
    }else{
      //打开弹窗
      //this.controller.open()
    }*
  //点击同意后调用
/*  onConfirm(){
    //保存首选项
    preferenceUtil.putPreferenceValue(PREF_KEY,true)
    console.log('保存用户协议的首选项了')
    //跳转到首页
    this.jumpToIndex()
  }*/
  // 跳转首页
/*  exitApp(){
    //退出APP(终结自己)
    //this.context.terminateSelf()
  }*/

  build() {
    Column({space: 10}) {
        //中央Slogan
      Row(){
        Image($r('app.media.drdThree')).width(260)
      }
      .layoutWeight(1)   //分配权重
      //logo
      Text('鸿蒙智能健康药箱app').fontSize(30).fontColor(Color.White)
      //文字描述
      Row(){
        Text('启明星创新实验室支持').opacityWhiteText(0.8,12)
        Text('IPv6')
          .opacityWhiteText(0.8,12)
          .padding({left:5,right:5})
          .border({style: BorderStyle.Solid, width: 1,color: Color.White , radius: 15})
        Text('网络').opacityWhiteText(0.8,12)
      }
      Text('只为帮助更多人成就健康')
        .opacityWhiteText(0.6,10)
      Text('鄂ICP备888899998888')
        .opacityWhiteText(0.4,10)
        .margin({bottom: 35})
    }
    .height('100%')
    .width('100%')
    .backgroundColor($r('app.color.welcome_page_background'))  //欢迎界面背景颜色（通过文本定义调用）
  }
}