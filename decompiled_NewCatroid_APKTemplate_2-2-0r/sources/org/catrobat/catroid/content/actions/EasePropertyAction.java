package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.content.EasingFunctions;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/EasePropertyAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: EasePropertyAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0014J\u0010\u0010\u0015\u001a\u00020\u00142\b\u0010\u0016\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u0017\u001a\u00020\u00142\b\u0010\u0016\u001a\u0004\u0018\u00010\u0004J\u000e\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\rJ\u0010\u0010\u001a\u001a\u00020\u00142\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fJ\u0010\u0010\u001b\u001a\u00020\u00142\b\u0010\u0016\u001a\u0004\u0018\u00010\u0004J\u000e\u0010\u001c\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\rJ\u0010\u0010\u001d\u001a\u00020\u00142\u0006\u0010\u001e\u001a\u00020\u000bH\u0014R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\bR\u0010\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/catrobat/catroid/content/actions/EasePropertyAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "durationFormula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "easingTypes", "", "Lorg/catrobat/catroid/content/EasingFunctions$EasingType;", "[Lorg/catrobat/catroid/content/EasingFunctions$EasingType;", "endFormula", "endVal", "", "propertyIndex", "", "scope", "Lorg/catrobat/catroid/content/Scope;", "startFormula", "startVal", "typeIndex", "begin", "", "setDurationFormula", "formula", "setEndFormula", "setPropertyIndex", "index", "setScope", "setStartFormula", "setTypeIndex", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EasePropertyAction extends TemporalAction {
    private Formula durationFormula;
    private final EasingFunctions.EasingType[] easingTypes = EasingFunctions.EasingType.values();
    private Formula endFormula;
    private float endVal;
    private int propertyIndex;
    private Scope scope;
    private Formula startFormula;
    private float startVal;
    private int typeIndex;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void begin() {
        try {
            Formula formula = this.durationFormula;
            Float fInterpretFloat = formula != null ? formula.interpretFloat(this.scope) : null;
            setDuration(fInterpretFloat == null ? 1.0f : fInterpretFloat.floatValue());
            Formula formula2 = this.startFormula;
            Float fInterpretFloat2 = formula2 != null ? formula2.interpretFloat(this.scope) : null;
            this.startVal = fInterpretFloat2 == null ? 0.0f : fInterpretFloat2.floatValue();
            Formula formula3 = this.endFormula;
            Float fInterpretFloat3 = formula3 != null ? formula3.interpretFloat(this.scope) : null;
            this.endVal = fInterpretFloat3 == null ? 0.0f : fInterpretFloat3.floatValue();
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation failed in EasePropertyAction.", e);
            setDuration(0.0f);
        }
        super.begin();
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Sprite sprite;
        Look look;
        Scope scope = this.scope;
        if (scope == null || (sprite = scope.getSprite()) == null || (look = sprite.look) == null) {
        }
        float fMin = Math.min(getTime(), getDuration());
        float duration = getDuration();
        int i = this.typeIndex;
        if (i < 0 || i >= this.easingTypes.length) {
            i = 0;
        }
        float fCalculate = EasingFunctions.calculate(this.easingTypes[i], fMin, duration, this.startVal, this.endVal);
        switch (this.propertyIndex) {
            case 0:
                look.setPositionInUserInterfaceDimensionUnit(fCalculate, look.getYInUserInterfaceDimensionUnit());
                break;
            case 1:
                look.setPositionInUserInterfaceDimensionUnit(look.getXInUserInterfaceDimensionUnit(), fCalculate);
                break;
            case 2:
                look.setSizeInUserInterfaceDimensionUnit(fCalculate);
                break;
            case 3:
                look.setTransparencyInUserInterfaceDimensionUnit(fCalculate);
                break;
            case 4:
                look.setColorInUserInterfaceDimensionUnit(fCalculate);
                break;
            case 5:
                look.setWidthV(Float.valueOf(fCalculate / 100.0f));
                break;
            case 6:
                look.setHeightV(Float.valueOf(fCalculate / 100.0f));
                break;
            case 7:
                look.setBrightnessInUserInterfaceDimensionUnit(fCalculate);
                break;
        }
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final void setPropertyIndex(int index) {
        this.propertyIndex = index;
    }

    public final void setTypeIndex(int index) {
        this.typeIndex = index;
    }

    public final void setDurationFormula(Formula formula) {
        this.durationFormula = formula;
    }

    public final void setStartFormula(Formula formula) {
        this.startFormula = formula;
    }

    public final void setEndFormula(Formula formula) {
        this.endFormula = formula;
    }
}
