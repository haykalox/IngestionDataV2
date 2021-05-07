package com.ingestion.app

import com.ingestion.addon.{Converter, Read, SparkConnecte}
import com.ingestion.model.{CaseData, File}
import org.json4s.DefaultFormats

object test {

  import org.json4s.native.JsonMethods._

  implicit val formats = DefaultFormats

  def main(args: Array[String]): Unit = {
    val connector = new SparkConnecte
    val spark = connector.getSession()
    import spark.implicits._
    val read = new Read
    val data = Converter.conToJson("file")
    val file = parse(data).extract[File]

    val df = read.data("readXls", file.fileLocation)
df.show()
    println("----------------------------------")
    /*
val dz = df.map(r=> Sc(
  orderDate = r.getString(0),
  region = r.getString(1),
  rep = r.getString(2),
  item = r.getString(3),
  units = r.getString(4),
  unitsCost = r.getString(5),
  total = r.getString(6)
))
*/
      val dz = df.rdd.map(r => {
      val rowAsMap: Map[String, Any] = r.getValuesMap[Any](r.schema.fieldNames)
      val rowAsString:String= Converter.mapToJson(rowAsMap)
      val rowAsSc: CaseData = Converter.jsonToCase(rowAsString)
      rowAsSc
    }).toDF()

    dz.show(false)
  }
}
