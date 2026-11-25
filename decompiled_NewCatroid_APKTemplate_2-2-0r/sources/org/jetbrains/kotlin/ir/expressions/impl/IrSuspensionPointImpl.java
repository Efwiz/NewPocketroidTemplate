package org.jetbrains.kotlin.ir.expressions.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrSuspensionPoint;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: IrSuspendImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0014\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n¢\u0006\u0002\u0010\fR\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\t\u001a\u00020\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u000b\u001a\u00020\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0010\"\u0004\b\u0014\u0010\u0012R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000eR\u001a\u0010\u0007\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001d¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrSuspensionPointImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrSuspensionPoint;", "startOffset", "", "endOffset", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "suspensionPointIdParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "result", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "resumeResult", "(IILorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/declarations/IrVariable;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "getEndOffset", "()I", "getResult", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "setResult", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "getResumeResult", "setResumeResult", "getStartOffset", "getSuspensionPointIdParameter", "()Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "setSuspensionPointIdParameter", "(Lorg/jetbrains/kotlin/ir/declarations/IrVariable;)V", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrSuspensionPointImpl extends IrSuspensionPoint {
    private final int endOffset;
    private IrExpression result;
    private IrExpression resumeResult;
    private final int startOffset;
    private IrVariable suspensionPointIdParameter;
    private IrType type;

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getStartOffset() {
        return this.startOffset;
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getEndOffset() {
        return this.endOffset;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrExpression
    public IrType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrExpression
    public void setType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<set-?>");
        this.type = irType;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrSuspensionPoint
    public IrVariable getSuspensionPointIdParameter() {
        return this.suspensionPointIdParameter;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrSuspensionPoint
    public void setSuspensionPointIdParameter(IrVariable irVariable) {
        Intrinsics.checkNotNullParameter(irVariable, "<set-?>");
        this.suspensionPointIdParameter = irVariable;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrSuspensionPoint
    public IrExpression getResult() {
        return this.result;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrSuspensionPoint
    public void setResult(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<set-?>");
        this.result = irExpression;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrSuspensionPoint
    public IrExpression getResumeResult() {
        return this.resumeResult;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrSuspensionPoint
    public void setResumeResult(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<set-?>");
        this.resumeResult = irExpression;
    }

    public IrSuspensionPointImpl(int i, int i2, IrType type, IrVariable suspensionPointIdParameter, IrExpression result, IrExpression resumeResult) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(suspensionPointIdParameter, "suspensionPointIdParameter");
        Intrinsics.checkNotNullParameter(result, "result");
        Intrinsics.checkNotNullParameter(resumeResult, "resumeResult");
        this.startOffset = i;
        this.endOffset = i2;
        this.type = type;
        this.suspensionPointIdParameter = suspensionPointIdParameter;
        this.result = result;
        this.resumeResult = resumeResult;
    }
}
