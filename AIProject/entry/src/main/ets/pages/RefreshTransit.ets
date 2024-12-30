/*
 *@Author: Dairundong
 *@Date: 2024/3/27 21:07
 * 刷新中转页:：用来解决组件页面无法控制总页面从而导致刷新问题
 * */
import router from '@ohos.router'
@Entry
@Component
struct RefreshTransit {
  taskId: number=-1

  onPageShow(){
    router.back()
  }

  build() {
    Column(){
    }
    .backgroundColor($r('app.color.hei1'))
  }
}