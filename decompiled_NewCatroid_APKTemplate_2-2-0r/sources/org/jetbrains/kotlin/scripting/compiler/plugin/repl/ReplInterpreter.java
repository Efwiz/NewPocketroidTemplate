package org.jetbrains.kotlin.scripting.compiler.plugin.repl;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptCompilationKt;
import kotlin.script.experimental.api.ScriptDiagnostic;
import kotlin.script.experimental.api.ScriptEvaluationConfiguration;
import kotlin.script.experimental.api.ScriptEvaluationKt;
import kotlin.script.experimental.api.SourceCode;
import kotlin.script.experimental.host.ScriptHostUtilKt;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import kotlin.script.experimental.impl.RunSuspendKt;
import kotlin.script.experimental.jvm.BasicJvmReplEvaluator;
import kotlin.script.experimental.jvm.JvmScriptingHostConfigurationKt;
import kotlin.text.StringsKt;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.config.ContentRoot;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageLocation;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.repl.ReplClassLoader;
import org.jetbrains.kotlin.cli.common.repl.ReplEvalResult;
import org.jetbrains.kotlin.cli.jvm.compiler.CoreEnvironmentUtilsKt;
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.cli.jvm.config.JvmClasspathRoot;
import org.jetbrains.kotlin.cli.jvm.config.JvmModulePathRoot;
import org.jetbrains.kotlin.com.intellij.core.JavaCoreProjectEnvironment;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.resolve.calls.tower.ImplicitsExtensionsResolutionFilter;
import org.jetbrains.kotlin.scripting.compiler.plugin.impl.CompilationContextKt;
import org.jetbrains.kotlin.scripting.compiler.plugin.impl.KJvmReplCompilerBase;
import org.jetbrains.kotlin.scripting.compiler.plugin.impl.ReplCompilationState;
import org.jetbrains.kotlin.scripting.compiler.plugin.impl.ScriptDiagnosticsMessageCollector;
import org.jetbrains.kotlin.scripting.compiler.plugin.impl.SharedScriptCompilationContext;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.configuration.ReplConfiguration;
import org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinition;
import org.jetbrains.kotlin.scripting.definitions.ScriptCompilationConfigurationFromDefinition;
import org.jetbrains.kotlin.scripting.definitions.ScriptEvaluationConfigurationFromDefinition;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ReplInterpreter.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u008d\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003*\u0001\u001c\u0018\u0000 .2\u00020\u0001:\u0001.B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'J\u000e\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020 J\u0010\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020 H\u0002R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u001dR\u001e\u0010\u001e\u001a\u0012\u0012\u0004\u0012\u00020 0\u001fj\b\u0012\u0004\u0012\u00020 `!X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\"\u001a\u0006\u0012\u0002\b\u00030#X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006/"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplInterpreter;", "", "projectEnvironment", "Lorg/jetbrains/kotlin/com/intellij/core/JavaCoreProjectEnvironment;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "replConfiguration", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/configuration/ReplConfiguration;", "(Lcom/intellij/core/JavaCoreProjectEnvironment;Lorg/jetbrains/kotlin/config/CompilerConfiguration;Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/configuration/ReplConfiguration;)V", "classLoader", "Lorg/jetbrains/kotlin/cli/common/repl/ReplClassLoader;", "classpathRoots", "", "Ljava/io/File;", "compilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "compiler", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/impl/KJvmReplCompilerBase;", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplCodeAnalyzerBase;", "evaluationConfiguration", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "evaluator", "Lkotlin/script/experimental/jvm/BasicJvmReplEvaluator;", "hostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "lineNumber", "Ljava/util/concurrent/atomic/AtomicInteger;", "messageCollector", "org/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplInterpreter$messageCollector$1", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplInterpreter$messageCollector$1;", "previousIncompleteLines", "Ljava/util/ArrayList;", "", "Lkotlin/collections/ArrayList;", "replState", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/JvmReplCompilerState;", "dumpClasses", "", "out", "Ljava/io/PrintWriter;", "eval", "Lorg/jetbrains/kotlin/cli/common/repl/ReplEvalResult;", Constant.LINE, "nextSnippet", "Lkotlin/script/experimental/api/SourceCode;", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "Companion", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReplInterpreter {
    private static final ReplInterpreter$Companion$REPL_LINE_AS_SCRIPT_DEFINITION$1 REPL_LINE_AS_SCRIPT_DEFINITION;
    private final ReplClassLoader classLoader;
    private final List<File> classpathRoots;
    private final ScriptCompilationConfiguration compilationConfiguration;
    private final KJvmReplCompilerBase<ReplCodeAnalyzerBase> compiler;
    private final CompilerConfiguration configuration;
    private final ScriptEvaluationConfiguration evaluationConfiguration;
    private final BasicJvmReplEvaluator evaluator;
    private final ScriptingHostConfiguration hostConfiguration;
    private final AtomicInteger lineNumber;
    private final ReplInterpreter$messageCollector$1 messageCollector;
    private final ArrayList<String> previousIncompleteLines;
    private final ReplConfiguration replConfiguration;
    private final JvmReplCompilerState<?> replState;

    /* JADX INFO: compiled from: ReplInterpreter.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ScriptDiagnostic.Severity.values().length];
            try {
                iArr[ScriptDiagnostic.Severity.ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ScriptDiagnostic.Severity.FATAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ScriptDiagnostic.Severity.WARNING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ReplInterpreter(JavaCoreProjectEnvironment projectEnvironment, CompilerConfiguration configuration, ReplConfiguration replConfiguration) {
        KotlinCoreEnvironment kotlinCoreEnvironmentCreateForProduction;
        File file;
        Intrinsics.checkNotNullParameter(projectEnvironment, "projectEnvironment");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(replConfiguration, "replConfiguration");
        this.configuration = configuration;
        this.replConfiguration = replConfiguration;
        ScriptingHostConfiguration defaultJvmScriptingHostConfiguration = JvmScriptingHostConfigurationKt.getDefaultJvmScriptingHostConfiguration();
        this.hostConfiguration = defaultJvmScriptingHostConfiguration;
        Object[] objArr = 0;
        Object[] objArr2 = 0;
        KotlinCoreEnvironment.ProjectEnvironment projectEnvironment2 = projectEnvironment instanceof KotlinCoreEnvironment.ProjectEnvironment ? (KotlinCoreEnvironment.ProjectEnvironment) projectEnvironment : null;
        if (projectEnvironment2 == null || (kotlinCoreEnvironmentCreateForProduction = KotlinCoreEnvironment.INSTANCE.createForProduction(projectEnvironment2, configuration, EnvironmentConfigFiles.JVM_CONFIG_FILES)) == null) {
            KotlinCoreEnvironment.Companion companion = KotlinCoreEnvironment.INSTANCE;
            Disposable parentDisposable = projectEnvironment.getParentDisposable();
            Intrinsics.checkNotNullExpressionValue(parentDisposable, "getParentDisposable(...)");
            kotlinCoreEnvironmentCreateForProduction = companion.createForProduction(parentDisposable, configuration, EnvironmentConfigFiles.JVM_CONFIG_FILES);
        }
        ReplInterpreter$Companion$REPL_LINE_AS_SCRIPT_DEFINITION$1 replInterpreter$Companion$REPL_LINE_AS_SCRIPT_DEFINITION$1 = REPL_LINE_AS_SCRIPT_DEFINITION;
        final SharedScriptCompilationContext sharedScriptCompilationContextCreateCompilationContextFromEnvironment = CompilationContextKt.createCompilationContextFromEnvironment(new ScriptCompilationConfigurationFromDefinition(defaultJvmScriptingHostConfiguration, replInterpreter$Companion$REPL_LINE_AS_SCRIPT_DEFINITION$1), kotlinCoreEnvironmentCreateForProduction, new ScriptDiagnosticsMessageCollector(CoreEnvironmentUtilsKt.getMessageCollector(kotlinCoreEnvironmentCreateForProduction)));
        this.compilationConfiguration = sharedScriptCompilationContextCreateCompilationContextFromEnvironment.getBaseScriptCompilationConfiguration();
        this.evaluationConfiguration = ScriptEvaluationKt.with(new ScriptEvaluationConfigurationFromDefinition(defaultJvmScriptingHostConfiguration, replInterpreter$Companion$REPL_LINE_AS_SCRIPT_DEFINITION$1), new Function1<ScriptEvaluationConfiguration.Builder, Unit>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplInterpreter.1
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
                final ReplInterpreter replInterpreter = ReplInterpreter.this;
                ScriptEvaluationKt.scriptExecutionWrapper(with, new Function1<Function0<? extends Object>, Object>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplInterpreter.1.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Function0<? extends Object> it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return replInterpreter.replConfiguration.getExecutionInterceptor().execute(it);
                    }
                });
            }
        });
        JvmReplCompilerState<?> jvmReplCompilerState = new JvmReplCompilerState<>(new Function1<ScriptCompilationConfiguration, ReplCompilationState<ReplCodeAnalyzerBase>>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplInterpreter.2
            {
                super(1);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function1
            public final ReplCompilationState<ReplCodeAnalyzerBase> invoke(ScriptCompilationConfiguration it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return new ReplCompilationState<>(sharedScriptCompilationContextCreateCompilationContextFromEnvironment, new Function2<SharedScriptCompilationContext, ImplicitsExtensionsResolutionFilter, ReplCodeAnalyzerBase>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplInterpreter.2.1
                    @Override // kotlin.jvm.functions.Function2
                    public final ReplCodeAnalyzerBase invoke(SharedScriptCompilationContext context1, ImplicitsExtensionsResolutionFilter resolutionFilter) {
                        Intrinsics.checkNotNullParameter(context1, "context1");
                        Intrinsics.checkNotNullParameter(resolutionFilter, "resolutionFilter");
                        return new ReplCodeAnalyzerBase(context1.getEnvironment(), null, resolutionFilter, 2, null);
                    }
                }, new ReplImplicitsExtensionsResolutionFilter(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0));
            }
        }, null, 2, null);
        this.replState = jvmReplCompilerState;
        this.compiler = new KJvmReplCompilerBase<>(defaultJvmScriptingHostConfiguration, jvmReplCompilerState);
        this.evaluator = new BasicJvmReplEvaluator(objArr2 == true ? 1 : 0, 1, objArr == true ? 1 : 0);
        this.lineNumber = new AtomicInteger();
        this.previousIncompleteLines = new ArrayList<>();
        List<ContentRoot> list = configuration.getList(CLIConfigurationKeys.CONTENT_ROOTS);
        Intrinsics.checkNotNullExpressionValue(list, "getList(...)");
        ArrayList arrayList = new ArrayList();
        for (ContentRoot contentRoot : list) {
            if (contentRoot instanceof JvmModulePathRoot) {
                file = ((JvmModulePathRoot) contentRoot).getFile();
            } else {
                file = contentRoot instanceof JvmClasspathRoot ? ((JvmClasspathRoot) contentRoot).getFile() : null;
            }
            if (file != null) {
                arrayList.add(file);
            }
        }
        ArrayList arrayList2 = arrayList;
        this.classpathRoots = arrayList2;
        ArrayList arrayList3 = arrayList2;
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
        Iterator it = arrayList3.iterator();
        while (it.hasNext()) {
            arrayList4.add(((File) it.next()).toURI().toURL());
        }
        URL[] urlArr = (URL[]) arrayList4.toArray(new URL[0]);
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        this.classLoader = new ReplClassLoader(new URLClassLoader(urlArr, systemClassLoader != null ? systemClassLoader.getParent() : null));
        this.messageCollector = new ReplInterpreter$messageCollector$1(this);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplInterpreter$Companion$REPL_LINE_AS_SCRIPT_DEFINITION$1] */
    static {
        final KClass orCreateKotlinClass = Reflection.getOrCreateKotlinClass(Object.class);
        REPL_LINE_AS_SCRIPT_DEFINITION = new KotlinScriptDefinition(orCreateKotlinClass) { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplInterpreter$Companion$REPL_LINE_AS_SCRIPT_DEFINITION$1
            private final String name = "Kotlin REPL";

            @Override // org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinition
            public String getName() {
                return this.name;
            }
        };
    }

    private final SourceCode nextSnippet(String code) {
        return ScriptHostUtilKt.toScriptSource(code, "Line_" + this.lineNumber.getAndIncrement() + '.' + ((String) this.compilationConfiguration.get(ScriptCompilationKt.getFileExtension(ScriptCompilationConfiguration.INSTANCE))));
    }

    public final ReplEvalResult eval(String line) {
        Intrinsics.checkNotNullParameter(line, "line");
        try {
            ReplEvalResult replEvalResult = (ReplEvalResult) RunSuspendKt.internalScriptingRunSuspend(new ReplInterpreter$eval$evalRes$1(this, nextSnippet(CollectionsKt.joinToString$default(CollectionsKt.plus((Collection<? extends String>) this.previousIncompleteLines, line), "\n", null, null, 0, null, null, 62, null)), null));
            if (!(replEvalResult instanceof ReplEvalResult.Incomplete)) {
                this.previousIncompleteLines.clear();
            } else {
                if (!this.replConfiguration.getAllowIncompleteLines()) {
                    return new ReplEvalResult.Error.CompileTime("incomplete code", null, 2, null);
                }
                this.previousIncompleteLines.add(line);
            }
            return replEvalResult;
        } catch (Throwable th) {
            PrintWriter printWriter = new PrintWriter(System.err);
            this.classLoader.dumpClasses(printWriter);
            printWriter.flush();
            throw th;
        }
    }

    private static final CompilerMessageLocation eval$toCompilerMessageLocation(SourceCode.Location location, SourceCode sourceCode) {
        return CompilerMessageLocation.INSTANCE.create(sourceCode.getName(), location.getStart().getLine(), location.getStart().getCol(), (String) CollectionsKt.getOrNull(StringsKt.lines(sourceCode.getText()), location.getStart().getLine() - 1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void eval$reportToMessageCollector(ResultWithDiagnostics<?> resultWithDiagnostics, ReplInterpreter replInterpreter, SourceCode sourceCode) {
        CompilerMessageSeverity compilerMessageSeverity;
        for (ScriptDiagnostic scriptDiagnostic : resultWithDiagnostics.getReports()) {
            int i = WhenMappings.$EnumSwitchMapping$0[scriptDiagnostic.getSeverity().ordinal()];
            if (i == 1) {
                compilerMessageSeverity = CompilerMessageSeverity.ERROR;
            } else if (i == 2) {
                compilerMessageSeverity = CompilerMessageSeverity.EXCEPTION;
            } else if (i == 3) {
                compilerMessageSeverity = CompilerMessageSeverity.WARNING;
            }
            ReplInterpreter$messageCollector$1 replInterpreter$messageCollector$1 = replInterpreter.messageCollector;
            String message = scriptDiagnostic.getMessage();
            SourceCode.Location location = scriptDiagnostic.getLocation();
            replInterpreter$messageCollector$1.report(compilerMessageSeverity, message, location != null ? eval$toCompilerMessageLocation(location, sourceCode) : null);
        }
    }

    public final void dumpClasses(PrintWriter out) {
        Intrinsics.checkNotNullParameter(out, "out");
        this.classLoader.dumpClasses(out);
    }
}
