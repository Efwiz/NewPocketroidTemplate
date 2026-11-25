package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ClearSceneAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: ClearSceneAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\b2\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004J\u000e\u0010\n\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\rH\u0014R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/catrobat/catroid/content/actions/ClearSceneAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "sceneName", "", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "reset", "", "setScene", "setSprite", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClearSceneAction extends TemporalAction {
    private String sceneName;
    private Sprite sprite;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        StageListener stageListener;
        if (this.sceneName != null) {
            Sprite sprite = this.sprite;
            if (sprite == null) {
                Intrinsics.throwUninitializedPropertyAccessException("sprite");
                sprite = null;
            }
            sprite.releaseAllPointers();
            StageActivity stageActivity = StageActivity.activeStageActivity.get();
            if (stageActivity == null || (stageListener = stageActivity.stageListener) == null) {
                return;
            }
            stageListener.clearScene(this.sceneName);
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction, com.badlogic.gdx.scenes.scene2d.Action, com.badlogic.gdx.utils.Pool.Poolable
    public void reset() {
        super.reset();
        this.sceneName = null;
    }

    public final void setScene(String sceneName) {
        this.sceneName = sceneName;
    }

    public final void setSprite(Sprite sprite) {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        this.sprite = sprite;
    }
}
