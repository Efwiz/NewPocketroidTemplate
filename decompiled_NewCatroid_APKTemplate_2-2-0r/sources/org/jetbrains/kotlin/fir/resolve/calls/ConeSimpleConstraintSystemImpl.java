package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.Collection;
import java.util.LinkedHashMap;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.resolve.inference.ConeTypeParameterBasedTypeVariable;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.substitution.SubstitutorsKt;
import org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeTypeVariableType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.utils.exceptions.FirExceptionUtilsKt;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.SimpleConstraintSystemConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.results.SimpleConstraintSystem;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder;
import org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments;

/* JADX INFO: compiled from: ConeOverloadConflictResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0016H\u0016J\b\u0010\u0018\u001a\u00020\bH\u0016J\u0016\u0010\u0019\u001a\u00020\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cH\u0016R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/ConeSimpleConstraintSystemImpl;", "Lorg/jetbrains/kotlin/resolve/calls/results/SimpleConstraintSystem;", "system", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintSystemImpl;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintSystemImpl;Lorg/jetbrains/kotlin/fir/FirSession;)V", "captureFromArgument", "", "getCaptureFromArgument", "()Z", "context", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "getContext", "()Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "getSystem", "()Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintSystemImpl;", "addSubtypeConstraint", "", "subType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "superType", "hasContradiction", "registerTypeVariables", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", "typeParameters", "", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeSimpleConstraintSystemImpl implements SimpleConstraintSystem {
    private final FirSession session;
    private final NewConstraintSystemImpl system;

    @Override // org.jetbrains.kotlin.resolve.calls.results.SimpleConstraintSystem
    public boolean getCaptureFromArgument() {
        return true;
    }

    public ConeSimpleConstraintSystemImpl(NewConstraintSystemImpl system, FirSession session) {
        Intrinsics.checkNotNullParameter(system, "system");
        Intrinsics.checkNotNullParameter(session, "session");
        this.system = system;
        this.session = session;
    }

    public final FirSession getSession() {
        return this.session;
    }

    public final NewConstraintSystemImpl getSystem() {
        return this.system;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.SimpleConstraintSystem
    public TypeSubstitutorMarker registerTypeVariables(Collection<? extends TypeParameterMarker> typeParameters) {
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        getContext();
        NewConstraintSystemImpl builder = this.system.getBuilder();
        Collection<? extends TypeParameterMarker> collection = typeParameters;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(collection, 10)), 16));
        for (TypeParameterMarker typeParameterMarker : collection) {
            Intrinsics.checkNotNull(typeParameterMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag");
            ConeTypeParameterLookupTag coneTypeParameterLookupTag = (ConeTypeParameterLookupTag) typeParameterMarker;
            FirTypeParameterSymbol typeParameterSymbol = coneTypeParameterLookupTag.getTypeParameterSymbol();
            if (!(typeParameterMarker instanceof ConeTypeParameterLookupTag)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            ConeTypeParameterBasedTypeVariable coneTypeParameterBasedTypeVariable = new ConeTypeParameterBasedTypeVariable(coneTypeParameterLookupTag.getTypeParameterSymbol());
            builder.registerVariable(coneTypeParameterBasedTypeVariable);
            linkedHashMap.put(typeParameterSymbol, coneTypeParameterBasedTypeVariable.getDefaultType());
        }
        ConeSubstitutor coneSubstitutorSubstitutorByMap = SubstitutorsKt.substitutorByMap(linkedHashMap, this.session);
        for (TypeParameterMarker typeParameterMarker2 : typeParameters) {
            if (!(typeParameterMarker2 instanceof ConeTypeParameterLookupTag)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            ConeTypeParameterLookupTag coneTypeParameterLookupTag2 = (ConeTypeParameterLookupTag) typeParameterMarker2;
            for (FirResolvedTypeRef firResolvedTypeRef : coneTypeParameterLookupTag2.getSymbol().getResolvedBounds()) {
                ConeTypeVariableType coneTypeVariableType = (ConeTypeVariableType) linkedHashMap.get(coneTypeParameterLookupTag2.getTypeParameterSymbol());
                if (coneTypeVariableType != null) {
                    addSubtypeConstraint(coneTypeVariableType, coneSubstitutorSubstitutorByMap.substituteOrSelf(FirTypeUtilsKt.getConeType(firResolvedTypeRef)));
                } else {
                    KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("No " + coneTypeParameterLookupTag2.getSymbol().getFir().getClass() + " in substitution map", null);
                    ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
                    FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, "typeParameter", coneTypeParameterLookupTag2.getSymbol().getFir());
                    kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
                    throw kotlinIllegalArgumentExceptionWithAttachments;
                }
            }
        }
        return coneSubstitutorSubstitutorByMap;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.SimpleConstraintSystem
    public void addSubtypeConstraint(KotlinTypeMarker subType, KotlinTypeMarker superType) {
        Intrinsics.checkNotNullParameter(subType, "subType");
        Intrinsics.checkNotNullParameter(superType, "superType");
        this.system.addSubtypeConstraint(subType, superType, SimpleConstraintSystemConstraintPosition.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.SimpleConstraintSystem
    public boolean hasContradiction() {
        return this.system.getHasContradiction();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.SimpleConstraintSystem
    public TypeSystemInferenceExtensionContext getContext() {
        return this.system;
    }
}
