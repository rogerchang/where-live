package controllers

import engine.LocationClusterer
import model.{Features, Facet, LatLng}
import play.api._
import play.api.libs.json._
import play.api.Play.current
import play.api.mvc._

object Application extends Controller with ImplicitJsonConversions {

    val file = Play.getFile("/public/data/facets.json")
    val facetsJson = Json.parse(scala.io.Source.fromFile(file).mkString)

    val file2 = Play.getFile("/public/data/test.json")
    val facetsJson2 = Json.parse(scala.io.Source.fromFile(file2).mkString)

    def index = Action {
        Ok(views.html.index("Your new application is ready."))
    }

    def cluster(facets: String) = Action {

        val fs = facets.split(",")
        val f = Application.facetsJson.as[List[Facet]]
        val filtered = f.filter(facet => fs.contains(facet.id))
        val latlngs = filtered.foldLeft(List[LatLng]())((b, a) => a.latLngs.get ::: b)
        val locationClusterer = new LocationClusterer

        val clustered = locationClusterer.cluster(latlngs)
        val centroidsAndRadii = clustered.map(locationClusterer.centroidAndRadius)

        val json = Json.toJson(Map("data" -> centroidsAndRadii))
        Ok(json)
    }

    def transform = Action {
        val latlngs = Application.facetsJson2.as[Features].features.map(feature => LatLng(feature.geometry.coordinates(0), feature.geometry.coordinates(1)))
        val latlngsJson = Json.toJson(latlngs)
        Ok(latlngsJson)
    }

    def facets = Action {
        val f = Application.facetsJson.as[List[Facet]].map(facet => Facet(facet.id, facet.name, facet.description))
        val json = Json.toJson(Map("facets" -> f))
        Ok(json)
    }
}