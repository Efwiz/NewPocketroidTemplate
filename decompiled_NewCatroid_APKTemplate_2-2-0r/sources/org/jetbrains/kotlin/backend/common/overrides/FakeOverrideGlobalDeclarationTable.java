package org.jetbrains.kotlin.backend.common.overrides;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.GlobalDeclarationTable;
import org.jetbrains.kotlin.ir.util.KotlinMangler;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: FakeOverrides.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/overrides/FakeOverrideGlobalDeclarationTable;", "Lorg/jetbrains/kotlin/backend/common/serialization/GlobalDeclarationTable;", "mangler", "Lorg/jetbrains/kotlin/ir/util/KotlinMangler$IrMangler;", "(Lorg/jetbrains/kotlin/ir/util/KotlinMangler$IrMangler;)V", LineReader.CLEAR, "", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FakeOverrideGlobalDeclarationTable extends GlobalDeclarationTable {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FakeOverrideGlobalDeclarationTable(KotlinMangler.IrMangler mangler) {
        super(mangler);
        Intrinsics.checkNotNullParameter(mangler, "mangler");
    }

    public final void clear() {
        getTable().clear();
    }
}
