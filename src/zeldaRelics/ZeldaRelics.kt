package zeldaRelics

import basemod.BaseMod
import basemod.BaseMod.subscribe
import basemod.helpers.RelicType
import basemod.interfaces.EditRelicsSubscriber
import basemod.interfaces.EditStringsSubscriber
import basemod.interfaces.OnPlayerLoseBlockSubscriber
import basemod.interfaces.PostUpdateSubscriber
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.localization.PowerStrings
import com.megacrit.cardcrawl.localization.RelicStrings
import zeldaRelics.relics.*

@SpireInitializer
class ZeldaRelics :
    EditStringsSubscriber,
    EditRelicsSubscriber,
    PostUpdateSubscriber,
    OnPlayerLoseBlockSubscriber
{
    override fun receiveOnPlayerLoseBlock(block: Int): Int {
        if (AbstractDungeon.player.hasRelic(HylianShield.id)) {
            return 0
        }
        return block
    }

    override fun receivePostUpdate() {
        if (AbstractDungeon.player == null) return
        AbstractDungeon.player.getRelic(CouragePiece.id)?.onTrigger()
        AbstractDungeon.player.getRelic(WisdomPiece.id)?.onTrigger()
    }

    override fun receiveEditStrings() {
        val lang = when (Settings.language) {
            else -> "eng"
        }
        BaseMod.loadCustomStringsFile(PowerStrings::class.java, "zeldaRelicsResources/localization/$lang/powers.json")
        BaseMod.loadCustomStringsFile(RelicStrings::class.java, "zeldaRelicsResources/localization/$lang/relics.json")
    }

    override fun receiveEditRelics() {
        BaseMod.addRelic(GreenHat(), RelicType.SHARED)
        BaseMod.addRelic(RedTunic(), RelicType.SHARED)
        BaseMod.addRelic(BlueTunic(), RelicType.SHARED)
        BaseMod.addRelic(Boomerang(), RelicType.SHARED)
        BaseMod.addRelic(HylianShield(), RelicType.SHARED)
        BaseMod.addRelic(MasterSword(), RelicType.SHARED)
        BaseMod.addRelic(WisdomTriforce(), RelicType.SHARED)
        BaseMod.addRelic(CourageTriforce(), RelicType.SHARED)
        BaseMod.addRelic(PowerTriforce(), RelicType.SHARED)
        BaseMod.addRelic(Navi(), RelicType.SHARED)
        BaseMod.addRelic(HeroBow(), RelicType.SHARED)
        BaseMod.addRelic(CouragePiece(), RelicType.SHARED)
        BaseMod.addRelic(WisdomPiece(), RelicType.SHARED)
    }

    companion object {
        var textureLoader = AssetLoader()
        private var modID: String? = null

        fun makeID(c: Class<*>): String {
            return makeID(c.simpleName)
        }

        fun makeID(idText: String): String {
            return getModID() + ":" + idText
        }

        fun getModID(): String? {
            return modID
        }

        fun setModID(ID: String) {
            modID = ID
        }

        @JvmStatic
        fun initialize() {
            ZeldaRelics()
        }

    }

    init {
        subscribe(this)
        setModID("zeldaRelics")
    }

}