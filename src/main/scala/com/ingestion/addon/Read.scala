package com.ingestion.addon

import com.ingestion.model.ReadData
import org.json4s.DefaultFormats

class Read {
  val connector = new SparkConnecte
  val spark = connector.getSession()

  import org.json4s.native.JsonMethods._

  implicit val formats = DefaultFormats

  def data(types: String, location: String) = {


    val res = Converter.conToJson(types)
    val Sr = parse(res).extract[ReadData]

    spark.read
      .format(Sr.format)
      .options(Sr.options)
      .load(location)
  }

}
