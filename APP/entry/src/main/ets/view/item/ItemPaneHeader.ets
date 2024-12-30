/*
 *@Author: Dairundong
 *@Date: 2024/4/1 17:27
 * 键盘弹窗顶部时间组件
 * */

import DateUtil from '../../common/utils/DateUtil'

@Component
export default struct ItemPanelHeader {
  @StorageProp('selectedDate') selectedDate: number = DateUtil.beginTimeOfDay(new Date())
  build() {
    Row(){
      Text(DateUtil.formatDate(this.selectedDate)+'')
        .fontSize(18)
      Image($r('app.media.ic_public_spinner'))
        .width(20)
        .fillColor(Color.Black)
    }
  }
}