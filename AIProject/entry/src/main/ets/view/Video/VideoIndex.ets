/*
 * 创建:drd
 * */
import { HorizontalVideoItem } from '../../model/videoModel/HorizontalVideoItem'
import SwiperVideo from './SwiperVideo'
import router from '@ohos.router';
import mainViewModel from '../../model/videoModel/MainViewModel';  //这里路径有bug,大小写未识别
import ItemData from '../../viewmodel/ItemData';
import SearchHeader from '../record/SearchHeader';

 const spingData: HorizontalVideoItem[] =[
   new HorizontalVideoItem(2,'https://drd-web.oss-cn-beijing.aliyuncs.com/13.png','https://drd-web.oss-cn-beijing.aliyuncs.com/%E8%B6%85%E7%AE%80%E5%8D%95%EF%BC%81%E6%98%8E%E6%98%9F%E9%83%BD%E5%9C%A8%E7%94%A8%E7%9A%84mewing%E5%81%A5%E5%BA%B7%E5%8F%98%E7%BE%8E%E8%AE%AD%E7%BB%83.mp4','超简单！明星都在用的mewing健康变美训练'), new HorizontalVideoItem(3,'https://drd-web.oss-cn-beijing.aliyuncs.com/12.png','https://drd-web.oss-cn-beijing.aliyuncs.com/%E5%85%BB%E7%94%9F%E5%B1%8A%E7%9A%84C%E4%BD%8D%E6%9E%B8%E6%9D%9E%E7%9C%9F%E7%9A%84%E5%BE%88%E8%A1%A5%E5%90%97%EF%BC%9F.mp4','养生届的C位枸杞真的很补吗？'),
  new HorizontalVideoItem(1,'https://drd-web.oss-cn-beijing.aliyuncs.com/11.png','https://drd-web.oss-cn-beijing.aliyuncs.com/%E8%AE%A9%E5%90%83%E8%8D%AF%E5%8F%98%E5%BE%97%E6%9B%B4%E7%AE%80%E5%8D%95%EF%BC%81%E6%9B%B4%E5%AE%B9%E6%98%93%E5%90%9E%E4%B8%8B%E8%83%B6%E5%9B%8A%E5%92%8C%E8%8D%AF%E7%89%87%E7%9A%84%E4%B8%A4%E4%B8%AA%E5%B0%8F%E6%8A%80%E5%B7%A7%E3%80%90Mandell%E5%8D%9A%E5%A3%AB%E3%80%91.mp4','让吃药变得更简单'),
  new HorizontalVideoItem(4,'https://drd-web.oss-cn-beijing.aliyuncs.com/14.png','https://drd-web.oss-cn-beijing.aliyuncs.com/%E3%80%90%E8%B6%85%E7%87%83%E5%8A%B1%E5%BF%97%E3%80%91%E6%B2%B9%E7%AE%A13000%E4%B8%87%E6%92%AD%E6%94%BE%E2%80%94%E2%80%94%E3%80%8A%E6%B2%A1%E6%9C%89%E5%80%9F%E5%8F%A3%E3%80%8BNO%20EXCUSES%EF%BC%81%E6%AF%8F%E5%A4%A9%E4%B8%80%E9%81%8D%EF%BC%8C%E9%98%B2%E6%AD%A2%E9%A2%93%E5%BA%9F%EF%BC%81.mp4','养生届的C位枸杞真的很补吗？'),
  new HorizontalVideoItem(5,'https://drd-web.oss-cn-beijing.aliyuncs.com/15.png','https://drd-web.oss-cn-beijing.aliyuncs.com/%E9%9B%86%E5%90%88%EF%BC%81get%E6%AD%A3%E7%A1%AE%E7%9C%BC%E8%8D%AF%E6%B0%B4%E6%BB%B4%E6%B3%95%E2%86%93.mp4','集合！get正确眼药水滴法'),
  new HorizontalVideoItem(6,'https://drd-web.oss-cn-beijing.aliyuncs.com/16.png','https://drd-web.oss-cn-beijing.aliyuncs.com/%E4%B8%89%E7%94%B2%E5%8C%BB%E7%94%9F%EF%BD%9C%E6%97%85%E8%A1%8C%E5%BF%85%E5%A4%87%EF%BC%81%E5%BA%94%E6%80%A5%E8%8D%AF%E5%93%81%E6%B8%85%E5%8D%95%EF%BC%81.mp4','三甲医生｜旅行必备！应急药品清单！'),
  new HorizontalVideoItem(7,'https://drd-web.oss-cn-beijing.aliyuncs.com/17.png','https://drd-web.oss-cn-beijing.aliyuncs.com/%E8%87%AA%E5%BE%8B%E7%9A%84%E6%9C%AC%E8%B4%A8%E6%98%AF%E4%BB%80%E4%B9%88%EF%BC%9F%E6%88%91%E4%BB%AC%E6%99%AE%E9%80%9A%E4%BA%BA%E5%A6%82%E4%BD%95%E6%89%8D%E8%83%BD%E5%81%9A%E5%88%B0%E8%87%AA%E5%BE%8B%EF%BC%9F.mp4','自律的本质是什么？我们普通人如何才能做到自律？'),
];

@Component
export struct videoIndex {
  build() {
      Column({space : 14}){
       SearchHeader().backgroundColor($r('app.color.lv3')).borderRadius(20).margin(5)
       // SwiperVideo()

        //为你推荐
        Column(){
          Text("为您推荐").fontColor($r('app.color.qian1')).fontSize('20')
          List(){
            ForEach(spingData,(item: HorizontalVideoItem) => {
              ListItem() {
                this.videoList(item.image,item.sping,item.name,item.id)
              }
            })
          }.listDirection(Axis.Horizontal)
        }.width("90%").height('30%').borderRadius(15).backgroundColor($r('app.color.hei1')).alignItems(HorizontalAlign.Start)
        //大图标
        Grid() {
          ForEach(mainViewModel.getSecondGridData(), (secondItem: ItemData) => {
            GridItem() {
              Column() {
                Text(secondItem.title)
                  .fontSize(16)
                  .fontWeight(FontWeight.Medium)
                Text(secondItem.others)
                  .margin({ top: 4 })
                  .fontSize(12)
                  .fontColor("#99182431")
              }
              .alignItems(HorizontalAlign.Start)
            }
            .padding({ top: 8, left: 8})
            .borderRadius(12)
            .align(Alignment.TopStart)
            .backgroundImage(secondItem.img)
            .backgroundImageSize(ImageSize.Cover)
            .width("100%")
            .height("100%")
          }, (secondItem: ItemData) => JSON.stringify(secondItem))
        }
        .width("100%")
        .height(260)
        .columnsTemplate('1fr 1fr')
        .rowsTemplate('1fr 1fr')
        .columnsGap(8)
        .rowsGap(12)
        .margin({ bottom: 5 })

      }.justifyContent(FlexAlign.Start).width('100%')
      .height('100%')
  }
  @Builder videoList(image: ResourceStr, sping: ResourceStr,name: string,id:number){
    Column(){
      Image(image).height(180).width(300).borderRadius(10).backgroundColor($r('app.color.hei2'))
        .onClick(() =>{
            router.pushUrl(
              {
                url: 'pages/VideoPlayer',
                params: {
                  source: sping,
                  previewUris: image,
                  name: name,
                  id:id
                },
              },
              router.RouterMode.Single,

              err => {
                if(err){
                  console.log('路由失败')
                }
              }
            )
          })
      Text(name).fontSize(15).fontColor(Color.White).backgroundColor($r('app.color.hei1'))
    }.backgroundColor($r('app.color.hei1')).margin({left:5})
  }
}