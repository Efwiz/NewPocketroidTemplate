package org.catrobat.catroid.utils.lunoscript;

import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/ExpressionStatement.class
  classes14.dex
 */
/* JADX INFO: compiled from: AstNodes.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/ExpressionStatement;", "Lorg/catrobat/catroid/utils/lunoscript/Statement;", "expression", "Lorg/catrobat/catroid/utils/lunoscript/Expression;", Constant.LINE, "", "(Lorg/catrobat/catroid/utils/lunoscript/Expression;I)V", "getExpression", "()Lorg/catrobat/catroid/utils/lunoscript/Expression;", "getLine", "()I", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ExpressionStatement implements Statement {
    private final Expression expression;
    private final int line;

    public static /* synthetic */ ExpressionStatement copy$default(ExpressionStatement expressionStatement, Expression expression, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            expression = expressionStatement.expression;
        }
        if ((i2 & 2) != 0) {
            i = expressionStatement.line;
        }
        return expressionStatement.copy(expression, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final Expression getExpression() {
        return this.expression;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getLine() {
        return this.line;
    }

    public final ExpressionStatement copy(Expression expression, int line) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return new ExpressionStatement(expression, line);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExpressionStatement)) {
            return false;
        }
        ExpressionStatement expressionStatement = (ExpressionStatement) other;
        return Intrinsics.areEqual(this.expression, expressionStatement.expression) && this.line == expressionStatement.line;
    }

    public int hashCode() {
        return (this.expression.hashCode() * 31) + Integer.hashCode(this.line);
    }

    public String toString() {
        return "ExpressionStatement(expression=" + this.expression + ", line=" + this.line + Constant.AFTER_QUTO;
    }

    public ExpressionStatement(Expression expression, int i) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        this.expression = expression;
        this.line = i;
    }

    public final Expression getExpression() {
        return this.expression;
    }

    @Override // org.catrobat.catroid.utils.lunoscript.AstNode
    public int getLine() {
        return this.line;
    }
}
