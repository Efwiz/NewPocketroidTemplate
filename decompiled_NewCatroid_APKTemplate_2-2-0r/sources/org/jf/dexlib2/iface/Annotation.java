package org.jf.dexlib2.iface;

import java.util.Set;

/* JADX INFO: loaded from: classes6.dex */
public interface Annotation extends BasicAnnotation, Comparable<Annotation> {
    @Override // java.lang.Comparable
    int compareTo(Annotation annotation);

    boolean equals(Object obj);

    @Override // org.jf.dexlib2.iface.BasicAnnotation
    Set<? extends AnnotationElement> getElements();

    @Override // org.jf.dexlib2.iface.BasicAnnotation
    String getType();

    int getVisibility();

    int hashCode();
}
