package org.jetbrains.kotlin.fir.resolve;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirCodeFragment;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationDataRegistry;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirQualifierPart;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: DeclarationUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\\\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u001a \u0010\u001d\u001a\u00020\u000b2\u0006\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010!2\u0006\u0010\"\u001a\u00020#\u001a\u0014\u0010$\u001a\u0004\u0018\u00010%*\u00020&2\u0006\u0010\"\u001a\u00020#\u001a\u0014\u0010'\u001a\u0004\u0018\u00010%*\u00020%2\u0006\u0010\"\u001a\u00020#\u001a\u001b\u0010(\u001a\b\u0012\u0004\u0012\u00020*0)*\b\u0012\u0004\u0012\u00020,0+¢\u0006\u0002\u0010-\"3\u0010\u0002\u001a\u0004\u0018\u00010\u0001*\u00020\u00032\b\u0010\u0000\u001a\u0004\u0018\u00010\u00018F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\b\u0010\t\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007\"\u0015\u0010\n\u001a\u00020\u000b*\u00020\f8F¢\u0006\u0006\u001a\u0004\b\n\u0010\r\"3\u0010\u000f\u001a\u0004\u0018\u00010\u000e*\u00020\u000e2\b\u0010\u0000\u001a\u0004\u0018\u00010\u000e8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u0014\u0010\t\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013\"3\u0010\u0016\u001a\u0004\u0018\u00010\u0015*\u00020\u000e2\b\u0010\u0000\u001a\u0004\u0018\u00010\u00158F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u001b\u0010\t\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a\"\u0017\u0010\u0016\u001a\u0004\u0018\u00010\u0015*\u00020\f8F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u001c¨\u0006."}, d2 = {"<set-?>", "Lorg/jetbrains/kotlin/fir/resolve/FirCodeFragmentContext;", "codeFragmentContext", "Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;", "getCodeFragmentContext", "(Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;)Lorg/jetbrains/kotlin/fir/resolve/FirCodeFragmentContext;", "setCodeFragmentContext", "(Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;Lorg/jetbrains/kotlin/fir/resolve/FirCodeFragmentContext;)V", "codeFragmentContext$delegate", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationDataRegistry$DeclarationDataAccessor;", "isTypeAliasedConstructor", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;)Z", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "originalConstructorIfTypeAlias", "getOriginalConstructorIfTypeAlias", "(Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;)Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "setOriginalConstructorIfTypeAlias", "(Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;)V", "originalConstructorIfTypeAlias$delegate", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeAliasSymbol;", "typeAliasForConstructor", "getTypeAliasForConstructor", "(Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;)Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeAliasSymbol;", "setTypeAliasForConstructor", "(Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeAliasSymbol;)V", "typeAliasForConstructor$delegate", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;)Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeAliasSymbol;", "isValidTypeParameterFromOuterDeclaration", "typeParameterSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "firClassLike", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "getContainingDeclaration", "toTypeProjections", "", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "", "Lorg/jetbrains/kotlin/fir/types/FirQualifierPart;", "(Ljava/util/List;)[Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "semantics"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DeclarationUtilsKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(DeclarationUtilsKt.class, "semantics"), "originalConstructorIfTypeAlias", "getOriginalConstructorIfTypeAlias(Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;)Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(DeclarationUtilsKt.class, "semantics"), "typeAliasForConstructor", "getTypeAliasForConstructor(Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;)Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeAliasSymbol;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(DeclarationUtilsKt.class, "semantics"), "codeFragmentContext", "getCodeFragmentContext(Lorg/jetbrains/kotlin/fir/declarations/FirCodeFragment;)Lorg/jetbrains/kotlin/fir/resolve/FirCodeFragmentContext;"))};
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor originalConstructorIfTypeAlias$delegate = FirDeclarationDataRegistry.INSTANCE.data(TypeAliasConstructorKey.INSTANCE);
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor typeAliasForConstructor$delegate = FirDeclarationDataRegistry.INSTANCE.data(TypeAliasForConstructorKey.INSTANCE);
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor codeFragmentContext$delegate = FirDeclarationDataRegistry.INSTANCE.data(CodeFragmentContextDataKey.INSTANCE);

    public static final boolean isValidTypeParameterFromOuterDeclaration(FirTypeParameterSymbol typeParameterSymbol, FirDeclaration firDeclaration, FirSession session) {
        Intrinsics.checkNotNullParameter(typeParameterSymbol, "typeParameterSymbol");
        Intrinsics.checkNotNullParameter(session, "session");
        if (firDeclaration == null) {
            return true;
        }
        return isValidTypeParameterFromOuterDeclaration$containsTypeParameter(new LinkedHashSet(), session, typeParameterSymbol, firDeclaration);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final boolean isValidTypeParameterFromOuterDeclaration$containsTypeParameter(Set<FirDeclaration> set, FirSession firSession, FirTypeParameterSymbol firTypeParameterSymbol, FirDeclaration firDeclaration) {
        if (firDeclaration != 0 && set.add(firDeclaration) && (firDeclaration instanceof FirTypeParameterRefsOwner)) {
            List<FirTypeParameterRef> typeParameters = ((FirTypeParameterRefsOwner) firDeclaration).getTypeParameters();
            if (!(typeParameters instanceof Collection) || !typeParameters.isEmpty()) {
                Iterator<T> it = typeParameters.iterator();
                while (it.hasNext()) {
                    if (Intrinsics.areEqual(((FirTypeParameterRef) it.next()).getSymbol(), firTypeParameterSymbol)) {
                        return true;
                    }
                }
            }
            if (firDeclaration instanceof FirCallableDeclaration) {
                ClassId classId = ((FirCallableDeclaration) firDeclaration).getSymbol().getCallableId().getClassId();
                if (classId == null) {
                    return true;
                }
                FirClassLikeSymbol<?> classLikeSymbolByClassId = FirSymbolProviderKt.getSymbolProvider(firSession).getClassLikeSymbolByClassId(classId);
                return isValidTypeParameterFromOuterDeclaration$containsTypeParameter(set, firSession, firTypeParameterSymbol, classLikeSymbolByClassId != null ? (FirClassLikeDeclaration) classLikeSymbolByClassId.getFir() : null);
            }
            if (firDeclaration instanceof FirClass) {
                Iterator<FirTypeRef> it2 = ((FirClass) firDeclaration).getSuperTypeRefs().iterator();
                while (it2.hasNext()) {
                    FirClassLikeDeclaration firClassLikeDeclarationFirClassLike = firClassLike(it2.next(), firSession);
                    if (firClassLikeDeclarationFirClassLike == null || ((firClassLikeDeclarationFirClassLike instanceof FirRegularClass) && isValidTypeParameterFromOuterDeclaration$containsTypeParameter(set, firSession, firTypeParameterSymbol, firClassLikeDeclarationFirClassLike))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static final ConeTypeProjection[] toTypeProjections(List<? extends FirQualifierPart> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        List listAsReversed = CollectionsKt.asReversed(list);
        ArrayList arrayList = new ArrayList();
        Iterator it = listAsReversed.iterator();
        while (it.hasNext()) {
            List<FirTypeProjection> typeArguments = ((FirQualifierPart) it.next()).getTypeArgumentList().getTypeArguments();
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeArguments, 10));
            Iterator<T> it2 = typeArguments.iterator();
            while (it2.hasNext()) {
                arrayList2.add(FirTypeUtilsKt.toConeTypeProjection((FirTypeProjection) it2.next()));
            }
            CollectionsKt.addAll(arrayList, arrayList2);
        }
        return (ConeTypeProjection[]) arrayList.toArray(new ConeTypeProjection[0]);
    }

    public static final FirConstructor getOriginalConstructorIfTypeAlias(FirConstructor firConstructor) {
        Intrinsics.checkNotNullParameter(firConstructor, "<this>");
        return (FirConstructor) originalConstructorIfTypeAlias$delegate.getValue(firConstructor, $$delegatedProperties[0]);
    }

    public static final void setOriginalConstructorIfTypeAlias(FirConstructor firConstructor, FirConstructor firConstructor2) {
        Intrinsics.checkNotNullParameter(firConstructor, "<this>");
        originalConstructorIfTypeAlias$delegate.setValue(firConstructor, $$delegatedProperties[0], firConstructor2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final boolean isTypeAliasedConstructor(FirConstructorSymbol firConstructorSymbol) {
        Intrinsics.checkNotNullParameter(firConstructorSymbol, "<this>");
        return getOriginalConstructorIfTypeAlias((FirConstructor) firConstructorSymbol.getFir()) != null;
    }

    public static final FirTypeAliasSymbol getTypeAliasForConstructor(FirConstructor firConstructor) {
        Intrinsics.checkNotNullParameter(firConstructor, "<this>");
        return (FirTypeAliasSymbol) typeAliasForConstructor$delegate.getValue(firConstructor, $$delegatedProperties[1]);
    }

    public static final void setTypeAliasForConstructor(FirConstructor firConstructor, FirTypeAliasSymbol firTypeAliasSymbol) {
        Intrinsics.checkNotNullParameter(firConstructor, "<this>");
        typeAliasForConstructor$delegate.setValue(firConstructor, $$delegatedProperties[1], firTypeAliasSymbol);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final FirTypeAliasSymbol getTypeAliasForConstructor(FirConstructorSymbol firConstructorSymbol) {
        Intrinsics.checkNotNullParameter(firConstructorSymbol, "<this>");
        return getTypeAliasForConstructor((FirConstructor) firConstructorSymbol.getFir());
    }

    public static final FirCodeFragmentContext getCodeFragmentContext(FirCodeFragment firCodeFragment) {
        Intrinsics.checkNotNullParameter(firCodeFragment, "<this>");
        return (FirCodeFragmentContext) codeFragmentContext$delegate.getValue(firCodeFragment, $$delegatedProperties[2]);
    }

    public static final void setCodeFragmentContext(FirCodeFragment firCodeFragment, FirCodeFragmentContext firCodeFragmentContext) {
        Intrinsics.checkNotNullParameter(firCodeFragment, "<this>");
        codeFragmentContext$delegate.setValue(firCodeFragment, $$delegatedProperties[2], firCodeFragmentContext);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final FirClassLikeDeclaration getContainingDeclaration(FirClassLikeDeclaration firClassLikeDeclaration, FirSession session) {
        ConeClassLikeLookupTag containingClassForLocalAttr;
        Intrinsics.checkNotNullParameter(firClassLikeDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FirRegularClass firRegularClass = null;
        if (firClassLikeDeclaration.getSymbol().getClassId().isLocal()) {
            FirRegularClass firRegularClass2 = firClassLikeDeclaration instanceof FirRegularClass ? (FirRegularClass) firClassLikeDeclaration : null;
            if (firRegularClass2 != null && (containingClassForLocalAttr = ClassMembersKt.getContainingClassForLocalAttr(firRegularClass2)) != null) {
                firRegularClass = LookupTagUtilsKt.toFirRegularClass(containingClassForLocalAttr, session);
            }
            return firRegularClass;
        }
        ClassId classId = firClassLikeDeclaration.getSymbol().getClassId();
        FqName fqNameParent = classId.getRelativeClassName().parent();
        Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
        if (fqNameParent.isRoot()) {
            return null;
        }
        FirClassLikeSymbol<?> classLikeSymbolByClassId = FirSymbolProviderKt.getSymbolProvider(session).getClassLikeSymbolByClassId(new ClassId(classId.getPackageFqName(), fqNameParent, false));
        if (classLikeSymbolByClassId != null) {
            return (FirClassLikeDeclaration) classLikeSymbolByClassId.getFir();
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final FirClassLikeDeclaration firClassLike(FirTypeRef firTypeRef, FirSession session) {
        FirClassLikeSymbol<?> symbol;
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FirResolvedTypeRef firResolvedTypeRef = firTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) firTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        if (!(type instanceof ConeClassLikeType)) {
            type = null;
        }
        ConeClassLikeType coneClassLikeType = (ConeClassLikeType) type;
        if (coneClassLikeType == null || (symbol = LookupTagUtilsKt.toSymbol(coneClassLikeType.getLookupTag(), session)) == null) {
            return null;
        }
        return (FirClassLikeDeclaration) symbol.getFir();
    }
}
