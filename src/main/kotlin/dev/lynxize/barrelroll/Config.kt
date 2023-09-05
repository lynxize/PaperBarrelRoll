package dev.lynxize.barrelroll

import kotlinx.serialization.Serializable

@Serializable
class Config(
	val allowThrusting: Boolean,
	val forceEnabled: Boolean,
	val forceInstalled: Boolean,
	val installedTimeout: Int,
	val kineticDamage: KineticDamage
)

@Serializable
enum class KineticDamage{
	VANILLA,
	HIGH_SPEED,
	NONE,
	INSTANT_KILL;
}