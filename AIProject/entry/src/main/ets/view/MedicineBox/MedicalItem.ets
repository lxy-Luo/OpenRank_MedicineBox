/*
 *@Author: Dairundong
 *@Date: 2024/3/28 22:30
 * 当前日期使用记录数组渲染组件（BUG）
 * */
import { CommonConstants } from '../../common/constants/CommonConstants'
import ItemModel from '../../model/ItemModel'
import MedicalRecordItem from '../../viewmodel/medical/MedicalRecordItem'
import MedicalRecordDTO from '../../viewmodel/medical/MedicalRecordDTO'
@Component
export default struct MedicalItem {
  @ObjectLink item: MedicalRecordDTO
  hhh: MedicalRecordItem

  getNowItem(item: MedicalRecordDTO){
    this.hhh = ItemModel.medicalGetById(item.DrugId)
  }
  build() {
    Row({space: CommonConstants.SPACE_6}){
      //本地图片
      Image(this.hhh.image).width(50)
      //硬件图片
      Image(this.item.image).width(50)
      Column(){
        Text('hhhh').fontSize(15).fontColor(Color.Black)
        Text(this.item.time).fontSize(10).fontColor(Color.Black)
      }
      .justifyContent(FlexAlign.SpaceAround)
      .alignItems(HorizontalAlign.Start)
      //.backgroundColor(Color.Black)
      .layoutWeight(1)
      Column(){
        Text(this.item.All_number+"("+this.hhh.unit+")").fontColor(Color.Black).fontSize(20)
      }
    }
    .margin({left:8})
    .padding({left: 10,right:10,top:3})
    .width('95%')
    .height('15%')
    .backgroundColor($r('app.color.light_primary_color'))
    .borderRadius(10)
  }
}