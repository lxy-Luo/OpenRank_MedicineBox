/**
 * List item data entity.资源类
 */
export default class PageResource {
  /**
   * Text of list item. 标题资源
   */
  title: Resource;
  /**
   * Image of list item.   图片资源
   */
  img: Resource;
  /**
   * Other resource of list item.   其他资源（可选）
   */
  others?: Resource;

  constructor(title: Resource, img: Resource, others?: Resource) {
    this.title = title;
    this.img = img;
    this.others = others;
  }
}