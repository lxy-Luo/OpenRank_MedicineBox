import RecordItem from '../viewmodel/RecordItem'
import ItemCategory from '../viewmodel/ItemCategory'
import { FoodCategories, FoodCategoryEnum, MedicalCategories, MedicalCategoryEnum, WorkoutCategories, WorkoutCategoryEnum } from './ItemCategoryModel'
import GroupInfo from '../viewmodel/GroupInfo'
import MedicalRecordItem from '../viewmodel/medical/MedicalRecordItem'
import ArrayList from '@ohos.util.ArrayList'
//import GroupInfo from '../view model/GroupInfo'


/*
 * 食物，运动数据，与 其数据相关获取接口*/
const foods: RecordItem[] = [
  new RecordItem(0, '米饭',$r('app.media.rice'),FoodCategoryEnum.STAPLE, '碗',  209, 46.6, 4.7, 0.5),
  new RecordItem(1, '馒头',$r('app.media.steamed_bun'),FoodCategoryEnum.STAPLE, '个',  114, 24.0, 3.6, 0.6),
  new RecordItem(2, '面包',$r('app.media.bun'),FoodCategoryEnum.STAPLE, '个',  188, 35.2, 5.0, 3.1),
  new RecordItem(3, '全麦吐司',$r('app.media.toast'),FoodCategoryEnum.STAPLE, '片',  91, 15.5, 4.4, 1.3),
  new RecordItem(4, '紫薯',$r('app.media.purple_potato'),FoodCategoryEnum.STAPLE, '个',  163, 42.0, 1.6, 0.4),
  new RecordItem(5, '煮玉米',$r('app.media.corn'),FoodCategoryEnum.STAPLE, '根',  111, 22.6, 4.0, 1.2),
  new RecordItem(6, '黄瓜',$r('app.media.cucumber'),FoodCategoryEnum.FRUIT, '根',  29, 5.3, 1.5, 0.4),
  new RecordItem(7, '蓝莓',$r('app.media.blueberry'),FoodCategoryEnum.FRUIT, '盒',  71, 18.1, 0.9, 0.4),
  new RecordItem(8, '草莓',$r('app.media.strawberry'),FoodCategoryEnum.FRUIT, '颗',  14, 3.1, 0.4, 0.1),
  new RecordItem(9, '火龙果',$r('app.media.pitaya'),FoodCategoryEnum.FRUIT, '个',  100, 24.6, 2.2, 0.5),
  new RecordItem(10, '奇异果',$r('app.media.kiwi'),FoodCategoryEnum.FRUIT, '个',  25, 8.4, 0.5, 0.3),
  new RecordItem(11, '煮鸡蛋',$r('app.media.egg'),FoodCategoryEnum.MEAT, '个',  74, 0.1, 6.2, 5.4),
  new RecordItem(12, '煮鸡胸肉',$r('app.media.chicken_breast'),FoodCategoryEnum.MEAT, '克',  1.15, 0.011, 0.236, 0.018),
  new RecordItem(13, '煮鸡腿肉',$r('app.media.chicken_leg'),FoodCategoryEnum.MEAT, '克',  1.87, 0.0, 0.243, 0.092),
  new RecordItem(14, '牛肉',$r('app.media.beef'),FoodCategoryEnum.MEAT, '克',  1.22, 0.0, 0.23, 0.033),
  new RecordItem(15, '鱼肉',$r("app.media.fish"),FoodCategoryEnum.MEAT, '克',  1.04, 0.0, 0.206, 0.024),
  new RecordItem(16, '牛奶',$r("app.media.milk"),FoodCategoryEnum.MEAT, '毫升',  0.66, 0.05, 0.03, 0.038),
  new RecordItem(17, '酸奶',$r("app.media.yogurt"),FoodCategoryEnum.MEAT, '毫升',  0.7, 0.10, 0.032, 0.019),
  new RecordItem(18, '核桃',$r("app.media.walnut"),FoodCategoryEnum.NUT, '颗',  42, 1.2, 1.0, 3.8),
  new RecordItem(19, '花生',$r("app.media.peanut"),FoodCategoryEnum.NUT, '克',  3.13, 0.13, 0.12, 0.254),
  new RecordItem(20, '腰果',$r("app.media.cashew"),FoodCategoryEnum.NUT, '克',  5.59, 0.416, 0.173, 0.367),
  new RecordItem(21, '无糖拿铁',$r("app.media.coffee"),FoodCategoryEnum.OTHER, '毫升',  0.43, 0.044, 0.028, 0.016),
  new RecordItem(22, '豆浆',$r("app.media.soybean_milk"),FoodCategoryEnum.OTHER, '毫升',  0.31, 0.012, 0.030, 0.016),
]

const workouts: RecordItem[] = [
  new RecordItem(10000, '散步',$r('app.media.ic_walk'), WorkoutCategoryEnum.WALKING, '小时', 111),
  new RecordItem(10001, '快走',$r('app.media.ic_walk'), WorkoutCategoryEnum.WALKING, '小时',  343),
  new RecordItem(10002, '慢跑',$r('app.media.ic_running'), WorkoutCategoryEnum.RUNNING, '小时',  472),
  new RecordItem(10003, '快跑',$r('app.media.ic_running'), WorkoutCategoryEnum.RUNNING, '小时',  652),
  new RecordItem(10004, '自行车',$r('app.media.ic_ridding'), WorkoutCategoryEnum.RIDING, '小时',  497),
  new RecordItem(10005, '动感单车',$r('app.media.ic_ridding'), WorkoutCategoryEnum.RIDING, '小时',  587),
  new RecordItem(10006, '瑜伽',$r('app.media.ic_aerobics'), WorkoutCategoryEnum.AEROBICS, '小时',  172),
  new RecordItem(10007, '健身操',$r('app.media.ic_aerobics'), WorkoutCategoryEnum.AEROBICS, '小时',  429),
  new RecordItem(10008, '游泳',$r('app.media.ic_swimming'), WorkoutCategoryEnum.SWIMMING, '小时',  472),
  new RecordItem(10009, '冲浪',$r('app.media.ic_swimming'), WorkoutCategoryEnum.SWIMMING, '小时',  429),
  new RecordItem(10010, '篮球',$r('app.media.ic_basketball'), WorkoutCategoryEnum.BALLGAME, '小时',  472),
  new RecordItem(10011, '足球',$r('app.media.ic_football'), WorkoutCategoryEnum.BALLGAME, '小时',  515),
  new RecordItem(10012, '排球',$r("app.media.ic_volleyball"), WorkoutCategoryEnum.BALLGAME, '小时',  403),
  new RecordItem(10013, '羽毛球',$r("app.media.ic_badminton"), WorkoutCategoryEnum.BALLGAME, '小时',  386),
  new RecordItem(10014, '乒乓球',$r("app.media.ic_table_tennis"), WorkoutCategoryEnum.BALLGAME, '小时',  257),
  new RecordItem(10015, '哑铃飞鸟',$r("app.media.ic_dumbbell"), WorkoutCategoryEnum.STRENGTH, '小时',  343),
  new RecordItem(10016, '哑铃卧推',$r("app.media.ic_dumbbell"), WorkoutCategoryEnum.STRENGTH, '小时',  429),
  new RecordItem(10017, '仰卧起坐',$r("app.media.ic_sit_up"), WorkoutCategoryEnum.STRENGTH, '小时',  515),
]

/**
 * 药品数据以50000开始
 */
const medicals: MedicalRecordItem[] =[
  new MedicalRecordItem(50000, '阿奇霉素干混悬剂',$r('app.media.lxy_50000'), MedicalCategoryEnum.RespiratoryMedicines, '包','阿奇霉素干混悬剂，适应症为本品适用于敏感细菌所引起的下列感染：支气管炎、肺炎等下呼吸道感染；皮肤和软组织感染；急性中耳炎；鼻窦炎、咽炎、扁桃体炎等上呼吸道感染（青霉素是治疗化脓性链球菌咽炎的常用药，也是预防风湿热的常用药物。阿奇霉素可有效清除口咽部链球菌，但目前尚无阿奇霉素治疗和预防风湿热疗效的资料）。阿奇霉素可用于男女性传播疾病中由沙眼衣原体所致的单纯性生殖器感染。阿奇霉素亦可用于由非多重耐药淋球菌所致的单纯性生殖器感染及由杜克嗜血杆菌引起的软下疳（需排除梅毒螺旋体的合并感染）。' ),
  new MedicalRecordItem(50001, '乳酸司帕沙星',$r('app.media.lxy_50001'), MedicalCategoryEnum.RespiratoryMedicines, '片','能够抑制细菌DNA复制，从而起到杀菌的作用。本品用于治疗呼吸系统、肠道、胆道、泌尿生殖系统、皮肤,软组织及口腔科感染。' ),

  new MedicalRecordItem(50002, '金莲花胶囊',$r('app.media.lxy_50002'), MedicalCategoryEnum.AntiInflammatories, '粒','金莲花胶囊，抗菌消炎。用于上呼吸道感染，咽炎，扁桃体炎。' ),
  new MedicalRecordItem(50003, '阿莫西林胶囊',$r('app.media.lxy_50003'), MedicalCategoryEnum.AntiInflammatories, '粒','阿莫西林胶囊，适应症为阿莫西林适用于敏感菌(不产β内酰胺酶菌株)所致的下列感染：1.溶血链球菌、肺炎链球菌、葡萄球菌或流感嗜血杆菌所致中耳炎、鼻窦炎、咽炎、扁桃体炎等上呼吸道感染。2.大肠埃希菌、奇异变形杆菌或粪肠球菌所致的泌尿生殖道感染。3.溶血链球菌、葡萄球菌或大肠埃希菌所致的皮肤软组织感染。4.溶血链球菌、肺炎链球菌、葡萄球菌或流感嗜血杆菌所致急性支气管炎、肺炎等下呼吸道感染。5.急性单纯性淋病。6.本品尚可用于治疗伤寒、伤寒带菌者及钩端螺旋体病；阿莫西林亦可与克拉霉素、兰索拉唑三联用药根除胃、十二指肠幽门螺杆菌，降低消化道溃疡复发率。' ),

  new MedicalRecordItem(50004, '健胃消食片',$r('app.media.lxy_50004'), MedicalCategoryEnum.PediatricMedications, '片' ,'健胃消食片，中成药名。为消食剂，具有健胃消食之功效。主治脾胃虚弱所致的食积，症见不思饮食，嗳腐酸臭，脘腹胀满；消化不良见上述证候者。'),
  new MedicalRecordItem(50005, '儿童感清口服液',$r('app.media.lxy_50005'), MedicalCategoryEnum.PediatricMedications, '支','儿感清口服液，解表清热，宣肺化痰。用于小儿外感风寒、肺胃蕴热证，症见：发热恶寒，鼻塞流涕，咳嗽有痰，咽喉肿痛，口渴。' ),

  new MedicalRecordItem(50006, '左氧氟沙星滴眼液',$r('app.media.lxy_50006'), MedicalCategoryEnum.OphthalmicMedications, '滴' ,'左氧氟沙星滴眼液，适应症为适应菌种：对左氧氟沙星敏感的葡萄球菌属、链球菌属、肺炎球菌、细球菌属、肠球菌属、棒状杆菌属、假单胞菌属、绿脓杆菌、嗜血杆菌属[流感嗜血杆菌、结膜炎嗜血杆菌（科—威氏杆菌）]、莫拉（布兰氏）卡他菌、莫拉杆菌、莫拉－阿氏杆菌、沙雷氏菌属、克雷白氏菌属、变形杆菌属、不动杆菌属、肠杆菌属、厌氧菌属（丙酸杆菌）。适应症：用于治疗眼睑炎、睑腺炎、泪囊炎、结膜炎、睑板腺炎、角膜炎以及用于眼科围手术期的无菌化疗法。'),
  new MedicalRecordItem(50007, '玻璃酸钠滴眼液',$r('app.media.lxy_50007'), MedicalCategoryEnum.OphthalmicMedications, '滴' ,'玻璃酸钠滴眼液，适应症为伴随下述疾患的角结膜上皮损伤： ·干燥综合征（Sjogren&#39;s syndrome）、斯·约二氏综合征（Stevens-Johnson syndrome）、干眼综合征（dry eye syndrome）等内因性疾患； ·手术后、药物性、外伤、配戴隐形眼镜等外因性疾患。'),

  new MedicalRecordItem(50008, '阿泰宁酪酸梭菌活菌胶囊',$r('app.media.lxy_50008'), MedicalCategoryEnum.GastrointestinalMedications, '粒' ,'本品是一种肠道益生菌，用于治疗肠道菌群紊乱引起的各种消化道症状及相关的急、慢性腹泻和消化不良等。'),
  new MedicalRecordItem(50009, '奥美拉唑肠溶胶囊',$r('app.media.lxy_50009'), MedicalCategoryEnum.GastrointestinalMedications, '粒','奥美拉唑肠溶胶囊，适用于胃溃疡、十二指肠溃疡、应激性溃疡、反流性食管炎和卓-艾综合征（胃泌素瘤）。' ),

  new MedicalRecordItem(50010, '连花清瘟胶囊',$r('app.media.lxy_50010'), MedicalCategoryEnum.ColdMedications, '粒' ,'连花清瘟胶囊，清瘟解毒，宣肺泄热。用于治疗流行性感冒属热毒袭肺证，症见：发热或高热，恶寒，肌肉酸痛，鼻塞流涕，咳嗽，头痛，咽干咽痛，舌偏红，苔黄或黄腻等。'),
  new MedicalRecordItem(50011, '金银花口服液',$r('app.media.lxy_50011'), MedicalCategoryEnum.ColdMedications, '支' ,'金银花口服液，中成药名。由金银花、蔗糖、苯甲酸钠组成。具有清热解毒，疏散风热的功效。用于暑热内犯所致的中暑、痱疹、疖肿、风热感冒，症见发热口渴、咽喉肿痛、痱疹鲜红、头部疖肿。'),

  new MedicalRecordItem(50012, '糠酸莫米松乳膏',$r('app.media.lxy_50012'), MedicalCategoryEnum.DermalMedications, 'g' ,'糠酸莫米松乳膏，适应症为用于湿疹、神经性皮炎、异位性皮炎及皮肤瘙痒症。'),
  new MedicalRecordItem(50013, '酮康唑乳膏',$r('app.media.lxy_50013'), MedicalCategoryEnum.DermalMedications, 'g' ,'酮康唑乳膏，适应症为用于手癣、足癣、体癣、股癣、花斑癣以及皮肤念珠菌病。'),

  new MedicalRecordItem(50014, '布洛芬凝胶',$r('app.media.lxy_50014'), MedicalCategoryEnum.TraumaMedications, 'g' ,'好好爱自己'),
  new MedicalRecordItem(50015, '壮骨麝香止痛膏',$r('app.media.lxy_50015'), MedicalCategoryEnum.TraumaMedications, '贴' ,'好好爱自己'),

  new MedicalRecordItem(50016, '达克宁硝酸咪康唑栓',$r('app.media.lxy_50016'), MedicalCategoryEnum.GynecologicalMedications, '枚','好好爱自己' ),
  new MedicalRecordItem(50017, '修正消糜栓',$r('app.media.lxy_50017'), MedicalCategoryEnum.GynecologicalMedications, '枚','好好爱自己' ),

  new MedicalRecordItem(50018, '苯磺酸氨氯地平片',$r('app.media.lxy_50018'), MedicalCategoryEnum.BloodPressureMedications, '片' ,'好好爱自己'),
  new MedicalRecordItem(50019, '厄贝沙坦片',$r('app.media.lxy_50019'), MedicalCategoryEnum.BloodPressureMedications, '片','好好爱自己' ),

  new MedicalRecordItem(50020, '999感冒灵',$r('app.media.lxy_50020'), MedicalCategoryEnum.ColdMedications, '包' ,'好好爱自己'),
  new MedicalRecordItem(50021, '感冒灵颗粒',$r('app.media.lxy_50021'), MedicalCategoryEnum.ColdMedications, '包','好好爱自己' ),
  new MedicalRecordItem(50022, '复方感冒灵颗粒',$r('app.media.lxy_50022'), MedicalCategoryEnum.ColdMedications, '包' ,'好好爱自己'),
  new MedicalRecordItem(50023, '感冒清热颗粒',$r('app.media.lxy_50023'), MedicalCategoryEnum.ColdMedications, '包' ,'好好爱自己'),

  new MedicalRecordItem(50024, '新复方芦荟胶囊',$r('app.media.lxy_50024'), MedicalCategoryEnum.GastrointestinalMedications, '粒','好好爱自己' ),
  new MedicalRecordItem(50025, '健胃消食片',$r('app.media.lxy_50025'), MedicalCategoryEnum.GastrointestinalMedications, '片','好好爱自己' ),
  new MedicalRecordItem(50026, '多番立酮片',$r('app.media.lxy_50026'), MedicalCategoryEnum.GastrointestinalMedications, '片','好好爱自己' ),
  new MedicalRecordItem(50027, '马应龙麝香痔疮膏',$r('app.media.lxy_50027'), MedicalCategoryEnum.GastrointestinalMedications, 'g','好好爱自己' ),
  new MedicalRecordItem(50028, '四磨汤口服液',$r('app.media.lxy_50028'), MedicalCategoryEnum.GastrointestinalMedications, '支','好好爱自己' ),
  new MedicalRecordItem(50029, '蒙脱石散',$r('app.media.lxy_50029'), MedicalCategoryEnum.GastrointestinalMedications, '袋' ,'好好爱自己'),

  new MedicalRecordItem(50030, '达克宁',$r('app.media.lxy_50030'), MedicalCategoryEnum.DermalMedications, 'g' ,'好好爱自己'),
  new MedicalRecordItem(50031, '复方醋酸地塞米松乳膏',$r('app.media.lxy_50031'), MedicalCategoryEnum.DermalMedications, 'g' ,'好好爱自己'),
  new MedicalRecordItem(50032, '地奈德乳膏',$r('app.media.lxy_50032'), MedicalCategoryEnum.DermalMedications, 'g' ,'好好爱自己'),
  new MedicalRecordItem(50033, '复方酮康唑发用洗剂',$r('app.media.lxy_50033'), MedicalCategoryEnum.DermalMedications, 'ml','好好爱自己' ),
  new MedicalRecordItem(50034, '酮康唑乳膏',$r('app.media.lxy_50034'), MedicalCategoryEnum.DermalMedications, 'g','好好爱自己' ),
  new MedicalRecordItem(50035, '润湿烧伤膏',$r('app.media.lxy_50035'), MedicalCategoryEnum.DermalMedications, 'g' ,'好好爱自己'),
  new MedicalRecordItem(50036, '兰索拉唑肠溶片',$r('app.media.lxy_50036'), MedicalCategoryEnum.GastrointestinalMedications, '片' ,'新增药品'),


]




/*
 * 记录项的查询接口*/
class ItemModel{

  getById(id: number, isFood: boolean = true){
    return isFood ? foods[id] : workouts[id - 10000]
  }

  list(isFood: boolean = true): RecordItem[]{
    return isFood ? foods : workouts
  }

  listItemGroupByCategory(isFood: boolean = true){
    // 1.判断要处理的是食物还是运动
    let categories = isFood ? FoodCategories : WorkoutCategories
    let items = isFood ? foods: workouts
    // 2.创建空的分组
    let groups = categories.map(itemCategory => new GroupInfo(itemCategory, []))
    // 3.遍历记录项列表，将食物添加到对应的分组
    items.forEach(item => groups[item.categoryId].items.push(item))
    // 4.返回结果
    return groups
  }




  /**
   * 药品的方法
   */

  //获取所有药品
  medicalList(): MedicalRecordItem[]{
    return medicals
  }
  //获取根据分类id分组的药品列表
  ListMedicalItemGroupByCategory(){
    //通过类型数组用map映射出，一个药品分类list页面的数据类型 GoryInfo
    let groups=MedicalCategories.map(itemCategory=>new GroupInfo(itemCategory,[]))
    //遍历本机的药品数据将其分类
    medicals.forEach(item=>groups[item.categoryId].items.push(item))
    return groups
  }
  //根据id获取药品
  medicalGetById(id: number): MedicalRecordItem {
    return medicals[id-5000]
  }
  //list集合中存储id，返回药品集合MedicalRecordItem
  medicalGetByIds(list: ArrayList<number>){
    let groups: ArrayList<MedicalRecordItem>=new ArrayList()
    list.forEach(id=>{
      groups.add(medicals[id])
    })
    return groups
  }
}

let itemModel = new ItemModel()

export default itemModel as ItemModel