
import { CommonConstants } from '../../common/constants/CommonConstants'
/*
 * 首页轮播图第2页：营养素统计组件*/

@Component
export default struct CalorieStats {
  @Prop carbon: number    //碳水
  @Prop  protein: number  //蛋白质
  @Prop fat: number  //脂肪

  //推荐碳水，蛋白，脂肪
  recommendCarbon: number = CommonConstants.RECOMMEND_CALORIE
  recommendProtein: number = CommonConstants.RECOMMEND_PROTEIN
  recommendFat: number = CommonConstants.RECOMMEND_FAT

  build() {
    Row({space: CommonConstants.SPACE_6}){
      //碳水
      this.StatsBuilder({
        label:'碳水',
        value: this.carbon,
        recommend:this.recommendCarbon,
        color:$r('app.color.lv1')})
      //蛋白质
      this.StatsBuilder({
        label:'蛋白质',
        value:this.protein,
        recommend:this.recommendProtein,
        color:$r('app.color.lv1')})
      //脂肪
      this.StatsBuilder({
        label:'脂肪',
        value:this.fat,
        recommend:this.recommendFat,
        color:$r('app.color.lv1')})
    }
    .width('100%')
    .justifyContent(FlexAlign.SpaceEvenly)
    .padding({top: 30,bottom: 15})
  }

  @Builder StatsBuilder($$:{label: string, value: number,recommend: number,color: ResourceStr}){
    Column({space: CommonConstants.SPACE_6}){
      Stack(){
        Progress({
          value: $$.value,
          total: $$.recommend,
          type: ProgressType.Ring
        })
          .width(95)
          .style({strokeWidth: CommonConstants.DEFAULT_6})
          .color( $$.color)
        Column(){
          Text('摄入推荐')
            .fontSize(12)
            .fontColor(Color.White)
          Text( $$.value.toFixed(0) + '/'+  $$.recommend.toFixed(0))
            .fontSize(18)
            .fontColor(Color.White)
            .fontWeight(CommonConstants.FONT_WEIGHT_700)
        }

      }
      Text( $$.label+'(克)')
        .fontSize(12)
        .fontColor(Color.White)
    }
  }
}