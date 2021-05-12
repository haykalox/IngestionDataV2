package com.ingestion.app

import com.ingestion.addon.{Converter, CreateDf, Read, SaveData, SparkConnecte}
import com.ingestion.model.File
import org.json4s.DefaultFormats

object Xls {

  import org.json4s.native.JsonMethods._

  implicit val formats = DefaultFormats

  def main(args: Array[String]): Unit = {
    val connector = new SparkConnecte
    val spark = connector.getSession()

    val read = new Read
    val data = Converter.conToJson("file")
    val file = parse(data).extract[File]

    val df = {
      if (file.fileType == "xls") {
        read.data("readXls", file.fileLocation)
      } else if (file.fileType == "csv") {
        read.data("readCsv", file.fileLocation)
      } else if (file.fileType == "mysql") {
        read.mysql("readMysql")
      } else Seq.empty(3)
    }
    val write = new CreateDf
    val res = write.dataJSON(df)
    val test = write.dataColumn(df)

    res.show()
    test.show()
    val save = new SaveData(res)

    spark.sql("SELECT * FROM xls").show()
  }
}
