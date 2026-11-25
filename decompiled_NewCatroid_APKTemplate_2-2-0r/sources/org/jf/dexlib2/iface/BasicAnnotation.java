package org.jf.dexlib2.iface;

import java.util.Set;

/* JADX INFO: loaded from: classes6.dex */
public interface BasicAnnotation {
    Set<? extends AnnotationElement> getElements();

    String getType();
}
