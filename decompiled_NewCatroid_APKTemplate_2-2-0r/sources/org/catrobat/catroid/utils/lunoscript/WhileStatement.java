package org.catrobat.catroid.utils.lunoscript;

import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/WhileStatement.class
  classes14.dex
 */
/* JADX INFO: compiled from: AstNodes.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0001\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0001HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0015\u001a\u00020\bHÆ\u0003J1\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00012\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bHÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aHÖ\u0003J\t\u0010\u001b\u001a\u00020\bHÖ\u0001J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u001e"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/WhileStatement;", "Lorg/catrobat/catroid/utils/lunoscript/Statement;", "condition", "Lorg/catrobat/catroid/utils/lunoscript/Expression;", "body", "whileToken", "Lorg/catrobat/catroid/utils/lunoscript/Token;", Constant.LINE, "", "(Lorg/catrobat/catroid/utils/lunoscript/Expression;Lorg/catrobat/catroid/utils/lunoscript/Statement;Lorg/catrobat/catroid/utils/lunoscript/Token;I)V", "getBody", "()Lorg/catrobat/catroid/utils/lunoscript/Statement;", "getCondition", "()Lorg/catrobat/catroid/utils/lunoscript/Expression;", "getLine", "()I", "getWhileToken", "()Lorg/catrobat/catroid/utils/lunoscript/Token;", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class WhileStatement implements Statement {
    private final Statement body;
    private final Expression condition;
    private final int line;
    private final Token whileToken;

    public static /* synthetic */ WhileStatement copy$default(WhileStatement whileStatement, Expression expression, Statement statement, Token token, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            expression = whileStatement.condition;
        }
        if ((i2 & 2) != 0) {
            statement = whileStatement.body;
        }
        if ((i2 & 4) != 0) {
            token = whileStatement.whileToken;
        }
        if ((i2 & 8) != 0) {
            i = whileStatement.line;
        }
        return whileStatement.copy(expression, statement, token, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final Expression getCondition() {
        return this.condition;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final Statement getBody() {
        return this.body;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final Token getWhileToken() {
        return this.whileToken;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final int getLine() {
        return this.line;
    }

    public final WhileStatement copy(Expression condition, Statement body, Token whileToken, int line) {
        Intrinsics.checkNotNullParameter(condition, "condition");
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(whileToken, "whileToken");
        return new WhileStatement(condition, body, whileToken, line);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof WhileStatement)) {
            return false;
        }
        WhileStatement whileStatement = (WhileStatement) other;
        return Intrinsics.areEqual(this.condition, whileStatement.condition) && Intrinsics.areEqual(this.body, whileStatement.body) && Intrinsics.areEqual(this.whileToken, whileStatement.whileToken) && this.line == whileStatement.line;
    }

    public int hashCode() {
        return (((((this.condition.hashCode() * 31) + this.body.hashCode()) * 31) + this.whileToken.hashCode()) * 31) + Integer.hashCode(this.line);
    }

    public String toString() {
        return "WhileStatement(condition=" + this.condition + ", body=" + this.body + ", whileToken=" + this.whileToken + ", line=" + this.line + Constant.AFTER_QUTO;
    }

    public WhileStatement(Expression condition, Statement body, Token whileToken, int i) {
        Intrinsics.checkNotNullParameter(condition, "condition");
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(whileToken, "whileToken");
        this.condition = condition;
        this.body = body;
        this.whileToken = whileToken;
        this.line = i;
    }

    public final Statement getBody() {
        return this.body;
    }

    public final Expression getCondition() {
        return this.condition;
    }

    @Override // org.catrobat.catroid.utils.lunoscript.AstNode
    public int getLine() {
        return this.line;
    }

    public final Token getWhileToken() {
        return this.whileToken;
    }
}
