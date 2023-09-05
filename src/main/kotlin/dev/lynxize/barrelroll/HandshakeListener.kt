package dev.lynxize.barrelroll

import com.google.common.io.ByteStreams
import dev.lynxize.barrelroll.PaperBarrelRoll.Companion.instance
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.messaging.PluginMessageListener

class HandshakeListener: PluginMessageListener, Listener {
	private val players = mutableSetOf<Player>()

	override fun onPluginMessageReceived(channel: String, player: Player, message: ByteArray?) {
		if (channel != PaperBarrelRoll.CHANNEL) return
		println(message)
	}

	@EventHandler
	fun onPlayerJoin(event: PlayerJoinEvent) {
		instance.logger.info("Sending handshake to ${event.player.name}")

		@Suppress("UnstableApiUsage")
		var bytes = ByteStreams.newDataOutput()
		bytes.writeInt(PaperBarrelRoll.PROTOCOL_VERSION)
		bytes.write(Json.encodeToString(instance.config).toByteArray())

		instance.logger.info("Sending handshake: $bytes")
		event.player.sendPluginMessage(instance, PaperBarrelRoll.CHANNEL, bytes.toByteArray())
	}
}