package org.jf.dexlib2.writer.builder;

import org.jf.dexlib2.base.BaseMethodParameter;

/* JADX INFO: loaded from: classes6.dex */
public class BuilderMethodParameter extends BaseMethodParameter {
    final BuilderAnnotationSet annotations;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    final BuilderStringReference f1036name;
    final BuilderTypeReference type;

    public BuilderMethodParameter(BuilderTypeReference builderTypeReference, BuilderStringReference builderStringReference, BuilderAnnotationSet builderAnnotationSet) {
        this.type = builderTypeReference;
        this.f1036name = builderStringReference;
        this.annotations = builderAnnotationSet;
    }

    @Override // org.jf.dexlib2.iface.reference.TypeReference, org.jf.dexlib2.iface.ClassDef
    public String getType() {
        return this.type.getType();
    }

    @Override // org.jf.dexlib2.iface.MethodParameter, org.jf.dexlib2.iface.debug.LocalInfo
    public String getName() {
        BuilderStringReference builderStringReference = this.f1036name;
        if (builderStringReference == null) {
            return null;
        }
        return builderStringReference.getString();
    }

    @Override // org.jf.dexlib2.iface.MethodParameter
    public BuilderAnnotationSet getAnnotations() {
        return this.annotations;
    }
}
