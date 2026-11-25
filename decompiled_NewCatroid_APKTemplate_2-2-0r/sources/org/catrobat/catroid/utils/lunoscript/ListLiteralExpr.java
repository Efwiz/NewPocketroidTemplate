package org.catrobat.catroid.utils.lunoscript;

import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.archive.PathTree;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/ListLiteralExpr.class
  classes14.dex
 */
/* JADX INFO: compiled from: AstNodes.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B#\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u000f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0007HÆ\u0003J-\u0010\u0012\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0007HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001a"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/ListLiteralExpr;", "Lorg/catrobat/catroid/utils/lunoscript/Expression;", PathTree.NAME_elements, "", "bracket", "Lorg/catrobat/catroid/utils/lunoscript/Token;", Constant.LINE, "", "(Ljava/util/List;Lorg/catrobat/catroid/utils/lunoscript/Token;I)V", "getBracket", "()Lorg/catrobat/catroid/utils/lunoscript/Token;", "getElements", "()Ljava/util/List;", "getLine", "()I", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ListLiteralExpr implements Expression {
    private final Token bracket;
    private final List<Expression> elements;
    private final int line;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ListLiteralExpr copy$default(ListLiteralExpr listLiteralExpr, List list, Token token, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            list = listLiteralExpr.elements;
        }
        if ((i2 & 2) != 0) {
            token = listLiteralExpr.bracket;
        }
        if ((i2 & 4) != 0) {
            i = listLiteralExpr.line;
        }
        return listLiteralExpr.copy(list, token, i);
    }

    public final List<Expression> component1() {
        return this.elements;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final Token getBracket() {
        return this.bracket;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final int getLine() {
        return this.line;
    }

    public final ListLiteralExpr copy(List<? extends Expression> elements, Token bracket, int line) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        Intrinsics.checkNotNullParameter(bracket, "bracket");
        return new ListLiteralExpr(elements, bracket, line);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ListLiteralExpr)) {
            return false;
        }
        ListLiteralExpr listLiteralExpr = (ListLiteralExpr) other;
        return Intrinsics.areEqual(this.elements, listLiteralExpr.elements) && Intrinsics.areEqual(this.bracket, listLiteralExpr.bracket) && this.line == listLiteralExpr.line;
    }

    public int hashCode() {
        return (((this.elements.hashCode() * 31) + this.bracket.hashCode()) * 31) + Integer.hashCode(this.line);
    }

    public String toString() {
        return "ListLiteralExpr(elements=" + this.elements + ", bracket=" + this.bracket + ", line=" + this.line + Constant.AFTER_QUTO;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ListLiteralExpr(List<? extends Expression> elements, Token bracket, int i) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        Intrinsics.checkNotNullParameter(bracket, "bracket");
        this.elements = elements;
        this.bracket = bracket;
        this.line = i;
    }

    public final Token getBracket() {
        return this.bracket;
    }

    public final List<Expression> getElements() {
        return this.elements;
    }

    @Override // org.catrobat.catroid.utils.lunoscript.AstNode
    public int getLine() {
        return this.line;
    }
}
