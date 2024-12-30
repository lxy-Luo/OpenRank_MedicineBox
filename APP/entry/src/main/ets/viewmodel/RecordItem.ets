/*
 *饮食记录项的数据模型 */
export default class RecordItem{
  //id
  id: number
  //名称
  name: ResourceStr
  //图片
  image: ResourceStr
  //分类id
  categoryId: number
  //包含的卡路里
  calorie: number
  //单位
  unit: ResourceStr
  //碳水含量，单位克
  carbon: number
  //蛋白质含量，单位克
  protein: number
  //脂肪含量，单位克
  fat: number

  constructor(id: number, name: ResourceStr, image: ResourceStr, categoryId: number, unit: ResourceStr, calorie: number,
              carbon: number = 0, protein: number = 0, fat: number = 0) {
    this.id = id;
    this.name = name;
    this.image = image;
    this.categoryId =categoryId;
    this.unit = unit;
    this.calorie = calorie;
    this.protein = protein;
    this.fat = fat;
    this.carbon = carbon;
    this.protein = protein;
    this.fat = fat;
  }
}