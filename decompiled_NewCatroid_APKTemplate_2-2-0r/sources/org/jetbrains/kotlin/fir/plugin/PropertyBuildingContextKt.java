package org.jetbrains.kotlin.fir.plugin;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.GeneratedDeclarationKey;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.impl.FirResolvedDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.extensions.FirExtension;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: PropertyBuildingContext.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000N\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001ao\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0018\u0010\t\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\u0004\u0012\u00020\r0\n2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u000f2\u0019\b\u0002\u0010\u0011\u001a\u0013\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00130\n¢\u0006\u0002\b\u0014\u001a]\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u000f2\u0019\b\u0002\u0010\u0011\u001a\u0013\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00130\n¢\u0006\u0002\b\u0014\u001ac\u0010\u0016\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00182\u0018\u0010\t\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\u0004\u0012\u00020\r0\n2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u000f2\u0019\b\u0002\u0010\u0011\u001a\u0013\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00130\n¢\u0006\u0002\b\u0014\u001aQ\u0010\u0016\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0015\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u000f2\u0019\b\u0002\u0010\u0011\u001a\u0013\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00130\n¢\u0006\u0002\b\u0014¨\u0006\u0019"}, d2 = {"createMemberProperty", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "Lorg/jetbrains/kotlin/fir/extensions/FirExtension;", "owner", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/GeneratedDeclarationKey;", "name", "Lorg/jetbrains/kotlin/name/Name;", "returnTypeProvider", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "isVal", "", "hasBackingField", "config", "Lorg/jetbrains/kotlin/fir/plugin/PropertyBuildingContext;", "", "Lkotlin/ExtensionFunctionType;", "returnType", "createTopLevelProperty", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "plugin-utils"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PropertyBuildingContextKt {
    public static final FirProperty createMemberProperty(FirExtension firExtension, FirClassSymbol<?> owner, GeneratedDeclarationKey key, Name name2, final ConeKotlinType returnType, boolean z, boolean z2, Function1<? super PropertyBuildingContext, Unit> config) {
        Intrinsics.checkNotNullParameter(firExtension, "<this>");
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(config, "config");
        return createMemberProperty(firExtension, owner, key, name2, new Function1<List<? extends FirTypeParameterRef>, ConeKotlinType>() { // from class: org.jetbrains.kotlin.fir.plugin.PropertyBuildingContextKt.createMemberProperty.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ConeKotlinType invoke(List<? extends FirTypeParameterRef> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return returnType;
            }
        }, z, z2, config);
    }

    public static final FirProperty createMemberProperty(FirExtension firExtension, final FirClassSymbol<?> owner, GeneratedDeclarationKey key, Name name2, Function1<? super List<? extends FirTypeParameterRef>, ? extends ConeKotlinType> returnTypeProvider, boolean z, boolean z2, Function1<? super PropertyBuildingContext, Unit> config) {
        Intrinsics.checkNotNullParameter(firExtension, "<this>");
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(returnTypeProvider, "returnTypeProvider");
        Intrinsics.checkNotNullParameter(config, "config");
        PropertyBuildingContext propertyBuildingContext = new PropertyBuildingContext(firExtension.getSession(), key, owner, new CallableId(owner.getClassId(), name2), returnTypeProvider, z, z2);
        config.invoke(propertyBuildingContext);
        propertyBuildingContext.status(new Function1<FirResolvedDeclarationStatusImpl, Unit>() { // from class: org.jetbrains.kotlin.fir.plugin.PropertyBuildingContextKt$createMemberProperty$4$1
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
        return propertyBuildingContext.build();
    }

    public static final FirProperty createTopLevelProperty(FirExtension firExtension, GeneratedDeclarationKey key, CallableId callableId, final ConeKotlinType returnType, boolean z, boolean z2, Function1<? super PropertyBuildingContext, Unit> config) {
        Intrinsics.checkNotNullParameter(firExtension, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(callableId, "callableId");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(config, "config");
        return createTopLevelProperty(firExtension, key, callableId, new Function1<List<? extends FirTypeParameterRef>, ConeKotlinType>() { // from class: org.jetbrains.kotlin.fir.plugin.PropertyBuildingContextKt.createTopLevelProperty.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ConeKotlinType invoke(List<? extends FirTypeParameterRef> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return returnType;
            }
        }, z, z2, config);
    }

    public static final FirProperty createTopLevelProperty(FirExtension firExtension, GeneratedDeclarationKey key, CallableId callableId, Function1<? super List<? extends FirTypeParameterRef>, ? extends ConeKotlinType> returnTypeProvider, boolean z, boolean z2, Function1<? super PropertyBuildingContext, Unit> config) {
        Intrinsics.checkNotNullParameter(firExtension, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(callableId, "callableId");
        Intrinsics.checkNotNullParameter(returnTypeProvider, "returnTypeProvider");
        Intrinsics.checkNotNullParameter(config, "config");
        if (callableId.getClassId() != null) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        PropertyBuildingContext propertyBuildingContext = new PropertyBuildingContext(firExtension.getSession(), key, null, callableId, returnTypeProvider, z, z2);
        config.invoke(propertyBuildingContext);
        return propertyBuildingContext.build();
    }
}
