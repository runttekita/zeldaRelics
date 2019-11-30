package zeldaRelics

import basemod.BaseMod
import basemod.BaseMod.subscribe
import basemod.helpers.RelicType
import basemod.interfaces.EditRelicsSubscriber
import basemod.interfaces.EditStringsSubscriber
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.localization.PowerStrings
import com.megacrit.cardcrawl.localization.RelicStrings
import zeldaRelics.relics.*

@SpireInitializer
class ZeldaRelics :
    EditStringsSubscriber,
    EditRelicsSubscriber
{
    override fun receiveEditStrings() {
        val lang = when (Settings.language) {
            else -> "eng"
        }
        BaseMod.loadCustomStringsFile(PowerStrings::class.java, "zeldaRelics/localization/$lang/powers.json")
        BaseMod.loadCustomStringsFile(RelicStrings::class.java, "zeldaRelics/localization/$lang/relics.json")
    }

    override fun receiveEditRelics() {
        BaseMod.addRelic(GreenHat(), RelicType.SHARED)
        BaseMod.addRelic(RedTunic(), RelicType.SHARED)
        BaseMod.addRelic(BlueTunic(), RelicType.SHARED)
        BaseMod.addRelic(Boomerang(), RelicType.SHARED)
        BaseMod.addRelic(HylianShield(), RelicType.SHARED)
        BaseMod.addRelic(MasterSword(), RelicType.SHARED)
        BaseMod.addRelic(WisdomTriforce(), RelicType.SHARED)
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