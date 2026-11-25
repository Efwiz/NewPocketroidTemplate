package org.catrobat.catroid.codeanalysis;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.BroadcastBrick;
import org.catrobat.catroid.content.bricks.BroadcastReceiverBrick;
import org.catrobat.catroid.content.bricks.BroadcastWaitBrick;
import org.catrobat.catroid.content.bricks.ChangeVariableBrick;
import org.catrobat.catroid.content.bricks.CompositeBrick;
import org.catrobat.catroid.content.bricks.EnablePbrRenderBrick;
import org.catrobat.catroid.content.bricks.FormulaBrick;
import org.catrobat.catroid.content.bricks.IfLogicBeginBrick;
import org.catrobat.catroid.content.bricks.SetVariableBrick;
import org.catrobat.catroid.content.bricks.TryCatchFinallyBrick;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.formulaeditor.Operators;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/codeanalysis/GlobalAnalysisContext.class
  classes12.dex
 */
/* JADX INFO: compiled from: GlobalAnalysisContext.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0004\u0018\u0000 /2\u00020\u0001:\u0001/B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\fH\u0002J.\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u001d2\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000e0\r2\b\b\u0002\u0010\u001f\u001a\u00020 H\u0002J\"\u0010!\u001a\u00020\u000e2\u0006\u0010\"\u001a\u00020#2\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000e0\rJ<\u0010$\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000e0\r2\u0012\u0010%\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000e0\r2\u0012\u0010&\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000e0\rH\u0002J\u0010\u0010'\u001a\u00020\u00192\u0006\u0010(\u001a\u00020)H\u0002JB\u0010*\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000e0\r2\u000e\u0010+\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010,2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000e0\r2\b\b\u0002\u0010\u001f\u001a\u00020 H\u0002J\u001a\u0010.\u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u001d2\b\b\u0002\u0010\u001f\u001a\u00020 H\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007R)\u0010\n\u001a\u001a\u0012\u0004\u0012\u00020\f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000e0\r0\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0007¨\u00060"}, d2 = {"Lorg/catrobat/catroid/codeanalysis/GlobalAnalysisContext;", "", "()V", "allBroadcastsReceived", "", "", "getAllBroadcastsReceived", "()Ljava/util/Set;", "allBroadcastsSent", "getAllBroadcastsSent", "incomingStates", "", "Lorg/catrobat/catroid/content/bricks/Brick;", "", "Lorg/catrobat/catroid/codeanalysis/StaticValue;", "getIncomingStates", "()Ljava/util/Map;", "isPbrEnabledInProject", "", "()Z", "setPbrEnabledInProject", "(Z)V", "variablesRead", "getVariablesRead", "collectVariableReads", "", "brick", "evaluateElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/catrobat/catroid/formulaeditor/FormulaElement;", "state", ConfigConstants.CONFIG_KEY_DEPTH, "", "evaluateFormula", "formula", "Lorg/catrobat/catroid/formulaeditor/Formula;", "mergeStates", "s1", "s2", "propagateConstantsInScript", "script", "Lorg/catrobat/catroid/content/Script;", "propagateInBrickList", "bricks", "", "initialState", "traverseAndCollectReads", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class GlobalAnalysisContext {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private boolean isPbrEnabledInProject;
    private final Map<Brick, Map<String, StaticValue>> incomingStates = new LinkedHashMap();
    private final Set<String> allBroadcastsSent = new LinkedHashSet();
    private final Set<String> variablesRead = new LinkedHashSet();
    private final Set<String> allBroadcastsReceived = new LinkedHashSet();

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/codeanalysis/GlobalAnalysisContext$WhenMappings.class
      classes12.dex
     */
    /* JADX INFO: compiled from: GlobalAnalysisContext.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FormulaElement.ElementType.values().length];
            try {
                iArr[FormulaElement.ElementType.NUMBER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[FormulaElement.ElementType.STRING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[FormulaElement.ElementType.USER_VARIABLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[FormulaElement.ElementType.OPERATOR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[FormulaElement.ElementType.FUNCTION.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public final Map<Brick, Map<String, StaticValue>> getIncomingStates() {
        return this.incomingStates;
    }

    /* JADX INFO: renamed from: isPbrEnabledInProject, reason: from getter */
    public final boolean getIsPbrEnabledInProject() {
        return this.isPbrEnabledInProject;
    }

    public final void setPbrEnabledInProject(boolean z) {
        this.isPbrEnabledInProject = z;
    }

    public final Set<String> getAllBroadcastsSent() {
        return this.allBroadcastsSent;
    }

    public final Set<String> getVariablesRead() {
        return this.variablesRead;
    }

    public final Set<String> getAllBroadcastsReceived() {
        return this.allBroadcastsReceived;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/codeanalysis/GlobalAnalysisContext$Companion.class
      classes12.dex
     */
    /* JADX INFO: compiled from: GlobalAnalysisContext.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"Lorg/catrobat/catroid/codeanalysis/GlobalAnalysisContext$Companion;", "", "()V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/catrobat/catroid/codeanalysis/GlobalAnalysisContext;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final GlobalAnalysisContext build() {
            String broadcastMessage;
            String broadcastMessage2;
            String broadcastMessage3;
            GlobalAnalysisContext globalAnalysisContext = new GlobalAnalysisContext();
            Project currentProject = ProjectManager.getInstance().getCurrentProject();
            if (currentProject == null) {
                return globalAnalysisContext;
            }
            try {
                List<Scene> sceneList = currentProject.getSceneList();
                if (sceneList == null) {
                    return globalAnalysisContext;
                }
                Iterator<Scene> it = sceneList.iterator();
                while (it.hasNext()) {
                    List<Sprite> spriteList = it.next().getSpriteList();
                    if (spriteList != null) {
                        Iterator<Sprite> it2 = spriteList.iterator();
                        while (it2.hasNext()) {
                            List<Script> scriptList = it2.next().getScriptList();
                            if (scriptList != null) {
                                ArrayList<Brick> arrayList = new ArrayList();
                                Iterator<Script> it3 = scriptList.iterator();
                                while (it3.hasNext()) {
                                    it3.next().addToFlatList(arrayList);
                                }
                                for (Brick brick : arrayList) {
                                    if (!CodeAnalyzer.INSTANCE.isBrickCommented(brick)) {
                                        if (brick instanceof EnablePbrRenderBrick) {
                                            globalAnalysisContext.setPbrEnabledInProject(true);
                                        }
                                        if ((brick instanceof BroadcastReceiverBrick) && (broadcastMessage3 = ((BroadcastReceiverBrick) brick).getBroadcastMessage()) != null) {
                                            globalAnalysisContext.getAllBroadcastsReceived().add(broadcastMessage3);
                                        }
                                        if ((brick instanceof BroadcastBrick) && (broadcastMessage2 = ((BroadcastBrick) brick).getBroadcastMessage()) != null) {
                                            globalAnalysisContext.getAllBroadcastsSent().add(broadcastMessage2);
                                        }
                                        if ((brick instanceof BroadcastWaitBrick) && (broadcastMessage = ((BroadcastWaitBrick) brick).getBroadcastMessage()) != null) {
                                            globalAnalysisContext.getAllBroadcastsSent().add(broadcastMessage);
                                        }
                                        globalAnalysisContext.collectVariableReads(brick);
                                    }
                                }
                            }
                        }
                    }
                }
                Iterator<Scene> it4 = sceneList.iterator();
                while (it4.hasNext()) {
                    List<Sprite> spriteList2 = it4.next().getSpriteList();
                    if (spriteList2 != null) {
                        Iterator<Sprite> it5 = spriteList2.iterator();
                        while (it5.hasNext()) {
                            List<Script> scriptList2 = it5.next().getScriptList();
                            if (scriptList2 != null) {
                                for (Script script : scriptList2) {
                                    Intrinsics.checkNotNull(script);
                                    globalAnalysisContext.propagateConstantsInScript(script);
                                }
                            }
                        }
                    }
                }
            } catch (Throwable unused) {
            }
            return globalAnalysisContext;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void collectVariableReads(Brick brick) {
        if (brick instanceof FormulaBrick) {
            Iterator<Formula> it = ((FormulaBrick) brick).getAllFormulaFieldsWithFormulas().values().iterator();
            while (it.hasNext()) {
                FormulaElement root = it.next().getRoot();
                if (root != null) {
                    traverseAndCollectReads$default(this, root, 0, 2, null);
                }
            }
        }
        try {
            Object objInvoke = brick.getClass().getMethod("getUserVariable", new Class[0]).invoke(brick, new Object[0]);
            UserVariable userVariable = objInvoke instanceof UserVariable ? (UserVariable) objInvoke : null;
            if (userVariable == null || (brick instanceof SetVariableBrick)) {
                return;
            }
            Set<String> set = this.variablesRead;
            String name2 = userVariable.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            set.add(name2);
        } catch (Exception unused) {
        }
    }

    static /* synthetic */ void traverseAndCollectReads$default(GlobalAnalysisContext globalAnalysisContext, FormulaElement formulaElement, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        globalAnalysisContext.traverseAndCollectReads(formulaElement, i);
    }

    private final void traverseAndCollectReads(FormulaElement element, int depth) {
        String value;
        String string;
        if (depth > 50) {
            return;
        }
        if (StringsKt.contains$default((CharSequence) element.type.name(), (CharSequence) "VAR", false, 2, (Object) null) && (value = element.getValue()) != null && (string = value.toString()) != null) {
            this.variablesRead.add(string);
        }
        FormulaElement formulaElement = element.leftChild;
        if (formulaElement != null) {
            traverseAndCollectReads(formulaElement, depth + 1);
        }
        FormulaElement formulaElement2 = element.rightChild;
        if (formulaElement2 != null) {
            traverseAndCollectReads(formulaElement2, depth + 1);
        }
        for (FormulaElement formulaElement3 : element.additionalChildren) {
            Intrinsics.checkNotNull(formulaElement3);
            traverseAndCollectReads(formulaElement3, depth + 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void propagateConstantsInScript(Script script) {
        propagateInBrickList$default(this, script.getBrickList(), MapsKt.emptyMap(), 0, 4, null);
    }

    static /* synthetic */ Map propagateInBrickList$default(GlobalAnalysisContext globalAnalysisContext, List list, Map map, int i, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            i = 0;
        }
        return globalAnalysisContext.propagateInBrickList(list, map, i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v0, types: [org.catrobat.catroid.codeanalysis.GlobalAnalysisContext] */
    /* JADX WARN: Type inference failed for: r9v0, types: [java.util.Map, java.util.Map<java.lang.String, ? extends org.catrobat.catroid.codeanalysis.StaticValue>] */
    /* JADX WARN: Type inference failed for: r9v1, types: [java.util.Map<java.lang.String, org.catrobat.catroid.codeanalysis.StaticValue>] */
    /* JADX WARN: Type inference failed for: r9v10, types: [java.util.Map] */
    /* JADX WARN: Type inference failed for: r9v12, types: [java.util.Map] */
    /* JADX WARN: Type inference failed for: r9v14 */
    /* JADX WARN: Type inference failed for: r9v15 */
    /* JADX WARN: Type inference failed for: r9v2, types: [java.util.Map] */
    /* JADX WARN: Type inference failed for: r9v3, types: [java.lang.Object, java.util.Map] */
    /* JADX WARN: Type inference failed for: r9v4 */
    /* JADX WARN: Type inference failed for: r9v5, types: [java.util.Map] */
    /* JADX WARN: Type inference failed for: r9v6, types: [java.util.Map] */
    /* JADX WARN: Type inference failed for: r9v7, types: [java.util.Map] */
    /* JADX WARN: Type inference failed for: r9v8, types: [java.util.Map] */
    /* JADX WARN: Type inference failed for: r9v9, types: [java.util.Map] */
    private final Map<String, StaticValue> propagateInBrickList(List<? extends Brick> bricks, Map<String, ? extends StaticValue> initialState, int depth) {
        String name2;
        if (bricks != null && depth <= 50) {
            initialState = MapsKt.toMap((Map) initialState);
            for (Brick brick : bricks) {
                this.incomingStates.put(brick, (Map<String, StaticValue>) initialState);
                if (!CodeAnalyzer.INSTANCE.isBrickCommented(brick)) {
                    if (brick instanceof SetVariableBrick) {
                        SetVariableBrick setVariableBrick = (SetVariableBrick) brick;
                        UserVariable userVariable = setVariableBrick.getUserVariable();
                        name2 = userVariable != null ? userVariable.getName() : null;
                        if (name2 != null) {
                            Formula formula = (Formula) CollectionsKt.firstOrNull(setVariableBrick.getAllFormulaFieldsWithFormulas().values());
                            initialState = MapsKt.plus((Map) initialState, TuplesKt.to(name2, formula != null ? evaluateFormula(formula, initialState) : StaticValue.Dynamic.INSTANCE));
                        }
                    } else if (brick instanceof ChangeVariableBrick) {
                        ChangeVariableBrick changeVariableBrick = (ChangeVariableBrick) brick;
                        UserVariable userVariable2 = changeVariableBrick.getUserVariable();
                        name2 = userVariable2 != null ? userVariable2.getName() : null;
                        if (name2 != null) {
                            StaticValue staticValue = (StaticValue) initialState.get(name2);
                            Formula formula2 = (Formula) CollectionsKt.firstOrNull(changeVariableBrick.getAllFormulaFieldsWithFormulas().values());
                            if ((staticValue instanceof StaticValue.Number) && formula2 != null) {
                                StaticValue staticValueEvaluateFormula = evaluateFormula(formula2, initialState);
                                initialState = staticValueEvaluateFormula instanceof StaticValue.Number ? MapsKt.plus((Map) initialState, TuplesKt.to(name2, new StaticValue.Number(((StaticValue.Number) staticValue).getValue() + ((StaticValue.Number) staticValueEvaluateFormula).getValue()))) : MapsKt.plus((Map) initialState, TuplesKt.to(name2, StaticValue.Dynamic.INSTANCE));
                            } else {
                                initialState = MapsKt.plus((Map) initialState, TuplesKt.to(name2, StaticValue.Dynamic.INSTANCE));
                            }
                        }
                    } else if (brick instanceof CompositeBrick) {
                        if (brick instanceof IfLogicBeginBrick) {
                            IfLogicBeginBrick ifLogicBeginBrick = (IfLogicBeginBrick) brick;
                            int i = depth + 1;
                            Map<String, StaticValue> mapPropagateInBrickList = propagateInBrickList(ifLogicBeginBrick.getNestedBricks(), initialState, i);
                            ?? PropagateInBrickList = initialState;
                            if (((CompositeBrick) brick).hasSecondaryList()) {
                                PropagateInBrickList = propagateInBrickList(ifLogicBeginBrick.getSecondaryNestedBricks(), initialState, i);
                            }
                            initialState = mergeStates(mapPropagateInBrickList, PropagateInBrickList);
                        } else {
                            ArrayList arrayList = new ArrayList();
                            brick.addToFlatList(arrayList);
                            ArrayList arrayList2 = arrayList;
                            ArrayList arrayList3 = new ArrayList();
                            for (Object obj : arrayList2) {
                                if (obj instanceof SetVariableBrick) {
                                    arrayList3.add(obj);
                                }
                            }
                            ArrayList arrayList4 = new ArrayList();
                            Iterator it = arrayList3.iterator();
                            while (it.hasNext()) {
                                UserVariable userVariable3 = ((SetVariableBrick) it.next()).getUserVariable();
                                String name3 = userVariable3 != null ? userVariable3.getName() : null;
                                if (name3 != null) {
                                    arrayList4.add(name3);
                                }
                            }
                            ArrayList arrayList5 = arrayList4;
                            ArrayList arrayList6 = new ArrayList();
                            for (Object obj2 : arrayList2) {
                                if (obj2 instanceof ChangeVariableBrick) {
                                    arrayList6.add(obj2);
                                }
                            }
                            ArrayList arrayList7 = new ArrayList();
                            Iterator it2 = arrayList6.iterator();
                            while (it2.hasNext()) {
                                UserVariable userVariable4 = ((ChangeVariableBrick) it2.next()).getUserVariable();
                                String name4 = userVariable4 != null ? userVariable4.getName() : null;
                                if (name4 != null) {
                                    arrayList7.add(name4);
                                }
                            }
                            List listPlus = CollectionsKt.plus((Collection) arrayList5, (Iterable) arrayList7);
                            initialState = MapsKt.toMutableMap(initialState);
                            Iterator it3 = listPlus.iterator();
                            while (it3.hasNext()) {
                                initialState.put((String) it3.next(), StaticValue.Dynamic.INSTANCE);
                            }
                            CompositeBrick compositeBrick = (CompositeBrick) brick;
                            int i2 = depth + 1;
                            propagateInBrickList(compositeBrick.getNestedBricks(), initialState, i2);
                            if (compositeBrick.hasSecondaryList()) {
                                propagateInBrickList(compositeBrick.getSecondaryNestedBricks(), initialState, i2);
                            }
                            if (brick instanceof TryCatchFinallyBrick) {
                                propagateInBrickList(((TryCatchFinallyBrick) brick).getThirdNestedBricks(), initialState, i2);
                            }
                        }
                    }
                }
            }
        }
        return initialState;
    }

    private final Map<String, StaticValue> mergeStates(Map<String, ? extends StaticValue> s1, Map<String, ? extends StaticValue> s2) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (String str : SetsKt.plus((Set) s1.keySet(), (Iterable) s2.keySet())) {
            StaticValue.Dynamic dynamic = s1.get(str);
            if (dynamic == null) {
                dynamic = StaticValue.Dynamic.INSTANCE;
            }
            StaticValue.Dynamic dynamic2 = s2.get(str);
            if (dynamic2 == null) {
                dynamic2 = StaticValue.Dynamic.INSTANCE;
            }
            if (Intrinsics.areEqual(dynamic, dynamic2)) {
                linkedHashMap.put(str, dynamic);
            } else {
                linkedHashMap.put(str, StaticValue.Dynamic.INSTANCE);
            }
        }
        return linkedHashMap;
    }

    public final StaticValue evaluateFormula(Formula formula, Map<String, ? extends StaticValue> state) {
        Intrinsics.checkNotNullParameter(formula, "formula");
        Intrinsics.checkNotNullParameter(state, "state");
        FormulaElement root = formula.getRoot();
        return root == null ? StaticValue.Dynamic.INSTANCE : evaluateElement$default(this, root, state, 0, 4, null);
    }

    static /* synthetic */ StaticValue evaluateElement$default(GlobalAnalysisContext globalAnalysisContext, FormulaElement formulaElement, Map map, int i, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            i = 0;
        }
        return globalAnalysisContext.evaluateElement(formulaElement, map, i);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Multi-variable type inference failed */
    private final StaticValue evaluateElement(FormulaElement element, Map<String, ? extends StaticValue> state, int depth) {
        String string;
        String string2;
        StaticValue.Dynamic dynamicEvaluateElement;
        StaticValue.Dynamic dynamicEvaluateElement2;
        StaticValue staticValueEvaluateElement;
        Double dValueOf;
        if (depth > 50) {
            return StaticValue.Dynamic.INSTANCE;
        }
        FormulaElement.ElementType elementType = element.type;
        String value = element.getValue();
        String strName = "";
        if (value == null || (string = value.toString()) == null) {
            string = "";
        }
        int i = elementType == null ? -1 : WhenMappings.$EnumSwitchMapping$0[elementType.ordinal()];
        boolean z = true;
        if (i == 1) {
            Double doubleOrNull = StringsKt.toDoubleOrNull(string);
            return doubleOrNull != null ? new StaticValue.Number(doubleOrNull.doubleValue()) : StaticValue.Dynamic.INSTANCE;
        }
        if (i == 2) {
            return new StaticValue.Str(string);
        }
        if (i == 3) {
            StaticValue staticValue = state.get(string);
            return staticValue == null ? StaticValue.Dynamic.INSTANCE : staticValue;
        }
        StaticValue staticValueEvaluateElement2 = null;
        dValueOf = null;
        dValueOf = null;
        dValueOf = null;
        Double dValueOf2 = null;
        if (i == 4) {
            String value2 = element.getValue();
            if (value2 instanceof Operators) {
                strName = ((Operators) value2).name();
            } else if (value2 instanceof Enum) {
                strName = ((Enum) value2).name();
            } else if (value2 != null && (string2 = value2.toString()) != null) {
                String upperCase = string2.toUpperCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
                if (upperCase != null) {
                    strName = upperCase;
                }
            }
            if (Intrinsics.areEqual(strName, "LOGICAL_NOT")) {
                FormulaElement formulaElement = element.leftChild;
                if (formulaElement == null || (staticValueEvaluateElement = evaluateElement(formulaElement, state, depth + 1)) == null) {
                    FormulaElement formulaElement2 = element.rightChild;
                    if (formulaElement2 != null) {
                        staticValueEvaluateElement2 = evaluateElement(formulaElement2, state, depth + 1);
                    }
                } else {
                    staticValueEvaluateElement2 = staticValueEvaluateElement;
                }
                return staticValueEvaluateElement2 instanceof StaticValue.Bool ? new StaticValue.Bool(!((StaticValue.Bool) staticValueEvaluateElement2).getValue()) : StaticValue.Dynamic.INSTANCE;
            }
            FormulaElement formulaElement3 = element.leftChild;
            if (formulaElement3 == null || (dynamicEvaluateElement = evaluateElement(formulaElement3, state, depth + 1)) == null) {
                dynamicEvaluateElement = StaticValue.Dynamic.INSTANCE;
            }
            FormulaElement formulaElement4 = element.rightChild;
            if (formulaElement4 == null || (dynamicEvaluateElement2 = evaluateElement(formulaElement4, state, depth + 1)) == null) {
                dynamicEvaluateElement2 = StaticValue.Dynamic.INSTANCE;
            }
            if (Intrinsics.areEqual(strName, "MINUS") && element.leftChild == null) {
                return dynamicEvaluateElement2 instanceof StaticValue.Number ? new StaticValue.Number(-((StaticValue.Number) dynamicEvaluateElement2).getValue()) : StaticValue.Dynamic.INSTANCE;
            }
            if (!(dynamicEvaluateElement instanceof StaticValue.Number) || !(dynamicEvaluateElement2 instanceof StaticValue.Number)) {
                if (!(dynamicEvaluateElement instanceof StaticValue.Bool) || !(dynamicEvaluateElement2 instanceof StaticValue.Bool)) {
                    return StaticValue.Dynamic.INSTANCE;
                }
                boolean value3 = ((StaticValue.Bool) dynamicEvaluateElement).getValue();
                boolean value4 = ((StaticValue.Bool) dynamicEvaluateElement2).getValue();
                switch (strName.hashCode()) {
                    case -1605945247:
                        if (strName.equals("LOGICAL_AND")) {
                            return new StaticValue.Bool(value3 && value4);
                        }
                        break;
                    case -1575824903:
                        if (strName.equals("LOGICAL_OR")) {
                            if (!value3 && !value4) {
                                z = false;
                            }
                            return new StaticValue.Bool(z);
                        }
                        break;
                    case 66219796:
                        if (strName.equals("EQUAL")) {
                            return new StaticValue.Bool(value3 == value4);
                        }
                        break;
                    case 1022422664:
                        if (strName.equals("NOT_EQUAL")) {
                            return new StaticValue.Bool(value3 != value4);
                        }
                        break;
                }
                return StaticValue.Dynamic.INSTANCE;
            }
            double value5 = ((StaticValue.Number) dynamicEvaluateElement).getValue();
            double value6 = ((StaticValue.Number) dynamicEvaluateElement2).getValue();
            switch (strName.hashCode()) {
                case -1491640893:
                    if (strName.equals("SMALLER_OR_EQUAL")) {
                        return new StaticValue.Bool(value5 <= value6);
                    }
                    break;
                case -57689300:
                    if (strName.equals("SMALLER_THAN")) {
                        return new StaticValue.Bool(value5 < value6);
                    }
                    break;
                case 76514:
                    if (strName.equals("MOD")) {
                        return value6 == 0.0d ? StaticValue.Dynamic.INSTANCE : new StaticValue.Number(value5 % value6);
                    }
                    break;
                case 79416:
                    if (strName.equals("POW")) {
                        double dPow = Math.pow(value5, value6);
                        return (Double.isNaN(dPow) || Double.isInfinite(dPow)) ? StaticValue.Dynamic.INSTANCE : new StaticValue.Number(dPow);
                    }
                    break;
                case 2378032:
                    if (strName.equals("MULT")) {
                        return new StaticValue.Number(value5 * value6);
                    }
                    break;
                case 2459034:
                    if (strName.equals("PLUS")) {
                        return new StaticValue.Number(value5 + value6);
                    }
                    break;
                case 66219796:
                    if (strName.equals("EQUAL")) {
                        return new StaticValue.Bool(value5 == value6);
                    }
                    break;
                case 73363536:
                    if (strName.equals("MINUS")) {
                        return new StaticValue.Number(value5 - value6);
                    }
                    break;
                case 972152550:
                    if (strName.equals("GREATER_THAN")) {
                        return new StaticValue.Bool(value5 > value6);
                    }
                    break;
                case 1022422664:
                    if (strName.equals("NOT_EQUAL")) {
                        return new StaticValue.Bool(!(value5 == value6));
                    }
                    break;
                case 1525486717:
                    if (strName.equals("GREATER_OR_EQUAL")) {
                        return new StaticValue.Bool(value5 >= value6);
                    }
                    break;
                case 2016833657:
                    if (strName.equals("DIVIDE")) {
                        return value6 == 0.0d ? StaticValue.Dynamic.INSTANCE : new StaticValue.Number(value5 / value6);
                    }
                    break;
            }
            return StaticValue.Dynamic.INSTANCE;
        }
        if (i != 5) {
            return StaticValue.Dynamic.INSTANCE;
        }
        String upperCase2 = string.toUpperCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(upperCase2, "toUpperCase(...)");
        FormulaElement formulaElement5 = element.leftChild;
        StaticValue staticValueEvaluateElement3 = formulaElement5 != null ? evaluateElement(formulaElement5, state, depth + 1) : null;
        if (staticValueEvaluateElement3 instanceof StaticValue.Number) {
            double value7 = ((StaticValue.Number) staticValueEvaluateElement3).getValue();
            switch (upperCase2) {
                case "LN":
                    dValueOf = Double.valueOf(Math.log(value7));
                    break;
                case "ABS":
                    dValueOf = Double.valueOf(Math.abs(value7));
                    break;
                case "COS":
                    dValueOf = Double.valueOf(Math.cos(Math.toRadians(value7)));
                    break;
                case "EXP":
                    dValueOf = Double.valueOf(Math.exp(value7));
                    break;
                case "LOG":
                    dValueOf = Double.valueOf(Math.log10(value7));
                    break;
                case "SIN":
                    dValueOf = Double.valueOf(Math.sin(Math.toRadians(value7)));
                    break;
                case "TAN":
                    dValueOf = Double.valueOf(Math.tan(Math.toRadians(value7)));
                    break;
                case "CEIL":
                    dValueOf = Double.valueOf(Math.ceil(value7));
                    break;
                case "SQRT":
                    dValueOf = Double.valueOf(Math.sqrt(value7));
                    break;
                case "FLOOR":
                    dValueOf = Double.valueOf(Math.floor(value7));
                    break;
                case "ROUND":
                    dValueOf = Double.valueOf(Math.round(value7));
                    break;
                default:
                    dValueOf = null;
                    break;
            }
            if (dValueOf != null && !Double.isNaN(dValueOf.doubleValue()) && !Double.isInfinite(dValueOf.doubleValue())) {
                return new StaticValue.Number(dValueOf.doubleValue());
            }
        }
        if (element.leftChild != null && element.rightChild != null) {
            FormulaElement leftChild = element.leftChild;
            Intrinsics.checkNotNullExpressionValue(leftChild, "leftChild");
            int i2 = depth + 1;
            StaticValue staticValueEvaluateElement4 = evaluateElement(leftChild, state, i2);
            FormulaElement rightChild = element.rightChild;
            Intrinsics.checkNotNullExpressionValue(rightChild, "rightChild");
            StaticValue staticValueEvaluateElement5 = evaluateElement(rightChild, state, i2);
            if ((staticValueEvaluateElement4 instanceof StaticValue.Number) && (staticValueEvaluateElement5 instanceof StaticValue.Number)) {
                double value8 = ((StaticValue.Number) staticValueEvaluateElement4).getValue();
                double value9 = ((StaticValue.Number) staticValueEvaluateElement5).getValue();
                int iHashCode = upperCase2.hashCode();
                if (iHashCode != 76100) {
                    if (iHashCode != 76338) {
                        if (iHashCode == 79416 && upperCase2.equals("POW")) {
                            dValueOf2 = Double.valueOf(Math.pow(value8, value9));
                        }
                    } else if (upperCase2.equals("MIN")) {
                        dValueOf2 = Double.valueOf(Math.min(value8, value9));
                    }
                } else if (upperCase2.equals("MAX")) {
                    dValueOf2 = Double.valueOf(Math.max(value8, value9));
                }
                if (dValueOf2 != null) {
                    return new StaticValue.Number(dValueOf2.doubleValue());
                }
            }
        }
        return StaticValue.Dynamic.INSTANCE;
    }
}
