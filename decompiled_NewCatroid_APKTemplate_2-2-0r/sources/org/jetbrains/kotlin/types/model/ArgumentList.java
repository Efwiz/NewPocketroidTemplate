package org.jetbrains.kotlin.types.model;

import java.util.ArrayList;
import kotlin.Metadata;

/* JADX INFO: compiled from: TypeSystemContext.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u00020\u0001j\b\u0012\u0004\u0012\u00020\u0002`\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/types/model/ArgumentList;", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/types/model/TypeArgumentMarker;", "Lkotlin/collections/ArrayList;", "Lorg/jetbrains/kotlin/types/model/TypeArgumentListMarker;", "initialSize", "", "(I)V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ArgumentList extends ArrayList<TypeArgumentMarker> implements TypeArgumentListMarker {
    public ArgumentList(int i) {
        super(i);
    }

    @Override // java.util.ArrayList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ boolean contains(Object obj) {
        if (obj instanceof TypeArgumentMarker) {
            return contains((TypeArgumentMarker) obj);
        }
        return false;
    }

    public /* bridge */ boolean contains(TypeArgumentMarker typeArgumentMarker) {
        return super.contains((Object) typeArgumentMarker);
    }

    public /* bridge */ int getSize() {
        return super.size();
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
    public final /* bridge */ int indexOf(Object obj) {
        if (obj instanceof TypeArgumentMarker) {
            return indexOf((TypeArgumentMarker) obj);
        }
        return -1;
    }

    public /* bridge */ int indexOf(TypeArgumentMarker typeArgumentMarker) {
        return super.indexOf((Object) typeArgumentMarker);
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
    public final /* bridge */ int lastIndexOf(Object obj) {
        if (obj instanceof TypeArgumentMarker) {
            return lastIndexOf((TypeArgumentMarker) obj);
        }
        return -1;
    }

    public /* bridge */ int lastIndexOf(TypeArgumentMarker typeArgumentMarker) {
        return super.lastIndexOf((Object) typeArgumentMarker);
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
    public final /* bridge */ TypeArgumentMarker remove(int i) {
        return removeAt(i);
    }

    @Override // java.util.ArrayList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ boolean remove(Object obj) {
        if (obj instanceof TypeArgumentMarker) {
            return remove((TypeArgumentMarker) obj);
        }
        return false;
    }

    public /* bridge */ boolean remove(TypeArgumentMarker typeArgumentMarker) {
        return super.remove((Object) typeArgumentMarker);
    }

    public /* bridge */ TypeArgumentMarker removeAt(int i) {
        return (TypeArgumentMarker) super.remove(i);
    }

    @Override // java.util.ArrayList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ int size() {
        return getSize();
    }
}
