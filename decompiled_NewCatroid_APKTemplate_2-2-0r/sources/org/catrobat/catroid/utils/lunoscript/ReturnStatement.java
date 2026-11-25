package org.catrobat.catroid.utils.lunoscript;

import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/ReturnStatement.class
  classes14.dex
 */
/* JADX INFO: compiled from: AstNodes.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0007HÆ\u0003J)\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0007HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001a"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/ReturnStatement;", "Lorg/catrobat/catroid/utils/lunoscript/Statement;", "keyword", "Lorg/catrobat/catroid/utils/lunoscript/Token;", "value", "Lorg/catrobat/catroid/utils/lunoscript/Expression;", Constant.LINE, "", "(Lorg/catrobat/catroid/utils/lunoscript/Token;Lorg/catrobat/catroid/utils/lunoscript/Expression;I)V", "getKeyword", "()Lorg/catrobat/catroid/utils/lunoscript/Token;", "getLine", "()I", "getValue", "()Lorg/catrobat/catroid/utils/lunoscript/Expression;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ReturnStatement implements Statement {
    private final Token keyword;
    private final int line;
    private final Expression value;

    public static /* synthetic */ ReturnStatement copy$default(ReturnStatement returnStatement, Token token, Expression expression, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            token = returnStatement.keyword;
        }
        if ((i2 & 2) != 0) {
            expression = returnStatement.value;
        }
        if ((i2 & 4) != 0) {
            i = returnStatement.line;
        }
        return returnStatement.copy(token, expression, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final Token getKeyword() {
        return this.keyword;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final Expression getValue() {
        return this.value;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final int getLine() {
        return this.line;
    }

    public final ReturnStatement copy(Token keyword, Expression value, int line) {
        Intrinsics.checkNotNullParameter(keyword, "keyword");
        return new ReturnStatement(keyword, value, line);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ReturnStatement)) {
            return false;
        }
        ReturnStatement returnStatement = (ReturnStatement) other;
        return Intrinsics.areEqual(this.keyword, returnStatement.keyword) && Intrinsics.areEqual(this.value, returnStatement.value) && this.line == returnStatement.line;
    }

    public int hashCode() {
        int iHashCode = this.keyword.hashCode() * 31;
        Expression expression = this.value;
        return ((iHashCode + (expression == null ? 0 : expression.hashCode())) * 31) + Integer.hashCode(this.line);
    }

    public String toString() {
        return "ReturnStatement(keyword=" + this.keyword + ", value=" + this.value + ", line=" + this.line + Constant.AFTER_QUTO;
    }

    public ReturnStatement(Token keyword, Expression expression, int i) {
        Intrinsics.checkNotNullParameter(keyword, "keyword");
        this.keyword = keyword;
        this.value = expression;
        this.line = i;
    }

    public final Token getKeyword() {
        return this.keyword;
    }

    @Override // org.catrobat.catroid.utils.lunoscript.AstNode
    public int getLine() {
        return this.line;
    }

    public final Expression getValue() {
        return this.value;
    }
}
