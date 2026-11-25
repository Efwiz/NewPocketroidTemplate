package org.jetbrains.kotlin.psi2ir.intermediate;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: OnceExpressionValue.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b \u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/intermediate/ExpressionValue;", "Lorg/jetbrains/kotlin/psi2ir/intermediate/IntermediateValue;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ExpressionValue implements IntermediateValue {
    private final IrType type;

    public ExpressionValue(IrType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.type = type;
    }

    @Override // org.jetbrains.kotlin.psi2ir.intermediate.IntermediateValue
    public IrType getType() {
        return this.type;
    }
}
