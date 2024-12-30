/**
 * @Author 罗骁阳
 * @Date 2024/4/7
 */
import MedicalRecordVO from './MedicalRecordVO'

@Observed
export default class MedicalListVO {
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
  //time: string

  /**
   * 吃药的数量
   */
  All_number: number

  /**
   * 硬件图片
   */
  //image: ResourceStr

  /**
   *总共要吃的数量
   */
  total_number: number

  constructor(id:number, DrugId: number,name: ResourceStr,DrugImage: ResourceStr,categoryId: number,unit: ResourceStr,introduction: string,ALL_number: number,total_number:number) {
    this.id = id
    this.DrugId = DrugId
    this.name = name
    this.DrugImage = DrugImage
    this.categoryId = categoryId
    this.unit = unit
    this.introduction = introduction
   // this.time = time
    this.All_number = ALL_number
    //this.image = image
    this.total_number=total_number
  }
}