package com.example.FlightApplication.DataClass

import com.google.gson.annotations.SerializedName

data class FlightOfferResponse(
    @SerializedName("meta") val metadata: MetaData2,
    val data: List<FlightOffer>,
    val dictionaries: Dictionaries
)

data class MetaData2(
    val count: Int
)

data class FlightOffer(
    val type: String,
    val id: String,
    val source: String,
    val instantTicketingRequired: Boolean,
    val nonHomogeneous: Boolean,
    val oneWay: Boolean,
    val lastTicketingDate: String,
    val numberOfBookableSeats: Int,
    val itineraries: List<Itinerary>,
    val price: Price,
    val pricingOptions: PricingOptions,
    val validatingAirlineCodes: List<String>,
    val travelerPricings: List<TravelerPricing>
)

data class Itinerary(
    val duration: String,
    val segments: List<Segment>
)

data class Segment(
    val departure: AirportEvent,
    val arrival: AirportEvent,
    val carrierCode: String,
    val number: String,
    val aircraft: Aircraft,
    val operating: OperatingCarrier,
    val duration: String,
    val id: String,
    val numberOfStops: Int,
    val blacklistedInEU: Boolean
)

data class AirportEvent(
    val iataCode: String,
    val at: String
)

data class Aircraft(
    val code: String
)

data class OperatingCarrier(
    val carrierCode: String
)

data class Price(
    val currency: String,
    val total: String,
    val base: String,
    val fees: List<Fee>,
    val grandTotal: String
)

data class Fee(
    val amount: String,
    val type: String
)

data class PricingOptions(
    val fareType: List<String>,
    val includedCheckedBagsOnly: Boolean
)

data class TravelerPricing(
    val travelerId: String,
    val fareOption: String,
    val travelerType: String,
    val price: TravelerPrice,
    val fareDetailsBySegment: List<FareDetailsBySegment>
)

data class TravelerPrice(
    val currency: String,
    val total: String,
    val base: String
)

data class FareDetailsBySegment(
    val segmentId: String,
    val cabin: String,
    val fareBasis: String,
    val `class`: String,
    val includedCheckedBags: IncludedCheckedBags
)

data class IncludedCheckedBags(
    val quantity: Int
)

data class Dictionaries(
    val locations: Map<String, Location>,
    val aircraft: Map<String, String>,
    val currencies: Map<String, String>,
    val carriers: Map<String, String>
)

data class Location(
    val cityCode: String,
    val countryCode: String
)