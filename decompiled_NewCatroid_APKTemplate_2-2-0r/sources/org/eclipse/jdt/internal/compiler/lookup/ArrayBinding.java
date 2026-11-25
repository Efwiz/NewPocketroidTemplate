package org.eclipse.jdt.internal.compiler.lookup;

import io.netty.util.internal.StringUtil;
import java.util.List;
import java.util.Set;
import okhttp3.HttpUrl;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.impl.Constant;

/* JADX INFO: loaded from: classes14.dex */
public final class ArrayBinding extends TypeBinding {
    public static final FieldBinding ArrayLength = new FieldBinding(TypeConstants.LENGTH, TypeBinding.INT, 17, null, Constant.NotAConstant);
    char[] constantPoolName;
    public int dimensions;
    LookupEnvironment environment;
    char[] genericTypeSignature;
    public TypeBinding leafComponentType;
    public long[] nullTagBitsPerDimension;

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean acceptsNonNullDefault() {
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public int kind() {
        return 68;
    }

    public ArrayBinding(TypeBinding typeBinding, int i, LookupEnvironment lookupEnvironment) {
        this.tagBits |= 1;
        this.leafComponentType = typeBinding;
        this.dimensions = i;
        this.environment = lookupEnvironment;
        if (typeBinding instanceof UnresolvedReferenceBinding) {
            ((UnresolvedReferenceBinding) typeBinding).addWrapper(this, lookupEnvironment);
        } else {
            this.tagBits |= typeBinding.tagBits & 2305843010824308864L;
        }
        long j = typeBinding.tagBits & TagBits.AnnotationNullMASK;
        if (j != 0) {
            int i2 = this.dimensions;
            long[] jArr = new long[i2 + 1];
            this.nullTagBitsPerDimension = jArr;
            jArr[i2] = j;
            this.tagBits |= 1048576;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding closestMatch() {
        if (isValidBinding()) {
            return this;
        }
        if (this.leafComponentType.closestMatch() == null) {
            return null;
        }
        return this.environment.createArrayType(this.leafComponentType.closestMatch(), this.dimensions);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public List<TypeBinding> collectMissingTypes(List<TypeBinding> list) {
        return (this.tagBits & 128) != 0 ? this.leafComponentType.collectMissingTypes(list) : list;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public void collectSubstitutes(Scope scope, TypeBinding typeBinding, InferenceContext inferenceContext, int i) {
        if ((this.tagBits & TagBits.HasTypeVariable) == 0 || typeBinding == TypeBinding.NULL || typeBinding.kind() == 65540 || typeBinding.kind() != 68) {
            return;
        }
        int iDimensions = typeBinding.dimensions();
        int i2 = this.dimensions;
        if (iDimensions == i2) {
            this.leafComponentType.collectSubstitutes(scope, typeBinding.leafComponentType(), inferenceContext, i);
        } else if (iDimensions > i2) {
            this.leafComponentType.collectSubstitutes(scope, this.environment.createArrayType(typeBinding.leafComponentType(), iDimensions - this.dimensions), inferenceContext, i);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean mentionsAny(TypeBinding[] typeBindingArr, int i) {
        return this.leafComponentType.mentionsAny(typeBindingArr, i);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    void collectInferenceVariables(Set<InferenceVariable> set) {
        this.leafComponentType.collectInferenceVariables(set);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    TypeBinding substituteInferenceVariable(InferenceVariable inferenceVariable, TypeBinding typeBinding) {
        TypeBinding typeBindingSubstituteInferenceVariable = this.leafComponentType.substituteInferenceVariable(inferenceVariable, typeBinding);
        return TypeBinding.notEquals(typeBindingSubstituteInferenceVariable, this.leafComponentType) ? this.environment.createArrayType(typeBindingSubstituteInferenceVariable, this.dimensions, this.typeAnnotations) : this;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] computeUniqueKey(boolean z) {
        int i = this.dimensions;
        char[] cArr = new char[i];
        for (int i2 = i - 1; i2 >= 0; i2--) {
            cArr[i2] = '[';
        }
        return CharOperation.concat(cArr, this.leafComponentType.computeUniqueKey(z));
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] constantPoolName() {
        char[] cArr = this.constantPoolName;
        if (cArr != null) {
            return cArr;
        }
        int i = this.dimensions;
        char[] cArr2 = new char[i];
        for (int i2 = i - 1; i2 >= 0; i2--) {
            cArr2[i2] = '[';
        }
        char[] cArrConcat = CharOperation.concat(cArr2, this.leafComponentType.signature());
        this.constantPoolName = cArrConcat;
        return cArrConcat;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public String debugName() {
        if (hasTypeAnnotations()) {
            return annotatedDebugName();
        }
        StringBuffer stringBuffer = new StringBuffer(this.dimensions * 2);
        int i = this.dimensions;
        while (true) {
            i--;
            if (i >= 0) {
                stringBuffer.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
            } else {
                return String.valueOf(this.leafComponentType.debugName()) + stringBuffer.toString();
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public String annotatedDebugName() {
        AnnotationBinding annotationBinding;
        StringBuffer stringBuffer = new StringBuffer(this.dimensions * 2);
        stringBuffer.append(this.leafComponentType.annotatedDebugName());
        stringBuffer.append(StringUtil.SPACE);
        AnnotationBinding[] typeAnnotations = getTypeAnnotations();
        int i = -1;
        for (int i2 = 0; i2 < this.dimensions; i2++) {
            if (typeAnnotations != null) {
                if (i2 != 0) {
                    stringBuffer.append(StringUtil.SPACE);
                }
                while (true) {
                    i++;
                    if (i >= typeAnnotations.length || (annotationBinding = typeAnnotations[i]) == null) {
                        break;
                    }
                    stringBuffer.append(annotationBinding);
                    stringBuffer.append(StringUtil.SPACE);
                }
            }
            stringBuffer.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
        }
        return stringBuffer.toString();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public int dimensions() {
        return this.dimensions;
    }

    public TypeBinding elementsType() {
        if (this.dimensions == 1) {
            return this.leafComponentType;
        }
        AnnotationBinding[] typeAnnotations = getTypeAnnotations();
        AnnotationBinding[] annotationBindingArr = Binding.NO_ANNOTATIONS;
        int length = typeAnnotations == null ? 0 : typeAnnotations.length;
        for (int i = 0; i < length; i++) {
            if (typeAnnotations[i] == null) {
                int i2 = i + 1;
                int i3 = (length - i) - 1;
                AnnotationBinding[] annotationBindingArr2 = new AnnotationBinding[i3];
                System.arraycopy(typeAnnotations, i2, annotationBindingArr2, 0, i3);
                annotationBindingArr = annotationBindingArr2;
                break;
            }
        }
        return this.environment.createArrayType(this.leafComponentType, this.dimensions - 1, annotationBindingArr);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding erasure() {
        TypeBinding typeBindingErasure = this.leafComponentType.erasure();
        return TypeBinding.notEquals(this.leafComponentType, typeBindingErasure) ? this.environment.createArrayType(typeBindingErasure, this.dimensions) : this;
    }

    public LookupEnvironment environment() {
        return this.environment;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] genericTypeSignature() {
        if (this.genericTypeSignature == null) {
            int i = this.dimensions;
            char[] cArr = new char[i];
            for (int i2 = i - 1; i2 >= 0; i2--) {
                cArr[i2] = '[';
            }
            this.genericTypeSignature = CharOperation.concat(cArr, this.leafComponentType.genericTypeSignature());
        }
        return this.genericTypeSignature;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public PackageBinding getPackage() {
        return this.leafComponentType.getPackage();
    }

    public int hashCode() {
        TypeBinding typeBinding = this.leafComponentType;
        return typeBinding == null ? super.hashCode() : typeBinding.hashCode();
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x001f, code lost:
    
        if (r0 != 8196) goto L38;
     */
    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean isCompatibleWith(org.eclipse.jdt.internal.compiler.lookup.TypeBinding r5, org.eclipse.jdt.internal.compiler.lookup.Scope r6) {
        /*
            r4 = this;
            boolean r0 = equalsEquals(r4, r5)
            r1 = 1
            if (r0 == 0) goto L8
            return r1
        L8:
            int r0 = r5.kind()
            r2 = 68
            r3 = 0
            if (r0 == r2) goto L43
            r2 = 132(0x84, float:1.85E-43)
            if (r0 == r2) goto L42
            r2 = 516(0x204, float:7.23E-43)
            if (r0 == r2) goto L3b
            r2 = 4100(0x1004, float:5.745E-42)
            if (r0 == r2) goto L22
            r6 = 8196(0x2004, float:1.1485E-41)
            if (r0 == r6) goto L3b
            goto L61
        L22:
            boolean r0 = r5.isCapture()
            if (r0 == 0) goto L3a
            org.eclipse.jdt.internal.compiler.lookup.CaptureBinding r5 = (org.eclipse.jdt.internal.compiler.lookup.CaptureBinding) r5
            org.eclipse.jdt.internal.compiler.lookup.TypeBinding r5 = r5.lowerBound
            if (r5 == 0) goto L3a
            boolean r0 = r5.isArrayType()
            if (r0 != 0) goto L35
            return r3
        L35:
            boolean r5 = r4.isCompatibleWith(r5, r6)
            return r5
        L3a:
            return r3
        L3b:
            org.eclipse.jdt.internal.compiler.lookup.WildcardBinding r5 = (org.eclipse.jdt.internal.compiler.lookup.WildcardBinding) r5
            boolean r5 = r5.boundCheck(r4)
            return r5
        L42:
            return r3
        L43:
            r6 = r5
            org.eclipse.jdt.internal.compiler.lookup.ArrayBinding r6 = (org.eclipse.jdt.internal.compiler.lookup.ArrayBinding) r6
            org.eclipse.jdt.internal.compiler.lookup.TypeBinding r0 = r6.leafComponentType
            boolean r0 = r0.isBaseType()
            if (r0 == 0) goto L4f
            return r3
        L4f:
            int r0 = r4.dimensions
            int r2 = r6.dimensions
            if (r0 != r2) goto L5e
            org.eclipse.jdt.internal.compiler.lookup.TypeBinding r5 = r4.leafComponentType
            org.eclipse.jdt.internal.compiler.lookup.TypeBinding r6 = r6.leafComponentType
            boolean r5 = r5.isCompatibleWith(r6)
            return r5
        L5e:
            if (r0 >= r2) goto L61
            return r3
        L61:
            org.eclipse.jdt.internal.compiler.lookup.TypeBinding r5 = r5.leafComponentType()
            int r5 = r5.f626id
            if (r5 == r1) goto L72
            r6 = 36
            if (r5 == r6) goto L72
            r6 = 37
            if (r5 == r6) goto L72
            return r3
        L72:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jdt.internal.compiler.lookup.ArrayBinding.isCompatibleWith(org.eclipse.jdt.internal.compiler.lookup.TypeBinding, org.eclipse.jdt.internal.compiler.lookup.Scope):boolean");
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isSubtypeOf(TypeBinding typeBinding) {
        if (equalsEquals(this, typeBinding)) {
            return true;
        }
        int iKind = typeBinding.kind();
        if (iKind == 68) {
            ArrayBinding arrayBinding = (ArrayBinding) typeBinding;
            if (arrayBinding.leafComponentType.isBaseType()) {
                return false;
            }
            int i = this.dimensions;
            int i2 = arrayBinding.dimensions;
            if (i == i2) {
                return this.leafComponentType.isSubtypeOf(arrayBinding.leafComponentType);
            }
            if (i < i2) {
                return false;
            }
        } else if (iKind == 132) {
            return false;
        }
        int i3 = typeBinding.leafComponentType().f626id;
        return i3 == 1 || i3 == 36 || i3 == 37;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isProperType(boolean z) {
        return this.leafComponentType.isProperType(z);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding leafComponentType() {
        return this.leafComponentType;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] nullAnnotatedReadableName(CompilerOptions compilerOptions, boolean z) {
        char[][] cArr;
        char[] cArrConcatWith;
        if (this.nullTagBitsPerDimension == null) {
            return z ? shortReadableName() : readableName();
        }
        char[][] cArr2 = new char[this.dimensions][];
        for (int i = 0; i < this.dimensions; i++) {
            long j = this.nullTagBitsPerDimension[i];
            if ((TagBits.AnnotationNullMASK & j) != 0) {
                if ((j & 72057594037927936L) != 0) {
                    cArr = compilerOptions.nonNullAnnotationName;
                } else {
                    cArr = compilerOptions.nullableAnnotationName;
                }
                if (z) {
                    cArrConcatWith = cArr[cArr.length - 1];
                } else {
                    cArrConcatWith = CharOperation.concatWith(cArr, '.');
                }
                char[] cArr3 = new char[cArrConcatWith.length + 3];
                cArr2[i] = cArr3;
                cArr3[0] = '@';
                System.arraycopy(cArrConcatWith, 0, cArr3, 1, cArrConcatWith.length);
                char[] cArr4 = cArr2[i];
                cArr4[cArrConcatWith.length + 1] = '[';
                cArr4[cArrConcatWith.length + 2] = ']';
            } else {
                cArr2[i] = new char[]{'[', ']'};
            }
        }
        return CharOperation.concat(this.leafComponentType.nullAnnotatedReadableName(compilerOptions, z), CharOperation.concatWith(cArr2, StringUtil.SPACE), StringUtil.SPACE);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public int problemId() {
        return this.leafComponentType.problemId();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] qualifiedSourceName() {
        int i = this.dimensions;
        char[] cArr = new char[i * 2];
        for (int i2 = (i * 2) - 1; i2 >= 0; i2 -= 2) {
            cArr[i2] = ']';
            cArr[i2 - 1] = '[';
        }
        return CharOperation.concat(this.leafComponentType.qualifiedSourceName(), cArr);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] readableName() {
        int i = this.dimensions;
        char[] cArr = new char[i * 2];
        for (int i2 = (i * 2) - 1; i2 >= 0; i2 -= 2) {
            cArr[i2] = ']';
            cArr[i2 - 1] = '[';
        }
        return CharOperation.concat(this.leafComponentType.readableName(), cArr);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public void setTypeAnnotations(AnnotationBinding[] annotationBindingArr, boolean z) {
        this.tagBits |= 2097152;
        if (annotationBindingArr == null || annotationBindingArr.length == 0) {
            return;
        }
        this.typeAnnotations = annotationBindingArr;
        if (z) {
            if (this.nullTagBitsPerDimension == null) {
                this.nullTagBitsPerDimension = new long[this.dimensions + 1];
            }
            int i = 0;
            long j = 0;
            for (AnnotationBinding annotationBinding : annotationBindingArr) {
                if (annotationBinding == null) {
                    if (j != 0) {
                        this.nullTagBitsPerDimension[i] = j;
                        j = 0;
                    }
                    i++;
                } else if (annotationBinding.type.hasNullBit(64)) {
                    j |= 36028797018963968L;
                    this.tagBits |= 1048576;
                } else if (annotationBinding.type.hasNullBit(32)) {
                    j |= 72057594037927936L;
                    this.tagBits |= 1048576;
                }
            }
            this.tagBits |= this.nullTagBitsPerDimension[0];
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] shortReadableName() {
        int i = this.dimensions;
        char[] cArr = new char[i * 2];
        for (int i2 = (i * 2) - 1; i2 >= 0; i2 -= 2) {
            cArr[i2] = ']';
            cArr[i2 - 1] = '[';
        }
        return CharOperation.concat(this.leafComponentType.shortReadableName(), cArr);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] sourceName() {
        int i = this.dimensions;
        char[] cArr = new char[i * 2];
        for (int i2 = (i * 2) - 1; i2 >= 0; i2 -= 2) {
            cArr[i2] = ']';
            cArr[i2 - 1] = '[';
        }
        return CharOperation.concat(this.leafComponentType.sourceName(), cArr);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public void swapUnresolved(UnresolvedReferenceBinding unresolvedReferenceBinding, ReferenceBinding referenceBinding, LookupEnvironment lookupEnvironment) {
        if (this.leafComponentType == unresolvedReferenceBinding) {
            TypeBinding typeBindingConvertUnresolvedBinaryToRawType = lookupEnvironment.convertUnresolvedBinaryToRawType(referenceBinding);
            this.leafComponentType = typeBindingConvertUnresolvedBinaryToRawType;
            if (typeBindingConvertUnresolvedBinaryToRawType != referenceBinding) {
                this.f626id = lookupEnvironment.createArrayType(typeBindingConvertUnresolvedBinaryToRawType, this.dimensions, this.typeAnnotations).f626id;
            }
            this.tagBits |= this.leafComponentType.tagBits & 2305843010824306816L;
        }
    }

    public String toString() {
        return this.leafComponentType != null ? debugName() : "NULL TYPE ARRAY";
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
        return this.environment.createArrayType(this.leafComponentType, this.dimensions, this.environment.filterNullTypeAnnotations(this.typeAnnotations));
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding uncapture(Scope scope) {
        if ((this.tagBits & 2305843009213693952L) == 0) {
            return this;
        }
        return scope.environment().createArrayType(this.leafComponentType.uncapture(scope), this.dimensions, this.typeAnnotations);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public long updateTagBits() {
        if (this.leafComponentType != null) {
            this.tagBits |= this.leafComponentType.updateTagBits();
        }
        return super.updateTagBits();
    }
}
