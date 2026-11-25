package org.jetbrains.kotlin.fir.declarations.utils;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationDataRegistry;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyBackingField;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyGetter;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.impl.FirPropertyFromParameterResolvedNamedReference;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirSyntheticPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;

/* JADX INFO: compiled from: declarationAttributes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000n\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010F\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a*\u00020\u001e\u001a\f\u0010G\u001a\u0004\u0018\u00010H*\u00020\u0002\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0006\"3\u0010\t\u001a\u0004\u0018\u00010\b*\u00020\u00022\b\u0010\u0007\u001a\u0004\u0018\u00010\b8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r\"\u0017\u0010\u0010\u001a\u0004\u0018\u00010\u0011*\u00020\u00128F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014\"\u0017\u0010\u0015\u001a\u0004\u0018\u00010\u0016*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018\"\u0017\u0010\u0015\u001a\u0004\u0018\u00010\u0016*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0019\"M\u0010\u001c\u001a\n\u0012\u0004\u0012\u00020\u001b\u0018\u00010\u001a\"\f\b\u0000\u0010\u001d*\u00020\u001e*\u00020\u001f*\u0002H\u001d2\u000e\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\u001b\u0018\u00010\u001a8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b$\u0010\u000f\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#\"3\u0010%\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\b\u0010\u0007\u001a\u0004\u0018\u00010\u00018F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b*\u0010\u000f\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)\"\u0015\u0010%\u001a\u00020\u0001*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b&\u0010\u0006\"\u0015\u0010+\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b,\u0010\u0004\"\u0015\u0010+\u001a\u00020\u0001*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b,\u0010\u0006\"\u0015\u0010-\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b.\u0010\u0004\"\u0015\u0010-\u001a\u00020\u0001*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b.\u0010\u0006\"3\u0010/\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\b\u0010\u0007\u001a\u0004\u0018\u00010\u00018F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b1\u0010\u000f\u001a\u0004\b/\u0010'\"\u0004\b0\u0010)\"3\u00102\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\b\u0010\u0007\u001a\u0004\u0018\u00010\u00018F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b4\u0010\u000f\u001a\u0004\b2\u0010'\"\u0004\b3\u0010)\"3\u00106\u001a\u0004\u0018\u000105*\u0002072\b\u0010\u0007\u001a\u0004\u0018\u0001058F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b<\u0010\u000f\u001a\u0004\b8\u00109\"\u0004\b:\u0010;\"3\u0010=\u001a\u0004\u0018\u00010\u0011*\u00020>2\b\u0010\u0007\u001a\u0004\u0018\u00010\u00118F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\bC\u0010\u000f\u001a\u0004\b?\u0010@\"\u0004\bA\u0010B\"\u001b\u0010=\u001a\u0004\u0018\u00010\u0011*\u0006\u0012\u0002\b\u00030D8F¢\u0006\u0006\u001a\u0004\b?\u0010E¨\u0006I"}, d2 = {"canNarrowDownGetterType", "", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "getCanNarrowDownGetterType", "(Lorg/jetbrains/kotlin/fir/declarations/FirProperty;)Z", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;)Z", "<set-?>", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "componentFunctionSymbol", "getComponentFunctionSymbol", "(Lorg/jetbrains/kotlin/fir/declarations/FirProperty;)Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "setComponentFunctionSymbol", "(Lorg/jetbrains/kotlin/fir/declarations/FirProperty;Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;)V", "componentFunctionSymbol$delegate", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationDataRegistry$DeclarationDataAccessor;", "containerSource", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "getContainerSource", "(Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;)Lorg/jetbrains/kotlin/descriptors/SourceElement;", "correspondingValueParameterFromPrimaryConstructor", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirValueParameterSymbol;", "getCorrespondingValueParameterFromPrimaryConstructor", "(Lorg/jetbrains/kotlin/fir/declarations/FirProperty;)Lorg/jetbrains/kotlin/fir/symbols/impl/FirValueParameterSymbol;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;)Lorg/jetbrains/kotlin/fir/symbols/impl/FirValueParameterSymbol;", "", "Lorg/jetbrains/kotlin/fir/declarations/utils/DanglingTypeConstraint;", "danglingTypeConstraints", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRefsOwner;", "getDanglingTypeConstraints", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;)Ljava/util/List;", "setDanglingTypeConstraints", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;Ljava/util/List;)V", "danglingTypeConstraints$delegate", "fromPrimaryConstructor", "getFromPrimaryConstructor", "(Lorg/jetbrains/kotlin/fir/declarations/FirProperty;)Ljava/lang/Boolean;", "setFromPrimaryConstructor", "(Lorg/jetbrains/kotlin/fir/declarations/FirProperty;Ljava/lang/Boolean;)V", "fromPrimaryConstructor$delegate", "hasBackingField", "getHasBackingField", "hasExplicitBackingField", "getHasExplicitBackingField", "isFromVararg", "setFromVararg", "isFromVararg$delegate", "isReferredViaField", "setReferredViaField", "isReferredViaField$delegate", "", "moduleName", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "getModuleName", "(Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;)Ljava/lang/String;", "setModuleName", "(Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;Ljava/lang/String;)V", "moduleName$delegate", "sourceElement", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "getSourceElement", "(Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;)Lorg/jetbrains/kotlin/descriptors/SourceElement;", "setSourceElement", "(Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;Lorg/jetbrains/kotlin/descriptors/SourceElement;)V", "sourceElement$delegate", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;)Lorg/jetbrains/kotlin/descriptors/SourceElement;", "getDanglingTypeConstraintsOrEmpty", "getExplicitBackingField", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DeclarationAttributesKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(DeclarationAttributesKt.class, Constants.TYPE_TREE), "isFromVararg", "isFromVararg(Lorg/jetbrains/kotlin/fir/declarations/FirProperty;)Ljava/lang/Boolean;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(DeclarationAttributesKt.class, Constants.TYPE_TREE), "isReferredViaField", "isReferredViaField(Lorg/jetbrains/kotlin/fir/declarations/FirProperty;)Ljava/lang/Boolean;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(DeclarationAttributesKt.class, Constants.TYPE_TREE), "fromPrimaryConstructor", "getFromPrimaryConstructor(Lorg/jetbrains/kotlin/fir/declarations/FirProperty;)Ljava/lang/Boolean;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(DeclarationAttributesKt.class, Constants.TYPE_TREE), "componentFunctionSymbol", "getComponentFunctionSymbol(Lorg/jetbrains/kotlin/fir/declarations/FirProperty;)Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(DeclarationAttributesKt.class, Constants.TYPE_TREE), "sourceElement", "getSourceElement(Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;)Lorg/jetbrains/kotlin/descriptors/SourceElement;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(DeclarationAttributesKt.class, Constants.TYPE_TREE), "moduleName", "getModuleName(Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;)Ljava/lang/String;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(DeclarationAttributesKt.class, Constants.TYPE_TREE), "danglingTypeConstraints", "getDanglingTypeConstraints(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;)Ljava/util/List;"))};
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor isFromVararg$delegate = FirDeclarationDataRegistry.INSTANCE.data(IsFromVarargKey.INSTANCE);
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor isReferredViaField$delegate = FirDeclarationDataRegistry.INSTANCE.data(IsReferredViaField.INSTANCE);
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor fromPrimaryConstructor$delegate = FirDeclarationDataRegistry.INSTANCE.data(IsFromPrimaryConstructor.INSTANCE);
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor componentFunctionSymbol$delegate = FirDeclarationDataRegistry.INSTANCE.data(ComponentFunctionSymbolKey.INSTANCE);
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor sourceElement$delegate = FirDeclarationDataRegistry.INSTANCE.data(SourceElementKey.INSTANCE);
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor moduleName$delegate = FirDeclarationDataRegistry.INSTANCE.data(ModuleNameKey.INSTANCE);
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor danglingTypeConstraints$delegate = FirDeclarationDataRegistry.INSTANCE.data(DanglingTypeConstraintsKey.INSTANCE);

    public static final Boolean isFromVararg(FirProperty firProperty) {
        Intrinsics.checkNotNullParameter(firProperty, "<this>");
        return (Boolean) isFromVararg$delegate.getValue(firProperty, $$delegatedProperties[0]);
    }

    public static final void setFromVararg(FirProperty firProperty, Boolean bool) {
        Intrinsics.checkNotNullParameter(firProperty, "<this>");
        isFromVararg$delegate.setValue(firProperty, $$delegatedProperties[0], bool);
    }

    public static final Boolean isReferredViaField(FirProperty firProperty) {
        Intrinsics.checkNotNullParameter(firProperty, "<this>");
        return (Boolean) isReferredViaField$delegate.getValue(firProperty, $$delegatedProperties[1]);
    }

    public static final void setReferredViaField(FirProperty firProperty, Boolean bool) {
        Intrinsics.checkNotNullParameter(firProperty, "<this>");
        isReferredViaField$delegate.setValue(firProperty, $$delegatedProperties[1], bool);
    }

    public static final Boolean getFromPrimaryConstructor(FirProperty firProperty) {
        Intrinsics.checkNotNullParameter(firProperty, "<this>");
        return (Boolean) fromPrimaryConstructor$delegate.getValue(firProperty, $$delegatedProperties[2]);
    }

    public static final void setFromPrimaryConstructor(FirProperty firProperty, Boolean bool) {
        Intrinsics.checkNotNullParameter(firProperty, "<this>");
        fromPrimaryConstructor$delegate.setValue(firProperty, $$delegatedProperties[2], bool);
    }

    public static final FirNamedFunctionSymbol getComponentFunctionSymbol(FirProperty firProperty) {
        Intrinsics.checkNotNullParameter(firProperty, "<this>");
        return (FirNamedFunctionSymbol) componentFunctionSymbol$delegate.getValue(firProperty, $$delegatedProperties[3]);
    }

    public static final void setComponentFunctionSymbol(FirProperty firProperty, FirNamedFunctionSymbol firNamedFunctionSymbol) {
        Intrinsics.checkNotNullParameter(firProperty, "<this>");
        componentFunctionSymbol$delegate.setValue(firProperty, $$delegatedProperties[3], firNamedFunctionSymbol);
    }

    public static final SourceElement getSourceElement(FirClassLikeDeclaration firClassLikeDeclaration) {
        Intrinsics.checkNotNullParameter(firClassLikeDeclaration, "<this>");
        return (SourceElement) sourceElement$delegate.getValue(firClassLikeDeclaration, $$delegatedProperties[4]);
    }

    public static final void setSourceElement(FirClassLikeDeclaration firClassLikeDeclaration, SourceElement sourceElement) {
        Intrinsics.checkNotNullParameter(firClassLikeDeclaration, "<this>");
        sourceElement$delegate.setValue(firClassLikeDeclaration, $$delegatedProperties[4], sourceElement);
    }

    public static final String getModuleName(FirRegularClass firRegularClass) {
        Intrinsics.checkNotNullParameter(firRegularClass, "<this>");
        return (String) moduleName$delegate.getValue(firRegularClass, $$delegatedProperties[5]);
    }

    public static final void setModuleName(FirRegularClass firRegularClass, String str) {
        Intrinsics.checkNotNullParameter(firRegularClass, "<this>");
        moduleName$delegate.setValue(firRegularClass, $$delegatedProperties[5], str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final SourceElement getSourceElement(FirClassLikeSymbol<?> firClassLikeSymbol) {
        Intrinsics.checkNotNullParameter(firClassLikeSymbol, "<this>");
        return getSourceElement((FirClassLikeDeclaration) firClassLikeSymbol.getFir());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final boolean getFromPrimaryConstructor(FirPropertySymbol firPropertySymbol) {
        Intrinsics.checkNotNullParameter(firPropertySymbol, "<this>");
        Boolean fromPrimaryConstructor = getFromPrimaryConstructor((FirProperty) firPropertySymbol.getFir());
        if (fromPrimaryConstructor != null) {
            return fromPrimaryConstructor.booleanValue();
        }
        return false;
    }

    public static final <T extends FirDeclaration & FirTypeParameterRefsOwner> List<DanglingTypeConstraint> getDanglingTypeConstraints(T t) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        return (List) danglingTypeConstraints$delegate.getValue(t, $$delegatedProperties[6]);
    }

    public static final <T extends FirDeclaration & FirTypeParameterRefsOwner> void setDanglingTypeConstraints(T t, List<DanglingTypeConstraint> list) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        danglingTypeConstraints$delegate.setValue(t, $$delegatedProperties[6], list);
    }

    public static final SourceElement getContainerSource(FirMemberDeclaration firMemberDeclaration) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        if (firMemberDeclaration instanceof FirCallableDeclaration) {
            return ((FirCallableDeclaration) firMemberDeclaration).getContainerSource();
        }
        if (firMemberDeclaration instanceof FirClassLikeDeclaration) {
            return getSourceElement((FirClassLikeDeclaration) firMemberDeclaration);
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final boolean getHasExplicitBackingField(FirProperty firProperty) {
        Intrinsics.checkNotNullParameter(firProperty, "<this>");
        return (firProperty.getBackingField() == null || (firProperty.getBackingField() instanceof FirDefaultPropertyBackingField)) ? false : true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final boolean getHasExplicitBackingField(FirPropertySymbol firPropertySymbol) {
        Intrinsics.checkNotNullParameter(firPropertySymbol, "<this>");
        return getHasExplicitBackingField((FirProperty) firPropertySymbol.getFir());
    }

    public static final FirBackingField getExplicitBackingField(FirProperty firProperty) {
        Intrinsics.checkNotNullParameter(firProperty, "<this>");
        if (getHasExplicitBackingField(firProperty)) {
            return firProperty.getBackingField();
        }
        return null;
    }

    public static final boolean getCanNarrowDownGetterType(FirProperty firProperty) {
        FirTypeRef returnTypeRef;
        Intrinsics.checkNotNullParameter(firProperty, "<this>");
        if (firProperty.getBackingField() != null) {
            FirBackingField backingField = firProperty.getBackingField();
            if (!Intrinsics.areEqual((backingField == null || (returnTypeRef = backingField.getReturnTypeRef()) == null) ? null : FirTypeUtilsKt.getConeType(returnTypeRef), FirTypeUtilsKt.getConeType(firProperty.getReturnTypeRef())) && (firProperty.getGetter() instanceof FirDefaultPropertyGetter)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final boolean getCanNarrowDownGetterType(FirPropertySymbol firPropertySymbol) {
        Intrinsics.checkNotNullParameter(firPropertySymbol, "<this>");
        return getCanNarrowDownGetterType((FirProperty) firPropertySymbol.getFir());
    }

    public static final boolean getHasBackingField(FirProperty firProperty) {
        FirPropertyAccessor setter;
        Intrinsics.checkNotNullParameter(firProperty, "<this>");
        FirProperty firProperty2 = firProperty;
        if (firProperty2.getStatus().getModality() == Modality.ABSTRACT || firProperty2.getStatus().isExpect() || firProperty.getDelegate() != null) {
            return false;
        }
        if (getHasExplicitBackingField(firProperty)) {
            return true;
        }
        if ((firProperty.getSymbol() instanceof FirSyntheticPropertySymbol) || firProperty2.getStatus().isStatic()) {
            return false;
        }
        FirDeclarationOrigin origin = firProperty.getOrigin();
        if ((origin instanceof FirDeclarationOrigin.SubstitutionOverride) || Intrinsics.areEqual(origin, FirDeclarationOrigin.IntersectionOverride.INSTANCE) || Intrinsics.areEqual(origin, FirDeclarationOrigin.Delegated.INSTANCE)) {
            return false;
        }
        FirPropertyAccessor getter = firProperty.getGetter();
        if (getter == null) {
            return true;
        }
        if (firProperty.getIsVar() && firProperty.getSetter() == null) {
            return true;
        }
        FirPropertyAccessor setter2 = firProperty.getSetter();
        if (setter2 != null && setter2.getBody() == null && (setter = firProperty.getSetter()) != null && setter.getStatus().getModality() != Modality.ABSTRACT) {
            return true;
        }
        if (getter.getBody() == null && getter.getStatus().getModality() != Modality.ABSTRACT) {
            return true;
        }
        return Intrinsics.areEqual((Object) isReferredViaField(firProperty), (Object) true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final boolean getHasBackingField(FirPropertySymbol firPropertySymbol) {
        Intrinsics.checkNotNullParameter(firPropertySymbol, "<this>");
        FirLazyDeclarationResolverKt.lazyResolveToPhase(firPropertySymbol, FirResolvePhase.BODY_RESOLVE);
        return getHasBackingField((FirProperty) firPropertySymbol.getFir());
    }

    public static final List<DanglingTypeConstraint> getDanglingTypeConstraintsOrEmpty(FirDeclaration firDeclaration) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        List<DanglingTypeConstraint> danglingTypeConstraints = ((firDeclaration instanceof FirRegularClass) || (firDeclaration instanceof FirSimpleFunction) || (firDeclaration instanceof FirProperty)) ? getDanglingTypeConstraints(firDeclaration) : null;
        return danglingTypeConstraints == null ? CollectionsKt.emptyList() : danglingTypeConstraints;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final FirValueParameterSymbol getCorrespondingValueParameterFromPrimaryConstructor(FirPropertySymbol firPropertySymbol) {
        Intrinsics.checkNotNullParameter(firPropertySymbol, "<this>");
        return getCorrespondingValueParameterFromPrimaryConstructor((FirProperty) firPropertySymbol.getFir());
    }

    public static final FirValueParameterSymbol getCorrespondingValueParameterFromPrimaryConstructor(FirProperty firProperty) {
        Intrinsics.checkNotNullParameter(firProperty, "<this>");
        if (!Intrinsics.areEqual((Object) getFromPrimaryConstructor(firProperty), (Object) true)) {
            return null;
        }
        FirExpression initializer = firProperty.getInitializer();
        FirPropertyAccessExpression firPropertyAccessExpression = initializer instanceof FirPropertyAccessExpression ? (FirPropertyAccessExpression) initializer : null;
        if (firPropertyAccessExpression == null) {
            return null;
        }
        FirReference calleeReference = firPropertyAccessExpression.getCalleeReference();
        FirPropertyFromParameterResolvedNamedReference firPropertyFromParameterResolvedNamedReference = calleeReference instanceof FirPropertyFromParameterResolvedNamedReference ? (FirPropertyFromParameterResolvedNamedReference) calleeReference : null;
        if (firPropertyFromParameterResolvedNamedReference == null) {
            return null;
        }
        FirBasedSymbol<?> resolvedSymbol = firPropertyFromParameterResolvedNamedReference.getResolvedSymbol();
        if (resolvedSymbol instanceof FirValueParameterSymbol) {
            return (FirValueParameterSymbol) resolvedSymbol;
        }
        return null;
    }
}
