import mediaQuery from '@ohos.mediaquery';     //媒体查询功具包
import BreakpointConstants from '../constants/BreakpointConstans';   //自定义屏幕比例常量类

/*
 * 媒体查询工具类*/

export default class BreakpointSystem{
  private smListener: mediaQuery.MediaQueryListener = mediaQuery.matchMediaSync(BreakpointConstants.RANGE_SM)
  private mdListener: mediaQuery.MediaQueryListener = mediaQuery.matchMediaSync(BreakpointConstants.RANGE_MD)
  private lgListener: mediaQuery.MediaQueryListener = mediaQuery.matchMediaSync(BreakpointConstants.RANGE_LG)

  smListenerCallback(result: mediaQuery.MediaQueryResult){
    if(result.matches){
      this.updateCurrentBreakpoint(BreakpointConstants.BREAKPOINT_SM)
    }
  }
  mdListenerCallback(result: mediaQuery.MediaQueryResult){
    if(result.matches){
      this.updateCurrentBreakpoint(BreakpointConstants.BREAKPOINT_MD)
    }
  }
  lgListenerCallback(result: mediaQuery.MediaQueryResult){
    if(result.matches){
      this.updateCurrentBreakpoint(BreakpointConstants.BREAKPOINT_LG)
    }
  }

  //抽取出指定全局参数的方法
  updateCurrentBreakpoint(breakpoint: string){
    AppStorage.SetOrCreate(BreakpointConstants.CURRENT_BREAKPOINT,breakpoint)
  }

  //注册回调
  register(){
    this.smListener.on('change',this.smListenerCallback.bind(this))
    this.mdListener.on('change',this.mdListenerCallback.bind(this))
    this.lgListener.on('change',this.lgListenerCallback.bind(this))
  }
  //取消回调
  unregister(){
    this.smListener.off('change',this.smListenerCallback.bind(this))
    this.mdListener.off('change',this.mdListenerCallback.bind(this))
    this.lgListener.off('change',this.lgListenerCallback.bind(this))
  }
}