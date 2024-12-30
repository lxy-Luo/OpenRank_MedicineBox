/*
 * 闹钟信息详情页
 * */
import reminderAgentManager from '@ohos.reminderAgentManager'
import util from '@ohos.util'
import promptAction from '@ohos.promptAction'
import router from '@ohos.router'
import { ClockItem, ClockStore } from '../model/locker/clockItem'
import { AlarmManager } from '../model/locker/alarmManager'

@Entry
@Component
struct DetailPage {


  @State
  clockItem: ClockItem = {}

  // Date 不能直接使用 @State 装饰器
  @State
  state: { now: Date } = { now: new Date() }
  // @State
  // clockList: ClockItem[] = []

  clockStore = new ClockStore()

  aboutToAppear() {
    const params = router.getParams() as ClockItem
    if (params && params.id) {
      this.clockItem = { ...params }
      this.state.now.setHours(this.clockItem.hour)
      this.state.now.setMinutes(this.clockItem.minute)
    }
  }


  async submit() {

    // 1. 开启提醒
    const hour = this.state.now.getHours()
    const minute = this.state.now.getMinutes()
    const reminderId = await AlarmManager.addReminder(hour, minute)
    // 2. 记录数据
    if (this.clockItem.id) {
      this.clockItem.hour = hour
      this.clockItem.minute = minute
      this.clockItem.enabled = true
      // 不加 await 让它异步执行，因为可能存在失效的代理提醒，这样不会阻碍后续逻辑
      reminderAgentManager.cancelReminder(this.clockItem.reminderId)
      this.clockItem.reminderId = reminderId
      await this.clockStore.addClock(this.clockItem)
      promptAction.showToast({ message:'编辑闹钟成功' })
      router.back()
    } else {
      this.clockItem = {
        hour,
        minute,
        id: util.generateRandomUUID(),
        reminderId,
        enabled: true
      }
      await this.clockStore.addClock(this.clockItem)
      promptAction.showToast({ message:'添加闹钟成功' })
      router.back()
    }
    router.back()
  }

  async remove () {
    try {
      await this.clockStore.delClock(this.clockItem.id)
      promptAction.showToast({ message: '删除闹钟成功' })
      // 不加 await 让它异步执行，因为可能存在失效的代理提醒，这样不会阻碍跳转
      reminderAgentManager.cancelReminder(this.clockItem.reminderId)
      router.back()
    } catch (e) {
      console.error('CLOCK-REMOVE', e.message)
    }
  }

  @Builder
  CellBuilder (title: string, value: string) {
    Row(){
      Text(title)
        .layoutWeight(1)
      Text(value)
        .fontColor('#999')
      Image('/images/ic_public_arrow_right.svg')
        .width(18)
        .aspectRatio(1)
        .fillColor('#999')
    }
    .height(60)
    .padding({ left: 15, right: 15 })
    .backgroundColor('#fff')
  }

  build() {
    Navigation(){
      Column({ space: 15 }){
        //日期选择器
        TimePicker({
          selected: this.state.now
        })
          .borderRadius(16)
          .clip(true)

        Column({ space: 1 }){
          this.CellBuilder('闹铃名称', '闹铃')
          this.CellBuilder('重复', '不重复')
        }
        .borderRadius(16)
        .clip(true)

        if ( this.clockItem.id ) {
          Text('删除闹铃')
            .width(100)
            .height(40)
            .borderRadius(20)
            .backgroundColor('#c3c4c5')
            .textAlign(TextAlign.Center)
            .fontColor('#f00')
            .opacity(0.3)
            .onClick(() => {
              this.remove()
            })
        }
      }
      .padding(15)
    }
    .title((this.clockItem.id ? '编辑' : '添加') + '闹钟')   //页面标题
    .titleMode(NavigationTitleMode.Mini)    //页面显示模式
    .mode(NavigationMode.Stack)
    .backgroundColor('#f5f5f5')
    .menus([
      { value: '确定',  action: () => this.submit() }
    ])
  }
}