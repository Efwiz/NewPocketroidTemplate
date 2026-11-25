package org.jetbrains.kotlin.incremental;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ClasspathChanges.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/incremental/ClasspathChanges;", "Ljava/io/Serializable;", "()V", "ClasspathSnapshotDisabled", "ClasspathSnapshotEnabled", "NotAvailableForJSCompiler", "Lorg/jetbrains/kotlin/incremental/ClasspathChanges$ClasspathSnapshotDisabled;", "Lorg/jetbrains/kotlin/incremental/ClasspathChanges$ClasspathSnapshotEnabled;", "Lorg/jetbrains/kotlin/incremental/ClasspathChanges$NotAvailableForJSCompiler;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ClasspathChanges implements Serializable {
    public /* synthetic */ ClasspathChanges(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private ClasspathChanges() {
    }

    /* JADX INFO: compiled from: ClasspathChanges.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0007\b\tB\u0007\b\u0004¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0003\n\u000b\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/incremental/ClasspathChanges$ClasspathSnapshotEnabled;", "Lorg/jetbrains/kotlin/incremental/ClasspathChanges;", "()V", "classpathSnapshotFiles", "Lorg/jetbrains/kotlin/incremental/ClasspathSnapshotFiles;", "getClasspathSnapshotFiles", "()Lorg/jetbrains/kotlin/incremental/ClasspathSnapshotFiles;", "IncrementalRun", "NotAvailableDueToMissingClasspathSnapshot", "NotAvailableForNonIncrementalRun", "Lorg/jetbrains/kotlin/incremental/ClasspathChanges$ClasspathSnapshotEnabled$IncrementalRun;", "Lorg/jetbrains/kotlin/incremental/ClasspathChanges$ClasspathSnapshotEnabled$NotAvailableDueToMissingClasspathSnapshot;", "Lorg/jetbrains/kotlin/incremental/ClasspathChanges$ClasspathSnapshotEnabled$NotAvailableForNonIncrementalRun;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class ClasspathSnapshotEnabled extends ClasspathChanges {
        public /* synthetic */ ClasspathSnapshotEnabled(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public abstract ClasspathSnapshotFiles getClasspathSnapshotFiles();

        private ClasspathSnapshotEnabled() {
            super(null);
        }

        /* JADX INFO: compiled from: ClasspathChanges.kt */
        @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/incremental/ClasspathChanges$ClasspathSnapshotEnabled$IncrementalRun;", "Lorg/jetbrains/kotlin/incremental/ClasspathChanges$ClasspathSnapshotEnabled;", "()V", "NoChanges", "ToBeComputedByIncrementalCompiler", "Lorg/jetbrains/kotlin/incremental/ClasspathChanges$ClasspathSnapshotEnabled$IncrementalRun$NoChanges;", "Lorg/jetbrains/kotlin/incremental/ClasspathChanges$ClasspathSnapshotEnabled$IncrementalRun$ToBeComputedByIncrementalCompiler;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static abstract class IncrementalRun extends ClasspathSnapshotEnabled {
            public /* synthetic */ IncrementalRun(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private IncrementalRun() {
                super(null);
            }

            /* JADX INFO: compiled from: ClasspathChanges.kt */
            @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/incremental/ClasspathChanges$ClasspathSnapshotEnabled$IncrementalRun$NoChanges;", "Lorg/jetbrains/kotlin/incremental/ClasspathChanges$ClasspathSnapshotEnabled$IncrementalRun;", "classpathSnapshotFiles", "Lorg/jetbrains/kotlin/incremental/ClasspathSnapshotFiles;", "(Lorg/jetbrains/kotlin/incremental/ClasspathSnapshotFiles;)V", "getClasspathSnapshotFiles", "()Lorg/jetbrains/kotlin/incremental/ClasspathSnapshotFiles;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
            public static final class NoChanges extends IncrementalRun {
                private final ClasspathSnapshotFiles classpathSnapshotFiles;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public NoChanges(ClasspathSnapshotFiles classpathSnapshotFiles) {
                    super(null);
                    Intrinsics.checkNotNullParameter(classpathSnapshotFiles, "classpathSnapshotFiles");
                    this.classpathSnapshotFiles = classpathSnapshotFiles;
                }

                @Override // org.jetbrains.kotlin.incremental.ClasspathChanges.ClasspathSnapshotEnabled
                public ClasspathSnapshotFiles getClasspathSnapshotFiles() {
                    return this.classpathSnapshotFiles;
                }
            }

            /* JADX INFO: compiled from: ClasspathChanges.kt */
            @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/incremental/ClasspathChanges$ClasspathSnapshotEnabled$IncrementalRun$ToBeComputedByIncrementalCompiler;", "Lorg/jetbrains/kotlin/incremental/ClasspathChanges$ClasspathSnapshotEnabled$IncrementalRun;", "classpathSnapshotFiles", "Lorg/jetbrains/kotlin/incremental/ClasspathSnapshotFiles;", "(Lorg/jetbrains/kotlin/incremental/ClasspathSnapshotFiles;)V", "getClasspathSnapshotFiles", "()Lorg/jetbrains/kotlin/incremental/ClasspathSnapshotFiles;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
            public static final class ToBeComputedByIncrementalCompiler extends IncrementalRun {
                private final ClasspathSnapshotFiles classpathSnapshotFiles;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public ToBeComputedByIncrementalCompiler(ClasspathSnapshotFiles classpathSnapshotFiles) {
                    super(null);
                    Intrinsics.checkNotNullParameter(classpathSnapshotFiles, "classpathSnapshotFiles");
                    this.classpathSnapshotFiles = classpathSnapshotFiles;
                }

                @Override // org.jetbrains.kotlin.incremental.ClasspathChanges.ClasspathSnapshotEnabled
                public ClasspathSnapshotFiles getClasspathSnapshotFiles() {
                    return this.classpathSnapshotFiles;
                }
            }
        }

        /* JADX INFO: compiled from: ClasspathChanges.kt */
        @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/incremental/ClasspathChanges$ClasspathSnapshotEnabled$NotAvailableDueToMissingClasspathSnapshot;", "Lorg/jetbrains/kotlin/incremental/ClasspathChanges$ClasspathSnapshotEnabled;", "classpathSnapshotFiles", "Lorg/jetbrains/kotlin/incremental/ClasspathSnapshotFiles;", "(Lorg/jetbrains/kotlin/incremental/ClasspathSnapshotFiles;)V", "getClasspathSnapshotFiles", "()Lorg/jetbrains/kotlin/incremental/ClasspathSnapshotFiles;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class NotAvailableDueToMissingClasspathSnapshot extends ClasspathSnapshotEnabled {
            private final ClasspathSnapshotFiles classpathSnapshotFiles;

            @Override // org.jetbrains.kotlin.incremental.ClasspathChanges.ClasspathSnapshotEnabled
            public ClasspathSnapshotFiles getClasspathSnapshotFiles() {
                return this.classpathSnapshotFiles;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public NotAvailableDueToMissingClasspathSnapshot(ClasspathSnapshotFiles classpathSnapshotFiles) {
                super(null);
                Intrinsics.checkNotNullParameter(classpathSnapshotFiles, "classpathSnapshotFiles");
                this.classpathSnapshotFiles = classpathSnapshotFiles;
            }
        }

        /* JADX INFO: compiled from: ClasspathChanges.kt */
        @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/incremental/ClasspathChanges$ClasspathSnapshotEnabled$NotAvailableForNonIncrementalRun;", "Lorg/jetbrains/kotlin/incremental/ClasspathChanges$ClasspathSnapshotEnabled;", "classpathSnapshotFiles", "Lorg/jetbrains/kotlin/incremental/ClasspathSnapshotFiles;", "(Lorg/jetbrains/kotlin/incremental/ClasspathSnapshotFiles;)V", "getClasspathSnapshotFiles", "()Lorg/jetbrains/kotlin/incremental/ClasspathSnapshotFiles;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class NotAvailableForNonIncrementalRun extends ClasspathSnapshotEnabled {
            private final ClasspathSnapshotFiles classpathSnapshotFiles;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public NotAvailableForNonIncrementalRun(ClasspathSnapshotFiles classpathSnapshotFiles) {
                super(null);
                Intrinsics.checkNotNullParameter(classpathSnapshotFiles, "classpathSnapshotFiles");
                this.classpathSnapshotFiles = classpathSnapshotFiles;
            }

            @Override // org.jetbrains.kotlin.incremental.ClasspathChanges.ClasspathSnapshotEnabled
            public ClasspathSnapshotFiles getClasspathSnapshotFiles() {
                return this.classpathSnapshotFiles;
            }
        }
    }

    /* JADX INFO: compiled from: ClasspathChanges.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/incremental/ClasspathChanges$ClasspathSnapshotDisabled;", "Lorg/jetbrains/kotlin/incremental/ClasspathChanges;", "()V", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ClasspathSnapshotDisabled extends ClasspathChanges {
        public static final ClasspathSnapshotDisabled INSTANCE = new ClasspathSnapshotDisabled();

        private ClasspathSnapshotDisabled() {
            super(null);
        }
    }

    /* JADX INFO: compiled from: ClasspathChanges.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/incremental/ClasspathChanges$NotAvailableForJSCompiler;", "Lorg/jetbrains/kotlin/incremental/ClasspathChanges;", "()V", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NotAvailableForJSCompiler extends ClasspathChanges {
        public static final NotAvailableForJSCompiler INSTANCE = new NotAvailableForJSCompiler();

        private NotAvailableForJSCompiler() {
            super(null);
        }
    }
}
