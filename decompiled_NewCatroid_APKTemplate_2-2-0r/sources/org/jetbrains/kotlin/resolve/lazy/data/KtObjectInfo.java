package org.jetbrains.kotlin.resolve.lazy.data;

import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtStubbedPsiUtil;
import org.jetbrains.kotlin.psi.KtTypeParameterList;

/* JADX INFO: loaded from: classes6.dex */
public class KtObjectInfo extends KtClassOrObjectInfo<KtObjectDeclaration> {
    private final ClassKind kind;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
        } else {
            objArr[0] = "org/jetbrains/kotlin/resolve/lazy/data/KtObjectInfo";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/resolve/lazy/data/KtObjectInfo";
        } else {
            objArr[1] = "getClassKind";
        }
        if (i != 1) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected KtObjectInfo(KtObjectDeclaration ktObjectDeclaration) {
        super(ktObjectDeclaration);
        if (ktObjectDeclaration == null) {
            $$$reportNull$$$0(0);
        }
        this.kind = ktObjectDeclaration.isObjectLiteral() ? ClassKind.CLASS : ClassKind.OBJECT;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.data.KtClassLikeInfo
    /* JADX INFO: renamed from: getTypeParameterList */
    public KtTypeParameterList mo7453getTypeParameterList() {
        return ((KtObjectDeclaration) this.element).mo7395getTypeParameterList();
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.data.KtClassLikeInfo
    public ClassKind getClassKind() {
        ClassKind classKind = this.kind;
        if (classKind == null) {
            $$$reportNull$$$0(1);
        }
        return classKind;
    }

    public boolean isCompanionObject() {
        return ((KtObjectDeclaration) this.element).isCompanion() && (KtStubbedPsiUtil.getContainingDeclaration(this.element) instanceof KtClassOrObject);
    }
}
