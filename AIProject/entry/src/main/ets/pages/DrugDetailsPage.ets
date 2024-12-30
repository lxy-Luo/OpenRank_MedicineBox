import router from '@ohos.router'
import { CommonConstants } from '../common/constants/CommonConstants'
import { MedicalCategoryEnum } from '../model/ItemCategoryModel'
import MedicalRecordVO from '../viewmodel/medical/MedicalRecordVO'
@Entry
@Component
struct DrugDetailsPage {
  //药品信息
   @State DrugDetail: MedicalRecordVO = new MedicalRecordVO(1,50001, '乳酸司帕沙星',$r('app.media.lxy_50001'), MedicalCategoryEnum.RespiratoryMedicines, '片','好好爱自己','12:12',5,'https://drd-web.oss-cn-beijing.aliyuncs.com/633a6f7a-1669-41c1-9e2b-63b758962ed0.jpg')
   DrugImage1: ResourceStr = $r('app.media.lxy_50001')
   DrugImage2: ResourceStr = $r('app.media.lxy_50001')

  aboutToAppear(){
    //1.获取跳转时的参数
    let params: any = router.getParams()
    //2.获取跳转的药品信息
    this.DrugDetail = params.DrugDetail
    this.DrugImage1 = params.DrugDetailImage1
    this.DrugImage2 = params.DrugDetailImage2
  }
  build() {
      Column(){
        this.Header()
        Row(){
          Column(){
            Text(this.DrugDetail.time).fontSize(20)
            Text(this.DrugDetail.name).fontSize(23)
          }
          .justifyContent(FlexAlign.SpaceAround)
          .alignItems(HorizontalAlign.Start)
          .margin({left:10})
          Blank()
          Row(){
            Text(this.DrugDetail.All_number+' '+this.DrugDetail.unit).fontSize(25).margin({right:10})
          }
        }
        .width('90%')
        .height('10%')
        .borderRadius(20)
        .margin({left: 15, right :15,top:20})
        .backgroundColor($r('app.color.lv1'))

        Row(){
          this.imageCard(this.DrugImage1,'官方图片')
          this.imageCard(this.DrugImage2,'设备图片')
        }
        .margin({top:20,bottom:15})

        Column(){
          Text("药品介绍：").fontColor(Color.White).fontSize(25)
          Text(this.DrugDetail.introduction)
            .fontColor(Color.White).fontSize(15)
        }.width("95%")
        .justifyContent(FlexAlign.Start)



      }
      .width('100%')
      .height('100%')
      .backgroundColor($r('app.color.hei1'))
  }

  @Builder Header(){
    Row(){
      Image($r('app.media.ic_public_back'))
        .backgroundColor($r('app.color.lv3'))
        .borderRadius(50)
        .width(24)
        .onClick(() => router.back())
      Blank()
      Text('药品详情页').fontSize(18).fontWeight(CommonConstants.FONT_WEIGHT_600).fontColor($r('app.color.lv3'))
    }
    .width(CommonConstants.THOUSANDTH_940)
    .height(32)
  }

  @Builder imageCard(item: ResourceStr,name: string){
    Column(){
      Text(name).fontColor(Color.White).fontSize(20)
      Image(item).height(180).width(180)
    }
  }
}