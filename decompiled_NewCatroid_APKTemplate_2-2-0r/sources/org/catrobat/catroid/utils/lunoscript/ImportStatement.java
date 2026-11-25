package org.catrobat.catroid.utils.lunoscript;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/lunoscript/ImportStatement.class
  classes14.dex
 */
/* JADX INFO: compiled from: AstNodes.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0006HÆ\u0003J#\u0010\u000e\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0006HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0016"}, d2 = {"Lorg/catrobat/catroid/utils/lunoscript/ImportStatement;", "Lorg/catrobat/catroid/utils/lunoscript/Statement;", "path", "", "Lorg/catrobat/catroid/utils/lunoscript/Token;", Constant.LINE, "", "(Ljava/util/List;I)V", "getLine", "()I", "getPath", "()Ljava/util/List;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ImportStatement implements Statement {
    private final int line;
    private final List<Token> path;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ImportStatement copy$default(ImportStatement importStatement, List list, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            list = importStatement.path;
        }
        if ((i2 & 2) != 0) {
            i = importStatement.line;
        }
        return importStatement.copy(list, i);
    }

    public final List<Token> component1() {
        return this.path;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getLine() {
        return this.line;
    }

    public final ImportStatement copy(List<Token> path, int line) {
        Intrinsics.checkNotNullParameter(path, "path");
        return new ImportStatement(path, line);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ImportStatement)) {
            return false;
        }
        ImportStatement importStatement = (ImportStatement) other;
        return Intrinsics.areEqual(this.path, importStatement.path) && this.line == importStatement.line;
    }

    public int hashCode() {
        return (this.path.hashCode() * 31) + Integer.hashCode(this.line);
    }

    public String toString() {
        return "ImportStatement(path=" + this.path + ", line=" + this.line + Constant.AFTER_QUTO;
    }

    public ImportStatement(List<Token> path, int i) {
        Intrinsics.checkNotNullParameter(path, "path");
        this.path = path;
        this.line = i;
    }

    public final List<Token> getPath() {
        return this.path;
    }

    @Override // org.catrobat.catroid.utils.lunoscript.AstNode
    public int getLine() {
        return this.line;
    }
}
