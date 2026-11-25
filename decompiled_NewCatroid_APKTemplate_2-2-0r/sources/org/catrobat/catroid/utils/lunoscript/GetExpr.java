package org.catrobat.catroid.utils.lunoscript;

import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/GetExpr.class
  classes14.dex
 */
/* JADX INFO: compiled from: AstNodes.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\t\u0010\u000e\u001a\u00020\u0001HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0004HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0006HÆ\u0003J'\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00012\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0006HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/GetExpr;", "Lorg/catrobat/catroid/utils/lunoscript/Expression;", "obj", "name", "Lorg/catrobat/catroid/utils/lunoscript/Token;", Constant.LINE, "", "(Lorg/catrobat/catroid/utils/lunoscript/Expression;Lorg/catrobat/catroid/utils/lunoscript/Token;I)V", "getLine", "()I", "getName", "()Lorg/catrobat/catroid/utils/lunoscript/Token;", "getObj", "()Lorg/catrobat/catroid/utils/lunoscript/Expression;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class GetExpr implements Expression {
    private final int line;
    private final Token name;
    private final Expression obj;

    public static /* synthetic */ GetExpr copy$default(GetExpr getExpr, Expression expression, Token token, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            expression = getExpr.obj;
        }
        if ((i2 & 2) != 0) {
            token = getExpr.name;
        }
        if ((i2 & 4) != 0) {
            i = getExpr.line;
        }
        return getExpr.copy(expression, token, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final Expression getObj() {
        return this.obj;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final Token getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final int getLine() {
        return this.line;
    }

    public final GetExpr copy(Expression obj, Token name2, int line) {
        Intrinsics.checkNotNullParameter(obj, "obj");
        Intrinsics.checkNotNullParameter(name2, "name");
        return new GetExpr(obj, name2, line);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof GetExpr)) {
            return false;
        }
        GetExpr getExpr = (GetExpr) other;
        return Intrinsics.areEqual(this.obj, getExpr.obj) && Intrinsics.areEqual(this.name, getExpr.name) && this.line == getExpr.line;
    }

    public int hashCode() {
        return (((this.obj.hashCode() * 31) + this.name.hashCode()) * 31) + Integer.hashCode(this.line);
    }

    public String toString() {
        return "GetExpr(obj=" + this.obj + ", name=" + this.name + ", line=" + this.line + Constant.AFTER_QUTO;
    }

    public GetExpr(Expression obj, Token name2, int i) {
        Intrinsics.checkNotNullParameter(obj, "obj");
        Intrinsics.checkNotNullParameter(name2, "name");
        this.obj = obj;
        this.name = name2;
        this.line = i;
    }

    @Override // org.catrobat.catroid.utils.lunoscript.AstNode
    public int getLine() {
        return this.line;
    }

    public final Token getName() {
        return this.name;
    }

    public final Expression getObj() {
        return this.obj;
    }
}
