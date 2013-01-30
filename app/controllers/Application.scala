package controllers

import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._
import models._

object Application extends Controller {

  case class TaskForm(label:String, description:String)
  val taskForm2 = Form(
      mapping(
        "label" -> nonEmptyText,
        "description" -> nonEmptyText
      )(TaskForm.apply)(TaskForm.unapply)
    )
  val taskForm = Form(
    tuple(
      "label" -> nonEmptyText,
      "description" -> nonEmptyText
    )
  )

  def index = Action {
    Redirect(routes.Application.tasks)
  }

  def tasks = Action {
    Ok(views.html.index(Task.all(), taskForm2))
  }

  // def newTask = Action { implicit request =>
  // // with case class
  //   taskForm.bindFromRequest.fold(
  //     errors => BadRequest(views.html.index(Task.all(), errors)),
  //     {
  //       case (label, description) =>{
  //         Task.create( label, description)
  //         Redirect(routes.Application.tasks)
  //       }
  //     }
      
  //   )
  // }

  def newTask = Action { implicit request =>
  // with case class
    taskForm2.bindFromRequest.fold(
      errors => BadRequest(views.html.index(Task.all(), errors)),
      {
        case form:TaskForm =>{
          Task.create( form.label, form.description)
          Redirect(routes.Application.tasks)
        }
      }
      
    )
  }




  def deleteTask(id: Long) = Action { implicit request =>
    Task.delete(id)
    Redirect(routes.Application.tasks)
  }











}