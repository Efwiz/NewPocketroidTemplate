package org.jetbrains.kotlin.ir.backend.js;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analyzer.AbstractAnalyzerWithCompilerReport;
import org.jetbrains.kotlin.cli.js.klib.TopDownAnalyzerFacadeForJSIR;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.js.analyze.AbstractTopDownAnalyzerFacadeForWeb;
import org.jetbrains.kotlin.js.config.ErrorTolerancePolicy;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.psi.KtFile;

/* JADX INFO: compiled from: prepareAnalyzedSourceModule.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\\\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00052\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\u00052\u0006\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u0011¨\u0006\u0012"}, d2 = {"prepareAnalyzedSourceModule", "Lorg/jetbrains/kotlin/ir/backend/js/ModulesStructure;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "files", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "dependencies", "", "friendDependencies", "analyzer", "Lorg/jetbrains/kotlin/analyzer/AbstractAnalyzerWithCompilerReport;", "errorPolicy", "Lorg/jetbrains/kotlin/js/config/ErrorTolerancePolicy;", "analyzerFacade", "Lorg/jetbrains/kotlin/js/analyze/AbstractTopDownAnalyzerFacadeForWeb;", "cli-js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PrepareAnalyzedSourceModuleKt {
    public static /* synthetic */ ModulesStructure prepareAnalyzedSourceModule$default(Project project, List list, CompilerConfiguration compilerConfiguration, List list2, List list3, AbstractAnalyzerWithCompilerReport abstractAnalyzerWithCompilerReport, ErrorTolerancePolicy errorTolerancePolicy, AbstractTopDownAnalyzerFacadeForWeb abstractTopDownAnalyzerFacadeForWeb, int i, Object obj) {
        ErrorTolerancePolicy errorTolerancePolicy2;
        if ((i & 64) != 0) {
            ErrorTolerancePolicy errorTolerancePolicy3 = (ErrorTolerancePolicy) compilerConfiguration.get(JSConfigurationKeys.ERROR_TOLERANCE_POLICY);
            if (errorTolerancePolicy3 == null) {
                errorTolerancePolicy3 = ErrorTolerancePolicy.INSTANCE.getDEFAULT();
            }
            errorTolerancePolicy2 = errorTolerancePolicy3;
        } else {
            errorTolerancePolicy2 = errorTolerancePolicy;
        }
        return prepareAnalyzedSourceModule(project, list, compilerConfiguration, list2, list3, abstractAnalyzerWithCompilerReport, errorTolerancePolicy2, (i & 128) != 0 ? TopDownAnalyzerFacadeForJSIR.INSTANCE : abstractTopDownAnalyzerFacadeForWeb);
    }

    public static final ModulesStructure prepareAnalyzedSourceModule(Project project, List<? extends KtFile> files, CompilerConfiguration configuration, List<String> dependencies, List<String> friendDependencies, AbstractAnalyzerWithCompilerReport analyzer, ErrorTolerancePolicy errorPolicy, AbstractTopDownAnalyzerFacadeForWeb analyzerFacade) {
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(dependencies, "dependencies");
        Intrinsics.checkNotNullParameter(friendDependencies, "friendDependencies");
        Intrinsics.checkNotNullParameter(analyzer, "analyzer");
        Intrinsics.checkNotNullParameter(errorPolicy, "errorPolicy");
        Intrinsics.checkNotNullParameter(analyzerFacade, "analyzerFacade");
        ModulesStructure modulesStructure = new ModulesStructure(project, new MainModule.SourceFiles(files), configuration, dependencies, friendDependencies);
        modulesStructure.runAnalysis(errorPolicy, analyzer, analyzerFacade);
        return modulesStructure;
    }
}
