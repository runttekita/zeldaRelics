package zeldaRelics.relics

import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.relics.AbstractRelic
import com.megacrit.cardcrawl.rooms.AbstractRoom
import zeldaRelics.ZeldaRelics.Companion.makeID

class GreenHat : AbstractZeldaRelic(id, rarity, landingSound) {

    companion object {
        public val id = makeID(GreenHat::class.java)
        private val rarity = AbstractRelic.RelicTier.STARTER
        private val landingSound = LandingSound.FLAT
        private const val healthGain = 1
    }

    override fun getUpdatedDescription(): String {
        return "${DESCRIPTIONS[0]}${healthGain}${DESCRIPTIONS[1]}"
    }

    override fun onEnterRoom(room: AbstractRoom?) {
        flash()
        player.gainGold(healthGain)
    }

}