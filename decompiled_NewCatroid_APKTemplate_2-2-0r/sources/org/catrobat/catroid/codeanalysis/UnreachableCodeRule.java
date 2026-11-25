package org.catrobat.catroid.codeanalysis;

import android.content.Context;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.CompositeBrick;
import org.catrobat.catroid.content.bricks.ForeverBrick;
import org.catrobat.catroid.content.bricks.IfLogicBeginBrick;
import org.catrobat.catroid.content.bricks.IfThenLogicBeginBrick;
import org.catrobat.catroid.content.bricks.RepeatUntilBrick;
import org.catrobat.catroid.content.bricks.StopScriptBrick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/codeanalysis/UnreachableCodeRule.class
  classes12.dex
 */
/* JADX INFO: compiled from: UnreachableCodeRule.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u001a\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0002J\u0018\u0010\u0011\u001a\u00020\f2\u000e\u0010\u0012\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0013H\u0002J\u0010\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\u0015H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/catrobat/catroid/codeanalysis/UnreachableCodeRule;", "Lorg/catrobat/catroid/codeanalysis/AnalysisRule;", "context2", "Landroid/content/Context;", "(Landroid/content/Context;)V", "analyze", "Lorg/catrobat/catroid/codeanalysis/AnalysisResult;", "brick", "Lorg/catrobat/catroid/content/bricks/Brick;", "context", "Lorg/catrobat/catroid/codeanalysis/GlobalAnalysisContext;", "containsBreak", "", "compositeBrick", "Lorg/catrobat/catroid/content/bricks/CompositeBrick;", ConfigConstants.CONFIG_KEY_DEPTH, "", "endsWithHaltInList", "bricks", "", "isHaltingStopScript", "Lorg/catrobat/catroid/content/bricks/StopScriptBrick;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UnreachableCodeRule implements AnalysisRule {
    private final Context context2;

    public UnreachableCodeRule(Context context2) {
        Intrinsics.checkNotNullParameter(context2, "context2");
        this.context2 = context2;
    }

    @Override // org.catrobat.catroid.codeanalysis.AnalysisRule
    public AnalysisResult analyze(Brick brick, GlobalAnalysisContext context) {
        List<Brick> dragAndDropTargetList;
        int iIndexOf;
        Formula formulaWithBrickField;
        Intrinsics.checkNotNullParameter(brick, "brick");
        Intrinsics.checkNotNullParameter(context, "context");
        Brick parent = brick.getParent();
        if (parent != null && (dragAndDropTargetList = parent.getDragAndDropTargetList()) != null && (iIndexOf = dragAndDropTargetList.indexOf(brick)) > 0) {
            Brick brick2 = dragAndDropTargetList.get(iIndexOf - 1);
            if ((brick2 instanceof StopScriptBrick) && isHaltingStopScript((StopScriptBrick) brick2)) {
                Severity severity = Severity.ERROR;
                String string = this.context2.getString(R.string.analysis_unreachable_stop_script);
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                return new AnalysisResult(severity, string);
            }
            if ((brick2 instanceof ForeverBrick) && !containsBreak$default(this, (CompositeBrick) brick2, 0, 2, null)) {
                Severity severity2 = Severity.WARNING;
                String string2 = this.context2.getString(R.string.analysis_unreachable_after_forever);
                Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                return new AnalysisResult(severity2, string2);
            }
            if (brick2 instanceof IfLogicBeginBrick) {
                IfLogicBeginBrick ifLogicBeginBrick = (IfLogicBeginBrick) brick2;
                if (endsWithHaltInList(ifLogicBeginBrick.getNestedBricks()) && endsWithHaltInList(ifLogicBeginBrick.getSecondaryNestedBricks())) {
                    Severity severity3 = Severity.ERROR;
                    String string3 = this.context2.getString(R.string.analysis_unreachable_both_branches_halt);
                    Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
                    return new AnalysisResult(severity3, string3);
                }
            }
            if ((brick2 instanceof RepeatUntilBrick) && (formulaWithBrickField = ((RepeatUntilBrick) brick2).getFormulaWithBrickField(Brick.BrickField.REPEAT_UNTIL_CONDITION)) != null) {
                Map<String, StaticValue> mapEmptyMap = context.getIncomingStates().get(brick2);
                if (mapEmptyMap == null) {
                    mapEmptyMap = MapsKt.emptyMap();
                }
                StaticValue staticValueEvaluateFormula = context.evaluateFormula(formulaWithBrickField, mapEmptyMap);
                if ((staticValueEvaluateFormula instanceof StaticValue.Bool) && !((StaticValue.Bool) staticValueEvaluateFormula).getValue() && !containsBreak$default(this, (CompositeBrick) brick2, 0, 2, null)) {
                    Severity severity4 = Severity.WARNING;
                    String string4 = this.context2.getString(R.string.analysis_unreachable_after_forever);
                    Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
                    return new AnalysisResult(severity4, string4);
                }
            }
        }
        return null;
    }

    private final boolean isHaltingStopScript(StopScriptBrick brick) {
        try {
            Field declaredField = brick.getClass().getDeclaredField("spinnerSelection");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(brick);
            Integer num = obj instanceof Integer ? (Integer) obj : null;
            if (num == null) {
                return true;
            }
            int iIntValue = num.intValue();
            int iIntValue2 = 2;
            try {
                Field declaredField2 = Class.forName("org.catrobat.catroid.common.BrickValues").getDeclaredField("STOP_OTHER_SCRIPTS");
                declaredField2.setAccessible(true);
                Object obj2 = declaredField2.get(null);
                Integer num2 = obj2 instanceof Integer ? (Integer) obj2 : null;
                if (num2 != null) {
                    iIntValue2 = num2.intValue();
                }
            } catch (Exception unused) {
            }
            return iIntValue != iIntValue2;
        } catch (Exception unused2) {
            return true;
        }
    }

    private final boolean endsWithHaltInList(List<? extends Brick> bricks) {
        if (bricks != null && !bricks.isEmpty()) {
            Brick brick = (Brick) CollectionsKt.last((List) bricks);
            if ((brick instanceof StopScriptBrick) && isHaltingStopScript((StopScriptBrick) brick)) {
                return true;
            }
            if ((brick instanceof CompositeBrick) && !(brick instanceof IfThenLogicBeginBrick)) {
                if (brick instanceof IfLogicBeginBrick) {
                    IfLogicBeginBrick ifLogicBeginBrick = (IfLogicBeginBrick) brick;
                    return endsWithHaltInList(ifLogicBeginBrick.getNestedBricks()) && endsWithHaltInList(ifLogicBeginBrick.getSecondaryNestedBricks());
                }
                if (brick instanceof ForeverBrick) {
                    return !containsBreak$default(this, (CompositeBrick) brick, 0, 2, null);
                }
            }
        }
        return false;
    }

    static /* synthetic */ boolean containsBreak$default(UnreachableCodeRule unreachableCodeRule, CompositeBrick compositeBrick, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        return unreachableCodeRule.containsBreak(compositeBrick, i);
    }

    private final boolean containsBreak(CompositeBrick compositeBrick, int depth) {
        List<Brick> secondaryNestedBricks;
        if (depth > 50) {
            return false;
        }
        List<Brick> nestedBricks = compositeBrick.getNestedBricks();
        if (nestedBricks != null) {
            for (Brick brick : nestedBricks) {
                if ((brick instanceof StopScriptBrick) && isHaltingStopScript((StopScriptBrick) brick)) {
                    return true;
                }
                if ((brick instanceof CompositeBrick) && containsBreak((CompositeBrick) brick, depth + 1)) {
                    return true;
                }
            }
        }
        if (compositeBrick.hasSecondaryList() && (secondaryNestedBricks = compositeBrick.getSecondaryNestedBricks()) != null) {
            for (Brick brick2 : secondaryNestedBricks) {
                if ((brick2 instanceof StopScriptBrick) && isHaltingStopScript((StopScriptBrick) brick2)) {
                    return true;
                }
                if ((brick2 instanceof CompositeBrick) && containsBreak((CompositeBrick) brick2, depth + 1)) {
                    return true;
                }
            }
        }
        return false;
    }
}
