package org.jetbrains.kotlin.cli.common.repl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;

/* JADX INFO: compiled from: GenericReplEvaluator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\b\u0012\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0007H\u0016J\u0018\u0010\u0011\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0007H\u0016J.\u0010\u0012\u001a\u001a\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0014\u0012\u0006\u0012\u0004\u0018\u00010\u000f\u0018\u00010\u00132\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0016H\u0016J2\u0010\u0017\u001a\u0016\u0012\u0004\u0012\u00020\u0018\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u00190\u00132\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J$\u0010\u001c\u001a\u0016\u0012\u0004\u0012\u00020\u0018\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u00190\u00132\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/repl/HistoryActionsForNoRepeat;", "", "state", "Lorg/jetbrains/kotlin/cli/common/repl/GenericReplEvaluatorState;", "(Lorg/jetbrains/kotlin/cli/common/repl/GenericReplEvaluatorState;)V", "effectiveHistory", "", "Lorg/jetbrains/kotlin/cli/common/repl/EvalClassWithInstanceAndLoader;", "getEffectiveHistory", "()Ljava/util/List;", "getState", "()Lorg/jetbrains/kotlin/cli/common/repl/GenericReplEvaluatorState;", "addFinal", "", "lineId", "Lorg/jetbrains/kotlin/cli/common/repl/ILineId;", "value", "addPlaceholder", "firstMismatch", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplHistoryRecord;", "other", "Lkotlin/sequences/Sequence;", "prependClassLoaderWithNewClasses", "Ljava/lang/ClassLoader;", "Ljava/lang/Class;", "compileResult", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCompileResult$CompiledClasses;", "processClasses", "removePlaceholder", "", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
class HistoryActionsForNoRepeat {
    private final GenericReplEvaluatorState state;

    public HistoryActionsForNoRepeat(GenericReplEvaluatorState state) {
        Intrinsics.checkNotNullParameter(state, "state");
        this.state = state;
    }

    public final GenericReplEvaluatorState getState() {
        return this.state;
    }

    public List<EvalClassWithInstanceAndLoader> getEffectiveHistory() {
        IReplStageHistory<EvalClassWithInstanceAndLoader> history = this.state.getHistory();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(history, 10));
        Iterator<EvalClassWithInstanceAndLoader> it = history.iterator();
        while (it.hasNext()) {
            arrayList.add((EvalClassWithInstanceAndLoader) ((ReplHistoryRecord) it.next()).getItem());
        }
        return arrayList;
    }

    public Pair<ReplHistoryRecord<EvalClassWithInstanceAndLoader>, ILineId> firstMismatch(Sequence<? extends ILineId> other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return ReplStateKt.firstMismatch(this.state.getHistory(), other);
    }

    public void addPlaceholder(ILineId lineId, EvalClassWithInstanceAndLoader value) {
        Intrinsics.checkNotNullParameter(lineId, "lineId");
        Intrinsics.checkNotNullParameter(value, "value");
        this.state.getHistory().push(lineId, value);
    }

    public boolean removePlaceholder(ILineId lineId) {
        Intrinsics.checkNotNullParameter(lineId, "lineId");
        return this.state.getHistory().verifiedPop(lineId) != null;
    }

    public void addFinal(ILineId lineId, EvalClassWithInstanceAndLoader value) {
        Intrinsics.checkNotNullParameter(lineId, "lineId");
        Intrinsics.checkNotNullParameter(value, "value");
        this.state.getHistory().push(lineId, value);
    }

    public Pair<ClassLoader, Class<? extends Object>> processClasses(ReplCompileResult.CompiledClasses compileResult) {
        Intrinsics.checkNotNullParameter(compileResult, "compileResult");
        return prependClassLoaderWithNewClasses(getEffectiveHistory(), compileResult);
    }

    /* JADX WARN: Code duplicated, block: B:22:0x00a0  */
    private final Pair<ClassLoader, Class<? extends Object>> prependClassLoaderWithNewClasses(List<EvalClassWithInstanceAndLoader> effectiveHistory, ReplCompileResult.CompiledClasses compileResult) throws Exception {
        ReplClassLoader topClassLoader;
        EvalClassWithInstanceAndLoader evalClassWithInstanceAndLoader = (EvalClassWithInstanceAndLoader) CollectionsKt.lastOrNull((List) effectiveHistory);
        if (evalClassWithInstanceAndLoader == null || (topClassLoader = evalClassWithInstanceAndLoader.getClassLoader()) == null) {
            topClassLoader = this.state.getTopClassLoader();
        }
        ReplClassLoader replClassLoaderMakeReplClassLoader = GenericEvaluatorStateKt.makeReplClassLoader(topClassLoader, compileResult.getClasspathAddendum());
        String mainClassName = compileResult.getMainClassName();
        List<CompiledClassData> classes = compileResult.getClasses();
        ArrayList<CompiledClassData> arrayList = new ArrayList();
        for (Object obj : classes) {
            if (StringsKt.endsWith$default(((CompiledClassData) obj).getPath(), ".class", false, 2, (Object) null)) {
                arrayList.add(obj);
            }
        }
        String strReplace$default = null;
        for (CompiledClassData compiledClassData : arrayList) {
            JvmClassName jvmClassNamePrependClassLoaderWithNewClasses$classNameFromPath = prependClassLoaderWithNewClasses$classNameFromPath(compiledClassData.getPath());
            if (Intrinsics.areEqual(jvmClassNamePrependClassLoaderWithNewClasses$classNameFromPath.getInternalName(), mainClassName)) {
                String internalName = jvmClassNamePrependClassLoaderWithNewClasses$classNameFromPath.getInternalName();
                Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
                strReplace$default = StringsKt.replace$default(internalName, '/', '.', false, 4, (Object) null);
            } else {
                String internalName2 = jvmClassNamePrependClassLoaderWithNewClasses$classNameFromPath.getInternalName();
                Intrinsics.checkNotNullExpressionValue(internalName2, "getInternalName(...)");
                if (StringsKt.endsWith$default(internalName2, "/" + mainClassName, false, 2, (Object) null)) {
                    String internalName3 = jvmClassNamePrependClassLoaderWithNewClasses$classNameFromPath.getInternalName();
                    Intrinsics.checkNotNullExpressionValue(internalName3, "getInternalName(...)");
                    strReplace$default = StringsKt.replace$default(internalName3, '/', '.', false, 4, (Object) null);
                }
            }
            replClassLoaderMakeReplClassLoader.addClass(jvmClassNamePrependClassLoaderWithNewClasses$classNameFromPath, compiledClassData.getBytes());
        }
        try {
            Intrinsics.checkNotNull(strReplace$default);
            return new Pair<>(replClassLoaderMakeReplClassLoader, replClassLoaderMakeReplClassLoader.loadClass(strReplace$default));
        } catch (Throwable th) {
            throw new Exception("Error loading class " + strReplace$default + ": known classes: " + prependClassLoaderWithNewClasses$compiledClassesNames(compileResult), th);
        }
    }

    private static final JvmClassName prependClassLoaderWithNewClasses$classNameFromPath(String str) {
        JvmClassName jvmClassNameByInternalName = JvmClassName.byInternalName(StringsKt.removeSuffix(str, (CharSequence) ".class"));
        Intrinsics.checkNotNullExpressionValue(jvmClassNameByInternalName, "byInternalName(...)");
        return jvmClassNameByInternalName;
    }

    private static final List<String> prependClassLoaderWithNewClasses$compiledClassesNames(ReplCompileResult.CompiledClasses compiledClasses) {
        List<CompiledClassData> classes = compiledClasses.getClasses();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(classes, 10));
        Iterator<T> it = classes.iterator();
        while (it.hasNext()) {
            String internalName = prependClassLoaderWithNewClasses$classNameFromPath(((CompiledClassData) it.next()).getPath()).getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            arrayList.add(StringsKt.replace$default(internalName, '/', '.', false, 4, (Object) null));
        }
        return arrayList;
    }
}
