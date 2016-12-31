import com.tipz.app._
import org.scalatra._
import javax.servlet.ServletContext

import com.tipz.app.MyScalatraServlet
import controllers.{ParticipateController, ProjectController, SessionController}
import models.Account

class ScalatraBootstrap extends LifeCycle {
  /**
    * Crestion of the controller route and url
    * @param context
    */
  override def init(context: ServletContext) {
    context.mount(new MyScalatraServlet, "/*")
    context.mount(new ParticipateController, "/participate/*")
    context.mount(new ProjectController, "/project/*")
    context.mount(new SessionController, "/session/*")
  }
}
