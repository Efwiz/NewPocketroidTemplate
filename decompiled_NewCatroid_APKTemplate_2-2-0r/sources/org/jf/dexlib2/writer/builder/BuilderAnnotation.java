package org.jf.dexlib2.writer.builder;

import java.util.Set;
import org.jf.dexlib2.base.BaseAnnotation;

/* JADX INFO: loaded from: classes6.dex */
class BuilderAnnotation extends BaseAnnotation {
    final Set<? extends BuilderAnnotationElement> elements;
    int offset = 0;
    final BuilderTypeReference type;
    int visibility;

    public BuilderAnnotation(int i, BuilderTypeReference builderTypeReference, Set<? extends BuilderAnnotationElement> set) {
        this.visibility = i;
        this.type = builderTypeReference;
        this.elements = set;
    }

    @Override // org.jf.dexlib2.iface.Annotation
    public int getVisibility() {
        return this.visibility;
    }

    @Override // org.jf.dexlib2.iface.Annotation, org.jf.dexlib2.iface.BasicAnnotation
    public String getType() {
        return this.type.getType();
    }

    @Override // org.jf.dexlib2.iface.Annotation, org.jf.dexlib2.iface.BasicAnnotation
    public Set<? extends BuilderAnnotationElement> getElements() {
        return this.elements;
    }
}
