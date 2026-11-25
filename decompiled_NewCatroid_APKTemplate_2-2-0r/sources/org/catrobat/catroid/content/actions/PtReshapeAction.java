package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.text.StringsKt;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.ml.MLBridge;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/PtReshapeAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: PtReshapeAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\b¨\u0006\u0016"}, d2 = {"Lorg/catrobat/catroid/content/actions/PtReshapeAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "nameFormula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getNameFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setNameFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "shapeFormula", "getShapeFormula", "setShapeFormula", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PtReshapeAction extends TemporalAction {
    private Formula nameFormula;
    private Scope scope;
    private Formula shapeFormula;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getNameFormula() {
        return this.nameFormula;
    }

    public final void setNameFormula(Formula formula) {
        this.nameFormula = formula;
    }

    public final Formula getShapeFormula() {
        return this.shapeFormula;
    }

    public final void setShapeFormula(Formula formula) {
        this.shapeFormula = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Formula formula = this.nameFormula;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "";
        }
        Formula formula2 = this.shapeFormula;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        List listSplit$default = StringsKt.split$default((CharSequence) (strInterpretString2 != null ? strInterpretString2 : ""), new String[]{","}, false, 0, 6, (Object) null);
        ArrayList arrayList = new ArrayList();
        Iterator it = listSplit$default.iterator();
        while (it.hasNext()) {
            Integer intOrNull = StringsKt.toIntOrNull(StringsKt.trim((CharSequence) it.next()).toString());
            if (intOrNull != null) {
                arrayList.add(intOrNull);
            }
        }
        int[] intArray = CollectionsKt.toIntArray(arrayList);
        if (strInterpretString.length() > 0) {
            if (intArray.length == 0) {
                return;
            }
            MLBridge.nativeReshape(strInterpretString, intArray);
        }
    }
}
