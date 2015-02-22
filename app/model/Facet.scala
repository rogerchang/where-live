package model

case class Facet(id: String, name: String, description: String, latLngs:Option[List[LatLng]] = None)
