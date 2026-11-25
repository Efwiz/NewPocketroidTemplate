package org.jetbrains.kotlin.psi;

import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public interface KtAnnotated extends KtElement {
    List<KtAnnotationEntry> getAnnotationEntries();

    List<KtAnnotation> getAnnotations();
}
