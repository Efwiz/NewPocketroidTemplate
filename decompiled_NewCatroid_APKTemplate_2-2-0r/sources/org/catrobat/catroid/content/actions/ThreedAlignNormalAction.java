package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.StageActivity;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ThreedAlignNormalAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: ThreedAlignNormalAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/content/actions/ThreedAlignNormalAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "nx", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getNx", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setNx", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "ny", "getNy", "setNy", "nz", "getNz", "setNz", "objId", "getObjId", "setObjId", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ThreedAlignNormalAction extends TemporalAction {
    private Formula nx;
    private Formula ny;
    private Formula nz;
    private Formula objId;
    private Scope scope;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getObjId() {
        return this.objId;
    }

    public final void setObjId(Formula formula) {
        this.objId = formula;
    }

    public final Formula getNx() {
        return this.nx;
    }

    public final void setNx(Formula formula) {
        this.nx = formula;
    }

    public final Formula getNy() {
        return this.ny;
    }

    public final void setNy(Formula formula) {
        this.ny = formula;
    }

    public final Formula getNz() {
        return this.nz;
    }

    public final void setNz(Formula formula) {
        this.nz = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        ThreeDManager threeDManager = StageActivity.getActiveStageListener().getThreeDManager();
        if (threeDManager == null) {
            return;
        }
        try {
            Formula formula = this.objId;
            Intrinsics.checkNotNull(formula);
            String strInterpretString = formula.interpretString(this.scope);
            Intrinsics.checkNotNull(strInterpretString);
            if (strInterpretString.length() == 0) {
                return;
            }
            Formula formula2 = this.nx;
            Intrinsics.checkNotNull(formula2);
            Float fInterpretFloat = formula2.interpretFloat(this.scope);
            Intrinsics.checkNotNullExpressionValue(fInterpretFloat, "interpretFloat(...)");
            float fFloatValue = fInterpretFloat.floatValue();
            Formula formula3 = this.ny;
            Intrinsics.checkNotNull(formula3);
            Float fInterpretFloat2 = formula3.interpretFloat(this.scope);
            Intrinsics.checkNotNullExpressionValue(fInterpretFloat2, "interpretFloat(...)");
            float fFloatValue2 = fInterpretFloat2.floatValue();
            Formula formula4 = this.nz;
            Intrinsics.checkNotNull(formula4);
            Float fInterpretFloat3 = formula4.interpretFloat(this.scope);
            Intrinsics.checkNotNullExpressionValue(fInterpretFloat3, "interpretFloat(...)");
            threeDManager.alignObjectToNormal(strInterpretString, fFloatValue, fFloatValue2, fInterpretFloat3.floatValue());
        } catch (Exception unused) {
        }
    }
}
