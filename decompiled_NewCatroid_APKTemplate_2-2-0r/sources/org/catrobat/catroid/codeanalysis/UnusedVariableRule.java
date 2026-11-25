package org.catrobat.catroid.codeanalysis;

import android.content.Context;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.ChangeVariableBrick;
import org.catrobat.catroid.content.bricks.SetVariableBrick;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/codeanalysis/UnusedVariableRule.class
  classes12.dex
 */
/* JADX INFO: compiled from: UnusedVariableRule.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/codeanalysis/UnusedVariableRule;", "Lorg/catrobat/catroid/codeanalysis/AnalysisRule;", "context2", "Landroid/content/Context;", "(Landroid/content/Context;)V", "analyze", "Lorg/catrobat/catroid/codeanalysis/AnalysisResult;", "brick", "Lorg/catrobat/catroid/content/bricks/Brick;", "context", "Lorg/catrobat/catroid/codeanalysis/GlobalAnalysisContext;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UnusedVariableRule implements AnalysisRule {
    private final Context context2;

    public UnusedVariableRule(Context context2) {
        Intrinsics.checkNotNullParameter(context2, "context2");
        this.context2 = context2;
    }

    /* JADX WARN: Code duplicated, block: B:12:0x002d  */
    @Override // org.catrobat.catroid.codeanalysis.AnalysisRule
    public AnalysisResult analyze(Brick brick, GlobalAnalysisContext context) {
        UserVariable userVariable;
        String name2;
        Intrinsics.checkNotNullParameter(brick, "brick");
        Intrinsics.checkNotNullParameter(context, "context");
        if (brick instanceof SetVariableBrick) {
            UserVariable userVariable2 = ((SetVariableBrick) brick).getUserVariable();
            if (userVariable2 != null) {
                name2 = userVariable2.getName();
            } else {
                name2 = null;
            }
        } else if (!(brick instanceof ChangeVariableBrick) || (userVariable = ((ChangeVariableBrick) brick).getUserVariable()) == null) {
            name2 = null;
        } else {
            name2 = userVariable.getName();
        }
        if (name2 == null || context.getVariablesRead().contains(name2)) {
            return null;
        }
        Severity severity = Severity.WARNING;
        String string = this.context2.getString(R.string.analysis_unused_variable, name2);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        return new AnalysisResult(severity, string);
    }
}
