package controllers

import com.typesafe.scalalogging.LazyLogging
import facades.account.{InfoFacade, InfoForm, SigninFacade, SigninForm}
import javax.inject._
import play.api.mvc.Action
import yoda.security.mvc.RESTController

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class AccountController @Inject()(signin: SigninFacade, info: InfoFacade) extends RESTController
  with LazyLogging {

  def info: Action[String] = action { implicit request =>
    request.toOption[InfoForm]
      .map(f => info(f))
      .map(js => okJSon(js)(request))
      .get
  }

  def signin: Action[String] = action { implicit request =>
    request.toOption[SigninForm]
      .map(f => signin(f))
      .map(js => okJSon(js)(request))
      .get
  }

}
