package org.catrobat.catroid.utils.lunoscript;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/SwitchStatement.class
  classes14.dex
 */
/* JADX INFO: compiled from: AstNodes.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J\t\u0010\u0016\u001a\u00020\bHÆ\u0003J\t\u0010\u0017\u001a\u00020\nHÆ\u0003J7\u0010\u0018\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nHÆ\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cHÖ\u0003J\t\u0010\u001d\u001a\u00020\nHÖ\u0001J\t\u0010\u001e\u001a\u00020\u001fHÖ\u0001R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006 "}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/SwitchStatement;", "Lorg/catrobat/catroid/utils/lunoscript/Statement;", "expression", "Lorg/catrobat/catroid/utils/lunoscript/Expression;", "cases", "", "Lorg/catrobat/catroid/utils/lunoscript/SwitchCase;", "switchToken", "Lorg/catrobat/catroid/utils/lunoscript/Token;", Constant.LINE, "", "(Lorg/catrobat/catroid/utils/lunoscript/Expression;Ljava/util/List;Lorg/catrobat/catroid/utils/lunoscript/Token;I)V", "getCases", "()Ljava/util/List;", "getExpression", "()Lorg/catrobat/catroid/utils/lunoscript/Expression;", "getLine", "()I", "getSwitchToken", "()Lorg/catrobat/catroid/utils/lunoscript/Token;", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class SwitchStatement implements Statement {
    private final List<SwitchCase> cases;
    private final Expression expression;
    private final int line;
    private final Token switchToken;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ SwitchStatement copy$default(SwitchStatement switchStatement, Expression expression, List list, Token token, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            expression = switchStatement.expression;
        }
        if ((i2 & 2) != 0) {
            list = switchStatement.cases;
        }
        if ((i2 & 4) != 0) {
            token = switchStatement.switchToken;
        }
        if ((i2 & 8) != 0) {
            i = switchStatement.line;
        }
        return switchStatement.copy(expression, list, token, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final Expression getExpression() {
        return this.expression;
    }

    public final List<SwitchCase> component2() {
        return this.cases;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final Token getSwitchToken() {
        return this.switchToken;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final int getLine() {
        return this.line;
    }

    public final SwitchStatement copy(Expression expression, List<SwitchCase> cases, Token switchToken, int line) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(cases, "cases");
        Intrinsics.checkNotNullParameter(switchToken, "switchToken");
        return new SwitchStatement(expression, cases, switchToken, line);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SwitchStatement)) {
            return false;
        }
        SwitchStatement switchStatement = (SwitchStatement) other;
        return Intrinsics.areEqual(this.expression, switchStatement.expression) && Intrinsics.areEqual(this.cases, switchStatement.cases) && Intrinsics.areEqual(this.switchToken, switchStatement.switchToken) && this.line == switchStatement.line;
    }

    public int hashCode() {
        return (((((this.expression.hashCode() * 31) + this.cases.hashCode()) * 31) + this.switchToken.hashCode()) * 31) + Integer.hashCode(this.line);
    }

    public String toString() {
        return "SwitchStatement(expression=" + this.expression + ", cases=" + this.cases + ", switchToken=" + this.switchToken + ", line=" + this.line + Constant.AFTER_QUTO;
    }

    public SwitchStatement(Expression expression, List<SwitchCase> cases, Token switchToken, int i) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(cases, "cases");
        Intrinsics.checkNotNullParameter(switchToken, "switchToken");
        this.expression = expression;
        this.cases = cases;
        this.switchToken = switchToken;
        this.line = i;
    }

    public final Expression getExpression() {
        return this.expression;
    }

    public final List<SwitchCase> getCases() {
        return this.cases;
    }

    public final Token getSwitchToken() {
        return this.switchToken;
    }

    @Override // org.catrobat.catroid.utils.lunoscript.AstNode
    public int getLine() {
        return this.line;
    }
}
