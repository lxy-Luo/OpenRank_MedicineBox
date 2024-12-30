
import { CommonConstants } from '../../common/constants/CommonConstants'
/*
 * 首页轮播图第一页：热量统计组件*/

@Component
export default struct CalorieStats {
  @Prop intake: number //摄入热量
  @Prop expend: number //运动消耗热量
  recommend: number = CommonConstants.RECOMMEND_CALORIE  //每日推荐摄入热量

  remainCalorie(){
    return this.recommend - this.intake + this.expend
  }
  build() {
    Row({space: CommonConstants.SPACE_6}){
      //饮食摄入
      this.StatsBuilder({label:'饮食摄入',value: this.intake})
      //还可以吃
      Stack(){
        Progress({
          value: this.intake,
          total:this.recommend,
          type:ProgressType.Ring
        })
          .width(120)
          .style({strokeWidth:CommonConstants.DEFAULT_10})
          .color($r('app.color.lv1'))
          .backgroundColor(Color.White)
        this.StatsBuilder({label:'还可以吃',value: this.remainCalorie(),tips:'推荐'+this.recommend})
      }

      //运动消耗
      this.StatsBuilder({label:'运动消耗',value: this.expend})
    }
    .width('100%')
    .justifyContent(FlexAlign.SpaceEvenly)
    .padding({top: 30,bottom: 15})
  }

  /*
   * Builder在传值的情况下，传入值改变不会触发重新渲染，使用$$传递对象，可以在页面数据更新时触发函数重新渲染*/
  @Builder StatsBuilder($$:{label: string, value: number,tips?:string}){
    Column({space: CommonConstants.SPACE_6}){
      Text($$.label)
        .fontColor(Color.White)
        .fontWeight(CommonConstants.FONT_WEIGHT_600)
      Text($$.value+'')
        .fontColor(Color.White)
        .fontSize(20)
        .fontWeight(CommonConstants.FONT_WEIGHT_700)
      if($$.tips){
        Text($$.tips)
          .fontColor(Color.White)
          .fontSize(12)
          .fontColor($r('app.color.light_gray'))
      }
    }
  }
}