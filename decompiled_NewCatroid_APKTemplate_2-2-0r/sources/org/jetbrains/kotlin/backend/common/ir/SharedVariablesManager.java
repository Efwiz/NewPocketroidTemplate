package org.jetbrains.kotlin.backend.common.ir;

import kotlin.Metadata;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;

/* JADX INFO: compiled from: SharedVariablesManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H&J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0003H&J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH&J\u0018\u0010\u000e\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u0010H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0011À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/ir/SharedVariablesManager;", "", "declareSharedVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "originalDeclaration", "defineSharedValue", "Lorg/jetbrains/kotlin/ir/IrStatement;", "sharedVariableDeclaration", "getSharedValue", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "sharedVariableSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "originalGet", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "setSharedValue", "originalSet", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface SharedVariablesManager {
    IrVariable declareSharedVariable(IrVariable originalDeclaration);

    IrStatement defineSharedValue(IrVariable originalDeclaration, IrVariable sharedVariableDeclaration);

    IrExpression getSharedValue(IrValueSymbol sharedVariableSymbol, IrGetValue originalGet);

    IrExpression setSharedValue(IrValueSymbol sharedVariableSymbol, IrSetValue originalSet);
}
