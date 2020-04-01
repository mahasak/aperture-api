package facades.account

import javax.inject.Singleton

@Singleton
case class SigninFacade() {

  def apply(form: SigninForm): String = "Peerapat A"

}