package org.catrobat.catroid.codeanalysis;

import android.content.Context;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import name.antonsmirnov.firmata.FormatHelper;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.bricks.AsyncRepeatBrick;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.Set3dScaleBrick;
import org.catrobat.catroid.content.bricks.SetShadowQualityBrick;
import org.catrobat.catroid.content.bricks.WaitBrick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/codeanalysis/ParameterValidationRule.class
  classes12.dex
 */
/* JADX INFO: compiled from: ParameterValidationRule.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0012\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0002J\u0012\u0010\u000f\u001a\u00020\f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/catrobat/catroid/codeanalysis/ParameterValidationRule;", "Lorg/catrobat/catroid/codeanalysis/AnalysisRule;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "analyze", "Lorg/catrobat/catroid/codeanalysis/AnalysisResult;", "brick", "Lorg/catrobat/catroid/content/bricks/Brick;", "context2", "Lorg/catrobat/catroid/codeanalysis/GlobalAnalysisContext;", "checkElementIsConstant", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/catrobat/catroid/formulaeditor/FormulaElement;", "isFormulaConstant", "formula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ParameterValidationRule implements AnalysisRule {
    private final Context context;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/codeanalysis/ParameterValidationRule$WhenMappings.class
      classes12.dex
     */
    /* JADX INFO: compiled from: ParameterValidationRule.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FormulaElement.ElementType.values().length];
            try {
                iArr[FormulaElement.ElementType.SENSOR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[FormulaElement.ElementType.USER_VARIABLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[FormulaElement.ElementType.USER_LIST.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[FormulaElement.ElementType.COLLISION_FORMULA.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[FormulaElement.ElementType.FUNCTION.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public ParameterValidationRule(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // org.catrobat.catroid.codeanalysis.AnalysisRule
    public AnalysisResult analyze(Brick brick, GlobalAnalysisContext context2) {
        Formula formulaWithBrickField;
        Intrinsics.checkNotNullParameter(brick, "brick");
        Intrinsics.checkNotNullParameter(context2, "context2");
        if (brick instanceof WaitBrick) {
            for (Brick parent = ((WaitBrick) brick).getParent(); parent != null; parent = parent.getParent()) {
                if (parent instanceof AsyncRepeatBrick) {
                    Severity severity = Severity.WARNING;
                    String string = this.context.getString(R.string.analysis_wait_inside_async);
                    Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                    return new AnalysisResult(severity, string);
                }
            }
        }
        if ((brick instanceof SetShadowQualityBrick) && (formulaWithBrickField = ((SetShadowQualityBrick) brick).getFormulaWithBrickField(Brick.BrickField.SHADOW_RESOLUTION)) != null && isFormulaConstant(formulaWithBrickField)) {
            String trimmedFormulaString = formulaWithBrickField.getTrimmedFormulaString(this.context);
            Intrinsics.checkNotNullExpressionValue(trimmedFormulaString, "getTrimmedFormulaString(...)");
            String string2 = StringsKt.trim((CharSequence) StringsKt.replace$default(trimmedFormulaString, FormatHelper.QUOTE, "", false, 4, (Object) null)).toString();
            Integer intOrNull = StringsKt.toIntOrNull(string2);
            if (intOrNull != null && intOrNull.intValue() > 0) {
                if (((intOrNull.intValue() - 1) & intOrNull.intValue()) != 0) {
                    Severity severity2 = Severity.WARNING;
                    String string3 = this.context.getString(R.string.analysis_shadow_quality_not_power_of_two, string2);
                    Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
                    return new AnalysisResult(severity2, string3);
                }
            }
        }
        if (!(brick instanceof Set3dScaleBrick)) {
            return null;
        }
        Set3dScaleBrick set3dScaleBrick = (Set3dScaleBrick) brick;
        if (CollectionsKt.toList(set3dScaleBrick.getAllFormulaFieldsWithFormulas().values()).size() < 3) {
            return null;
        }
        Formula formulaWithBrickField2 = set3dScaleBrick.getFormulaWithBrickField(Brick.BrickField.VALUE_2);
        Formula formulaWithBrickField3 = set3dScaleBrick.getFormulaWithBrickField(Brick.BrickField.VALUE_3);
        Formula formulaWithBrickField4 = set3dScaleBrick.getFormulaWithBrickField(Brick.BrickField.VALUE_4);
        if (!isFormulaConstant(formulaWithBrickField2) || !isFormulaConstant(formulaWithBrickField3) || !isFormulaConstant(formulaWithBrickField4)) {
            return null;
        }
        String trimmedFormulaString2 = formulaWithBrickField2.getTrimmedFormulaString(this.context);
        String trimmedFormulaString3 = formulaWithBrickField3.getTrimmedFormulaString(this.context);
        String trimmedFormulaString4 = formulaWithBrickField4.getTrimmedFormulaString(this.context);
        if (Intrinsics.areEqual(trimmedFormulaString2, trimmedFormulaString3) && Intrinsics.areEqual(trimmedFormulaString3, trimmedFormulaString4)) {
            return null;
        }
        Severity severity3 = Severity.WARNING;
        String string4 = this.context.getString(R.string.analysis_scale_3d_unequal, trimmedFormulaString2, trimmedFormulaString3, trimmedFormulaString4);
        Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
        return new AnalysisResult(severity3, string4);
    }

    private final boolean isFormulaConstant(Formula formula) {
        FormulaElement root = formula != null ? formula.getRoot() : null;
        if (root == null) {
            return false;
        }
        return checkElementIsConstant(root);
    }

    private final boolean checkElementIsConstant(FormulaElement element) {
        if (element == null) {
            return true;
        }
        FormulaElement.ElementType elementType = element.type;
        int i = elementType == null ? -1 : WhenMappings.$EnumSwitchMapping$0[elementType.ordinal()];
        if (i == 1 || i == 2 || i == 3 || i == 4) {
            return false;
        }
        if (i == 5 && Intrinsics.areEqual(element.getValue(), "RAND")) {
            return false;
        }
        if (checkElementIsConstant(element.leftChild) && checkElementIsConstant(element.rightChild)) {
            List<FormulaElement> additionalChildren = element.additionalChildren;
            Intrinsics.checkNotNullExpressionValue(additionalChildren, "additionalChildren");
            List<FormulaElement> list = additionalChildren;
            if ((list instanceof Collection) && list.isEmpty()) {
                return true;
            }
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                if (!checkElementIsConstant((FormulaElement) it.next())) {
                }
            }
            return true;
        }
        return false;
    }
}
