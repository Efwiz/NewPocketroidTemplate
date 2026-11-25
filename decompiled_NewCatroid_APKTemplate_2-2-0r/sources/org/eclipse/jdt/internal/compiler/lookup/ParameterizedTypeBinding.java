package org.eclipse.jdt.internal.compiler.lookup;

import com.huawei.hms.framework.common.ContainerUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.core.compiler.InvalidInputException;
import org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.eclipse.jdt.internal.compiler.ast.NullAnnotationMatching;
import org.eclipse.jdt.internal.compiler.ast.ParameterizedSingleTypeReference;
import org.eclipse.jdt.internal.compiler.ast.TypeReference;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;

/* JADX INFO: loaded from: classes14.dex */
public class ParameterizedTypeBinding extends ReferenceBinding implements Substitution {
    public TypeBinding[] arguments;
    protected ReferenceBinding enclosingType;
    public LookupEnvironment environment;
    public FieldBinding[] fields;
    public char[] genericTypeSignature;
    public ReferenceBinding[] memberTypes;
    public MethodBinding[] methods;
    public ReferenceBinding[] superInterfaces;
    public ReferenceBinding superclass;
    protected ReferenceBinding type;

    protected void initializeArguments() {
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isParameterizedType() {
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public int kind() {
        return 260;
    }

    public ParameterizedTypeBinding(ReferenceBinding referenceBinding, TypeBinding[] typeBindingArr, ReferenceBinding referenceBinding2, LookupEnvironment lookupEnvironment) {
        this.environment = lookupEnvironment;
        this.enclosingType = referenceBinding2;
        initialize(referenceBinding, typeBindingArr);
        if (referenceBinding instanceof UnresolvedReferenceBinding) {
            ((UnresolvedReferenceBinding) referenceBinding).addWrapper(this, lookupEnvironment);
        }
        if (typeBindingArr != null) {
            int length = typeBindingArr.length;
            for (int i = 0; i < length; i++) {
                TypeBinding typeBinding = typeBindingArr[i];
                if (typeBinding instanceof UnresolvedReferenceBinding) {
                    ((UnresolvedReferenceBinding) typeBinding).addWrapper(this, lookupEnvironment);
                }
                if (typeBindingArr[i].hasNullTypeAnnotations()) {
                    this.tagBits |= 1048576;
                }
            }
        }
        if (referenceBinding2 != null && referenceBinding2.hasNullTypeAnnotations()) {
            this.tagBits |= 1048576;
        }
        this.tagBits |= TagBits.HasUnresolvedTypeVariables;
        this.typeBits = referenceBinding.typeBits;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public ReferenceBinding actualType() {
        return this.type;
    }

    public void boundCheck(Scope scope, TypeReference[] typeReferenceArr) {
        if ((this.tagBits & 4194304) == 0) {
            TypeVariableBinding[] typeVariableBindingArrTypeVariables = this.type.typeVariables();
            boolean z = false;
            if (this.arguments != null && typeVariableBindingArrTypeVariables != null) {
                int length = typeVariableBindingArrTypeVariables.length;
                boolean z2 = false;
                for (int i = 0; i < length; i++) {
                    TypeConstants.BoundCheckStatus boundCheckStatusBoundCheck = typeVariableBindingArrTypeVariables[i].boundCheck(this, this.arguments[i], scope, typeReferenceArr[i]);
                    z2 |= boundCheckStatusBoundCheck != TypeConstants.BoundCheckStatus.OK;
                    if (!boundCheckStatusBoundCheck.isOKbyJLS() && (this.arguments[i].tagBits & 128) == 0) {
                        scope.problemReporter().typeMismatchError(this.arguments[i], typeVariableBindingArrTypeVariables[i], this.type, typeReferenceArr[i]);
                    }
                }
                z = z2;
            }
            if (z) {
                return;
            }
            this.tagBits |= 4194304;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean canBeInstantiated() {
        return (this.tagBits & 1073741824) == 0 && super.canBeInstantiated();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public ParameterizedTypeBinding capture(Scope scope, int i, int i2) {
        int i3;
        if ((this.tagBits & 1073741824) == 0) {
            return this;
        }
        TypeBinding[] typeBindingArr = this.arguments;
        int length = typeBindingArr.length;
        TypeBinding[] typeBindingArr2 = new TypeBinding[length];
        ReferenceBinding referenceBindingEnclosingSourceType = scope.enclosingSourceType();
        if (referenceBindingEnclosingSourceType != null) {
            referenceBindingEnclosingSourceType = referenceBindingEnclosingSourceType.outermostEnclosingType();
        }
        CompilationUnitScope compilationUnitScope = scope.compilationUnitScope();
        CompilationUnitDeclaration compilationUnitDeclaration = compilationUnitScope.referenceContext;
        boolean z = this.environment.globalOptions.sourceLevel >= ClassFileConstants.JDK1_8;
        int i4 = 0;
        while (i4 < length) {
            TypeBinding typeBinding = typeBindingArr[i4];
            if (typeBinding.kind() == 516) {
                WildcardBinding wildcardBinding = (WildcardBinding) typeBinding;
                if (wildcardBinding.boundKind == 2 && wildcardBinding.bound.f626id == 1) {
                    typeBindingArr2[i4] = wildcardBinding.bound;
                    i3 = i4;
                } else if (z) {
                    i3 = i4;
                    typeBindingArr2[i3] = this.environment.createCapturedWildcard(wildcardBinding, referenceBindingEnclosingSourceType, i, i2, compilationUnitDeclaration, compilationUnitScope.nextCaptureID());
                } else {
                    i3 = i4;
                    typeBindingArr2[i3] = new CaptureBinding(wildcardBinding, referenceBindingEnclosingSourceType, i, i2, compilationUnitDeclaration, compilationUnitScope.nextCaptureID());
                }
            } else {
                i3 = i4;
                typeBindingArr2[i3] = typeBinding;
            }
            i4 = i3 + 1;
        }
        ParameterizedTypeBinding parameterizedTypeBindingCreateParameterizedType = this.environment.createParameterizedType(this.type, typeBindingArr2, enclosingType(), this.typeAnnotations);
        for (int i5 = 0; i5 < length; i5++) {
            TypeBinding typeBinding2 = typeBindingArr2[i5];
            if (typeBinding2.isCapture()) {
                ((CaptureBinding) typeBinding2).initializeBounds(scope, parameterizedTypeBindingCreateParameterizedType);
            }
        }
        return parameterizedTypeBindingCreateParameterizedType;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding uncapture(Scope scope) {
        if ((this.tagBits & 2305843009213693952L) == 0) {
            return this;
        }
        TypeBinding[] typeBindingArr = this.arguments;
        int length = typeBindingArr == null ? 0 : typeBindingArr.length;
        TypeBinding[] typeBindingArr2 = new TypeBinding[length];
        for (int i = 0; i < length; i++) {
            typeBindingArr2[i] = this.arguments[i].uncapture(scope);
        }
        LookupEnvironment lookupEnvironmentEnvironment = scope.environment();
        ReferenceBinding referenceBinding = this.type;
        ReferenceBinding referenceBinding2 = this.enclosingType;
        return lookupEnvironmentEnvironment.createParameterizedType(referenceBinding, typeBindingArr2, (ReferenceBinding) (referenceBinding2 != null ? referenceBinding2.uncapture(scope) : null), this.typeAnnotations);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public List<TypeBinding> collectMissingTypes(List<TypeBinding> list) {
        if ((this.tagBits & 128) != 0) {
            ReferenceBinding referenceBinding = this.enclosingType;
            if (referenceBinding != null) {
                list = referenceBinding.collectMissingTypes(list);
            }
            list = genericType().collectMissingTypes(list);
            TypeBinding[] typeBindingArr = this.arguments;
            if (typeBindingArr != null) {
                int length = typeBindingArr.length;
                for (int i = 0; i < length; i++) {
                    list = this.arguments[i].collectMissingTypes(list);
                }
            }
        }
        return list;
    }

    /* JADX WARN: Code duplicated, block: B:78:0x00de  */
    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public void collectSubstitutes(Scope scope, TypeBinding typeBinding, InferenceContext inferenceContext, int i) {
        TypeBinding typeBindingFindSuperTypeOriginatingFrom;
        TypeBinding[] typeBindingArrTypeVariables;
        TypeBinding[] typeBindingArrTypeVariables2;
        if ((this.tagBits & TagBits.HasTypeVariable) == 0) {
            TypeBinding typeBindingFindSuperTypeOriginatingFrom2 = typeBinding.findSuperTypeOriginatingFrom(this.type);
            if (typeBindingFindSuperTypeOriginatingFrom2 == null || !typeBindingFindSuperTypeOriginatingFrom2.isRawType()) {
                return;
            }
            inferenceContext.isUnchecked = true;
            return;
        }
        if (typeBinding == TypeBinding.NULL || typeBinding.kind() == 65540 || !(typeBinding instanceof ReferenceBinding)) {
            return;
        }
        if (i == 0 || i == 1) {
            typeBinding = typeBinding.findSuperTypeOriginatingFrom(this.type);
            if (typeBinding == null) {
                return;
            } else {
                typeBindingFindSuperTypeOriginatingFrom = this;
            }
        } else {
            typeBindingFindSuperTypeOriginatingFrom = findSuperTypeOriginatingFrom(typeBinding);
            if (typeBindingFindSuperTypeOriginatingFrom == null) {
                return;
            }
        }
        ReferenceBinding referenceBindingEnclosingType = typeBindingFindSuperTypeOriginatingFrom.enclosingType();
        if (referenceBindingEnclosingType != null) {
            referenceBindingEnclosingType.collectSubstitutes(scope, typeBinding.enclosingType(), inferenceContext, i);
        }
        if (this.arguments == null) {
            return;
        }
        int iKind = typeBindingFindSuperTypeOriginatingFrom.kind();
        if (iKind == 260) {
            typeBindingArrTypeVariables = ((ParameterizedTypeBinding) typeBindingFindSuperTypeOriginatingFrom).arguments;
        } else if (iKind == 1028) {
            if (inferenceContext.depth > 0) {
                inferenceContext.status = 1;
                return;
            }
            return;
        } else if (iKind != 2052) {
            return;
        } else {
            typeBindingArrTypeVariables = typeBindingFindSuperTypeOriginatingFrom.typeVariables();
        }
        int iKind2 = typeBinding.kind();
        if (iKind2 == 260) {
            typeBindingArrTypeVariables2 = ((ParameterizedTypeBinding) typeBinding).arguments;
        } else {
            if (iKind2 == 1028) {
                if (inferenceContext.depth > 0) {
                    inferenceContext.status = 1;
                    return;
                } else {
                    inferenceContext.isUnchecked = true;
                    return;
                }
            }
            if (iKind2 != 2052) {
                return;
            } else {
                typeBindingArrTypeVariables2 = typeBinding.typeVariables();
            }
        }
        if (typeBindingArrTypeVariables == null || typeBindingArrTypeVariables2 == null) {
            return;
        }
        inferenceContext.depth++;
        int length = typeBindingArrTypeVariables.length;
        for (int i2 = 0; i2 < length; i2++) {
            TypeBinding typeBinding2 = typeBindingArrTypeVariables[i2];
            TypeBinding typeBinding3 = typeBindingArrTypeVariables2[i2];
            if (typeBinding2.isWildcard()) {
                typeBinding2.collectSubstitutes(scope, typeBinding3, inferenceContext, i);
            } else if (typeBinding3.isWildcard()) {
                WildcardBinding wildcardBinding = (WildcardBinding) typeBinding3;
                if (wildcardBinding.otherBounds != null) {
                    typeBinding2.collectSubstitutes(scope, typeBinding3, inferenceContext, 0);
                } else if (i == 2) {
                    int i3 = wildcardBinding.boundKind;
                    if (i3 == 1) {
                        typeBinding2.collectSubstitutes(scope, wildcardBinding.bound, inferenceContext, 2);
                    } else if (i3 == 2) {
                        typeBinding2.collectSubstitutes(scope, wildcardBinding.bound, inferenceContext, 1);
                    }
                }
            } else {
                typeBinding2.collectSubstitutes(scope, typeBinding3, inferenceContext, 0);
            }
        }
        inferenceContext.depth--;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public void computeId() {
        this.f626id = Integer.MAX_VALUE;
    }

    /* JADX WARN: Code duplicated, block: B:11:0x003e  */
    /* JADX WARN: Code duplicated, block: B:15:0x0061 A[LOOP:1: B:12:0x0046->B:15:0x0061, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:16:0x0063  */
    /* JADX WARN: Code duplicated, block: B:36:0x004c A[EDGE_INSN: B:36:0x004c->B:14:0x004c BREAK  A[LOOP:1: B:12:0x0046->B:15:0x0061], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:9:0x0036  */
    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] computeUniqueKey(boolean z) {
        ReferenceBinding referenceBindingEnclosingType;
        ReferenceBinding referenceBindingEnclosingType2;
        StringBuffer stringBuffer = new StringBuffer(10);
        if (isMemberType()) {
            ReferenceBinding referenceBindingEnclosingType3 = enclosingType();
            if (referenceBindingEnclosingType3.isParameterizedType() || referenceBindingEnclosingType3.isRawType()) {
                char[] cArrComputeUniqueKey = referenceBindingEnclosingType3.computeUniqueKey(false);
                stringBuffer.append(cArrComputeUniqueKey, 0, cArrComputeUniqueKey.length - 1);
                stringBuffer.append('.').append(sourceName());
            } else if (this.type.isLocalType()) {
                LocalTypeBinding localTypeBinding = (LocalTypeBinding) this.type;
                referenceBindingEnclosingType = localTypeBinding.enclosingType();
                while (true) {
                    referenceBindingEnclosingType2 = referenceBindingEnclosingType.enclosingType();
                    if (referenceBindingEnclosingType2 == null) {
                        break;
                    }
                    referenceBindingEnclosingType = referenceBindingEnclosingType2;
                }
                char[] cArrComputeUniqueKey2 = referenceBindingEnclosingType.computeUniqueKey(false);
                stringBuffer.append(cArrComputeUniqueKey2, 0, cArrComputeUniqueKey2.length - 1);
                stringBuffer.append('$');
                stringBuffer.append(localTypeBinding.sourceStart);
            } else {
                char[] cArrComputeUniqueKey3 = this.type.computeUniqueKey(false);
                stringBuffer.append(cArrComputeUniqueKey3, 0, cArrComputeUniqueKey3.length - 1);
            }
        } else if (this.type.isLocalType()) {
            LocalTypeBinding localTypeBinding2 = (LocalTypeBinding) this.type;
            referenceBindingEnclosingType = localTypeBinding2.enclosingType();
            while (true) {
                referenceBindingEnclosingType2 = referenceBindingEnclosingType.enclosingType();
                if (referenceBindingEnclosingType2 == null) {
                    break;
                    break;
                }
                referenceBindingEnclosingType = referenceBindingEnclosingType2;
            }
            char[] cArrComputeUniqueKey4 = referenceBindingEnclosingType.computeUniqueKey(false);
            stringBuffer.append(cArrComputeUniqueKey4, 0, cArrComputeUniqueKey4.length - 1);
            stringBuffer.append('$');
            stringBuffer.append(localTypeBinding2.sourceStart);
        } else {
            char[] cArrComputeUniqueKey5 = this.type.computeUniqueKey(false);
            stringBuffer.append(cArrComputeUniqueKey5, 0, cArrComputeUniqueKey5.length - 1);
        }
        ReferenceBinding referenceBinding = null;
        if (this.arguments != null) {
            stringBuffer.append('<');
            int length = this.arguments.length;
            for (int i = 0; i < length; i++) {
                TypeBinding typeBinding = this.arguments[i];
                stringBuffer.append(typeBinding.computeUniqueKey(false));
                if (typeBinding instanceof CaptureBinding) {
                    referenceBinding = ((CaptureBinding) typeBinding).sourceType;
                }
            }
            stringBuffer.append('>');
        }
        stringBuffer.append(';');
        if (referenceBinding != null && TypeBinding.notEquals(referenceBinding, this.type)) {
            stringBuffer.insert(0, ContainerUtils.FIELD_DELIMITER);
            stringBuffer.insert(0, referenceBinding.computeUniqueKey(false));
        }
        int length2 = stringBuffer.length();
        char[] cArr = new char[length2];
        stringBuffer.getChars(0, length2, cArr, 0);
        return cArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] constantPoolName() {
        return this.type.constantPoolName();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding clone(TypeBinding typeBinding) {
        return new ParameterizedTypeBinding(this.type, this.arguments, (ReferenceBinding) typeBinding, this.environment);
    }

    public ParameterizedMethodBinding createParameterizedMethod(MethodBinding methodBinding) {
        return new ParameterizedMethodBinding(this, methodBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public String debugName() {
        if (hasTypeAnnotations()) {
            return annotatedDebugName();
        }
        StringBuffer stringBuffer = new StringBuffer(10);
        ReferenceBinding referenceBinding = this.type;
        if (referenceBinding instanceof UnresolvedReferenceBinding) {
            stringBuffer.append(referenceBinding);
        } else {
            stringBuffer.append(referenceBinding.sourceName());
        }
        TypeBinding[] typeBindingArr = this.arguments;
        if (typeBindingArr != null && typeBindingArr.length > 0) {
            stringBuffer.append('<');
            int length = this.arguments.length;
            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    stringBuffer.append(',');
                }
                stringBuffer.append(this.arguments[i].debugName());
            }
            stringBuffer.append('>');
        }
        return stringBuffer.toString();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public String annotatedDebugName() {
        StringBuffer stringBuffer = new StringBuffer(super.annotatedDebugName());
        TypeBinding[] typeBindingArr = this.arguments;
        if (typeBindingArr != null && typeBindingArr.length > 0) {
            stringBuffer.append('<');
            int length = this.arguments.length;
            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    stringBuffer.append(',');
                }
                stringBuffer.append(this.arguments[i].annotatedDebugName());
            }
            stringBuffer.append('>');
        }
        return stringBuffer.toString();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public ReferenceBinding enclosingType() {
        return this.enclosingType;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Substitution
    public LookupEnvironment environment() {
        return this.environment;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding erasure() {
        return this.type.erasure();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public int fieldCount() {
        return this.type.fieldCount();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public FieldBinding[] fields() {
        if ((this.tagBits & 8192) != 0) {
            return this.fields;
        }
        try {
            FieldBinding[] fieldBindingArrFields = this.type.fields();
            int length = fieldBindingArrFields.length;
            FieldBinding[] fieldBindingArr = new FieldBinding[length];
            for (int i = 0; i < length; i++) {
                fieldBindingArr[i] = new ParameterizedFieldBinding(this, fieldBindingArrFields[i]);
            }
            this.fields = fieldBindingArr;
            return this.fields;
        } finally {
            if (this.fields == null) {
                this.fields = Binding.NO_FIELDS;
            }
            this.tagBits |= 8192;
        }
    }

    public ReferenceBinding genericType() {
        ReferenceBinding referenceBinding = this.type;
        if (referenceBinding instanceof UnresolvedReferenceBinding) {
            ((UnresolvedReferenceBinding) referenceBinding).resolve(this.environment, false);
        }
        return this.type;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] genericTypeSignature() {
        if (this.genericTypeSignature == null) {
            if ((this.modifiers & 1073741824) == 0) {
                this.genericTypeSignature = this.type.signature();
            } else {
                StringBuffer stringBuffer = new StringBuffer(10);
                if (isMemberType()) {
                    ReferenceBinding referenceBindingEnclosingType = enclosingType();
                    char[] cArrGenericTypeSignature = referenceBindingEnclosingType.genericTypeSignature();
                    stringBuffer.append(cArrGenericTypeSignature, 0, cArrGenericTypeSignature.length - 1);
                    if ((1073741824 & referenceBindingEnclosingType.modifiers) != 0) {
                        stringBuffer.append('.');
                    } else {
                        stringBuffer.append('$');
                    }
                    stringBuffer.append(sourceName());
                } else {
                    char[] cArrSignature = this.type.signature();
                    stringBuffer.append(cArrSignature, 0, cArrSignature.length - 1);
                }
                if (this.arguments != null) {
                    stringBuffer.append('<');
                    int length = this.arguments.length;
                    for (int i = 0; i < length; i++) {
                        stringBuffer.append(this.arguments[i].genericTypeSignature());
                    }
                    stringBuffer.append('>');
                }
                stringBuffer.append(';');
                int length2 = stringBuffer.length();
                char[] cArr = new char[length2];
                this.genericTypeSignature = cArr;
                stringBuffer.getChars(0, length2, cArr, 0);
            }
        }
        return this.genericTypeSignature;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public long getAnnotationTagBits() {
        return this.type.getAnnotationTagBits();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public int getEnclosingInstancesSlotSize() {
        return genericType().getEnclosingInstancesSlotSize();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public MethodBinding getExactConstructor(TypeBinding[] typeBindingArr) throws Throwable {
        MethodBinding methodBinding;
        int length = typeBindingArr.length;
        if ((this.tagBits & 32768) != 0) {
            long jBinarySearch = ReferenceBinding.binarySearch(TypeConstants.INIT, this.methods);
            if (jBinarySearch < 0) {
                return null;
            }
            int i = (int) (jBinarySearch >> 32);
            methodBinding = null;
            for (int i2 = (int) jBinarySearch; i2 <= i; i2++) {
                MethodBinding methodBinding2 = this.methods[i2];
                if (methodBinding2.parameters.length == length) {
                    TypeBinding[] typeBindingArr2 = methodBinding2.parameters;
                    int i3 = 0;
                    while (true) {
                        if (i3 >= length) {
                            if (methodBinding == null) {
                                methodBinding = methodBinding2;
                                break;
                            }
                            return null;
                        }
                        if (TypeBinding.notEquals(typeBindingArr2[i3], typeBindingArr[i3])) {
                            break;
                        }
                        i3++;
                    }
                }
            }
        } else {
            MethodBinding[] methods = getMethods(TypeConstants.INIT);
            int length2 = methods.length;
            methodBinding = null;
            while (true) {
                length2--;
                if (length2 < 0) {
                    break;
                }
                MethodBinding methodBinding3 = methods[length2];
                TypeBinding[] typeBindingArr3 = methodBinding3.parameters;
                if (typeBindingArr3.length == length) {
                    int i4 = 0;
                    while (true) {
                        if (i4 >= length) {
                            if (methodBinding == null) {
                                methodBinding = methodBinding3;
                                break;
                            }
                            return null;
                        }
                        if (TypeBinding.notEquals(typeBindingArr3[i4], typeBindingArr[i4])) {
                            break;
                        }
                        i4++;
                    }
                }
            }
        }
        return methodBinding;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public MethodBinding getExactMethod(char[] cArr, TypeBinding[] typeBindingArr, CompilationUnitScope compilationUnitScope) throws Throwable {
        boolean z;
        MethodBinding methodBinding;
        TypeBinding[] typeBindingArr2;
        int length = typeBindingArr.length;
        int i = 0;
        if ((this.tagBits & 32768) != 0) {
            long jBinarySearch = ReferenceBinding.binarySearch(cArr, this.methods);
            if (jBinarySearch >= 0) {
                int i2 = (int) jBinarySearch;
                int i3 = (int) (jBinarySearch >> 32);
                z = true;
                methodBinding = null;
                while (i2 <= i3) {
                    MethodBinding methodBinding2 = this.methods[i2];
                    if (methodBinding2.parameters.length == length) {
                        TypeBinding[] typeBindingArr3 = methodBinding2.parameters;
                        int i4 = 0;
                        while (true) {
                            if (i4 >= length) {
                                if (methodBinding == null) {
                                    methodBinding = methodBinding2;
                                    break;
                                }
                                return null;
                            }
                            if (TypeBinding.notEquals(typeBindingArr3[i4], typeBindingArr[i4])) {
                                break;
                            }
                            i4++;
                        }
                    }
                    i2++;
                    z = false;
                }
            } else {
                z = true;
                methodBinding = null;
            }
        } else {
            MethodBinding[] methods = getMethods(cArr);
            z = methods == Binding.NO_METHODS;
            int length2 = methods.length;
            methodBinding = null;
            while (true) {
                length2--;
                if (length2 < 0) {
                    break;
                }
                MethodBinding methodBinding3 = methods[length2];
                TypeBinding[] typeBindingArr4 = methodBinding3.parameters;
                if (typeBindingArr4.length == length) {
                    int i5 = i;
                    while (true) {
                        if (i5 >= length) {
                            if (methodBinding == null) {
                                methodBinding = methodBinding3;
                                break;
                            }
                            return null;
                        }
                        if (!TypeBinding.notEquals(typeBindingArr4[i5], typeBindingArr[i5])) {
                            i5++;
                            i = 0;
                        }
                    }
                }
                i = 0;
            }
        }
        if (methodBinding != null) {
            if (methodBinding.hasSubstitutedParameters()) {
                return null;
            }
            return methodBinding;
        }
        if (z && ((typeBindingArr2 = this.arguments) == null || typeBindingArr2.length <= 1)) {
            if (isInterface()) {
                if (superInterfaces().length == 1) {
                    if (compilationUnitScope != null) {
                        compilationUnitScope.recordTypeReference(this.superInterfaces[i]);
                    }
                    return this.superInterfaces[i].getExactMethod(cArr, typeBindingArr, compilationUnitScope);
                }
            } else if (superclass() != null) {
                if (compilationUnitScope != null) {
                    compilationUnitScope.recordTypeReference(this.superclass);
                }
                return this.superclass.getExactMethod(cArr, typeBindingArr, compilationUnitScope);
            }
        }
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public FieldBinding getField(char[] cArr, boolean z) {
        fields();
        return ReferenceBinding.binarySearch(cArr, this.fields);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public ReferenceBinding getMemberType(char[] cArr) {
        memberTypes();
        int length = cArr.length;
        int length2 = this.memberTypes.length;
        while (true) {
            length2--;
            if (length2 < 0) {
                return null;
            }
            ReferenceBinding referenceBinding = this.memberTypes[length2];
            if (referenceBinding.sourceName.length == length && CharOperation.equals(referenceBinding.sourceName, cArr)) {
                return referenceBinding;
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public MethodBinding[] getMethods(char[] cArr) throws Throwable {
        MethodBinding[] methodBindingArr = this.methods;
        if (methodBindingArr != null) {
            long jBinarySearch = ReferenceBinding.binarySearch(cArr, methodBindingArr);
            if (jBinarySearch >= 0) {
                int i = (int) jBinarySearch;
                int i2 = (((int) (jBinarySearch >> 32)) - i) + 1;
                MethodBinding[] methodBindingArr2 = new MethodBinding[i2];
                System.arraycopy(this.methods, i, methodBindingArr2, 0, i2);
                return methodBindingArr2;
            }
        }
        if ((this.tagBits & 32768) != 0) {
            return Binding.NO_METHODS;
        }
        MethodBinding[] methodBindingArr3 = null;
        try {
            MethodBinding[] methods = this.type.getMethods(cArr);
            int length = methods.length;
            if (length == 0) {
                MethodBinding[] methodBindingArr4 = Binding.NO_METHODS;
                this.methods = Binding.NO_METHODS;
                return methodBindingArr4;
            }
            MethodBinding[] methodBindingArr5 = new MethodBinding[length];
            try {
                boolean zUsesNullTypeAnnotations = this.environment.usesNullTypeAnnotations();
                for (int i3 = 0; i3 < length; i3++) {
                    methodBindingArr5[i3] = createParameterizedMethod(methods[i3]);
                    if (zUsesNullTypeAnnotations) {
                        methodBindingArr5[i3] = NullAnnotationMatching.checkForContradictions(methodBindingArr5[i3], null, null);
                    }
                }
                MethodBinding[] methodBindingArr6 = this.methods;
                if (methodBindingArr6 == null) {
                    MethodBinding[] methodBindingArr7 = new MethodBinding[length];
                    System.arraycopy(methodBindingArr5, 0, methodBindingArr7, 0, length);
                    this.methods = methodBindingArr7;
                } else {
                    int length2 = methodBindingArr6.length + length;
                    MethodBinding[] methodBindingArr8 = new MethodBinding[length2];
                    System.arraycopy(methodBindingArr5, 0, methodBindingArr8, 0, length);
                    MethodBinding[] methodBindingArr9 = this.methods;
                    System.arraycopy(methodBindingArr9, 0, methodBindingArr8, length, methodBindingArr9.length);
                    if (length2 > 1) {
                        ReferenceBinding.sortMethods(methodBindingArr8, 0, length2);
                    }
                    this.methods = methodBindingArr8;
                }
                return methodBindingArr5;
            } catch (Throwable th) {
                th = th;
                methodBindingArr3 = methodBindingArr5;
                if (methodBindingArr3 == null) {
                    this.methods = Binding.NO_METHODS;
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public int getOuterLocalVariablesSlotSize() {
        return genericType().getOuterLocalVariablesSlotSize();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public boolean hasMemberTypes() {
        return this.type.hasMemberTypes();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean hasTypeBit(int i) {
        TypeBinding typeBindingErasure = erasure();
        if (typeBindingErasure instanceof ReferenceBinding) {
            return ((ReferenceBinding) typeBindingErasure).hasTypeBit(i);
        }
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public boolean implementsMethod(MethodBinding methodBinding) {
        return this.type.implementsMethod(methodBinding);
    }

    void initialize(ReferenceBinding referenceBinding, TypeBinding[] typeBindingArr) {
        this.type = referenceBinding;
        this.sourceName = referenceBinding.sourceName;
        this.compoundName = referenceBinding.compoundName;
        this.fPackage = referenceBinding.fPackage;
        this.fileName = referenceBinding.fileName;
        this.modifiers = referenceBinding.modifiers & (-1073741825);
        if (typeBindingArr != null) {
            this.modifiers = 1073741824 | this.modifiers;
        } else if (this.enclosingType != null) {
            this.modifiers = (1073741824 & this.enclosingType.modifiers) | this.modifiers;
            this.tagBits |= this.enclosingType.tagBits & 2305843009750564992L;
        }
        if (typeBindingArr != null) {
            this.arguments = typeBindingArr;
            for (TypeBinding typeBinding : typeBindingArr) {
                int iKind = typeBinding.kind();
                if (iKind == 516) {
                    this.tagBits |= 1073741824;
                    if (((WildcardBinding) typeBinding).boundKind != 0) {
                        this.tagBits |= 8388608;
                    }
                } else if (iKind == 8196) {
                    this.tagBits |= 1082130432;
                } else {
                    this.tagBits |= 8388608;
                }
                this.tagBits |= typeBinding.tagBits & 2305843009750567040L;
            }
        }
        this.tagBits = (referenceBinding.tagBits & 2846274964498155676L) | this.tagBits;
        this.tagBits &= -40961;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    void initializeForStaticImports() {
        this.type.initializeForStaticImports();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isBoundParameterizedType() {
        return (this.tagBits & 8388608) != 0;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isEquivalentTo(TypeBinding typeBinding) {
        ReferenceBinding referenceBindingEnclosingType;
        if (equalsEquals(this, typeBinding)) {
            return true;
        }
        if (typeBinding == null) {
            return false;
        }
        int iKind = typeBinding.kind();
        if (iKind != 260) {
            if (iKind != 516) {
                if (iKind == 1028) {
                    return TypeBinding.equalsEquals(erasure(), typeBinding.erasure());
                }
                if (iKind != 8196) {
                    return TypeBinding.equalsEquals(erasure(), typeBinding);
                }
            }
            return ((WildcardBinding) typeBinding).boundCheck(this);
        }
        ParameterizedTypeBinding parameterizedTypeBinding = (ParameterizedTypeBinding) typeBinding;
        if (TypeBinding.notEquals(this.type, parameterizedTypeBinding.type)) {
            return false;
        }
        if (!isStatic() && (referenceBindingEnclosingType = enclosingType()) != null) {
            ReferenceBinding referenceBindingEnclosingType2 = parameterizedTypeBinding.enclosingType();
            if (referenceBindingEnclosingType2 == null) {
                return false;
            }
            if ((referenceBindingEnclosingType2.tagBits & 1073741824) == 0) {
                if (TypeBinding.notEquals(referenceBindingEnclosingType, referenceBindingEnclosingType2)) {
                    return false;
                }
            } else if (!referenceBindingEnclosingType.isEquivalentTo(parameterizedTypeBinding.enclosingType())) {
                return false;
            }
        }
        if (this.arguments != ParameterizedSingleTypeReference.DIAMOND_TYPE_ARGUMENTS) {
            TypeBinding[] typeBindingArr = this.arguments;
            if (typeBindingArr == null) {
                return parameterizedTypeBinding.arguments == null;
            }
            int length = typeBindingArr.length;
            TypeBinding[] typeBindingArr2 = parameterizedTypeBinding.arguments;
            if (typeBindingArr2 == null || typeBindingArr2.length != length) {
                return false;
            }
            for (int i = 0; i < length; i++) {
                if (!this.arguments[i].isTypeArgumentContainedBy(typeBindingArr2[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public boolean isHierarchyConnected() {
        return (this.superclass == null || this.superInterfaces == null) ? false : true;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isProperType(boolean z) {
        if (this.arguments != null) {
            int i = 0;
            while (true) {
                TypeBinding[] typeBindingArr = this.arguments;
                if (i >= typeBindingArr.length) {
                    break;
                }
                if (!typeBindingArr[i].isProperType(z)) {
                    return false;
                }
                i++;
            }
        }
        return super.isProperType(z);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    TypeBinding substituteInferenceVariable(InferenceVariable inferenceVariable, TypeBinding typeBinding) {
        TypeBinding[] typeBindingArr = this.arguments;
        if (typeBindingArr != null) {
            int length = typeBindingArr.length;
            TypeBinding[] typeBindingArr2 = null;
            for (int i = 0; i < length; i++) {
                TypeBinding typeBinding2 = this.arguments[i];
                TypeBinding typeBindingSubstituteInferenceVariable = typeBinding2.substituteInferenceVariable(inferenceVariable, typeBinding);
                if (TypeBinding.notEquals(typeBindingSubstituteInferenceVariable, typeBinding2)) {
                    if (typeBindingArr2 == null) {
                        TypeBinding[] typeBindingArr3 = new TypeBinding[length];
                        System.arraycopy(this.arguments, 0, typeBindingArr3, 0, length);
                        typeBindingArr2 = typeBindingArr3;
                    }
                    typeBindingArr2[i] = typeBindingSubstituteInferenceVariable;
                }
            }
            if (typeBindingArr2 != null) {
                return this.environment.createParameterizedType(this.type, typeBindingArr2, this.enclosingType);
            }
        }
        return this;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Substitution
    public boolean isRawSubstitution() {
        return isRawType();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding unannotated() {
        return hasTypeAnnotations() ? this.environment.getUnannotatedType(this) : this;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding withoutToplevelNullAnnotation() {
        if (!hasNullTypeAnnotations()) {
            return this;
        }
        return this.environment.createParameterizedType((ReferenceBinding) this.environment.getUnannotatedType(this.type), this.arguments, this.enclosingType, this.environment.filterNullTypeAnnotations(this.typeAnnotations));
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public ReferenceBinding[] memberTypes() {
        if (this.memberTypes == null) {
            try {
                ReferenceBinding[] referenceBindingArrMemberTypes = this.type.memberTypes();
                int length = referenceBindingArrMemberTypes.length;
                ReferenceBinding[] referenceBindingArr = new ReferenceBinding[length];
                for (int i = 0; i < length; i++) {
                    referenceBindingArr[i] = this.environment.createParameterizedType(referenceBindingArrMemberTypes[i], null, this);
                }
                this.memberTypes = referenceBindingArr;
            } catch (Throwable th) {
                if (this.memberTypes == null) {
                    this.memberTypes = Binding.NO_MEMBER_TYPES;
                }
                throw th;
            }
        }
        return this.memberTypes;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean mentionsAny(TypeBinding[] typeBindingArr, int i) {
        if (super.mentionsAny(typeBindingArr, i)) {
            return true;
        }
        TypeBinding[] typeBindingArr2 = this.arguments;
        if (typeBindingArr2 != null) {
            int length = typeBindingArr2.length;
            for (int i2 = 0; i2 < length; i2++) {
                if (TypeBinding.notEquals(this.arguments[i2], this) && this.arguments[i2].mentionsAny(typeBindingArr, i)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    void collectInferenceVariables(Set<InferenceVariable> set) {
        TypeBinding[] typeBindingArr = this.arguments;
        if (typeBindingArr != null) {
            int length = typeBindingArr.length;
            for (int i = 0; i < length; i++) {
                if (TypeBinding.notEquals(this.arguments[i], this)) {
                    this.arguments[i].collectInferenceVariables(set);
                }
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public MethodBinding[] methods() {
        if ((this.tagBits & 32768) != 0) {
            return this.methods;
        }
        try {
            MethodBinding[] methodBindingArrMethods = this.type.methods();
            int length = methodBindingArrMethods.length;
            MethodBinding[] methodBindingArr = new MethodBinding[length];
            boolean zUsesNullTypeAnnotations = this.environment.usesNullTypeAnnotations();
            for (int i = 0; i < length; i++) {
                methodBindingArr[i] = createParameterizedMethod(methodBindingArrMethods[i]);
                if (zUsesNullTypeAnnotations) {
                    methodBindingArr[i] = NullAnnotationMatching.checkForContradictions(methodBindingArr[i], null, null);
                }
            }
            this.methods = methodBindingArr;
            return this.methods;
        } finally {
            if (this.methods == null) {
                this.methods = Binding.NO_METHODS;
            }
            this.tagBits |= 32768;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public int problemId() {
        return this.type.problemId();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] qualifiedPackageName() {
        return this.type.qualifiedPackageName();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] qualifiedSourceName() {
        return this.type.qualifiedSourceName();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] readableName() {
        StringBuffer stringBuffer = new StringBuffer(10);
        if (isMemberType()) {
            stringBuffer.append(CharOperation.concat(enclosingType().readableName(), this.sourceName, '.'));
        } else {
            stringBuffer.append(CharOperation.concatWith(this.type.compoundName, '.'));
        }
        TypeBinding[] typeBindingArr = this.arguments;
        if (typeBindingArr != null && typeBindingArr.length > 0) {
            stringBuffer.append('<');
            int length = this.arguments.length;
            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    stringBuffer.append(',');
                }
                stringBuffer.append(this.arguments[i].readableName());
            }
            stringBuffer.append('>');
        }
        int length2 = stringBuffer.length();
        char[] cArr = new char[length2];
        stringBuffer.getChars(0, length2, cArr, 0);
        return cArr;
    }

    ReferenceBinding resolve() {
        if ((this.tagBits & TagBits.HasUnresolvedTypeVariables) == 0) {
            return this;
        }
        this.tagBits &= -16777217;
        ReferenceBinding referenceBinding = (ReferenceBinding) BinaryTypeBinding.resolveType(this.type, this.environment, false);
        this.tagBits |= referenceBinding.tagBits & 2048;
        TypeBinding[] typeBindingArr = this.arguments;
        if (typeBindingArr != null) {
            int length = typeBindingArr.length;
            for (int i = 0; i < length; i++) {
                this.arguments[i] = BinaryTypeBinding.resolveType(this.arguments[i], this.environment, true);
                this.tagBits |= referenceBinding.tagBits & 2048;
            }
        }
        return this;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] shortReadableName() {
        StringBuffer stringBuffer = new StringBuffer(10);
        if (isMemberType()) {
            stringBuffer.append(CharOperation.concat(enclosingType().shortReadableName(), this.sourceName, '.'));
        } else {
            stringBuffer.append(this.type.sourceName);
        }
        TypeBinding[] typeBindingArr = this.arguments;
        if (typeBindingArr != null && typeBindingArr.length > 0) {
            stringBuffer.append('<');
            int length = this.arguments.length;
            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    stringBuffer.append(',');
                }
                stringBuffer.append(this.arguments[i].shortReadableName());
            }
            stringBuffer.append('>');
        }
        int length2 = stringBuffer.length();
        char[] cArr = new char[length2];
        stringBuffer.getChars(0, length2, cArr, 0);
        return cArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] nullAnnotatedReadableName(CompilerOptions compilerOptions, boolean z) {
        if (z) {
            return nullAnnotatedShortReadableName(compilerOptions);
        }
        return nullAnnotatedReadableName(compilerOptions);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    char[] nullAnnotatedReadableName(CompilerOptions compilerOptions) {
        StringBuffer stringBuffer = new StringBuffer(10);
        if (isMemberType()) {
            stringBuffer.append(enclosingType().nullAnnotatedReadableName(compilerOptions, false));
            stringBuffer.append('.');
            appendNullAnnotation(stringBuffer, compilerOptions);
            stringBuffer.append(this.sourceName);
        } else if (this.type.compoundName != null) {
            int length = this.type.compoundName.length;
            int i = 0;
            while (i < length - 1) {
                stringBuffer.append(this.type.compoundName[i]);
                stringBuffer.append('.');
                i++;
            }
            appendNullAnnotation(stringBuffer, compilerOptions);
            stringBuffer.append(this.type.compoundName[i]);
        } else {
            appendNullAnnotation(stringBuffer, compilerOptions);
            if (this.type.sourceName != null) {
                stringBuffer.append(this.type.sourceName);
            } else {
                stringBuffer.append(this.type.readableName());
            }
        }
        TypeBinding[] typeBindingArr = this.arguments;
        if (typeBindingArr != null && typeBindingArr.length > 0) {
            stringBuffer.append('<');
            int length2 = this.arguments.length;
            for (int i2 = 0; i2 < length2; i2++) {
                if (i2 > 0) {
                    stringBuffer.append(',');
                }
                stringBuffer.append(this.arguments[i2].nullAnnotatedReadableName(compilerOptions, false));
            }
            stringBuffer.append('>');
        }
        int length3 = stringBuffer.length();
        char[] cArr = new char[length3];
        stringBuffer.getChars(0, length3, cArr, 0);
        return cArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    char[] nullAnnotatedShortReadableName(CompilerOptions compilerOptions) {
        StringBuffer stringBuffer = new StringBuffer(10);
        if (isMemberType()) {
            stringBuffer.append(enclosingType().nullAnnotatedReadableName(compilerOptions, true));
            stringBuffer.append('.');
            appendNullAnnotation(stringBuffer, compilerOptions);
            stringBuffer.append(this.sourceName);
        } else {
            appendNullAnnotation(stringBuffer, compilerOptions);
            if (this.type.sourceName != null) {
                stringBuffer.append(this.type.sourceName);
            } else {
                stringBuffer.append(this.type.shortReadableName());
            }
        }
        TypeBinding[] typeBindingArr = this.arguments;
        if (typeBindingArr != null && typeBindingArr.length > 0) {
            stringBuffer.append('<');
            int length = this.arguments.length;
            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    stringBuffer.append(',');
                }
                stringBuffer.append(this.arguments[i].nullAnnotatedReadableName(compilerOptions, true));
            }
            stringBuffer.append('>');
        }
        int length2 = stringBuffer.length();
        char[] cArr = new char[length2];
        stringBuffer.getChars(0, length2, cArr, 0);
        return cArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] signature() {
        if (this.signature == null) {
            this.signature = this.type.signature();
        }
        return this.signature;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] sourceName() {
        return this.type.sourceName();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Substitution
    public TypeBinding substitute(TypeVariableBinding typeVariableBinding) {
        ParameterizedTypeBinding parameterizedTypeBinding = this;
        while (true) {
            TypeVariableBinding[] typeVariableBindingArrTypeVariables = parameterizedTypeBinding.type.typeVariables();
            if (typeVariableBinding.rank < typeVariableBindingArrTypeVariables.length && TypeBinding.equalsEquals(typeVariableBindingArrTypeVariables[typeVariableBinding.rank], typeVariableBinding)) {
                if (parameterizedTypeBinding.arguments == null) {
                    parameterizedTypeBinding.initializeArguments();
                }
                TypeBinding[] typeBindingArr = parameterizedTypeBinding.arguments;
                if (typeBindingArr != null) {
                    return typeBindingArr.length == 0 ? typeVariableBinding : typeVariableBinding.combineTypeAnnotations(typeBindingArr[typeVariableBinding.rank]);
                }
            }
            if (!parameterizedTypeBinding.isStatic()) {
                ReferenceBinding referenceBindingEnclosingType = parameterizedTypeBinding.enclosingType();
                if (referenceBindingEnclosingType instanceof ParameterizedTypeBinding) {
                    parameterizedTypeBinding = (ParameterizedTypeBinding) referenceBindingEnclosingType;
                }
            }
            return typeVariableBinding;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public ReferenceBinding superclass() {
        if (this.superclass == null) {
            ReferenceBinding referenceBindingSuperclass = this.type.superclass();
            if (referenceBindingSuperclass == null) {
                return null;
            }
            this.superclass = (ReferenceBinding) Scope.substitute(this, referenceBindingSuperclass);
            this.typeBits |= this.superclass.typeBits & 19;
            if ((this.typeBits & 3) != 0) {
                this.typeBits |= applyCloseableClassWhitelists();
            }
        }
        return this.superclass;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public ReferenceBinding[] superInterfaces() {
        if (this.superInterfaces == null) {
            if (this.type.isHierarchyBeingConnected()) {
                return Binding.NO_SUPERINTERFACES;
            }
            ReferenceBinding[] referenceBindingArrSubstitute = Scope.substitute((Substitution) this, this.type.superInterfaces());
            this.superInterfaces = referenceBindingArrSubstitute;
            if (referenceBindingArrSubstitute != null) {
                int length = referenceBindingArrSubstitute.length;
                while (true) {
                    length--;
                    if (length < 0) {
                        break;
                    }
                    this.typeBits |= this.superInterfaces[length].typeBits & 19;
                    if ((this.typeBits & 3) != 0) {
                        this.typeBits |= applyCloseableInterfaceWhitelists();
                    }
                }
            }
        }
        return this.superInterfaces;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public void swapUnresolved(UnresolvedReferenceBinding unresolvedReferenceBinding, ReferenceBinding referenceBinding, LookupEnvironment lookupEnvironment) {
        boolean z;
        if (this.type == unresolvedReferenceBinding) {
            this.type = referenceBinding;
            ReferenceBinding referenceBindingEnclosingType = referenceBinding.enclosingType();
            if (referenceBindingEnclosingType != null) {
                this.enclosingType = (ReferenceBinding) lookupEnvironment.convertUnresolvedBinaryToRawType(referenceBindingEnclosingType);
            }
            z = true;
        } else {
            z = false;
        }
        TypeBinding[] typeBindingArr = this.arguments;
        if (typeBindingArr != null) {
            int length = typeBindingArr.length;
            for (int i = 0; i < length; i++) {
                TypeBinding[] typeBindingArr2 = this.arguments;
                if (typeBindingArr2[i] == unresolvedReferenceBinding) {
                    typeBindingArr2[i] = lookupEnvironment.convertUnresolvedBinaryToRawType(referenceBinding);
                    z = true;
                }
            }
        }
        if (z) {
            initialize(this.type, this.arguments);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public ReferenceBinding[] syntheticEnclosingInstanceTypes() {
        return genericType().syntheticEnclosingInstanceTypes();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public SyntheticArgumentBinding[] syntheticOuterLocalVariables() {
        return genericType().syntheticOuterLocalVariables();
    }

    public String toString() {
        if (hasTypeAnnotations()) {
            return annotatedDebugName();
        }
        StringBuffer stringBuffer = new StringBuffer(30);
        if (this.type instanceof UnresolvedReferenceBinding) {
            stringBuffer.append(debugName());
        } else {
            if (isDeprecated()) {
                stringBuffer.append("deprecated ");
            }
            if (isPublic()) {
                stringBuffer.append("public ");
            }
            if (isProtected()) {
                stringBuffer.append("protected ");
            }
            if (isPrivate()) {
                stringBuffer.append("private ");
            }
            if (isAbstract() && isClass()) {
                stringBuffer.append("abstract ");
            }
            if (isStatic() && isNestedType()) {
                stringBuffer.append("static ");
            }
            if (isFinal()) {
                stringBuffer.append("final ");
            }
            if (isEnum()) {
                stringBuffer.append("enum ");
            } else if (isAnnotationType()) {
                stringBuffer.append("@interface ");
            } else if (isClass()) {
                stringBuffer.append(ExternalAnnotationProvider.CLASS_PREFIX);
            } else {
                stringBuffer.append("interface ");
            }
            stringBuffer.append(debugName());
            stringBuffer.append("\n\textends ");
            ReferenceBinding referenceBinding = this.superclass;
            stringBuffer.append(referenceBinding != null ? referenceBinding.debugName() : "NULL TYPE");
            ReferenceBinding[] referenceBindingArr = this.superInterfaces;
            if (referenceBindingArr != null) {
                if (referenceBindingArr != Binding.NO_SUPERINTERFACES) {
                    stringBuffer.append("\n\timplements : ");
                    int length = this.superInterfaces.length;
                    for (int i = 0; i < length; i++) {
                        if (i > 0) {
                            stringBuffer.append(", ");
                        }
                        ReferenceBinding referenceBinding2 = this.superInterfaces[i];
                        stringBuffer.append(referenceBinding2 != null ? referenceBinding2.debugName() : "NULL TYPE");
                    }
                }
            } else {
                stringBuffer.append("NULL SUPERINTERFACES");
            }
            if (enclosingType() != null) {
                stringBuffer.append("\n\tenclosing type : ");
                stringBuffer.append(enclosingType().debugName());
            }
            FieldBinding[] fieldBindingArr = this.fields;
            if (fieldBindingArr != null) {
                if (fieldBindingArr != Binding.NO_FIELDS) {
                    stringBuffer.append("\n/*   fields   */");
                    int length2 = this.fields.length;
                    for (int i2 = 0; i2 < length2; i2++) {
                        StringBuffer stringBufferAppend = stringBuffer.append('\n');
                        FieldBinding fieldBinding = this.fields[i2];
                        stringBufferAppend.append(fieldBinding != null ? fieldBinding.toString() : "NULL FIELD");
                    }
                }
            } else {
                stringBuffer.append("NULL FIELDS");
            }
            MethodBinding[] methodBindingArr = this.methods;
            if (methodBindingArr != null) {
                if (methodBindingArr != Binding.NO_METHODS) {
                    stringBuffer.append("\n/*   methods   */");
                    int length3 = this.methods.length;
                    for (int i3 = 0; i3 < length3; i3++) {
                        StringBuffer stringBufferAppend2 = stringBuffer.append('\n');
                        MethodBinding methodBinding = this.methods[i3];
                        stringBufferAppend2.append(methodBinding != null ? methodBinding.toString() : "NULL METHOD");
                    }
                }
            } else {
                stringBuffer.append("NULL METHODS");
            }
            stringBuffer.append("\n\n");
        }
        return stringBuffer.toString();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeVariableBinding[] typeVariables() {
        if (this.arguments == null) {
            return this.type.typeVariables();
        }
        return Binding.NO_TYPE_VARIABLES;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding[] typeArguments() {
        return this.arguments;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public FieldBinding[] unResolvedFields() {
        return this.fields;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    protected MethodBinding[] getInterfaceAbstractContracts(Scope scope, boolean z) throws InvalidInputException {
        if (z) {
            TypeBinding[] nonWildcardParameterization = getNonWildcardParameterization(scope);
            if (nonWildcardParameterization == null) {
                return new MethodBinding[]{new ProblemMethodBinding(TypeConstants.ANONYMOUS_METHOD, null, 18)};
            }
            for (int i = 0; i < nonWildcardParameterization.length; i++) {
                if (TypeBinding.notEquals(nonWildcardParameterization[i], this.arguments[i])) {
                    LookupEnvironment lookupEnvironmentEnvironment = scope.environment();
                    ReferenceBinding referenceBinding = this.type;
                    ParameterizedTypeBinding parameterizedTypeBindingCreateParameterizedType = lookupEnvironmentEnvironment.createParameterizedType(referenceBinding, nonWildcardParameterization, referenceBinding.enclosingType());
                    TypeVariableBinding[] typeVariableBindingArrTypeVariables = this.type.typeVariables();
                    int length = typeVariableBindingArrTypeVariables.length;
                    for (int i2 = 0; i2 < length; i2++) {
                        if (!typeVariableBindingArrTypeVariables[i2].boundCheck(parameterizedTypeBindingCreateParameterizedType, nonWildcardParameterization[i2], scope, null).isOKbyJLS()) {
                            return new MethodBinding[]{new ProblemMethodBinding(TypeConstants.ANONYMOUS_METHOD, null, 18)};
                        }
                    }
                    return parameterizedTypeBindingCreateParameterizedType.getInterfaceAbstractContracts(scope, z);
                }
            }
        }
        return super.getInterfaceAbstractContracts(scope, z);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public MethodBinding getSingleAbstractMethod(Scope scope, boolean z) {
        return getSingleAbstractMethod(scope, z, -1, -1);
    }

    public MethodBinding getSingleAbstractMethod(Scope scope, boolean z, int i, int i2) {
        char c = z ? i2 < 0 ? (char) 0 : (char) 1 : (char) 2;
        if (this.singleAbstractMethod != null) {
            if (this.singleAbstractMethod[c] != null) {
                return this.singleAbstractMethod[c];
            }
        } else {
            this.singleAbstractMethod = new MethodBinding[3];
        }
        if (!isValidBinding()) {
            return null;
        }
        ReferenceBinding referenceBindingGenericType = genericType();
        MethodBinding singleAbstractMethod = referenceBindingGenericType.getSingleAbstractMethod(scope, z);
        if (singleAbstractMethod == null || !singleAbstractMethod.isValidBinding()) {
            this.singleAbstractMethod[c] = singleAbstractMethod;
            return singleAbstractMethod;
        }
        TypeBinding[] nonWildcardParameterization = this.arguments;
        if (z) {
            nonWildcardParameterization = getNonWildcardParameterization(scope);
            if (nonWildcardParameterization == null) {
                MethodBinding[] methodBindingArr = this.singleAbstractMethod;
                ProblemMethodBinding problemMethodBinding = new ProblemMethodBinding(TypeConstants.ANONYMOUS_METHOD, null, 18);
                methodBindingArr[c] = problemMethodBinding;
                return problemMethodBinding;
            }
        } else if (nonWildcardParameterization == null) {
            nonWildcardParameterization = NO_TYPES;
        }
        if (i2 >= 0) {
            int length = nonWildcardParameterization.length;
            for (int i3 = 0; i3 < length; i3++) {
                nonWildcardParameterization[i3] = nonWildcardParameterization[i3].capture(scope, i, i2);
            }
        }
        ParameterizedTypeBinding parameterizedTypeBindingCreateParameterizedType = scope.environment().createParameterizedType(referenceBindingGenericType, nonWildcardParameterization, referenceBindingGenericType.enclosingType());
        TypeVariableBinding[] typeVariableBindingArrTypeVariables = referenceBindingGenericType.typeVariables();
        int length2 = typeVariableBindingArrTypeVariables.length;
        for (int i4 = 0; i4 < length2; i4++) {
            if (!typeVariableBindingArrTypeVariables[i4].boundCheck(parameterizedTypeBindingCreateParameterizedType, nonWildcardParameterization[i4], scope, null).isOKbyJLS()) {
                MethodBinding[] methodBindingArr2 = this.singleAbstractMethod;
                ProblemMethodBinding problemMethodBinding2 = new ProblemMethodBinding(TypeConstants.ANONYMOUS_METHOD, null, 18);
                methodBindingArr2[c] = problemMethodBinding2;
                return problemMethodBinding2;
            }
        }
        for (MethodBinding methodBinding : ((ReferenceBinding) parameterizedTypeBindingCreateParameterizedType.findSuperTypeOriginatingFrom(singleAbstractMethod.declaringClass)).getMethods(singleAbstractMethod.selector)) {
            if (methodBinding.isAbstract() && !methodBinding.redeclaresPublicObjectMethod(scope)) {
                this.singleAbstractMethod[c] = methodBinding;
                break;
            }
        }
        return this.singleAbstractMethod[c];
    }

    /* JADX WARN: Multi-variable type inference failed */
    public TypeBinding[] getNonWildcardParameterization(Scope scope) {
        int i;
        TypeBinding[] typeBindingArr = this.arguments;
        if (typeBindingArr == null) {
            return NO_TYPES;
        }
        TypeVariableBinding[] typeVariableBindingArrTypeVariables = genericType().typeVariables();
        TypeBinding[] typeBindingArr2 = new TypeBinding[typeBindingArr.length];
        int length = typeBindingArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            TypeBinding typeBinding = typeBindingArr[i2];
            if (typeBinding.kind() == 516) {
                if (typeVariableBindingArrTypeVariables[i2].mentionsAny(typeVariableBindingArrTypeVariables, i2)) {
                    return null;
                }
                WildcardBinding wildcardBinding = (WildcardBinding) typeBinding;
                int i3 = wildcardBinding.boundKind;
                if (i3 == 0) {
                    TypeBinding typeBinding2 = typeVariableBindingArrTypeVariables[i2].firstBound;
                    typeBindingArr2[i2] = typeBinding2;
                    if (typeBinding2 == null) {
                        typeBindingArr2[i2] = typeVariableBindingArrTypeVariables[i2].superclass;
                    }
                } else {
                    if (i3 == 1) {
                        TypeBinding[] typeBindingArr3 = wildcardBinding.otherBounds;
                        TypeBinding[] typeBindingArrOtherUpperBounds = typeVariableBindingArrTypeVariables[i2].otherUpperBounds();
                        int length2 = (typeBindingArr3 != null ? typeBindingArr3.length : 0) + 1 + typeBindingArrOtherUpperBounds.length;
                        if (typeVariableBindingArrTypeVariables[i2].firstBound != null) {
                            length2++;
                        }
                        TypeBinding[] typeBindingArr4 = new TypeBinding[length2];
                        typeBindingArr4[0] = wildcardBinding.bound;
                        if (typeBindingArr3 != null) {
                            int i4 = 0;
                            i = 1;
                            while (i4 < typeBindingArr3.length) {
                                typeBindingArr4[i] = typeBindingArr3[i4];
                                i4++;
                                i++;
                            }
                        } else {
                            i = 1;
                        }
                        if (typeVariableBindingArrTypeVariables[i2].firstBound != null) {
                            typeBindingArr4[i] = typeVariableBindingArrTypeVariables[i2].firstBound;
                            i++;
                        }
                        int i5 = 0;
                        while (i5 < typeBindingArrOtherUpperBounds.length) {
                            typeBindingArr4[i] = typeBindingArrOtherUpperBounds[i5];
                            i5++;
                            i++;
                        }
                        TypeBinding[] typeBindingArrGreaterLowerBound = Scope.greaterLowerBound(typeBindingArr4, null, this.environment);
                        if (typeBindingArrGreaterLowerBound != null && typeBindingArrGreaterLowerBound.length != 0) {
                            if (typeBindingArrGreaterLowerBound.length == 1) {
                                typeBindingArr2[i2] = typeBindingArrGreaterLowerBound[0];
                            } else {
                                try {
                                    ReferenceBinding[] referenceBindingArr = new ReferenceBinding[typeBindingArrGreaterLowerBound.length];
                                    System.arraycopy(typeBindingArrGreaterLowerBound, 0, referenceBindingArr, 0, typeBindingArrGreaterLowerBound.length);
                                    typeBindingArr2[i2] = this.environment.createIntersectionType18(referenceBindingArr);
                                } catch (ArrayStoreException unused) {
                                    scope.problemReporter().genericInferenceError("Cannot compute glb of " + Arrays.toString(typeBindingArrGreaterLowerBound), null);
                                }
                            }
                        }
                        return null;
                    }
                    if (i3 == 2) {
                        typeBindingArr2[i2] = wildcardBinding.bound;
                    }
                }
            } else {
                typeBindingArr2[i2] = typeBinding;
            }
        }
        return typeBindingArr2;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public long updateTagBits() {
        TypeBinding[] typeBindingArr = this.arguments;
        if (typeBindingArr != null) {
            for (TypeBinding typeBinding : typeBindingArr) {
                this.tagBits |= typeBinding.updateTagBits();
            }
        }
        return super.updateTagBits();
    }
}
