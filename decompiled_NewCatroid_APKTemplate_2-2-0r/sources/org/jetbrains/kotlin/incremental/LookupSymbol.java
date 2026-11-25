package org.jetbrains.kotlin.incremental;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: LookupStorage.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0000H\u0096\u0002J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u000b\u001a\u0004\u0018\u00010\u0011HÖ\u0003J\t\u0010\u0012\u001a\u00020\nHÖ\u0001J\t\u0010\u0013\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/incremental/LookupSymbol;", "", "name", "", "scope", "(Ljava/lang/String;Ljava/lang/String;)V", "getName", "()Ljava/lang/String;", "getScope", "compareTo", "", "other", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "", "hashCode", "toString", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class LookupSymbol implements Comparable<LookupSymbol> {
    private final String name;
    private final String scope;

    public static /* synthetic */ LookupSymbol copy$default(LookupSymbol lookupSymbol, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = lookupSymbol.name;
        }
        if ((i & 2) != 0) {
            str2 = lookupSymbol.scope;
        }
        return lookupSymbol.copy(str, str2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getScope() {
        return this.scope;
    }

    public final LookupSymbol copy(String name2, String scope) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(scope, "scope");
        return new LookupSymbol(name2, scope);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LookupSymbol)) {
            return false;
        }
        LookupSymbol lookupSymbol = (LookupSymbol) other;
        return Intrinsics.areEqual(this.name, lookupSymbol.name) && Intrinsics.areEqual(this.scope, lookupSymbol.scope);
    }

    public int hashCode() {
        return (this.name.hashCode() * 31) + this.scope.hashCode();
    }

    public String toString() {
        return "LookupSymbol(name=" + this.name + ", scope=" + this.scope + ')';
    }

    public LookupSymbol(String name2, String scope) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(scope, "scope");
        this.name = name2;
        this.scope = scope;
    }

    public final String getName() {
        return this.name;
    }

    public final String getScope() {
        return this.scope;
    }

    @Override // java.lang.Comparable
    public int compareTo(LookupSymbol other) {
        Intrinsics.checkNotNullParameter(other, "other");
        int iCompareTo = this.scope.compareTo(other.scope);
        return iCompareTo != 0 ? iCompareTo : this.name.compareTo(other.name);
    }
}
