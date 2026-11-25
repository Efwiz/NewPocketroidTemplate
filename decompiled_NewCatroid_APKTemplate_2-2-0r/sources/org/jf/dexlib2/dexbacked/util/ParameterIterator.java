package org.jf.dexlib2.dexbacked.util;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.jf.dexlib2.base.BaseMethodParameter;
import org.jf.dexlib2.iface.Annotation;
import org.jf.dexlib2.iface.MethodParameter;
import org.jf.util.collection.EmptySet;

/* JADX INFO: loaded from: classes6.dex */
public class ParameterIterator implements Iterator<MethodParameter> {
    private final Iterator<? extends Set<? extends Annotation>> parameterAnnotations;
    private final Iterator<String> parameterNames;
    private final Iterator<? extends CharSequence> parameterTypes;

    public ParameterIterator(List<? extends CharSequence> list, List<? extends Set<? extends Annotation>> list2, Iterator<String> it) {
        this.parameterTypes = list.iterator();
        this.parameterAnnotations = list2.iterator();
        this.parameterNames = it;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.parameterTypes.hasNext();
    }

    @Override // java.util.Iterator
    public MethodParameter next() {
        final Set<? extends Annotation> setOf;
        final String string = this.parameterTypes.next().toString();
        if (this.parameterAnnotations.hasNext()) {
            setOf = this.parameterAnnotations.next();
        } else {
            setOf = EmptySet.of();
        }
        final String next = this.parameterNames.hasNext() ? this.parameterNames.next() : null;
        return new BaseMethodParameter() { // from class: org.jf.dexlib2.dexbacked.util.ParameterIterator.1
            @Override // org.jf.dexlib2.iface.MethodParameter
            public Set<? extends Annotation> getAnnotations() {
                return setOf;
            }

            @Override // org.jf.dexlib2.iface.MethodParameter, org.jf.dexlib2.iface.debug.LocalInfo
            public String getName() {
                return next;
            }

            @Override // org.jf.dexlib2.iface.reference.TypeReference, org.jf.dexlib2.iface.ClassDef
            public String getType() {
                return string;
            }
        };
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
