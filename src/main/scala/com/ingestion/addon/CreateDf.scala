package com.ingestion.addon

import com.ingestion.model.CaseData
import org.apache.spark.sql.DataFrame


class CreateDf {
  val connector = new SparkConnecte
  val spark = connector.getSession()

  import spark.implicits._

  def dataJSON(df: DataFrame) = {
    val dz = df.rdd.map(r => {
      val rowAsMap: Map[String, Any] = r.getValuesMap[Any](r.schema.fieldNames)
      val rowAsString: String = Converter.mapToJson(rowAsMap)
      val rowAsSc: CaseData = Converter.jsonToCase(rowAsString)
      rowAsSc
    }).toDF()
    dz
  }

  def dataColumn(df: DataFrame) = {

    val dj = df.rdd.map(r => new CaseData(
      orderDate = r.getString(0),
      region = r.getString(1),
      rep = r.getString(2),
      item = r.getString(3),
      units = r.getString(4),
      unitsCost = r.getFloat(5),
      total = r.getFloat(6)
    )).toDF()
    dj
  }
}
