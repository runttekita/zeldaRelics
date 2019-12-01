package zeldaRelics.events

import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.events.AbstractImageEvent
import com.megacrit.cardcrawl.helpers.MonsterHelper
import com.megacrit.cardcrawl.helpers.PotionHelper
import com.megacrit.cardcrawl.helpers.RelicLibrary
import com.megacrit.cardcrawl.potions.FairyPotion
import com.megacrit.cardcrawl.rewards.RewardItem
import zeldaRelics.ZeldaRelics.Companion.makeID
import zeldaRelics.relics.BlueTunic
import zeldaRelics.relics.RedTunic
import zeldaRelics.rewards.LinkedRewardItem

class TunicMonsterEvent : AbstractImageEvent(name, desc[0], "zeldaRelicResources/images/events/tunicMonster.png") {

    companion object {
        val id = makeID(TunicMonsterEvent::class.java)
        private val eventStrings = CardCrawlGame.languagePack.getEventString(id)
        private val name = eventStrings.NAME
        private val desc = eventStrings.DESCRIPTIONS
        private val options = eventStrings.OPTIONS
    }

    init  {
        imageEventText.setDialogOption(OPTIONS[0])
        imageEventText.setDialogOption(OPTIONS[1])
    }

    override fun buttonEffect(i: Int) {
        when (screenNum) {
            0 -> {
                when (i) {
                    0 -> {
                        screenNum = 1
                    }
                    1 -> {
                        AbstractDungeon.getCurrRoom().rewards.clear()
                        AbstractDungeon.getCurrRoom().rewards.add(RewardItem(PotionHelper.getPotion(FairyPotion.POTION_ID)))
                        AbstractDungeon.combatRewardScreen.open()
                        imageEventText.updateBodyText(desc[1])
                        imageEventText.updateDialogOption(0, options[3])
                        imageEventText.clearRemainingOptions()
                        screenNum = 2
                    }
                }
            }
            1 -> {
                AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter(AbstractDungeon.eliteMonsterList[0])
                enterCombatFromImage()
                val blueTunicReward = LinkedRewardItem(RewardItem(RelicLibrary.getRelic(BlueTunic.id).makeCopy()))
                val linkedTunics = LinkedRewardItem(blueTunicReward, RelicLibrary.getRelic(RedTunic.id).makeCopy());
                AbstractDungeon.getCurrRoom().rewards.add(linkedTunics)
            }
            2 -> {
                openMap()
            }
        }
    }


}