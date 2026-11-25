package org.catrobat.catroid.utils.lunoscript;

import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/ForInStatement.class
  classes14.dex
 */
/* JADX INFO: compiled from: AstNodes.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0001\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0001HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0018\u001a\u00020\tHÆ\u0003J;\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00012\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\tHÆ\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dHÖ\u0003J\t\u0010\u001e\u001a\u00020\tHÖ\u0001J\t\u0010\u001f\u001a\u00020 HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000e¨\u0006!"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/ForInStatement;", "Lorg/catrobat/catroid/utils/lunoscript/Statement;", "variable", "Lorg/catrobat/catroid/utils/lunoscript/Token;", "iterable", "Lorg/catrobat/catroid/utils/lunoscript/Expression;", "body", "forToken", Constant.LINE, "", "(Lorg/catrobat/catroid/utils/lunoscript/Token;Lorg/catrobat/catroid/utils/lunoscript/Expression;Lorg/catrobat/catroid/utils/lunoscript/Statement;Lorg/catrobat/catroid/utils/lunoscript/Token;I)V", "getBody", "()Lorg/catrobat/catroid/utils/lunoscript/Statement;", "getForToken", "()Lorg/catrobat/catroid/utils/lunoscript/Token;", "getIterable", "()Lorg/catrobat/catroid/utils/lunoscript/Expression;", "getLine", "()I", "getVariable", "component1", "component2", "component3", "component4", "component5", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ForInStatement implements Statement {
    private final Statement body;
    private final Token forToken;
    private final Expression iterable;
    private final int line;
    private final Token variable;

    public static /* synthetic */ ForInStatement copy$default(ForInStatement forInStatement, Token token, Expression expression, Statement statement, Token token2, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            token = forInStatement.variable;
        }
        if ((i2 & 2) != 0) {
            expression = forInStatement.iterable;
        }
        Expression expression2 = expression;
        if ((i2 & 4) != 0) {
            statement = forInStatement.body;
        }
        Statement statement2 = statement;
        if ((i2 & 8) != 0) {
            token2 = forInStatement.forToken;
        }
        Token token3 = token2;
        if ((i2 & 16) != 0) {
            i = forInStatement.line;
        }
        return forInStatement.copy(token, expression2, statement2, token3, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final Token getVariable() {
        return this.variable;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final Expression getIterable() {
        return this.iterable;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final Statement getBody() {
        return this.body;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final Token getForToken() {
        return this.forToken;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final int getLine() {
        return this.line;
    }

    public final ForInStatement copy(Token variable, Expression iterable, Statement body, Token forToken, int line) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        Intrinsics.checkNotNullParameter(iterable, "iterable");
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(forToken, "forToken");
        return new ForInStatement(variable, iterable, body, forToken, line);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ForInStatement)) {
            return false;
        }
        ForInStatement forInStatement = (ForInStatement) other;
        return Intrinsics.areEqual(this.variable, forInStatement.variable) && Intrinsics.areEqual(this.iterable, forInStatement.iterable) && Intrinsics.areEqual(this.body, forInStatement.body) && Intrinsics.areEqual(this.forToken, forInStatement.forToken) && this.line == forInStatement.line;
    }

    public int hashCode() {
        return (((((((this.variable.hashCode() * 31) + this.iterable.hashCode()) * 31) + this.body.hashCode()) * 31) + this.forToken.hashCode()) * 31) + Integer.hashCode(this.line);
    }

    public String toString() {
        return "ForInStatement(variable=" + this.variable + ", iterable=" + this.iterable + ", body=" + this.body + ", forToken=" + this.forToken + ", line=" + this.line + Constant.AFTER_QUTO;
    }

    public ForInStatement(Token variable, Expression iterable, Statement body, Token forToken, int i) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        Intrinsics.checkNotNullParameter(iterable, "iterable");
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(forToken, "forToken");
        this.variable = variable;
        this.iterable = iterable;
        this.body = body;
        this.forToken = forToken;
        this.line = i;
    }

    public final Statement getBody() {
        return this.body;
    }

    public final Token getForToken() {
        return this.forToken;
    }

    public final Expression getIterable() {
        return this.iterable;
    }

    @Override // org.catrobat.catroid.utils.lunoscript.AstNode
    public int getLine() {
        return this.line;
    }

    public final Token getVariable() {
        return this.variable;
    }
}
