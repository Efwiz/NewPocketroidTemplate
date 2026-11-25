package org.jetbrains.kotlin.backend.common.overrides;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.DeclarationTable;
import org.jetbrains.kotlin.backend.common.serialization.signature.IdSignatureSerializer;
import org.jetbrains.kotlin.backend.common.serialization.signature.PublicIdSignatureComputer;
import org.jetbrains.kotlin.ir.util.KotlinMangler;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: FakeOverrides.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\u0018\u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\t0\u0007¢\u0006\u0002\u0010\nJ\u0006\u0010\u0011\u001a\u00020\u0012R\u0014\u0010\u000b\u001a\u00020\u0005X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/overrides/FakeOverrideDeclarationTable;", "Lorg/jetbrains/kotlin/backend/common/serialization/DeclarationTable;", "mangler", "Lorg/jetbrains/kotlin/ir/util/KotlinMangler$IrMangler;", "globalTable", "Lorg/jetbrains/kotlin/backend/common/overrides/FakeOverrideGlobalDeclarationTable;", "signatureSerializerFactory", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/backend/common/serialization/signature/PublicIdSignatureComputer;", "Lorg/jetbrains/kotlin/backend/common/serialization/signature/IdSignatureSerializer;", "(Lorg/jetbrains/kotlin/ir/util/KotlinMangler$IrMangler;Lorg/jetbrains/kotlin/backend/common/overrides/FakeOverrideGlobalDeclarationTable;Lkotlin/jvm/functions/Function2;)V", "globalDeclarationTable", "getGlobalDeclarationTable", "()Lorg/jetbrains/kotlin/backend/common/overrides/FakeOverrideGlobalDeclarationTable;", "signaturer", "getSignaturer", "()Lorg/jetbrains/kotlin/backend/common/serialization/signature/IdSignatureSerializer;", LineReader.CLEAR, "", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FakeOverrideDeclarationTable extends DeclarationTable {
    private final FakeOverrideGlobalDeclarationTable globalDeclarationTable;
    private final IdSignatureSerializer signaturer;

    public /* synthetic */ FakeOverrideDeclarationTable(KotlinMangler.IrMangler irMangler, FakeOverrideGlobalDeclarationTable fakeOverrideGlobalDeclarationTable, Function2 function2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(irMangler, (i & 2) != 0 ? new FakeOverrideGlobalDeclarationTable(irMangler) : fakeOverrideGlobalDeclarationTable, function2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FakeOverrideDeclarationTable(KotlinMangler.IrMangler mangler, FakeOverrideGlobalDeclarationTable globalTable, Function2<? super PublicIdSignatureComputer, ? super DeclarationTable, IdSignatureSerializer> signatureSerializerFactory) {
        super(globalTable);
        Intrinsics.checkNotNullParameter(mangler, "mangler");
        Intrinsics.checkNotNullParameter(globalTable, "globalTable");
        Intrinsics.checkNotNullParameter(signatureSerializerFactory, "signatureSerializerFactory");
        this.globalDeclarationTable = globalTable;
        this.signaturer = signatureSerializerFactory.invoke(globalTable.getPublicIdSignatureComputer(), this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.DeclarationTable
    public FakeOverrideGlobalDeclarationTable getGlobalDeclarationTable() {
        return this.globalDeclarationTable;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.DeclarationTable
    public IdSignatureSerializer getSignaturer() {
        return this.signaturer;
    }

    public final void clear() {
        getTable().clear();
        getGlobalDeclarationTable().clear();
    }
}
