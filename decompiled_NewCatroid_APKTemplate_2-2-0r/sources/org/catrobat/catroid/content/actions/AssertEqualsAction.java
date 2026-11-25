package org.catrobat.catroid.content.actions;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.formulaeditor.Functions;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/AssertEqualsAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: AssertEqualsAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0002J\u0018\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\n2\u0006\u0010\u001e\u001a\u00020\nH\u0002J\u0018\u0010\u001f\u001a\u00020\u00162\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u001cH\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001a\u0010\u0012\u001a\u00020\nX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\f\"\u0004\b\u0014\u0010\u000e¨\u0006#"}, d2 = {"Lorg/catrobat/catroid/content/actions/AssertEqualsAction;", "Lorg/catrobat/catroid/content/actions/AssertAction;", "()V", "actualFormula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getActualFormula", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setActualFormula", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "actualValue", "", "getActualValue", "()Ljava/lang/Object;", "setActualValue", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "expectedFormula", "getExpectedFormula", "setExpectedFormula", "expectedValue", "getExpectedValue", "setExpectedValue", "act", "", "delta", "", "convertValuesToBooleanString", "", "formattedAssertEqualsError", "", "actual", "expected", "isValueBoolean", "elementType", "Lorg/catrobat/catroid/formulaeditor/FormulaElement$ElementType;", "value", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AssertEqualsAction extends AssertAction {
    private Formula actualFormula;
    public Object actualValue;
    private Formula expectedFormula;
    public Object expectedValue;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/AssertEqualsAction$WhenMappings.class
      classes14.dex
     */
    /* JADX INFO: compiled from: AssertEqualsAction.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FormulaElement.ElementType.values().length];
            try {
                iArr[FormulaElement.ElementType.FUNCTION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[FormulaElement.ElementType.OPERATOR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public final Formula getActualFormula() {
        return this.actualFormula;
    }

    public final void setActualFormula(Formula formula) {
        this.actualFormula = formula;
    }

    public final Formula getExpectedFormula() {
        return this.expectedFormula;
    }

    public final void setExpectedFormula(Formula formula) {
        this.expectedFormula = formula;
    }

    public final Object getActualValue() {
        Object obj = this.actualValue;
        if (obj != null) {
            return obj;
        }
        Intrinsics.throwUninitializedPropertyAccessException("actualValue");
        return Unit.INSTANCE;
    }

    public final void setActualValue(Object obj) {
        Intrinsics.checkNotNullParameter(obj, "<set-?>");
        this.actualValue = obj;
    }

    public final Object getExpectedValue() {
        Object obj = this.expectedValue;
        if (obj != null) {
            return obj;
        }
        Intrinsics.throwUninitializedPropertyAccessException("expectedValue");
        return Unit.INSTANCE;
    }

    public final void setExpectedValue(Object obj) {
        Intrinsics.checkNotNullParameter(obj, "<set-?>");
        this.expectedValue = obj;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        setAssertTitle("\nAssertEqualsError\n");
        Formula formula = this.actualFormula;
        if (formula == null) {
            failWith("Actual is null");
            return false;
        }
        if (this.expectedFormula == null) {
            failWith("Expected is null");
            return false;
        }
        Intrinsics.checkNotNull(formula);
        Object objInterpretObject = formula.interpretObject(getScope());
        Intrinsics.checkNotNullExpressionValue(objInterpretObject, "interpretObject(...)");
        setActualValue(objInterpretObject);
        Formula formula2 = this.expectedFormula;
        Intrinsics.checkNotNull(formula2);
        Object objInterpretObject2 = formula2.interpretObject(getScope());
        Intrinsics.checkNotNullExpressionValue(objInterpretObject2, "interpretObject(...)");
        setExpectedValue(objInterpretObject2);
        if (equalValues(getActualValue().toString(), getExpectedValue().toString())) {
            return true;
        }
        convertValuesToBooleanString();
        failWith(formattedAssertEqualsError(getActualValue(), getExpectedValue()));
        return false;
    }

    private final void convertValuesToBooleanString() {
        Formula formula = this.actualFormula;
        Intrinsics.checkNotNull(formula);
        FormulaElement.ElementType elementType = formula.getFormulaTree().getElementType();
        Intrinsics.checkNotNullExpressionValue(elementType, "getElementType(...)");
        Formula formula2 = this.actualFormula;
        Intrinsics.checkNotNull(formula2);
        String value = formula2.getFormulaTree().getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        boolean zIsValueBoolean = isValueBoolean(elementType, value);
        Formula formula3 = this.expectedFormula;
        Intrinsics.checkNotNull(formula3);
        FormulaElement.ElementType elementType2 = formula3.getFormulaTree().getElementType();
        Intrinsics.checkNotNullExpressionValue(elementType2, "getElementType(...)");
        Formula formula4 = this.expectedFormula;
        Intrinsics.checkNotNull(formula4);
        String value2 = formula4.getFormulaTree().getValue();
        Intrinsics.checkNotNullExpressionValue(value2, "getValue(...)");
        boolean zIsValueBoolean2 = isValueBoolean(elementType2, value2);
        if (zIsValueBoolean) {
            Object actualValue = getActualValue();
            Intrinsics.checkNotNull(actualValue, "null cannot be cast to non-null type kotlin.Double");
            setActualValue(String.valueOf(((Double) actualValue).doubleValue() > 0.0d));
        }
        if (zIsValueBoolean2) {
            Object expectedValue = getExpectedValue();
            Intrinsics.checkNotNull(expectedValue, "null cannot be cast to non-null type kotlin.Double");
            setExpectedValue(String.valueOf(((Double) expectedValue).doubleValue() > 0.0d));
        }
    }

    private final boolean isValueBoolean(FormulaElement.ElementType elementType, String value) {
        int i = WhenMappings.$EnumSwitchMapping$0[elementType.ordinal()];
        if (i == 1) {
            if (!(Intrinsics.areEqual(value, Functions.TRUE.toString()) ? true : Intrinsics.areEqual(value, Functions.FALSE.toString()))) {
                return false;
            }
        } else if (i != 2) {
            return false;
        }
        return true;
    }

    private final String formattedAssertEqualsError(Object actual, Object expected) {
        return "expected: <" + expected + ">\nactual:   <" + actual + ">\ndeviation: " + generateIndicator(actual, expected);
    }
}
