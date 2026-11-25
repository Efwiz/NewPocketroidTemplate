package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.GlobalManager;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SceneTransitionAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SceneTransitionAction extends TemporalAction {
    private String sceneName;
    private Sprite sprite;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        if (this.sceneName == null || ProjectManager.getInstance().getCurrentlyPlayingScene().getName().equals(this.sceneName)) {
            return;
        }
        this.sprite.releaseAllPointers();
        StageActivity.getActiveStageListener().transitionToScene(this.sceneName, Boolean.valueOf(GlobalManager.INSTANCE.getStopSounds()), Boolean.valueOf(GlobalManager.INSTANCE.getSaveScenes()));
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction, com.badlogic.gdx.scenes.scene2d.Action, com.badlogic.gdx.utils.Pool.Poolable
    public void reset() {
        super.reset();
        this.sceneName = null;
    }

    public void setScene(String sceneName) {
        this.sceneName = sceneName;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
