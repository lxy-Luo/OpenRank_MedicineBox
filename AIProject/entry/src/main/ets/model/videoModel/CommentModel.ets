//评论模型
/**
 * @Author 罗骁阳
 * @Date 2024/4/17
 */
@Observed
export default class CommentModel {
  /**
   * id
   */
  id: number

  /**
   * 关联的视频id
   */
  videoId:number

  /**
   * 发送该评论的用户名
   */
  userName: string

  /**
   * 用户的头像
   */
  userImage: ResourceStr

  /**
   * 评论内容
   */
  message: string

  /**
   * 发送评论的时间 “MM-dd”
   */
  time:string

  /**
   * IP 地址
   */
  address:string

  /**
   * 点赞数
   */
  ThumbsUpNumber: number



  constructor(id:number,videoId:number,userName:string,userImage:ResourceStr,message:string,time:string,address:string,ThumbsUpNumber: number) {
    this.id = id
    this.videoId=videoId
    this.userName=userName
    this.userImage=userImage
    this.message=message
    this.time=time
    this.address=address
    this.ThumbsUpNumber=ThumbsUpNumber
  }
}