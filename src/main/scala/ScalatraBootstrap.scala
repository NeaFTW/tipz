import com.tipz.app._
import org.scalatra._
import javax.servlet.ServletContext

import com.tipz.app.MyScalatraServlet
import controllers.{ParticipateController, ProjectController, SessionController}
import models.Account

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {

    println("------------------- Database ---------------------")
    val test = new Account()
    println("++++ Find All Account ++++")
    println(test.findAllAccount())
    println(test.findAllAccount().size)
    println("++++ Find By Email ++++ - zheng_v@epita.fr")
    println(test.findByEmail("zheng_v@epita.fr"))
    println(if (test.findByEmail("zheng_v@epita.fr").size > 0) test.findByEmail("zheng_v@epita.fr")(0))
    test.closeConnection()
    println("------------------- Database END -----------------")

    println("------------------- Database ---------------------")

    println("------------------- Database END -----------------")

    context.mount(new MyScalatraServlet, "/*")
    context.mount(new ParticipateController, "/participate/*")
    context.mount(new ProjectController, "/project/*")
    context.mount(new SessionController, "/session/*")
  }
}
