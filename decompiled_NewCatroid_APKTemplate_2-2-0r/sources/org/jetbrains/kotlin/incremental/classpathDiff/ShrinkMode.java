package org.jetbrains.kotlin.incremental.classpathDiff;

import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.incremental.storage.LookupSymbolKey;

/* JADX INFO: compiled from: ClasspathSnapshotShrinker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b2\u0018\u00002\u00020\u0001:\u0006\u0003\u0004\u0005\u0006\u0007\bB\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0004\t\n\u000b\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/ShrinkMode;", "", "()V", "ChangedLookups", "ChangedLookupsChangedClasspath", "ChangedLookupsUnchangedClasspath", "NonIncremental", "UnchangedLookupsChangedClasspath", "UnchangedLookupsUnchangedClasspath", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ShrinkMode$ChangedLookups;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ShrinkMode$NonIncremental;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ShrinkMode$UnchangedLookupsChangedClasspath;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ShrinkMode$UnchangedLookupsUnchangedClasspath;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
abstract class ShrinkMode {
    public /* synthetic */ ShrinkMode(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: compiled from: ClasspathSnapshotShrinker.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/ShrinkMode$UnchangedLookupsUnchangedClasspath;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ShrinkMode;", "()V", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class UnchangedLookupsUnchangedClasspath extends ShrinkMode {
        public static final UnchangedLookupsUnchangedClasspath INSTANCE = new UnchangedLookupsUnchangedClasspath();

        private UnchangedLookupsUnchangedClasspath() {
            super(null);
        }
    }

    private ShrinkMode() {
    }

    /* JADX INFO: compiled from: ClasspathSnapshotShrinker.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B!\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0006R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\b¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/ShrinkMode$UnchangedLookupsChangedClasspath;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ShrinkMode;", "currentClasspathSnapshot", "", "Lorg/jetbrains/kotlin/incremental/classpathDiff/AccessibleClassSnapshot;", "shrunkCurrentClasspathAgainstPreviousLookups", "(Ljava/util/List;Ljava/util/List;)V", "getCurrentClasspathSnapshot", "()Ljava/util/List;", "getShrunkCurrentClasspathAgainstPreviousLookups", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class UnchangedLookupsChangedClasspath extends ShrinkMode {
        private final List<AccessibleClassSnapshot> currentClasspathSnapshot;
        private final List<AccessibleClassSnapshot> shrunkCurrentClasspathAgainstPreviousLookups;

        public final List<AccessibleClassSnapshot> getCurrentClasspathSnapshot() {
            return this.currentClasspathSnapshot;
        }

        public final List<AccessibleClassSnapshot> getShrunkCurrentClasspathAgainstPreviousLookups() {
            return this.shrunkCurrentClasspathAgainstPreviousLookups;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public UnchangedLookupsChangedClasspath(List<? extends AccessibleClassSnapshot> currentClasspathSnapshot, List<? extends AccessibleClassSnapshot> shrunkCurrentClasspathAgainstPreviousLookups) {
            super(null);
            Intrinsics.checkNotNullParameter(currentClasspathSnapshot, "currentClasspathSnapshot");
            Intrinsics.checkNotNullParameter(shrunkCurrentClasspathAgainstPreviousLookups, "shrunkCurrentClasspathAgainstPreviousLookups");
            this.currentClasspathSnapshot = currentClasspathSnapshot;
            this.shrunkCurrentClasspathAgainstPreviousLookups = shrunkCurrentClasspathAgainstPreviousLookups;
        }
    }

    /* JADX INFO: compiled from: ClasspathSnapshotShrinker.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0004¢\u0006\u0002\u0010\u0002R\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\u0082\u0001\u0002\b\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/ShrinkMode$ChangedLookups;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ShrinkMode;", "()V", "addedLookupSymbols", "", "Lorg/jetbrains/kotlin/incremental/storage/LookupSymbolKey;", "getAddedLookupSymbols", "()Ljava/util/Set;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ShrinkMode$ChangedLookupsChangedClasspath;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ShrinkMode$ChangedLookupsUnchangedClasspath;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class ChangedLookups extends ShrinkMode {
        public /* synthetic */ ChangedLookups(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public abstract Set<LookupSymbolKey> getAddedLookupSymbols();

        private ChangedLookups() {
            super(null);
        }
    }

    /* JADX INFO: compiled from: ClasspathSnapshotShrinker.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005R\u001a\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/ShrinkMode$ChangedLookupsUnchangedClasspath;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ShrinkMode$ChangedLookups;", "addedLookupSymbols", "", "Lorg/jetbrains/kotlin/incremental/storage/LookupSymbolKey;", "(Ljava/util/Set;)V", "getAddedLookupSymbols", "()Ljava/util/Set;", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ChangedLookupsUnchangedClasspath extends ChangedLookups {
        private final Set<LookupSymbolKey> addedLookupSymbols;

        @Override // org.jetbrains.kotlin.incremental.classpathDiff.ShrinkMode.ChangedLookups
        public Set<LookupSymbolKey> getAddedLookupSymbols() {
            return this.addedLookupSymbols;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ChangedLookupsUnchangedClasspath(Set<LookupSymbolKey> addedLookupSymbols) {
            super(null);
            Intrinsics.checkNotNullParameter(addedLookupSymbols, "addedLookupSymbols");
            this.addedLookupSymbols = addedLookupSymbols;
        }
    }

    /* JADX INFO: compiled from: ClasspathSnapshotShrinker.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B/\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\tR\u001a\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\r¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/ShrinkMode$ChangedLookupsChangedClasspath;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ShrinkMode$ChangedLookups;", "addedLookupSymbols", "", "Lorg/jetbrains/kotlin/incremental/storage/LookupSymbolKey;", "currentClasspathSnapshot", "", "Lorg/jetbrains/kotlin/incremental/classpathDiff/AccessibleClassSnapshot;", "shrunkCurrentClasspathAgainstPreviousLookups", "(Ljava/util/Set;Ljava/util/List;Ljava/util/List;)V", "getAddedLookupSymbols", "()Ljava/util/Set;", "getCurrentClasspathSnapshot", "()Ljava/util/List;", "getShrunkCurrentClasspathAgainstPreviousLookups", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ChangedLookupsChangedClasspath extends ChangedLookups {
        private final Set<LookupSymbolKey> addedLookupSymbols;
        private final List<AccessibleClassSnapshot> currentClasspathSnapshot;
        private final List<AccessibleClassSnapshot> shrunkCurrentClasspathAgainstPreviousLookups;

        @Override // org.jetbrains.kotlin.incremental.classpathDiff.ShrinkMode.ChangedLookups
        public Set<LookupSymbolKey> getAddedLookupSymbols() {
            return this.addedLookupSymbols;
        }

        public final List<AccessibleClassSnapshot> getCurrentClasspathSnapshot() {
            return this.currentClasspathSnapshot;
        }

        public final List<AccessibleClassSnapshot> getShrunkCurrentClasspathAgainstPreviousLookups() {
            return this.shrunkCurrentClasspathAgainstPreviousLookups;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public ChangedLookupsChangedClasspath(Set<LookupSymbolKey> addedLookupSymbols, List<? extends AccessibleClassSnapshot> currentClasspathSnapshot, List<? extends AccessibleClassSnapshot> shrunkCurrentClasspathAgainstPreviousLookups) {
            super(null);
            Intrinsics.checkNotNullParameter(addedLookupSymbols, "addedLookupSymbols");
            Intrinsics.checkNotNullParameter(currentClasspathSnapshot, "currentClasspathSnapshot");
            Intrinsics.checkNotNullParameter(shrunkCurrentClasspathAgainstPreviousLookups, "shrunkCurrentClasspathAgainstPreviousLookups");
            this.addedLookupSymbols = addedLookupSymbols;
            this.currentClasspathSnapshot = currentClasspathSnapshot;
            this.shrunkCurrentClasspathAgainstPreviousLookups = shrunkCurrentClasspathAgainstPreviousLookups;
        }
    }

    /* JADX INFO: compiled from: ClasspathSnapshotShrinker.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/ShrinkMode$NonIncremental;", "Lorg/jetbrains/kotlin/incremental/classpathDiff/ShrinkMode;", "()V", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NonIncremental extends ShrinkMode {
        public static final NonIncremental INSTANCE = new NonIncremental();

        private NonIncremental() {
            super(null);
        }
    }
}
