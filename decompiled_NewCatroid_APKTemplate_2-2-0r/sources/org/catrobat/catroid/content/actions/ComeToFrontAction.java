package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Sprite;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ComeToFrontAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ComeToFrontAction extends TemporalAction {
    private Sprite sprite;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float delta) {
        this.sprite.look.setZIndex(ProjectManager.getInstance().getCurrentProject().getSpriteListWithClones().size() + 1);
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
