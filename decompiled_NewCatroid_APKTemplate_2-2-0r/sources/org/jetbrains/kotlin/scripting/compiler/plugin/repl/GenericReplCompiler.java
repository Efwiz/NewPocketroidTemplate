package org.jetbrains.kotlin.scripting.compiler.plugin.repl;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.script.experimental.dependencies.ScriptDependencies;
import org.jetbrains.kotlin.backend.common.output.OutputFile;
import org.jetbrains.kotlin.cli.common.messages.AnalyzerWithCompilerReport;
import org.jetbrains.kotlin.cli.common.messages.DiagnosticMessageReporter;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.repl.CompiledClassData;
import org.jetbrains.kotlin.cli.common.repl.IReplStageHistory;
import org.jetbrains.kotlin.cli.common.repl.IReplStageState;
import org.jetbrains.kotlin.cli.common.repl.LineId;
import org.jetbrains.kotlin.cli.common.repl.ReplCheckResult;
import org.jetbrains.kotlin.cli.common.repl.ReplCodeLine;
import org.jetbrains.kotlin.cli.common.repl.ReplCompileResult;
import org.jetbrains.kotlin.cli.common.repl.ReplCompiler;
import org.jetbrains.kotlin.cli.common.repl.ReplHistoryRecord;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.cli.jvm.config.JvmClasspathRoot;
import org.jetbrains.kotlin.codegen.ClassBuilderFactories;
import org.jetbrains.kotlin.codegen.ClassBuilderFactory;
import org.jetbrains.kotlin.codegen.KotlinCodegenFacade;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.diagnostics.GenericDiagnostics;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.scripting.compiler.plugin.repl.messages.DiagnosticMessageHolder;
import org.jetbrains.kotlin.scripting.definitions.KotlinScriptDefinition;
import org.jetbrains.kotlin.scripting.definitions.ScriptDependenciesProvider;
import org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: GenericReplCompiler.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\u001f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bB%\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\u000bJ\u001c\u0010\u000e\u001a\u00020\u000f2\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u001c\u0010\u0014\u001a\u00020\u00152\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0014\u0010\u0016\u001a\u0006\u0012\u0002\b\u00030\u00112\u0006\u0010\u0017\u001a\u00020\u0018H\u0016R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/GenericReplCompiler;", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCompiler;", "scriptDefinition", "Lorg/jetbrains/kotlin/scripting/definitions/KotlinScriptDefinition;", "compilerConfiguration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "messageCollector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "(Lorg/jetbrains/kotlin/scripting/definitions/KotlinScriptDefinition;Lorg/jetbrains/kotlin/config/CompilerConfiguration;Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;)V", "disposable", "Lorg/jetbrains/kotlin/com/intellij/openapi/Disposable;", "(Lcom/intellij/openapi/Disposable;Lorg/jetbrains/kotlin/scripting/definitions/KotlinScriptDefinition;Lorg/jetbrains/kotlin/config/CompilerConfiguration;Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;)V", "checker", "Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/GenericReplChecker;", "check", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCheckResult;", "state", "Lorg/jetbrains/kotlin/cli/common/repl/IReplStageState;", "codeLine", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCodeLine;", "compile", "Lorg/jetbrains/kotlin/cli/common/repl/ReplCompileResult;", "createState", "lock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "Companion", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public class GenericReplCompiler implements ReplCompiler {
    private static final String SCRIPT_RESULT_FIELD_NAME = "$$result";
    private final GenericReplChecker checker;
    private final CompilerConfiguration compilerConfiguration;

    public GenericReplCompiler(Disposable disposable, KotlinScriptDefinition scriptDefinition, CompilerConfiguration compilerConfiguration, MessageCollector messageCollector) {
        Intrinsics.checkNotNullParameter(disposable, "disposable");
        Intrinsics.checkNotNullParameter(scriptDefinition, "scriptDefinition");
        Intrinsics.checkNotNullParameter(compilerConfiguration, "compilerConfiguration");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        this.compilerConfiguration = compilerConfiguration;
        this.checker = new GenericReplChecker(disposable, scriptDefinition, compilerConfiguration, messageCollector);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public GenericReplCompiler(KotlinScriptDefinition scriptDefinition, CompilerConfiguration compilerConfiguration, MessageCollector messageCollector) {
        Intrinsics.checkNotNullParameter(scriptDefinition, "scriptDefinition");
        Intrinsics.checkNotNullParameter(compilerConfiguration, "compilerConfiguration");
        Intrinsics.checkNotNullParameter(messageCollector, "messageCollector");
        Disposable disposableNewDisposable = Disposer.newDisposable();
        Intrinsics.checkNotNullExpressionValue(disposableNewDisposable, "newDisposable(...)");
        this(disposableNewDisposable, scriptDefinition, compilerConfiguration, messageCollector);
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.CreateReplStageStateAction
    public IReplStageState<?> createState(ReentrantReadWriteLock lock) {
        Intrinsics.checkNotNullParameter(lock, "lock");
        return new GenericReplCompilerState(this.checker.getEnvironment(), lock);
    }

    @Override // org.jetbrains.kotlin.cli.common.repl.ReplCheckAction
    public ReplCheckResult check(IReplStageState<?> state, ReplCodeLine codeLine) {
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(codeLine, "codeLine");
        return this.checker.check(state, codeLine);
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0057 A[Catch: all -> 0x0342, TryCatch #0 {all -> 0x0342, blocks: (B:10:0x0035, B:12:0x0046, B:29:0x009c, B:31:0x00d5, B:33:0x00db, B:35:0x00e1, B:37:0x00ed, B:39:0x00f2, B:40:0x010d, B:42:0x0113, B:43:0x0122, B:45:0x012a, B:47:0x0144, B:49:0x014f, B:52:0x0155, B:53:0x01ab, B:55:0x01b1, B:56:0x01c1, B:57:0x0229, B:59:0x022f, B:60:0x0246, B:61:0x026c, B:63:0x0272, B:64:0x0280, B:69:0x02a3, B:70:0x02a7, B:72:0x02b3, B:74:0x02be, B:79:0x02d9, B:80:0x0303, B:81:0x0304, B:83:0x0308, B:88:0x0321, B:89:0x0341, B:14:0x0057, B:16:0x0061, B:21:0x0076, B:23:0x007a, B:28:0x009a), top: B:95:0x0035 }] */
    /* JADX WARN: Code duplicated, block: B:16:0x0061 A[Catch: all -> 0x0342, TRY_LEAVE, TryCatch #0 {all -> 0x0342, blocks: (B:10:0x0035, B:12:0x0046, B:29:0x009c, B:31:0x00d5, B:33:0x00db, B:35:0x00e1, B:37:0x00ed, B:39:0x00f2, B:40:0x010d, B:42:0x0113, B:43:0x0122, B:45:0x012a, B:47:0x0144, B:49:0x014f, B:52:0x0155, B:53:0x01ab, B:55:0x01b1, B:56:0x01c1, B:57:0x0229, B:59:0x022f, B:60:0x0246, B:61:0x026c, B:63:0x0272, B:64:0x0280, B:69:0x02a3, B:70:0x02a7, B:72:0x02b3, B:74:0x02be, B:79:0x02d9, B:80:0x0303, B:81:0x0304, B:83:0x0308, B:88:0x0321, B:89:0x0341, B:14:0x0057, B:16:0x0061, B:21:0x0076, B:23:0x007a, B:28:0x009a), top: B:95:0x0035 }] */
    /* JADX WARN: Code duplicated, block: B:18:0x006c A[LOOP:7: B:17:0x006a->B:18:0x006c, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:21:0x0076 A[Catch: all -> 0x0342, TRY_ENTER, TryCatch #0 {all -> 0x0342, blocks: (B:10:0x0035, B:12:0x0046, B:29:0x009c, B:31:0x00d5, B:33:0x00db, B:35:0x00e1, B:37:0x00ed, B:39:0x00f2, B:40:0x010d, B:42:0x0113, B:43:0x0122, B:45:0x012a, B:47:0x0144, B:49:0x014f, B:52:0x0155, B:53:0x01ab, B:55:0x01b1, B:56:0x01c1, B:57:0x0229, B:59:0x022f, B:60:0x0246, B:61:0x026c, B:63:0x0272, B:64:0x0280, B:69:0x02a3, B:70:0x02a7, B:72:0x02b3, B:74:0x02be, B:79:0x02d9, B:80:0x0303, B:81:0x0304, B:83:0x0308, B:88:0x0321, B:89:0x0341, B:14:0x0057, B:16:0x0061, B:21:0x0076, B:23:0x007a, B:28:0x009a), top: B:95:0x0035 }] */
    /* JADX WARN: Code duplicated, block: B:23:0x007a A[Catch: all -> 0x0342, TRY_LEAVE, TryCatch #0 {all -> 0x0342, blocks: (B:10:0x0035, B:12:0x0046, B:29:0x009c, B:31:0x00d5, B:33:0x00db, B:35:0x00e1, B:37:0x00ed, B:39:0x00f2, B:40:0x010d, B:42:0x0113, B:43:0x0122, B:45:0x012a, B:47:0x0144, B:49:0x014f, B:52:0x0155, B:53:0x01ab, B:55:0x01b1, B:56:0x01c1, B:57:0x0229, B:59:0x022f, B:60:0x0246, B:61:0x026c, B:63:0x0272, B:64:0x0280, B:69:0x02a3, B:70:0x02a7, B:72:0x02b3, B:74:0x02be, B:79:0x02d9, B:80:0x0303, B:81:0x0304, B:83:0x0308, B:88:0x0321, B:89:0x0341, B:14:0x0057, B:16:0x0061, B:21:0x0076, B:23:0x007a, B:28:0x009a), top: B:95:0x0035 }] */
    /* JADX WARN: Code duplicated, block: B:25:0x0090 A[LOOP:8: B:24:0x008e->B:25:0x0090, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:28:0x009a A[Catch: all -> 0x0342, TRY_ENTER, TryCatch #0 {all -> 0x0342, blocks: (B:10:0x0035, B:12:0x0046, B:29:0x009c, B:31:0x00d5, B:33:0x00db, B:35:0x00e1, B:37:0x00ed, B:39:0x00f2, B:40:0x010d, B:42:0x0113, B:43:0x0122, B:45:0x012a, B:47:0x0144, B:49:0x014f, B:52:0x0155, B:53:0x01ab, B:55:0x01b1, B:56:0x01c1, B:57:0x0229, B:59:0x022f, B:60:0x0246, B:61:0x026c, B:63:0x0272, B:64:0x0280, B:69:0x02a3, B:70:0x02a7, B:72:0x02b3, B:74:0x02be, B:79:0x02d9, B:80:0x0303, B:81:0x0304, B:83:0x0308, B:88:0x0321, B:89:0x0341, B:14:0x0057, B:16:0x0061, B:21:0x0076, B:23:0x007a, B:28:0x009a), top: B:95:0x0035 }] */
    /* JADX WARN: Code duplicated, block: B:44:0x0129  */
    @Override // org.jetbrains.kotlin.cli.common.repl.ReplCompileAction
    public ReplCompileResult compile(IReplStageState<?> state, ReplCodeLine codeLine) {
        ReplCheckResult replCheckResultCheck;
        List<File> listEmptyList;
        ScriptCompilationConfigurationWrapper scriptConfiguration;
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
            GenericReplCompilerState genericReplCompilerState = (GenericReplCompilerState) state.asState(GenericReplCompilerState.class);
            GenericReplCompiler genericReplCompiler = this;
            if (genericReplCompilerState.getLastLineState() != null) {
                GenericReplCheckerState.LineState lastLineState = genericReplCompilerState.getLastLineState();
                Intrinsics.checkNotNull(lastLineState);
                if (!Intrinsics.areEqual(lastLineState.getCodeLine(), codeLine)) {
                    replCheckResultCheck = this.checker.check(state, codeLine);
                    if (replCheckResultCheck instanceof ReplCheckResult.Incomplete) {
                        ReplCompileResult.Incomplete incomplete = new ReplCompileResult.Incomplete("Code is incomplete");
                        while (i < readHoldCount) {
                            lock2.lock();
                            i++;
                        }
                        writeLock.unlock();
                        return incomplete;
                    }
                    if (replCheckResultCheck instanceof ReplCheckResult.Error) {
                        ReplCompileResult.Error error = new ReplCompileResult.Error(((ReplCheckResult.Error) replCheckResultCheck).getMessage(), ((ReplCheckResult.Error) replCheckResultCheck).getLocation());
                        while (i < readHoldCount) {
                            lock2.lock();
                            i++;
                        }
                        writeLock.unlock();
                        return error;
                    }
                    boolean z = replCheckResultCheck instanceof ReplCheckResult.Ok;
                }
            } else {
                replCheckResultCheck = this.checker.check(state, codeLine);
                if (replCheckResultCheck instanceof ReplCheckResult.Incomplete) {
                    ReplCompileResult.Incomplete incomplete2 = new ReplCompileResult.Incomplete("Code is incomplete");
                    while (i < readHoldCount) {
                        lock2.lock();
                        i++;
                    }
                    writeLock.unlock();
                    return incomplete2;
                }
                if (replCheckResultCheck instanceof ReplCheckResult.Error) {
                    ReplCompileResult.Error error2 = new ReplCompileResult.Error(((ReplCheckResult.Error) replCheckResultCheck).getMessage(), ((ReplCheckResult.Error) replCheckResultCheck).getLocation());
                    while (i < readHoldCount) {
                        lock2.lock();
                        i++;
                    }
                    writeLock.unlock();
                    return error2;
                }
                boolean z2 = replCheckResultCheck instanceof ReplCheckResult.Ok;
            }
            GenericReplCheckerState.LineState lastLineState2 = genericReplCompilerState.getLastLineState();
            Intrinsics.checkNotNull(lastLineState2);
            KtFile psiFile = lastLineState2.getPsiFile();
            GenericReplCheckerState.LineState lastLineState3 = genericReplCompilerState.getLastLineState();
            Intrinsics.checkNotNull(lastLineState3);
            Pair pair = new Pair(psiFile, lastLineState3.getErrorHolder());
            KtFile ktFile = (KtFile) pair.component1();
            DiagnosticMessageHolder diagnosticMessageHolder = (DiagnosticMessageHolder) pair.component2();
            ScriptDependenciesProvider companion = ScriptDependenciesProvider.INSTANCE.getInstance(this.checker.getEnvironment().getProject());
            ScriptDependencies legacyDependencies = (companion == null || (scriptConfiguration = companion.getScriptConfiguration(ktFile)) == null) ? null : scriptConfiguration.getLegacyDependencies();
            if (Intrinsics.areEqual(genericReplCompilerState.getLastDependencies(), legacyDependencies)) {
                listEmptyList = null;
            } else {
                genericReplCompilerState.setLastDependencies(legacyDependencies);
                if (legacyDependencies != null) {
                    KotlinCoreEnvironment environment = this.checker.getEnvironment();
                    List<File> classpath = legacyDependencies.getClasspath();
                    ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(classpath, 10));
                    Iterator<T> it = classpath.iterator();
                    while (it.hasNext()) {
                        arrayList.add(new JvmClasspathRoot((File) it.next()));
                    }
                    listEmptyList = environment.updateClasspath(arrayList);
                } else {
                    listEmptyList = null;
                }
            }
            ReplCodeAnalyzerBase.ReplLineAnalysisResult replLineAnalysisResultAnalyzeReplLine = genericReplCompilerState.getAnalyzerEngine().analyzeReplLine(ktFile, codeLine);
            AnalyzerWithCompilerReport.INSTANCE.reportDiagnostics((GenericDiagnostics<?>) replLineAnalysisResultAnalyzeReplLine.getDiagnostics(), (DiagnosticMessageReporter) diagnosticMessageHolder, false);
            if (replLineAnalysisResultAnalyzeReplLine instanceof ReplCodeAnalyzerBase.ReplLineAnalysisResult.Successful) {
                ClassDescriptorWithResolutionScopes scriptDescriptor = ((ReplCodeAnalyzerBase.ReplLineAnalysisResult.Successful) replLineAnalysisResultAnalyzeReplLine).getScriptDescriptor();
                ScriptDescriptor scriptDescriptor2 = scriptDescriptor instanceof ScriptDescriptor ? (ScriptDescriptor) scriptDescriptor : null;
                if (scriptDescriptor2 == null) {
                    throw new IllegalStateException(("Unexpected script descriptor type " + Reflection.getOrCreateKotlinClass(((ReplCodeAnalyzerBase.ReplLineAnalysisResult.Successful) replLineAnalysisResultAnalyzeReplLine).getScriptDescriptor().getClass())).toString());
                }
                Project project = ktFile.getProject();
                Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
                ClassBuilderFactory BINARIES = ClassBuilderFactories.BINARIES;
                Intrinsics.checkNotNullExpressionValue(BINARIES, "BINARIES");
                ModuleDescriptorImpl module = genericReplCompilerState.getAnalyzerEngine().getModule();
                BindingContext context = genericReplCompilerState.getAnalyzerEngine().getTrace().getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
                GenerationState generationStateBuild = new GenerationState.Builder(project, BINARIES, module, context, this.compilerConfiguration).build();
                GenerationState.ForScript scriptSpecific = generationStateBuild.getScriptSpecific();
                IReplStageHistory<ScriptDescriptor> history = genericReplCompilerState.getHistory();
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(history, 10));
                Iterator<ScriptDescriptor> it2 = history.iterator();
                while (it2.hasNext()) {
                    arrayList2.add((ScriptDescriptor) ((ReplHistoryRecord) it2.next()).getItem());
                }
                scriptSpecific.setEarlierScriptsForReplInterpreter(arrayList2);
                generationStateBuild.beforeCompile();
                generationStateBuild.oldBEInitTrace(CollectionsKt.listOf(ktFile));
                KtScript script = ktFile.getScript();
                Intrinsics.checkNotNull(script);
                FqName packageFqName = script.getContainingKtFile().getPackageFqName();
                KtScript script2 = ktFile.getScript();
                Intrinsics.checkNotNull(script2);
                KotlinCodegenFacade.generatePackage(generationStateBuild, packageFqName, SetsKt.setOf(script2.getContainingKtFile()));
                genericReplCompilerState.getHistory().push(new LineId(codeLine.getNo(), 0, codeLine.hashCode()), scriptDescriptor2);
                List<OutputFile> listAsList = generationStateBuild.getFactory().asList();
                Intrinsics.checkNotNullExpressionValue(listAsList, "asList(...)");
                List<OutputFile> list = listAsList;
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                for (OutputFile outputFile : list) {
                    arrayList3.add(new CompiledClassData(outputFile.getRelativePath(), outputFile.getContent()));
                }
                ArrayList arrayList4 = arrayList3;
                LineId lineId = new LineId(codeLine.getNo(), 0, codeLine.hashCode());
                IReplStageHistory<ScriptDescriptor> history2 = genericReplCompilerState.getHistory();
                ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(history2, 10));
                Iterator<ScriptDescriptor> it3 = history2.iterator();
                while (it3.hasNext()) {
                    arrayList5.add(((ReplHistoryRecord) it3.next()).getId());
                }
                ArrayList arrayList6 = arrayList5;
                String identifier = scriptDescriptor2.getShortName().getIdentifier();
                Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
                boolean z3 = generationStateBuild.getScriptSpecific().getResultFieldName() != null;
                if (listEmptyList == null) {
                    listEmptyList = CollectionsKt.emptyList();
                }
                List<File> list2 = listEmptyList;
                KotlinType resultType = generationStateBuild.getScriptSpecific().getResultType();
                ReplCompileResult.CompiledClasses compiledClasses = new ReplCompileResult.CompiledClasses(lineId, arrayList6, identifier, arrayList4, z3, list2, resultType != null ? DescriptorRenderer.FQ_NAMES_IN_TYPES.renderType(resultType) : null, null);
                while (i < readHoldCount) {
                    lock2.lock();
                    i++;
                }
                writeLock.unlock();
                return compiledClasses;
            }
            if (replLineAnalysisResultAnalyzeReplLine instanceof ReplCodeAnalyzerBase.ReplLineAnalysisResult.WithErrors) {
                ReplCompileResult.Error error3 = new ReplCompileResult.Error(diagnosticMessageHolder.renderMessage(), null, 2, null);
                while (i < readHoldCount) {
                    lock2.lock();
                    i++;
                }
                writeLock.unlock();
                return error3;
            }
            throw new IllegalStateException(("Unexpected result " + replLineAnalysisResultAnalyzeReplLine.getClass()).toString());
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
