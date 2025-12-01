package com.example.FlightApplication.DataClass

data class FlightSearchRequest(
    val currencyCode: String,
    val originDestinations: List<OriginDestination>,
    val travelers: List<Traveler>,
    val sources: List<String>,
    val searchCriteria: SearchCriteria
)

data class OriginDestination(
    val id: String,
    val originLocationCode: String,
    val destinationLocationCode: String,
    val departureDateTimeRange: DepartureDateTimeRange
)

data class DepartureDateTimeRange(
    val date: String,
    val time: String
)

data class Traveler(
    val id: String,
    val travelerType: String
)

data class SearchCriteria(
    val maxFlightOffers: Int,
    val flightFilters: FlightFilters
)

data class FlightFilters(
    val cabinRestrictions: List<CabinRestriction>
)

data class CabinRestriction(
    val cabin: String,
    val coverage: String,
    val originDestinationIds: List<String>
)