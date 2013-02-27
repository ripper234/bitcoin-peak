package controllers

import play.api._
import cache.Cache
import play.api.mvc._

object Application extends Controller {
  
  def index = Action {
    val peak = PeakPriceFinder.getCachedPeak
    Ok(views.html.index(peak))
  }
  
}