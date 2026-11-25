package org.eclipse.jdt.internal.compiler.lookup;

import java.util.Set;
import org.eclipse.jdt.core.compiler.InvalidInputException;

/* JADX INFO: loaded from: classes14.dex */
public class IntersectionTypeBinding18 extends ReferenceBinding {
    public ReferenceBinding[] intersectingTypes;
    private ReferenceBinding javaLangObject;
    int length;

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean canBeInstantiated() {
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isIntersectionType18() {
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public int kind() {
        return 32772;
    }

    public IntersectionTypeBinding18(ReferenceBinding[] referenceBindingArr, LookupEnvironment lookupEnvironment) {
        this.intersectingTypes = referenceBindingArr;
        this.length = referenceBindingArr.length;
        if (referenceBindingArr[0].isClass()) {
            return;
        }
        this.javaLangObject = lookupEnvironment.getResolvedType(TypeConstants.JAVA_LANG_OBJECT, null);
        this.modifiers |= 512;
    }

    private IntersectionTypeBinding18(IntersectionTypeBinding18 intersectionTypeBinding18) {
        ReferenceBinding[] referenceBindingArr = intersectionTypeBinding18.intersectingTypes;
        this.intersectingTypes = referenceBindingArr;
        this.length = intersectionTypeBinding18.length;
        if (referenceBindingArr[0].isClass()) {
            return;
        }
        this.javaLangObject = intersectionTypeBinding18.javaLangObject;
        this.modifiers |= 512;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding clone(TypeBinding typeBinding) {
        return new IntersectionTypeBinding18(this);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    protected MethodBinding[] getInterfaceAbstractContracts(Scope scope, boolean z) throws InvalidInputException {
        int length = this.intersectingTypes.length;
        MethodBinding[][] methodBindingArr = new MethodBinding[length][];
        int length2 = 0;
        for (int i = 0; i < length; i++) {
            MethodBinding[] interfaceAbstractContracts = this.intersectingTypes[i].getInterfaceAbstractContracts(scope, z);
            methodBindingArr[i] = interfaceAbstractContracts;
            length2 += interfaceAbstractContracts.length;
        }
        MethodBinding[] methodBindingArr2 = new MethodBinding[length2];
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            MethodBinding[] methodBindingArr3 = methodBindingArr[i3];
            int length3 = methodBindingArr3.length;
            System.arraycopy(methodBindingArr3, 0, methodBindingArr2, i2, length3);
            i2 += length3;
        }
        return methodBindingArr2;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean hasTypeBit(int i) {
        for (int i2 = 0; i2 < this.length; i2++) {
            if (this.intersectingTypes[i2].hasTypeBit(i)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public boolean canBeSeenBy(PackageBinding packageBinding) {
        for (int i = 0; i < this.length; i++) {
            if (!this.intersectingTypes[i].canBeSeenBy(packageBinding)) {
                return false;
            }
        }
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean canBeSeenBy(Scope scope) {
        for (int i = 0; i < this.length; i++) {
            if (!this.intersectingTypes[i].canBeSeenBy(scope)) {
                return false;
            }
        }
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public boolean canBeSeenBy(ReferenceBinding referenceBinding, ReferenceBinding referenceBinding2) {
        for (int i = 0; i < this.length; i++) {
            if (!this.intersectingTypes[i].canBeSeenBy(referenceBinding, referenceBinding2)) {
                return false;
            }
        }
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] constantPoolName() {
        return this.intersectingTypes[0].constantPoolName();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public PackageBinding getPackage() {
        throw new UnsupportedOperationException();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public ReferenceBinding[] getIntersectingTypes() {
        return this.intersectingTypes;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public ReferenceBinding superclass() {
        return this.intersectingTypes[0].isClass() ? this.intersectingTypes[0] : this.javaLangObject;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public ReferenceBinding[] superInterfaces() {
        if (this.intersectingTypes[0].isClass()) {
            int i = this.length;
            ReferenceBinding[] referenceBindingArr = new ReferenceBinding[i - 1];
            System.arraycopy(this.intersectingTypes, 1, referenceBindingArr, 0, i - 1);
            return referenceBindingArr;
        }
        return this.intersectingTypes;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isBoxedPrimitiveType() {
        return this.intersectingTypes[0].isBoxedPrimitiveType();
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0032  */
    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isCompatibleWith(TypeBinding typeBinding, Scope scope) {
        ReferenceBinding[] referenceBindingArr;
        if (TypeBinding.equalsEquals(this, typeBinding)) {
            return true;
        }
        int iKind = typeBinding.kind();
        if (iKind == 8196 && typeBinding.boundKind() == 1) {
            TypeBinding typeBindingAllBounds = ((WildcardBinding) typeBinding).allBounds();
            if (typeBindingAllBounds instanceof IntersectionTypeBinding18) {
                referenceBindingArr = ((IntersectionTypeBinding18) typeBindingAllBounds).intersectingTypes;
            } else {
                referenceBindingArr = null;
            }
        } else if (iKind == 32772) {
            referenceBindingArr = ((IntersectionTypeBinding18) typeBinding).intersectingTypes;
        } else {
            referenceBindingArr = null;
        }
        if (referenceBindingArr != null) {
            int length = referenceBindingArr.length;
            TypeBinding[] typeBindingArr = new TypeBinding[length];
            System.arraycopy(referenceBindingArr, 0, typeBindingArr, 0, length);
            int i = length;
            for (int i2 = 0; i2 < this.length; i2++) {
                ReferenceBinding referenceBinding = this.intersectingTypes[i2];
                for (int i3 = 0; i3 < length; i3++) {
                    TypeBinding typeBinding2 = typeBindingArr[i3];
                    if (typeBinding2 != null && referenceBinding.isCompatibleWith(typeBinding2, scope)) {
                        typeBindingArr[i3] = null;
                        i--;
                        if (i != 0) {
                            break;
                        }
                        return true;
                    }
                }
            }
            return false;
        }
        for (int i4 = 0; i4 < this.length; i4++) {
            if (this.intersectingTypes[i4].isCompatibleWith(typeBinding, scope)) {
                return true;
            }
        }
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isSubtypeOf(TypeBinding typeBinding) {
        if (TypeBinding.equalsEquals(this, typeBinding)) {
            return true;
        }
        int i = 0;
        while (true) {
            ReferenceBinding[] referenceBindingArr = this.intersectingTypes;
            if (i >= referenceBindingArr.length) {
                return false;
            }
            if (referenceBindingArr[i].isSubtypeOf(typeBinding)) {
                return true;
            }
            i++;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] qualifiedSourceName() {
        StringBuffer stringBuffer = new StringBuffer(16);
        for (int i = 0; i < this.length; i++) {
            stringBuffer.append(this.intersectingTypes[i].qualifiedSourceName());
            if (i != this.length - 1) {
                stringBuffer.append(" & ");
            }
        }
        return stringBuffer.toString().toCharArray();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] sourceName() {
        StringBuffer stringBuffer = new StringBuffer(16);
        for (int i = 0; i < this.length; i++) {
            stringBuffer.append(this.intersectingTypes[i].sourceName());
            if (i != this.length - 1) {
                stringBuffer.append(" & ");
            }
        }
        return stringBuffer.toString().toCharArray();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] readableName() {
        StringBuffer stringBuffer = new StringBuffer(16);
        for (int i = 0; i < this.length; i++) {
            stringBuffer.append(this.intersectingTypes[i].readableName());
            if (i != this.length - 1) {
                stringBuffer.append(" & ");
            }
        }
        return stringBuffer.toString().toCharArray();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] shortReadableName() {
        StringBuffer stringBuffer = new StringBuffer(16);
        for (int i = 0; i < this.length; i++) {
            stringBuffer.append(this.intersectingTypes[i].shortReadableName());
            if (i != this.length - 1) {
                stringBuffer.append(" & ");
            }
        }
        return stringBuffer.toString().toCharArray();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public String debugName() {
        StringBuffer stringBuffer = new StringBuffer(16);
        for (int i = 0; i < this.length; i++) {
            stringBuffer.append(this.intersectingTypes[i].debugName());
            if (i != this.length - 1) {
                stringBuffer.append(" & ");
            }
        }
        return stringBuffer.toString();
    }

    public String toString() {
        return debugName();
    }

    public TypeBinding getSAMType(Scope scope) {
        int length = this.intersectingTypes.length;
        for (int i = 0; i < length; i++) {
            ReferenceBinding referenceBinding = this.intersectingTypes[i];
            MethodBinding singleAbstractMethod = referenceBinding.getSingleAbstractMethod(scope, true);
            if (singleAbstractMethod != null && singleAbstractMethod.problemId() != 17) {
                return referenceBinding;
            }
        }
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    void collectInferenceVariables(Set<InferenceVariable> set) {
        int i = 0;
        while (true) {
            ReferenceBinding[] referenceBindingArr = this.intersectingTypes;
            if (i >= referenceBindingArr.length) {
                return;
            }
            referenceBindingArr[i].collectInferenceVariables(set);
            i++;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean mentionsAny(TypeBinding[] typeBindingArr, int i) {
        if (super.mentionsAny(typeBindingArr, i)) {
            return true;
        }
        int i2 = 0;
        while (true) {
            ReferenceBinding[] referenceBindingArr = this.intersectingTypes;
            if (i2 >= referenceBindingArr.length) {
                return false;
            }
            if (referenceBindingArr[i2].mentionsAny(typeBindingArr, -1)) {
                return true;
            }
            i2++;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public long updateTagBits() {
        for (ReferenceBinding referenceBinding : this.intersectingTypes) {
            this.tagBits |= referenceBinding.updateTagBits();
        }
        return super.updateTagBits();
    }
}
