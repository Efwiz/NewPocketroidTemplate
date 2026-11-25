package com.badlogic.gdx.scenes.scene2d.actions;

import com.badlogic.gdx.math.MathUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/gdx.jar:com/badlogic/gdx/scenes/scene2d/actions/RotateToAction.class
  classes8.dex
 */
/* JADX INFO: loaded from: classes8.dex */
public class RotateToAction extends TemporalAction {
    private float end;
    private float start;
    private boolean useShortestDirection;

    public RotateToAction() {
        this.useShortestDirection = false;
    }

    public RotateToAction(boolean z) {
        this.useShortestDirection = z;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void begin() {
        this.start = this.target.getRotation();
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float f) {
        float fLerpAngleDeg;
        if (f == 0.0f) {
            fLerpAngleDeg = this.start;
        } else if (f == 1.0f) {
            fLerpAngleDeg = this.end;
        } else if (this.useShortestDirection) {
            fLerpAngleDeg = MathUtils.lerpAngleDeg(this.start, this.end, f);
        } else {
            float f2 = this.start;
            fLerpAngleDeg = f2 + ((this.end - f2) * f);
        }
        this.target.setRotation(fLerpAngleDeg);
    }

    public float getRotation() {
        return this.end;
    }

    public void setRotation(float f) {
        this.end = f;
    }

    public boolean isUseShortestDirection() {
        return this.useShortestDirection;
    }

    public void setUseShortestDirection(boolean z) {
        this.useShortestDirection = z;
    }
}
