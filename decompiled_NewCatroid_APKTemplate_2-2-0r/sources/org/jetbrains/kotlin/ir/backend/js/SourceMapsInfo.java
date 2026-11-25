package org.jetbrains.kotlin.ir.backend.js;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.js.config.SourceMapNamesPolicy;
import org.jetbrains.kotlin.js.config.SourceMapSourceEmbedding;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: SourceMapsInfo.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\b\u0018\u0000 #2\u00020\u0001:\u0001#B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005HÆ\u0003J\u000b\u0010\u0019\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\t\u0010\u001a\u001a\u00020\tHÆ\u0003J\t\u0010\u001b\u001a\u00020\u000bHÆ\u0003JC\u0010\u001c\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000bHÆ\u0001J\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010 \u001a\u00020!HÖ\u0001J\t\u0010\"\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/SourceMapsInfo;", "", "sourceMapPrefix", "", "sourceRoots", "", ModuleXmlParser.OUTPUT_DIR, "Ljava/io/File;", "sourceMapContentEmbedding", "Lorg/jetbrains/kotlin/js/config/SourceMapSourceEmbedding;", "namesPolicy", "Lorg/jetbrains/kotlin/js/config/SourceMapNamesPolicy;", "(Ljava/lang/String;Ljava/util/List;Ljava/io/File;Lorg/jetbrains/kotlin/js/config/SourceMapSourceEmbedding;Lorg/jetbrains/kotlin/js/config/SourceMapNamesPolicy;)V", "getNamesPolicy", "()Lorg/jetbrains/kotlin/js/config/SourceMapNamesPolicy;", "getOutputDir", "()Ljava/io/File;", "getSourceMapContentEmbedding", "()Lorg/jetbrains/kotlin/js/config/SourceMapSourceEmbedding;", "getSourceMapPrefix", "()Ljava/lang/String;", "getSourceRoots", "()Ljava/util/List;", "component1", "component2", "component3", "component4", "component5", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "Companion", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class SourceMapsInfo {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final SourceMapNamesPolicy namesPolicy;
    private final File outputDir;
    private final SourceMapSourceEmbedding sourceMapContentEmbedding;
    private final String sourceMapPrefix;
    private final List<String> sourceRoots;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ SourceMapsInfo copy$default(SourceMapsInfo sourceMapsInfo, String str, List list, File file, SourceMapSourceEmbedding sourceMapSourceEmbedding, SourceMapNamesPolicy sourceMapNamesPolicy, int i, Object obj) {
        if ((i & 1) != 0) {
            str = sourceMapsInfo.sourceMapPrefix;
        }
        if ((i & 2) != 0) {
            list = sourceMapsInfo.sourceRoots;
        }
        List list2 = list;
        if ((i & 4) != 0) {
            file = sourceMapsInfo.outputDir;
        }
        File file2 = file;
        if ((i & 8) != 0) {
            sourceMapSourceEmbedding = sourceMapsInfo.sourceMapContentEmbedding;
        }
        SourceMapSourceEmbedding sourceMapSourceEmbedding2 = sourceMapSourceEmbedding;
        if ((i & 16) != 0) {
            sourceMapNamesPolicy = sourceMapsInfo.namesPolicy;
        }
        return sourceMapsInfo.copy(str, list2, file2, sourceMapSourceEmbedding2, sourceMapNamesPolicy);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getSourceMapPrefix() {
        return this.sourceMapPrefix;
    }

    public final List<String> component2() {
        return this.sourceRoots;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final File getOutputDir() {
        return this.outputDir;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final SourceMapSourceEmbedding getSourceMapContentEmbedding() {
        return this.sourceMapContentEmbedding;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final SourceMapNamesPolicy getNamesPolicy() {
        return this.namesPolicy;
    }

    public final SourceMapsInfo copy(String sourceMapPrefix, List<String> sourceRoots, File outputDir, SourceMapSourceEmbedding sourceMapContentEmbedding, SourceMapNamesPolicy namesPolicy) {
        Intrinsics.checkNotNullParameter(sourceMapPrefix, "sourceMapPrefix");
        Intrinsics.checkNotNullParameter(sourceRoots, "sourceRoots");
        Intrinsics.checkNotNullParameter(sourceMapContentEmbedding, "sourceMapContentEmbedding");
        Intrinsics.checkNotNullParameter(namesPolicy, "namesPolicy");
        return new SourceMapsInfo(sourceMapPrefix, sourceRoots, outputDir, sourceMapContentEmbedding, namesPolicy);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SourceMapsInfo)) {
            return false;
        }
        SourceMapsInfo sourceMapsInfo = (SourceMapsInfo) other;
        return Intrinsics.areEqual(this.sourceMapPrefix, sourceMapsInfo.sourceMapPrefix) && Intrinsics.areEqual(this.sourceRoots, sourceMapsInfo.sourceRoots) && Intrinsics.areEqual(this.outputDir, sourceMapsInfo.outputDir) && this.sourceMapContentEmbedding == sourceMapsInfo.sourceMapContentEmbedding && this.namesPolicy == sourceMapsInfo.namesPolicy;
    }

    public int hashCode() {
        int iHashCode = ((this.sourceMapPrefix.hashCode() * 31) + this.sourceRoots.hashCode()) * 31;
        File file = this.outputDir;
        return ((((iHashCode + (file == null ? 0 : file.hashCode())) * 31) + this.sourceMapContentEmbedding.hashCode()) * 31) + this.namesPolicy.hashCode();
    }

    public String toString() {
        return "SourceMapsInfo(sourceMapPrefix=" + this.sourceMapPrefix + ", sourceRoots=" + this.sourceRoots + ", outputDir=" + this.outputDir + ", sourceMapContentEmbedding=" + this.sourceMapContentEmbedding + ", namesPolicy=" + this.namesPolicy + ')';
    }

    public SourceMapsInfo(String sourceMapPrefix, List<String> sourceRoots, File file, SourceMapSourceEmbedding sourceMapContentEmbedding, SourceMapNamesPolicy namesPolicy) {
        Intrinsics.checkNotNullParameter(sourceMapPrefix, "sourceMapPrefix");
        Intrinsics.checkNotNullParameter(sourceRoots, "sourceRoots");
        Intrinsics.checkNotNullParameter(sourceMapContentEmbedding, "sourceMapContentEmbedding");
        Intrinsics.checkNotNullParameter(namesPolicy, "namesPolicy");
        this.sourceMapPrefix = sourceMapPrefix;
        this.sourceRoots = sourceRoots;
        this.outputDir = file;
        this.sourceMapContentEmbedding = sourceMapContentEmbedding;
        this.namesPolicy = namesPolicy;
    }

    public final String getSourceMapPrefix() {
        return this.sourceMapPrefix;
    }

    public final List<String> getSourceRoots() {
        return this.sourceRoots;
    }

    public final File getOutputDir() {
        return this.outputDir;
    }

    public final SourceMapSourceEmbedding getSourceMapContentEmbedding() {
        return this.sourceMapContentEmbedding;
    }

    public final SourceMapNamesPolicy getNamesPolicy() {
        return this.namesPolicy;
    }

    /* JADX INFO: compiled from: SourceMapsInfo.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/SourceMapsInfo$Companion;", "", "()V", TypedValues.TransitionType.S_FROM, "Lorg/jetbrains/kotlin/ir/backend/js/SourceMapsInfo;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final SourceMapsInfo from(CompilerConfiguration configuration) {
            Intrinsics.checkNotNullParameter(configuration, "configuration");
            if (!configuration.getBoolean(JSConfigurationKeys.SOURCE_MAP)) {
                return null;
            }
            Object obj = configuration.get(JSConfigurationKeys.SOURCE_MAP_PREFIX, "");
            Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
            String str = (String) obj;
            Object obj2 = configuration.get(JSConfigurationKeys.SOURCE_MAP_SOURCE_ROOTS, CollectionsKt.emptyList());
            Intrinsics.checkNotNullExpressionValue(obj2, "get(...)");
            List list = (List) obj2;
            File file = (File) configuration.get(JSConfigurationKeys.OUTPUT_DIR);
            Object obj3 = configuration.get(JSConfigurationKeys.SOURCE_MAP_EMBED_SOURCES, SourceMapSourceEmbedding.INLINING);
            Intrinsics.checkNotNullExpressionValue(obj3, "get(...)");
            SourceMapSourceEmbedding sourceMapSourceEmbedding = (SourceMapSourceEmbedding) obj3;
            Object obj4 = configuration.get(JSConfigurationKeys.SOURCEMAP_NAMES_POLICY, SourceMapNamesPolicy.SIMPLE_NAMES);
            Intrinsics.checkNotNullExpressionValue(obj4, "get(...)");
            return new SourceMapsInfo(str, list, file, sourceMapSourceEmbedding, (SourceMapNamesPolicy) obj4);
        }
    }
}
