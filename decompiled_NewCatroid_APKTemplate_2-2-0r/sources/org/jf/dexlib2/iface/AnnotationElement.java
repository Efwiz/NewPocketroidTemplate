package org.jf.dexlib2.iface;

import org.jf.dexlib2.iface.value.EncodedValue;

/* JADX INFO: loaded from: classes6.dex */
public interface AnnotationElement extends Comparable<AnnotationElement> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.lang.Comparable
    int compareTo(AnnotationElement annotationElement);

    boolean equals(Object obj);

    String getName();

    EncodedValue getValue();

    int hashCode();
}
