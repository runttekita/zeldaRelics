package zeldaRelics.relics

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.ArtifactPower
import com.megacrit.cardcrawl.powers.StrengthPower
import com.megacrit.cardcrawl.relics.AbstractRelic
import zeldaRelics.ZeldaRelics

class CourageTriforce : AbstractZeldaRelic(id, rarity, landingSound) {

    companion object {
        public val id = ZeldaRelics.makeID(CourageTriforce::class.java)
        private val rarity = AbstractRelic.RelicTier.SPECIAL
        private val landingSound = LandingSound.MAGICAL
        private const val artifactGain = 5
    }

    override fun getUpdatedDescription(): String {
        return "${DESCRIPTIONS[0]}${artifactGain}${DESCRIPTIONS[1]}"
    }

    override fun atBattleStart() {
        flash()
        addToTop(
            ApplyPowerAction(
                player,
                player,
                ArtifactPower(player, artifactGain),
                artifactGain
            )
        )
        addToTop(RelicAboveCreatureAction(player, this))
    }

}