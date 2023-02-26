package agent.trade.plugins

import agent.trade.dto.*
import agent.trade.models.*
import com.google.gson.Gson
import io.ktor.server.sessions.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import javax.naming.AuthenticationException

fun Application.configureSecurity() {
    val sessionTransportTransformer = configureSessionTransportTransformer()

    install(Sessions) {
        cookie<Session>("TradeAgent_Session") {
            cookie.extensions["SameSite"] = "strict"
            cookie.path = "/"
            transform(sessionTransportTransformer)
        }
    }

    routing {

        get("/auth/session") {
            val session = call.sessions.get() ?: Session()
            val response = Session(session.userID)

            call.respond(response)
        }

        post("/auth/login") {
            val request = call.receive<UserLoginDTO>()

            /* attempt to fetch user data with supplied username */
            val user = call.application.getUser(request);

            /* validate the password of the user fetched from the db */
            val hash = call.application.encryptPassword(request.password)
            if (user.getPassword() != hash) throw AuthenticationException("Failed to authenticate password hash")

            /* Register Session */
            val session = call.sessions.get() ?: Session(user.getId())

            call.sessions.set(session.copy(userID = user.getId()))
            call.respond(session)
        }

        post("/auth/register") {
            val request = call.receive<UserRegistrationDTO>()

            /* Register user in Database */
            val registeredUser = call.application.insertUser(request)

            /* Register user to Session */
            val session = call.sessions.get() ?: Session(registeredUser.getId())

            call.sessions.set(session.copy(userID = registeredUser.getId()))
            call.respond(session)
        }
    }
}
