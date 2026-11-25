package org.catrobat.catroid.utils.lunoscript;

import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.arsc.chunk.TypeBlock;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/MapLiteralExpr.class
  classes14.dex
 */
/* JADX INFO: compiled from: AstNodes.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B)\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0015\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0004HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0007HÆ\u0003J3\u0010\u0012\u001a\u00020\u00002\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0007HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001a"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/MapLiteralExpr;", "Lorg/catrobat/catroid/utils/lunoscript/Expression;", TypeBlock.NAME_entries, "", "Lorg/catrobat/catroid/utils/lunoscript/Token;", "brace", Constant.LINE, "", "(Ljava/util/Map;Lorg/catrobat/catroid/utils/lunoscript/Token;I)V", "getBrace", "()Lorg/catrobat/catroid/utils/lunoscript/Token;", "getEntries", "()Ljava/util/Map;", "getLine", "()I", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class MapLiteralExpr implements Expression {
    private final Token brace;
    private final Map<Token, Expression> entries;
    private final int line;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ MapLiteralExpr copy$default(MapLiteralExpr mapLiteralExpr, Map map, Token token, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            map = mapLiteralExpr.entries;
        }
        if ((i2 & 2) != 0) {
            token = mapLiteralExpr.brace;
        }
        if ((i2 & 4) != 0) {
            i = mapLiteralExpr.line;
        }
        return mapLiteralExpr.copy(map, token, i);
    }

    public final Map<Token, Expression> component1() {
        return this.entries;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final Token getBrace() {
        return this.brace;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final int getLine() {
        return this.line;
    }

    public final MapLiteralExpr copy(Map<Token, ? extends Expression> entries, Token brace, int line) {
        Intrinsics.checkNotNullParameter(entries, "entries");
        Intrinsics.checkNotNullParameter(brace, "brace");
        return new MapLiteralExpr(entries, brace, line);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MapLiteralExpr)) {
            return false;
        }
        MapLiteralExpr mapLiteralExpr = (MapLiteralExpr) other;
        return Intrinsics.areEqual(this.entries, mapLiteralExpr.entries) && Intrinsics.areEqual(this.brace, mapLiteralExpr.brace) && this.line == mapLiteralExpr.line;
    }

    public int hashCode() {
        return (((this.entries.hashCode() * 31) + this.brace.hashCode()) * 31) + Integer.hashCode(this.line);
    }

    public String toString() {
        return "MapLiteralExpr(entries=" + this.entries + ", brace=" + this.brace + ", line=" + this.line + Constant.AFTER_QUTO;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public MapLiteralExpr(Map<Token, ? extends Expression> entries, Token brace, int i) {
        Intrinsics.checkNotNullParameter(entries, "entries");
        Intrinsics.checkNotNullParameter(brace, "brace");
        this.entries = entries;
        this.brace = brace;
        this.line = i;
    }

    public final Token getBrace() {
        return this.brace;
    }

    public final Map<Token, Expression> getEntries() {
        return this.entries;
    }

    @Override // org.catrobat.catroid.utils.lunoscript.AstNode
    public int getLine() {
        return this.line;
    }
}
