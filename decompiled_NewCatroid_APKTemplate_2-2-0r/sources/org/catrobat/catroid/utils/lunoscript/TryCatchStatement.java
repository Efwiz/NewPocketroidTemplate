package org.catrobat.catroid.utils.lunoscript;

import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/TryCatchStatement.class
  classes14.dex
 */
/* JADX INFO: compiled from: AstNodes.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0001\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0001\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\t\u0010\u0012\u001a\u00020\u0001HÆ\u0003J\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0004HÆ\u0003J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÆ\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÆ\u0003J\t\u0010\u0016\u001a\u00020\bHÆ\u0003JA\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00012\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00012\b\b\u0002\u0010\u0007\u001a\u00020\bHÆ\u0001J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bHÖ\u0003J\t\u0010\u001c\u001a\u00020\bHÖ\u0001J\t\u0010\u001d\u001a\u00020\u001eHÖ\u0001R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0001¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000bR\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000b¨\u0006\u001f"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/TryCatchStatement;", "Lorg/catrobat/catroid/utils/lunoscript/Statement;", "tryBlock", "catchVariable", "Lorg/catrobat/catroid/utils/lunoscript/Token;", "catchBlock", "finallyBlock", Constant.LINE, "", "(Lorg/catrobat/catroid/utils/lunoscript/Statement;Lorg/catrobat/catroid/utils/lunoscript/Token;Lorg/catrobat/catroid/utils/lunoscript/Statement;Lorg/catrobat/catroid/utils/lunoscript/Statement;I)V", "getCatchBlock", "()Lorg/catrobat/catroid/utils/lunoscript/Statement;", "getCatchVariable", "()Lorg/catrobat/catroid/utils/lunoscript/Token;", "getFinallyBlock", "getLine", "()I", "getTryBlock", "component1", "component2", "component3", "component4", "component5", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class TryCatchStatement implements Statement {
    private final Statement catchBlock;
    private final Token catchVariable;
    private final Statement finallyBlock;
    private final int line;
    private final Statement tryBlock;

    public static /* synthetic */ TryCatchStatement copy$default(TryCatchStatement tryCatchStatement, Statement statement, Token token, Statement statement2, Statement statement3, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            statement = tryCatchStatement.tryBlock;
        }
        if ((i2 & 2) != 0) {
            token = tryCatchStatement.catchVariable;
        }
        Token token2 = token;
        if ((i2 & 4) != 0) {
            statement2 = tryCatchStatement.catchBlock;
        }
        Statement statement4 = statement2;
        if ((i2 & 8) != 0) {
            statement3 = tryCatchStatement.finallyBlock;
        }
        Statement statement5 = statement3;
        if ((i2 & 16) != 0) {
            i = tryCatchStatement.line;
        }
        return tryCatchStatement.copy(statement, token2, statement4, statement5, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final Statement getTryBlock() {
        return this.tryBlock;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final Token getCatchVariable() {
        return this.catchVariable;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final Statement getCatchBlock() {
        return this.catchBlock;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final Statement getFinallyBlock() {
        return this.finallyBlock;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final int getLine() {
        return this.line;
    }

    public final TryCatchStatement copy(Statement tryBlock, Token catchVariable, Statement catchBlock, Statement finallyBlock, int line) {
        Intrinsics.checkNotNullParameter(tryBlock, "tryBlock");
        return new TryCatchStatement(tryBlock, catchVariable, catchBlock, finallyBlock, line);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TryCatchStatement)) {
            return false;
        }
        TryCatchStatement tryCatchStatement = (TryCatchStatement) other;
        return Intrinsics.areEqual(this.tryBlock, tryCatchStatement.tryBlock) && Intrinsics.areEqual(this.catchVariable, tryCatchStatement.catchVariable) && Intrinsics.areEqual(this.catchBlock, tryCatchStatement.catchBlock) && Intrinsics.areEqual(this.finallyBlock, tryCatchStatement.finallyBlock) && this.line == tryCatchStatement.line;
    }

    public int hashCode() {
        int iHashCode = this.tryBlock.hashCode() * 31;
        Token token = this.catchVariable;
        int iHashCode2 = (iHashCode + (token == null ? 0 : token.hashCode())) * 31;
        Statement statement = this.catchBlock;
        int iHashCode3 = (iHashCode2 + (statement == null ? 0 : statement.hashCode())) * 31;
        Statement statement2 = this.finallyBlock;
        return ((iHashCode3 + (statement2 != null ? statement2.hashCode() : 0)) * 31) + Integer.hashCode(this.line);
    }

    public String toString() {
        return "TryCatchStatement(tryBlock=" + this.tryBlock + ", catchVariable=" + this.catchVariable + ", catchBlock=" + this.catchBlock + ", finallyBlock=" + this.finallyBlock + ", line=" + this.line + Constant.AFTER_QUTO;
    }

    public TryCatchStatement(Statement tryBlock, Token token, Statement statement, Statement statement2, int i) {
        Intrinsics.checkNotNullParameter(tryBlock, "tryBlock");
        this.tryBlock = tryBlock;
        this.catchVariable = token;
        this.catchBlock = statement;
        this.finallyBlock = statement2;
        this.line = i;
    }

    public final Statement getTryBlock() {
        return this.tryBlock;
    }

    public final Token getCatchVariable() {
        return this.catchVariable;
    }

    public final Statement getCatchBlock() {
        return this.catchBlock;
    }

    public final Statement getFinallyBlock() {
        return this.finallyBlock;
    }

    @Override // org.catrobat.catroid.utils.lunoscript.AstNode
    public int getLine() {
        return this.line;
    }
}
