package model

case class Geometry(`type`: String, coordinates: List[Double])
case class Feature(`type`: String, geometry: Geometry)
case class Features(`type`: String, features: List[Feature])


/**
{
    "type": "FeatureCollection",
    "features": [
        {
            "geometry": {
                "type": "Point",
                "coordinates": [
                    -75.15351422696591,
                    40.0642869592902
                ]
            },
            "type": "Feature",
            "id": 1,
            "properties": {
                "DAY_TIME": "Tu 2-6",
                "NAME": "West Oak Lane",
                "DISTRIBUTE": "Yes",
                "EBT_MACHIN": "Yes",
                "OPERATOR": "The Food Trust",
                "ONLY_REDEE": "No",
                "ADDRESS": "72nd Ave and Ogontz Ave, 19138",
                "ACCEPT_FMN": "Yes",
                "ACCEPT_SNA": "Yes",
                "ZIP_CODE": "19138"
            }
        },
  */