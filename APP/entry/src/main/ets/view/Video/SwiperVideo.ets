@Component


export default struct SwiperVideo {

  @Styles fillScreen(){
  .width('100%')
  .height('100%')
  .padding(20)
}

  build() {

    Column(){
      Swiper(){
        Image('https://drd-web.oss-cn-beijing.aliyuncs.com/15.png').borderRadius(20)
        Image('https://drd-web.oss-cn-beijing.aliyuncs.com/16.png').borderRadius(20)
        Image('https://drd-web.oss-cn-beijing.aliyuncs.com/17.png').borderRadius(20)
        Image('https://drd-web.oss-cn-beijing.aliyuncs.com/13.png').borderRadius(20)
      }.autoPlay(true).backgroundColor($r('app.color.hei1')).width('90%').height('100%')
    }.justifyContent(FlexAlign.Start).width('90%').height('20%').margin({top:50,left:20,right:20,bottom:0})
  }
}