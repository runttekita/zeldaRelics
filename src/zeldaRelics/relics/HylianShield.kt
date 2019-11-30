package zeldaRelics.relics

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.actions.GameActionManager
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.relics.AbstractRelic
import javassist.CannotCompileException
import javassist.expr.ExprEditor
import javassist.expr.MethodCall
import zeldaRelics.ZeldaRelics
import javax.naming.CannotProceedException
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import javazoom.jl.decoder.LayerIIIDecoder.io



class HylianShield : AbstractZeldaRelic(id, rarity, landingSound) {

    companion object {
        public val id = ZeldaRelics.makeID(HylianShield::class.java)
        private val rarity = AbstractRelic.RelicTier.BOSS
        private val landingSound = LandingSound.HEAVY
    }

    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0]
    }

}