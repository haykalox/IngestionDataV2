package com.ingestion.app

import com.ingestion.addon.{Converter, Read}
import com.ingestion.model.File
import org.json4s.DefaultFormats

object Xls {

  import org.json4s.native.JsonMethods._

  implicit val formats = DefaultFormats

  def main(args: Array[String]): Unit = {

    val read = new Read
    val data = Converter.conToJson("file")
    val file = parse(data).extract[File]

    val df = {
      if (file.fileType == "xls") {
        read.data("readXls", file.fileLocation)
      } else if (file.fileType == "csv") {
        read.data("readCsv", file.fileLocation)
      }
      else Seq.empty(3)
    }
    df.show()


  }
}
