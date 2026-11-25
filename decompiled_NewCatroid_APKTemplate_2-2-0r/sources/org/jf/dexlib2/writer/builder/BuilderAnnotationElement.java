package org.jf.dexlib2.writer.builder;

import org.jf.dexlib2.base.BaseAnnotationElement;
import org.jf.dexlib2.iface.value.EncodedValue;

/* JADX INFO: loaded from: classes6.dex */
public class BuilderAnnotationElement extends BaseAnnotationElement {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    final BuilderStringReference f1033name;
    final BuilderEncodedValues.BuilderEncodedValue value;

    public BuilderAnnotationElement(BuilderStringReference builderStringReference, BuilderEncodedValues.BuilderEncodedValue builderEncodedValue) {
        this.f1033name = builderStringReference;
        this.value = builderEncodedValue;
    }

    @Override // org.jf.dexlib2.iface.AnnotationElement
    public String getName() {
        return this.f1033name.getString();
    }

    @Override // org.jf.dexlib2.iface.AnnotationElement
    public EncodedValue getValue() {
        return this.value;
    }
}
