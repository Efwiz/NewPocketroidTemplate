package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.utils.TouchUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/GoToTouchPositionAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class GoToTouchPositionAction extends TemporalAction {
    private Sprite sprite;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        int lastTouchIndex = TouchUtil.getLastTouchIndex();
        this.sprite.look.setPositionInUserInterfaceDimensionUnit(TouchUtil.getX(lastTouchIndex), TouchUtil.getY(lastTouchIndex));
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
