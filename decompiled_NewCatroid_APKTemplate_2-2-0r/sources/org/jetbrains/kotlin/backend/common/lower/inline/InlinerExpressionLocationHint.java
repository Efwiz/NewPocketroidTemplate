package org.jetbrains.kotlin.backend.common.lower.inline;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrFileEntry;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FunctionInlining.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000f\u001a\u00020\nH\u0016R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/inline/InlinerExpressionLocationHint;", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "inlineAtSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "(Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;)V", "functionFileOrNull", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "getFunctionFileOrNull", "()Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "functionNameOrDefaultToString", "", "getFunctionNameOrDefaultToString", "()Ljava/lang/String;", "getInlineAtSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "toString", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InlinerExpressionLocationHint implements IrStatementOrigin {
    private final IrSymbol inlineAtSymbol;

    public InlinerExpressionLocationHint(IrSymbol inlineAtSymbol) {
        Intrinsics.checkNotNullParameter(inlineAtSymbol, "inlineAtSymbol");
        this.inlineAtSymbol = inlineAtSymbol;
    }

    public final IrSymbol getInlineAtSymbol() {
        return this.inlineAtSymbol;
    }

    public String toString() {
        IrFileEntry fileEntry;
        StringBuilder sbAppend = new StringBuilder("(").append(getClass().getSimpleName()).append(" : ").append(getFunctionNameOrDefaultToString()).append(" @");
        IrFile functionFileOrNull = getFunctionFileOrNull();
        return sbAppend.append((functionFileOrNull == null || (fileEntry = functionFileOrNull.getFileEntry()) == null) ? null : fileEntry.getName()).append(')').toString();
    }

    private final IrFile getFunctionFileOrNull() {
        Object obj = this.inlineAtSymbol;
        IrFunction irFunction = obj instanceof IrFunction ? (IrFunction) obj : null;
        if (irFunction != null) {
            return IrUtilsKt.getFile(irFunction);
        }
        return null;
    }

    private final String getFunctionNameOrDefaultToString() {
        Name name2;
        Object obj = this.inlineAtSymbol;
        String strAsString = null;
        IrFunction irFunction = obj instanceof IrFunction ? (IrFunction) obj : null;
        if (irFunction != null && (name2 = irFunction.getName()) != null) {
            strAsString = name2.asString();
        }
        return strAsString == null ? this.inlineAtSymbol.toString() : strAsString;
    }
}
