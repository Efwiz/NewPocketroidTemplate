package org.jetbrains.kotlin.fir.extensions;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.util.AbstractArrayMapOwner;
import org.jetbrains.kotlin.util.ArrayMapAccessor;

/* JADX INFO: compiled from: FirDeclarationGenerationExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\"%\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\u0004\u0010\u0005*\n\u0010\b\"\u00020\t2\u00020\t*\n\u0010\n\"\u00020\u000b2\u00020\u000b¨\u0006\f"}, d2 = {"declarationGenerators", "", "Lorg/jetbrains/kotlin/fir/extensions/FirDeclarationGenerationExtension;", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionService;", "getDeclarationGenerators", "(Lorg/jetbrains/kotlin/fir/extensions/FirExtensionService;)Ljava/util/List;", "declarationGenerators$delegate", "Lorg/jetbrains/kotlin/util/ArrayMapAccessor;", "MemberGenerationContext", "Lorg/jetbrains/kotlin/fir/extensions/DeclarationGenerationContext$Member;", "NestedClassGenerationContext", "Lorg/jetbrains/kotlin/fir/extensions/DeclarationGenerationContext$Nested;", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirDeclarationGenerationExtensionKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirDeclarationGenerationExtensionKt.class, "providers"), "declarationGenerators", "getDeclarationGenerators(Lorg/jetbrains/kotlin/fir/extensions/FirExtensionService;)Ljava/util/List;"))};
    private static final ArrayMapAccessor declarationGenerators$delegate;

    static {
        FirExtensionService.Companion companion = FirExtensionService.INSTANCE;
        KClass orCreateKotlinClass = Reflection.getOrCreateKotlinClass(FirDeclarationGenerationExtension.class);
        List listEmptyList = CollectionsKt.emptyList();
        Intrinsics.checkNotNull(listEmptyList, "null cannot be cast to non-null type V of org.jetbrains.kotlin.fir.extensions.FirExtensionService.Companion.registeredExtensions");
        declarationGenerators$delegate = companion.generateAccessor(orCreateKotlinClass, listEmptyList);
    }

    public static final List<FirDeclarationGenerationExtension> getDeclarationGenerators(FirExtensionService firExtensionService) {
        Intrinsics.checkNotNullParameter(firExtensionService, "<this>");
        return (List) declarationGenerators$delegate.getValue((AbstractArrayMapOwner) firExtensionService, $$delegatedProperties[0]);
    }
}
