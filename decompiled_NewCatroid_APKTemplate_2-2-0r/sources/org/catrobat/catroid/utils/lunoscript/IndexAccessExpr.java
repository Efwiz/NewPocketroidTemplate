package org.catrobat.catroid.utils.lunoscript;

import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/IndexAccessExpr.class
  classes14.dex
 */
/* JADX INFO: compiled from: AstNodes.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0001\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u0010\u001a\u00020\u0001HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0004HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0001HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0007HÆ\u0003J1\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00012\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00012\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018HÖ\u0003J\t\u0010\u0019\u001a\u00020\u0007HÖ\u0001J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0005\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/IndexAccessExpr;", "Lorg/catrobat/catroid/utils/lunoscript/Expression;", "callee", "bracket", "Lorg/catrobat/catroid/utils/lunoscript/Token;", "index", Constant.LINE, "", "(Lorg/catrobat/catroid/utils/lunoscript/Expression;Lorg/catrobat/catroid/utils/lunoscript/Token;Lorg/catrobat/catroid/utils/lunoscript/Expression;I)V", "getBracket", "()Lorg/catrobat/catroid/utils/lunoscript/Token;", "getCallee", "()Lorg/catrobat/catroid/utils/lunoscript/Expression;", "getIndex", "getLine", "()I", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class IndexAccessExpr implements Expression {
    private final Token bracket;
    private final Expression callee;
    private final Expression index;
    private final int line;

    public static /* synthetic */ IndexAccessExpr copy$default(IndexAccessExpr indexAccessExpr, Expression expression, Token token, Expression expression2, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            expression = indexAccessExpr.callee;
        }
        if ((i2 & 2) != 0) {
            token = indexAccessExpr.bracket;
        }
        if ((i2 & 4) != 0) {
            expression2 = indexAccessExpr.index;
        }
        if ((i2 & 8) != 0) {
            i = indexAccessExpr.line;
        }
        return indexAccessExpr.copy(expression, token, expression2, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final Expression getCallee() {
        return this.callee;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final Token getBracket() {
        return this.bracket;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final Expression getIndex() {
        return this.index;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final int getLine() {
        return this.line;
    }

    public final IndexAccessExpr copy(Expression callee, Token bracket, Expression index, int line) {
        Intrinsics.checkNotNullParameter(callee, "callee");
        Intrinsics.checkNotNullParameter(bracket, "bracket");
        Intrinsics.checkNotNullParameter(index, "index");
        return new IndexAccessExpr(callee, bracket, index, line);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof IndexAccessExpr)) {
            return false;
        }
        IndexAccessExpr indexAccessExpr = (IndexAccessExpr) other;
        return Intrinsics.areEqual(this.callee, indexAccessExpr.callee) && Intrinsics.areEqual(this.bracket, indexAccessExpr.bracket) && Intrinsics.areEqual(this.index, indexAccessExpr.index) && this.line == indexAccessExpr.line;
    }

    public int hashCode() {
        return (((((this.callee.hashCode() * 31) + this.bracket.hashCode()) * 31) + this.index.hashCode()) * 31) + Integer.hashCode(this.line);
    }

    public String toString() {
        return "IndexAccessExpr(callee=" + this.callee + ", bracket=" + this.bracket + ", index=" + this.index + ", line=" + this.line + Constant.AFTER_QUTO;
    }

    public IndexAccessExpr(Expression callee, Token bracket, Expression index, int i) {
        Intrinsics.checkNotNullParameter(callee, "callee");
        Intrinsics.checkNotNullParameter(bracket, "bracket");
        Intrinsics.checkNotNullParameter(index, "index");
        this.callee = callee;
        this.bracket = bracket;
        this.index = index;
        this.line = i;
    }

    public final Token getBracket() {
        return this.bracket;
    }

    public final Expression getCallee() {
        return this.callee;
    }

    public final Expression getIndex() {
        return this.index;
    }

    @Override // org.catrobat.catroid.utils.lunoscript.AstNode
    public int getLine() {
        return this.line;
    }
}
