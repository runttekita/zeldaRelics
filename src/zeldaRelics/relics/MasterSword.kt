package zeldaRelics.relics

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.powers.DexterityPower
import com.megacrit.cardcrawl.relics.AbstractRelic
import zeldaRelics.ZeldaRelics
import zeldaRelics.powers.MasterSwordPower

class MasterSword : AbstractZeldaRelic(id, rarity, landingSound) {

    companion object {
        public val id = ZeldaRelics.makeID(MasterSword::class.java)
        private val rarity = AbstractRelic.RelicTier.BOSS
        private val landingSound = LandingSound.HEAVY
        private const val healthLoss = 20
        private const val costReduction = 1
    }

    override fun getUpdatedDescription(): String {
        return "${DESCRIPTIONS[0]}${healthLoss}${DESCRIPTIONS[1]}${costReduction}${DESCRIPTIONS[2]}"
    }

    override fun atBattleStart() {
        flash()
        addToTop(
            ApplyPowerAction(
                player,
                player,
                MasterSwordPower(player),
                0
            )
        )
        addToTop(RelicAboveCreatureAction(player, this))
    }

    override fun onEquip() {
        player.decreaseMaxHealth(healthLoss)
    }

}