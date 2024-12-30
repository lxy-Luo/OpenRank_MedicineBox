/*
 *@Author: Dairundong
 *@Date: 2024/3/28 14:30
 * 药箱模块轮播图展示的当日使用记录模块
 * */
import { CommonConstants } from '../../common/constants/CommonConstants'
import ItemModel from '../../model/ItemModel'
import MedicalRecordItem from '../../viewmodel/medical/MedicalRecordItem'
import MedicalRecordDTO from '../../viewmodel/medical/MedicalRecordDTO'
import MedicalItem from './MedicalItem'
import MedicalRecordVO from '../../viewmodel/medical/MedicalRecordVO'
import router from '@ohos.router'
@Component
export default struct EatenList {
  @Consume todayMedicalList: MedicalRecordVO[]
  @State Total: number =  1  //this.todayMedicalList.length
  build() {
    Column(){
      if(this.Total == 0){
        Row(){
          Text('暂无数据').fontSize(30)
        }
      }else{
        List({space: CommonConstants.SPACE_2}){
          ForEach(this.todayMedicalList, (item: MedicalRecordVO) =>{
            ListItem(){
              //现在有bug
              //MedicalItem({item:item})
              Row({space: CommonConstants.SPACE_6}){
                //本地图片
                Image(item.image).width(50)
                //硬件图片
                Image(item.DrugImage).width(50)
                Column(){
                  Text(item.name+'').fontSize(15).fontColor($r('app.color.qian1'))
                  Text(item.time).fontSize(10).fontColor($r('app.color.qian2'))
                }
                .justifyContent(FlexAlign.SpaceAround)
                .alignItems(HorizontalAlign.Start)
                //.backgroundColor(Color.Black)
                .layoutWeight(1)
                Column(){
                  Text(item.All_number+"("+item.unit+")").fontColor($r('app.color.lv3')).fontSize(20)
                }
              }
              .margin({left:8})
              .padding({left: 10,right:10,top:3})
              .width('95%')
              .height('15%')
              .backgroundColor($r('app.color.hei2'))
              .borderRadius(10)
              .onClick(() =>{
                  router.pushUrl(
                    {
                      url: 'pages/DrugDetailsPage',
                      params: {
                        DrugDetail: item,
                        DrugDetailImage1: item.DrugImage,
                        DrugDetailImage2:item.image
                      }
                    },
                    router.RouterMode.Single,
                    err => {
                      if(err){
                        console.log('路由失败')
                      }
                    }
                  )
              })
            }
          })
        }
        .margin({top: 10})
      }

    }

  }
}

