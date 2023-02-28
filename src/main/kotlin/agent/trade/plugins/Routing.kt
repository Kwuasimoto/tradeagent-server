package agent.trade.plugins

import agent.trade.dto.*
import agent.trade.models.TWSClientConfigurationTable
import agent.trade.models.getImage
import agent.trade.models.getImages
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Application.configureRouting() {
    configureClientRouting();
    configureResourceRoutes();
    configureSecurityRoutes();
    configureSeleniumRoutes();
}

fun Application.configureResourceRoutes() {

    routing {
        get("resources/img/{imageName}") {
            val imageName = call.parameters["imageName"]

            /* Attempt to fetch image from database by image name */
            val image = application.getImage(imageName!!)

            application.log.info("Fetched Image ${image.size}")

            /* Send image to client */
            call.response.headers.append("Content-Type", "image/png")
            call.respondBytes(image)
        }
        post("resources/images") {
            val request = call.receive<BatchImageResourceRequestDTO>()

            /* Get the images as a mutableList<ByteArray> and map to Base64 Strings */
            val images = application.getImages(request.getImageNames())

            /* Send list of images back to client */
            call.respond(images)
        }
    }
}

fun Application.configureClientRouting() {
    routing {
        /* create unique post routes for handling each configuration type */
        post("configurations/client") {
            /* Get the user in the session */
            val user = call.sessions.get() ?: Session()

            /* no userID in session? return */
            if (user.userID === null) {
                application.log.warn("No [userID] found in session")
                return@post call.respond("No [userID] found in session")
            }

            /* get request data */
            val request = call.receive<ClientConfigurationRequestDTO>()

            /* Infer the data type based on the type passed in the request  */
            when (MarketClientProviders.values().getOrNull(request.configType.toInt())) {
                MarketClientProviders.TWS -> {
                    /* Save client configuration to specific table based on ordinal */
                    val data = TWSClientConfigurationRequestDTO(request)

                    /* Determine if insert was successful and respond to client */
                    val result = TWSClientConfigurationTable.insertClientConfigurationRequestDTO(data);

                    if (result.insertedCount > 0) {
                        call.respond("Successfully inserted client configuration")
                    } else {
                        call.respond("Failed to insert client configuration")
                    }

                }

                else -> {
                    call.respond("Could not infer client configuration type from ordinal")
                }

            }
        }
        get("configurations/client") {

        }
    }
}