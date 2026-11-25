package org.jetbrains.kotlin.backend.jvm.serialization;

import kotlin.Metadata;
import org.jetbrains.kotlin.backend.common.serialization.GlobalDeclarationTable;
import org.jetbrains.kotlin.ir.backend.jvm.serialization.JvmIrMangler;

/* JADX INFO: compiled from: JvmGlobalDeclarationTable.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/serialization/JvmGlobalDeclarationTable;", "Lorg/jetbrains/kotlin/backend/common/serialization/GlobalDeclarationTable;", "()V", "ir.serialization.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmGlobalDeclarationTable extends GlobalDeclarationTable {
    public JvmGlobalDeclarationTable() {
        super(JvmIrMangler.INSTANCE);
    }
}
