package org.jetbrains.kotlin.psi2ir.generators;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.Scope;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: StatementGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b \u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\n\u0010\u0013\u001a\u00020\u0014*\u00020\u0015J\n\u0010\u0016\u001a\u00020\u0017*\u00020\u0015J\n\u0010\u0018\u001a\u00020\u0010*\u00020\u0012R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/StatementGeneratorExtension;", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorWithScope;", "statementGenerator", "Lorg/jetbrains/kotlin/psi2ir/generators/StatementGenerator;", "(Lorg/jetbrains/kotlin/psi2ir/generators/StatementGenerator;)V", "context", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;", "getContext", "()Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;", "scope", "Lorg/jetbrains/kotlin/ir/builders/Scope;", "getScope", "()Lorg/jetbrains/kotlin/ir/builders/Scope;", "getStatementGenerator", "()Lorg/jetbrains/kotlin/psi2ir/generators/StatementGenerator;", "translateType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "genExpr", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/psi/KtExpression;", "genStmt", "Lorg/jetbrains/kotlin/ir/IrStatement;", "toIrType", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class StatementGeneratorExtension implements GeneratorWithScope {
    private final StatementGenerator statementGenerator;

    public StatementGeneratorExtension(StatementGenerator statementGenerator) {
        Intrinsics.checkNotNullParameter(statementGenerator, "statementGenerator");
        this.statementGenerator = statementGenerator;
    }

    public final StatementGenerator getStatementGenerator() {
        return this.statementGenerator;
    }

    @Override // org.jetbrains.kotlin.ir.builders.IrGeneratorWithScope
    public Scope getScope() {
        return this.statementGenerator.getScope();
    }

    @Override // org.jetbrains.kotlin.ir.builders.IrGenerator
    public GeneratorContext getContext() {
        return this.statementGenerator.getContext();
    }

    public final IrExpression genExpr(KtExpression ktExpression) {
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        return this.statementGenerator.generateExpression(ktExpression);
    }

    public final IrStatement genStmt(KtExpression ktExpression) {
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        return this.statementGenerator.generateStatement(ktExpression);
    }

    public final IrType toIrType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return this.statementGenerator.toIrType(kotlinType);
    }

    public final IrType translateType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
        return toIrType(kotlinType);
    }
}
