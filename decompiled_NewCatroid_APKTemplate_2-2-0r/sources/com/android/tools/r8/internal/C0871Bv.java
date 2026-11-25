package com.android.tools.r8.internal;

import javax.lang.model.element.AnnotationMirror;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Bv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0871Bv extends AbstractC0939Dv {
    private AnnotationMirror a;

    private C0871Bv() {
        super();
        this.a = null;
    }

    public final Object visitAnnotation(AnnotationMirror annotationMirror, Object obj) {
        this.a = annotationMirror;
        return this;
    }
}
