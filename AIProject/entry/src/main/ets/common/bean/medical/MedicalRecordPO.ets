/*
 *@Author: 罗晓阳 , 戴润东
 *@Date: 2024/3/27 21:07
 *@upDate: 2024/4/1 16:07
 * @upDate 2024/4/5 19:45
 * 今日使用药品，记录的数据库对应类型
 * */

import { MedicalCategoryEnum } from '../../../model/ItemCategoryModel'
export default class MedicalRecordPO{
  /**
   * 记录id
   */
  id?: number

  /**
   * 记录中的药品信息
   */
  itemId: number

  /**
   *每日计划中所要吃的药品数量
   */
  amount: number

}