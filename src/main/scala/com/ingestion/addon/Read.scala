package com.ingestion.addon

import com.ingestion.model.ReadData
import org.json4s.DefaultFormats

class Read {
  val connector = new SparkConnecte
  val spark = connector.getSession()

  import org.json4s.native.JsonMethods._

  implicit val formats = DefaultFormats

  def data(app: String, location: String) = {


    val res = Converter.conToJson(app)
    val Sr = parse(res).extract[ReadData]

    spark.read
      .format(Sr.format)
      .options(Sr.options)
      .load(location)
  }

  def mysql(app: String) = {
    val res = Converter.conToJson(app)
    val Sr = parse(res).extract[ReadData]

    spark.read
      .format(Sr.format)
      .options(Sr.options)
  }

}
