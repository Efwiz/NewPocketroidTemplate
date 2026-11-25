package org.jf.dexlib2.writer;

import java.util.Collection;

/* JADX INFO: loaded from: classes6.dex */
public interface TypeListSection<TypeKey, TypeListKey> extends NullableOffsetSection<TypeListKey> {
    @Override // org.jf.dexlib2.writer.NullableOffsetSection
    int getNullableItemOffset(TypeListKey typelistkey);

    Collection<? extends TypeKey> getTypes(TypeListKey typelistkey);
}
