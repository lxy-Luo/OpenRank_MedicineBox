import router from '@ohos.router'
import ItemCard from '../view/item/ItemCard'
import ItemList from '../view/item/ItemList'
import ItemPanelHeader from '../view/item/ItemPaneHeader'
import NumberKeyboard from '../view/item/NumberKeyboard'
import { CommonConstants } from '../common/constants/CommonConstants'
import RecordItem from '../viewmodel/RecordItem'
import { RecordTypeEnum, RecordTypes } from '../model/RecordTypeModel'
import RecordType from '../viewmodel/RecordType'
import RecordService from '../service/RecordService'

/*
 * 食物记页页面
 * 运动记录页面*/

@Entry
@Component
struct ItemIndex {
  @State amount: number = 1  //物品数量
  @State value: string = '' //拼接从键盘获取的数值
  @State showPanel: boolean = false
  @State item: RecordItem = null  //底部弹窗显示的记录项数据
  @State type: RecordType = RecordTypes[0]  //当前list页面的类型，默认早餐
  @State isFood: boolean = true

  onPanelShow(item: RecordItem){
    //初始化相关数值
    this.amount = 1
    this.value = ''
    this.item = item  //给当前记录项赋值
    this.showPanel = true
  }
  //页面显示时生命周期钩子
  onPageShow(){
    //1.获取跳转时的参数
    let params: any = router.getParams()
    //2.获取点击的饮食记录类型
    this.type = params.type
    this.isFood = this.type.id !== RecordTypeEnum.WORKOUT
  }

  build() {
      Column() {
        //1.头部导航
        this.Header()
        //2.列表(组件)
        //将onPanelShow传递给itemList组件
        ItemList({showPanel: this.onPanelShow.bind(this),isFood: this.isFood})
        .layoutWeight(1)
        //3.底部弹出面板
        //Panel组件需要页面有一个固定高度才会渲染，给list组件设置权重为1,将高度达到最大
        Panel(this.showPanel){
          //顶部日期
          ItemPanelHeader()
          //记录条目
          //当item有值的情况下渲染选项卡片
          if(this.item){
            ItemCard({amount: this.amount,item: $item})
          }
          //数字键盘
          NumberKeyboard({amount: $amount, value: $value})
          //按钮
          Row({space: CommonConstants.SPACE_6}){
            Button('取消')
              .width(120)
              .backgroundColor($r('app.color.light_gray'))
              .type(ButtonType.Normal)
              .borderRadius(6)
              .onClick(() => this.showPanel = false)
            Button('提交')
              .width(120)
              .backgroundColor($r('app.color.primary_color'))
              .type(ButtonType.Normal)
              .borderRadius(6)
              .onClick(() => {
                // //持久化保存
                // RecordService.insert(this.type.id,this.item.id,this.amount)
                //   .then(() => {
                //     //插入成功关闭弹窗
                //     this.showPanel = false
                  //})45
              })
          }
          .margin({top: 10})
        }
        .mode(PanelMode.Full)
        .dragBar(false)
        .backgroundMask($r('app.color.light_gray'))
        .backgroundColor(Color.White)

      }
      .width('100%')
      .height('100%')
  }

  @Builder Header(){
    Row(){
      Image($r('app.media.ic_public_back'))
        .width(24)
        .onClick(() => router.back())
      Blank()
      Text(this.type.name).fontSize(18).fontWeight(CommonConstants.FONT_WEIGHT_600)
    }
    .width(CommonConstants.THOUSANDTH_940)
    .height(32)
  }
}