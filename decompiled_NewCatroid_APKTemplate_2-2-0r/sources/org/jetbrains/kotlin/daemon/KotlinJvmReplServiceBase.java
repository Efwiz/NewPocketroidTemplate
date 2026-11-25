package org.jetbrains.kotlin.daemon;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.cli.common.extensions.ReplFactoryExtension;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.repl.CreateReplStageStateAction;
import org.jetbrains.kotlin.cli.common.repl.IReplStageState;
import org.jetbrains.kotlin.cli.common.repl.ReplCheckAction;
import org.jetbrains.kotlin.cli.common.repl.ReplCheckResult;
import org.jetbrains.kotlin.cli.common.repl.ReplCodeLine;
import org.jetbrains.kotlin.cli.common.repl.ReplCompileAction;
import org.jetbrains.kotlin.cli.common.repl.ReplCompileResult;
import org.jetbrains.kotlin.cli.common.repl.ReplCompiler;
import org.jetbrains.kotlin.cli.jvm.JvmArgumentsKt;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.cli.jvm.config.JvmContentRootsKt;
import org.jetbrains.kotlin.com.intellij.mock.MockProject;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.config.AnalysisFlags;
import org.jetbrains.kotlin.config.ApiVersion;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.LanguageVersion;
import org.jetbrains.kotlin.config.LanguageVersionSettingsImpl;
import org.jetbrains.kotlin.daemon.common.CompilerId;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.utils.KotlinPaths;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: KotlinRemoteReplService.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B3\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fJ\u0010\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020\fH\u0014J\u0010\u0010/\u001a\u00020-2\u0006\u0010.\u001a\u00020\fH\u0014J\u001c\u00100\u001a\u0002012\n\u00102\u001a\u0006\u0012\u0002\b\u0003032\u0006\u00104\u001a\u000205H\u0016J\u001c\u00106\u001a\u0002072\n\u00102\u001a\u0006\u0012\u0002\b\u0003032\u0006\u00104\u001a\u000205H\u0016J\u0014\u00108\u001a\u0006\u0012\u0002\b\u0003032\u0006\u00109\u001a\u00020)H\u0016R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u0013X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R#\u0010\u0016\u001a\n \u0018*\u0004\u0018\u00010\u00170\u00178BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\r\u001a\u00020\u000eX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u001d\u0010\u001f\u001a\u0004\u0018\u00010 8DX\u0084\u0084\u0002¢\u0006\f\n\u0004\b#\u0010\u001c\u001a\u0004\b!\u0010\"R\u0014\u0010$\u001a\u00020%X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'R\u0014\u0010(\u001a\u00020)X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+¨\u0006:"}, d2 = {"Lorg/jetbrains/kotlin/daemon/KotlinJvmReplServiceBase;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCompileAction;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCheckAction;", "Lorg/jetbrains/kotlin/cli/common/repl/CreateReplStageStateAction;", "disposable", "Lorg/jetbrains/kotlin/com/intellij/openapi/Disposable;", "compilerId", "Lorg/jetbrains/kotlin/daemon/common/CompilerId;", "templateClasspath", "", "Ljava/io/File;", "templateClassName", "", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "(Lcom/intellij/openapi/Disposable;Lorg/jetbrains/kotlin/daemon/common/CompilerId;Ljava/util/List;Ljava/lang/String;Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;)V", "getCompilerId", "()Lorg/jetbrains/kotlin/daemon/common/CompilerId;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "getConfiguration", "()Lorg/jetbrains/kotlin/config/CompilerConfiguration;", K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_LOG, "Ljava/util/logging/Logger;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getLog", "()Ljava/util/logging/Logger;", "log$delegate", "Lkotlin/Lazy;", "getMessageCollector", "()Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "replCompiler", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCompiler;", "getReplCompiler", "()Lorg/jetbrains/kotlin/cli/common/repl/ReplCompiler;", "replCompiler$delegate", "stateIdCounter", "Ljava/util/concurrent/atomic/AtomicInteger;", "getStateIdCounter", "()Ljava/util/concurrent/atomic/AtomicInteger;", "statesLock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "getStatesLock", "()Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "after", "", "s", "before", "check", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCheckResult;", "state", "Lorg/jetbrains/kotlin/cli/common/repl/IReplStageState;", "codeLine", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCodeLine;", "compile", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCompileResult;", "createState", "lock", "kotlin-daemon"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KotlinJvmReplServiceBase implements ReplCompileAction, ReplCheckAction, CreateReplStageStateAction {
    private final CompilerId compilerId;
    private final CompilerConfiguration configuration;

    /* JADX INFO: renamed from: log$delegate, reason: from kotlin metadata */
    private final Lazy log;
    private final MessageCollector messageCollector;

    /* JADX INFO: renamed from: replCompiler$delegate, reason: from kotlin metadata */
    private final Lazy replCompiler;
    private final AtomicInteger stateIdCounter;
    private final ReentrantReadWriteLock statesLock;

    protected void after(String s) {
        Intrinsics.checkNotNullParameter(s, "s");
    }

    protected void before(String s) {
        Intrinsics.checkNotNullParameter(s, "s");
    }

    public KotlinJvmReplServiceBase(final Disposable disposable, CompilerId compilerId, final List<? extends File> templateClasspath, final String templateClassName, MessageCollector messageCollector) {
        Intrinsics.checkNotNullParameter(disposable, "disposable");
        Intrinsics.checkNotNullParameter(compilerId, "compilerId");
        Intrinsics.checkNotNullParameter(templateClasspath, "templateClasspath");
        Intrinsics.checkNotNullParameter(templateClassName, "templateClassName");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        this.compilerId = compilerId;
        this.messageCollector = messageCollector;
        this.log = LazyKt.lazy(new Function0<Logger>() { // from class: org.jetbrains.kotlin.daemon.KotlinJvmReplServiceBase$log$2
            @Override // kotlin.jvm.functions.Function0
            public final Logger invoke() {
                return Logger.getLogger("replService");
            }
        });
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        compilerConfiguration.put(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, messageCollector);
        KotlinPaths kotlinPathsForCompiler = PathUtil.getKotlinPathsForCompiler();
        JvmContentRootsKt.addJvmClasspathRoots(compilerConfiguration, CollectionsKt.listOf((Object[]) new File[]{kotlinPathsForCompiler.getStdlibPath(), kotlinPathsForCompiler.getReflectPath(), kotlinPathsForCompiler.getScriptRuntimePath()}));
        JvmContentRootsKt.addJvmClasspathRoots(compilerConfiguration, templateClasspath);
        JvmArgumentsKt.configureJdkHomeFromSystemProperty(compilerConfiguration);
        JvmContentRootsKt.configureJdkClasspathRoots(compilerConfiguration);
        compilerConfiguration.put(CommonConfigurationKeys.MODULE_NAME, "kotlin-script");
        CommonConfigurationKeysKt.setLanguageVersionSettings(compilerConfiguration, new LanguageVersionSettingsImpl(LanguageVersion.LATEST_STABLE, ApiVersion.LATEST_STABLE, MapsKt.mapOf(TuplesKt.to(AnalysisFlags.getSkipMetadataVersionCheck(), true)), null, 8, null));
        KotlinRemoteReplServiceKt.configureScripting(compilerConfiguration, compilerId);
        this.configuration = compilerConfiguration;
        this.replCompiler = LazyKt.lazy(new Function0<ReplCompiler>() { // from class: org.jetbrains.kotlin.daemon.KotlinJvmReplServiceBase$replCompiler$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ReplCompiler invoke() {
                try {
                    KotlinCoreEnvironment.ProjectEnvironment projectEnvironment = new KotlinCoreEnvironment.ProjectEnvironment(disposable, KotlinCoreEnvironment.INSTANCE.getOrCreateApplicationEnvironmentForProduction(disposable, this.getConfiguration()), this.getConfiguration());
                    ReplFactoryExtension.Companion companion = ReplFactoryExtension.INSTANCE;
                    MockProject project = projectEnvironment.getProject();
                    Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
                    companion.registerExtensionPoint(project);
                    projectEnvironment.registerExtensionsFromPlugins(this.getConfiguration());
                    ReplFactoryExtension.Companion companion2 = ReplFactoryExtension.INSTANCE;
                    MockProject project2 = projectEnvironment.getProject();
                    Intrinsics.checkNotNullExpressionValue(project2, "getProject(...)");
                    List<ReplFactoryExtension> instances = companion2.getInstances(project2);
                    if (instances.isEmpty()) {
                        throw new IllegalStateException("no scripting plugin loaded");
                    }
                    if (instances.size() > 1) {
                        throw new IllegalStateException("several scripting plugins loaded");
                    }
                    return ((ReplFactoryExtension) CollectionsKt.first((List) instances)).makeReplCompiler(templateClassName, templateClasspath, this.getClass().getClassLoader(), this.getConfiguration(), projectEnvironment);
                } catch (Throwable th) {
                    MessageCollector.report$default(this.getMessageCollector(), CompilerMessageSeverity.ERROR, "Unable to construct repl compiler: " + th.getMessage(), null, 4, null);
                    throw new IllegalStateException("Unable to use scripting/REPL in the daemon: " + th.getMessage(), th);
                }
            }
        });
        this.statesLock = new ReentrantReadWriteLock();
        this.stateIdCounter = new AtomicInteger();
    }

    public final CompilerId getCompilerId() {
        return this.compilerId;
    }

    protected final MessageCollector getMessageCollector() {
        return this.messageCollector;
    }

    private final Logger getLog() {
        return (Logger) this.log.getValue();
    }

    protected final CompilerConfiguration getConfiguration() {
        return this.configuration;
    }

    protected final ReplCompiler getReplCompiler() {
        return (ReplCompiler) this.replCompiler.getValue();
    }

    protected final ReentrantReadWriteLock getStatesLock() {
        return this.statesLock;
    }

    protected final AtomicInteger getStateIdCounter() {
        return this.stateIdCounter;
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.CreateReplStageStateAction
    public IReplStageState<?> createState(ReentrantReadWriteLock lock) {
        IReplStageState<?> iReplStageStateCreateState;
        Intrinsics.checkNotNullParameter(lock, "lock");
        ReplCompiler replCompiler = getReplCompiler();
        if (replCompiler == null || (iReplStageStateCreateState = replCompiler.createState(lock)) == null) {
            throw new IllegalStateException("repl compiler is not initialized properly");
        }
        return iReplStageStateCreateState;
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.ReplCheckAction
    public ReplCheckResult check(IReplStageState<?> state, ReplCodeLine codeLine) {
        ReplCheckResult.Error error;
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(codeLine, "codeLine");
        before("check");
        try {
            ReplCompiler replCompiler = getReplCompiler();
            if (replCompiler == null || (error = replCompiler.check(state, codeLine)) == null) {
                error = new ReplCheckResult.Error("Initialization error", null, 2, null);
            }
            return error;
        } finally {
            after("check");
        }
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.ReplCompileAction
    public ReplCompileResult compile(IReplStageState<?> state, ReplCodeLine codeLine) {
        ReplCompileResult.Error error;
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(codeLine, "codeLine");
        before("compile");
        try {
            ReplCompiler replCompiler = getReplCompiler();
            if (replCompiler == null || (error = replCompiler.compile(state, codeLine)) == null) {
                error = new ReplCompileResult.Error("Initialization error", null, 2, null);
            }
            return error;
        } finally {
            after("compile");
        }
    }
}
