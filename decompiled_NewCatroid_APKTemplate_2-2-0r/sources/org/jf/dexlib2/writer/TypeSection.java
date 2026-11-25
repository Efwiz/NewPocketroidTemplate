package org.jf.dexlib2.writer;

import org.jf.dexlib2.iface.reference.TypeReference;

/* JADX INFO: loaded from: classes6.dex */
public interface TypeSection<StringKey, TypeKey, TypeRef extends TypeReference> extends NullableIndexSection<TypeKey> {
    int getItemIndex(TypeRef typeref);

    StringKey getString(TypeKey typekey);
}
