package org.jetbrains.kotlin.fir.java;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.fir.CopyUtilsKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirAnnotationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.impl.FirLazyDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.references.builder.FirResolvedNamedReferenceBuilder;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.transformers.PlatformSupertypeUpdater;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirScopeKt;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirImplicitTypeRef;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitBuiltinTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: JvmSupertypeUpdater.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\rB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/JvmSupertypeUpdater;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/PlatformSupertypeUpdater;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "jvmRecordUpdater", "Lorg/jetbrains/kotlin/fir/java/JvmSupertypeUpdater$DelegatedConstructorCallTransformer;", "updateSupertypesIfNeeded", "", "firClass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "DelegatedConstructorCallTransformer", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmSupertypeUpdater extends PlatformSupertypeUpdater {
    private final DelegatedConstructorCallTransformer jvmRecordUpdater;
    private final FirSession session;

    public JvmSupertypeUpdater(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
        this.jvmRecordUpdater = new DelegatedConstructorCallTransformer(session);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.PlatformSupertypeUpdater
    public void updateSupertypesIfNeeded(FirClass firClass, ScopeSession scopeSession) {
        Intrinsics.checkNotNullParameter(firClass, "firClass");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        if (firClass instanceof FirRegularClass) {
            FirRegularClass firRegularClass = (FirRegularClass) firClass;
            if (firRegularClass.getStatus().isData() && FirAnnotationUtilsKt.hasAnnotationSafe(firClass, StandardClassIds.Annotations.INSTANCE.getJvmRecord(), this.session)) {
                List<FirTypeRef> superTypeRefs = firClass.getSuperTypeRefs();
                ArrayList arrayList = new ArrayList();
                Iterator<T> it = superTypeRefs.iterator();
                boolean z = false;
                boolean z2 = false;
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    FirResolvedTypeRef firResolvedTypeRefWithReplacedConeType$default = (FirTypeRef) it.next();
                    if ((firResolvedTypeRefWithReplacedConeType$default instanceof FirImplicitBuiltinTypeRef) && Intrinsics.areEqual(((FirImplicitBuiltinTypeRef) firResolvedTypeRefWithReplacedConeType$default).getId(), StandardClassIds.INSTANCE.getAny())) {
                        firResolvedTypeRefWithReplacedConeType$default = TypeUtilsKt.withReplacedConeType$default(firResolvedTypeRefWithReplacedConeType$default, DelegatedConstructorCallTransformer.INSTANCE.getRecordType(), null, 2, null);
                        z = true;
                    } else {
                        FirRegularClassSymbol regularClassSymbol = TypeUtilsKt.toRegularClassSymbol(FirTypeUtilsKt.getConeType(firResolvedTypeRefWithReplacedConeType$default), this.session);
                        if ((regularClassSymbol != null ? regularClassSymbol.getClassKind() : null) == ClassKind.CLASS) {
                            z2 = true;
                        }
                    }
                    arrayList.add(firResolvedTypeRefWithReplacedConeType$default);
                }
                ArrayList arrayList2 = arrayList;
                if (!z && !z2) {
                    arrayList2.add(TypeUtilsKt.toFirResolvedTypeRef$default(DelegatedConstructorCallTransformer.INSTANCE.getRecordType(), null, null, 3, null));
                }
                if (z || !z2) {
                    firClass.replaceSuperTypeRefs(arrayList2);
                    firRegularClass.transformDeclarations((FirTransformer<? super ScopeSession>) this.jvmRecordUpdater, scopeSession);
                }
            }
        }
    }

    /* JADX INFO: compiled from: JvmSupertypeUpdater.kt */
    @Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000 \u00162\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0016B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0002H\u0016J\u0018\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u0002H\u0016J'\u0010\u000e\u001a\u0002H\u000f\"\b\b\u0000\u0010\u000f*\u00020\u00102\u0006\u0010\u0011\u001a\u0002H\u000f2\u0006\u0010\n\u001a\u00020\u0002H\u0016¢\u0006\u0002\u0010\u0012J\u0018\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\n\u001a\u00020\u0002H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/JvmSupertypeUpdater$DelegatedConstructorCallTransformer;", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "transformConstructor", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "data", "transformDelegatedConstructorCall", "delegatedConstructorCall", "Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "transformElement", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Lorg/jetbrains/kotlin/fir/FirElement;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformRegularClass", "regularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Companion", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class DelegatedConstructorCallTransformer extends FirTransformer<ScopeSession> {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private static final ConeClassLikeType recordType = TypeConstructionUtilsKt.constructClassLikeType$default(StandardClassIds.Java.INSTANCE.getRecord(), new ConeTypeProjection[0], false, null, 4, null);
        private final FirSession session;

        @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
        public <E extends FirElement> E transformElement(E element, ScopeSession data) {
            Intrinsics.checkNotNullParameter(element, "element");
            Intrinsics.checkNotNullParameter(data, "data");
            return element;
        }

        /* JADX INFO: compiled from: JvmSupertypeUpdater.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/JvmSupertypeUpdater$DelegatedConstructorCallTransformer$Companion;", "", "()V", "recordType", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "getRecordType", "()Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final ConeClassLikeType getRecordType() {
                return DelegatedConstructorCallTransformer.recordType;
            }
        }

        public DelegatedConstructorCallTransformer(FirSession session) {
            Intrinsics.checkNotNullParameter(session, "session");
            this.session = session;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
        public FirStatement transformRegularClass(FirRegularClass regularClass, ScopeSession data) {
            Intrinsics.checkNotNullParameter(regularClass, "regularClass");
            Intrinsics.checkNotNullParameter(data, "data");
            return regularClass;
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
        public FirStatement transformConstructor(FirConstructor constructor, ScopeSession data) {
            Intrinsics.checkNotNullParameter(constructor, "constructor");
            Intrinsics.checkNotNullParameter(data, "data");
            return constructor.transformDelegatedConstructor(this, data);
        }

        /* JADX WARN: Code duplicated, block: B:26:0x004c  */
        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
        public FirStatement transformDelegatedConstructorCall(FirDelegatedConstructorCall delegatedConstructorCall, ScopeSession data) {
            FirTypeScope firTypeScopeUnsubstitutedScope;
            List<FirConstructorSymbol> declaredConstructors;
            Intrinsics.checkNotNullParameter(delegatedConstructorCall, "delegatedConstructorCall");
            Intrinsics.checkNotNullParameter(data, "data");
            if (!(delegatedConstructorCall instanceof FirLazyDelegatedConstructorCall)) {
                KtSourceElement source = delegatedConstructorCall.getSource();
                Object obj = null;
                if (Intrinsics.areEqual(source != null ? source.getKind() : null, KtFakeSourceElementKind.DelegatingConstructorCall.INSTANCE)) {
                    FirTypeRef constructedTypeRef = delegatedConstructorCall.getConstructedTypeRef();
                    if (constructedTypeRef instanceof FirImplicitTypeRef) {
                        delegatedConstructorCall.replaceConstructedTypeRef(CopyUtilsKt.resolvedTypeFromPrototype$default(constructedTypeRef, recordType, null, 2, null));
                    } else {
                        FirResolvedTypeRef firResolvedTypeRef = constructedTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) constructedTypeRef : null;
                        ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
                        if (!(type instanceof ConeKotlinType)) {
                            type = null;
                        }
                        if (type != null && ConeBuiltinTypeUtilsKt.isAny(type)) {
                            delegatedConstructorCall.replaceConstructedTypeRef(CopyUtilsKt.resolvedTypeFromPrototype$default(constructedTypeRef, recordType, null, 2, null));
                        }
                    }
                    FirRegularClassSymbol firRegularClassSymbol = LookupTagUtilsKt.toFirRegularClassSymbol(recordType.getLookupTag(), this.session);
                    if (firRegularClassSymbol != null && (firTypeScopeUnsubstitutedScope = FirKotlinScopeProviderKt.unsubstitutedScope((FirClassSymbol<?>) firRegularClassSymbol, this.session, data, false, (FirResolvePhase) null)) != null && (declaredConstructors = FirScopeKt.getDeclaredConstructors(firTypeScopeUnsubstitutedScope)) != null) {
                        for (Object obj2 : declaredConstructors) {
                            if (((FirConstructor) ((FirConstructorSymbol) obj2).getFir()).getValueParameters().isEmpty()) {
                                obj = obj2;
                                break;
                            }
                        }
                        obj = (FirConstructorSymbol) obj;
                    }
                    if (obj != null) {
                        FirResolvedNamedReferenceBuilder firResolvedNamedReferenceBuilder = new FirResolvedNamedReferenceBuilder();
                        Name shortClassName = StandardClassIds.Java.INSTANCE.getRecord().getShortClassName();
                        Intrinsics.checkNotNullExpressionValue(shortClassName, "getShortClassName(...)");
                        firResolvedNamedReferenceBuilder.setName(shortClassName);
                        firResolvedNamedReferenceBuilder.setResolvedSymbol((FirBasedSymbol) obj);
                        delegatedConstructorCall.replaceCalleeReference(firResolvedNamedReferenceBuilder.build());
                    }
                    return delegatedConstructorCall;
                }
            }
            return delegatedConstructorCall;
        }
    }
}
