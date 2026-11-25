package org.jetbrains.kotlin.fir.extensions;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.reflect.KProperty;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.impl.FirDeclarationStatusImpl;
import org.jetbrains.kotlin.util.AbstractArrayMapOwner;
import org.jetbrains.kotlin.util.ArrayMapAccessor;

/* JADX INFO: compiled from: FirStatusTransformerExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u001aA\u0010\b\u001a\u00020\t*\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r2\u0019\b\u0002\u0010\u000e\u001a\u0013\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000f¢\u0006\u0002\b\u0012H\u0086\bø\u0001\u0000\"%\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\u0004\u0010\u0005\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0013"}, d2 = {"statusTransformerExtensions", "", "Lorg/jetbrains/kotlin/fir/extensions/FirStatusTransformerExtension;", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionService;", "getStatusTransformerExtensions", "(Lorg/jetbrains/kotlin/fir/extensions/FirExtensionService;)Ljava/util/List;", "statusTransformerExtensions$delegate", "Lorg/jetbrains/kotlin/util/ArrayMapAccessor;", "transform", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", ConfigConstants.CONFIG_INIT_SECTION, "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/declarations/impl/FirDeclarationStatusImpl;", "", "Lkotlin/ExtensionFunctionType;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirStatusTransformerExtensionKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirStatusTransformerExtensionKt.class, "resolve"), "statusTransformerExtensions", "getStatusTransformerExtensions(Lorg/jetbrains/kotlin/fir/extensions/FirExtensionService;)Ljava/util/List;"))};
    private static final ArrayMapAccessor statusTransformerExtensions$delegate;

    static {
        FirExtensionService.Companion companion = FirExtensionService.INSTANCE;
        KClass orCreateKotlinClass = Reflection.getOrCreateKotlinClass(FirStatusTransformerExtension.class);
        List listEmptyList = CollectionsKt.emptyList();
        Intrinsics.checkNotNull(listEmptyList, "null cannot be cast to non-null type V of org.jetbrains.kotlin.fir.extensions.FirExtensionService.Companion.registeredExtensions");
        statusTransformerExtensions$delegate = companion.generateAccessor(orCreateKotlinClass, listEmptyList);
    }

    public static final List<FirStatusTransformerExtension> getStatusTransformerExtensions(FirExtensionService firExtensionService) {
        Intrinsics.checkNotNullParameter(firExtensionService, "<this>");
        return (List) statusTransformerExtensions$delegate.getValue((AbstractArrayMapOwner) firExtensionService, $$delegatedProperties[0]);
    }

    public static /* synthetic */ FirDeclarationStatus transform$default(FirDeclarationStatus firDeclarationStatus, Visibility visibility, Modality modality, Function1 init, int i, Object obj) {
        if ((i & 1) != 0) {
            visibility = firDeclarationStatus.getVisibility();
        }
        if ((i & 2) != 0) {
            modality = firDeclarationStatus.getModality();
        }
        if ((i & 4) != 0) {
            init = new Function1<FirDeclarationStatusImpl, Unit>() { // from class: org.jetbrains.kotlin.fir.extensions.FirStatusTransformerExtensionKt.transform.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirDeclarationStatusImpl firDeclarationStatusImpl) {
                    Intrinsics.checkNotNullParameter(firDeclarationStatusImpl, "$this$null");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(FirDeclarationStatusImpl firDeclarationStatusImpl) {
                    invoke2(firDeclarationStatusImpl);
                    return Unit.INSTANCE;
                }
            };
        }
        Intrinsics.checkNotNullParameter(firDeclarationStatus, "<this>");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(init, "init");
        FirDeclarationStatusImpl firDeclarationStatusImpl = new FirDeclarationStatusImpl(visibility, modality);
        firDeclarationStatusImpl.setExpect(firDeclarationStatus.isExpect());
        firDeclarationStatusImpl.setActual(firDeclarationStatus.isActual());
        firDeclarationStatusImpl.setOverride(firDeclarationStatus.isOverride());
        firDeclarationStatusImpl.setOperator(firDeclarationStatus.isOperator());
        firDeclarationStatusImpl.setInfix(firDeclarationStatus.isInfix());
        firDeclarationStatusImpl.setInline(firDeclarationStatus.isInline());
        firDeclarationStatusImpl.setTailRec(firDeclarationStatus.isTailRec());
        firDeclarationStatusImpl.setExternal(firDeclarationStatus.isExternal());
        firDeclarationStatusImpl.setConst(firDeclarationStatus.isConst());
        firDeclarationStatusImpl.setLateInit(firDeclarationStatus.isLateInit());
        firDeclarationStatusImpl.setInner(firDeclarationStatus.isInner());
        firDeclarationStatusImpl.setCompanion(firDeclarationStatus.isCompanion());
        firDeclarationStatusImpl.setData(firDeclarationStatus.isData());
        firDeclarationStatusImpl.setSuspend(firDeclarationStatus.isSuspend());
        firDeclarationStatusImpl.setStatic(firDeclarationStatus.isStatic());
        firDeclarationStatusImpl.setFromSealedClass(firDeclarationStatus.isFromSealedClass());
        firDeclarationStatusImpl.setFromEnumClass(firDeclarationStatus.isFromEnumClass());
        init.invoke(firDeclarationStatusImpl);
        return firDeclarationStatusImpl;
    }

    public static final FirDeclarationStatus transform(FirDeclarationStatus firDeclarationStatus, Visibility visibility, Modality modality, Function1<? super FirDeclarationStatusImpl, Unit> init) {
        Intrinsics.checkNotNullParameter(firDeclarationStatus, "<this>");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(init, "init");
        FirDeclarationStatusImpl firDeclarationStatusImpl = new FirDeclarationStatusImpl(visibility, modality);
        firDeclarationStatusImpl.setExpect(firDeclarationStatus.isExpect());
        firDeclarationStatusImpl.setActual(firDeclarationStatus.isActual());
        firDeclarationStatusImpl.setOverride(firDeclarationStatus.isOverride());
        firDeclarationStatusImpl.setOperator(firDeclarationStatus.isOperator());
        firDeclarationStatusImpl.setInfix(firDeclarationStatus.isInfix());
        firDeclarationStatusImpl.setInline(firDeclarationStatus.isInline());
        firDeclarationStatusImpl.setTailRec(firDeclarationStatus.isTailRec());
        firDeclarationStatusImpl.setExternal(firDeclarationStatus.isExternal());
        firDeclarationStatusImpl.setConst(firDeclarationStatus.isConst());
        firDeclarationStatusImpl.setLateInit(firDeclarationStatus.isLateInit());
        firDeclarationStatusImpl.setInner(firDeclarationStatus.isInner());
        firDeclarationStatusImpl.setCompanion(firDeclarationStatus.isCompanion());
        firDeclarationStatusImpl.setData(firDeclarationStatus.isData());
        firDeclarationStatusImpl.setSuspend(firDeclarationStatus.isSuspend());
        firDeclarationStatusImpl.setStatic(firDeclarationStatus.isStatic());
        firDeclarationStatusImpl.setFromSealedClass(firDeclarationStatus.isFromSealedClass());
        firDeclarationStatusImpl.setFromEnumClass(firDeclarationStatus.isFromEnumClass());
        init.invoke(firDeclarationStatusImpl);
        return firDeclarationStatusImpl;
    }
}
