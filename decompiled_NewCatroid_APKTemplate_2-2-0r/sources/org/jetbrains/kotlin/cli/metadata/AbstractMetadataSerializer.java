package org.jetbrains.kotlin.cli.metadata;

import androidx.exifinterface.media.ExifInterface;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys;
import org.jetbrains.kotlin.cli.common.CommonCompilerPerformanceManager;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment;
import org.jetbrains.kotlin.config.CommonConfigurationKeys;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.metadata.builtins.BuiltInsBinaryVersion;

/* JADX INFO: compiled from: AbstractMetadataSerializer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B!\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tJ\u000f\u0010\u0011\u001a\u0004\u0018\u00018\u0000H$¢\u0006\u0002\u0010\u0012J\u0006\u0010\u0013\u001a\u00020\u0014J\u001d\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00028\u00002\u0006\u0010\u0017\u001a\u00020\u0018H$¢\u0006\u0002\u0010\u0019R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/cli/metadata/AbstractMetadataSerializer;", ExifInterface.GPS_DIRECTION_TRUE, "", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "environment", "Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;", "definedMetadataVersion", "Lorg/jetbrains/kotlin/metadata/builtins/BuiltInsBinaryVersion;", "(Lorg/jetbrains/kotlin/config/CompilerConfiguration;Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;Lorg/jetbrains/kotlin/metadata/builtins/BuiltInsBinaryVersion;)V", "getConfiguration", "()Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "getEnvironment", "()Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreEnvironment;", "metadataVersion", "getMetadataVersion", "()Lorg/jetbrains/kotlin/metadata/builtins/BuiltInsBinaryVersion;", "analyze", "()Ljava/lang/Object;", "analyzeAndSerialize", "", "serialize", "analysisResult", "destDir", "Ljava/io/File;", "(Ljava/lang/Object;Ljava/io/File;)V", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractMetadataSerializer<T> {
    private final CompilerConfiguration configuration;
    private final KotlinCoreEnvironment environment;
    private final BuiltInsBinaryVersion metadataVersion;

    protected abstract T analyze();

    protected abstract void serialize(T analysisResult, File destDir);

    public AbstractMetadataSerializer(CompilerConfiguration configuration, KotlinCoreEnvironment environment, BuiltInsBinaryVersion builtInsBinaryVersion) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(environment, "environment");
        this.configuration = configuration;
        this.environment = environment;
        if (builtInsBinaryVersion == null) {
            Object obj = configuration.get(CommonConfigurationKeys.METADATA_VERSION);
            builtInsBinaryVersion = obj instanceof BuiltInsBinaryVersion ? (BuiltInsBinaryVersion) obj : null;
            if (builtInsBinaryVersion == null) {
                builtInsBinaryVersion = BuiltInsBinaryVersion.INSTANCE;
            }
        }
        this.metadataVersion = builtInsBinaryVersion;
    }

    public /* synthetic */ AbstractMetadataSerializer(CompilerConfiguration compilerConfiguration, KotlinCoreEnvironment kotlinCoreEnvironment, BuiltInsBinaryVersion builtInsBinaryVersion, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(compilerConfiguration, kotlinCoreEnvironment, (i & 4) != 0 ? null : builtInsBinaryVersion);
    }

    public final CompilerConfiguration getConfiguration() {
        return this.configuration;
    }

    public final KotlinCoreEnvironment getEnvironment() {
        return this.environment;
    }

    protected final BuiltInsBinaryVersion getMetadataVersion() {
        return this.metadataVersion;
    }

    public final void analyzeAndSerialize() {
        File destDir = CommonAnalysisKt.getDestDir(this.environment);
        if (destDir == null) {
            Object notNull = this.environment.getConfiguration().getNotNull(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY);
            Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
            MessageCollector.report$default((MessageCollector) notNull, CompilerMessageSeverity.ERROR, "Specify destination via -d", null, 4, null);
            return;
        }
        T tAnalyze = analyze();
        if (tAnalyze == null) {
            return;
        }
        Object notNull2 = this.environment.getConfiguration().getNotNull(CLIConfigurationKeys.PERF_MANAGER);
        Intrinsics.checkNotNullExpressionValue(notNull2, "getNotNull(...)");
        CommonCompilerPerformanceManager commonCompilerPerformanceManager = (CommonCompilerPerformanceManager) notNull2;
        commonCompilerPerformanceManager.notifyGenerationStarted();
        serialize(tAnalyze, destDir);
        commonCompilerPerformanceManager.notifyGenerationFinished();
    }
}
