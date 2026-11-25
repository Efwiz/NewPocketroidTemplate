package org.catrobat.catroid.content.actions;

import androidx.core.app.NotificationCompat;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.TestResult;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/FinishStageAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: FinishStageAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\u0018\u0000 \r2\u00020\u0001:\u0001\rB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0014R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\u000e"}, d2 = {"Lorg/catrobat/catroid/content/actions/FinishStageAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", NotificationCompat.GROUP_KEY_SILENT, "", "getSilent", "()Z", "setSilent", "(Z)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FinishStageAction extends TemporalAction {
    private static final String SUCCESS_MESSAGE = "Success";
    private boolean silent;

    public final boolean getSilent() {
        return this.silent;
    }

    public final void setSilent(boolean z) {
        this.silent = z;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        if (this.silent) {
            StageActivity.finishStage();
        } else {
            StageActivity.finishTestWithResult(new TestResult(SUCCESS_MESSAGE, TestResult.STAGE_ACTIVITY_TEST_SUCCESS));
        }
    }
}
