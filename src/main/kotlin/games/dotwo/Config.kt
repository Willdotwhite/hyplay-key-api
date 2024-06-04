package games.dotwo

import io.ktor.server.config.*

// Use object to make Config a singleton reference
// I truly hate that Ktor makes me do this
object Config {
    private lateinit var config: ApplicationConfig

    fun initConfig(_config: ApplicationConfig) {
        config = _config
    }

    /**
     * Get property string value
     */
    fun getString(key: String): String = config.property(key).getString()
}
