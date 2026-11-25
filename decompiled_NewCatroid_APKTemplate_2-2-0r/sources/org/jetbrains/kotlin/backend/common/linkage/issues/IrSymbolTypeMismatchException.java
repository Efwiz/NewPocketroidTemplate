package org.jetbrains.kotlin.backend.common.linkage.issues;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.IdSignatureRendererKt;

/* JADX INFO: compiled from: IrDeserializationExceptions.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001d\u0012\u000e\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0005\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0019\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/issues/IrSymbolTypeMismatchException;", "Lorg/jetbrains/kotlin/backend/common/linkage/issues/IrDeserializationException;", "expected", "Ljava/lang/Class;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "actual", "(Ljava/lang/Class;Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;)V", "getActual", "()Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "getExpected", "()Ljava/lang/Class;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrSymbolTypeMismatchException extends IrDeserializationException {
    private final IrSymbol actual;
    private final Class<? extends IrSymbol> expected;

    public final Class<? extends IrSymbol> getExpected() {
        return this.expected;
    }

    public final IrSymbol getActual() {
        return this.actual;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public IrSymbolTypeMismatchException(Class<? extends IrSymbol> expected, IrSymbol actual) {
        String strRender$default;
        Intrinsics.checkNotNullParameter(expected, "expected");
        Intrinsics.checkNotNullParameter(actual, "actual");
        StringBuilder sbAppend = new StringBuilder("The symbol of unexpected type encountered during IR deserialization: ").append(actual.getClass().getSimpleName()).append(", ");
        IdSignature signature = actual.getSignature();
        super(sbAppend.append((signature == null || (strRender$default = IdSignatureRendererKt.render$default(signature, null, 1, null)) == null) ? actual.toString() : strRender$default).append(". ").append(expected.getSimpleName()).append(" is expected.").toString(), null);
        this.expected = expected;
        this.actual = actual;
    }
}
