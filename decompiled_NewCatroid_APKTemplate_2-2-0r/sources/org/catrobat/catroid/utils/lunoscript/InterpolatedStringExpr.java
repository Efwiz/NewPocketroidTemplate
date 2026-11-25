package org.catrobat.catroid.utils.lunoscript;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/InterpolatedStringExpr.class
  classes14.dex
 */
/* JADX INFO: compiled from: AstNodes.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J#\u0010\r\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/InterpolatedStringExpr;", "Lorg/catrobat/catroid/utils/lunoscript/Expression;", "parts", "", Constant.LINE, "", "(Ljava/util/List;I)V", "getLine", "()I", "getParts", "()Ljava/util/List;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class InterpolatedStringExpr implements Expression {
    private final int line;
    private final List<Expression> parts;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ InterpolatedStringExpr copy$default(InterpolatedStringExpr interpolatedStringExpr, List list, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            list = interpolatedStringExpr.parts;
        }
        if ((i2 & 2) != 0) {
            i = interpolatedStringExpr.line;
        }
        return interpolatedStringExpr.copy(list, i);
    }

    public final List<Expression> component1() {
        return this.parts;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getLine() {
        return this.line;
    }

    public final InterpolatedStringExpr copy(List<? extends Expression> parts, int line) {
        Intrinsics.checkNotNullParameter(parts, "parts");
        return new InterpolatedStringExpr(parts, line);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof InterpolatedStringExpr)) {
            return false;
        }
        InterpolatedStringExpr interpolatedStringExpr = (InterpolatedStringExpr) other;
        return Intrinsics.areEqual(this.parts, interpolatedStringExpr.parts) && this.line == interpolatedStringExpr.line;
    }

    public int hashCode() {
        return (this.parts.hashCode() * 31) + Integer.hashCode(this.line);
    }

    public String toString() {
        return "InterpolatedStringExpr(parts=" + this.parts + ", line=" + this.line + Constant.AFTER_QUTO;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public InterpolatedStringExpr(List<? extends Expression> parts, int i) {
        Intrinsics.checkNotNullParameter(parts, "parts");
        this.parts = parts;
        this.line = i;
    }

    @Override // org.catrobat.catroid.utils.lunoscript.AstNode
    public int getLine() {
        return this.line;
    }

    public final List<Expression> getParts() {
        return this.parts;
    }
}
