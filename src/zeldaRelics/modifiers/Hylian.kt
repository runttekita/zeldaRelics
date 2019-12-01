package zeldaRelics.modifiers

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.daily.mods.AbstractDailyMod
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.RelicLibrary
import com.megacrit.cardcrawl.relics.AbstractRelic
import zeldaRelics.ZeldaRelics.Companion.makeID
import zeldaRelics.ZeldaRelics.Companion.textureLoader
import zeldaRelics.relics.AbstractZeldaRelic


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

    @SpirePatch(
        clz = AbstractDungeon::class,
        method = "initializeRelicList"
    )
    object RemoveRelicsIfNotHylian {
        @JvmStatic
        fun Postfix(__instance: AbstractDungeon) {
            if (CardCrawlGame.trial == null || !CardCrawlGame.trial.dailyModIDs().contains(Hylian.id)) {
                removeRelics(AbstractDungeon.commonRelicPool)
                removeRelics(AbstractDungeon.uncommonRelicPool)
                removeRelics(AbstractDungeon.rareRelicPool)
                removeRelics(AbstractDungeon.bossRelicPool)
                removeRelics(AbstractDungeon.shopRelicPool)
            }
        }

        fun removeRelics(list: ArrayList<String>) {
            val relicsToRemove = ArrayList<String>()
            for (relic in list) {
                if (RelicLibrary.getRelic(relic) is AbstractZeldaRelic) {
                    relicsToRemove.add(relic)
                }
            }
            for (relic in relicsToRemove) {
                AbstractDungeon.commonRelicPool.remove(relic)
            }
            relicsToRemove.clear()
        }
    }

}