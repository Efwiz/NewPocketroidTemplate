package org.jetbrains.kotlin.ir.expressions.impl;

import com.google.android.gms.cast.MediaTrack;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.expressions.IrErrorCallExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: IrErrorCallExpressionImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u000e\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\fR\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u0007\u001a\u00020\bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0014R\u001a\u0010\u0005\u001a\u00020\u0006X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001e¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrErrorCallExpressionImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrErrorCallExpression;", "startOffset", "", "endOffset", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", MediaTrack.ROLE_DESCRIPTION, "", "(IILorg/jetbrains/kotlin/ir/types/IrType;Ljava/lang/String;)V", "arguments", "", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getArguments", "()Ljava/util/List;", "getDescription", "()Ljava/lang/String;", "setDescription", "(Ljava/lang/String;)V", "getEndOffset", "()I", "explicitReceiver", "getExplicitReceiver", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "setExplicitReceiver", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "getStartOffset", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "addArgument", "", "argument", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrErrorCallExpressionImpl extends IrErrorCallExpression {
    private final List<IrExpression> arguments;
    private String description;
    private final int endOffset;
    private IrExpression explicitReceiver;
    private final int startOffset;
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

    @Override // org.jetbrains.kotlin.ir.expressions.IrErrorExpression
    public String getDescription() {
        return this.description;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrErrorExpression
    public void setDescription(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.description = str;
    }

    public IrErrorCallExpressionImpl(int i, int i2, IrType type, String description) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(description, "description");
        this.startOffset = i;
        this.endOffset = i2;
        this.type = type;
        this.description = description;
        this.arguments = new SmartList();
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrErrorCallExpression
    public IrExpression getExplicitReceiver() {
        return this.explicitReceiver;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrErrorCallExpression
    public void setExplicitReceiver(IrExpression irExpression) {
        this.explicitReceiver = irExpression;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrErrorCallExpression
    public List<IrExpression> getArguments() {
        return this.arguments;
    }

    public final void addArgument(IrExpression argument) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        getArguments().add(argument);
    }
}
