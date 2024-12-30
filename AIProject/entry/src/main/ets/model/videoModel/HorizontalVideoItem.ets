/*
 * 作者：drd
 * 视频数据模型
 * */

@Observed
export class HorizontalVideoItem {
  id: number;
  image: ResourceStr;
  sping: ResourceStr
  name: string;

  constructor(id: number, image: ResourceStr, sping: ResourceStr,name: string) {
    this.id = id;
    this.image = image;
    this.sping = sping;
    this.name = name;
  }
}