package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.embroidery.DSTStitchCommand;
import org.catrobat.catroid.embroidery.EmbroideryPatternManager;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/StitchAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class StitchAction extends TemporalAction {
    private Sprite sprite;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float delta) {
        this.sprite.runningStitch.pause();
        float xInUserInterfaceDimensionUnit = this.sprite.look.getXInUserInterfaceDimensionUnit();
        float yInUserInterfaceDimensionUnit = this.sprite.look.getYInUserInterfaceDimensionUnit();
        EmbroideryPatternManager embroideryPatternManager = StageActivity.getActiveStageListener().embroideryPatternManager;
        int zIndex = this.sprite.look.getZIndex();
        Sprite sprite = this.sprite;
        embroideryPatternManager.addStitchCommand(new DSTStitchCommand(xInUserInterfaceDimensionUnit, yInUserInterfaceDimensionUnit, zIndex, sprite, sprite.getEmbroideryThreadColor()));
        this.sprite.runningStitch.setStartCoordinates(xInUserInterfaceDimensionUnit, yInUserInterfaceDimensionUnit);
        this.sprite.runningStitch.resume();
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
