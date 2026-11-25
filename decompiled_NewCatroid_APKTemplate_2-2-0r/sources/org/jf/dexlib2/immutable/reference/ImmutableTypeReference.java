package org.jf.dexlib2.immutable.reference;

import java.util.List;
import org.jf.dexlib2.base.reference.BaseTypeReference;
import org.jf.dexlib2.iface.reference.TypeReference;
import org.jf.util.ImmutableConverter;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableTypeReference extends BaseTypeReference implements ImmutableReference {
    private static final ImmutableConverter<ImmutableTypeReference, TypeReference> CONVERTER = new ImmutableConverter<ImmutableTypeReference, TypeReference>() { // from class: org.jf.dexlib2.immutable.reference.ImmutableTypeReference.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jf.util.ImmutableConverter
        public boolean isImmutable(TypeReference typeReference) {
            return typeReference instanceof ImmutableTypeReference;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jf.util.ImmutableConverter
        public ImmutableTypeReference makeImmutable(TypeReference typeReference) {
            return ImmutableTypeReference.of(typeReference);
        }
    };
    protected final String type;

    public ImmutableTypeReference(String str) {
        this.type = str;
    }

    public static ImmutableTypeReference of(TypeReference typeReference) {
        if (typeReference instanceof ImmutableTypeReference) {
            return (ImmutableTypeReference) typeReference;
        }
        return new ImmutableTypeReference(typeReference.getType());
    }

    @Override // org.jf.dexlib2.iface.reference.TypeReference, org.jf.dexlib2.iface.ClassDef
    public String getType() {
        return this.type;
    }

    public static List<ImmutableTypeReference> immutableListOf(List<? extends TypeReference> list) {
        return CONVERTER.toList(list);
    }
}
