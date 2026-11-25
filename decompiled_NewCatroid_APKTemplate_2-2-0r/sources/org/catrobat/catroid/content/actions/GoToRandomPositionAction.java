package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.common.ScreenValues;
import org.catrobat.catroid.content.Sprite;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/GoToRandomPositionAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class GoToRandomPositionAction extends TemporalAction {
    private float randomXPosition;
    private float randomYPosition;
    private Sprite sprite;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        this.randomXPosition = (((float) Math.random()) * (ScreenValues.currentScreenResolution.getWidth() + 1)) - (ScreenValues.currentScreenResolution.getWidth() / 2);
        this.randomYPosition = (((float) Math.random()) * (ScreenValues.currentScreenResolution.getHeight() + 1)) - (ScreenValues.currentScreenResolution.getHeight() / 2);
        this.sprite.look.setPositionInUserInterfaceDimensionUnit(this.randomXPosition, this.randomYPosition);
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public float getRandomXPosition() {
        return this.randomXPosition;
    }

    public float getRandomYPosition() {
        return this.randomYPosition;
    }
}
