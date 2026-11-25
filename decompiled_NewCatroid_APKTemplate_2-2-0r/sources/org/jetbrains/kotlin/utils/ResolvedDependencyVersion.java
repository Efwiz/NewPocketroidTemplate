package org.jetbrains.kotlin.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ResolvedDependencies.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\b\u0086\b\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\f\u001a\u00020\rHÖ\u0001J\u0006\u0010\u000e\u001a\u00020\nJ\b\u0010\u000f\u001a\u00020\u0003H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/utils/ResolvedDependencyVersion;", "", "version", "", "(Ljava/lang/String;)V", "getVersion", "()Ljava/lang/String;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "isEmpty", "toString", "Companion", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ResolvedDependencyVersion {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final ResolvedDependencyVersion EMPTY = new ResolvedDependencyVersion("");
    private final String version;

    public static /* synthetic */ ResolvedDependencyVersion copy$default(ResolvedDependencyVersion resolvedDependencyVersion, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = resolvedDependencyVersion.version;
        }
        return resolvedDependencyVersion.copy(str);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getVersion() {
        return this.version;
    }

    public final ResolvedDependencyVersion copy(String version) {
        Intrinsics.checkNotNullParameter(version, "version");
        return new ResolvedDependencyVersion(version);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof ResolvedDependencyVersion) && Intrinsics.areEqual(this.version, ((ResolvedDependencyVersion) other).version);
    }

    public int hashCode() {
        return this.version.hashCode();
    }

    public ResolvedDependencyVersion(String version) {
        Intrinsics.checkNotNullParameter(version, "version");
        this.version = version;
    }

    public final String getVersion() {
        return this.version;
    }

    public final boolean isEmpty() {
        return this.version.length() == 0;
    }

    public String toString() {
        return this.version;
    }

    /* JADX INFO: compiled from: ResolvedDependencies.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/utils/ResolvedDependencyVersion$Companion;", "", "()V", "EMPTY", "Lorg/jetbrains/kotlin/utils/ResolvedDependencyVersion;", "getEMPTY", "()Lorg/jetbrains/kotlin/utils/ResolvedDependencyVersion;", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ResolvedDependencyVersion getEMPTY() {
            return ResolvedDependencyVersion.EMPTY;
        }
    }
}
