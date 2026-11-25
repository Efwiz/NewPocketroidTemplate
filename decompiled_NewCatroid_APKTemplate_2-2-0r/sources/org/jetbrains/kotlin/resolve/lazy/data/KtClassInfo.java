package org.jetbrains.kotlin.resolve.lazy.data;

import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtEnumEntry;
import org.jetbrains.kotlin.psi.KtTypeParameterList;

/* JADX INFO: loaded from: classes6.dex */
public class KtClassInfo extends KtClassOrObjectInfo<KtClass> {
    private final ClassKind kind;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "classOrObject";
        } else {
            objArr[0] = "org/jetbrains/kotlin/resolve/lazy/data/KtClassInfo";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/resolve/lazy/data/KtClassInfo";
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
    protected KtClassInfo(KtClass ktClass) {
        super(ktClass);
        if (ktClass == null) {
            $$$reportNull$$$0(0);
        }
        if (this.element instanceof KtEnumEntry) {
            this.kind = ClassKind.ENUM_ENTRY;
            return;
        }
        if (((KtClass) this.element).isInterface()) {
            this.kind = ClassKind.INTERFACE;
            return;
        }
        if (((KtClass) this.element).isEnum()) {
            this.kind = ClassKind.ENUM_CLASS;
        } else if (((KtClass) this.element).isAnnotation()) {
            this.kind = ClassKind.ANNOTATION_CLASS;
        } else {
            this.kind = ClassKind.CLASS;
        }
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.data.KtClassLikeInfo
    /* JADX INFO: renamed from: getTypeParameterList */
    public KtTypeParameterList mo7453getTypeParameterList() {
        return ((KtClass) this.element).mo7395getTypeParameterList();
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.data.KtClassLikeInfo
    public ClassKind getClassKind() {
        ClassKind classKind = this.kind;
        if (classKind == null) {
            $$$reportNull$$$0(1);
        }
        return classKind;
    }
}
