import router from '@ohos.router'
import MessageSelectDialog from '../view/message/MessageSelectDialog'
import Message from '../viewmodel/message/Message'
import axios from '@ohos/axios';
import prompt from '@ohos.promptAction';
import MessageMenuDialog from '../view/message/MessageMenuDialog';
import PreferenceUtil from '../common/utils/PreferenceUtil';


@Entry
@Component
struct MessageOverviewPage {


  @Provide unreadCount:number = 0
  // 判断当前是否需要要选择
  @State isSelected:boolean = false
  // 操作消息id，默认为-1
  @State optionId: number = -1
  // 消息列表
  @State messages: Message[] = []

  @State isFirstTop: boolean = true
  // 长按消息弹窗
  controller: CustomDialogController = new CustomDialogController({
    builder:MessageSelectDialog({optId:this.optionId,messages:$messages}),
    alignment:DialogAlignment.Bottom,
    offset:{dx:0,dy:-25}
  })

  // 菜单弹窗
  menuController: CustomDialogController = new CustomDialogController({
    builder:MessageMenuDialog({
      isSelected:$isSelected,
      messages:$messages,
      getMessage:()=>this.getMessages()
    }),
    alignment:DialogAlignment.TopEnd,
    offset:{dx:-40,dy:30 },
    customStyle:true
  })

  // 需要删除的消息Id
  selectedIdList:number[] = []
  // 本地删除的消息索引
  selectedIndexList:number[]= []

  // 删除选中Id（分别在本地和服务器删)
  deleteSelectedMessages(){
    axios.post('http://123.56.84.181:81/sys/message/delete', this.selectedIdList
    ).then((res)=>{
      if(res.data.code===0){
        prompt.showToast({message:$r('app.string.async_cloud')})
      }else{
        prompt.showToast({message:$r('app.string.async_cloud_failed')})
      }
    }).catch((err)=>{
      prompt.showToast({message:$r('app.string.networkOrServiceError')})
    })

    this.selectedIndexList.forEach((index:number)=>this.messages[index]=null)
    this.messages = this.messages.filter((message:Message)=>message!==null)
    this.selectedIndexList=[]
    this.selectedIdList=[]
  }

  //获取选中的消息id和索引放入对应的数组
  getSelectedMessagesIdAndIndex(message:Message,value:boolean,index:number){
    let idx = this.selectedIdList.indexOf(message.id,0)
    if(value && idx === -1){
      this.selectedIndexList.push(index)
      this.selectedIdList.push(message.id)
    }else if(!value && idx!==-1){
      this.selectedIdList.splice(idx,1)
      this.selectedIndexList.splice(idx,1)
    }
  }


  // 优先展示未读消息
  sort(m1:Message,m2:Message):number{
    if(!m1.isRead&&!m2.isRead){
      return -1
    }
    if(m1.isRead&&!m2.isRead){
      return 1
    }else{
      return 0
    }
  }

  //向服务器获取消息列表
  async getMessages(){
    let userId = await PreferenceUtil.getPreferenceValue("userId",1)
    axios.get('http://123.56.84.181:81/sys/message/list/'+userId)
      .then((response)=>{
        if(response.data.code===0){
          this.unreadCount = response.data.list.unreadTotal
          // 这里对结果进行排序优先显示未读消息
          this.messages = response.data.list.messages.sort((m1:Message,m2:Message)=>this.sort(m1,m2))
        }else{
          console.log(response.data.msg)
        }
      })
      .catch((err)=>{
        console.log(err.code+err.message)
        let message = $r('app.string.networkOrServiceError')
        prompt.showToast({message:message+''})
      })
  }

  async aboutToAppear(){
    this.getMessages()
  }

  // 查看消息详情
   ReadMessageInfo(message:Message){
    router.pushUrl({url:'pages/MessageInfoPage',params:{
      message: message
    }})
  }

  build() {
    Column(){
      Row(){
        // 返回按钮
        Image($r('app.media.ic_public_back'))
          .width(40)
          .fillColor('#c6ffe6')
          .backgroundColor('#454545')
          .borderRadius(10)
          .onClick(()=>{
            router.back()
          })
        Text($r('app.string.message_center'))
          .fontWeight(FontWeight.Bold)
          .fontSize(20)
          .fontColor('#c6ffe6')
          .width('30%')
        Image($r('app.media.msg_menu'))
          .fillColor('#8FBC8F')
          .width(40)
          .margin({right:10})
          .onClick(()=>{
            this.menuController.open()
          })
      }
      .justifyContent(FlexAlign.SpaceBetween)
      .width('94%')
      .height(30)
      .margin({bottom:15,top:10})
        Column() {
          if(this.messages.length !==0 && this.messages!==null ){
            List({ space: 8 }) {
              ForEach(this.messages, (message: Message, index: number) => {
                if(message){
                  ListItem() {
                    this.MessageItem(message, index)
                  }
                  // 长按触发弹窗
                  .gesture(
                    LongPressGesture({ repeat: false })
                      .onAction((event: GestureEvent) => {
                        // 获取当前的消息id同步到弹窗组件
                        this.optionId = index
                        this.controller.open()
                      })
                  )
                  .onClick(() => {
                    if(!this.isSelected) {
                      this.ReadMessageInfo(message)
                      message.isRead = true
                      this.messages.push(null)
                    }
                  })
                  .margin({ bottom: 6 })
                }
              })
            }
            .width('100%')
            .height(this.isSelected?'75%':'90%')
            .borderRadius(10)
          }else {
            Text($r('app.string.no_data'))
              .padding({ top: 50, left: 50 })
              .fontColor(Color.Grey)
              .fontWeight(FontWeight.Regular)
              .fontSize(60)
              .height('100%')
              .width('100%')
          }
          if (this.isSelected) {
            Row() {
              Button($r('app.string.delete_selected'))
                .width(150)
                .height(50)
                .onClick(()=>{
                  this.deleteSelectedMessages()
                })
                .backgroundColor('#61bc84')
              Button($r('app.string.cancel'))
                .width(150)
                .height(50)
                .backgroundColor(Color.Grey)
                .onClick(()=>{
                  this.isSelected = false
                })
            }
            .borderRadius(25)
            .padding({top:20})
            .justifyContent(FlexAlign.SpaceEvenly)
            .alignItems(VerticalAlign.Center)
            .width('94%')
            .height(100)
          }
        }
        .width('94%')
      }
    .backgroundColor('#1E1E1E')
    .width('100%')
    .height('100%')
  }


  @Builder MessageItem(message:Message,index:number){
      Row(){
        Column(){
          Image($r('app.media.ic_public_email'))
            .borderRadius(25)
            .width(30)
            .height(30)
            .margin({top:20})
        }
        .width(35)
        .height('100%')
        Column(){
          Row(){
            Text(message.sender)
              .maxLines(1)
              .fontSize(15)
              .fontWeight(FontWeight.Bold)
              .fontColor('#FFFFFF')
          }
          .justifyContent(FlexAlign.SpaceBetween)
          .height(30)
          .width('100%')
          Row(){
            Text(message.content)
              .fontColor('#e0e0e0')
              .fontSize(12)
              .maxLines(1)
          }
          .height(15)
          .width('100%')
        }
        .width('60%')
        .height(50)
        Column({space:2}){
          // 如何是选择状态则会出现选择框
          if(!message.isRead){
            Badge({count: 1, position: BadgePosition.Right,style: {fontSize: 12}}){
              Text()
            }
            .width(30)
            .height(20)
          }
          if(this.isSelected){
            Checkbox()
              .selectedColor('#61bc84')
              .onChange((value)=>{
                this.getSelectedMessagesIdAndIndex(message,value,index)
              })
          }else{
            Text(message.time)
              .fontSize(10)
              .fontColor('#e0e0e0')
            Text(message.date)
              .fontSize(10)
              .fontColor('#e0e0e0')
          }
        }
      }
      .justifyContent(FlexAlign.SpaceEvenly)
      .borderColor('#454545')
      .height(80)
      .margin({left:6})
      .width('94%')
      .backgroundColor('#2d2d2d')
    .borderRadius(25)
  }
}