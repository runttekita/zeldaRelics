package zeldaRelics.relics

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.relics.AbstractRelic
import com.megacrit.cardcrawl.rooms.AbstractRoom
import zeldaRelics.ZeldaRelics.Companion.makeID

class GreenTunic : AbstractZeldaRelic(id, rarity, landingSound) {

    companion object {
        public val id = makeID(GreenTunic::class.java)
        private val rarity = AbstractRelic.RelicTier.BOSS
        private val landingSound = LandingSound.FLAT
        private const val healthGain = 3
    }

    override fun getUpdatedDescription(): String {
        return "${DESCRIPTIONS[0]}${healthGain}${DESCRIPTIONS[1]}"
    }

    override fun onEnterRoom(room: AbstractRoom?) {
        flash()
        player.increaseMaxHp(healthGain, true)
    }

    override fun canSpawn(): Boolean {
        return player.hasRelic(GreenHat.id)
    }

    override fun instantObtain() {
        if (player.hasRelic(GreenHat.id)) {
            var relicAtIndex = 0
            for (i in AbstractDungeon.player.relics.indices) {
                if ((AbstractDungeon.player.relics[i] as AbstractRelic).relicId == GreenHat.id) {
                    relicAtIndex = i
                    break
                }
            }
            player.loseRelic(GreenHat.id)
            instantObtain(player, relicAtIndex, false)
        } else {
            super.instantObtain()
        }
    }

    override fun instantObtain(p: AbstractPlayer, slot: Int, callOnEquip: Boolean) {
        if (player.hasRelic(GreenHat.id)) {
            var relicAtIndex = 0
            for (i in AbstractDungeon.player.relics.indices) {
                if ((AbstractDungeon.player.relics[i] as AbstractRelic).relicId == GreenHat.id) {
                    relicAtIndex = i
                    break
                }
            }
            player.loseRelic(GreenHat.id)
            instantObtain(player, relicAtIndex, false)
        } else {
            super.instantObtain(player, slot, callOnEquip)
        }
    }

    override fun obtain() {
        if (player.hasRelic(GreenHat.id)) {
            var relicAtIndex = 0
            for (i in AbstractDungeon.player.relics.indices) {
                if ((AbstractDungeon.player.relics[i] as AbstractRelic).relicId == GreenHat.id) {
                    relicAtIndex = i
                    break
                }
            }
            player.loseRelic(GreenHat.id)
            instantObtain(player, relicAtIndex, false)
        } else {
            super.obtain()
        }
    }

}