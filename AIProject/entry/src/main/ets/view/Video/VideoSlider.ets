@Component
export default struct VideoSlider {
  private controller: VideoController;

  //@Consume isOpacity: boolean;  //是否透明

  //@Consume progressVal: number;
  //@Consume isLoading: boolean;
  //@Consume flag: boolean;

  @Consume isPlay: boolean;   //是否在播放
  @Consume durationStringTime: string;  //总播放时长字符串
  @Consume durationTime: number;  //视频总长（毫秒值）
  @Consume currentTime: number;   //
  @Consume currentStringTime: string;
  build() {
    Row({space: 12}){
      Image(this.isPlay ? $r('app.media.drdic_pause'):$r('app.media.drdic_play')).width(24).height(24)
        .margin({left:12})
        .onClick(() => {
          this.iconOnclick();  //控制视频播放和暂停的方法
        })

      //当前时间
      Text(this.currentStringTime).fontSize(16)
        .margin({ right: 12 })
        .fontColor(Color.White)

      Slider({
        value: this.currentTime,
        min: 0,
        max: this.durationTime,
        step: 1,
        style: SliderStyle.OutSet
      })
        .blockColor($r('app.color.qian1'))
        .width('46.7%')
        .trackColor(Color.Gray)
        .selectedColor($r("app.color.qian1"))
        .showSteps(true)
        .showTips(true)
        .trackThickness( 4)
        .onChange((value: number, mode: SliderChangeMode) => {
          this.sliderOnchange(value, mode);
        })  //通过拖动滑动条来控制进度
      Text(this.durationStringTime)
        .fontSize(16)
        .margin({ right: 12 })
        .fontColor(Color.White)
    }
    //.opacity(this.isOpacity ? Number.parseFloat('0.2') : 1)
    .width("100%")
    .alignItems(VerticalAlign.Center)
    .justifyContent(FlexAlign.Center)
  }

  iconOnclick() {
    //当视频未播放的时候设置点击播放视频
    if (this.isPlay == true) {
      this.controller.pause()  //暂停视频
      this.isPlay = false;
      //this.isOpacity = false;
      //return;
    }else{
      this.controller.start();
      this.isPlay =true;
    }
    ////////////////////////////////

    // if (this.flag === true) {
    //   this.controller.start();
    //   this.isPlay = true;
    //   this.isOpacity = true;
    // } else {
    //   this.isLoading = true;
    //   // The video loading is not complete. The loading action is displayed.
    //   let intervalLoading = setInterval(() => {
    //     if (this.progressVal >= 100) {
    //       this.progressVal = 0;
    //     } else {
    //       this.progressVal += 10;
    //     }
    //   }, 100)
    //   // The scheduled task determines whether the video loading is complete.
    //   let intervalFlag = setInterval(() => {
    //     if (this.flag === true) {
    //       this.controller.start();
    //       this.isPlay = true;
    //       this.isOpacity = true;
    //       this.isLoading = false;
    //       clearInterval(intervalFlag);
    //       clearInterval(intervalLoading);
    //     }
    //   }, 100);
    // }
  }

  sliderOnchange(value: number, mode: SliderChangeMode) {
    this.currentTime = Number.parseInt(value.toString());
    this.controller.setCurrentTime(Number.parseInt(value.toString()), SeekMode.Accurate);  //跳转到视频指定时间位置
    // if (mode === SliderChangeMode.Begin || mode === SliderChangeMode.Moving) {
    //   this.isOpacity = false;
    // }
    // if (mode === SliderChangeMode.End) {
    //   this.isOpacity = true;
    // }
  }
}