package zeldaRelics.relics

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.relics.AbstractRelic
import zeldaRelics.ZeldaRelics
import zeldaRelics.helpers.ZeldaEnums
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable.increment
import com.sun.xml.internal.bind.v2.model.core.ID
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.helpers.RelicLibrary
import com.megacrit.cardcrawl.relics.BloodyIdol
import com.megacrit.cardcrawl.relics.Circlet
import com.megacrit.cardcrawl.relics.GoldenIdol
import javazoom.jl.decoder.LayerIIIDecoder.io
import javazoom.jl.decoder.LayerIIIDecoder.io

class WisdomPiece : AbstractZeldaRelic(id, rarity, landingSound) {
    private var hereWeGoAgain = false

    companion object {
        public val id = ZeldaRelics.makeID(WisdomPiece::class.java)
        private val rarity = AbstractRelic.RelicTier.SPECIAL
        private val landingSound = LandingSound.MAGICAL
    }

    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0]
    }

    override fun onEquip() {
        setCounter(1)
    }

    override fun instantObtain() {
        if (AbstractDungeon.player.hasRelic(WisdomTriforce.id)) {
            RelicLibrary.getRelic(PowerPiece.id).makeCopy().instantObtain()
        }
        if (AbstractDungeon.player.hasRelic(WisdomPiece.id)) {
            val wisdomPiece = AbstractDungeon.player.getRelic(WisdomPiece.id) as WisdomPiece
            wisdomPiece.increment()
            wisdomPiece.flash()
        } else {
            super.instantObtain()
        }
    }

    override fun instantObtain(p: AbstractPlayer, slot: Int, callOnEquip: Boolean) {
        if (AbstractDungeon.player.hasRelic(WisdomTriforce.id)) {
            RelicLibrary.getRelic(PowerPiece.id).makeCopy().instantObtain(p, slot, callOnEquip)
        }
        if (AbstractDungeon.player.hasRelic(WisdomPiece.id)) {
            val wisdomPiece = AbstractDungeon.player.getRelic(WisdomPiece.id) as WisdomPiece
            wisdomPiece.increment()
            wisdomPiece.flash()
            isDone = true
            isObtained = true
            discarded = true
        } else {
            super.instantObtain(p, slot, callOnEquip)
        }
    }

    override fun obtain() {
        if (AbstractDungeon.player.hasRelic(WisdomTriforce.id)) {
            RelicLibrary.getRelic(PowerPiece.id).makeCopy().obtain()
        }
        if (AbstractDungeon.player.hasRelic(WisdomPiece.id)) {
            val wisdomPiece = AbstractDungeon.player.getRelic(WisdomPiece.id) as WisdomPiece
            wisdomPiece.increment()
            wisdomPiece.flash()
        } else {
            super.obtain()
        }
    }

    fun increment() {
        if (counter < 0) {
            counter = 0
        }
        counter++
        if (counter >= 3) {
            hereWeGoAgain = true
        }
    }

    override fun onTrigger() {
        if (hereWeGoAgain) {
            var relicAtIndex = 0
            for (i in AbstractDungeon.player.relics.indices) {
                if ((AbstractDungeon.player.relics[i] as AbstractRelic).relicId == WisdomPiece.id) {
                    relicAtIndex = i
                    break
                }
            }
            player.loseRelic(WisdomPiece.id)
            val triforce = RelicLibrary.getRelic(WisdomTriforce.id).makeCopy()
            triforce.instantObtain(AbstractDungeon.player, relicAtIndex, false)
        }
    }

}