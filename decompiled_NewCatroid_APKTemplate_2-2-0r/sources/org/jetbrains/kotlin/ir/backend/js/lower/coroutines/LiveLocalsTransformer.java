package org.jetbrains.kotlin.ir.backend.js.lower.coroutines;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.backend.js.utils.MiscKt;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetFieldImpl;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: SuspendLoweringUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B/\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0010\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u001a\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/LiveLocalsTransformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "localMap", "", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "receiver", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "unitType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "(Ljava/util/Map;Lkotlin/jvm/functions/Function0;Lorg/jetbrains/kotlin/ir/types/IrType;)V", "visitGetValue", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "visitSetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "visitVariable", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LiveLocalsTransformer extends IrElementTransformerVoid {
    private final Map<IrValueSymbol, IrFieldSymbol> localMap;
    private final Function0<IrExpression> receiver;
    private final IrType unitType;

    /* JADX WARN: Multi-variable type inference failed */
    public LiveLocalsTransformer(Map<IrValueSymbol, ? extends IrFieldSymbol> localMap, Function0<? extends IrExpression> receiver, IrType unitType) {
        Intrinsics.checkNotNullParameter(localMap, "localMap");
        Intrinsics.checkNotNullParameter(receiver, "receiver");
        Intrinsics.checkNotNullParameter(unitType, "unitType");
        this.localMap = localMap;
        this.receiver = receiver;
        this.unitType = unitType;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitGetValue(IrGetValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrFieldSymbol irFieldSymbol = this.localMap.get(expression.getSymbol());
        if (irFieldSymbol == null) {
            if (MiscKt.isDispatchReceiver(expression.getSymbol().getOwner())) {
                return this.receiver.invoke();
            }
            return expression;
        }
        return new IrGetFieldImpl(expression.getStartOffset(), expression.getEndOffset(), irFieldSymbol, expression.getType(), this.receiver.invoke(), expression.getOrigin(), null, 64, null);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitSetValue(IrSetValue expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrElementTransformerVoidKt.transformChildrenVoid(expression, this);
        IrFieldSymbol irFieldSymbol = this.localMap.get(expression.getSymbol());
        return irFieldSymbol == null ? expression : new IrSetFieldImpl(expression.getStartOffset(), expression.getEndOffset(), irFieldSymbol, this.receiver.invoke(), expression.getValue(), this.unitType, expression.getOrigin(), null, 128, null);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrStatement visitVariable(IrVariable declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrElementTransformerVoidKt.transformChildrenVoid(declaration, this);
        IrFieldSymbol irFieldSymbol = this.localMap.get(declaration.getSymbol());
        if (irFieldSymbol == null) {
            return declaration;
        }
        IrExpression initializer = declaration.getInitializer();
        if (initializer != null) {
            return new IrSetFieldImpl(declaration.getStartOffset(), declaration.getEndOffset(), irFieldSymbol, this.receiver.invoke(), initializer, this.unitType, null, null, 192, null);
        }
        return JsIrBuilder.buildComposite$default(JsIrBuilder.INSTANCE, this.unitType, null, 2, null);
    }
}
