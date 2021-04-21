package dependencies

object Modules {

    const val main = ":main"

    object Features {

    }

    object Library {
        object Network {
            const val public = ":library:network:public"
            const val impl = ":library:network:impl"
        }
    }
}
