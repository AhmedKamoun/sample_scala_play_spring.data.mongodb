package com.core.dal.admin

import com.core.dom.admin.{AccessPermission, Administrator}
import org.springframework.data.mongodb.repository.{MongoRepository, Query}
import org.springframework.stereotype.Repository

@Repository
trait AdministratorRepository extends MongoRepository[Administrator, String] {

  @Query("{ 'email' : ?0 }")
  def findByEmail(email: String): Administrator

  //@Query("SELECT ca FROM Administrator ca  WHERE id = :id AND ca.group IN (SELECT DISTINCT gap.group FROM GroupAdminPermission gap WHERE permission = :permission )")
  @Query("{ 'id' : ?0 , 'group' : ?1}")
  def findByPermission(id: String, permission: AccessPermission): Administrator

  //@Query("SELECT ca FROM Administrator ca  WHERE id = :id AND ca.group IN (SELECT DISTINCT gap.group FROM GroupAdminPermission gap WHERE permission = :permission AND  isAdmin = :isAdmin  )")
  @Query("{ 'id' : ?0 , 'group' : ?1 , 'isadmin' : ?2 }")
  def findByPermissionIsAdmin(id: String, permission: AccessPermission, isAdmin: Boolean): Administrator


}
