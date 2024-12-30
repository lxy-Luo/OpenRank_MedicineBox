import RecordIndex from '../view/record/RecordIndex'
import BreakpointType from '../common/bean/BreanpointType'
import BreakpointConstants from '../common/constants/BreakpointConstans'
import { CommonConstants } from '../common/constants/CommonConstants'
import BreakpointSystem from '../common/utils/BreakpointSystem'
import MedicineBoxIndex from '../view/MedicineBox/MedicineBoxIndex'
import UserInfoIndex from '../view/userInfo/UserInfoIndex'
import MedicalRecordItem from '../viewmodel/medical/MedicalRecordItem'
import DateUtil from '../common/utils/DateUtil'
import axios from '@ohos/axios'
import MedicalRecordDTO from '../viewmodel/medical/MedicalRecordDTO'
import MedicalRecordVO from '../viewmodel/medical/MedicalRecordVO'
import medicineBoxService from '../service/MedicineBoxService'
import prompt from '@ohos.promptAction'
import PreferenceUtil from '../common/utils/PreferenceUtil'
import MedicalListVO from '../viewmodel/medical/MedicalListVO'
import { MedicalCategoryEnum } from '../model/ItemCategoryModel'
import router from '@ohos.router'
import RecordService from '../service/RecordService'
import ItemModel from '../model/ItemModel'
import Logger from '../common/utils/Logger'
import { videoIndex } from '../view/Video/VideoIndex'
import LockerIndex from '../view/locker/LockerIndex'


@Entry
@Component
struct Index {
  //tab角标变量
  @State currentIndex: number = 0
  private breakpointSystem: BreakpointSystem = new BreakpointSystem()  //声明引入屏幕状态检测类
  @StorageProp('currentBreakpoint') currentBreakpoint: string = BreakpointConstants.BREAKPOINT_SM  //获取屏幕状态信息（默认为窄屏手机）
  //获取当前时间，并监听变化时向后端发起请求得到相关数据
  @StorageProp('selectedDate') @Watch('getTakeMedicineByDate') selectedDate: number = DateUtil.beginTimeOfDay(new Date())
  //解决页面切换后首页资源未刷新问题的传递参数(当前页面关闭时，为false)，使用组件：RecordIndex
  @State isPageShow: boolean = false
  //当日使用药品数据
   todayMedicalListDTO: MedicalRecordDTO[] = []
  @Provide todayMedicalList: MedicalRecordVO[] = []
  // 未读数量
  @Provide unreadCount:number = 0

  //今日计划数据
  @Provide record:MedicalListVO[]=[]

  //今日已服用药品的数量
  @State eatTotal: number =0
  //今日计划服用的药品数量
  @State TotalNumber :number=0
  //今日未服用数量
  @State unfinished: number =0



  async onPageShow(){
    //给record一些数据
    this.isPageShow = true
    //拿取当前当前选择日期的用药记录
    await this.getTakeMedicineByDate()
    //从rdb中获取今日计划的数据

    //根据rdb中存储id查询到对应的药品数据
    this.record=[]
    let test= await RecordService.queryMedicalRecord()
    test.map((item)=>{
      let medicalData= ItemModel.medicalGetById(item.id-50000+5000)
      let temp=new MedicalListVO(medicalData.id,medicalData.id,
        medicalData.name,medicalData.image,medicalData.categoryId,
        medicalData.unit,medicalData.introduction,0,item.All_number)
      this.record.push(temp)
    })
    //获取今日用药数据
    this.getMedicDate()
    this.getEatMedicalNumber()
    this.getTotalMedicalNumber()
    this.getUnfinished()


    console.log('赋值了')
    this.getUnReadCount()

  }

  async getUnReadCount(){
    let userId = await PreferenceUtil.getPreferenceValue("userId",34)
    axios.get("http://123.56.84.181:81/sys/message/getUnreadTotal/"+userId,{timeout:3000})
      .then((res)=>{
        if(res.data.code===0){
          this.unreadCount=res.data.unreadTotal
        }
      })
    this.isPageShow = true
  }


  onPageHide(){
    this.isPageShow = false
  }
  // 统一的TabBar样式
  @Builder TabBarBuilder(title: ResourceStr, image0: ResourceStr, image1: ResourceStr,index: number){
    Column({space: CommonConstants.SPACE_8}){
      Image(this.currentIndex === index?image1: image0)
        .width(22)
        //.fillColor(this.selectColor(index))
      Text(title)
        .fontSize(14)
        .fontColor(this.selectColor(index))
    }
  }

  async aboutToAppear(){
    this.breakpointSystem.register()
    //从rdb中获取今日计划的数据
    RecordService.insert(50001,8)
    RecordService.insert(50005,3)
    RecordService.insert(50011,5)
    let test= await RecordService.queryMedicalRecord()
    //根据rdb中存储id查询到对应的药品数据
    test.map((item)=>{
      let medicalData= ItemModel.medicalGetById(item.id-50000+5000)
      let temp=new MedicalListVO(medicalData.id,medicalData.id,
        medicalData.name,medicalData.image,medicalData.categoryId,
        medicalData.unit,medicalData.introduction,0,item.All_number)
      this.record.push(temp)
    })
  }
  aboutToDisappear(){
    this.breakpointSystem.unregister()
  }
  //颜色切换函数
  selectColor(index: number){
     return this.currentIndex === index ? $r('app.color.primary_color') : $r('app.color.gray')
  }
  //Tab导航栏根据current屏幕状态切换函数
  //使用对象值来省去if else等各类判断
  chooseBarPosition(){
    return new BreakpointType({
      sm: BarPosition.End,
      md: BarPosition.Start,
      lg: BarPosition.Start}).getValue(this.currentBreakpoint)
  }
  async getTakeMedicineByDate(){
      await axios.get(
        // TODO 需要更改userID
        '123.56.84.181:81/smb/medicationsituation/list'+'/43/'+DateUtil.formatDate(this.selectedDate)   //DateUtil.formatDate(this.selectedDate)
      ).then((res) => {
        if(res.data.code === 0){
          prompt.showToast({
            message: ('获取最新用药记录成功')
          })
          this.todayMedicalListDTO = res.data.date.map((item) => ({
            // id: item.id,
            // DrugId: item.drugId,
            // time: item.time,
            // All_number: item.all_number,
            // image: item.image
            //改动
            //id的值为long类型毫秒值
            //id
            //


          }))
         this.todayMedicalList =  medicineBoxService.calculateMedicalRecordVO(this.todayMedicalListDTO)
        }else{
          prompt.showToast({
            message: res.data.msg
          })
        }
      })
        .catch(error => {
          prompt.showToast({
            message: ('失败')
          })
        })
 }
  getMedicDate(){
    for (let i = 0; i < this.record.length; i++) {
      let sum=0;
      for(let j=0;j<this.todayMedicalList.length;j++){
        if(this.record[i].DrugId === this.todayMedicalList[j].DrugId){
          sum+=this.todayMedicalList[j].All_number
          this.record[i] =
          new MedicalListVO( this.record[i].id,this.record[i].DrugId, this.record[i].name,this.record[i].DrugImage, this.record[i].categoryId, this.record[i].unit,this.record[i].introduction ,sum,this.record[i].total_number);
        }
      }
    }
  }
  getEatMedicalNumber(){
    let sum=0
    for(let i=0;i<this.todayMedicalList.length;i++){
      sum+=this.todayMedicalList[i].All_number;
    }
    this.eatTotal=sum
  }

  getTotalMedicalNumber(){
    let sum=0
    for(let i=0;i<this.record.length;i++){
      sum+=this.record[i].total_number
    }
    this.TotalNumber=sum
  }

  getUnfinished(){
    if(this.TotalNumber-this.eatTotal>0){
      this.unfinished=this.TotalNumber-this.eatTotal
    }else{
      this.unfinished=0
    }
  }

  build() {
    Tabs({barPosition: this.chooseBarPosition()}) {
      TabContent() {
        MedicineBoxIndex({eatTotal: $eatTotal,TotalNumber:$TotalNumber,unfinished:$unfinished})
      }
      .tabBar(this.TabBarBuilder($r('app.string.yaoxiang'),$r("app.media.drdyaoxiangnew"),$r("app.media.yaoxiang1"),0))

      TabContent() {
        videoIndex()
        //RecordIndex({isPageShow: this.isPageShow})
      }
      .tabBar(this.TabBarBuilder($r('app.string.tab_record'),$r("app.media.ic_calendar"),$r("app.media.eat1"),1))
      TabContent() {
        LockerIndex()
      }
      .tabBar(this.TabBarBuilder($r('app.string.tab_locker'),$r("app.media.nz2"),$r("app.media.nz1"),2))
      TabContent() {
        UserInfoIndex()
      }
      .tabBar(this.TabBarBuilder($r('app.string.tab_user'),$r("app.media.my1"),$r("app.media.ic_user_portrait"),2))
    }
    .width('100%')
    .height('100%')
    .backgroundColor($r('app.color.hei1'))
    .onChange(index => this.currentIndex = index)
    .vertical(new BreakpointType({
      sm: false,
      md: true,
      lg: true
    }).getValue(this.currentBreakpoint))
  }
}
