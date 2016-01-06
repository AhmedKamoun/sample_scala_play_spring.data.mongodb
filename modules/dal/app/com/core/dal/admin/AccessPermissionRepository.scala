package com.core.dal.admin

import com.core.dom.admin.AccessPermission
import org.springframework.data.mongodb.repository.{MongoRepository, Query}
import org.springframework.stereotype.Repository

@Repository
trait AccessPermissionRepository extends MongoRepository[AccessPermission, String] {

  @Query("{ 'identifier' : ?0 }")
  def findByIdentifier(identifier: String): AccessPermission


}
