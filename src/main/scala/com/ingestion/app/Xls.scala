package com.ingestion.app

import com.ingestion.addon.{Converter, Read, SparkConnecte}
import com.ingestion.model.{CaseData, File}
import org.json4s.DefaultFormats

object Xls {

  import org.json4s.native.JsonMethods._

  implicit val formats = DefaultFormats

  def main(args: Array[String]): Unit = {

    val connector = new SparkConnecte
    val spark = connector.getSession()
    import spark.implicits._

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
    val dz = df.rdd.map(r => {
      val rowAsMap: Map[String, Any] = r.getValuesMap[Any](r.schema.fieldNames)
      val rowAsString:String= Converter.mapToJson(rowAsMap)
      val rowAsSc: CaseData = Converter.jsonToCase(rowAsString)
      rowAsSc
    }).toDF()
dz.show()

  }
}
