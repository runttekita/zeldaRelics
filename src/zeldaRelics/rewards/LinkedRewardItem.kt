package zeldaRelics.rewards

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride
import com.evacipated.cardcrawl.modthespire.lib.SpireSuper
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.FontHelper
import com.megacrit.cardcrawl.helpers.PowerTip
import com.megacrit.cardcrawl.helpers.TipHelper
import com.megacrit.cardcrawl.helpers.input.InputHelper
import com.megacrit.cardcrawl.relics.AbstractRelic
import com.megacrit.cardcrawl.rewards.RewardItem
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect
import java.util.*

class LinkedRewardItem : RewardItem {
    var relicLinks: MutableList<RewardItem> = ArrayList()

    constructor(original: RewardItem) {
        type = original.type
        outlineImg = original.outlineImg
        img = original.img
        goldAmt = original.goldAmt
        bonusGold = original.bonusGold
        text = original.text
        //relicLink = original.relicLink; // TODO?
        if (original.relicLink != null) { //relicLinks.add(original.relicLink);
        }
        relic = original.relic
        potion = original.potion
        cards = original.cards
        //effects
//isBoss
        hb = original.hb
        y = original.y
        flashTimer = original.flashTimer
        isDone = original.isDone
        ignoreReward = original.ignoreReward
        redText = original.redText
    }

    constructor(setRelicLink: LinkedRewardItem, relic: AbstractRelic?) : super(relic) {
        addRelicLink(setRelicLink)
    }

    fun addRelicLink(setRelicLink: LinkedRewardItem) {
        if (!relicLinks.contains(setRelicLink)) {
            relicLinks.add(setRelicLink)
        }
        if (!setRelicLink.relicLinks.contains(this)) {
            setRelicLink.relicLinks.add(this)
        }
    }

    //if (AbstractDungeon.getCurrRoom().rewards.indexOf(this) > AbstractDungeon.getCurrRoom().rewards.indexOf(relicLink)) {
    private val isFirst: Boolean
        private get() { //if (AbstractDungeon.getCurrRoom().rewards.indexOf(this) > AbstractDungeon.getCurrRoom().rewards.indexOf(relicLink)) {
            val thisIndexOf = AbstractDungeon.getCurrRoom().rewards.indexOf(this)
            for (link in relicLinks) {
                if (AbstractDungeon.getCurrRoom().rewards.indexOf(link) < thisIndexOf) {
                    return false
                }
            }
            return true
        }

    override fun claimReward(): Boolean {
        val ret: Boolean
        ret = if (type == RewardType.SAPPHIRE_KEY) {
            if (!ignoreReward) {
                AbstractDungeon.topLevelEffects.add(ObtainKeyEffect(ObtainKeyEffect.KeyColor.BLUE))
            }
            true
        } else {
            super.claimReward()
        }
        if (ret) {
            for (link in relicLinks) {
                link.isDone = true
                link.ignoreReward = true
            }
        }
        return ret
    }

    override fun update() {
        super.update()
        if (isFirst) {
            redText = false
            for (link in relicLinks) {
                link.redText = false
            }
        }
        if (hb.hovered) {
            for (link in relicLinks) {
                link.redText = hb.hovered
            }
        }
    }

    override fun render(sb: SpriteBatch) {
        super.render(sb)
        if (!relicLinks.isEmpty() && type != RewardType.SAPPHIRE_KEY) {
            if (hb.hovered) { // Make TipHelper think we haven't tried to render a tip this frame
                try {
                    val f = TipHelper::class.java.getDeclaredField("renderedTipThisFrame")
                    f.isAccessible = true
                    f.setBoolean(null, false)
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: NoSuchFieldException) {
                    e.printStackTrace()
                }
                val tips = ArrayList<PowerTip>()
                tips.add(PowerTip(relic.name, relic.description))
                for (link in relicLinks) {
                    if (link.type == RewardType.SAPPHIRE_KEY) {
                        tips.add(
                            PowerTip(
                                TEXT[7],
                                TEXT[8].toString() + FontHelper.colorString(
                                    TEXT[6] + TEXT[9], "y"
                                )
                            )
                        )
                    } else if (link.relic != null) {
                        tips.add(
                            PowerTip(
                                TEXT[7],
                                TEXT[8].toString() + FontHelper.colorString(
                                    link.relic.name,
                                    "y"
                                ) + TEXT[9]
                            )
                        )
                    }
                }
                TipHelper.queuePowerTips(
                    360.0f * Settings.scale,
                    InputHelper.mY + 50.0f * Settings.scale,
                    tips
                )
            }
            if (!isFirst) {
                renderRelicLink(sb)
            }
        } else if (!relicLinks.isEmpty() && type == RewardType.SAPPHIRE_KEY) {
            if (hb.hovered) {
                val tips = ArrayList<PowerTip>()
                for (link in relicLinks) {
                    tips.add(
                        PowerTip(
                            TEXT[7],
                            TEXT[8].toString() + FontHelper.colorString(
                                link.relic.name + TEXT[9], "y"
                            )
                        )
                    )
                }
                TipHelper.queuePowerTips(
                    360.0f * Settings.scale,
                    InputHelper.mY + 50.0f * Settings.scale,
                    tips
                )
            }
        }
        hb.render(sb)
    }

    @SpireOverride
    protected fun renderRelicLink(sb: SpriteBatch?) {
        SpireSuper.call<Any>(sb)
    }
}