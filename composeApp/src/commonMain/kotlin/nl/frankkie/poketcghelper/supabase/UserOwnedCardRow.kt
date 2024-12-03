package nl.frankkie.poketcghelper.supabase

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
data class UserOwnedCardRow(
    var id: Int?=null,
    var user_uid: String,
    var created_at: Instant = Clock.System.now(),
    var card_set_id: String,
    var card_number: Int,
    var card_amount: Int,
    var card_remarks: Array<String>
)