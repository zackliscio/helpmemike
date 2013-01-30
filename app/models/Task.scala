package models

// Required for Anorm mapping capability
import anorm._
import anorm.SqlParser._

// Required for the Play db functionality
import play.api.db._
import play.api.Play.current
import play.api.data.Forms._

case class Task(id: Long, label: String, description: String)
case class TaskForm(label: String, description: Form)

object Task {
  // Parser for mapping JDBC ResultSet to a single entity of Task model
  val task = {
    get[Long]("id") ~
      get[String]("label") ~
        get[String]("description") map {
      case id~label~description => Task(id, label, description)
    }
  }

  def all(): List[Task] = DB.withConnection { implicit c =>
    SQL("select * from task").as(task *)
  }

 /* def create(label: String) {
    DB.withConnection { implicit c =>
      SQL("insert into task (label) values ({label})").on(
        'label -> label
      ).executeUpdate()
    }
  }*/

  taskForm: Form[TaskForm] // with the case class defined above


  def create(label: String, description: String) {
    DB.withConnection { implicit c =>
      SQL("insert into task (label, description) values ({label}, {description})").on(
        'label -> label,
        'description -> description
      ).executeUpdate()
    }
  }

  def delete(id: Long) {
    DB.withConnection { implicit c =>
      SQL("delete from task where id = {id}").on(
        'id -> id
      ).executeUpdate()
    }
  }

}