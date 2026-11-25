package org.jf.dexlib2.writer.pool;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import org.jf.dexlib2.iface.Annotation;
import org.jf.dexlib2.writer.AnnotationSetSection;

/* JADX INFO: loaded from: classes6.dex */
public class AnnotationSetPool extends BaseNullableOffsetPool<Set<? extends Annotation>> implements AnnotationSetSection<Annotation, Set<? extends Annotation>> {
    @Override // org.jf.dexlib2.writer.AnnotationSetSection
    public Collection<? extends Annotation> getAnnotations(Set<? extends Annotation> set) {
        return set;
    }

    public AnnotationSetPool(DexPool dexPool) {
        super(dexPool);
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    public void intern(Set<? extends Annotation> set) {
        if (set.size() <= 0 || ((Integer) this.internedItems.put((Key) set, (Value) 0)) != null) {
            return;
        }
        Iterator<? extends Annotation> it = set.iterator();
        while (it.hasNext()) {
            ((AnnotationPool) this.dexPool.annotationSection).intern(it.next());
        }
    }
}
