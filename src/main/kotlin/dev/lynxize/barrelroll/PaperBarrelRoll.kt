package dev.lynxize.barrelroll

import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
class PaperBarrelRoll: JavaPlugin() {

	companion object {
		const val PROTOCOL_VERSION = 3;
		const val CHANNEL = "do_a_barrel_roll:handshake"

		lateinit var instance: PaperBarrelRoll
	}

	var config: Config = Config(
		true,
		true,
		true,
		10,
		KineticDamage.VANILLA
	)

	override fun onEnable() {
		instance = this
		val listener = HandshakeListener()
		this.server.pluginManager.registerEvents(listener, this)
		this.server.messenger.registerOutgoingPluginChannel(this, CHANNEL)
		this.server.messenger.registerIncomingPluginChannel(this, CHANNEL, listener)
	}
}