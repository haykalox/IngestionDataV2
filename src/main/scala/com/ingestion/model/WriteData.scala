package com.ingestion.model

case class WriteData(format:String,
                     mode:String,
                     options:Map[String,String],
                     location:String,
                     TbName:String

                    )
