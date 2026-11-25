package org.jetbrains.kotlin.scripting.compiler.plugin.definitions;

import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.scripting.definitions.DefinitionsKt;
import org.jetbrains.kotlin.scripting.definitions.ScriptDefinition;
import org.jetbrains.kotlin.scripting.definitions.ScriptDependenciesProvider;
import org.jetbrains.kotlin.scripting.resolve.KtFileScriptSource;
import org.jetbrains.kotlin.scripting.resolve.RefineCompilationConfigurationKt;
import org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper;
import org.jetbrains.kotlin.scripting.resolve.ScriptReportSink;
import org.jetbrains.kotlin.scripting.resolve.VirtualFileScriptSource;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: CliScriptDependenciesProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J(\u0010\u0011\u001a\u0010\u0012\u0004\u0012\u00020\t\u0018\u00010\bj\u0004\u0018\u0001`\n2\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0002J\u001e\u0010\u0016\u001a\u0010\u0012\u0004\u0012\u00020\t\u0018\u00010\bj\u0004\u0018\u0001`\n2\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J(\u0010\u0016\u001a\u0010\u0012\u0004\u0012\u00020\t\u0018\u00010\bj\u0004\u0018\u0001`\n2\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016RF\u0010\u0005\u001a:\u0012\u0004\u0012\u00020\u0007\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\t\u0018\u00010\bj\u0004\u0018\u0001`\n0\u0006j\u001c\u0012\u0004\u0012\u00020\u0007\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\t\u0018\u00010\bj\u0004\u0018\u0001`\n`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/definitions/CliScriptDependenciesProvider;", "Lorg/jetbrains/kotlin/scripting/definitions/ScriptDependenciesProvider;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "(Lcom/intellij/openapi/project/Project;)V", ResponseCacheMiddleware.CACHE, "Ljava/util/HashMap;", "", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "Lorg/jetbrains/kotlin/scripting/resolve/ScriptCompilationConfigurationWrapper;", "Lorg/jetbrains/kotlin/scripting/resolve/ScriptCompilationConfigurationResult;", "Lkotlin/collections/HashMap;", "cacheLock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "knownVirtualFileSources", "", "Lorg/jetbrains/kotlin/scripting/resolve/VirtualFileScriptSource;", "calculateRefinedConfiguration", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "providedConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "getScriptConfigurationResult", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CliScriptDependenciesProvider extends ScriptDependenciesProvider {
    private final HashMap<String, ResultWithDiagnostics<ScriptCompilationConfigurationWrapper>> cache;
    private final ReentrantReadWriteLock cacheLock;
    private final Map<String, VirtualFileScriptSource> knownVirtualFileSources;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CliScriptDependenciesProvider(Project project) {
        super(project);
        Intrinsics.checkNotNullParameter(project, "project");
        this.cacheLock = new ReentrantReadWriteLock();
        this.cache = new HashMap<>();
        this.knownVirtualFileSources = new LinkedHashMap();
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDependenciesProvider
    public ResultWithDiagnostics<ScriptCompilationConfigurationWrapper> getScriptConfigurationResult(KtFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        ReentrantReadWriteLock.ReadLock lock = this.cacheLock.readLock();
        lock.lock();
        try {
            return calculateRefinedConfiguration(file, null);
        } finally {
            lock.unlock();
        }
    }

    @Override // org.jetbrains.kotlin.scripting.definitions.ScriptDependenciesProvider
    public ResultWithDiagnostics<ScriptCompilationConfigurationWrapper> getScriptConfigurationResult(KtFile file, ScriptCompilationConfiguration providedConfiguration) {
        Intrinsics.checkNotNullParameter(file, "file");
        ReentrantReadWriteLock.ReadLock lock = this.cacheLock.readLock();
        lock.lock();
        try {
            return calculateRefinedConfiguration(file, providedConfiguration);
        } finally {
            lock.unlock();
        }
    }

    private final ResultWithDiagnostics<ScriptCompilationConfigurationWrapper> calculateRefinedConfiguration(KtFile file, ScriptCompilationConfiguration providedConfiguration) {
        String virtualFilePath = file.getVirtualFilePath();
        ResultWithDiagnostics<ScriptCompilationConfigurationWrapper> resultWithDiagnostics = this.cache.get(virtualFilePath);
        if (resultWithDiagnostics != null) {
            return resultWithDiagnostics;
        }
        ScriptDefinition scriptDefinitionFindScriptDefinition = DefinitionsKt.findScriptDefinition(file);
        if (scriptDefinitionFindScriptDefinition == null) {
            return null;
        }
        ResultWithDiagnostics<ScriptCompilationConfigurationWrapper> resultWithDiagnosticsRefineScriptCompilationConfiguration = RefineCompilationConfigurationKt.refineScriptCompilationConfiguration(new KtFileScriptSource(file, null, 2, null), scriptDefinitionFindScriptDefinition, getProject(), providedConfiguration, this.knownVirtualFileSources);
        ScriptReportSink scriptReportSink = (ScriptReportSink) getProject().getService(ScriptReportSink.class);
        if (scriptReportSink != null) {
            VirtualFile virtualFile = file.getVirtualFile();
            Intrinsics.checkNotNullExpressionValue(virtualFile, "getVirtualFile(...)");
            scriptReportSink.attachReports(virtualFile, resultWithDiagnosticsRefineScriptCompilationConfiguration.getReports());
        }
        ReentrantReadWriteLock reentrantReadWriteLock = this.cacheLock;
        ReentrantReadWriteLock.ReadLock lock = reentrantReadWriteLock.readLock();
        int i = 0;
        int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
        for (int i2 = 0; i2 < readHoldCount; i2++) {
            lock.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        writeLock.lock();
        try {
            this.cache.put(virtualFilePath, resultWithDiagnosticsRefineScriptCompilationConfiguration);
            while (i < readHoldCount) {
                lock.lock();
                i++;
            }
            return resultWithDiagnosticsRefineScriptCompilationConfiguration;
        } finally {
            while (i < readHoldCount) {
                lock.lock();
                i++;
            }
            writeLock.unlock();
        }
    }
}
