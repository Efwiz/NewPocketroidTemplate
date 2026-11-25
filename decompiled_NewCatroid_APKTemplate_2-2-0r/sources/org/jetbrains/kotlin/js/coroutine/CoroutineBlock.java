package org.jetbrains.kotlin.js.coroutine;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;

/* JADX INFO: compiled from: CoroutineBlock.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/js/coroutine/CoroutineBlock;", "", "()V", "jsBlock", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "getJsBlock", "()Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "statements", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "getStatements", "()Ljava/util/List;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CoroutineBlock {
    private final JsBlock jsBlock;
    private final List<JsStatement> statements;

    public CoroutineBlock() {
        ArrayList arrayList = new ArrayList();
        this.statements = arrayList;
        this.jsBlock = new JsBlock(arrayList);
    }

    public final List<JsStatement> getStatements() {
        return this.statements;
    }

    public final JsBlock getJsBlock() {
        return this.jsBlock;
    }
}
