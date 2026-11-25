package org.jetbrains.kotlin.fir.resolve.providers.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.fir.CopyUtilsKt;
import org.jetbrains.kotlin.fir.FirFunctionTypeParameter;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirVisibilityChecker;
import org.jetbrains.kotlin.fir.FirVisibilityCheckerKt;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.DeprecationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirOuterClassTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.diagnostics.ConeAmbiguousFunctionTypeKinds;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeSimpleDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeUnexpectedTypeArgumentsError;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.resolve.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.resolve.FirQualifierResolver;
import org.jetbrains.kotlin.fir.resolve.FirQualifierResolverKt;
import org.jetbrains.kotlin.fir.resolve.FirTypeResolutionResult;
import org.jetbrains.kotlin.fir.resolve.FirTypeResolver;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.SupertypeSupplier;
import org.jetbrains.kotlin.fir.resolve.calls.AbstractCallInfo;
import org.jetbrains.kotlin.fir.resolve.calls.AbstractCandidate;
import org.jetbrains.kotlin.fir.resolve.calls.ResolutionDiagnostic;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeAmbiguityError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeForbiddenIntersection;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeOuterClassArgumentsRequired;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnresolvedTypeQualifierError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeVisibilityError;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeWrongNumberOfTypeArgumentsError;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.transformers.ScopeClassDeclaration;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.symbols.ConeClassifierLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.ConeClassLikeLookupTagImpl;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.CompilerConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeDefinitelyNotNullType;
import org.jetbrains.kotlin.fir.types.ConeDynamicType;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirDynamicTypeRef;
import org.jetbrains.kotlin.fir.types.FirFunctionTypeKindServiceKt;
import org.jetbrains.kotlin.fir.types.FirFunctionTypeRef;
import org.jetbrains.kotlin.fir.types.FirIntersectionTypeRef;
import org.jetbrains.kotlin.fir.types.FirQualifierPart;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeArgumentList;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirUserTypeRef;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.fir.types.impl.ConeTypeParameterTypeImpl;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintSystemError;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationLevelValue;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirTypeResolverImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000°\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0004ABCDB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J?\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\f2\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\nH\u0002¢\u0006\u0002\u0010\u0010J\u001c\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0002J\u0010\u0010\u0017\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\bH\u0002J*\u0010\u0019\u001a\u00020\u001a2\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\f2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J!\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\u0006\u0010\u0013\u001a\u00020\u000e2\b\u0010 \u001a\u0004\u0018\u00010\nH\u0002¢\u0006\u0002\u0010!J$\u0010\"\u001a\u0004\u0018\u00010#2\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\f2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cH\u0002J0\u0010$\u001a\b\u0012\u0002\b\u0003\u0018\u00010%2\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030%2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c2\u0006\u0010&\u001a\u00020'H\u0002JB\u0010(\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020)2\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020-2\u0006\u0010/\u001a\u00020-2\b\u00100\u001a\u0004\u0018\u0001012\u0006\u00102\u001a\u000203H\u0016J<\u00104\u001a\u0002052\u0006\u0010\u0013\u001a\u00020\u000e2\u0006\u00106\u001a\u0002072\u0006\u0010,\u001a\u00020-2\b\u00108\u001a\u0004\u0018\u00010\u00162\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010.\u001a\u00020-H\u0002J0\u00109\u001a\u0002072\u0006\u0010\u0013\u001a\u00020\u000e2\u0006\u0010*\u001a\u00020+2\b\u00100\u001a\u0004\u0018\u0001012\u0006\u00102\u001a\u0002032\u0006\u0010/\u001a\u00020-J2\u0010:\u001a\u00020-*\b\u0012\u0002\b\u0003\u0018\u00010%2\b\u00100\u001a\u0004\u0018\u0001012\f\u0010;\u001a\b\u0012\u0004\u0012\u00020\u00160\u001c2\u0006\u00102\u001a\u000203H\u0002J*\u0010<\u001a\n\u0012\u0004\u0012\u00020>\u0018\u00010=*\u00020'2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c2\u0006\u0010?\u001a\u00020@H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006E"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirTypeResolverImpl;", "Lorg/jetbrains/kotlin/fir/resolve/FirTypeResolver;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "createDiagnosticsIfExists", "Lorg/jetbrains/kotlin/fir/types/ConeErrorType;", "parameterClass", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "qualifierPartIndex", "", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "userTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirUserTypeRef;", "qualifierPartArgumentsCount", "(Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;ILorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;Lorg/jetbrains/kotlin/fir/types/FirUserTypeRef;Ljava/lang/Integer;)Lorg/jetbrains/kotlin/fir/types/ConeErrorType;", "createFunctionType", "Lorg/jetbrains/kotlin/fir/resolve/FirTypeResolutionResult;", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirFunctionTypeRef;", "containerDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "getActualTypeParametersCount", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "getClassesAlignedToQualifierParts", "Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirTypeResolverImpl$ParametersMapAndOuterClasses;", "qualifier", "", "Lorg/jetbrains/kotlin/fir/types/FirQualifierPart;", "getTypeArgumentsOrNameSource", "Lorg/jetbrains/kotlin/KtSourceElement;", "qualifierIndex", "(Lorg/jetbrains/kotlin/fir/types/FirUserTypeRef;Ljava/lang/Integer;)Lorg/jetbrains/kotlin/KtSourceElement;", "resolveLocalClassChain", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "resolveSymbol", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "qualifierResolver", "Lorg/jetbrains/kotlin/fir/resolve/FirQualifierResolver;", "resolveType", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "scopeClassDeclaration", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ScopeClassDeclaration;", "areBareTypesAllowed", "", "isOperandOfIsOperator", "resolveDeprecations", "useSiteFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "supertypeSupplier", "Lorg/jetbrains/kotlin/fir/resolve/SupertypeSupplier;", "resolveUserType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "result", "Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirTypeResolverImpl$TypeResolutionResult;", "topContainer", "resolveUserTypeToSymbol", "isVisible", "containingDeclarations", "resolveEnumEntrySymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "ClassWithQualifierPartIndex", "ParametersMapAndOuterClasses", "TypeCandidate", "TypeResolutionResult", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirTypeResolverImpl extends FirTypeResolver {
    private final FirSession session;

    public FirTypeResolverImpl(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FirBasedSymbol<?> resolveSymbol(FirBasedSymbol<?> symbol, List<? extends FirQualifierPart> qualifier, FirQualifierResolver qualifierResolver) {
        if (symbol instanceof FirClassLikeSymbol) {
            if (qualifier.size() == 1) {
                return symbol;
            }
            FirClassLikeSymbol<?> firClassLikeSymbol = (FirClassLikeSymbol) symbol;
            FirClassifierSymbol<?> firClassifierSymbolResolveLocalClassChain = resolveLocalClassChain(firClassLikeSymbol, qualifier);
            return (firClassifierSymbolResolveLocalClassChain == null && (firClassifierSymbolResolveLocalClassChain = qualifierResolver.resolveSymbolWithPrefix(qualifier, firClassLikeSymbol.getClassId())) == null) ? resolveEnumEntrySymbol(qualifierResolver, qualifier, firClassLikeSymbol.getClassId()) : firClassifierSymbolResolveLocalClassChain;
        }
        if (!(symbol instanceof FirTypeParameterSymbol)) {
            throw new IllegalStateException("!".toString());
        }
        if (qualifier.size() == 1) {
            return symbol;
        }
        return null;
    }

    private final boolean isVisible(FirBasedSymbol<?> firBasedSymbol, FirFile firFile, List<? extends FirDeclaration> list, SupertypeSupplier supertypeSupplier) {
        FirDeclaration fir = firBasedSymbol != null ? firBasedSymbol.getFir() : null;
        if (firFile == null || !(fir instanceof FirMemberDeclaration)) {
            return true;
        }
        return FirVisibilityChecker.isVisible$default(FirVisibilityCheckerKt.getVisibilityChecker(this.session), (FirMemberDeclaration) fir, this.session, firFile, list, null, false, null, false, supertypeSupplier, 192, null);
    }

    public final TypeResolutionResult resolveUserTypeToSymbol(FirUserTypeRef typeRef, ScopeClassDeclaration scopeClassDeclaration, final FirFile useSiteFile, final SupertypeSupplier supertypeSupplier, final boolean resolveDeprecations) {
        FirClassifierSymbol<?> firClassifierSymbolResolveSymbol;
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        Intrinsics.checkNotNullParameter(scopeClassDeclaration, "scopeClassDeclaration");
        Intrinsics.checkNotNullParameter(supertypeSupplier, "supertypeSupplier");
        final FirQualifierResolver qualifierResolver = FirQualifierResolverKt.getQualifierResolver(this.session);
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        final List<FirQualifierPart> qualifier = typeRef.getQualifier();
        Iterable<FirScope> scopes = scopeClassDeclaration.getScopes();
        final List<FirDeclaration> containingDeclarations = scopeClassDeclaration.getContainingDeclarations();
        for (FirScope firScope : scopes) {
            if (objectRef.element == CandidateApplicability.RESOLVED) {
                break;
            }
            firScope.processClassifiersByNameWithSubstitution(((FirQualifierPart) CollectionsKt.first((List) qualifier)).getName(), new Function2<FirClassifierSymbol<?>, ConeSubstitutor, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.providers.impl.FirTypeResolverImpl.resolveUserTypeToSymbol.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(FirClassifierSymbol<?> firClassifierSymbol, ConeSubstitutor coneSubstitutor) {
                    invoke2(firClassifierSymbol, coneSubstitutor);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirClassifierSymbol<?> symbol, ConeSubstitutor substitutorFromScope) {
                    Intrinsics.checkNotNullParameter(symbol, "symbol");
                    Intrinsics.checkNotNullParameter(substitutorFromScope, "substitutorFromScope");
                    FirBasedSymbol firBasedSymbolResolveSymbol = FirTypeResolverImpl.this.resolveSymbol(symbol, qualifier, qualifierResolver);
                    if (firBasedSymbolResolveSymbol == null) {
                        return;
                    }
                    FirTypeResolverImpl.resolveUserTypeToSymbol$processCandidate(FirTypeResolverImpl.this, useSiteFile, containingDeclarations, supertypeSupplier, resolveDeprecations, objectRef, linkedHashSet, firBasedSymbolResolveSymbol, substitutorFromScope);
                }
            });
        }
        if (objectRef.element != CandidateApplicability.RESOLVED && (firClassifierSymbolResolveSymbol = qualifierResolver.resolveSymbol(qualifier)) != null) {
            resolveUserTypeToSymbol$processCandidate(this, useSiteFile, containingDeclarations, supertypeSupplier, resolveDeprecations, objectRef, linkedHashSet, firClassifierSymbolResolveSymbol, null);
        }
        int size = linkedHashSet.size();
        if (size == 1) {
            return new TypeResolutionResult.Resolved((TypeCandidate) CollectionsKt.single(linkedHashSet));
        }
        if (size > 1) {
            return new TypeResolutionResult.Ambiguity(CollectionsKt.toList(linkedHashSet));
        }
        if (size == 0) {
            return TypeResolutionResult.Unresolved.INSTANCE;
        }
        throw new IllegalStateException("Unexpected".toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:12:0x0041  */
    /* JADX WARN: Code duplicated, block: B:17:0x0053  */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v2, types: [T, org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability] */
    public static final void resolveUserTypeToSymbol$processCandidate(FirTypeResolverImpl firTypeResolverImpl, FirFile firFile, List<? extends FirDeclaration> list, SupertypeSupplier supertypeSupplier, boolean z, Ref.ObjectRef<CandidateApplicability> objectRef, Set<TypeCandidate> set, FirBasedSymbol<?> firBasedSymbol, ConeSubstitutor coneSubstitutor) {
        ConeVisibilityError coneVisibilityError;
        CandidateApplicability candidateApplicability;
        ?? r0;
        CandidateApplicability candidateApplicability2;
        CandidateApplicability candidateApplicability3 = CandidateApplicability.RESOLVED;
        boolean zIsVisible = firTypeResolverImpl.isVisible(firBasedSymbol, firFile, list, supertypeSupplier);
        ConeVisibilityError coneVisibilityError2 = null;
        if (zIsVisible) {
            coneVisibilityError = null;
        } else {
            candidateApplicability2 = (CandidateApplicability) ComparisonsKt.minOf(CandidateApplicability.K2_VISIBILITY_ERROR, candidateApplicability3);
            coneVisibilityError = new ConeVisibilityError(firBasedSymbol);
        }
        if (z) {
            candidateApplicability = candidateApplicability3;
            DeprecationInfo ownDeprecation = DeprecationUtilsKt.getOwnDeprecation(firBasedSymbol, firTypeResolverImpl.session, firFile);
            if (ownDeprecation == null || ownDeprecation.getDeprecationLevel() != DeprecationLevelValue.HIDDEN) {
                candidateApplicability = candidateApplicability3;
                candidateApplicability = candidateApplicability2;
                candidateApplicability = candidateApplicability2;
                candidateApplicability = candidateApplicability2;
                coneVisibilityError2 = coneVisibilityError;
                r0 = candidateApplicability;
            } else {
                candidateApplicability = candidateApplicability2;
                r0 = (CandidateApplicability) ComparisonsKt.minOf(CandidateApplicability.HIDDEN, candidateApplicability);
            }
        } else {
            candidateApplicability = candidateApplicability3;
            candidateApplicability = candidateApplicability2;
            candidateApplicability = candidateApplicability2;
            candidateApplicability = candidateApplicability2;
            coneVisibilityError2 = coneVisibilityError;
            r0 = candidateApplicability;
        }
        if (objectRef.element != null) {
            CandidateApplicability candidateApplicability4 = objectRef.element;
            Intrinsics.checkNotNull(candidateApplicability4);
            if (r0.compareTo(candidateApplicability4) > 0) {
                objectRef.element = r0;
                set.clear();
            }
        } else {
            objectRef.element = r0;
            set.clear();
        }
        if (r0 == objectRef.element) {
            set.add(new TypeCandidate(firBasedSymbol, coneSubstitutor, coneVisibilityError2, r0));
        }
    }

    /* JADX INFO: compiled from: FirTypeResolverImpl.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirTypeResolverImpl$TypeResolutionResult;", "", "()V", "Ambiguity", "Resolved", "Unresolved", "Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirTypeResolverImpl$TypeResolutionResult$Ambiguity;", "Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirTypeResolverImpl$TypeResolutionResult$Resolved;", "Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirTypeResolverImpl$TypeResolutionResult$Unresolved;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class TypeResolutionResult {
        public /* synthetic */ TypeResolutionResult(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: compiled from: FirTypeResolverImpl.kt */
        @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirTypeResolverImpl$TypeResolutionResult$Ambiguity;", "Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirTypeResolverImpl$TypeResolutionResult;", "typeCandidates", "", "Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirTypeResolverImpl$TypeCandidate;", "(Ljava/util/List;)V", "getTypeCandidates", "()Ljava/util/List;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Ambiguity extends TypeResolutionResult {
            private final List<TypeCandidate> typeCandidates;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Ambiguity(List<TypeCandidate> typeCandidates) {
                super(null);
                Intrinsics.checkNotNullParameter(typeCandidates, "typeCandidates");
                this.typeCandidates = typeCandidates;
            }

            public final List<TypeCandidate> getTypeCandidates() {
                return this.typeCandidates;
            }
        }

        private TypeResolutionResult() {
        }

        /* JADX INFO: compiled from: FirTypeResolverImpl.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirTypeResolverImpl$TypeResolutionResult$Unresolved;", "Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirTypeResolverImpl$TypeResolutionResult;", "()V", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Unresolved extends TypeResolutionResult {
            public static final Unresolved INSTANCE = new Unresolved();

            private Unresolved() {
                super(null);
            }
        }

        /* JADX INFO: compiled from: FirTypeResolverImpl.kt */
        @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirTypeResolverImpl$TypeResolutionResult$Resolved;", "Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirTypeResolverImpl$TypeResolutionResult;", "typeCandidate", "Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirTypeResolverImpl$TypeCandidate;", "(Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirTypeResolverImpl$TypeCandidate;)V", "getTypeCandidate", "()Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirTypeResolverImpl$TypeCandidate;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Resolved extends TypeResolutionResult {
            private final TypeCandidate typeCandidate;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Resolved(TypeCandidate typeCandidate) {
                super(null);
                Intrinsics.checkNotNullParameter(typeCandidate, "typeCandidate");
                this.typeCandidate = typeCandidate;
            }

            public final TypeCandidate getTypeCandidate() {
                return this.typeCandidate;
            }
        }
    }

    private final FirRegularClassSymbol resolveLocalClassChain(FirClassLikeSymbol<?> symbol, List<? extends FirQualifierPart> qualifier) {
        if ((symbol instanceof FirRegularClassSymbol) && symbol.getClassId().isLocal()) {
            return resolveLocalClassChain$resolveLocalClassChain(qualifier, (FirRegularClassSymbol) symbol, 1);
        }
        return null;
    }

    private static final FirRegularClassSymbol resolveLocalClassChain$resolveLocalClassChain(List<? extends FirQualifierPart> list, FirRegularClassSymbol firRegularClassSymbol, int i) {
        if (i == list.size()) {
            return firRegularClassSymbol;
        }
        Name name2 = list.get(i).getName();
        for (FirBasedSymbol<?> firBasedSymbol : firRegularClassSymbol.getDeclarationSymbols()) {
            if (firBasedSymbol instanceof FirRegularClassSymbol) {
                FirRegularClassSymbol firRegularClassSymbol2 = (FirRegularClassSymbol) firBasedSymbol;
                if (Intrinsics.areEqual(firRegularClassSymbol2.getLookupTag().getName(), name2)) {
                    return resolveLocalClassChain$resolveLocalClassChain(list, firRegularClassSymbol2, i + 1);
                }
            }
        }
        return null;
    }

    private final FirVariableSymbol<FirEnumEntry> resolveEnumEntrySymbol(FirQualifierResolver firQualifierResolver, List<? extends FirQualifierPart> list, ClassId classId) {
        Object next;
        FirClassifierSymbol<?> firClassifierSymbolResolveSymbolWithPrefix = firQualifierResolver.resolveSymbolWithPrefix(CollectionsKt.dropLast(list, 1), classId);
        if (firClassifierSymbolResolveSymbolWithPrefix == null) {
            return null;
        }
        Object fir = firClassifierSymbolResolveSymbolWithPrefix.getFir();
        FirRegularClass firRegularClass = fir instanceof FirRegularClass ? (FirRegularClass) fir : null;
        if (firRegularClass == null || firRegularClass.getClassKind() != ClassKind.ENUM_CLASS) {
            return null;
        }
        Iterator<T> it = firRegularClass.getDeclarations().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            FirDeclaration firDeclaration = (FirDeclaration) next;
            if ((firDeclaration instanceof FirEnumEntry) && Intrinsics.areEqual(((FirEnumEntry) firDeclaration).getName(), ((FirQualifierPart) CollectionsKt.last((List) list)).getName())) {
                break;
            }
        }
        FirEnumEntry firEnumEntry = next instanceof FirEnumEntry ? (FirEnumEntry) next : null;
        return firEnumEntry != null ? firEnumEntry.getSymbol() : null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final ConeKotlinType resolveUserType(FirUserTypeRef typeRef, TypeResolutionResult result, boolean areBareTypesAllowed, FirDeclaration topContainer, FirDeclaration containerDeclaration, boolean isOperandOfIsOperator) {
        Pair pair;
        ConeUnresolvedTypeQualifierError coneUnresolvedTypeQualifierError;
        ConeSimpleDiagnostic coneSimpleDiagnostic;
        ConeErrorType coneErrorTypeCreateDiagnosticsIfExists;
        int i;
        List<FirClassLikeDeclaration> list;
        Map<FirTypeParameterSymbol, ClassWithQualifierPartIndex> map;
        if (result instanceof TypeResolutionResult.Resolved) {
            TypeResolutionResult.Resolved resolved = (TypeResolutionResult.Resolved) result;
            pair = TuplesKt.to(resolved.getTypeCandidate().getSymbol(), resolved.getTypeCandidate().getSubstitutor());
        } else {
            if (!(result instanceof TypeResolutionResult.Ambiguity) && !Intrinsics.areEqual(result, TypeResolutionResult.Unresolved.INSTANCE)) {
                throw new NoWhenBranchMatchedException();
            }
            pair = TuplesKt.to(null, null);
        }
        FirBasedSymbol firBasedSymbol = (FirBasedSymbol) pair.component1();
        ConeSubstitutor.Empty empty = (ConeSubstitutor) pair.component2();
        ArrayList arrayList = new ArrayList();
        List<FirQualifierPart> qualifier = typeRef.getQualifier();
        int i2 = 0;
        for (int size = qualifier.size() - 1; -1 < size; size--) {
            Iterator<FirTypeProjection> it = qualifier.get(size).getTypeArgumentList().getTypeArguments().iterator();
            while (it.hasNext()) {
                arrayList.add(FirTypeUtilsKt.toConeTypeProjection(it.next()));
                i2++;
            }
        }
        if ((firBasedSymbol instanceof FirRegularClassSymbol) && (!areBareTypesAllowed || !arrayList.isEmpty())) {
            if (empty == null) {
                empty = ConeSubstitutor.Empty.INSTANCE;
            }
            ConeSubstitutor coneSubstitutor = empty;
            FirRegularClassSymbol firRegularClassSymbol = (FirRegularClassSymbol) firBasedSymbol;
            List<FirTypeParameterRef> typeParameters = ((FirRegularClass) firRegularClassSymbol.getFir()).getTypeParameters();
            FirClassLikeSymbol<?> firClassLikeSymbol = (FirClassLikeSymbol) firBasedSymbol;
            ParametersMapAndOuterClasses classesAlignedToQualifierParts = getClassesAlignedToQualifierParts(firClassLikeSymbol, qualifier, this.session);
            Map<FirTypeParameterSymbol, ClassWithQualifierPartIndex> mapComponent1 = classesAlignedToQualifierParts.component1();
            List<FirClassLikeDeclaration> listComponent2 = classesAlignedToQualifierParts.component2();
            int size2 = firRegularClassSymbol.getTypeParameterSymbols().size();
            int i3 = 0;
            for (FirTypeParameterRef firTypeParameterRef : typeParameters) {
                int i4 = i3 + 1;
                ClassWithQualifierPartIndex classWithQualifierPartIndex = mapComponent1.get(firTypeParameterRef.getSymbol());
                if (classWithQualifierPartIndex == null) {
                    i = size2;
                    list = listComponent2;
                    map = mapComponent1;
                } else {
                    FirClassLikeDeclaration klass = classWithQualifierPartIndex.getKlass();
                    int index = classWithQualifierPartIndex.getIndex();
                    if (i3 < i2) {
                        i = size2;
                        list = listComponent2;
                        map = mapComponent1;
                        ConeErrorType coneErrorTypeCreateDiagnosticsIfExists2 = createDiagnosticsIfExists(klass, index, firClassLikeSymbol, typeRef, Integer.valueOf(qualifier.get(index).getTypeArgumentList().getTypeArguments().size()));
                        if (coneErrorTypeCreateDiagnosticsIfExists2 != null) {
                            return coneErrorTypeCreateDiagnosticsIfExists2;
                        }
                    } else {
                        i = size2;
                        list = listComponent2;
                        map = mapComponent1;
                        if ((firTypeParameterRef instanceof FirOuterClassTypeParameterRef) && !DeclarationUtilsKt.isValidTypeParameterFromOuterDeclaration(firTypeParameterRef.getSymbol(), topContainer, this.session)) {
                            return new ConeErrorType(new ConeOuterClassArgumentsRequired(klass.getSymbol()), false, null, null, 14, null);
                        }
                        ConeKotlinType coneKotlinTypeSubstituteOrNull = coneSubstitutor.substituteOrNull(new ConeTypeParameterTypeImpl(new ConeTypeParameterLookupTag(firTypeParameterRef.getSymbol()), false, null, 4, null));
                        if (coneKotlinTypeSubstituteOrNull == null) {
                            ConeErrorType coneErrorTypeCreateDiagnosticsIfExists3 = createDiagnosticsIfExists(klass, index, firClassLikeSymbol, typeRef, null);
                            if (coneErrorTypeCreateDiagnosticsIfExists3 != null) {
                                return coneErrorTypeCreateDiagnosticsIfExists3;
                            }
                        } else {
                            arrayList.add(coneKotlinTypeSubstituteOrNull);
                        }
                    }
                }
                size2 = i;
                listComponent2 = list;
                i3 = i4;
                mapComponent1 = map;
            }
            List<FirClassLikeDeclaration> list2 = listComponent2;
            if (i2 > size2) {
                int size3 = qualifier.size();
                for (int i5 = 0; i5 < size3; i5++) {
                    if (!qualifier.get(i5).getTypeArgumentList().getTypeArguments().isEmpty() && (coneErrorTypeCreateDiagnosticsIfExists = createDiagnosticsIfExists((FirClassLikeDeclaration) CollectionsKt.getOrNull(list2, i5), i5, firClassLikeSymbol, typeRef, null)) != null) {
                        return coneErrorTypeCreateDiagnosticsIfExists;
                    }
                }
            }
        }
        ConeTypeProjection[] coneTypeProjectionArr = (ConeTypeProjection[]) arrayList.toArray(new ConeTypeProjection[0]);
        if (firBasedSymbol == null || !(firBasedSymbol instanceof FirClassifierSymbol)) {
            if ((firBasedSymbol != null ? firBasedSymbol.getFir() : null) instanceof FirEnumEntry) {
                if (isOperandOfIsOperator) {
                    coneSimpleDiagnostic = new ConeSimpleDiagnostic("'is' operator can not be applied to an enum entry.", DiagnosticKind.IsEnumEntry);
                } else {
                    coneSimpleDiagnostic = new ConeSimpleDiagnostic("An enum entry should not be used as a type.", DiagnosticKind.EnumEntryAsType);
                }
                coneUnresolvedTypeQualifierError = coneSimpleDiagnostic;
            } else if (result instanceof TypeResolutionResult.Ambiguity) {
                TypeResolutionResult.Ambiguity ambiguity = (TypeResolutionResult.Ambiguity) result;
                coneUnresolvedTypeQualifierError = new ConeAmbiguityError(((FirQualifierPart) CollectionsKt.last((List) typeRef.getQualifier())).getName(), ((TypeCandidate) CollectionsKt.first((List) ambiguity.getTypeCandidates())).getApplicability(), ambiguity.getTypeCandidates());
            } else {
                coneUnresolvedTypeQualifierError = new ConeUnresolvedTypeQualifierError(typeRef.getQualifier(), typeRef.getIsMarkedNullable());
            }
            return new ConeErrorType(coneUnresolvedTypeQualifierError, false, coneTypeProjectionArr, CopyUtilsKt.computeTypeAttributes$default(typeRef.getAnnotations(), this.session, null, null, true, 6, null), 2, null);
        }
        if (firBasedSymbol instanceof FirTypeParameterSymbol) {
            for (FirQualifierPart firQualifierPart : typeRef.getQualifier()) {
                if (!firQualifierPart.getTypeArgumentList().getTypeArguments().isEmpty()) {
                    return new ConeErrorType(new ConeUnexpectedTypeArgumentsError("Type arguments not allowed", firQualifierPart.getTypeArgumentList().getSource()), false, coneTypeProjectionArr, null, 10, null);
                }
            }
        }
        ConeLookupTagBasedType coneLookupTagBasedTypeConstructType = TypeConstructionUtilsKt.constructType((FirClassifierSymbol<?>) firBasedSymbol, coneTypeProjectionArr, typeRef.getIsMarkedNullable(), CopyUtilsKt.computeTypeAttributes$default(typeRef.getAnnotations(), this.session, null, containerDeclaration, true, 2, null));
        ConeClassifierLookupTag lookupTag = coneLookupTagBasedTypeConstructType.getLookupTag();
        if ((lookupTag instanceof ConeClassLikeLookupTagImpl) && (firBasedSymbol instanceof FirClassLikeSymbol)) {
            LookupTagUtilsKt.bindSymbolToLookupTag((ConeClassLikeLookupTagImpl) lookupTag, this.session, (FirClassLikeSymbol) firBasedSymbol);
        }
        return coneLookupTagBasedTypeConstructType;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final ParametersMapAndOuterClasses getClassesAlignedToQualifierParts(FirClassLikeSymbol<?> symbol, List<? extends FirQualifierPart> qualifier, FirSession session) {
        List<FirTypeParameterRef> typeParameters;
        ArrayList arrayList = new ArrayList();
        FirClassLikeDeclaration containingDeclaration = null;
        int i = 0;
        while (true) {
            if (i >= qualifier.size() && containingDeclaration == null) {
                break;
            }
            if (i == 0) {
                containingDeclaration = (FirClassLikeDeclaration) symbol.getFir();
            } else if (containingDeclaration != null) {
                containingDeclaration = DeclarationUtilsKt.getContainingDeclaration(containingDeclaration, session);
            }
            arrayList.add(containingDeclaration);
            i++;
        }
        int size = arrayList.size() - qualifier.size();
        List listAsReversedMutable = CollectionsKt.asReversedMutable(arrayList);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        int size2 = listAsReversedMutable.size();
        for (int i2 = 0; i2 < size2; i2++) {
            FirClassLikeDeclaration firClassLikeDeclaration = (FirClassLikeDeclaration) listAsReversedMutable.get(i2);
            if (firClassLikeDeclaration instanceof FirTypeAlias) {
                typeParameters = ((FirTypeAlias) firClassLikeDeclaration).getTypeParameters();
            } else {
                typeParameters = firClassLikeDeclaration instanceof FirClass ? ((FirClass) firClassLikeDeclaration).getTypeParameters() : null;
            }
            if (firClassLikeDeclaration != null && typeParameters != null) {
                Iterator<FirTypeParameterRef> it = typeParameters.iterator();
                while (it.hasNext()) {
                    FirTypeParameterSymbol symbol2 = it.next().getSymbol();
                    if (!linkedHashMap.containsKey(symbol2)) {
                        linkedHashMap.put(symbol2, new ClassWithQualifierPartIndex(firClassLikeDeclaration, i2 - size));
                    }
                }
            }
        }
        return new ParametersMapAndOuterClasses(linkedHashMap, CollectionsKt.drop(listAsReversedMutable, size));
    }

    /* JADX INFO: compiled from: FirTypeResolverImpl.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B)\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u0012\u000e\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0007¢\u0006\u0002\u0010\tJ\u0015\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003HÆ\u0003J\u0011\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0007HÆ\u0003J1\u0010\u0010\u001a\u00020\u00002\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u00032\u0010\b\u0002\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0007HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001R\u0019\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirTypeResolverImpl$ParametersMapAndOuterClasses;", "", "parameters", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirTypeResolverImpl$ClassWithQualifierPartIndex;", "outerClasses", "", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "(Ljava/util/Map;Ljava/util/List;)V", "getOuterClasses", "()Ljava/util/List;", "getParameters", "()Ljava/util/Map;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class ParametersMapAndOuterClasses {
        private final List<FirClassLikeDeclaration> outerClasses;
        private final Map<FirTypeParameterSymbol, ClassWithQualifierPartIndex> parameters;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ ParametersMapAndOuterClasses copy$default(ParametersMapAndOuterClasses parametersMapAndOuterClasses, Map map, List list, int i, Object obj) {
            if ((i & 1) != 0) {
                map = parametersMapAndOuterClasses.parameters;
            }
            if ((i & 2) != 0) {
                list = parametersMapAndOuterClasses.outerClasses;
            }
            return parametersMapAndOuterClasses.copy(map, list);
        }

        public final Map<FirTypeParameterSymbol, ClassWithQualifierPartIndex> component1() {
            return this.parameters;
        }

        public final List<FirClassLikeDeclaration> component2() {
            return this.outerClasses;
        }

        public final ParametersMapAndOuterClasses copy(Map<FirTypeParameterSymbol, ClassWithQualifierPartIndex> parameters, List<? extends FirClassLikeDeclaration> outerClasses) {
            Intrinsics.checkNotNullParameter(parameters, "parameters");
            Intrinsics.checkNotNullParameter(outerClasses, "outerClasses");
            return new ParametersMapAndOuterClasses(parameters, outerClasses);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ParametersMapAndOuterClasses)) {
                return false;
            }
            ParametersMapAndOuterClasses parametersMapAndOuterClasses = (ParametersMapAndOuterClasses) other;
            return Intrinsics.areEqual(this.parameters, parametersMapAndOuterClasses.parameters) && Intrinsics.areEqual(this.outerClasses, parametersMapAndOuterClasses.outerClasses);
        }

        public int hashCode() {
            return (this.parameters.hashCode() * 31) + this.outerClasses.hashCode();
        }

        public String toString() {
            return "ParametersMapAndOuterClasses(parameters=" + this.parameters + ", outerClasses=" + this.outerClasses + ')';
        }

        /* JADX WARN: Multi-variable type inference failed */
        public ParametersMapAndOuterClasses(Map<FirTypeParameterSymbol, ClassWithQualifierPartIndex> parameters, List<? extends FirClassLikeDeclaration> outerClasses) {
            Intrinsics.checkNotNullParameter(parameters, "parameters");
            Intrinsics.checkNotNullParameter(outerClasses, "outerClasses");
            this.parameters = parameters;
            this.outerClasses = outerClasses;
        }

        public final Map<FirTypeParameterSymbol, ClassWithQualifierPartIndex> getParameters() {
            return this.parameters;
        }

        public final List<FirClassLikeDeclaration> getOuterClasses() {
            return this.outerClasses;
        }
    }

    /* JADX INFO: compiled from: FirTypeResolverImpl.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirTypeResolverImpl$ClassWithQualifierPartIndex;", "", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "index", "", "(Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;I)V", "getIndex", "()I", "getKlass", "()Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "toString", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class ClassWithQualifierPartIndex {
        private final int index;
        private final FirClassLikeDeclaration klass;

        public static /* synthetic */ ClassWithQualifierPartIndex copy$default(ClassWithQualifierPartIndex classWithQualifierPartIndex, FirClassLikeDeclaration firClassLikeDeclaration, int i, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                firClassLikeDeclaration = classWithQualifierPartIndex.klass;
            }
            if ((i2 & 2) != 0) {
                i = classWithQualifierPartIndex.index;
            }
            return classWithQualifierPartIndex.copy(firClassLikeDeclaration, i);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final FirClassLikeDeclaration getKlass() {
            return this.klass;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final int getIndex() {
            return this.index;
        }

        public final ClassWithQualifierPartIndex copy(FirClassLikeDeclaration klass, int index) {
            Intrinsics.checkNotNullParameter(klass, "klass");
            return new ClassWithQualifierPartIndex(klass, index);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ClassWithQualifierPartIndex)) {
                return false;
            }
            ClassWithQualifierPartIndex classWithQualifierPartIndex = (ClassWithQualifierPartIndex) other;
            return Intrinsics.areEqual(this.klass, classWithQualifierPartIndex.klass) && this.index == classWithQualifierPartIndex.index;
        }

        public int hashCode() {
            return (this.klass.hashCode() * 31) + Integer.hashCode(this.index);
        }

        public String toString() {
            return "ClassWithQualifierPartIndex(klass=" + this.klass + ", index=" + this.index + ')';
        }

        public ClassWithQualifierPartIndex(FirClassLikeDeclaration klass, int i) {
            Intrinsics.checkNotNullParameter(klass, "klass");
            this.klass = klass;
            this.index = i;
        }

        public final FirClassLikeDeclaration getKlass() {
            return this.klass;
        }

        public final int getIndex() {
            return this.index;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final ConeErrorType createDiagnosticsIfExists(FirClassLikeDeclaration parameterClass, int qualifierPartIndex, FirClassLikeSymbol<?> symbol, FirUserTypeRef userTypeRef, Integer qualifierPartArgumentsCount) {
        KtSourceElement typeArgumentsOrNameSource;
        FirClassLikeSymbol<? extends FirClassLikeDeclaration> symbol2;
        int actualTypeParametersCount = getActualTypeParametersCount(parameterClass == null ? (FirClassLikeDeclaration) symbol.getFir() : parameterClass);
        if ((qualifierPartArgumentsCount != null && actualTypeParametersCount == qualifierPartArgumentsCount.intValue()) || (typeArgumentsOrNameSource = getTypeArgumentsOrNameSource(userTypeRef, Integer.valueOf(qualifierPartIndex))) == null) {
            return null;
        }
        if (parameterClass != null && (symbol2 = parameterClass.getSymbol()) != null) {
            symbol = symbol2;
        }
        return new ConeErrorType(new ConeWrongNumberOfTypeArgumentsError(actualTypeParametersCount, symbol, typeArgumentsOrNameSource), false, null, null, 14, null);
    }

    private final int getActualTypeParametersCount(FirClassLikeDeclaration element) {
        Intrinsics.checkNotNull(element, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner");
        List<FirTypeParameterRef> typeParameters = element.getTypeParameters();
        int i = 0;
        if (!(typeParameters instanceof Collection) || !typeParameters.isEmpty()) {
            Iterator<T> it = typeParameters.iterator();
            while (it.hasNext()) {
                if (!(((FirTypeParameterRef) it.next()) instanceof FirOuterClassTypeParameterRef) && (i = i + 1) < 0) {
                    CollectionsKt.throwCountOverflow();
                }
            }
        }
        return i;
    }

    private final KtSourceElement getTypeArgumentsOrNameSource(FirUserTypeRef typeRef, Integer qualifierIndex) {
        KtSourceElement source;
        FirQualifierPart firQualifierPart = qualifierIndex != null ? (FirQualifierPart) CollectionsKt.getOrNull(typeRef.getQualifier(), qualifierIndex.intValue()) : null;
        FirTypeArgumentList typeArgumentList = firQualifierPart != null ? firQualifierPart.getTypeArgumentList() : null;
        if (typeArgumentList == null || typeArgumentList.getTypeArguments().isEmpty()) {
            return (firQualifierPart == null || (source = firQualifierPart.getSource()) == null) ? typeRef.getSource() : source;
        }
        return typeArgumentList.getSource();
    }

    private final FirTypeResolutionResult createFunctionType(FirFunctionTypeRef typeRef, FirDeclaration containerDeclaration) {
        FunctionTypeKind.Function function;
        List<FirTypeRef> contextReceiverTypeRefs = typeRef.getContextReceiverTypeRefs();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(contextReceiverTypeRefs, 10));
        Iterator<T> it = contextReceiverTypeRefs.iterator();
        while (it.hasNext()) {
            arrayList.add(FirTypeUtilsKt.getConeType((FirTypeRef) it.next()));
        }
        ArrayList arrayList2 = arrayList;
        FirTypeRef receiverTypeRef = typeRef.getReceiverTypeRef();
        ConeAmbiguousFunctionTypeKinds coneAmbiguousFunctionTypeKinds = null;
        List listPlus = CollectionsKt.plus((Collection) arrayList2, (Iterable) CollectionsKt.listOfNotNull(receiverTypeRef != null ? FirTypeUtilsKt.getConeType(receiverTypeRef) : null));
        List<FirFunctionTypeParameter> parameters = typeRef.getParameters();
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(parameters, 10));
        for (FirFunctionTypeParameter firFunctionTypeParameter : parameters) {
            arrayList3.add(LookupTagUtilsKt.withParameterNameAnnotation(FirTypeUtilsKt.getConeType(firFunctionTypeParameter.getReturnTypeRef()), firFunctionTypeParameter, this.session));
        }
        List listPlus2 = CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) listPlus, (Iterable) arrayList3), (Iterable) CollectionsKt.listOf(FirTypeUtilsKt.getConeType(typeRef.getReturnTypeRef())));
        List<FunctionTypeKind> listExtractAllSpecialKindsForFunctionTypeRef = FirFunctionTypeKindServiceKt.getFunctionTypeService(this.session).extractAllSpecialKindsForFunctionTypeRef(typeRef);
        int size = listExtractAllSpecialKindsForFunctionTypeRef.size();
        if (size == 0) {
            function = FunctionTypeKind.Function.INSTANCE;
        } else if (size != 1) {
            coneAmbiguousFunctionTypeKinds = new ConeAmbiguousFunctionTypeKinds(listExtractAllSpecialKindsForFunctionTypeRef);
            function = FunctionTypeKind.Function.INSTANCE;
        } else {
            function = (FunctionTypeKind) CollectionsKt.single((List) listExtractAllSpecialKindsForFunctionTypeRef);
        }
        ClassId classIdNumberedClassId = function.numberedClassId(FirTypeUtilsKt.getParametersCount(typeRef));
        List<FirAnnotation> annotations = typeRef.getAnnotations();
        FirSession firSession = this.session;
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        if (typeRef.getReceiverTypeRef() != null) {
            listCreateListBuilder.add(CompilerConeAttributes.ExtensionFunctionType.INSTANCE);
        }
        if (!typeRef.getContextReceiverTypeRefs().isEmpty()) {
            listCreateListBuilder.add(new CompilerConeAttributes.ContextFunctionTypeParams(typeRef.getContextReceiverTypeRefs().size()));
        }
        Unit unit = Unit.INSTANCE;
        return new FirTypeResolutionResult(new ConeClassLikeTypeImpl(TypeConstructionUtilsKt.toLookupTag(classIdNumberedClassId), (ConeTypeProjection[]) listPlus2.toArray(new ConeKotlinType[0]), typeRef.getIsMarkedNullable(), CopyUtilsKt.computeTypeAttributes(annotations, firSession, CollectionsKt.build(listCreateListBuilder), containerDeclaration, true)), coneAmbiguousFunctionTypeKinds);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.FirTypeResolver
    public FirTypeResolutionResult resolveType(FirTypeRef typeRef, ScopeClassDeclaration scopeClassDeclaration, boolean areBareTypesAllowed, boolean isOperandOfIsOperator, boolean resolveDeprecations, FirFile useSiteFile, SupertypeSupplier supertypeSupplier) {
        TypeCandidate typeCandidate;
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        Intrinsics.checkNotNullParameter(scopeClassDeclaration, "scopeClassDeclaration");
        Intrinsics.checkNotNullParameter(supertypeSupplier, "supertypeSupplier");
        if (typeRef instanceof FirResolvedTypeRef) {
            throw new IllegalStateException("Do not resolve, resolved type-refs".toString());
        }
        ConeDiagnostic diagnostic = null;
        if (typeRef instanceof FirUserTypeRef) {
            FirUserTypeRef firUserTypeRef = (FirUserTypeRef) typeRef;
            TypeResolutionResult typeResolutionResultResolveUserTypeToSymbol = resolveUserTypeToSymbol(firUserTypeRef, scopeClassDeclaration, useSiteFile, supertypeSupplier, resolveDeprecations);
            FirDeclaration topContainer = scopeClassDeclaration.getTopContainer();
            if (topContainer == null) {
                topContainer = (FirDeclaration) CollectionsKt.lastOrNull((List) scopeClassDeclaration.getContainingDeclarations());
            }
            ConeKotlinType coneKotlinTypeResolveUserType = resolveUserType(firUserTypeRef, typeResolutionResultResolveUserTypeToSymbol, areBareTypesAllowed, topContainer, scopeClassDeclaration.getContainerDeclaration(), isOperandOfIsOperator);
            TypeResolutionResult.Resolved resolved = typeResolutionResultResolveUserTypeToSymbol instanceof TypeResolutionResult.Resolved ? (TypeResolutionResult.Resolved) typeResolutionResultResolveUserTypeToSymbol : null;
            if (resolved != null && (typeCandidate = resolved.getTypeCandidate()) != null) {
                diagnostic = typeCandidate.getDiagnostic();
            }
            return new FirTypeResolutionResult(coneKotlinTypeResolveUserType, diagnostic);
        }
        if (typeRef instanceof FirFunctionTypeRef) {
            return createFunctionType((FirFunctionTypeRef) typeRef, scopeClassDeclaration.getContainerDeclaration());
        }
        if (typeRef instanceof FirDynamicTypeRef) {
            return new FirTypeResolutionResult(TypeUtilsKt.create(ConeDynamicType.INSTANCE, this.session), null);
        }
        if (typeRef instanceof FirIntersectionTypeRef) {
            ConeKotlinType coneType = FirTypeUtilsKt.getConeType(((FirIntersectionTypeRef) typeRef).getLeftType());
            if (coneType instanceof ConeTypeParameterType) {
                return new FirTypeResolutionResult(new ConeDefinitelyNotNullType((ConeSimpleKotlinType) coneType), null);
            }
            return new FirTypeResolutionResult(new ConeErrorType(ConeForbiddenIntersection.INSTANCE, false, null, null, 14, null), null);
        }
        throw new IllegalStateException(UtilsKt.render(typeRef).toString());
    }

    /* JADX INFO: compiled from: FirTypeResolverImpl.kt */
    @Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B-\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0013\u0010)\u001a\u00020*2\b\u0010+\u001a\u0004\u0018\u00010,H\u0096\u0002J\b\u0010-\u001a\u00020.H\u0016R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0016\u0010\u0011\u001a\u0004\u0018\u00010\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u00188VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0016\u0010\u001c\u001a\u0004\u0018\u00010\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u0014R\u001a\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\u00188VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b \u0010\u001bR\u0014\u0010!\u001a\u00020\"8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b#\u0010$R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0018\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b'\u0010(¨\u0006/"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/providers/impl/FirTypeResolverImpl$TypeCandidate;", "Lorg/jetbrains/kotlin/fir/resolve/calls/AbstractCandidate;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "substitutor", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "diagnostic", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "applicability", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "(Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;)V", "getApplicability", "()Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "callInfo", "Lorg/jetbrains/kotlin/fir/resolve/calls/AbstractCallInfo;", "getCallInfo", "()Lorg/jetbrains/kotlin/fir/resolve/calls/AbstractCallInfo;", "chosenExtensionReceiver", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getChosenExtensionReceiver", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getDiagnostic", "()Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "diagnostics", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionDiagnostic;", "getDiagnostics", "()Ljava/util/List;", "dispatchReceiver", "getDispatchReceiver", "errors", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintSystemError;", "getErrors", "explicitReceiverKind", "Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;", "getExplicitReceiverKind", "()Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;", "getSubstitutor", "()Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class TypeCandidate extends AbstractCandidate {
        private final CandidateApplicability applicability;
        private final ConeDiagnostic diagnostic;
        private final ConeSubstitutor substitutor;
        private final FirBasedSymbol<?> symbol;

        @Override // org.jetbrains.kotlin.fir.resolve.calls.AbstractCandidate
        public FirExpression getChosenExtensionReceiver() {
            return null;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.calls.AbstractCandidate
        public FirExpression getDispatchReceiver() {
            return null;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.calls.AbstractCandidate
        public FirBasedSymbol<?> getSymbol() {
            return this.symbol;
        }

        public final ConeSubstitutor getSubstitutor() {
            return this.substitutor;
        }

        public final ConeDiagnostic getDiagnostic() {
            return this.diagnostic;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.calls.AbstractCandidate
        public CandidateApplicability getApplicability() {
            return this.applicability;
        }

        public TypeCandidate(FirBasedSymbol<?> symbol, ConeSubstitutor coneSubstitutor, ConeDiagnostic coneDiagnostic, CandidateApplicability applicability) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            Intrinsics.checkNotNullParameter(applicability, "applicability");
            this.symbol = symbol;
            this.substitutor = coneSubstitutor;
            this.diagnostic = coneDiagnostic;
            this.applicability = applicability;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.calls.AbstractCandidate
        public ExplicitReceiverKind getExplicitReceiverKind() {
            return ExplicitReceiverKind.NO_EXPLICIT_RECEIVER;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.calls.AbstractCandidate
        public List<ResolutionDiagnostic> getDiagnostics() {
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.fir.resolve.calls.AbstractCandidate
        public List<ConstraintSystemError> getErrors() {
            return CollectionsKt.emptyList();
        }

        @Override // org.jetbrains.kotlin.fir.resolve.calls.AbstractCandidate
        public AbstractCallInfo getCallInfo() {
            AddToStdlibKt.shouldNotBeCalled$default(null, 1, null);
            throw null;
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof TypeCandidate) && Intrinsics.areEqual(getSymbol(), ((TypeCandidate) other).getSymbol());
        }

        public int hashCode() {
            return getSymbol().hashCode();
        }
    }
}
