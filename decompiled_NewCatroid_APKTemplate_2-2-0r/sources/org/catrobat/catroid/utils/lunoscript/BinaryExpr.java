package org.catrobat.catroid.utils.lunoscript;

import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/BinaryExpr.class
  classes14.dex
 */
/* JADX INFO: compiled from: AstNodes.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0001\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u0010\u001a\u00020\u0001HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0004HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0001HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0007HÆ\u0003J1\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00012\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00012\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018HÖ\u0003J\t\u0010\u0019\u001a\u00020\u0007HÖ\u0001J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0005\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\n¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/BinaryExpr;", "Lorg/catrobat/catroid/utils/lunoscript/Expression;", "left", "operator", "Lorg/catrobat/catroid/utils/lunoscript/Token;", "right", Constant.LINE, "", "(Lorg/catrobat/catroid/utils/lunoscript/Expression;Lorg/catrobat/catroid/utils/lunoscript/Token;Lorg/catrobat/catroid/utils/lunoscript/Expression;I)V", "getLeft", "()Lorg/catrobat/catroid/utils/lunoscript/Expression;", "getLine", "()I", "getOperator", "()Lorg/catrobat/catroid/utils/lunoscript/Token;", "getRight", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class BinaryExpr implements Expression {
    private final Expression left;
    private final int line;
    private final Token operator;
    private final Expression right;

    public static /* synthetic */ BinaryExpr copy$default(BinaryExpr binaryExpr, Expression expression, Token token, Expression expression2, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            expression = binaryExpr.left;
        }
        if ((i2 & 2) != 0) {
            token = binaryExpr.operator;
        }
        if ((i2 & 4) != 0) {
            expression2 = binaryExpr.right;
        }
        if ((i2 & 8) != 0) {
            i = binaryExpr.line;
        }
        return binaryExpr.copy(expression, token, expression2, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final Expression getLeft() {
        return this.left;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final Token getOperator() {
        return this.operator;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final Expression getRight() {
        return this.right;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final int getLine() {
        return this.line;
    }

    public final BinaryExpr copy(Expression left, Token operator, Expression right, int line) {
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(operator, "operator");
        Intrinsics.checkNotNullParameter(right, "right");
        return new BinaryExpr(left, operator, right, line);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BinaryExpr)) {
            return false;
        }
        BinaryExpr binaryExpr = (BinaryExpr) other;
        return Intrinsics.areEqual(this.left, binaryExpr.left) && Intrinsics.areEqual(this.operator, binaryExpr.operator) && Intrinsics.areEqual(this.right, binaryExpr.right) && this.line == binaryExpr.line;
    }

    public int hashCode() {
        return (((((this.left.hashCode() * 31) + this.operator.hashCode()) * 31) + this.right.hashCode()) * 31) + Integer.hashCode(this.line);
    }

    public String toString() {
        return "BinaryExpr(left=" + this.left + ", operator=" + this.operator + ", right=" + this.right + ", line=" + this.line + Constant.AFTER_QUTO;
    }

    public BinaryExpr(Expression left, Token operator, Expression right, int i) {
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(operator, "operator");
        Intrinsics.checkNotNullParameter(right, "right");
        this.left = left;
        this.operator = operator;
        this.right = right;
        this.line = i;
    }

    public final Expression getLeft() {
        return this.left;
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
