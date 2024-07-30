package com.group76.order.configuration.feign.decoder

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.group76.order.configuration.feign.exception.*
import feign.Response
import feign.codec.ErrorDecoder

class FeignExceptionDecoder : ErrorDecoder {
    override fun decode(methodKey: String?, response: Response): Exception {
        var gatewayMessage: String = response.status().toString()
        if(response.body() != null){
            val bufferedReader = response.body().asInputStream().bufferedReader()
            val stringBuilder = StringBuilder()
            var line: String?
            try {
                while (bufferedReader.readLine().also { line = it } != null) {
                    stringBuilder.append(line).append('\n')
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                bufferedReader.close()
            }

            val feignResponse = stringBuilder.toString()

            gatewayMessage = try {
                ObjectMapper().readValue(feignResponse, ExceptionMessage::class.java).message
            } catch (ex: JsonMappingException) {
                "Failed to retrieve gateway message: $feignResponse"
            }
        }

        return when (response.status()) {
            400 -> BadRequestException(gatewayMessage)
            404 -> NotFoundException(gatewayMessage)
            502 -> BadGatewayException(gatewayMessage)
            409 -> ConflictException(gatewayMessage)
            500 -> InternalServerErrorException(gatewayMessage)
            else -> RuntimeException("Exception while calling API: ${response.status()} - $gatewayMessage")
        }
    }
}