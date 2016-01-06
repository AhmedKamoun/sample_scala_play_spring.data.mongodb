package com.core.dom.admin

import com.core.dom.MainEntity
import org.springframework.data.mongodb.core.mapping.Document

@Document
class GroupAdmin extends MainEntity {

  // @Column(unique = true)
  var name: String = _

  var description: String = _

  def this(name_ : String, description_ : String) {
    this()
    name = name_
    description = description_

  }
}
