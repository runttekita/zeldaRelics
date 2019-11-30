package zeldaRelics

import basemod.BaseMod
import basemod.BaseMod.subscribe
import basemod.interfaces.EditRelicsSubscriber
import basemod.interfaces.EditStringsSubscriber
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.localization.PowerStrings
import com.megacrit.cardcrawl.localization.RelicStrings

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
        println("dab")
    }

}