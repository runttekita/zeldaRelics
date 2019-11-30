package zeldaRelics.relics

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.powers.DexterityPower
import com.megacrit.cardcrawl.powers.StrengthPower
import com.megacrit.cardcrawl.relics.AbstractRelic
import zeldaRelics.ZeldaRelics

class BlueTunic : AbstractZeldaRelic(id, rarity, landingSound) {

    companion object {
        public val id = ZeldaRelics.makeID(BlueTunic::class.java)
        private val rarity = AbstractRelic.RelicTier.SPECIAL
        private val landingSound = LandingSound.FLAT
        private const val dexGain = 2
    }

    override fun getUpdatedDescription(): String {
        return "${DESCRIPTIONS[0]}${dexGain}${DESCRIPTIONS[1]}"
    }

    override fun atBattleStart() {
        flash()
        addToTop(
            ApplyPowerAction(
                player,
                player,
                DexterityPower(player, dexGain),
                dexGain
            )
        )
        addToTop(RelicAboveCreatureAction(player, this))
    }

}