package org.jetbrains.kotlin.scripting.compiler.plugin;

import java.io.File;
import java.io.PrintStream;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.CompiledScript;
import kotlin.script.experimental.api.EvaluationResult;
import kotlin.script.experimental.api.ResultValue;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptDiagnostic;
import kotlin.script.experimental.api.ScriptEvaluationConfiguration;
import kotlin.script.experimental.api.ScriptEvaluationKt;
import kotlin.script.experimental.api.ScriptEvaluator;
import kotlin.script.experimental.api.SourceCode;
import kotlin.script.experimental.host.ScriptHostUtilKt;
import kotlin.script.experimental.host.StringScriptSource;
import kotlin.script.experimental.impl.RunSuspendKt;
import kotlin.script.experimental.jvm.util.RuntimeExceptionReportingKt;
import kotlin.script.experimental.util.PropertiesCollection;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.ExitCode;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;
import org.jetbrains.kotlin.cli.common.arguments.K2JVMCompilerArguments;
import org.jetbrains.kotlin.cli.common.extensions.ScriptEvaluationExtension;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageLocation;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.com.intellij.mock.MockProject;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.scripting.configuration.ScriptingConfigurationKeys;
import org.jetbrains.kotlin.scripting.definitions.ScriptDefinition;
import org.jetbrains.kotlin.scripting.definitions.ScriptDefinitionProvider;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: AbstractScriptEvaluationExtension.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH&J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0004H&J\b\u0010\f\u001a\u00020\rH&J0\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J \u0010\u0018\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0007\u001a\u00020\bH&J\f\u0010\u001d\u001a\u00020\u001c*\u00020\u001eH$¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/AbstractScriptEvaluationExtension;", "Lorg/jetbrains/kotlin/cli/common/extensions/ScriptEvaluationExtension;", "()V", "createEnvironment", "Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;", "projectEnvironment", "Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment$ProjectEnvironment;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "createScriptCompiler", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/ScriptCompilerProxy;", "environment", "createScriptEvaluator", "Lkotlin/script/experimental/api/ScriptEvaluator;", "doEval", "Lorg/jetbrains/kotlin/cli/common/ExitCode;", "script", "Lkotlin/script/experimental/api/SourceCode;", "scriptCompilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "evaluationConfiguration", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "eval", "arguments", "Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;", "setupScriptConfiguration", "", "platformEvaluationConfiguration", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration$Builder;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractScriptEvaluationExtension implements ScriptEvaluationExtension {
    public abstract KotlinCoreEnvironment createEnvironment(KotlinCoreEnvironment.ProjectEnvironment projectEnvironment, CompilerConfiguration configuration);

    public abstract ScriptCompilerProxy createScriptCompiler(KotlinCoreEnvironment environment);

    public abstract ScriptEvaluator createScriptEvaluator();

    protected abstract void platformEvaluationConfiguration(ScriptEvaluationConfiguration.Builder builder);

    public abstract void setupScriptConfiguration(CompilerConfiguration configuration);

    /* JADX WARN: Code duplicated, block: B:25:0x009f  */
    /* JADX WARN: Code duplicated, block: B:27:0x00a5  */
    /* JADX WARN: Code duplicated, block: B:29:0x00be  */
    /* JADX WARN: Code duplicated, block: B:31:0x00d2  */
    /* JADX WARN: Code duplicated, block: B:33:0x00d8  */
    /* JADX WARN: Code duplicated, block: B:35:0x00ec  */
    /* JADX WARN: Code duplicated, block: B:38:0x00f7  */
    /* JADX WARN: Code duplicated, block: B:43:0x0124  */
    /* JADX WARN: Code duplicated, block: B:46:0x012a  */
    /* JADX WARN: Code duplicated, block: B:48:0x0142  */
    /* JADX WARN: Code duplicated, block: B:62:0x0190  */
    @Override // org.jetbrains.kotlin.cli.common.extensions.ScriptEvaluationExtension
    public ExitCode eval(CommonCompilerArguments arguments, CompilerConfiguration configuration, KotlinCoreEnvironment.ProjectEnvironment projectEnvironment) {
        String defaultScriptExtension;
        File fileNormalize;
        ExplicitlyNamedFileScriptSource scriptSource;
        StringScriptSource stringScriptSource;
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(projectEnvironment, "projectEnvironment");
        Object notNull = configuration.getNotNull(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
        Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
        MessageCollector messageCollector = (MessageCollector) notNull;
        ScriptDefinitionProvider.Companion companion = ScriptDefinitionProvider.INSTANCE;
        MockProject project = projectEnvironment.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        ScriptDefinitionProvider companion2 = companion.getInstance(project);
        if (companion2 == null) {
            MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, "Unable to process the script, scripting plugin is not configured", null, 4, null);
            return ExitCode.COMPILATION_ERROR;
        }
        setupScriptConfiguration(configuration);
        boolean z = arguments instanceof K2JVMCompilerArguments;
        K2JVMCompilerArguments k2JVMCompilerArguments = z ? (K2JVMCompilerArguments) arguments : null;
        if (k2JVMCompilerArguments == null || (defaultScriptExtension = k2JVMCompilerArguments.getDefaultScriptExtension()) == null) {
            defaultScriptExtension = null;
        } else if (!StringsKt.startsWith$default((CharSequence) defaultScriptExtension, '.', false, 2, (Object) null)) {
            defaultScriptExtension = "." + defaultScriptExtension;
        }
        if (z) {
            K2JVMCompilerArguments k2JVMCompilerArguments2 = (K2JVMCompilerArguments) arguments;
            if (k2JVMCompilerArguments2.getExpression() != null) {
                String expression = k2JVMCompilerArguments2.getExpression();
                Intrinsics.checkNotNull(expression);
                StringBuilder sb = new StringBuilder("script");
                if (defaultScriptExtension == null) {
                    defaultScriptExtension = ".kts";
                }
                stringScriptSource = new StringScriptSource(expression, sb.append(defaultScriptExtension).toString());
            } else if (arguments.getScript()) {
                fileNormalize = FilesKt.normalize(new File((String) CollectionsKt.first((List) arguments.getFreeArgs())));
                if (!fileNormalize.exists()) {
                    return eval$invalidScript(configuration, messageCollector, "Script file not found: " + fileNormalize);
                }
                if (fileNormalize.isDirectory()) {
                    return eval$invalidScript(configuration, messageCollector, "Script argument points to a directory: " + fileNormalize);
                }
                scriptSource = ScriptHostUtilKt.toScriptSource(fileNormalize);
                if (!companion2.isScript(scriptSource)) {
                    scriptSource = null;
                }
                if (scriptSource == null && defaultScriptExtension != null) {
                    ExplicitlyNamedFileScriptSource explicitlyNamedFileScriptSource = new ExplicitlyNamedFileScriptSource(FilesKt.getNameWithoutExtension(fileNormalize) + defaultScriptExtension, fileNormalize, null, 4, null);
                    scriptSource = companion2.isScript(explicitlyNamedFileScriptSource) ? explicitlyNamedFileScriptSource : null;
                }
                if (scriptSource == null) {
                    return eval$invalidScript(configuration, messageCollector, "Unrecognized script type: " + fileNormalize.getName());
                }
                stringScriptSource = scriptSource;
            } else {
                MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, "Illegal set of arguments: either -script or -expression arguments expected at this point", null, 4, null);
                return ExitCode.COMPILATION_ERROR;
            }
        } else if (arguments.getScript()) {
            fileNormalize = FilesKt.normalize(new File((String) CollectionsKt.first((List) arguments.getFreeArgs())));
            if (!fileNormalize.exists()) {
                return eval$invalidScript(configuration, messageCollector, "Script file not found: " + fileNormalize);
            }
            if (fileNormalize.isDirectory()) {
                return eval$invalidScript(configuration, messageCollector, "Script argument points to a directory: " + fileNormalize);
            }
            scriptSource = ScriptHostUtilKt.toScriptSource(fileNormalize);
            if (!companion2.isScript(scriptSource)) {
                scriptSource = null;
            }
            if (scriptSource == null) {
                ExplicitlyNamedFileScriptSource explicitlyNamedFileScriptSource2 = new ExplicitlyNamedFileScriptSource(FilesKt.getNameWithoutExtension(fileNormalize) + defaultScriptExtension, fileNormalize, null, 4, null);
                scriptSource = companion2.isScript(explicitlyNamedFileScriptSource2) ? explicitlyNamedFileScriptSource2 : null;
            }
            if (scriptSource == null) {
                return eval$invalidScript(configuration, messageCollector, "Unrecognized script type: " + fileNormalize.getName());
            }
            stringScriptSource = scriptSource;
        } else {
            MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, "Illegal set of arguments: either -script or -expression arguments expected at this point", null, 4, null);
            return ExitCode.COMPILATION_ERROR;
        }
        KotlinCoreEnvironment kotlinCoreEnvironmentCreateEnvironment = createEnvironment(projectEnvironment, configuration);
        if (messageCollector.getHasErrors()) {
            return ExitCode.COMPILATION_ERROR;
        }
        ScriptDefinition scriptDefinitionFindDefinition = companion2.findDefinition(stringScriptSource);
        if (scriptDefinitionFindDefinition == null) {
            scriptDefinitionFindDefinition = companion2.getDefaultDefinition();
        }
        ScriptCompilationConfiguration compilationConfiguration = scriptDefinitionFindDefinition.getCompilationConfiguration();
        final List<String> listSubList = arguments.getScript() ? arguments.getFreeArgs().subList(1, arguments.getFreeArgs().size()) : arguments.getFreeArgs();
        return doEval(stringScriptSource, compilationConfiguration, ScriptEvaluationKt.with(scriptDefinitionFindDefinition.getEvaluationConfiguration(), new Function1<ScriptEvaluationConfiguration.Builder, Unit>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.AbstractScriptEvaluationExtension$eval$evaluationConfiguration$1
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
            public final void invoke2(ScriptEvaluationConfiguration.Builder with) {
                Intrinsics.checkNotNullParameter(with, "$this$with");
                with.invoke((PropertiesCollection.Key) ScriptEvaluationKt.getConstructorArgs(with), listSubList.toArray(new String[0]));
                this.platformEvaluationConfiguration(with);
            }
        }), kotlinCoreEnvironmentCreateEnvironment, messageCollector);
    }

    private static final ExitCode eval$invalidScript(CompilerConfiguration compilerConfiguration, MessageCollector messageCollector, String str) {
        String str2;
        List list = (List) compilerConfiguration.get(ScriptingConfigurationKeys.INSTANCE.getSCRIPT_DEFINITIONS());
        if (list != null && list.size() == 1 && ((ScriptDefinition) CollectionsKt.first(list)).getIsDefault()) {
            str2 = " (.kts)";
        } else {
            str2 = "";
        }
        MessageCollector.report$default(messageCollector, CompilerMessageSeverity.ERROR, str + "; Specify path to the script file" + str2 + " as the first argument", null, 4, null);
        return ExitCode.COMPILATION_ERROR;
    }

    private final ExitCode doEval(SourceCode script, ScriptCompilationConfiguration scriptCompilationConfiguration, ScriptEvaluationConfiguration evaluationConfiguration, KotlinCoreEnvironment environment, MessageCollector messageCollector) {
        return (ExitCode) RunSuspendKt.internalScriptingRunSuspend(new AnonymousClass1(createScriptCompiler(environment), script, scriptCompilationConfiguration, this, evaluationConfiguration, messageCollector, null));
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.scripting.compiler.plugin.AbstractScriptEvaluationExtension$doEval$1, reason: invalid class name */
    /* JADX INFO: compiled from: AbstractScriptEvaluationExtension.kt */
    @Metadata(d1 = {"\u0000\u0006\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Lorg/jetbrains/kotlin/cli/common/ExitCode;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.scripting.compiler.plugin.AbstractScriptEvaluationExtension$doEval$1", f = "AbstractScriptEvaluationExtension.kt", i = {}, l = {156}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function1<Continuation<? super ExitCode>, Object> {
        final /* synthetic */ ScriptEvaluationConfiguration $evaluationConfiguration;
        final /* synthetic */ MessageCollector $messageCollector;
        final /* synthetic */ SourceCode $script;
        final /* synthetic */ ScriptCompilationConfiguration $scriptCompilationConfiguration;
        final /* synthetic */ ScriptCompilerProxy $scriptCompiler;
        int label;
        final /* synthetic */ AbstractScriptEvaluationExtension this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(ScriptCompilerProxy scriptCompilerProxy, SourceCode sourceCode, ScriptCompilationConfiguration scriptCompilationConfiguration, AbstractScriptEvaluationExtension abstractScriptEvaluationExtension, ScriptEvaluationConfiguration scriptEvaluationConfiguration, MessageCollector messageCollector, Continuation<? super AnonymousClass1> continuation) {
            super(1, continuation);
            this.$scriptCompiler = scriptCompilerProxy;
            this.$script = sourceCode;
            this.$scriptCompilationConfiguration = scriptCompilationConfiguration;
            this.this$0 = abstractScriptEvaluationExtension;
            this.$evaluationConfiguration = scriptEvaluationConfiguration;
            this.$messageCollector = messageCollector;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Continuation<?> continuation) {
            return new AnonymousClass1(this.$scriptCompiler, this.$script, this.$scriptCompilationConfiguration, this.this$0, this.$evaluationConfiguration, this.$messageCollector, continuation);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Object invoke(Continuation<? super ExitCode> continuation) {
            return ((AnonymousClass1) create(continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            CompilerMessageLocation compilerMessageLocationCreate;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ResultWithDiagnostics<CompiledScript> resultWithDiagnosticsCompile = this.$scriptCompiler.compile(this.$script, this.$scriptCompilationConfiguration);
                SourceCode sourceCode = this.$script;
                MessageCollector messageCollector = this.$messageCollector;
                if (resultWithDiagnosticsCompile instanceof ResultWithDiagnostics.Success) {
                    CompiledScript compiledScript = (CompiledScript) ((ResultWithDiagnostics.Success) resultWithDiagnosticsCompile).getValue();
                    this.label = 1;
                    obj = this.this$0.createScriptEvaluator().invoke(compiledScript, this.$evaluationConfiguration, this);
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (!(resultWithDiagnosticsCompile instanceof ResultWithDiagnostics.Failure)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    ResultWithDiagnostics.Failure failure = (ResultWithDiagnostics.Failure) resultWithDiagnosticsCompile;
                    List<String> listLines = failure.getReports().isEmpty() ? null : StringsKt.lines(sourceCode.getText());
                    for (ScriptDiagnostic scriptDiagnostic : failure.getReports()) {
                        SourceCode.Location location = scriptDiagnostic.getLocation();
                        String sourcePath = scriptDiagnostic.getSourcePath();
                        CompilerMessageSeverity compilerMessageSeverity = AbstractScriptEvaluationExtensionKt.toCompilerMessageSeverity(scriptDiagnostic.getSeverity());
                        String strRender$default = ScriptDiagnostic.render$default(scriptDiagnostic, false, location == null || sourcePath == null, false, false, 12, null);
                        if (location == null || sourcePath == null) {
                            compilerMessageLocationCreate = null;
                        } else {
                            compilerMessageLocationCreate = CompilerMessageLocation.INSTANCE.create(sourcePath, location.getStart().getLine(), location.getStart().getCol(), listLines != null ? (String) CollectionsKt.getOrNull(listLines, location.getStart().getLine() - 1) : null);
                        }
                        messageCollector.report(compilerMessageSeverity, strRender$default, compilerMessageLocationCreate);
                    }
                    return ExitCode.COMPILATION_ERROR;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
            }
            ResultWithDiagnostics resultWithDiagnostics = (ResultWithDiagnostics) obj;
            MessageCollector messageCollector2 = this.$messageCollector;
            if (resultWithDiagnostics instanceof ResultWithDiagnostics.Success) {
                EvaluationResult evaluationResult = (EvaluationResult) ((ResultWithDiagnostics.Success) resultWithDiagnostics).getValue();
                ResultValue returnValue = evaluationResult.getReturnValue();
                if (returnValue instanceof ResultValue.Value) {
                    ResultValue returnValue2 = evaluationResult.getReturnValue();
                    Intrinsics.checkNotNull(returnValue2, "null cannot be cast to non-null type kotlin.script.experimental.api.ResultValue.Value");
                    System.out.println(((ResultValue.Value) returnValue2).getValue());
                    return ExitCode.OK;
                }
                if (returnValue instanceof ResultValue.Error) {
                    ResultValue returnValue3 = evaluationResult.getReturnValue();
                    Intrinsics.checkNotNull(returnValue3, "null cannot be cast to non-null type kotlin.script.experimental.api.ResultValue.Error");
                    PrintStream err = System.err;
                    Intrinsics.checkNotNullExpressionValue(err, "err");
                    RuntimeExceptionReportingKt.renderError((ResultValue.Error) returnValue3, err);
                    return ExitCode.SCRIPT_EXECUTION_ERROR;
                }
                return ExitCode.OK;
            }
            if (!(resultWithDiagnostics instanceof ResultWithDiagnostics.Failure)) {
                throw new NoWhenBranchMatchedException();
            }
            for (ScriptDiagnostic scriptDiagnostic2 : ((ResultWithDiagnostics.Failure) resultWithDiagnostics).getReports()) {
                MessageCollector.report$default(messageCollector2, AbstractScriptEvaluationExtensionKt.toCompilerMessageSeverity(scriptDiagnostic2.getSeverity()), ScriptDiagnostic.render$default(scriptDiagnostic2, false, false, false, false, 14, null), null, 4, null);
            }
            return ExitCode.INTERNAL_ERROR;
        }
    }
}
