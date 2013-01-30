package controllers

import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._
import models._

object Application extends Controller {


  val taskForm = Form(
    "label" -> nonEmptyText,
    "description" -> nonEmptyText
  )

  def index = Action {
    Redirect(routes.Application.tasks)
  }

  def tasks = Action {
    Ok(views.html.index(Task.all(), taskForm))
  }

  def newTask = Action { implicit request =>
  // with case class
    taskForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index(Task.all(), errors)),
      form => { Task.create(Form.label, Form.description) }
    )
  }



  def deleteTask(id: Long) = Action { implicit request =>
    Task.delete(id)
    Redirect(routes.Application.tasks)
  }











}