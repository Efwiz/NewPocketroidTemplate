package org.jetbrains.kotlin.fir;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationDataRegistry;
import org.jetbrains.kotlin.fir.extensions.FirDeclarationGenerationExtension;

/* JADX INFO: compiled from: FirGeneratedDeclarationsUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\"3\u0010\u0002\u001a\u0004\u0018\u00010\u0001*\u00020\u00032\b\u0010\u0000\u001a\u0004\u0018\u00010\u00018@@@X\u0080\u008e\u0002¢\u0006\u0012\n\u0004\b\b\u0010\t\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007¨\u0006\n"}, d2 = {"<set-?>", "Lorg/jetbrains/kotlin/fir/extensions/FirDeclarationGenerationExtension;", "ownerGenerator", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "getOwnerGenerator", "(Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;)Lorg/jetbrains/kotlin/fir/extensions/FirDeclarationGenerationExtension;", "setOwnerGenerator", "(Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;Lorg/jetbrains/kotlin/fir/extensions/FirDeclarationGenerationExtension;)V", "ownerGenerator$delegate", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationDataRegistry$DeclarationDataAccessor;", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirGeneratedDeclarationsUtilsKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirGeneratedDeclarationsUtilsKt.class, "providers"), "ownerGenerator", "getOwnerGenerator(Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;)Lorg/jetbrains/kotlin/fir/extensions/FirDeclarationGenerationExtension;"))};
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor ownerGenerator$delegate = FirDeclarationDataRegistry.INSTANCE.data(OwnerForGeneratedDeclarationKey.INSTANCE);

    public static final FirDeclarationGenerationExtension getOwnerGenerator(FirClassLikeDeclaration firClassLikeDeclaration) {
        Intrinsics.checkNotNullParameter(firClassLikeDeclaration, "<this>");
        return (FirDeclarationGenerationExtension) ownerGenerator$delegate.getValue(firClassLikeDeclaration, $$delegatedProperties[0]);
    }

    public static final void setOwnerGenerator(FirClassLikeDeclaration firClassLikeDeclaration, FirDeclarationGenerationExtension firDeclarationGenerationExtension) {
        Intrinsics.checkNotNullParameter(firClassLikeDeclaration, "<this>");
        ownerGenerator$delegate.setValue(firClassLikeDeclaration, $$delegatedProperties[0], firDeclarationGenerationExtension);
    }
}
