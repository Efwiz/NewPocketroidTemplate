package org.eclipse.jdt.internal.compiler.lookup;

import java.util.ArrayList;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jdt.internal.compiler.classfmt.NonNullDefaultAwareTypeAnnotationWalker;
import org.eclipse.jdt.internal.compiler.classfmt.TypeAnnotationWalker;
import org.eclipse.jdt.internal.compiler.codegen.ConstantPool;
import org.eclipse.jdt.internal.compiler.env.ClassSignature;
import org.eclipse.jdt.internal.compiler.env.EnumConstantSignature;
import org.eclipse.jdt.internal.compiler.env.IBinaryAnnotation;
import org.eclipse.jdt.internal.compiler.env.IBinaryElementValuePair;
import org.eclipse.jdt.internal.compiler.env.IBinaryField;
import org.eclipse.jdt.internal.compiler.env.IBinaryMethod;
import org.eclipse.jdt.internal.compiler.env.IBinaryNestedType;
import org.eclipse.jdt.internal.compiler.env.IBinaryType;
import org.eclipse.jdt.internal.compiler.env.IBinaryTypeAnnotation;
import org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.problem.AbortCompilation;
import org.eclipse.jdt.internal.compiler.util.SimpleLookupTable;

/* JADX INFO: loaded from: classes14.dex */
public class BinaryTypeBinding extends ReferenceBinding {
    private static final IBinaryMethod[] NO_BINARY_METHODS = new IBinaryMethod[0];
    private ReferenceBinding containerAnnotationType;
    int defaultNullness;
    protected ReferenceBinding enclosingType;
    protected LookupEnvironment environment;
    public ExternalAnnotationStatus externalAnnotationStatus;
    protected FieldBinding[] fields;
    protected ReferenceBinding[] memberTypes;
    protected MethodBinding[] methods;
    private BinaryTypeBinding prototype;
    protected SimpleLookupTable storedAnnotations;
    protected ReferenceBinding[] superInterfaces;
    protected ReferenceBinding superclass;
    protected TypeVariableBinding[] typeVariables;

    public enum ExternalAnnotationStatus {
        FROM_SOURCE,
        NOT_EEA_CONFIGURED,
        NO_EEA_FILE,
        TYPE_IS_ANNOTATED;

        private static /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$lookup$BinaryTypeBinding$ExternalAnnotationStatus;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static ExternalAnnotationStatus[] valuesCustom() {
            ExternalAnnotationStatus[] externalAnnotationStatusArrValuesCustom = values();
            int length = externalAnnotationStatusArrValuesCustom.length;
            ExternalAnnotationStatus[] externalAnnotationStatusArr = new ExternalAnnotationStatus[length];
            System.arraycopy(externalAnnotationStatusArrValuesCustom, 0, externalAnnotationStatusArr, 0, length);
            return externalAnnotationStatusArr;
        }

        static /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$lookup$BinaryTypeBinding$ExternalAnnotationStatus() {
            int[] iArr = $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$lookup$BinaryTypeBinding$ExternalAnnotationStatus;
            if (iArr != null) {
                return iArr;
            }
            int[] iArr2 = new int[valuesCustom().length];
            try {
                iArr2[FROM_SOURCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr2[NOT_EEA_CONFIGURED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr2[NO_EEA_FILE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr2[TYPE_IS_ANNOTATED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$lookup$BinaryTypeBinding$ExternalAnnotationStatus = iArr2;
            return iArr2;
        }

        public boolean isPotentiallyUnannotatedLib() {
            int i = $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$lookup$BinaryTypeBinding$ExternalAnnotationStatus()[ordinal()];
            return (i == 1 || i == 4) ? false : true;
        }
    }

    static Object convertMemberValue(Object obj, LookupEnvironment lookupEnvironment, char[][][] cArr, boolean z) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Constant) {
            return obj;
        }
        if (obj instanceof ClassSignature) {
            return lookupEnvironment.getTypeFromSignature(((ClassSignature) obj).getTypeName(), 0, -1, false, null, cArr, ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER);
        }
        if (obj instanceof IBinaryAnnotation) {
            return createAnnotation((IBinaryAnnotation) obj, lookupEnvironment, cArr);
        }
        if (obj instanceof EnumConstantSignature) {
            EnumConstantSignature enumConstantSignature = (EnumConstantSignature) obj;
            ReferenceBinding referenceBinding = (ReferenceBinding) lookupEnvironment.getTypeFromSignature(enumConstantSignature.getTypeName(), 0, -1, false, null, cArr, ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER);
            if (referenceBinding.isUnresolvedType() && !z) {
                return new ElementValuePair.UnresolvedEnumConstant(referenceBinding, lookupEnvironment, enumConstantSignature.getEnumConstantName());
            }
            return ((ReferenceBinding) resolveType(referenceBinding, lookupEnvironment, false)).getField(enumConstantSignature.getEnumConstantName(), false);
        }
        if (obj instanceof Object[]) {
            Object[] objArr = (Object[]) obj;
            int length = objArr.length;
            if (length == 0) {
                return objArr;
            }
            Object[] objArr2 = new Object[length];
            for (int i = 0; i < length; i++) {
                objArr2[i] = convertMemberValue(objArr[i], lookupEnvironment, cArr, z);
            }
            return objArr2;
        }
        throw new IllegalStateException();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding clone(TypeBinding typeBinding) {
        BinaryTypeBinding binaryTypeBinding = new BinaryTypeBinding(this);
        ReferenceBinding referenceBinding = (ReferenceBinding) typeBinding;
        binaryTypeBinding.enclosingType = referenceBinding;
        if (referenceBinding != null) {
            binaryTypeBinding.tagBits |= TagBits.HasUnresolvedEnclosingType;
        } else {
            binaryTypeBinding.tagBits &= -134217729;
        }
        binaryTypeBinding.tagBits |= TagBits.HasUnresolvedMemberTypes;
        return binaryTypeBinding;
    }

    static AnnotationBinding createAnnotation(IBinaryAnnotation iBinaryAnnotation, LookupEnvironment lookupEnvironment, char[][][] cArr) {
        IBinaryElementValuePair[] elementValuePairs = iBinaryAnnotation.getElementValuePairs();
        int length = elementValuePairs == null ? 0 : elementValuePairs.length;
        ElementValuePair[] elementValuePairArr = length == 0 ? Binding.NO_ELEMENT_VALUE_PAIRS : new ElementValuePair[length];
        for (int i = 0; i < length; i++) {
            elementValuePairArr[i] = new ElementValuePair(elementValuePairs[i].getName(), convertMemberValue(elementValuePairs[i].getValue(), lookupEnvironment, cArr, false), (MethodBinding) null);
        }
        char[] typeName = iBinaryAnnotation.getTypeName();
        return lookupEnvironment.createUnresolvedAnnotation(lookupEnvironment.getTypeFromConstantPoolName(typeName, 1, typeName.length - 1, false, cArr), elementValuePairArr);
    }

    public static AnnotationBinding[] createAnnotations(IBinaryAnnotation[] iBinaryAnnotationArr, LookupEnvironment lookupEnvironment, char[][][] cArr) {
        int length = iBinaryAnnotationArr == null ? 0 : iBinaryAnnotationArr.length;
        AnnotationBinding[] annotationBindingArr = length == 0 ? Binding.NO_ANNOTATIONS : new AnnotationBinding[length];
        for (int i = 0; i < length; i++) {
            annotationBindingArr[i] = createAnnotation(iBinaryAnnotationArr[i], lookupEnvironment, cArr);
        }
        return annotationBindingArr;
    }

    public static TypeBinding resolveType(TypeBinding typeBinding, LookupEnvironment lookupEnvironment, boolean z) {
        int iKind = typeBinding.kind();
        if (iKind == 68) {
            ArrayBinding arrayBinding = (ArrayBinding) typeBinding;
            TypeBinding typeBinding2 = arrayBinding.leafComponentType;
            resolveType(typeBinding2, lookupEnvironment, z);
            if (typeBinding2.hasNullTypeAnnotations() && lookupEnvironment.usesNullTypeAnnotations()) {
                if (arrayBinding.nullTagBitsPerDimension == null) {
                    arrayBinding.nullTagBitsPerDimension = new long[arrayBinding.dimensions + 1];
                }
                arrayBinding.nullTagBitsPerDimension[arrayBinding.dimensions] = typeBinding2.tagBits & TagBits.AnnotationNullMASK;
            }
        } else if (iKind == 260) {
            ((ParameterizedTypeBinding) typeBinding).resolve();
        } else {
            if (iKind != 516) {
                if (iKind != 2052) {
                    if (iKind == 4100) {
                        ((TypeVariableBinding) typeBinding).resolve();
                    } else if (iKind != 8196) {
                        if (typeBinding instanceof UnresolvedReferenceBinding) {
                            return ((UnresolvedReferenceBinding) typeBinding).resolve(lookupEnvironment, z);
                        }
                        if (z) {
                            return lookupEnvironment.convertUnresolvedBinaryToRawType(typeBinding);
                        }
                    }
                } else if (z) {
                    return lookupEnvironment.convertUnresolvedBinaryToRawType(typeBinding);
                }
            }
            return ((WildcardBinding) typeBinding).resolve();
        }
        return typeBinding;
    }

    protected BinaryTypeBinding() {
        this.storedAnnotations = null;
        this.defaultNullness = 0;
        this.externalAnnotationStatus = ExternalAnnotationStatus.NOT_EEA_CONFIGURED;
        this.prototype = this;
    }

    public BinaryTypeBinding(BinaryTypeBinding binaryTypeBinding) {
        super(binaryTypeBinding);
        this.storedAnnotations = null;
        this.defaultNullness = 0;
        this.externalAnnotationStatus = ExternalAnnotationStatus.NOT_EEA_CONFIGURED;
        this.superclass = binaryTypeBinding.superclass;
        this.enclosingType = binaryTypeBinding.enclosingType;
        this.superInterfaces = binaryTypeBinding.superInterfaces;
        this.fields = binaryTypeBinding.fields;
        this.methods = binaryTypeBinding.methods;
        this.memberTypes = binaryTypeBinding.memberTypes;
        this.typeVariables = binaryTypeBinding.typeVariables;
        this.prototype = binaryTypeBinding.prototype;
        this.environment = binaryTypeBinding.environment;
        this.storedAnnotations = binaryTypeBinding.storedAnnotations;
    }

    public BinaryTypeBinding(PackageBinding packageBinding, IBinaryType iBinaryType, LookupEnvironment lookupEnvironment) {
        this(packageBinding, iBinaryType, lookupEnvironment, false);
    }

    public BinaryTypeBinding(PackageBinding packageBinding, IBinaryType iBinaryType, LookupEnvironment lookupEnvironment, boolean z) {
        this.storedAnnotations = null;
        this.defaultNullness = 0;
        this.externalAnnotationStatus = ExternalAnnotationStatus.NOT_EEA_CONFIGURED;
        this.prototype = this;
        this.compoundName = CharOperation.splitOn('/', iBinaryType.getName());
        computeId();
        this.tagBits |= 64;
        this.environment = lookupEnvironment;
        this.fPackage = packageBinding;
        this.fileName = iBinaryType.getFileName();
        char[] genericSignature = iBinaryType.getGenericSignature();
        this.typeVariables = (genericSignature == null || genericSignature.length <= 0 || genericSignature[0] != '<') ? Binding.NO_TYPE_VARIABLES : null;
        this.sourceName = iBinaryType.getSourceName();
        this.modifiers = iBinaryType.getModifiers();
        if ((iBinaryType.getTagBits() & 131072) != 0) {
            this.tagBits |= 131072;
        }
        if (iBinaryType.isAnonymous()) {
            this.tagBits |= TagBits.AnonymousTypeMask;
        } else if (iBinaryType.isLocal()) {
            this.tagBits |= TagBits.LocalTypeMask;
        } else if (iBinaryType.isMember()) {
            this.tagBits |= TagBits.MemberTypeMask;
        }
        char[] enclosingTypeName = iBinaryType.getEnclosingTypeName();
        if (enclosingTypeName != null) {
            this.enclosingType = lookupEnvironment.getTypeFromConstantPoolName(enclosingTypeName, 0, -1, true, null);
            this.tagBits = TagBits.MemberTypeMask | this.tagBits;
            this.tagBits |= TagBits.HasUnresolvedEnclosingType;
            if (enclosingType().isStrictfp()) {
                this.modifiers |= 2048;
            }
            if (enclosingType().isDeprecated()) {
                this.modifiers |= 2097152;
            }
        }
        if (z) {
            cachePartsFrom(iBinaryType, true);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public FieldBinding[] availableFields() {
        if (!isPrototype()) {
            return this.prototype.availableFields();
        }
        if ((this.tagBits & 8192) != 0) {
            return this.fields;
        }
        if ((this.tagBits & 4096) == 0) {
            FieldBinding[] fieldBindingArr = this.fields;
            int length = fieldBindingArr.length;
            if (length > 1) {
                ReferenceBinding.sortFields(fieldBindingArr, 0, length);
            }
            this.tagBits |= 4096;
        }
        int length2 = this.fields.length;
        FieldBinding[] fieldBindingArr2 = new FieldBinding[length2];
        int i = 0;
        int i2 = 0;
        while (true) {
            FieldBinding[] fieldBindingArr3 = this.fields;
            if (i >= fieldBindingArr3.length) {
                break;
            }
            try {
                fieldBindingArr2[i2] = resolveTypeFor(fieldBindingArr3[i]);
                i2++;
            } catch (AbortCompilation unused) {
            }
            i++;
        }
        if (i2 >= length2) {
            return fieldBindingArr2;
        }
        FieldBinding[] fieldBindingArr4 = new FieldBinding[i2];
        System.arraycopy(fieldBindingArr2, 0, fieldBindingArr4, 0, i2);
        return fieldBindingArr4;
    }

    private TypeVariableBinding[] addMethodTypeVariables(TypeVariableBinding[] typeVariableBindingArr) {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        TypeVariableBinding[] typeVariableBindingArr2 = this.typeVariables;
        if (typeVariableBindingArr2 == null || typeVariableBindingArr2 == Binding.NO_TYPE_VARIABLES) {
            return typeVariableBindingArr;
        }
        if (typeVariableBindingArr == null || typeVariableBindingArr == Binding.NO_TYPE_VARIABLES) {
            return this.typeVariables;
        }
        TypeVariableBinding[] typeVariableBindingArr3 = this.typeVariables;
        int length = typeVariableBindingArr3.length + typeVariableBindingArr.length;
        TypeVariableBinding[] typeVariableBindingArr4 = new TypeVariableBinding[length];
        System.arraycopy(typeVariableBindingArr3, 0, typeVariableBindingArr4, 0, typeVariableBindingArr3.length);
        int length2 = this.typeVariables.length;
        int length3 = typeVariableBindingArr.length;
        for (int i = 0; i < length3; i++) {
            int length4 = this.typeVariables.length - 1;
            while (true) {
                if (length4 >= 0) {
                    if (CharOperation.equals(typeVariableBindingArr[i].sourceName, this.typeVariables[length4].sourceName)) {
                        break;
                    }
                    length4--;
                } else {
                    typeVariableBindingArr4[length2] = typeVariableBindingArr[i];
                    length2++;
                    break;
                }
            }
        }
        if (length2 == length) {
            return typeVariableBindingArr4;
        }
        TypeVariableBinding[] typeVariableBindingArr5 = new TypeVariableBinding[length2];
        System.arraycopy(typeVariableBindingArr4, 0, typeVariableBindingArr5, 0, length2);
        return typeVariableBindingArr5;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public MethodBinding[] availableMethods() {
        if (!isPrototype()) {
            return this.prototype.availableMethods();
        }
        if ((this.tagBits & 32768) != 0) {
            return this.methods;
        }
        if ((this.tagBits & 16384) == 0) {
            MethodBinding[] methodBindingArr = this.methods;
            int length = methodBindingArr.length;
            if (length > 1) {
                ReferenceBinding.sortMethods(methodBindingArr, 0, length);
            }
            this.tagBits |= 16384;
        }
        int length2 = this.methods.length;
        MethodBinding[] methodBindingArr2 = new MethodBinding[length2];
        int i = 0;
        int i2 = 0;
        while (true) {
            MethodBinding[] methodBindingArr3 = this.methods;
            if (i >= methodBindingArr3.length) {
                break;
            }
            try {
                methodBindingArr2[i2] = resolveTypesFor(methodBindingArr3[i]);
                i2++;
            } catch (AbortCompilation unused) {
            }
            i++;
        }
        if (i2 >= length2) {
            return methodBindingArr2;
        }
        MethodBinding[] methodBindingArr4 = new MethodBinding[i2];
        System.arraycopy(methodBindingArr2, 0, methodBindingArr4, 0, i2);
        return methodBindingArr4;
    }

    void cachePartsFrom(IBinaryType iBinaryType, boolean z) {
        SignatureWrapper signatureWrapper;
        char[][][] cArr;
        int length;
        MethodBinding methodBindingFindMethod;
        int length2;
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        try {
            this.typeVariables = Binding.NO_TYPE_VARIABLES;
            this.superInterfaces = Binding.NO_SUPERINTERFACES;
            this.memberTypes = Binding.NO_MEMBER_TYPES;
            IBinaryNestedType[] memberTypes = iBinaryType.getMemberTypes();
            if (memberTypes != null && (length2 = memberTypes.length) > 0) {
                this.memberTypes = new ReferenceBinding[length2];
                for (int i = 0; i < length2; i++) {
                    this.memberTypes[i] = this.environment.getTypeFromConstantPoolName(memberTypes[i].getName(), 0, -1, false, null);
                }
                this.tagBits |= TagBits.HasUnresolvedMemberTypes;
            }
            long j = this.environment.globalOptions.originalSourceLevel;
            if (this.environment.globalOptions.isAnnotationBasedNullAnalysisEnabled) {
                scanTypeForNullDefaultAnnotation(iBinaryType, this.fPackage);
            }
            ITypeAnnotationWalker typeAnnotationWalker = getTypeAnnotationWalker(iBinaryType.getTypeAnnotations(), 0);
            ITypeAnnotationWalker iTypeAnnotationWalkerEnrichWithExternalAnnotationsFor = iBinaryType.enrichWithExternalAnnotationsFor(typeAnnotationWalker, null, this.environment);
            ExternalAnnotationStatus externalAnnotationStatus = iBinaryType.getExternalAnnotationStatus();
            this.externalAnnotationStatus = externalAnnotationStatus;
            if (externalAnnotationStatus.isPotentiallyUnannotatedLib() && this.defaultNullness != 0) {
                this.externalAnnotationStatus = ExternalAnnotationStatus.TYPE_IS_ANNOTATED;
            }
            char[] genericSignature = iBinaryType.getGenericSignature();
            this.tagBits |= iBinaryType.getTagBits();
            char[][][] missingTypeNames = iBinaryType.getMissingTypeNames();
            if (genericSignature != null) {
                signatureWrapper = new SignatureWrapper(genericSignature);
                if (signatureWrapper.signature[signatureWrapper.start] == '<') {
                    signatureWrapper.start++;
                    this.typeVariables = createTypeVariables(signatureWrapper, true, missingTypeNames, iTypeAnnotationWalkerEnrichWithExternalAnnotationsFor, true);
                    signatureWrapper.start++;
                    this.tagBits |= TagBits.HasUnresolvedTypeVariables;
                    this.modifiers |= 1073741824;
                }
            } else {
                signatureWrapper = null;
            }
            TypeVariableBinding[] typeVariableBindingArr = Binding.NO_TYPE_VARIABLES;
            char[] enclosingMethod = iBinaryType.getEnclosingMethod();
            if (enclosingMethod != null && (methodBindingFindMethod = findMethod(enclosingMethod, missingTypeNames)) != null) {
                typeVariableBindingArr = methodBindingFindMethod.typeVariables;
                this.typeVariables = addMethodTypeVariables(typeVariableBindingArr);
            }
            TypeVariableBinding[] typeVariableBindingArr2 = typeVariableBindingArr;
            if (genericSignature == null) {
                char[] superclassName = iBinaryType.getSuperclassName();
                if (superclassName != null) {
                    cArr = missingTypeNames;
                    this.superclass = this.environment.getTypeFromConstantPoolName(superclassName, 0, -1, false, cArr, iTypeAnnotationWalkerEnrichWithExternalAnnotationsFor.toSupertype((short) -1, superclassName));
                    this.tagBits |= TagBits.HasUnresolvedSuperclass;
                } else {
                    cArr = missingTypeNames;
                }
                this.superInterfaces = Binding.NO_SUPERINTERFACES;
                char[][] interfaceNames = iBinaryType.getInterfaceNames();
                if (interfaceNames != null && (length = interfaceNames.length) > 0) {
                    this.superInterfaces = new ReferenceBinding[length];
                    for (short s = 0; s < length; s = (short) (s + 1)) {
                        this.superInterfaces[s] = this.environment.getTypeFromConstantPoolName(interfaceNames[s], 0, -1, false, cArr, iTypeAnnotationWalkerEnrichWithExternalAnnotationsFor.toSupertype(s, superclassName));
                    }
                    this.tagBits |= TagBits.HasUnresolvedSuperinterfaces;
                }
            } else {
                cArr = missingTypeNames;
                this.superclass = (ReferenceBinding) this.environment.getTypeFromTypeSignature(signatureWrapper, typeVariableBindingArr2, this, cArr, iTypeAnnotationWalkerEnrichWithExternalAnnotationsFor.toSupertype((short) -1, signatureWrapper.peekFullType()));
                this.tagBits |= TagBits.HasUnresolvedSuperclass;
                this.superInterfaces = Binding.NO_SUPERINTERFACES;
                if (!signatureWrapper.atEnd()) {
                    ArrayList arrayList = new ArrayList(2);
                    short s2 = 0;
                    while (true) {
                        short s3 = (short) (s2 + 1);
                        arrayList.add(this.environment.getTypeFromTypeSignature(signatureWrapper, typeVariableBindingArr2, this, cArr, iTypeAnnotationWalkerEnrichWithExternalAnnotationsFor.toSupertype(s2, signatureWrapper.peekFullType())));
                        if (signatureWrapper.atEnd()) {
                            break;
                        } else {
                            s2 = s3;
                        }
                    }
                    ReferenceBinding[] referenceBindingArr = new ReferenceBinding[arrayList.size()];
                    this.superInterfaces = referenceBindingArr;
                    arrayList.toArray(referenceBindingArr);
                    this.tagBits |= TagBits.HasUnresolvedSuperinterfaces;
                }
            }
            boolean z2 = this.environment.globalOptions.isAnnotationBasedNullAnalysisEnabled && this.environment.globalOptions.sourceLevel >= ClassFileConstants.JDK1_8;
            if (z2 && this.externalAnnotationStatus.isPotentiallyUnannotatedLib()) {
                ReferenceBinding referenceBinding = this.superclass;
                if (referenceBinding != null && referenceBinding.hasNullTypeAnnotations()) {
                    this.externalAnnotationStatus = ExternalAnnotationStatus.TYPE_IS_ANNOTATED;
                } else {
                    for (ReferenceBinding referenceBinding2 : this.superInterfaces) {
                        if (referenceBinding2.hasNullTypeAnnotations()) {
                            this.externalAnnotationStatus = ExternalAnnotationStatus.TYPE_IS_ANNOTATED;
                            break;
                        }
                    }
                }
            }
            if (z) {
                IBinaryField[] fields = iBinaryType.getFields();
                createFields(fields, iBinaryType, j, cArr);
                IBinaryMethod[] iBinaryMethodArrCreateMethods = createMethods(iBinaryType.getMethods(), iBinaryType, j, cArr);
                if (isViewedAsDeprecated()) {
                    int length3 = this.fields.length;
                    for (int i2 = 0; i2 < length3; i2++) {
                        FieldBinding fieldBinding = this.fields[i2];
                        if (!fieldBinding.isDeprecated()) {
                            fieldBinding.modifiers = 2097152 | fieldBinding.modifiers;
                        }
                    }
                    int length4 = this.methods.length;
                    for (int i3 = 0; i3 < length4; i3++) {
                        MethodBinding methodBinding = this.methods[i3];
                        if (!methodBinding.isDeprecated()) {
                            methodBinding.modifiers |= 2097152;
                        }
                    }
                }
                if (this.environment.globalOptions.isAnnotationBasedNullAnalysisEnabled) {
                    if (fields != null) {
                        for (int i4 = 0; i4 < fields.length; i4++) {
                            ITypeAnnotationWalker iTypeAnnotationWalkerEnrichWithExternalAnnotationsFor2 = ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
                            if (j < ClassFileConstants.JDK1_8) {
                                iTypeAnnotationWalkerEnrichWithExternalAnnotationsFor2 = iBinaryType.enrichWithExternalAnnotationsFor(typeAnnotationWalker, fields[i4], this.environment);
                            }
                            scanFieldForNullAnnotation(fields[i4], this.fields[i4], isEnum(), iTypeAnnotationWalkerEnrichWithExternalAnnotationsFor2);
                        }
                    }
                    if (iBinaryMethodArrCreateMethods != null) {
                        for (int i5 = 0; i5 < iBinaryMethodArrCreateMethods.length; i5++) {
                            ITypeAnnotationWalker iTypeAnnotationWalkerEnrichWithExternalAnnotationsFor3 = ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
                            if (j < ClassFileConstants.JDK1_8) {
                                iTypeAnnotationWalkerEnrichWithExternalAnnotationsFor3 = iBinaryType.enrichWithExternalAnnotationsFor(iTypeAnnotationWalkerEnrichWithExternalAnnotationsFor3, iBinaryMethodArrCreateMethods[i5], this.environment);
                            }
                            scanMethodForNullAnnotation(iBinaryMethodArrCreateMethods[i5], this.methods[i5], iTypeAnnotationWalkerEnrichWithExternalAnnotationsFor3, z2);
                        }
                    }
                }
            }
            if (this.environment.globalOptions.storeAnnotations) {
                setAnnotations(createAnnotations(iBinaryType.getAnnotations(), this.environment, cArr));
            }
            if (isAnnotationType()) {
                scanTypeForContainerAnnotation(iBinaryType, cArr);
            }
        } finally {
            if (this.fields == null) {
                this.fields = Binding.NO_FIELDS;
            }
            if (this.methods == null) {
                this.methods = Binding.NO_METHODS;
            }
        }
    }

    private ITypeAnnotationWalker getTypeAnnotationWalker(IBinaryTypeAnnotation[] iBinaryTypeAnnotationArr, int i) {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        if (iBinaryTypeAnnotationArr == null || iBinaryTypeAnnotationArr.length == 0 || !this.environment.usesAnnotatedTypeSystem()) {
            if (this.environment.globalOptions.isAnnotationBasedNullAnalysisEnabled) {
                if (i == 0) {
                    i = getNullDefault();
                }
                if (i > 2) {
                    return new NonNullDefaultAwareTypeAnnotationWalker(i, this.environment);
                }
            }
            return ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
        }
        if (this.environment.globalOptions.isAnnotationBasedNullAnalysisEnabled) {
            if (i == 0) {
                i = getNullDefault();
            }
            if (i > 2) {
                return new NonNullDefaultAwareTypeAnnotationWalker(iBinaryTypeAnnotationArr, i, this.environment);
            }
        }
        return new TypeAnnotationWalker(iBinaryTypeAnnotationArr);
    }

    private int getNullDefaultFrom(IBinaryAnnotation[] iBinaryAnnotationArr) {
        if (iBinaryAnnotationArr != null) {
            for (IBinaryAnnotation iBinaryAnnotation : iBinaryAnnotationArr) {
                if (this.environment.getNullAnnotationBit(signature2qualifiedTypeName(iBinaryAnnotation.getTypeName())) == 128) {
                    return getNonNullByDefaultValue(iBinaryAnnotation);
                }
            }
        }
        return 0;
    }

    private void createFields(IBinaryField[] iBinaryFieldArr, IBinaryType iBinaryType, long j, char[][][] cArr) {
        int length;
        char[] cArr2;
        TypeBinding typeFromTypeSignature;
        IBinaryField[] iBinaryFieldArr2 = iBinaryFieldArr;
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        this.fields = Binding.NO_FIELDS;
        if (iBinaryFieldArr2 == null || (length = iBinaryFieldArr2.length) <= 0) {
            return;
        }
        FieldBinding[] fieldBindingArr = new FieldBinding[length];
        int i = 0;
        boolean z = j >= ClassFileConstants.JDK1_5;
        boolean zHasRestrictedAccess = hasRestrictedAccess();
        int i2 = -1;
        int i3 = 0;
        while (i3 < length) {
            IBinaryField iBinaryField = iBinaryFieldArr2[i3];
            char[] genericSignature = z ? iBinaryField.getGenericSignature() : null;
            ITypeAnnotationWalker typeAnnotationWalker = getTypeAnnotationWalker(iBinaryField.getTypeAnnotations(), i);
            if (j >= ClassFileConstants.JDK1_8) {
                typeAnnotationWalker = iBinaryType.enrichWithExternalAnnotationsFor(typeAnnotationWalker, iBinaryFieldArr2[i3], this.environment);
            }
            ITypeAnnotationWalker field = typeAnnotationWalker.toField();
            if (genericSignature == null) {
                cArr2 = genericSignature;
                typeFromTypeSignature = this.environment.getTypeFromSignature(iBinaryField.getTypeName(), 0, -1, false, this, cArr, field);
            } else {
                cArr2 = genericSignature;
                typeFromTypeSignature = this.environment.getTypeFromTypeSignature(new SignatureWrapper(cArr2), Binding.NO_TYPE_VARIABLES, this, cArr, field);
            }
            FieldBinding fieldBinding = new FieldBinding(iBinaryField.getName(), typeFromTypeSignature, 33554432 | iBinaryField.getModifiers(), this, iBinaryField.getConstant());
            if (i2 < 0 && this.environment.globalOptions.storeAnnotations && iBinaryField.getAnnotations() != null) {
                i2 = i3;
            }
            fieldBinding.f627id = i3;
            if (z) {
                fieldBinding.tagBits |= iBinaryField.getTagBits();
            }
            if (zHasRestrictedAccess) {
                fieldBinding.modifiers |= 262144;
            }
            if (cArr2 != null) {
                fieldBinding.modifiers |= 1073741824;
            }
            fieldBindingArr[i3] = fieldBinding;
            i3++;
            iBinaryFieldArr2 = iBinaryFieldArr;
            i = 0;
        }
        this.fields = fieldBindingArr;
        if (i2 >= 0) {
            while (i2 < length) {
                this.fields[i2].setAnnotations(createAnnotations(iBinaryFieldArr2[i2].getAnnotations(), this.environment, cArr));
                i2++;
            }
        }
    }

    private MethodBinding createMethod(IBinaryMethod iBinaryMethod, IBinaryType iBinaryType, long j, char[][][] cArr) {
        ITypeAnnotationWalker iTypeAnnotationWalker;
        int i;
        boolean z;
        int i2;
        TypeBinding[] typeBindingArr;
        AnnotationBinding[][] annotationBindingArr;
        TypeBinding[] typeBindingArr2;
        AnnotationBinding[][] annotationBindingArr2;
        TypeBinding typeBinding;
        TypeVariableBinding[] typeVariableBindingArr;
        ReferenceBinding[] referenceBindingArr;
        int length;
        ArrayList arrayList;
        MethodBinding methodBinding;
        char c;
        char c2;
        int i3;
        int i4;
        ReferenceBinding[] referenceBindingArr2;
        TypeVariableBinding[] typeVariableBindingArr2;
        boolean z2;
        int i5;
        TypeBinding[] typeBindingArr3;
        AnnotationBinding[][] annotationBindingArr3;
        int length2;
        char c3;
        int i6;
        int i7;
        int i8;
        ReferenceBinding[] referenceBindingArr3;
        boolean z3;
        ITypeAnnotationWalker iTypeAnnotationWalker2;
        TypeBinding[] typeBindingArr4;
        TypeVariableBinding[] typeVariableBindingArr3;
        int i9;
        short s;
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        int modifiers = iBinaryMethod.getModifiers() | 33554432;
        if (j < ClassFileConstants.JDK1_5) {
            modifiers &= -129;
        }
        if (isInterface() && (modifiers & 1024) == 0 && (modifiers & 8) == 0) {
            modifiers |= 65536;
        }
        int i10 = modifiers;
        ReferenceBinding[] referenceBindingArr4 = Binding.NO_EXCEPTIONS;
        TypeBinding[] typeBindingArr5 = Binding.NO_PARAMETERS;
        TypeVariableBinding[] typeVariableBindingArr4 = Binding.NO_TYPE_VARIABLES;
        char[][] argumentNames = iBinaryMethod.getArgumentNames();
        int i11 = 1;
        boolean z4 = j >= ClassFileConstants.JDK1_5;
        ITypeAnnotationWalker typeAnnotationWalker = getTypeAnnotationWalker(iBinaryMethod.getTypeAnnotations(), getNullDefaultFrom(iBinaryMethod.getAnnotations()));
        char[] genericSignature = iBinaryMethod.getGenericSignature();
        boolean z5 = z4;
        if (genericSignature == null) {
            char[] methodDescriptor = iBinaryMethod.getMethodDescriptor();
            if (j >= ClassFileConstants.JDK1_8) {
                typeAnnotationWalker = iBinaryType.enrichWithExternalAnnotationsFor(typeAnnotationWalker, iBinaryMethod, this.environment);
            }
            ITypeAnnotationWalker iTypeAnnotationWalker3 = typeAnnotationWalker;
            int i12 = 0;
            int i13 = 0;
            while (true) {
                i12 += i11;
                char c4 = methodDescriptor[i12];
                c = 'L';
                c2 = '[';
                if (c4 == ')') {
                    break;
                }
                int i14 = i13;
                ReferenceBinding[] referenceBindingArr5 = referenceBindingArr4;
                typeVariableBindingArr4 = typeVariableBindingArr4;
                boolean z6 = z5;
                iTypeAnnotationWalker3 = iTypeAnnotationWalker3;
                i11 = i11;
                if (c4 != '[') {
                    i13 = i14 + 1;
                    if (c4 == 'L') {
                        do {
                            i12 += i11;
                        } while (methodDescriptor[i12] != ';');
                    }
                } else {
                    i13 = i14;
                }
                referenceBindingArr4 = referenceBindingArr5;
                z5 = z6;
            }
            if (iBinaryMethod.isConstructor()) {
                int i15 = (!isMemberType() || isStatic()) ? 0 : i11;
                if (isEnum()) {
                    i15 += 2;
                }
                i3 = i15;
            } else {
                i3 = 0;
            }
            int i16 = i13 - i3;
            if (i16 > 0) {
                TypeBinding[] typeBindingArr6 = new TypeBinding[i16];
                annotationBindingArr3 = this.environment.globalOptions.storeAnnotations ? new AnnotationBinding[i16][] : null;
                i5 = i11;
                int i17 = 0;
                int i18 = 0;
                short s2 = 0;
                while (i18 < i13) {
                    while (true) {
                        i17 += i11;
                        c3 = methodDescriptor[i17];
                        if (c3 != c2) {
                            break;
                        }
                        i11 = 1;
                    }
                    if (c3 == c) {
                        do {
                            i17++;
                        } while (methodDescriptor[i17] != ';');
                    }
                    int i19 = i17;
                    if (i18 >= i3) {
                        int i20 = i18 - i3;
                        short s3 = s2;
                        s = (short) (s3 + 1);
                        i6 = i18;
                        i7 = i13;
                        referenceBindingArr3 = referenceBindingArr4;
                        iTypeAnnotationWalker2 = iTypeAnnotationWalker3;
                        i8 = i3;
                        z3 = z5;
                        typeBindingArr4 = typeBindingArr6;
                        typeVariableBindingArr3 = typeVariableBindingArr4;
                        i9 = 1;
                        typeBindingArr4[i20] = this.environment.getTypeFromSignature(methodDescriptor, i5, i19, false, this, cArr, iTypeAnnotationWalker3.toMethodParameter(s3));
                        if (annotationBindingArr3 != null) {
                            annotationBindingArr3[i20] = createAnnotations(iBinaryMethod.getParameterAnnotations(i20, this.fileName), this.environment, cArr);
                        }
                    } else {
                        i6 = i18;
                        i7 = i13;
                        i8 = i3;
                        referenceBindingArr3 = referenceBindingArr4;
                        z3 = z5;
                        short s4 = s2;
                        iTypeAnnotationWalker2 = iTypeAnnotationWalker3;
                        typeBindingArr4 = typeBindingArr6;
                        typeVariableBindingArr3 = typeVariableBindingArr4;
                        i9 = 1;
                        s = s4;
                    }
                    i5 = i19 + 1;
                    i18 = i6 + 1;
                    iTypeAnnotationWalker3 = iTypeAnnotationWalker2;
                    i11 = i9;
                    typeBindingArr6 = typeBindingArr4;
                    typeVariableBindingArr4 = typeVariableBindingArr3;
                    i17 = i19;
                    i13 = i7;
                    referenceBindingArr4 = referenceBindingArr3;
                    z5 = z3;
                    i3 = i8;
                    c2 = '[';
                    s2 = s;
                    c = 'L';
                }
                i4 = i3;
                typeBindingArr3 = typeBindingArr6;
                referenceBindingArr2 = referenceBindingArr4;
                typeVariableBindingArr2 = typeVariableBindingArr4;
                z2 = z5;
                iTypeAnnotationWalker = iTypeAnnotationWalker3;
            } else {
                i4 = i3;
                referenceBindingArr2 = referenceBindingArr4;
                typeVariableBindingArr2 = typeVariableBindingArr4;
                z2 = z5;
                iTypeAnnotationWalker = iTypeAnnotationWalker3;
                i5 = i12;
                typeBindingArr3 = typeBindingArr5;
                annotationBindingArr3 = null;
            }
            char[][] exceptionTypeNames = iBinaryMethod.getExceptionTypeNames();
            if (exceptionTypeNames != null && (length2 = exceptionTypeNames.length) > 0) {
                ReferenceBinding[] referenceBindingArr6 = new ReferenceBinding[length2];
                int i21 = 0;
                while (i21 < length2) {
                    int i22 = i21;
                    ReferenceBinding[] referenceBindingArr7 = referenceBindingArr6;
                    referenceBindingArr7[i22] = this.environment.getTypeFromConstantPoolName(exceptionTypeNames[i21], 0, -1, false, cArr, iTypeAnnotationWalker.toThrows(i21));
                    i21 = i22 + 1;
                    referenceBindingArr6 = referenceBindingArr7;
                    length2 = length2;
                }
                referenceBindingArr2 = referenceBindingArr6;
            }
            TypeBinding typeFromSignature = !iBinaryMethod.isConstructor() ? this.environment.getTypeFromSignature(methodDescriptor, i5 + 1, -1, false, this, cArr, iTypeAnnotationWalker.toMethodReturn()) : null;
            int length3 = argumentNames == null ? 0 : argumentNames.length;
            if (i4 > 0 && length3 > 0) {
                if (i4 >= length3) {
                    argumentNames = Binding.NO_PARAMETER_NAMES;
                } else {
                    int i23 = length3 - i4;
                    char[][] cArr2 = new char[i23][];
                    System.arraycopy(argumentNames, i4, cArr2, 0, i23);
                    argumentNames = cArr2;
                    i = i10;
                    typeBindingArr2 = typeBindingArr3;
                    annotationBindingArr2 = annotationBindingArr3;
                    typeVariableBindingArr = typeVariableBindingArr2;
                    referenceBindingArr = referenceBindingArr2;
                    z = z2;
                    typeBinding = typeFromSignature;
                }
            }
            typeBinding = typeFromSignature;
            i = i10;
            typeBindingArr2 = typeBindingArr3;
            annotationBindingArr2 = annotationBindingArr3;
            typeVariableBindingArr = typeVariableBindingArr2;
            referenceBindingArr = referenceBindingArr2;
            z = z2;
        } else {
            TypeVariableBinding[] typeVariableBindingArr5 = typeVariableBindingArr4;
            int i24 = 1;
            if (j >= ClassFileConstants.JDK1_8) {
                typeAnnotationWalker = iBinaryType.enrichWithExternalAnnotationsFor(typeAnnotationWalker, iBinaryMethod, this.environment);
            }
            iTypeAnnotationWalker = typeAnnotationWalker;
            i = i10 | 1073741824;
            z = z5;
            SignatureWrapper signatureWrapper = new SignatureWrapper(genericSignature, z);
            if (signatureWrapper.signature[signatureWrapper.start] == '<') {
                signatureWrapper.start++;
                TypeVariableBinding[] typeVariableBindingArrCreateTypeVariables = createTypeVariables(signatureWrapper, false, cArr, iTypeAnnotationWalker, false);
                signatureWrapper.start++;
                typeVariableBindingArr5 = typeVariableBindingArrCreateTypeVariables;
            }
            int i25 = 2;
            if (signatureWrapper.signature[signatureWrapper.start] == '(') {
                signatureWrapper.start++;
                if (signatureWrapper.signature[signatureWrapper.start] == c) {
                    signatureWrapper.start++;
                    i2 = 2;
                    typeBindingArr = typeBindingArr5;
                    annotationBindingArr = null;
                } else {
                    ArrayList arrayList2 = new ArrayList(2);
                    short s5 = 0;
                    for (char c5 = ')'; signatureWrapper.signature[signatureWrapper.start] != c5; c5 = ')') {
                        arrayList2.add(this.environment.getTypeFromTypeSignature(signatureWrapper, typeVariableBindingArr5, this, cArr, iTypeAnnotationWalker.toMethodParameter(s5)));
                        i25 = i25;
                        s5 = (short) (s5 + 1);
                    }
                    signatureWrapper.start++;
                    int size = arrayList2.size();
                    TypeBinding[] typeBindingArr7 = new TypeBinding[size];
                    arrayList2.toArray(typeBindingArr7);
                    if (this.environment.globalOptions.storeAnnotations) {
                        AnnotationBinding[][] annotationBindingArr4 = new AnnotationBinding[size][];
                        for (int i26 = 0; i26 < size; i26++) {
                            annotationBindingArr4[i26] = createAnnotations(iBinaryMethod.getParameterAnnotations(i26, this.fileName), this.environment, cArr);
                        }
                        typeBindingArr = typeBindingArr7;
                        annotationBindingArr = annotationBindingArr4;
                        i2 = i25;
                    } else {
                        typeBindingArr = typeBindingArr7;
                        i2 = i25;
                        annotationBindingArr = null;
                    }
                }
            } else {
                i2 = 2;
                typeBindingArr = typeBindingArr5;
                annotationBindingArr = null;
            }
            TypeBinding typeFromTypeSignature = this.environment.getTypeFromTypeSignature(signatureWrapper, typeVariableBindingArr5, this, cArr, iTypeAnnotationWalker.toMethodReturn());
            if (!signatureWrapper.atEnd() && signatureWrapper.signature[signatureWrapper.start] == '^') {
                ArrayList arrayList3 = new ArrayList(i2);
                int i27 = 0;
                while (true) {
                    signatureWrapper.start += i24;
                    int i28 = i27 + 1;
                    arrayList = arrayList3;
                    arrayList.add(this.environment.getTypeFromTypeSignature(signatureWrapper, typeVariableBindingArr5, this, cArr, iTypeAnnotationWalker.toThrows(i27)));
                    if (signatureWrapper.atEnd() || signatureWrapper.signature[signatureWrapper.start] != '^') {
                        break;
                    }
                    i27 = i28;
                    arrayList3 = arrayList;
                    i24 = 1;
                }
                ReferenceBinding[] referenceBindingArr8 = new ReferenceBinding[arrayList.size()];
                arrayList.toArray(referenceBindingArr8);
                referenceBindingArr = referenceBindingArr8;
            } else {
                char[][] exceptionTypeNames2 = iBinaryMethod.getExceptionTypeNames();
                if (exceptionTypeNames2 == null || (length = exceptionTypeNames2.length) <= 0) {
                    i = i;
                    typeBindingArr2 = typeBindingArr;
                    annotationBindingArr2 = annotationBindingArr;
                    typeBinding = typeFromTypeSignature;
                    typeVariableBindingArr = typeVariableBindingArr5;
                    referenceBindingArr = referenceBindingArr4;
                } else {
                    ReferenceBinding[] referenceBindingArr9 = new ReferenceBinding[length];
                    int i29 = 0;
                    while (i29 < length) {
                        int i30 = i29;
                        referenceBindingArr9[i30] = this.environment.getTypeFromConstantPoolName(exceptionTypeNames2[i29], 0, -1, false, cArr, iTypeAnnotationWalker.toThrows(i29));
                        i29 = i30 + 1;
                    }
                    referenceBindingArr = referenceBindingArr9;
                }
            }
            typeBindingArr2 = typeBindingArr;
            annotationBindingArr2 = annotationBindingArr;
            typeBinding = typeFromTypeSignature;
            typeVariableBindingArr = typeVariableBindingArr5;
        }
        if (iBinaryMethod.isConstructor()) {
            methodBinding = new MethodBinding(i, typeBindingArr2, referenceBindingArr, this);
        } else {
            methodBinding = new MethodBinding(i, iBinaryMethod.getSelector(), typeBinding, typeBindingArr2, referenceBindingArr, this);
        }
        IBinaryAnnotation[] annotationsAtCursor = iTypeAnnotationWalker.toReceiver().getAnnotationsAtCursor(this.f626id);
        if (annotationsAtCursor != null && annotationsAtCursor.length > 0) {
            LookupEnvironment lookupEnvironment = this.environment;
            methodBinding.receiver = lookupEnvironment.createAnnotatedType(this, createAnnotations(annotationsAtCursor, lookupEnvironment, cArr));
        }
        if (this.environment.globalOptions.storeAnnotations) {
            IBinaryAnnotation[] annotations = iBinaryMethod.getAnnotations();
            if ((annotations == null || annotations.length == 0) && iBinaryMethod.isConstructor()) {
                annotations = iTypeAnnotationWalker.toMethodReturn().getAnnotationsAtCursor(this.f626id);
            }
            methodBinding.setAnnotations(createAnnotations(annotations, this.environment, cArr), annotationBindingArr2, isAnnotationType() ? convertMemberValue(iBinaryMethod.getDefaultValue(), this.environment, cArr, true) : null, this.environment);
        }
        if (argumentNames != null) {
            methodBinding.parameterNames = argumentNames;
        }
        if (z) {
            methodBinding.tagBits |= iBinaryMethod.getTagBits();
        }
        methodBinding.typeVariables = typeVariableBindingArr;
        for (TypeVariableBinding typeVariableBinding : typeVariableBindingArr) {
            this.environment.typeSystem.fixTypeVariableDeclaringElement(typeVariableBinding, methodBinding);
        }
        return methodBinding;
    }

    private IBinaryMethod[] createMethods(IBinaryMethod[] iBinaryMethodArr, IBinaryType iBinaryType, long j, char[][][] cArr) {
        int i;
        int i2;
        int[] iArr;
        int i3;
        int i4;
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        int[] iArr2 = null;
        if (iBinaryMethodArr != null) {
            int length = iBinaryMethodArr.length;
            boolean z = j < ClassFileConstants.JDK1_5;
            int i5 = length;
            i = i5;
            int i6 = -1;
            while (true) {
                i5--;
                if (i5 < 0) {
                    break;
                }
                IBinaryMethod iBinaryMethod = iBinaryMethodArr[i5];
                if ((iBinaryMethod.getModifiers() & 4096) != 0) {
                    if (!z || (iBinaryMethod.getModifiers() & 64) == 0) {
                        if (iArr2 == null) {
                            iArr2 = new int[iBinaryMethodArr.length];
                        }
                        iArr2[i5] = -1;
                        i--;
                    }
                } else if (i6 == -1) {
                    char[] selector = iBinaryMethod.getSelector();
                    if (selector.length == 8 && selector[0] == '<') {
                        i--;
                        i6 = i5;
                    }
                }
            }
            i2 = length;
            i3 = i6;
            iArr = iArr2;
        } else {
            i = 0;
            i2 = 0;
            iArr = null;
            i3 = -1;
        }
        if (i == 0) {
            this.methods = Binding.NO_METHODS;
            return NO_BINARY_METHODS;
        }
        boolean zHasRestrictedAccess = hasRestrictedAccess();
        MethodBinding[] methodBindingArr = new MethodBinding[i];
        if (i == i2) {
            for (int i7 = 0; i7 < i2; i7++) {
                MethodBinding methodBindingCreateMethod = createMethod(iBinaryMethodArr[i7], iBinaryType, j, cArr);
                if (zHasRestrictedAccess) {
                    methodBindingCreateMethod.modifiers |= 262144;
                }
                methodBindingArr[i7] = methodBindingCreateMethod;
            }
            this.methods = methodBindingArr;
            return iBinaryMethodArr;
        }
        IBinaryMethod[] iBinaryMethodArr2 = new IBinaryMethod[i];
        int i8 = 0;
        int i9 = 0;
        while (i8 < i2) {
            if (i3 == i8 || (iArr != null && iArr[i8] == -1)) {
                i4 = i8;
            } else {
                i4 = i8;
                MethodBinding methodBindingCreateMethod2 = createMethod(iBinaryMethodArr[i8], iBinaryType, j, cArr);
                if (zHasRestrictedAccess) {
                    methodBindingCreateMethod2.modifiers |= 262144;
                }
                iBinaryMethodArr2[i9] = iBinaryMethodArr[i4];
                methodBindingArr[i9] = methodBindingCreateMethod2;
                i9++;
            }
            i8 = i4 + 1;
        }
        this.methods = methodBindingArr;
        return iBinaryMethodArr2;
    }

    private TypeVariableBinding[] createTypeVariables(SignatureWrapper signatureWrapper, boolean z, char[][][] cArr, ITypeAnnotationWalker iTypeAnnotationWalker, boolean z2) {
        int i;
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        char[] cArr2 = signatureWrapper.signature;
        int length = cArr2.length;
        ArrayList arrayList = new ArrayList(1);
        boolean z3 = true;
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 1; i4 < length; i4++) {
            char c = cArr2[i4];
            if (c != ';') {
                if (c == '<') {
                    i2++;
                } else if (c == '>') {
                    i2--;
                    if (i2 < 0) {
                        break;
                    }
                } else if (z3) {
                    TypeVariableBinding typeVariableBinding = new TypeVariableBinding(CharOperation.subarray(cArr2, i4, CharOperation.indexOf(':', cArr2, i4)), this, i3, this.environment);
                    int i5 = i3 + 1;
                    AnnotationBinding[] annotationBindingArrCreateAnnotations = createAnnotations(iTypeAnnotationWalker.toTypeParameter(z2, i3).getAnnotationsAtCursor(0), this.environment, cArr);
                    if (annotationBindingArrCreateAnnotations != null && annotationBindingArrCreateAnnotations != Binding.NO_ANNOTATIONS) {
                        typeVariableBinding.setTypeAnnotations(annotationBindingArrCreateAnnotations, this.environment.globalOptions.isAnnotationBasedNullAnalysisEnabled);
                    }
                    arrayList.add(typeVariableBinding);
                    i3 = i5;
                    z3 = false;
                }
            } else if (i2 == 0 && (i = i4 + 1) < length && cArr2[i] != ':') {
                z3 = true;
            }
        }
        TypeVariableBinding[] typeVariableBindingArr = new TypeVariableBinding[i3];
        arrayList.toArray(typeVariableBindingArr);
        if (z) {
            this.typeVariables = typeVariableBindingArr;
        }
        for (int i6 = 0; i6 < i3; i6++) {
            initializeTypeVariable(typeVariableBindingArr[i6], typeVariableBindingArr, signatureWrapper, cArr, iTypeAnnotationWalker.toTypeParameterBounds(z2, i6));
            if (this.externalAnnotationStatus.isPotentiallyUnannotatedLib() && typeVariableBindingArr[i6].hasNullTypeAnnotations()) {
                this.externalAnnotationStatus = ExternalAnnotationStatus.TYPE_IS_ANNOTATED;
            }
        }
        return typeVariableBindingArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public ReferenceBinding enclosingType() {
        if ((this.tagBits & TagBits.HasUnresolvedEnclosingType) == 0) {
            return this.enclosingType;
        }
        this.enclosingType = (ReferenceBinding) resolveType(this.enclosingType, this.environment, false);
        this.tagBits &= -134217729;
        return this.enclosingType;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public FieldBinding[] fields() {
        if (!isPrototype()) {
            FieldBinding[] fieldBindingArrFields = this.prototype.fields();
            this.fields = fieldBindingArrFields;
            return fieldBindingArrFields;
        }
        if ((this.tagBits & 8192) != 0) {
            return this.fields;
        }
        if ((this.tagBits & 4096) == 0) {
            FieldBinding[] fieldBindingArr = this.fields;
            int length = fieldBindingArr.length;
            if (length > 1) {
                ReferenceBinding.sortFields(fieldBindingArr, 0, length);
            }
            this.tagBits |= 4096;
        }
        int length2 = this.fields.length;
        while (true) {
            length2--;
            if (length2 >= 0) {
                resolveTypeFor(this.fields[length2]);
            } else {
                this.tagBits |= 8192;
                return this.fields;
            }
        }
    }

    private MethodBinding findMethod(char[] cArr, char[][][] cArr2) {
        int i;
        char c;
        TypeBinding[] typeBindingArr;
        char c2;
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        int i2 = -1;
        while (true) {
            i = i2 + 1;
            if (cArr[i] == '(') {
                break;
            }
            i2 = i;
        }
        char[] cArr3 = new char[i];
        int i3 = 0;
        System.arraycopy(cArr, 0, cArr3, 0, i);
        TypeBinding[] typeBindingArr2 = Binding.NO_PARAMETERS;
        int i4 = i;
        int i5 = 0;
        while (true) {
            i4++;
            char c3 = cArr[i4];
            c = '[';
            if (c3 == ')') {
                break;
            }
            if (c3 != '[') {
                i5++;
                if (c3 == 'L') {
                    do {
                        i4++;
                    } while (cArr[i4] != ';');
                }
            }
            i3 = 0;
        }
        if (i5 > 0) {
            typeBindingArr = new TypeBinding[i5];
            int i6 = i2 + 2;
            int i7 = i3;
            while (i7 < i5) {
                do {
                    i++;
                    c2 = cArr[i];
                } while (c2 == c);
                if (c2 == 'L') {
                    do {
                        i++;
                    } while (cArr[i] != ';');
                }
                int i8 = i;
                int i9 = i7;
                TypeBinding[] typeBindingArr3 = typeBindingArr;
                char c4 = c;
                TypeBinding typeFromSignature = this.environment.getTypeFromSignature(cArr, i6, i8, false, this, cArr2, ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER);
                if (typeFromSignature instanceof UnresolvedReferenceBinding) {
                    typeFromSignature = resolveType(typeFromSignature, this.environment, true);
                }
                typeBindingArr3[i9] = typeFromSignature;
                i6 = i8 + 1;
                i7 = i9 + 1;
                c = c4;
                i = i8;
                typeBindingArr = typeBindingArr3;
            }
        } else {
            typeBindingArr = typeBindingArr2;
        }
        int length = typeBindingArr.length;
        for (MethodBinding methodBinding : this.enclosingType.getMethods(cArr3, length)) {
            TypeBinding[] typeBindingArr4 = methodBinding.parameters;
            int length2 = typeBindingArr4.length;
            if (length == length2) {
                for (int i10 = 0; i10 < length2; i10++) {
                    if (!TypeBinding.notEquals(typeBindingArr[i10], typeBindingArr4[i10]) || !TypeBinding.notEquals(typeBindingArr[i10].erasure(), typeBindingArr4[i10].erasure())) {
                    }
                }
                return methodBinding;
            }
        }
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] genericTypeSignature() {
        if (!isPrototype()) {
            return this.prototype.computeGenericTypeSignature(this.typeVariables);
        }
        return computeGenericTypeSignature(this.typeVariables);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public MethodBinding getExactConstructor(TypeBinding[] typeBindingArr) {
        if (!isPrototype()) {
            return this.prototype.getExactConstructor(typeBindingArr);
        }
        if ((this.tagBits & 16384) == 0) {
            MethodBinding[] methodBindingArr = this.methods;
            int length = methodBindingArr.length;
            if (length > 1) {
                ReferenceBinding.sortMethods(methodBindingArr, 0, length);
            }
            this.tagBits = 16384 | this.tagBits;
        }
        int length2 = typeBindingArr.length;
        long jBinarySearch = ReferenceBinding.binarySearch(TypeConstants.INIT, this.methods);
        if (jBinarySearch < 0) {
            return null;
        }
        int i = (int) (jBinarySearch >> 32);
        for (int i2 = (int) jBinarySearch; i2 <= i; i2++) {
            MethodBinding methodBinding = this.methods[i2];
            if (methodBinding.parameters.length == length2) {
                resolveTypesFor(methodBinding);
                TypeBinding[] typeBindingArr2 = methodBinding.parameters;
                for (int i3 = 0; i3 < length2; i3++) {
                    if (!TypeBinding.notEquals(typeBindingArr2[i3], typeBindingArr[i3])) {
                    }
                }
                return methodBinding;
            }
        }
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public MethodBinding getExactMethod(char[] cArr, TypeBinding[] typeBindingArr, CompilationUnitScope compilationUnitScope) {
        boolean z;
        if (!isPrototype()) {
            return this.prototype.getExactMethod(cArr, typeBindingArr, compilationUnitScope);
        }
        if ((this.tagBits & 16384) == 0) {
            MethodBinding[] methodBindingArr = this.methods;
            int length = methodBindingArr.length;
            if (length > 1) {
                ReferenceBinding.sortMethods(methodBindingArr, 0, length);
            }
            this.tagBits = 16384 | this.tagBits;
        }
        int length2 = typeBindingArr.length;
        long jBinarySearch = ReferenceBinding.binarySearch(cArr, this.methods);
        if (jBinarySearch >= 0) {
            int i = (int) jBinarySearch;
            int i2 = (int) (jBinarySearch >> 32);
            z = true;
            while (i <= i2) {
                MethodBinding methodBinding = this.methods[i];
                if (methodBinding.parameters.length == length2) {
                    resolveTypesFor(methodBinding);
                    TypeBinding[] typeBindingArr2 = methodBinding.parameters;
                    for (int i3 = 0; i3 < length2; i3++) {
                        if (!TypeBinding.notEquals(typeBindingArr2[i3], typeBindingArr[i3])) {
                        }
                    }
                    return methodBinding;
                }
                i++;
                z = false;
            }
        } else {
            z = true;
        }
        if (!z) {
            return null;
        }
        if (isInterface()) {
            if (superInterfaces().length != 1) {
                return null;
            }
            if (compilationUnitScope != null) {
                compilationUnitScope.recordTypeReference(this.superInterfaces[0]);
            }
            return this.superInterfaces[0].getExactMethod(cArr, typeBindingArr, compilationUnitScope);
        }
        if (superclass() == null) {
            return null;
        }
        if (compilationUnitScope != null) {
            compilationUnitScope.recordTypeReference(this.superclass);
        }
        return this.superclass.getExactMethod(cArr, typeBindingArr, compilationUnitScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public FieldBinding getField(char[] cArr, boolean z) {
        if (!isPrototype()) {
            return this.prototype.getField(cArr, z);
        }
        if ((this.tagBits & 4096) == 0) {
            FieldBinding[] fieldBindingArr = this.fields;
            int length = fieldBindingArr.length;
            if (length > 1) {
                ReferenceBinding.sortFields(fieldBindingArr, 0, length);
            }
            this.tagBits |= 4096;
        }
        FieldBinding fieldBindingBinarySearch = ReferenceBinding.binarySearch(cArr, this.fields);
        return (!z || fieldBindingBinarySearch == null) ? fieldBindingBinarySearch : resolveTypeFor(fieldBindingBinarySearch);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public ReferenceBinding getMemberType(char[] cArr) {
        if (!isPrototype()) {
            ReferenceBinding memberType = this.prototype.getMemberType(cArr);
            if (memberType == null) {
                return null;
            }
            return this.environment.createMemberType(memberType, this);
        }
        int length = this.memberTypes.length;
        while (true) {
            length--;
            if (length < 0) {
                return null;
            }
            ReferenceBinding referenceBinding = this.memberTypes[length];
            if (referenceBinding instanceof UnresolvedReferenceBinding) {
                char[] cArr2 = referenceBinding.sourceName;
                int length2 = this.compoundName[this.compoundName.length - 1].length + 1;
                if (cArr2.length == cArr.length + length2 && CharOperation.fragmentEquals(cArr, cArr2, length2, true)) {
                    ReferenceBinding[] referenceBindingArr = this.memberTypes;
                    ReferenceBinding referenceBinding2 = (ReferenceBinding) resolveType(referenceBinding, this.environment, false);
                    referenceBindingArr[length] = referenceBinding2;
                    return referenceBinding2;
                }
            } else if (CharOperation.equals(cArr, referenceBinding.sourceName)) {
                return referenceBinding;
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public MethodBinding[] getMethods(char[] cArr) {
        if (!isPrototype()) {
            return this.prototype.getMethods(cArr);
        }
        int i = 0;
        if ((this.tagBits & 32768) != 0) {
            long jBinarySearch = ReferenceBinding.binarySearch(cArr, this.methods);
            if (jBinarySearch >= 0) {
                int i2 = (int) jBinarySearch;
                int i3 = (((int) (jBinarySearch >> 32)) - i2) + 1;
                if ((this.tagBits & 32768) != 0) {
                    MethodBinding[] methodBindingArr = new MethodBinding[i3];
                    System.arraycopy(this.methods, i2, methodBindingArr, 0, i3);
                    return methodBindingArr;
                }
            }
            return Binding.NO_METHODS;
        }
        if ((this.tagBits & 16384) == 0) {
            MethodBinding[] methodBindingArr2 = this.methods;
            int length = methodBindingArr2.length;
            if (length > 1) {
                ReferenceBinding.sortMethods(methodBindingArr2, 0, length);
            }
            this.tagBits |= 16384;
        }
        long jBinarySearch2 = ReferenceBinding.binarySearch(cArr, this.methods);
        if (jBinarySearch2 >= 0) {
            int i4 = (int) jBinarySearch2;
            int i5 = (int) (jBinarySearch2 >> 32);
            MethodBinding[] methodBindingArr3 = new MethodBinding[(i5 - i4) + 1];
            while (i4 <= i5) {
                methodBindingArr3[i] = resolveTypesFor(this.methods[i4]);
                i4++;
                i++;
            }
            return methodBindingArr3;
        }
        return Binding.NO_METHODS;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public MethodBinding[] getMethods(char[] cArr, int i) {
        if (!isPrototype()) {
            return this.prototype.getMethods(cArr, i);
        }
        if ((this.tagBits & 32768) != 0) {
            return getMethods(cArr);
        }
        int i2 = 0;
        if ((this.tagBits & 16384) == 0) {
            MethodBinding[] methodBindingArr = this.methods;
            int length = methodBindingArr.length;
            if (length > 1) {
                ReferenceBinding.sortMethods(methodBindingArr, 0, length);
            }
            this.tagBits = 16384 | this.tagBits;
        }
        long jBinarySearch = ReferenceBinding.binarySearch(cArr, this.methods);
        if (jBinarySearch < 0) {
            return Binding.NO_METHODS;
        }
        int i3 = (int) jBinarySearch;
        int i4 = (int) (jBinarySearch >> 32);
        int i5 = (i4 - i3) + 1;
        int i6 = 0;
        for (int i7 = i3; i7 <= i4; i7++) {
            int length2 = this.methods[i7].parameters.length;
            if (length2 <= i || (this.methods[i7].isVarargs() && length2 == i + 1)) {
                i6++;
            }
        }
        if (i6 == 0) {
            MethodBinding[] methodBindingArr2 = new MethodBinding[i5];
            while (i3 <= i4) {
                methodBindingArr2[i2] = resolveTypesFor(this.methods[i3]);
                i3++;
                i2++;
            }
            return methodBindingArr2;
        }
        MethodBinding[] methodBindingArr3 = new MethodBinding[i6];
        while (i3 <= i4) {
            int length3 = this.methods[i3].parameters.length;
            if (length3 <= i || (this.methods[i3].isVarargs() && length3 == i + 1)) {
                methodBindingArr3[i2] = resolveTypesFor(this.methods[i3]);
                i2++;
            }
            i3++;
        }
        return methodBindingArr3;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public boolean hasMemberTypes() {
        if (isPrototype()) {
            return this.memberTypes.length > 0;
        }
        return this.prototype.hasMemberTypes();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public TypeVariableBinding getTypeVariable(char[] cArr) {
        if (!isPrototype()) {
            return this.prototype.getTypeVariable(cArr);
        }
        TypeVariableBinding typeVariable = super.getTypeVariable(cArr);
        typeVariable.resolve();
        return typeVariable;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean hasTypeBit(int i) {
        if (!isPrototype()) {
            return this.prototype.hasTypeBit(i);
        }
        boolean z = this.environment.mayTolerateMissingType;
        this.environment.mayTolerateMissingType = true;
        try {
            superclass();
            superInterfaces();
            this.environment.mayTolerateMissingType = z;
            return (i & this.typeBits) != 0;
        } catch (Throwable th) {
            this.environment.mayTolerateMissingType = z;
            throw th;
        }
    }

    private void initializeTypeVariable(TypeVariableBinding typeVariableBinding, TypeVariableBinding[] typeVariableBindingArr, SignatureWrapper signatureWrapper, char[][][] cArr, ITypeAnnotationWalker iTypeAnnotationWalker) {
        ReferenceBinding resolvedType;
        short s;
        ReferenceBinding referenceBinding;
        ReferenceBinding[] referenceBindingArr;
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        signatureWrapper.start = CharOperation.indexOf(':', signatureWrapper.signature, signatureWrapper.start) + 1;
        if (signatureWrapper.signature[signatureWrapper.start] == ':') {
            resolvedType = this.environment.getResolvedType(TypeConstants.JAVA_LANG_OBJECT, null);
            s = (short) 1;
            referenceBinding = null;
        } else {
            short s2 = (short) 1;
            TypeBinding typeFromTypeSignature = this.environment.getTypeFromTypeSignature(signatureWrapper, typeVariableBindingArr, this, cArr, iTypeAnnotationWalker.toTypeBound((short) 0));
            if (typeFromTypeSignature instanceof ReferenceBinding) {
                resolvedType = (ReferenceBinding) typeFromTypeSignature;
            } else {
                resolvedType = this.environment.getResolvedType(TypeConstants.JAVA_LANG_OBJECT, null);
            }
            s = s2;
            referenceBinding = resolvedType;
        }
        typeVariableBinding.modifiers |= 33554432;
        typeVariableBinding.setSuperClass(resolvedType);
        if (signatureWrapper.signature[signatureWrapper.start] == ':') {
            ArrayList arrayList = new ArrayList(2);
            while (true) {
                signatureWrapper.start++;
                short s3 = (short) (s + 1);
                arrayList.add(this.environment.getTypeFromTypeSignature(signatureWrapper, typeVariableBindingArr, this, cArr, iTypeAnnotationWalker.toTypeBound(s)));
                if (signatureWrapper.signature[signatureWrapper.start] != ':') {
                    break;
                } else {
                    s = s3;
                }
            }
            referenceBindingArr = new ReferenceBinding[arrayList.size()];
            arrayList.toArray(referenceBindingArr);
        } else {
            referenceBindingArr = null;
        }
        if (referenceBindingArr == null) {
            referenceBindingArr = Binding.NO_SUPERINTERFACES;
        }
        typeVariableBinding.setSuperInterfaces(referenceBindingArr);
        if (referenceBinding == null) {
            referenceBinding = typeVariableBinding.superInterfaces.length != 0 ? typeVariableBinding.superInterfaces[0] : null;
        }
        typeVariableBinding.setFirstBound(referenceBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isEquivalentTo(TypeBinding typeBinding) {
        if (TypeBinding.equalsEquals(this, typeBinding)) {
            return true;
        }
        if (typeBinding == null) {
            return false;
        }
        int iKind = typeBinding.kind();
        if (iKind != 260) {
            if (iKind != 516) {
                if (iKind != 1028) {
                    if (iKind != 8196) {
                        return false;
                    }
                }
            }
            return ((WildcardBinding) typeBinding).boundCheck(this);
        }
        return TypeBinding.equalsEquals(typeBinding.erasure(), this);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isGenericType() {
        if (isPrototype()) {
            return this.typeVariables != Binding.NO_TYPE_VARIABLES;
        }
        return this.prototype.isGenericType();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public boolean isHierarchyConnected() {
        if (isPrototype()) {
            return (this.tagBits & 100663296) == 0;
        }
        return this.prototype.isHierarchyConnected();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isRepeatableAnnotationType() {
        if (isPrototype()) {
            return this.containerAnnotationType != null;
        }
        throw new IllegalStateException();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public int kind() {
        if (!isPrototype()) {
            return this.prototype.kind();
        }
        if (this.typeVariables != Binding.NO_TYPE_VARIABLES) {
            return Binding.GENERIC_TYPE;
        }
        return 4;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public ReferenceBinding[] memberTypes() {
        if (!isPrototype()) {
            if ((TagBits.HasUnresolvedMemberTypes & this.tagBits) == 0) {
                return this.memberTypes;
            }
            ReferenceBinding[] referenceBindingArrMemberTypes = this.prototype.memberTypes();
            int length = referenceBindingArrMemberTypes == null ? 0 : referenceBindingArrMemberTypes.length;
            if (length > 0) {
                this.memberTypes = new ReferenceBinding[length];
                for (int i = 0; i < length; i++) {
                    this.memberTypes[i] = this.environment.createMemberType(referenceBindingArrMemberTypes[i], this);
                }
            }
            this.tagBits &= -268435457;
            return this.memberTypes;
        }
        if ((TagBits.HasUnresolvedMemberTypes & this.tagBits) == 0) {
            return this.memberTypes;
        }
        int length2 = this.memberTypes.length;
        while (true) {
            length2--;
            if (length2 >= 0) {
                ReferenceBinding[] referenceBindingArr = this.memberTypes;
                referenceBindingArr[length2] = (ReferenceBinding) resolveType(referenceBindingArr[length2], this.environment, false);
            } else {
                this.tagBits &= -268435457;
                return this.memberTypes;
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public MethodBinding[] methods() {
        if (!isPrototype()) {
            MethodBinding[] methodBindingArrMethods = this.prototype.methods();
            this.methods = methodBindingArrMethods;
            return methodBindingArrMethods;
        }
        if ((this.tagBits & 32768) != 0) {
            return this.methods;
        }
        if ((this.tagBits & 16384) == 0) {
            MethodBinding[] methodBindingArr = this.methods;
            int length = methodBindingArr.length;
            if (length > 1) {
                ReferenceBinding.sortMethods(methodBindingArr, 0, length);
            }
            this.tagBits |= 16384;
        }
        int length2 = this.methods.length;
        while (true) {
            length2--;
            if (length2 >= 0) {
                resolveTypesFor(this.methods[length2]);
            } else {
                this.tagBits |= 32768;
                return this.methods;
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding prototype() {
        return this.prototype;
    }

    private boolean isPrototype() {
        return this == this.prototype;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public ReferenceBinding containerAnnotationType() {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        ReferenceBinding referenceBinding = this.containerAnnotationType;
        if (referenceBinding instanceof UnresolvedReferenceBinding) {
            this.containerAnnotationType = (ReferenceBinding) resolveType(referenceBinding, this.environment, false);
        }
        return this.containerAnnotationType;
    }

    private FieldBinding resolveTypeFor(FieldBinding fieldBinding) {
        if (!isPrototype()) {
            return this.prototype.resolveTypeFor(fieldBinding);
        }
        if ((fieldBinding.modifiers & 33554432) == 0) {
            return fieldBinding;
        }
        TypeBinding typeBindingResolveType = resolveType(fieldBinding.type, this.environment, true);
        fieldBinding.type = typeBindingResolveType;
        if ((typeBindingResolveType.tagBits & 128) != 0) {
            fieldBinding.tagBits |= 128;
        }
        fieldBinding.modifiers &= -33554433;
        return fieldBinding;
    }

    MethodBinding resolveTypesFor(MethodBinding methodBinding) {
        if (!isPrototype()) {
            return this.prototype.resolveTypesFor(methodBinding);
        }
        if ((methodBinding.modifiers & 33554432) == 0) {
            return methodBinding;
        }
        if (!methodBinding.isConstructor()) {
            TypeBinding typeBindingResolveType = resolveType(methodBinding.returnType, this.environment, true);
            methodBinding.returnType = typeBindingResolveType;
            if ((typeBindingResolveType.tagBits & 128) != 0) {
                methodBinding.tagBits |= 128;
            }
        }
        int length = methodBinding.parameters.length;
        while (true) {
            length--;
            if (length < 0) {
                break;
            }
            TypeBinding typeBindingResolveType2 = resolveType(methodBinding.parameters[length], this.environment, true);
            methodBinding.parameters[length] = typeBindingResolveType2;
            if ((typeBindingResolveType2.tagBits & 128) != 0) {
                methodBinding.tagBits |= 128;
            }
        }
        int length2 = methodBinding.thrownExceptions.length;
        while (true) {
            length2--;
            if (length2 < 0) {
                break;
            }
            ReferenceBinding referenceBinding = (ReferenceBinding) resolveType(methodBinding.thrownExceptions[length2], this.environment, true);
            methodBinding.thrownExceptions[length2] = referenceBinding;
            if ((referenceBinding.tagBits & 128) != 0) {
                methodBinding.tagBits |= 128;
            }
        }
        int length3 = methodBinding.typeVariables.length;
        while (true) {
            length3--;
            if (length3 >= 0) {
                methodBinding.typeVariables[length3].resolve();
            } else {
                methodBinding.modifiers &= -33554433;
                return methodBinding;
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    AnnotationBinding[] retrieveAnnotations(Binding binding) {
        if (!isPrototype()) {
            return this.prototype.retrieveAnnotations(binding);
        }
        return AnnotationBinding.addStandardAnnotations(super.retrieveAnnotations(binding), binding.getAnnotationTagBits(), this.environment);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public void setContainerAnnotationType(ReferenceBinding referenceBinding) {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        this.containerAnnotationType = referenceBinding;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public void tagAsHavingDefectiveContainerType() {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        ReferenceBinding referenceBinding = this.containerAnnotationType;
        if (referenceBinding == null || !referenceBinding.isValidBinding()) {
            return;
        }
        this.containerAnnotationType = new ProblemReferenceBinding(this.containerAnnotationType.compoundName, this.containerAnnotationType, 22);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    SimpleLookupTable storedAnnotations(boolean z) {
        if (!isPrototype()) {
            return this.prototype.storedAnnotations(z);
        }
        if (z && this.storedAnnotations == null) {
            if (!this.environment.globalOptions.storeAnnotations) {
                return null;
            }
            this.storedAnnotations = new SimpleLookupTable(3);
        }
        return this.storedAnnotations;
    }

    private void scanFieldForNullAnnotation(IBinaryField iBinaryField, FieldBinding fieldBinding, boolean z, ITypeAnnotationWalker iTypeAnnotationWalker) {
        IBinaryAnnotation[] annotations;
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        if (z && (iBinaryField.getModifiers() & 16384) != 0) {
            fieldBinding.tagBits |= 72057594037927936L;
            return;
        }
        boolean z2 = false;
        if (!CharOperation.equals(this.fPackage.compoundName, TypeConstants.JAVA_LANG_ANNOTATION) && this.environment.usesNullTypeAnnotations()) {
            TypeBinding typeBinding = fieldBinding.type;
            if (typeBinding != null && !typeBinding.isBaseType() && (typeBinding.tagBits & TagBits.AnnotationNullMASK) == 0 && typeBinding.acceptsNonNullDefault() && hasNonNullDefaultFor(32, true)) {
                LookupEnvironment lookupEnvironment = this.environment;
                fieldBinding.type = lookupEnvironment.createAnnotatedType(typeBinding, new AnnotationBinding[]{lookupEnvironment.getNonNullAnnotation()});
                return;
            }
            return;
        }
        if (fieldBinding.type == null || fieldBinding.type.isBaseType()) {
            return;
        }
        if (iTypeAnnotationWalker != ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER) {
            annotations = iTypeAnnotationWalker.getAnnotationsAtCursor(fieldBinding.type.f626id);
        } else {
            annotations = iBinaryField.getAnnotations();
        }
        if (annotations != null) {
            for (IBinaryAnnotation iBinaryAnnotation : annotations) {
                char[] typeName = iBinaryAnnotation.getTypeName();
                if (typeName[0] == 'L') {
                    int nullAnnotationBit = this.environment.getNullAnnotationBit(signature2qualifiedTypeName(typeName));
                    if (nullAnnotationBit == 32) {
                        fieldBinding.tagBits |= 72057594037927936L;
                    } else if (nullAnnotationBit == 64) {
                        fieldBinding.tagBits |= 36028797018963968L;
                    }
                    z2 = true;
                    break;
                }
            }
        }
        if (z2 && this.externalAnnotationStatus.isPotentiallyUnannotatedLib()) {
            this.externalAnnotationStatus = ExternalAnnotationStatus.TYPE_IS_ANNOTATED;
        }
        if (z2 || (this.tagBits & 144115188075855872L) == 0) {
            return;
        }
        fieldBinding.tagBits |= 72057594037927936L;
    }

    private void scanMethodForNullAnnotation(IBinaryMethod iBinaryMethod, MethodBinding methodBinding, ITypeAnnotationWalker iTypeAnnotationWalker, boolean z) {
        IBinaryAnnotation[] annotations;
        int annotatedParametersCount;
        IBinaryAnnotation[] parameterAnnotations;
        int i;
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        if (isEnum()) {
            if (CharOperation.equals(TypeConstants.VALUEOF, iBinaryMethod.getSelector()) && methodBinding.parameters.length == 1 && methodBinding.parameters[0].f626id == 11) {
                i = 10;
            } else {
                i = (CharOperation.equals(TypeConstants.VALUES, iBinaryMethod.getSelector()) && methodBinding.parameters == Binding.NO_PARAMETERS) ? 9 : 0;
            }
            if (i != 0) {
                if (this.environment.globalOptions.useNullTypeAnnotations == null) {
                    this.environment.deferredEnumMethods.add(methodBinding);
                    return;
                } else {
                    SyntheticMethodBinding.markNonNull(methodBinding, i, this.environment);
                    return;
                }
            }
        }
        ITypeAnnotationWalker methodReturn = iTypeAnnotationWalker.toMethodReturn();
        if (methodReturn != ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER) {
            annotations = methodReturn.getAnnotationsAtCursor(methodBinding.returnType.f626id);
        } else {
            annotations = iBinaryMethod.getAnnotations();
        }
        if (annotations != null) {
            for (int i2 = 0; i2 < annotations.length; i2++) {
                char[] typeName = annotations[i2].getTypeName();
                if (typeName[0] == 'L') {
                    int nullAnnotationBit = this.environment.getNullAnnotationBit(signature2qualifiedTypeName(typeName));
                    if (nullAnnotationBit == 128) {
                        methodBinding.defaultNullness = getNonNullByDefaultValue(annotations[i2]);
                        if (methodBinding.defaultNullness == 2) {
                            methodBinding.tagBits |= 288230376151711744L;
                        } else if (methodBinding.defaultNullness != 0) {
                            methodBinding.tagBits |= 144115188075855872L;
                            if (methodBinding.defaultNullness == 1 && this.environment.usesNullTypeAnnotations()) {
                                methodBinding.defaultNullness |= 24;
                            }
                        }
                    } else if (nullAnnotationBit == 32) {
                        methodBinding.tagBits |= 72057594037927936L;
                    } else if (nullAnnotationBit == 64) {
                        methodBinding.tagBits |= 36028797018963968L;
                    }
                }
            }
        }
        TypeBinding[] typeBindingArr = methodBinding.parameters;
        int length = typeBindingArr.length;
        if (iTypeAnnotationWalker instanceof ExternalAnnotationProvider.IMethodAnnotationWalker) {
            annotatedParametersCount = ((ExternalAnnotationProvider.IMethodAnnotationWalker) iTypeAnnotationWalker).getParameterCount();
        } else {
            annotatedParametersCount = iBinaryMethod.getAnnotatedParametersCount();
        }
        if (annotatedParametersCount > 0) {
            for (int i3 = 0; i3 < length; i3++) {
                if (annotatedParametersCount > 0) {
                    int i4 = (annotatedParametersCount - length) + i3;
                    ITypeAnnotationWalker methodParameter = iTypeAnnotationWalker.toMethodParameter((short) i4);
                    if (methodParameter != ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER) {
                        parameterAnnotations = methodParameter.getAnnotationsAtCursor(typeBindingArr[i3].f626id);
                    } else {
                        parameterAnnotations = iBinaryMethod.getParameterAnnotations(i4, this.fileName);
                    }
                    if (parameterAnnotations != null) {
                        for (IBinaryAnnotation iBinaryAnnotation : parameterAnnotations) {
                            char[] typeName2 = iBinaryAnnotation.getTypeName();
                            if (typeName2[0] == 'L') {
                                int nullAnnotationBit2 = this.environment.getNullAnnotationBit(signature2qualifiedTypeName(typeName2));
                                if (nullAnnotationBit2 != 32) {
                                    if (nullAnnotationBit2 == 64) {
                                        if (methodBinding.parameterNonNullness == null) {
                                            methodBinding.parameterNonNullness = new Boolean[length];
                                        }
                                        methodBinding.parameterNonNullness[i3] = Boolean.FALSE;
                                        break;
                                    }
                                } else {
                                    if (methodBinding.parameterNonNullness == null) {
                                        methodBinding.parameterNonNullness = new Boolean[length];
                                    }
                                    methodBinding.parameterNonNullness[i3] = Boolean.TRUE;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (z && this.externalAnnotationStatus.isPotentiallyUnannotatedLib()) {
            if (methodBinding.returnType.hasNullTypeAnnotations()) {
                this.externalAnnotationStatus = ExternalAnnotationStatus.TYPE_IS_ANNOTATED;
                return;
            }
            for (TypeBinding typeBinding : typeBindingArr) {
                if (typeBinding.hasNullTypeAnnotations()) {
                    this.externalAnnotationStatus = ExternalAnnotationStatus.TYPE_IS_ANNOTATED;
                    return;
                }
            }
        }
    }

    private void scanTypeForNullDefaultAnnotation(IBinaryType iBinaryType, PackageBinding packageBinding) {
        long j;
        int nonNullByDefaultValue;
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        char[][] nonNullByDefaultAnnotationName = this.environment.getNonNullByDefaultAnnotationName();
        if (nonNullByDefaultAnnotationName == null || CharOperation.equals(CharOperation.splitOn('/', iBinaryType.getName()), nonNullByDefaultAnnotationName)) {
            return;
        }
        IBinaryAnnotation[] annotations = iBinaryType.getAnnotations();
        boolean zEquals = CharOperation.equals(sourceName(), TypeConstants.PACKAGE_INFO_NAME);
        if (annotations != null) {
            int length = annotations.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    j = 0;
                    nonNullByDefaultValue = 0;
                    break;
                }
                char[] typeName = annotations[i].getTypeName();
                if (typeName[0] == 'L' && this.environment.getNullAnnotationBit(signature2qualifiedTypeName(typeName)) == 128) {
                    nonNullByDefaultValue = getNonNullByDefaultValue(annotations[i]);
                    if (nonNullByDefaultValue == 2) {
                        j = 288230376151711744L;
                    } else if (nonNullByDefaultValue != 0) {
                        if (nonNullByDefaultValue == 1 && this.environment.usesNullTypeAnnotations()) {
                            nonNullByDefaultValue |= 56;
                        }
                        j = 144115188075855872L;
                    } else {
                        j = 0;
                    }
                    this.defaultNullness = nonNullByDefaultValue;
                    break;
                }
                i++;
            }
            if (j != 0) {
                this.tagBits |= j;
                if (zEquals) {
                    packageBinding.defaultNullness = nonNullByDefaultValue;
                    return;
                }
                return;
            }
        }
        if (zEquals) {
            packageBinding.defaultNullness = 0;
            return;
        }
        ReferenceBinding referenceBinding = this.enclosingType;
        if (referenceBinding == null || !setNullDefault(referenceBinding.tagBits, referenceBinding.getNullDefault())) {
            if (packageBinding.defaultNullness == 0 && !zEquals && packageBinding.getType(TypeConstants.PACKAGE_INFO_NAME) == null) {
                packageBinding.defaultNullness = 0;
            }
            setNullDefault(0L, packageBinding.defaultNullness);
        }
    }

    boolean setNullDefault(long j, int i) {
        this.defaultNullness = i;
        if (i != 0) {
            if (i == 2) {
                this.tagBits |= 288230376151711744L;
            } else {
                this.tagBits |= 144115188075855872L;
            }
            return true;
        }
        if ((j & 144115188075855872L) != 0) {
            this.tagBits |= 144115188075855872L;
            return true;
        }
        if ((j & 288230376151711744L) == 0) {
            return false;
        }
        this.tagBits |= 288230376151711744L;
        return true;
    }

    int getNonNullByDefaultValue(IBinaryAnnotation iBinaryAnnotation) {
        char[][] cArrSignature2qualifiedTypeName = signature2qualifiedTypeName(iBinaryAnnotation.getTypeName());
        IBinaryElementValuePair[] elementValuePairs = iBinaryAnnotation.getElementValuePairs();
        if (elementValuePairs == null || elementValuePairs.length == 0) {
            ReferenceBinding type = this.environment.getType(cArrSignature2qualifiedTypeName);
            if (type == null) {
                return 0;
            }
            if (type.isUnresolvedType()) {
                type = ((UnresolvedReferenceBinding) type).resolve(this.environment, false);
            }
            MethodBinding[] methodBindingArrMethods = type.methods();
            if (methodBindingArrMethods == null || methodBindingArrMethods.length != 1) {
                return 1;
            }
            return Annotation.nullLocationBitsFromAnnotationValue(methodBindingArrMethods[0].getDefaultValue());
        }
        if (elementValuePairs.length <= 0) {
            return 2;
        }
        int iNullLocationBitsFromAnnotationValue = 0;
        for (IBinaryElementValuePair iBinaryElementValuePair : elementValuePairs) {
            iNullLocationBitsFromAnnotationValue |= Annotation.nullLocationBitsFromAnnotationValue(iBinaryElementValuePair.getValue());
        }
        return iNullLocationBitsFromAnnotationValue;
    }

    private char[][] signature2qualifiedTypeName(char[] cArr) {
        return CharOperation.splitOn('/', cArr, 1, cArr.length - 1);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    int getNullDefault() {
        return this.defaultNullness;
    }

    private void scanTypeForContainerAnnotation(IBinaryType iBinaryType, char[][][] cArr) {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        IBinaryAnnotation[] annotations = iBinaryType.getAnnotations();
        if (annotations != null) {
            int length = annotations.length;
            for (int i = 0; i < length; i++) {
                if (CharOperation.equals(annotations[i].getTypeName(), ConstantPool.JAVA_LANG_ANNOTATION_REPEATABLE)) {
                    IBinaryElementValuePair[] elementValuePairs = annotations[i].getElementValuePairs();
                    if (elementValuePairs == null || elementValuePairs.length != 1) {
                        return;
                    }
                    Object value = elementValuePairs[0].getValue();
                    if (value instanceof ClassSignature) {
                        this.containerAnnotationType = (ReferenceBinding) this.environment.getTypeFromSignature(((ClassSignature) value).getTypeName(), 0, -1, false, null, cArr, ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER);
                        return;
                    }
                    return;
                }
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public ReferenceBinding superclass() {
        if (!isPrototype()) {
            ReferenceBinding referenceBindingSuperclass = this.prototype.superclass();
            this.superclass = referenceBindingSuperclass;
            return referenceBindingSuperclass;
        }
        if ((this.tagBits & TagBits.HasUnresolvedSuperclass) == 0) {
            return this.superclass;
        }
        this.superclass = (ReferenceBinding) resolveType(this.superclass, this.environment, true);
        this.tagBits &= -33554433;
        if (this.superclass.problemId() == 1) {
            this.tagBits |= 131072;
        } else {
            boolean z = this.environment.mayTolerateMissingType;
            this.environment.mayTolerateMissingType = true;
            try {
                this.superclass.superclass();
                this.superclass.superInterfaces();
                this.environment.mayTolerateMissingType = z;
            } catch (Throwable th) {
                this.environment.mayTolerateMissingType = z;
                throw th;
            }
        }
        this.typeBits |= this.superclass.typeBits & 19;
        if ((this.typeBits & 3) != 0) {
            this.typeBits |= applyCloseableClassWhitelists();
        }
        return this.superclass;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public ReferenceBinding[] superInterfaces() {
        if (!isPrototype()) {
            ReferenceBinding[] referenceBindingArrSuperInterfaces = this.prototype.superInterfaces();
            this.superInterfaces = referenceBindingArrSuperInterfaces;
            return referenceBindingArrSuperInterfaces;
        }
        if ((this.tagBits & TagBits.HasUnresolvedSuperinterfaces) == 0) {
            return this.superInterfaces;
        }
        int length = this.superInterfaces.length;
        while (true) {
            length--;
            if (length >= 0) {
                ReferenceBinding[] referenceBindingArr = this.superInterfaces;
                referenceBindingArr[length] = (ReferenceBinding) resolveType(referenceBindingArr[length], this.environment, true);
                if (this.superInterfaces[length].problemId() == 1) {
                    this.tagBits |= 131072;
                } else {
                    boolean z = this.environment.mayTolerateMissingType;
                    this.environment.mayTolerateMissingType = true;
                    try {
                        this.superInterfaces[length].superclass();
                        if (this.superInterfaces[length].isParameterizedType() && TypeBinding.equalsEquals(this.superInterfaces[length].actualType(), this)) {
                            this.tagBits = 131072 | this.tagBits;
                            this.environment.mayTolerateMissingType = z;
                        } else {
                            this.superInterfaces[length].superInterfaces();
                            this.environment.mayTolerateMissingType = z;
                        }
                    } catch (Throwable th) {
                        this.environment.mayTolerateMissingType = z;
                        throw th;
                    }
                }
                this.typeBits |= this.superInterfaces[length].typeBits & 19;
                if ((this.typeBits & 3) != 0) {
                    this.typeBits |= applyCloseableInterfaceWhitelists();
                }
            } else {
                this.tagBits &= -67108865;
                return this.superInterfaces;
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeVariableBinding[] typeVariables() {
        if (!isPrototype()) {
            TypeVariableBinding[] typeVariableBindingArrTypeVariables = this.prototype.typeVariables();
            this.typeVariables = typeVariableBindingArrTypeVariables;
            return typeVariableBindingArrTypeVariables;
        }
        if ((this.tagBits & TagBits.HasUnresolvedTypeVariables) == 0) {
            return this.typeVariables;
        }
        int length = this.typeVariables.length;
        while (true) {
            length--;
            if (length >= 0) {
                this.typeVariables[length].resolve();
            } else {
                this.tagBits &= -16777217;
                return this.typeVariables;
            }
        }
    }

    public String toString() {
        if (hasTypeAnnotations()) {
            return annotatedDebugName();
        }
        StringBuffer stringBuffer = new StringBuffer();
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
        stringBuffer.append(this.compoundName != null ? CharOperation.toString(this.compoundName) : "UNNAMED TYPE");
        TypeVariableBinding[] typeVariableBindingArr = this.typeVariables;
        if (typeVariableBindingArr == null) {
            stringBuffer.append("<NULL TYPE VARIABLES>");
        } else if (typeVariableBindingArr != Binding.NO_TYPE_VARIABLES) {
            stringBuffer.append("<");
            int length = this.typeVariables.length;
            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    stringBuffer.append(", ");
                }
                TypeVariableBinding typeVariableBinding = this.typeVariables[i];
                if (typeVariableBinding == null) {
                    stringBuffer.append("NULL TYPE VARIABLE");
                } else {
                    char[] charArray = typeVariableBinding.toString().toCharArray();
                    stringBuffer.append(charArray, 1, charArray.length - 2);
                }
            }
            stringBuffer.append(">");
        }
        stringBuffer.append("\n\textends ");
        ReferenceBinding referenceBinding = this.superclass;
        stringBuffer.append(referenceBinding != null ? referenceBinding.debugName() : "NULL TYPE");
        ReferenceBinding[] referenceBindingArr = this.superInterfaces;
        if (referenceBindingArr != null) {
            if (referenceBindingArr != Binding.NO_SUPERINTERFACES) {
                stringBuffer.append("\n\timplements : ");
                int length2 = this.superInterfaces.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    if (i2 > 0) {
                        stringBuffer.append(", ");
                    }
                    ReferenceBinding referenceBinding2 = this.superInterfaces[i2];
                    stringBuffer.append(referenceBinding2 != null ? referenceBinding2.debugName() : "NULL TYPE");
                }
            }
        } else {
            stringBuffer.append("NULL SUPERINTERFACES");
        }
        if (this.enclosingType != null) {
            stringBuffer.append("\n\tenclosing type : ");
            stringBuffer.append(this.enclosingType.debugName());
        }
        FieldBinding[] fieldBindingArr = this.fields;
        if (fieldBindingArr != null) {
            if (fieldBindingArr != Binding.NO_FIELDS) {
                stringBuffer.append("\n/*   fields   */");
                int length3 = this.fields.length;
                for (int i3 = 0; i3 < length3; i3++) {
                    stringBuffer.append(this.fields[i3] != null ? "\n" + this.fields[i3].toString() : "\nNULL FIELD");
                }
            }
        } else {
            stringBuffer.append("NULL FIELDS");
        }
        MethodBinding[] methodBindingArr = this.methods;
        if (methodBindingArr != null) {
            if (methodBindingArr != Binding.NO_METHODS) {
                stringBuffer.append("\n/*   methods   */");
                int length4 = this.methods.length;
                for (int i4 = 0; i4 < length4; i4++) {
                    stringBuffer.append(this.methods[i4] != null ? "\n" + this.methods[i4].toString() : "\nNULL METHOD");
                }
            }
        } else {
            stringBuffer.append("NULL METHODS");
        }
        ReferenceBinding[] referenceBindingArr2 = this.memberTypes;
        if (referenceBindingArr2 != null) {
            if (referenceBindingArr2 != Binding.NO_MEMBER_TYPES) {
                stringBuffer.append("\n/*   members   */");
                int length5 = this.memberTypes.length;
                for (int i5 = 0; i5 < length5; i5++) {
                    stringBuffer.append(this.memberTypes[i5] != null ? "\n" + this.memberTypes[i5].toString() : "\nNULL TYPE");
                }
            }
        } else {
            stringBuffer.append("NULL MEMBER TYPES");
        }
        stringBuffer.append("\n\n\n");
        return stringBuffer.toString();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding unannotated() {
        return this.prototype;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding withoutToplevelNullAnnotation() {
        if (!hasNullTypeAnnotations()) {
            return this;
        }
        AnnotationBinding[] annotationBindingArrFilterNullTypeAnnotations = this.environment.filterNullTypeAnnotations(this.typeAnnotations);
        if (annotationBindingArrFilterNullTypeAnnotations.length > 0) {
            return this.environment.createAnnotatedType(this.prototype, annotationBindingArrFilterNullTypeAnnotations);
        }
        return this.prototype;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    MethodBinding[] unResolvedMethods() {
        if (!isPrototype()) {
            return this.prototype.unResolvedMethods();
        }
        return this.methods;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public FieldBinding[] unResolvedFields() {
        if (!isPrototype()) {
            return this.prototype.unResolvedFields();
        }
        return this.fields;
    }
}
