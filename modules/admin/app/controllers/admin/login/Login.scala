package controllers.admin.login

import java.util.{List => JList}

import com.core.dal.admin.AccessPermissionRepository
import com.core.service.admin.AdministratorService
import com.core.service.utils.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype
import play.api.Logger
import play.api.data.Forms._
import play.api.data._
import play.api.mvc.Action
import security.SecuredController

@stereotype.Controller
class Login extends SecuredController {

  @Autowired
  var tokenService: TokenService = _
  @Autowired
  var adminService: AdministratorService = _
  @Autowired
  var accessPermissionRepository: AccessPermissionRepository = _

  val logger: Logger = Logger(this.getClass())

  val login_form = Form(
    tuple(
      "email" -> email,
      "password" -> nonEmptyText

    ) verifying("error login, invalid email or password!", result => result match {
      case (email, password) => adminService.authenticate(email, password)
    })
  )

  def options(all: String) = Action {
    Ok
  }

  def login() = Action {
    implicit request =>
      tokenService.getTokenClaim("admin_id") match {
        case Some(user_id) => Ok("you are already logged in!")
        case None => Ok(views.html.login.login(login_form))

      }

  }

  def submitLogin() = Action {
    implicit request =>
      login_form.bindFromRequest.fold(
        formWithErrors => {
          Logger.warn("login failed !")
          BadRequest(views.html.login.login(formWithErrors))
        }
        ,
        SucceededForm => {
          Logger.debug("login succeeded !")
          val admin = administratorRepository.findByEmail(SucceededForm._1)
          Ok(tokenService.generateToken(Map("admin_id" -> admin.id))) //return token
        }
      )
  }

  def index() = IsAuthenticated {
    admin_id => request =>
      Ok("admin with id " + admin_id + " has a valid token, he can access to this api")
  }

  /**
    * Check API access with authorization
    * @return
    */
  def getAdmin() = IsAuthorized(accessPermissionRepository.findByIdentifier("user-management")) {
    administratorID => implicit request =>

      Ok("Hello admin!")

  }
}