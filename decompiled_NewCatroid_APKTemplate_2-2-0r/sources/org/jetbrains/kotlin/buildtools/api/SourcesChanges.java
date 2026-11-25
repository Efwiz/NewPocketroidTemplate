package org.jetbrains.kotlin.buildtools.api;

import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SourcesChanges.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bw\u0018\u00002\u00020\u0001:\u0003\u0002\u0003\u0004\u0082\u0001\u0003\u0005\u0006\u0007ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/buildtools/api/SourcesChanges;", "", "Known", "ToBeCalculated", "Unknown", "Lorg/jetbrains/kotlin/buildtools/api/SourcesChanges$Known;", "Lorg/jetbrains/kotlin/buildtools/api/SourcesChanges$ToBeCalculated;", "Lorg/jetbrains/kotlin/buildtools/api/SourcesChanges$Unknown;", "kotlin-build-tools-api"}, k = 1, mv = {1, 5, 1}, xi = 48)
@ExperimentalBuildToolsApi
public interface SourcesChanges {

    /* JADX INFO: compiled from: SourcesChanges.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/buildtools/api/SourcesChanges$Unknown;", "Lorg/jetbrains/kotlin/buildtools/api/SourcesChanges;", "()V", "kotlin-build-tools-api"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Unknown implements SourcesChanges {
        public static final Unknown INSTANCE = new Unknown();

        private Unknown() {
        }
    }

    /* JADX INFO: compiled from: SourcesChanges.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/buildtools/api/SourcesChanges$ToBeCalculated;", "Lorg/jetbrains/kotlin/buildtools/api/SourcesChanges;", "()V", "kotlin-build-tools-api"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class ToBeCalculated implements SourcesChanges {
        public static final ToBeCalculated INSTANCE = new ToBeCalculated();

        private ToBeCalculated() {
        }
    }

    /* JADX INFO: compiled from: SourcesChanges.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B!\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0006R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\b¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/buildtools/api/SourcesChanges$Known;", "Lorg/jetbrains/kotlin/buildtools/api/SourcesChanges;", "modifiedFiles", "", "Ljava/io/File;", "removedFiles", "(Ljava/util/List;Ljava/util/List;)V", "getModifiedFiles", "()Ljava/util/List;", "getRemovedFiles", "kotlin-build-tools-api"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Known implements SourcesChanges {
        private final List<File> modifiedFiles;
        private final List<File> removedFiles;

        /* JADX WARN: Multi-variable type inference failed */
        public Known(List<? extends File> modifiedFiles, List<? extends File> removedFiles) {
            Intrinsics.checkNotNullParameter(modifiedFiles, "modifiedFiles");
            Intrinsics.checkNotNullParameter(removedFiles, "removedFiles");
            this.modifiedFiles = modifiedFiles;
            this.removedFiles = removedFiles;
        }

        public final List<File> getModifiedFiles() {
            return this.modifiedFiles;
        }

        public final List<File> getRemovedFiles() {
            return this.removedFiles;
        }
    }
}
