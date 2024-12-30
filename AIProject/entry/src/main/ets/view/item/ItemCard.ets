import { CommonConstants } from '../../common/constants/CommonConstants'
import MedicalRecordItem from '../../viewmodel/medical/MedicalRecordItem'
import RecordItem from '../../viewmodel/RecordItem'

@Component
export default struct ItemCard {

  @Prop amount: number  //显示输入数值，用来接收键盘组件传递的输入数值，因此用双向传递
  @Link item: RecordItem


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
            if(this.item.id<5000){
              this.NutrientInfo({label:'热量(千卡)',value:this.item.calorie})
              this.NutrientInfo({label: '碳水(克)',value:this.item.carbon})
              this.NutrientInfo({label:'蛋白质(克)',value:this.item.protein})
              this.NutrientInfo({label:'脂肪(克)',value:this.item.fat})
            }else{
              this.NutrientInfo({label:'热量(千卡)',value:this.item.calorie})
            }
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
  @Builder NutrientInfo($$:{label: string, value: number}){
    Column({space: CommonConstants.SPACE_8}){
      Text($$.label).fontSize(14).fontColor($r('app.color.light_gray'))
      Text($$.value+'').fontSize(16).fontWeight(CommonConstants.FONT_WEIGHT_700)
        .textOverflow({overflow: TextOverflow.Ellipsis})
        .maxLines(4)
    }
  }
}