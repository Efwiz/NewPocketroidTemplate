package org.catrobat.catroid.utils.lunoscript;

import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/IfStatement.class
  classes14.dex
 */
/* JADX INFO: compiled from: AstNodes.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0001\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0001\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0001HÆ\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0007HÆ\u0003J\t\u0010\u0018\u001a\u00020\tHÆ\u0003J=\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00012\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\tHÆ\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dHÖ\u0003J\t\u0010\u001e\u001a\u00020\tHÖ\u0001J\t\u0010\u001f\u001a\u00020 HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0001¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0004\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000e¨\u0006!"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/IfStatement;", "Lorg/catrobat/catroid/utils/lunoscript/Statement;", "condition", "Lorg/catrobat/catroid/utils/lunoscript/Expression;", "thenBranch", "elseBranch", "ifToken", "Lorg/catrobat/catroid/utils/lunoscript/Token;", Constant.LINE, "", "(Lorg/catrobat/catroid/utils/lunoscript/Expression;Lorg/catrobat/catroid/utils/lunoscript/Statement;Lorg/catrobat/catroid/utils/lunoscript/Statement;Lorg/catrobat/catroid/utils/lunoscript/Token;I)V", "getCondition", "()Lorg/catrobat/catroid/utils/lunoscript/Expression;", "getElseBranch", "()Lorg/catrobat/catroid/utils/lunoscript/Statement;", "getIfToken", "()Lorg/catrobat/catroid/utils/lunoscript/Token;", "getLine", "()I", "getThenBranch", "component1", "component2", "component3", "component4", "component5", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class IfStatement implements Statement {
    private final Expression condition;
    private final Statement elseBranch;
    private final Token ifToken;
    private final int line;
    private final Statement thenBranch;

    public static /* synthetic */ IfStatement copy$default(IfStatement ifStatement, Expression expression, Statement statement, Statement statement2, Token token, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            expression = ifStatement.condition;
        }
        if ((i2 & 2) != 0) {
            statement = ifStatement.thenBranch;
        }
        Statement statement3 = statement;
        if ((i2 & 4) != 0) {
            statement2 = ifStatement.elseBranch;
        }
        Statement statement4 = statement2;
        if ((i2 & 8) != 0) {
            token = ifStatement.ifToken;
        }
        Token token2 = token;
        if ((i2 & 16) != 0) {
            i = ifStatement.line;
        }
        return ifStatement.copy(expression, statement3, statement4, token2, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final Expression getCondition() {
        return this.condition;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final Statement getThenBranch() {
        return this.thenBranch;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final Statement getElseBranch() {
        return this.elseBranch;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final Token getIfToken() {
        return this.ifToken;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final int getLine() {
        return this.line;
    }

    public final IfStatement copy(Expression condition, Statement thenBranch, Statement elseBranch, Token ifToken, int line) {
        Intrinsics.checkNotNullParameter(condition, "condition");
        Intrinsics.checkNotNullParameter(thenBranch, "thenBranch");
        Intrinsics.checkNotNullParameter(ifToken, "ifToken");
        return new IfStatement(condition, thenBranch, elseBranch, ifToken, line);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof IfStatement)) {
            return false;
        }
        IfStatement ifStatement = (IfStatement) other;
        return Intrinsics.areEqual(this.condition, ifStatement.condition) && Intrinsics.areEqual(this.thenBranch, ifStatement.thenBranch) && Intrinsics.areEqual(this.elseBranch, ifStatement.elseBranch) && Intrinsics.areEqual(this.ifToken, ifStatement.ifToken) && this.line == ifStatement.line;
    }

    public int hashCode() {
        int iHashCode = ((this.condition.hashCode() * 31) + this.thenBranch.hashCode()) * 31;
        Statement statement = this.elseBranch;
        return ((((iHashCode + (statement == null ? 0 : statement.hashCode())) * 31) + this.ifToken.hashCode()) * 31) + Integer.hashCode(this.line);
    }

    public String toString() {
        return "IfStatement(condition=" + this.condition + ", thenBranch=" + this.thenBranch + ", elseBranch=" + this.elseBranch + ", ifToken=" + this.ifToken + ", line=" + this.line + Constant.AFTER_QUTO;
    }

    public IfStatement(Expression condition, Statement thenBranch, Statement statement, Token ifToken, int i) {
        Intrinsics.checkNotNullParameter(condition, "condition");
        Intrinsics.checkNotNullParameter(thenBranch, "thenBranch");
        Intrinsics.checkNotNullParameter(ifToken, "ifToken");
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = statement;
        this.ifToken = ifToken;
        this.line = i;
    }

    public final Expression getCondition() {
        return this.condition;
    }

    public final Statement getElseBranch() {
        return this.elseBranch;
    }

    public final Token getIfToken() {
        return this.ifToken;
    }

    @Override // org.catrobat.catroid.utils.lunoscript.AstNode
    public int getLine() {
        return this.line;
    }

    public final Statement getThenBranch() {
        return this.thenBranch;
    }
}
