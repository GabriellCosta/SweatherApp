package dependencies

object Modules {

    const val main = ":main"

    object Features {
        object WeatherView {
            const val public = ":feature:weather-view:public"
            const val impl = ":feature:weather-view:impl"
        }
    }

    object Library {
        object Network {
            const val public = ":library:network:public"
            const val impl = ":library:network:impl"
        }
    }
}
