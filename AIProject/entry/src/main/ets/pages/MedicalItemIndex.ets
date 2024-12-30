/*
 *@Author: Dairundong
 *@Date: 2024/4/1 17:27
 * @Update 2024/4/4 罗骁阳
 * 药品列表页面
 * */

import router from '@ohos.router'
import ItemCard from '../view/item/ItemCard'
import ItemPanelHeader from '../view/item/ItemPaneHeader'
import NumberKeyboard from '../view/item/NumberKeyboard'
import { CommonConstants } from '../common/constants/CommonConstants'
import MedicalItemList from '../view/item/MedicalItemList'
import MedicalRecordItem from '../viewmodel/medical/MedicalRecordItem'
import MedicalRecordService from '../service/MedicalRecordService'
import MedicalItemCard from '../view/item/MedicalItemCard'
import RecordService from '../service/RecordService'
import MedicalRecordVO from '../viewmodel/medical/MedicalRecordVO'
import Logger from '../common/utils/Logger'
import MedicalListVO from '../viewmodel/medical/MedicalListVO'


@Entry
@Component
struct MeidcalItemIndex {
  @State amount: number = 1  //物品数量
  @State value: string = '' //拼接从键盘获取的数值
  @State showPanel: boolean = false
  @State item: MedicalRecordItem = null  //底部弹窗显示的记录项数据
  @State  record: MedicalListVO[]=[]

  //点击list列表相关药品的加号调用的方法
  onPanelShow(item: MedicalRecordItem){
    //初始化相关数值
    this.amount = 1
    this.value = ''
    this.item = item  //给当前记录项赋值
    this.showPanel = true  //添加编辑面板显示值改为true
  }
  //页面显示时生命周期钩子
  onPageShow(){
    //1.获取跳转时的参数
    let params: any = router.getParams()
  }

  build() {
    Column() {
      //1.头部导航
      this.Header()
      //2.列表(组件)
      //将onPanelShow传递给itemList组件
      MedicalItemList({showPanel: this.onPanelShow.bind(this)})
        .layoutWeight(1)
      //3.底部弹出面板
      //Panel组件需要页面有一个固定高度才会渲染，给list组件设置权重为1,将高度达到最大
      Panel(this.showPanel){
        //顶部日期
        ItemPanelHeader()
        //记录条目
        //当item有值的情况下渲染选项卡片
        if(this.item){
          MedicalItemCard({amount: this.amount,item: $item}).height('40%')
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
            .onClick(async () => {
              //持久化保存
              RecordService.insert(this.item.id,this.amount)
                  //插入成功关闭弹窗
              let test= await RecordService.queryMedicalRecord()
              // Logger.debug(test.toString())
              this.record=test
              this.showPanel = false
              router.back()
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
      Text('药品列表').fontSize(18).fontWeight(CommonConstants.FONT_WEIGHT_600)
    }
    .width(CommonConstants.THOUSANDTH_940)
    .height(32)
  }
}