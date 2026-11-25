package org.eclipse.jdt.internal.compiler.lookup;

import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.util.HashtableOfPackage;
import org.eclipse.jdt.internal.compiler.util.HashtableOfType;

/* JADX INFO: loaded from: classes14.dex */
public class PackageBinding extends Binding implements TypeConstants {
    public char[][] compoundName;
    protected int defaultNullness;
    public LookupEnvironment environment;
    HashtableOfPackage knownPackages;
    HashtableOfType knownTypes;
    PackageBinding parent;
    public long tagBits;

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public final int kind() {
        return 16;
    }

    protected PackageBinding() {
        this.tagBits = 0L;
        this.defaultNullness = 0;
    }

    public PackageBinding(char[] cArr, LookupEnvironment lookupEnvironment) {
        this(new char[][]{cArr}, null, lookupEnvironment);
    }

    public PackageBinding(char[][] cArr, PackageBinding packageBinding, LookupEnvironment lookupEnvironment) {
        this.tagBits = 0L;
        this.defaultNullness = 0;
        this.compoundName = cArr;
        this.parent = packageBinding;
        this.environment = lookupEnvironment;
        this.knownTypes = null;
        this.knownPackages = new HashtableOfPackage(3);
        if (cArr != CharOperation.NO_CHAR_CHAR) {
            checkIfNullAnnotationPackage();
        }
    }

    public PackageBinding(LookupEnvironment lookupEnvironment) {
        this(CharOperation.NO_CHAR_CHAR, null, lookupEnvironment);
    }

    private void addNotFoundPackage(char[] cArr) {
        this.knownPackages.put(cArr, LookupEnvironment.TheNotFoundPackage);
    }

    private void addNotFoundType(char[] cArr) {
        if (this.knownTypes == null) {
            this.knownTypes = new HashtableOfType(25);
        }
        this.knownTypes.put(cArr, LookupEnvironment.TheNotFoundType);
    }

    void addPackage(PackageBinding packageBinding) {
        if ((packageBinding.tagBits & 128) == 0) {
            clearMissingTagBit();
        }
        HashtableOfPackage hashtableOfPackage = this.knownPackages;
        char[][] cArr = packageBinding.compoundName;
        hashtableOfPackage.put(cArr[cArr.length - 1], packageBinding);
    }

    void addType(ReferenceBinding referenceBinding) {
        if ((referenceBinding.tagBits & 128) == 0) {
            clearMissingTagBit();
        }
        if (this.knownTypes == null) {
            this.knownTypes = new HashtableOfType(25);
        }
        ReferenceBinding referenceBinding2 = this.knownTypes.getput(referenceBinding.compoundName[referenceBinding.compoundName.length - 1], referenceBinding);
        if (referenceBinding2 != null && referenceBinding2.isUnresolvedType() && !referenceBinding.isUnresolvedType()) {
            ((UnresolvedReferenceBinding) referenceBinding2).setResolvedType(referenceBinding, this.environment);
        }
        if (this.environment.globalOptions.isAnnotationBasedNullAnalysisEnabled) {
            if (referenceBinding.isAnnotationType() || (referenceBinding instanceof UnresolvedReferenceBinding)) {
                checkIfNullAnnotationType(referenceBinding);
            }
        }
    }

    void clearMissingTagBit() {
        PackageBinding packageBinding = this;
        do {
            packageBinding.tagBits &= -129;
            packageBinding = packageBinding.parent;
        } while (packageBinding != null);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] computeUniqueKey(boolean z) {
        return CharOperation.concatWith(this.compoundName, '/');
    }

    private PackageBinding findPackage(char[] cArr) {
        if (!this.environment.isPackage(this.compoundName, cArr)) {
            return null;
        }
        PackageBinding packageBinding = new PackageBinding(CharOperation.arrayConcat(this.compoundName, cArr), this, this.environment);
        addPackage(packageBinding);
        return packageBinding;
    }

    PackageBinding getPackage(char[] cArr) {
        PackageBinding package0 = getPackage0(cArr);
        if (package0 != null) {
            if (package0 == LookupEnvironment.TheNotFoundPackage) {
                return null;
            }
            return package0;
        }
        PackageBinding packageBindingFindPackage = findPackage(cArr);
        if (packageBindingFindPackage != null) {
            return packageBindingFindPackage;
        }
        addNotFoundPackage(cArr);
        return null;
    }

    PackageBinding getPackage0(char[] cArr) {
        return this.knownPackages.get(cArr);
    }

    ReferenceBinding getType(char[] cArr) {
        ReferenceBinding type0 = getType0(cArr);
        if (type0 == null && (type0 = this.environment.askForType(this, cArr)) == null) {
            addNotFoundType(cArr);
            return null;
        }
        if (type0 == LookupEnvironment.TheNotFoundType) {
            return null;
        }
        ReferenceBinding referenceBinding = (ReferenceBinding) BinaryTypeBinding.resolveType(type0, this.environment, false);
        return referenceBinding.isNestedType() ? new ProblemReferenceBinding(new char[][]{cArr}, referenceBinding, 4) : referenceBinding;
    }

    ReferenceBinding getType0(char[] cArr) {
        HashtableOfType hashtableOfType = this.knownTypes;
        if (hashtableOfType == null) {
            return null;
        }
        return hashtableOfType.get(cArr);
    }

    public Binding getTypeOrPackage(char[] cArr) {
        ReferenceBinding type0 = getType0(cArr);
        if (type0 != null && type0 != LookupEnvironment.TheNotFoundType) {
            type0 = (ReferenceBinding) BinaryTypeBinding.resolveType(type0, this.environment, false);
            if (type0.isNestedType()) {
                return new ProblemReferenceBinding(new char[][]{cArr}, type0, 4);
            }
            if ((type0.tagBits & 128) == 0) {
                return type0;
            }
        }
        PackageBinding package0 = getPackage0(cArr);
        if (package0 != null && package0 != LookupEnvironment.TheNotFoundPackage) {
            return package0;
        }
        if (package0 == null) {
            PackageBinding packageBindingFindPackage = findPackage(cArr);
            if (packageBindingFindPackage != null) {
                return packageBindingFindPackage;
            }
            if (type0 != null && type0 != LookupEnvironment.TheNotFoundType) {
                return type0;
            }
            addNotFoundPackage(cArr);
        }
        if (type0 != null) {
            return null;
        }
        ReferenceBinding referenceBindingAskForType = this.environment.askForType(this, cArr);
        if (referenceBindingAskForType != null) {
            return referenceBindingAskForType.isNestedType() ? new ProblemReferenceBinding(new char[][]{cArr}, referenceBindingAskForType, 4) : referenceBindingAskForType;
        }
        addNotFoundType(cArr);
        return null;
    }

    public final boolean isViewedAsDeprecated() {
        ReferenceBinding type;
        long j = this.tagBits;
        if ((j & 17179869184L) == 0) {
            this.tagBits = j | 17179869184L;
            if (this.compoundName != CharOperation.NO_CHAR_CHAR && (type = getType(TypeConstants.PACKAGE_INFO_NAME)) != null) {
                type.initializeDeprecatedAnnotationTagBits();
                this.tagBits |= type.tagBits & TagBits.AllStandardAnnotationsMask;
            }
        }
        return (this.tagBits & 70368744177664L) != 0;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public int problemId() {
        return (this.tagBits & 128) != 0 ? 1 : 0;
    }

    void checkIfNullAnnotationPackage() {
        LookupEnvironment lookupEnvironment = this.environment;
        if (lookupEnvironment.globalOptions.isAnnotationBasedNullAnalysisEnabled) {
            if (isPackageOfQualifiedTypeName(this.compoundName, lookupEnvironment.getNullableAnnotationName())) {
                lookupEnvironment.nullableAnnotationPackage = this;
            }
            if (isPackageOfQualifiedTypeName(this.compoundName, lookupEnvironment.getNonNullAnnotationName())) {
                lookupEnvironment.nonnullAnnotationPackage = this;
            }
            if (isPackageOfQualifiedTypeName(this.compoundName, lookupEnvironment.getNonNullByDefaultAnnotationName())) {
                lookupEnvironment.nonnullByDefaultAnnotationPackage = this;
            }
        }
    }

    private boolean isPackageOfQualifiedTypeName(char[][] cArr, char[][] cArr2) {
        int length;
        if (cArr2 == null || (length = cArr.length) != cArr2.length - 1) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (!CharOperation.equals(cArr[i], cArr2[i])) {
                return false;
            }
        }
        return true;
    }

    void checkIfNullAnnotationType(ReferenceBinding referenceBinding) {
        if (this.environment.nullableAnnotationPackage == this && CharOperation.equals(referenceBinding.compoundName, this.environment.getNullableAnnotationName())) {
            referenceBinding.typeBits |= 64;
            if (referenceBinding instanceof UnresolvedReferenceBinding) {
                return;
            }
            this.environment.nullableAnnotationPackage = null;
            return;
        }
        if (this.environment.nonnullAnnotationPackage == this && CharOperation.equals(referenceBinding.compoundName, this.environment.getNonNullAnnotationName())) {
            referenceBinding.typeBits |= 32;
            if (referenceBinding instanceof UnresolvedReferenceBinding) {
                return;
            }
            this.environment.nonnullAnnotationPackage = null;
            return;
        }
        if (this.environment.nonnullByDefaultAnnotationPackage == this && CharOperation.equals(referenceBinding.compoundName, this.environment.getNonNullByDefaultAnnotationName())) {
            referenceBinding.typeBits |= 128;
            if (referenceBinding instanceof UnresolvedReferenceBinding) {
                return;
            }
            this.environment.nonnullByDefaultAnnotationPackage = null;
            return;
        }
        referenceBinding.typeBits |= this.environment.getNullAnnotationBit(referenceBinding.compoundName);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] readableName() {
        return CharOperation.concatWith(this.compoundName, '.');
    }

    public String toString() {
        String string;
        if (this.compoundName == CharOperation.NO_CHAR_CHAR) {
            string = "The Default Package";
        } else {
            StringBuilder sb = new StringBuilder("package ");
            char[][] cArr = this.compoundName;
            string = sb.append(cArr != null ? CharOperation.toString(cArr) : "UNNAMED").toString();
        }
        return (this.tagBits & 128) != 0 ? String.valueOf(string) + "[MISSING]" : string;
    }
}
