import reminderAgentManager from '@ohos.reminderAgentManager'
import promptAction from '@ohos.promptAction'
import { ClockItem, ClockStore } from '../../model/locker/clockItem'
@Component
export struct ClockItemComp {
  @State
  item: ClockItem = {}

  clockStore = new ClockStore()

  async toggle(isOn: boolean) {
    reminderAgentManager.cancelReminder(this.item.reminderId)

    this.item.enabled = isOn
    if (this.item.enabled) {
      // @ts-ignore
      const reminderId = await AlarmManager.createAlarmTask(this.item.hour, this.item.minute)
      this.item.reminderId = reminderId
    }
    this.clockStore.addClock(this.item)
    promptAction.showToast({ message: isOn ? '已开启' : '已关闭' })
  }

  padZero(val: number = 0) {
    return val.toString().padStart(2, '0')
  }
  build() {
    Row(){
      Column({ space: 5 }) {
        Row({ space: 5 }) {
          Text(this.item.hour > 12 ? '下午' : '上午')
            .fontColor('#666')
          Text(`${this.padZero(this.item.hour > 12 ? this.item.hour - 12 : this.item.hour)}:${this.padZero(this.item.minute)}`)
            .fontWeight(600)
            .fontSize(18)
        }
        Row({ space: 15 }){
          Text('闹钟')
            .fontColor('#999')
            .fontSize(14)
          Text('不重复')
            .fontColor('#999')
            .fontSize(14)
        }
      }
      .alignItems(HorizontalAlign.Start)

      // 开关
      Toggle({ type: ToggleType.Switch, isOn: this.item.enabled })
        .onChange(isOn => this.toggle(isOn))
    }
    .height(64)
    .padding({ left: 20, right: 15 })
    .width('100%')
    .backgroundColor('#fff')
    .borderRadius(30)
    .justifyContent(FlexAlign.SpaceBetween)
  }
}