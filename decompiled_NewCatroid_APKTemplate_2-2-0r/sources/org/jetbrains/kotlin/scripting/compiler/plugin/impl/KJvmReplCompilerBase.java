package org.jetbrains.kotlin.scripting.compiler.plugin.impl;

import com.gaurav.avnc.vnc.XKeySym;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.script.experimental.api.CompiledScript;
import kotlin.script.experimental.api.ErrorHandlingKt;
import kotlin.script.experimental.api.KotlinType;
import kotlin.script.experimental.api.ReplCompiler;
import kotlin.script.experimental.api.ReplDataKt;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptCompilationKt;
import kotlin.script.experimental.api.ScriptCompiler;
import kotlin.script.experimental.api.ScriptDiagnostic;
import kotlin.script.experimental.api.SourceCode;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import kotlin.script.experimental.jvm.JvmScriptingHostConfigurationKt;
import kotlin.script.experimental.jvm.impl.KJvmCompiledScript;
import kotlin.script.experimental.util.LinkedSnippet;
import kotlin.script.experimental.util.LinkedSnippetImpl;
import kotlin.script.experimental.util.LinkedSnippetKt;
import kotlin.script.experimental.util.PropertiesCollection;
import kotlin.sequences.SequencesKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.phaser.PhaseConfig;
import org.jetbrains.kotlin.backend.jvm.JvmGeneratorExtensionsImpl;
import org.jetbrains.kotlin.backend.jvm.JvmIrCodegenFactory;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.UtilsKt;
import org.jetbrains.kotlin.cli.common.environment.UtilKt;
import org.jetbrains.kotlin.cli.common.messages.AnalyzerWithCompilerReport;
import org.jetbrains.kotlin.cli.common.messages.DiagnosticMessageReporter;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.messages.MessageCollectorBasedReporter;
import org.jetbrains.kotlin.cli.common.repl.IReplStageHistory;
import org.jetbrains.kotlin.cli.common.repl.LineId;
import org.jetbrains.kotlin.cli.common.repl.ReplHistoryRecord;
import org.jetbrains.kotlin.codegen.ClassBuilderFactories;
import org.jetbrains.kotlin.codegen.ClassBuilderFactory;
import org.jetbrains.kotlin.codegen.CodegenFactory;
import org.jetbrains.kotlin.codegen.KotlinCodegenFacade;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.diagnostics.GenericDiagnostics;
import org.jetbrains.kotlin.diagnostics.impl.SimpleDiagnosticsCollector;
import org.jetbrains.kotlin.ir.symbols.IrScriptSymbol;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.tower.ImplicitsExtensionsResolutionFilter;
import org.jetbrains.kotlin.scripting.compiler.plugin.dependencies.ScriptsCompilationDependencies;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.JvmReplCompilerState;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplCodeAnalyzerBase;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplImplicitsExtensionsResolutionFilter;
import org.jetbrains.kotlin.scripting.definitions.ScriptDependenciesProvider;
import org.jetbrains.kotlin.scripting.resolve.ResolveCompilationOptionsKt;
import org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: KJvmReplCompilerBase.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0016\u0018\u0000 ;*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u00020\u00040\u00032\u00020\u0005:\u0002:;B\u001d\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\f\b\u0002\u0010\b\u001a\u0006\u0012\u0002\b\u00030\t¢\u0006\u0002\u0010\nJ0\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00180\u00172\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001dH\u0096@¢\u0006\u0002\u0010\u001eJ,\u0010\u001f\u001a\u00020 2\f\u0010!\u001a\b\u0012\u0004\u0012\u00028\u00000\"2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020%0$2\u0006\u0010&\u001a\u00020'H\u0002J&\u0010(\u001a\u00020 2\u0006\u0010)\u001a\u00020%2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020%0$2\u0006\u0010&\u001a\u00020'H\u0002J$\u0010*\u001a\b\u0012\u0004\u0012\u00020+0\u00172\u0006\u0010,\u001a\u00020\u001b2\u0006\u0010-\u001a\u00020\u001dH\u0096B¢\u0006\u0002\u0010.J.\u0010/\u001a\b\u0012\u0004\u0012\u0002000\u00172\u0006\u00101\u001a\u00020\u001b2\u0006\u00102\u001a\u0002032\u0006\u0010!\u001a\u0002042\u0006\u00105\u001a\u000206H\u0004J\u0010\u00107\u001a\u00020\u001d2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0010\u00108\u001a\u0002092\u0006\u0010\u001c\u001a\u00020\u001dH\u0004R\u0014\u0010\u0006\u001a\u00020\u0007X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR4\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u000e2\u000e\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u000e@TX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0015\u0010\b\u001a\u0006\u0012\u0002\b\u00030\t¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015¨\u0006<"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/KJvmReplCompilerBase;", "AnalyzerT", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase;", "Lkotlin/script/experimental/api/ReplCompiler;", "Lkotlin/script/experimental/jvm/impl/KJvmCompiledScript;", "Lkotlin/script/experimental/api/ScriptCompiler;", "hostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "state", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/JvmReplCompilerState;", "(Lkotlin/script/experimental/host/ScriptingHostConfiguration;Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/JvmReplCompilerState;)V", "getHostConfiguration", "()Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "<set-?>", "Lkotlin/script/experimental/util/LinkedSnippetImpl;", "lastCompiledSnippet", "getLastCompiledSnippet", "()Lkotlin/script/experimental/util/LinkedSnippetImpl;", "setLastCompiledSnippet", "(Lkotlin/script/experimental/util/LinkedSnippetImpl;)V", "getState", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/JvmReplCompilerState;", "compile", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "Lkotlin/script/experimental/util/LinkedSnippet;", "snippets", "", "Lkotlin/script/experimental/api/SourceCode;", "configuration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "(Ljava/lang/Iterable;Lkotlin/script/experimental/api/ScriptCompilationConfiguration;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "generateWithBackendIr", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "compilationState", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/ReplCompilationState;", "sourceFiles", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "prebuiltState", "Lorg/jetbrains/kotlin/codegen/state/GenerationState$Builder;", "generateWithOldBackend", "snippetKtFile", "invoke", "Lkotlin/script/experimental/api/CompiledScript;", "script", "scriptCompilationConfiguration", "(Lkotlin/script/experimental/api/SourceCode;Lkotlin/script/experimental/api/ScriptCompilationConfiguration;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "prepareForAnalyze", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/KJvmReplCompilerBase$AnalyzePreparationResult;", "snippet", "parentMessageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/JvmReplCompilerState$Compilation;", "failOnSyntaxErrors", "", "updateConfigurationWithPreviousScripts", "updateResolutionFilterWithHistory", "", "AnalyzePreparationResult", "Companion", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KJvmReplCompilerBase<AnalyzerT extends ReplCodeAnalyzerBase> implements ReplCompiler<KJvmCompiledScript>, ScriptCompiler {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final ScriptingHostConfiguration hostConfiguration;
    private LinkedSnippetImpl<KJvmCompiledScript> lastCompiledSnippet;
    private final JvmReplCompilerState<?> state;

    /* JADX INFO: renamed from: org.jetbrains.kotlin.scripting.compiler.plugin.impl.KJvmReplCompilerBase$invoke$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: KJvmReplCompilerBase.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.scripting.compiler.plugin.impl.KJvmReplCompilerBase", f = "KJvmReplCompilerBase.kt", i = {}, l = {XKeySym.XK_adiaeresis}, m = "invoke$suspendImpl", n = {}, s = {})
    static final class C50211<AnalyzerT extends ReplCodeAnalyzerBase> extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;
        final /* synthetic */ KJvmReplCompilerBase<AnalyzerT> this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C50211(KJvmReplCompilerBase<AnalyzerT> kJvmReplCompilerBase, Continuation<? super C50211> continuation) {
            super(continuation);
            this.this$0 = kJvmReplCompilerBase;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return KJvmReplCompilerBase.invoke$suspendImpl(this.this$0, null, null, this);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public KJvmReplCompilerBase() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    @Override // kotlin.script.experimental.api.ReplCompiler
    public Object compile(Iterable<? extends SourceCode> iterable, ScriptCompilationConfiguration scriptCompilationConfiguration, Continuation<? super ResultWithDiagnostics<? extends LinkedSnippet<KJvmCompiledScript>>> continuation) {
        return compile$suspendImpl(this, iterable, scriptCompilationConfiguration, continuation);
    }

    @Override // kotlin.script.experimental.api.ScriptCompiler
    public Object invoke(SourceCode sourceCode, ScriptCompilationConfiguration scriptCompilationConfiguration, Continuation<? super ResultWithDiagnostics<? extends CompiledScript>> continuation) {
        return invoke$suspendImpl(this, sourceCode, scriptCompilationConfiguration, continuation);
    }

    public KJvmReplCompilerBase(ScriptingHostConfiguration hostConfiguration, JvmReplCompilerState<?> state) {
        Intrinsics.checkNotNullParameter(hostConfiguration, "hostConfiguration");
        Intrinsics.checkNotNullParameter(state, "state");
        this.hostConfiguration = hostConfiguration;
        this.state = state;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ KJvmReplCompilerBase(final ScriptingHostConfiguration scriptingHostConfiguration, JvmReplCompilerState jvmReplCompilerState, int i, DefaultConstructorMarker defaultConstructorMarker) {
        scriptingHostConfiguration = (i & 1) != 0 ? JvmScriptingHostConfigurationKt.getDefaultJvmScriptingHostConfiguration() : scriptingHostConfiguration;
        this(scriptingHostConfiguration, (i & 2) != 0 ? new JvmReplCompilerState(new Function1<ScriptCompilationConfiguration, ReplCompilationState<ReplCodeAnalyzerBase>>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.KJvmReplCompilerBase.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ReplCompilationState<ReplCodeAnalyzerBase> invoke(ScriptCompilationConfiguration it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return KJvmReplCompilerBase.INSTANCE.createCompilationState(it, scriptingHostConfiguration);
            }
        }, null, 2, null) : jvmReplCompilerState);
    }

    protected final ScriptingHostConfiguration getHostConfiguration() {
        return this.hostConfiguration;
    }

    public final JvmReplCompilerState<?> getState() {
        return this.state;
    }

    @Override // kotlin.script.experimental.api.ReplCompiler
    public LinkedSnippetImpl<KJvmCompiledScript> getLastCompiledSnippet() {
        return this.lastCompiledSnippet;
    }

    protected void setLastCompiledSnippet(LinkedSnippetImpl<KJvmCompiledScript> linkedSnippetImpl) {
        this.lastCompiledSnippet = linkedSnippetImpl;
    }

    private final GenerationState generateWithOldBackend(KtFile snippetKtFile, List<? extends KtFile> sourceFiles, GenerationState.Builder prebuiltState) {
        GenerationState generationStateBuild = prebuiltState.build();
        GenerationState.ForScript scriptSpecific = generationStateBuild.getScriptSpecific();
        IReplStageHistory<ScriptDescriptor> history = this.state.getHistory();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(history, 10));
        Iterator<ScriptDescriptor> it = history.iterator();
        while (it.hasNext()) {
            arrayList.add((ScriptDescriptor) ((ReplHistoryRecord) it.next()).getItem());
        }
        scriptSpecific.setEarlierScriptsForReplInterpreter(arrayList);
        generationStateBuild.beforeCompile();
        List<? extends KtFile> list = sourceFiles;
        generationStateBuild.oldBEInitTrace(list);
        KtScript script = snippetKtFile.getScript();
        Intrinsics.checkNotNull(script);
        KotlinCodegenFacade.generatePackage(generationStateBuild, script.getContainingKtFile().getPackageFqName(), list);
        return generationStateBuild;
    }

    private final GenerationState generateWithBackendIr(final ReplCompilationState<AnalyzerT> compilationState, List<? extends KtFile> sourceFiles, GenerationState.Builder prebuiltState) {
        final CompilerConfiguration configuration = compilationState.getEnvironment().getConfiguration();
        JvmIrCodegenFactory jvmIrCodegenFactory = new JvmIrCodegenFactory(compilationState.getEnvironment().getConfiguration(), (PhaseConfig) compilationState.getEnvironment().getConfiguration().get(CLIConfigurationKeys.PHASE_CONFIG), compilationState.getMangler(), compilationState.getSymbolTable(), new JvmGeneratorExtensionsImpl(this, configuration) { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.KJvmReplCompilerBase$generateWithBackendIr$generatorExtensions$1
            final /* synthetic */ KJvmReplCompilerBase<AnalyzerT> this$0;

            {
                this.this$0 = this;
                int i = 2;
                DefaultConstructorMarker defaultConstructorMarker = null;
                boolean z = false;
            }

            @Override // org.jetbrains.kotlin.psi2ir.generators.GeneratorExtensions
            public List<IrScriptSymbol> getPreviousScripts() {
                IReplStageHistory<ScriptDescriptor> history = this.this$0.getState().getHistory();
                ReplCompilationState<AnalyzerT> replCompilationState = compilationState;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(history, 10));
                Iterator<ScriptDescriptor> it = history.iterator();
                while (it.hasNext()) {
                    arrayList.add(replCompilationState.getSymbolTable().getDescriptorExtension().referenceScript(((ReplHistoryRecord) it.next()).getItem()));
                }
                return arrayList;
            }
        }, null, null, 96, null);
        GenerationState generationStateBuild = prebuiltState.build();
        jvmIrCodegenFactory.generateModule(generationStateBuild, jvmIrCodegenFactory.convertToIr(CodegenFactory.IrConversionInput.INSTANCE.fromGenerationStateAndFiles(generationStateBuild, sourceFiles)));
        return generationStateBuild;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0014  */
    static /* synthetic */ <AnalyzerT extends ReplCodeAnalyzerBase> Object invoke$suspendImpl(KJvmReplCompilerBase<AnalyzerT> kJvmReplCompilerBase, SourceCode sourceCode, ScriptCompilationConfiguration scriptCompilationConfiguration, Continuation<? super ResultWithDiagnostics<? extends CompiledScript>> continuation) throws Throwable {
        C50211 c50211;
        if (continuation instanceof C50211) {
            c50211 = (C50211) continuation;
            if ((c50211.label & Integer.MIN_VALUE) != 0) {
                c50211.label -= Integer.MIN_VALUE;
            } else {
                c50211 = new C50211(kJvmReplCompilerBase, continuation);
            }
        } else {
            c50211 = new C50211(kJvmReplCompilerBase, continuation);
        }
        Object objCompile = c50211.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = c50211.label;
        if (i == 0) {
            ResultKt.throwOnFailure(objCompile);
            c50211.label = 1;
            objCompile = kJvmReplCompilerBase.compile(sourceCode, scriptCompilationConfiguration, c50211);
            if (objCompile == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
            ResultKt.throwOnFailure(objCompile);
        }
        ResultWithDiagnostics resultWithDiagnostics = (ResultWithDiagnostics) objCompile;
        if (resultWithDiagnostics instanceof ResultWithDiagnostics.Success) {
            return ErrorHandlingKt.asSuccess(((LinkedSnippet) ((ResultWithDiagnostics.Success) resultWithDiagnostics).getValue()).get(), resultWithDiagnostics.getReports());
        }
        if (resultWithDiagnostics instanceof ResultWithDiagnostics.Failure) {
            return resultWithDiagnostics;
        }
        throw new NoWhenBranchMatchedException();
    }

    /* JADX INFO: compiled from: KJvmReplCompilerBase.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0084\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0007HÆ\u0003J'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/KJvmReplCompilerBase$AnalyzePreparationResult;", "", "context", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/SharedScriptCompilationContext;", "errorHolder", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollectorBasedReporter;", "snippetKtFile", "Lorg/jetbrains/kotlin/psi/KtFile;", "(Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/SharedScriptCompilationContext;Lorg/jetbrains/kotlin/cli/common/messages/MessageCollectorBasedReporter;Lorg/jetbrains/kotlin/psi/KtFile;)V", "getContext", "()Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/SharedScriptCompilationContext;", "getErrorHolder", "()Lorg/jetbrains/kotlin/cli/common/messages/MessageCollectorBasedReporter;", "getSnippetKtFile", "()Lorg/jetbrains/kotlin/psi/KtFile;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    protected static final /* data */ class AnalyzePreparationResult {
        private final SharedScriptCompilationContext context;
        private final MessageCollectorBasedReporter errorHolder;
        private final KtFile snippetKtFile;

        public static /* synthetic */ AnalyzePreparationResult copy$default(AnalyzePreparationResult analyzePreparationResult, SharedScriptCompilationContext sharedScriptCompilationContext, MessageCollectorBasedReporter messageCollectorBasedReporter, KtFile ktFile, int i, Object obj) {
            if ((i & 1) != 0) {
                sharedScriptCompilationContext = analyzePreparationResult.context;
            }
            if ((i & 2) != 0) {
                messageCollectorBasedReporter = analyzePreparationResult.errorHolder;
            }
            if ((i & 4) != 0) {
                ktFile = analyzePreparationResult.snippetKtFile;
            }
            return analyzePreparationResult.copy(sharedScriptCompilationContext, messageCollectorBasedReporter, ktFile);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final SharedScriptCompilationContext getContext() {
            return this.context;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final MessageCollectorBasedReporter getErrorHolder() {
            return this.errorHolder;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final KtFile getSnippetKtFile() {
            return this.snippetKtFile;
        }

        public final AnalyzePreparationResult copy(SharedScriptCompilationContext context, MessageCollectorBasedReporter errorHolder, KtFile snippetKtFile) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(errorHolder, "errorHolder");
            Intrinsics.checkNotNullParameter(snippetKtFile, "snippetKtFile");
            return new AnalyzePreparationResult(context, errorHolder, snippetKtFile);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof AnalyzePreparationResult)) {
                return false;
            }
            AnalyzePreparationResult analyzePreparationResult = (AnalyzePreparationResult) other;
            return Intrinsics.areEqual(this.context, analyzePreparationResult.context) && Intrinsics.areEqual(this.errorHolder, analyzePreparationResult.errorHolder) && Intrinsics.areEqual(this.snippetKtFile, analyzePreparationResult.snippetKtFile);
        }

        public int hashCode() {
            return (((this.context.hashCode() * 31) + this.errorHolder.hashCode()) * 31) + this.snippetKtFile.hashCode();
        }

        public String toString() {
            return "AnalyzePreparationResult(context=" + this.context + ", errorHolder=" + this.errorHolder + ", snippetKtFile=" + this.snippetKtFile + ')';
        }

        public AnalyzePreparationResult(SharedScriptCompilationContext context, MessageCollectorBasedReporter errorHolder, KtFile snippetKtFile) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(errorHolder, "errorHolder");
            Intrinsics.checkNotNullParameter(snippetKtFile, "snippetKtFile");
            this.context = context;
            this.errorHolder = errorHolder;
            this.snippetKtFile = snippetKtFile;
        }

        public final SharedScriptCompilationContext getContext() {
            return this.context;
        }

        public final MessageCollectorBasedReporter getErrorHolder() {
            return this.errorHolder;
        }

        public final KtFile getSnippetKtFile() {
            return this.snippetKtFile;
        }
    }

    protected final void updateResolutionFilterWithHistory(ScriptCompilationConfiguration configuration) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        ScriptCompilationConfiguration scriptCompilationConfigurationUpdateConfigurationWithPreviousScripts = updateConfigurationWithPreviousScripts(configuration);
        Object obj = scriptCompilationConfigurationUpdateConfigurationWithPreviousScripts.get(ResolveCompilationOptionsKt.getSkipExtensionsResolutionForImplicits(ScriptCompilationConfiguration.INSTANCE));
        Intrinsics.checkNotNull(obj);
        Object obj2 = scriptCompilationConfigurationUpdateConfigurationWithPreviousScripts.get(ResolveCompilationOptionsKt.getSkipExtensionsResolutionForImplicitsExceptInnermost(ScriptCompilationConfiguration.INSTANCE));
        Intrinsics.checkNotNull(obj2);
        JvmReplCompilerState.Compilation compilation$kotlin_scripting_compiler = this.state.getCompilation$kotlin_scripting_compiler();
        Intrinsics.checkNotNull(compilation$kotlin_scripting_compiler, "null cannot be cast to non-null type org.jetbrains.kotlin.scripting.compiler.plugin.impl.ReplCompilationState<*>");
        ((ReplCompilationState) compilation$kotlin_scripting_compiler).getImplicitsResolutionFilter().update((Collection) obj, (Collection) obj2);
    }

    private final ScriptCompilationConfiguration updateConfigurationWithPreviousScripts(ScriptCompilationConfiguration configuration) {
        List list = SequencesKt.toList(SequencesKt.map(SequencesKt.generateSequence(getLastCompiledSnippet(), new Function1<LinkedSnippetImpl<KJvmCompiledScript>, LinkedSnippetImpl<KJvmCompiledScript>>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.KJvmReplCompilerBase$updateConfigurationWithPreviousScripts$allPreviousLines$1
            @Override // kotlin.jvm.functions.Function1
            public final LinkedSnippetImpl<KJvmCompiledScript> invoke(LinkedSnippetImpl<KJvmCompiledScript> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getPrevious();
            }
        }), new Function1<LinkedSnippetImpl<KJvmCompiledScript>, KotlinType>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.KJvmReplCompilerBase$updateConfigurationWithPreviousScripts$allPreviousLines$2
            @Override // kotlin.jvm.functions.Function1
            public final KotlinType invoke(LinkedSnippetImpl<KJvmCompiledScript> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return new KotlinType(it.get().getScriptClassFQName(), false, 2, (DefaultConstructorMarker) null);
            }
        }));
        final List listSubList = list.subList(0, Math.min(1, list.size()));
        final List listEmptyList = list.isEmpty() ? CollectionsKt.emptyList() : list.subList(1, list.size());
        return new ScriptCompilationConfiguration(new ScriptCompilationConfiguration[]{configuration}, new Function1<ScriptCompilationConfiguration.Builder, Unit>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.KJvmReplCompilerBase.updateConfigurationWithPreviousScripts.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ScriptCompilationConfiguration.Builder builder) {
                invoke2(builder);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ScriptCompilationConfiguration.Builder $receiver) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                ScriptCompilationConfiguration.Builder builder = $receiver;
                PropertiesCollection.Key<Collection<KotlinType>> skipExtensionsResolutionForImplicits = ResolveCompilationOptionsKt.getSkipExtensionsResolutionForImplicits(builder);
                KotlinType[] kotlinTypeArr = (KotlinType[]) listEmptyList.toArray(new KotlinType[0]);
                $receiver.invoke((PropertiesCollection.Key) skipExtensionsResolutionForImplicits, Arrays.copyOf(kotlinTypeArr, kotlinTypeArr.length));
                PropertiesCollection.Key<Collection<KotlinType>> skipExtensionsResolutionForImplicitsExceptInnermost = ResolveCompilationOptionsKt.getSkipExtensionsResolutionForImplicitsExceptInnermost(builder);
                KotlinType[] kotlinTypeArr2 = (KotlinType[]) listSubList.toArray(new KotlinType[0]);
                $receiver.invoke((PropertiesCollection.Key) skipExtensionsResolutionForImplicitsExceptInnermost, Arrays.copyOf(kotlinTypeArr2, kotlinTypeArr2.length));
            }
        });
    }

    /* JADX INFO: compiled from: KJvmReplCompilerBase.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\tJB\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\n0\u0004\"\b\b\u0001\u0010\n*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\u0018\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u0002H\n0\f¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/KJvmReplCompilerBase$Companion;", "", "()V", "createCompilationState", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/ReplCompilationState;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase;", "scriptCompilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "hostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "AnalyzerT", "initAnalyzer", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/SharedScriptCompilationContext;", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitsExtensionsResolutionFilter;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ ReplCompilationState createCompilationState$default(Companion companion, ScriptCompilationConfiguration scriptCompilationConfiguration, ScriptingHostConfiguration scriptingHostConfiguration, int i, Object obj) {
            if ((i & 2) != 0) {
                scriptingHostConfiguration = JvmScriptingHostConfigurationKt.getDefaultJvmScriptingHostConfiguration();
            }
            return companion.createCompilationState(scriptCompilationConfiguration, scriptingHostConfiguration);
        }

        public final ReplCompilationState<ReplCodeAnalyzerBase> createCompilationState(ScriptCompilationConfiguration scriptCompilationConfiguration, ScriptingHostConfiguration hostConfiguration) {
            Intrinsics.checkNotNullParameter(scriptCompilationConfiguration, "scriptCompilationConfiguration");
            Intrinsics.checkNotNullParameter(hostConfiguration, "hostConfiguration");
            return createCompilationState(scriptCompilationConfiguration, hostConfiguration, new Function2<SharedScriptCompilationContext, ImplicitsExtensionsResolutionFilter, ReplCodeAnalyzerBase>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.KJvmReplCompilerBase$Companion$createCompilationState$1
                @Override // kotlin.jvm.functions.Function2
                public final ReplCodeAnalyzerBase invoke(SharedScriptCompilationContext context1, ImplicitsExtensionsResolutionFilter resolutionFilter) {
                    Intrinsics.checkNotNullParameter(context1, "context1");
                    Intrinsics.checkNotNullParameter(resolutionFilter, "resolutionFilter");
                    return new ReplCodeAnalyzerBase(context1.getEnvironment(), null, resolutionFilter, 2, null);
                }
            });
        }

        public static /* synthetic */ ReplCompilationState createCompilationState$default(Companion companion, ScriptCompilationConfiguration scriptCompilationConfiguration, ScriptingHostConfiguration scriptingHostConfiguration, Function2 function2, int i, Object obj) {
            if ((i & 2) != 0) {
                scriptingHostConfiguration = JvmScriptingHostConfigurationKt.getDefaultJvmScriptingHostConfiguration();
            }
            return companion.createCompilationState(scriptCompilationConfiguration, scriptingHostConfiguration, function2);
        }

        public final <AnalyzerT extends ReplCodeAnalyzerBase> ReplCompilationState<AnalyzerT> createCompilationState(ScriptCompilationConfiguration scriptCompilationConfiguration, ScriptingHostConfiguration hostConfiguration, Function2<? super SharedScriptCompilationContext, ? super ImplicitsExtensionsResolutionFilter, ? extends AnalyzerT> initAnalyzer) {
            ResultWithDiagnostics.Failure failure;
            Intrinsics.checkNotNullParameter(scriptCompilationConfiguration, "scriptCompilationConfiguration");
            Intrinsics.checkNotNullParameter(hostConfiguration, "hostConfiguration");
            Intrinsics.checkNotNullParameter(initAnalyzer, "initAnalyzer");
            Disposable disposableNewDisposable = Disposer.newDisposable();
            Intrinsics.checkNotNullExpressionValue(disposableNewDisposable, "newDisposable(...)");
            ScriptDiagnosticsMessageCollector scriptDiagnosticsMessageCollector = new ScriptDiagnosticsMessageCollector(null);
            try {
                UtilKt.setIdeaIoUseFallback();
                failure = ErrorHandlingKt.asSuccess$default(CompilationContextKt.createIsolatedCompilationContext(scriptCompilationConfiguration, hostConfiguration, scriptDiagnosticsMessageCollector, disposableNewDisposable), null, 1, null);
                if (failure instanceof ResultWithDiagnostics.Failure) {
                    Disposer.dispose(disposableNewDisposable);
                }
            } catch (Throwable th) {
                try {
                    failure = ErrorReportingKt.failure(scriptDiagnosticsMessageCollector, ErrorHandlingKt.asDiagnostics$default(th, 0, null, null, null, null, 27, null));
                } catch (Throwable th2) {
                    Disposer.dispose(disposableNewDisposable);
                    throw th2;
                }
            }
            if (failure instanceof ResultWithDiagnostics.Success) {
                SharedScriptCompilationContext sharedScriptCompilationContext = (SharedScriptCompilationContext) ((ResultWithDiagnostics.Success) failure).getValue();
                List listEmptyList = (Collection) scriptCompilationConfiguration.get(ResolveCompilationOptionsKt.getSkipExtensionsResolutionForImplicits(ScriptCompilationConfiguration.INSTANCE));
                if (listEmptyList == null) {
                    listEmptyList = CollectionsKt.emptyList();
                }
                List listEmptyList2 = (Collection) scriptCompilationConfiguration.get(ResolveCompilationOptionsKt.getSkipExtensionsResolutionForImplicitsExceptInnermost(ScriptCompilationConfiguration.INSTANCE));
                if (listEmptyList2 == null) {
                    listEmptyList2 = CollectionsKt.emptyList();
                }
                return new ReplCompilationState<>(sharedScriptCompilationContext, initAnalyzer, new ReplImplicitsExtensionsResolutionFilter(listEmptyList, listEmptyList2));
            }
            if (failure instanceof ResultWithDiagnostics.Failure) {
                throw new IllegalStateException("Unable to initialize repl compiler:\n  " + CollectionsKt.joinToString$default(((ResultWithDiagnostics.Failure) failure).getReports(), "\n  ", null, null, 0, null, null, 62, null));
            }
            throw new NoWhenBranchMatchedException();
        }
    }

    /* JADX WARN: Code duplicated, block: B:112:0x031b  */
    /* JADX WARN: Code duplicated, block: B:113:0x0321  */
    /* JADX WARN: Type inference failed for: r7v10, types: [org.jetbrains.kotlin.scripting.compiler.plugin.repl.JvmReplCompilerStageHistory] */
    static /* synthetic */ <AnalyzerT extends ReplCodeAnalyzerBase> Object compile$suspendImpl(KJvmReplCompilerBase<AnalyzerT> kJvmReplCompilerBase, Iterable<? extends SourceCode> iterable, ScriptCompilationConfiguration scriptCompilationConfiguration, Continuation<? super ResultWithDiagnostics<? extends LinkedSnippet<KJvmCompiledScript>>> continuation) {
        ArrayList arrayList;
        Iterator<? extends SourceCode> it;
        Throwable th;
        int i;
        String locationId;
        ResultWithDiagnostics resultWithDiagnosticsFailure;
        Object next;
        ResultWithDiagnostics.Failure failure;
        ScriptCompilationConfiguration baseScriptCompilationConfiguration;
        ResultWithDiagnostics<ScriptCompilationConfigurationWrapper> scriptConfigurationResult;
        ScriptCompilationConfigurationWrapper scriptCompilationConfigurationWrapper;
        GenerationState generationStateGenerateWithOldBackend;
        ResultWithDiagnostics.Success successAsSuccess;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
        Iterator<? extends SourceCode> it2 = iterable.iterator();
        while (it2.hasNext()) {
            SourceCode next2 = it2.next();
            ScriptDiagnosticsMessageCollector scriptDiagnosticsMessageCollector = new ScriptDiagnosticsMessageCollector(null);
            try {
                ResultWithDiagnostics resultWithDiagnosticsRefineBeforeParsing$default = ScriptCompilationKt.refineBeforeParsing$default(scriptCompilationConfiguration, next2, null, 2, null);
                if (resultWithDiagnosticsRefineBeforeParsing$default instanceof ResultWithDiagnostics.Success) {
                    ScriptCompilationConfiguration scriptCompilationConfiguration2 = (ScriptCompilationConfiguration) ((ResultWithDiagnostics.Success) resultWithDiagnosticsRefineBeforeParsing$default).getValue();
                    JvmReplCompilerState.Compilation compilationState = ((KJvmReplCompilerBase) kJvmReplCompilerBase).state.getCompilationState(scriptCompilationConfiguration2);
                    Intrinsics.checkNotNull(compilationState, "null cannot be cast to non-null type org.jetbrains.kotlin.scripting.compiler.plugin.impl.ReplCompilationState<AnalyzerT of org.jetbrains.kotlin.scripting.compiler.plugin.impl.KJvmReplCompilerBase.compile$lambda$6$lambda$5>");
                    ReplCompilationState<AnalyzerT> replCompilationState = (ReplCompilationState) compilationState;
                    kJvmReplCompilerBase.updateResolutionFilterWithHistory(scriptCompilationConfiguration);
                    ResultWithDiagnostics resultWithDiagnosticsPrepareForAnalyze = kJvmReplCompilerBase.prepareForAnalyze(next2, scriptDiagnosticsMessageCollector, replCompilationState, true);
                    if (resultWithDiagnosticsPrepareForAnalyze instanceof ResultWithDiagnostics.Success) {
                        AnalyzePreparationResult analyzePreparationResult = (AnalyzePreparationResult) ((ResultWithDiagnostics.Success) resultWithDiagnosticsPrepareForAnalyze).getValue();
                        final SharedScriptCompilationContext context = analyzePreparationResult.getContext();
                        MessageCollectorBasedReporter errorHolder = analyzePreparationResult.getErrorHolder();
                        KtFile snippetKtFile = analyzePreparationResult.getSnippetKtFile();
                        Pair<List<KtFile>, List<ScriptsCompilationDependencies.SourceDependencies>> pairCollectRefinedSourcesAndUpdateEnvironment = CompilationContextKt.collectRefinedSourcesAndUpdateEnvironment(context, snippetKtFile, scriptCompilationConfiguration2, scriptDiagnosticsMessageCollector);
                        List<KtFile> listComponent1 = pairCollectRefinedSourcesAndUpdateEnvironment.component1();
                        List<ScriptsCompilationDependencies.SourceDependencies> listComponent2 = pairCollectRefinedSourcesAndUpdateEnvironment.component2();
                        Iterator<T> it3 = listComponent2.iterator();
                        do {
                            if (!it3.hasNext()) {
                                next = null;
                                break;
                            }
                            try {
                                next = it3.next();
                            } catch (Throwable th2) {
                                th = th2;
                                arrayList = arrayList2;
                                it = it2;
                                i = 1;
                                ScriptDiagnostic[] scriptDiagnosticArr = new ScriptDiagnostic[i];
                                if (next2 != null) {
                                    locationId = next2.getLocationId();
                                } else {
                                    locationId = null;
                                }
                                scriptDiagnosticArr[0] = ErrorHandlingKt.asDiagnostics$default(th, 0, null, locationId, null, null, 27, null);
                                resultWithDiagnosticsFailure = ErrorReportingKt.failure(scriptDiagnosticsMessageCollector, scriptDiagnosticArr);
                            }
                        } while (!(((ScriptsCompilationDependencies.SourceDependencies) next).getSourceDependencies() instanceof ResultWithDiagnostics.Failure));
                        ScriptsCompilationDependencies.SourceDependencies sourceDependencies = (ScriptsCompilationDependencies.SourceDependencies) next;
                        if (sourceDependencies != null) {
                            ResultWithDiagnostics<List<KtFile>> sourceDependencies2 = sourceDependencies.getSourceDependencies();
                            Intrinsics.checkNotNull(sourceDependencies2, "null cannot be cast to non-null type kotlin.script.experimental.api.ResultWithDiagnostics.Failure");
                            failure = (ResultWithDiagnostics.Failure) sourceDependencies2;
                        } else {
                            failure = null;
                        }
                        if (failure != null) {
                            return failure;
                        }
                        UtilsKt.checkKotlinPackageUsageForPsi(context.getEnvironment().getConfiguration(), listComponent1, scriptDiagnosticsMessageCollector);
                        if (scriptDiagnosticsMessageCollector.getHasErrors()) {
                            return ErrorReportingKt.failure(scriptDiagnosticsMessageCollector, new ScriptDiagnostic[0]);
                        }
                        if (((KJvmReplCompilerBase) kJvmReplCompilerBase).state.getHistory().isEmpty()) {
                            try {
                                ScriptDependenciesProvider companion = ScriptDependenciesProvider.INSTANCE.getInstance(context.getEnvironment().getProject());
                                if (companion == null || (scriptConfigurationResult = companion.getScriptConfigurationResult(snippetKtFile, context.getBaseScriptCompilationConfiguration())) == null || (scriptCompilationConfigurationWrapper = (ScriptCompilationConfigurationWrapper) ErrorHandlingKt.valueOrNull(scriptConfigurationResult)) == null || (baseScriptCompilationConfiguration = scriptCompilationConfigurationWrapper.getConfiguration()) == null) {
                                    baseScriptCompilationConfiguration = context.getBaseScriptCompilationConfiguration();
                                }
                                ScriptJvmCompilerImplsKt.registerPackageFragmentProvidersIfNeeded(baseScriptCompilationConfiguration, context.getEnvironment());
                            } catch (Throwable th3) {
                                th = th3;
                                arrayList = arrayList2;
                                it = it2;
                                i = 1;
                                ScriptDiagnostic[] scriptDiagnosticArr2 = new ScriptDiagnostic[i];
                                if (next2 != null) {
                                    locationId = next2.getLocationId();
                                } else {
                                    locationId = null;
                                }
                                scriptDiagnosticArr2[0] = ErrorHandlingKt.asDiagnostics$default(th, 0, null, locationId, null, null, 27, null);
                                resultWithDiagnosticsFailure = ErrorReportingKt.failure(scriptDiagnosticsMessageCollector, scriptDiagnosticArr2);
                            }
                        }
                        LineId lineId = (LineId) scriptCompilationConfiguration.get(KJvmReplCompilerBaseKt.getCurrentLineId(ReplDataKt.getRepl(ScriptCompilationConfiguration.INSTANCE)));
                        if (lineId == null) {
                            lineId = new LineId(((KJvmReplCompilerBase) kJvmReplCompilerBase).state.getNextLineNo(), ((KJvmReplCompilerBase) kJvmReplCompilerBase).state.getCurrentGeneration(), next2.hashCode());
                        }
                        ReplCodeAnalyzerBase.ReplLineAnalysisResult replLineAnalysisResultAnalyzeReplLineWithImportedScripts = replCompilationState.getAnalyzerEngine().analyzeReplLineWithImportedScripts(snippetKtFile, CollectionsKt.drop(listComponent1, 1), next2, lineId.getNo());
                        AnalyzerWithCompilerReport.INSTANCE.reportDiagnostics((GenericDiagnostics<?>) replLineAnalysisResultAnalyzeReplLineWithImportedScripts.getDiagnostics(), (DiagnosticMessageReporter) errorHolder, false);
                        if (replLineAnalysisResultAnalyzeReplLineWithImportedScripts instanceof ReplCodeAnalyzerBase.ReplLineAnalysisResult.WithErrors) {
                            return ErrorReportingKt.failure(scriptDiagnosticsMessageCollector, new ScriptDiagnostic[0]);
                        }
                        if (replLineAnalysisResultAnalyzeReplLineWithImportedScripts instanceof ReplCodeAnalyzerBase.ReplLineAnalysisResult.Successful) {
                            ClassDescriptorWithResolutionScopes scriptDescriptor = ((ReplCodeAnalyzerBase.ReplLineAnalysisResult.Successful) replLineAnalysisResultAnalyzeReplLineWithImportedScripts).getScriptDescriptor();
                            ScriptDescriptor scriptDescriptor2 = scriptDescriptor instanceof ScriptDescriptor ? (ScriptDescriptor) scriptDescriptor : null;
                            if (scriptDescriptor2 == null) {
                                throw new AssertionError("Unexpected script descriptor type " + Reflection.getOrCreateKotlinClass(((ReplCodeAnalyzerBase.ReplLineAnalysisResult.Successful) replLineAnalysisResultAnalyzeReplLineWithImportedScripts).getScriptDescriptor().getClass()));
                            }
                            boolean z = context.getEnvironment().getConfiguration().getBoolean(JVMConfigurationKeys.IR);
                            SimpleDiagnosticsCollector simpleDiagnosticsCollector = new SimpleDiagnosticsCollector(null, 1, null);
                            Project project = snippetKtFile.getProject();
                            it = it2;
                            try {
                                Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
                                ClassBuilderFactory BINARIES = ClassBuilderFactories.BINARIES;
                                arrayList = arrayList2;
                                try {
                                    Intrinsics.checkNotNullExpressionValue(BINARIES, "BINARIES");
                                    ModuleDescriptorImpl module = replCompilationState.getAnalyzerEngine().getModule();
                                    BindingContext context2 = replCompilationState.getAnalyzerEngine().getTrace().getContext();
                                    Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
                                    GenerationState.Builder builderDiagnosticReporter = new GenerationState.Builder(project, BINARIES, module, context2, replCompilationState.getEnvironment().getConfiguration()).diagnosticReporter(simpleDiagnosticsCollector);
                                    if (z) {
                                        generationStateGenerateWithOldBackend = kJvmReplCompilerBase.generateWithBackendIr(replCompilationState, listComponent1, builderDiagnosticReporter);
                                    } else {
                                        generationStateGenerateWithOldBackend = kJvmReplCompilerBase.generateWithOldBackend(snippetKtFile, listComponent1, builderDiagnosticReporter);
                                    }
                                    if (simpleDiagnosticsCollector.getHasErrors()) {
                                        ScriptDiagnostic[] scriptDiagnosticArr3 = (ScriptDiagnostic[]) ErrorReportingKt.scriptDiagnostics(simpleDiagnosticsCollector, next2).toArray(new ScriptDiagnostic[0]);
                                        return ErrorReportingKt.failure(scriptDiagnosticsMessageCollector, (ScriptDiagnostic[]) Arrays.copyOf(scriptDiagnosticArr3, scriptDiagnosticArr3.length));
                                    }
                                    ((KJvmReplCompilerBase) kJvmReplCompilerBase).state.getHistory().push(lineId, scriptDescriptor2);
                                    final ScriptDependenciesProvider companion2 = ScriptDependenciesProvider.INSTANCE.getInstance(context.getEnvironment().getProject());
                                    resultWithDiagnosticsFailure = JvmCompilationUtilKt.makeCompiledScript(generationStateGenerateWithOldBackend, next2, (KtFile) CollectionsKt.first((List) listComponent1), listComponent2, new Function1<KtFile, ScriptCompilationConfiguration>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.KJvmReplCompilerBase$compile$2$1$2
                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                        {
                                            super(1);
                                        }

                                        @Override // kotlin.jvm.functions.Function1
                                        public final ScriptCompilationConfiguration invoke(KtFile ktFile) {
                                            ResultWithDiagnostics<ScriptCompilationConfigurationWrapper> scriptConfigurationResult2;
                                            ScriptCompilationConfigurationWrapper scriptCompilationConfigurationWrapper2;
                                            ScriptCompilationConfiguration configuration;
                                            Intrinsics.checkNotNullParameter(ktFile, "ktFile");
                                            ScriptDependenciesProvider scriptDependenciesProvider = companion2;
                                            return (scriptDependenciesProvider == null || (scriptConfigurationResult2 = scriptDependenciesProvider.getScriptConfigurationResult(ktFile, context.getBaseScriptCompilationConfiguration())) == null || (scriptCompilationConfigurationWrapper2 = (ScriptCompilationConfigurationWrapper) ErrorHandlingKt.valueOrNull(scriptConfigurationResult2)) == null || (configuration = scriptCompilationConfigurationWrapper2.getConfiguration()) == null) ? context.getBaseScriptCompilationConfiguration() : configuration;
                                        }
                                    });
                                    if (resultWithDiagnosticsFailure instanceof ResultWithDiagnostics.Success) {
                                        List<ScriptDiagnostic> reports = resultWithDiagnosticsFailure.getReports();
                                        kJvmReplCompilerBase.setLastCompiledSnippet(LinkedSnippetKt.add(kJvmReplCompilerBase.getLastCompiledSnippet(), (KJvmCompiledScript) ((ResultWithDiagnostics.Success) resultWithDiagnosticsFailure).getValue()));
                                        LinkedSnippetImpl<KJvmCompiledScript> lastCompiledSnippet = kJvmReplCompilerBase.getLastCompiledSnippet();
                                        resultWithDiagnosticsFailure = ErrorHandlingKt.plus(reports, (lastCompiledSnippet == null || (successAsSuccess = ErrorHandlingKt.asSuccess(lastCompiledSnippet, scriptDiagnosticsMessageCollector.getDiagnostics())) == null) ? ErrorReportingKt.failure(next2, scriptDiagnosticsMessageCollector, "last compiled snippet should not be null") : successAsSuccess);
                                    } else if (!(resultWithDiagnosticsFailure instanceof ResultWithDiagnostics.Failure)) {
                                        throw new NoWhenBranchMatchedException();
                                    }
                                } catch (Throwable th4) {
                                    th = th4;
                                    th = th;
                                    i = 1;
                                    ScriptDiagnostic[] scriptDiagnosticArr4 = new ScriptDiagnostic[i];
                                    if (next2 != null) {
                                        locationId = next2.getLocationId();
                                    } else {
                                        locationId = null;
                                    }
                                    scriptDiagnosticArr4[0] = ErrorHandlingKt.asDiagnostics$default(th, 0, null, locationId, null, null, 27, null);
                                    resultWithDiagnosticsFailure = ErrorReportingKt.failure(scriptDiagnosticsMessageCollector, scriptDiagnosticArr4);
                                }
                            } catch (Throwable th5) {
                                th = th5;
                                arrayList = arrayList2;
                            }
                        } else {
                            throw new AssertionError("Unexpected result " + replLineAnalysisResultAnalyzeReplLineWithImportedScripts.getClass());
                        }
                    } else {
                        arrayList = arrayList2;
                        it = it2;
                        if (!(resultWithDiagnosticsPrepareForAnalyze instanceof ResultWithDiagnostics.Failure)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        resultWithDiagnosticsFailure = (ResultWithDiagnostics.Failure) resultWithDiagnosticsPrepareForAnalyze;
                    }
                    ArrayList arrayList3 = arrayList;
                    arrayList3.add(resultWithDiagnosticsFailure);
                    arrayList2 = arrayList3;
                    it2 = it;
                } else {
                    if (resultWithDiagnosticsRefineBeforeParsing$default instanceof ResultWithDiagnostics.Failure) {
                        return (ResultWithDiagnostics.Failure) resultWithDiagnosticsRefineBeforeParsing$default;
                    }
                    throw new NoWhenBranchMatchedException();
                }
            } catch (Throwable th6) {
                th = th6;
                arrayList = arrayList2;
                it = it2;
            }
            th = th;
            i = 1;
            ScriptDiagnostic[] scriptDiagnosticArr5 = new ScriptDiagnostic[i];
            if (next2 != null) {
                locationId = next2.getLocationId();
            } else {
                locationId = null;
            }
            scriptDiagnosticArr5[0] = ErrorHandlingKt.asDiagnostics$default(th, 0, null, locationId, null, null, 27, null);
            resultWithDiagnosticsFailure = ErrorReportingKt.failure(scriptDiagnosticsMessageCollector, scriptDiagnosticArr5);
            ArrayList arrayList4 = arrayList;
            arrayList4.add(resultWithDiagnosticsFailure);
            arrayList2 = arrayList4;
            it2 = it;
        }
        return CollectionsKt.last((List) arrayList2);
    }

    protected final ResultWithDiagnostics<AnalyzePreparationResult> prepareForAnalyze(SourceCode snippet, MessageCollector parentMessageCollector, JvmReplCompilerState.Compilation compilationState, boolean failOnSyntaxErrors) {
        SharedScriptCompilationContext context;
        Intrinsics.checkNotNullParameter(snippet, "snippet");
        Intrinsics.checkNotNullParameter(parentMessageCollector, "parentMessageCollector");
        Intrinsics.checkNotNullParameter(compilationState, "compilationState");
        final ScriptDiagnosticsMessageCollector scriptDiagnosticsMessageCollector = new ScriptDiagnosticsMessageCollector(parentMessageCollector);
        try {
            ReplCompilationState replCompilationState = compilationState instanceof ReplCompilationState ? (ReplCompilationState) compilationState : null;
            if (replCompilationState != null && (context = replCompilationState.getContext()) != null) {
                UtilKt.setIdeaIoUseFallback();
                MessageCollectorBasedReporter messageCollectorBasedReporter = new MessageCollectorBasedReporter(scriptDiagnosticsMessageCollector) { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.impl.KJvmReplCompilerBase$prepareForAnalyze$1$errorHolder$1
                    private final ScriptDiagnosticsMessageCollector messageCollector;

                    {
                        this.messageCollector = scriptDiagnosticsMessageCollector;
                    }

                    @Override // org.jetbrains.kotlin.cli.common.messages.MessageCollectorBasedReporter
                    public ScriptDiagnosticsMessageCollector getMessageCollector() {
                        return this.messageCollector;
                    }
                };
                ResultWithDiagnostics<KtFile> scriptKtFile = JvmCompilationUtilKt.getScriptKtFile(snippet, context.getBaseScriptCompilationConfiguration(), context.getEnvironment().getProject(), scriptDiagnosticsMessageCollector);
                if (scriptKtFile instanceof ResultWithDiagnostics.Success) {
                    KtFile ktFile = (KtFile) ((ResultWithDiagnostics.Success) scriptKtFile).getValue();
                    AnalyzerWithCompilerReport.SyntaxErrorReport syntaxErrorReportReportSyntaxErrors = AnalyzerWithCompilerReport.INSTANCE.reportSyntaxErrors(ktFile, messageCollectorBasedReporter);
                    if (syntaxErrorReportReportSyntaxErrors.getIsHasErrors() && syntaxErrorReportReportSyntaxErrors.getIsAllErrorsAtEof()) {
                        scriptDiagnosticsMessageCollector.report(new ScriptDiagnostic(-3, "Incomplete code", null, null, null, null, 60, null));
                    }
                    return (failOnSyntaxErrors && syntaxErrorReportReportSyntaxErrors.getIsHasErrors()) ? ErrorReportingKt.failure(scriptDiagnosticsMessageCollector, new ScriptDiagnostic[0]) : ErrorHandlingKt.asSuccess$default(new AnalyzePreparationResult(context, messageCollectorBasedReporter, ktFile), null, 1, null);
                }
                if (scriptKtFile instanceof ResultWithDiagnostics.Failure) {
                    return (ResultWithDiagnostics.Failure) scriptKtFile;
                }
                throw new NoWhenBranchMatchedException();
            }
            return ErrorReportingKt.failure(snippet, scriptDiagnosticsMessageCollector, "Internal error: unknown parameter passed as compilationState: " + compilationState);
        } catch (Throwable th) {
            return ErrorReportingKt.failure(scriptDiagnosticsMessageCollector, ErrorHandlingKt.asDiagnostics$default(th, 0, null, snippet.getLocationId(), null, null, 27, null));
        }
    }
}
