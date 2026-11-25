package org.jetbrains.kotlin.resolve.lazy.data;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;

/* JADX INFO: loaded from: classes6.dex */
public class KtClassInfoUtil {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        objArr[0] = "classOrObject";
        objArr[1] = "org/jetbrains/kotlin/resolve/lazy/data/KtClassInfoUtil";
        if (i != 1) {
            objArr[2] = "createClassLikeInfo";
        } else {
            objArr[2] = "createClassOrObjectInfo";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Use createClassOrObjectInfo(KtClassOrObject) instead")
    @Deprecated
    public static KtClassLikeInfo createClassLikeInfo(KtClassOrObject ktClassOrObject) {
        if (ktClassOrObject == null) {
            $$$reportNull$$$0(0);
        }
        return createClassOrObjectInfo(ktClassOrObject);
    }

    public static KtClassOrObjectInfo<? extends KtClassOrObject> createClassOrObjectInfo(KtClassOrObject ktClassOrObject) {
        if (ktClassOrObject == null) {
            $$$reportNull$$$0(1);
        }
        if (ktClassOrObject instanceof KtClass) {
            return new KtClassInfo((KtClass) ktClassOrObject);
        }
        if (ktClassOrObject instanceof KtObjectDeclaration) {
            return new KtObjectInfo((KtObjectDeclaration) ktClassOrObject);
        }
        throw new IllegalArgumentException("Unknown declaration type: " + ktClassOrObject + ktClassOrObject.getText());
    }
}
