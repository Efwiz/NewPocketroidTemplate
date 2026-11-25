package org.eclipse.jdt.internal.compiler.lookup;

import java.util.List;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ClassFile;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Argument;
import org.eclipse.jdt.internal.compiler.ast.LambdaExpression;
import org.eclipse.jdt.internal.compiler.ast.MethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.TypeReference;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.codegen.ConstantPool;
import org.eclipse.jdt.internal.compiler.util.Util;
import org.luaj.vm2.Lua;

/* JADX INFO: loaded from: classes14.dex */
public class MethodBinding extends Binding {
    public ReferenceBinding declaringClass;
    public int defaultNullness;
    public int modifiers;
    public char[][] parameterNames;
    public Boolean[] parameterNonNullness;
    public TypeBinding[] parameters;
    public TypeBinding receiver;
    public TypeBinding returnType;
    public char[] selector;
    char[] signature;
    public long tagBits;
    public ReferenceBinding[] thrownExceptions;
    protected AnnotationBinding[] typeAnnotations;
    public TypeVariableBinding[] typeVariables;

    public MethodBinding genericMethod() {
        return this;
    }

    public boolean hasSubstitutedParameters() {
        return false;
    }

    public boolean hasSubstitutedReturnType() {
        return false;
    }

    public boolean isParameterizedGeneric() {
        return false;
    }

    public boolean isPolymorphic() {
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public final int kind() {
        return 8;
    }

    public MethodBinding original() {
        return this;
    }

    public LambdaExpression sourceLambda() {
        return null;
    }

    public MethodBinding tiebreakMethod() {
        return this;
    }

    protected MethodBinding() {
        this.typeVariables = Binding.NO_TYPE_VARIABLES;
        this.typeAnnotations = Binding.NO_ANNOTATIONS;
        this.parameterNames = Binding.NO_PARAMETER_NAMES;
    }

    public MethodBinding(int i, char[] cArr, TypeBinding typeBinding, TypeBinding[] typeBindingArr, ReferenceBinding[] referenceBindingArr, ReferenceBinding referenceBinding) {
        this.typeVariables = Binding.NO_TYPE_VARIABLES;
        this.typeAnnotations = Binding.NO_ANNOTATIONS;
        this.parameterNames = Binding.NO_PARAMETER_NAMES;
        this.modifiers = i;
        this.selector = cArr;
        this.returnType = typeBinding;
        this.parameters = (typeBindingArr == null || typeBindingArr.length == 0) ? Binding.NO_PARAMETERS : typeBindingArr;
        this.thrownExceptions = (referenceBindingArr == null || referenceBindingArr.length == 0) ? Binding.NO_EXCEPTIONS : referenceBindingArr;
        this.declaringClass = referenceBinding;
        if (referenceBinding == null || !referenceBinding.isStrictfp() || isNative() || isAbstract()) {
            return;
        }
        this.modifiers |= 2048;
    }

    public MethodBinding(int i, TypeBinding[] typeBindingArr, ReferenceBinding[] referenceBindingArr, ReferenceBinding referenceBinding) {
        this(i, TypeConstants.INIT, TypeBinding.VOID, typeBindingArr, referenceBindingArr, referenceBinding);
    }

    public MethodBinding(MethodBinding methodBinding, ReferenceBinding referenceBinding) {
        this.typeVariables = Binding.NO_TYPE_VARIABLES;
        this.typeAnnotations = Binding.NO_ANNOTATIONS;
        this.parameterNames = Binding.NO_PARAMETER_NAMES;
        this.modifiers = methodBinding.modifiers;
        this.selector = methodBinding.selector;
        this.returnType = methodBinding.returnType;
        this.parameters = methodBinding.parameters;
        this.thrownExceptions = methodBinding.thrownExceptions;
        this.declaringClass = referenceBinding;
        referenceBinding.storeAnnotationHolder(this, methodBinding.declaringClass.retrieveAnnotationHolder(methodBinding, true));
    }

    public final boolean areParameterErasuresEqual(MethodBinding methodBinding) {
        TypeBinding[] typeBindingArr = methodBinding.parameters;
        TypeBinding[] typeBindingArr2 = this.parameters;
        if (typeBindingArr2 == typeBindingArr) {
            return true;
        }
        int length = typeBindingArr2.length;
        if (length != typeBindingArr.length) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (TypeBinding.notEquals(this.parameters[i], typeBindingArr[i]) && TypeBinding.notEquals(this.parameters[i].erasure(), typeBindingArr[i].erasure())) {
                return false;
            }
        }
        return true;
    }

    public final boolean areParametersCompatibleWith(TypeBinding[] typeBindingArr) {
        int length = this.parameters.length;
        int length2 = typeBindingArr.length;
        if (isVarargs()) {
            int i = length - 1;
            if (length == length2) {
                TypeBinding typeBinding = this.parameters[i];
                TypeBinding typeBinding2 = typeBindingArr[i];
                if (TypeBinding.notEquals(typeBinding, typeBinding2) && !typeBinding2.isCompatibleWith(typeBinding)) {
                    return false;
                }
            } else if (length < length2) {
                TypeBinding typeBindingElementsType = ((ArrayBinding) this.parameters[i]).elementsType();
                for (int i2 = i; i2 < length2; i2++) {
                    if (TypeBinding.notEquals(typeBindingElementsType, typeBindingArr[i2]) && !typeBindingArr[i2].isCompatibleWith(typeBindingElementsType)) {
                        return false;
                    }
                }
            } else if (i != length2) {
                return false;
            }
            length2 = i;
        }
        for (int i3 = 0; i3 < length2; i3++) {
            if (TypeBinding.notEquals(this.parameters[i3], typeBindingArr[i3]) && !typeBindingArr[i3].isCompatibleWith(this.parameters[i3])) {
                return false;
            }
        }
        return true;
    }

    public final boolean areParametersEqual(MethodBinding methodBinding) {
        TypeBinding[] typeBindingArr = methodBinding.parameters;
        TypeBinding[] typeBindingArr2 = this.parameters;
        if (typeBindingArr2 == typeBindingArr) {
            return true;
        }
        int length = typeBindingArr2.length;
        if (length != typeBindingArr.length) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (TypeBinding.notEquals(this.parameters[i], typeBindingArr[i])) {
                return false;
            }
        }
        return true;
    }

    public final boolean areTypeVariableErasuresEqual(MethodBinding methodBinding) {
        TypeVariableBinding[] typeVariableBindingArr = methodBinding.typeVariables;
        TypeVariableBinding[] typeVariableBindingArr2 = this.typeVariables;
        if (typeVariableBindingArr2 == typeVariableBindingArr) {
            return true;
        }
        int length = typeVariableBindingArr2.length;
        if (length != typeVariableBindingArr.length) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (TypeBinding.notEquals(this.typeVariables[i], typeVariableBindingArr[i]) && TypeBinding.notEquals(this.typeVariables[i].erasure(), typeVariableBindingArr[i].erasure())) {
                return false;
            }
        }
        return true;
    }

    public MethodBinding asRawMethod(LookupEnvironment lookupEnvironment) {
        TypeBinding typeBinding;
        TypeBinding[] typeBindingArr;
        if (this.typeVariables == Binding.NO_TYPE_VARIABLES) {
            return this;
        }
        int length = this.typeVariables.length;
        TypeBinding[] typeBindingArr2 = new TypeBinding[length];
        for (int i = 0; i < length; i++) {
            TypeVariableBinding typeVariableBinding = this.typeVariables[i];
            if (typeVariableBinding.boundsCount() <= 1) {
                typeBindingArr2[i] = lookupEnvironment.convertToRawType(typeVariableBinding.upperBound(), false);
            } else {
                ReferenceBinding[] referenceBindingArrSuperInterfaces = typeVariableBinding.superInterfaces();
                int length2 = referenceBindingArrSuperInterfaces.length;
                if (typeVariableBinding.boundsCount() == length2) {
                    TypeBinding typeBindingConvertToRawType = lookupEnvironment.convertToRawType(referenceBindingArrSuperInterfaces[0], false);
                    TypeBinding[] typeBindingArr3 = new TypeBinding[length2 - 1];
                    for (int i2 = 1; i2 < length2; i2++) {
                        typeBindingArr3[i2 - 1] = lookupEnvironment.convertToRawType(referenceBindingArrSuperInterfaces[i2], false);
                    }
                    typeBinding = typeBindingConvertToRawType;
                    typeBindingArr = typeBindingArr3;
                } else {
                    TypeBinding typeBindingConvertToRawType2 = lookupEnvironment.convertToRawType(typeVariableBinding.superclass(), false);
                    TypeBinding[] typeBindingArr4 = new TypeBinding[length2];
                    for (int i3 = 0; i3 < length2; i3++) {
                        typeBindingArr4[i3] = lookupEnvironment.convertToRawType(referenceBindingArrSuperInterfaces[i3], false);
                    }
                    typeBinding = typeBindingConvertToRawType2;
                    typeBindingArr = typeBindingArr4;
                }
                typeBindingArr2[i] = lookupEnvironment.createWildcard(null, 0, typeBinding, typeBindingArr, 1);
            }
        }
        return lookupEnvironment.createParameterizedGenericMethod(this, typeBindingArr2);
    }

    public final boolean canBeSeenBy(InvocationSite invocationSite, Scope scope) {
        SourceTypeBinding sourceTypeBinding;
        if (isPublic()) {
            return true;
        }
        SourceTypeBinding sourceTypeBindingEnclosingSourceType = scope.enclosingSourceType();
        if (TypeBinding.equalsEquals(sourceTypeBindingEnclosingSourceType, this.declaringClass)) {
            return true;
        }
        if (isProtected()) {
            if (sourceTypeBindingEnclosingSourceType.fPackage == this.declaringClass.fPackage) {
                return true;
            }
            return invocationSite.isSuperAccess();
        }
        if (!isPrivate()) {
            return sourceTypeBindingEnclosingSourceType.fPackage == this.declaringClass.fPackage;
        }
        ReferenceBinding referenceBindingEnclosingType = sourceTypeBindingEnclosingSourceType.enclosingType();
        while (true) {
            sourceTypeBinding = sourceTypeBindingEnclosingSourceType;
            sourceTypeBindingEnclosingSourceType = referenceBindingEnclosingType;
            if (sourceTypeBindingEnclosingSourceType == null) {
                break;
            }
            referenceBindingEnclosingType = sourceTypeBindingEnclosingSourceType.enclosingType();
        }
        ReferenceBinding referenceBinding = (ReferenceBinding) this.declaringClass.erasure();
        ReferenceBinding referenceBindingEnclosingType2 = referenceBinding.enclosingType();
        while (true) {
            ReferenceBinding referenceBinding2 = referenceBindingEnclosingType2;
            ReferenceBinding referenceBinding3 = referenceBinding;
            referenceBinding = referenceBinding2;
            if (referenceBinding != null) {
                referenceBindingEnclosingType2 = referenceBinding.enclosingType();
            } else {
                return TypeBinding.equalsEquals(sourceTypeBinding, referenceBinding3);
            }
        }
    }

    public final boolean canBeSeenBy(PackageBinding packageBinding) {
        if (isPublic()) {
            return true;
        }
        return !isPrivate() && packageBinding == this.declaringClass.getPackage();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding] */
    /* JADX WARN: Type inference failed for: r0v10, types: [org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding] */
    /* JADX WARN: Type inference failed for: r0v11, types: [org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding] */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r1v9, types: [org.eclipse.jdt.internal.compiler.lookup.TypeBinding] */
    /* JADX WARN: Type inference failed for: r6v0, types: [org.eclipse.jdt.internal.compiler.lookup.TypeBinding] */
    public final boolean canBeSeenBy(TypeBinding typeBinding, InvocationSite invocationSite, Scope scope) {
        ?? r6;
        ReferenceBinding referenceBinding;
        ?? EnclosingSourceType = scope.enclosingSourceType();
        if (this.declaringClass.isInterface() && isStatic()) {
            if (scope.compilerOptions().sourceLevel < ClassFileConstants.JDK1_8) {
                return false;
            }
            return (invocationSite.isTypeAccess() || invocationSite.receiverIsImplicitThis()) && TypeBinding.equalsEquals(typeBinding, this.declaringClass);
        }
        if (isPublic()) {
            return true;
        }
        if (TypeBinding.equalsEquals(EnclosingSourceType, this.declaringClass) && TypeBinding.equalsEquals(EnclosingSourceType, typeBinding)) {
            return true;
        }
        if (EnclosingSourceType == 0) {
            return !isPrivate() && scope.getCurrentPackage() == this.declaringClass.fPackage;
        }
        if (isProtected()) {
            if (TypeBinding.equalsEquals(EnclosingSourceType, this.declaringClass) || EnclosingSourceType.fPackage == this.declaringClass.fPackage) {
                return true;
            }
            ?? Erasure = typeBinding.erasure();
            ReferenceBinding referenceBinding2 = (ReferenceBinding) this.declaringClass.erasure();
            int i = 0;
            do {
                if (EnclosingSourceType.findSuperTypeOriginatingFrom(referenceBinding2) != null) {
                    if (invocationSite.isSuperAccess()) {
                        return true;
                    }
                    if (typeBinding instanceof ArrayBinding) {
                        return false;
                    }
                    if (isStatic()) {
                        if (i > 0) {
                            invocationSite.setDepth(i);
                        }
                        return true;
                    }
                    if (TypeBinding.equalsEquals(EnclosingSourceType, Erasure) || Erasure.findSuperTypeOriginatingFrom(EnclosingSourceType) != null) {
                        if (i > 0) {
                            invocationSite.setDepth(i);
                        }
                        return true;
                    }
                }
                i++;
                EnclosingSourceType = EnclosingSourceType.enclosingType();
            } while (EnclosingSourceType != 0);
            return false;
        }
        if (isPrivate()) {
            if (TypeBinding.notEquals(typeBinding, this.declaringClass) && (scope.compilerOptions().complianceLevel > ClassFileConstants.JDK1_6 || !typeBinding.isTypeVariable() || !((TypeVariableBinding) typeBinding).isErasureBoundTo(this.declaringClass.erasure()))) {
                return false;
            }
            if (TypeBinding.notEquals(EnclosingSourceType, this.declaringClass)) {
                ReferenceBinding referenceBindingEnclosingType = EnclosingSourceType.enclosingType();
                ?? r0 = EnclosingSourceType;
                while (true) {
                    r6 = r0;
                    ReferenceBinding referenceBinding3 = referenceBindingEnclosingType;
                    if (referenceBinding3 == null) {
                        break;
                    }
                    referenceBindingEnclosingType = referenceBinding3.enclosingType();
                    r0 = referenceBinding3;
                }
                ReferenceBinding referenceBinding4 = (ReferenceBinding) this.declaringClass.erasure();
                ReferenceBinding referenceBindingEnclosingType2 = referenceBinding4.enclosingType();
                while (true) {
                    ReferenceBinding referenceBinding5 = referenceBindingEnclosingType2;
                    referenceBinding = referenceBinding4;
                    referenceBinding4 = referenceBinding5;
                    if (referenceBinding4 == null) {
                        break;
                    }
                    referenceBindingEnclosingType2 = referenceBinding4.enclosingType();
                }
                if (TypeBinding.notEquals(r6, referenceBinding)) {
                    return false;
                }
            }
            return true;
        }
        PackageBinding packageBinding = this.declaringClass.fPackage;
        if (EnclosingSourceType.fPackage != packageBinding || (typeBinding instanceof ArrayBinding)) {
            return false;
        }
        TypeBinding typeBindingOriginal = this.declaringClass.original();
        ReferenceBinding referenceBindingSuperclass = (ReferenceBinding) typeBinding;
        do {
            if (referenceBindingSuperclass.isCapture()) {
                if (TypeBinding.equalsEquals(typeBindingOriginal, referenceBindingSuperclass.erasure().original())) {
                    return true;
                }
            } else if (TypeBinding.equalsEquals(typeBindingOriginal, referenceBindingSuperclass.original())) {
                return true;
            }
            PackageBinding packageBinding2 = referenceBindingSuperclass.fPackage;
            if (!referenceBindingSuperclass.isCapture() && packageBinding2 != null && packageBinding2 != packageBinding) {
                return false;
            }
            referenceBindingSuperclass = referenceBindingSuperclass.superclass();
        } while (referenceBindingSuperclass != null);
        return false;
    }

    public List<TypeBinding> collectMissingTypes(List<TypeBinding> list) {
        if ((this.tagBits & 128) != 0) {
            list = this.returnType.collectMissingTypes(list);
            int length = this.parameters.length;
            for (int i = 0; i < length; i++) {
                list = this.parameters[i].collectMissingTypes(list);
            }
            int length2 = this.thrownExceptions.length;
            for (int i2 = 0; i2 < length2; i2++) {
                list = this.thrownExceptions[i2].collectMissingTypes(list);
            }
            int length3 = this.typeVariables.length;
            for (int i3 = 0; i3 < length3; i3++) {
                TypeVariableBinding typeVariableBinding = this.typeVariables[i3];
                list = typeVariableBinding.superclass().collectMissingTypes(list);
                ReferenceBinding[] referenceBindingArrSuperInterfaces = typeVariableBinding.superInterfaces();
                for (ReferenceBinding referenceBinding : referenceBindingArrSuperInterfaces) {
                    list = referenceBinding.collectMissingTypes(list);
                }
            }
        }
        return list;
    }

    MethodBinding computeSubstitutedMethod(MethodBinding methodBinding, LookupEnvironment lookupEnvironment) {
        TypeVariableBinding[] typeVariableBindingArr = this.typeVariables;
        int length = typeVariableBindingArr.length;
        TypeVariableBinding[] typeVariableBindingArr2 = methodBinding.typeVariables;
        if (length != typeVariableBindingArr2.length) {
            return null;
        }
        ParameterizedGenericMethodBinding parameterizedGenericMethodBindingCreateParameterizedGenericMethod = lookupEnvironment.createParameterizedGenericMethod(methodBinding, typeVariableBindingArr);
        for (int i = 0; i < length; i++) {
            if (!this.typeVariables[i].isInterchangeableWith(typeVariableBindingArr2[i], parameterizedGenericMethodBindingCreateParameterizedGenericMethod)) {
                return null;
            }
        }
        return parameterizedGenericMethodBindingCreateParameterizedGenericMethod;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] computeUniqueKey(boolean z) {
        char[][] cArr;
        int length;
        char[] cArrComputeUniqueKey = this.declaringClass.computeUniqueKey(false);
        int length2 = cArrComputeUniqueKey.length;
        int length3 = this.selector == TypeConstants.INIT ? 0 : this.selector.length;
        char[] cArrGenericSignature = genericSignature();
        boolean z2 = cArrGenericSignature != null;
        if (!z2) {
            cArrGenericSignature = signature();
        }
        int length4 = cArrGenericSignature.length;
        int length5 = this.thrownExceptions.length;
        if (length5 <= 0 || (z2 && CharOperation.lastIndexOf(Util.C_EXCEPTION_START, cArrGenericSignature) >= 0)) {
            cArr = null;
            length = 0;
        } else {
            cArr = new char[length5][];
            length = 0;
            for (int i = 0; i < length5; i++) {
                ReferenceBinding referenceBinding = this.thrownExceptions[i];
                if (referenceBinding != null) {
                    char[] cArrSignature = referenceBinding.signature();
                    cArr[i] = cArrSignature;
                    length += cArrSignature.length + 1;
                }
            }
        }
        int i2 = length2 + 1;
        int i3 = i2 + length3;
        int i4 = i3 + length4;
        char[] cArr2 = new char[i4 + length];
        System.arraycopy(cArrComputeUniqueKey, 0, cArr2, 0, length2);
        cArr2[length2] = '.';
        System.arraycopy(this.selector, 0, cArr2, i2, length3);
        System.arraycopy(cArrGenericSignature, 0, cArr2, i3, length4);
        if (length > 0) {
            for (int i5 = 0; i5 < length5; i5++) {
                char[] cArr3 = cArr[i5];
                if (cArr3 != null) {
                    int i6 = i4 + 1;
                    cArr2[i4] = '|';
                    int length6 = cArr3.length;
                    System.arraycopy(cArr3, 0, cArr2, i6, length6);
                    i4 = i6 + length6;
                }
            }
        }
        return cArr2;
    }

    public final char[] constantPoolName() {
        return this.selector;
    }

    protected void fillInDefaultNonNullness(AbstractMethodDeclaration abstractMethodDeclaration) {
        if (this.parameterNonNullness == null) {
            this.parameterNonNullness = new Boolean[this.parameters.length];
        }
        int length = this.parameterNonNullness.length;
        boolean z = false;
        for (int i = 0; i < length; i++) {
            if (!this.parameters[i].isBaseType()) {
                Boolean[] boolArr = this.parameterNonNullness;
                Boolean bool = boolArr[i];
                if (bool == null) {
                    boolArr[i] = Boolean.TRUE;
                    if (abstractMethodDeclaration != null) {
                        LocalVariableBinding localVariableBinding = abstractMethodDeclaration.arguments[i].binding;
                        localVariableBinding.tagBits = 72057594037927936L | localVariableBinding.tagBits;
                    }
                    z = true;
                } else if (abstractMethodDeclaration != null && bool.booleanValue()) {
                    abstractMethodDeclaration.scope.problemReporter().nullAnnotationIsRedundant(abstractMethodDeclaration, i);
                }
            }
        }
        if (z) {
            this.tagBits |= 1024;
        }
        TypeBinding typeBinding = this.returnType;
        if (typeBinding != null && !typeBinding.isBaseType()) {
            long j = this.tagBits;
            if ((TagBits.AnnotationNullMASK & j) == 0) {
                this.tagBits = j | 72057594037927936L;
                return;
            }
        }
        if (abstractMethodDeclaration == null || (72057594037927936L & this.tagBits) == 0) {
            return;
        }
        abstractMethodDeclaration.scope.problemReporter().nullAnnotationIsRedundant(abstractMethodDeclaration, -1);
    }

    protected void fillInDefaultNonNullness18(AbstractMethodDeclaration abstractMethodDeclaration, LookupEnvironment lookupEnvironment) {
        if (hasNonNullDefaultFor(8, true)) {
            int length = this.parameters.length;
            boolean z = false;
            for (int i = 0; i < length; i++) {
                TypeBinding typeBinding = this.parameters[i];
                if (typeBinding.acceptsNonNullDefault()) {
                    if ((typeBinding.tagBits & TagBits.AnnotationNullMASK) == 0) {
                        if (!typeBinding.isBaseType()) {
                            this.parameters[i] = lookupEnvironment.createAnnotatedType(typeBinding, new AnnotationBinding[]{lookupEnvironment.getNonNullAnnotation()});
                            if (abstractMethodDeclaration != null) {
                                abstractMethodDeclaration.arguments[i].binding.type = this.parameters[i];
                            }
                        }
                        z = true;
                    } else if (abstractMethodDeclaration != null && (typeBinding.tagBits & 72057594037927936L) != 0 && abstractMethodDeclaration.arguments[i].hasNullTypeAnnotation(TypeReference.AnnotationPosition.MAIN_TYPE)) {
                        abstractMethodDeclaration.scope.problemReporter().nullAnnotationIsRedundant(abstractMethodDeclaration, i);
                    }
                }
            }
            if (z) {
                this.tagBits |= 1024;
            }
        }
        if (this.returnType != null && hasNonNullDefaultFor(16, true) && this.returnType.acceptsNonNullDefault()) {
            if ((this.returnType.tagBits & TagBits.AnnotationNullMASK) == 0) {
                this.returnType = lookupEnvironment.createAnnotatedType(this.returnType, new AnnotationBinding[]{lookupEnvironment.getNonNullAnnotation()});
            } else if ((abstractMethodDeclaration instanceof MethodDeclaration) && (this.returnType.tagBits & 72057594037927936L) != 0 && ((MethodDeclaration) abstractMethodDeclaration).hasNullTypeAnnotation(TypeReference.AnnotationPosition.MAIN_TYPE)) {
                abstractMethodDeclaration.scope.problemReporter().nullAnnotationIsRedundant(abstractMethodDeclaration, -1);
            }
        }
    }

    public MethodBinding findOriginalInheritedMethod(MethodBinding methodBinding) {
        MethodBinding methodBindingOriginal = methodBinding.original();
        TypeBinding typeBindingFindSuperTypeOriginatingFrom = this.declaringClass.findSuperTypeOriginatingFrom(methodBindingOriginal.declaringClass);
        if (typeBindingFindSuperTypeOriginatingFrom == null || !(typeBindingFindSuperTypeOriginatingFrom instanceof ReferenceBinding)) {
            return null;
        }
        if (TypeBinding.notEquals(methodBindingOriginal.declaringClass, typeBindingFindSuperTypeOriginatingFrom)) {
            MethodBinding[] methods = ((ReferenceBinding) typeBindingFindSuperTypeOriginatingFrom).getMethods(methodBindingOriginal.selector, methodBindingOriginal.parameters.length);
            int length = methods.length;
            for (int i = 0; i < length; i++) {
                if (methods[i].original() == methodBindingOriginal) {
                    return methods[i];
                }
            }
        }
        return methodBindingOriginal;
    }

    public char[] genericSignature() {
        if ((this.modifiers & 1073741824) == 0) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer(10);
        if (this.typeVariables != Binding.NO_TYPE_VARIABLES) {
            stringBuffer.append('<');
            int length = this.typeVariables.length;
            for (int i = 0; i < length; i++) {
                stringBuffer.append(this.typeVariables[i].genericSignature());
            }
            stringBuffer.append('>');
        }
        stringBuffer.append('(');
        int length2 = this.parameters.length;
        for (int i2 = 0; i2 < length2; i2++) {
            stringBuffer.append(this.parameters[i2].genericTypeSignature());
        }
        stringBuffer.append(')');
        TypeBinding typeBinding = this.returnType;
        if (typeBinding != null) {
            stringBuffer.append(typeBinding.genericTypeSignature());
        }
        int length3 = this.thrownExceptions.length;
        for (int i3 = 0; i3 < length3; i3++) {
            if ((this.thrownExceptions[i3].modifiers & 1073741824) != 0) {
                for (int i4 = 0; i4 < length3; i4++) {
                    stringBuffer.append(Util.C_EXCEPTION_START);
                    stringBuffer.append(this.thrownExceptions[i4].genericTypeSignature());
                }
                break;
            }
        }
        int length4 = stringBuffer.length();
        char[] cArr = new char[length4];
        stringBuffer.getChars(0, length4, cArr, 0);
        return cArr;
    }

    public final int getAccessFlags() {
        return this.modifiers & Lua.MAXARG_sBx;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public AnnotationBinding[] getAnnotations() {
        MethodBinding methodBindingOriginal = original();
        return methodBindingOriginal.declaringClass.retrieveAnnotations(methodBindingOriginal);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public long getAnnotationTagBits() {
        ClassScope classScope;
        long j;
        MethodBinding methodBindingOriginal = original();
        if ((methodBindingOriginal.tagBits & 8589934592L) == 0) {
            ReferenceBinding referenceBinding = methodBindingOriginal.declaringClass;
            if ((referenceBinding instanceof SourceTypeBinding) && (classScope = ((SourceTypeBinding) referenceBinding).scope) != null) {
                AbstractMethodDeclaration abstractMethodDeclarationDeclarationOf = classScope.referenceContext.declarationOf(methodBindingOriginal);
                if (abstractMethodDeclarationDeclarationOf != null) {
                    ASTNode.resolveAnnotations(abstractMethodDeclarationDeclarationOf.scope, abstractMethodDeclarationDeclarationOf.annotations, methodBindingOriginal);
                }
                if (classScope.compilerOptions().isAnnotationBasedNullAnalysisEnabled) {
                    boolean zUsesNullTypeAnnotations = classScope.environment().usesNullTypeAnnotations();
                    if (zUsesNullTypeAnnotations) {
                        j = this.defaultNullness;
                    } else {
                        j = this.tagBits & 432345564227567616L;
                    }
                    long j2 = j;
                    if (j2 != 0) {
                        ReferenceBinding referenceBinding2 = this.declaringClass;
                        if (referenceBinding2 instanceof SourceTypeBinding) {
                            SourceTypeBinding sourceTypeBinding = (SourceTypeBinding) referenceBinding2;
                            if (sourceTypeBinding.checkRedundantNullnessDefaultOne(abstractMethodDeclarationDeclarationOf, abstractMethodDeclarationDeclarationOf.annotations, j2, zUsesNullTypeAnnotations)) {
                                sourceTypeBinding.checkRedundantNullnessDefaultRecurse(abstractMethodDeclarationDeclarationOf, abstractMethodDeclarationDeclarationOf.annotations, j2, zUsesNullTypeAnnotations);
                            }
                        }
                    }
                }
            }
        }
        return methodBindingOriginal.tagBits;
    }

    public Object getDefaultValue() {
        AbstractMethodDeclaration abstractMethodDeclarationSourceMethod;
        MethodBinding methodBindingOriginal = original();
        if ((methodBindingOriginal.tagBits & 576460752303423488L) == 0) {
            ReferenceBinding referenceBinding = methodBindingOriginal.declaringClass;
            if (referenceBinding instanceof SourceTypeBinding) {
                SourceTypeBinding sourceTypeBinding = (SourceTypeBinding) referenceBinding;
                if (sourceTypeBinding.scope != null && (abstractMethodDeclarationSourceMethod = methodBindingOriginal.sourceMethod()) != null && abstractMethodDeclarationSourceMethod.isAnnotationMethod()) {
                    abstractMethodDeclarationSourceMethod.resolve(sourceTypeBinding.scope);
                }
            }
            methodBindingOriginal.tagBits |= 576460752303423488L;
        }
        AnnotationHolder annotationHolderRetrieveAnnotationHolder = methodBindingOriginal.declaringClass.retrieveAnnotationHolder(methodBindingOriginal, true);
        if (annotationHolderRetrieveAnnotationHolder == null) {
            return null;
        }
        return annotationHolderRetrieveAnnotationHolder.getDefaultValue();
    }

    public AnnotationBinding[][] getParameterAnnotations() {
        int length = this.parameters.length;
        if (length == 0) {
            return null;
        }
        MethodBinding methodBindingOriginal = original();
        AnnotationHolder annotationHolderRetrieveAnnotationHolder = methodBindingOriginal.declaringClass.retrieveAnnotationHolder(methodBindingOriginal, true);
        AnnotationBinding[][] parameterAnnotations = annotationHolderRetrieveAnnotationHolder != null ? annotationHolderRetrieveAnnotationHolder.getParameterAnnotations() : null;
        if (parameterAnnotations != null || (this.tagBits & 1024) == 0) {
            return parameterAnnotations;
        }
        AnnotationBinding[][] annotationBindingArr = new AnnotationBinding[length][];
        ReferenceBinding referenceBinding = this.declaringClass;
        int i = 0;
        if (referenceBinding instanceof SourceTypeBinding) {
            SourceTypeBinding sourceTypeBinding = (SourceTypeBinding) referenceBinding;
            if (sourceTypeBinding.scope != null) {
                AbstractMethodDeclaration abstractMethodDeclarationDeclarationOf = sourceTypeBinding.scope.referenceType().declarationOf(methodBindingOriginal);
                while (i < length) {
                    Argument argument = abstractMethodDeclarationDeclarationOf.arguments[i];
                    if (argument.annotations != null) {
                        ASTNode.resolveAnnotations(abstractMethodDeclarationDeclarationOf.scope, argument.annotations, argument.binding);
                        annotationBindingArr[i] = argument.binding.getAnnotations();
                    } else {
                        annotationBindingArr[i] = Binding.NO_ANNOTATIONS;
                    }
                    i++;
                }
            } else {
                while (i < length) {
                    annotationBindingArr[i] = Binding.NO_ANNOTATIONS;
                    i++;
                }
            }
        } else {
            while (i < length) {
                annotationBindingArr[i] = Binding.NO_ANNOTATIONS;
                i++;
            }
        }
        setParameterAnnotations(annotationBindingArr);
        return annotationBindingArr;
    }

    public TypeVariableBinding getTypeVariable(char[] cArr) {
        int length = this.typeVariables.length;
        do {
            length--;
            if (length < 0) {
                return null;
            }
        } while (!CharOperation.equals(this.typeVariables[length].sourceName, cArr));
        return this.typeVariables[length];
    }

    public TypeVariableBinding[] getAllTypeVariables(boolean z) {
        TypeVariableBinding[] typeVariableBindingArr = this.typeVariables;
        if (!z) {
            return typeVariableBindingArr;
        }
        TypeVariableBinding[] typeVariableBindingArrTypeVariables = this.declaringClass.typeVariables();
        int length = typeVariableBindingArr.length;
        int length2 = typeVariableBindingArrTypeVariables.length;
        if (length == 0) {
            return typeVariableBindingArrTypeVariables;
        }
        if (length2 == 0) {
            return typeVariableBindingArr;
        }
        TypeVariableBinding[] typeVariableBindingArr2 = new TypeVariableBinding[length + length2];
        System.arraycopy(typeVariableBindingArr, 0, typeVariableBindingArr2, 0, length);
        System.arraycopy(typeVariableBindingArrTypeVariables, 0, typeVariableBindingArr2, length, length2);
        return typeVariableBindingArr2;
    }

    public final boolean isAbstract() {
        return (this.modifiers & 1024) != 0;
    }

    public final boolean isBridge() {
        return (this.modifiers & 64) != 0;
    }

    public final boolean isConstructor() {
        return this.selector == TypeConstants.INIT;
    }

    public final boolean isDefault() {
        return (isPublic() || isProtected() || isPrivate()) ? false : true;
    }

    public final boolean isDefaultAbstract() {
        return (this.modifiers & 524288) != 0;
    }

    public boolean isDefaultMethod() {
        return (this.modifiers & 65536) != 0;
    }

    public final boolean isDeprecated() {
        return (this.modifiers & 1048576) != 0;
    }

    public final boolean isFinal() {
        return (this.modifiers & 16) != 0;
    }

    public final boolean isImplementing() {
        return (this.modifiers & 536870912) != 0;
    }

    public final boolean isMain() {
        char[] cArr = this.selector;
        if (cArr.length == 4 && CharOperation.equals(cArr, TypeConstants.MAIN) && (this.modifiers & 9) != 0 && TypeBinding.VOID == this.returnType) {
            TypeBinding[] typeBindingArr = this.parameters;
            if (typeBindingArr.length == 1) {
                TypeBinding typeBinding = typeBindingArr[0];
                if (typeBinding.dimensions() == 1 && typeBinding.leafComponentType().f626id == 11) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean isNative() {
        return (this.modifiers & 256) != 0;
    }

    public final boolean isOverriding() {
        return (this.modifiers & 268435456) != 0;
    }

    public final boolean isPrivate() {
        return (this.modifiers & 2) != 0;
    }

    public final boolean isOrEnclosedByPrivateType() {
        if ((this.modifiers & 2) != 0) {
            return true;
        }
        ReferenceBinding referenceBinding = this.declaringClass;
        return referenceBinding != null && referenceBinding.isOrEnclosedByPrivateType();
    }

    public final boolean isProtected() {
        return (this.modifiers & 4) != 0;
    }

    public final boolean isPublic() {
        return (this.modifiers & 1) != 0;
    }

    public final boolean isStatic() {
        return (this.modifiers & 8) != 0;
    }

    public final boolean isStrictfp() {
        return (this.modifiers & 2048) != 0;
    }

    public final boolean isSynchronized() {
        return (this.modifiers & 32) != 0;
    }

    public final boolean isSynthetic() {
        return (this.modifiers & 4096) != 0;
    }

    public final boolean isUsed() {
        return (this.modifiers & 134217728) != 0;
    }

    public boolean isVarargs() {
        return (this.modifiers & 128) != 0;
    }

    public final boolean isViewedAsDeprecated() {
        return (this.modifiers & 3145728) != 0;
    }

    public MethodBinding shallowOriginal() {
        return original();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] readableName() {
        StringBuffer stringBuffer = new StringBuffer(this.parameters.length + 20);
        if (isConstructor()) {
            stringBuffer.append(this.declaringClass.sourceName());
        } else {
            stringBuffer.append(this.selector);
        }
        stringBuffer.append('(');
        if (this.parameters != Binding.NO_PARAMETERS) {
            int length = this.parameters.length;
            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    stringBuffer.append(", ");
                }
                stringBuffer.append(this.parameters[i].sourceName());
            }
        }
        stringBuffer.append(')');
        return stringBuffer.toString().toCharArray();
    }

    public final AnnotationBinding[] getTypeAnnotations() {
        return this.typeAnnotations;
    }

    public void setTypeAnnotations(AnnotationBinding[] annotationBindingArr) {
        this.typeAnnotations = annotationBindingArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public void setAnnotations(AnnotationBinding[] annotationBindingArr) {
        this.declaringClass.storeAnnotations(this, annotationBindingArr);
    }

    public void setAnnotations(AnnotationBinding[] annotationBindingArr, AnnotationBinding[][] annotationBindingArr2, Object obj, LookupEnvironment lookupEnvironment) {
        this.declaringClass.storeAnnotationHolder(this, AnnotationHolder.storeAnnotations(annotationBindingArr, annotationBindingArr2, obj, lookupEnvironment));
    }

    public void setDefaultValue(Object obj) {
        original().tagBits |= 576460752303423488L;
        AnnotationHolder annotationHolderRetrieveAnnotationHolder = this.declaringClass.retrieveAnnotationHolder(this, false);
        if (annotationHolderRetrieveAnnotationHolder == null) {
            setAnnotations(null, null, obj, null);
        } else {
            setAnnotations(annotationHolderRetrieveAnnotationHolder.getAnnotations(), annotationHolderRetrieveAnnotationHolder.getParameterAnnotations(), obj, null);
        }
    }

    public void setParameterAnnotations(AnnotationBinding[][] annotationBindingArr) {
        AnnotationHolder annotationHolderRetrieveAnnotationHolder = this.declaringClass.retrieveAnnotationHolder(this, false);
        if (annotationHolderRetrieveAnnotationHolder == null) {
            setAnnotations(null, annotationBindingArr, null, null);
        } else {
            setAnnotations(annotationHolderRetrieveAnnotationHolder.getAnnotations(), annotationBindingArr, annotationHolderRetrieveAnnotationHolder.getDefaultValue(), null);
        }
    }

    protected final void setSelector(char[] cArr) {
        this.selector = cArr;
        this.signature = null;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] shortReadableName() {
        StringBuffer stringBuffer = new StringBuffer(this.parameters.length + 20);
        if (isConstructor()) {
            stringBuffer.append(this.declaringClass.shortReadableName());
        } else {
            stringBuffer.append(this.selector);
        }
        stringBuffer.append('(');
        if (this.parameters != Binding.NO_PARAMETERS) {
            int length = this.parameters.length;
            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    stringBuffer.append(", ");
                }
                stringBuffer.append(this.parameters[i].shortReadableName());
            }
        }
        stringBuffer.append(')');
        int length2 = stringBuffer.length();
        char[] cArr = new char[length2];
        stringBuffer.getChars(0, length2, cArr, 0);
        return cArr;
    }

    public final char[] signature() {
        char[] cArr = this.signature;
        if (cArr != null) {
            return cArr;
        }
        StringBuffer stringBuffer = new StringBuffer(this.parameters.length + 20);
        stringBuffer.append('(');
        TypeBinding[] typeBindingArr = this.parameters;
        boolean zIsConstructor = isConstructor();
        if (zIsConstructor && this.declaringClass.isEnum()) {
            stringBuffer.append(ConstantPool.JavaLangStringSignature);
            stringBuffer.append(TypeBinding.INT.signature());
        }
        boolean z = zIsConstructor && this.declaringClass.isNestedType();
        if (z) {
            ReferenceBinding[] referenceBindingArrSyntheticEnclosingInstanceTypes = this.declaringClass.syntheticEnclosingInstanceTypes();
            if (referenceBindingArrSyntheticEnclosingInstanceTypes != null) {
                for (ReferenceBinding referenceBinding : referenceBindingArrSyntheticEnclosingInstanceTypes) {
                    stringBuffer.append(referenceBinding.signature());
                }
            }
            if (this instanceof SyntheticMethodBinding) {
                typeBindingArr = ((SyntheticMethodBinding) this).targetMethod.parameters;
            }
        }
        if (typeBindingArr != Binding.NO_PARAMETERS) {
            for (TypeBinding typeBinding : typeBindingArr) {
                stringBuffer.append(typeBinding.signature());
            }
        }
        if (z) {
            SyntheticArgumentBinding[] syntheticArgumentBindingArrSyntheticOuterLocalVariables = this.declaringClass.syntheticOuterLocalVariables();
            int length = syntheticArgumentBindingArrSyntheticOuterLocalVariables == null ? 0 : syntheticArgumentBindingArrSyntheticOuterLocalVariables.length;
            for (int i = 0; i < length; i++) {
                stringBuffer.append(syntheticArgumentBindingArrSyntheticOuterLocalVariables[i].type.signature());
            }
            int length2 = this.parameters.length;
            for (int length3 = typeBindingArr.length; length3 < length2; length3++) {
                stringBuffer.append(this.parameters[length3].signature());
            }
        }
        stringBuffer.append(')');
        TypeBinding typeBinding2 = this.returnType;
        if (typeBinding2 != null) {
            stringBuffer.append(typeBinding2.signature());
        }
        int length4 = stringBuffer.length();
        char[] cArr2 = new char[length4];
        this.signature = cArr2;
        stringBuffer.getChars(0, length4, cArr2, 0);
        return this.signature;
    }

    public final char[] signature(ClassFile classFile) {
        boolean z;
        if (this.signature != null) {
            if ((this.tagBits & 2048) != 0) {
                boolean zIsConstructor = isConstructor();
                TypeBinding[] typeBindingArr = this.parameters;
                z = zIsConstructor && this.declaringClass.isNestedType();
                if (z) {
                    ReferenceBinding[] referenceBindingArrSyntheticEnclosingInstanceTypes = this.declaringClass.syntheticEnclosingInstanceTypes();
                    if (referenceBindingArrSyntheticEnclosingInstanceTypes != null) {
                        for (ReferenceBinding referenceBinding : referenceBindingArrSyntheticEnclosingInstanceTypes) {
                            if ((referenceBinding.tagBits & 2048) != 0) {
                                Util.recordNestedType(classFile, referenceBinding);
                            }
                        }
                    }
                    if (this instanceof SyntheticMethodBinding) {
                        typeBindingArr = ((SyntheticMethodBinding) this).targetMethod.parameters;
                    }
                }
                if (typeBindingArr != Binding.NO_PARAMETERS) {
                    for (TypeBinding typeBinding : typeBindingArr) {
                        TypeBinding typeBindingLeafComponentType = typeBinding.leafComponentType();
                        if ((typeBindingLeafComponentType.tagBits & 2048) != 0) {
                            Util.recordNestedType(classFile, typeBindingLeafComponentType);
                        }
                    }
                }
                if (z) {
                    int length = this.parameters.length;
                    for (int length2 = typeBindingArr.length; length2 < length; length2++) {
                        TypeBinding typeBindingLeafComponentType2 = this.parameters[length2].leafComponentType();
                        if ((typeBindingLeafComponentType2.tagBits & 2048) != 0) {
                            Util.recordNestedType(classFile, typeBindingLeafComponentType2);
                        }
                    }
                }
                TypeBinding typeBinding2 = this.returnType;
                if (typeBinding2 != null) {
                    TypeBinding typeBindingLeafComponentType3 = typeBinding2.leafComponentType();
                    if ((typeBindingLeafComponentType3.tagBits & 2048) != 0) {
                        Util.recordNestedType(classFile, typeBindingLeafComponentType3);
                    }
                }
            }
            return this.signature;
        }
        StringBuffer stringBuffer = new StringBuffer((this.parameters.length + 1) * 20);
        stringBuffer.append('(');
        TypeBinding[] typeBindingArr2 = this.parameters;
        boolean zIsConstructor2 = isConstructor();
        if (zIsConstructor2 && this.declaringClass.isEnum()) {
            stringBuffer.append(ConstantPool.JavaLangStringSignature);
            stringBuffer.append(TypeBinding.INT.signature());
        }
        z = zIsConstructor2 && this.declaringClass.isNestedType();
        if (z) {
            ReferenceBinding[] referenceBindingArrSyntheticEnclosingInstanceTypes2 = this.declaringClass.syntheticEnclosingInstanceTypes();
            if (referenceBindingArrSyntheticEnclosingInstanceTypes2 != null) {
                for (ReferenceBinding referenceBinding2 : referenceBindingArrSyntheticEnclosingInstanceTypes2) {
                    if ((referenceBinding2.tagBits & 2048) != 0) {
                        this.tagBits |= 2048;
                        Util.recordNestedType(classFile, referenceBinding2);
                    }
                    stringBuffer.append(referenceBinding2.signature());
                }
            }
            if (this instanceof SyntheticMethodBinding) {
                typeBindingArr2 = ((SyntheticMethodBinding) this).targetMethod.parameters;
            }
        }
        if (typeBindingArr2 != Binding.NO_PARAMETERS) {
            for (TypeBinding typeBinding3 : typeBindingArr2) {
                TypeBinding typeBindingLeafComponentType4 = typeBinding3.leafComponentType();
                if ((typeBindingLeafComponentType4.tagBits & 2048) != 0) {
                    this.tagBits |= 2048;
                    Util.recordNestedType(classFile, typeBindingLeafComponentType4);
                }
                stringBuffer.append(typeBinding3.signature());
            }
        }
        if (z) {
            SyntheticArgumentBinding[] syntheticArgumentBindingArrSyntheticOuterLocalVariables = this.declaringClass.syntheticOuterLocalVariables();
            int length3 = syntheticArgumentBindingArrSyntheticOuterLocalVariables == null ? 0 : syntheticArgumentBindingArrSyntheticOuterLocalVariables.length;
            for (int i = 0; i < length3; i++) {
                stringBuffer.append(syntheticArgumentBindingArrSyntheticOuterLocalVariables[i].type.signature());
            }
            int length4 = this.parameters.length;
            for (int length5 = typeBindingArr2.length; length5 < length4; length5++) {
                TypeBinding typeBinding4 = this.parameters[length5];
                TypeBinding typeBindingLeafComponentType5 = typeBinding4.leafComponentType();
                if ((typeBindingLeafComponentType5.tagBits & 2048) != 0) {
                    this.tagBits |= 2048;
                    Util.recordNestedType(classFile, typeBindingLeafComponentType5);
                }
                stringBuffer.append(typeBinding4.signature());
            }
        }
        stringBuffer.append(')');
        TypeBinding typeBinding5 = this.returnType;
        if (typeBinding5 != null) {
            TypeBinding typeBindingLeafComponentType6 = typeBinding5.leafComponentType();
            if ((typeBindingLeafComponentType6.tagBits & 2048) != 0) {
                this.tagBits |= 2048;
                Util.recordNestedType(classFile, typeBindingLeafComponentType6);
            }
            stringBuffer.append(this.returnType.signature());
        }
        int length6 = stringBuffer.length();
        char[] cArr = new char[length6];
        this.signature = cArr;
        stringBuffer.getChars(0, length6, cArr, 0);
        return this.signature;
    }

    public final int sourceEnd() {
        AbstractMethodDeclaration abstractMethodDeclarationSourceMethod = sourceMethod();
        if (abstractMethodDeclarationSourceMethod == null) {
            ReferenceBinding referenceBinding = this.declaringClass;
            if (referenceBinding instanceof SourceTypeBinding) {
                return ((SourceTypeBinding) referenceBinding).sourceEnd();
            }
            return 0;
        }
        return abstractMethodDeclarationSourceMethod.sourceEnd;
    }

    public AbstractMethodDeclaration sourceMethod() {
        if (isSynthetic()) {
            return null;
        }
        try {
            SourceTypeBinding sourceTypeBinding = (SourceTypeBinding) this.declaringClass;
            AbstractMethodDeclaration[] abstractMethodDeclarationArr = sourceTypeBinding.scope != null ? sourceTypeBinding.scope.referenceContext.methods : null;
            if (abstractMethodDeclarationArr != null) {
                int length = abstractMethodDeclarationArr.length;
                do {
                    length--;
                    if (length < 0) {
                    }
                } while (this != abstractMethodDeclarationArr[length].binding);
                return abstractMethodDeclarationArr[length];
            }
        } catch (ClassCastException unused) {
        }
        return null;
    }

    public final int sourceStart() {
        AbstractMethodDeclaration abstractMethodDeclarationSourceMethod = sourceMethod();
        if (abstractMethodDeclarationSourceMethod == null) {
            ReferenceBinding referenceBinding = this.declaringClass;
            if (referenceBinding instanceof SourceTypeBinding) {
                return ((SourceTypeBinding) referenceBinding).sourceStart();
            }
            return 0;
        }
        return abstractMethodDeclarationSourceMethod.sourceStart;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(10);
        if ((this.modifiers & 33554432) != 0) {
            stringBuffer.append("[unresolved] ");
        }
        ASTNode.printModifiers(this.modifiers, stringBuffer);
        TypeBinding typeBinding = this.returnType;
        stringBuffer.append(typeBinding != null ? typeBinding.debugName() : "<no type>");
        stringBuffer.append(" ");
        stringBuffer.append(this.selector != null ? new String(this.selector) : "<no selector>");
        stringBuffer.append("(");
        TypeBinding[] typeBindingArr = this.parameters;
        if (typeBindingArr != null) {
            if (typeBindingArr != Binding.NO_PARAMETERS) {
                int length = this.parameters.length;
                for (int i = 0; i < length; i++) {
                    if (i > 0) {
                        stringBuffer.append(", ");
                    }
                    TypeBinding typeBinding2 = this.parameters[i];
                    stringBuffer.append(typeBinding2 != null ? typeBinding2.debugName() : "<no argument type>");
                }
            }
        } else {
            stringBuffer.append("<no argument types>");
        }
        stringBuffer.append(") ");
        ReferenceBinding[] referenceBindingArr = this.thrownExceptions;
        if (referenceBindingArr != null) {
            if (referenceBindingArr != Binding.NO_EXCEPTIONS) {
                stringBuffer.append("throws ");
                int length2 = this.thrownExceptions.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    if (i2 > 0) {
                        stringBuffer.append(", ");
                    }
                    ReferenceBinding referenceBinding = this.thrownExceptions[i2];
                    stringBuffer.append(referenceBinding != null ? referenceBinding.debugName() : "<no exception type>");
                }
            }
        } else {
            stringBuffer.append("<no exception types>");
        }
        return stringBuffer.toString();
    }

    public TypeVariableBinding[] typeVariables() {
        return this.typeVariables;
    }

    public boolean hasNonNullDefaultFor(int i, boolean z) {
        if ((this.modifiers & 67108864) != 0) {
            return false;
        }
        if (z) {
            int i2 = this.defaultNullness;
            if (i2 != 0) {
                return (i & i2) != 0;
            }
        } else {
            long j = this.tagBits;
            if ((144115188075855872L & j) != 0) {
                return true;
            }
            if ((j & 288230376151711744L) != 0) {
                return false;
            }
        }
        return this.declaringClass.hasNonNullDefaultFor(i, z);
    }

    public boolean redeclaresPublicObjectMethod(Scope scope) {
        MethodBinding[] methods = scope.getJavaLangObject().getMethods(this.selector);
        int length = methods == null ? 0 : methods.length;
        for (int i = 0; i < length; i++) {
            MethodBinding methodBinding = methods[i];
            if (methodBinding.isPublic() && !methodBinding.isStatic() && methodBinding.parameters.length == this.parameters.length && MethodVerifier.doesMethodOverride(this, methodBinding, scope.environment())) {
                return true;
            }
        }
        return false;
    }

    public boolean isVoidMethod() {
        return this.returnType == TypeBinding.VOID;
    }
}
