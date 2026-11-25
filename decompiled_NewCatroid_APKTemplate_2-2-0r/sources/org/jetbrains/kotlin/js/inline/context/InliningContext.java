package org.jetbrains.kotlin.js.inline.context;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;

/* JADX INFO: compiled from: InliningContext.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u000b\u001a\u00020\fR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00030\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/context/InliningContext;", "", "currentStatement", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;)V", "getCurrentStatement", "()Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "previousStatements", "", "getPreviousStatements", "()Ljava/util/List;", "newNamingContext", "Lorg/jetbrains/kotlin/js/inline/context/NamingContext;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InliningContext {
    private final JsStatement currentStatement;
    private final List<JsStatement> previousStatements = new ArrayList();

    public InliningContext(JsStatement jsStatement) {
        this.currentStatement = jsStatement;
    }

    public final JsStatement getCurrentStatement() {
        return this.currentStatement;
    }

    public final List<JsStatement> getPreviousStatements() {
        return this.previousStatements;
    }

    public final NamingContext newNamingContext() {
        return new NamingContext(this.previousStatements);
    }
}
