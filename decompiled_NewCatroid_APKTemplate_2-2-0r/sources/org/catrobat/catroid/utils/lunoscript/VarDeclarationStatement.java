package org.catrobat.catroid.utils.lunoscript;

import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/VarDeclarationStatement.class
  classes14.dex
 */
/* JADX INFO: compiled from: AstNodes.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0007HÆ\u0003J\t\u0010\u0015\u001a\u00020\tHÆ\u0003J3\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\tHÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00072\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019HÖ\u0003J\t\u0010\u001a\u001a\u00020\tHÖ\u0001J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\rR\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u001d"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/VarDeclarationStatement;", "Lorg/catrobat/catroid/utils/lunoscript/Statement;", "name", "Lorg/catrobat/catroid/utils/lunoscript/Token;", "initializer", "Lorg/catrobat/catroid/utils/lunoscript/Expression;", "isConstant", "", Constant.LINE, "", "(Lorg/catrobat/catroid/utils/lunoscript/Token;Lorg/catrobat/catroid/utils/lunoscript/Expression;ZI)V", "getInitializer", "()Lorg/catrobat/catroid/utils/lunoscript/Expression;", "()Z", "getLine", "()I", "getName", "()Lorg/catrobat/catroid/utils/lunoscript/Token;", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class VarDeclarationStatement implements Statement {
    private final Expression initializer;
    private final boolean isConstant;
    private final int line;
    private final Token name;

    public static /* synthetic */ VarDeclarationStatement copy$default(VarDeclarationStatement varDeclarationStatement, Token token, Expression expression, boolean z, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            token = varDeclarationStatement.name;
        }
        if ((i2 & 2) != 0) {
            expression = varDeclarationStatement.initializer;
        }
        if ((i2 & 4) != 0) {
            z = varDeclarationStatement.isConstant;
        }
        if ((i2 & 8) != 0) {
            i = varDeclarationStatement.line;
        }
        return varDeclarationStatement.copy(token, expression, z, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final Token getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final Expression getInitializer() {
        return this.initializer;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final boolean getIsConstant() {
        return this.isConstant;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final int getLine() {
        return this.line;
    }

    public final VarDeclarationStatement copy(Token name2, Expression initializer, boolean isConstant, int line) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return new VarDeclarationStatement(name2, initializer, isConstant, line);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof VarDeclarationStatement)) {
            return false;
        }
        VarDeclarationStatement varDeclarationStatement = (VarDeclarationStatement) other;
        return Intrinsics.areEqual(this.name, varDeclarationStatement.name) && Intrinsics.areEqual(this.initializer, varDeclarationStatement.initializer) && this.isConstant == varDeclarationStatement.isConstant && this.line == varDeclarationStatement.line;
    }

    public int hashCode() {
        int iHashCode = this.name.hashCode() * 31;
        Expression expression = this.initializer;
        return ((((iHashCode + (expression == null ? 0 : expression.hashCode())) * 31) + Boolean.hashCode(this.isConstant)) * 31) + Integer.hashCode(this.line);
    }

    public String toString() {
        return "VarDeclarationStatement(name=" + this.name + ", initializer=" + this.initializer + ", isConstant=" + this.isConstant + ", line=" + this.line + Constant.AFTER_QUTO;
    }

    public VarDeclarationStatement(Token name2, Expression expression, boolean z, int i) {
        Intrinsics.checkNotNullParameter(name2, "name");
        this.name = name2;
        this.initializer = expression;
        this.isConstant = z;
        this.line = i;
    }

    public final Token getName() {
        return this.name;
    }

    public final Expression getInitializer() {
        return this.initializer;
    }

    public final boolean isConstant() {
        return this.isConstant;
    }

    @Override // org.catrobat.catroid.utils.lunoscript.AstNode
    public int getLine() {
        return this.line;
    }
}
