package kotlin.script.experimental.jvm.impl;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.dependencies.ScriptContents;
import kotlin.script.experimental.api.ErrorHandlingKt;
import kotlin.script.experimental.api.ExternalSourceCode;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptCollectedData;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptCompilationKt;
import kotlin.script.experimental.api.ScriptConfigurationRefinementContext;
import kotlin.script.experimental.api.ScriptDependency;
import kotlin.script.experimental.api.ScriptDiagnostic;
import kotlin.script.experimental.api.ScriptIdeConfigurationKt;
import kotlin.script.experimental.api.SourceCode;
import kotlin.script.experimental.dependencies.ScriptDependencies;
import kotlin.script.experimental.dependencies.ScriptReport;
import kotlin.script.experimental.host.FileScriptSource;
import kotlin.script.experimental.host.ScriptHostUtilKt;
import kotlin.script.experimental.jvm.JvmDependency;
import kotlin.script.experimental.jvm.compat.DiagnosticsUtilKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: BridgeDependenciesResolver.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000L\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0001H\u0000\u001aB\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005*\u00020\u00062 \u0010\u0007\u001a\u001c\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u0005\u0018\u00010\bj\u0004\u0018\u0001`\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e\u001a\u0018\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u0001*\n\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u0001\u001a\u0018\u0010\u0012\u001a\u00020\u0013*\u00020\u00062\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00100\u0001\u001a\u001a\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00100\u0001*\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\u0001H\u0000\u001a\f\u0010\u0016\u001a\u00020\u000e*\u00020\u0017H\u0000¨\u0006\u0018"}, d2 = {"mapScriptReportsToDiagnostics", "", "Lkotlin/script/experimental/dependencies/ScriptReport;", "Lkotlin/script/experimental/api/ScriptDiagnostic;", "refineWith", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "handler", "Lkotlin/Function1;", "Lkotlin/script/experimental/api/ScriptConfigurationRefinementContext;", "Lkotlin/script/experimental/api/RefineScriptCompilationConfigurationHandler;", "processedScriptData", "Lkotlin/script/experimental/api/ScriptCollectedData;", "script", "Lkotlin/script/experimental/api/SourceCode;", "toClassPathOrEmpty", "Ljava/io/File;", "Lkotlin/script/experimental/api/ScriptDependency;", "toDependencies", "Lkotlin/script/experimental/dependencies/ScriptDependencies;", ModuleXmlParser.CLASSPATH, "toFilesOrEmpty", "toScriptSource", "Lkotlin/script/dependencies/ScriptContents;", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class BridgeDependenciesResolverKt {
    public static final ScriptDependencies toDependencies(ScriptCompilationConfiguration scriptCompilationConfiguration, List<? extends File> classpath) {
        List listEmptyList;
        Intrinsics.checkNotNullParameter(scriptCompilationConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(classpath, "classpath");
        List list = (List) scriptCompilationConfiguration.get(ScriptCompilationKt.getDefaultImports(ScriptCompilationConfiguration.INSTANCE));
        if (list == null || (listEmptyList = CollectionsKt.toList(list)) == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        return new ScriptDependencies(null, classpath, listEmptyList, toClassPathOrEmpty((List) scriptCompilationConfiguration.get(ScriptIdeConfigurationKt.getDependenciesSources(ScriptIdeConfigurationKt.getIde(ScriptCompilationConfiguration.INSTANCE)))), toFilesOrEmpty((List) scriptCompilationConfiguration.get(ScriptCompilationKt.getImportScripts(ScriptCompilationConfiguration.INSTANCE))), 1, null);
    }

    public static final List<ScriptReport> mapScriptReportsToDiagnostics(List<ScriptDiagnostic> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        List<ScriptDiagnostic> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        for (ScriptDiagnostic scriptDiagnostic : list2) {
            arrayList.add(new ScriptReport(scriptDiagnostic.getMessage(), DiagnosticsUtilKt.mapToLegacyScriptReportSeverity(scriptDiagnostic.getSeverity()), DiagnosticsUtilKt.mapToLegacyScriptReportPosition(scriptDiagnostic.getLocation())));
        }
        return arrayList;
    }

    public static final SourceCode toScriptSource(ScriptContents scriptContents) {
        Intrinsics.checkNotNullParameter(scriptContents, "<this>");
        if (scriptContents.getFile() != null) {
            File file = scriptContents.getFile();
            Intrinsics.checkNotNull(file);
            CharSequence text = scriptContents.getText();
            return new FileScriptSource(file, text != null ? text.toString() : null);
        }
        if (scriptContents.getText() == null) {
            throw new IllegalArgumentException("Unable to convert script contents " + scriptContents + " into script source");
        }
        CharSequence text2 = scriptContents.getText();
        Intrinsics.checkNotNull(text2);
        return ScriptHostUtilKt.toScriptSource$default(text2.toString(), null, 1, null);
    }

    public static final List<File> toClassPathOrEmpty(List<? extends ScriptDependency> list) {
        List<File> listEmptyList;
        if (list == null) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (ScriptDependency scriptDependency : list) {
            JvmDependency jvmDependency = scriptDependency instanceof JvmDependency ? (JvmDependency) scriptDependency : null;
            if (jvmDependency == null || (listEmptyList = jvmDependency.getClasspath()) == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            CollectionsKt.addAll(arrayList, listEmptyList);
        }
        return arrayList;
    }

    public static final List<File> toFilesOrEmpty(List<? extends SourceCode> list) {
        URL externalLocation;
        File fileOrNull;
        if (list != null) {
            List<? extends SourceCode> list2 = list;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            for (SourceCode sourceCode : list2) {
                ExternalSourceCode externalSourceCode = sourceCode instanceof ExternalSourceCode ? (ExternalSourceCode) sourceCode : null;
                if (externalSourceCode == null || (externalLocation = externalSourceCode.getExternalLocation()) == null || (fileOrNull = PathUtilKt.toFileOrNull(externalLocation)) == null) {
                    throw new RuntimeException("Unsupported source in requireSources parameter - only local files are supported now (" + (externalSourceCode != null ? externalSourceCode.getExternalLocation() : null) + ')');
                }
                arrayList.add(fileOrNull);
            }
            return arrayList;
        }
        return CollectionsKt.emptyList();
    }

    public static final ResultWithDiagnostics<ScriptCompilationConfiguration> refineWith(ScriptCompilationConfiguration scriptCompilationConfiguration, Function1<? super ScriptConfigurationRefinementContext, ? extends ResultWithDiagnostics<? extends ScriptCompilationConfiguration>> function1, ScriptCollectedData processedScriptData, SourceCode script) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(processedScriptData, "processedScriptData");
        Intrinsics.checkNotNullParameter(script, "script");
        if (function1 == null) {
            return ErrorHandlingKt.asSuccess$default(scriptCompilationConfiguration, null, 1, null);
        }
        return (ResultWithDiagnostics) function1.invoke(new ScriptConfigurationRefinementContext(script, scriptCompilationConfiguration, processedScriptData));
    }
}
