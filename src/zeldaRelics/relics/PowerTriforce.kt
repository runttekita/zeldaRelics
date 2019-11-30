package zeldaRelics.relics

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.powers.ArtifactPower
import com.megacrit.cardcrawl.powers.DexterityPower
import com.megacrit.cardcrawl.powers.StrengthPower
import com.megacrit.cardcrawl.relics.AbstractRelic
import zeldaRelics.ZeldaRelics

class PowerTriforce : AbstractZeldaRelic(id, rarity, landingSound) {

    companion object {
        public val id = ZeldaRelics.makeID(PowerTriforce::class.java)
        private val rarity = AbstractRelic.RelicTier.SPECIAL
        private val landingSound = AbstractRelic.LandingSound.MAGICAL
        private const val statGain = 3
    }

    override fun getUpdatedDescription(): String {
        return "${DESCRIPTIONS[0]}${statGain}${DESCRIPTIONS[1]}"
    }

    override fun atBattleStart() {
        flash()
        addToTop(
            ApplyPowerAction(
                player,
                player,
                StrengthPower(player, statGain),
                statGain
            )
        )
        addToTop(
            ApplyPowerAction(
                player,
                player,
                DexterityPower(player, statGain),
                statGain
            )
        )
        addToTop(RelicAboveCreatureAction(player, this))
    }

}