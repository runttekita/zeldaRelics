package zeldaRelics.modifiers

import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.daily.mods.AbstractDailyMod
import zeldaRelics.ZeldaRelics.Companion.makeID
import zeldaRelics.ZeldaRelics.Companion.textureLoader


class Hylian : AbstractDailyMod(id, name, desc, null, true) {

    companion object {
        val id = makeID(Hylian::class.java)
        private val modStrings = CardCrawlGame.languagePack.getRunModString(id)
        val name = modStrings.NAME
        val desc = modStrings.DESCRIPTION
    }

    init {
        img = textureLoader.getTexture("zeldaRelicsResources/images/ui/hylian.png")
    }

}