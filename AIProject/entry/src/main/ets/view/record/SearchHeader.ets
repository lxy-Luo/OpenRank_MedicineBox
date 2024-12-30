import router from '@ohos.router'
import { CommonConstants } from '../../common/constants/CommonConstants'
import PreferenceUtil from '../../common/utils/PreferenceUtil'
import Message from '../../viewmodel/message/Message'
import MessageVO from  '../../viewmodel/message/MessageVO'
/*
 * 首页头部搜索框组件·*/

@Component
export default struct SearchHeader {


  @Consume unreadCount:number

  readMessage(){
    router.pushUrl({ url:'pages/MessageOverviewPage' })
  }

  build() {
    Row({space: CommonConstants.SPACE_6}){
      Search({placeholder: '搜索相关词'})
        .textFont({size:18})
        .placeholderColor($r('app.color.hei3'))
        .layoutWeight(1)
      Badge({count: this.unreadCount, position: BadgePosition.RightTop,style: {fontSize: 12}}){
        Image($r('app.media.ic_public_email'))
          .width(24)
          //.colorBlend($r('app.color.lv2'))
          .margin({right: 5})
      }
      .onClick(()=>{
        this.readMessage()
      })
    }
    .width(CommonConstants.THOUSANDTH_940)
  }
}