package engine

import model.LatLng

object Haversine {

    val EARTH_RADIUS_IN_METERS = 6369628.75

    def distanceInMeters(latLng1: LatLng, latLng2: LatLng) = {
        val dLat = Math.toRadians(latLng2.lat - latLng1.lat)
        val dLng = Math.toRadians(latLng2.lng - latLng1.lng)

        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(latLng1.lat)) *
                        Math.cos(Math.toRadians(latLng2.lat)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

        EARTH_RADIUS_IN_METERS * c
    }
}
