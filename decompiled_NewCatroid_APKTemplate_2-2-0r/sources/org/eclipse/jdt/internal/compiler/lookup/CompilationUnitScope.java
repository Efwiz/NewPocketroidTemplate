package org.eclipse.jdt.internal.compiler.lookup;

import java.util.ArrayList;
import java.util.Iterator;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.eclipse.jdt.internal.compiler.ast.ImportReference;
import org.eclipse.jdt.internal.compiler.ast.Invocation;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.env.AccessRestriction;
import org.eclipse.jdt.internal.compiler.problem.ProblemReporter;
import org.eclipse.jdt.internal.compiler.util.CompoundNameVector;
import org.eclipse.jdt.internal.compiler.util.HashtableOfObject;
import org.eclipse.jdt.internal.compiler.util.HashtableOfType;
import org.eclipse.jdt.internal.compiler.util.ObjectVector;
import org.eclipse.jdt.internal.compiler.util.SimpleNameVector;

/* JADX INFO: loaded from: classes14.dex */
public class CompilationUnitScope extends Scope {
    private int captureID;
    boolean connectingHierarchy;
    HashtableOfType constantPoolNameUsage;
    public char[][] currentPackageName;
    public LookupEnvironment environment;
    public PackageBinding fPackage;
    public int importPtr;
    public ImportBinding[] imports;
    private ArrayList<Invocation> inferredInvocations;
    private CompoundNameVector qualifiedReferences;
    public CompilationUnitDeclaration referenceContext;
    private ObjectVector referencedSuperTypes;
    private ObjectVector referencedTypes;
    private SimpleNameVector rootReferences;
    private SimpleNameVector simpleNameReferences;
    private boolean skipCachingImports;
    public boolean suppressImportErrors;
    private ImportBinding[] tempImports;
    public SourceTypeBinding[] topLevelTypes;
    public HashtableOfObject typeOrPackageCache;

    public CompilationUnitScope(CompilationUnitDeclaration compilationUnitDeclaration, LookupEnvironment lookupEnvironment) {
        super(4, null);
        this.captureID = 1;
        this.environment = lookupEnvironment;
        this.referenceContext = compilationUnitDeclaration;
        compilationUnitDeclaration.scope = this;
        this.currentPackageName = compilationUnitDeclaration.currentPackage == null ? CharOperation.NO_CHAR_CHAR : compilationUnitDeclaration.currentPackage.tokens;
        if (compilerOptions().produceReferenceInfo) {
            this.qualifiedReferences = new CompoundNameVector();
            this.simpleNameReferences = new SimpleNameVector();
            this.rootReferences = new SimpleNameVector();
            this.referencedTypes = new ObjectVector();
            this.referencedSuperTypes = new ObjectVector();
            return;
        }
        this.qualifiedReferences = null;
        this.simpleNameReferences = null;
        this.rootReferences = null;
        this.referencedTypes = null;
        this.referencedSuperTypes = null;
    }

    void buildFieldsAndMethods() {
        int length = this.topLevelTypes.length;
        for (int i = 0; i < length; i++) {
            this.topLevelTypes[i].scope.buildFieldsAndMethods();
        }
    }

    void buildTypeBindings(AccessRestriction accessRestriction) {
        boolean z;
        char[] mainTypeName;
        char[][] packageName;
        this.topLevelTypes = new SourceTypeBinding[0];
        if (this.referenceContext.compilationResult.compilationUnit != null && (packageName = this.referenceContext.compilationResult.compilationUnit.getPackageName()) != null && !CharOperation.equals(this.currentPackageName, packageName)) {
            if (this.referenceContext.currentPackage != null || this.referenceContext.types != null || this.referenceContext.imports != null) {
                problemReporter().packageIsNotExpectedPackage(this.referenceContext);
            }
            if (packageName.length == 0) {
                packageName = CharOperation.NO_CHAR_CHAR;
            }
            this.currentPackageName = packageName;
        }
        if (this.currentPackageName == CharOperation.NO_CHAR_CHAR) {
            this.fPackage = this.environment.defaultPackage;
            z = false;
        } else {
            PackageBinding packageBindingCreatePackage = this.environment.createPackage(this.currentPackageName);
            this.fPackage = packageBindingCreatePackage;
            if (packageBindingCreatePackage == null) {
                if (this.referenceContext.currentPackage != null) {
                    problemReporter().packageCollidesWithType(this.referenceContext);
                }
                this.fPackage = this.environment.defaultPackage;
                return;
            }
            if (this.referenceContext.isPackageInfo()) {
                if (this.referenceContext.types == null || this.referenceContext.types.length == 0) {
                    this.referenceContext.types = new TypeDeclaration[1];
                    this.referenceContext.createPackageInfoType();
                    z = true;
                } else {
                    z = false;
                }
                if (this.referenceContext.currentPackage != null && this.referenceContext.currentPackage.annotations != null) {
                    this.referenceContext.types[0].annotations = this.referenceContext.currentPackage.annotations;
                }
            } else {
                z = false;
            }
            recordQualifiedReference(this.currentPackageName);
        }
        TypeDeclaration[] typeDeclarationArr = this.referenceContext.types;
        int length = typeDeclarationArr == null ? 0 : typeDeclarationArr.length;
        this.topLevelTypes = new SourceTypeBinding[length];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            TypeDeclaration typeDeclaration = typeDeclarationArr[i2];
            if (this.environment.isProcessingAnnotations && this.environment.isMissingType(typeDeclaration.f619name)) {
                throw new SourceTypeCollisionException();
            }
            ReferenceBinding type0 = this.fPackage.getType0(typeDeclaration.f619name);
            recordSimpleReference(typeDeclaration.f619name);
            if (type0 != null && type0.isValidBinding() && !(type0 instanceof UnresolvedReferenceBinding)) {
                if (this.environment.isProcessingAnnotations) {
                    throw new SourceTypeCollisionException();
                }
                problemReporter().duplicateTypes(this.referenceContext, typeDeclaration);
            } else {
                if (this.fPackage != this.environment.defaultPackage && this.fPackage.getPackage(typeDeclaration.f619name) != null) {
                    problemReporter().typeCollidesWithPackage(this.referenceContext, typeDeclaration);
                }
                if ((typeDeclaration.modifiers & 1) != 0 && (mainTypeName = this.referenceContext.getMainTypeName()) != null && !CharOperation.equals(mainTypeName, typeDeclaration.f619name)) {
                    problemReporter().publicClassMustMatchFileName(this.referenceContext, typeDeclaration);
                }
                SourceTypeBinding sourceTypeBindingBuildType = new ClassScope(this, typeDeclaration).buildType(null, this.fPackage, accessRestriction);
                if (z && i2 == 0) {
                    sourceTypeBindingBuildType.modifiers |= 4096;
                }
                if (sourceTypeBindingBuildType != null) {
                    this.topLevelTypes[i] = sourceTypeBindingBuildType;
                    i++;
                }
            }
        }
        SourceTypeBinding[] sourceTypeBindingArr = this.topLevelTypes;
        if (i != sourceTypeBindingArr.length) {
            SourceTypeBinding[] sourceTypeBindingArr2 = new SourceTypeBinding[i];
            this.topLevelTypes = sourceTypeBindingArr2;
            System.arraycopy(sourceTypeBindingArr, 0, sourceTypeBindingArr2, 0, i);
        }
    }

    void checkAndSetImports() {
        int i;
        if (this.referenceContext.imports == null) {
            this.imports = getDefaultImports();
            return;
        }
        int length = this.referenceContext.imports.length;
        int i2 = length + 1;
        int i3 = 0;
        while (true) {
            if (i3 >= length) {
                i = i2;
                break;
            }
            ImportReference importReference = this.referenceContext.imports[i3];
            if ((importReference.bits & 131072) != 0 && CharOperation.equals(TypeConstants.JAVA_LANG, importReference.tokens) && !importReference.isStatic()) {
                i = length;
                break;
            }
            i3++;
        }
        ImportBinding[] importBindingArr = new ImportBinding[i];
        importBindingArr[0] = getDefaultImports()[0];
        int i4 = 1;
        for (int i5 = 0; i5 < length; i5++) {
            ImportReference importReference2 = this.referenceContext.imports[i5];
            char[][] cArr = importReference2.tokens;
            int i6 = 0;
            while (true) {
                if (i6 < i4) {
                    ImportBinding importBinding = importBindingArr[i6];
                    if (importBinding.onDemand == ((importReference2.bits & 131072) != 0) && importBinding.isStatic() == importReference2.isStatic() && CharOperation.equals(cArr, importBindingArr[i6].compoundName)) {
                        break;
                    } else {
                        i6++;
                    }
                } else {
                    if ((importReference2.bits & 131072) != 0) {
                        if (!CharOperation.equals(cArr, this.currentPackageName)) {
                            Binding bindingFindImport = findImport(cArr, cArr.length);
                            if (bindingFindImport.isValidBinding() && (!importReference2.isStatic() || !(bindingFindImport instanceof PackageBinding))) {
                                importBindingArr[i4] = new ImportBinding(cArr, true, bindingFindImport, importReference2);
                                i4++;
                                break;
                            }
                            break;
                        }
                        break;
                    }
                    importBindingArr[i4] = new ImportBinding(cArr, false, null, importReference2);
                    i4++;
                    break;
                }
            }
        }
        if (i > i4) {
            ImportBinding[] importBindingArr2 = new ImportBinding[i4];
            System.arraycopy(importBindingArr, 0, importBindingArr2, 0, i4);
            importBindingArr = importBindingArr2;
        }
        this.imports = importBindingArr;
    }

    void checkParameterizedTypes() {
        if (compilerOptions().sourceLevel < ClassFileConstants.JDK1_5) {
            return;
        }
        int length = this.topLevelTypes.length;
        for (int i = 0; i < length; i++) {
            ClassScope classScope = this.topLevelTypes[i].scope;
            classScope.checkParameterizedTypeBounds();
            classScope.checkParameterizedSuperTypeCollisions();
        }
    }

    public char[] computeConstantPoolName(LocalTypeBinding localTypeBinding) {
        char[] cArrConcat;
        if (localTypeBinding.constantPoolName != null) {
            return localTypeBinding.constantPoolName;
        }
        if (this.constantPoolNameUsage == null) {
            this.constantPoolNameUsage = new HashtableOfType();
        }
        SourceTypeBinding sourceTypeBindingEnclosingSourceType = localTypeBinding.scope.outerMostClassScope().enclosingSourceType();
        int i = 0;
        boolean z = compilerOptions().complianceLevel >= ClassFileConstants.JDK1_5;
        while (true) {
            if (localTypeBinding.isMemberType()) {
                if (i == 0) {
                    cArrConcat = CharOperation.concat(localTypeBinding.enclosingType().constantPoolName(), localTypeBinding.sourceName, '$');
                } else {
                    cArrConcat = CharOperation.concat(localTypeBinding.enclosingType().constantPoolName(), '$', String.valueOf(i).toCharArray(), '$', localTypeBinding.sourceName);
                }
            } else if (localTypeBinding.isAnonymousType()) {
                if (z) {
                    cArrConcat = CharOperation.concat(localTypeBinding.enclosingType.constantPoolName(), String.valueOf(i + 1).toCharArray(), '$');
                } else {
                    cArrConcat = CharOperation.concat(sourceTypeBindingEnclosingSourceType.constantPoolName(), String.valueOf(i + 1).toCharArray(), '$');
                }
            } else if (z) {
                cArrConcat = CharOperation.concat(CharOperation.concat(localTypeBinding.enclosingType().constantPoolName(), String.valueOf(i + 1).toCharArray(), '$'), localTypeBinding.sourceName);
            } else {
                cArrConcat = CharOperation.concat(sourceTypeBindingEnclosingSourceType.constantPoolName(), '$', String.valueOf(i + 1).toCharArray(), '$', localTypeBinding.sourceName);
            }
            if (this.constantPoolNameUsage.get(cArrConcat) == null) {
                this.constantPoolNameUsage.put(cArrConcat, localTypeBinding);
                return cArrConcat;
            }
            i++;
        }
    }

    void connectTypeHierarchy() {
        int length = this.topLevelTypes.length;
        for (int i = 0; i < length; i++) {
            this.topLevelTypes[i].scope.connectTypeHierarchy();
        }
    }

    void faultInImports() {
        HashtableOfType hashtableOfType;
        boolean z = this.suppressImportErrors;
        if (this.typeOrPackageCache == null || this.skipCachingImports) {
            if (this.referenceContext.imports == null) {
                this.typeOrPackageCache = new HashtableOfObject(1);
                return;
            }
            int length = this.referenceContext.imports.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    hashtableOfType = null;
                    break;
                }
                if ((this.referenceContext.imports[i].bits & 131072) == 0) {
                    HashtableOfType hashtableOfType2 = new HashtableOfType(this.topLevelTypes.length + length);
                    int length2 = this.topLevelTypes.length;
                    for (int i2 = 0; i2 < length2; i2++) {
                        hashtableOfType2.put(this.topLevelTypes[i2].sourceName, this.topLevelTypes[i2]);
                    }
                    hashtableOfType = hashtableOfType2;
                    break;
                }
                i++;
            }
            int i3 = length + 1;
            for (int i4 = 0; i4 < length; i4++) {
                ImportReference importReference = this.referenceContext.imports[i4];
                if ((importReference.bits & 131072) != 0 && CharOperation.equals(TypeConstants.JAVA_LANG, importReference.tokens) && !importReference.isStatic()) {
                    i3 = length;
                    break;
                }
            }
            ImportBinding[] importBindingArr = new ImportBinding[i3];
            this.tempImports = importBindingArr;
            importBindingArr[0] = getDefaultImports()[0];
            this.importPtr = 1;
            boolean z2 = false;
            for (int i5 = 0; i5 < length; i5++) {
                ImportReference importReference2 = this.referenceContext.imports[i5];
                char[][] cArr = importReference2.tokens;
                int i6 = 0;
                while (true) {
                    if (i6 < this.importPtr) {
                        ImportBinding importBinding = this.tempImports[i6];
                        if (importBinding.onDemand == ((importReference2.bits & 131072) != 0) && importBinding.isStatic() == importReference2.isStatic() && CharOperation.equals(cArr, importBinding.compoundName)) {
                            problemReporter().unusedImport(importReference2);
                            break;
                        }
                        i6++;
                    } else {
                        if ((importReference2.bits & 131072) != 0) {
                            if (CharOperation.equals(cArr, this.currentPackageName)) {
                                problemReporter().unusedImport(importReference2);
                                break;
                            }
                            Binding bindingFindImport = findImport(cArr, cArr.length);
                            if (!bindingFindImport.isValidBinding()) {
                                problemReporter().importProblem(importReference2, bindingFindImport);
                                break;
                            } else if (importReference2.isStatic() && (bindingFindImport instanceof PackageBinding)) {
                                problemReporter().cannotImportPackage(importReference2);
                                break;
                            } else {
                                recordImportBinding(new ImportBinding(cArr, true, bindingFindImport, importReference2));
                                break;
                            }
                        }
                        Binding bindingFindSingleImport = findSingleImport(cArr, 13, importReference2.isStatic());
                        if (!bindingFindSingleImport.isValidBinding() && bindingFindSingleImport.problemId() != 3) {
                            if (!z) {
                                problemReporter().importProblem(importReference2, bindingFindSingleImport);
                            }
                            z2 = true;
                            break;
                        } else {
                            if (bindingFindSingleImport instanceof PackageBinding) {
                                problemReporter().cannotImportPackage(importReference2);
                                break;
                            }
                            if (checkAndRecordImportBinding(bindingFindSingleImport, hashtableOfType, importReference2, cArr) == -1 || !importReference2.isStatic()) {
                                break;
                            }
                            if (bindingFindSingleImport.kind() == 1) {
                                checkMoreStaticBindings(cArr, hashtableOfType, 12, importReference2);
                                break;
                            } else {
                                if (bindingFindSingleImport.kind() != 8) {
                                    break;
                                }
                                checkMoreStaticBindings(cArr, hashtableOfType, 4, importReference2);
                                break;
                            }
                        }
                    }
                }
            }
            ImportBinding[] importBindingArr2 = this.tempImports;
            int length3 = importBindingArr2.length;
            int i7 = this.importPtr;
            if (length3 > i7) {
                ImportBinding[] importBindingArr3 = new ImportBinding[i7];
                this.tempImports = importBindingArr3;
                System.arraycopy(importBindingArr2, 0, importBindingArr3, 0, i7);
            }
            ImportBinding[] importBindingArr4 = this.tempImports;
            this.imports = importBindingArr4;
            int length4 = importBindingArr4.length;
            this.typeOrPackageCache = new HashtableOfObject(length4);
            for (int i8 = 0; i8 < length4; i8++) {
                ImportBinding importBinding2 = this.imports[i8];
                if ((!importBinding2.onDemand && (importBinding2.resolvedImport instanceof ReferenceBinding)) || (importBinding2 instanceof ImportConflictBinding)) {
                    this.typeOrPackageCache.put(importBinding2.compoundName[importBinding2.compoundName.length - 1], importBinding2);
                }
            }
            this.skipCachingImports = this.suppressImportErrors && z2;
        }
    }

    public void faultInTypes() throws Throwable {
        faultInImports();
        int length = this.topLevelTypes.length;
        for (int i = 0; i < length; i++) {
            this.topLevelTypes[i].faultInTypesForFieldsAndMethods();
        }
    }

    public Binding findImport(char[][] cArr, boolean z, boolean z2) {
        if (z2) {
            return findImport(cArr, cArr.length);
        }
        return findSingleImport(cArr, 13, z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v13, types: [org.eclipse.jdt.internal.compiler.lookup.Binding, org.eclipse.jdt.internal.compiler.lookup.PackageBinding] */
    /* JADX WARN: Type inference failed for: r0v17 */
    /* JADX WARN: Type inference failed for: r0v18 */
    private Binding findImport(char[][] cArr, int i) {
        int i2;
        ReferenceBinding memberType;
        ?? r0;
        recordQualifiedReference(cArr);
        Binding topLevelPackage = this.environment.getTopLevelPackage(cArr[0]);
        if (topLevelPackage != null) {
            int i3 = 1;
            while (true) {
                if (i3 >= i) {
                    r0 = topLevelPackage;
                    return r0;
                }
                i2 = i3 + 1;
                topLevelPackage = r0.getTypeOrPackage(cArr[i3]);
                if (topLevelPackage == null || !topLevelPackage.isValidBinding()) {
                    r0 = topLevelPackage;
                    topLevelPackage = null;
                    break;
                }
                if (!(topLevelPackage instanceof PackageBinding)) {
                    break;
                }
                r0 = topLevelPackage;
                i3 = i2;
                r0 = (PackageBinding) topLevelPackage;
            }
        } else {
            i2 = 1;
        }
        if (topLevelPackage != null) {
            r0 = topLevelPackage;
            memberType = (ReferenceBinding) topLevelPackage;
        } else {
            if (compilerOptions().complianceLevel >= ClassFileConstants.JDK1_4) {
                r0 = topLevelPackage;
                return new ProblemReferenceBinding(CharOperation.subarray(cArr, 0, i2), null, 1);
            }
            memberType = findType(cArr[0], this.environment.defaultPackage, this.environment.defaultPackage);
            if (memberType == null || !memberType.isValidBinding()) {
                r0 = topLevelPackage;
                r0 = topLevelPackage;
                return new ProblemReferenceBinding(CharOperation.subarray(cArr, 0, i2), null, 1);
            }
            r0 = topLevelPackage;
            i2 = 1;
        }
        while (i2 < i) {
            ReferenceBinding referenceBinding = (ReferenceBinding) this.environment.convertToRawType(memberType, false);
            if (!referenceBinding.canBeSeenBy(this.fPackage)) {
                return new ProblemReferenceBinding(CharOperation.subarray(cArr, 0, i2), referenceBinding, 2);
            }
            int i4 = i2 + 1;
            memberType = referenceBinding.getMemberType(cArr[i2]);
            if (memberType == null) {
                return new ProblemReferenceBinding(CharOperation.subarray(cArr, 0, i4), null, 1);
            }
            i2 = i4;
        }
        return !memberType.canBeSeenBy(this.fPackage) ? new ProblemReferenceBinding(cArr, memberType, 2) : memberType;
    }

    private Binding findSingleImport(char[][] cArr, int i, boolean z) {
        if (cArr.length != 1) {
            if (z) {
                return findSingleStaticImport(cArr, i);
            }
            return findImport(cArr, cArr.length);
        }
        if (compilerOptions().complianceLevel >= ClassFileConstants.JDK1_4) {
            return new ProblemReferenceBinding(cArr, null, 1);
        }
        ReferenceBinding referenceBindingFindType = findType(cArr[0], this.environment.defaultPackage, this.fPackage);
        return referenceBindingFindType == null ? new ProblemReferenceBinding(cArr, null, 1) : referenceBindingFindType;
    }

    private Binding findSingleStaticImport(char[][] cArr, int i) {
        Binding bindingFindImport = findImport(cArr, cArr.length - 1);
        if (!bindingFindImport.isValidBinding()) {
            return bindingFindImport;
        }
        char[] cArr2 = cArr[cArr.length - 1];
        if (bindingFindImport instanceof PackageBinding) {
            Binding typeOrPackage = ((PackageBinding) bindingFindImport).getTypeOrPackage(cArr2);
            return (typeOrPackage == null || !(typeOrPackage instanceof ReferenceBinding)) ? bindingFindImport : new ProblemReferenceBinding(cArr, (ReferenceBinding) typeOrPackage, 14);
        }
        ReferenceBinding referenceBinding = (ReferenceBinding) bindingFindImport;
        FieldBinding fieldBindingFindField = (i & 1) != 0 ? findField(referenceBinding, cArr2, null, true) : null;
        if (fieldBindingFindField != null) {
            if (fieldBindingFindField.problemId() == 3 && ((ProblemFieldBinding) fieldBindingFindField).closestMatch.isStatic()) {
                return fieldBindingFindField;
            }
            if (fieldBindingFindField.isValidBinding() && fieldBindingFindField.isStatic() && fieldBindingFindField.canBeSeenBy(referenceBinding, null, this)) {
                return fieldBindingFindField;
            }
        }
        MethodBinding methodBindingFindStaticMethod = (i & 8) != 0 ? findStaticMethod(referenceBinding, cArr2) : null;
        if (methodBindingFindStaticMethod != null) {
            return methodBindingFindStaticMethod;
        }
        ReferenceBinding referenceBindingFindMemberType = findMemberType(cArr2, referenceBinding);
        if (referenceBindingFindMemberType == null || !referenceBindingFindMemberType.isStatic()) {
            return (fieldBindingFindField == null || fieldBindingFindField.isValidBinding() || fieldBindingFindField.problemId() == 1) ? new ProblemReferenceBinding(cArr, referenceBindingFindMemberType, 1) : fieldBindingFindField;
        }
        if (!referenceBindingFindMemberType.isValidBinding() || referenceBindingFindMemberType.canBeSeenBy(this.fPackage)) {
            return referenceBindingFindMemberType.problemId() == 2 ? new ProblemReferenceBinding(cArr, ((ProblemReferenceBinding) referenceBindingFindMemberType).closestMatch, 2) : referenceBindingFindMemberType;
        }
        return new ProblemReferenceBinding(cArr, referenceBindingFindMemberType, 2);
    }

    private MethodBinding findStaticMethod(ReferenceBinding referenceBinding, char[] cArr) {
        if (!referenceBinding.canBeSeenBy(this)) {
            return null;
        }
        do {
            referenceBinding.initializeForStaticImports();
            MethodBinding[] methods = referenceBinding.getMethods(cArr);
            if (methods != Binding.NO_METHODS) {
                int length = methods.length;
                while (true) {
                    length--;
                    if (length < 0) {
                        break;
                    }
                    MethodBinding methodBinding = methods[length];
                    if (methodBinding.isStatic() && methodBinding.canBeSeenBy(this.fPackage)) {
                        return methodBinding;
                    }
                }
            }
            referenceBinding = referenceBinding.superclass();
        } while (referenceBinding != null);
        return null;
    }

    ImportBinding[] getDefaultImports() {
        PackageBinding typeOrPackage;
        PackageBinding packageBinding;
        if (this.environment.defaultImports != null) {
            return this.environment.defaultImports;
        }
        PackageBinding topLevelPackage = this.environment.getTopLevelPackage(TypeConstants.JAVA);
        if (topLevelPackage != null) {
            typeOrPackage = topLevelPackage;
            typeOrPackage = topLevelPackage.getTypeOrPackage(TypeConstants.JAVA_LANG[1]);
        }
        typeOrPackage = topLevelPackage;
        if (typeOrPackage == null || !typeOrPackage.isValidBinding()) {
            packageBinding = typeOrPackage;
            problemReporter().isClassPathCorrect(TypeConstants.JAVA_LANG_OBJECT, this.referenceContext, this.environment.missingClassFileLocation);
            packageBinding = this.environment.createMissingType(null, TypeConstants.JAVA_LANG_OBJECT).fPackage;
        }
        packageBinding = typeOrPackage;
        LookupEnvironment lookupEnvironment = this.environment;
        ImportBinding[] importBindingArr = {new ImportBinding(TypeConstants.JAVA_LANG, true, packageBinding, null)};
        lookupEnvironment.defaultImports = importBindingArr;
        return importBindingArr;
    }

    public final Binding getImport(char[][] cArr, boolean z, boolean z2) {
        if (z) {
            return findImport(cArr, cArr.length);
        }
        return findSingleImport(cArr, 13, z2);
    }

    public int nextCaptureID() {
        int i = this.captureID;
        this.captureID = i + 1;
        return i;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Scope
    public ProblemReporter problemReporter() {
        ProblemReporter problemReporter = this.referenceContext.problemReporter;
        problemReporter.referenceContext = this.referenceContext;
        return problemReporter;
    }

    void recordQualifiedReference(char[][] cArr) {
        if (this.qualifiedReferences == null) {
            return;
        }
        int length = cArr.length;
        if (length <= 1) {
            if (length == 1) {
                recordRootReference(cArr[0]);
                recordSimpleReference(cArr[0]);
                return;
            }
            return;
        }
        recordRootReference(cArr[0]);
        while (!this.qualifiedReferences.contains(cArr)) {
            this.qualifiedReferences.add(cArr);
            if (length == 2) {
                recordSimpleReference(cArr[0]);
                recordSimpleReference(cArr[1]);
                return;
            } else {
                length--;
                recordSimpleReference(cArr[length]);
                char[][] cArr2 = new char[length][];
                System.arraycopy(cArr, 0, cArr2, 0, length);
                cArr = cArr2;
            }
        }
    }

    void recordReference(char[][] cArr, char[] cArr2) {
        recordQualifiedReference(cArr);
        if (cArr.length == 0) {
            recordRootReference(cArr2);
        }
        recordSimpleReference(cArr2);
    }

    void recordReference(ReferenceBinding referenceBinding, char[] cArr) {
        ReferenceBinding referenceBindingTypeToRecord = typeToRecord(referenceBinding);
        if (referenceBindingTypeToRecord != null) {
            recordReference(referenceBindingTypeToRecord.compoundName, cArr);
        }
    }

    void recordRootReference(char[] cArr) {
        SimpleNameVector simpleNameVector = this.rootReferences;
        if (simpleNameVector == null || simpleNameVector.contains(cArr)) {
            return;
        }
        this.rootReferences.add(cArr);
    }

    void recordSimpleReference(char[] cArr) {
        SimpleNameVector simpleNameVector = this.simpleNameReferences;
        if (simpleNameVector == null || simpleNameVector.contains(cArr)) {
            return;
        }
        this.simpleNameReferences.add(cArr);
    }

    void recordSuperTypeReference(TypeBinding typeBinding) {
        ReferenceBinding referenceBindingTypeToRecord;
        if (this.referencedSuperTypes == null || (referenceBindingTypeToRecord = typeToRecord(typeBinding)) == null || this.referencedSuperTypes.containsIdentical(referenceBindingTypeToRecord)) {
            return;
        }
        this.referencedSuperTypes.add(referenceBindingTypeToRecord);
    }

    public void recordTypeConversion(TypeBinding typeBinding, TypeBinding typeBinding2) {
        recordSuperTypeReference(typeBinding2);
    }

    void recordTypeReference(TypeBinding typeBinding) {
        ReferenceBinding referenceBindingTypeToRecord;
        if (this.referencedTypes == null || (referenceBindingTypeToRecord = typeToRecord(typeBinding)) == null || this.referencedTypes.containsIdentical(referenceBindingTypeToRecord)) {
            return;
        }
        this.referencedTypes.add(referenceBindingTypeToRecord);
    }

    void recordTypeReferences(TypeBinding[] typeBindingArr) {
        if (this.referencedTypes == null || typeBindingArr == null || typeBindingArr.length == 0) {
            return;
        }
        for (TypeBinding typeBinding : typeBindingArr) {
            ReferenceBinding referenceBindingTypeToRecord = typeToRecord(typeBinding);
            if (referenceBindingTypeToRecord != null && !this.referencedTypes.containsIdentical(referenceBindingTypeToRecord)) {
                this.referencedTypes.add(referenceBindingTypeToRecord);
            }
        }
    }

    Binding resolveSingleImport(ImportBinding importBinding, int i) {
        if (importBinding.resolvedImport == null) {
            importBinding.resolvedImport = findSingleImport(importBinding.compoundName, i, importBinding.isStatic());
            if (!importBinding.resolvedImport.isValidBinding() || (importBinding.resolvedImport instanceof PackageBinding)) {
                if (importBinding.resolvedImport.problemId() == 3) {
                    return importBinding.resolvedImport;
                }
                ImportBinding[] importBindingArr = this.imports;
                if (importBindingArr == null) {
                    return null;
                }
                ImportBinding[] importBindingArr2 = new ImportBinding[importBindingArr.length - 1];
                int length = importBindingArr.length;
                int i2 = 0;
                for (int i3 = 0; i3 < length; i3++) {
                    ImportBinding importBinding2 = this.imports[i3];
                    if (importBinding2 != importBinding) {
                        importBindingArr2[i2] = importBinding2;
                        i2++;
                    }
                }
                this.imports = importBindingArr2;
                return null;
            }
        }
        return importBinding.resolvedImport;
    }

    public void storeDependencyInfo() {
        char[][] cArrSplitOn;
        ReferenceBinding referenceBindingEnclosingType;
        for (int i = 0; i < this.referencedSuperTypes.size; i++) {
            ReferenceBinding referenceBinding = (ReferenceBinding) this.referencedSuperTypes.elementAt(i);
            if (!this.referencedTypes.containsIdentical(referenceBinding)) {
                this.referencedTypes.add(referenceBinding);
            }
            if (!referenceBinding.isLocalType() && (referenceBindingEnclosingType = referenceBinding.enclosingType()) != null) {
                recordSuperTypeReference(referenceBindingEnclosingType);
            }
            ReferenceBinding referenceBindingSuperclass = referenceBinding.superclass();
            if (referenceBindingSuperclass != null) {
                recordSuperTypeReference(referenceBindingSuperclass);
            }
            ReferenceBinding[] referenceBindingArrSuperInterfaces = referenceBinding.superInterfaces();
            if (referenceBindingArrSuperInterfaces != null) {
                for (ReferenceBinding referenceBinding2 : referenceBindingArrSuperInterfaces) {
                    recordSuperTypeReference(referenceBinding2);
                }
            }
        }
        int i2 = this.referencedTypes.size;
        for (int i3 = 0; i3 < i2; i3++) {
            ReferenceBinding referenceBinding3 = (ReferenceBinding) this.referencedTypes.elementAt(i3);
            if (!referenceBinding3.isLocalType()) {
                if (referenceBinding3.isMemberType()) {
                    cArrSplitOn = CharOperation.splitOn('.', referenceBinding3.readableName());
                } else {
                    cArrSplitOn = referenceBinding3.compoundName;
                }
                recordQualifiedReference(cArrSplitOn);
            }
        }
        int i4 = this.qualifiedReferences.size;
        char[][][] cArr = new char[i4][][];
        for (int i5 = 0; i5 < i4; i5++) {
            cArr[i5] = this.qualifiedReferences.elementAt(i5);
        }
        this.referenceContext.compilationResult.qualifiedReferences = cArr;
        int i6 = this.simpleNameReferences.size;
        char[][] cArr2 = new char[i6][];
        for (int i7 = 0; i7 < i6; i7++) {
            cArr2[i7] = this.simpleNameReferences.elementAt(i7);
        }
        this.referenceContext.compilationResult.simpleNameReferences = cArr2;
        int i8 = this.rootReferences.size;
        char[][] cArr3 = new char[i8][];
        for (int i9 = 0; i9 < i8; i9++) {
            cArr3[i9] = this.rootReferences.elementAt(i9);
        }
        this.referenceContext.compilationResult.rootReferences = cArr3;
    }

    public String toString() {
        return "--- CompilationUnit Scope : ".concat(new String(this.referenceContext.getFileName()));
    }

    /* JADX WARN: Code duplicated, block: B:24:0x0031  */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x002e, code lost:
    
        if (r1 != 65540) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding typeToRecord(org.eclipse.jdt.internal.compiler.lookup.TypeBinding r4) {
        /*
            r3 = this;
            r0 = 0
            if (r4 != 0) goto L4
            return r0
        L4:
            boolean r1 = r4.isArrayType()
            if (r1 != 0) goto L40
            int r1 = r4.kind()
            r2 = 132(0x84, float:1.85E-43)
            if (r1 == r2) goto L3f
            r2 = 260(0x104, float:3.64E-43)
            if (r1 == r2) goto L31
            r2 = 516(0x204, float:7.23E-43)
            if (r1 == r2) goto L3f
            r2 = 1028(0x404, float:1.44E-42)
            if (r1 == r2) goto L31
            r2 = 4100(0x1004, float:5.745E-42)
            if (r1 == r2) goto L3f
            r2 = 8196(0x2004, float:1.1485E-41)
            if (r1 == r2) goto L3f
            r2 = 32772(0x8004, float:4.5923E-41)
            if (r1 == r2) goto L3f
            r2 = 65540(0x10004, float:9.1841E-41)
            if (r1 == r2) goto L3f
            goto L35
        L31:
            org.eclipse.jdt.internal.compiler.lookup.TypeBinding r4 = r4.erasure()
        L35:
            org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding r4 = (org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding) r4
            boolean r1 = r4.isLocalType()
            if (r1 == 0) goto L3e
            return r0
        L3e:
            return r4
        L3f:
            return r0
        L40:
            org.eclipse.jdt.internal.compiler.lookup.ArrayBinding r4 = (org.eclipse.jdt.internal.compiler.lookup.ArrayBinding) r4
            org.eclipse.jdt.internal.compiler.lookup.TypeBinding r4 = r4.leafComponentType()
            goto L4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jdt.internal.compiler.lookup.CompilationUnitScope.typeToRecord(org.eclipse.jdt.internal.compiler.lookup.TypeBinding):org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding");
    }

    public void verifyMethods(MethodVerifier methodVerifier) {
        int length = this.topLevelTypes.length;
        for (int i = 0; i < length; i++) {
            this.topLevelTypes[i].verifyMethods(methodVerifier);
        }
    }

    private void recordImportBinding(ImportBinding importBinding) {
        ImportBinding[] importBindingArr = this.tempImports;
        int length = importBindingArr.length;
        int i = this.importPtr;
        if (length == i) {
            ImportBinding[] importBindingArr2 = new ImportBinding[i + 1];
            this.tempImports = importBindingArr2;
            System.arraycopy(importBindingArr, 0, importBindingArr2, 0, i);
        }
        ImportBinding[] importBindingArr3 = this.tempImports;
        int i2 = this.importPtr;
        this.importPtr = i2 + 1;
        importBindingArr3[i2] = importBinding;
    }

    private void checkMoreStaticBindings(char[][] cArr, HashtableOfType hashtableOfType, int i, ImportReference importReference) {
        Binding bindingFindSingleStaticImport = findSingleStaticImport(cArr, i);
        if (bindingFindSingleStaticImport.isValidBinding() || bindingFindSingleStaticImport.problemId() == 3) {
            checkAndRecordImportBinding(bindingFindSingleStaticImport, hashtableOfType, importReference, cArr);
        }
        if ((i & 8) == 0 || bindingFindSingleStaticImport.kind() != 8) {
            return;
        }
        checkMoreStaticBindings(cArr, hashtableOfType, i & (-9), importReference);
    }

    private int checkAndRecordImportBinding(Binding binding, HashtableOfType hashtableOfType, ImportReference importReference, char[][] cArr) {
        ReferenceBinding referenceBinding = null;
        if (binding instanceof MethodBinding) {
            ReferenceBinding referenceBinding2 = (ReferenceBinding) getType(cArr, cArr.length);
            if (referenceBinding2.isValidBinding() && (!importReference.isStatic() || referenceBinding2.isStatic())) {
                referenceBinding = referenceBinding2;
            }
        }
        char[] cArr2 = cArr[cArr.length - 1];
        boolean z = binding instanceof ReferenceBinding;
        if (z || referenceBinding != null) {
            ReferenceBinding referenceBinding3 = referenceBinding == null ? (ReferenceBinding) binding : referenceBinding;
            ReferenceBinding referenceBinding4 = referenceBinding3.problemId() == 3 ? ((ProblemReferenceBinding) referenceBinding3).closestMatch : referenceBinding3;
            if (importReference.isTypeUseDeprecated(referenceBinding4, this)) {
                problemReporter().deprecatedType(referenceBinding4, importReference);
            }
            ReferenceBinding referenceBinding5 = hashtableOfType.get(cArr2);
            if (referenceBinding5 != null) {
                if (TypeBinding.equalsEquals(referenceBinding5, referenceBinding3)) {
                    for (int i = 0; i < this.importPtr; i++) {
                        ImportBinding importBinding = this.tempImports[i];
                        if (importBinding instanceof ImportConflictBinding) {
                            if (TypeBinding.equalsEquals(((ImportConflictBinding) importBinding).conflictingTypeBinding, referenceBinding3) && !importReference.isStatic()) {
                                problemReporter().duplicateImport(importReference);
                                recordImportBinding(new ImportBinding(cArr, false, binding, importReference));
                            }
                        } else if (importBinding.resolvedImport == referenceBinding3 && importReference.isStatic() != importBinding.isStatic()) {
                            problemReporter().duplicateImport(importReference);
                            recordImportBinding(new ImportBinding(cArr, false, binding, importReference));
                        }
                    }
                    return -1;
                }
                int length = this.topLevelTypes.length;
                for (int i2 = 0; i2 < length; i2++) {
                    if (CharOperation.equals(this.topLevelTypes[i2].sourceName, referenceBinding5.sourceName)) {
                        problemReporter().conflictingImport(importReference);
                        return -1;
                    }
                }
                if (importReference.isStatic() && z && compilerOptions().sourceLevel >= ClassFileConstants.JDK1_8) {
                    for (int i3 = 0; i3 < this.importPtr; i3++) {
                        ImportBinding importBinding2 = this.tempImports[i3];
                        if (importBinding2.isStatic() && (importBinding2.resolvedImport instanceof ReferenceBinding) && binding != importBinding2.resolvedImport && CharOperation.equals(cArr2, importBinding2.compoundName[importBinding2.compoundName.length - 1])) {
                            importBinding2.resolvedImport = new ProblemReferenceBinding(new char[][]{cArr2}, (ReferenceBinding) importBinding2.resolvedImport, 3);
                            return -1;
                        }
                    }
                }
                problemReporter().duplicateImport(importReference);
                return -1;
            }
            hashtableOfType.put(cArr2, referenceBinding3);
        } else if (binding instanceof FieldBinding) {
            for (int i4 = 0; i4 < this.importPtr; i4++) {
                ImportBinding importBinding3 = this.tempImports[i4];
                if (importBinding3.isStatic() && (importBinding3.resolvedImport instanceof FieldBinding) && binding != importBinding3.resolvedImport && CharOperation.equals(cArr2, importBinding3.compoundName[importBinding3.compoundName.length - 1])) {
                    if (compilerOptions().sourceLevel >= ClassFileConstants.JDK1_8) {
                        FieldBinding fieldBinding = (FieldBinding) importBinding3.resolvedImport;
                        importBinding3.resolvedImport = new ProblemFieldBinding(fieldBinding, fieldBinding.declaringClass, cArr2, 3);
                        return -1;
                    }
                    problemReporter().duplicateImport(importReference);
                    return -1;
                }
            }
        }
        if (referenceBinding == null) {
            recordImportBinding(new ImportBinding(cArr, false, binding, importReference));
        } else {
            recordImportBinding(new ImportConflictBinding(cArr, binding, referenceBinding, importReference));
        }
        return this.importPtr;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Scope
    public boolean hasDefaultNullnessFor(int i) {
        PackageBinding packageBinding = this.fPackage;
        return (packageBinding == null || (i & packageBinding.defaultNullness) == 0) ? false : true;
    }

    public void registerInferredInvocation(Invocation invocation) {
        if (this.inferredInvocations == null) {
            this.inferredInvocations = new ArrayList<>();
        }
        this.inferredInvocations.add(invocation);
    }

    public void cleanUpInferenceContexts() {
        ArrayList<Invocation> arrayList = this.inferredInvocations;
        if (arrayList == null) {
            return;
        }
        Iterator<Invocation> it = arrayList.iterator();
        while (it.hasNext()) {
            it.next().cleanUpInferenceContexts();
        }
        this.inferredInvocations = null;
    }
}
