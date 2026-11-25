package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOverloadabilityHelper;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirTypeSpecificityComparatorProvider;
import org.jetbrains.kotlin.fir.declarations.FirTypeSpecificityComparatorProviderKt;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.resolve.inference.InferenceComponentsKt;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.resolve.calls.results.FlatSignature;
import org.jetbrains.kotlin.resolve.calls.results.FlatSignatureKt;
import org.jetbrains.kotlin.resolve.calls.results.OverloadabilitySpecificityCallbacks;
import org.jetbrains.kotlin.resolve.calls.results.SimpleConstraintSystem;
import org.jetbrains.kotlin.resolve.calls.results.TypeSpecificityComparator;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: FirDeclarationOverloadabilityHelperImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0002J\u0016\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\u000bH\u0002J$\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u0018\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u000bH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/FirDeclarationOverloadabilityHelperImpl;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOverloadabilityHelper;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "createEmptyConstraintSystem", "Lorg/jetbrains/kotlin/resolve/calls/results/SimpleConstraintSystem;", "createSignature", "Lorg/jetbrains/kotlin/resolve/calls/results/FlatSignature;", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "declaration", "isNotLessSpecific", "", "sigA", "sigB", "isOverloadable", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDeclarationOverloadabilityHelperImpl implements FirDeclarationOverloadabilityHelper {
    private final FirSession session;

    public FirDeclarationOverloadabilityHelperImpl(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
    }

    public final FirSession getSession() {
        return this.session;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclarationOverloadabilityHelper
    public boolean isOverloadable(FirCallableDeclaration a, FirCallableDeclaration b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        FlatSignature<FirCallableDeclaration> flatSignatureCreateSignature = createSignature(a);
        FlatSignature<FirCallableDeclaration> flatSignatureCreateSignature2 = createSignature(b);
        return (isNotLessSpecific(flatSignatureCreateSignature, flatSignatureCreateSignature2) && isNotLessSpecific(flatSignatureCreateSignature2, flatSignatureCreateSignature)) ? false : true;
    }

    private final boolean isNotLessSpecific(FlatSignature<? extends FirCallableDeclaration> sigA, FlatSignature<? extends FirCallableDeclaration> sigB) {
        TypeSpecificityComparator.NONE typeSpecificityComparator;
        SimpleConstraintSystem simpleConstraintSystemCreateEmptyConstraintSystem = createEmptyConstraintSystem();
        OverloadabilitySpecificityCallbacks overloadabilitySpecificityCallbacks = OverloadabilitySpecificityCallbacks.INSTANCE;
        FirTypeSpecificityComparatorProvider typeSpecificityComparatorProvider = FirTypeSpecificityComparatorProviderKt.getTypeSpecificityComparatorProvider(this.session);
        if (typeSpecificityComparatorProvider == null || (typeSpecificityComparator = typeSpecificityComparatorProvider.getTypeSpecificityComparator()) == null) {
            typeSpecificityComparator = TypeSpecificityComparator.NONE.INSTANCE;
        }
        return FlatSignatureKt.isSignatureNotLessSpecific$default(simpleConstraintSystemCreateEmptyConstraintSystem, sigA, sigB, overloadabilitySpecificityCallbacks, typeSpecificityComparator, false, 16, null);
    }

    private final FlatSignature<FirCallableDeclaration> createSignature(FirCallableDeclaration declaration) {
        boolean z;
        FirFunction firFunction = declaration instanceof FirFunction ? (FirFunction) declaration : null;
        List<FirValueParameter> valueParameters = firFunction != null ? firFunction.getValueParameters() : null;
        if (valueParameters == null) {
            valueParameters = CollectionsKt.emptyList();
        }
        List<FirTypeParameterRef> typeParameters = declaration.getTypeParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(((FirTypeParameterRef) it.next()).getSymbol().getLookupTag());
        }
        ArrayList arrayList2 = arrayList;
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        Iterator<T> it2 = declaration.getContextReceivers().iterator();
        while (it2.hasNext()) {
            listCreateListBuilder.add(FirTypeUtilsKt.getConeType(((FirContextReceiver) it2.next()).getTypeRef()));
        }
        List list = listCreateListBuilder;
        FirReceiverParameter receiverParameter = declaration.getReceiverParameter();
        if (receiverParameter != null) {
            listCreateListBuilder.add(FirTypeUtilsKt.getConeType(receiverParameter.getTypeRef()));
        }
        List<FirValueParameter> list2 = valueParameters;
        Iterator<T> it3 = list2.iterator();
        while (it3.hasNext()) {
            list.add(FirTypeUtilsKt.getConeType(((FirValueParameter) it3.next()).getReturnTypeRef()));
        }
        Unit unit = Unit.INSTANCE;
        List listBuild = CollectionsKt.build(listCreateListBuilder);
        boolean z2 = declaration.getReceiverParameter() != null;
        int size = declaration.getContextReceivers().size();
        if ((list2 instanceof Collection) && list2.isEmpty()) {
            z = false;
        } else {
            Iterator<T> it4 = list2.iterator();
            while (it4.hasNext()) {
                if (((FirValueParameter) it4.next()).getIsVararg()) {
                    z = true;
                }
            }
            z = false;
        }
        return new FlatSignature<>(declaration, arrayList2, (List<? extends KotlinTypeMarker>) listBuild, z2, size, z, 0, declaration.getStatus().isExpect(), declaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic);
    }

    private final SimpleConstraintSystem createEmptyConstraintSystem() {
        return new ConeSimpleConstraintSystemImpl(InferenceComponentsKt.getInferenceComponents(this.session).createConstraintSystem(), this.session);
    }
}
