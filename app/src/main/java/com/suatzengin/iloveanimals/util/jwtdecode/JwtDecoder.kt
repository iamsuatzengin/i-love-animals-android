package com.suatzengin.iloveanimals.util.jwtdecode

import android.util.Base64
import kotlinx.serialization.json.Json
import java.nio.charset.StandardCharsets

object JwtDecoder {
    fun decode(token: String): JwtPayload {
        val data =
            String(Base64.decode(token.split(".")[1], Base64.DEFAULT), StandardCharsets.UTF_8)
        return Json.decodeFromString(data)
    }
}
