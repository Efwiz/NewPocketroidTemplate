package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SceneIdAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SceneIdAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\b\u001a\u00020\tH\u0016J\u0015\u0010\n\u001a\u00020\t2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\fJ\u000e\u0010\r\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u0007J\u0010\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0010H\u0014R\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0005R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/catrobat/catroid/content/actions/SceneIdAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "sceneId", "", "Ljava/lang/Integer;", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "reset", "", "setScene", "sceneName", "(Ljava/lang/Integer;)V", "setSprite", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SceneIdAction extends TemporalAction {
    private Integer sceneId;
    private Sprite sprite;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        StageListener stageListener;
        Integer num = this.sceneId;
        if (num != null) {
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
            stageListener.startSceneById(Integer.valueOf(num.intValue() - 1));
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction, com.badlogic.gdx.scenes.scene2d.Action, com.badlogic.gdx.utils.Pool.Poolable
    public void reset() {
        super.reset();
        this.sceneId = null;
    }

    public final void setScene(Integer sceneName) {
        this.sceneId = sceneName;
    }

    public final void setSprite(Sprite sprite) {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        this.sprite = sprite;
    }
}
