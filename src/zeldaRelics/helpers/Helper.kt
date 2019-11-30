package zeldaRelics.helpers

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.AbstractPower

interface Helper {
    val player: AbstractPlayer
        get() = AbstractDungeon.player
    val drawPile: CardGroup
        get() = player.drawPile
    val discardPile: CardGroup
        get() = player.discardPile
    val hand: CardGroup
        get() = player.hand
    val exhaustPile: CardGroup
        get() = player.exhaustPile
    val limbo: CardGroup
        get() = player.limbo
    val defaultDrawAmount: Int
        get() = TODO()
    val defaultSource: AbstractCreature
        get() = TODO()
    val defaultBlock: Int
        get() = TODO()
    val defaultDamage: Int
        get() = TODO()

    fun act(a: AbstractGameAction) {
        AbstractDungeon.actionManager.addToBottom(a)
    }

    fun act(vararg a: AbstractGameAction) {
        for (action in a) {
            act(action)
        }
    }

    fun actButProbablyAHack(a: AbstractGameAction) {
        AbstractDungeon.actionManager.addToTop(a)
    }

    fun power(power: AbstractPower, source: AbstractCreature = defaultSource) {
        act(ApplyPowerAction(power.owner, defaultSource, power))
    }

    fun block(creature: AbstractCreature = defaultSource, amt: Int = defaultBlock) {
        act(GainBlockAction(creature, creature, amt))
    }

    fun damage(
        target: AbstractCreature,
        amount: Int = defaultDamage,
        source: AbstractCreature = defaultSource,
        type: DamageInfo.DamageType = DamageInfo.DamageType.NORMAL
    ) {
        act(DamageAction(target, DamageInfo(source, amount), AbstractGameAction.AttackEffect.BLUNT_LIGHT))
    }

    fun loopOverAllPiles(callback: (ArrayList<AbstractCard>) -> Unit) {
        callback(drawPile.group)
        callback(discardPile.group)
        callback(hand.group)
        callback(exhaustPile.group)
    }

    fun randomMonster(): AbstractMonster {
        return AbstractDungeon.getMonsters().getRandomMonster(true)
    }

    fun loopOverMonsters(callback: (AbstractMonster) -> Unit) {
        AbstractDungeon.getMonsters().monsters.forEach(callback)
    }
}