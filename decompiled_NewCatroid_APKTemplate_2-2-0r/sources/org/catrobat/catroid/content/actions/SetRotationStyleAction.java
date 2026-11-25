package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetRotationStyleAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetRotationStyleAction extends TemporalAction {
    private int mode;
    private Sprite sprite;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        this.sprite.look.setRotationMode(this.mode);
        this.sprite.look.setMotionDirectionInUserInterfaceDimensionUnit(this.sprite.look.getMotionDirectionInUserInterfaceDimensionUnit());
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setRotationStyle(int mode) {
        this.mode = mode;
    }
}
