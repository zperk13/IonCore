package net.starlegacy.feature.multiblock.starshipweapon.turret

import java.util.concurrent.TimeUnit
import net.starlegacy.feature.multiblock.MultiblockShape
import net.starlegacy.feature.starship.active.ActiveStarship
import net.starlegacy.feature.starship.subsystem.weapon.TurretWeaponSubsystem
import net.starlegacy.feature.starship.subsystem.weapon.primary.LightTurretWeaponSubsystem
import net.starlegacy.util.Vec3i
import org.bukkit.Material.GRINDSTONE
import org.bukkit.Material.IRON_TRAPDOOR
import org.bukkit.Material.LIGHTNING_ROD
import org.bukkit.block.BlockFace

sealed class LightTurretMultiblock : TurretMultiblock() {
	override fun createSubsystem(starship: ActiveStarship, pos: Vec3i, face: BlockFace): TurretWeaponSubsystem {
		return LightTurretWeaponSubsystem(starship, pos, getFacing(pos, starship), this)
	}

	protected abstract fun getSign(): Int

	override val cooldownNanos: Long = TimeUnit.MILLISECONDS.toNanos(250L)
	override val range: Double = 200.0
	override val sound: String = "starship.weapon.turbolaser.light.shoot"

	override val projectileSpeed: Int = 250
	override val projectileParticleThickness: Double = 0.3
	override val projectileExplosionPower: Float = 4f
	override val projectileShieldDamageMultiplier: Int = 2

	override fun buildFirePointOffsets(): List<Vec3i> = listOf(Vec3i(0, +3 * getSign(), +4))

	override fun MultiblockShape.buildStructure() {
		z(-1) {
			y(getSign() * 3) {
				x(+0).stainedTerracotta()
			}
			y(getSign() * 4) {
				x(+0).anyStairs()
			}
		}
		z(+0) {
			y(getSign() * 2) {
				x(+0).sponge()
			}
			y(getSign() * 3) {
				x(-1).anyStairs()
				x(+0).ironBlock()
				x(+1).anyStairs()
			}
			y(getSign() * 4) {
				x(-1).type(IRON_TRAPDOOR)
				x(+0).type(GRINDSTONE)
				x(+1).type(IRON_TRAPDOOR)
			}
		}
		z(+1) {
			y(getSign() * 3) {
				x(+0).anyStairs()
			}
			y(getSign() * 4) {
				x(+0).type(LIGHTNING_ROD)
			}
		}
	}
}

object TopLightTurretMultiblock : LightTurretMultiblock() {
	override fun getSign(): Int = 1
	override fun getPilotOffset(): Vec3i = Vec3i(+0, +3, +1)
}

object BottomLightTurretMultiblock : LightTurretMultiblock() {
	override fun getSign(): Int = -1
	override fun getPilotOffset(): Vec3i = Vec3i(+0, -4, +1)
}
