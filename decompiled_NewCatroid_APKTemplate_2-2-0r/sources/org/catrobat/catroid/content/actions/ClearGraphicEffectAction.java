package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ClearGraphicEffectAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ClearGraphicEffectAction extends TemporalAction {
    private Sprite sprite;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        this.sprite.look.setBrightnessInUserInterfaceDimensionUnit(100.0f);
        this.sprite.look.setTransparencyInUserInterfaceDimensionUnit(0.0f);
        this.sprite.look.setColorInUserInterfaceDimensionUnit(0.0f);
        this.sprite.look.hasParticleEffect = false;
        this.sprite.look.clearParticleEffect();
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
