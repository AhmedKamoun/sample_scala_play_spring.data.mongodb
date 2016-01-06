package com.core.bl.admin

import java.util.{List => JList}

import com.core.dal.admin.AdministratorRepository
import com.core.dom.admin.{AccessPermission, Administrator}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AdministratorService {

  @Autowired
  var administratorRepository: AdministratorRepository = _

  def hasPermission(permission: AccessPermission)(implicit me: Administrator): Boolean = {
    //TODO
    true
  }

  def authenticate(email: String, password: String): Boolean = {
    // check if user exist
    Option(administratorRepository.findByEmail(email)) match {
      case Some(user) => {
        //NOTE TO KEEP LOGIN SIMPLE WE DO NOT CHECK FOR PASSWORD
        true
      }
      case None => false
    }
  }
}