package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsCompositeBlock;
import org.jetbrains.kotlin.js.backend.ast.JsName;

/* JADX INFO: compiled from: JsClassGenerator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrIcClassModel;", "", "superClasses", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "(Ljava/util/List;)V", "postDeclarationBlock", "Lorg/jetbrains/kotlin/js/backend/ast/JsCompositeBlock;", "getPostDeclarationBlock", "()Lorg/jetbrains/kotlin/js/backend/ast/JsCompositeBlock;", "preDeclarationBlock", "getPreDeclarationBlock", "getSuperClasses", "()Ljava/util/List;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsIrIcClassModel {
    private final JsCompositeBlock postDeclarationBlock;
    private final JsCompositeBlock preDeclarationBlock;
    private final List<JsName> superClasses;

    /* JADX WARN: Multi-variable type inference failed */
    public JsIrIcClassModel(List<? extends JsName> superClasses) {
        Intrinsics.checkNotNullParameter(superClasses, "superClasses");
        this.superClasses = superClasses;
        this.preDeclarationBlock = new JsCompositeBlock();
        this.postDeclarationBlock = new JsCompositeBlock();
    }

    public final List<JsName> getSuperClasses() {
        return this.superClasses;
    }

    public final JsCompositeBlock getPreDeclarationBlock() {
        return this.preDeclarationBlock;
    }

    public final JsCompositeBlock getPostDeclarationBlock() {
        return this.postDeclarationBlock;
    }
}
