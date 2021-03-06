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
          val fb_response = (response.json \ "about").as[Option[String]].getOrElse("none");
          val fb_name = (response.json \ "name").as[Option[String]].getOrElse("none");
          val fb_cover_url = (response.json \ "cover" \ "source").as[Option[String]].getOrElse("none");
          val fb_cover_offset_y = (response.json \ "cover" \ "offset_y").as[Long];
          val fb_mission = (response.json \ "mission").as[Option[String]].getOrElse("none");
          val fb_website = (response.json \ "website").as[Option[String]].getOrElse("none");
          val fb_year_founded = (response.json \ "founded").as[Option[String]].getOrElse("none");
          val fb_link = (response.json \ "link").as[Option[String]].getOrElse("none");
          val fb_likes = (response.json \ "likes").as[Long];

//          val fb_response = "fake response"
//          val fb_name = "fake name"
//          val fb_cover_url = "fake url"
//          val fb_cover_offset_y = 12
//          val fb_mission = "fake mission"
//          val fb_website = "fake website"
//          val fb_year_founded = "fake year"
//          val fb_link = "fake fb link"
//          val fb_likes = 250


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