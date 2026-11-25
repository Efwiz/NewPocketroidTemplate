package org.eclipse.jdt.internal.compiler.lookup;

import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.FieldDeclaration;
import org.eclipse.jdt.internal.compiler.ast.LambdaExpression;
import org.eclipse.jdt.internal.compiler.ast.ReferenceExpression;

/* JADX INFO: loaded from: classes14.dex */
public class SyntheticMethodBinding extends MethodBinding {
    public static final int ArrayClone = 15;
    public static final int ArrayConstructor = 14;
    public static final int BridgeMethod = 8;
    public static final int ConstructorAccess = 6;
    public static final int DeserializeLambda = 17;
    public static final int EnumValueOf = 10;
    public static final int EnumValues = 9;
    public static final int FactoryMethod = 16;
    public static final int FieldReadAccess = 1;
    public static final int FieldWriteAccess = 2;
    public static final int LambdaMethod = 13;
    public static final int MethodAccess = 5;
    public static final int SerializableMethodReference = 18;
    public static final int SuperFieldReadAccess = 3;
    public static final int SuperFieldWriteAccess = 4;
    public static final int SuperMethodAccess = 7;
    public static final int SwitchTable = 11;
    public static final int TooManyEnumsConstants = 12;
    public int endIndex;
    public int fakePaddedParameters;
    public int index;
    public LambdaExpression lambda;
    public int purpose;
    public ReferenceExpression serializableMethodRef;
    public int sourceStart;
    public int startIndex;
    public TypeBinding targetEnumType;
    public MethodBinding targetMethod;
    public FieldBinding targetReadField;
    public FieldBinding targetWriteField;

    /* JADX WARN: Code duplicated, block: B:42:0x00c6  */
    /* JADX WARN: Code duplicated, block: B:45:0x00cb  */
    /* JADX WARN: Code duplicated, block: B:81:0x00e8 A[SYNTHETIC] */
    public SyntheticMethodBinding(FieldBinding fieldBinding, boolean z, boolean z2, ReferenceBinding referenceBinding) throws Throwable {
        boolean z3;
        int length;
        int i;
        this.sourceStart = 0;
        this.fakePaddedParameters = 0;
        this.modifiers = 4104;
        this.tagBits |= 25769803776L;
        SourceTypeBinding sourceTypeBinding = (SourceTypeBinding) referenceBinding;
        SyntheticMethodBinding[] syntheticMethodBindingArrSyntheticMethods = sourceTypeBinding.syntheticMethods();
        int length2 = syntheticMethodBindingArrSyntheticMethods == null ? 0 : syntheticMethodBindingArrSyntheticMethods.length;
        this.index = length2;
        this.selector = CharOperation.concat(TypeConstants.SYNTHETIC_ACCESS_METHOD_PREFIX, String.valueOf(length2).toCharArray());
        if (z) {
            this.returnType = fieldBinding.type;
            if (fieldBinding.isStatic()) {
                this.parameters = Binding.NO_PARAMETERS;
            } else {
                this.parameters = new TypeBinding[1];
                this.parameters[0] = sourceTypeBinding;
            }
            this.targetReadField = fieldBinding;
            this.purpose = z2 ? 3 : 1;
        } else {
            this.returnType = TypeBinding.VOID;
            if (fieldBinding.isStatic()) {
                this.parameters = new TypeBinding[1];
                this.parameters[0] = fieldBinding.type;
            } else {
                this.parameters = new TypeBinding[2];
                this.parameters[0] = sourceTypeBinding;
                this.parameters[1] = fieldBinding.type;
            }
            this.targetWriteField = fieldBinding;
            this.purpose = z2 ? 4 : 2;
        }
        this.thrownExceptions = Binding.NO_EXCEPTIONS;
        this.declaringClass = sourceTypeBinding;
        do {
            MethodBinding[] methodBindingArrMethods = sourceTypeBinding.methods();
            long jBinarySearch = ReferenceBinding.binarySearch(this.selector, methodBindingArrMethods);
            if (jBinarySearch >= 0) {
                int length3 = this.parameters.length;
                int i2 = (int) jBinarySearch;
                int i3 = (int) (jBinarySearch >> 32);
                while (true) {
                    if (i2 > i3) {
                        if (syntheticMethodBindingArrSyntheticMethods != null) {
                            length = syntheticMethodBindingArrSyntheticMethods.length;
                            i = 0;
                            while (true) {
                                if (i >= length) {
                                    if (syntheticMethodBindingArrSyntheticMethods[i] == null || !CharOperation.equals(this.selector, syntheticMethodBindingArrSyntheticMethods[i].selector) || !areParametersEqual(methodBindingArrMethods[i])) {
                                        i++;
                                    }
                                }
                            }
                        }
                        z3 = false;
                    } else {
                        MethodBinding methodBinding = methodBindingArrMethods[i2];
                        if (methodBinding.parameters.length == length3) {
                            TypeBinding[] typeBindingArr = methodBinding.parameters;
                            int i4 = 0;
                            while (true) {
                                if (i4 < length3) {
                                    if (!TypeBinding.notEquals(typeBindingArr[i4], this.parameters[i4])) {
                                        i4++;
                                    }
                                }
                            }
                        }
                        i2++;
                    }
                    z3 = true;
                }
            } else {
                if (syntheticMethodBindingArrSyntheticMethods != null) {
                    length = syntheticMethodBindingArrSyntheticMethods.length;
                    i = 0;
                    while (true) {
                        if (i >= length) {
                            if (syntheticMethodBindingArrSyntheticMethods[i] == null) {
                                z3 = true;
                            }
                            i++;
                        }
                    }
                }
                z3 = false;
            }
            if (z3) {
                length2++;
                setSelector(CharOperation.concat(TypeConstants.SYNTHETIC_ACCESS_METHOD_PREFIX, String.valueOf(length2).toCharArray()));
            }
        } while (z3);
        FieldDeclaration[] fieldDeclarationArr = sourceTypeBinding.scope.referenceContext.fields;
        if (fieldDeclarationArr != null) {
            int length4 = fieldDeclarationArr.length;
            for (int i5 = 0; i5 < length4; i5++) {
                if (fieldDeclarationArr[i5].binding == fieldBinding) {
                    this.sourceStart = fieldDeclarationArr[i5].sourceStart;
                    return;
                }
            }
        }
        this.sourceStart = sourceTypeBinding.scope.referenceContext.sourceStart;
    }

    /* JADX WARN: Code duplicated, block: B:25:0x0090  */
    /* JADX WARN: Code duplicated, block: B:28:0x0095  */
    /* JADX WARN: Code duplicated, block: B:53:0x00b2 A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    public SyntheticMethodBinding(FieldBinding fieldBinding, ReferenceBinding referenceBinding, TypeBinding typeBinding, char[] cArr) throws Throwable {
        Object[] objArr;
        int length;
        int i;
        this.sourceStart = 0;
        this.fakePaddedParameters = 0;
        this.modifiers = (referenceBinding.isInterface() ? 1 : 0) | 4104;
        this.tagBits |= 25769803776L;
        SourceTypeBinding sourceTypeBinding = (SourceTypeBinding) referenceBinding;
        SyntheticMethodBinding[] syntheticMethodBindingArrSyntheticMethods = sourceTypeBinding.syntheticMethods();
        int length2 = syntheticMethodBindingArrSyntheticMethods == null ? 0 : syntheticMethodBindingArrSyntheticMethods.length;
        this.index = length2;
        this.selector = cArr;
        this.returnType = sourceTypeBinding.scope.createArrayType(TypeBinding.INT, 1);
        this.parameters = Binding.NO_PARAMETERS;
        this.targetReadField = fieldBinding;
        this.targetEnumType = typeBinding;
        this.purpose = 11;
        this.thrownExceptions = Binding.NO_EXCEPTIONS;
        this.declaringClass = sourceTypeBinding;
        if (sourceTypeBinding.isStrictfp()) {
            this.modifiers |= 2048;
        }
        do {
            MethodBinding[] methodBindingArrMethods = sourceTypeBinding.methods();
            long jBinarySearch = ReferenceBinding.binarySearch(this.selector, methodBindingArrMethods);
            if (jBinarySearch >= 0) {
                int length3 = this.parameters.length;
                int i2 = (int) jBinarySearch;
                int i3 = (int) (jBinarySearch >> 32);
                while (true) {
                    if (i2 > i3) {
                        if (syntheticMethodBindingArrSyntheticMethods != null) {
                            length = syntheticMethodBindingArrSyntheticMethods.length;
                            i = 0;
                            while (true) {
                                if (i >= length) {
                                    if (syntheticMethodBindingArrSyntheticMethods[i] == null || !CharOperation.equals(this.selector, syntheticMethodBindingArrSyntheticMethods[i].selector) || !areParametersEqual(methodBindingArrMethods[i])) {
                                        i++;
                                    }
                                }
                            }
                        }
                        objArr = false;
                    } else {
                        MethodBinding methodBinding = methodBindingArrMethods[i2];
                        if (methodBinding.parameters.length == length3) {
                            TypeBinding[] typeBindingArr = methodBinding.parameters;
                            int i4 = 0;
                            while (true) {
                                if (i4 < length3) {
                                    if (!TypeBinding.notEquals(typeBindingArr[i4], this.parameters[i4])) {
                                        i4++;
                                    }
                                }
                            }
                        }
                        i2++;
                    }
                    objArr = true;
                }
            } else {
                if (syntheticMethodBindingArrSyntheticMethods != null) {
                    length = syntheticMethodBindingArrSyntheticMethods.length;
                    i = 0;
                    while (true) {
                        if (i >= length) {
                            if (syntheticMethodBindingArrSyntheticMethods[i] == null) {
                                objArr = true;
                            }
                            i++;
                        }
                    }
                }
                objArr = false;
            }
            if (objArr != false) {
                length2++;
                setSelector(CharOperation.concat(cArr, String.valueOf(length2).toCharArray()));
            }
        } while (objArr != false);
        this.sourceStart = sourceTypeBinding.scope.referenceContext.sourceStart;
    }

    public SyntheticMethodBinding(MethodBinding methodBinding, boolean z, ReferenceBinding referenceBinding) throws Throwable {
        this.sourceStart = 0;
        this.fakePaddedParameters = 0;
        if (methodBinding.isConstructor()) {
            initializeConstructorAccessor(methodBinding);
        } else {
            initializeMethodAccessor(methodBinding, z, referenceBinding);
        }
    }

    public SyntheticMethodBinding(MethodBinding methodBinding, MethodBinding methodBinding2, SourceTypeBinding sourceTypeBinding) {
        this.sourceStart = 0;
        this.fakePaddedParameters = 0;
        this.declaringClass = sourceTypeBinding;
        this.selector = methodBinding.selector;
        this.modifiers = (methodBinding2.modifiers | 4160) & (-1073743153);
        this.tagBits |= 25769803776L;
        this.returnType = methodBinding.returnType;
        this.parameters = methodBinding.parameters;
        this.thrownExceptions = methodBinding.thrownExceptions;
        this.targetMethod = methodBinding2;
        this.purpose = 8;
        SyntheticMethodBinding[] syntheticMethodBindingArrSyntheticMethods = sourceTypeBinding.syntheticMethods();
        this.index = syntheticMethodBindingArrSyntheticMethods != null ? syntheticMethodBindingArrSyntheticMethods.length : 0;
    }

    public SyntheticMethodBinding(SourceTypeBinding sourceTypeBinding, char[] cArr) {
        this.sourceStart = 0;
        this.fakePaddedParameters = 0;
        this.declaringClass = sourceTypeBinding;
        this.selector = cArr;
        this.modifiers = 9;
        this.tagBits |= 25769803776L;
        LookupEnvironment lookupEnvironmentEnvironment = sourceTypeBinding.scope.environment();
        this.thrownExceptions = Binding.NO_EXCEPTIONS;
        if (cArr == TypeConstants.VALUES) {
            this.returnType = lookupEnvironmentEnvironment.createArrayType(lookupEnvironmentEnvironment.convertToParameterizedType(sourceTypeBinding), 1);
            this.parameters = Binding.NO_PARAMETERS;
            this.purpose = 9;
        } else if (cArr == TypeConstants.VALUEOF) {
            this.returnType = lookupEnvironmentEnvironment.convertToParameterizedType(sourceTypeBinding);
            this.parameters = new TypeBinding[]{sourceTypeBinding.scope.getJavaLangString()};
            this.purpose = 10;
        }
        SyntheticMethodBinding[] syntheticMethodBindingArrSyntheticMethods = ((SourceTypeBinding) this.declaringClass).syntheticMethods();
        this.index = syntheticMethodBindingArrSyntheticMethods != null ? syntheticMethodBindingArrSyntheticMethods.length : 0;
        if (sourceTypeBinding.isStrictfp()) {
            this.modifiers |= 2048;
        }
    }

    public SyntheticMethodBinding(SourceTypeBinding sourceTypeBinding) {
        this.sourceStart = 0;
        this.fakePaddedParameters = 0;
        this.declaringClass = sourceTypeBinding;
        this.selector = TypeConstants.DESERIALIZE_LAMBDA;
        this.modifiers = 4106;
        this.tagBits |= 25769803776L;
        this.thrownExceptions = Binding.NO_EXCEPTIONS;
        this.returnType = sourceTypeBinding.scope.getJavaLangObject();
        this.parameters = new TypeBinding[]{sourceTypeBinding.scope.getJavaLangInvokeSerializedLambda()};
        this.purpose = 17;
        SyntheticMethodBinding[] syntheticMethodBindingArrSyntheticMethods = sourceTypeBinding.syntheticMethods();
        this.index = syntheticMethodBindingArrSyntheticMethods != null ? syntheticMethodBindingArrSyntheticMethods.length : 0;
    }

    public SyntheticMethodBinding(SourceTypeBinding sourceTypeBinding, int i, int i2) {
        this.sourceStart = 0;
        this.fakePaddedParameters = 0;
        this.declaringClass = sourceTypeBinding;
        SyntheticMethodBinding[] syntheticMethodBindingArrSyntheticMethods = sourceTypeBinding.syntheticMethods();
        this.index = syntheticMethodBindingArrSyntheticMethods != null ? syntheticMethodBindingArrSyntheticMethods.length : 0;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(TypeConstants.SYNTHETIC_ENUM_CONSTANT_INITIALIZATION_METHOD_PREFIX).append(this.index);
        this.selector = String.valueOf(stringBuffer).toCharArray();
        this.modifiers = 10;
        this.tagBits |= 25769803776L;
        this.purpose = 12;
        this.thrownExceptions = Binding.NO_EXCEPTIONS;
        this.returnType = TypeBinding.VOID;
        this.parameters = Binding.NO_PARAMETERS;
        this.startIndex = i;
        this.endIndex = i2;
    }

    public SyntheticMethodBinding(MethodBinding methodBinding, SourceTypeBinding sourceTypeBinding) {
        this.sourceStart = 0;
        this.fakePaddedParameters = 0;
        this.declaringClass = sourceTypeBinding;
        this.selector = methodBinding.selector;
        this.modifiers = (methodBinding.modifiers | 4160) & (-1073743153);
        this.tagBits |= 25769803776L;
        this.returnType = methodBinding.returnType;
        this.parameters = methodBinding.parameters;
        this.thrownExceptions = methodBinding.thrownExceptions;
        this.targetMethod = methodBinding;
        this.purpose = 7;
        SyntheticMethodBinding[] syntheticMethodBindingArrSyntheticMethods = sourceTypeBinding.syntheticMethods();
        this.index = syntheticMethodBindingArrSyntheticMethods != null ? syntheticMethodBindingArrSyntheticMethods.length : 0;
    }

    public SyntheticMethodBinding(int i, ArrayBinding arrayBinding, char[] cArr, SourceTypeBinding sourceTypeBinding) {
        this.sourceStart = 0;
        this.fakePaddedParameters = 0;
        this.declaringClass = sourceTypeBinding;
        this.selector = cArr;
        this.modifiers = 4106;
        this.tagBits |= 25769803776L;
        this.returnType = arrayBinding;
        LookupEnvironment lookupEnvironment = sourceTypeBinding.environment;
        if (lookupEnvironment.globalOptions.isAnnotationBasedNullAnalysisEnabled) {
            if (lookupEnvironment.usesNullTypeAnnotations()) {
                this.returnType = lookupEnvironment.createAnnotatedType(this.returnType, new AnnotationBinding[]{lookupEnvironment.getNonNullAnnotation()});
            } else {
                this.tagBits |= 72057594037927936L;
            }
        }
        TypeBinding[] typeBindingArr = new TypeBinding[1];
        typeBindingArr[0] = i == 14 ? TypeBinding.INT : arrayBinding;
        this.parameters = typeBindingArr;
        this.thrownExceptions = Binding.NO_EXCEPTIONS;
        this.purpose = i;
        SyntheticMethodBinding[] syntheticMethodBindingArrSyntheticMethods = sourceTypeBinding.syntheticMethods();
        this.index = syntheticMethodBindingArrSyntheticMethods != null ? syntheticMethodBindingArrSyntheticMethods.length : 0;
    }

    public SyntheticMethodBinding(LambdaExpression lambdaExpression, char[] cArr, SourceTypeBinding sourceTypeBinding) {
        this.sourceStart = 0;
        this.fakePaddedParameters = 0;
        this.lambda = lambdaExpression;
        this.declaringClass = sourceTypeBinding;
        this.selector = cArr;
        this.modifiers = lambdaExpression.binding.modifiers;
        this.tagBits |= (lambdaExpression.binding.tagBits & 1024) | 25769803776L;
        this.returnType = lambdaExpression.binding.returnType;
        this.parameters = lambdaExpression.binding.parameters;
        this.thrownExceptions = lambdaExpression.binding.thrownExceptions;
        this.purpose = 13;
        SyntheticMethodBinding[] syntheticMethodBindingArrSyntheticMethods = sourceTypeBinding.syntheticMethods();
        this.index = syntheticMethodBindingArrSyntheticMethods != null ? syntheticMethodBindingArrSyntheticMethods.length : 0;
    }

    public SyntheticMethodBinding(ReferenceExpression referenceExpression, SourceTypeBinding sourceTypeBinding) {
        this.sourceStart = 0;
        this.fakePaddedParameters = 0;
        this.serializableMethodRef = referenceExpression;
        this.declaringClass = sourceTypeBinding;
        this.selector = referenceExpression.binding.selector;
        this.modifiers = referenceExpression.binding.modifiers;
        this.tagBits |= (referenceExpression.binding.tagBits & 1024) | 25769803776L;
        this.returnType = referenceExpression.binding.returnType;
        this.parameters = referenceExpression.binding.parameters;
        this.thrownExceptions = referenceExpression.binding.thrownExceptions;
        this.purpose = 18;
        SyntheticMethodBinding[] syntheticMethodBindingArrSyntheticMethods = sourceTypeBinding.syntheticMethods();
        this.index = syntheticMethodBindingArrSyntheticMethods != null ? syntheticMethodBindingArrSyntheticMethods.length : 0;
    }

    public SyntheticMethodBinding(MethodBinding methodBinding, MethodBinding methodBinding2, char[] cArr, TypeBinding[] typeBindingArr, SourceTypeBinding sourceTypeBinding) {
        this.sourceStart = 0;
        this.fakePaddedParameters = 0;
        this.declaringClass = sourceTypeBinding;
        this.selector = cArr;
        this.modifiers = 4106;
        this.tagBits |= 25769803776L;
        this.returnType = methodBinding2.declaringClass;
        int length = methodBinding.parameters.length;
        int length2 = typeBindingArr.length;
        this.parameters = new TypeBinding[length2 + length];
        System.arraycopy(typeBindingArr, 0, this.parameters, 0, length2);
        System.arraycopy(methodBinding.parameters, 0, this.parameters, length2, length);
        this.fakePaddedParameters = methodBinding2.parameters.length - length;
        this.thrownExceptions = methodBinding2.thrownExceptions;
        this.purpose = 16;
        this.targetMethod = methodBinding2;
        SyntheticMethodBinding[] syntheticMethodBindingArrSyntheticMethods = sourceTypeBinding.syntheticMethods();
        this.index = syntheticMethodBindingArrSyntheticMethods != null ? syntheticMethodBindingArrSyntheticMethods.length : 0;
    }

    public void initializeConstructorAccessor(MethodBinding methodBinding) throws Throwable {
        boolean z;
        this.targetMethod = methodBinding;
        this.modifiers = 4096;
        this.tagBits |= 25769803776L;
        SourceTypeBinding sourceTypeBinding = (SourceTypeBinding) methodBinding.declaringClass;
        SyntheticMethodBinding[] syntheticMethodBindingArrSyntheticMethods = sourceTypeBinding.syntheticMethods();
        this.index = syntheticMethodBindingArrSyntheticMethods == null ? 0 : syntheticMethodBindingArrSyntheticMethods.length;
        this.selector = methodBinding.selector;
        this.returnType = methodBinding.returnType;
        this.purpose = 6;
        int length = methodBinding.parameters.length;
        this.parameters = new TypeBinding[length + 1];
        System.arraycopy(methodBinding.parameters, 0, this.parameters, 0, length);
        this.parameters[length] = methodBinding.declaringClass;
        this.thrownExceptions = methodBinding.thrownExceptions;
        this.declaringClass = sourceTypeBinding;
        do {
            MethodBinding[] methodBindingArrMethods = sourceTypeBinding.methods();
            int length2 = methodBindingArrMethods.length;
            int i = 0;
            while (true) {
                z = true;
                if (i < length2) {
                    if (CharOperation.equals(this.selector, methodBindingArrMethods[i].selector) && areParameterErasuresEqual(methodBindingArrMethods[i])) {
                        break;
                    } else {
                        i++;
                    }
                } else {
                    if (syntheticMethodBindingArrSyntheticMethods == null) {
                        z = false;
                        break;
                    }
                    int length3 = syntheticMethodBindingArrSyntheticMethods.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length3) {
                            z = false;
                            break;
                        } else if (syntheticMethodBindingArrSyntheticMethods[i2] != null && CharOperation.equals(this.selector, syntheticMethodBindingArrSyntheticMethods[i2].selector) && areParameterErasuresEqual(syntheticMethodBindingArrSyntheticMethods[i2])) {
                            break;
                        } else {
                            i2++;
                        }
                    }
                }
            }
            if (z) {
                int length4 = this.parameters.length;
                TypeBinding[] typeBindingArr = this.parameters;
                TypeBinding[] typeBindingArr2 = new TypeBinding[length4 + 1];
                this.parameters = typeBindingArr2;
                System.arraycopy(typeBindingArr, 0, typeBindingArr2, 0, length4);
                this.parameters[length4] = this.declaringClass;
            }
        } while (z);
        AbstractMethodDeclaration[] abstractMethodDeclarationArr = sourceTypeBinding.scope.referenceContext.methods;
        if (abstractMethodDeclarationArr != null) {
            int length5 = abstractMethodDeclarationArr.length;
            for (int i3 = 0; i3 < length5; i3++) {
                if (abstractMethodDeclarationArr[i3].binding == methodBinding) {
                    this.sourceStart = abstractMethodDeclarationArr[i3].sourceStart;
                    return;
                }
            }
        }
    }

    public void initializeMethodAccessor(MethodBinding methodBinding, boolean z, ReferenceBinding referenceBinding) throws Throwable {
        boolean z2;
        this.targetMethod = methodBinding;
        if (z && referenceBinding.isInterface() && !methodBinding.isStatic()) {
            this.modifiers = 4098;
        } else {
            this.modifiers = 4104;
        }
        this.tagBits |= 25769803776L;
        SourceTypeBinding sourceTypeBinding = (SourceTypeBinding) referenceBinding;
        SyntheticMethodBinding[] syntheticMethodBindingArrSyntheticMethods = sourceTypeBinding.syntheticMethods();
        int length = syntheticMethodBindingArrSyntheticMethods == null ? 0 : syntheticMethodBindingArrSyntheticMethods.length;
        this.index = length;
        this.selector = CharOperation.concat(TypeConstants.SYNTHETIC_ACCESS_METHOD_PREFIX, String.valueOf(length).toCharArray());
        this.returnType = methodBinding.returnType;
        this.purpose = z ? 7 : 5;
        if (methodBinding.isStatic() || (z && referenceBinding.isInterface())) {
            this.parameters = methodBinding.parameters;
        } else {
            this.parameters = new TypeBinding[methodBinding.parameters.length + 1];
            this.parameters[0] = sourceTypeBinding;
            System.arraycopy(methodBinding.parameters, 0, this.parameters, 1, methodBinding.parameters.length);
        }
        this.thrownExceptions = methodBinding.thrownExceptions;
        this.declaringClass = sourceTypeBinding;
        do {
            MethodBinding[] methodBindingArrMethods = sourceTypeBinding.methods();
            int length2 = methodBindingArrMethods.length;
            int i = 0;
            while (true) {
                if (i >= length2) {
                    if (syntheticMethodBindingArrSyntheticMethods != null) {
                        int length3 = syntheticMethodBindingArrSyntheticMethods.length;
                        int i2 = 0;
                        while (true) {
                            if (i2 < length3) {
                                if (syntheticMethodBindingArrSyntheticMethods[i2] == null || !CharOperation.equals(this.selector, syntheticMethodBindingArrSyntheticMethods[i2].selector) || !areParameterErasuresEqual(syntheticMethodBindingArrSyntheticMethods[i2])) {
                                    i2++;
                                }
                            }
                        }
                    }
                    z2 = false;
                    break;
                }
                if (!CharOperation.equals(this.selector, methodBindingArrMethods[i].selector) || !areParameterErasuresEqual(methodBindingArrMethods[i])) {
                    i++;
                }
                z2 = true;
                break;
            }
            if (z2) {
                length++;
                setSelector(CharOperation.concat(TypeConstants.SYNTHETIC_ACCESS_METHOD_PREFIX, String.valueOf(length).toCharArray()));
            }
        } while (z2);
        AbstractMethodDeclaration[] abstractMethodDeclarationArr = sourceTypeBinding.scope.referenceContext.methods;
        if (abstractMethodDeclarationArr != null) {
            int length4 = abstractMethodDeclarationArr.length;
            for (int i3 = 0; i3 < length4; i3++) {
                if (abstractMethodDeclarationArr[i3].binding == methodBinding) {
                    this.sourceStart = abstractMethodDeclarationArr[i3].sourceStart;
                    return;
                }
            }
        }
    }

    protected boolean isConstructorRelated() {
        return this.purpose == 6;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodBinding
    public LambdaExpression sourceLambda() {
        return this.lambda;
    }

    public void markNonNull(LookupEnvironment lookupEnvironment) {
        markNonNull(this, this.purpose, lookupEnvironment);
    }

    static void markNonNull(MethodBinding methodBinding, int i, LookupEnvironment lookupEnvironment) {
        if (i != 9) {
            if (i != 10) {
                return;
            }
            if (lookupEnvironment.usesNullTypeAnnotations()) {
                methodBinding.returnType = lookupEnvironment.createAnnotatedType(methodBinding.returnType, new AnnotationBinding[]{lookupEnvironment.getNonNullAnnotation()});
                return;
            } else {
                methodBinding.tagBits |= 72057594037927936L;
                return;
            }
        }
        if (lookupEnvironment.usesNullTypeAnnotations()) {
            TypeBinding typeBindingLeafComponentType = ((ArrayBinding) methodBinding.returnType).leafComponentType();
            AnnotationBinding nonNullAnnotation = lookupEnvironment.getNonNullAnnotation();
            TypeBinding typeBindingCreateAnnotatedType = lookupEnvironment.createAnnotatedType(typeBindingLeafComponentType, new AnnotationBinding[]{lookupEnvironment.getNonNullAnnotation()});
            AnnotationBinding[] annotationBindingArr = new AnnotationBinding[2];
            annotationBindingArr[0] = nonNullAnnotation;
            methodBinding.returnType = lookupEnvironment.createArrayType(typeBindingCreateAnnotatedType, 1, annotationBindingArr);
            return;
        }
        methodBinding.tagBits |= 72057594037927936L;
    }
}
