package org.jf.dexlib2.writer.pool;

import org.jf.dexlib2.iface.reference.StringReference;
import org.jf.dexlib2.writer.StringSection;
import org.jf.util.ExceptionWithContext;

/* JADX INFO: loaded from: classes6.dex */
public class StringPool extends StringTypeBasePool implements StringSection<CharSequence, StringReference> {
    public StringPool(DexPool dexPool) {
        super(dexPool);
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    public void intern(CharSequence charSequence) {
        this.internedItems.put((Key) charSequence.toString(), (Value) 0);
    }

    public void internNullable(CharSequence charSequence) {
        if (charSequence != null) {
            intern(charSequence);
        }
    }

    @Override // org.jf.dexlib2.writer.StringSection
    public int getItemIndex(StringReference stringReference) {
        Integer num = (Integer) this.internedItems.get(stringReference.toString());
        if (num == null) {
            throw new ExceptionWithContext("Item not found.: %s", stringReference.toString());
        }
        return num.intValue();
    }

    @Override // org.jf.dexlib2.writer.StringSection
    public boolean hasJumboIndexes() {
        return this.internedItems.size() > 65536;
    }
}
