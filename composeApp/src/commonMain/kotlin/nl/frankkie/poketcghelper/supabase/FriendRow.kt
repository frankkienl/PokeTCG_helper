package nl.frankkie.poketcghelper.supabase

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class FriendRow(
    var id: Int? = null,
    var created_at: Instant = Clock.System.now(),
    var user_uid: String,
    var friend_uid: String? = null,
    var status: String? = null,
    var remarks: String? = null,
    var user_email: String? = null,
    var friend_email: String? = null,
)