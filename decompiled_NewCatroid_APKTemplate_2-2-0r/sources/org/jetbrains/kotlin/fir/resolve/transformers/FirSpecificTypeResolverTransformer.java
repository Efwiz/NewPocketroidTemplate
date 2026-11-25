package org.jetbrains.kotlin.fir.resolve.transformers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKind;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.fir.FirLookupTrackerComponent;
import org.jetbrains.kotlin.fir.FirLookupTrackerComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeSimpleDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.ConeUnexpectedTypeArgumentsError;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.resolve.FirTypeResolutionResult;
import org.jetbrains.kotlin.fir.resolve.FirTypeResolver;
import org.jetbrains.kotlin.fir.resolve.FirTypeResolverKt;
import org.jetbrains.kotlin.fir.resolve.SupertypeSupplier;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeUnsupportedDefaultValueInFunctionType;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.FirFunctionTypeRef;
import org.jetbrains.kotlin.fir.types.FirImplicitTypeRef;
import org.jetbrains.kotlin.fir.types.FirQualifierPart;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirUserTypeRef;
import org.jetbrains.kotlin.fir.types.builder.FirErrorTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.builder.FirUserTypeRefBuilder;
import org.jetbrains.kotlin.util.PrivateForInline;

/* JADX INFO: compiled from: FirSpecificTypeResolverTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B+\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0018\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u0002H\u0016J\u0018\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010\u001f\u001a\u00020\u0002H\u0016J\u0018\u0010$\u001a\u00020\u001c2\u0006\u0010%\u001a\u00020&2\u0006\u0010\u001f\u001a\u00020\u0002H\u0016J\u0018\u0010'\u001a\u00020\u001c2\u0006\u0010(\u001a\u00020!2\u0006\u0010\u001f\u001a\u00020\u0002H\u0016J*\u0010)\u001a\u00020!2\u0006\u0010*\u001a\u00020\u001c2\u0006\u0010+\u001a\u00020,2\b\u0010-\u001a\u0004\u0018\u00010.2\u0006\u0010/\u001a\u00020\u0002H\u0002J\u0018\u00100\u001a\u00020!2\u0006\u0010*\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\u0002H\u0016J\u0018\u00101\u001a\u0002022\u0006\u00103\u001a\u0002042\u0006\u0010\u001f\u001a\u00020\u0002H\u0016J\u001a\u00105\u001a\u0004\u0018\u00010\u001c2\u0006\u0010*\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\u0002H\u0002J/\u00106\u001a\u0002H7\"\u0004\b\u0000\u001072\b\b\u0002\u00108\u001a\u00020\u00062\f\u00109\u001a\b\u0012\u0004\u0012\u0002H70:H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010;J:\u0010<\u001a\u0002H7\"\u0004\b\u0000\u001072\b\u0010=\u001a\u0004\u0018\u00010\u00122\u0017\u00109\u001a\u0013\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u0002H70>¢\u0006\u0002\b?H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010@J%\u0010A\u001a\u0002H7\"\u0004\b\u0000\u001072\f\u00109\u001a\b\u0012\u0004\u0012\u0002H70:H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010BJ\u001c\u0010C\u001a\u00020D*\u00020\u00002\u0006\u0010*\u001a\u00020\u001c2\u0006\u0010/\u001a\u00020\u0002H\u0002J\u000e\u0010E\u001a\u0004\u0018\u00010,*\u00020,H\u0002R$\u0010\f\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006@GX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u0004\u0018\u00010\u00128\u0006@\u0006X\u0087\u000e¢\u0006\b\n\u0000\u0012\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006@GX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u000e\"\u0004\b\u0016\u0010\u0010R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006F"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/FirSpecificTypeResolverTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirAbstractTreeTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ScopeClassDeclaration;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "errorTypeAsResolved", "", "resolveDeprecations", "supertypeSupplier", "Lorg/jetbrains/kotlin/fir/resolve/SupertypeSupplier;", "(Lorg/jetbrains/kotlin/fir/FirSession;ZZLorg/jetbrains/kotlin/fir/resolve/SupertypeSupplier;)V", "<set-?>", "areBareTypesAllowed", "getAreBareTypesAllowed", "()Z", "setAreBareTypesAllowed", "(Z)V", "currentFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "getCurrentFile$annotations", "()V", "isOperandOfIsOperator", "setOperandOfIsOperator", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "typeResolver", "Lorg/jetbrains/kotlin/fir/resolve/FirTypeResolver;", "transformErrorTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "errorTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirErrorTypeRef;", "data", "transformFunctionTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "functionTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirFunctionTypeRef;", "transformImplicitTypeRef", "implicitTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirImplicitTypeRef;", "transformResolvedTypeRef", "resolvedTypeRef", "transformType", "typeRef", "resolvedType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "diagnostic", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "scopeClassDeclaration", "transformTypeRef", "transformValueParameter", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "valueParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "tryCalculatingPartiallyResolvedTypeRef", "withBareTypes", "R", "allowed", "block", "Lkotlin/Function0;", "(ZLkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withFile", "file", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "(Lorg/jetbrains/kotlin/fir/declarations/FirFile;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "withIsOperandOfIsOperator", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "resolveType", "Lorg/jetbrains/kotlin/fir/resolve/FirTypeResolutionResult;", "takeIfAcceptable", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSpecificTypeResolverTransformer extends FirAbstractTreeTransformer<ScopeClassDeclaration> {
    private boolean areBareTypesAllowed;
    public FirFile currentFile;
    private final boolean errorTypeAsResolved;
    private boolean isOperandOfIsOperator;
    private final boolean resolveDeprecations;
    private final FirSession session;
    private final SupertypeSupplier supertypeSupplier;
    private final FirTypeResolver typeResolver;

    @PrivateForInline
    public static /* synthetic */ void getCurrentFile$annotations() {
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.FirAbstractPhaseTransformer
    public FirSession getSession() {
        return this.session;
    }

    public /* synthetic */ FirSpecificTypeResolverTransformer(FirSession firSession, boolean z, boolean z2, SupertypeSupplier.Default r5, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, (i & 2) != 0 ? true : z, (i & 4) != 0 ? true : z2, (i & 8) != 0 ? SupertypeSupplier.Default.INSTANCE : r5);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirSpecificTypeResolverTransformer(FirSession session, boolean z, boolean z2, SupertypeSupplier supertypeSupplier) {
        super(FirResolvePhase.SUPER_TYPES);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(supertypeSupplier, "supertypeSupplier");
        this.session = session;
        this.errorTypeAsResolved = z;
        this.resolveDeprecations = z2;
        this.supertypeSupplier = supertypeSupplier;
        this.typeResolver = FirTypeResolverKt.getTypeResolver(getSession());
    }

    public final boolean getAreBareTypesAllowed() {
        return this.areBareTypesAllowed;
    }

    @PrivateForInline
    public final void setAreBareTypesAllowed(boolean z) {
        this.areBareTypesAllowed = z;
    }

    public static /* synthetic */ Object withBareTypes$default(FirSpecificTypeResolverTransformer firSpecificTypeResolverTransformer, boolean z, Function0 block, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        Intrinsics.checkNotNullParameter(block, "block");
        boolean areBareTypesAllowed = firSpecificTypeResolverTransformer.getAreBareTypesAllowed();
        firSpecificTypeResolverTransformer.setAreBareTypesAllowed(z);
        try {
            return block.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            firSpecificTypeResolverTransformer.setAreBareTypesAllowed(areBareTypesAllowed);
            InlineMarker.finallyEnd(1);
        }
    }

    public final <R> R withBareTypes(boolean allowed, Function0<? extends R> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        boolean areBareTypesAllowed = getAreBareTypesAllowed();
        setAreBareTypesAllowed(allowed);
        try {
            return block.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            setAreBareTypesAllowed(areBareTypesAllowed);
            InlineMarker.finallyEnd(1);
        }
    }

    /* JADX INFO: renamed from: isOperandOfIsOperator, reason: from getter */
    public final boolean getIsOperandOfIsOperator() {
        return this.isOperandOfIsOperator;
    }

    @PrivateForInline
    public final void setOperandOfIsOperator(boolean z) {
        this.isOperandOfIsOperator = z;
    }

    public final <R> R withIsOperandOfIsOperator(Function0<? extends R> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        boolean isOperandOfIsOperator = getIsOperandOfIsOperator();
        setOperandOfIsOperator(true);
        try {
            return block.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            setOperandOfIsOperator(isOperandOfIsOperator);
            InlineMarker.finallyEnd(1);
        }
    }

    public final <R> R withFile(FirFile file, Function1<? super FirSpecificTypeResolverTransformer, ? extends R> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        FirFile firFile = this.currentFile;
        this.currentFile = file;
        try {
            return block.invoke(this);
        } finally {
            InlineMarker.finallyStart(1);
            this.currentFile = firFile;
            InlineMarker.finallyEnd(1);
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirResolvedTypeRef transformTypeRef(FirTypeRef typeRef, ScopeClassDeclaration data) {
        Intrinsics.checkNotNullParameter(typeRef, "typeRef");
        Intrinsics.checkNotNullParameter(data, "data");
        Iterable<FirScope> scopes = data.getScopes();
        ArrayList arrayList = new ArrayList();
        Iterator<FirScope> it = scopes.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, it.next().getScopeOwnerLookupNames());
        }
        ArrayList arrayList2 = arrayList;
        FirLookupTrackerComponent lookupTracker = FirLookupTrackerComponentKt.getLookupTracker(getSession());
        if (lookupTracker != null) {
            FirFile firFile = this.currentFile;
            FirLookupTrackerComponentKt.recordTypeLookup(lookupTracker, typeRef, arrayList2, firFile != null ? firFile.getSource() : null);
        }
        boolean areBareTypesAllowed = getAreBareTypesAllowed();
        setAreBareTypesAllowed(false);
        try {
            typeRef.transformChildren(this, data);
            FirTypeResolutionResult firTypeResolutionResultResolveType = resolveType(this, typeRef, data);
            return transformType(typeRef, firTypeResolutionResultResolveType.getType(), firTypeResolutionResultResolveType.getDiagnostic(), data);
        } finally {
            setAreBareTypesAllowed(areBareTypesAllowed);
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirResolvedTypeRef transformFunctionTypeRef(FirFunctionTypeRef functionTypeRef, ScopeClassDeclaration data) {
        Intrinsics.checkNotNullParameter(functionTypeRef, "functionTypeRef");
        Intrinsics.checkNotNullParameter(data, "data");
        functionTypeRef.transformChildren(this, data);
        Iterable<FirScope> scopes = data.getScopes();
        ArrayList arrayList = new ArrayList();
        Iterator<FirScope> it = scopes.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, it.next().getScopeOwnerLookupNames());
        }
        ArrayList arrayList2 = arrayList;
        FirLookupTrackerComponent lookupTracker = FirLookupTrackerComponentKt.getLookupTracker(getSession());
        if (lookupTracker != null) {
            FirFunctionTypeRef firFunctionTypeRef = functionTypeRef;
            FirFile firFile = this.currentFile;
            FirLookupTrackerComponentKt.recordTypeLookup(lookupTracker, firFunctionTypeRef, arrayList2, firFile != null ? firFile.getSource() : null);
        }
        FirFunctionTypeRef firFunctionTypeRef2 = functionTypeRef;
        FirTypeResolutionResult firTypeResolutionResultResolveType = resolveType(this, firFunctionTypeRef2, data);
        ConeKotlinType coneKotlinTypeTakeIfAcceptable = takeIfAcceptable(firTypeResolutionResultResolveType.getType());
        ConeSimpleDiagnostic diagnostic = firTypeResolutionResultResolveType.getDiagnostic();
        if (coneKotlinTypeTakeIfAcceptable != null && !(coneKotlinTypeTakeIfAcceptable instanceof ConeErrorType) && diagnostic == null) {
            FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
            firResolvedTypeRefBuilder.setSource(functionTypeRef.getSource());
            firResolvedTypeRefBuilder.setType(coneKotlinTypeTakeIfAcceptable);
            CollectionsKt.addAll(firResolvedTypeRefBuilder.getAnnotations(), functionTypeRef.getAnnotations());
            firResolvedTypeRefBuilder.setDelegatedTypeRef(firFunctionTypeRef2);
            return firResolvedTypeRefBuilder.mo6809build();
        }
        FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
        firErrorTypeRefBuilder.setSource(functionTypeRef.getSource());
        if (coneKotlinTypeTakeIfAcceptable != null) {
            firErrorTypeRefBuilder.setType(coneKotlinTypeTakeIfAcceptable);
        }
        if (diagnostic == null) {
            ConeErrorType coneErrorType = coneKotlinTypeTakeIfAcceptable instanceof ConeErrorType ? (ConeErrorType) coneKotlinTypeTakeIfAcceptable : null;
            diagnostic = coneErrorType != null ? coneErrorType.getDiagnostic() : null;
            if (diagnostic == null) {
                diagnostic = new ConeSimpleDiagnostic("Unresolved function type: " + UtilsKt.render(functionTypeRef), null, 2, null);
            }
        }
        firErrorTypeRefBuilder.setDiagnostic(diagnostic);
        return firErrorTypeRefBuilder.mo6809build();
    }

    private final FirTypeResolutionResult resolveType(FirSpecificTypeResolverTransformer firSpecificTypeResolverTransformer, FirTypeRef firTypeRef, ScopeClassDeclaration scopeClassDeclaration) {
        return firSpecificTypeResolverTransformer.typeResolver.resolveType(firTypeRef, scopeClassDeclaration, firSpecificTypeResolverTransformer.areBareTypesAllowed, firSpecificTypeResolverTransformer.isOperandOfIsOperator, firSpecificTypeResolverTransformer.resolveDeprecations, firSpecificTypeResolverTransformer.currentFile, firSpecificTypeResolverTransformer.supertypeSupplier);
    }

    private final FirResolvedTypeRef transformType(FirTypeRef typeRef, ConeKotlinType resolvedType, ConeDiagnostic diagnostic, ScopeClassDeclaration scopeClassDeclaration) {
        if (!(resolvedType instanceof ConeErrorType)) {
            if (diagnostic != null) {
                FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
                firErrorTypeRefBuilder.setSource(typeRef.getSource());
                firErrorTypeRefBuilder.setDiagnostic(diagnostic);
                firErrorTypeRefBuilder.setType(resolvedType);
                firErrorTypeRefBuilder.setDelegatedTypeRef(typeRef);
                firErrorTypeRefBuilder.setPartiallyResolvedTypeRef(tryCalculatingPartiallyResolvedTypeRef(typeRef, scopeClassDeclaration));
                return firErrorTypeRefBuilder.mo6809build();
            }
            FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
            firResolvedTypeRefBuilder.setSource(typeRef.getSource());
            firResolvedTypeRefBuilder.setType(resolvedType);
            CollectionsKt.addAll(firResolvedTypeRefBuilder.getAnnotations(), typeRef.getAnnotations());
            firResolvedTypeRefBuilder.setDelegatedTypeRef(typeRef);
            return firResolvedTypeRefBuilder.mo6809build();
        }
        FirErrorTypeRefBuilder firErrorTypeRefBuilder2 = new FirErrorTypeRefBuilder();
        KtSourceElement source = typeRef.getSource();
        KtSourceElementKind kind = source != null ? source.getKind() : null;
        ConeErrorType coneErrorType = (ConeErrorType) resolvedType;
        ConeDiagnostic diagnostic2 = coneErrorType.getDiagnostic();
        ConeUnexpectedTypeArgumentsError coneUnexpectedTypeArgumentsError = diagnostic2 instanceof ConeUnexpectedTypeArgumentsError ? (ConeUnexpectedTypeArgumentsError) diagnostic2 : null;
        KtSourceElement source2 = coneUnexpectedTypeArgumentsError != null ? coneUnexpectedTypeArgumentsError.getSource() : null;
        if (source2 != null) {
            if (kind instanceof KtFakeSourceElementKind) {
                source2 = KtSourceElementKt.fakeElement$default(source2, (KtFakeSourceElementKind) kind, 0, 0, 6, null);
            }
        } else {
            source2 = typeRef.getSource();
        }
        firErrorTypeRefBuilder2.setSource(source2);
        firErrorTypeRefBuilder2.setDelegatedTypeRef(typeRef);
        firErrorTypeRefBuilder2.setType(resolvedType);
        firErrorTypeRefBuilder2.setPartiallyResolvedTypeRef(tryCalculatingPartiallyResolvedTypeRef(typeRef, scopeClassDeclaration));
        firErrorTypeRefBuilder2.setDiagnostic(coneErrorType.getDiagnostic());
        return firErrorTypeRefBuilder2.mo6809build();
    }

    private final FirTypeRef tryCalculatingPartiallyResolvedTypeRef(FirTypeRef typeRef, ScopeClassDeclaration data) {
        if (!(typeRef instanceof FirUserTypeRef)) {
            return null;
        }
        List<FirQualifierPart> qualifier = ((FirUserTypeRef) typeRef).getQualifier();
        if (qualifier.size() <= 1) {
            return null;
        }
        List mutableList = CollectionsKt.toMutableList((Collection) qualifier);
        while (mutableList.size() > 1) {
            CollectionsKt.removeLast(mutableList);
            FirUserTypeRefBuilder firUserTypeRefBuilder = new FirUserTypeRefBuilder();
            CollectionsKt.addAll(firUserTypeRefBuilder.getQualifier(), mutableList);
            firUserTypeRefBuilder.setMarkedNullable(false);
            FirUserTypeRef firUserTypeRefMo6809build = firUserTypeRefBuilder.mo6809build();
            FirTypeResolutionResult firTypeResolutionResultResolveType = resolveType(this, firUserTypeRefMo6809build, data);
            ConeKotlinType type = firTypeResolutionResultResolveType.getType();
            ConeDiagnostic diagnostic = firTypeResolutionResultResolveType.getDiagnostic();
            if (!(type instanceof ConeErrorType) && diagnostic == null) {
                FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
                firResolvedTypeRefBuilder.setSource(((FirQualifierPart) CollectionsKt.last(mutableList)).getSource());
                firResolvedTypeRefBuilder.setType(type);
                firResolvedTypeRefBuilder.setDelegatedTypeRef(firUserTypeRefMo6809build);
                return firResolvedTypeRefBuilder.mo6809build();
            }
        }
        return null;
    }

    private final ConeKotlinType takeIfAcceptable(ConeKotlinType coneKotlinType) {
        if (this.errorTypeAsResolved || !(coneKotlinType instanceof ConeErrorType)) {
            return coneKotlinType;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirTypeRef transformResolvedTypeRef(FirResolvedTypeRef resolvedTypeRef, ScopeClassDeclaration data) {
        Intrinsics.checkNotNullParameter(resolvedTypeRef, "resolvedTypeRef");
        Intrinsics.checkNotNullParameter(data, "data");
        return resolvedTypeRef;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirTypeRef transformErrorTypeRef(FirErrorTypeRef errorTypeRef, ScopeClassDeclaration data) {
        Intrinsics.checkNotNullParameter(errorTypeRef, "errorTypeRef");
        Intrinsics.checkNotNullParameter(data, "data");
        errorTypeRef.transformPartiallyResolvedTypeRef(this, data);
        return errorTypeRef;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirTypeRef transformImplicitTypeRef(FirImplicitTypeRef implicitTypeRef, ScopeClassDeclaration data) {
        Intrinsics.checkNotNullParameter(implicitTypeRef, "implicitTypeRef");
        Intrinsics.checkNotNullParameter(data, "data");
        return implicitTypeRef;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformValueParameter(FirValueParameter valueParameter, ScopeClassDeclaration data) {
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        Intrinsics.checkNotNullParameter(data, "data");
        FirValueParameter firValueParameter = (FirValueParameter) transformElement(valueParameter, data);
        FirExpression defaultValue = firValueParameter.getDefaultValue();
        if (defaultValue != null) {
            FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
            firErrorTypeRefBuilder.setDiagnostic(new ConeUnsupportedDefaultValueInFunctionType(defaultValue.getSource()));
            defaultValue.replaceTypeRef(firErrorTypeRefBuilder.mo6809build());
        }
        return firValueParameter;
    }
}
