package agent.trade.dto

import kotlinx.serialization.Serializable

@Serializable
data class Session(val userID: String? = null)

@Serializable
data class UserLoginDTO(val username: String, val password: String)

@Serializable
data class UserRegistrationDTO(val username: String, val password: String, val email: String)
