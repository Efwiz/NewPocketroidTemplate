package kotlin.script.experimental.jvm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import kotlin.script.experimental.api.CompiledScript;
import kotlin.script.experimental.api.ErrorHandlingKt;
import kotlin.script.experimental.api.EvaluationResult;
import kotlin.script.experimental.api.ReplEvaluator;
import kotlin.script.experimental.api.ResultValue;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptDiagnostic;
import kotlin.script.experimental.api.ScriptEvaluationConfiguration;
import kotlin.script.experimental.api.ScriptEvaluationKt;
import kotlin.script.experimental.api.ScriptEvaluator;
import kotlin.script.experimental.jvm.util.SnippetsHistory;
import kotlin.script.experimental.util.LinkedSnippet;
import kotlin.script.experimental.util.LinkedSnippetImpl;
import kotlin.script.experimental.util.LinkedSnippetKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: BasicJvmReplEvaluator.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u0012\u0012\b\u0012\u00060\u0002j\u0002`\u0003\u0012\u0004\u0012\u00020\u00040\u0001B\u000f\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J6\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00150\u00142\u0012\u0010\u0016\u001a\u000e\u0012\n\b\u0001\u0012\u00060\u0002j\u0002`\u00030\u00152\u0006\u0010\u0017\u001a\u00020\u0018H\u0096@¢\u0006\u0002\u0010\u0019J\u001c\u0010\u001a\u001a\u00020\u001b2\u0012\u0010\u001c\u001a\u000e\u0012\n\b\u0001\u0012\u00060\u0002j\u0002`\u00030\u0015H\u0002R\"\u0010\b\u001a\u0016\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\tX\u0082\u0004¢\u0006\u0002\n\u0000R.\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\r2\u000e\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\r@RX\u0096\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001d"}, d2 = {"Lkotlin/script/experimental/jvm/BasicJvmReplEvaluator;", "Lkotlin/script/experimental/api/ReplEvaluator;", "Lkotlin/script/experimental/api/CompiledScript;", "Lkotlin/script/experimental/api/CompiledSnippet;", "Lkotlin/script/experimental/jvm/KJvmEvaluatedSnippet;", "scriptEvaluator", "Lkotlin/script/experimental/api/ScriptEvaluator;", "(Lkotlin/script/experimental/api/ScriptEvaluator;)V", "history", "Lkotlin/script/experimental/jvm/util/SnippetsHistory;", "Lkotlin/reflect/KClass;", "", "<set-?>", "Lkotlin/script/experimental/util/LinkedSnippetImpl;", "lastEvaluatedSnippet", "getLastEvaluatedSnippet", "()Lkotlin/script/experimental/util/LinkedSnippetImpl;", "getScriptEvaluator", "()Lkotlin/script/experimental/api/ScriptEvaluator;", "eval", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "Lkotlin/script/experimental/util/LinkedSnippet;", "snippet", "configuration", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "(Lkotlin/script/experimental/util/LinkedSnippet;Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "verifyHistoryConsistency", "", "compiledSnippet", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BasicJvmReplEvaluator implements ReplEvaluator<CompiledScript, KJvmEvaluatedSnippet> {
    private final SnippetsHistory<KClass<?>, Object> history;
    private LinkedSnippetImpl<KJvmEvaluatedSnippet> lastEvaluatedSnippet;
    private final ScriptEvaluator scriptEvaluator;

    /* JADX INFO: renamed from: kotlin.script.experimental.jvm.BasicJvmReplEvaluator$eval$1, reason: invalid class name */
    /* JADX INFO: compiled from: BasicJvmReplEvaluator.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "kotlin.script.experimental.jvm.BasicJvmReplEvaluator", f = "BasicJvmReplEvaluator.kt", i = {0, 0, 0}, l = {49}, m = "eval", n = {"this", "currentConfiguration", "snippetVal"}, s = {"L$0", "L$1", "L$2"})
    static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return BasicJvmReplEvaluator.this.eval(null, null, this);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public BasicJvmReplEvaluator() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public BasicJvmReplEvaluator(ScriptEvaluator scriptEvaluator) {
        Intrinsics.checkNotNullParameter(scriptEvaluator, "scriptEvaluator");
        this.scriptEvaluator = scriptEvaluator;
        this.history = new SnippetsHistory<>(null, 1, null);
    }

    public /* synthetic */ BasicJvmReplEvaluator(BasicJvmScriptEvaluator basicJvmScriptEvaluator, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new BasicJvmScriptEvaluator() : basicJvmScriptEvaluator);
    }

    public final ScriptEvaluator getScriptEvaluator() {
        return this.scriptEvaluator;
    }

    @Override // kotlin.script.experimental.api.ReplEvaluator
    public LinkedSnippetImpl<KJvmEvaluatedSnippet> getLastEvaluatedSnippet() {
        return this.lastEvaluatedSnippet;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    @Override // kotlin.script.experimental.api.ReplEvaluator
    public Object eval(LinkedSnippet<? extends CompiledScript> linkedSnippet, ScriptEvaluationConfiguration scriptEvaluationConfiguration, Continuation<? super ResultWithDiagnostics<? extends LinkedSnippet<KJvmEvaluatedSnippet>>> continuation) throws Throwable {
        AnonymousClass1 anonymousClass1;
        CompiledScript compiledScript;
        BasicJvmReplEvaluator basicJvmReplEvaluator;
        ScriptEvaluationConfiguration scriptEvaluationConfiguration2;
        KJvmEvaluatedSnippet kJvmEvaluatedSnippet;
        Throwable exception;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            if ((anonymousClass1.label & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label -= Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        } else {
            anonymousClass1 = new AnonymousClass1(continuation);
        }
        Object objInvoke = anonymousClass1.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = anonymousClass1.label;
        Object obj = null;
        if (i == 0) {
            ResultKt.throwOnFailure(objInvoke);
            if (!verifyHistoryConsistency(linkedSnippet)) {
                return new ResultWithDiagnostics.Failure(new ScriptDiagnostic(-1, "Snippet cannot be evaluated due to history mismatch", null, null, null, null, 60, null));
            }
            Pair<KClass<?>, Object> pairLastItem = this.history.lastItem();
            final KClass<?> first = pairLastItem != null ? pairLastItem.getFirst() : null;
            List<Pair<KClass<?>, Object>> items = this.history.getItems();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(items, 10));
            Iterator<T> it = items.iterator();
            while (it.hasNext()) {
                arrayList.add(((Pair) it.next()).getSecond());
            }
            final ArrayList arrayList2 = arrayList;
            ScriptEvaluationConfiguration scriptEvaluationConfiguration3 = new ScriptEvaluationConfiguration(new ScriptEvaluationConfiguration[]{scriptEvaluationConfiguration}, new Function1<ScriptEvaluationConfiguration.Builder, Unit>() { // from class: kotlin.script.experimental.jvm.BasicJvmReplEvaluator$eval$currentConfiguration$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ScriptEvaluationConfiguration.Builder builder) {
                    invoke2(builder);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ScriptEvaluationConfiguration.Builder $receiver) {
                    Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                    ScriptEvaluationConfiguration.Builder builder = $receiver;
                    $receiver.put(ScriptEvaluationKt.getPreviousSnippets(builder), arrayList2);
                    if (first != null) {
                        JvmScriptEvaluationConfigurationBuilder jvm = JvmScriptEvaluationKt.getJvm(builder);
                        final KClass<?> kClass = first;
                        $receiver.invoke(jvm, new Function1<JvmScriptEvaluationConfigurationBuilder, Unit>() { // from class: kotlin.script.experimental.jvm.BasicJvmReplEvaluator$eval$currentConfiguration$1.1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(JvmScriptEvaluationConfigurationBuilder jvmScriptEvaluationConfigurationBuilder) {
                                invoke2(jvmScriptEvaluationConfigurationBuilder);
                                return Unit.INSTANCE;
                            }

                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(JvmScriptEvaluationConfigurationBuilder invoke) {
                                Intrinsics.checkNotNullParameter(invoke, "$this$invoke");
                                invoke.invoke(JvmScriptEvaluationKt.getLastSnippetClassLoader(invoke), JvmClassMappingKt.getJavaClass((KClass) kClass).getClassLoader());
                            }
                        });
                    }
                }
            });
            compiledScript = linkedSnippet.get();
            ScriptEvaluator scriptEvaluator = this.scriptEvaluator;
            anonymousClass1.L$0 = this;
            anonymousClass1.L$1 = scriptEvaluationConfiguration3;
            anonymousClass1.L$2 = compiledScript;
            anonymousClass1.label = 1;
            objInvoke = scriptEvaluator.invoke(compiledScript, scriptEvaluationConfiguration3, anonymousClass1);
            if (objInvoke == coroutine_suspended) {
                return coroutine_suspended;
            }
            basicJvmReplEvaluator = this;
            scriptEvaluationConfiguration2 = scriptEvaluationConfiguration3;
        } else {
            if (i != 1) {
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
            compiledScript = (CompiledScript) anonymousClass1.L$2;
            scriptEvaluationConfiguration2 = (ScriptEvaluationConfiguration) anonymousClass1.L$1;
            basicJvmReplEvaluator = (BasicJvmReplEvaluator) anonymousClass1.L$0;
            ResultKt.throwOnFailure(objInvoke);
        }
        ResultWithDiagnostics resultWithDiagnostics = (ResultWithDiagnostics) objInvoke;
        if (resultWithDiagnostics instanceof ResultWithDiagnostics.Success) {
            ResultValue returnValue = ((EvaluationResult) ((ResultWithDiagnostics.Success) resultWithDiagnostics).getValue()).getReturnValue();
            if (returnValue instanceof ResultValue.Error) {
                basicJvmReplEvaluator.history.add(returnValue.getScriptClass(), null);
            } else if (returnValue instanceof ResultValue.Value ? true : returnValue instanceof ResultValue.Unit) {
                basicJvmReplEvaluator.history.add(returnValue.getScriptClass(), returnValue.getScriptInstance());
            } else {
                boolean z = returnValue instanceof ResultValue.NotEvaluated;
            }
            kJvmEvaluatedSnippet = new KJvmEvaluatedSnippet(compiledScript, scriptEvaluationConfiguration2, returnValue);
        } else {
            for (Object obj2 : resultWithDiagnostics.getReports()) {
                if (ErrorHandlingKt.isError((ScriptDiagnostic) obj2)) {
                    obj = obj2;
                    break;
                }
            }
            ScriptDiagnostic scriptDiagnostic = (ScriptDiagnostic) obj;
            kJvmEvaluatedSnippet = new KJvmEvaluatedSnippet(compiledScript, scriptEvaluationConfiguration2, (scriptDiagnostic == null || (exception = scriptDiagnostic.getException()) == null) ? ResultValue.NotEvaluated.INSTANCE : new ResultValue.Error(exception, null, null, 6, null));
        }
        LinkedSnippetImpl<KJvmEvaluatedSnippet> linkedSnippetImplAdd = LinkedSnippetKt.add(basicJvmReplEvaluator.getLastEvaluatedSnippet(), kJvmEvaluatedSnippet);
        basicJvmReplEvaluator.lastEvaluatedSnippet = linkedSnippetImplAdd;
        return ErrorHandlingKt.asSuccess(linkedSnippetImplAdd, resultWithDiagnostics.getReports());
    }

    private final boolean verifyHistoryConsistency(LinkedSnippet<? extends CompiledScript> compiledSnippet) {
        LinkedSnippet<? extends CompiledScript> previous = compiledSnippet.getPrevious();
        LinkedSnippetImpl<KJvmEvaluatedSnippet> lastEvaluatedSnippet = getLastEvaluatedSnippet();
        while (previous != null && lastEvaluatedSnippet != null) {
            KJvmEvaluatedSnippet kJvmEvaluatedSnippet = lastEvaluatedSnippet.get();
            if (kJvmEvaluatedSnippet.getCompiledSnippet() != previous.get() || kJvmEvaluatedSnippet.getResult().getScriptClass() == null) {
                return false;
            }
            previous = previous.getPrevious();
            lastEvaluatedSnippet = lastEvaluatedSnippet.getPrevious();
        }
        return previous == null && lastEvaluatedSnippet == null;
    }
}
