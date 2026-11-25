package org.jetbrains.kotlin.js.backend.ast;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata;
import org.jetbrains.kotlin.js.util.AstUtil;

/* JADX INFO: compiled from: JsCompositeBlock.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0007\b\u0016¢\u0006\u0002\u0010\u0002B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0015\b\u0016\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0007¢\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u00020\u0000H\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/JsCompositeBlock;", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "()V", "statement", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;)V", "statements", "", "(Ljava/util/List;)V", "deepCopy", "isTransparent", "", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsCompositeBlock extends JsBlock {
    @Override // org.jetbrains.kotlin.js.backend.ast.JsBlock
    public boolean isTransparent() {
        return true;
    }

    public JsCompositeBlock() {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsCompositeBlock(JsStatement statement) {
        super(statement);
        Intrinsics.checkNotNullParameter(statement, "statement");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsCompositeBlock(List<? extends JsStatement> statements) {
        super((List<JsStatement>) statements);
        Intrinsics.checkNotNullParameter(statements, "statements");
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsBlock, org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsCompositeBlock deepCopy() {
        JsCompositeBlock jsCompositeBlock = new JsCompositeBlock();
        List listDeepCopy = AstUtil.deepCopy(getStatements());
        Intrinsics.checkNotNullExpressionValue(listDeepCopy, "deepCopy(...)");
        jsCompositeBlock.getStatements().addAll(listDeepCopy);
        HasMetadata hasMetadataWithMetadataFrom = jsCompositeBlock.withMetadataFrom(this);
        Intrinsics.checkNotNullExpressionValue(hasMetadataWithMetadataFrom, "withMetadataFrom(...)");
        return (JsCompositeBlock) hasMetadataWithMetadataFrom;
    }
}
