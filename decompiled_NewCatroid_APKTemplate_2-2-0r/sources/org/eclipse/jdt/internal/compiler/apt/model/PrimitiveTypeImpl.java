package org.eclipse.jdt.internal.compiler.apt.model;

import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeVisitor;
import org.eclipse.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import org.eclipse.jdt.internal.compiler.lookup.BaseTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class PrimitiveTypeImpl extends TypeMirrorImpl implements PrimitiveType {
    public static final PrimitiveTypeImpl BOOLEAN = new PrimitiveTypeImpl(TypeBinding.BOOLEAN);
    public static final PrimitiveTypeImpl BYTE = new PrimitiveTypeImpl(TypeBinding.BYTE);
    public static final PrimitiveTypeImpl CHAR = new PrimitiveTypeImpl(TypeBinding.CHAR);
    public static final PrimitiveTypeImpl DOUBLE = new PrimitiveTypeImpl(TypeBinding.DOUBLE);
    public static final PrimitiveTypeImpl FLOAT = new PrimitiveTypeImpl(TypeBinding.FLOAT);
    public static final PrimitiveTypeImpl INT = new PrimitiveTypeImpl(TypeBinding.INT);
    public static final PrimitiveTypeImpl LONG = new PrimitiveTypeImpl(TypeBinding.LONG);
    public static final PrimitiveTypeImpl SHORT = new PrimitiveTypeImpl(TypeBinding.SHORT);

    private PrimitiveTypeImpl(BaseTypeBinding baseTypeBinding) {
        super(null, baseTypeBinding);
    }

    PrimitiveTypeImpl(BaseProcessingEnvImpl baseProcessingEnvImpl, BaseTypeBinding baseTypeBinding) {
        super(baseProcessingEnvImpl, baseTypeBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.TypeMirrorImpl
    public <R, P> R accept(TypeVisitor<R, P> typeVisitor, P p) {
        return (R) typeVisitor.visitPrimitive(this, p);
    }

    @Override // org.eclipse.jdt.internal.compiler.apt.model.TypeMirrorImpl
    public TypeKind getKind() {
        return getKind((BaseTypeBinding) this._binding);
    }

    public static TypeKind getKind(BaseTypeBinding baseTypeBinding) {
        switch (baseTypeBinding.f626id) {
            case 2:
                return TypeKind.CHAR;
            case 3:
                return TypeKind.BYTE;
            case 4:
                return TypeKind.SHORT;
            case 5:
                return TypeKind.BOOLEAN;
            case 6:
            default:
                throw new IllegalArgumentException("BaseTypeBinding of unexpected id " + baseTypeBinding.f626id);
            case 7:
                return TypeKind.LONG;
            case 8:
                return TypeKind.DOUBLE;
            case 9:
                return TypeKind.FLOAT;
            case 10:
                return TypeKind.INT;
        }
    }
}
