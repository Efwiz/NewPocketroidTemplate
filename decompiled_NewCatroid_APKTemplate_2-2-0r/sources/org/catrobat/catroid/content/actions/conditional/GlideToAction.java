package org.catrobat.catroid.content.actions.conditional;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/conditional/GlideToAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class GlideToAction extends TemporalAction {
    private float currentXValue;
    private float currentYValue;
    private Formula duration;
    private float durationValue;
    private Formula endX;
    private float endXValue;
    private Formula endY;
    private float endYValue;
    protected Scope scope;
    private float startXValue;
    private float startYValue;
    private float velocityXValue = 0.0f;
    private float velocityYValue = 0.0f;
    private boolean restart = false;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void begin() throws InterpretationException {
        Float fInterpretFloat;
        Float fInterpretFloat2;
        Float fValueOf = Float.valueOf(0.0f);
        try {
            Formula formula = this.duration;
            fInterpretFloat = formula == null ? fValueOf : formula.interpretFloat(this.scope);
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
        }
        try {
            Formula formula2 = this.endX;
            fInterpretFloat2 = formula2 == null ? fValueOf : formula2.interpretFloat(this.scope);
        } catch (InterpretationException e2) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e2);
            fInterpretFloat = fValueOf;
            fInterpretFloat2 = fInterpretFloat;
        }
        try {
            Formula formula3 = this.endY;
            if (formula3 != null) {
                fValueOf = formula3.interpretFloat(this.scope);
            }
        } catch (InterpretationException e3) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e3);
            fInterpretFloat = fValueOf;
        }
        if (!this.restart) {
            if (this.duration != null) {
                super.setDuration(fInterpretFloat.floatValue());
                this.durationValue = fInterpretFloat.floatValue();
            }
            this.endXValue = fInterpretFloat2.floatValue();
            this.endYValue = fValueOf.floatValue();
        }
        this.restart = false;
        this.startXValue = this.scope.getSprite().look.getXInUserInterfaceDimensionUnit();
        float yInUserInterfaceDimensionUnit = this.scope.getSprite().look.getYInUserInterfaceDimensionUnit();
        this.startYValue = yInUserInterfaceDimensionUnit;
        float f = this.startXValue;
        this.currentXValue = f;
        this.currentYValue = yInUserInterfaceDimensionUnit;
        if (f == fInterpretFloat2.floatValue() && this.startYValue == fValueOf.floatValue()) {
            super.finish();
        }
        if (this.velocityXValue == 0.0f && this.velocityYValue == 0.0f) {
            float f2 = this.durationValue;
            if (f2 != 0.0f) {
                this.velocityXValue = (this.endXValue - this.startXValue) / f2;
                this.velocityYValue = (this.endYValue - this.startYValue) / f2;
            }
        }
        this.scope.getSprite().setGlidingVelocityX(this.velocityXValue);
        this.scope.getSprite().setGlidingVelocityY(this.velocityYValue);
        this.scope.getSprite().setGliding(true);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        float xInUserInterfaceDimensionUnit = this.scope.getSprite().look.getXInUserInterfaceDimensionUnit() - this.currentXValue;
        float yInUserInterfaceDimensionUnit = this.scope.getSprite().look.getYInUserInterfaceDimensionUnit() - this.currentYValue;
        if (-0.1f > xInUserInterfaceDimensionUnit || xInUserInterfaceDimensionUnit > 0.1f || -0.1f > yInUserInterfaceDimensionUnit || yInUserInterfaceDimensionUnit > 0.1f) {
            this.restart = true;
            setDuration(getDuration() - getTime());
            restart();
        } else {
            float f = this.startXValue;
            this.currentXValue = f + ((this.endXValue - f) * percent);
            float f2 = this.startYValue;
            this.currentYValue = f2 + ((this.endYValue - f2) * percent);
            this.scope.getSprite().look.setPositionInUserInterfaceDimensionUnit(this.currentXValue, this.currentYValue);
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void end() {
        this.scope.getSprite().setGliding(false);
        this.scope.getSprite().setGlidingVelocityX(0.0f);
        this.scope.getSprite().setGlidingVelocityY(0.0f);
    }

    public void setDuration(Formula duration) {
        this.duration = duration;
    }

    public void setPosition(Formula x, Formula y) {
        this.endX = x;
        this.endY = y;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }
}
