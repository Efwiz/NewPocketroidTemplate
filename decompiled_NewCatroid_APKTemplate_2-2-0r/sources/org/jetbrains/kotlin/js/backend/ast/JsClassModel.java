package org.jetbrains.kotlin.js.backend.ast;

import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: JsClassModel.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010#\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000b¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/JsClassModel;", "", "name", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "superName", "(Lorg/jetbrains/kotlin/js/backend/ast/JsName;Lorg/jetbrains/kotlin/js/backend/ast/JsName;)V", Namer.METADATA_SUPERTYPES, "", "getInterfaces", "()Ljava/util/Set;", "getName", "()Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "postDeclarationBlock", "Lorg/jetbrains/kotlin/js/backend/ast/JsCompositeBlock;", "getPostDeclarationBlock", "()Lorg/jetbrains/kotlin/js/backend/ast/JsCompositeBlock;", "getSuperName", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsClassModel {
    private final Set<JsName> interfaces;
    private final JsName name;
    private final JsCompositeBlock postDeclarationBlock;
    private final JsName superName;

    public JsClassModel(JsName name2, JsName jsName) {
        Intrinsics.checkNotNullParameter(name2, "name");
        this.name = name2;
        this.superName = jsName;
        this.interfaces = new LinkedHashSet();
        this.postDeclarationBlock = new JsCompositeBlock();
    }

    public final JsName getName() {
        return this.name;
    }

    public final JsName getSuperName() {
        return this.superName;
    }

    public final Set<JsName> getInterfaces() {
        return this.interfaces;
    }

    public final JsCompositeBlock getPostDeclarationBlock() {
        return this.postDeclarationBlock;
    }
}
