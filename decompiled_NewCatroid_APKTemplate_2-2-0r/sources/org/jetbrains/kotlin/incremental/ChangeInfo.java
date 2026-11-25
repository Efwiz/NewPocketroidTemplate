package org.jetbrains.kotlin.incremental;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: IncrementalJvmCache.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0004\n\u000b\f\rB\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\bH\u0014R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0003\u000e\u000f\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/incremental/ChangeInfo;", "", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "(Lorg/jetbrains/kotlin/name/FqName;)V", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "toString", "", "toStringProperties", "MembersChanged", "ParentsChanged", "Removed", "SignatureChanged", "Lorg/jetbrains/kotlin/incremental/ChangeInfo$MembersChanged;", "Lorg/jetbrains/kotlin/incremental/ChangeInfo$ParentsChanged;", "Lorg/jetbrains/kotlin/incremental/ChangeInfo$SignatureChanged;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ChangeInfo {
    private final FqName fqName;

    public /* synthetic */ ChangeInfo(FqName fqName, DefaultConstructorMarker defaultConstructorMarker) {
        this(fqName);
    }

    /* JADX INFO: compiled from: IncrementalJvmCache.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0016\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\b\u0010\n\u001a\u00020\u0006H\u0014R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/incremental/ChangeInfo$MembersChanged;", "Lorg/jetbrains/kotlin/incremental/ChangeInfo;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "names", "", "", "(Lorg/jetbrains/kotlin/name/FqName;Ljava/util/Collection;)V", "getNames", "()Ljava/util/Collection;", "toStringProperties", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static class MembersChanged extends ChangeInfo {
        private final Collection<String> names;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MembersChanged(FqName fqName, Collection<String> names) {
            super(fqName, null);
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            Intrinsics.checkNotNullParameter(names, "names");
            this.names = names;
        }

        public final Collection<String> getNames() {
            return this.names;
        }

        @Override // org.jetbrains.kotlin.incremental.ChangeInfo
        protected String toStringProperties() {
            return super.toStringProperties() + ", names = " + this.names;
        }
    }

    private ChangeInfo(FqName fqName) {
        this.fqName = fqName;
    }

    public final FqName getFqName() {
        return this.fqName;
    }

    /* JADX INFO: compiled from: IncrementalJvmCache.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/incremental/ChangeInfo$Removed;", "Lorg/jetbrains/kotlin/incremental/ChangeInfo$MembersChanged;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "names", "", "", "(Lorg/jetbrains/kotlin/name/FqName;Ljava/util/Collection;)V", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Removed extends MembersChanged {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Removed(FqName fqName, Collection<String> names) {
            super(fqName, names);
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            Intrinsics.checkNotNullParameter(names, "names");
        }
    }

    /* JADX INFO: compiled from: IncrementalJvmCache.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/incremental/ChangeInfo$SignatureChanged;", "Lorg/jetbrains/kotlin/incremental/ChangeInfo;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "areSubclassesAffected", "", "(Lorg/jetbrains/kotlin/name/FqName;Z)V", "getAreSubclassesAffected", "()Z", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SignatureChanged extends ChangeInfo {
        private final boolean areSubclassesAffected;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SignatureChanged(FqName fqName, boolean z) {
            super(fqName, null);
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            this.areSubclassesAffected = z;
        }

        public final boolean getAreSubclassesAffected() {
            return this.areSubclassesAffected;
        }
    }

    /* JADX INFO: compiled from: IncrementalJvmCache.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\u0002\u0010\u0006R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/incremental/ChangeInfo$ParentsChanged;", "Lorg/jetbrains/kotlin/incremental/ChangeInfo;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "parentsChanged", "", "(Lorg/jetbrains/kotlin/name/FqName;Ljava/util/Collection;)V", "getParentsChanged", "()Ljava/util/Collection;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ParentsChanged extends ChangeInfo {
        private final Collection<FqName> parentsChanged;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ParentsChanged(FqName fqName, Collection<FqName> parentsChanged) {
            super(fqName, null);
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            Intrinsics.checkNotNullParameter(parentsChanged, "parentsChanged");
            this.parentsChanged = parentsChanged;
        }

        public final Collection<FqName> getParentsChanged() {
            return this.parentsChanged;
        }
    }

    protected String toStringProperties() {
        return "fqName = " + this.fqName;
    }

    public String toString() {
        return getClass().getSimpleName() + '(' + toStringProperties() + ')';
    }
}
