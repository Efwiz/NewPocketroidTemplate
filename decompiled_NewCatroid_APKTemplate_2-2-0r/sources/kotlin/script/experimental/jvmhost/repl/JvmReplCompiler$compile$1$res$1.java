package kotlin.script.experimental.jvmhost.repl;

import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ReplDataKt;
import kotlin.script.experimental.api.ReplScriptCompilationConfigurationBuilder;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptCompilationKt;
import kotlin.script.experimental.api.SourceCode;
import kotlin.script.experimental.jvm.impl.KJvmCompiledScript;
import kotlin.script.experimental.util.LinkedSnippet;
import org.jetbrains.kotlin.cli.common.repl.LineId;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.scripting.compiler.plugin.impl.KJvmReplCompilerBase;
import org.jetbrains.kotlin.scripting.compiler.plugin.impl.KJvmReplCompilerBaseKt;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplCodeAnalyzerBase;

/* JADX INFO: compiled from: legacyReplCompilation.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "Lkotlin/script/experimental/util/LinkedSnippet;", "Lkotlin/script/experimental/jvm/impl/KJvmCompiledScript;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "kotlin.script.experimental.jvmhost.repl.JvmReplCompiler$compile$1$res$1", f = "legacyReplCompilation.kt", i = {}, l = {46}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
final class JvmReplCompiler$compile$1$res$1 extends SuspendLambda implements Function1<Continuation<? super ResultWithDiagnostics<? extends LinkedSnippet<KJvmCompiledScript>>>, Object> {
    final /* synthetic */ LineId $lineId;
    final /* synthetic */ KJvmReplCompilerBase<ReplCodeAnalyzerBase> $replCompiler;
    final /* synthetic */ SourceCode $snippet;
    int label;
    final /* synthetic */ JvmReplCompiler this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    JvmReplCompiler$compile$1$res$1(KJvmReplCompilerBase<ReplCodeAnalyzerBase> kJvmReplCompilerBase, SourceCode sourceCode, JvmReplCompiler jvmReplCompiler, LineId lineId, Continuation<? super JvmReplCompiler$compile$1$res$1> continuation) {
        super(1, continuation);
        this.$replCompiler = kJvmReplCompilerBase;
        this.$snippet = sourceCode;
        this.this$0 = jvmReplCompiler;
        this.$lineId = lineId;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Continuation<?> continuation) {
        return new JvmReplCompiler$compile$1$res$1(this.$replCompiler, this.$snippet, this.this$0, this.$lineId, continuation);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Continuation<? super ResultWithDiagnostics<? extends LinkedSnippet<KJvmCompiledScript>>> continuation) {
        return ((JvmReplCompiler$compile$1$res$1) create(continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            KJvmReplCompilerBase<ReplCodeAnalyzerBase> kJvmReplCompilerBase = this.$replCompiler;
            List listListOf = CollectionsKt.listOf(this.$snippet);
            ScriptCompilationConfiguration scriptCompilationConfiguration = this.this$0.getScriptCompilationConfiguration();
            final LineId lineId = this.$lineId;
            this.label = 1;
            obj = kJvmReplCompilerBase.compile(listListOf, ScriptCompilationKt.with(scriptCompilationConfiguration, new Function1<ScriptCompilationConfiguration.Builder, Unit>() { // from class: kotlin.script.experimental.jvmhost.repl.JvmReplCompiler$compile$1$res$1.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ScriptCompilationConfiguration.Builder builder) {
                    invoke2(builder);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ScriptCompilationConfiguration.Builder with) {
                    Intrinsics.checkNotNullParameter(with, "$this$with");
                    ReplScriptCompilationConfigurationBuilder repl = ReplDataKt.getRepl(with);
                    final LineId lineId2 = lineId;
                    with.invoke(repl, new Function1<ReplScriptCompilationConfigurationBuilder, Unit>() { // from class: kotlin.script.experimental.jvmhost.repl.JvmReplCompiler.compile.1.res.1.1.1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(ReplScriptCompilationConfigurationBuilder replScriptCompilationConfigurationBuilder) {
                            invoke2(replScriptCompilationConfigurationBuilder);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(ReplScriptCompilationConfigurationBuilder invoke) {
                            Intrinsics.checkNotNullParameter(invoke, "$this$invoke");
                            invoke.invoke(KJvmReplCompilerBaseKt.getCurrentLineId(invoke), lineId2);
                        }
                    });
                }
            }), this);
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
