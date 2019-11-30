package zeldaRelics.relics

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.StrengthPower
import com.megacrit.cardcrawl.relics.AbstractRelic
import zeldaRelics.ZeldaRelics

class RedTunic : AbstractZeldaRelic(id, rarity, landingSound) {

    companion object {
        public val id = ZeldaRelics.makeID(GreenHat::class.java)
        private val rarity = AbstractRelic.RelicTier.STARTER
        private val landingSound = LandingSound.FLAT
        private const val strengthGain = 2
    }

    override fun getUpdatedDescription(): String {
        return "${DESCRIPTIONS[0]}${strengthGain}${DESCRIPTIONS[1]}"
    }

    override fun atBattleStart() {
        flash()
        addToTop(
            ApplyPowerAction(
                player,
                player,
                StrengthPower(player, strengthGain),
                strengthGain
            )
        )
        addToTop(RelicAboveCreatureAction(player, this))
    }

}