package org.eclipse.jdt.internal.compiler.lookup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ClassFile;
import org.eclipse.jdt.internal.compiler.ClassFilePool;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.env.AccessRestriction;
import org.eclipse.jdt.internal.compiler.env.IBinaryAnnotation;
import org.eclipse.jdt.internal.compiler.env.IBinaryType;
import org.eclipse.jdt.internal.compiler.env.INameEnvironment;
import org.eclipse.jdt.internal.compiler.env.INameEnvironmentExtension;
import org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker;
import org.eclipse.jdt.internal.compiler.env.NameEnvironmentAnswer;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.impl.ITypeRequestor;
import org.eclipse.jdt.internal.compiler.problem.AbortCompilation;
import org.eclipse.jdt.internal.compiler.problem.ProblemReporter;
import org.eclipse.jdt.internal.compiler.util.HashtableOfPackage;
import org.eclipse.jdt.internal.compiler.util.SimpleLookupTable;

/* JADX INFO: loaded from: classes14.dex */
public class LookupEnvironment implements ProblemReasons, TypeConstants {
    static final int BUILD_FIELDS_AND_METHODS = 4;
    static final int BUILD_TYPE_HIERARCHY = 1;
    static final int CHECK_AND_SET_IMPORTS = 2;
    static final int CONNECT_TYPE_HIERARCHY = 3;
    static final ProblemPackageBinding TheNotFoundPackage = new ProblemPackageBinding(CharOperation.NO_CHAR, 1);
    static final ProblemReferenceBinding TheNotFoundType = new ProblemReferenceBinding(CharOperation.NO_CHAR_CHAR, null, 1);
    public MethodBinding arrayClone;
    InferenceContext18 currentInferenceContext;
    public CompilerOptions globalOptions;
    public INameEnvironment nameEnvironment;
    AnnotationBinding nonNullAnnotation;
    PackageBinding nonnullAnnotationPackage;
    PackageBinding nonnullByDefaultAnnotationPackage;
    AnnotationBinding nullableAnnotation;
    PackageBinding nullableAnnotationPackage;
    public ProblemReporter problemReporter;
    private int stepCompleted;
    public ITypeRequestor typeRequestor;
    TypeSystem typeSystem;
    private SimpleLookupTable uniqueGetClassMethodBinding;
    private MethodVerifier verifier;
    private int lastCompletedUnitIndex = -1;
    private int lastUnitIndex = -1;
    public CompilationUnitDeclaration unitBeingCompleted = null;
    public Object missingClassFileLocation = null;
    private CompilationUnitDeclaration[] units = new CompilationUnitDeclaration[4];
    public boolean isProcessingAnnotations = false;
    public boolean mayTolerateMissingType = false;
    Map<String, Integer> allNullAnnotations = null;
    final List<MethodBinding> deferredEnumMethods = new ArrayList();
    public IQualifiedTypeResolutionListener[] resolutionListeners = new IQualifiedTypeResolutionListener[0];
    public PackageBinding defaultPackage = new PackageBinding(this);
    ImportBinding[] defaultImports = null;
    HashtableOfPackage knownPackages = new HashtableOfPackage();
    private SimpleLookupTable uniqueParameterizedGenericMethodBindings = new SimpleLookupTable(3);
    private SimpleLookupTable uniquePolymorphicMethodBindings = new SimpleLookupTable(3);
    private ArrayList missingTypes = null;
    private Map accessRestrictions = new HashMap(3);
    public ClassFilePool classFilePool = ClassFilePool.newInstance();
    Set<SourceTypeBinding> typesBeingConnected = new HashSet();

    public LookupEnvironment(ITypeRequestor iTypeRequestor, CompilerOptions compilerOptions, ProblemReporter problemReporter, INameEnvironment iNameEnvironment) {
        this.typeRequestor = iTypeRequestor;
        this.globalOptions = compilerOptions;
        this.problemReporter = problemReporter;
        this.nameEnvironment = iNameEnvironment;
        this.typeSystem = (this.globalOptions.sourceLevel < ClassFileConstants.JDK1_8 || !this.globalOptions.storeAnnotations) ? new TypeSystem(this) : new AnnotatableTypeSystem(this);
    }

    public ReferenceBinding askForType(char[][] cArr) {
        NameEnvironmentAnswer nameEnvironmentAnswerFindType = this.nameEnvironment.findType(cArr);
        if (nameEnvironmentAnswerFindType == null) {
            return null;
        }
        if (nameEnvironmentAnswerFindType.isBinaryType()) {
            this.typeRequestor.accept(nameEnvironmentAnswerFindType.getBinaryType(), computePackageFrom(cArr, false), nameEnvironmentAnswerFindType.getAccessRestriction());
        } else if (nameEnvironmentAnswerFindType.isCompilationUnit()) {
            this.typeRequestor.accept(nameEnvironmentAnswerFindType.getCompilationUnit(), nameEnvironmentAnswerFindType.getAccessRestriction());
        } else if (nameEnvironmentAnswerFindType.isSourceType()) {
            this.typeRequestor.accept(nameEnvironmentAnswerFindType.getSourceTypes(), computePackageFrom(cArr, false), nameEnvironmentAnswerFindType.getAccessRestriction());
        }
        return getCachedType(cArr);
    }

    ReferenceBinding askForType(PackageBinding packageBinding, char[] cArr) {
        if (packageBinding == null) {
            packageBinding = this.defaultPackage;
        }
        NameEnvironmentAnswer nameEnvironmentAnswerFindType = this.nameEnvironment.findType(cArr, packageBinding.compoundName);
        if (nameEnvironmentAnswerFindType == null) {
            return null;
        }
        if (nameEnvironmentAnswerFindType.isBinaryType()) {
            this.typeRequestor.accept(nameEnvironmentAnswerFindType.getBinaryType(), packageBinding, nameEnvironmentAnswerFindType.getAccessRestriction());
        } else if (nameEnvironmentAnswerFindType.isCompilationUnit()) {
            try {
                this.typeRequestor.accept(nameEnvironmentAnswerFindType.getCompilationUnit(), nameEnvironmentAnswerFindType.getAccessRestriction());
            } catch (AbortCompilation e) {
                if (CharOperation.equals(cArr, TypeConstants.PACKAGE_INFO_NAME)) {
                    return null;
                }
                throw e;
            }
        } else if (nameEnvironmentAnswerFindType.isSourceType()) {
            this.typeRequestor.accept(nameEnvironmentAnswerFindType.getSourceTypes(), packageBinding, nameEnvironmentAnswerFindType.getAccessRestriction());
            ReferenceBinding type0 = packageBinding.getType0(cArr);
            String externalAnnotationPath = nameEnvironmentAnswerFindType.getExternalAnnotationPath();
            if (externalAnnotationPath != null && this.globalOptions.isAnnotationBasedNullAnalysisEnabled && (type0 instanceof SourceTypeBinding)) {
                ExternalAnnotationSuperimposer.apply((SourceTypeBinding) type0, externalAnnotationPath);
            }
            return type0;
        }
        return packageBinding.getType0(cArr);
    }

    public void buildTypeBindings(CompilationUnitDeclaration compilationUnitDeclaration, AccessRestriction accessRestriction) {
        new CompilationUnitScope(compilationUnitDeclaration, this).buildTypeBindings(accessRestriction);
        CompilationUnitDeclaration[] compilationUnitDeclarationArr = this.units;
        int length = compilationUnitDeclarationArr.length;
        int i = this.lastUnitIndex + 1;
        this.lastUnitIndex = i;
        if (i >= length) {
            CompilationUnitDeclaration[] compilationUnitDeclarationArr2 = new CompilationUnitDeclaration[length * 2];
            this.units = compilationUnitDeclarationArr2;
            System.arraycopy(compilationUnitDeclarationArr, 0, compilationUnitDeclarationArr2, 0, length);
        }
        this.units[this.lastUnitIndex] = compilationUnitDeclaration;
    }

    public BinaryTypeBinding cacheBinaryType(IBinaryType iBinaryType, AccessRestriction accessRestriction) {
        return cacheBinaryType(iBinaryType, true, accessRestriction);
    }

    public BinaryTypeBinding cacheBinaryType(IBinaryType iBinaryType, boolean z, AccessRestriction accessRestriction) {
        char[][] cArrSplitOn = CharOperation.splitOn('/', iBinaryType.getName());
        ReferenceBinding cachedType = getCachedType(cArrSplitOn);
        if (cachedType == null || (cachedType instanceof UnresolvedReferenceBinding)) {
            return createBinaryTypeFrom(iBinaryType, computePackageFrom(cArrSplitOn, false), z, accessRestriction);
        }
        return null;
    }

    public void completeTypeBindings() {
        this.stepCompleted = 1;
        for (int i = this.lastCompletedUnitIndex + 1; i <= this.lastUnitIndex; i++) {
            CompilationUnitDeclaration compilationUnitDeclaration = this.units[i];
            this.unitBeingCompleted = compilationUnitDeclaration;
            compilationUnitDeclaration.scope.checkAndSetImports();
        }
        this.stepCompleted = 2;
        for (int i2 = this.lastCompletedUnitIndex + 1; i2 <= this.lastUnitIndex; i2++) {
            CompilationUnitDeclaration compilationUnitDeclaration2 = this.units[i2];
            this.unitBeingCompleted = compilationUnitDeclaration2;
            compilationUnitDeclaration2.scope.connectTypeHierarchy();
        }
        this.stepCompleted = 3;
        int i3 = this.lastCompletedUnitIndex + 1;
        while (true) {
            int i4 = this.lastUnitIndex;
            if (i3 <= i4) {
                CompilationUnitDeclaration compilationUnitDeclaration3 = this.units[i3];
                this.unitBeingCompleted = compilationUnitDeclaration3;
                CompilationUnitScope compilationUnitScope = compilationUnitDeclaration3.scope;
                compilationUnitScope.checkParameterizedTypes();
                compilationUnitScope.buildFieldsAndMethods();
                this.units[i3] = null;
                i3++;
            } else {
                this.stepCompleted = 4;
                this.lastCompletedUnitIndex = i4;
                this.unitBeingCompleted = null;
                return;
            }
        }
    }

    public void completeTypeBindings(CompilationUnitDeclaration compilationUnitDeclaration) {
        if (this.stepCompleted == 4) {
            completeTypeBindings();
            return;
        }
        if (compilationUnitDeclaration.scope == null) {
            return;
        }
        if (this.stepCompleted >= 2) {
            this.unitBeingCompleted = compilationUnitDeclaration;
            compilationUnitDeclaration.scope.checkAndSetImports();
        }
        if (this.stepCompleted >= 3) {
            this.unitBeingCompleted = compilationUnitDeclaration;
            compilationUnitDeclaration.scope.connectTypeHierarchy();
        }
        this.unitBeingCompleted = null;
    }

    public void completeTypeBindings(CompilationUnitDeclaration compilationUnitDeclaration, boolean z) {
        if (compilationUnitDeclaration.scope == null) {
            return;
        }
        this.unitBeingCompleted = compilationUnitDeclaration;
        compilationUnitDeclaration.scope.checkAndSetImports();
        compilationUnitDeclaration.scope.connectTypeHierarchy();
        compilationUnitDeclaration.scope.checkParameterizedTypes();
        if (z) {
            compilationUnitDeclaration.scope.buildFieldsAndMethods();
        }
        this.unitBeingCompleted = null;
    }

    public void completeTypeBindings(CompilationUnitDeclaration[] compilationUnitDeclarationArr, boolean[] zArr, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            CompilationUnitDeclaration compilationUnitDeclaration = compilationUnitDeclarationArr[i2];
            if (compilationUnitDeclaration.scope != null) {
                this.unitBeingCompleted = compilationUnitDeclaration;
                compilationUnitDeclaration.scope.checkAndSetImports();
            }
        }
        for (int i3 = 0; i3 < i; i3++) {
            CompilationUnitDeclaration compilationUnitDeclaration2 = compilationUnitDeclarationArr[i3];
            if (compilationUnitDeclaration2.scope != null) {
                this.unitBeingCompleted = compilationUnitDeclaration2;
                compilationUnitDeclaration2.scope.connectTypeHierarchy();
            }
        }
        for (int i4 = 0; i4 < i; i4++) {
            CompilationUnitDeclaration compilationUnitDeclaration3 = compilationUnitDeclarationArr[i4];
            if (compilationUnitDeclaration3.scope != null) {
                this.unitBeingCompleted = compilationUnitDeclaration3;
                compilationUnitDeclaration3.scope.checkParameterizedTypes();
                if (zArr[i4]) {
                    compilationUnitDeclaration3.scope.buildFieldsAndMethods();
                }
            }
        }
        this.unitBeingCompleted = null;
    }

    public MethodBinding computeArrayClone(MethodBinding methodBinding) {
        if (this.arrayClone == null) {
            this.arrayClone = new MethodBinding((methodBinding.modifiers & (-5)) | 1, TypeConstants.CLONE, methodBinding.returnType, Binding.NO_PARAMETERS, Binding.NO_EXCEPTIONS, (ReferenceBinding) methodBinding.returnType);
        }
        return this.arrayClone;
    }

    public TypeBinding computeBoxingType(TypeBinding typeBinding) {
        int i = typeBinding.f626id;
        if (i == 2) {
            ReferenceBinding type = getType(JAVA_LANG_CHARACTER);
            return type != null ? type : new ProblemReferenceBinding(JAVA_LANG_CHARACTER, null, 1);
        }
        if (i == 3) {
            ReferenceBinding type2 = getType(JAVA_LANG_BYTE);
            return type2 != null ? type2 : new ProblemReferenceBinding(JAVA_LANG_BYTE, null, 1);
        }
        if (i == 4) {
            ReferenceBinding type3 = getType(JAVA_LANG_SHORT);
            return type3 != null ? type3 : new ProblemReferenceBinding(JAVA_LANG_SHORT, null, 1);
        }
        if (i != 5) {
            switch (i) {
                case 7:
                    ReferenceBinding type4 = getType(JAVA_LANG_LONG);
                    return type4 != null ? type4 : new ProblemReferenceBinding(JAVA_LANG_LONG, null, 1);
                case 8:
                    ReferenceBinding type5 = getType(JAVA_LANG_DOUBLE);
                    return type5 != null ? type5 : new ProblemReferenceBinding(JAVA_LANG_DOUBLE, null, 1);
                case 9:
                    ReferenceBinding type6 = getType(JAVA_LANG_FLOAT);
                    return type6 != null ? type6 : new ProblemReferenceBinding(JAVA_LANG_FLOAT, null, 1);
                case 10:
                    ReferenceBinding type7 = getType(JAVA_LANG_INTEGER);
                    return type7 != null ? type7 : new ProblemReferenceBinding(JAVA_LANG_INTEGER, null, 1);
                default:
                    switch (i) {
                        case 26:
                            return TypeBinding.BYTE;
                        case 27:
                            return TypeBinding.SHORT;
                        case 28:
                            return TypeBinding.CHAR;
                        case 29:
                            return TypeBinding.INT;
                        case 30:
                            return TypeBinding.LONG;
                        case 31:
                            return TypeBinding.FLOAT;
                        case 32:
                            return TypeBinding.DOUBLE;
                        case 33:
                            return TypeBinding.BOOLEAN;
                        default:
                            int iKind = typeBinding.kind();
                            if (iKind != 516 && iKind != 4100 && iKind != 8196) {
                                if (iKind != 32772) {
                                    return iKind != 65540 ? typeBinding : ((PolyTypeBinding) typeBinding).computeBoxingType();
                                }
                                return computeBoxingType(typeBinding.getIntersectingTypes()[0]);
                            }
                            switch (typeBinding.erasure().f626id) {
                                case 26:
                                    return TypeBinding.BYTE;
                                case 27:
                                    return TypeBinding.SHORT;
                                case 28:
                                    return TypeBinding.CHAR;
                                case 29:
                                    return TypeBinding.INT;
                                case 30:
                                    return TypeBinding.LONG;
                                case 31:
                                    return TypeBinding.FLOAT;
                                case 32:
                                    return TypeBinding.DOUBLE;
                                case 33:
                                    return TypeBinding.BOOLEAN;
                                default:
                                    return typeBinding;
                            }
                    }
            }
        }
        ReferenceBinding type8 = getType(JAVA_LANG_BOOLEAN);
        return type8 != null ? type8 : new ProblemReferenceBinding(JAVA_LANG_BOOLEAN, null, 1);
    }

    private PackageBinding computePackageFrom(char[][] cArr, boolean z) {
        if (cArr.length == 1) {
            return this.defaultPackage;
        }
        PackageBinding package0 = getPackage0(cArr[0]);
        if (package0 == null || package0 == TheNotFoundPackage) {
            package0 = new PackageBinding(cArr[0], this);
            if (z) {
                package0.tagBits |= 128;
            }
            this.knownPackages.put(cArr[0], package0);
        }
        int length = cArr.length - 1;
        for (int i = 1; i < length; i++) {
            PackageBinding package1 = package0.getPackage0(cArr[i]);
            if (package1 == null || package1 == TheNotFoundPackage) {
                package1 = new PackageBinding(CharOperation.subarray(cArr, 0, i + 1), package0, this);
                if (z) {
                    package1.tagBits |= 128;
                }
                package0.addPackage(package1);
            }
            package0 = package1;
        }
        return package0;
    }

    public ReferenceBinding convertToParameterizedType(ReferenceBinding referenceBinding) {
        ReferenceBinding referenceBindingConvertToParameterizedType;
        boolean zNotEquals;
        if (referenceBinding == null) {
            return referenceBinding;
        }
        boolean zIsGenericType = referenceBinding.isGenericType();
        ReferenceBinding referenceBindingEnclosingType = referenceBinding.enclosingType();
        if (referenceBindingEnclosingType != null) {
            if (referenceBinding.isStatic()) {
                referenceBindingConvertToParameterizedType = (ReferenceBinding) convertToRawType(referenceBindingEnclosingType, false);
            } else {
                referenceBindingConvertToParameterizedType = convertToParameterizedType(referenceBindingEnclosingType);
            }
            zNotEquals = TypeBinding.notEquals(referenceBindingEnclosingType, referenceBindingConvertToParameterizedType) | zIsGenericType;
        } else {
            referenceBindingConvertToParameterizedType = referenceBindingEnclosingType;
            zNotEquals = zIsGenericType;
        }
        if (zNotEquals) {
            return createParameterizedType(referenceBinding, zIsGenericType ? referenceBinding.typeVariables() : null, referenceBindingConvertToParameterizedType);
        }
        return referenceBinding;
    }

    /* JADX WARN: Code duplicated, block: B:48:0x00b5  */
    public TypeBinding convertToRawType(TypeBinding typeBinding, boolean z) {
        int iDimensions;
        TypeBinding typeBindingLeafComponentType;
        boolean zIsGenericType;
        ReferenceBinding referenceBindingConvertToParameterizedType;
        TypeBinding typeBindingCreateParameterizedType;
        int iKind = typeBinding.kind();
        if (iKind == 68) {
            iDimensions = typeBinding.dimensions();
            typeBindingLeafComponentType = typeBinding.leafComponentType();
        } else {
            if (iKind == 132 || iKind == 516 || iKind == 1028 || iKind == 4100 || iKind == 8196 || typeBinding.f626id == 1) {
                return typeBinding;
            }
            typeBindingLeafComponentType = typeBinding;
            iDimensions = 0;
        }
        int iKind2 = typeBindingLeafComponentType.kind();
        if (iKind2 != 132) {
            if (iKind2 != 260) {
                zIsGenericType = iKind2 == 2052;
            } else {
                zIsGenericType = ((ParameterizedTypeBinding) typeBindingLeafComponentType).genericType().isGenericType();
            }
            ReferenceBinding referenceBindingEnclosingType = typeBindingLeafComponentType.enclosingType();
            if (referenceBindingEnclosingType != null) {
                if (referenceBindingEnclosingType.kind() == 1028) {
                    zIsGenericType |= !((ReferenceBinding) typeBindingLeafComponentType).isStatic();
                    referenceBindingConvertToParameterizedType = referenceBindingEnclosingType;
                } else if (z && !zIsGenericType) {
                    referenceBindingConvertToParameterizedType = (ReferenceBinding) convertToRawType(referenceBindingEnclosingType, z);
                    zIsGenericType = TypeBinding.notEquals(referenceBindingEnclosingType, referenceBindingConvertToParameterizedType);
                } else if (zIsGenericType || ((ReferenceBinding) typeBindingLeafComponentType).isStatic()) {
                    referenceBindingConvertToParameterizedType = (ReferenceBinding) convertToRawType(referenceBindingEnclosingType, false);
                } else {
                    referenceBindingConvertToParameterizedType = convertToParameterizedType(referenceBindingEnclosingType);
                }
                if (zIsGenericType) {
                    typeBindingCreateParameterizedType = createRawType((ReferenceBinding) typeBindingLeafComponentType.erasure(), referenceBindingConvertToParameterizedType);
                } else if (TypeBinding.notEquals(referenceBindingEnclosingType, referenceBindingConvertToParameterizedType)) {
                    typeBindingCreateParameterizedType = createParameterizedType((ReferenceBinding) typeBindingLeafComponentType.erasure(), null, referenceBindingConvertToParameterizedType);
                } else {
                    typeBindingCreateParameterizedType = typeBindingLeafComponentType;
                }
            } else if (zIsGenericType) {
                typeBindingCreateParameterizedType = createRawType((ReferenceBinding) typeBindingLeafComponentType.erasure(), null);
            } else {
                typeBindingCreateParameterizedType = typeBindingLeafComponentType;
            }
            if (TypeBinding.notEquals(typeBindingLeafComponentType, typeBindingCreateParameterizedType)) {
                return iDimensions > 0 ? createArrayType(typeBindingCreateParameterizedType, iDimensions) : typeBindingCreateParameterizedType;
            }
        }
        return typeBinding;
    }

    public ReferenceBinding[] convertToRawTypes(ReferenceBinding[] referenceBindingArr, boolean z, boolean z2) {
        if (referenceBindingArr == null) {
            return null;
        }
        int length = referenceBindingArr.length;
        ReferenceBinding[] referenceBindingArr2 = referenceBindingArr;
        for (int i = 0; i < length; i++) {
            ReferenceBinding referenceBinding = referenceBindingArr[i];
            ReferenceBinding referenceBinding2 = (ReferenceBinding) convertToRawType(z ? referenceBinding.erasure() : referenceBinding, z2);
            if (TypeBinding.notEquals(referenceBinding2, referenceBinding)) {
                if (referenceBindingArr2 == referenceBindingArr) {
                    referenceBindingArr2 = new ReferenceBinding[length];
                    System.arraycopy(referenceBindingArr, 0, referenceBindingArr2, 0, i);
                }
                referenceBindingArr2[i] = referenceBinding2;
            } else if (referenceBindingArr2 != referenceBindingArr) {
                referenceBindingArr2[i] = referenceBinding;
            }
        }
        return referenceBindingArr2;
    }

    /* JADX WARN: Code duplicated, block: B:40:0x0092  */
    public TypeBinding convertUnresolvedBinaryToRawType(TypeBinding typeBinding) {
        int iDimensions;
        TypeBinding typeBindingLeafComponentType;
        TypeBinding typeBindingCreateParameterizedType;
        int iKind = typeBinding.kind();
        boolean zIsGenericType = false;
        if (iKind == 68) {
            iDimensions = typeBinding.dimensions();
            typeBindingLeafComponentType = typeBinding.leafComponentType();
        } else {
            if (iKind == 132 || iKind == 516 || iKind == 1028 || iKind == 4100 || iKind == 8196 || typeBinding.f626id == 1) {
                return typeBinding;
            }
            typeBindingLeafComponentType = typeBinding;
            iDimensions = 0;
        }
        int iKind2 = typeBindingLeafComponentType.kind();
        if (iKind2 != 132) {
            if (iKind2 == 260) {
                zIsGenericType = ((ParameterizedTypeBinding) typeBindingLeafComponentType).genericType().isGenericType();
            } else if (iKind2 == 2052) {
                zIsGenericType = true;
            }
            ReferenceBinding referenceBindingEnclosingType = typeBindingLeafComponentType.enclosingType();
            if (referenceBindingEnclosingType != null) {
                ReferenceBinding referenceBinding = (ReferenceBinding) convertUnresolvedBinaryToRawType(referenceBindingEnclosingType);
                if (TypeBinding.notEquals(referenceBinding, referenceBindingEnclosingType)) {
                    zIsGenericType |= true ^ ((ReferenceBinding) typeBindingLeafComponentType).isStatic();
                }
                if (zIsGenericType) {
                    typeBindingCreateParameterizedType = createRawType((ReferenceBinding) typeBindingLeafComponentType.erasure(), referenceBinding);
                } else if (TypeBinding.notEquals(referenceBindingEnclosingType, referenceBinding)) {
                    typeBindingCreateParameterizedType = createParameterizedType((ReferenceBinding) typeBindingLeafComponentType.erasure(), null, referenceBinding);
                } else {
                    typeBindingCreateParameterizedType = typeBindingLeafComponentType;
                }
            } else if (zIsGenericType) {
                typeBindingCreateParameterizedType = createRawType((ReferenceBinding) typeBindingLeafComponentType.erasure(), null);
            } else {
                typeBindingCreateParameterizedType = typeBindingLeafComponentType;
            }
            if (TypeBinding.notEquals(typeBindingLeafComponentType, typeBindingCreateParameterizedType)) {
                return iDimensions > 0 ? createArrayType(typeBindingCreateParameterizedType, iDimensions) : typeBindingCreateParameterizedType;
            }
        }
        return typeBinding;
    }

    public AnnotationBinding createAnnotation(ReferenceBinding referenceBinding, ElementValuePair[] elementValuePairArr) {
        if (elementValuePairArr.length != 0) {
            AnnotationBinding.setMethodBindings(referenceBinding, elementValuePairArr);
            return new AnnotationBinding(referenceBinding, elementValuePairArr);
        }
        return this.typeSystem.getAnnotationType(referenceBinding, true);
    }

    public AnnotationBinding createUnresolvedAnnotation(ReferenceBinding referenceBinding, ElementValuePair[] elementValuePairArr) {
        if (elementValuePairArr.length != 0) {
            return new UnresolvedAnnotationBinding(referenceBinding, elementValuePairArr, this);
        }
        return this.typeSystem.getAnnotationType(referenceBinding, false);
    }

    public ArrayBinding createArrayType(TypeBinding typeBinding, int i) {
        return this.typeSystem.getArrayType(typeBinding, i);
    }

    public ArrayBinding createArrayType(TypeBinding typeBinding, int i, AnnotationBinding[] annotationBindingArr) {
        return this.typeSystem.getArrayType(typeBinding, i, annotationBindingArr);
    }

    public TypeBinding createIntersectionType18(ReferenceBinding[] referenceBindingArr) {
        return this.typeSystem.getIntersectionType18(referenceBindingArr);
    }

    public BinaryTypeBinding createBinaryTypeFrom(IBinaryType iBinaryType, PackageBinding packageBinding, AccessRestriction accessRestriction) {
        return createBinaryTypeFrom(iBinaryType, packageBinding, true, accessRestriction);
    }

    public BinaryTypeBinding createBinaryTypeFrom(IBinaryType iBinaryType, PackageBinding packageBinding, boolean z, AccessRestriction accessRestriction) {
        BinaryTypeBinding binaryTypeBinding = new BinaryTypeBinding(packageBinding, iBinaryType, this);
        ReferenceBinding type0 = packageBinding.getType0(binaryTypeBinding.compoundName[binaryTypeBinding.compoundName.length - 1]);
        if (type0 != null && !type0.isUnresolvedType()) {
            if (type0.isBinaryBinding()) {
                return (BinaryTypeBinding) type0;
            }
            return null;
        }
        packageBinding.addType(binaryTypeBinding);
        setAccessRestriction(binaryTypeBinding, accessRestriction);
        binaryTypeBinding.cachePartsFrom(iBinaryType, z);
        return binaryTypeBinding;
    }

    public MissingTypeBinding createMissingType(PackageBinding packageBinding, char[][] cArr) {
        if (packageBinding == null && (packageBinding = computePackageFrom(cArr, true)) == TheNotFoundPackage) {
            packageBinding = this.defaultPackage;
        }
        MissingTypeBinding missingTypeBinding = new MissingTypeBinding(packageBinding, cArr, this);
        if (missingTypeBinding.f626id != 1) {
            ReferenceBinding type = getType(TypeConstants.JAVA_LANG_OBJECT);
            if (type == null) {
                type = createMissingType(null, TypeConstants.JAVA_LANG_OBJECT);
            }
            missingTypeBinding.setMissingSuperclass(type);
        }
        packageBinding.addType(missingTypeBinding);
        if (this.missingTypes == null) {
            this.missingTypes = new ArrayList(3);
        }
        this.missingTypes.add(missingTypeBinding);
        return missingTypeBinding;
    }

    public PackageBinding createPackage(char[][] cArr) {
        PackageBinding package0 = getPackage0(cArr[0]);
        if (package0 == null || package0 == TheNotFoundPackage) {
            package0 = new PackageBinding(cArr[0], this);
            this.knownPackages.put(cArr[0], package0);
        }
        int length = cArr.length;
        for (int i = 1; i < length; i++) {
            ReferenceBinding type0 = package0.getType0(cArr[i]);
            if (type0 != null && type0 != TheNotFoundType && !(type0 instanceof UnresolvedReferenceBinding)) {
                return null;
            }
            PackageBinding package1 = package0.getPackage0(cArr[i]);
            if (package1 == null || package1 == TheNotFoundPackage) {
                INameEnvironment iNameEnvironment = this.nameEnvironment;
                if (iNameEnvironment instanceof INameEnvironmentExtension) {
                    if (((INameEnvironmentExtension) iNameEnvironment).findType(cArr[i], package0.compoundName, false) != null) {
                        return null;
                    }
                } else if (iNameEnvironment.findType(cArr[i], package0.compoundName) != null) {
                    return null;
                }
                package1 = new PackageBinding(CharOperation.subarray(cArr, 0, i + 1), package0, this);
                package0.addPackage(package1);
            }
            package0 = package1;
        }
        return package0;
    }

    public ParameterizedGenericMethodBinding createParameterizedGenericMethod(MethodBinding methodBinding, RawTypeBinding rawTypeBinding) {
        boolean z;
        int i;
        ParameterizedGenericMethodBinding[] parameterizedGenericMethodBindingArr = (ParameterizedGenericMethodBinding[]) this.uniqueParameterizedGenericMethodBindings.get(methodBinding);
        if (parameterizedGenericMethodBindingArr != null) {
            int length = parameterizedGenericMethodBindingArr.length;
            i = 0;
            while (i < length) {
                ParameterizedGenericMethodBinding parameterizedGenericMethodBinding = parameterizedGenericMethodBindingArr[i];
                if (parameterizedGenericMethodBinding == null) {
                    break;
                }
                if (parameterizedGenericMethodBinding.isRaw) {
                    if (parameterizedGenericMethodBinding.declaringClass == (rawTypeBinding == null ? methodBinding.declaringClass : rawTypeBinding)) {
                        return parameterizedGenericMethodBinding;
                    }
                }
                i++;
            }
            z = true;
        } else {
            parameterizedGenericMethodBindingArr = new ParameterizedGenericMethodBinding[5];
            this.uniqueParameterizedGenericMethodBindings.put(methodBinding, parameterizedGenericMethodBindingArr);
            z = false;
            i = 0;
        }
        int length2 = parameterizedGenericMethodBindingArr.length;
        if (z && i == length2) {
            ParameterizedGenericMethodBinding[] parameterizedGenericMethodBindingArr2 = new ParameterizedGenericMethodBinding[length2 * 2];
            System.arraycopy(parameterizedGenericMethodBindingArr, 0, parameterizedGenericMethodBindingArr2, 0, length2);
            this.uniqueParameterizedGenericMethodBindings.put(methodBinding, parameterizedGenericMethodBindingArr2);
            parameterizedGenericMethodBindingArr = parameterizedGenericMethodBindingArr2;
        }
        ParameterizedGenericMethodBinding parameterizedGenericMethodBinding2 = new ParameterizedGenericMethodBinding(methodBinding, rawTypeBinding, this);
        parameterizedGenericMethodBindingArr[i] = parameterizedGenericMethodBinding2;
        return parameterizedGenericMethodBinding2;
    }

    public ParameterizedGenericMethodBinding createParameterizedGenericMethod(MethodBinding methodBinding, TypeBinding[] typeBindingArr) {
        return createParameterizedGenericMethod(methodBinding, typeBindingArr, false, false);
    }

    public ParameterizedGenericMethodBinding createParameterizedGenericMethod(MethodBinding methodBinding, TypeBinding[] typeBindingArr, boolean z, boolean z2) {
        boolean z3;
        int i;
        ParameterizedGenericMethodBinding[] parameterizedGenericMethodBindingArr = (ParameterizedGenericMethodBinding[]) this.uniqueParameterizedGenericMethodBindings.get(methodBinding);
        int length = typeBindingArr == null ? 0 : typeBindingArr.length;
        if (parameterizedGenericMethodBindingArr != null) {
            int length2 = parameterizedGenericMethodBindingArr.length;
            i = 0;
            while (i < length2) {
                ParameterizedGenericMethodBinding parameterizedGenericMethodBinding = parameterizedGenericMethodBindingArr[i];
                if (parameterizedGenericMethodBinding == null) {
                    break;
                }
                if (!parameterizedGenericMethodBinding.isRaw && parameterizedGenericMethodBinding.inferredWithUncheckedConversion == z) {
                    TypeBinding[] typeBindingArr2 = parameterizedGenericMethodBinding.typeArguments;
                    int length3 = typeBindingArr2 == null ? 0 : typeBindingArr2.length;
                    if (length != length3) {
                        continue;
                    } else {
                        int i2 = 0;
                        while (true) {
                            if (i2 >= length3) {
                                if (z) {
                                    if (parameterizedGenericMethodBinding.returnType.isParameterizedType() || parameterizedGenericMethodBinding.returnType.isTypeVariable()) {
                                        break;
                                    }
                                    for (ReferenceBinding referenceBinding : parameterizedGenericMethodBinding.thrownExceptions) {
                                        if (referenceBinding.isParameterizedType() || referenceBinding.isTypeVariable()) {
                                            break;
                                        }
                                    }
                                }
                                return parameterizedGenericMethodBinding;
                            }
                            if (typeBindingArr[i2] != typeBindingArr2[i2]) {
                                break;
                            }
                            i2++;
                        }
                    }
                }
                i++;
            }
            z3 = true;
        } else {
            parameterizedGenericMethodBindingArr = new ParameterizedGenericMethodBinding[5];
            this.uniqueParameterizedGenericMethodBindings.put(methodBinding, parameterizedGenericMethodBindingArr);
            z3 = false;
            i = 0;
        }
        int length4 = parameterizedGenericMethodBindingArr.length;
        if (z3 && i == length4) {
            ParameterizedGenericMethodBinding[] parameterizedGenericMethodBindingArr2 = new ParameterizedGenericMethodBinding[length4 * 2];
            System.arraycopy(parameterizedGenericMethodBindingArr, 0, parameterizedGenericMethodBindingArr2, 0, length4);
            this.uniqueParameterizedGenericMethodBindings.put(methodBinding, parameterizedGenericMethodBindingArr2);
            parameterizedGenericMethodBindingArr = parameterizedGenericMethodBindingArr2;
        }
        ParameterizedGenericMethodBinding parameterizedGenericMethodBinding2 = new ParameterizedGenericMethodBinding(methodBinding, typeBindingArr, this, z, z2);
        parameterizedGenericMethodBindingArr[i] = parameterizedGenericMethodBinding2;
        return parameterizedGenericMethodBinding2;
    }

    public PolymorphicMethodBinding createPolymorphicMethod(MethodBinding methodBinding, TypeBinding[] typeBindingArr) {
        boolean z;
        int i;
        String str = new String(methodBinding.selector);
        PolymorphicMethodBinding[] polymorphicMethodBindingArr = (PolymorphicMethodBinding[]) this.uniquePolymorphicMethodBindings.get(str);
        int length = typeBindingArr == null ? 0 : typeBindingArr.length;
        TypeBinding[] typeBindingArr2 = new TypeBinding[length];
        for (int i2 = 0; i2 < length; i2++) {
            TypeBinding typeBinding = typeBindingArr[i2];
            if (typeBinding.f626id == 12) {
                typeBindingArr2[i2] = getType(JAVA_LANG_VOID);
            } else {
                typeBindingArr2[i2] = typeBinding.erasure();
            }
        }
        if (polymorphicMethodBindingArr != null) {
            int length2 = polymorphicMethodBindingArr.length;
            i = 0;
            while (i < length2) {
                PolymorphicMethodBinding polymorphicMethodBinding = polymorphicMethodBindingArr[i];
                if (polymorphicMethodBinding == null) {
                    break;
                }
                if (polymorphicMethodBinding.matches(typeBindingArr2, methodBinding.returnType)) {
                    return polymorphicMethodBinding;
                }
                i++;
            }
            z = true;
        } else {
            polymorphicMethodBindingArr = new PolymorphicMethodBinding[5];
            this.uniquePolymorphicMethodBindings.put(str, polymorphicMethodBindingArr);
            z = false;
            i = 0;
        }
        int length3 = polymorphicMethodBindingArr.length;
        if (z && i == length3) {
            PolymorphicMethodBinding[] polymorphicMethodBindingArr2 = new PolymorphicMethodBinding[length3 * 2];
            System.arraycopy(polymorphicMethodBindingArr, 0, polymorphicMethodBindingArr2, 0, length3);
            this.uniquePolymorphicMethodBindings.put(str, polymorphicMethodBindingArr2);
            polymorphicMethodBindingArr = polymorphicMethodBindingArr2;
        }
        PolymorphicMethodBinding polymorphicMethodBinding2 = new PolymorphicMethodBinding(methodBinding, typeBindingArr2);
        polymorphicMethodBindingArr[i] = polymorphicMethodBinding2;
        return polymorphicMethodBinding2;
    }

    public boolean usesAnnotatedTypeSystem() {
        return this.typeSystem.isAnnotatedTypeSystem();
    }

    public MethodBinding updatePolymorphicMethodReturnType(PolymorphicMethodBinding polymorphicMethodBinding, TypeBinding typeBinding) {
        boolean z;
        int i;
        String str = new String(polymorphicMethodBinding.selector);
        PolymorphicMethodBinding[] polymorphicMethodBindingArr = (PolymorphicMethodBinding[]) this.uniquePolymorphicMethodBindings.get(str);
        TypeBinding[] typeBindingArr = polymorphicMethodBinding.parameters;
        if (polymorphicMethodBindingArr != null) {
            int length = polymorphicMethodBindingArr.length;
            i = 0;
            while (i < length) {
                PolymorphicMethodBinding polymorphicMethodBinding2 = polymorphicMethodBindingArr[i];
                if (polymorphicMethodBinding2 == null) {
                    break;
                }
                if (polymorphicMethodBinding2.matches(typeBindingArr, typeBinding)) {
                    return polymorphicMethodBinding2;
                }
                i++;
            }
            z = true;
        } else {
            polymorphicMethodBindingArr = new PolymorphicMethodBinding[5];
            this.uniquePolymorphicMethodBindings.put(str, polymorphicMethodBindingArr);
            z = false;
            i = 0;
        }
        int length2 = polymorphicMethodBindingArr.length;
        if (z && i == length2) {
            PolymorphicMethodBinding[] polymorphicMethodBindingArr2 = new PolymorphicMethodBinding[length2 * 2];
            System.arraycopy(polymorphicMethodBindingArr, 0, polymorphicMethodBindingArr2, 0, length2);
            this.uniquePolymorphicMethodBindings.put(str, polymorphicMethodBindingArr2);
            polymorphicMethodBindingArr = polymorphicMethodBindingArr2;
        }
        PolymorphicMethodBinding polymorphicMethodBinding3 = new PolymorphicMethodBinding(polymorphicMethodBinding.original(), typeBinding, typeBindingArr);
        polymorphicMethodBindingArr[i] = polymorphicMethodBinding3;
        return polymorphicMethodBinding3;
    }

    public ParameterizedMethodBinding createGetClassMethod(TypeBinding typeBinding, MethodBinding methodBinding, Scope scope) {
        ParameterizedMethodBinding parameterizedMethodBinding;
        SimpleLookupTable simpleLookupTable = this.uniqueGetClassMethodBinding;
        if (simpleLookupTable == null) {
            this.uniqueGetClassMethodBinding = new SimpleLookupTable(3);
            parameterizedMethodBinding = null;
        } else {
            parameterizedMethodBinding = (ParameterizedMethodBinding) simpleLookupTable.get(typeBinding);
        }
        if (parameterizedMethodBinding != null) {
            return parameterizedMethodBinding;
        }
        ParameterizedMethodBinding parameterizedMethodBindingInstantiateGetClass = ParameterizedMethodBinding.instantiateGetClass(typeBinding, methodBinding, scope);
        this.uniqueGetClassMethodBinding.put(typeBinding, parameterizedMethodBindingInstantiateGetClass);
        return parameterizedMethodBindingInstantiateGetClass;
    }

    public ReferenceBinding createMemberType(ReferenceBinding referenceBinding, ReferenceBinding referenceBinding2) {
        return this.typeSystem.getMemberType(referenceBinding, referenceBinding2);
    }

    public ParameterizedTypeBinding createParameterizedType(ReferenceBinding referenceBinding, TypeBinding[] typeBindingArr, ReferenceBinding referenceBinding2) {
        AnnotationBinding[] annotationBindingArr = referenceBinding.typeAnnotations;
        if (annotationBindingArr != Binding.NO_ANNOTATIONS) {
            return this.typeSystem.getParameterizedType((ReferenceBinding) referenceBinding.unannotated(), typeBindingArr, referenceBinding2, annotationBindingArr);
        }
        return this.typeSystem.getParameterizedType(referenceBinding, typeBindingArr, referenceBinding2);
    }

    public ParameterizedTypeBinding createParameterizedType(ReferenceBinding referenceBinding, TypeBinding[] typeBindingArr, ReferenceBinding referenceBinding2, AnnotationBinding[] annotationBindingArr) {
        return this.typeSystem.getParameterizedType(referenceBinding, typeBindingArr, referenceBinding2, annotationBindingArr);
    }

    public TypeBinding createAnnotatedType(TypeBinding typeBinding, AnnotationBinding[][] annotationBindingArr) {
        return this.typeSystem.getAnnotatedType(typeBinding, annotationBindingArr);
    }

    public TypeBinding createAnnotatedType(TypeBinding typeBinding, AnnotationBinding[] annotationBindingArr) {
        long j;
        int i;
        int length = annotationBindingArr == null ? 0 : annotationBindingArr.length;
        if (typeBinding == null || length == 0) {
            return typeBinding;
        }
        AnnotationBinding[] typeAnnotations = typeBinding.getTypeAnnotations();
        int length2 = typeAnnotations == null ? 0 : typeAnnotations.length;
        if (length2 > 0) {
            AnnotationBinding[] annotationBindingArr2 = new AnnotationBinding[length + length2];
            System.arraycopy(annotationBindingArr, 0, annotationBindingArr2, 0, length);
            System.arraycopy(typeAnnotations, 0, annotationBindingArr2, length, length2);
            annotationBindingArr = annotationBindingArr2;
        }
        if (this.globalOptions.isAnnotationBasedNullAnalysisEnabled) {
            AnnotationBinding[] annotationBindingArr3 = new AnnotationBinding[annotationBindingArr.length];
            int i2 = 0;
            long j2 = 0;
            for (int i3 = 0; i3 < annotationBindingArr.length; i3++) {
                AnnotationBinding annotationBinding = annotationBindingArr[i3];
                if (annotationBinding == null) {
                    i = i2 + 1;
                    annotationBindingArr3[i2] = null;
                } else {
                    if (annotationBinding.type.hasNullBit(32)) {
                        j = 72057594037927936L;
                    } else {
                        j = annotationBindingArr[i3].type.hasNullBit(64) ? 36028797018963968L : 0L;
                    }
                    if ((j2 & j) == 0) {
                        j2 |= j;
                        i = i2 + 1;
                        annotationBindingArr3[i2] = annotationBindingArr[i3];
                    }
                }
                i2 = i;
            }
            if (i2 < annotationBindingArr.length) {
                annotationBindingArr = new AnnotationBinding[i2];
                System.arraycopy(annotationBindingArr3, 0, annotationBindingArr, 0, i2);
            }
        }
        return this.typeSystem.getAnnotatedType(typeBinding, new AnnotationBinding[][]{annotationBindingArr});
    }

    public RawTypeBinding createRawType(ReferenceBinding referenceBinding, ReferenceBinding referenceBinding2) {
        AnnotationBinding[] annotationBindingArr = referenceBinding.typeAnnotations;
        if (annotationBindingArr != Binding.NO_ANNOTATIONS) {
            return this.typeSystem.getRawType((ReferenceBinding) referenceBinding.unannotated(), referenceBinding2, annotationBindingArr);
        }
        return this.typeSystem.getRawType(referenceBinding, referenceBinding2);
    }

    public RawTypeBinding createRawType(ReferenceBinding referenceBinding, ReferenceBinding referenceBinding2, AnnotationBinding[] annotationBindingArr) {
        return this.typeSystem.getRawType(referenceBinding, referenceBinding2, annotationBindingArr);
    }

    public WildcardBinding createWildcard(ReferenceBinding referenceBinding, int i, TypeBinding typeBinding, TypeBinding[] typeBindingArr, int i2) {
        AnnotationBinding[] annotationBindingArr;
        if (referenceBinding != null && (annotationBindingArr = referenceBinding.typeAnnotations) != Binding.NO_ANNOTATIONS) {
            return this.typeSystem.getWildcard((ReferenceBinding) referenceBinding.unannotated(), i, typeBinding, typeBindingArr, i2, annotationBindingArr);
        }
        return this.typeSystem.getWildcard(referenceBinding, i, typeBinding, typeBindingArr, i2);
    }

    public CaptureBinding createCapturedWildcard(WildcardBinding wildcardBinding, ReferenceBinding referenceBinding, int i, int i2, ASTNode aSTNode, int i3) {
        return this.typeSystem.getCapturedWildcard(wildcardBinding, referenceBinding, i, i2, aSTNode, i3);
    }

    public WildcardBinding createWildcard(ReferenceBinding referenceBinding, int i, TypeBinding typeBinding, TypeBinding[] typeBindingArr, int i2, AnnotationBinding[] annotationBindingArr) {
        return this.typeSystem.getWildcard(referenceBinding, i, typeBinding, typeBindingArr, i2, annotationBindingArr);
    }

    public AccessRestriction getAccessRestriction(TypeBinding typeBinding) {
        return (AccessRestriction) this.accessRestrictions.get(typeBinding);
    }

    public ReferenceBinding getCachedType(char[][] cArr) {
        if (cArr.length == 1) {
            return this.defaultPackage.getType0(cArr[0]);
        }
        PackageBinding package0 = getPackage0(cArr[0]);
        if (package0 != null && package0 != TheNotFoundPackage) {
            int length = cArr.length - 1;
            for (int i = 1; i < length; i++) {
                package0 = package0.getPackage0(cArr[i]);
                if (package0 != null && package0 != TheNotFoundPackage) {
                }
            }
            return package0.getType0(cArr[cArr.length - 1]);
        }
        return null;
    }

    public AnnotationBinding getNullableAnnotation() {
        AnnotationBinding annotationBinding = this.nullableAnnotation;
        if (annotationBinding != null) {
            return annotationBinding;
        }
        AnnotationBinding annotationType = this.typeSystem.getAnnotationType(getResolvedType(this.globalOptions.nullableAnnotationName, null), true);
        this.nullableAnnotation = annotationType;
        return annotationType;
    }

    public char[][] getNullableAnnotationName() {
        return this.globalOptions.nullableAnnotationName;
    }

    public AnnotationBinding getNonNullAnnotation() {
        AnnotationBinding annotationBinding = this.nonNullAnnotation;
        if (annotationBinding != null) {
            return annotationBinding;
        }
        AnnotationBinding annotationType = this.typeSystem.getAnnotationType(getResolvedType(this.globalOptions.nonNullAnnotationName, null), true);
        this.nonNullAnnotation = annotationType;
        return annotationType;
    }

    public AnnotationBinding[] nullAnnotationsFromTagBits(long j) {
        if (j == 72057594037927936L) {
            return new AnnotationBinding[]{getNonNullAnnotation()};
        }
        if (j == 36028797018963968L) {
            return new AnnotationBinding[]{getNullableAnnotation()};
        }
        return null;
    }

    public char[][] getNonNullAnnotationName() {
        return this.globalOptions.nonNullAnnotationName;
    }

    public char[][] getNonNullByDefaultAnnotationName() {
        return this.globalOptions.nonNullByDefaultAnnotationName;
    }

    int getNullAnnotationBit(char[][] cArr) {
        if (this.allNullAnnotations == null) {
            HashMap map = new HashMap();
            this.allNullAnnotations = map;
            map.put(CharOperation.toString(this.globalOptions.nonNullAnnotationName), 32);
            this.allNullAnnotations.put(CharOperation.toString(this.globalOptions.nullableAnnotationName), 64);
            this.allNullAnnotations.put(CharOperation.toString(this.globalOptions.nonNullByDefaultAnnotationName), 128);
            for (String str : this.globalOptions.nullableAnnotationSecondaryNames) {
                this.allNullAnnotations.put(str, 64);
            }
            for (String str2 : this.globalOptions.nonNullAnnotationSecondaryNames) {
                this.allNullAnnotations.put(str2, 32);
            }
            for (String str3 : this.globalOptions.nonNullByDefaultAnnotationSecondaryNames) {
                this.allNullAnnotations.put(str3, 128);
            }
        }
        Integer num = this.allNullAnnotations.get(CharOperation.toString(cArr));
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    public boolean isNullnessAnnotationPackage(PackageBinding packageBinding) {
        return this.nonnullAnnotationPackage == packageBinding || this.nullableAnnotationPackage == packageBinding || this.nonnullByDefaultAnnotationPackage == packageBinding;
    }

    public boolean usesNullTypeAnnotations() {
        int i;
        if (this.globalOptions.useNullTypeAnnotations != null) {
            return this.globalOptions.useNullTypeAnnotations.booleanValue();
        }
        initializeUsesNullTypeAnnotation();
        for (MethodBinding methodBinding : this.deferredEnumMethods) {
            if (CharOperation.equals(methodBinding.selector, TypeConstants.VALUEOF)) {
                i = 10;
            } else {
                i = CharOperation.equals(methodBinding.selector, TypeConstants.VALUES) ? 9 : 0;
            }
            if (i != 0) {
                SyntheticMethodBinding.markNonNull(methodBinding, i, this);
            }
        }
        this.deferredEnumMethods.clear();
        return this.globalOptions.useNullTypeAnnotations.booleanValue();
    }

    private void initializeUsesNullTypeAnnotation() {
        this.globalOptions.useNullTypeAnnotations = Boolean.FALSE;
        if (!this.globalOptions.isAnnotationBasedNullAnalysisEnabled || this.globalOptions.originalSourceLevel < ClassFileConstants.JDK1_8) {
            return;
        }
        AnnotationBinding annotationBinding = this.nullableAnnotation;
        ReferenceBinding annotationType = annotationBinding != null ? annotationBinding.getAnnotationType() : getType(getNullableAnnotationName());
        AnnotationBinding annotationBinding2 = this.nonNullAnnotation;
        ReferenceBinding annotationType2 = annotationBinding2 != null ? annotationBinding2.getAnnotationType() : getType(getNonNullAnnotationName());
        if ((annotationType == null && annotationType2 == null) || annotationType == null || annotationType2 == null) {
            return;
        }
        long annotationTagBits = annotationType.getAnnotationTagBits() & 9007199254740992L;
        if (annotationTagBits == (annotationType2.getAnnotationTagBits() & 9007199254740992L) && annotationTagBits != 0) {
            this.globalOptions.useNullTypeAnnotations = Boolean.TRUE;
        }
    }

    PackageBinding getPackage0(char[] cArr) {
        return this.knownPackages.get(cArr);
    }

    public ReferenceBinding getResolvedType(char[][] cArr, Scope scope) {
        ReferenceBinding type = getType(cArr);
        if (type != null) {
            return type;
        }
        this.problemReporter.isClassPathCorrect(cArr, scope == null ? this.unitBeingCompleted : scope.referenceCompilationUnit(), this.missingClassFileLocation);
        return createMissingType(null, cArr);
    }

    PackageBinding getTopLevelPackage(char[] cArr) {
        PackageBinding package0 = getPackage0(cArr);
        if (package0 != null) {
            if (package0 == TheNotFoundPackage) {
                return null;
            }
            return package0;
        }
        if (this.nameEnvironment.isPackage(null, cArr)) {
            HashtableOfPackage hashtableOfPackage = this.knownPackages;
            PackageBinding packageBinding = new PackageBinding(cArr, this);
            hashtableOfPackage.put(cArr, packageBinding);
            return packageBinding;
        }
        this.knownPackages.put(cArr, TheNotFoundPackage);
        return null;
    }

    public ReferenceBinding getType(char[][] cArr) {
        ReferenceBinding referenceBindingAskForType;
        if (cArr.length == 1) {
            referenceBindingAskForType = this.defaultPackage.getType0(cArr[0]);
            if (referenceBindingAskForType == null) {
                PackageBinding package0 = getPackage0(cArr[0]);
                if (package0 != null && package0 != TheNotFoundPackage) {
                    return null;
                }
                referenceBindingAskForType = askForType(this.defaultPackage, cArr[0]);
            }
        } else {
            PackageBinding package1 = getPackage0(cArr[0]);
            if (package1 == TheNotFoundPackage) {
                return null;
            }
            if (package1 != null) {
                int length = cArr.length - 1;
                for (int i = 1; i < length; i++) {
                    package1 = package1.getPackage0(cArr[i]);
                    if (package1 == null) {
                        break;
                    }
                    if (package1 == TheNotFoundPackage) {
                        return null;
                    }
                }
            }
            if (package1 == null) {
                referenceBindingAskForType = askForType(cArr);
            } else {
                ReferenceBinding type0 = package1.getType0(cArr[cArr.length - 1]);
                referenceBindingAskForType = type0 == null ? askForType(package1, cArr[cArr.length - 1]) : type0;
            }
        }
        if (referenceBindingAskForType == null || referenceBindingAskForType == TheNotFoundType) {
            return null;
        }
        ReferenceBinding referenceBinding = (ReferenceBinding) BinaryTypeBinding.resolveType(referenceBindingAskForType, this, false);
        return referenceBinding.isNestedType() ? new ProblemReferenceBinding(cArr, referenceBinding, 4) : referenceBinding;
    }

    private TypeBinding[] getTypeArgumentsFromSignature(SignatureWrapper signatureWrapper, TypeVariableBinding[] typeVariableBindingArr, ReferenceBinding referenceBinding, ReferenceBinding referenceBinding2, char[][][] cArr, ITypeAnnotationWalker iTypeAnnotationWalker) {
        ArrayList arrayList = new ArrayList(2);
        int i = 0;
        while (true) {
            int i2 = i + 1;
            arrayList.add(getTypeFromVariantTypeSignature(signatureWrapper, typeVariableBindingArr, referenceBinding, referenceBinding2, i, cArr, iTypeAnnotationWalker.toTypeArgument(i)));
            if (signatureWrapper.signature[signatureWrapper.start] == '>') {
                signatureWrapper.start++;
                TypeBinding[] typeBindingArr = new TypeBinding[arrayList.size()];
                arrayList.toArray(typeBindingArr);
                return typeBindingArr;
            }
            i = i2;
        }
    }

    private ReferenceBinding getTypeFromCompoundName(char[][] cArr, boolean z, boolean z2) {
        ReferenceBinding cachedType = getCachedType(cArr);
        if (cachedType == null) {
            PackageBinding packageBindingComputePackageFrom = computePackageFrom(cArr, false);
            UnresolvedReferenceBinding unresolvedReferenceBinding = new UnresolvedReferenceBinding(cArr, packageBindingComputePackageFrom);
            if (z2) {
                unresolvedReferenceBinding.tagBits |= 128;
            }
            packageBindingComputePackageFrom.addType(unresolvedReferenceBinding);
            return unresolvedReferenceBinding;
        }
        if (cachedType != TheNotFoundType) {
            return !z ? (ReferenceBinding) convertUnresolvedBinaryToRawType(cachedType) : cachedType;
        }
        if (!z2) {
            this.problemReporter.isClassPathCorrect(cArr, this.unitBeingCompleted, this.missingClassFileLocation);
        }
        return createMissingType(null, cArr);
    }

    ReferenceBinding getTypeFromConstantPoolName(char[] cArr, int i, int i2, boolean z, char[][][] cArr2, ITypeAnnotationWalker iTypeAnnotationWalker) {
        if (i2 == -1) {
            i2 = cArr.length;
        }
        char[][] cArrSplitOn = CharOperation.splitOn('/', cArr, i, i2);
        boolean z2 = false;
        if (cArr2 != null) {
            for (char[][] cArr3 : cArr2) {
                if (CharOperation.equals(cArrSplitOn, cArr3)) {
                    z2 = true;
                    break;
                }
            }
        }
        ReferenceBinding typeFromCompoundName = getTypeFromCompoundName(cArrSplitOn, z, z2);
        return iTypeAnnotationWalker != ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER ? (ReferenceBinding) annotateType(typeFromCompoundName, iTypeAnnotationWalker, cArr2) : typeFromCompoundName;
    }

    ReferenceBinding getTypeFromConstantPoolName(char[] cArr, int i, int i2, boolean z, char[][][] cArr2) {
        return getTypeFromConstantPoolName(cArr, i, i2, z, cArr2, ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER);
    }

    /* JADX WARN: Code duplicated, block: B:22:0x003d  */
    /* JADX WARN: Code duplicated, block: B:24:0x0043  */
    /* JADX WARN: Code duplicated, block: B:26:0x0049  */
    /* JADX WARN: Code duplicated, block: B:28:0x004d  */
    /* JADX WARN: Code duplicated, block: B:30:0x0051  */
    /* JADX WARN: Code duplicated, block: B:32:0x0055  */
    /* JADX WARN: Code duplicated, block: B:34:0x0059  */
    /* JADX WARN: Code duplicated, block: B:36:0x005d  */
    /* JADX WARN: Code duplicated, block: B:37:0x0060  */
    /* JADX WARN: Code duplicated, block: B:38:0x0068  */
    /* JADX WARN: Code duplicated, block: B:39:0x006b  */
    /* JADX WARN: Code duplicated, block: B:40:0x006e  */
    /* JADX WARN: Code duplicated, block: B:41:0x0071  */
    /* JADX WARN: Code duplicated, block: B:42:0x0074  */
    /* JADX WARN: Code duplicated, block: B:43:0x0077  */
    /* JADX WARN: Code duplicated, block: B:44:0x007a  */
    /* JADX WARN: Code duplicated, block: B:45:0x007d  */
    /* JADX WARN: Code duplicated, block: B:46:0x0080  */
    /* JADX WARN: Code duplicated, block: B:47:0x0083  */
    /* JADX WARN: Code duplicated, block: B:49:0x0092 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:50:0x0094 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:51:0x0095  */
    /* JADX WARN: Code duplicated, block: B:53:0x009b  */
    /* JADX WARN: Code duplicated, block: B:55:0x009f  */
    /* JADX WARN: Code duplicated, block: B:57:0x00a5  */
    /* JADX WARN: Code duplicated, block: B:64:? A[RETURN, SYNTHETIC] */
    TypeBinding getTypeFromSignature(char[] cArr, int i, int i2, boolean z, TypeBinding typeBinding, char[][][] cArr2, ITypeAnnotationWalker iTypeAnnotationWalker) {
        ITypeAnnotationWalker nextArrayDimension;
        AnnotationBinding[][] annotationBindingArr;
        ITypeAnnotationWalker iTypeAnnotationWalker2;
        int length;
        char c;
        int i3 = i;
        int i4 = 0;
        while (cArr[i3] == '[') {
            i3++;
            i4++;
        }
        TypeBinding typeFromConstantPoolName = null;
        if (i4 > 0) {
            nextArrayDimension = iTypeAnnotationWalker;
            if (nextArrayDimension != ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER) {
                AnnotationBinding[][] annotationBindingArr2 = null;
                for (int i5 = 0; i5 < i4; i5++) {
                    AnnotationBinding[] annotationBindingArrCreateAnnotations = BinaryTypeBinding.createAnnotations(nextArrayDimension.getAnnotationsAtCursor(0), this, cArr2);
                    if (annotationBindingArrCreateAnnotations != Binding.NO_ANNOTATIONS) {
                        if (annotationBindingArr2 == null) {
                            annotationBindingArr2 = new AnnotationBinding[i4][];
                        }
                        annotationBindingArr2[i5] = annotationBindingArrCreateAnnotations;
                    }
                    nextArrayDimension = nextArrayDimension.toNextArrayDimension();
                }
                annotationBindingArr = annotationBindingArr2;
            }
            iTypeAnnotationWalker2 = nextArrayDimension;
            length = i2;
            if (length == -1) {
                length = cArr.length - 1;
            }
            if (i3 == length) {
                c = cArr[i3];
                if (c != 'F') {
                    typeFromConstantPoolName = TypeBinding.FLOAT;
                } else if (c != 'S') {
                    typeFromConstantPoolName = TypeBinding.SHORT;
                } else if (c != 'V') {
                    typeFromConstantPoolName = TypeBinding.VOID;
                } else if (c != 'Z') {
                    typeFromConstantPoolName = TypeBinding.BOOLEAN;
                } else if (c != 'I') {
                    typeFromConstantPoolName = TypeBinding.INT;
                } else if (c != 'J') {
                    switch (c) {
                        case 'B':
                            typeFromConstantPoolName = TypeBinding.BYTE;
                            break;
                        case 'C':
                            typeFromConstantPoolName = TypeBinding.CHAR;
                            break;
                        case 'D':
                            typeFromConstantPoolName = TypeBinding.DOUBLE;
                            break;
                        default:
                            this.problemReporter.corruptedSignature(typeBinding, cArr, i3);
                            break;
                    }
                } else {
                    typeFromConstantPoolName = TypeBinding.LONG;
                }
            } else {
                typeFromConstantPoolName = getTypeFromConstantPoolName(cArr, i3 + 1, length, z, cArr2);
            }
            if (z) {
                if (i4 == 0) {
                    return typeFromConstantPoolName;
                }
                throw new IllegalStateException();
            }
            if (iTypeAnnotationWalker2 != ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER) {
                typeFromConstantPoolName = annotateType(typeFromConstantPoolName, iTypeAnnotationWalker2, cArr2);
            }
            if (i4 != 0) {
                return this.typeSystem.getArrayType(typeFromConstantPoolName, i4, AnnotatableTypeSystem.flattenedAnnotations(annotationBindingArr));
            }
            return typeFromConstantPoolName;
        }
        nextArrayDimension = iTypeAnnotationWalker;
        annotationBindingArr = null;
        iTypeAnnotationWalker2 = nextArrayDimension;
        length = i2;
        if (length == -1) {
            length = cArr.length - 1;
        }
        if (i3 == length) {
            c = cArr[i3];
            if (c != 'F') {
                typeFromConstantPoolName = TypeBinding.FLOAT;
            } else if (c != 'S') {
                typeFromConstantPoolName = TypeBinding.SHORT;
            } else if (c != 'V') {
                typeFromConstantPoolName = TypeBinding.VOID;
            } else if (c != 'Z') {
                typeFromConstantPoolName = TypeBinding.BOOLEAN;
            } else if (c != 'I') {
                typeFromConstantPoolName = TypeBinding.INT;
            } else if (c != 'J') {
                switch (c) {
                    case 'B':
                        typeFromConstantPoolName = TypeBinding.BYTE;
                        break;
                    case 'C':
                        typeFromConstantPoolName = TypeBinding.CHAR;
                        break;
                    case 'D':
                        typeFromConstantPoolName = TypeBinding.DOUBLE;
                        break;
                    default:
                        this.problemReporter.corruptedSignature(typeBinding, cArr, i3);
                        break;
                }
            } else {
                typeFromConstantPoolName = TypeBinding.LONG;
            }
        } else {
            typeFromConstantPoolName = getTypeFromConstantPoolName(cArr, i3 + 1, length, z, cArr2);
        }
        if (z) {
            if (i4 == 0) {
                return typeFromConstantPoolName;
            }
            throw new IllegalStateException();
        }
        if (iTypeAnnotationWalker2 != ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER) {
            typeFromConstantPoolName = annotateType(typeFromConstantPoolName, iTypeAnnotationWalker2, cArr2);
        }
        if (i4 != 0) {
            return this.typeSystem.getArrayType(typeFromConstantPoolName, i4, AnnotatableTypeSystem.flattenedAnnotations(annotationBindingArr));
        }
        return typeFromConstantPoolName;
    }

    private TypeBinding annotateType(TypeBinding typeBinding, ITypeAnnotationWalker iTypeAnnotationWalker, char[][][] cArr) {
        int iDepth = typeBinding.depth() + 1;
        if (iDepth > 1) {
            if (typeBinding.isUnresolvedType()) {
                typeBinding = ((UnresolvedReferenceBinding) typeBinding).resolve(this, true);
            }
            TypeBinding typeBinding2 = typeBinding;
            iDepth = 0;
            for (TypeBinding typeBindingEnclosingType = typeBinding2; typeBindingEnclosingType != null; typeBindingEnclosingType = typeBindingEnclosingType.enclosingType()) {
                iDepth++;
                if (typeBindingEnclosingType.isStatic()) {
                    break;
                }
            }
            typeBinding = typeBinding2;
        }
        AnnotationBinding[][] annotationBindingArr = null;
        for (int i = 0; i < iDepth; i++) {
            AnnotationBinding[] annotationBindingArrCreateAnnotations = BinaryTypeBinding.createAnnotations(iTypeAnnotationWalker.getAnnotationsAtCursor(typeBinding.f626id), this, cArr);
            if (annotationBindingArrCreateAnnotations != null && annotationBindingArrCreateAnnotations.length > 0) {
                if (annotationBindingArr == null) {
                    annotationBindingArr = new AnnotationBinding[iDepth][];
                }
                annotationBindingArr[i] = annotationBindingArrCreateAnnotations;
            }
            iTypeAnnotationWalker = iTypeAnnotationWalker.toNextNestedType();
        }
        return annotationBindingArr != null ? createAnnotatedType(typeBinding, annotationBindingArr) : typeBinding;
    }

    boolean qualifiedNameMatchesSignature(char[][] cArr, char[] cArr2) {
        int i = 1;
        for (int i2 = 0; i2 < cArr.length; i2++) {
            char[] cArr3 = cArr[i2];
            int i3 = 0;
            while (i3 < cArr3.length) {
                int i4 = i + 1;
                if (cArr3[i3] != cArr2[i]) {
                    return false;
                }
                i3++;
                i = i4;
            }
            char c = cArr2[i];
            if (c == ';' && i2 == cArr.length - 1) {
                return true;
            }
            i++;
            if (c != '/') {
                return false;
            }
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:22:0x004e  */
    /* JADX WARN: Code duplicated, block: B:28:0x0060  */
    /* JADX WARN: Code duplicated, block: B:29:0x0066  */
    /* JADX WARN: Code duplicated, block: B:38:0x0087  */
    /* JADX WARN: Code duplicated, block: B:42:0x00a4  */
    /* JADX WARN: Code duplicated, block: B:46:0x00c1  */
    /* JADX WARN: Code duplicated, block: B:48:0x00d1  */
    /* JADX WARN: Code duplicated, block: B:49:0x00d4  */
    /* JADX WARN: Code duplicated, block: B:52:0x00e6 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:54:0x00e9  */
    /* JADX WARN: Code duplicated, block: B:56:0x00f2  */
    /* JADX WARN: Code duplicated, block: B:63:0x0115  */
    /* JADX WARN: Code duplicated, block: B:70:0x0154  */
    /* JADX WARN: Code duplicated, block: B:72:0x015d  */
    /* JADX WARN: Code duplicated, block: B:74:0x0176  */
    /* JADX WARN: Code duplicated, block: B:77:0x0195  */
    /* JADX WARN: Code duplicated, block: B:78:0x01ad  */
    /* JADX WARN: Code duplicated, block: B:85:0x005a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:90:0x0070 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:95:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:96:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v47 */
    /* JADX WARN: Type inference failed for: r0v48, types: [int] */
    /* JADX WARN: Type inference failed for: r0v52 */
    /* JADX WARN: Type inference failed for: r12v0 */
    /* JADX WARN: Type inference failed for: r12v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r12v7 */
    /* JADX WARN: Type inference failed for: r17v0 */
    /* JADX WARN: Type inference failed for: r17v1 */
    /* JADX WARN: Type inference failed for: r17v2 */
    /* JADX WARN: Type inference failed for: r18v0, types: [org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker] */
    /* JADX WARN: Type inference failed for: r18v1, types: [org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker] */
    /* JADX WARN: Type inference failed for: r18v2 */
    /* JADX WARN: Type inference failed for: r19v0, types: [org.eclipse.jdt.internal.compiler.lookup.LookupEnvironment] */
    /* JADX WARN: Type inference failed for: r1v24, types: [org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker] */
    /* JADX WARN: Type inference failed for: r1v26 */
    /* JADX WARN: Type inference failed for: r1v27 */
    /* JADX WARN: Type inference failed for: r24v1, types: [org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker] */
    /* JADX WARN: Type inference failed for: r4v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r4v6, types: [org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker] */
    /* JADX WARN: Type inference failed for: r4v8, types: [org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker] */
    /* JADX WARN: Type inference failed for: r6v5, types: [org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker] */
    /* JADX WARN: Type inference failed for: r7v0 */
    /* JADX WARN: Type inference failed for: r7v1, types: [org.eclipse.jdt.internal.compiler.env.ITypeAnnotationWalker] */
    /* JADX WARN: Type inference failed for: r7v7 */
    public TypeBinding getTypeFromTypeSignature(SignatureWrapper signatureWrapper, TypeVariableBinding[] typeVariableBindingArr, ReferenceBinding referenceBinding, char[][][] cArr, ITypeAnnotationWalker iTypeAnnotationWalker) {
        ITypeAnnotationWalker iTypeAnnotationWalker2;
        ?? r7;
        AnnotationBinding[][] annotationBindingArr;
        ?? r17;
        ?? r24;
        TypeBinding typeFromSignature;
        ReferenceBinding referenceBinding2;
        ReferenceBinding referenceBindingEnclosingType;
        ParameterizedTypeBinding parameterizedTypeBindingCreateParameterizedType;
        ?? r18;
        int i;
        ReferenceBinding memberType;
        ITypeAnnotationWalker nextNestedType;
        TypeBinding[] typeArgumentsFromSignature;
        int i2;
        int iComputeEnd;
        int length;
        ReferenceBinding referenceBindingEnclosingType2;
        TypeVariableBinding[] typeVariableBindingArrTypeVariables;
        TypeVariableBinding[] typeVariableBindingArr2;
        int length2;
        ITypeAnnotationWalker iTypeAnnotationWalker3;
        ?? nextArrayDimension;
        ?? r12 = 0;
        int i3 = 0;
        while (signatureWrapper.signature[signatureWrapper.start] == '[') {
            signatureWrapper.start++;
            i3++;
            r12 = 0;
        }
        if (i3 > 0) {
            iTypeAnnotationWalker3 = iTypeAnnotationWalker;
            if (iTypeAnnotationWalker3 != ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER) {
                ?? r0 = r12;
                AnnotationBinding[][] annotationBindingArr2 = null;
                while (r0 < i3) {
                    AnnotationBinding[] annotationBindingArrCreateAnnotations = BinaryTypeBinding.createAnnotations(nextArrayDimension.getAnnotationsAtCursor(r12), this, cArr);
                    if (annotationBindingArrCreateAnnotations != Binding.NO_ANNOTATIONS) {
                        if (annotationBindingArr2 == null) {
                            annotationBindingArr2 = new AnnotationBinding[i3][];
                        }
                        annotationBindingArr2[r0] = annotationBindingArrCreateAnnotations;
                    } else {
                        iTypeAnnotationWalker2 = iTypeAnnotationWalker3;
                        nextArrayDimension = iTypeAnnotationWalker3;
                    }
                    r0++;
                    nextArrayDimension = nextArrayDimension.toNextArrayDimension();
                }
                iTypeAnnotationWalker2 = iTypeAnnotationWalker3;
                nextArrayDimension = iTypeAnnotationWalker3;
                r7 = nextArrayDimension;
                annotationBindingArr = annotationBindingArr2;
            }
            if (signatureWrapper.signature[signatureWrapper.start] == 'T') {
                i2 = signatureWrapper.start + 1;
                iComputeEnd = signatureWrapper.computeEnd();
                length = typeVariableBindingArr.length;
                do {
                    length--;
                    if (length < 0) {
                        referenceBindingEnclosingType2 = referenceBinding;
                        do {
                            if (referenceBindingEnclosingType2 instanceof BinaryTypeBinding) {
                                typeVariableBindingArrTypeVariables = ((BinaryTypeBinding) referenceBindingEnclosingType2).typeVariables;
                            } else {
                                typeVariableBindingArrTypeVariables = referenceBindingEnclosingType2.typeVariables();
                            }
                            typeVariableBindingArr2 = typeVariableBindingArrTypeVariables;
                            length2 = typeVariableBindingArr2.length;
                            do {
                                length2--;
                                if (length2 < 0) {
                                    referenceBindingEnclosingType2 = referenceBindingEnclosingType2.enclosingType();
                                }
                            } while (!CharOperation.equals(typeVariableBindingArr2[length2].sourceName, signatureWrapper.signature, i2, iComputeEnd));
                            return getTypeFromTypeVariable(typeVariableBindingArr2[length2], i3, annotationBindingArr, r7, cArr);
                        } while (referenceBindingEnclosingType2 != null);
                        this.problemReporter.undefinedTypeVariableSignature(CharOperation.subarray(signatureWrapper.signature, i2, iComputeEnd), referenceBinding);
                        return null;
                    }
                } while (!CharOperation.equals(typeVariableBindingArr[length].sourceName, signatureWrapper.signature, i2, iComputeEnd));
                return getTypeFromTypeVariable(typeVariableBindingArr[length], i3, annotationBindingArr, r7, cArr);
            }
            char[] cArr2 = signatureWrapper.signature;
            int i4 = signatureWrapper.start;
            int iComputeEnd2 = signatureWrapper.computeEnd();
            if (signatureWrapper.end == signatureWrapper.bracket) {
                r17 = 1;
            } else {
                r17 = r12;
            }
            r24 = r7;
            typeFromSignature = getTypeFromSignature(cArr2, i4, iComputeEnd2, r17, referenceBinding, cArr, r7);
            if (r17 == 0) {
                if (i3 == 0) {
                    return typeFromSignature;
                }
                return createArrayType(typeFromSignature, i3, AnnotatableTypeSystem.flattenedAnnotations(annotationBindingArr));
            }
            referenceBinding2 = (ReferenceBinding) typeFromSignature;
            if ((referenceBinding2 instanceof UnresolvedReferenceBinding) && CharOperation.indexOf('$', referenceBinding2.compoundName[referenceBinding2.compoundName.length - 1]) > 0) {
                referenceBinding2 = (ReferenceBinding) BinaryTypeBinding.resolveType(referenceBinding2, this, r12);
            }
            ReferenceBinding referenceBinding3 = referenceBinding2;
            referenceBindingEnclosingType = referenceBinding3.enclosingType();
            if (referenceBindingEnclosingType != null) {
                referenceBindingEnclosingType = (ReferenceBinding) convertToRawType(referenceBindingEnclosingType, r12);
            }
            ?? r19 = r24;
            parameterizedTypeBindingCreateParameterizedType = createParameterizedType(referenceBinding3, getTypeArgumentsFromSignature(signatureWrapper, typeVariableBindingArr, referenceBinding, referenceBinding3, cArr, r19), referenceBindingEnclosingType, BinaryTypeBinding.createAnnotations(r24.getAnnotationsAtCursor(referenceBinding3.f626id), this, cArr));
            r18 = r19;
            while (signatureWrapper.signature[signatureWrapper.start] == '.') {
                signatureWrapper.start++;
                i = signatureWrapper.start;
                char[] cArrNextWord = signatureWrapper.nextWord();
                BinaryTypeBinding.resolveType(parameterizedTypeBindingCreateParameterizedType, this, false);
                memberType = parameterizedTypeBindingCreateParameterizedType.genericType().getMemberType(cArrNextWord);
                if (memberType == null) {
                    this.problemReporter.corruptedSignature(parameterizedTypeBindingCreateParameterizedType, signatureWrapper.signature, i);
                }
                nextNestedType = r18.toNextNestedType();
                AnnotationBinding[] annotationBindingArrCreateAnnotations2 = BinaryTypeBinding.createAnnotations(nextNestedType.getAnnotationsAtCursor(memberType.f626id), this, cArr);
                if (signatureWrapper.signature[signatureWrapper.start] == '<') {
                    signatureWrapper.start++;
                    typeArgumentsFromSignature = getTypeArgumentsFromSignature(signatureWrapper, typeVariableBindingArr, referenceBinding, memberType, cArr, nextNestedType);
                } else {
                    typeArgumentsFromSignature = null;
                }
                parameterizedTypeBindingCreateParameterizedType = createParameterizedType(memberType, typeArgumentsFromSignature, parameterizedTypeBindingCreateParameterizedType, annotationBindingArrCreateAnnotations2);
                r18 = nextNestedType;
            }
            signatureWrapper.start++;
            if (i3 == 0) {
                return parameterizedTypeBindingCreateParameterizedType;
            }
            return createArrayType(parameterizedTypeBindingCreateParameterizedType, i3, AnnotatableTypeSystem.flattenedAnnotations(annotationBindingArr));
        }
        iTypeAnnotationWalker2 = iTypeAnnotationWalker;
        iTypeAnnotationWalker2 = iTypeAnnotationWalker3;
        r7 = iTypeAnnotationWalker2;
        annotationBindingArr = null;
        if (signatureWrapper.signature[signatureWrapper.start] == 'T') {
            i2 = signatureWrapper.start + 1;
            iComputeEnd = signatureWrapper.computeEnd();
            length = typeVariableBindingArr.length;
            do {
                length--;
                if (length < 0) {
                    referenceBindingEnclosingType2 = referenceBinding;
                    do {
                        if (referenceBindingEnclosingType2 instanceof BinaryTypeBinding) {
                            typeVariableBindingArrTypeVariables = ((BinaryTypeBinding) referenceBindingEnclosingType2).typeVariables;
                        } else {
                            typeVariableBindingArrTypeVariables = referenceBindingEnclosingType2.typeVariables();
                        }
                        typeVariableBindingArr2 = typeVariableBindingArrTypeVariables;
                        length2 = typeVariableBindingArr2.length;
                        do {
                            length2--;
                            if (length2 < 0) {
                                referenceBindingEnclosingType2 = referenceBindingEnclosingType2.enclosingType();
                            }
                        } while (!CharOperation.equals(typeVariableBindingArr2[length2].sourceName, signatureWrapper.signature, i2, iComputeEnd));
                        return getTypeFromTypeVariable(typeVariableBindingArr2[length2], i3, annotationBindingArr, r7, cArr);
                    } while (referenceBindingEnclosingType2 != null);
                    this.problemReporter.undefinedTypeVariableSignature(CharOperation.subarray(signatureWrapper.signature, i2, iComputeEnd), referenceBinding);
                    return null;
                }
            } while (!CharOperation.equals(typeVariableBindingArr[length].sourceName, signatureWrapper.signature, i2, iComputeEnd));
            return getTypeFromTypeVariable(typeVariableBindingArr[length], i3, annotationBindingArr, r7, cArr);
        }
        char[] cArr3 = signatureWrapper.signature;
        int i5 = signatureWrapper.start;
        int iComputeEnd3 = signatureWrapper.computeEnd();
        if (signatureWrapper.end == signatureWrapper.bracket) {
            r17 = 1;
        } else {
            r17 = r12;
        }
        r24 = r7;
        typeFromSignature = getTypeFromSignature(cArr3, i5, iComputeEnd3, r17, referenceBinding, cArr, r7);
        if (r17 == 0) {
            if (i3 == 0) {
                return typeFromSignature;
            }
            return createArrayType(typeFromSignature, i3, AnnotatableTypeSystem.flattenedAnnotations(annotationBindingArr));
        }
        referenceBinding2 = (ReferenceBinding) typeFromSignature;
        if (referenceBinding2 instanceof UnresolvedReferenceBinding) {
            referenceBinding2 = (ReferenceBinding) BinaryTypeBinding.resolveType(referenceBinding2, this, r12);
        }
        ReferenceBinding referenceBinding4 = referenceBinding2;
        referenceBindingEnclosingType = referenceBinding4.enclosingType();
        if (referenceBindingEnclosingType != null) {
            referenceBindingEnclosingType = (ReferenceBinding) convertToRawType(referenceBindingEnclosingType, r12);
        }
        ?? r110 = r24;
        parameterizedTypeBindingCreateParameterizedType = createParameterizedType(referenceBinding4, getTypeArgumentsFromSignature(signatureWrapper, typeVariableBindingArr, referenceBinding, referenceBinding4, cArr, r110), referenceBindingEnclosingType, BinaryTypeBinding.createAnnotations(r24.getAnnotationsAtCursor(referenceBinding4.f626id), this, cArr));
        r18 = r110;
        while (signatureWrapper.signature[signatureWrapper.start] == '.') {
            signatureWrapper.start++;
            i = signatureWrapper.start;
            char[] cArrNextWord2 = signatureWrapper.nextWord();
            BinaryTypeBinding.resolveType(parameterizedTypeBindingCreateParameterizedType, this, false);
            memberType = parameterizedTypeBindingCreateParameterizedType.genericType().getMemberType(cArrNextWord2);
            if (memberType == null) {
                this.problemReporter.corruptedSignature(parameterizedTypeBindingCreateParameterizedType, signatureWrapper.signature, i);
            }
            nextNestedType = r18.toNextNestedType();
            AnnotationBinding[] annotationBindingArrCreateAnnotations3 = BinaryTypeBinding.createAnnotations(nextNestedType.getAnnotationsAtCursor(memberType.f626id), this, cArr);
            if (signatureWrapper.signature[signatureWrapper.start] == '<') {
                signatureWrapper.start++;
                typeArgumentsFromSignature = getTypeArgumentsFromSignature(signatureWrapper, typeVariableBindingArr, referenceBinding, memberType, cArr, nextNestedType);
            } else {
                typeArgumentsFromSignature = null;
            }
            parameterizedTypeBindingCreateParameterizedType = createParameterizedType(memberType, typeArgumentsFromSignature, parameterizedTypeBindingCreateParameterizedType, annotationBindingArrCreateAnnotations3);
            r18 = nextNestedType;
        }
        signatureWrapper.start++;
        if (i3 == 0) {
            return parameterizedTypeBindingCreateParameterizedType;
        }
        return createArrayType(parameterizedTypeBindingCreateParameterizedType, i3, AnnotatableTypeSystem.flattenedAnnotations(annotationBindingArr));
    }

    private TypeBinding getTypeFromTypeVariable(TypeVariableBinding typeVariableBinding, int i, AnnotationBinding[][] annotationBindingArr, ITypeAnnotationWalker iTypeAnnotationWalker, char[][][] cArr) {
        AnnotationBinding[] annotationBindingArrCreateAnnotations = BinaryTypeBinding.createAnnotations(iTypeAnnotationWalker.getAnnotationsAtCursor(-1), this, cArr);
        if (annotationBindingArrCreateAnnotations != null && annotationBindingArrCreateAnnotations != Binding.NO_ANNOTATIONS) {
            typeVariableBinding = (TypeVariableBinding) createAnnotatedType(typeVariableBinding, new AnnotationBinding[][]{annotationBindingArrCreateAnnotations});
        }
        return i == 0 ? typeVariableBinding : this.typeSystem.getArrayType(typeVariableBinding, i, AnnotatableTypeSystem.flattenedAnnotations(annotationBindingArr));
    }

    TypeBinding getTypeFromVariantTypeSignature(SignatureWrapper signatureWrapper, TypeVariableBinding[] typeVariableBindingArr, ReferenceBinding referenceBinding, ReferenceBinding referenceBinding2, int i, char[][][] cArr, ITypeAnnotationWalker iTypeAnnotationWalker) {
        char c = signatureWrapper.signature[signatureWrapper.start];
        if (c == '*') {
            signatureWrapper.start++;
            return this.typeSystem.getWildcard(referenceBinding2, i, null, null, 0, BinaryTypeBinding.createAnnotations(iTypeAnnotationWalker.getAnnotationsAtCursor(-1), this, cArr));
        }
        if (c == '+') {
            signatureWrapper.start++;
            return this.typeSystem.getWildcard(referenceBinding2, i, getTypeFromTypeSignature(signatureWrapper, typeVariableBindingArr, referenceBinding, cArr, iTypeAnnotationWalker.toWildcardBound()), null, 1, BinaryTypeBinding.createAnnotations(iTypeAnnotationWalker.getAnnotationsAtCursor(-1), this, cArr));
        }
        if (c == '-') {
            signatureWrapper.start++;
            return this.typeSystem.getWildcard(referenceBinding2, i, getTypeFromTypeSignature(signatureWrapper, typeVariableBindingArr, referenceBinding, cArr, iTypeAnnotationWalker.toWildcardBound()), null, 2, BinaryTypeBinding.createAnnotations(iTypeAnnotationWalker.getAnnotationsAtCursor(-1), this, cArr));
        }
        return getTypeFromTypeSignature(signatureWrapper, typeVariableBindingArr, referenceBinding, cArr, iTypeAnnotationWalker);
    }

    boolean isMissingType(char[] cArr) {
        ArrayList arrayList = this.missingTypes;
        int size = arrayList == null ? 0 : arrayList.size();
        do {
            size--;
            if (size < 0) {
                return false;
            }
        } while (!CharOperation.equals(((MissingTypeBinding) this.missingTypes.get(size)).sourceName, cArr));
        return true;
    }

    boolean isPackage(char[][] cArr, char[] cArr2) {
        if (cArr == null || cArr.length == 0) {
            return this.nameEnvironment.isPackage(null, cArr2);
        }
        return this.nameEnvironment.isPackage(cArr, cArr2);
    }

    public MethodVerifier methodVerifier() {
        if (this.verifier == null) {
            this.verifier = newMethodVerifier();
        }
        return this.verifier;
    }

    public MethodVerifier newMethodVerifier() {
        return new MethodVerifier15(this);
    }

    public void releaseClassFiles(ClassFile[] classFileArr) {
        for (ClassFile classFile : classFileArr) {
            this.classFilePool.release(classFile);
        }
    }

    public void reset() {
        this.defaultPackage = new PackageBinding(this);
        this.defaultImports = null;
        this.knownPackages = new HashtableOfPackage();
        this.accessRestrictions = new HashMap(3);
        this.verifier = null;
        this.uniqueParameterizedGenericMethodBindings = new SimpleLookupTable(3);
        this.uniquePolymorphicMethodBindings = new SimpleLookupTable(3);
        this.uniqueGetClassMethodBinding = null;
        this.missingTypes = null;
        this.typesBeingConnected = new HashSet();
        int length = this.units.length;
        while (true) {
            length--;
            if (length >= 0) {
                this.units[length] = null;
            } else {
                this.lastUnitIndex = -1;
                this.lastCompletedUnitIndex = -1;
                this.unitBeingCompleted = null;
                this.classFilePool.reset();
                this.typeSystem.reset();
                return;
            }
        }
    }

    public void setAccessRestriction(ReferenceBinding referenceBinding, AccessRestriction accessRestriction) {
        if (accessRestriction == null) {
            return;
        }
        referenceBinding.modifiers |= 262144;
        this.accessRestrictions.put(referenceBinding, accessRestriction);
    }

    void updateCaches(UnresolvedReferenceBinding unresolvedReferenceBinding, ReferenceBinding referenceBinding) {
        this.typeSystem.updateCaches(unresolvedReferenceBinding, referenceBinding);
    }

    public void addResolutionListener(IQualifiedTypeResolutionListener iQualifiedTypeResolutionListener) {
        int length = this.resolutionListeners.length;
        for (int i = 0; i < length; i++) {
            if (this.resolutionListeners[i].equals(iQualifiedTypeResolutionListener)) {
                return;
            }
        }
        IQualifiedTypeResolutionListener[] iQualifiedTypeResolutionListenerArr = this.resolutionListeners;
        IQualifiedTypeResolutionListener[] iQualifiedTypeResolutionListenerArr2 = new IQualifiedTypeResolutionListener[length + 1];
        this.resolutionListeners = iQualifiedTypeResolutionListenerArr2;
        System.arraycopy(iQualifiedTypeResolutionListenerArr, 0, iQualifiedTypeResolutionListenerArr2, 0, length);
        this.resolutionListeners[length] = iQualifiedTypeResolutionListener;
    }

    public TypeBinding getUnannotatedType(TypeBinding typeBinding) {
        return this.typeSystem.getUnannotatedType(typeBinding);
    }

    public TypeBinding[] getAnnotatedTypes(TypeBinding typeBinding) {
        return this.typeSystem.getAnnotatedTypes(typeBinding);
    }

    public AnnotationBinding[] filterNullTypeAnnotations(AnnotationBinding[] annotationBindingArr) {
        if (annotationBindingArr.length == 0) {
            return annotationBindingArr;
        }
        AnnotationBinding[] annotationBindingArr2 = new AnnotationBinding[annotationBindingArr.length];
        int i = 0;
        for (AnnotationBinding annotationBinding : annotationBindingArr) {
            if (annotationBinding == null) {
                i++;
            } else if (!annotationBinding.type.hasNullBit(96)) {
                annotationBindingArr2[i] = annotationBinding;
                i++;
            }
        }
        if (i == 0) {
            return Binding.NO_ANNOTATIONS;
        }
        if (i == annotationBindingArr.length) {
            return annotationBindingArr;
        }
        AnnotationBinding[] annotationBindingArr3 = new AnnotationBinding[i];
        System.arraycopy(annotationBindingArr2, 0, annotationBindingArr3, 0, i);
        return annotationBindingArr3;
    }

    public boolean containsNullTypeAnnotation(IBinaryAnnotation[] iBinaryAnnotationArr) {
        if (iBinaryAnnotationArr.length == 0) {
            return false;
        }
        for (IBinaryAnnotation iBinaryAnnotation : iBinaryAnnotationArr) {
            char[] typeName = iBinaryAnnotation.getTypeName();
            if (typeName != null && typeName.length >= 3 && typeName[0] == 'L' && getNullAnnotationBit(CharOperation.splitOn('/', typeName, 1, typeName.length - 1)) != 0) {
                return true;
            }
        }
        return false;
    }

    public boolean containsNullTypeAnnotation(AnnotationBinding[] annotationBindingArr) {
        if (annotationBindingArr.length == 0) {
            return false;
        }
        for (AnnotationBinding annotationBinding : annotationBindingArr) {
            if (annotationBinding.type.hasNullBit(96)) {
                return true;
            }
        }
        return false;
    }
}
