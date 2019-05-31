package de.bringmeister.domain.entity

/*
Holds the data for product prices.

As you can see I'm not using an enum for neither currency nor unit. It would probably be a good fit,
but my thinking was that this data is probably coming from another microservice.

Therefore this microservice would be responsible to validate the values of these properties.
 */
data class Price(val sku: String, val value: Double, val currency: String, val unit: String)