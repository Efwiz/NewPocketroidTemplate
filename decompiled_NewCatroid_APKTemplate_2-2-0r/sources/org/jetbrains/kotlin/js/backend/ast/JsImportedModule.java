package org.jetbrains.kotlin.js.backend.ast;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;

/* JADX INFO: compiled from: JsImportedModule.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B-\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0010\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u000b¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/JsImportedModule;", "", "externalName", "", "internalName", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "plainReference", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "relativeRequirePath", "(Ljava/lang/String;Lorg/jetbrains/kotlin/js/backend/ast/JsName;Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;Ljava/lang/String;)V", "getExternalName", "()Ljava/lang/String;", "getInternalName", "()Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "setInternalName", "(Lorg/jetbrains/kotlin/js/backend/ast/JsName;)V", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/js/backend/ast/JsImportedModuleKey;", "getKey", "()Lorg/jetbrains/kotlin/js/backend/ast/JsImportedModuleKey;", "getPlainReference", "()Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "getRelativeRequirePath", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsImportedModule {
    private final String externalName;
    private JsName internalName;
    private final JsImportedModuleKey key;
    private final JsExpression plainReference;
    private final String relativeRequirePath;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public JsImportedModule(String externalName, JsName internalName, JsExpression jsExpression) {
        this(externalName, internalName, jsExpression, null, 8, null);
        Intrinsics.checkNotNullParameter(externalName, "externalName");
        Intrinsics.checkNotNullParameter(internalName, "internalName");
    }

    public JsImportedModule(String externalName, JsName internalName, JsExpression jsExpression, String str) {
        Intrinsics.checkNotNullParameter(externalName, "externalName");
        Intrinsics.checkNotNullParameter(internalName, "internalName");
        this.externalName = externalName;
        this.internalName = internalName;
        this.plainReference = jsExpression;
        this.relativeRequirePath = str;
        this.key = new JsImportedModuleKey(externalName, jsExpression != null ? jsExpression.toString() : null);
    }

    public /* synthetic */ JsImportedModule(String str, JsName jsName, JsExpression jsExpression, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, jsName, jsExpression, (i & 8) != 0 ? null : str2);
    }

    public final String getExternalName() {
        return this.externalName;
    }

    public final JsName getInternalName() {
        return this.internalName;
    }

    public final void setInternalName(JsName jsName) {
        Intrinsics.checkNotNullParameter(jsName, "<set-?>");
        this.internalName = jsName;
    }

    public final JsExpression getPlainReference() {
        return this.plainReference;
    }

    public final String getRelativeRequirePath() {
        return this.relativeRequirePath;
    }

    public final JsImportedModuleKey getKey() {
        return this.key;
    }
}
