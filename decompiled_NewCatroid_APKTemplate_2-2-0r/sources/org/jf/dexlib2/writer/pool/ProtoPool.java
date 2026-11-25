package org.jf.dexlib2.writer.pool;

import java.util.Collection;
import java.util.List;
import org.jf.dexlib2.iface.reference.MethodProtoReference;
import org.jf.dexlib2.util.MethodUtil;
import org.jf.dexlib2.writer.ProtoSection;

/* JADX INFO: loaded from: classes6.dex */
public class ProtoPool extends BaseIndexPool<MethodProtoReference> implements ProtoSection<CharSequence, CharSequence, MethodProtoReference, TypeListPool.Key<? extends Collection<? extends CharSequence>>> {
    public ProtoPool(DexPool dexPool) {
        super(dexPool);
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    public void intern(MethodProtoReference methodProtoReference) {
        if (((Integer) this.internedItems.put((Key) methodProtoReference, (Value) 0)) == null) {
            ((StringPool) this.dexPool.stringSection).intern(getShorty(methodProtoReference));
            ((TypePool) this.dexPool.typeSection).intern(methodProtoReference.getReturnType());
            ((TypeListPool) this.dexPool.typeListSection).intern(methodProtoReference.getParameterTypes());
        }
    }

    @Override // org.jf.dexlib2.writer.ProtoSection
    public CharSequence getShorty(MethodProtoReference methodProtoReference) {
        return MethodUtil.getShorty(methodProtoReference.getParameterTypes(), methodProtoReference.getReturnType());
    }

    @Override // org.jf.dexlib2.writer.ProtoSection
    public CharSequence getReturnType(MethodProtoReference methodProtoReference) {
        return methodProtoReference.getReturnType();
    }

    @Override // org.jf.dexlib2.writer.ProtoSection
    public TypeListPool.Key<List<? extends CharSequence>> getParameters(MethodProtoReference methodProtoReference) {
        return new TypeListPool.Key<>(methodProtoReference.getParameterTypes());
    }
}
