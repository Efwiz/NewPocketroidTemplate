package org.jetbrains.kotlin.fir.resolve.transformers;

import androidx.core.app.NotificationCompat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.EffectiveVisibilityUtilsKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirVisibilityCheckerKt;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirOuterClassTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.impl.FirDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.declarations.impl.FirResolvedDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.extensions.FirExtensionServiceKt;
import org.jetbrains.kotlin.fir.extensions.FirStatusTransformerExtension;
import org.jetbrains.kotlin.fir.extensions.FirStatusTransformerExtensionKt;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.ProcessorAction;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjectionIn;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjectionOut;
import org.jetbrains.kotlin.fir.types.ConeStarProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.utils.exceptions.FirExceptionUtilsKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder;
import org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments;

/* JADX INFO: compiled from: FirStatusResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000®\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 ?2\u00020\u0001:\u0001?B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u001e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\b2\u0006\u0010\u0016\u001a\u00020\u00152\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018J\u001e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\b2\u0006\u0010\u001b\u001a\u00020\u001a2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018J\u001a\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u001e2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0002J\u001a\u0010\u001f\u001a\u00020 2\u0006\u0010\u001d\u001a\u00020\u001e2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0002J\"\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010%\u001a\u00020\u000fH\u0002J \u0010!\u001a\u00020\"2\u0006\u0010&\u001a\u00020\u00182\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010%\u001a\u00020\u000fJ \u0010!\u001a\u00020\"2\u0006\u0010'\u001a\u00020(2\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010%\u001a\u00020\u000fJ*\u0010!\u001a\u00020\"2\u0006\u0010\u001d\u001a\u00020\u001e2\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\b\u0010)\u001a\u0004\u0018\u00010\u001a2\u0006\u0010%\u001a\u00020\u000fJB\u0010!\u001a\u00020\"2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010*\u001a\u00020+2\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\b\u0010)\u001a\u0004\u0018\u00010\u001a2\u0006\u0010%\u001a\u00020\u000f2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020\"0\bH\u0002J \u0010!\u001a\u00020\"2\u0006\u0010-\u001a\u00020.2\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010%\u001a\u00020\u000fJ \u0010!\u001a\u00020\"2\u0006\u0010/\u001a\u0002002\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010%\u001a\u00020\u000fJ2\u0010!\u001a\u00020\"2\u0006\u0010\u001b\u001a\u00020\u001a2\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010%\u001a\u00020\u000f2\u0010\b\u0002\u0010,\u001a\n\u0012\u0004\u0012\u00020\"\u0018\u00010\bJ:\u0010!\u001a\u00020\"2\u0006\u00101\u001a\u0002022\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\b\u0010)\u001a\u0004\u0018\u00010\u001a2\u0006\u0010%\u001a\u00020\u000f2\u000e\b\u0002\u0010,\u001a\b\u0012\u0004\u0012\u00020\"0\bJ2\u0010!\u001a\u00020\"2\u0006\u0010\u0016\u001a\u00020\u00152\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010%\u001a\u00020\u000f2\u0010\b\u0002\u0010,\u001a\n\u0012\u0004\u0012\u00020\"\u0018\u00010\bJ \u0010!\u001a\u00020\"2\u0006\u00103\u001a\u0002042\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010%\u001a\u00020\u000fJ2\u00105\u001a\u0002062\u0006\u0010\u001d\u001a\u00020\u001e2\b\u0010\u0017\u001a\u0004\u0018\u00010\u00182\b\u0010)\u001a\u0004\u0018\u00010\u001a2\f\u0010,\u001a\b\u0012\u0004\u0012\u0002070\bH\u0002J,\u00108\u001a\u00020+*\u0002092\u001d\u0010:\u001a\u0019\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020+\u0012\u0004\u0012\u00020+0;¢\u0006\u0002\b<H\u0082\bJ\u0014\u0010\u000e\u001a\u00020\u000f*\u00020=2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\f\u0010>\u001a\u00020\u000f*\u00020\u0018H\u0002R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006@"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/FirStatusResolver;", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;)V", "extensionStatusTransformers", "", "Lorg/jetbrains/kotlin/fir/extensions/FirStatusTransformerExtension;", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "contradictsWith", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "requiredVariance", "Lorg/jetbrains/kotlin/types/Variance;", "getOverriddenFunctions", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "function", "containingClass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "getOverriddenProperties", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "property", "isPrivateToThis", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "resolveModality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "resolveStatus", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvedDeclarationStatus;", "backingField", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "isLocal", "firClass", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "containingProperty", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "overriddenStatuses", "enumEntry", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "field", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "propertyAccessor", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "typeAlias", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "resolveVisibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "Lorg/jetbrains/kotlin/fir/declarations/impl/FirResolvedDeclarationStatusImpl;", "applyExtensionTransformers", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "operation", "Lkotlin/Function2;", "Lkotlin/ExtensionFunctionType;", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "hasPrivateConstructor", "Companion", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirStatusResolver {
    private static final List<FirDeclarationStatusImpl.Modifier> MODIFIERS_FROM_OVERRIDDEN;
    private static final List<FirDeclarationStatusImpl.Modifier> NOT_INHERITED_MODIFIERS;
    private final List<FirStatusTransformerExtension> extensionStatusTransformers;
    private final ScopeSession scopeSession;
    private final FirSession session;

    public FirStatusResolver(FirSession session, ScopeSession scopeSession) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        this.session = session;
        this.scopeSession = scopeSession;
        this.extensionStatusTransformers = FirStatusTransformerExtensionKt.getStatusTransformerExtensions(FirExtensionServiceKt.getExtensionService(session));
    }

    public final FirSession getSession() {
        return this.session;
    }

    public final ScopeSession getScopeSession() {
        return this.scopeSession;
    }

    static {
        List<FirDeclarationStatusImpl.Modifier> listListOf = CollectionsKt.listOf((Object[]) new FirDeclarationStatusImpl.Modifier[]{FirDeclarationStatusImpl.Modifier.ACTUAL, FirDeclarationStatusImpl.Modifier.EXPECT, FirDeclarationStatusImpl.Modifier.CONST, FirDeclarationStatusImpl.Modifier.LATEINIT, FirDeclarationStatusImpl.Modifier.TAILREC, FirDeclarationStatusImpl.Modifier.EXTERNAL});
        NOT_INHERITED_MODIFIERS = listListOf;
        MODIFIERS_FROM_OVERRIDDEN = CollectionsKt.minus((Iterable) ArraysKt.toList(FirDeclarationStatusImpl.Modifier.values()), (Iterable) listListOf);
    }

    public final FirResolvedDeclarationStatus resolveStatus(FirDeclaration declaration, FirClass containingClass, FirProperty containingProperty, boolean isLocal) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (declaration instanceof FirProperty) {
            return resolveStatus$default(this, (FirProperty) declaration, containingClass, isLocal, (List) null, 8, (Object) null);
        }
        if (declaration instanceof FirSimpleFunction) {
            return resolveStatus$default(this, (FirSimpleFunction) declaration, containingClass, isLocal, (List) null, 8, (Object) null);
        }
        if (declaration instanceof FirPropertyAccessor) {
            return resolveStatus$default(this, (FirPropertyAccessor) declaration, containingClass, containingProperty, isLocal, null, 16, null);
        }
        if (declaration instanceof FirRegularClass) {
            return resolveStatus((FirClass) declaration, containingClass, isLocal);
        }
        if (declaration instanceof FirTypeAlias) {
            return resolveStatus((FirTypeAlias) declaration, containingClass, isLocal);
        }
        if (declaration instanceof FirConstructor) {
            return resolveStatus((FirConstructor) declaration, containingClass, isLocal);
        }
        if (declaration instanceof FirField) {
            return resolveStatus((FirField) declaration, containingClass, isLocal);
        }
        if (declaration instanceof FirBackingField) {
            return resolveStatus((FirBackingField) declaration, containingClass, isLocal);
        }
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Unsupported declaration type: " + declaration.getClass(), null);
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, "declaration", declaration);
        kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
        throw kotlinIllegalArgumentExceptionWithAttachments;
    }

    public final List<FirProperty> getOverriddenProperties(final FirProperty property, FirClass containingClass) {
        Intrinsics.checkNotNullParameter(property, "property");
        if (containingClass == null) {
            return CollectionsKt.emptyList();
        }
        FirTypeScope firTypeScopeUnsubstitutedScope = FirKotlinScopeProviderKt.unsubstitutedScope(containingClass, this.session, this.scopeSession, false, (FirResolvePhase) null);
        final List listCreateListBuilder = CollectionsKt.createListBuilder();
        firTypeScopeUnsubstitutedScope.processPropertiesByName(property.getName(), new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.FirStatusResolver$getOverriddenProperties$1$1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirVariableSymbol<?> it) {
                Intrinsics.checkNotNullParameter(it, "it");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                invoke2(firVariableSymbol);
                return Unit.INSTANCE;
            }
        });
        firTypeScopeUnsubstitutedScope.processDirectOverriddenPropertiesWithBaseScope(property.getSymbol(), new Function2<FirPropertySymbol, FirTypeScope, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.FirStatusResolver$getOverriddenProperties$1$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function2
            public final ProcessorAction invoke(FirPropertySymbol overriddenSymbol, FirTypeScope firTypeScope) {
                Intrinsics.checkNotNullParameter(overriddenSymbol, "overriddenSymbol");
                Intrinsics.checkNotNullParameter(firTypeScope, "<anonymous parameter 1>");
                if (FirVisibilityCheckerKt.getVisibilityChecker(this.this$0.getSession()).isVisibleForOverriding(property, (FirMemberDeclaration) overriddenSymbol.getFir())) {
                    listCreateListBuilder.add(overriddenSymbol.getFir());
                }
                return ProcessorAction.NEXT;
            }
        });
        return CollectionsKt.build(listCreateListBuilder);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ FirResolvedDeclarationStatus resolveStatus$default(FirStatusResolver firStatusResolver, FirProperty firProperty, FirClass firClass, boolean z, List list, int i, Object obj) {
        if ((i & 8) != 0) {
            list = null;
        }
        return firStatusResolver.resolveStatus(firProperty, firClass, z, (List<? extends FirResolvedDeclarationStatus>) list);
    }

    public final FirResolvedDeclarationStatus resolveStatus(FirProperty property, FirClass containingClass, boolean isLocal, List<? extends FirResolvedDeclarationStatus> overriddenStatuses) {
        FirDeclarationStatus status;
        Intrinsics.checkNotNullParameter(property, "property");
        if (overriddenStatuses == null) {
            List<FirProperty> overriddenProperties = getOverriddenProperties(property, containingClass);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(overriddenProperties, 10));
            Iterator<T> it = overriddenProperties.iterator();
            while (it.hasNext()) {
                FirDeclarationStatus status2 = ((FirProperty) it.next()).getStatus();
                Intrinsics.checkNotNull(status2, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus");
                arrayList.add((FirResolvedDeclarationStatus) status2);
            }
            overriddenStatuses = arrayList;
        }
        List<? extends FirResolvedDeclarationStatus> list = overriddenStatuses;
        FirProperty firProperty = property;
        if (this.extensionStatusTransformers.isEmpty()) {
            status = firProperty.getStatus();
        } else {
            List<FirStatusTransformerExtension> list2 = this.extensionStatusTransformers;
            FirDeclarationStatus status3 = firProperty.getStatus();
            for (FirStatusTransformerExtension firStatusTransformerExtension : list2) {
                if (firStatusTransformerExtension.needTransformStatus(firProperty)) {
                    status3 = firStatusTransformerExtension.transformStatus(status3, property, containingClass != null ? containingClass.getSymbol() : null, isLocal);
                }
            }
            status = status3;
        }
        return resolveStatus(property, status, containingClass, null, isLocal, list);
    }

    public final List<FirSimpleFunction> getOverriddenFunctions(final FirSimpleFunction function, FirClass containingClass) {
        Intrinsics.checkNotNullParameter(function, "function");
        if (containingClass == null) {
            return CollectionsKt.emptyList();
        }
        final List listCreateListBuilder = CollectionsKt.createListBuilder();
        FirTypeScope firTypeScopeUnsubstitutedScope = FirKotlinScopeProviderKt.unsubstitutedScope(containingClass, this.session, this.scopeSession, false, (FirResolvePhase) null);
        FirNamedFunctionSymbol symbol = function.getSymbol();
        firTypeScopeUnsubstitutedScope.processFunctionsByName(function.getName(), new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.FirStatusResolver$getOverriddenFunctions$1$1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirNamedFunctionSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                invoke2(firNamedFunctionSymbol);
                return Unit.INSTANCE;
            }
        });
        firTypeScopeUnsubstitutedScope.processDirectOverriddenFunctionsWithBaseScope(symbol, new Function2<FirNamedFunctionSymbol, FirTypeScope, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.FirStatusResolver$getOverriddenFunctions$1$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function2
            public final ProcessorAction invoke(FirNamedFunctionSymbol overriddenSymbol, FirTypeScope firTypeScope) {
                Intrinsics.checkNotNullParameter(overriddenSymbol, "overriddenSymbol");
                Intrinsics.checkNotNullParameter(firTypeScope, "<anonymous parameter 1>");
                if (FirVisibilityCheckerKt.getVisibilityChecker(this.this$0.getSession()).isVisibleForOverriding(function, (FirMemberDeclaration) overriddenSymbol.getFir())) {
                    listCreateListBuilder.add(overriddenSymbol.getFir());
                }
                return ProcessorAction.NEXT;
            }
        });
        return CollectionsKt.build(listCreateListBuilder);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ FirResolvedDeclarationStatus resolveStatus$default(FirStatusResolver firStatusResolver, FirSimpleFunction firSimpleFunction, FirClass firClass, boolean z, List list, int i, Object obj) {
        if ((i & 8) != 0) {
            list = null;
        }
        return firStatusResolver.resolveStatus(firSimpleFunction, firClass, z, (List<? extends FirResolvedDeclarationStatus>) list);
    }

    public final FirResolvedDeclarationStatus resolveStatus(FirSimpleFunction function, FirClass containingClass, boolean isLocal, List<? extends FirResolvedDeclarationStatus> overriddenStatuses) {
        FirDeclarationStatus status;
        Intrinsics.checkNotNullParameter(function, "function");
        FirSimpleFunction firSimpleFunction = function;
        if (this.extensionStatusTransformers.isEmpty()) {
            status = firSimpleFunction.getStatus();
        } else {
            List<FirStatusTransformerExtension> list = this.extensionStatusTransformers;
            FirDeclarationStatus status2 = firSimpleFunction.getStatus();
            for (FirStatusTransformerExtension firStatusTransformerExtension : list) {
                if (firStatusTransformerExtension.needTransformStatus(firSimpleFunction)) {
                    status2 = firStatusTransformerExtension.transformStatus(status2, function, containingClass != null ? containingClass.getSymbol() : null, isLocal);
                }
            }
            status = status2;
        }
        if (overriddenStatuses == null) {
            List<FirSimpleFunction> overriddenFunctions = getOverriddenFunctions(function, containingClass);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(overriddenFunctions, 10));
            Iterator<T> it = overriddenFunctions.iterator();
            while (it.hasNext()) {
                FirDeclarationStatus status3 = ((FirSimpleFunction) it.next()).getStatus();
                Intrinsics.checkNotNull(status3, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus");
                arrayList.add((FirResolvedDeclarationStatus) status3);
            }
            overriddenStatuses = arrayList;
        }
        return resolveStatus(function, status, containingClass, null, isLocal, overriddenStatuses);
    }

    public final FirResolvedDeclarationStatus resolveStatus(FirClass firClass, FirClass containingClass, boolean isLocal) {
        FirDeclarationStatus status;
        FirDeclarationStatus firDeclarationStatus;
        Intrinsics.checkNotNullParameter(firClass, "firClass");
        if (!(firClass instanceof FirRegularClass)) {
            status = firClass.getStatus();
        } else {
            FirClass firClass2 = firClass;
            if (this.extensionStatusTransformers.isEmpty()) {
                status = firClass2.getStatus();
            } else {
                List<FirStatusTransformerExtension> list = this.extensionStatusTransformers;
                FirDeclarationStatus status2 = firClass2.getStatus();
                for (FirStatusTransformerExtension firStatusTransformerExtension : list) {
                    if (firStatusTransformerExtension.needTransformStatus(firClass2)) {
                        status2 = firStatusTransformerExtension.transformStatus(status2, (FirRegularClass) firClass, containingClass != null ? containingClass.getSymbol() : null, isLocal);
                    }
                }
                firDeclarationStatus = status2;
            }
            return resolveStatus(firClass, firDeclarationStatus, containingClass, null, isLocal, CollectionsKt.emptyList());
        }
        firDeclarationStatus = status;
        return resolveStatus(firClass, firDeclarationStatus, containingClass, null, isLocal, CollectionsKt.emptyList());
    }

    public final FirResolvedDeclarationStatus resolveStatus(FirTypeAlias typeAlias, FirClass containingClass, boolean isLocal) {
        FirDeclarationStatus status;
        Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        FirTypeAlias firTypeAlias = typeAlias;
        if (this.extensionStatusTransformers.isEmpty()) {
            status = firTypeAlias.getStatus();
        } else {
            List<FirStatusTransformerExtension> list = this.extensionStatusTransformers;
            FirDeclarationStatus status2 = firTypeAlias.getStatus();
            for (FirStatusTransformerExtension firStatusTransformerExtension : list) {
                if (firStatusTransformerExtension.needTransformStatus(firTypeAlias)) {
                    status2 = firStatusTransformerExtension.transformStatus(status2, typeAlias, containingClass != null ? containingClass.getSymbol() : null, isLocal);
                }
            }
            status = status2;
        }
        return resolveStatus(typeAlias, status, containingClass, null, isLocal, CollectionsKt.emptyList());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ FirResolvedDeclarationStatus resolveStatus$default(FirStatusResolver firStatusResolver, FirPropertyAccessor firPropertyAccessor, FirClass firClass, FirProperty firProperty, boolean z, List list, int i, Object obj) {
        if ((i & 16) != 0) {
            list = CollectionsKt.emptyList();
        }
        return firStatusResolver.resolveStatus(firPropertyAccessor, firClass, firProperty, z, list);
    }

    public final FirResolvedDeclarationStatus resolveStatus(FirPropertyAccessor propertyAccessor, FirClass containingClass, FirProperty containingProperty, boolean isLocal, List<? extends FirResolvedDeclarationStatus> overriddenStatuses) {
        FirDeclarationStatus status;
        Intrinsics.checkNotNullParameter(propertyAccessor, "propertyAccessor");
        Intrinsics.checkNotNullParameter(overriddenStatuses, "overriddenStatuses");
        FirPropertyAccessor firPropertyAccessor = propertyAccessor;
        if (this.extensionStatusTransformers.isEmpty()) {
            status = firPropertyAccessor.getStatus();
        } else {
            List<FirStatusTransformerExtension> list = this.extensionStatusTransformers;
            FirDeclarationStatus status2 = firPropertyAccessor.getStatus();
            for (FirStatusTransformerExtension firStatusTransformerExtension : list) {
                if (firStatusTransformerExtension.needTransformStatus(firPropertyAccessor)) {
                    status2 = firStatusTransformerExtension.transformStatus(status2, propertyAccessor, containingClass != null ? containingClass.getSymbol() : null, containingProperty, isLocal);
                }
            }
            status = status2;
        }
        return resolveStatus(propertyAccessor, status, containingClass, containingProperty, isLocal, overriddenStatuses);
    }

    public final FirResolvedDeclarationStatus resolveStatus(FirConstructor constructor, FirClass containingClass, boolean isLocal) {
        FirDeclarationStatus status;
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        FirConstructor firConstructor = constructor;
        if (this.extensionStatusTransformers.isEmpty()) {
            status = firConstructor.getStatus();
        } else {
            List<FirStatusTransformerExtension> list = this.extensionStatusTransformers;
            FirDeclarationStatus status2 = firConstructor.getStatus();
            for (FirStatusTransformerExtension firStatusTransformerExtension : list) {
                if (firStatusTransformerExtension.needTransformStatus(firConstructor)) {
                    status2 = firStatusTransformerExtension.transformStatus(status2, constructor, containingClass != null ? containingClass.getSymbol() : null, isLocal);
                }
            }
            status = status2;
        }
        return resolveStatus(constructor, status, containingClass, null, isLocal, CollectionsKt.emptyList());
    }

    public final FirResolvedDeclarationStatus resolveStatus(FirField field, FirClass containingClass, boolean isLocal) {
        Intrinsics.checkNotNullParameter(field, "field");
        return resolveStatus(field, field.getStatus(), containingClass, null, isLocal, CollectionsKt.emptyList());
    }

    private final FirResolvedDeclarationStatus resolveStatus(FirBackingField backingField, FirClass containingClass, boolean isLocal) {
        FirDeclarationStatus status;
        FirBackingField firBackingField = backingField;
        if (this.extensionStatusTransformers.isEmpty()) {
            status = firBackingField.getStatus();
        } else {
            List<FirStatusTransformerExtension> list = this.extensionStatusTransformers;
            FirDeclarationStatus status2 = firBackingField.getStatus();
            for (FirStatusTransformerExtension firStatusTransformerExtension : list) {
                if (firStatusTransformerExtension.needTransformStatus(firBackingField)) {
                    status2 = firStatusTransformerExtension.transformStatus(status2, backingField, containingClass != null ? containingClass.getSymbol() : null, isLocal);
                }
            }
            status = status2;
        }
        return resolveStatus(backingField, status, containingClass, null, isLocal, CollectionsKt.emptyList());
    }

    public final FirResolvedDeclarationStatus resolveStatus(FirEnumEntry enumEntry, FirClass containingClass, boolean isLocal) {
        FirDeclarationStatus status;
        Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
        FirEnumEntry firEnumEntry = enumEntry;
        if (this.extensionStatusTransformers.isEmpty()) {
            status = firEnumEntry.getStatus();
        } else {
            List<FirStatusTransformerExtension> list = this.extensionStatusTransformers;
            FirDeclarationStatus status2 = firEnumEntry.getStatus();
            for (FirStatusTransformerExtension firStatusTransformerExtension : list) {
                if (firStatusTransformerExtension.needTransformStatus(firEnumEntry)) {
                    status2 = firStatusTransformerExtension.transformStatus(status2, enumEntry, containingClass != null ? containingClass.getSymbol() : null, isLocal);
                }
            }
            status = status2;
        }
        return resolveStatus(enumEntry, status, containingClass, null, isLocal, CollectionsKt.emptyList());
    }

    /* JADX WARN: Code duplicated, block: B:44:0x009f  */
    /* JADX WARN: Code duplicated, block: B:62:0x00eb  */
    private final FirResolvedDeclarationStatus resolveStatus(FirDeclaration declaration, FirDeclarationStatus status, FirClass containingClass, FirProperty containingProperty, boolean isLocal, List<? extends FirResolvedDeclarationStatus> overriddenStatuses) {
        Visibilities.Private visibility;
        EffectiveVisibility.Public effectiveVisibility;
        EffectiveVisibility publishedApiEffectiveVisibility;
        FirClassSymbol<? extends FirClass> symbol;
        boolean z;
        boolean z2;
        if (status instanceof FirResolvedDeclarationStatus) {
            return (FirResolvedDeclarationStatus) status;
        }
        if (!(status instanceof FirDeclarationStatusImpl)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        Intrinsics.checkNotNull(overriddenStatuses, "null cannot be cast to non-null type kotlin.collections.List<org.jetbrains.kotlin.fir.declarations.impl.FirResolvedDeclarationStatusImpl>");
        Visibility visibility2 = status.getVisibility();
        if (Intrinsics.areEqual(visibility2, Visibilities.Unknown.INSTANCE)) {
            if (isLocal) {
                visibility = Visibilities.Local.INSTANCE;
            } else {
                visibility = resolveVisibility(declaration, containingClass, containingProperty, overriddenStatuses);
            }
        } else if (Intrinsics.areEqual(visibility2, Visibilities.Private.INSTANCE)) {
            if (declaration instanceof FirPropertyAccessor) {
                if (Intrinsics.areEqual(containingProperty != null ? containingProperty.getStatus().getVisibility() : null, Visibilities.PrivateToThis.INSTANCE)) {
                    visibility = Visibilities.PrivateToThis.INSTANCE;
                } else {
                    visibility = Visibilities.Private.INSTANCE;
                }
            } else {
                visibility = isPrivateToThis(declaration, containingClass) ? Visibilities.PrivateToThis.INSTANCE : Visibilities.Private.INSTANCE;
            }
        } else {
            visibility = status.getVisibility();
        }
        Modality modality = status.getModality();
        if (modality != null) {
            if (modality == Modality.OPEN) {
                if ((containingClass != null ? containingClass.getClassKind() : null) == ClassKind.INTERFACE && !FirStatusResolverKt.hasOwnBodyOrAccessorBody(declaration) && !((FirDeclarationStatusImpl) status).isExpect()) {
                    modality = Modality.ABSTRACT;
                }
            }
            if (modality == null) {
                modality = resolveModality(declaration, containingClass);
            }
        } else {
            modality = resolveModality(declaration, containingClass);
        }
        if (!overriddenStatuses.isEmpty()) {
            for (FirDeclarationStatusImpl.Modifier modifier : MODIFIERS_FROM_OVERRIDDEN) {
                FirDeclarationStatusImpl firDeclarationStatusImpl = (FirDeclarationStatusImpl) status;
                if (firDeclarationStatusImpl.get(modifier)) {
                    z = true;
                } else {
                    Iterator<T> it = overriddenStatuses.iterator();
                    z = false;
                    while (true) {
                        z2 = false;
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl = (FirResolvedDeclarationStatusImpl) it.next();
                            if (z2 || firResolvedDeclarationStatusImpl.get(modifier)) {
                                z2 = true;
                            }
                        }
                    }
                    if (z2) {
                        z = true;
                    }
                }
                firDeclarationStatusImpl.set(modifier, z);
            }
        }
        if (containingProperty == null) {
            if (!(containingClass instanceof FirRegularClass)) {
                effectiveVisibility = containingClass instanceof FirAnonymousObject ? EffectiveVisibility.Local.INSTANCE : EffectiveVisibility.Public.INSTANCE;
            } else {
                FirDeclarationStatus status2 = containingClass.getStatus();
                FirResolvedDeclarationStatus firResolvedDeclarationStatus = status2 instanceof FirResolvedDeclarationStatus ? (FirResolvedDeclarationStatus) status2 : null;
                if (firResolvedDeclarationStatus == null || (effectiveVisibility = firResolvedDeclarationStatus.getEffectiveVisibility()) == null) {
                    effectiveVisibility = EffectiveVisibility.Local.INSTANCE;
                }
            }
        } else {
            FirDeclarationStatus status3 = containingProperty.getStatus();
            FirResolvedDeclarationStatus firResolvedDeclarationStatus2 = status3 instanceof FirResolvedDeclarationStatus ? (FirResolvedDeclarationStatus) status3 : null;
            if (firResolvedDeclarationStatus2 == null || (effectiveVisibility = firResolvedDeclarationStatus2.getEffectiveVisibility()) == null) {
                effectiveVisibility = EffectiveVisibility.Local.INSTANCE;
            }
        }
        ConeClassLikeLookupTag lookupTag = (containingClass == null || (symbol = containingClass.getSymbol()) == null) ? null : symbol.getLookupTag();
        boolean z3 = declaration instanceof FirClass;
        EffectiveVisibility effectiveVisibility$default = EffectiveVisibilityUtilsKt.toEffectiveVisibility$default(visibility, lookupTag, z3, false, 4, (Object) null);
        EffectiveVisibility effectiveVisibilityLowerBound = effectiveVisibility.lowerBound(effectiveVisibility$default, TypeComponentsKt.getTypeContext(this.session));
        List<FirAnnotation> annotations = (containingProperty != null ? containingProperty : declaration).getAnnotations();
        if (containingProperty != null) {
            publishedApiEffectiveVisibility = PublishedApiEffectiveVisibilityKt.getPublishedApiEffectiveVisibility(containingProperty);
        } else {
            publishedApiEffectiveVisibility = containingClass instanceof FirRegularClass ? PublishedApiEffectiveVisibilityKt.getPublishedApiEffectiveVisibility(containingClass) : null;
        }
        EffectiveVisibility effectiveVisibilityComputePublishedApiEffectiveVisibility = PublishedApiEffectiveVisibilityKt.computePublishedApiEffectiveVisibility(annotations, visibility, effectiveVisibility$default, containingClass != null ? containingClass.getSymbol() : null, effectiveVisibility, publishedApiEffectiveVisibility, z3, this.session);
        if (effectiveVisibilityComputePublishedApiEffectiveVisibility != null) {
            PublishedApiEffectiveVisibilityKt.setNonLazyPublishedApiEffectiveVisibility(declaration, effectiveVisibilityComputePublishedApiEffectiveVisibility);
        }
        if ((containingClass instanceof FirRegularClass) && containingClass.getStatus().isExpect()) {
            ((FirDeclarationStatusImpl) status).setExpect(true);
        }
        return ((FirDeclarationStatusImpl) status).resolved(visibility, modality, effectiveVisibilityLowerBound);
    }

    private final boolean isPrivateToThis(FirDeclaration declaration, FirClass containingClass) {
        FirTypeRef typeRef;
        if (containingClass == null || !(declaration instanceof FirCallableDeclaration) || (declaration instanceof FirConstructor)) {
            return false;
        }
        List<FirTypeParameterRef> typeParameters = containingClass.getTypeParameters();
        if (!(typeParameters instanceof Collection) || !typeParameters.isEmpty()) {
            Iterator<T> it = typeParameters.iterator();
            while (it.hasNext()) {
                if (((FirTypeParameterRef) it.next()).getSymbol().getVariance() != Variance.INVARIANT) {
                    FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) declaration;
                    FirReceiverParameter receiverParameter = firCallableDeclaration.getReceiverParameter();
                    if (receiverParameter != null && (typeRef = receiverParameter.getTypeRef()) != null && contradictsWith(typeRef, Variance.IN_VARIANCE)) {
                        return true;
                    }
                    if (!contradictsWith(firCallableDeclaration.getReturnTypeRef(), ((declaration instanceof FirProperty) && ((FirProperty) declaration).getIsVar()) ? Variance.INVARIANT : Variance.OUT_VARIANCE)) {
                        if (!(declaration instanceof FirFunction)) {
                            break;
                        }
                        Iterator<FirValueParameter> it2 = ((FirFunction) declaration).getValueParameters().iterator();
                        while (it2.hasNext()) {
                            if (contradictsWith(it2.next().getReturnTypeRef(), Variance.IN_VARIANCE)) {
                                return true;
                            }
                        }
                        break;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final boolean contradictsWith(ConeKotlinType type, Variance requiredVariance) {
        Pair pair;
        List<FirTypeParameterRef> typeParameters;
        if (type instanceof ConeTypeParameterType) {
            return !((FirTypeParameter) ((ConeTypeParameterType) type).getLookupTag().getTypeParameterSymbol().getFir()).getVariance().allowsPosition(requiredVariance);
        }
        if (type instanceof ConeClassLikeType) {
            FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(((ConeClassLikeType) type).getLookupTag(), this.session);
            FirClassLikeDeclaration firClassLikeDeclaration = symbol != null ? (FirClassLikeDeclaration) symbol.getFir() : null;
            ConeTypeProjection[] typeArguments = type.getTypeArguments();
            int length = typeArguments.length;
            for (int i = 0; i < length; i++) {
                ConeTypeProjection coneTypeProjection = typeArguments[i];
                if (!(((firClassLikeDeclaration == null || (typeParameters = firClassLikeDeclaration.getTypeParameters()) == null) ? null : (FirTypeParameterRef) CollectionsKt.getOrNull(typeParameters, i)) instanceof FirOuterClassTypeParameterRef)) {
                    if (coneTypeProjection instanceof ConeKotlinTypeProjectionOut) {
                        pair = TuplesKt.to(((ConeKotlinTypeProjectionOut) coneTypeProjection).getType(), requiredVariance);
                    } else if (coneTypeProjection instanceof ConeKotlinTypeProjectionIn) {
                        pair = TuplesKt.to(((ConeKotlinTypeProjectionIn) coneTypeProjection).getType(), requiredVariance.opposite());
                    } else if (coneTypeProjection instanceof ConeKotlinTypeProjection) {
                        pair = TuplesKt.to(((ConeKotlinTypeProjection) coneTypeProjection).getType(), Variance.INVARIANT);
                    } else if (!(coneTypeProjection instanceof ConeStarProjection)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    if (contradictsWith((ConeKotlinType) pair.component1(), (Variance) pair.component2())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private final Visibility resolveVisibility(FirDeclaration declaration, FirClass containingClass, FirProperty containingProperty, List<FirResolvedDeclarationStatusImpl> overriddenStatuses) {
        Visibilities.Public visibility;
        Visibility visibilityNormalize;
        if ((declaration instanceof FirConstructor) && containingClass != null && hasPrivateConstructor(containingClass)) {
            return Visibilities.Private.INSTANCE;
        }
        if (!(declaration instanceof FirPropertyAccessor) || containingProperty == null) {
            visibility = Visibilities.Public.INSTANCE;
        } else {
            visibility = containingProperty.getStatus().getVisibility();
        }
        List<FirResolvedDeclarationStatusImpl> list = overriddenStatuses;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((FirResolvedDeclarationStatusImpl) it.next()).getVisibility());
        }
        final AnonymousClass2 anonymousClass2 = new Function2<Visibility, Visibility, Integer>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.FirStatusResolver.resolveVisibility.2
            @Override // kotlin.jvm.functions.Function2
            public final Integer invoke(Visibility visibility2, Visibility visibility3) {
                Visibilities visibilities = Visibilities.INSTANCE;
                Intrinsics.checkNotNull(visibility2);
                Intrinsics.checkNotNull(visibility3);
                Integer numCompare = visibilities.compare(visibility2, visibility3);
                return Integer.valueOf(numCompare != null ? numCompare.intValue() : -1);
            }
        };
        Visibility visibility2 = (Visibility) CollectionsKt.maxWithOrNull(arrayList, new Comparator() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.FirStatusResolver$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return FirStatusResolver.resolveVisibility$lambda$19(anonymousClass2, obj, obj2);
            }
        });
        return (visibility2 == null || (visibilityNormalize = visibility2.normalize()) == null) ? visibility : visibilityNormalize;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int resolveVisibility$lambda$19(Function2 tmp0, Object obj, Object obj2) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return ((Number) tmp0.invoke(obj, obj2)).intValue();
    }

    private final boolean hasPrivateConstructor(FirClass firClass) {
        ClassKind classKind = firClass.getClassKind();
        return classKind == ClassKind.ENUM_CLASS || classKind == ClassKind.ENUM_ENTRY || FirStatusResolverKt.getModality(firClass) == Modality.SEALED || (firClass instanceof FirAnonymousObject);
    }

    private final Modality resolveModality(FirDeclaration declaration, FirClass containingClass) {
        if (declaration instanceof FirRegularClass) {
            return ((FirRegularClass) declaration).getClassKind() == ClassKind.INTERFACE ? Modality.ABSTRACT : Modality.FINAL;
        }
        if (!(declaration instanceof FirCallableDeclaration)) {
            return Modality.FINAL;
        }
        if (containingClass == null) {
            return Modality.FINAL;
        }
        if (containingClass.getClassKind() == ClassKind.INTERFACE) {
            if (Intrinsics.areEqual(((FirMemberDeclaration) declaration).getStatus().getVisibility(), Visibilities.Private.INSTANCE)) {
                return Modality.FINAL;
            }
            return !FirStatusResolverKt.hasOwnBodyOrAccessorBody(declaration) ? Modality.ABSTRACT : Modality.OPEN;
        }
        if (((FirMemberDeclaration) declaration).getStatus().isOverride()) {
            return Modality.OPEN;
        }
        return Modality.FINAL;
    }

    private final boolean contradictsWith(FirTypeRef firTypeRef, Variance variance) {
        FirResolvedTypeRef firResolvedTypeRef = firTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) firTypeRef : null;
        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
        ConeKotlinType coneKotlinType = type instanceof ConeKotlinType ? type : null;
        if (coneKotlinType == null) {
            return false;
        }
        return contradictsWith(coneKotlinType, variance);
    }
}
