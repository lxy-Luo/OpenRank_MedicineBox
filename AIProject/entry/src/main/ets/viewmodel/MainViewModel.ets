import ItemData from './ItemData';

/**
 *@Author: Dairundong
 *@Date: 2024/4/2 14:27
 * Binds data to components and provides interfaces. 数据模块
 */
export class MainViewModel {

  /**
   *中间grid
   * @return {Array<PageResource>} firstGridData.
   */
  getFirstGridData(): Array<ItemData> {
    let firstGridData: ItemData[] = [
      new ItemData($r('app.string.my_love'), $r('app.media.drdlove')),
      new ItemData($r('app.string.history_record'), $r('app.media.drdrecord')),
      new ItemData($r('app.string.message'), $r('app.media.drdmessage')),
      new ItemData($r('app.string.shopping_cart'), $r('app.media.drdshopping')),
      new ItemData($r('app.string.my_goal'), $r('app.media.drdtarget')),
      new ItemData($r('app.string.group'), $r('app.media.drdcircle')),
      new ItemData($r('app.string.favorites'), $r('app.media.drdfavorite')),
      new ItemData($r('app.string.recycle_bin'), $r('app.media.drdrecycle'))
    ];
    return firstGridData;
  }
}
export default new MainViewModel();