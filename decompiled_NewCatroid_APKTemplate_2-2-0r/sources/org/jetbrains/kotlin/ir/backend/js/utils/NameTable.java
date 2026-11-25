package org.jetbrains.kotlin.ir.backend.js.utils;

import androidx.exifinterface.media.ExifInterface;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;

/* JADX INFO: compiled from: NameTables.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010%\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B7\u0012\b\b\u0002\u0010\u0003\u001a\u00020\u0002\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0016\b\u0002\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006\u0018\u00010\b¢\u0006\u0002\u0010\tJ\u001b\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00028\u00002\u0006\u0010\u0016\u001a\u00020\u0006¢\u0006\u0002\u0010\u0017J\u001b\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0015\u001a\u00028\u00002\u0006\u0010\u001a\u001a\u00020\u0006¢\u0006\u0002\u0010\u001bJ\u0010\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u0006H\u0002J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001a\u001a\u00020\u0006H\u0016R\u001f\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001d\u0010\f\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00060\b¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000bR\u0011\u0010\u0003\u001a\u00020\u0002¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00130\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/utils/NameTable;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/backend/js/utils/NameScope;", "parent", "reserved", "", "", "mappedNames", "", "(Lorg/jetbrains/kotlin/ir/backend/js/utils/NameScope;Ljava/util/Set;Ljava/util/Map;)V", "getMappedNames", "()Ljava/util/Map;", "names", "getNames", "getParent", "()Lorg/jetbrains/kotlin/ir/backend/js/utils/NameScope;", "getReserved", "()Ljava/util/Set;", "suggestedNameLastIdx", "", "declareFreshName", "declaration", "suggestedName", "(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/String;", "declareStableName", "", "name", "(Ljava/lang/Object;Ljava/lang/String;)V", "findFreshName", "isReserved", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NameTable<T> extends NameScope {
    private final Map<String, String> mappedNames;
    private final Map<T, String> names;
    private final NameScope parent;
    private final Set<String> reserved;
    private final Map<String, Integer> suggestedNameLastIdx;

    public NameTable() {
        this(null, null, null, 7, null);
    }

    public /* synthetic */ NameTable(NameScope.EmptyScope emptyScope, LinkedHashSet linkedHashSet, Map map, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? NameScope.EmptyScope.INSTANCE : emptyScope, (i & 2) != 0 ? new LinkedHashSet() : linkedHashSet, (i & 4) != 0 ? null : map);
    }

    public final NameScope getParent() {
        return this.parent;
    }

    public final Set<String> getReserved() {
        return this.reserved;
    }

    public final Map<String, String> getMappedNames() {
        return this.mappedNames;
    }

    public NameTable(NameScope parent, Set<String> reserved, Map<String, String> map) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(reserved, "reserved");
        this.parent = parent;
        this.reserved = reserved;
        this.mappedNames = map;
        this.names = new LinkedHashMap();
        this.suggestedNameLastIdx = new LinkedHashMap();
    }

    public final Map<T, String> getNames() {
        return this.names;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.NameScope
    public boolean isReserved(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return this.parent.isReserved(name2) || this.reserved.contains(name2);
    }

    public final void declareStableName(T declaration, String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        this.names.put(declaration, name2);
        this.reserved.add(name2);
        Map<String, String> map = this.mappedNames;
        if (map != null) {
            map.put(NameTablesKt.mapToKey(declaration), name2);
        }
    }

    public final String declareFreshName(T declaration, String suggestedName) {
        Intrinsics.checkNotNullParameter(suggestedName, "suggestedName");
        String strFindFreshName = findFreshName(NameTablesKt.sanitizeName$default(suggestedName, false, 2, null));
        declareStableName(declaration, strFindFreshName);
        return strFindFreshName;
    }

    private final String findFreshName(String suggestedName) {
        if (!isReserved(suggestedName)) {
            return suggestedName;
        }
        Ref.IntRef intRef = new Ref.IntRef();
        Integer num = this.suggestedNameLastIdx.get(suggestedName);
        intRef.element = num != null ? num.intValue() : 0;
        while (isReserved(findFreshName$freshName(suggestedName, intRef))) {
            intRef.element++;
        }
        this.suggestedNameLastIdx.put(suggestedName, Integer.valueOf(intRef.element));
        return findFreshName$freshName(suggestedName, intRef);
    }

    private static final String findFreshName$freshName(String str, Ref.IntRef intRef) {
        return str + '_' + intRef.element;
    }
}
