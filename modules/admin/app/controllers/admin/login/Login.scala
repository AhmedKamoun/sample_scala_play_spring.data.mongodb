package controllers.admin.login

import com.core.bl.admin.AdministratorService
import com.core.bl.utils.TokenService
import com.core.dal.admin.{AccessPermissionRepository, AdministratorRepository}
import com.core.dom.admin.{Administrator, GroupAdmin}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.{BasicQuery, Criteria}
import org.springframework.data.mongodb.core.query.Criteria._
import org.springframework.data.mongodb.core.query.Query._
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
  var adminRepository: AdministratorRepository = _
  @Autowired
  var accessPermissionRepository: AccessPermissionRepository = _
  @Autowired
  var mongoTemplate: MongoTemplate = _

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
        case None => {

          adminRepository.deleteAll()
          val admin = new Administrator
          admin.email = "ahmed.kamoun@gmail.com"
          admin.name = "Kamoun Ahmed"
          admin.group = new GroupAdmin("G1", "first group")
          adminRepository.save(admin)

          //SEARCH
          var criteria_builder: Criteria = where("")
          //List of predicates => criteria
          criteria_builder.andOperator(where("email").is(admin.email), where("email").exists(true))

          val result = mongoTemplate.findOne(query(criteria_builder), classOf[Administrator])

          Option(result) match {
            case Some(admin) => Logger.debug("Hello! Im " + admin.name)
            case None => Logger.debug("admin not found !")
          }


          Ok(views.html.login.login(login_form))
        }

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