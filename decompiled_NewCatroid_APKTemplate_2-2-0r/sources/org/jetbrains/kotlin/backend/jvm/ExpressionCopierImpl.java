package org.jetbrains.kotlin.backend.jvm;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols;
import org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper;
import org.jetbrains.kotlin.ir.util.DeepCopyTypeRemapper;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;

/* JADX INFO: compiled from: MfvcNodeInstance.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0000\u0018\u00002\u00020\u0001:\u0003\u000f\u0010\u0011B+\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007¢\u0006\u0002\u0010\nJ\b\u0010\r\u001a\u0004\u0018\u00010\u0003J\f\u0010\u000e\u001a\u00020\f*\u00020\u0003H\u0002R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/ExpressionCopierImpl;", "", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "scope", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBuilder;", "saveVariable", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/ir/builders/IrBlockBuilder;Lkotlin/jvm/functions/Function1;)V", "copyableExpression", "Lorg/jetbrains/kotlin/backend/jvm/ExpressionCopierImpl$CopyableExpression;", "makeCopy", "orSavedToVariable", "CopyableExpression", "PureExpression", "SavedToVariable", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExpressionCopierImpl {
    private final CopyableExpression copyableExpression;
    private final Function1<IrVariable, Unit> saveVariable;
    private final IrBlockBuilder scope;

    /* JADX INFO: compiled from: MfvcNodeInstance.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\br\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&\u0082\u0001\u0002\u0006\u0007ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/ExpressionCopierImpl$CopyableExpression;", "", "makeExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "scope", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "Lorg/jetbrains/kotlin/backend/jvm/ExpressionCopierImpl$PureExpression;", "Lorg/jetbrains/kotlin/backend/jvm/ExpressionCopierImpl$SavedToVariable;", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private interface CopyableExpression {
        IrExpression makeExpression(IrBuilderWithScope scope);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ExpressionCopierImpl(IrExpression irExpression, IrBlockBuilder scope, Function1<? super IrVariable, Unit> saveVariable) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(saveVariable, "saveVariable");
        this.scope = scope;
        this.saveVariable = saveVariable;
        this.copyableExpression = irExpression != null ? orSavedToVariable(irExpression) : null;
    }

    /* JADX INFO: compiled from: MfvcNodeInstance.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/ExpressionCopierImpl$SavedToVariable;", "Lorg/jetbrains/kotlin/backend/jvm/ExpressionCopierImpl$CopyableExpression;", "variable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "(Lorg/jetbrains/kotlin/ir/declarations/IrVariable;)V", "getVariable", "()Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "makeExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "scope", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class SavedToVariable implements CopyableExpression {
        private final IrVariable variable;

        public SavedToVariable(IrVariable variable) {
            Intrinsics.checkNotNullParameter(variable, "variable");
            this.variable = variable;
        }

        public final IrVariable getVariable() {
            return this.variable;
        }

        @Override // org.jetbrains.kotlin.backend.jvm.ExpressionCopierImpl.CopyableExpression
        public IrExpression makeExpression(IrBuilderWithScope scope) {
            Intrinsics.checkNotNullParameter(scope, "scope");
            return ExpressionHelpersKt.irGet(scope, this.variable);
        }
    }

    /* JADX INFO: compiled from: MfvcNodeInstance.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/ExpressionCopierImpl$PureExpression;", "Lorg/jetbrains/kotlin/backend/jvm/ExpressionCopierImpl$CopyableExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "getExpression", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "makeExpression", "scope", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class PureExpression implements CopyableExpression {
        private final IrExpression expression;

        public PureExpression(IrExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            this.expression = expression;
        }

        public final IrExpression getExpression() {
            return this.expression;
        }

        @Override // org.jetbrains.kotlin.backend.jvm.ExpressionCopierImpl.CopyableExpression
        public IrExpression makeExpression(IrBuilderWithScope scope) {
            Intrinsics.checkNotNullParameter(scope, "scope");
            IrExpression irExpression = this.expression;
            DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(null, 1, null);
            IrVisitorsKt.acceptVoid(irExpression, deepCopySymbolRemapper);
            DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
            IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(irExpression.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new DeepCopyTypeRemapper(deepCopySymbolRemapper2)), (Object) null), null);
            if (irElementPatchDeclarationParents != null) {
                return (IrExpression) irElementPatchDeclarationParents;
            }
            throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
        }
    }

    private final CopyableExpression orSavedToVariable(IrExpression irExpression) {
        if (MfvcNodeInstanceKt.isRepeatableGetter(irExpression)) {
            return new PureExpression(irExpression);
        }
        IrBlockBuilder irBlockBuilder = this.scope;
        return new SavedToVariable(MfvcNodeInstanceKt.savableStandaloneVariableWithSetter$default(irBlockBuilder, irExpression, null, false, JvmLoweredDeclarationOrigin.TEMPORARY_MULTI_FIELD_VALUE_CLASS_VARIABLE.INSTANCE, true, this.saveVariable, 6, null));
    }

    public final IrExpression makeCopy() {
        CopyableExpression copyableExpression = this.copyableExpression;
        if (copyableExpression != null) {
            return copyableExpression.makeExpression(this.scope);
        }
        return null;
    }
}
