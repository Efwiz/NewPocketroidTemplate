package org.jetbrains.kotlin.scripting.compiler.plugin.repl;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.host.HostConfigurationKt;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import kotlin.script.experimental.jvm.JvmDependency;
import kotlin.script.experimental.jvm.JvmScriptingHostConfigurationKt;
import kotlin.script.experimental.util.PropertiesCollection;
import org.apache.commons.lang3.SystemProperties;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.messages.AnalyzerWithCompilerReport;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.repl.IReplStageState;
import org.jetbrains.kotlin.cli.common.repl.ReplCheckAction;
import org.jetbrains.kotlin.cli.common.repl.ReplCheckResult;
import org.jetbrains.kotlin.cli.common.repl.ReplCodeLine;
import org.jetbrains.kotlin.cli.common.repl.ReplUtilKt;
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.cli.jvm.config.JvmContentRootsKt;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiFileFactory;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiFileFactoryImpl;
import org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFile;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.CompilerConfigurationKey;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.config.JvmTarget;
import org.jetbrains.kotlin.idea.KotlinLanguage;
import org.jetbrains.kotlin.parsing.KotlinParserDefinition;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.messages.ConsoleDiagnosticMessageHolder;
import org.jetbrains.kotlin.scripting.configuration.ScriptingConfigurationKeys;
import org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinition;
import org.jetbrains.kotlin.scripting.definitions.ScriptDefinition;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: GenericReplChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u001c\u0010\u0011\u001a\u00020\u00122\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\fX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/GenericReplChecker;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCheckAction;", "disposable", "Lorg/jetbrains/kotlin/com/intellij/openapi/Disposable;", "scriptDefinition", "Lorg/jetbrains/kotlin/scripting/definitions/KotlinScriptDefinition;", "compilerConfiguration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "(Lcom/intellij/openapi/Disposable;Lorg/jetbrains/kotlin/scripting/definitions/KotlinScriptDefinition;Lorg/jetbrains/kotlin/config/CompilerConfiguration;Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;)V", "environment", "Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;", "getEnvironment$kotlin_scripting_compiler", "()Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;", "psiFileFactory", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/PsiFileFactoryImpl;", "check", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCheckResult;", "state", "Lorg/jetbrains/kotlin/cli/common/repl/IReplStageState;", "codeLine", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCodeLine;", "createDiagnosticHolder", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/messages/ConsoleDiagnosticMessageHolder;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public class GenericReplChecker implements ReplCheckAction {
    private final CompilerConfiguration compilerConfiguration;
    private final KotlinCoreEnvironment environment;
    private final PsiFileFactoryImpl psiFileFactory;
    private final KotlinScriptDefinition scriptDefinition;

    /* JADX WARN: Code duplicated, block: B:10:0x007d  */
    /* JADX WARN: Code duplicated, block: B:11:0x0087  */
    /* JADX WARN: Code duplicated, block: B:13:0x008a  */
    /* JADX WARN: Code duplicated, block: B:8:0x0075  */
    public GenericReplChecker(Disposable disposable, KotlinScriptDefinition scriptDefinition, CompilerConfiguration compilerConfiguration, MessageCollector messageCollector) {
        String property;
        JvmTarget jvmTargetFromString;
        Intrinsics.checkNotNullParameter(disposable, "disposable");
        Intrinsics.checkNotNullParameter(scriptDefinition, "scriptDefinition");
        Intrinsics.checkNotNullParameter(compilerConfiguration, "compilerConfiguration");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        this.scriptDefinition = scriptDefinition;
        this.compilerConfiguration = compilerConfiguration;
        final CompilerConfiguration compilerConfiguration2 = this.compilerConfiguration;
        compilerConfiguration2.add(ScriptingConfigurationKeys.INSTANCE.getSCRIPT_DEFINITIONS(), new ScriptDefinition.FromLegacy(new ScriptingHostConfiguration(new ScriptingHostConfiguration[]{JvmScriptingHostConfigurationKt.getDefaultJvmScriptingHostConfiguration()}, new Function1<ScriptingHostConfiguration.Builder, Unit>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.repl.GenericReplChecker$environment$1$1$hostConfiguration$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ScriptingHostConfiguration.Builder builder) {
                invoke2(builder);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ScriptingHostConfiguration.Builder $receiver) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                $receiver.invoke((PropertiesCollection.Key) HostConfigurationKt.getConfigurationDependencies($receiver), (Object[]) new JvmDependency[]{new JvmDependency(JvmContentRootsKt.getJvmClasspathRoots(compilerConfiguration2))});
            }
        }), this.scriptDefinition, null, 4, null));
        compilerConfiguration2.put(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, messageCollector);
        compilerConfiguration2.put(JVMConfigurationKeys.RETAIN_OUTPUT_IN_MEMORY, true);
        if (compilerConfiguration2.get(JVMConfigurationKeys.JVM_TARGET) == null) {
            CompilerConfigurationKey<JvmTarget> compilerConfigurationKey = JVMConfigurationKeys.JVM_TARGET;
            String property2 = System.getProperty(GenericReplCheckerKt.KOTLIN_REPL_JVM_TARGET_PROPERTY);
            if (property2 != null) {
                Intrinsics.checkNotNull(property2);
                jvmTargetFromString = JvmTarget.INSTANCE.fromString(property2);
                if (jvmTargetFromString == null) {
                    property = System.getProperty(SystemProperties.JAVA_SPECIFICATION_VERSION);
                    if (property != null) {
                        Intrinsics.checkNotNull(property);
                        jvmTargetFromString = JvmTarget.INSTANCE.fromString(property);
                    } else {
                        jvmTargetFromString = null;
                    }
                    if (jvmTargetFromString == null) {
                        jvmTargetFromString = JvmTarget.DEFAULT;
                    }
                }
            } else {
                property = System.getProperty(SystemProperties.JAVA_SPECIFICATION_VERSION);
                if (property != null) {
                    Intrinsics.checkNotNull(property);
                    jvmTargetFromString = JvmTarget.INSTANCE.fromString(property);
                } else {
                    jvmTargetFromString = null;
                }
                if (jvmTargetFromString == null) {
                    jvmTargetFromString = JvmTarget.DEFAULT;
                }
            }
            compilerConfiguration2.put(compilerConfigurationKey, jvmTargetFromString);
        }
        KotlinCoreEnvironment kotlinCoreEnvironmentCreateForProduction = KotlinCoreEnvironment.INSTANCE.createForProduction(disposable, this.compilerConfiguration, EnvironmentConfigFiles.JVM_CONFIG_FILES);
        this.environment = kotlinCoreEnvironmentCreateForProduction;
        PsiFileFactory psiFileFactory = PsiFileFactory.getInstance(kotlinCoreEnvironmentCreateForProduction.getProject());
        Intrinsics.checkNotNull(psiFileFactory, "null cannot be cast to non-null type com.intellij.psi.impl.PsiFileFactoryImpl");
        this.psiFileFactory = (PsiFileFactoryImpl) psiFileFactory;
    }

    /* JADX INFO: renamed from: getEnvironment$kotlin_scripting_compiler, reason: from getter */
    public final KotlinCoreEnvironment getEnvironment() {
        return this.environment;
    }

    private final ConsoleDiagnosticMessageHolder createDiagnosticHolder() {
        return new ConsoleDiagnosticMessageHolder();
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.ReplCheckAction
    public ReplCheckResult check(IReplStageState<?> state, ReplCodeLine codeLine) {
        ReplCheckResult.Ok error;
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(codeLine, "codeLine");
        ReentrantReadWriteLock lock = state.getLock();
        ReentrantReadWriteLock.ReadLock lock2 = lock.readLock();
        int i = 0;
        int readHoldCount = lock.getWriteHoldCount() == 0 ? lock.getReadHoldCount() : 0;
        for (int i2 = 0; i2 < readHoldCount; i2++) {
            lock2.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        writeLock.lock();
        try {
            GenericReplCheckerState genericReplCheckerState = (GenericReplCheckerState) state.asState(GenericReplCheckerState.class);
            LightVirtualFile lightVirtualFile = new LightVirtualFile(ReplUtilKt.makeScriptBaseName(codeLine) + KotlinParserDefinition.STD_SCRIPT_EXT, KotlinLanguage.INSTANCE, StringUtil.convertLineSeparators(codeLine.getCode()));
            lightVirtualFile.setCharset(StandardCharsets.UTF_8);
            KtFile ktFile = (KtFile) this.psiFileFactory.trySetupPsiForFile(lightVirtualFile, KotlinLanguage.INSTANCE, true, false);
            if (ktFile == null) {
                throw new IllegalStateException(("Script file not analyzed at line " + codeLine.getNo() + ": " + codeLine.getCode()).toString());
            }
            ConsoleDiagnosticMessageHolder consoleDiagnosticMessageHolderCreateDiagnosticHolder = createDiagnosticHolder();
            AnalyzerWithCompilerReport.SyntaxErrorReport syntaxErrorReportReportSyntaxErrors = AnalyzerWithCompilerReport.INSTANCE.reportSyntaxErrors(ktFile, consoleDiagnosticMessageHolderCreateDiagnosticHolder);
            if (!syntaxErrorReportReportSyntaxErrors.getIsHasErrors()) {
                genericReplCheckerState.setLastLineState(new GenericReplCheckerState.LineState(codeLine, ktFile, consoleDiagnosticMessageHolderCreateDiagnosticHolder));
            }
            if (syntaxErrorReportReportSyntaxErrors.getIsHasErrors() && syntaxErrorReportReportSyntaxErrors.getIsAllErrorsAtEof()) {
                error = new ReplCheckResult.Incomplete();
            } else {
                error = syntaxErrorReportReportSyntaxErrors.getIsHasErrors() ? new ReplCheckResult.Error(consoleDiagnosticMessageHolderCreateDiagnosticHolder.renderMessage(), null, 2, null) : new ReplCheckResult.Ok();
            }
            while (i < readHoldCount) {
                lock2.lock();
                i++;
            }
            writeLock.unlock();
            return error;
        } catch (Throwable th) {
            while (i < readHoldCount) {
                lock2.lock();
                i++;
            }
            writeLock.unlock();
            throw th;
        }
    }
}
