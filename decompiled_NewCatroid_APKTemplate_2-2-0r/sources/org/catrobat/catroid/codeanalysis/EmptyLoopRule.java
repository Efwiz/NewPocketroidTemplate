package org.catrobat.catroid.codeanalysis;

import android.content.Context;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.CompositeBrick;
import org.catrobat.catroid.content.bricks.IfLogicBeginBrick;
import org.catrobat.catroid.content.bricks.TryCatchFinallyBrick;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/codeanalysis/EmptyLoopRule.class
  classes12.dex
 */
/* JADX INFO: compiled from: EmptyLoopRule.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/codeanalysis/EmptyLoopRule;", "Lorg/catrobat/catroid/codeanalysis/AnalysisRule;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "analyze", "Lorg/catrobat/catroid/codeanalysis/AnalysisResult;", "brick", "Lorg/catrobat/catroid/content/bricks/Brick;", "context2", "Lorg/catrobat/catroid/codeanalysis/GlobalAnalysisContext;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EmptyLoopRule implements AnalysisRule {
    private final Context context;

    public EmptyLoopRule(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:77:0x014e  */
    @Override // org.catrobat.catroid.codeanalysis.AnalysisRule
    public AnalysisResult analyze(Brick brick, GlobalAnalysisContext context2) {
        String strReplace$default;
        Intrinsics.checkNotNullParameter(brick, "brick");
        Intrinsics.checkNotNullParameter(context2, "context2");
        if (!(brick instanceof CompositeBrick)) {
            return null;
        }
        if (brick instanceof IfLogicBeginBrick) {
            IfLogicBeginBrick ifLogicBeginBrick = (IfLogicBeginBrick) brick;
            boolean zIsEmpty = ifLogicBeginBrick.getNestedBricks().isEmpty();
            boolean zIsEmpty2 = ifLogicBeginBrick.getSecondaryNestedBricks().isEmpty();
            if (zIsEmpty && zIsEmpty2) {
                Severity severity = Severity.WARNING;
                String string = this.context.getString(R.string.analysis_empty_loop_if_else_empty);
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                return new AnalysisResult(severity, string);
            }
            if (zIsEmpty && !zIsEmpty2) {
                Severity severity2 = Severity.WARNING;
                String string2 = this.context.getString(R.string.analysis_empty_loop_if_empty_else_not);
                Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                return new AnalysisResult(severity2, string2);
            }
            if (zIsEmpty || !zIsEmpty2) {
                return null;
            }
            Severity severity3 = Severity.WARNING;
            String string3 = this.context.getString(R.string.analysis_empty_loop_if_not_else_empty);
            Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
            return new AnalysisResult(severity3, string3);
        }
        if (brick instanceof TryCatchFinallyBrick) {
            TryCatchFinallyBrick tryCatchFinallyBrick = (TryCatchFinallyBrick) brick;
            boolean zIsEmpty3 = tryCatchFinallyBrick.getNestedBricks().isEmpty();
            boolean zIsEmpty4 = tryCatchFinallyBrick.getSecondaryNestedBricks().isEmpty();
            boolean zIsEmpty5 = tryCatchFinallyBrick.getThirdNestedBricks().isEmpty();
            if (!zIsEmpty3 || !zIsEmpty4 || !zIsEmpty5) {
                return null;
            }
            Severity severity4 = Severity.WARNING;
            String string4 = this.context.getString(R.string.analysis_empty_loop_try_catch_finally_empty);
            Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
            return new AnalysisResult(severity4, string4);
        }
        if (!((CompositeBrick) brick).getNestedBricks().isEmpty()) {
            return null;
        }
        String simpleName = brick.getClass().getSimpleName();
        if (simpleName != null) {
            switch (simpleName) {
                case "AsyncRepeatBrick":
                    strReplace$default = "Async Repeat";
                    break;
                case "ForVariableFromToBrick":
                    strReplace$default = "For Variable";
                    break;
                case "RepeatBrick":
                    strReplace$default = "Repeat";
                    break;
                case "RunAsSpriteBrick":
                    strReplace$default = "Run As";
                    break;
                case "InstantBrick":
                    strReplace$default = "Execute Instant";
                    break;
                case "ForItemInUserListBrick":
                    strReplace$default = "For Each Item";
                    break;
                case "SpawnThreadBrick":
                    strReplace$default = "Start Thread";
                    break;
                case "IntervalRepeatBrick":
                    strReplace$default = "Interval Repeat";
                    break;
                case "RepeatUntilBrick":
                    strReplace$default = "Repeat Until";
                    break;
                case "ForeverBrick":
                    strReplace$default = "Forever";
                    break;
                default:
                    Intrinsics.checkNotNull(simpleName);
                    strReplace$default = StringsKt.replace$default(StringsKt.replace$default(simpleName, "Brick", "", false, 4, (Object) null), "LogicBegin", "", false, 4, (Object) null);
                    break;
            }
        } else {
            Intrinsics.checkNotNull(simpleName);
            strReplace$default = StringsKt.replace$default(StringsKt.replace$default(simpleName, "Brick", "", false, 4, (Object) null), "LogicBegin", "", false, 4, (Object) null);
        }
        Severity severity5 = Severity.WARNING;
        String string5 = this.context.getString(R.string.analysis_empty_loop_empty_brick, strReplace$default);
        Intrinsics.checkNotNullExpressionValue(string5, "getString(...)");
        return new AnalysisResult(severity5, string5);
    }
}
