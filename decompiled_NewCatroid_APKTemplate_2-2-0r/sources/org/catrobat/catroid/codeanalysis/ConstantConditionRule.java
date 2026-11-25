package org.catrobat.catroid.codeanalysis;

import android.content.Context;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.IfLogicBeginBrick;
import org.catrobat.catroid.content.bricks.IfThenLogicBeginBrick;
import org.catrobat.catroid.content.bricks.RepeatUntilBrick;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/codeanalysis/ConstantConditionRule.class
  classes12.dex
 */
/* JADX INFO: compiled from: ConstantConditionRule.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/codeanalysis/ConstantConditionRule;", "Lorg/catrobat/catroid/codeanalysis/AnalysisRule;", "context2", "Landroid/content/Context;", "(Landroid/content/Context;)V", "analyze", "Lorg/catrobat/catroid/codeanalysis/AnalysisResult;", "brick", "Lorg/catrobat/catroid/content/bricks/Brick;", "context", "Lorg/catrobat/catroid/codeanalysis/GlobalAnalysisContext;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConstantConditionRule implements AnalysisRule {
    private final Context context2;

    public ConstantConditionRule(Context context2) {
        Intrinsics.checkNotNullParameter(context2, "context2");
        this.context2 = context2;
    }

    @Override // org.catrobat.catroid.codeanalysis.AnalysisRule
    public AnalysisResult analyze(Brick brick, GlobalAnalysisContext context) {
        Formula formulaWithBrickField;
        Intrinsics.checkNotNullParameter(brick, "brick");
        Intrinsics.checkNotNullParameter(context, "context");
        if (brick instanceof IfThenLogicBeginBrick) {
            formulaWithBrickField = ((IfThenLogicBeginBrick) brick).getFormulaWithBrickField(Brick.BrickField.IF_CONDITION);
        } else if (brick instanceof IfLogicBeginBrick) {
            formulaWithBrickField = ((IfLogicBeginBrick) brick).getFormulaWithBrickField(Brick.BrickField.IF_CONDITION);
        } else {
            if (!(brick instanceof RepeatUntilBrick)) {
                return null;
            }
            formulaWithBrickField = ((RepeatUntilBrick) brick).getFormulaWithBrickField(Brick.BrickField.REPEAT_UNTIL_CONDITION);
        }
        if (formulaWithBrickField == null) {
            return null;
        }
        Map<String, StaticValue> mapEmptyMap = context.getIncomingStates().get(brick);
        if (mapEmptyMap == null) {
            mapEmptyMap = MapsKt.emptyMap();
        }
        StaticValue staticValueEvaluateFormula = context.evaluateFormula(formulaWithBrickField, mapEmptyMap);
        if (!(staticValueEvaluateFormula instanceof StaticValue.Bool)) {
            return null;
        }
        boolean value = ((StaticValue.Bool) staticValueEvaluateFormula).getValue();
        String trimmedFormulaString = formulaWithBrickField.getTrimmedFormulaString(this.context2);
        Intrinsics.checkNotNull(trimmedFormulaString);
        if (StringsKt.isBlank(trimmedFormulaString)) {
            return null;
        }
        if (value) {
            String string = brick instanceof RepeatUntilBrick ? this.context2.getString(R.string.analysis_constant_condition_repeat_until_true, trimmedFormulaString) : this.context2.getString(R.string.analysis_constant_condition_if_true, trimmedFormulaString);
            Intrinsics.checkNotNull(string);
            return new AnalysisResult(Severity.WARNING, string);
        }
        String string2 = brick instanceof RepeatUntilBrick ? this.context2.getString(R.string.analysis_constant_condition_repeat_until_false, trimmedFormulaString) : this.context2.getString(R.string.analysis_constant_condition_if_false, trimmedFormulaString);
        Intrinsics.checkNotNull(string2);
        return new AnalysisResult(Severity.WARNING, string2);
    }
}
