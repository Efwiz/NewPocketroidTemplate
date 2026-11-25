package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.sensing.CollisionInformation;
import org.catrobat.catroid.stage.StageActivity;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ScreenShotAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: ScreenShotAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u0000  2\u00020\u0001:\u0001 B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0002J\b\u0010\u0017\u001a\u00020\u0006H\u0002J\b\u0010\u0018\u001a\u00020\u0016H\u0016J\b\u0010\u0019\u001a\u00020\u0016H\u0016J\u0010\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0010\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u001e\u001a\u00020\u0014H\u0014J\b\u0010\u001f\u001a\u00020\u0016H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lorg/catrobat/catroid/content/actions/ScreenShotAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "isDone", "", "name", "", "getName", "()Ljava/lang/String;", "setName", "(Ljava/lang/String;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "started", "act", "delta", "", "captureAndSetLookAsync", "", "getScreenshotPath", "reset", "restart", "setLook", "lookData", "Lorg/catrobat/catroid/common/LookData;", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "updateLookListIndex", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ScreenShotAction extends TemporalAction {
    private static final String DEFAULT_FILENAME = "screenshot";
    private static final String FILE_EXTENSION = ".png";
    private static final String TAG = "ScreenShotAction";
    private boolean isDone;
    private String name;
    private Scope scope;
    private boolean started;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
    }

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(String str) {
        this.name = str;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction, com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (this.isDone) {
            return true;
        }
        if (this.started) {
            return false;
        }
        this.started = true;
        captureAndSetLookAsync();
        return false;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction, com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        super.restart();
        this.isDone = false;
        this.started = false;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction, com.badlogic.gdx.scenes.scene2d.Action, com.badlogic.gdx.utils.Pool.Poolable
    public void reset() {
        super.reset();
        this.isDone = false;
        this.started = false;
    }

    private final void captureAndSetLookAsync() {
        final StageActivity stageActivity = StageActivity.activeStageActivity.get();
        if (stageActivity == null) {
            Log.e(TAG, "StageActivity is null, cannot take screenshot.");
            this.isDone = true;
        } else {
            final String screenshotPath = getScreenshotPath();
            stageActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.content.actions.ScreenShotAction$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ScreenShotAction.captureAndSetLookAsync$lambda$1(stageActivity, screenshotPath, this);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void captureAndSetLookAsync$lambda$1(StageActivity activity, String filesDir, ScreenShotAction this$0) {
        Intrinsics.checkNotNullParameter(activity, "$activity");
        Intrinsics.checkNotNullParameter(filesDir, "$filesDir");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        activity.captureScreenWithNativeViews(filesDir, "screenshot.png", new ScreenShotAction$captureAndSetLookAsync$1$1(filesDir, this$0));
    }

    private final String getScreenshotPath() {
        return ProjectManager.getInstance().getCurrentlyPlayingScene().getDirectory().getAbsolutePath() + "/";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setLook(LookData lookData) throws InterruptedException {
        Sprite sprite;
        Thread thread;
        Scope scope = this.scope;
        if (scope == null || (sprite = scope.getSprite()) == null) {
            return;
        }
        updateLookListIndex();
        sprite.look.setLookData(lookData);
        CollisionInformation collisionInformation = lookData.getCollisionInformation();
        if (collisionInformation != null && (thread = collisionInformation.collisionPolygonCalculationThread) != null) {
            thread.join();
        }
        lookData.setIsWebRequest(true);
    }

    private final void updateLookListIndex() {
        Sprite sprite;
        Sprite sprite2;
        Scope scope = this.scope;
        List<LookData> lookList = null;
        Look look = (scope == null || (sprite2 = scope.getSprite()) == null) ? null : sprite2.look;
        if (look != null) {
            if (look.getLookListIndexBeforeLookRequest() <= -1) {
                Scope scope2 = this.scope;
                if (scope2 != null && (sprite = scope2.getSprite()) != null) {
                    lookList = sprite.getLookList();
                }
                look.setLookListIndexBeforeLookRequest(lookList != null ? lookList.indexOf(look.getLookData()) : -1);
            }
        }
    }
}
