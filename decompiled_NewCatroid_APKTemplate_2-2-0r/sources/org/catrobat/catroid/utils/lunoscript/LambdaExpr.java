package org.catrobat.catroid.utils.lunoscript;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/LambdaExpr.class
  classes14.dex
 */
/* JADX INFO: compiled from: AstNodes.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B#\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u000f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0012\u001a\u00020\bHÆ\u0003J-\u0010\u0013\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bHÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017HÖ\u0003J\t\u0010\u0018\u001a\u00020\bHÖ\u0001J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001b"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/LambdaExpr;", "Lorg/catrobat/catroid/utils/lunoscript/Expression;", "params", "", "Lorg/catrobat/catroid/utils/lunoscript/Token;", "body", "Lorg/catrobat/catroid/utils/lunoscript/BlockStatement;", Constant.LINE, "", "(Ljava/util/List;Lorg/catrobat/catroid/utils/lunoscript/BlockStatement;I)V", "getBody", "()Lorg/catrobat/catroid/utils/lunoscript/BlockStatement;", "getLine", "()I", "getParams", "()Ljava/util/List;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class LambdaExpr implements Expression {
    private final BlockStatement body;
    private final int line;
    private final List<Token> params;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ LambdaExpr copy$default(LambdaExpr lambdaExpr, List list, BlockStatement blockStatement, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            list = lambdaExpr.params;
        }
        if ((i2 & 2) != 0) {
            blockStatement = lambdaExpr.body;
        }
        if ((i2 & 4) != 0) {
            i = lambdaExpr.line;
        }
        return lambdaExpr.copy(list, blockStatement, i);
    }

    public final List<Token> component1() {
        return this.params;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final BlockStatement getBody() {
        return this.body;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final int getLine() {
        return this.line;
    }

    public final LambdaExpr copy(List<Token> params, BlockStatement body, int line) {
        Intrinsics.checkNotNullParameter(params, "params");
        Intrinsics.checkNotNullParameter(body, "body");
        return new LambdaExpr(params, body, line);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LambdaExpr)) {
            return false;
        }
        LambdaExpr lambdaExpr = (LambdaExpr) other;
        return Intrinsics.areEqual(this.params, lambdaExpr.params) && Intrinsics.areEqual(this.body, lambdaExpr.body) && this.line == lambdaExpr.line;
    }

    public int hashCode() {
        return (((this.params.hashCode() * 31) + this.body.hashCode()) * 31) + Integer.hashCode(this.line);
    }

    public String toString() {
        return "LambdaExpr(params=" + this.params + ", body=" + this.body + ", line=" + this.line + Constant.AFTER_QUTO;
    }

    public LambdaExpr(List<Token> params, BlockStatement body, int i) {
        Intrinsics.checkNotNullParameter(params, "params");
        Intrinsics.checkNotNullParameter(body, "body");
        this.params = params;
        this.body = body;
        this.line = i;
    }

    public final List<Token> getParams() {
        return this.params;
    }

    public final BlockStatement getBody() {
        return this.body;
    }

    @Override // org.catrobat.catroid.utils.lunoscript.AstNode
    public int getLine() {
        return this.line;
    }
}
