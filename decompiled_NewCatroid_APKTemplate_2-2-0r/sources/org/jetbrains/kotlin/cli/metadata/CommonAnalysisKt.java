package org.jetbrains.kotlin.cli.metadata;

import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analyzer.AnalysisResult;
import org.jetbrains.kotlin.analyzer.ModuleContent;
import org.jetbrains.kotlin.analyzer.ModuleInfo;
import org.jetbrains.kotlin.analyzer.common.CommonDependenciesContainer;
import org.jetbrains.kotlin.analyzer.common.CommonResolverForModuleFactory;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.CommonCompilerPerformanceManager;
import org.jetbrains.kotlin.cli.common.messages.AnalyzerWithCompilerReport;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.platform.CommonPlatforms;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.CompilerEnvironment;
import org.jetbrains.kotlin.serialization.deserialization.MetadataPartProvider;

/* JADX INFO: compiled from: CommonAnalysis.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a*\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u000e\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000bH\u0000\u001a\"\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\b\u0010\u000f\u001a\u0004\u0018\u00010\fH\u0002\"\u001a\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0010"}, d2 = {"destDir", "Ljava/io/File;", "Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;", "getDestDir", "(Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;)Ljava/io/File;", "runCommonAnalysisForSerialization", "Lorg/jetbrains/kotlin/cli/metadata/CommonAnalysisResult;", "environment", "dependOnBuiltins", "", "dependencyContainerFactory", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/analyzer/common/CommonDependenciesContainer;", "runCommonAnalysisIteration", "Lorg/jetbrains/kotlin/cli/metadata/AnalysisResultWithHasErrors;", "dependencyContainer", "cli"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CommonAnalysisKt {
    public static final File getDestDir(KotlinCoreEnvironment kotlinCoreEnvironment) {
        Intrinsics.checkNotNullParameter(kotlinCoreEnvironment, "<this>");
        return (File) kotlinCoreEnvironment.getConfiguration().get(CLIConfigurationKeys.METADATA_DESTINATION_DIRECTORY);
    }

    public static final CommonAnalysisResult runCommonAnalysisForSerialization(KotlinCoreEnvironment environment, boolean z, Function0<? extends CommonDependenciesContainer> dependencyContainerFactory) {
        AnalysisResultWithHasErrors analysisResultWithHasErrorsRunCommonAnalysisIteration;
        boolean z2;
        Intrinsics.checkNotNullParameter(environment, "environment");
        Intrinsics.checkNotNullParameter(dependencyContainerFactory, "dependencyContainerFactory");
        Object notNull = environment.getConfiguration().getNotNull(CLIConfigurationKeys.PERF_MANAGER);
        Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
        CommonCompilerPerformanceManager commonCompilerPerformanceManager = (CommonCompilerPerformanceManager) notNull;
        do {
            commonCompilerPerformanceManager.notifyAnalysisStarted();
            analysisResultWithHasErrorsRunCommonAnalysisIteration = runCommonAnalysisIteration(environment, z, dependencyContainerFactory.invoke());
            AnalysisResult result = analysisResultWithHasErrorsRunCommonAnalysisIteration.getResult();
            z2 = result instanceof AnalysisResult.RetryWithAdditionalRoots;
            if (z2) {
                environment.addKotlinSourceRoots(((AnalysisResult.RetryWithAdditionalRoots) result).getAdditionalKotlinRoots());
            }
            commonCompilerPerformanceManager.notifyAnalysisFinished();
        } while (z2);
        AnalysisResult result2 = analysisResultWithHasErrorsRunCommonAnalysisIteration.getResult();
        if (!result2.getShouldGenerateCode() || analysisResultWithHasErrorsRunCommonAnalysisIteration.getHasErrors()) {
            return null;
        }
        return new CommonAnalysisResult(result2.getModuleDescriptor(), result2.getBindingContext());
    }

    private static final AnalysisResultWithHasErrors runCommonAnalysisIteration(final KotlinCoreEnvironment kotlinCoreEnvironment, final boolean z, final CommonDependenciesContainer commonDependenciesContainer) {
        final CompilerConfiguration configuration = kotlinCoreEnvironment.getConfiguration();
        Object notNull = configuration.getNotNull(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
        Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
        final List<KtFile> sourceFiles = kotlinCoreEnvironment.getSourceFiles();
        final Name nameSpecial = Name.special("<" + ((String) configuration.getNotNull(CommonConfigurationKeys.MODULE_NAME)) + '>');
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        AnalyzerWithCompilerReport analyzerWithCompilerReport = new AnalyzerWithCompilerReport((MessageCollector) notNull, CommonConfigurationKeysKt.getLanguageVersionSettings(configuration), configuration.getBoolean(CLIConfigurationKeys.RENDER_DIAGNOSTIC_INTERNAL_NAME));
        analyzerWithCompilerReport.analyzeAndReport(sourceFiles, new Function0<AnalysisResult>() { // from class: org.jetbrains.kotlin.cli.metadata.CommonAnalysisKt.runCommonAnalysisIteration.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final AnalysisResult invoke() {
                CommonResolverForModuleFactory.Companion companion = CommonResolverForModuleFactory.INSTANCE;
                List<KtFile> list = sourceFiles;
                Name name2 = nameSpecial;
                boolean z2 = z;
                LanguageVersionSettings languageVersionSettings = CommonConfigurationKeysKt.getLanguageVersionSettings(configuration);
                TargetPlatform defaultCommonPlatform = CommonPlatforms.INSTANCE.getDefaultCommonPlatform();
                CompilerEnvironment compilerEnvironment = CompilerEnvironment.INSTANCE;
                CommonDependenciesContainer commonDependenciesContainer2 = commonDependenciesContainer;
                final KotlinCoreEnvironment kotlinCoreEnvironment2 = kotlinCoreEnvironment;
                return companion.analyzeFiles(list, name2, z2, languageVersionSettings, defaultCommonPlatform, compilerEnvironment, (64 & 64) != 0 ? MapsKt.emptyMap() : null, (64 & 128) != 0 ? null : commonDependenciesContainer2, new Function1<ModuleContent<? extends ModuleInfo>, MetadataPartProvider>() { // from class: org.jetbrains.kotlin.cli.metadata.CommonAnalysisKt.runCommonAnalysisIteration.1.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final MetadataPartProvider invoke(ModuleContent<? extends ModuleInfo> content) {
                        Intrinsics.checkNotNullParameter(content, "content");
                        return kotlinCoreEnvironment2.createPackagePartProvider(content.getModuleContentScope());
                    }
                });
            }
        });
        return new AnalysisResultWithHasErrors(analyzerWithCompilerReport.getAnalysisResult(), analyzerWithCompilerReport.hasErrors());
    }
}
