package com.ingestion.app

import com.ingestion.addon.Read
import org.json4s.DefaultFormats

object test {

  implicit val formats = DefaultFormats

  def main(args: Array[String]): Unit = {

    val read = new Read

    val df = read.mysql("readMysql")
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

  }
}
