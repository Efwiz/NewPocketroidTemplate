package org.jetbrains.kotlin.fir.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKindKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.scopes.FakeOverrideTypeCalculator;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt;
import org.jetbrains.kotlin.fir.scopes.ProcessorAction;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.utils.exceptions.FirExceptionUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeCheckerProviderContext;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder;
import org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments;

/* JADX INFO: compiled from: FunctionalTypeUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Z\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0018\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u001c\u0010\u0005\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a\u0012\u0010\t\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u001c\u0010\n\u001a\u0004\u0018\u00010\u000b*\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r\u001a0\u0010\u000e\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000f*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0012\u001a\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0006\u001a\u001e\u0010\u0014\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0006H\u0002\u001a\u0016\u0010\u0015\u001a\u0004\u0018\u00010\b*\u00020\u00162\u0006\u0010\u0003\u001a\u00020\u0004H\u0002\u001a\u0014\u0010\u0015\u001a\u0004\u0018\u00010\b*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0012\u0010\u0017\u001a\u00020\u0012*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u001c\u0010\u0018\u001a\u00020\u0012*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u0012H\u0002\u001a3\u0010\u001a\u001a\u00020\u0012*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0019\u001a\u00020\u00122\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00120\u001cH\u0082\b\u001a\u0012\u0010\u001d\u001a\u00020\u0012*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0012\u0010\u001e\u001a\u00020\u0012*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0012\u0010\u001f\u001a\u00020\u0012*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0012\u0010 \u001a\u00020\u0012*\u00020\u00162\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0012\u0010 \u001a\u00020\u0012*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u001c\u0010!\u001a\u00020\u0012*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0006H\u0002\u001a\u0012\u0010\"\u001a\u00020\u0012*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0014\u0010#\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0012\u0010$\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0014\u0010%\u001a\u0004\u0018\u00010\b*\u00020&2\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0014\u0010'\u001a\u00020\u0002*\u00020(2\u0006\u0010)\u001a\u00020\u0002H\u0002\u001a\u0018\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0018\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006,"}, d2 = {"contextReceiversTypes", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "createFunctionTypeWithNewKind", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/builtins/functions/FunctionTypeKind;", "customFunctionTypeToSimpleFunctionType", "findBaseInvokeSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "findContributedInvokeSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "expectedFunctionType", "shouldCalculateReturnTypesOfFakeOverrides", "", "findSubtypeOfBasicFunctionType", "findSubtypeOfBasicFunctionTypeImpl", "functionTypeKind", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "isBasicFunctionType", "isFunctionOrKFunctionType", "errorOnNotFunctionType", "isFunctionTypeWithPredicate", "predicate", "Lkotlin/Function1;", "isNonReflectFunctionType", "isNotBasicFunctionType", "isReflectFunctionType", "isSomeFunctionType", "isSubtypeOfFunctionType", "isSuspendOrKSuspendFunctionType", "receiverType", "returnType", "specialFunctionTypeKind", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "typeOrDefault", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "default", "valueParameterTypesIncludingReceiver", "valueParameterTypesWithoutReceivers", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FunctionalTypeUtilsKt {
    public static final FunctionTypeKind functionTypeKind(ConeKotlinType coneKotlinType, FirSession session) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        if (coneKotlinType instanceof ConeClassLikeType) {
            return functionTypeKind(TypeExpansionUtilsKt.fullyExpandedType$default((ConeClassLikeType) coneKotlinType, session, null, 2, null).getLookupTag(), session);
        }
        return null;
    }

    private static final FunctionTypeKind functionTypeKind(ConeClassLikeLookupTag coneClassLikeLookupTag, FirSession firSession) {
        ClassId classId = coneClassLikeLookupTag.getClassId();
        FirFunctionTypeKindService functionTypeService = FirFunctionTypeKindServiceKt.getFunctionTypeService(firSession);
        FqName packageFqName = classId.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
        String strAsString = classId.getShortClassName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return functionTypeService.getKindByClassNamePrefix(packageFqName, strAsString);
    }

    public static final boolean isSomeFunctionType(ConeKotlinType coneKotlinType, FirSession session) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        return functionTypeKind(coneKotlinType, session) != null;
    }

    public static final boolean isSomeFunctionType(ConeClassLikeLookupTag coneClassLikeLookupTag, FirSession session) {
        Intrinsics.checkNotNullParameter(coneClassLikeLookupTag, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        return functionTypeKind(coneClassLikeLookupTag, session) != null;
    }

    public static final ConeClassLikeType customFunctionTypeToSimpleFunctionType(ConeKotlinType coneKotlinType, FirSession session) {
        FunctionTypeKind.Function function;
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FunctionTypeKind functionTypeKind = functionTypeKind(coneKotlinType, session);
        if (functionTypeKind == null || Intrinsics.areEqual(functionTypeKind, FunctionTypeKind.Function.INSTANCE) || Intrinsics.areEqual(functionTypeKind, FunctionTypeKind.KFunction.INSTANCE)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (functionTypeKind.getIsReflectType()) {
            function = FunctionTypeKind.KFunction.INSTANCE;
        } else {
            function = FunctionTypeKind.Function.INSTANCE;
        }
        return createFunctionTypeWithNewKind(coneKotlinType, session, function);
    }

    private static final ConeClassLikeType createFunctionTypeWithNewKind(ConeKotlinType coneKotlinType, FirSession firSession, FunctionTypeKind functionTypeKind) {
        ConeKotlinType coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(coneKotlinType, firSession);
        return TypeConstructionUtilsKt.constructClassType(TypeConstructionUtilsKt.toLookupTag(new ClassId(functionTypeKind.getPackageFqName(), functionTypeKind.numberedClassName(coneKotlinTypeFullyExpandedType.getTypeArguments().length - 1))), coneKotlinTypeFullyExpandedType.getTypeArguments(), false, coneKotlinTypeFullyExpandedType.getAttributes());
    }

    public static final ConeKotlinType findSubtypeOfBasicFunctionType(ConeKotlinType coneKotlinType, FirSession session, ConeClassLikeType expectedFunctionType) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(expectedFunctionType, "expectedFunctionType");
        if (!isFunctionOrKFunctionType(expectedFunctionType, session, true)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return findSubtypeOfBasicFunctionTypeImpl(coneKotlinType, session, expectedFunctionType);
    }

    private static final ConeKotlinType findSubtypeOfBasicFunctionTypeImpl(ConeKotlinType coneKotlinType, FirSession firSession, ConeClassLikeType coneClassLikeType) {
        Object obj = null;
        if (coneKotlinType instanceof ConeClassLikeType) {
            if (!isNotBasicFunctionType(coneKotlinType, firSession) && isSubtypeOfFunctionType(coneKotlinType, firSession, coneClassLikeType)) {
                obj = (ConeClassLikeType) coneKotlinType;
            }
            return (ConeKotlinType) obj;
        }
        boolean z = true;
        if (coneKotlinType instanceof ConeIntersectionType) {
            ConeIntersectionType coneIntersectionType = (ConeIntersectionType) coneKotlinType;
            Collection<ConeKotlinType> intersectedTypes = coneIntersectionType.getIntersectedTypes();
            if (!(intersectedTypes instanceof Collection) || !intersectedTypes.isEmpty()) {
                Iterator<T> it = intersectedTypes.iterator();
                do {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                } while (!isNotBasicFunctionType((ConeKotlinType) it.next(), firSession));
            } else {
                z = false;
                break;
            }
            if (z) {
                return null;
            }
            for (Object obj2 : coneIntersectionType.getIntersectedTypes()) {
                if (findSubtypeOfBasicFunctionTypeImpl((ConeKotlinType) obj2, firSession, coneClassLikeType) != null) {
                    obj = obj2;
                    break;
                }
            }
            return (ConeKotlinType) obj;
        }
        if (!(coneKotlinType instanceof ConeTypeParameterType)) {
            return null;
        }
        List<FirResolvedTypeRef> resolvedBounds = ((ConeTypeParameterType) coneKotlinType).getLookupTag().getTypeParameterSymbol().getResolvedBounds();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(resolvedBounds, 10));
        Iterator<T> it2 = resolvedBounds.iterator();
        while (it2.hasNext()) {
            arrayList.add(FirTypeUtilsKt.getConeType((FirResolvedTypeRef) it2.next()));
        }
        ArrayList arrayList2 = arrayList;
        if (!(arrayList2 instanceof Collection) || !arrayList2.isEmpty()) {
            Iterator it3 = arrayList2.iterator();
            do {
                if (!it3.hasNext()) {
                    z = false;
                    break;
                }
            } while (!isNotBasicFunctionType((ConeKotlinType) it3.next(), firSession));
        } else {
            z = false;
            break;
        }
        if (z) {
            return null;
        }
        for (Object obj3 : arrayList2) {
            if (findSubtypeOfBasicFunctionTypeImpl((ConeKotlinType) obj3, firSession, coneClassLikeType) != null) {
                obj = obj3;
                break;
            }
        }
        return (ConeKotlinType) obj;
    }

    private static final boolean isSubtypeOfFunctionType(ConeKotlinType coneKotlinType, FirSession firSession, ConeClassLikeType coneClassLikeType) {
        return AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, (TypeCheckerProviderContext) TypeComponentsKt.getTypeContext(firSession), (KotlinTypeMarker) coneKotlinType, (KotlinTypeMarker) ConeTypeUtilsKt.replaceArgumentsWithStarProjections(coneClassLikeType), false, 8, (Object) null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final FirNamedFunctionSymbol findBaseInvokeSymbol(ConeClassLikeType coneClassLikeType, FirSession session, ScopeSession scopeSession) {
        Intrinsics.checkNotNullParameter(coneClassLikeType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        if (!isSomeFunctionType(coneClassLikeType, session)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(coneClassLikeType.getLookupTag(), session);
        FirClassLikeDeclaration firClassLikeDeclaration = symbol != null ? (FirClassLikeDeclaration) symbol.getFir() : null;
        FirClass firClass = firClassLikeDeclaration instanceof FirClass ? (FirClass) firClassLikeDeclaration : null;
        if (firClass == null) {
            return null;
        }
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        FirKotlinScopeProviderKt.unsubstitutedScope(firClass, session, scopeSession, false, (FirResolvePhase) null).processFunctionsByName(OperatorNameConventions.INVOKE, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt.findBaseInvokeSymbol.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                invoke2(firNamedFunctionSymbol);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirNamedFunctionSymbol functionSymbol) {
                Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
                objectRef.element = functionSymbol;
            }
        });
        return (FirNamedFunctionSymbol) objectRef.element;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final FirFunctionSymbol<?> findContributedInvokeSymbol(ConeKotlinType coneKotlinType, FirSession session, ScopeSession scopeSession, ConeClassLikeType expectedFunctionType, boolean z) {
        FakeOverrideTypeCalculator.DoNothing doNothing;
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(expectedFunctionType, "expectedFunctionType");
        final FirNamedFunctionSymbol firNamedFunctionSymbolFindBaseInvokeSymbol = findBaseInvokeSymbol(expectedFunctionType, session, scopeSession);
        if (firNamedFunctionSymbolFindBaseInvokeSymbol == null) {
            return null;
        }
        if (z) {
            doNothing = FakeOverrideTypeCalculator.Forced.INSTANCE;
        } else {
            doNothing = FakeOverrideTypeCalculator.DoNothing.INSTANCE;
        }
        FirTypeScope firTypeScopeScope = ScopeUtilsKt.scope(coneKotlinType, session, scopeSession, doNothing, FirResolvePhase.STATUS);
        if (firTypeScopeScope == null) {
            return null;
        }
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        firTypeScopeScope.processFunctionsByName(OperatorNameConventions.INVOKE, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt.findContributedInvokeSymbol.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                invoke2(firNamedFunctionSymbol);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirNamedFunctionSymbol functionSymbol) {
                Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
                if (((FirSimpleFunction) functionSymbol.getFir()).getValueParameters().size() == ((FirSimpleFunction) firNamedFunctionSymbolFindBaseInvokeSymbol.getFir()).getValueParameters().size()) {
                    objectRef.element = functionSymbol;
                }
            }
        });
        final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
        if (objectRef.element != 0) {
            T t = objectRef.element;
            Intrinsics.checkNotNull(t);
            FirTypeScopeKt.processOverriddenFunctions(firTypeScopeScope, (FirNamedFunctionSymbol) t, new Function1<FirNamedFunctionSymbol, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt.findContributedInvokeSymbol.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                /* JADX WARN: Multi-variable type inference failed */
                @Override // kotlin.jvm.functions.Function1
                public final ProcessorAction invoke(FirNamedFunctionSymbol functionSymbol) {
                    Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
                    if (!Intrinsics.areEqual(functionSymbol, firNamedFunctionSymbolFindBaseInvokeSymbol)) {
                        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) functionSymbol.getFir();
                        FirCallableDeclaration originalForSubstitutionOverrideAttr = (ClassMembersKt.isSubstitutionOverride(firCallableDeclaration) || (firCallableDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) ? ClassMembersKt.getOriginalForSubstitutionOverrideAttr(firCallableDeclaration) : null;
                        if (!Intrinsics.areEqual((FirNamedFunctionSymbol) (originalForSubstitutionOverrideAttr != null ? originalForSubstitutionOverrideAttr.getSymbol() : null), firNamedFunctionSymbolFindBaseInvokeSymbol)) {
                            return ProcessorAction.NEXT;
                        }
                    }
                    objectRef2.element = functionSymbol;
                    return ProcessorAction.STOP;
                }
            });
        }
        if (objectRef2.element != 0) {
            return (FirFunctionSymbol) objectRef.element;
        }
        return null;
    }

    public static final List<ConeKotlinType> contextReceiversTypes(ConeKotlinType coneKotlinType, FirSession session) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        if (!isSomeFunctionType(coneKotlinType, session)) {
            return CollectionsKt.emptyList();
        }
        ConeKotlinType coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(coneKotlinType, session);
        List listTake = ArraysKt.take(coneKotlinTypeFullyExpandedType.getTypeArguments(), CompilerConeAttributesKt.getContextReceiversNumberForFunctionType(coneKotlinTypeFullyExpandedType));
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listTake, 10));
        Iterator it = listTake.iterator();
        while (it.hasNext()) {
            arrayList.add(typeOrDefault((ConeTypeProjection) it.next(), session.getBuiltinTypes().getNothingType().getType()));
        }
        return arrayList;
    }

    public static final ConeKotlinType receiverType(ConeKotlinType coneKotlinType, FirSession session) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        if (!isSomeFunctionType(coneKotlinType, session) || !TypeUtilsKt.isExtensionFunctionType(coneKotlinType, session)) {
            return null;
        }
        ConeKotlinType coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(coneKotlinType, session);
        return typeOrDefault(coneKotlinTypeFullyExpandedType.getTypeArguments()[CompilerConeAttributesKt.getContextReceiversNumberForFunctionType(coneKotlinTypeFullyExpandedType)], session.getBuiltinTypes().getNothingType().getType());
    }

    public static final ConeKotlinType returnType(ConeKotlinType coneKotlinType, FirSession session) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        if (!(coneKotlinType instanceof ConeClassLikeType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return typeOrDefault((ConeTypeProjection) ArraysKt.last(TypeExpansionUtilsKt.fullyExpandedType$default((ConeClassLikeType) coneKotlinType, session, null, 2, null).getTypeArguments()), session.getBuiltinTypes().getNullableAnyType().getType());
    }

    public static final List<ConeKotlinType> valueParameterTypesWithoutReceivers(ConeKotlinType coneKotlinType, FirSession session) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        if (!(coneKotlinType instanceof ConeClassLikeType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        ConeClassLikeType coneClassLikeTypeFullyExpandedType$default = TypeExpansionUtilsKt.fullyExpandedType$default((ConeClassLikeType) coneKotlinType, session, null, 2, null);
        ConeClassLikeType coneClassLikeType = coneClassLikeTypeFullyExpandedType$default;
        List listDropLast = CollectionsKt.dropLast(ArraysKt.drop(coneClassLikeTypeFullyExpandedType$default.getTypeArguments(), CompilerConeAttributesKt.getContextReceiversNumberForFunctionType(coneClassLikeType) + (CompilerConeAttributesKt.isExtensionFunctionType(coneClassLikeType) ? 1 : 0)), 1);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listDropLast, 10));
        Iterator it = listDropLast.iterator();
        while (it.hasNext()) {
            arrayList.add(typeOrDefault((ConeTypeProjection) it.next(), session.getBuiltinTypes().getNothingType().getType()));
        }
        return arrayList;
    }

    public static final List<ConeKotlinType> valueParameterTypesIncludingReceiver(ConeKotlinType coneKotlinType, FirSession session) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        if (!(coneKotlinType instanceof ConeClassLikeType)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        List listDropLast = ArraysKt.dropLast(TypeExpansionUtilsKt.fullyExpandedType$default((ConeClassLikeType) coneKotlinType, session, null, 2, null).getTypeArguments(), 1);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listDropLast, 10));
        Iterator it = listDropLast.iterator();
        while (it.hasNext()) {
            arrayList.add(typeOrDefault((ConeTypeProjection) it.next(), session.getBuiltinTypes().getNothingType().getType()));
        }
        return arrayList;
    }

    private static final ConeKotlinType typeOrDefault(ConeTypeProjection coneTypeProjection, ConeKotlinType coneKotlinType) {
        if (coneTypeProjection instanceof ConeKotlinTypeProjection) {
            return ((ConeKotlinTypeProjection) coneTypeProjection).getType();
        }
        if (coneTypeProjection instanceof ConeStarProjection) {
            return coneKotlinType;
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final FunctionTypeKind specialFunctionTypeKind(FirFunction firFunction, FirSession session) {
        Intrinsics.checkNotNullParameter(firFunction, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FirFunctionSymbol<? extends FirFunction> symbol = firFunction.getSymbol();
        FirNamedFunctionSymbol firNamedFunctionSymbol = symbol instanceof FirNamedFunctionSymbol ? (FirNamedFunctionSymbol) symbol : null;
        if (firNamedFunctionSymbol != null) {
            return FirFunctionTypeKindServiceKt.getFunctionTypeService(session).extractSingleSpecialKindForFunction(firNamedFunctionSymbol);
        }
        return null;
    }

    public static final boolean isBasicFunctionType(ConeKotlinType coneKotlinType, FirSession session) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FunctionTypeKind functionTypeKind = functionTypeKind(coneKotlinType, session);
        if (functionTypeKind == null) {
            return false;
        }
        return Intrinsics.areEqual(functionTypeKind, FunctionTypeKind.Function.INSTANCE);
    }

    public static final boolean isSuspendOrKSuspendFunctionType(ConeKotlinType coneKotlinType, FirSession session) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FunctionTypeKind functionTypeKind = functionTypeKind(coneKotlinType, session);
        if (functionTypeKind == null) {
            return false;
        }
        return Intrinsics.areEqual(functionTypeKind, FunctionTypeKind.SuspendFunction.INSTANCE) || Intrinsics.areEqual(functionTypeKind, FunctionTypeKind.KSuspendFunction.INSTANCE);
    }

    public static final boolean isReflectFunctionType(ConeKotlinType coneKotlinType, FirSession session) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FunctionTypeKind functionTypeKind = functionTypeKind(coneKotlinType, session);
        if (functionTypeKind == null) {
            return false;
        }
        return functionTypeKind.getIsReflectType();
    }

    public static final boolean isNonReflectFunctionType(ConeKotlinType coneKotlinType, FirSession session) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FunctionTypeKind functionTypeKind = functionTypeKind(coneKotlinType, session);
        if (functionTypeKind == null) {
            return false;
        }
        return !functionTypeKind.getIsReflectType();
    }

    public static final boolean isNotBasicFunctionType(ConeKotlinType coneKotlinType, FirSession session) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        FunctionTypeKind functionTypeKind = functionTypeKind(coneKotlinType, session);
        if (functionTypeKind == null) {
            return false;
        }
        return !FunctionTypeKindKt.isBasicFunctionOrKFunction(functionTypeKind);
    }

    private static final boolean isFunctionOrKFunctionType(ConeKotlinType coneKotlinType, FirSession firSession, boolean z) {
        FunctionTypeKind functionTypeKind = functionTypeKind(coneKotlinType, firSession);
        if (functionTypeKind != null) {
            return FunctionTypeKindKt.isBasicFunctionOrKFunction(functionTypeKind);
        }
        if (!z) {
            return false;
        }
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments(coneKotlinType.getClass() + " is not a function type", null);
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        FirExceptionUtilsKt.withConeTypeEntry(exceptionAttachmentBuilder, ModuleXmlParser.TYPE, coneKotlinType);
        kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
        throw kotlinIllegalArgumentExceptionWithAttachments;
    }
}
