package org.jetbrains.kotlin.fir.plugin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.GeneratedDeclarationKey;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.impl.FirResolvedDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.expressions.FirArgumentUtilKt;
import org.jetbrains.kotlin.fir.expressions.builder.FirDelegatedConstructorCallBuilder;
import org.jetbrains.kotlin.fir.extensions.FirExtension;
import org.jetbrains.kotlin.fir.references.builder.FirResolvedNamedReferenceBuilder;
import org.jetbrains.kotlin.fir.scopes.FirScopeKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirDeclaredMemberScopeProviderKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;

/* JADX INFO: compiled from: ConstructorBuildingContext.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001aM\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\u0019\b\u0002\u0010\n\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b¢\u0006\u0002\b\u000e\u001a(\u0010\u000f\u001a\u00020\u0001*\u00020\u00022\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\b\u001a\u0015\u0010\u0010\u001a\u00020\r*\u00020\u0001H\u0002R\u00020\u0002¢\u0006\u0002\u0010\u0011¨\u0006\u0012"}, d2 = {"createConstructor", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "Lorg/jetbrains/kotlin/fir/extensions/FirExtension;", "owner", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/GeneratedDeclarationKey;", "isPrimary", "", "generateDelegatedNoArgConstructorCall", "config", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/plugin/ConstructorBuildingContext;", "", "Lkotlin/ExtensionFunctionType;", "createDefaultPrivateConstructor", "generateNoArgDelegatingConstructorCall", "(Lorg/jetbrains/kotlin/fir/extensions/FirExtension;Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;)V", "plugin-utils"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConstructorBuildingContextKt {
    public static /* synthetic */ FirConstructor createConstructor$default(FirExtension firExtension, FirClassSymbol firClassSymbol, GeneratedDeclarationKey generatedDeclarationKey, boolean z, boolean z2, Function1 function1, int i, Object obj) {
        boolean z3 = (i & 4) != 0 ? false : z;
        boolean z4 = (i & 8) != 0 ? false : z2;
        if ((i & 16) != 0) {
            function1 = new Function1<ConstructorBuildingContext, Unit>() { // from class: org.jetbrains.kotlin.fir.plugin.ConstructorBuildingContextKt.createConstructor.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ConstructorBuildingContext constructorBuildingContext) {
                    Intrinsics.checkNotNullParameter(constructorBuildingContext, "$this$null");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ConstructorBuildingContext constructorBuildingContext) {
                    invoke2(constructorBuildingContext);
                    return Unit.INSTANCE;
                }
            };
        }
        return createConstructor(firExtension, firClassSymbol, generatedDeclarationKey, z3, z4, function1);
    }

    public static final FirConstructor createConstructor(FirExtension firExtension, final FirClassSymbol<?> owner, GeneratedDeclarationKey key, boolean z, boolean z2, Function1<? super ConstructorBuildingContext, Unit> config) {
        Intrinsics.checkNotNullParameter(firExtension, "<this>");
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(config, "config");
        ConstructorBuildingContext constructorBuildingContext = new ConstructorBuildingContext(firExtension.getSession(), key, owner, z);
        config.invoke(constructorBuildingContext);
        constructorBuildingContext.status(new Function1<FirResolvedDeclarationStatusImpl, Unit>() { // from class: org.jetbrains.kotlin.fir.plugin.ConstructorBuildingContextKt$createConstructor$2$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl) {
                invoke2(firResolvedDeclarationStatusImpl);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirResolvedDeclarationStatusImpl status) {
                Intrinsics.checkNotNullParameter(status, "$this$status");
                status.setExpect(owner.getRawStatus().isExpect());
            }
        });
        FirConstructor firConstructorBuild = constructorBuildingContext.build();
        if (z2) {
            generateNoArgDelegatingConstructorCall(firExtension, firConstructorBuild);
        }
        return firConstructorBuild;
    }

    public static /* synthetic */ FirConstructor createDefaultPrivateConstructor$default(FirExtension firExtension, FirClassSymbol firClassSymbol, GeneratedDeclarationKey generatedDeclarationKey, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = true;
        }
        return createDefaultPrivateConstructor(firExtension, firClassSymbol, generatedDeclarationKey, z);
    }

    public static final FirConstructor createDefaultPrivateConstructor(FirExtension firExtension, FirClassSymbol<?> owner, GeneratedDeclarationKey key, boolean z) {
        Intrinsics.checkNotNullParameter(firExtension, "<this>");
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(key, "key");
        return createConstructor(firExtension, owner, key, true, z, new Function1<ConstructorBuildingContext, Unit>() { // from class: org.jetbrains.kotlin.fir.plugin.ConstructorBuildingContextKt.createDefaultPrivateConstructor.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(ConstructorBuildingContext constructorBuildingContext) {
                invoke2(constructorBuildingContext);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(ConstructorBuildingContext createConstructor) {
                Intrinsics.checkNotNullParameter(createConstructor, "$this$createConstructor");
                createConstructor.setVisibility(Visibilities.Private.INSTANCE);
            }
        });
    }

    private static final void generateNoArgDelegatingConstructorCall(FirExtension firExtension, FirConstructor firConstructor) {
        ConeClassLikeType type;
        Object next;
        FirClassifierSymbol<?> symbol = org.jetbrains.kotlin.fir.types.TypeUtilsKt.toSymbol(FirTypeUtilsKt.getConeType(firConstructor.getReturnTypeRef()), firExtension.getSession());
        FirClassSymbol firClassSymbol = symbol instanceof FirClassSymbol ? (FirClassSymbol) symbol : null;
        if (firClassSymbol == null) {
            throw new IllegalArgumentException("Required value was null.".toString());
        }
        FirDelegatedConstructorCallBuilder firDelegatedConstructorCallBuilder = new FirDelegatedConstructorCallBuilder();
        List<ConeKotlinType> resolvedSuperTypes = firClassSymbol.getResolvedSuperTypes();
        ArrayList arrayList = new ArrayList();
        for (Object obj : resolvedSuperTypes) {
            FirRegularClassSymbol regularClassSymbol = org.jetbrains.kotlin.fir.types.TypeUtilsKt.toRegularClassSymbol((ConeKotlinType) obj, firExtension.getSession());
            if ((regularClassSymbol != null ? regularClassSymbol.getClassKind() : null) == ClassKind.CLASS) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        if (size == 0) {
            type = firExtension.getSession().getBuiltinTypes().getAnyType().getType();
        } else if (size == 1) {
            type = (ConeKotlinType) CollectionsKt.first((List) arrayList2);
        } else {
            throw new IllegalStateException(("Object " + firClassSymbol + " has more than one class supertypes: " + arrayList2).toString());
        }
        firDelegatedConstructorCallBuilder.setConstructedTypeRef(org.jetbrains.kotlin.fir.types.TypeUtilsKt.toFirResolvedTypeRef$default(type, null, null, 3, null));
        FirRegularClassSymbol regularClassSymbol2 = org.jetbrains.kotlin.fir.types.TypeUtilsKt.toRegularClassSymbol(type, firExtension.getSession());
        if (regularClassSymbol2 == null) {
            throw new IllegalStateException(("Symbol for supertype " + type + " not found").toString());
        }
        Iterator<T> it = FirScopeKt.getDeclaredConstructors(FirDeclaredMemberScopeProviderKt.declaredMemberScope(regularClassSymbol2, firExtension.getSession(), (FirResolvePhase) null)).iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!((FirConstructorSymbol) next).getValueParameterSymbols().isEmpty());
        FirConstructorSymbol firConstructorSymbol = (FirConstructorSymbol) next;
        if (firConstructorSymbol == null) {
            throw new IllegalStateException(("No arguments constructor for class " + type + " not found").toString());
        }
        FirResolvedNamedReferenceBuilder firResolvedNamedReferenceBuilder = new FirResolvedNamedReferenceBuilder();
        firResolvedNamedReferenceBuilder.setName(firConstructorSymbol.getName());
        firResolvedNamedReferenceBuilder.setResolvedSymbol(firConstructorSymbol);
        firDelegatedConstructorCallBuilder.setCalleeReference(firResolvedNamedReferenceBuilder.build());
        firDelegatedConstructorCallBuilder.setArgumentList(FirArgumentUtilKt.buildResolvedArgumentList$default(new LinkedHashMap(), null, 2, null));
        firDelegatedConstructorCallBuilder.setThis(false);
        firConstructor.replaceDelegatedConstructor(firDelegatedConstructorCallBuilder.mo6809build());
    }
}
