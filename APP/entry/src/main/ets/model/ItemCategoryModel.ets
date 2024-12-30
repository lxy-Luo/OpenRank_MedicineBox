import ItemCategory from '../viewmodel/ItemCategory'

/**
 * 食物类型的枚举
 */
enum FoodCategoryEnum{
  /**
   * 主食
   */
  STAPLE,
  /**
   * 蔬果
   */
  FRUIT,
  /**
   * 肉蛋奶
   */
  MEAT,
  /**
   * 坚果
   */
  NUT,
  /**
   * 其它
   */
  OTHER,
}
/**
 * 食物类型数组
 */
let FoodCategories = [
  new ItemCategory(0, $r('app.string.staple')),
  new ItemCategory(1, $r('app.string.fruit')),
  new ItemCategory(2, $r('app.string.meat')),
  new ItemCategory(3, $r('app.string.nut')),
  new ItemCategory(4, $r('app.string.other_type')),
]



/**
 * 运动类型枚举
 */
enum WorkoutCategoryEnum {
  /**
   * 走路
   */
  WALKING,
  /**
   * 跑步
   */
  RUNNING,
  /**
   * 骑行
   */
  RIDING,
  /**
   * 跳操
   */
  AEROBICS,
  /**
   * 游泳
   */
  SWIMMING,
  /**
   * 打球
   */
  BALLGAME,
  /**
   * 力量训练
   */
  STRENGTH
}
/**
 * 运动类型数组
 */
let WorkoutCategories = [
  new ItemCategory(0, $r('app.string.walking_type')),
  new ItemCategory(1, $r('app.string.running')),
  new ItemCategory(2, $r('app.string.riding')),
  new ItemCategory(3, $r('app.string.aerobics')),
  new ItemCategory(4, $r('app.string.swimming')),
  new ItemCategory(5, $r('app.string.ballgame')),
  new ItemCategory(6, $r('app.string.strength')),
]



/**
 * 药品类型枚举
 */
enum MedicalCategoryEnum{
  /**
   * 呼吸道药
   */
  RespiratoryMedicines,
  /**
   *抗菌消炎药
   */
  AntiInflammatories,
  /**
   * 儿科用药
   */
  PediatricMedications,
  /**
   * 眼科用药
   */
  OphthalmicMedications,
  /**
   * 肠胃用药
   */
  GastrointestinalMedications,
  /**
   * 感冒用药
   */
  ColdMedications,
  /**
   * 皮肤用药
   */
  DermalMedications,
  /**
   * 外伤用药
   */
  TraumaMedications,
  /**
   * 妇科用药
   */
  GynecologicalMedications,
  /**
   * 血压药
   */
  BloodPressureMedications,
}
/**
 * 药品分类数组
 */
let MedicalCategories= [
  new ItemCategory(0, $r('app.string.RespiratoryMedicines')),
  new ItemCategory(1, $r('app.string.AntiInflammatories')),
  new ItemCategory(2, $r('app.string.PediatricMedications')),
  new ItemCategory(3, $r('app.string.OphthalmicMedications')),
  new ItemCategory(4, $r('app.string.GastrointestinalMedications')),
  new ItemCategory(5, $r('app.string.ColdMedications')),
  new ItemCategory(6, $r('app.string.DermalMedications')),
  new ItemCategory(7, $r('app.string.TraumaMedications')),
  new ItemCategory(8, $r('app.string.GynecologicalMedications')),
  new ItemCategory(9, $r('app.string.BloodPressureMedications')),

]

export {FoodCategories , WorkoutCategories , FoodCategoryEnum, WorkoutCategoryEnum,MedicalCategoryEnum,MedicalCategories}