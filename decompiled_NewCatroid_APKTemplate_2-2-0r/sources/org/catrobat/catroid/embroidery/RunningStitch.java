package org.catrobat.catroid.embroidery;

import org.catrobat.catroid.content.Sprite;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/embroidery/RunningStitch.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class RunningStitch {
    private boolean isRunning = false;
    private Sprite sprite;
    private RunningStitchType type;

    public void activateStitching(Sprite sprite, RunningStitchType type) {
        if (sprite == null || type == null) {
            return;
        }
        this.sprite = sprite;
        this.type = type;
        this.isRunning = true;
    }

    public void update() {
        if (this.isRunning) {
            this.type.update(this.sprite.look.getXInUserInterfaceDimensionUnit(), this.sprite.look.getYInUserInterfaceDimensionUnit());
        }
    }

    public void setStartCoordinates(float xStart, float yStart) {
        RunningStitchType runningStitchType = this.type;
        if (runningStitchType != null) {
            runningStitchType.setStartCoordinates(xStart, yStart);
        }
    }

    public void pause() {
        this.isRunning = false;
    }

    public void resume() {
        if (this.type != null) {
            this.isRunning = true;
        }
    }

    public void deactivate() {
        this.isRunning = false;
        this.type = null;
        this.sprite = null;
    }

    public boolean isRunning() {
        return this.isRunning;
    }
}
