/*
 *@Author: Dairundong
 *@Date: 2024/3/28 14:30
 * @upDate 2024/04/05 19:47
 * 药箱模块轮播图下的计划表组件
 * */
import router from '@ohos.router'
import axios from '@ohos/axios'
import prompt from '@ohos.promptAction'
import { CommonConstants } from '../../common/constants/CommonConstants'
import { MedicalCategoryEnum } from '../../model/ItemCategoryModel'
import medicineBoxService from '../../service/MedicineBoxService'
import MedicalListVO from '../../viewmodel/medical/MedicalListVO'
import MedicalRecordDTO from '../../viewmodel/medical/MedicalRecordDTO'
//import MedicalRecordService from '../../service/MedicalRecordService'
import MedicalRecordVO from '../../viewmodel/medical/MedicalRecordVO'
import PreferenceUtil from '../../common/utils/PreferenceUtil'
import RecordService from '../../service/RecordService'
@Component
export default struct PlanList {
  //今日计划数据
  @Consume record: MedicalListVO[]
  @Builder
  itemEnd(id: number){
    Row(){
      Image($r('app.media.ic_public_delete_filled'))
        .padding(8)
        .fillColor(Color.Red)
        .width(40)
        .onClick(()=>{
          //删除操作
          RecordService.deleteById(id)
          router.pushUrl({
            url: 'pages/RefreshTransit'
          })
        })
    }
  }

  build() {
    Column({space:10}){
      Row({space:10}){
        Text('今日计划').fontSize(23).fontWeight(FontWeight.Bold).fontColor(Color.White).padding({left:140})
        Blank()
        Image($r('app.media.endAdd')).width(30)
          .onClick(()=>{
            router.pushUrl({
              url: 'pages/MedicalItemIndex'
            })
          })

      }
      .padding({top:15})
      .width('100%')
      .justifyContent(FlexAlign.Center)
      List({space:5}){
        ForEach(this.record, (item: MedicalListVO)=>{
            ListItem(){
              Row({space: CommonConstants.SPACE_8}){
                //药品图片
                Image(item.DrugImage).width(50)
                Text(item.name).fontColor(Color.White).fontSize(20)
                Blank()
                Text(item.All_number+'/'+item.total_number+' ('+item.unit+')' ).fontColor(Color.White)
              }
              .margin({left:8})
              .padding({left: 10,right:10,top:3})
              .width('95%')
              .height('15%')
              .backgroundColor($r('app.color.hei2'))
              .borderRadius(10)
            }
             .swipeAction({end: this.itemEnd(item.id)})
          }
        )
      }
    }
  }
}