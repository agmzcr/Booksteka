package dev.agmzcr.booksteka.data.model

data class MangasResponse(
    val request_hash: String,
    val request_cached: Boolean,
    val request_cache_expiry: Long,
    val results: List<MangasResults>
)
