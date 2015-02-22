package controllers

import model._
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.libs.json.{Writes, JsPath, Reads}

trait ImplicitJsonConversions {

    implicit val latlngReads: Reads[LatLng] = (
            (JsPath \ "lat").read[Double] and
                    (JsPath \ "lng").read[Double]
            )(LatLng.apply _)

    implicit val facetReads: Reads[Facet] = (
            (JsPath \ "id").read[String] and
                    (JsPath \ "name").read[String] and
                    (JsPath \ "description").read[String] and
                    (JsPath \ "latlngs").readNullable[List[LatLng]]
            )(Facet.apply _)

    implicit val geometryReads: Reads[Geometry] = (
            (JsPath \ "type").read[String] and
                    (JsPath \ "coordinates").read[List[Double]]
            )(Geometry.apply _)

    implicit val featureReads: Reads[Feature] = (
            (JsPath \ "type").read[String] and
                    (JsPath \ "geometry").read[Geometry]
            )(Feature.apply _)

    implicit val featuresReads: Reads[Features] = (
            (JsPath \ "type").read[String] and
                    (JsPath \ "features").read[List[Feature]]
            )(Features.apply _)

    implicit val latlngWrites: Writes[LatLng] = (
            (JsPath \ "lat").write[Double] and
                    (JsPath \ "lng").write[Double]
            )(unlift(LatLng.unapply))

    implicit val centroidAndRadiusWrites: Writes[CentroidAndRadius] = (
            (JsPath \ "centroid").write[LatLng] and
                    (JsPath \ "radius").write[Double]
            )(unlift(CentroidAndRadius.unapply))

    implicit val facetWrites: Writes[Facet] = (
            (JsPath \ "id").write[String] and
                    (JsPath \ "name").write[String] and
                    (JsPath \ "description").write[String] and
                    (JsPath \ "latlngs").writeNullable[List[LatLng]]
            )(unlift(Facet.unapply))
}
