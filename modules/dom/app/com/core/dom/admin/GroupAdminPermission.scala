package com.core.dom.admin

import com.core.dom.MainEntity
import org.springframework.data.mongodb.core.mapping.Document

@Document
class GroupAdminPermission extends MainEntity {

  //@ManyToOne
  //@JoinColumn(nullable = false)
  var group: GroupAdmin = _

  //@ManyToOne
  //@JoinColumn(nullable = false)
  var permission: AccessPermission = _

  var isAdmin: Boolean = _

}
