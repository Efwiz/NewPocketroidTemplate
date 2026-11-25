package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Sprite;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/GoToOtherSpritePositionAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: GoToOtherSpritePositionAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\b¨\u0006\u0010"}, d2 = {"Lorg/catrobat/catroid/content/actions/GoToOtherSpritePositionAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "destinationSprite", "Lorg/catrobat/catroid/content/Sprite;", "getDestinationSprite", "()Lorg/catrobat/catroid/content/Sprite;", "setDestinationSprite", "(Lorg/catrobat/catroid/content/Sprite;)V", "sprite", "getSprite", "setSprite", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class GoToOtherSpritePositionAction extends TemporalAction {
    private Sprite destinationSprite;
    private Sprite sprite;

    public final Sprite getSprite() {
        return this.sprite;
    }

    public final void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public final Sprite getDestinationSprite() {
        return this.destinationSprite;
    }

    public final void setDestinationSprite(Sprite sprite) {
        this.destinationSprite = sprite;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Sprite sprite = this.sprite;
        Look look = sprite != null ? sprite.look : null;
        if (look == null) {
            return;
        }
        Sprite sprite2 = this.destinationSprite;
        Look look2 = sprite2 != null ? sprite2.look : null;
        if (look2 == null) {
            return;
        }
        look.setXInUserInterfaceDimensionUnit(look2.getXInUserInterfaceDimensionUnit());
        look.setYInUserInterfaceDimensionUnit(look2.getYInUserInterfaceDimensionUnit());
    }
}
