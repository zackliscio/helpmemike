package models

import anorm._
import anorm.SqlParser._
import play.api._
import play.api.mvc._
import play.api.db._
import play.api.Play.current
import play.api.libs.ws.WS
import play.api.libs.json


case class FacebookInfo(id: Long, fb_id: Long, fb_response: String, fb_name: String, fb_cover_url: String, fb_cover_offset_y: Long, fb_mission: String, fb_website: String, fb_year_founded: String, fb_link: String, fb_likes: Long)

object FacebookInfo {

  val facebookInfo = {
    get[Long]("id") ~
      get[Long]("fb_id") ~
        get[String]("fb_response") ~
          get[String]("fb_name") ~
            get[String]("fb_cover_url") ~
              get[Long]("fb_cover_offset_y") ~
                get[String]("fb_mission") ~
                  get[String]("fb_website") ~
                    get[String]("fb_year_founded") ~
                      get[String]("fb_link") ~
                        get[Long]("fb_likes") map {
      case id~fb_id~fb_response~fb_name~fb_cover_url~fb_cover_offset_y~fb_mission~fb_website~fb_year_founded~fb_link~fb_likes => FacebookInfo(id, fb_id, fb_response, fb_name, fb_cover_url, fb_cover_offset_y, fb_mission, fb_website, fb_year_founded, fb_link, fb_likes)
    }
  }

  def all(): List[FacebookInfo] = DB.withConnection { implicit c =>
    SQL("select * from facebookInfo").as(facebookInfo *)
  }

  def create(fb_id: Long, fb_response: String, fb_name: String, fb_cover_url: String, fb_cover_offset_y: Long, fb_mission: String, fb_website: String, fb_year_founded: String, fb_link: String, fb_likes: Long) {
    DB.withConnection { implicit c =>
      SQL("insert into facebookInfo (fb_id, fb_response, fb_name, fb_cover_url, fb_cover_offset_y, fb_mission, fb_website, fb_year_founded, fb_link, fb_likes) values ({fb_id}, {fb_response}, {fb_name}, {fb_cover_url}, {fb_cover_offset_y}, {fb_mission}, {fb_website}, {fb_year_founded},{fb_link},{fb_likes})").on(
        'fb_id -> fb_id,
        'fb_response -> fb_response,
        'fb_name -> fb_name,
        'fb_cover_url -> fb_cover_url,
        'fb_cover_offset_y -> fb_cover_offset_y,
        'fb_mission -> fb_mission,
        'fb_website -> fb_website,
        'fb_year_founded -> fb_year_founded,
        'fb_link -> fb_link,
        'fb_likes -> fb_likes
      ).executeUpdate()
    }
  }

  def delete(id: Long) {
    DB.withConnection { implicit c =>
      SQL("delete from facebookInfo where id = {id}").on(
        'id -> id
      ).executeUpdate()
    }
  }

}