package org.catrobat.catroid.codeanalysis;

import android.content.Context;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.formulaeditor.Operators;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/codeanalysis/DivisionByZeroRule.class
  classes12.dex
 */
/* JADX INFO: compiled from: DivisionByZeroRule.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\nH\u0002J\"\u0010\u000f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u0010\u001a\u00020\u0011H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/catrobat/catroid/codeanalysis/DivisionByZeroRule;", "Lorg/catrobat/catroid/codeanalysis/AnalysisRule;", "context2", "Landroid/content/Context;", "(Landroid/content/Context;)V", "analyze", "Lorg/catrobat/catroid/codeanalysis/AnalysisResult;", "brick", "Lorg/catrobat/catroid/content/bricks/Brick;", "context", "Lorg/catrobat/catroid/codeanalysis/GlobalAnalysisContext;", "isElementConstantZero", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/catrobat/catroid/formulaeditor/FormulaElement;", "scanForDivisionByZero", ConfigConstants.CONFIG_KEY_DEPTH, "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DivisionByZeroRule implements AnalysisRule {
    private final Context context2;

    public DivisionByZeroRule(Context context2) {
        Intrinsics.checkNotNullParameter(context2, "context2");
        this.context2 = context2;
    }

    @Override // org.catrobat.catroid.codeanalysis.AnalysisRule
    public AnalysisResult analyze(Brick brick, GlobalAnalysisContext context) {
        Intrinsics.checkNotNullParameter(brick, "brick");
        Intrinsics.checkNotNullParameter(context, "context");
        if (!(brick instanceof FormulaBrick)) {
            return null;
        }
        try {
            Iterator<Formula> it = ((FormulaBrick) brick).getAllFormulaFieldsWithFormulas().values().iterator();
            while (it.hasNext()) {
                FormulaElement root = it.next().getRoot();
                if (root != null && scanForDivisionByZero$default(this, root, context, 0, 4, null)) {
                    Severity severity = Severity.ERROR;
                    String string = this.context2.getString(R.string.analysis_division_by_zero);
                    Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                    return new AnalysisResult(severity, string);
                }
            }
        } catch (Exception unused) {
        }
        return null;
    }

    static /* synthetic */ boolean scanForDivisionByZero$default(DivisionByZeroRule divisionByZeroRule, FormulaElement formulaElement, GlobalAnalysisContext globalAnalysisContext, int i, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            i = 0;
        }
        return divisionByZeroRule.scanForDivisionByZero(formulaElement, globalAnalysisContext, i);
    }

    /* JADX WARN: Code duplicated, block: B:18:0x003c  */
    private final boolean scanForDivisionByZero(FormulaElement element, GlobalAnalysisContext context, int depth) {
        boolean z;
        String upperCase;
        String string;
        FormulaElement formulaElement;
        if (depth > 50) {
            return false;
        }
        if (element.type == FormulaElement.ElementType.OPERATOR) {
            Object value = element.getValue();
            if (value instanceof Operators) {
                upperCase = ((Operators) value).name();
            } else if (value instanceof Enum) {
                upperCase = ((Enum) value).name();
            } else if (value == null || (string = value.toString()) == null) {
                upperCase = "";
            } else {
                upperCase = string.toUpperCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
                if (upperCase == null) {
                    upperCase = "";
                }
            }
            if ((Intrinsics.areEqual(upperCase, "DIVIDE") || Intrinsics.areEqual(upperCase, "MOD")) && (formulaElement = element.rightChild) != null && isElementConstantZero(formulaElement, context)) {
                return true;
            }
        }
        FormulaElement formulaElement2 = element.leftChild;
        boolean zScanForDivisionByZero = formulaElement2 != null ? scanForDivisionByZero(formulaElement2, context, depth + 1) : false;
        FormulaElement formulaElement3 = element.rightChild;
        boolean zScanForDivisionByZero2 = formulaElement3 != null ? scanForDivisionByZero(formulaElement3, context, depth + 1) : false;
        List<FormulaElement> additionalChildren = element.additionalChildren;
        Intrinsics.checkNotNullExpressionValue(additionalChildren, "additionalChildren");
        List<FormulaElement> list = additionalChildren;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                FormulaElement formulaElement4 = (FormulaElement) it.next();
                Intrinsics.checkNotNull(formulaElement4);
                if (scanForDivisionByZero(formulaElement4, context, depth + 1)) {
                    z = true;
                    break;
                }
            }
        } else {
            z = false;
            break;
        }
        return zScanForDivisionByZero || zScanForDivisionByZero2 || z;
    }

    private final boolean isElementConstantZero(FormulaElement element, GlobalAnalysisContext context) {
        StaticValue staticValueEvaluateFormula = context.evaluateFormula(new Formula(element), MapsKt.emptyMap());
        return (staticValueEvaluateFormula instanceof StaticValue.Number) && ((StaticValue.Number) staticValueEvaluateFormula).getValue() == 0.0d;
    }
}
