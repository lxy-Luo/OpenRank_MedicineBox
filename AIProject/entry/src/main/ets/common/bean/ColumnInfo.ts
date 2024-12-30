export class ColumnInfo{
  name: string        //实体里的字段名
  columnName: string   //实体对应数据库的字段名
  type: ColumnType     //对应的数据库字段类型
}

export enum ColumnType{
  LONG,
  DOUBLE,
  STRING,
  BLOB
}