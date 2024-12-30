/*
 *闹钟首页
 * */
import { ClockItemComp } from './ClockItemComp'
import router from '@ohos.router'
import { ClockItem, ClockStore } from '../../model/locker/clockItem'


@Component
export default struct LockerIndex {
  @State
  clockList: ClockItem[] = []

  clockStore = new ClockStore()

  aboutToAppear() {
    this.initData()
  }
  onPageShow() {
    this.initData()
  }
  async initData() {
    this.clockList = await this.clockStore.getAllClock()
  }

  build() {
    Stack({ alignContent: Alignment.Bottom }){
      Column({ space: 15 }) {
        Text('药品闹钟')
          .fontSize('24')
          .width('100%')

        Image($r('app.media.zhong')).width(300)
        ForEach(this.clockList, (item: ClockItem) => {
          ClockItemComp({ item })
            .onClick(() => {
              router.pushUrl({
                url: 'pages/DetailPage',
                params: item
              })
            })
        })
      }
      .padding(15)
      .height('100%')
      .width('100%')
      .backgroundColor('#f5f5f5')
      // 添加
      Text('+')
        .width(50)
        .aspectRatio(1)
        .backgroundColor('#06f')
        .fontSize(40)
        .fontWeight(100)
        .fontColor('#fff')
        .borderRadius(25)
        .textAlign(TextAlign.Center)
        .margin({ bottom: 50 })
        .onClick(() => {
          router.pushUrl({
            url: 'pages/DetailPage',
          })
        })
    }
  }
}