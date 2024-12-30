import { CommonConstants } from '../../common/constants/CommonConstants'
import ItemModel from '../../model/ItemModel'
import GroupInfo from '../../viewmodel/GroupInfo'
import ItemCategory from '../../viewmodel/ItemCategory'
import RecordItem from '../../viewmodel/RecordItem'

/*
 * 食物，运动列表展示组件*/

@Component
export default struct ItemList {

  showPanel: (item: RecordItem) => void
  @Prop isFood: boolean

  build() {
    Tabs(){
      TabContent(){
        this.tabContentBuilder(ItemModel.list(this.isFood))
      }
      .tabBar('全部')
      //遍历对应的食物运动数据
      ForEach(
        ItemModel.listItemGroupByCategory(this.isFood)
        ,(group: GroupInfo<ItemCategory,RecordItem>) => {
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

  //封装的食物列表组件
  @Builder tabContentBuilder(items: RecordItem[]){
    List({space: CommonConstants.SPACE_10}){
      ForEach(items,(item: RecordItem) => {
        ListItem(){
          Row({space: CommonConstants.SPACE_6}){
            Image(item.image).width(50)
            Column(){
              Text(item.name).fontWeight(CommonConstants.FONT_WEIGHT_500)
              Text(item.calorie+'千卡/'+item.unit).fontSize(14).fontColor($r('app.color.light_gray'))
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