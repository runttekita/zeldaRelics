package zeldaRelics.relics

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.relics.AbstractRelic
import com.megacrit.cardcrawl.unlock.UnlockTracker
import zeldaRelics.ZeldaRelics
import zeldaRelics.helpers.ZeldaEnums

class HeroBow : AbstractZeldaRelic(id, rarity, landingSound) {

    companion object {
        public val id = ZeldaRelics.makeID(HeroBow::class.java)
        private val rarity = AbstractRelic.RelicTier.COMMON
        private val landingSound = LandingSound.SOLID
        private const val damageBonus = 5
    }

    override fun getUpdatedDescription(): String {
        return "${DESCRIPTIONS[0]}${damageBonus}${DESCRIPTIONS[1]}"
    }

    override fun atDamageModify(damage: Float, c: AbstractCard): Float {
        return if (c.hasTag(ZeldaEnums.enums.ARROW)) damage + 5.0f else damage
    }

}