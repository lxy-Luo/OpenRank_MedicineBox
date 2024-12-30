/**
 * @author 夏俊杰
 * @date 2024/4/16 21:43
 * @description 注册页面
 *
 */

import prompt from '@ohos.promptAction';  //提示框依赖
import router from '@ohos.router';   //跳转路由依赖
import axios from '@ohos/axios'
import PreferenceUtils from '../common/utils/PreferenceUtil'

class SignUpVO{
  userName: string  //账号
  password: string  //密码
  uName: string // 用户姓名
  email:string // 邮箱

  constructor(userName:string,password:string,uName:string,email:string) {
    this.userName= userName;
    this.uName = uName;
    this.email = email;
    this.password = password
  }
}

const SERVICE_ADDRESS = $r('app.string.baseURL')
/*
 * 输入框样式
 * */
@Extend(TextInput) function inputStyle() {
  .placeholderColor("#99182431")
  .height("9.5%")
  .fontSize("18fp")
  .backgroundColor("#F1F3F5")
  .width('80%')
  .padding({ left: 5 })   //左边置顶
  .margin({ top: '4%' })
  .backgroundColor(Color.White)
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
  .width(25)
  .height(30)
  .margin({left:5})
  .padding({top:8,left:3})
  .fillColor(Color.Gray)
}
@Entry
@Component
struct SignUpPage {

  @State userName: string = '';   //账号
  @State password: string = '';  //密码
  @State uName: string = '' // 用户姓名
  @State email:string = '' // 邮箱
  @State SMSCode:string='' // 邮箱验证码
  @State confirmPassword:string = '' // 确认密码
  @State isShowProgress: boolean = false;  //是否显示加载动画
  /*
* 登录方法
* */
  getSMSCode() {
    if (this.userName === '' || this.confirmPassword===''
    || this.password === '' || this.uName===''||this.email==='') {
      //提示框
      prompt.showToast({
        message: $r('app.string.info_not_enough')
      })
      return
    }
    if(this.password!==this.confirmPassword){
      prompt.showToast({message:'两次输入的密码不一致'})
    }
    else {
      this.isShowProgress = true
      //这里发送axios给后端请求
      axios.get(
        "http://123.56.84.181:81/smb/user/register" ,{
        params:{
          uusername: this.userName,
          uname: this.uName,
          uemail: this.email,
          upassword: this.password
        },
        timeout:30000,
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
          prompt.showToast({
            message:"验证码已发送至对应邮箱"
          })
        }else {
          prompt.showToast({
            message: res.data.msg
          })
        }
      })
        .catch((err)=>{
          this.isShowProgress = false
          prompt.showToast({
            message: $r('app.string.networkOrServiceError')
          })
          console.log(`${err.code},${err.message}`)
        })
    }
  }

  SignUp(){
    if (this.userName === '' || this.confirmPassword===''||this.SMSCode===''
    || this.password === '' || this.uName===''||this.email==='') {
      //提示框
      prompt.showToast({
        message: $r('app.string.info_not_enough')
      })
      return
    }
    if(this.password!==this.confirmPassword){
      prompt.showToast({message:'两次输入的密码不一致'})
    }
    else {
      this.isShowProgress = true
      //这里发送axios给后端请求
      axios.get(
        "http://123.56.84.181:81/smb/user/verification" ,{
        params:{
          uverifyCode:this.SMSCode,
          uusername: this.userName,
          uname: this.uName,
          uemail: this.email,
          upassword: this.password
        },
        timeout:30000,
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
          prompt.showToast({
            message:'账户注册成功，3s后跳转至登录页面'
          })
          setTimeout(()=>{
            router.replaceUrl({url:'pages/LoginPage'})
          },3000)
        }else {
          prompt.showToast({
            message: res.data.msg as string
          })
        }
      })
        .catch((err)=>{
          this.isShowProgress = false
          prompt.showToast({
            message: $r('app.string.networkOrServiceError')
          })
          console.log(`${err.code},${err.message}`)
        })
    }
  }

  build() {
    Column() {
      //登录页图标
      Row(){
        Image($r('app.media.ic_public_back'))
          .width(30)
          .height(30)
          .onClick(()=>{
            router.back()
          })
      }
      .width('100%')
      Image($r('app.media.drdlovefrost'))
        .width('30%')
        .height('10%')
        .margin({top: 10,bottom: 8})
      Text($r('app.string.appName'))
        .fontSize(24)
        .fontWeight(FontWeight.Medium)
      Text($r('app.string.appInfo'))
        .fontSize(16)
        .fontColor('#39413a3a')
        .margin({ bottom: 30, top: 8})
      Column({space:8}){
        //姓名框
        Row({space: 6}){
          Image($r('app.media.username'))
            .iconStyle()
          TextInput({ placeholder: $r('app.string.real_name') ,text:this.uName})
            .maxLength(11)    //输入最大长度
            .inputStyle()
            .type(InputType.Normal)
            .onChange((value: string) => {
              this.uName = value;
            })
        }
        //分隔线
        Line().lineStyle()
        //用户名
        Row({space:6}){
          Image($r('app.media.username'))
            .iconStyle()
          TextInput({ placeholder: $r('app.string.userName') ,text:this.userName})
            .maxLength(11)    //输入最大长度
            .inputStyle()
            .onChange((value: string) => {
              this.userName = value;
            })
        }
        Line().lineStyle()
        // 密码
        Row({space:6}){
          Image($r('app.media.password'))
            .iconStyle()
          TextInput({ placeholder:$r('app.string.password')})
            .type(InputType.Password)
            .inputStyle()
            .onChange((value: string) => {
              this.password = value
            })

        }
        Line().lineStyle()
        // 确认密码
        Row({space:6}){
          Image($r('app.media.password'))
            .iconStyle()
          TextInput({ placeholder: $r('app.string.confirm_password')})
            .inputStyle()
            .type(InputType.Password)
            .onChange((value: string) => {
              this.confirmPassword = value;
            })
        }
        Line().lineStyle()
        // 邮箱
        Row({space:6}){
          Image($r('app.media.ic_public_email'))
            .iconStyle()
          Stack(){
            TextInput({ placeholder:$r('app.string.email'),text:this.email})
              .type(InputType.Email)
              .inputStyle()
              .onChange((value: string) => {
                this.email = value
              })
            // 获取验证码按钮
            Text(' 验证码 ')
              .fontSize('15fp')
              .fontColor(Color.Grey)
              .width(55)
              .height(30)
              .backgroundColor(Color.White)
              .border({
                color:Color.Grey
              })
              .shadow({
                radius:3
              })
              .onClick(()=>{
                this.getSMSCode()
              })
              .margin({left:200})
          }
        }
        Line().lineStyle()
        //验证码
        Row({space:6}){
          Image($r('app.media.SMS'))
            .iconStyle()
          TextInput({ placeholder:$r('app.string.sms_code'),text:this.SMSCode})
            .maxLength(8)
            .type(InputType.Email)
            .inputStyle()
            .onChange((value: string) => {
              this.SMSCode = value
            })
        }
        Line().lineStyle()
      }
      .shadow({radius:8,color:Color.Grey})
      .borderRadius(5)
      .backgroundColor(Color.White)
      .borderWidth(0.02)
      .height('60%')
      .margin({bottom:20})

      Button($r('app.string.signUp'), { type: ButtonType.Capsule })
        .width('90%')
        .height('6%')
        .fontWeight(FontWeight.Regular)
        .fontSize(16)
        .margin({ bottom: 12 })
        .onClick(() => {
          /*          let signUpVO = new SignUpVO(
                        this.userName,
                        this.uName,
                        this.email,
                        this.password
                    )
                    let jsonString = JSON.stringify(signUpVO) as string
                    console.log(jsonString)*/
          this.SignUp()
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
