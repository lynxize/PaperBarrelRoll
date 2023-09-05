package dev.lynxize.barrelroll

import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
class PaperBarrelRoll: JavaPlugin() {

	companion object {
		const val PROTOCOL_VERSION = 3;
		const val HANDSHAKE_CHANNEL = "do_a_barrel_roll:handshake"
		const val CONFIG_CHANNEL = "do_a_barrel_roll:server_config_update"

		lateinit var instance: PaperBarrelRoll
	}

	var config: Config = Config() // todo: load from file

	override fun onEnable() {
		instance = this
		val listener = HandshakeListener()
		this.server.pluginManager.registerEvents(listener, this)
		this.server.messenger.registerOutgoingPluginChannel(this, HANDSHAKE_CHANNEL)
		this.server.messenger.registerIncomingPluginChannel(this, HANDSHAKE_CHANNEL, listener)
		this.server.messenger.registerIncomingPluginChannel(this, CONFIG_CHANNEL, listener)
	}

	override fun onDisable() {
		// todo: save config to file
	}
}