package nl.frankkie.poketcghelper.supabase

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
data class UserOwnedCards @OptIn(ExperimentalUuidApi::class) constructor(
    var id: Int,
    var user_uid: Uuid,
    var created_at: Instant = Clock.System.now(),
    var card_set_id: Int,
    var card_nr: Int,
    var card_amount: Int,
    var card_remarks: Array<String>
)