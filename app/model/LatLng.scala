package model

case class LatLng(lat: Double, lng: Double) {
    def distance(other: LatLng) = math.sqrt(math.pow(lat - other.lat, 2) + math.pow(lng - other.lng, 2))
}
