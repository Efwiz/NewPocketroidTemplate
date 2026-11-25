package org.jetbrains.kotlin.incremental;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ChangedFiles.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 \u00032\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0006\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/incremental/ChangedFiles;", "Ljava/io/Serializable;", "()V", "Companion", "Known", "Unknown", "Lorg/jetbrains/kotlin/incremental/ChangedFiles$Known;", "Lorg/jetbrains/kotlin/incremental/ChangedFiles$Unknown;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ChangedFiles implements Serializable {
    public static final long serialVersionUID = 0;

    public /* synthetic */ ChangedFiles(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: compiled from: ChangedFiles.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u00002\u00020\u0001B+\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\f¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/incremental/ChangedFiles$Known;", "Lorg/jetbrains/kotlin/incremental/ChangedFiles;", "modified", "", "Ljava/io/File;", "removed", "forDependencies", "", "(Ljava/util/List;Ljava/util/List;Z)V", "getForDependencies", "()Z", "getModified", "()Ljava/util/List;", "getRemoved", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Known extends ChangedFiles {
        private final boolean forDependencies;
        private final List<File> modified;
        private final List<File> removed;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public Known(List<? extends File> modified, List<? extends File> removed, boolean z) {
            super(null);
            Intrinsics.checkNotNullParameter(modified, "modified");
            Intrinsics.checkNotNullParameter(removed, "removed");
            this.modified = modified;
            this.removed = removed;
            this.forDependencies = z;
        }

        public /* synthetic */ Known(List list, List list2, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(list, list2, (i & 4) != 0 ? false : z);
        }

        public final boolean getForDependencies() {
            return this.forDependencies;
        }

        public final List<File> getModified() {
            return this.modified;
        }

        public final List<File> getRemoved() {
            return this.removed;
        }
    }

    private ChangedFiles() {
    }

    /* JADX INFO: compiled from: ChangedFiles.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/incremental/ChangedFiles$Unknown;", "Lorg/jetbrains/kotlin/incremental/ChangedFiles;", "()V", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Unknown extends ChangedFiles {
        public Unknown() {
            super(null);
        }
    }
}
