package zeldaRelics.patches

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.helpers.RelicLibrary
import com.megacrit.cardcrawl.relics.AbstractRelic
import com.megacrit.cardcrawl.rewards.RewardItem
import zeldaRelics.helpers.Helper
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
            if (!player.hasRelic(CourageTriforce.id)) {
                __instance.relic = RelicLibrary.getRelic(CouragePiece.id).makeCopy()
                return
            }
            if (!player.hasRelic(WisdomTriforce.id)) {
                __instance.relic = RelicLibrary.getRelic(WisdomPiece.id).makeCopy()
                return
            }
            if (!player.hasRelic(PowerTriforce.id)) {
                __instance.relic = RelicLibrary.getRelic(PowerPiece.id).makeCopy()
                return
            }
        }
    }
}