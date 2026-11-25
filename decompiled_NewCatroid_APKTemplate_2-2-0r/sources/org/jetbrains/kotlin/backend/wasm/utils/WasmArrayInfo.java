package org.jetbrains.kotlin.backend.wasm.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: Annotations.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/backend/wasm/utils/WasmArrayInfo;", "", "klass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "isNullable", "", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Z)V", "()Z", "getKlass", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "backend.wasm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WasmArrayInfo {
    private final boolean isNullable;
    private final IrClass klass;
    private final IrType type;

    public WasmArrayInfo(IrClass klass, boolean z) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        this.klass = klass;
        this.isNullable = z;
        IrSimpleType defaultType = IrUtilsKt.getDefaultType(klass);
        this.type = z ? IrTypesKt.makeNullable(defaultType) : defaultType;
    }

    public final IrClass getKlass() {
        return this.klass;
    }

    /* JADX INFO: renamed from: isNullable, reason: from getter */
    public final boolean getIsNullable() {
        return this.isNullable;
    }

    public final IrType getType() {
        return this.type;
    }
}
