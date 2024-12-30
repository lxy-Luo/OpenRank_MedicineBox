import { CommonConstants } from '../../common/constants/CommonConstants'
import MedicalRecordItem from '../../viewmodel/medical/MedicalRecordItem'

@Component
export default struct MedicalItemCard {

  @Prop amount: number  //显示输入数值，用来接收键盘组件传递的输入数值，因此用双向传递
  @Link item: MedicalRecordItem

  build() {
    List(){
      ListItem(){
        Column({space:CommonConstants.SPACE_8}){
          Image(this.item.image)
            .width(150)
          Text(this.item.name)
            .fontWeight(CommonConstants.FONT_WEIGHT_700)
            .backgroundColor($r('app.color.lightest_primary_color'))
            .padding({top:5,bottom:5,left: 12,right:12})
          //分隔器
          Divider()
            .width(CommonConstants.THOUSANDTH_940)
            .opacity(0.6)
          Row({space: CommonConstants.SPACE_8}){
              this.NutrientInfo({label:'药品介绍', value: this.item.introduction})
              //这里有判断，因为食物的id是有限的，为了更方便区分食物和运动，我们将运动的id设置为10000起步，可以比免额外传变量

          }
          //分隔器
          Divider()
            .width(CommonConstants.THOUSANDTH_940)
            .opacity(0.6)
          //数值显示
          Row(){
            Column({space:CommonConstants.SPACE_4}){
              Text(this.amount.toFixed(1))
                .fontSize(50)
                .fontColor($r('app.color.primary_color'))
                .fontWeight(CommonConstants.FONT_WEIGHT_600)
              Divider()
                .width(CommonConstants.THOUSANDTH_400)
                .color($r('app.color.primary_color'))
            }
            Text(this.item.unit)
              .fontColor($r('app.color.light_gray'))
              .fontWeight(CommonConstants.FONT_WEIGHT_600)
          }
        }
      }
    }
  }
  @Builder NutrientInfo($$:{label: string, value: ResourceStr}){
    Column({space: CommonConstants.SPACE_8}){
      Text($$.label).fontSize(14).fontColor($r('app.color.light_gray'))
      Text($$.value).fontSize(16).fontWeight(CommonConstants.FONT_WEIGHT_700)
        .textOverflow({overflow: TextOverflow.Ellipsis})
        .maxLines(4)
    }
  }
}