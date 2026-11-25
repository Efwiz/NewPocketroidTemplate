package org.catrobat.catroid.utils.lunoscript;

import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/UnaryExpr.class
  classes14.dex
 */
/* JADX INFO: compiled from: AstNodes.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0001\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0001HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0006HÆ\u0003J'\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00012\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0006HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/UnaryExpr;", "Lorg/catrobat/catroid/utils/lunoscript/Expression;", "operator", "Lorg/catrobat/catroid/utils/lunoscript/Token;", "right", Constant.LINE, "", "(Lorg/catrobat/catroid/utils/lunoscript/Token;Lorg/catrobat/catroid/utils/lunoscript/Expression;I)V", "getLine", "()I", "getOperator", "()Lorg/catrobat/catroid/utils/lunoscript/Token;", "getRight", "()Lorg/catrobat/catroid/utils/lunoscript/Expression;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class UnaryExpr implements Expression {
    private final int line;
    private final Token operator;
    private final Expression right;

    public static /* synthetic */ UnaryExpr copy$default(UnaryExpr unaryExpr, Token token, Expression expression, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            token = unaryExpr.operator;
        }
        if ((i2 & 2) != 0) {
            expression = unaryExpr.right;
        }
        if ((i2 & 4) != 0) {
            i = unaryExpr.line;
        }
        return unaryExpr.copy(token, expression, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final Token getOperator() {
        return this.operator;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final Expression getRight() {
        return this.right;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final int getLine() {
        return this.line;
    }

    public final UnaryExpr copy(Token operator, Expression right, int line) {
        Intrinsics.checkNotNullParameter(operator, "operator");
        Intrinsics.checkNotNullParameter(right, "right");
        return new UnaryExpr(operator, right, line);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof UnaryExpr)) {
            return false;
        }
        UnaryExpr unaryExpr = (UnaryExpr) other;
        return Intrinsics.areEqual(this.operator, unaryExpr.operator) && Intrinsics.areEqual(this.right, unaryExpr.right) && this.line == unaryExpr.line;
    }

    public int hashCode() {
        return (((this.operator.hashCode() * 31) + this.right.hashCode()) * 31) + Integer.hashCode(this.line);
    }

    public String toString() {
        return "UnaryExpr(operator=" + this.operator + ", right=" + this.right + ", line=" + this.line + Constant.AFTER_QUTO;
    }

    public UnaryExpr(Token operator, Expression right, int i) {
        Intrinsics.checkNotNullParameter(operator, "operator");
        Intrinsics.checkNotNullParameter(right, "right");
        this.operator = operator;
        this.right = right;
        this.line = i;
    }

    @Override // org.catrobat.catroid.utils.lunoscript.AstNode
    public int getLine() {
        return this.line;
    }

    public final Token getOperator() {
        return this.operator;
    }

    public final Expression getRight() {
        return this.right;
    }
}
