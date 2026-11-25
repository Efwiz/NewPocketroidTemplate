package org.jetbrains.kotlin.js.inline.util;

import androidx.exifinterface.media.ExifInterface;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.inline.util.rewriters.NameReplacingVisitor;
import org.jetbrains.kotlin.js.inline.util.rewriters.ThisReplacingVisitor;

/* JADX INFO: compiled from: rewriteUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u001a1\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u0002H\u00012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\u0002\u0010\b\u001a%\u0010\t\u001a\u00020\n\"\b\b\u0000\u0010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u0002H\u00012\u0006\u0010\u000b\u001a\u00020\u0007¢\u0006\u0002\u0010\f¨\u0006\r"}, d2 = {"replaceNames", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "node", "replaceMap", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsNode;Ljava/util/Map;)Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "replaceThisReference", "", "replacement", "(Lorg/jetbrains/kotlin/js/backend/ast/JsNode;Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)V", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class RewriteUtilsKt {
    public static final <T extends JsNode> T replaceNames(T node, Map<JsName, ? extends JsExpression> replaceMap) {
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(replaceMap, "replaceMap");
        if (replaceMap.isEmpty()) {
            return node;
        }
        T t = (T) new NameReplacingVisitor(replaceMap).accept(node);
        Intrinsics.checkNotNull(t);
        return t;
    }

    public static final <T extends JsNode> void replaceThisReference(T node, JsExpression replacement) {
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        new ThisReplacingVisitor(replacement).accept(node);
    }
}
