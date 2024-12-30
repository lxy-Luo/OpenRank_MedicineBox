
import Message from './Message'
@Observed
export default class MessageVO{

  messages: Message[]
  unreadTotal: number
  total: number

  constructor(messages?:Message[],unreadTotal?:number,total:number=0) {
    this.messages = messages
    this.unreadTotal = unreadTotal
    this.total = total
  }


}