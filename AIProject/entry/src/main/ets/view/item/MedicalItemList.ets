/*
 *@Author: Dairundong
 *@Date: 2024/4/1 17:07
 * 药品列表显示组件
 * */

import { CommonConstants } from '../../common/constants/CommonConstants'
import ItemModel from '../../model/ItemModel'
import GroupInfo from '../../viewmodel/GroupInfo'
import ItemCategory from '../../viewmodel/ItemCategory'
import MedicalRecordItem from '../../viewmodel/medical/MedicalRecordItem'

@Component
export default struct ItemList {

  showPanel: (item: MedicalRecordItem) => void

  build() {
    Tabs(){
      TabContent(){
        this.tabContentBuilder(ItemModel. medicalList())
      }
      .tabBar('全部')
      //遍历对应的药品数据
      ForEach(
        ItemModel.ListMedicalItemGroupByCategory()
        ,(group: GroupInfo<ItemCategory,MedicalRecordItem>) => {
        TabContent(){
          this.tabContentBuilder(group.items)
        }
        .tabBar(group.type.name)
      })
    }
    .width(CommonConstants.THOUSANDTH_940)
    .height('100%')
    .barMode(BarMode.Scrollable)   //导航栏超出范围可滑动
  }

  //封装的药品列表组件
  @Builder tabContentBuilder(items: MedicalRecordItem[]){
    List({space: CommonConstants.SPACE_10}){
      ForEach(items,(item: MedicalRecordItem) => {
        ListItem(){
          Row({space: CommonConstants.SPACE_6}){
            Image(item.image).width(50)
            Column(){
              Text(item.name).fontWeight(CommonConstants.FONT_WEIGHT_500)
              Text('单位:'+item.unit).fontSize(14).fontColor($r('app.color.light_gray'))
            }
            Blank()
            Image($r('app.media.ic_public_add_norm_filled'))
              .width(18)
              .fillColor($r('app.color.primary_color'))
          }
          .width('100%')
          .padding(CommonConstants.SPACE_6)
        }
        .onClick(() => this.showPanel(item))
      })
    }
    .height('100%')
    .width('100%')
  }
}