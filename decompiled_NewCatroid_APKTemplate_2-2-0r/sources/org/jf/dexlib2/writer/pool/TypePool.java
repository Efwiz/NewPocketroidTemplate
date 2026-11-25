package org.jf.dexlib2.writer.pool;

import org.jf.dexlib2.iface.reference.TypeReference;
import org.jf.dexlib2.writer.TypeSection;

/* JADX INFO: loaded from: classes6.dex */
public class TypePool extends StringTypeBasePool implements TypeSection<CharSequence, CharSequence, TypeReference> {
    @Override // org.jf.dexlib2.writer.TypeSection
    public CharSequence getString(CharSequence charSequence) {
        return charSequence;
    }

    public TypePool(DexPool dexPool) {
        super(dexPool);
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    public void intern(CharSequence charSequence) {
        CharSequence string = charSequence.toString();
        if (((Integer) this.internedItems.put((Key) string, (Value) 0)) == null) {
            ((StringPool) this.dexPool.stringSection).intern(string);
        }
    }

    public void internNullable(CharSequence charSequence) {
        if (charSequence != null) {
            intern(charSequence);
        }
    }

    @Override // org.jf.dexlib2.writer.TypeSection
    public int getItemIndex(TypeReference typeReference) {
        return getItemIndex((CharSequence) typeReference.getType());
    }
}
