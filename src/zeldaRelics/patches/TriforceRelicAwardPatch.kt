package zeldaRelics.patches

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.helpers.RelicLibrary
import com.megacrit.cardcrawl.relics.AbstractRelic
import com.megacrit.cardcrawl.rewards.RewardItem
import zeldaRelics.helpers.Helper
import zeldaRelics.modifiers.Hylian
import zeldaRelics.relics.*

@SpirePatch(
    clz = RewardItem::class,
    method = SpirePatch.CONSTRUCTOR,
    paramtypez = [
        AbstractRelic::class
    ]
)
class TriforceRelicAwardPatch {
    companion object : Helper {
        @JvmStatic
        fun Postfix(__instance: RewardItem, relic: AbstractRelic) {
            if (CardCrawlGame.trial == null || !CardCrawlGame.trial.dailyModIDs().contains(Hylian.id)) return
            if (!player.hasRelic(CourageTriforce.id)) {
                val piece = RelicLibrary.getRelic(CouragePiece.id).makeCopy()
                __instance.relic = piece
                __instance.text =  piece.name
                return
            }
            if (!player.hasRelic(WisdomTriforce.id)) {
                val piece = RelicLibrary.getRelic(WisdomPiece.id).makeCopy()
                __instance.relic = piece
                __instance.text =  piece.name
                return
            }
            if (!player.hasRelic(PowerTriforce.id)) {
                val piece = RelicLibrary.getRelic(PowerPiece.id).makeCopy()
                __instance.relic = piece
                __instance.text =  piece.name
                return
            }
        }
    }
}