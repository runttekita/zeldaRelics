package zeldaRelics.relics

import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.relics.AbstractRelic
import zeldaRelics.ZeldaRelics

class WisdomTriforce : AbstractZeldaRelic(id, rarity, landingSound) {

    companion object {
        public val id = ZeldaRelics.makeID(WisdomTriforce::class.java)
        private val rarity = AbstractRelic.RelicTier.SPECIAL
        private val landingSound = LandingSound.MAGICAL
    }

    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0]
    }

    override fun onEquip() {
        ++AbstractDungeon.player.energy.energyMaster
    }

    override fun onUnequip() {
        --AbstractDungeon.player.energy.energyMaster
    }

}