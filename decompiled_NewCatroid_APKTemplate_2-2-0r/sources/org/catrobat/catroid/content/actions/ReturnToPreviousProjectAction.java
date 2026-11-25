package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.lang.ref.WeakReference;
import kotlin.Metadata;
import org.catrobat.catroid.stage.StageActivity;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ReturnToPreviousProjectAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: ReturnToPreviousProjectAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0014¨\u0006\u0007"}, d2 = {"Lorg/catrobat/catroid/content/actions/ReturnToPreviousProjectAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReturnToPreviousProjectAction extends TemporalAction {
    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        StageActivity stageActivity;
        WeakReference<StageActivity> weakReference = StageActivity.activeStageActivity;
        if (weakReference == null || (stageActivity = weakReference.get()) == null) {
            return;
        }
        stageActivity.finish();
    }
}
