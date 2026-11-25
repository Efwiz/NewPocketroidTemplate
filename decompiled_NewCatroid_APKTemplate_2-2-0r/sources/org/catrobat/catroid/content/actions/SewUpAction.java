package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.embroidery.DSTStitchCommand;
import org.catrobat.catroid.embroidery.EmbroideryPatternManager;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SewUpAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SewUpAction extends TemporalAction {
    public static final int STEPS = 3;
    private Sprite sprite;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float delta) {
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void begin() {
        double radians = Math.toRadians(this.sprite.look.getMotionDirectionInUserInterfaceDimensionUnit());
        this.sprite.runningStitch.pause();
        float xInUserInterfaceDimensionUnit = this.sprite.look.getXInUserInterfaceDimensionUnit();
        float yInUserInterfaceDimensionUnit = this.sprite.look.getYInUserInterfaceDimensionUnit();
        EmbroideryPatternManager embroideryPatternManager = StageActivity.getActiveStageListener().embroideryPatternManager;
        int zIndex = this.sprite.look.getZIndex();
        Sprite sprite = this.sprite;
        embroideryPatternManager.addStitchCommand(new DSTStitchCommand(xInUserInterfaceDimensionUnit, yInUserInterfaceDimensionUnit, zIndex, sprite, sprite.getEmbroideryThreadColor()));
        float fSin = (float) (((double) xInUserInterfaceDimensionUnit) + (Math.sin(radians) * 3.0d));
        float fCos = (float) (((double) yInUserInterfaceDimensionUnit) + (Math.cos(radians) * 3.0d));
        EmbroideryPatternManager embroideryPatternManager2 = StageActivity.getActiveStageListener().embroideryPatternManager;
        int zIndex2 = this.sprite.look.getZIndex();
        Sprite sprite2 = this.sprite;
        embroideryPatternManager2.addStitchCommand(new DSTStitchCommand(fSin, fCos, zIndex2, sprite2, sprite2.getEmbroideryThreadColor()));
        float fSin2 = (float) (((double) fSin) - (Math.sin(radians) * 3.0d));
        float fCos2 = (float) (((double) fCos) - (Math.cos(radians) * 3.0d));
        EmbroideryPatternManager embroideryPatternManager3 = StageActivity.getActiveStageListener().embroideryPatternManager;
        int zIndex3 = this.sprite.look.getZIndex();
        Sprite sprite3 = this.sprite;
        embroideryPatternManager3.addStitchCommand(new DSTStitchCommand(fSin2, fCos2, zIndex3, sprite3, sprite3.getEmbroideryThreadColor()));
        float fSin3 = (float) (((double) fSin2) - (Math.sin(radians) * 3.0d));
        float fCos3 = (float) (((double) fCos2) - (Math.cos(radians) * 3.0d));
        EmbroideryPatternManager embroideryPatternManager4 = StageActivity.getActiveStageListener().embroideryPatternManager;
        int zIndex4 = this.sprite.look.getZIndex();
        Sprite sprite4 = this.sprite;
        embroideryPatternManager4.addStitchCommand(new DSTStitchCommand(fSin3, fCos3, zIndex4, sprite4, sprite4.getEmbroideryThreadColor()));
        float fSin4 = (float) (((double) fSin3) + (Math.sin(radians) * 3.0d));
        float fCos4 = (float) (((double) fCos3) + (Math.cos(radians) * 3.0d));
        EmbroideryPatternManager embroideryPatternManager5 = StageActivity.getActiveStageListener().embroideryPatternManager;
        int zIndex5 = this.sprite.look.getZIndex();
        Sprite sprite5 = this.sprite;
        embroideryPatternManager5.addStitchCommand(new DSTStitchCommand(fSin4, fCos4, zIndex5, sprite5, sprite5.getEmbroideryThreadColor()));
        this.sprite.runningStitch.setStartCoordinates(this.sprite.look.getXInUserInterfaceDimensionUnit(), this.sprite.look.getYInUserInterfaceDimensionUnit());
        this.sprite.runningStitch.resume();
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
