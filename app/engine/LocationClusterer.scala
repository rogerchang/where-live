package engine

import net.sf.javaml.clustering.{KMeans, DensityBasedSpatialClustering}
import net.sf.javaml.distance.{EuclideanDistance, NormalizedEuclideanDistance}

import scala.collection.JavaConversions._
import model.{CentroidAndRadius, LatLng}
import net.sf.javaml.core.{DefaultDataset, Dataset, DenseInstance}

class LocationClusterer {

    def cluster(points: List[LatLng]): List[Dataset] = {
        val instances = points.map { point =>
            val latlng = Array[Double](point.lat, point.lng)
            new DenseInstance(latlng)
        }

//        val clusterer = new DensityBasedSpatialClustering(0.6, 6, new EuclideanDistance())
        val clusterer = new KMeans(20)

        clusterer.cluster(new DefaultDataset(instances)).toList
    }

    private def extractLatLngs(dataset: Dataset): List[LatLng] = {
        dataset.iterator().map{data => LatLng(data.value(0), data.value(1))}.toList
    }

    def centroidAndRadius(dataset: Dataset) = {
        val latlngs = extractLatLngs(dataset)
        val centroid = getCentroid(latlngs)
        val radius = getRadius(latlngs, centroid)

        CentroidAndRadius(centroid, radius)
    }

    def getCentroid(points: List[LatLng]) = {
        var latXTotal = 0.0
        var latYTotal = 0.0
        var lonDegreesTotal = 0.0

        points.foreach{point =>
            val latDegrees = point.lat
            val lonDegrees = point.lng

            val latRadians = Math.PI * latDegrees / 180
            latXTotal += Math.cos(latRadians)
            latYTotal += Math.sin(latRadians)

            lonDegreesTotal += lonDegrees
        }

        val finalLatRadians = Math.atan2(latYTotal, latXTotal)
        val finalLatDegrees = finalLatRadians * 180 / Math.PI

        val finalLonDegrees = lonDegreesTotal / points.size

        val finalLat = average(points.map(_.lat))
        val finalLon = average(points.map(_.lng))

        LatLng(finalLat, finalLon)
    }

    private def average(list: List[Double]) = list.foldLeft(0.0)(_+_) / list.foldLeft(0.0)((r,c) => r+1)

    private def median(list: List[Double]) = {
        val length = list.size
        val sortedList = list.sorted.toList
        if (length % 2 == 0)
            sortedList.get((length+1)/2)
        else
            sortedList.get(length/2)
    }

    def getRadius(points: List[LatLng], centroid: LatLng) = {
        //get median. then get distance from centroid to median
        val medianLat = median(points.map(_.lat))
        val medianLng = median(points.map(_.lng))

        Haversine.distanceInMeters(centroid, LatLng(medianLat, medianLng))
    }
}
