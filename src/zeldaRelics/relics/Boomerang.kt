package zeldaRelics.relics

import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.actions.common.GainEnergyAction
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.relics.AbstractRelic
import com.megacrit.cardcrawl.vfx.GainPennyEffect
import zeldaRelics.ZeldaRelics

class Boomerang : AbstractZeldaRelic(id, rarity, landingSound) {

    companion object {
        public val id = ZeldaRelics.makeID(Boomerang::class.java)
        private val rarity = AbstractRelic.RelicTier.SHOP
        private val landingSound = LandingSound.FLAT
        private const val goldGain = 15
        private const val surpriseChance = 20
    }

    override fun getUpdatedDescription(): String {
        return "${DESCRIPTIONS[0]}${goldGain}${DESCRIPTIONS[1]}"
    }

    override fun onMonsterDeath(m: AbstractMonster) {
        if (m.currentHealth == 0) {
            flash()
            addToBot(RelicAboveCreatureAction(m, this))
            player.gainGold(goldGain)
            for (i in 0 until goldGain) {
                AbstractDungeon.effectList.add(
                    GainPennyEffect(
                        player,
                        m.hb.cX,
                        m.hb.cY,
                        m.hb.cX,
                        m.hb.cY,
                        true
                    )
                )
            }
            if (AbstractDungeon.relicRng.random(99) < surpriseChance) {
                addToBot(ObtainPotionAction(AbstractDungeon.returnRandomPotion(true)))
            }
        }
    }

}