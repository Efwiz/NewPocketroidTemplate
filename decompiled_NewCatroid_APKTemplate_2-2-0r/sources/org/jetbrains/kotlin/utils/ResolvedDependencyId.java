package org.jetbrains.kotlin.utils;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ResolvedDependencies.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u001c\n\u0000\n\u0002\u0010\"\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u001b\b\u0016\u0012\u0012\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\"\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0015\b\u0016\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006¢\u0006\u0002\u0010\u0007B\u0015\b\u0002\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\b¢\u0006\u0002\u0010\tJ\u0011\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0000H\u0086\u0002J\u0013\u0010\u000f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0004H\u0016J\u000e\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u0015R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/utils/ResolvedDependencyId;", "", "uniqueNames", "", "", "([Ljava/lang/String;)V", "", "(Ljava/lang/Iterable;)V", "", "(Ljava/util/Set;)V", "getUniqueNames", "()Ljava/util/Set;", "contains", "", "other", Namer.EQUALS_METHOD_NAME, "hashCode", "", "toString", "withVersion", "version", "Lorg/jetbrains/kotlin/utils/ResolvedDependencyVersion;", "Companion", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ResolvedDependencyId {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final ResolvedDependencyId DEFAULT_SOURCE_CODE_MODULE_ID = new ResolvedDependencyId("/");
    private final Set<String> uniqueNames;

    private ResolvedDependencyId(Set<String> set) {
        this.uniqueNames = set;
        if (set.isEmpty()) {
            throw new IllegalStateException("Check failed.".toString());
        }
    }

    public final Set<String> getUniqueNames() {
        return this.uniqueNames;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ResolvedDependencyId(String... uniqueNames) {
        this((Set<String>) ArraysKt.toSortedSet(uniqueNames));
        Intrinsics.checkNotNullParameter(uniqueNames, "uniqueNames");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ResolvedDependencyId(Iterable<String> uniqueNames) {
        this((Set<String>) kotlin.collections.CollectionsKt.toSortedSet(uniqueNames));
        Intrinsics.checkNotNullParameter(uniqueNames, "uniqueNames");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Object obj : this.uniqueNames) {
            int i2 = i + 1;
            if (i < 0) {
                kotlin.collections.CollectionsKt.throwIndexOverflow();
            }
            String str = (String) obj;
            if (i == 0) {
                sb.append(str);
            } else {
                if (i == 1) {
                    sb.append(" (");
                } else {
                    sb.append(", ");
                }
                sb.append(str);
                if (i == this.uniqueNames.size() - 1) {
                    sb.append(Constant.AFTER_QUTO);
                }
            }
            i = i2;
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public boolean equals(Object other) {
        ResolvedDependencyId resolvedDependencyId = other instanceof ResolvedDependencyId ? (ResolvedDependencyId) other : null;
        return Intrinsics.areEqual(resolvedDependencyId != null ? resolvedDependencyId.uniqueNames : null, this.uniqueNames);
    }

    public int hashCode() {
        return this.uniqueNames.hashCode();
    }

    public final boolean contains(ResolvedDependencyId other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return this.uniqueNames.containsAll(other.uniqueNames);
    }

    public final String withVersion(ResolvedDependencyVersion version) {
        Intrinsics.checkNotNullParameter(version, "version");
        StringBuilder sb = new StringBuilder();
        sb.append(toString());
        if (!version.isEmpty()) {
            sb.append(": ").append(version);
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    /* JADX INFO: compiled from: ResolvedDependencies.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/utils/ResolvedDependencyId$Companion;", "", "()V", "DEFAULT_SOURCE_CODE_MODULE_ID", "Lorg/jetbrains/kotlin/utils/ResolvedDependencyId;", "getDEFAULT_SOURCE_CODE_MODULE_ID", "()Lorg/jetbrains/kotlin/utils/ResolvedDependencyId;", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ResolvedDependencyId getDEFAULT_SOURCE_CODE_MODULE_ID() {
            return ResolvedDependencyId.DEFAULT_SOURCE_CODE_MODULE_ID;
        }
    }
}
