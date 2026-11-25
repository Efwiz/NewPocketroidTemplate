package org.catrobat.catroid.utils.lunoscript;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/CallExpr.class
  classes14.dex
 */
/* JADX INFO: compiled from: AstNodes.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\t\u0010\u0012\u001a\u00020\u0001HÆ\u0003J\u000f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0015\u001a\u00020\bHÆ\u0003J7\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00012\u000e\b\u0002\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00010\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bHÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aHÖ\u0003J\t\u0010\u001b\u001a\u00020\bHÖ\u0001J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u001e"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/CallExpr;", "Lorg/catrobat/catroid/utils/lunoscript/Expression;", "callee", "arguments", "", "parenToken", "Lorg/catrobat/catroid/utils/lunoscript/Token;", Constant.LINE, "", "(Lorg/catrobat/catroid/utils/lunoscript/Expression;Ljava/util/List;Lorg/catrobat/catroid/utils/lunoscript/Token;I)V", "getArguments", "()Ljava/util/List;", "getCallee", "()Lorg/catrobat/catroid/utils/lunoscript/Expression;", "getLine", "()I", "getParenToken", "()Lorg/catrobat/catroid/utils/lunoscript/Token;", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class CallExpr implements Expression {
    private final List<Expression> arguments;
    private final Expression callee;
    private final int line;
    private final Token parenToken;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ CallExpr copy$default(CallExpr callExpr, Expression expression, List list, Token token, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            expression = callExpr.callee;
        }
        if ((i2 & 2) != 0) {
            list = callExpr.arguments;
        }
        if ((i2 & 4) != 0) {
            token = callExpr.parenToken;
        }
        if ((i2 & 8) != 0) {
            i = callExpr.line;
        }
        return callExpr.copy(expression, list, token, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final Expression getCallee() {
        return this.callee;
    }

    public final List<Expression> component2() {
        return this.arguments;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final Token getParenToken() {
        return this.parenToken;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final int getLine() {
        return this.line;
    }

    public final CallExpr copy(Expression callee, List<? extends Expression> arguments, Token parenToken, int line) {
        Intrinsics.checkNotNullParameter(callee, "callee");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(parenToken, "parenToken");
        return new CallExpr(callee, arguments, parenToken, line);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CallExpr)) {
            return false;
        }
        CallExpr callExpr = (CallExpr) other;
        return Intrinsics.areEqual(this.callee, callExpr.callee) && Intrinsics.areEqual(this.arguments, callExpr.arguments) && Intrinsics.areEqual(this.parenToken, callExpr.parenToken) && this.line == callExpr.line;
    }

    public int hashCode() {
        return (((((this.callee.hashCode() * 31) + this.arguments.hashCode()) * 31) + this.parenToken.hashCode()) * 31) + Integer.hashCode(this.line);
    }

    public String toString() {
        return "CallExpr(callee=" + this.callee + ", arguments=" + this.arguments + ", parenToken=" + this.parenToken + ", line=" + this.line + Constant.AFTER_QUTO;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public CallExpr(Expression callee, List<? extends Expression> arguments, Token parenToken, int i) {
        Intrinsics.checkNotNullParameter(callee, "callee");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(parenToken, "parenToken");
        this.callee = callee;
        this.arguments = arguments;
        this.parenToken = parenToken;
        this.line = i;
    }

    public final Expression getCallee() {
        return this.callee;
    }

    public final List<Expression> getArguments() {
        return this.arguments;
    }

    public final Token getParenToken() {
        return this.parenToken;
    }

    @Override // org.catrobat.catroid.utils.lunoscript.AstNode
    public int getLine() {
        return this.line;
    }
}
