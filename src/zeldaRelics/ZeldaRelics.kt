package zeldaRelics

import basemod.BaseMod
import basemod.BaseMod.subscribe
import basemod.abstracts.CustomSavable
import basemod.helpers.RelicType
import basemod.interfaces.*
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.dungeons.Exordium
import com.megacrit.cardcrawl.localization.EventStrings
import com.megacrit.cardcrawl.localization.PowerStrings
import com.megacrit.cardcrawl.localization.RelicStrings
import com.megacrit.cardcrawl.localization.RunModStrings
import com.megacrit.cardcrawl.screens.custom.CustomMod
import com.megacrit.cardcrawl.trials.CustomTrial
import com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder
import zeldaRelics.events.TunicMonsterEvent
import zeldaRelics.modifiers.Hylian
import zeldaRelics.relics.*

@SpireInitializer
class ZeldaRelics :
    EditStringsSubscriber,
    EditRelicsSubscriber,
    PostUpdateSubscriber,
    OnPlayerLoseBlockSubscriber,
    AddCustomModeModsSubscriber,
    PostInitializeSubscriber,
    CustomSavable<Boolean>
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
        AbstractDungeon.player.getRelic(PowerPiece.id)?.onTrigger()
    }

    override fun receiveEditStrings() {
        BaseMod.loadCustomStringsFile(PowerStrings::class.java, "zeldaRelicsResources/localization/eng/powers.json")
        BaseMod.loadCustomStringsFile(RelicStrings::class.java, "zeldaRelicsResources/localization/eng/relics.json")
        BaseMod.loadCustomStringsFile(RunModStrings::class.java, "zeldaRelicsResources/localization/eng/modifier.json")
        BaseMod.loadCustomStringsFile(EventStrings::class.java, "zeldaRelicsResources/localization/eng/events.json")
        if (Settings.language != Settings.GameLanguage.ENG) {
            val lang = when (Settings.language) {
                else -> "eng"
            }
            BaseMod.loadCustomStringsFile(PowerStrings::class.java, "zeldaRelicsResources/localization/$lang/powers.json")
            BaseMod.loadCustomStringsFile(RelicStrings::class.java, "zeldaRelicsResources/localization/$lang/relics.json")
            BaseMod.loadCustomStringsFile(RunModStrings::class.java, "zeldaRelicsResources/localization/$lang/modifier.json")
            BaseMod.loadCustomStringsFile(EventStrings::class.java, "zeldaRelicsResources/localization/$lang/events.json")
        }
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
        //BaseMod.addRelic(HeroBow(), RelicType.SHARED)
        BaseMod.addRelic(CouragePiece(), RelicType.SHARED)
        BaseMod.addRelic(WisdomPiece(), RelicType.SHARED)
        BaseMod.addRelic(PowerPiece(), RelicType.SHARED)
        BaseMod.addRelic(GreenTunic(), RelicType.SHARED)
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

    override fun receiveCustomModeMods(list: MutableList<CustomMod>?) {
        list?.add(CustomMod(Hylian.id, "b", true))
    }

    override fun receivePostInitialize() {
        BaseMod.addEvent(makeID(TunicMonsterEvent::class.java), TunicMonsterEvent::class.java, Exordium.ID)
        BaseMod.addSaveField<Boolean>("isHylian",this)
    }

    override fun onLoad(isHylian: Boolean?) {
        if (isHylian != null && isHylian) {
            if (CardCrawlGame.trial == null) {
                CardCrawlGame.trial = CustomTrial()
            }
            CardCrawlGame.trial.dailyModIDs().add(Hylian.id)
        }
    }

    override fun onSave(): Boolean? {
        return CardCrawlGame.trial?.dailyModIDs()?.contains(Hylian.id)
    }

    /**
     * Idea taken from Alchyr
     * ASCII Art made from
     * https://asciiart.club/
     */
    object ASCII_ART {

        @SpireEnum
        @JvmStatic
        var shamelessSelfPlug: AbstractCard.CardTags? = null

        init {
            init()
        }

        private fun init() {
            println("   \n" +
                    "                                           ▄\n" +
                    "       ,                                  ███▄\n" +
                    "        ███▄▄╓                          ╓█████▄                           ,▄▄███\n" +
                    "        ▐████████▄▄;            ,▄     ▄████████      ▄             ,▄▄████████`\n" +
                    "         ██████████████▄▄,     ▄█     █▀▀▀▀▀▀▀▀▀▀▄    ▐█,      ▄▄▄████████████▌\n" +
                    "         ╙███████████████████▄██▌   ╓███┐      ▄███    ██▄▄▄██████████████████\n" +
                    "                  `└\"▀▀▀▀▀▀▀████⌐  ▄█████▄   ▄██████p  ╫██████▀▀▀▀▀▀▀╙└└\n" +
                    "                             ███▌ █████████▄█████████▄ ███▌\n" +
                    "            Φ▄███████████████████p                    ▄███████████████▄▄▄▄▄⌐\n" +
                    "             ██████████████▀▀▀█████▄       ╓▄       ▄█████▀▀██████████████▌\n" +
                    "              ████████▀▀`   ▄▄████████▄   ▄██▄  ,▄████████▄   `▀▀████████▌\n" +
                    "               ▀▀▀\"      ▄▄████▀▀███████ ▄████▄j███████▀█████▄      \"▀▀█▌\n" +
                    "                      ▄▄█████▀  ▄███▀▀└ ███████▄`▀▀████  ▀██████▄\n" +
                    "                   .▄██████▀   ▄████  .█████████▌  ▐████   ▀███████▄\n" +
                    "                     \"▀██▀    ▄████▌    ▀█████▀└    █████    ╙███▀╙\n" +
                    "                             ▄█████  ╓██µ ███  ██▄  ▐█████\n" +
                    "                                  ` ╓███ ▐████ ▀██▌  ╙└'\n" +
                    "                             ▄▄▄▄▄▄▄███  ▐████  ▄███▄▄▄▄▄▄,\n" +
                    "                             ▀███▌▀▀▀█▀^  ███⌐  ▀██▀▀▀███▀ This mod was a commission!\n" +
                    "                                ▀█        └█▌        ▐▀^   If you want to commission a mod too,\n" +
                    "                                           ╙               Leave a comment or email reinashsl@gmail.com!")
        }
    }

}