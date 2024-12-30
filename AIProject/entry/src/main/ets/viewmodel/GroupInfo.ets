import ItemCategory from './ItemCategory'
import RecordItem from './RecordItem'

/*
 * 用于食物项，运动项循环渲染tabBar的结构类*/

//利用泛型将groupInfo变为通用的类型（主页面使用，食物运动列表页面使用）
export default class GroupInfo<TYPE,ELEMENT>{
  type: TYPE //记录项类型
  items: ELEMENT[]  //详细的类型数据信息
  /*
   * 组内记录的总热量*/
  calorie: number = 0
  constructor(type:  TYPE,items: ELEMENT[]) {
    this.type  = type
    this.items =items
  }
}