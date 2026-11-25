package org.catrobat.catroid.utils.lunoscript;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/ClassDeclarationStatement.class
  classes14.dex
 */
/* JADX INFO: compiled from: AstNodes.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\bHÆ\u0003J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\nHÆ\u0003J\t\u0010\u001c\u001a\u00020\fHÆ\u0003JE\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\u000b\u001a\u00020\fHÆ\u0001J\u0013\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010!HÖ\u0003J\t\u0010\"\u001a\u00020\fHÖ\u0001J\t\u0010#\u001a\u00020$HÖ\u0001R\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017¨\u0006%"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/ClassDeclarationStatement;", "Lorg/catrobat/catroid/utils/lunoscript/Statement;", "name", "Lorg/catrobat/catroid/utils/lunoscript/Token;", "methods", "", "Lorg/catrobat/catroid/utils/lunoscript/FunDeclarationStatement;", "superclass", "Lorg/catrobat/catroid/utils/lunoscript/VariableExpr;", "staticBlock", "Lorg/catrobat/catroid/utils/lunoscript/BlockStatement;", Constant.LINE, "", "(Lorg/catrobat/catroid/utils/lunoscript/Token;Ljava/util/List;Lorg/catrobat/catroid/utils/lunoscript/VariableExpr;Lorg/catrobat/catroid/utils/lunoscript/BlockStatement;I)V", "getLine", "()I", "getMethods", "()Ljava/util/List;", "getName", "()Lorg/catrobat/catroid/utils/lunoscript/Token;", "getStaticBlock", "()Lorg/catrobat/catroid/utils/lunoscript/BlockStatement;", "getSuperclass", "()Lorg/catrobat/catroid/utils/lunoscript/VariableExpr;", "component1", "component2", "component3", "component4", "component5", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ClassDeclarationStatement implements Statement {
    private final int line;
    private final List<FunDeclarationStatement> methods;
    private final Token name;
    private final BlockStatement staticBlock;
    private final VariableExpr superclass;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ClassDeclarationStatement copy$default(ClassDeclarationStatement classDeclarationStatement, Token token, List list, VariableExpr variableExpr, BlockStatement blockStatement, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            token = classDeclarationStatement.name;
        }
        if ((i2 & 2) != 0) {
            list = classDeclarationStatement.methods;
        }
        List list2 = list;
        if ((i2 & 4) != 0) {
            variableExpr = classDeclarationStatement.superclass;
        }
        VariableExpr variableExpr2 = variableExpr;
        if ((i2 & 8) != 0) {
            blockStatement = classDeclarationStatement.staticBlock;
        }
        BlockStatement blockStatement2 = blockStatement;
        if ((i2 & 16) != 0) {
            i = classDeclarationStatement.line;
        }
        return classDeclarationStatement.copy(token, list2, variableExpr2, blockStatement2, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final Token getName() {
        return this.name;
    }

    public final List<FunDeclarationStatement> component2() {
        return this.methods;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final VariableExpr getSuperclass() {
        return this.superclass;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final BlockStatement getStaticBlock() {
        return this.staticBlock;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final int getLine() {
        return this.line;
    }

    public final ClassDeclarationStatement copy(Token name2, List<FunDeclarationStatement> methods, VariableExpr superclass, BlockStatement staticBlock, int line) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(methods, "methods");
        return new ClassDeclarationStatement(name2, methods, superclass, staticBlock, line);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ClassDeclarationStatement)) {
            return false;
        }
        ClassDeclarationStatement classDeclarationStatement = (ClassDeclarationStatement) other;
        return Intrinsics.areEqual(this.name, classDeclarationStatement.name) && Intrinsics.areEqual(this.methods, classDeclarationStatement.methods) && Intrinsics.areEqual(this.superclass, classDeclarationStatement.superclass) && Intrinsics.areEqual(this.staticBlock, classDeclarationStatement.staticBlock) && this.line == classDeclarationStatement.line;
    }

    public int hashCode() {
        int iHashCode = ((this.name.hashCode() * 31) + this.methods.hashCode()) * 31;
        VariableExpr variableExpr = this.superclass;
        int iHashCode2 = (iHashCode + (variableExpr == null ? 0 : variableExpr.hashCode())) * 31;
        BlockStatement blockStatement = this.staticBlock;
        return ((iHashCode2 + (blockStatement != null ? blockStatement.hashCode() : 0)) * 31) + Integer.hashCode(this.line);
    }

    public String toString() {
        return "ClassDeclarationStatement(name=" + this.name + ", methods=" + this.methods + ", superclass=" + this.superclass + ", staticBlock=" + this.staticBlock + ", line=" + this.line + Constant.AFTER_QUTO;
    }

    public ClassDeclarationStatement(Token name2, List<FunDeclarationStatement> methods, VariableExpr variableExpr, BlockStatement blockStatement, int i) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(methods, "methods");
        this.name = name2;
        this.methods = methods;
        this.superclass = variableExpr;
        this.staticBlock = blockStatement;
        this.line = i;
    }

    @Override // org.catrobat.catroid.utils.lunoscript.AstNode
    public int getLine() {
        return this.line;
    }

    public final List<FunDeclarationStatement> getMethods() {
        return this.methods;
    }

    public final Token getName() {
        return this.name;
    }

    public final BlockStatement getStaticBlock() {
        return this.staticBlock;
    }

    public final VariableExpr getSuperclass() {
        return this.superclass;
    }
}
