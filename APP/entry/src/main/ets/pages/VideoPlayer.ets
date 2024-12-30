/*
 * 作者：drd
 * */


import { CommonConstants } from '../common/constants/CommonConstants';
import router from '@ohos.router';
import VideoSlider from '../view/Video/VideoSlider';
import { changeSliderTime } from '../model/videoModel/VideoControll';
import prompt from '@ohos.prompt';
import CommentData from '../model/videoModel/CommentData';
import CommentModel from '../model/videoModel/CommentModel';

// @Extend(Image) function  changeColorRed() {
//   .fillColor('#d81e06')
// }
//
// @Extend(Image) function  changeColorGray() {
//   .fillColor('#c3c4c5')
// }


@Entry
@Component
struct VideoPlayer {
  private controller: VideoController;   //用于控制视频暂停等

  @Provide isPlay: boolean = false;
  //@Provide isOpacity: boolean;

   @Provide currentTime: number = 0;   //当前播放时长（秒）
   @Provide currentStringTime: string = '00:00'  //当前播放时长字符串


  //@Consume flag: boolean;
  @Provide durationTime: number = 0   // 视频总时长（秒）
  @Provide durationStringTime: string = '00:00'  //视频总时长，会重新赋值

  //控制评论区开启、关闭
  @State isShow1: boolean=false
  //控制评论的回复区开关
  @State isShow2: boolean=false
  //点赞时改变爱心的颜色
  @State changeColor: boolean=false

  //接收视频的参数
  //@State videoParams: Object=router.getParams()
  @State comments: CommentModel[]=[]

  private source: string='https://drd-web.oss-cn-beijing.aliyuncs.com/%E3%80%90%E8%B6%85%E7%87%83%E5%8A%B1%E5%BF%97%E3%80%91%E6%B2%B9%E7%AE%A13000%E4%B8%87%E6%92%AD%E6%94%BE%E2%80%94%E2%80%94%E3%80%8A%E6%B2%A1%E6%9C%89%E5%80%9F%E5%8F%A3%E3%80%8BNO%20EXCUSES%EF%BC%81%E6%AF%8F%E5%A4%A9%E4%B8%80%E9%81%8D%EF%BC%8C%E9%98%B2%E6%AD%A2%E9%A2%93%E5%BA%9F%EF%BC%81.mp4';   //视频地址
  private previewUris: ResourceStr='https://drd-web.oss-cn-beijing.aliyuncs.com/f6ff5799-1def-4deb-842b-77e1fc0db1d1.jpg'   //视频预览图
  private name: string =  '自律'
  private videoId:number= 1

  aboutToAppear(){
    //1.获取跳转时的参数
    let params: any = router.getParams()
    //2.获取跳转的药品信息
    this.source = params.source
    this.previewUris = params.previewUris
    this.name = params.name
    this.videoId=params.id
    this.comments =CommentData.getByVideoId(this.videoId)
  }


  build() {
    Column() {
      Row(){
        this.Header()
      }
      Row(){
        Video({
          src: this.source,
          previewUri: this.previewUris,  //预览图片
          controller: this.controller  //视频控制器
        })
          .width('100%')
          .height('90%')
          .objectFit(ImageFit.Contain)  //使视频保持宽高比例缩放
            //.controls(false)  //原生控制器不在显示
            //视频播放准备完成时触发改事件
          .onPrepared((event) => {
            this.prepared(event?.duration);
          })
          .onUpdate((event) => {
            this.currentTime = event.time; //获取当前的播放时间
            this.currentStringTime = changeSliderTime(this.currentTime)  //将时长修改为最终的分秒显示形式，赋值给当前进度字符串
          })
          .onFinish(() =>{
            this.finish()
          })
          .onError(() => {
            prompt.showToast({
              duration: 5000,  //持续时间
              message: '请检查网络'
            });
          })
        //自定义组件导致线程崩溃暂时禁用此功能
        //VideoSlider({controller: this.controller})   //自定义视频控制组件
      }
      .layoutWeight(1)
      Row(){
        if(!this.isShow1){
          Text("点击跳转评论区")
            .fontColor(Color.White)
            .fontWeight(30)
            .onClick(()=>{
              this.isShow1=true
            })
        }
      }
      //视频的回复
      Panel(this.isShow1){
        Stack({ alignContent: Alignment.Bottom }) {
          //this.NavComp("评论")
          Column() {
            Scroll() {
              Column() {
                //导航
                 this.NavComp("评论")
                //评论
                this.CommentComp()
              }
              .padding({ bottom: 50 })
            }
          }
          .width('100%')
          .height('100%')
          //底部回复栏
          Row({ space: 15 }){
            TextInput({ placeholder: '回复~' })
              .layoutWeight(1)
            Text('发布')
              .fontColor('#069')
          }
          .padding({ left: 15, right: 15 ,bottom: 50})
          .width('100%')
          .height(120)
          .backgroundColor('#fff')
          .border({ width: { top: 0.5 }, color: '#e4e4e4' })
        }
      }
      .mode(PanelMode.Full)
      .dragBar(false)
      .backgroundMask($r('app.color.light_gray'))
      .backgroundColor(Color.White)

      //评论的回复
      Panel(this.isShow2){
        Stack({ alignContent: Alignment.Bottom }) {
          Column() {
            Scroll() {
              Column() {
                // 导航
                this.NavComp("评论回复")
                // 评论
                //this.CommentComp()
                // 分割线
                // Divider()
                //   .strokeWidth(8)
                //   //.color('#f5f5f5')
                //   .color(Color.Black)
                // 回复列表
                this.test1()
              }
              .padding({ bottom: 50 })
            }
          }
          .width('100%')
          .height('100%')
          //底部回复栏
          Row({ space: 15 }){
            TextInput({ placeholder: '回复~' })
              .layoutWeight(1)
            Text('发布')
              .fontColor('#069')
          }
          .padding({ left: 15, right: 15 ,bottom: 60})
          .width('100%')
          .height(120)
          .backgroundColor('#fff')
          .border({ width: { top: 0.5 }, color: '#e4e4e4' })
        }
      }
      .mode(PanelMode.Full)
      .dragBar(false)
      .backgroundMask($r('app.color.light_gray'))
      .backgroundColor(Color.White)

    }
    .backgroundColor($r('app.color.hei1'))
    .width('100%')
    .height('100%')
  }

  //评论区导航
  @Builder
  NavComp(title: string) {
    Row() {
      Row() {
        Image($r('app.media.lxyreturn'))
          .width(24)
          .aspectRatio(1)
          .onClick(()=>{
            if(this.isShow1){
              this.isShow1=false
            }
            if(this.isShow2){
              this.isShow2=false
              this.isShow1=true
            }
          })
      }
      .width(24)
      .aspectRatio(1)
      .backgroundColor('#f5f5f5')
      .borderRadius(12)
      .justifyContent(FlexAlign.Center)
      .margin({ left: 16 })


      Text(title)
        .layoutWeight(1)
        .textAlign(TextAlign.Center)
        .padding({ right: 40 })
    }
    .height(40)
    .border({ width: { bottom: 0.5 }, color: '#e4e4e4' })
    .padding({top: 20})
  }
  //评论
  @Builder
  CommentComp() {
    List(){
      ForEach(this.comments,(item:CommentModel)=>{
        ListItem(){
          Row() {
            Image(item.userImage)
              .width(32)
              .aspectRatio(1)
              .borderRadius(16)
            Column({ space: 5 }) {
              Text(item.userName)
                .width('100%')
                .fontWeight(FontWeight.Bold)
                .fontSize(15)
              Text(item.message)
                .width('100%')
                .onClick(()=>{
                  this.isShow1=false
                  this.isShow2=true
                })
              Row() {
                Text(item.time+' . IP属地'+item.address)
                  .fontSize(12)
                  .fontColor('#c3c4c5')
                Row({ space: 4 }) {
                  Image($r('app.media.heart'))
                    .width(14)
                    .aspectRatio(1)
                    .fillColor(this.changeColor?'#d81e06':'#c3c4c5')
                    .onClick(()=>{
                      this.changeColor=!this.changeColor
                    })
                  Text(item.ThumbsUpNumber.toString())
                    .fontSize(12)
                    .fontColor('#c3c4c5')
                }
              }
              .width('100%')
              .justifyContent(FlexAlign.SpaceBetween)
            }
            .layoutWeight(1)
            .padding({ left: 10 })
          }
          .padding(15)
          .alignItems(VerticalAlign.Top)
        }

      })
    }
    .padding({bottom: 150})



  }
  //评论的回复
  @Builder
  test1(){
    Column() {
      Text('回复 100')
        .width('100%')
        .fontWeight(600)
      ForEach([1, 2, 3, 4, 5, 6, 7], () => {
        Row() {
          Image($r('app.media.lxy_50033'))
            .width(32)
            .aspectRatio(1)
            .borderRadius(16)
          Column({ space: 5 }) {
            Text('周杰伦')
              .width('100%')
              .fontWeight(FontWeight.Bold)
              .fontSize(15)
            Text('大理石能雕刻出肌肉和皮肤的质感，那个年代的工匠好牛啊')
              .width('100%')
            Row() {
              Text('10-21 · IP属地北京')
                .fontSize(12)
                .fontColor('#c3c4c5')
              Row({ space: 4 }) {
                Image($r('app.media.lxy_50032'))
                  .width(14)
                  .aspectRatio(1)
                  .fillColor('#c3c4c5')
                Text('100')
                  .fontSize(12)
                  .fontColor('#c3c4c5')
              }
            }
            .width('100%')
            .justifyContent(FlexAlign.SpaceBetween)
          }
          .layoutWeight(1)
          .padding({ left: 10 })
        }
          .padding({ top: 15, bottom: 15 })
        .alignItems(VerticalAlign.Top)
      })

    }
    .padding(15)
  }





  @Builder Header(){
    Row(){
      Image($r('app.media.drdreturn1'))
        .backgroundColor($r('app.color.hei1'))
        .width(24)
        .onClick(() => router.back())
      Blank()
      Text(this.name)
        .fontSize(12)
        .fontWeight(CommonConstants.FONT_WEIGHT_600)
        .backgroundColor($r('app.color.hei1'))
        .fontColor($r('app.color.qian1'))
    }
    .width(CommonConstants.THOUSANDTH_940)
    .height(35)
  }

  //视频准备好时的回调函数   获取/计算视频时长
  prepared(duration: number) {
    this.durationTime = duration;  //赋值上视频时长（单位为秒）
    let second: number = duration % 60;  //剩余的秒数
    let min: number = Number.parseInt((duration / 60).toString());   //分钟数
    let head = min < 10 ? '0'+min : min;  //缺位时，拼接字符串补0
    let end = second < 10 ? '0'+second : second;
    this.durationStringTime = ''+head+':'+end;  //当前播放时间字符串
    //this.flag = true;
  }

  finish() {
    this.isPlay = false;
    //this.isOpacity = false;
  }
}