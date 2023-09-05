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
import java.util.concurrent.TimeUnit

class HandshakeListener: PluginMessageListener, Listener {
	private val players = mutableSetOf<Player>()

	override fun onPluginMessageReceived(channel: String, player: Player, message: ByteArray?) {
		instance.logger.info("Got ${message?.toString(Charsets.UTF_8)} from ${player.name}")
		when (channel) {
			PaperBarrelRoll.HANDSHAKE_CHANNEL -> handleHandshake(player, message)
			PaperBarrelRoll.CONFIG_CHANNEL -> handleConfig(player, message)
			else -> instance.logger.warning("Got plugin message from ${player.name} on unknown channel $channel, THIS IS A BUG")
		}
	}

	fun handleConfig(player: Player, message: ByteArray?) {

	}

	fun handleHandshake(player: Player, message: ByteArray?) {
		@Suppress("UnstableApiUsage")
		val bytes = ByteStreams.newDataInput(message)
		val clientVersion = bytes.readInt()
		val clientGotConfig = bytes.readBoolean()

		if (clientVersion != PaperBarrelRoll.PROTOCOL_VERSION) {
			instance.logger.warning("${player.name} responded with protocol version $clientVersion, only ${PaperBarrelRoll.PROTOCOL_VERSION} is supported")
			return
		}

		players.add(player)
	}

	@EventHandler
	fun onPlayerJoin(event: PlayerJoinEvent) {
		instance.server.asyncScheduler.runDelayed(instance, { sendHandshake(event.player) }, 1, TimeUnit.SECONDS)
	}

	fun sendHandshake(player: Player) {
		instance.logger.info("Sending handshake to ${player.name}")

		@Suppress("UnstableApiUsage")
		val bytes = ByteStreams.newDataOutput()
		bytes.writeInt(PaperBarrelRoll.PROTOCOL_VERSION)
		bytes.write(Json.encodeToString(instance.config).toByteArray())

		instance.logger.info("Sending handshake: ${bytes.toByteArray().toString(Charsets.UTF_8)}")
		player.sendPluginMessage(instance, PaperBarrelRoll.HANDSHAKE_CHANNEL, bytes.toByteArray())
	}
}