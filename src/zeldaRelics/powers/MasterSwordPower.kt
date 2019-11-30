package zeldaRelics.powers

import com.evacipated.cardcrawl.modthespire.lib.*
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.powers.AbstractPower
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.localization.PowerStrings
import javazoom.jl.decoder.LayerIIIDecoder.io
import zeldaRelics.ZeldaRelics.Companion.makeID
import javazoom.jl.decoder.LayerIIIDecoder.io
import com.megacrit.cardcrawl.cards.AbstractCard.CardType
import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.powers.ConfusionPower
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect
import javassist.CtBehavior
import javazoom.jl.decoder.LayerIIIDecoder.io
import zeldaRelics.helpers.Helper

class MasterSwordPower(val target: AbstractCreature) : AbstractPower(), Helper {
    val POWER_ID = makeID(MasterSwordPower::class.java)
    private val powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID)
    val NAME = powerStrings.NAME
    val DESCRIPTIONS = powerStrings.DESCRIPTIONS

    companion object : Helper {
        private const val costReduction = 1

        public fun reduceAttackCost() {
            loopOverAllPiles { list ->
                list.forEach {
                    if (it.type == CardType.ATTACK && !it.isCostModified && it.cost > 0) {
                        it.modifyCostForCombat(it.cost - 1)
                        it.isCostModified = true
                    }
                }
            }
        }

    }

    init {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = target;
        this.amount = -1;
        description = "${DESCRIPTIONS[0]}${costReduction}${DESCRIPTIONS[1]}";
        this.loadRegion("corruption");
    }


    override fun updateDescription() {
        description = "${DESCRIPTIONS[0]}${costReduction}${DESCRIPTIONS[1]}";
    }

    override fun onCardDraw(card: AbstractCard) {
        if (card.type == CardType.ATTACK) {
            card.setCostForTurn(card.cost - 1)
        }
    }

    override fun stackPower(stackAmount: Int) {

    }

    override fun onInitialApplication() {
        reduceAttackCost()
    }

    @SpirePatch(
        clz = AbstractPlayer::class,
        method = "onCardDrawOrDiscard"
    )
    object SneckoCheck : Helper {
        @JvmStatic
        @SpireInsertPatch(
            locator = Locator::class
        )
        fun Insert(__instance: AbstractPlayer) {
            for (card in player.hand.group) {
                if (card.type == CardType.ATTACK && !card.isCostModified && card.cost > 0) {
                    card.modifyCostForCombat(card.cost - 1)
                    card.isCostModified = true
                }
            }
        }
    }

    private class Locator : SpireInsertLocator() {
        @Throws(Exception::class)
        override fun Locate(m: CtBehavior): IntArray {
            val matcher = Matcher.MethodCallMatcher(CardGroup::class.java, "applyPowers")
            return LineFinder.findInOrder(m, matcher)
        }
    }

    @SpirePatch(
        clz = ShowCardAndAddToHandEffect::class,
        method = SpirePatch.CONSTRUCTOR,
        paramtypez = [
            AbstractCard::class,
            Float::class,
            Float::class
        ]
    )
    object AddToHandPatch {
        @JvmStatic
        fun Postfix(__instance: ShowCardAndAddToHandEffect, card: AbstractCard, useless: Float, uselessTwo: Float) {
            if (card.type == CardType.ATTACK && !card.isCostModified && card.cost > 0) {
                card.modifyCostForCombat(card.cost - 1)
                card.isCostModified = true
            }
        }
    }

    @SpirePatch(
        clz = ShowCardAndAddToHandEffect::class,
        method = SpirePatch.CONSTRUCTOR,
        paramtypez = [
            AbstractCard::class
        ]
    )
    object Same {
        @JvmStatic
        fun Postfix(__instance: ShowCardAndAddToHandEffect, card: AbstractCard) {
            if (card.type == CardType.ATTACK && !card.isCostModified && card.cost > 0) {
                card.modifyCostForCombat(card.cost - 1)
                card.isCostModified = true
            }
        }
    }
}