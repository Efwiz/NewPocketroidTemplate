package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/StopRunningStitchAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class StopRunningStitchAction extends TemporalAction {
    private Sprite sprite;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float delta) {
        this.sprite.runningStitch.deactivate();
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
