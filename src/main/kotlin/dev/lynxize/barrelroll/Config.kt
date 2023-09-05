package dev.lynxize.barrelroll

import kotlinx.serialization.Serializable

@Serializable
class Config(
	val allowThrusting: Boolean = false,
	val forceEnabled: Boolean = false,
	val forceInstalled: Boolean = false,
	val installedTimeout: Int = 40,
	val kineticDamage: KineticDamage = KineticDamage.VANILLA
)

@Serializable
enum class KineticDamage{
	VANILLA,
	HIGH_SPEED,
	NONE,
	INSTANT_KILL;
}