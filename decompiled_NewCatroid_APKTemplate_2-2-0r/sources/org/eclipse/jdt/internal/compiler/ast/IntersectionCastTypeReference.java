package org.eclipse.jdt.internal.compiler.ast;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.IntersectionTypeBinding18;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class IntersectionCastTypeReference extends TypeReference {
    public TypeReference[] typeReferences;

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public char[] getLastToken() {
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    protected TypeBinding getTypeBinding(Scope scope) {
        return null;
    }

    public IntersectionCastTypeReference(TypeReference[] typeReferenceArr) {
        this.typeReferences = typeReferenceArr;
        this.sourceStart = typeReferenceArr[0].sourceStart;
        this.sourceEnd = typeReferenceArr[typeReferenceArr.length - 1].sourceEnd;
        for (TypeReference typeReference : typeReferenceArr) {
            if ((typeReference.bits & 1048576) != 0) {
                this.bits |= 1048576;
                return;
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public TypeReference augmentTypeWithAdditionalDimensions(int i, Annotation[][] annotationArr, boolean z) {
        throw new UnsupportedOperationException();
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public TypeReference[] getTypeReferences() {
        return this.typeReferences;
    }

    /* JADX WARN: Code duplicated, block: B:57:0x00e1  */
    /* JADX WARN: Code duplicated, block: B:60:0x00ec  */
    /* JADX WARN: Code duplicated, block: B:62:0x00f4  */
    /* JADX WARN: Code duplicated, block: B:63:0x00fd  */
    /* JADX WARN: Code duplicated, block: B:66:0x0104  */
    /* JADX WARN: Code duplicated, block: B:69:0x0113  */
    /* JADX WARN: Code duplicated, block: B:79:0x0122 A[EDGE_INSN: B:79:0x0122->B:74:0x0122 BREAK  A[LOOP:1: B:58:0x00e2->B:72:0x011e], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:80:0x010e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:81:0x00e4 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:83:0x011e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:84:0x011e A[SYNTHETIC] */
    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public TypeBinding resolveType(BlockScope blockScope, boolean z, int i) {
        ReferenceBinding[] referenceBindingArr;
        ReferenceBinding referenceBinding;
        int i2;
        int i3;
        ReferenceBinding referenceBinding2;
        int length = this.typeReferences.length;
        ReferenceBinding[] referenceBindingArr2 = new ReferenceBinding[length];
        int i4 = 0;
        int i5 = 0;
        boolean z2 = false;
        int i6 = 0;
        while (i5 < length) {
            TypeReference typeReference = this.typeReferences[i5];
            TypeBinding typeBindingResolveType = typeReference.resolveType(blockScope, z, i);
            if (typeBindingResolveType == null || (typeBindingResolveType.tagBits & 128) != 0) {
                z2 = true;
            } else if (i5 == 0) {
                if (typeBindingResolveType.isBaseType()) {
                    blockScope.problemReporter().onlyReferenceTypesInIntersectionCast(typeReference);
                } else if (!typeBindingResolveType.isArrayType()) {
                    i3 = 0;
                    while (true) {
                        if (i3 >= i6) {
                            referenceBinding2 = referenceBindingArr2[i3];
                            if (TypeBinding.equalsEquals(referenceBinding2, typeBindingResolveType)) {
                                blockScope.problemReporter().duplicateBoundInIntersectionCast(typeReference);
                                z2 = true;
                            } else if (referenceBinding2.isInterface()) {
                                if (TypeBinding.equalsEquals(typeBindingResolveType.findSuperTypeOriginatingFrom(referenceBinding2), referenceBinding2)) {
                                    referenceBindingArr2[i3] = (ReferenceBinding) typeBindingResolveType;
                                    break;
                                }
                                if (TypeBinding.equalsEquals(referenceBinding2.findSuperTypeOriginatingFrom(typeBindingResolveType), typeBindingResolveType)) {
                                    break;
                                }
                            } else {
                                continue;
                            }
                            i3++;
                        } else {
                            referenceBindingArr2[i6] = (ReferenceBinding) typeBindingResolveType;
                            i6++;
                            break;
                        }
                    }
                } else {
                    blockScope.problemReporter().illegalArrayTypeInIntersectionCast(typeReference);
                }
                z2 = true;
            } else if (typeBindingResolveType.isInterface()) {
                i3 = 0;
                while (true) {
                    if (i3 >= i6) {
                        referenceBinding2 = referenceBindingArr2[i3];
                        if (TypeBinding.equalsEquals(referenceBinding2, typeBindingResolveType)) {
                            blockScope.problemReporter().duplicateBoundInIntersectionCast(typeReference);
                            z2 = true;
                        } else if (referenceBinding2.isInterface()) {
                            if (TypeBinding.equalsEquals(typeBindingResolveType.findSuperTypeOriginatingFrom(referenceBinding2), referenceBinding2)) {
                                referenceBindingArr2[i3] = (ReferenceBinding) typeBindingResolveType;
                                break;
                                break;
                            }
                            if (TypeBinding.equalsEquals(referenceBinding2.findSuperTypeOriginatingFrom(typeBindingResolveType), typeBindingResolveType)) {
                                break;
                                break;
                            }
                        } else {
                            continue;
                        }
                        i3++;
                    } else {
                        referenceBindingArr2[i6] = (ReferenceBinding) typeBindingResolveType;
                        i6++;
                        break;
                        break;
                    }
                }
            } else {
                blockScope.problemReporter().boundMustBeAnInterface(typeReference, typeBindingResolveType);
                z2 = true;
            }
            i5++;
            i4 = 0;
        }
        if (z2) {
            return null;
        }
        if (i6 != length) {
            if (i6 == 1) {
                ReferenceBinding referenceBinding3 = referenceBindingArr2[i4];
                this.resolvedType = referenceBinding3;
                return referenceBinding3;
            }
            ReferenceBinding[] referenceBindingArr3 = new ReferenceBinding[i6];
            System.arraycopy(referenceBindingArr2, i4, referenceBindingArr3, i4, i6);
            referenceBindingArr2 = referenceBindingArr3;
        }
        IntersectionTypeBinding18 intersectionTypeBinding18 = (IntersectionTypeBinding18) blockScope.environment().createIntersectionType18(referenceBindingArr2);
        ReferenceBinding referenceBinding4 = referenceBindingArr2[i4];
        if (referenceBinding4.isClass()) {
            ReferenceBinding referenceBindingSuperclass = referenceBinding4.superclass();
            int i7 = i6 - 1;
            ReferenceBinding[] referenceBindingArr4 = new ReferenceBinding[i7];
            System.arraycopy(referenceBindingArr2, 1, referenceBindingArr4, i4, i7);
            referenceBinding = referenceBindingSuperclass;
            referenceBindingArr = referenceBindingArr4;
        } else {
            referenceBindingArr = referenceBindingArr2;
            referenceBinding = null;
        }
        Map map = new HashMap(2);
        int length2 = referenceBindingArr.length;
        int i8 = i4;
        while (i8 < length2) {
            ReferenceBinding referenceBinding5 = referenceBindingArr[i8];
            if (referenceBinding5 == null) {
                i2 = i8;
            } else {
                if (referenceBinding != null) {
                    i2 = i8;
                    if (blockScope.hasErasedCandidatesCollisions(referenceBinding, referenceBinding5, map, intersectionTypeBinding18, this)) {
                    }
                } else {
                    i2 = i8;
                }
                for (int i9 = 0; i9 < i2; i9++) {
                    ReferenceBinding referenceBinding6 = referenceBindingArr[i9];
                    if (referenceBinding6 != null && blockScope.hasErasedCandidatesCollisions(referenceBinding5, referenceBinding6, map, intersectionTypeBinding18, this)) {
                        break;
                    }
                }
            }
            i8 = i2 + 1;
        }
        if ((intersectionTypeBinding18.tagBits & 131072) != 0) {
            return null;
        }
        this.resolvedType = intersectionTypeBinding18;
        return intersectionTypeBinding18;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference
    public char[][] getTypeName() {
        return this.typeReferences[0].getTypeName();
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            TypeReference[] typeReferenceArr = this.typeReferences;
            int length = typeReferenceArr == null ? 0 : typeReferenceArr.length;
            for (int i = 0; i < length; i++) {
                this.typeReferences[i].traverse(aSTVisitor, blockScope);
            }
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.TypeReference, org.eclipse.jdt.internal.compiler.ast.Expression
    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        throw new UnsupportedOperationException("Unexpected traversal request: IntersectionTypeReference in class scope");
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        TypeReference[] typeReferenceArr = this.typeReferences;
        int length = typeReferenceArr == null ? 0 : typeReferenceArr.length;
        printIndent(i, stringBuffer);
        for (int i2 = 0; i2 < length; i2++) {
            this.typeReferences[i2].printExpression(0, stringBuffer);
            if (i2 != length - 1) {
                stringBuffer.append(" & ");
            }
        }
        return stringBuffer;
    }
}
