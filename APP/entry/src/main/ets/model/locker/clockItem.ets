import preferences from '@ohos.data.preferences'

export class ClockItem {
  hour?: number
  minute?: number
  id?: string
  reminderId?: number
  enabled?: boolean
}

export class ClockStore {

  getStore() {
    return preferences.getPreferences(getContext(this), 'clockStore')
  }

  async addClock(clockItem: ClockItem) {
    const store = await this.getStore()
    await store.put(clockItem.id, JSON.stringify(clockItem))
    await store.flush()
  }

  async delClock(id: string) {
    const store = await this.getStore()
    await store.delete(id)
    await store.flush()
  }

  async getAllClock() {
    const store = await this.getStore()
    const data = await store.getAll()
    const list = Object.keys(data).map<ClockItem>(key => {
      return JSON.parse(data[key])
    })
    return list
  }
}