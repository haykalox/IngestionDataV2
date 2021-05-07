package com.ingestion.model

import com.fasterxml.jackson.annotation.JsonProperty

case class CaseData(@JsonProperty(value = "OrderDate") orderDate: String,
                    @JsonProperty("Region") region: String,
                    @JsonProperty(value = "Rep", required = false, defaultValue = "ttt") rep: String,
                    @JsonProperty("Item") item: String,
                    @JsonProperty("Units") units: String,
                    @JsonProperty("Unit Cost") unitsCost: Float,
                    @JsonProperty("Total") total: Float

                   )
