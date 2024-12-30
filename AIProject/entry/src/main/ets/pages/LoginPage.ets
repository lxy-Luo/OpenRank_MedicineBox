/**
 * @author 夏俊杰
 * @date 2024/3/28 20:53
 * @description 登录页面
 *
 */

import prompt from '@ohos.promptAction';  //提示框依赖
import router from '@ohos.router';   //跳转路由依赖
import axios from '@ohos/axios'
import PreferenceUtils from '../common/utils/PreferenceUtil'


const SERVICE_ADDRESS = $r('app.string.baseURL')
/*
 * 输入框样式
 * */
@Extend(TextInput) function inputStyle(isFirst:boolean) {
  .placeholderColor(isFirst?"#99182431":"#E54646")
  .height("45vp")
  .fontSize("18fp")
  .backgroundColor("#F1F3F5")
  .width('80%')
  .padding({ left: 0 })   //左边置顶
  .margin({ top: 12 })  //上边距12
}
/*
 * 分隔线样式
 * */
@Extend(Line)
function lineStyle() {
  .width("85%")
  .height(1)
  .backgroundColor("#33182431")
}
/*
 * 忘记密码文本样式
 * */
@Extend(Text)
function blueTextStyle() {
  .fontColor("#007DFF")
  .fontSize(14)
  .fontWeight(FontWeight.Medium)
}


/**
 * 图标样式
 */
@Extend(Image)
function iconStyle(){
  .width(20)
  .height(30)
  .padding({top:8})
  .fillColor(Color.Gray)
}
@Entry
@Component
struct LoginPage {

  @State isFirst :boolean = true
  @State passwordMessage : string = "密码"
  @State currentText: string = ''
  @State userName: string = '';   //账号
  @State password: string = '';  //密码
  @State isShowProgress: boolean = false;  //是否显示加载动画

  // 登录前查看首选项是否有数据
  async onPageShow(){
    this.userName = (await PreferenceUtils.getPreferenceValue("userName",'')).toString()
    this.password = (await PreferenceUtils.getPreferenceValue("password",'')).toString()
    // 有数据则直接登录
    console.log(this.userName+this.password)
    if(this.userName&&this.password){
      this.login()
    }
  }
  /*
* 登录方法
* */
  login() {
    if (this.userName === '' || this.password === '') {
      //提示框
      prompt.showToast({
        message: $r('app.string.inputUserInfo')
      })
    } else {
      this.isShowProgress = true
      //这里发送axios给后端请求
      axios.get(
        "http://123.56.84.181:81/smb/user/logIn/get/"+this.userName+"/"+this.password,{
        timeout:3000
      }
        // `${SERVICE_ADDRESS}/smb/user/logIn/get/${this.userName}/${this.password}`, // 服务器地址
        // {
        //   params:{
        //       username: this.userName,
        //       password: this.password
        //   },
      ).then((res)=>{

        //登录成功状态码校验
        this.isShowProgress = false
        if(res.data.code === 0) {
          // 将用户用户名，密码，唯一uid保存
          PreferenceUtils.putPreferenceValue("userName",this.userName)
          PreferenceUtils.putPreferenceValue("password",this.password)
          PreferenceUtils.putPreferenceValue("userId",res.data.userId)
          PreferenceUtils.putPreferenceValue("uname",res.data.name)
          PreferenceUtils.putPreferenceValue("email",res.data.email)
          PreferenceUtils.putPreferenceValue("ecId",res.data.ecId)
          router.replaceUrl({url:'pages/Index'})
        }else {
          prompt.showToast({
            message: res.data.msg
          })
          this.password = ''
          this.currentText = ''
          this.passwordMessage = '请重试'
          this.isFirst = false
        }
      })
        .catch((err)=>{
          //模拟跳转
          router.replaceUrl({url:'pages/Index'})
          // this.isShowProgress = false
          // prompt.showToast({
          //   message: $r('app.string.networkOrServiceError')
          // })
          // console.log(`${err.code},${err.message}`)
        })


    }
  }

  build() {
    Column() {
      //登录页图标
      Image($r('app.media.drdlovefrost'))
        .width(78)
        .height(78)
        .margin({top: 100,bottom: 8})
      Text($r('app.string.appName'))
        .fontSize(24)
        .fontWeight(FontWeight.Medium)
      Text($r('app.string.appInfo'))
        .fontSize(16)
        .fontColor('#39413a3a')
        .margin({ bottom: 30, top: 8})
      //用户名框
      Row({space: 8}){
        Image($r('app.media.username'))
          .iconStyle()
        TextInput({ placeholder: $r('app.string.userName') ,text:this.userName})
          .type(InputType.Normal)
          .inputStyle(true)
          .onChange((value: string) => {
            this.userName = value;
          })
      }
      //分隔线
      Line().lineStyle()
      //密码框
      Row({space:6}){
        Image($r('app.media.password'))
          .iconStyle()
        // 条件判断，若密码错误则重新渲染组价
        if(this.passwordMessage){
          // 如果密码不为空且第一次登录，则直接显示密码。否则即为请重试
          TextInput({ placeholder:this.passwordMessage,text:this.password?this.password:this.currentText})
            .maxLength(8)
            .type(InputType.Password)
            .inputStyle(this.isFirst)
            .onChange((value: string) => {
              this.password = value
              this.currentText = value
            })
        }
      }

      Line().lineStyle()
      //忘记密码，短信验证部分
      Row() {
        Text($r('app.string.messageSMS')).blueTextStyle()
        Text($r('app.string.forgetPassword')).blueTextStyle()
      }
      .justifyContent(FlexAlign.SpaceBetween)   //Row容器两边分布局
      .width("90%")
      .margin({ top: 8})
      Button($r('app.string.login'), { type: ButtonType.Capsule })
        .width('90%')
        .height(40)
        .fontSize(16)
        .fontWeight(FontWeight.Medium)
        .backgroundColor("#007DFF")
        .margin({ top: 87, bottom: 12 })
        .onClick(() => {
          this.login();
        })
      Button($r('app.string.signUp'), { type: ButtonType.Capsule })
        .width('90%')
        .height(40)
        .fontColor(Color.Black)
        .fontWeight(FontWeight.Regular)
        .fontSize(16)
        .backgroundColor(Color.White)
        .margin({ bottom: 12 })
        .onClick(() => {
          router.pushUrl({url:'pages/SignUpPage'})
        })
      //加载动画
      if (this.isShowProgress) {
        LoadingProgress()
          .color("#ff0c66c4")
          .width(40)
          .height(40)
          .margin({ top:20 })
      }
      Blank()
      Text($r('app.string.copyRight'))
        .fontColor("#838D97")
        .fontSize(12)
        .fontWeight(FontWeight.Medium)
        .margin({ top: 50, bottom: 12 })
    }
    .backgroundColor("#F1F3F5")
    .height('100%')
    .width('100%')
    .padding({
      left: 12,
      right: 12,
      bottom: 24
    })
  }



}
