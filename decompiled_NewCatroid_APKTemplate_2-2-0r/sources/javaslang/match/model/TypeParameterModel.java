package javaslang.match.model;

import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

/* JADX INFO: loaded from: classes11.dex */
public class TypeParameterModel {
    private final Elements elementUtils;
    private final TypeMirror typeMirror;

    public TypeParameterModel(Elements elements, TypeMirror typeMirror) {
        this.elementUtils = elements;
        this.typeMirror = typeMirror;
    }

    public ClassModel asType() {
        return new ClassModel(this.elementUtils, this.typeMirror);
    }

    public String asTypeVar() {
        return this.typeMirror.toString();
    }

    public boolean isType() {
        return isTypeKind(TypeKind.DECLARED);
    }

    public boolean isTypeVar() {
        return isTypeKind(TypeKind.TYPEVAR);
    }

    private boolean isTypeKind(TypeKind typeKind) {
        return this.typeMirror.getKind() == typeKind;
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof TypeParameterModel) && toString().equals(obj.toString()));
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public String toString() {
        return this.typeMirror.toString();
    }
}
