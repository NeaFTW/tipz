import com.tipz.app._
import org.scalatra._
import javax.servlet.ServletContext

import com.tipz.app.MyScalatraServlet
import controllers.{ParticipateController, ProjectController, SessionController}

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(new MyScalatraServlet, "/*")
    context.mount(new ParticipateController, "/participate/*")
    context.mount(new ProjectController, "/project/*")
    context.mount(new SessionController, "/session/*")
  }
}
