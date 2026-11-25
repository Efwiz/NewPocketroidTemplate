package org.eclipse.jdt.internal.compiler.apt.model;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.NullType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.Types;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import org.eclipse.jdt.internal.compiler.lookup.ArrayBinding;
import org.eclipse.jdt.internal.compiler.lookup.BaseTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class TypesImpl implements Types {
    private static /* synthetic */ int[] $SWITCH_TABLE$javax$lang$model$element$ElementKind;
    private static /* synthetic */ int[] $SWITCH_TABLE$javax$lang$model$type$TypeKind;
    private final BaseProcessingEnvImpl _env;

    static /* synthetic */ int[] $SWITCH_TABLE$javax$lang$model$element$ElementKind() {
        int[] iArr = $SWITCH_TABLE$javax$lang$model$element$ElementKind;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[ElementKind.values().length];
        try {
            iArr2[ElementKind.ANNOTATION_TYPE.ordinal()] = 4;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[ElementKind.CLASS.ordinal()] = 3;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr2[ElementKind.CONSTRUCTOR.ordinal()] = 12;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            iArr2[ElementKind.ENUM.ordinal()] = 2;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            iArr2[ElementKind.ENUM_CONSTANT.ordinal()] = 6;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            iArr2[ElementKind.EXCEPTION_PARAMETER.ordinal()] = 10;
        } catch (NoSuchFieldError unused6) {
        }
        try {
            iArr2[ElementKind.FIELD.ordinal()] = 7;
        } catch (NoSuchFieldError unused7) {
        }
        try {
            iArr2[ElementKind.INSTANCE_INIT.ordinal()] = 14;
        } catch (NoSuchFieldError unused8) {
        }
        try {
            iArr2[ElementKind.INTERFACE.ordinal()] = 5;
        } catch (NoSuchFieldError unused9) {
        }
        try {
            iArr2[ElementKind.LOCAL_VARIABLE.ordinal()] = 9;
        } catch (NoSuchFieldError unused10) {
        }
        try {
            iArr2[ElementKind.METHOD.ordinal()] = 11;
        } catch (NoSuchFieldError unused11) {
        }
        try {
            iArr2[ElementKind.OTHER.ordinal()] = 16;
        } catch (NoSuchFieldError unused12) {
        }
        try {
            iArr2[ElementKind.PACKAGE.ordinal()] = 1;
        } catch (NoSuchFieldError unused13) {
        }
        try {
            iArr2[ElementKind.PARAMETER.ordinal()] = 8;
        } catch (NoSuchFieldError unused14) {
        }
        try {
            iArr2[ElementKind.RESOURCE_VARIABLE.ordinal()] = 17;
        } catch (NoSuchFieldError unused15) {
        }
        try {
            iArr2[ElementKind.STATIC_INIT.ordinal()] = 13;
        } catch (NoSuchFieldError unused16) {
        }
        try {
            iArr2[ElementKind.TYPE_PARAMETER.ordinal()] = 15;
        } catch (NoSuchFieldError unused17) {
        }
        $SWITCH_TABLE$javax$lang$model$element$ElementKind = iArr2;
        return iArr2;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$javax$lang$model$type$TypeKind() {
        int[] iArr = $SWITCH_TABLE$javax$lang$model$type$TypeKind;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[TypeKind.values().length];
        try {
            iArr2[TypeKind.ARRAY.ordinal()] = 12;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[TypeKind.BOOLEAN.ordinal()] = 1;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr2[TypeKind.BYTE.ordinal()] = 2;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            iArr2[TypeKind.CHAR.ordinal()] = 6;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            iArr2[TypeKind.DECLARED.ordinal()] = 13;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            iArr2[TypeKind.DOUBLE.ordinal()] = 8;
        } catch (NoSuchFieldError unused6) {
        }
        try {
            iArr2[TypeKind.ERROR.ordinal()] = 14;
        } catch (NoSuchFieldError unused7) {
        }
        try {
            iArr2[TypeKind.EXECUTABLE.ordinal()] = 18;
        } catch (NoSuchFieldError unused8) {
        }
        try {
            iArr2[TypeKind.FLOAT.ordinal()] = 7;
        } catch (NoSuchFieldError unused9) {
        }
        try {
            iArr2[TypeKind.INT.ordinal()] = 4;
        } catch (NoSuchFieldError unused10) {
        }
        try {
            iArr2[TypeKind.LONG.ordinal()] = 5;
        } catch (NoSuchFieldError unused11) {
        }
        try {
            iArr2[TypeKind.NONE.ordinal()] = 10;
        } catch (NoSuchFieldError unused12) {
        }
        try {
            iArr2[TypeKind.NULL.ordinal()] = 11;
        } catch (NoSuchFieldError unused13) {
        }
        try {
            iArr2[TypeKind.OTHER.ordinal()] = 19;
        } catch (NoSuchFieldError unused14) {
        }
        try {
            iArr2[TypeKind.PACKAGE.ordinal()] = 17;
        } catch (NoSuchFieldError unused15) {
        }
        try {
            iArr2[TypeKind.SHORT.ordinal()] = 3;
        } catch (NoSuchFieldError unused16) {
        }
        try {
            iArr2[TypeKind.TYPEVAR.ordinal()] = 15;
        } catch (NoSuchFieldError unused17) {
        }
        try {
            iArr2[TypeKind.UNION.ordinal()] = 20;
        } catch (NoSuchFieldError unused18) {
        }
        try {
            iArr2[TypeKind.VOID.ordinal()] = 9;
        } catch (NoSuchFieldError unused19) {
        }
        try {
            iArr2[TypeKind.WILDCARD.ordinal()] = 16;
        } catch (NoSuchFieldError unused20) {
        }
        $SWITCH_TABLE$javax$lang$model$type$TypeKind = iArr2;
        return iArr2;
    }

    public TypesImpl(BaseProcessingEnvImpl baseProcessingEnvImpl) {
        this._env = baseProcessingEnvImpl;
    }

    public Element asElement(TypeMirror typeMirror) {
        int i = $SWITCH_TABLE$javax$lang$model$type$TypeKind()[typeMirror.getKind().ordinal()];
        if (i == 13 || i == 15) {
            return this._env.getFactory().newElement(((TypeMirrorImpl) typeMirror).binding());
        }
        return null;
    }

    public TypeMirror asMemberOf(DeclaredType declaredType, Element element) {
        ElementImpl elementImpl = (ElementImpl) element;
        ReferenceBinding referenceBindingSuperclass = (ReferenceBinding) ((DeclaredTypeImpl) declaredType)._binding;
        int i = $SWITCH_TABLE$javax$lang$model$element$ElementKind()[element.getKind().ordinal()];
        if (i == 11 || i == 12) {
            MethodBinding methodBinding = (MethodBinding) elementImpl._binding;
            while (referenceBindingSuperclass != null) {
                for (MethodBinding methodBinding2 : referenceBindingSuperclass.methods()) {
                    if (CharOperation.equals(methodBinding2.selector, methodBinding.selector) && (methodBinding2.original() == methodBinding || methodBinding2.areParameterErasuresEqual(methodBinding))) {
                        return this._env.getFactory().newTypeMirror(methodBinding2);
                    }
                }
                referenceBindingSuperclass = referenceBindingSuperclass.superclass();
            }
        } else {
            switch (i) {
                case 2:
                case 3:
                case 4:
                case 5:
                    ReferenceBinding referenceBinding = (ReferenceBinding) elementImpl._binding;
                    while (referenceBindingSuperclass != null) {
                        for (ReferenceBinding referenceBinding2 : referenceBindingSuperclass.memberTypes()) {
                            if (CharOperation.equals(referenceBinding.compoundName, referenceBinding2.compoundName)) {
                                return this._env.getFactory().newTypeMirror(referenceBinding2);
                            }
                        }
                        referenceBindingSuperclass = referenceBindingSuperclass.superclass();
                    }
                    break;
                case 6:
                case 7:
                    FieldBinding fieldBinding = (FieldBinding) elementImpl._binding;
                    while (referenceBindingSuperclass != null) {
                        for (FieldBinding fieldBinding2 : referenceBindingSuperclass.fields()) {
                            if (CharOperation.equals(fieldBinding2.f628name, fieldBinding.f628name)) {
                                return this._env.getFactory().newTypeMirror(fieldBinding2);
                            }
                        }
                        referenceBindingSuperclass = referenceBindingSuperclass.superclass();
                    }
                    break;
                default:
                    throw new IllegalArgumentException("element " + element + " has unrecognized element kind " + element.getKind());
            }
        }
        throw new IllegalArgumentException("element " + element + " is not a member of the containing type " + declaredType + " nor any of its superclasses");
    }

    public TypeElement boxedClass(PrimitiveType primitiveType) {
        return this._env.getFactory().newElement(this._env.getLookupEnvironment().computeBoxingType((BaseTypeBinding) ((PrimitiveTypeImpl) primitiveType)._binding));
    }

    public TypeMirror capture(TypeMirror typeMirror) {
        throw new UnsupportedOperationException("NYI: TypesImpl.capture(...)");
    }

    public boolean contains(TypeMirror typeMirror, TypeMirror typeMirror2) {
        int i = $SWITCH_TABLE$javax$lang$model$type$TypeKind()[typeMirror.getKind().ordinal()];
        if (i == 17 || i == 18) {
            throw new IllegalArgumentException("Executable and package are illegal argument for Types.contains(..)");
        }
        int i2 = $SWITCH_TABLE$javax$lang$model$type$TypeKind()[typeMirror2.getKind().ordinal()];
        if (i2 == 17 || i2 == 18) {
            throw new IllegalArgumentException("Executable and package are illegal argument for Types.contains(..)");
        }
        throw new UnsupportedOperationException("NYI: TypesImpl.contains(" + typeMirror + ", " + typeMirror2 + Constant.AFTER_QUTO);
    }

    public List<? extends TypeMirror> directSupertypes(TypeMirror typeMirror) {
        int i = $SWITCH_TABLE$javax$lang$model$type$TypeKind()[typeMirror.getKind().ordinal()];
        if (i == 17 || i == 18) {
            throw new IllegalArgumentException("Invalid type mirror for directSupertypes");
        }
        Binding binding = ((TypeMirrorImpl) typeMirror)._binding;
        if (binding instanceof ReferenceBinding) {
            ReferenceBinding referenceBinding = (ReferenceBinding) binding;
            ArrayList arrayList = new ArrayList();
            ReferenceBinding referenceBindingSuperclass = referenceBinding.superclass();
            if (referenceBindingSuperclass != null) {
                arrayList.add(this._env.getFactory().newTypeMirror(referenceBindingSuperclass));
            }
            for (ReferenceBinding referenceBinding2 : referenceBinding.superInterfaces()) {
                arrayList.add(this._env.getFactory().newTypeMirror(referenceBinding2));
            }
            return Collections.unmodifiableList(arrayList);
        }
        return Collections.emptyList();
    }

    public TypeMirror erasure(TypeMirror typeMirror) {
        Binding binding = ((TypeMirrorImpl) typeMirror)._binding;
        if (binding instanceof ReferenceBinding) {
            TypeBinding typeBindingErasure = ((ReferenceBinding) binding).erasure();
            if (typeBindingErasure.isGenericType()) {
                typeBindingErasure = this._env.getLookupEnvironment().convertToRawType(typeBindingErasure, false);
            }
            return this._env.getFactory().newTypeMirror(typeBindingErasure);
        }
        if (!(binding instanceof ArrayBinding)) {
            return typeMirror;
        }
        TypeBinding typeBinding = (TypeBinding) binding;
        TypeBinding typeBindingErasure2 = typeBinding.leafComponentType().erasure();
        if (typeBindingErasure2.isGenericType()) {
            typeBindingErasure2 = this._env.getLookupEnvironment().convertToRawType(typeBindingErasure2, false);
        }
        return this._env.getFactory().newTypeMirror(this._env.getLookupEnvironment().createArrayType(typeBindingErasure2, typeBinding.dimensions()));
    }

    public ArrayType getArrayType(TypeMirror typeMirror) {
        TypeBinding typeBinding = (TypeBinding) ((TypeMirrorImpl) typeMirror)._binding;
        return this._env.getFactory().newTypeMirror(this._env.getLookupEnvironment().createArrayType(typeBinding.leafComponentType(), typeBinding.dimensions() + 1));
    }

    public DeclaredType getDeclaredType(TypeElement typeElement, TypeMirror... typeMirrorArr) {
        int length = typeMirrorArr.length;
        ReferenceBinding referenceBinding = (ReferenceBinding) ((TypeElementImpl) typeElement)._binding;
        int length2 = referenceBinding.typeVariables().length;
        if (length == 0) {
            if (referenceBinding.isGenericType()) {
                return this._env.getFactory().newTypeMirror(this._env.getLookupEnvironment().createRawType(referenceBinding, null));
            }
            return typeElement.asType();
        }
        if (length != length2) {
            throw new IllegalArgumentException("Number of typeArguments doesn't match the number of formal parameters of typeElem");
        }
        TypeBinding[] typeBindingArr = new TypeBinding[length];
        for (int i = 0; i < length; i++) {
            TypeMirrorImpl typeMirrorImpl = (TypeMirrorImpl) typeMirrorArr[i];
            Binding binding = typeMirrorImpl._binding;
            if (!(binding instanceof TypeBinding)) {
                throw new IllegalArgumentException("Invalid type argument: " + typeMirrorImpl);
            }
            typeBindingArr[i] = (TypeBinding) binding;
        }
        return this._env.getFactory().newTypeMirror(this._env.getLookupEnvironment().createParameterizedType(referenceBinding, typeBindingArr, null));
    }

    public DeclaredType getDeclaredType(DeclaredType declaredType, TypeElement typeElement, TypeMirror... typeMirrorArr) {
        int length = typeMirrorArr.length;
        ReferenceBinding referenceBinding = (ReferenceBinding) ((TypeElementImpl) typeElement)._binding;
        int length2 = referenceBinding.typeVariables().length;
        ReferenceBinding referenceBinding2 = (ReferenceBinding) ((DeclaredTypeImpl) declaredType)._binding;
        if (length == 0) {
            if (referenceBinding.isGenericType()) {
                return this._env.getFactory().newTypeMirror(this._env.getLookupEnvironment().createRawType(referenceBinding, referenceBinding2));
            }
            return this._env.getFactory().newTypeMirror(this._env.getLookupEnvironment().createParameterizedType(referenceBinding, null, referenceBinding2));
        }
        if (length != length2) {
            throw new IllegalArgumentException("Number of typeArguments doesn't match the number of formal parameters of typeElem");
        }
        TypeBinding[] typeBindingArr = new TypeBinding[length];
        for (int i = 0; i < length; i++) {
            TypeMirrorImpl typeMirrorImpl = (TypeMirrorImpl) typeMirrorArr[i];
            Binding binding = typeMirrorImpl._binding;
            if (!(binding instanceof TypeBinding)) {
                throw new IllegalArgumentException("Invalid type for a type arguments : " + typeMirrorImpl);
            }
            typeBindingArr[i] = (TypeBinding) binding;
        }
        return this._env.getFactory().newTypeMirror(this._env.getLookupEnvironment().createParameterizedType(referenceBinding, typeBindingArr, referenceBinding2));
    }

    public NoType getNoType(TypeKind typeKind) {
        return this._env.getFactory().getNoType(typeKind);
    }

    public NullType getNullType() {
        return this._env.getFactory().getNullType();
    }

    public PrimitiveType getPrimitiveType(TypeKind typeKind) {
        return this._env.getFactory().getPrimitiveType(typeKind);
    }

    public WildcardType getWildcardType(TypeMirror typeMirror, TypeMirror typeMirror2) {
        if (typeMirror != null && typeMirror2 != null) {
            throw new IllegalArgumentException("Extends and super bounds cannot be set at the same time");
        }
        if (typeMirror != null) {
            return this._env.getFactory().newTypeMirror(this._env.getLookupEnvironment().createWildcard(null, 0, (TypeBinding) ((TypeMirrorImpl) typeMirror)._binding, null, 1));
        }
        if (typeMirror2 != null) {
            TypeBinding typeBinding = (TypeBinding) ((TypeMirrorImpl) typeMirror2)._binding;
            BaseProcessingEnvImpl baseProcessingEnvImpl = this._env;
            return new WildcardTypeImpl(baseProcessingEnvImpl, baseProcessingEnvImpl.getLookupEnvironment().createWildcard(null, 0, typeBinding, null, 2));
        }
        BaseProcessingEnvImpl baseProcessingEnvImpl2 = this._env;
        return new WildcardTypeImpl(baseProcessingEnvImpl2, baseProcessingEnvImpl2.getLookupEnvironment().createWildcard(null, 0, null, null, 0));
    }

    public boolean isAssignable(TypeMirror typeMirror, TypeMirror typeMirror2) {
        if (!(typeMirror instanceof TypeMirrorImpl) || !(typeMirror2 instanceof TypeMirrorImpl)) {
            return false;
        }
        Binding binding = ((TypeMirrorImpl) typeMirror).binding();
        Binding binding2 = ((TypeMirrorImpl) typeMirror2).binding();
        if (!(binding instanceof TypeBinding) || !(binding2 instanceof TypeBinding)) {
            throw new IllegalArgumentException();
        }
        TypeBinding typeBinding = (TypeBinding) binding;
        TypeBinding typeBinding2 = (TypeBinding) binding2;
        if (typeBinding.isCompatibleWith(typeBinding2)) {
            return true;
        }
        TypeBinding typeBindingComputeBoxingType = this._env.getLookupEnvironment().computeBoxingType(typeBinding);
        return typeBindingComputeBoxingType != null && typeBindingComputeBoxingType.isCompatibleWith(typeBinding2);
    }

    public boolean isSameType(TypeMirror typeMirror, TypeMirror typeMirror2) {
        if (typeMirror.getKind() != TypeKind.WILDCARD && typeMirror2.getKind() != TypeKind.WILDCARD) {
            if (typeMirror == typeMirror2) {
                return true;
            }
            if ((typeMirror instanceof TypeMirrorImpl) && (typeMirror2 instanceof TypeMirrorImpl)) {
                Binding binding = ((TypeMirrorImpl) typeMirror).binding();
                Binding binding2 = ((TypeMirrorImpl) typeMirror2).binding();
                if (binding == binding2) {
                    return true;
                }
                if ((binding instanceof TypeBinding) && (binding2 instanceof TypeBinding)) {
                    TypeBinding typeBinding = (TypeBinding) binding;
                    TypeBinding typeBinding2 = (TypeBinding) binding2;
                    if (TypeBinding.equalsEquals(typeBinding, typeBinding2)) {
                        return true;
                    }
                    return CharOperation.equals(typeBinding.computeUniqueKey(), typeBinding2.computeUniqueKey());
                }
            }
        }
        return false;
    }

    public boolean isSubsignature(ExecutableType executableType, ExecutableType executableType2) {
        MethodBinding methodBinding = (MethodBinding) ((ExecutableTypeImpl) executableType)._binding;
        MethodBinding methodBinding2 = (MethodBinding) ((ExecutableTypeImpl) executableType2)._binding;
        return CharOperation.equals(methodBinding.selector, methodBinding2.selector) && methodBinding.areParameterErasuresEqual(methodBinding2) && methodBinding.areTypeVariableErasuresEqual(methodBinding2);
    }

    public boolean isSubtype(TypeMirror typeMirror, TypeMirror typeMirror2) {
        Binding binding;
        Binding binding2;
        if (typeMirror instanceof NoTypeImpl) {
            return (typeMirror2 instanceof NoTypeImpl) && ((NoTypeImpl) typeMirror).getKind() == ((NoTypeImpl) typeMirror2).getKind();
        }
        if (!(typeMirror2 instanceof NoTypeImpl) && (typeMirror instanceof TypeMirrorImpl) && (typeMirror2 instanceof TypeMirrorImpl)) {
            if (typeMirror == typeMirror2 || (binding = ((TypeMirrorImpl) typeMirror).binding()) == (binding2 = ((TypeMirrorImpl) typeMirror2).binding())) {
                return true;
            }
            if ((binding instanceof TypeBinding) && (binding2 instanceof TypeBinding)) {
                if (binding.kind() == 132 || binding2.kind() == 132) {
                    if (binding.kind() != binding2.kind()) {
                        return false;
                    }
                    return ((TypeBinding) binding).isCompatibleWith((TypeBinding) binding2);
                }
                return ((TypeBinding) binding).isCompatibleWith((TypeBinding) binding2);
            }
        }
        return false;
    }

    public PrimitiveType unboxedType(TypeMirror typeMirror) {
        TypeMirrorImpl typeMirrorImpl = (TypeMirrorImpl) typeMirror;
        if (!(typeMirrorImpl._binding instanceof ReferenceBinding)) {
            throw new IllegalArgumentException("Given type mirror cannot be unboxed");
        }
        TypeBinding typeBindingComputeBoxingType = this._env.getLookupEnvironment().computeBoxingType((ReferenceBinding) typeMirrorImpl._binding);
        if (typeBindingComputeBoxingType.kind() != 132) {
            throw new IllegalArgumentException();
        }
        return this._env.getFactory().newTypeMirror((BaseTypeBinding) typeBindingComputeBoxingType);
    }
}
