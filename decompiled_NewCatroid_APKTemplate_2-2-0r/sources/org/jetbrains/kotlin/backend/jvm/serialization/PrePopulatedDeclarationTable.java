package org.jetbrains.kotlin.backend.jvm.serialization;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.backend.common.overrides.FakeOverrideDeclarationTable;
import org.jetbrains.kotlin.backend.common.serialization.DeclarationTable;
import org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureSerializer;
import org.jetbrains.kotlin.backend.common.serialization.signature.PublicIdSignatureComputer;
import org.jetbrains.kotlin.ir.backend.jvm.serialization.JvmIrMangler;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.IdSignature;

/* JADX INFO: compiled from: deserializeLazyDeclarations.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\u0010\u0006J\u0012\u0010\b\u001a\u0004\u0018\u00010\u00042\u0006\u0010\t\u001a\u00020\nH\u0014R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/serialization/PrePopulatedDeclarationTable;", "Lorg/jetbrains/kotlin/backend/common/overrides/FakeOverrideDeclarationTable;", "sig2symbol", "", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "(Ljava/util/Map;)V", "symbol2Sig", "tryComputeBackendSpecificSignature", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "ir.serialization.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PrePopulatedDeclarationTable extends FakeOverrideDeclarationTable {
    private final Map<IrSymbol, IdSignature> symbol2Sig;

    /* JADX INFO: renamed from: org.jetbrains.kotlin.backend.jvm.serialization.PrePopulatedDeclarationTable$1, reason: invalid class name */
    /* JADX INFO: compiled from: deserializeLazyDeclarations.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function2<PublicIdSignatureComputer, DeclarationTable, IdSignatureSerializer> {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        AnonymousClass1() {
            super(2);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "<init>(Lorg/jetbrains/kotlin/backend/common/serialization/signature/PublicIdSignatureComputer;Lorg/jetbrains/kotlin/backend/common/serialization/DeclarationTable;)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "<init>";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(IdSignatureSerializer.class);
        }

        @Override // kotlin.jvm.functions.Function2
        public final IdSignatureSerializer invoke(PublicIdSignatureComputer p0, DeclarationTable p1) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            Intrinsics.checkNotNullParameter(p1, "p1");
            return new IdSignatureSerializer(p0, p1);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PrePopulatedDeclarationTable(Map<IdSignature, ? extends IrSymbol> sig2symbol) {
        super(JvmIrMangler.INSTANCE, null, AnonymousClass1.INSTANCE, 2, null);
        Intrinsics.checkNotNullParameter(sig2symbol, "sig2symbol");
        Set<Map.Entry<IdSignature, ? extends IrSymbol>> setEntrySet = sig2symbol.entrySet();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(setEntrySet, 10)), 16));
        Iterator<T> it = setEntrySet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Pair pair = TuplesKt.to((IrSymbol) entry.getValue(), (IdSignature) entry.getKey());
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        this.symbol2Sig = linkedHashMap;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.DeclarationTable
    protected IdSignature tryComputeBackendSpecificSignature(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IdSignature idSignature = this.symbol2Sig.get(declaration.getSymbol());
        return idSignature != null ? idSignature : super.tryComputeBackendSpecificSignature(declaration);
    }
}
