package org.catrobat.catroid.codeanalysis;

import android.content.Context;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.CompositeBrick;
import org.catrobat.catroid.content.bricks.TryCatchFinallyBrick;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/codeanalysis/CodeAnalyzer.class
  classes12.dex
 */
/* JADX INFO: compiled from: CodeAnalyzer.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J:\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00062\u0006\u0010\f\u001a\u00020\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J$\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00100\u00142\u0006\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\f\u001a\u00020\rJ4\u0010\u0017\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/catrobat/catroid/codeanalysis/CodeAnalyzer;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "rules", "", "Lorg/catrobat/catroid/codeanalysis/AnalysisRule;", "analyzeBrickList", "", "brickList", "Lorg/catrobat/catroid/content/bricks/Brick;", "globalContext", "Lorg/catrobat/catroid/codeanalysis/GlobalAnalysisContext;", "results", "", "Lorg/catrobat/catroid/codeanalysis/AnalysisResult;", "isParentCommented", "", "analyzeScript", "", "script", "Lorg/catrobat/catroid/content/Script;", "traverseNestedBricks", "brick", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CodeAnalyzer {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Context context;
    private final List<AnalysisRule> rules;

    public CodeAnalyzer(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.rules = CollectionsKt.listOf((Object[]) new AnalysisRule[]{new ErrorRule(context), new EmptyLoopRule(context), new ConstantConditionRule(context), new UnreachableCodeRule(context), new RedundantBlockRule(context), new ResourceLeakRule(context), new InvalidCloneUsageRule(context), new ParameterValidationRule(context), new UndefinedReferenceRule(context), new UnusedVariableRule(context), new DeadScriptRule(context), new ThreedCompatibilityRule(context), new EventCascadeRule(context), new DivisionByZeroRule(context)});
    }

    public static /* synthetic */ Map analyzeScript$default(CodeAnalyzer codeAnalyzer, Script script, GlobalAnalysisContext globalAnalysisContext, int i, Object obj) {
        if ((i & 2) != 0) {
            globalAnalysisContext = GlobalAnalysisContext.INSTANCE.build();
        }
        return codeAnalyzer.analyzeScript(script, globalAnalysisContext);
    }

    public final Map<Brick, AnalysisResult> analyzeScript(Script script, GlobalAnalysisContext globalContext) {
        Brick brick;
        Intrinsics.checkNotNullParameter(script, "script");
        Intrinsics.checkNotNullParameter(globalContext, "globalContext");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        try {
            List<Brick> brickList = script.getBrickList();
            Intrinsics.checkNotNullExpressionValue(brickList, "getBrickList(...)");
            analyzeBrickList(brickList, globalContext, linkedHashMap, false);
            ArrayList arrayList = new ArrayList();
            script.addToFlatList(arrayList);
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : arrayList) {
                if (INSTANCE.isBrickCommentedDirectly((Brick) obj)) {
                    arrayList2.add(obj);
                }
            }
            ArrayList arrayList3 = arrayList2;
            if (arrayList3.size() >= 100 && (brick = (Brick) CollectionsKt.firstOrNull((List) arrayList3)) != null) {
                Severity severity = Severity.WARNING;
                String string = this.context.getString(R.string.analysis_too_many_commented_bricks, Integer.valueOf(arrayList3.size()));
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                linkedHashMap.put(brick, new AnalysisResult(severity, string));
            }
        } catch (Throwable unused) {
        }
        return linkedHashMap;
    }

    private final void analyzeBrickList(List<? extends Brick> brickList, GlobalAnalysisContext globalContext, Map<Brick, AnalysisResult> results, boolean isParentCommented) {
        for (Brick brick : brickList) {
            if (isParentCommented || INSTANCE.isBrickCommentedDirectly(brick)) {
                traverseNestedBricks(brick, globalContext, results, true);
            } else {
                Iterator<AnalysisRule> it = this.rules.iterator();
                while (it.hasNext()) {
                    AnalysisResult analysisResultAnalyze = it.next().analyze(brick, globalContext);
                    if (analysisResultAnalyze != null) {
                        results.put(brick, analysisResultAnalyze);
                        break;
                    }
                }
                traverseNestedBricks(brick, globalContext, results, false);
            }
        }
    }

    private final void traverseNestedBricks(Brick brick, GlobalAnalysisContext globalContext, Map<Brick, AnalysisResult> results, boolean isParentCommented) {
        List<Brick> thirdNestedBricks;
        List<Brick> secondaryNestedBricks;
        if (brick instanceof CompositeBrick) {
            CompositeBrick compositeBrick = (CompositeBrick) brick;
            List<Brick> nestedBricks = compositeBrick.getNestedBricks();
            if (nestedBricks != null) {
                analyzeBrickList(nestedBricks, globalContext, results, isParentCommented);
            }
            if (compositeBrick.hasSecondaryList() && (secondaryNestedBricks = compositeBrick.getSecondaryNestedBricks()) != null) {
                analyzeBrickList(secondaryNestedBricks, globalContext, results, isParentCommented);
            }
            if (!(brick instanceof TryCatchFinallyBrick) || (thirdNestedBricks = ((TryCatchFinallyBrick) brick).getThirdNestedBricks()) == null) {
                return;
            }
            analyzeBrickList(thirdNestedBricks, globalContext, results, isParentCommented);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/codeanalysis/CodeAnalyzer$Companion.class
      classes12.dex
     */
    /* JADX INFO: compiled from: CodeAnalyzer.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006J\u0010\u0010\u0007\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¨\u0006\b"}, d2 = {"Lorg/catrobat/catroid/codeanalysis/CodeAnalyzer$Companion;", "", "()V", "isBrickCommented", "", "brick", "Lorg/catrobat/catroid/content/bricks/Brick;", "isBrickCommentedDirectly", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean isBrickCommentedDirectly(Brick brick) {
            if (brick == null) {
                return false;
            }
            try {
                try {
                    Object objInvoke = brick.getClass().getMethod("isCommentedOut", new Class[0]).invoke(brick, new Object[0]);
                    Boolean bool = objInvoke instanceof Boolean ? (Boolean) objInvoke : null;
                    if (bool != null) {
                        return bool.booleanValue();
                    }
                    return false;
                } catch (Exception unused) {
                    return false;
                }
            } catch (Exception unused2) {
                Field field = brick.getClass().getField("commentedOut");
                field.setAccessible(true);
                Object obj = field.get(brick);
                Boolean bool2 = obj instanceof Boolean ? (Boolean) obj : null;
                if (bool2 != null) {
                    return bool2.booleanValue();
                }
                return false;
            }
            return false;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v2 */
        /* JADX WARN: Type inference failed for: r2v6, types: [java.lang.reflect.Method] */
        /* JADX WARN: Type inference failed for: r2v8, types: [java.lang.reflect.Field] */
        /* JADX WARN: Type inference failed for: r6v0, types: [org.catrobat.catroid.content.bricks.Brick] */
        /* JADX WARN: Type inference failed for: r6v1, types: [java.lang.Object] */
        /* JADX WARN: Type inference failed for: r6v2 */
        /* JADX WARN: Type inference failed for: r6v4, types: [java.lang.Object] */
        /* JADX WARN: Type inference failed for: r6v5 */
        /* JADX WARN: Type inference failed for: r6v6, types: [java.lang.Object] */
        public final boolean isBrickCommented(Brick brick) {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            while (brick != 0 && linkedHashSet.add(brick) && (brick instanceof Brick)) {
                if (isBrickCommentedDirectly((Brick) brick)) {
                    return true;
                }
                try {
                    try {
                        ?? field = brick.getClass().getField("parent");
                        field.setAccessible(true);
                        brick = field.get(brick);
                    } catch (Exception unused) {
                        brick = 0;
                    }
                } catch (Exception unused2) {
                    brick = brick.getClass().getMethod("getParent", new Class[0]).invoke(brick, new Object[0]);
                }
            }
            return false;
        }
    }
}
