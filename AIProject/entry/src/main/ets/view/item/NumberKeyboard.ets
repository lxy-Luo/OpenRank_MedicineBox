import { CommonConstants } from '../../common/constants/CommonConstants'

/*
 * 基于Grid的自定义键盘组件*/

@Component
export default  struct NumberKeyboard {
  numbers: string[] = ['1','2','3','4','5','6','7','8','9','0','.']
  @Link amount: number  //最终显示数值
  @Link value: string  //用于键盘拼接相关选项，来确定最终amount的value

  @Styles keyBoxStyle(){
    .backgroundColor(Color.White)
    .borderRadius(8)
    .height(60)
  }

  build() {
    Grid(){
      ForEach( this.numbers, num => {
        GridItem(){
          Text(num).fontSize(20).fontWeight(CommonConstants.FONT_WEIGHT_900)

        }
        .keyBoxStyle()
        .onClick(() => {
            this.clickNumber(num)
        })
      })
      GridItem(){
        Text('删除').fontSize(20).fontWeight(CommonConstants.FONT_WEIGHT_900)
      }
      .onClick(() => {
        this.clickDelete()
      })
      .keyBoxStyle()
    }
    .width('100%')
    .height(280)
    .backgroundColor($r('app.color.index_page_background'))
    .columnsTemplate('1fr 1fr 1fr')
    .columnsGap(8)
    .rowsGap(8)
    .padding(8)
  }

  clickNumber(num: string){
    //1.拼接用户输入的内容
    let val = this.value + num
    //2.校验输入格式是否正确
    let firstIndex = val.indexOf('.')
    let lastIndex = val.lastIndexOf('.')
    //没有'.'
    // '.'在正确位置 且 小数点为位置不能超过两位 且 有小数
    if(firstIndex !== lastIndex || (lastIndex != -1 && lastIndex < val.length -2)){
      //非法输入
      return
    }
    //3.将字符串转化为数值
    let amount = this.parseFloat(val)
    //4.保存(保证最大值上限)
    if(amount >= 999.9){
      this.amount = 999.9
      this.value = '999'
    }else{
      this.amount = amount
      this.value = val
    }
  }
  //键盘删除函数
  clickDelete(){
    if(this.value.length <= 0){
      this.value = ''
      this.amount = 0
      return
    }
    //删一位
    this.value = this.value.substring(0, this.value.length - 1)
    this.amount = this.parseFloat(this.value)
  }
  //将键盘输入的string转换为对应的浮点类型
  parseFloat(str: string){
    //防止全部删除完成显示0
    if(!str){
      return 0
    }
    //判断字符串是否是以'.'结尾
    if(str.endsWith('.')){
      str = str.substring(0,str.length - 1)
    }
    //转换
    return parseFloat(str)
  }
}