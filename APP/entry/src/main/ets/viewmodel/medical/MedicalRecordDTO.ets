/*
 *@Author: Dairundong
 *@Date: 2024/4/1 22:27
 * 药品列表页面
 * */
@Observed
export default class MedicalRecordDTO {
  /**
   * id
   */
  id: number

  /**
   * 药品id
   */
  DrugId: number

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

  constructor(id:number,DrugId: number,time:string,ALL_number: number,image:ResourceStr) {
    this.id = id
    this.DrugId = DrugId
    this.time = time
    this.All_number = ALL_number
    this.image = image
  }

}