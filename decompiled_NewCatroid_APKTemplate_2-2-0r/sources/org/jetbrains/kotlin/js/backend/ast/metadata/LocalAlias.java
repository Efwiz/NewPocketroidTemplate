package org.jetbrains.kotlin.js.backend.ast.metadata;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: metadataProperties.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u001f\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/metadata/LocalAlias;", "", "name", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "tag", "", "(Lorg/jetbrains/kotlin/js/backend/ast/JsName;Ljava/lang/String;)V", "getName", "()Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "getTag", "()Ljava/lang/String;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class LocalAlias {
    private final JsName name;
    private final String tag;

    public static /* synthetic */ LocalAlias copy$default(LocalAlias localAlias, JsName jsName, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            jsName = localAlias.name;
        }
        if ((i & 2) != 0) {
            str = localAlias.tag;
        }
        return localAlias.copy(jsName, str);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final JsName getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getTag() {
        return this.tag;
    }

    public final LocalAlias copy(JsName name2, String tag) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return new LocalAlias(name2, tag);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LocalAlias)) {
            return false;
        }
        LocalAlias localAlias = (LocalAlias) other;
        return Intrinsics.areEqual(this.name, localAlias.name) && Intrinsics.areEqual(this.tag, localAlias.tag);
    }

    public int hashCode() {
        int iHashCode = this.name.hashCode() * 31;
        String str = this.tag;
        return iHashCode + (str == null ? 0 : str.hashCode());
    }

    public String toString() {
        return "LocalAlias(name=" + this.name + ", tag=" + this.tag + ')';
    }

    public LocalAlias(JsName name2, String str) {
        Intrinsics.checkNotNullParameter(name2, "name");
        this.name = name2;
        this.tag = str;
    }

    public final JsName getName() {
        return this.name;
    }

    public final String getTag() {
        return this.tag;
    }
}
