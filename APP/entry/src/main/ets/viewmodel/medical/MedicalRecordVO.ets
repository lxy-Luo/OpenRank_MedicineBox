import MedicalRecordItem from './MedicalRecordItem'
/**
 * 当天饮食记录的页面数据模型
 */
@Observed
export default class MedicalRecordVO {
  /**
   * id
   */
  id: number

  /**
   * 药品id
   */
  DrugId: number
  /**
   * 名称
   */
  name: ResourceStr
  /**
   * 图片
   */
  DrugImage: ResourceStr
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

  /**
   *吃药的时间
   */
  time: string

  /**
   * 吃药的数量
   */
  All_number: number

  /**
   * 硬件图片
   */
  image: ResourceStr

  constructor(id:number, DrugId: number,name: ResourceStr,DrugImage: ResourceStr,categoryId: number,unit: ResourceStr,introduction: string,time:string,ALL_number: number,image:ResourceStr) {
    this.id = id
    this.DrugId = DrugId
    this.name = name
    this.DrugImage = DrugImage
    this.categoryId = categoryId
    this.unit = unit
    this.introduction = introduction
    this.time = time
    this.All_number = ALL_number
    this.image = image


  }

}