package com.ingestion.addon

import com.ingestion.model.WriteData
import org.apache.spark.sql.DataFrame
import org.json4s.DefaultFormats
import org.json4s.native.JsonMethods.parse

class SaveData(df: DataFrame) {
  implicit val formats = DefaultFormats
  val connector = new SparkConnecte
  val spark = connector.getSession()

  val res = Converter.conToJson("write")
  val Sw = parse(res).extract[WriteData]

  df.write
    .format(Sw.format)
    .mode(Sw.mode)
    .options(Sw.options)
    .save(Sw.location)

  spark.sql(s"""drop table if EXISTS ${Sw.TbName}""")

  val schema: String = GetSchema.res

  spark.sql(
    s"""CREATE EXTERNAL TABLE IF NOT EXISTS
       |${Sw.TbName} ($schema)
       |ROW FORMAT DELIMITED
       |FIELDS TERMINATED BY ';'
       |STORED AS TEXTFILE
       |LOCATION '/apps/hive/external/default/${Sw.TbName}'
       |""".stripMargin)
}
