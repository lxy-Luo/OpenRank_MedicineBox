@Observed
export default class Message{

  id: number
  content: string
  date:string
  time:string
  sender: string
  isRead: boolean

  constructor(id:number,content:string,date:string,time:string,sender:string,isRead:boolean = false) {
    this.id = id
    this.content = content
    this.date = date
    this.time = time
    this.sender =sender
    this.isRead = isRead
  }
}

/*let message = new Message();
export default message as Messag*/