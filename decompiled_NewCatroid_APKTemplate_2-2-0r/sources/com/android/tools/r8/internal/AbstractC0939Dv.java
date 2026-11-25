package com.android.tools.r8.internal;

import javax.lang.model.element.AnnotationValue;
import javax.lang.model.util.SimpleAnnotationValueVisitor7;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Dv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC0939Dv extends SimpleAnnotationValueVisitor7 {
    private AbstractC0939Dv() {
    }

    public final Object a(AnnotationValue annotationValue) {
        return annotationValue.accept(this, (Object) null);
    }

    protected final Object defaultAction(Object obj, Object obj2) {
        throw new IllegalStateException();
    }
}
