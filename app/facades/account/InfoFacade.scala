package facades.account

import javax.inject.Singleton

@Singleton
case class InfoFacade() {

  def apply(form: InfoForm): String = "Peerapat A"

}