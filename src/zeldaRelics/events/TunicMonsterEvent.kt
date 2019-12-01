package zeldaRelics.events

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.unique.IncreaseMaxHpAction
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.events.AbstractImageEvent
import com.megacrit.cardcrawl.events.exordium.Mushrooms
import com.megacrit.cardcrawl.helpers.MonsterHelper
import com.megacrit.cardcrawl.helpers.PotionHelper
import com.megacrit.cardcrawl.helpers.RelicLibrary
import com.megacrit.cardcrawl.potions.FairyPotion
import com.megacrit.cardcrawl.powers.MetallicizePower
import com.megacrit.cardcrawl.rewards.RewardItem
import com.megacrit.cardcrawl.ui.buttons.ProceedButton
import javassist.CannotCompileException
import javassist.NotFoundException
import javassist.expr.ExprEditor
import javassist.expr.Instanceof
import zeldaRelics.ZeldaRelics.Companion.makeID
import zeldaRelics.relics.BlueTunic
import zeldaRelics.relics.RedTunic
import zeldaRelics.rewards.LinkedRewardItem


class TunicMonsterEvent : AbstractImageEvent(name, desc[0], "zeldaRelicsResources/images/events/tunicMonster.png") {

    companion object {
        val id = makeID(TunicMonsterEvent::class.java)
        private val eventStrings = CardCrawlGame.languagePack.getEventString(id)
        private val name = eventStrings.NAME
        private val desc = eventStrings.DESCRIPTIONS
        private val options = eventStrings.OPTIONS
        private const val armorAmt = 3
        private const val hpIncrease = 0.2f
    }

    init  {
        imageEventText.setDialogOption(options[0])
        imageEventText.setDialogOption(options[1])
    }

    override fun buttonEffect(i: Int) {
        when (screenNum) {
            0 -> {
                when (i) {
                    0 -> {
                        AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter(AbstractDungeon.eliteMonsterList.get(AbstractDungeon.miscRng.random(2)))
                        for (monster in AbstractDungeon.getMonsters().monsters) {
                            AbstractDungeon.actionManager.addToNextCombat(ApplyPowerAction(monster, monster, MetallicizePower(monster, armorAmt), armorAmt))
                            AbstractDungeon.actionManager.addToNextCombat(IncreaseMaxHpAction(monster, hpIncrease, true))
                        }
                        enterCombatFromImage()
                        val blueTunicReward = LinkedRewardItem(RewardItem(RelicLibrary.getRelic(BlueTunic.id).makeCopy()))
                        AbstractDungeon.getCurrRoom().rewards.add(blueTunicReward)
                        val linkedTunics = LinkedRewardItem(blueTunicReward, RelicLibrary.getRelic(RedTunic.id).makeCopy());
                        AbstractDungeon.getCurrRoom().rewards.add(linkedTunics)
                    }
                    1 -> {
                        imageEventText.updateBodyText(desc[1])
                        imageEventText.updateDialogOption(0, options[2])
                        imageEventText.clearRemainingOptions()
                        screenNum = 1
                    }
                }
            }
            1 -> {
                openMap()
            }
        }
    }

    @SpirePatch(
        clz = ProceedButton::class,
        method = "update"
    )
    object ProceedButtonPatch {
        @JvmStatic
        fun Instrument(): ExprEditor {
            return object : ExprEditor() {
                @Throws(CannotCompileException::class)
                override fun edit(i: Instanceof) {
                    try {
                        if (i.type.name == Mushrooms::class.java.name) {
                            i.replace("\$_ = \$proceed($$) || currentRoom.event instanceof zeldaRelics.events.TunicMonsterEvent;")
                        }
                    } catch (e: NotFoundException) {
                        println(e)
                    }
                }
            }
        }
    }

}