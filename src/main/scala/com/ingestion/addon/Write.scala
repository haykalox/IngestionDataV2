package com.ingestion.addon

import com.ingestion.model.CaseData
import org.apache.spark.sql.DataFrame


class Write {

  def dataJSON(df:DataFrame) = {

    val dj=df.rdd.map(r => new CaseData(
      orderDate = r.getString(0),
      region = r.getString(1),
      rep = r.getString(2),
      item = r.getString(3),
      units = r.getString(4),
      unitsCost = r.getFloat(5),
      total = r.getFloat(6)
    ))

  }
}
