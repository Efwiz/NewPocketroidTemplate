package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ClearBackgroundAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ClearBackgroundAction extends TemporalAction {
    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float delta) {
        StageActivity.getActiveStageListener().clearBackground();
    }
}
