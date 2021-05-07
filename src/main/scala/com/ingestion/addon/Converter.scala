package com.ingestion.addon

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.ingestion.model.CaseData
import com.typesafe.config.ConfigFactory

import scala.collection.JavaConversions.mapAsScalaMap

object Converter {
  val config = ConfigFactory.load("application.conf")
  val mapper = new ObjectMapper() with ScalaObjectMapper
  mapper.registerModule(DefaultScalaModule)

  def conToJson(app: String): String = {
    val data: Map[String, AnyRef] = config.getObject(app).map({ case (k, v) => (k, v.unwrapped()) }).toMap
    mapToJson(data)
  }

  def mapToJson(app: Map[String, Any]): String = {
    mapper.writeValueAsString(app)
  }

  def jsonToCase(app: String): CaseData = {
    mapper.readValue[CaseData](app)
  }
}
