/*
 * 绘制实体表盘页面（时钟动效）
 * */

@Preview
@Component
export struct CanvasComp {
  private settings = new RenderingContextSettings(true)
  private context = new CanvasRenderingContext2D(this.settings)
  private panImg = new ImageBitmap('../../resources/base/media/biaopan.svg')
  private hourImg = new ImageBitmap('../../resources/base/media/hour.svg')
  private minuteImg = new ImageBitmap('../../resources/base/media/min.svg')
  private secondImg = new ImageBitmap('../../resources/base/media/miao.svg')

  // 画布尺寸
  canvasSize = 252
  // 指针尺寸
  pointerWidth = 8

  startDraw() {
    this.drawClock()
    setInterval(() => {
      this.drawClock()
    }, 1000)
  }

  /**
   * 绘制闹钟
   */
  drawClock() {
    this.context.clearRect(0, 0, this.canvasSize, this.canvasSize)
    this.context.drawImage(this.panImg, 0, 0, this.canvasSize, this.canvasSize)
    // 根据时间绘制指针
    const date = new Date()
    const hour = date.getHours()
    const minute = date.getMinutes()
    const second = date.getSeconds()
    this.drawPointer(this.hourImg, hour % 12 / 12 * 360)
    this.drawPointer(this.minuteImg, minute / 60 * 360)
    this.drawPointer(this.secondImg, second / 60 * 360)
  }

  /**
   * 绘制指针
   * @param img - 指针图片
   * @param angle - 旋转角度，起点是 Y 轴上方向
   */
  drawPointer(img: ImageBitmap, angle: number) {
    this.context.save()
    this.context.translate(this.canvasSize / 2, this.canvasSize / 2)
    this.context.rotate((angle + 180) * Math.PI / 180)
    this.context.translate(-this.canvasSize / 2, -this.canvasSize / 2)
    this.context.drawImage(img, this.canvasSize / 2 - this.pointerWidth / 2, 0, this.pointerWidth, this.canvasSize)
    this.context.restore()
  }

  build() {
    Canvas(this.context)
      .width(this.canvasSize)
      .aspectRatio(1)
      .onReady(() => {
        this.startDraw()
      })
  }
}