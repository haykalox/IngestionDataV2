package com.ingestion.addon

import org.apache.spark.sql.SparkSession

class SparkConnecte {
  def getSession() = {
    SparkSession
      .builder()
      .appName("Ingestion")
      .config("spark.master", "local")
      .enableHiveSupport()
      .getOrCreate()

  }
}
