import engine.LocationClusterer
import model.LatLng
import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play.PlaySpec
import play.api.Play
import play.api.libs.json.Json
import play.api.Play.current

class LocationClustererSpec extends PlaySpec with MockitoSugar {

    val locationClusterer = new LocationClusterer()

    "The Location Clusterer" should {
        "cluster" in {
            val latlngs = List(
                LatLng(40.7455316, -73.9840987), //work
                LatLng(40.7455326, -73.9840947), //work
                LatLng(40.7455336, -73.9840937), //work
                LatLng(40.7455346, -73.9840987), //work
                LatLng(40.745536, -73.984097), //work
                LatLng(40.74553163, -73.98409873), //work
                LatLng(40.666707, -73.975927), //home
                LatLng(40.666708, -73.975923), //home
                LatLng(40.6667078, -73.975926), //home
                LatLng(40.666709, -73.9759256), //home
                LatLng(40.66670, -73.975925) //home
            )

            val clustered = locationClusterer.cluster(latlngs)

            clustered.size mustBe 2

            val centroidsAndRadii = clustered.map(locationClusterer.centroidAndRadius)

            print(centroidsAndRadii)
        }

        "get the average" in {
            val latlngs = List(
                LatLng(1.0, 1.0),
                LatLng(3.0, 3.0)
            )

            val avg = locationClusterer.getCentroid(latlngs)

            avg mustBe LatLng(2.0, 2.0)
        }

        "get the median" in {
            val latlngs = List(
                LatLng(1.0, 1.0),
                LatLng(1.0, 1.0),
                LatLng(2.0, 2.0)
            )

            val radius = locationClusterer.getRadius(latlngs, LatLng(1.0, 1.0))

            radius mustBe 0.0
        }
    }
}
