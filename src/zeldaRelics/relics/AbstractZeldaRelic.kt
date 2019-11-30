package zeldaRelics.relics

import basemod.abstracts.CustomRelic
import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon

import com.megacrit.cardcrawl.relics.AbstractRelic
import zeldaRelics.ZeldaRelics
import zeldaRelics.ZeldaRelics.Companion.textureLoader

open class AbstractZeldaRelic(id: String, tier: AbstractRelic.RelicTier, sfx: AbstractRelic.LandingSound) : CustomRelic(id, getTexture(id), tier, sfx), Helper {

}


private fun getTexture(id: String): Texture {
    val imgName = id.substring(id.indexOf(":") + 1).trim { it <= ' ' }
    return textureLoader.getTexture(imgName)
}