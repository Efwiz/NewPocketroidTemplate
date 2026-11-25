package org.catrobat.catroid.utils.lunoscript;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/FunDeclarationStatement.class
  classes14.dex
 */
/* JADX INFO: compiled from: AstNodes.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0007HÆ\u0003J\t\u0010\u0016\u001a\u00020\tHÆ\u0003J7\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\tHÆ\u0001J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bHÖ\u0003J\t\u0010\u001c\u001a\u00020\tHÖ\u0001J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001f"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/FunDeclarationStatement;", "Lorg/catrobat/catroid/utils/lunoscript/Statement;", "name", "Lorg/catrobat/catroid/utils/lunoscript/Token;", "params", "", "body", "Lorg/catrobat/catroid/utils/lunoscript/BlockStatement;", Constant.LINE, "", "(Lorg/catrobat/catroid/utils/lunoscript/Token;Ljava/util/List;Lorg/catrobat/catroid/utils/lunoscript/BlockStatement;I)V", "getBody", "()Lorg/catrobat/catroid/utils/lunoscript/BlockStatement;", "getLine", "()I", "getName", "()Lorg/catrobat/catroid/utils/lunoscript/Token;", "getParams", "()Ljava/util/List;", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class FunDeclarationStatement implements Statement {
    private final BlockStatement body;
    private final int line;
    private final Token name;
    private final List<Token> params;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ FunDeclarationStatement copy$default(FunDeclarationStatement funDeclarationStatement, Token token, List list, BlockStatement blockStatement, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            token = funDeclarationStatement.name;
        }
        if ((i2 & 2) != 0) {
            list = funDeclarationStatement.params;
        }
        if ((i2 & 4) != 0) {
            blockStatement = funDeclarationStatement.body;
        }
        if ((i2 & 8) != 0) {
            i = funDeclarationStatement.line;
        }
        return funDeclarationStatement.copy(token, list, blockStatement, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final Token getName() {
        return this.name;
    }

    public final List<Token> component2() {
        return this.params;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final BlockStatement getBody() {
        return this.body;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final int getLine() {
        return this.line;
    }

    public final FunDeclarationStatement copy(Token name2, List<Token> params, BlockStatement body, int line) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(params, "params");
        Intrinsics.checkNotNullParameter(body, "body");
        return new FunDeclarationStatement(name2, params, body, line);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FunDeclarationStatement)) {
            return false;
        }
        FunDeclarationStatement funDeclarationStatement = (FunDeclarationStatement) other;
        return Intrinsics.areEqual(this.name, funDeclarationStatement.name) && Intrinsics.areEqual(this.params, funDeclarationStatement.params) && Intrinsics.areEqual(this.body, funDeclarationStatement.body) && this.line == funDeclarationStatement.line;
    }

    public int hashCode() {
        return (((((this.name.hashCode() * 31) + this.params.hashCode()) * 31) + this.body.hashCode()) * 31) + Integer.hashCode(this.line);
    }

    public String toString() {
        return "FunDeclarationStatement(name=" + this.name + ", params=" + this.params + ", body=" + this.body + ", line=" + this.line + Constant.AFTER_QUTO;
    }

    public FunDeclarationStatement(Token name2, List<Token> params, BlockStatement body, int i) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(params, "params");
        Intrinsics.checkNotNullParameter(body, "body");
        this.name = name2;
        this.params = params;
        this.body = body;
        this.line = i;
    }

    public final BlockStatement getBody() {
        return this.body;
    }

    @Override // org.catrobat.catroid.utils.lunoscript.AstNode
    public int getLine() {
        return this.line;
    }

    public final Token getName() {
        return this.name;
    }

    public final List<Token> getParams() {
        return this.params;
    }
}
