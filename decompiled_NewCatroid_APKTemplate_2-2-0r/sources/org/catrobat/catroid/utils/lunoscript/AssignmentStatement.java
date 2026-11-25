package org.catrobat.catroid.utils.lunoscript;

import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/AssignmentStatement.class
  classes14.dex
 */
/* JADX INFO: compiled from: AstNodes.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0014\u001a\u00020\bHÆ\u0003J1\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bHÆ\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019HÖ\u0003J\t\u0010\u001a\u001a\u00020\bHÖ\u0001J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000f¨\u0006\u001d"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/AssignmentStatement;", "Lorg/catrobat/catroid/utils/lunoscript/Statement;", "target", "Lorg/catrobat/catroid/utils/lunoscript/Expression;", "value", "operatorToken", "Lorg/catrobat/catroid/utils/lunoscript/Token;", Constant.LINE, "", "(Lorg/catrobat/catroid/utils/lunoscript/Expression;Lorg/catrobat/catroid/utils/lunoscript/Expression;Lorg/catrobat/catroid/utils/lunoscript/Token;I)V", "getLine", "()I", "getOperatorToken", "()Lorg/catrobat/catroid/utils/lunoscript/Token;", "getTarget", "()Lorg/catrobat/catroid/utils/lunoscript/Expression;", "getValue", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class AssignmentStatement implements Statement {
    private final int line;
    private final Token operatorToken;
    private final Expression target;
    private final Expression value;

    public static /* synthetic */ AssignmentStatement copy$default(AssignmentStatement assignmentStatement, Expression expression, Expression expression2, Token token, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            expression = assignmentStatement.target;
        }
        if ((i2 & 2) != 0) {
            expression2 = assignmentStatement.value;
        }
        if ((i2 & 4) != 0) {
            token = assignmentStatement.operatorToken;
        }
        if ((i2 & 8) != 0) {
            i = assignmentStatement.line;
        }
        return assignmentStatement.copy(expression, expression2, token, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final Expression getTarget() {
        return this.target;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final Expression getValue() {
        return this.value;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final Token getOperatorToken() {
        return this.operatorToken;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final int getLine() {
        return this.line;
    }

    public final AssignmentStatement copy(Expression target, Expression value, Token operatorToken, int line) {
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(operatorToken, "operatorToken");
        return new AssignmentStatement(target, value, operatorToken, line);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AssignmentStatement)) {
            return false;
        }
        AssignmentStatement assignmentStatement = (AssignmentStatement) other;
        return Intrinsics.areEqual(this.target, assignmentStatement.target) && Intrinsics.areEqual(this.value, assignmentStatement.value) && Intrinsics.areEqual(this.operatorToken, assignmentStatement.operatorToken) && this.line == assignmentStatement.line;
    }

    public int hashCode() {
        return (((((this.target.hashCode() * 31) + this.value.hashCode()) * 31) + this.operatorToken.hashCode()) * 31) + Integer.hashCode(this.line);
    }

    public String toString() {
        return "AssignmentStatement(target=" + this.target + ", value=" + this.value + ", operatorToken=" + this.operatorToken + ", line=" + this.line + Constant.AFTER_QUTO;
    }

    public AssignmentStatement(Expression target, Expression value, Token operatorToken, int i) {
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(operatorToken, "operatorToken");
        this.target = target;
        this.value = value;
        this.operatorToken = operatorToken;
        this.line = i;
    }

    @Override // org.catrobat.catroid.utils.lunoscript.AstNode
    public int getLine() {
        return this.line;
    }

    public final Token getOperatorToken() {
        return this.operatorToken;
    }

    public final Expression getTarget() {
        return this.target;
    }

    public final Expression getValue() {
        return this.value;
    }
}
