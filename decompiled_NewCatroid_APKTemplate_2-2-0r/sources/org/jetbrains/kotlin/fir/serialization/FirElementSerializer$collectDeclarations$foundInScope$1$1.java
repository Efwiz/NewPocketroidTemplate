package org.jetbrains.kotlin.fir.serialization;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: Add missing generic type declarations: [S] */
/* JADX INFO: compiled from: FirElementSerializer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003\"\f\b\u0001\u0010\u0004*\u0006\u0012\u0002\b\u00030\u00052\u0006\u0010\u0006\u001a\u0002H\u0004H\n¢\u0006\u0004\b\u0007\u0010\b"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "it", "invoke", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;)V"}, k = 3, mv = {1, 9, 0}, xi = 48)
final class FirElementSerializer$collectDeclarations$foundInScope$1$1<S> extends Lambda implements Function1<S, Unit> {
    final /* synthetic */ List<T> $this_buildList;
    final /* synthetic */ FirClass $this_collectDeclarations;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirElementSerializer$collectDeclarations$foundInScope$1$1(FirClass firClass, List<T> list) {
        super(1);
        this.$this_collectDeclarations = firClass;
        this.$this_buildList = list;
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Object obj) {
        invoke((FirCallableSymbol) obj);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Code duplicated, block: B:18:0x005b  */
    /* JADX WARN: Incorrect types in method signature: (TS;)V */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    public final void invoke(FirCallableSymbol it) {
        boolean z;
        Modality modality;
        Intrinsics.checkNotNullParameter(it, "it");
        D fir = it.getFir();
        Intrinsics.reifiedOperationMarker(1, ExifInterface.GPS_DIRECTION_TRUE);
        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) fir;
        ConeClassLikeLookupTag coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull = ClassMembersKt.dispatchReceiverClassLookupTagOrNull(firCallableDeclaration);
        FirClass firClass = this.$this_collectDeclarations;
        if ((firClass instanceof FirRegularClass) && (((FirRegularClass) firClass).getStatus().isData() || this.$this_collectDeclarations.getStatus().isInline())) {
            z = (!Intrinsics.areEqual(coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull != null ? coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull.getClassId() : null, StandardClassIds.INSTANCE.getAny()) || (modality = firCallableDeclaration.getStatus().getModality()) == null || modality == Modality.FINAL) ? false : true;
        }
        if (ClassMembersKt.isSubstitutionOrIntersectionOverride(firCallableDeclaration)) {
            if (!z) {
                return;
            }
        } else if (!firCallableDeclaration.getStatus().isStatic() && !(firCallableDeclaration instanceof FirConstructor) && !Intrinsics.areEqual(coneClassLikeLookupTagDispatchReceiverClassLookupTagOrNull, this.$this_collectDeclarations.getSymbol().getLookupTag()) && !z) {
            return;
        }
        this.$this_buildList.add((T) firCallableDeclaration);
    }
}
