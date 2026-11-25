package org.jetbrains.kotlin.cli.metadata;

import java.io.File;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analyzer.common.CommonDependenciesContainer;
import org.jetbrains.kotlin.backend.common.serialization.metadata.KlibMetadataMonolithicSerializer;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;

/* JADX INFO: compiled from: K2MetadataKlibSerializer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\n\u0010\b\u001a\u0004\u0018\u00010\u0002H\u0014J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\rH\u0014¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/cli/metadata/K2MetadataKlibSerializer;", "Lorg/jetbrains/kotlin/cli/metadata/AbstractMetadataSerializer;", "Lorg/jetbrains/kotlin/cli/metadata/CommonAnalysisResult;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "environment", "Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;", "(Lorg/jetbrains/kotlin/config/CompilerConfiguration;Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;)V", "analyze", "serialize", "", "analysisResult", "destDir", "Ljava/io/File;", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class K2MetadataKlibSerializer extends AbstractMetadataSerializer<CommonAnalysisResult> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public K2MetadataKlibSerializer(CompilerConfiguration configuration, KotlinCoreEnvironment environment) {
        super(configuration, environment, null, 4, null);
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(environment, "environment");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.cli.metadata.AbstractMetadataSerializer
    public CommonAnalysisResult analyze() {
        return CommonAnalysisKt.runCommonAnalysisForSerialization(getEnvironment(), true, new Function0<CommonDependenciesContainer>() { // from class: org.jetbrains.kotlin.cli.metadata.K2MetadataKlibSerializer.analyze.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final CommonDependenciesContainer invoke() {
                return new KlibMetadataDependencyContainer(K2MetadataKlibSerializer.this.getConfiguration(), new LockBasedStorageManager("K2MetadataKlibSerializer"));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.cli.metadata.AbstractMetadataSerializer
    public void serialize(CommonAnalysisResult analysisResult, File destDir) throws IOException {
        Intrinsics.checkNotNullParameter(analysisResult, "analysisResult");
        Intrinsics.checkNotNullParameter(destDir, "destDir");
        Project project = getEnvironment().getProject();
        MetadataUtilsKt.buildKotlinMetadataLibrary(getConfiguration(), new KlibMetadataMonolithicSerializer(CommonConfigurationKeysKt.getLanguageVersionSettings(getConfiguration()), getMetadataVersion(), project, false, false, true, false, 64, null).serializeModule(analysisResult.getModuleDescriptor()), destDir);
    }
}
