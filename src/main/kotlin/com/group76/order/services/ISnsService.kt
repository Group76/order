package com.group76.order.services

import com.group76.order.entities.request.OrderCancelledMessageSnsRequest
import com.group76.order.entities.request.OrderMessageSnsRequest
import software.amazon.awssdk.http.SdkHttpResponse

interface ISnsService {
    fun publishMessage(
        topicArn: String,
        message: Any,
        subject: String,
        id: String
    ): SdkHttpResponse

    fun getTopicArnByName(
        topicName: String
    ): String?
}