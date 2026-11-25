package org.jetbrains.kotlin.psi2ir.intermediate;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: OnceExpressionValue.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a$\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u000e\b\u0004\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0080\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0007"}, d2 = {"generateExpressionValue", "Lorg/jetbrains/kotlin/psi2ir/intermediate/ExpressionValue;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", CompilerOptions.GENERATE, "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "ir.psi2ir"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class OnceExpressionValueKt {
    public static final ExpressionValue generateExpressionValue(IrType type, final Function0<? extends IrExpression> generate) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(generate, "generate");
        return new ExpressionValue(type) { // from class: org.jetbrains.kotlin.psi2ir.intermediate.OnceExpressionValueKt.generateExpressionValue.1
            @Override // org.jetbrains.kotlin.psi2ir.intermediate.IntermediateValue
            public IrExpression load() {
                return generate.invoke();
            }
        };
    }
}
