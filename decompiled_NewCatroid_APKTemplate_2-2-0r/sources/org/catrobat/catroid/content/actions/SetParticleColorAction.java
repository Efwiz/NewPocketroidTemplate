package org.catrobat.catroid.content.actions;

import android.util.Log;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.utils.ShowTextUtils;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetParticleColorAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetParticleColorAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0014"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetParticleColorAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", TypedValues.Custom.S_COLOR, "Lorg/catrobat/catroid/formulaeditor/Formula;", "getColor", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setColor", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "delta", "", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetParticleColorAction extends TemporalAction {
    private static final float MAX_COLOR = 255.0f;
    private static final int MAX_SIZE = 3;
    private Formula color;
    private Scope scope;
    private static final String TAG = "SetParticleColorAction";

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getColor() {
        return this.color;
    }

    public final void setColor(Formula formula) {
        this.color = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float delta) {
        try {
            Formula formula = this.color;
            if (formula != null) {
                int[] iArrCalculateColorRGBs = ShowTextUtils.calculateColorRGBs(formula.interpretString(this.scope));
                float[] fArr = new float[3];
                for (int i = 0; i < 3; i++) {
                    fArr[i] = iArrCalculateColorRGBs[i] / MAX_COLOR;
                }
                Scope scope = this.scope;
                if (scope != null) {
                    scope.getSprite().look.getParticleEffect().getEmitters().first().getTint().setColors(fArr);
                }
            }
        } catch (InterpretationException e) {
            Log.d(TAG, "Formula interpretation for this specific Brick failed.", e);
        }
    }
}
