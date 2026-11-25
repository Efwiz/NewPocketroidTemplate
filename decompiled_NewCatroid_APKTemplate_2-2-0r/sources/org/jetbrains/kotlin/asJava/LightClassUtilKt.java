package org.jetbrains.kotlin.asJava;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtProperty;

/* JADX INFO: compiled from: LightClassUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"getAccessorLightMethods", "Lorg/jetbrains/kotlin/asJava/LightClassUtil$PropertyAccessorsPsiMethods;", "Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;", "light-classes-base"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LightClassUtilKt {
    public static final LightClassUtil.PropertyAccessorsPsiMethods getAccessorLightMethods(KtNamedDeclaration ktNamedDeclaration) {
        Intrinsics.checkNotNullParameter(ktNamedDeclaration, "<this>");
        if (ktNamedDeclaration instanceof KtProperty) {
            return LightClassUtil.INSTANCE.getLightClassPropertyMethods((KtProperty) ktNamedDeclaration);
        }
        if (ktNamedDeclaration instanceof KtParameter) {
            return LightClassUtil.INSTANCE.getLightClassPropertyMethods((KtParameter) ktNamedDeclaration);
        }
        throw new IllegalStateException("Unexpected property type: " + ktNamedDeclaration);
    }
}
