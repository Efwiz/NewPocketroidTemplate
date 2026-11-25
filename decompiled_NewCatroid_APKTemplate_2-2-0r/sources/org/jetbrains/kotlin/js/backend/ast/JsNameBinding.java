package org.jetbrains.kotlin.js.backend.ast;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: JsNameBinding.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/JsNameBinding;", "", ASTExpr.DEFAULT_MAP_KEY_NAME, "", "name", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/js/backend/ast/JsName;)V", "getKey", "()Ljava/lang/String;", "getName", "()Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "setName", "(Lorg/jetbrains/kotlin/js/backend/ast/JsName;)V", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class JsNameBinding {
    private final String key;
    private JsName name;

    public static /* synthetic */ JsNameBinding copy$default(JsNameBinding jsNameBinding, String str, JsName jsName, int i, Object obj) {
        if ((i & 1) != 0) {
            str = jsNameBinding.key;
        }
        if ((i & 2) != 0) {
            jsName = jsNameBinding.name;
        }
        return jsNameBinding.copy(str, jsName);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getKey() {
        return this.key;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final JsName getName() {
        return this.name;
    }

    public final JsNameBinding copy(String key, JsName name2) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(name2, "name");
        return new JsNameBinding(key, name2);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof JsNameBinding)) {
            return false;
        }
        JsNameBinding jsNameBinding = (JsNameBinding) other;
        return Intrinsics.areEqual(this.key, jsNameBinding.key) && Intrinsics.areEqual(this.name, jsNameBinding.name);
    }

    public int hashCode() {
        return (this.key.hashCode() * 31) + this.name.hashCode();
    }

    public String toString() {
        return "JsNameBinding(key=" + this.key + ", name=" + this.name + ')';
    }

    public JsNameBinding(String key, JsName name2) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(name2, "name");
        this.key = key;
        this.name = name2;
    }

    public final String getKey() {
        return this.key;
    }

    public final JsName getName() {
        return this.name;
    }

    public final void setName(JsName jsName) {
        Intrinsics.checkNotNullParameter(jsName, "<set-?>");
        this.name = jsName;
    }
}
