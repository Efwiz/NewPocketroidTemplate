package kotlin.script.experimental.jvm.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.dependencies.KotlinScriptExternalDependencies;
import kotlin.script.dependencies.ScriptContents;
import kotlin.script.dependencies.ScriptDependenciesResolver;
import kotlin.script.experimental.api.ErrorHandlingKt;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptCollectedData;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptCompilationKt;
import kotlin.script.experimental.api.ScriptDataKt;
import kotlin.script.experimental.api.ScriptDependency;
import kotlin.script.experimental.api.SourceCode;
import kotlin.script.experimental.dependencies.AsyncDependenciesResolver;
import kotlin.script.experimental.dependencies.DependenciesResolver;
import kotlin.script.experimental.dependencies.ScriptReport;
import kotlin.script.experimental.impl.RunSuspendKt;
import kotlin.script.experimental.jvm.JvmDependency;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: BridgeDependenciesResolver.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001BA\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u001a\b\u0002\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00070\u0005\u0012\u0016\b\u0002\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\t¢\u0006\u0002\u0010\u000bJ*\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\n2\u0018\u0010\u0015\u001a\u0014\u0012\u0004\u0012\u00020\u0017\u0012\u0006\u0012\u0004\u0018\u00010\u00180\u0016j\u0002`\u0019H\u0016J0\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\n2\u0018\u0010\u0015\u001a\u0014\u0012\u0004\u0012\u00020\u0017\u0012\u0006\u0012\u0004\u0018\u00010\u00180\u0016j\u0002`\u0019H\u0096@¢\u0006\u0002\u0010\u001bR\u001f\u0010\b\u001a\u0010\u0012\u0004\u0012\u00020\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\t¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR#\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u001c"}, d2 = {"Lkotlin/script/experimental/jvm/impl/BridgeDependenciesResolver;", "Lkotlin/script/experimental/dependencies/AsyncDependenciesResolver;", "scriptCompilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "onConfigurationUpdated", "Lkotlin/Function2;", "Lkotlin/script/experimental/api/SourceCode;", "", "getScriptSource", "Lkotlin/Function1;", "Lkotlin/script/dependencies/ScriptContents;", "(Lkotlin/script/experimental/api/ScriptCompilationConfiguration;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function1;)V", "getGetScriptSource", "()Lkotlin/jvm/functions/Function1;", "getOnConfigurationUpdated", "()Lkotlin/jvm/functions/Function2;", "getScriptCompilationConfiguration", "()Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "resolve", "Lkotlin/script/experimental/dependencies/DependenciesResolver$ResolveResult;", "scriptContents", "environment", "", "", "", "Lkotlin/script/dependencies/Environment;", "resolveAsync", "(Lkotlin/script/dependencies/ScriptContents;Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BridgeDependenciesResolver implements AsyncDependenciesResolver {
    private final Function1<ScriptContents, SourceCode> getScriptSource;
    private final Function2<SourceCode, ScriptCompilationConfiguration, Unit> onConfigurationUpdated;
    private final ScriptCompilationConfiguration scriptCompilationConfiguration;

    /* JADX WARN: Multi-variable type inference failed */
    public BridgeDependenciesResolver(ScriptCompilationConfiguration scriptCompilationConfiguration, Function2<? super SourceCode, ? super ScriptCompilationConfiguration, Unit> onConfigurationUpdated, Function1<? super ScriptContents, ? extends SourceCode> getScriptSource) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfiguration, "scriptCompilationConfiguration");
        Intrinsics.checkNotNullParameter(onConfigurationUpdated, "onConfigurationUpdated");
        Intrinsics.checkNotNullParameter(getScriptSource, "getScriptSource");
        this.scriptCompilationConfiguration = scriptCompilationConfiguration;
        this.onConfigurationUpdated = onConfigurationUpdated;
        this.getScriptSource = getScriptSource;
    }

    @Override // kotlin.script.dependencies.ScriptDependenciesResolver
    public Future<KotlinScriptExternalDependencies> resolve(ScriptContents scriptContents, Map<String, ? extends Object> map, Function3<? super ScriptDependenciesResolver.ReportSeverity, ? super String, ? super ScriptContents.Position, Unit> function3, KotlinScriptExternalDependencies kotlinScriptExternalDependencies) {
        return AsyncDependenciesResolver.DefaultImpls.resolve(this, scriptContents, map, function3, kotlinScriptExternalDependencies);
    }

    public final ScriptCompilationConfiguration getScriptCompilationConfiguration() {
        return this.scriptCompilationConfiguration;
    }

    public /* synthetic */ BridgeDependenciesResolver(ScriptCompilationConfiguration scriptCompilationConfiguration, AnonymousClass1 anonymousClass1, AnonymousClass2 anonymousClass2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(scriptCompilationConfiguration, (i & 2) != 0 ? new Function2<SourceCode, ScriptCompilationConfiguration, Unit>() { // from class: kotlin.script.experimental.jvm.impl.BridgeDependenciesResolver.1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(SourceCode sourceCode, ScriptCompilationConfiguration scriptCompilationConfiguration2) {
                Intrinsics.checkNotNullParameter(sourceCode, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(scriptCompilationConfiguration2, "<anonymous parameter 1>");
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(SourceCode sourceCode, ScriptCompilationConfiguration scriptCompilationConfiguration2) {
                invoke2(sourceCode, scriptCompilationConfiguration2);
                return Unit.INSTANCE;
            }
        } : anonymousClass1, (i & 4) != 0 ? new Function1() { // from class: kotlin.script.experimental.jvm.impl.BridgeDependenciesResolver.2
            @Override // kotlin.jvm.functions.Function1
            public final Void invoke(ScriptContents it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return null;
            }
        } : anonymousClass2);
    }

    public final Function2<SourceCode, ScriptCompilationConfiguration, Unit> getOnConfigurationUpdated() {
        return this.onConfigurationUpdated;
    }

    public final Function1<ScriptContents, SourceCode> getGetScriptSource() {
        return this.getScriptSource;
    }

    /* JADX INFO: renamed from: kotlin.script.experimental.jvm.impl.BridgeDependenciesResolver$resolve$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: BridgeDependenciesResolver.kt */
    @Metadata(d1 = {"\u0000\u0006\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Lkotlin/script/experimental/dependencies/DependenciesResolver$ResolveResult;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "kotlin.script.experimental.jvm.impl.BridgeDependenciesResolver$resolve$1", f = "BridgeDependenciesResolver.kt", i = {}, l = {32}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class C40541 extends SuspendLambda implements Function1<Continuation<? super DependenciesResolver.ResolveResult>, Object> {
        final /* synthetic */ Map<String, Object> $environment;
        final /* synthetic */ ScriptContents $scriptContents;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C40541(ScriptContents scriptContents, Map<String, ? extends Object> map, Continuation<? super C40541> continuation) {
            super(1, continuation);
            this.$scriptContents = scriptContents;
            this.$environment = map;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Continuation<?> continuation) {
            return BridgeDependenciesResolver.this.new C40541(this.$scriptContents, this.$environment, continuation);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Continuation<? super DependenciesResolver.ResolveResult> continuation) {
            return ((C40541) create(continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                obj = BridgeDependenciesResolver.this.resolveAsync(this.$scriptContents, this.$environment, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
            }
            return obj;
        }
    }

    @Override // kotlin.script.experimental.dependencies.AsyncDependenciesResolver, kotlin.script.experimental.dependencies.DependenciesResolver
    public DependenciesResolver.ResolveResult resolve(ScriptContents scriptContents, Map<String, ? extends Object> environment) {
        Intrinsics.checkNotNullParameter(scriptContents, "scriptContents");
        Intrinsics.checkNotNullParameter(environment, "environment");
        return (DependenciesResolver.ResolveResult) RunSuspendKt.internalScriptingRunSuspend(new C40541(scriptContents, environment, null));
    }

    @Override // kotlin.script.experimental.dependencies.AsyncDependenciesResolver
    public Object resolveAsync(ScriptContents scriptContents, Map<String, ? extends Object> map, Continuation<? super DependenciesResolver.ResolveResult> continuation) {
        ArrayList arrayListEmptyList;
        try {
            ArrayList arrayList = new ArrayList();
            ScriptCollectedData scriptCollectedData = new ScriptCollectedData(MapsKt.mapOf(TuplesKt.to(ScriptDataKt.getFoundAnnotations(ScriptCollectedData.INSTANCE), scriptContents.getAnnotations())));
            SourceCode sourceCodeInvoke = this.getScriptSource.invoke(scriptContents);
            if (sourceCodeInvoke == null) {
                sourceCodeInvoke = BridgeDependenciesResolverKt.toScriptSource(scriptContents);
            }
            ResultWithDiagnostics<ScriptCompilationConfiguration> resultWithDiagnosticsRefineOnAnnotations = ScriptCompilationKt.refineOnAnnotations(this.scriptCompilationConfiguration, sourceCodeInvoke, scriptCollectedData);
            if (resultWithDiagnosticsRefineOnAnnotations instanceof ResultWithDiagnostics.Success) {
                resultWithDiagnosticsRefineOnAnnotations = ErrorHandlingKt.plus(resultWithDiagnosticsRefineOnAnnotations.getReports(), ScriptCompilationKt.refineBeforeCompiling((ScriptCompilationConfiguration) ((ResultWithDiagnostics.Success) resultWithDiagnosticsRefineOnAnnotations).getValue(), sourceCodeInvoke, scriptCollectedData));
            } else if (!(resultWithDiagnosticsRefineOnAnnotations instanceof ResultWithDiagnostics.Failure)) {
                throw new NoWhenBranchMatchedException();
            }
            if (resultWithDiagnosticsRefineOnAnnotations instanceof ResultWithDiagnostics.Failure) {
                return new DependenciesResolver.ResolveResult.Failure(BridgeDependenciesResolverKt.mapScriptReportsToDiagnostics(resultWithDiagnosticsRefineOnAnnotations.getReports()));
            }
            if (resultWithDiagnosticsRefineOnAnnotations instanceof ResultWithDiagnostics.Success) {
                arrayList.addAll(BridgeDependenciesResolverKt.mapScriptReportsToDiagnostics(resultWithDiagnosticsRefineOnAnnotations.getReports()));
                ScriptCompilationConfiguration scriptCompilationConfiguration = (ScriptCompilationConfiguration) ((ResultWithDiagnostics.Success) resultWithDiagnosticsRefineOnAnnotations).getValue();
                if (!Intrinsics.areEqual(scriptCompilationConfiguration, this.scriptCompilationConfiguration)) {
                    this.onConfigurationUpdated.invoke(sourceCodeInvoke, scriptCompilationConfiguration);
                }
                List<ScriptDependency> list = (List) scriptCompilationConfiguration.get(ScriptCompilationKt.getDependencies(ScriptCompilationConfiguration.INSTANCE));
                if (list == null) {
                    arrayListEmptyList = CollectionsKt.emptyList();
                } else {
                    ArrayList arrayList2 = new ArrayList();
                    for (ScriptDependency scriptDependency : list) {
                        Intrinsics.checkNotNull(scriptDependency, "null cannot be cast to non-null type kotlin.script.experimental.jvm.JvmDependency");
                        CollectionsKt.addAll(arrayList2, ((JvmDependency) scriptDependency).getClasspath());
                    }
                    arrayListEmptyList = arrayList2;
                }
                return new DependenciesResolver.ResolveResult.Success(BridgeDependenciesResolverKt.toDependencies(scriptCompilationConfiguration, arrayListEmptyList), arrayList);
            }
            throw new NoWhenBranchMatchedException();
        } catch (Throwable th) {
            ScriptReport[] scriptReportArr = new ScriptReport[1];
            String message = th.getMessage();
            scriptReportArr[0] = new ScriptReport(message == null ? "unknown error " + th : message, null, null, 6, null);
            return new DependenciesResolver.ResolveResult.Failure(scriptReportArr);
        }
    }
}
