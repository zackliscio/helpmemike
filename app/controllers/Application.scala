package controllers

import play.api._
import play.api.mvc._
import play.api.libs.ws.WS
import play.api.data._
import play.api.data.Forms._
import play.api.libs.json._

import models._

object Application extends Controller {


  val fbForm = Form(
      "fb_id" -> longNumber
  )

  def index = Action {
    Redirect(routes.Application.facebookInfo)
  }

  def facebookInfo = Action {

        Ok(views.html.index(FacebookInfo.all(),fbForm))

  }

  def newFacebookInfo = Action { implicit request =>
  // with case class

    fbForm.bindFromRequest.fold(
    errors => BadRequest(views.html.index(FacebookInfo.all(), errors)),
    {
      case (fb_id) =>{
        val promise = WS.url("http://graph.facebook.com/"+fb_id).get()
        promise.onRedeem{response =>
          val fb_response = (response.json \ "username").as[String];
          val fb_name = (response.json \ "name").as[String]
          val fb_cover_url = (response.json \ "cover" \ "source").as[String]
          val fb_cover_offset_y = (response.json \ "cover" \ "offset_y").as[Long]
          val fb_mission = (response.json \ "mission").as[String]
          val fb_website = (response.json \ "website").as[String]
          val fb_year_founded = (response.json \ "founded").as[String]
          val fb_link = (response.json \ "link").as[String]
          val fb_likes = (response.json \ "likes").as[Long]
          FacebookInfo.create( fb_id, fb_response, fb_name, fb_cover_url, fb_cover_offset_y, fb_mission, fb_website, fb_year_founded, fb_link, fb_likes)

        }

        Redirect(routes.Application.facebookInfo)

      }
    }

    )
  }

  def deleteFacebookInfo(id: Long) = Action { implicit request =>
    FacebookInfo.delete(id)
    Redirect(routes.Application.facebookInfo)
  }

}