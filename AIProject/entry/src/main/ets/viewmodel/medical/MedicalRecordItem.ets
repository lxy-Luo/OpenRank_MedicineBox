/**
 * 饮食记录中的记录项，可以是食物或运动
 */
@Observed
export default class MedicalRecordItem{
  /**
   * id
   */
  id: number
  /**
   * 名称
   */
  name: ResourceStr
  /**
   * 图片
   */
  image: ResourceStr
  /**
   * 分类id
   */
  categoryId: number
  /**
   * 单位
   */
  unit: ResourceStr
  /*
   * 药品介绍
   * */
  introduction: string

  constructor(id: number, name: ResourceStr, image: ResourceStr,
              categoryId: number, unit: ResourceStr ,introduction: string) {
    this.id = id
    this.name = name
    this.image = image
    this.categoryId = categoryId
    this.unit = unit
    this.introduction = introduction
  }
}