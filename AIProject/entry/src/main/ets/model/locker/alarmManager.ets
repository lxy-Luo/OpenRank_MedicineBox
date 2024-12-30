import reminderAgentManager from '@ohos.reminderAgentManager'
export class AlarmManager {

  // 添加代理提醒
  static async addReminder (hour: number, minute: number) {
    // 1. 添加后台代理提醒-闹钟
    const reminderRequest: reminderAgentManager.ReminderRequestAlarm = {
      reminderType: reminderAgentManager.ReminderType.REMINDER_TYPE_ALARM,
      hour,
      minute,
      ringDuration: 60,
      title: '闹铃',
      // 跳转
      wantAgent: {
        pkgName: 'com.itcast.hmday05',
        abilityName: 'EntryAbility'
      },
      // 延时和关闭
      actionButton: [
        { title: '关闭', type: reminderAgentManager.ActionButtonType.ACTION_BUTTON_TYPE_CLOSE },
        { title: '延时提醒', type: reminderAgentManager.ActionButtonType.ACTION_BUTTON_TYPE_SNOOZE }
      ],
      snoozeTimes: 2,
      timeInterval: 10 * 60
    }

    const reminderId = await reminderAgentManager.publishReminder(reminderRequest)
    return reminderId
  }
}