package org.jetbrains.kotlin.fir.backend.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.serialization.FirElementAwareStringTable;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.metadata.jvm.serialization.JvmStringTable;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: FirMetadataSerializer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u00012\u00020\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"org/jetbrains/kotlin/fir/backend/jvm/FirMetadataSerializerKt$makeLocalFirMetadataSerializerForMetadataSource$stringTable$1", "Lorg/jetbrains/kotlin/metadata/jvm/serialization/JvmStringTable;", "Lorg/jetbrains/kotlin/fir/serialization/FirElementAwareStringTable;", "getLocalClassIdReplacement", "Lorg/jetbrains/kotlin/name/ClassId;", "firClass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "jvm-backend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirMetadataSerializerKt$makeLocalFirMetadataSerializerForMetadataSource$stringTable$1 extends JvmStringTable implements FirElementAwareStringTable {
    final /* synthetic */ FirSession $session;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    FirMetadataSerializerKt$makeLocalFirMetadataSerializerForMetadataSource$stringTable$1(FirSession firSession) {
        super(null);
        this.$session = firSession;
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirElementAwareStringTable
    public ClassId getLocalClassIdReplacement(FirClass firClass) {
        ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassForLocal;
        FirRegularClass firRegularClass;
        Intrinsics.checkNotNullParameter(firClass, "firClass");
        FirRegularClass firRegularClass2 = firClass instanceof FirRegularClass ? (FirRegularClass) firClass : null;
        if (firRegularClass2 != null && (coneClassLikeLookupTagContainingClassForLocal = ClassMembersKt.containingClassForLocal(firRegularClass2)) != null && (firRegularClass = LookupTagUtilsKt.toFirRegularClass(coneClassLikeLookupTagContainingClassForLocal, this.$session)) != null) {
            firClass = firRegularClass;
        }
        return firClass.getSymbol().getClassId();
    }
}
