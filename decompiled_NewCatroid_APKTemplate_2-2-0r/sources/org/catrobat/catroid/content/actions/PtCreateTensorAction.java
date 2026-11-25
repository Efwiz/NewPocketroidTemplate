package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.ml.MLBridge;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/PtCreateTensorAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: PtCreateTensorAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0014R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\b¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/content/actions/PtCreateTensorAction;", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "()V", "nameFormula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getNameFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setNameFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "shapeFormula", "getShapeFormula", "setShapeFormula", "trainableFormula", "getTrainableFormula", "setTrainableFormula", "valueFormula", "getValueFormula", "setValueFormula", ConfigConstants.CONFIG_KEY_UPDATE, "", "percent", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PtCreateTensorAction extends TemporalAction {
    private Formula nameFormula;
    private Scope scope;
    private Formula shapeFormula;
    private Formula trainableFormula;
    private Formula valueFormula;

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

    public final Formula getValueFormula() {
        return this.valueFormula;
    }

    public final void setValueFormula(Formula formula) {
        this.valueFormula = formula;
    }

    public final Formula getTrainableFormula() {
        return this.trainableFormula;
    }

    public final void setTrainableFormula(Formula formula) {
        this.trainableFormula = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        Float fInterpretFloat;
        Formula formula = this.nameFormula;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        if (strInterpretString == null) {
            strInterpretString = "t1";
        }
        Formula formula2 = this.shapeFormula;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        if (strInterpretString2 == null) {
            strInterpretString2 = SchemaSymbols.ATTVAL_TRUE_1;
        }
        Formula formula3 = this.valueFormula;
        if (Intrinsics.areEqual(formula3 != null ? formula3.interpretString(this.scope) : null, "RANDOM")) {
            Formula formula4 = this.trainableFormula;
            fInterpretFloat = formula4 != null ? formula4.interpretFloat(this.scope) : null;
            boolean z = (fInterpretFloat != null ? fInterpretFloat.floatValue() : 0.0f) > 0.5f;
            List listSplit$default = StringsKt.split$default((CharSequence) strInterpretString2, new String[]{","}, false, 0, 6, (Object) null);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default, 10));
            Iterator it = listSplit$default.iterator();
            while (it.hasNext()) {
                arrayList.add(Integer.valueOf(Integer.parseInt(StringsKt.trim((CharSequence) it.next()).toString())));
            }
            MLBridge.nativeCreateRandomTensor(strInterpretString, CollectionsKt.toIntArray(arrayList), z);
            return;
        }
        Formula formula5 = this.valueFormula;
        Float fInterpretFloat2 = formula5 != null ? formula5.interpretFloat(this.scope) : null;
        float fFloatValue = fInterpretFloat2 == null ? 0.0f : fInterpretFloat2.floatValue();
        Formula formula6 = this.trainableFormula;
        fInterpretFloat = formula6 != null ? formula6.interpretFloat(this.scope) : null;
        boolean z2 = (fInterpretFloat != null ? fInterpretFloat.floatValue() : 0.0f) > 0.5f;
        List listSplit$default2 = StringsKt.split$default((CharSequence) strInterpretString2, new String[]{","}, false, 0, 6, (Object) null);
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default2, 10));
        Iterator it2 = listSplit$default2.iterator();
        while (it2.hasNext()) {
            arrayList2.add(Integer.valueOf(Integer.parseInt(StringsKt.trim((CharSequence) it2.next()).toString())));
        }
        MLBridge.nativeCreateTensor(strInterpretString, CollectionsKt.toIntArray(arrayList2), fFloatValue, z2);
    }
}
