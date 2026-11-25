package org.jetbrains.kotlin.incremental.storage;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: values.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005B%\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\tJ\u0011\u0010\u0010\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\u0000H\u0096\u0002J\t\u0010\u0012\u001a\u00020\u0007HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0007HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J1\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0011\u001a\u0004\u0018\u00010\u0019H\u0096\u0002J\b\u0010\u001a\u001a\u00020\u0007H\u0016J\t\u0010\u001b\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000bR\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\r¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/incremental/storage/LookupSymbolKey;", "", "name", "", "scope", "(Ljava/lang/String;Ljava/lang/String;)V", "nameHash", "", "scopeHash", "(IILjava/lang/String;Ljava/lang/String;)V", "getName", "()Ljava/lang/String;", "getNameHash", "()I", "getScope", "getScopeHash", "compareTo", "other", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "", "hashCode", "toString", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class LookupSymbolKey implements Comparable<LookupSymbolKey> {
    private final String name;
    private final int nameHash;
    private final String scope;
    private final int scopeHash;

    public static /* synthetic */ LookupSymbolKey copy$default(LookupSymbolKey lookupSymbolKey, int i, int i2, String str, String str2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = lookupSymbolKey.nameHash;
        }
        if ((i3 & 2) != 0) {
            i2 = lookupSymbolKey.scopeHash;
        }
        if ((i3 & 4) != 0) {
            str = lookupSymbolKey.name;
        }
        if ((i3 & 8) != 0) {
            str2 = lookupSymbolKey.scope;
        }
        return lookupSymbolKey.copy(i, i2, str, str2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final int getNameHash() {
        return this.nameHash;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getScopeHash() {
        return this.scopeHash;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getScope() {
        return this.scope;
    }

    public final LookupSymbolKey copy(int nameHash, int scopeHash, String name2, String scope) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(scope, "scope");
        return new LookupSymbolKey(nameHash, scopeHash, name2, scope);
    }

    public String toString() {
        return "LookupSymbolKey(nameHash=" + this.nameHash + ", scopeHash=" + this.scopeHash + ", name=" + this.name + ", scope=" + this.scope + ')';
    }

    public LookupSymbolKey(int i, int i2, String name2, String scope) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(scope, "scope");
        this.nameHash = i;
        this.scopeHash = i2;
        this.name = name2;
        this.scope = scope;
    }

    public final String getName() {
        return this.name;
    }

    public final int getNameHash() {
        return this.nameHash;
    }

    public final String getScope() {
        return this.scope;
    }

    public final int getScopeHash() {
        return this.scopeHash;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LookupSymbolKey(String name2, String scope) {
        this(name2.hashCode(), scope.hashCode(), name2, scope);
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(scope, "scope");
    }

    @Override // java.lang.Comparable
    public int compareTo(LookupSymbolKey other) {
        Intrinsics.checkNotNullParameter(other, "other");
        int iCompare = Intrinsics.compare(this.nameHash, other.nameHash);
        return iCompare != 0 ? iCompare : Intrinsics.compare(this.scopeHash, other.scopeHash);
    }

    public int hashCode() {
        return (this.nameHash * 31) + this.scopeHash;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type org.jetbrains.kotlin.incremental.storage.LookupSymbolKey");
        LookupSymbolKey lookupSymbolKey = (LookupSymbolKey) other;
        return this.nameHash == lookupSymbolKey.nameHash && this.scopeHash == lookupSymbolKey.scopeHash;
    }
}
