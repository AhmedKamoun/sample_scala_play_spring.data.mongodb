package com.core.dom.admin

import com.core.dom.MainEntity
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Administrator extends MainEntity {

  var name: String = _

  //@Column(nullable = false, unique = true)
  var email: String = _

  var group: GroupAdmin = _

}
