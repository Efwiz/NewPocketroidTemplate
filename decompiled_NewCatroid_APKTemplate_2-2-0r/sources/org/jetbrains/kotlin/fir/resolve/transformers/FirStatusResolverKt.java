package org.jetbrains.kotlin.fir.resolve.transformers;

import kotlin.Metadata;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;

/* JADX INFO: compiled from: FirStatusResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0005\u001a\u00020\u0006*\u00020\u0007H\u0002\"\u001a\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\b"}, d2 = {"modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "getModality", "(Lorg/jetbrains/kotlin/fir/declarations/FirClass;)Lorg/jetbrains/kotlin/descriptors/Modality;", "hasOwnBodyOrAccessorBody", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirStatusResolverKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final Modality getModality(FirClass firClass) {
        if (firClass instanceof FirRegularClass) {
            return firClass.getStatus().getModality();
        }
        if (firClass instanceof FirAnonymousObject) {
            return Modality.FINAL;
        }
        throw new IllegalStateException(("Unknown kind of class: " + Reflection.getOrCreateKotlinClass(firClass.getClass())).toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean hasOwnBodyOrAccessorBody(FirDeclaration firDeclaration) {
        if (firDeclaration instanceof FirSimpleFunction) {
            if (((FirSimpleFunction) firDeclaration).getBody() == null) {
                return false;
            }
        } else if (firDeclaration instanceof FirProperty) {
            FirProperty firProperty = (FirProperty) firDeclaration;
            if (firProperty.getInitializer() == null) {
                FirPropertyAccessor getter = firProperty.getGetter();
                if ((getter != null ? getter.getBody() : null) == null) {
                    FirPropertyAccessor setter = firProperty.getSetter();
                    if ((setter != null ? setter.getBody() : null) == null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
