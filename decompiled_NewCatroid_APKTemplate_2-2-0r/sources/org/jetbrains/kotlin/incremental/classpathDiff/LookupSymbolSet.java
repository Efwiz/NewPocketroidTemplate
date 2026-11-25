package org.jetbrains.kotlin.incremental.classpathDiff;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.incremental.LookupSymbol;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: ProgramSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0011\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0004H\u0086\u0002J\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u000f\u001a\u00020\bR \u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/LookupSymbolSet;", "", "lookupSymbols", "", "Lorg/jetbrains/kotlin/incremental/LookupSymbol;", "(Ljava/lang/Iterable;)V", "scopeToLookupNames", "", "Lorg/jetbrains/kotlin/name/FqName;", "", "", "contains", "", "lookupSymbol", "getLookupNamesInScope", "scope", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LookupSymbolSet {
    private final Map<FqName, Set<String>> scopeToLookupNames;

    public LookupSymbolSet(Iterable<LookupSymbol> lookupSymbols) {
        Intrinsics.checkNotNullParameter(lookupSymbols, "lookupSymbols");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (LookupSymbol lookupSymbol : lookupSymbols) {
            FqName fqName = new FqName(lookupSymbol.getScope());
            Object obj = linkedHashMap.get(fqName);
            if (obj == null) {
                obj = (Set) new LinkedHashSet();
                linkedHashMap.put(fqName, obj);
            }
            ((Set) obj).add(lookupSymbol.getName());
        }
        this.scopeToLookupNames = linkedHashMap;
    }

    public final Set<String> getLookupNamesInScope(FqName scope) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Set<String> set = this.scopeToLookupNames.get(scope);
        return set == null ? SetsKt.emptySet() : set;
    }

    public final boolean contains(LookupSymbol lookupSymbol) {
        Intrinsics.checkNotNullParameter(lookupSymbol, "lookupSymbol");
        Set<String> set = this.scopeToLookupNames.get(new FqName(lookupSymbol.getScope()));
        if (set != null) {
            return set.contains(lookupSymbol.getName());
        }
        return false;
    }
}
