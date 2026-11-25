package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.content.Look;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetBrightnessAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetBrightnessAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\t\u001a\u00020\b2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006J\u0010\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fH\u0014R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetBrightnessAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "brightness", "Lorg/catrobat/catroid/formulaeditor/Formula;", "scope", "Lorg/catrobat/catroid/content/Scope;", "setBrightness", "", "setScope", ConfigConstants.CONFIG_KEY_UPDATE, "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetBrightnessAction extends TemporalAction {
    private Formula brightness;
    private Scope scope;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Sprite sprite;
        try {
            Formula formula = this.brightness;
            Look look = null;
            Float fInterpretFloat = formula != null ? formula.interpretFloat(this.scope) : null;
            float fFloatValue = fInterpretFloat == null ? 0.0f : fInterpretFloat.floatValue();
            Scope scope = this.scope;
            if (scope != null && (sprite = scope.getSprite()) != null) {
                look = sprite.look;
            }
            if (look == null) {
                return;
            }
            look.setBrightnessInUserInterfaceDimensionUnit(fFloatValue);
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
        }
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final void setBrightness(Formula brightness) {
        this.brightness = brightness;
    }
}
