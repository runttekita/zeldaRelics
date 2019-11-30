package zeldaRelics.relics

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.ArtifactPower
import com.megacrit.cardcrawl.relics.AbstractRelic
import com.megacrit.cardcrawl.unlock.UnlockTracker
import zeldaRelics.ZeldaRelics


class Navi : AbstractZeldaRelic(id, rarity, landingSound) {

    companion object {
        public val id = ZeldaRelics.makeID(Navi::class.java)
        private val rarity = AbstractRelic.RelicTier.COMMON
        private val landingSound = LandingSound.MAGICAL
        private const val costReduction = 1
    }

    override fun getUpdatedDescription(): String {
        return "${DESCRIPTIONS[0]}${costReduction}${DESCRIPTIONS[1]}"
    }

    override fun atBattleStart() {
        flash()
        val c = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy()
        if (c.cost > 0) {
            c.setCostForTurn(c.cost - 1)
        }
        UnlockTracker.markCardAsSeen(c.cardID)
        addToBot(MakeTempCardInHandAction(c))
    }

}