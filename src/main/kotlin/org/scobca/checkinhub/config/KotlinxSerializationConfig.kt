package org.scobca.checkinhub.config

import kotlinx.serialization.json.Json
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.http.codec.json.KotlinSerializationJsonDecoder
import org.springframework.http.codec.json.KotlinSerializationJsonEncoder
import org.springframework.web.reactive.config.WebFluxConfigurer

/**
 * Spring WebFlux configuration class implementing [WebFluxConfigurer] for Kotlinx Serialization.
 *
 * This configuration replaces the default Jackson codecs with Kotlinx Serialization-based codecs
 * to handle JSON serialization and deserialization in the reactive WebFlux stack.
 */
@Configuration
class KotlinxSerializationConfig : WebFluxConfigurer {

    /**
     * Configures custom JSON encoders and decoders for WebFlux using Kotlinx Serialization.
     *
     * A customized [Json] instance is used with the following settings:
     * - `ignoreUnknownKeys = true`: Ignores unknown fields in incoming JSON payloads.
     * - `encodeDefaults = true`: Includes default property values when serializing.
     * - `prettyPrint = true`: Pretty-prints JSON output for readability.
     *
     * The corresponding codecs are registered in the WebFlux [ServerCodecConfigurer].
     *
     * @param configurer The [ServerCodecConfigurer] used to register custom codecs.
     */
    override fun configureHttpMessageCodecs(configurer: ServerCodecConfigurer) {
        val json = Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
            prettyPrint = true
        }

        val decoder = KotlinSerializationJsonDecoder(json)
        val encoder = KotlinSerializationJsonEncoder(json)

        configurer.customCodecs().register(decoder)
        configurer.customCodecs().register(encoder)
    }
}
