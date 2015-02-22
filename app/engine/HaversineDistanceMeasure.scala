package engine

import model.LatLng
import net.sf.javaml.core.Instance
import net.sf.javaml.distance.DistanceMeasure

class HaversineDistanceMeasure extends DistanceMeasure {

    override def measure(x: Instance, y: Instance): Double = Haversine.distanceInMeters(LatLng(x.value(0), x.value(1)), LatLng(y.value(0), y.value(1)))

    override def getMinValue: Double = 0

    override def getMaxValue: Double = Double.MaxValue

    override def compare(x: Double, y: Double): Boolean = x > y
}
