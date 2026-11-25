package org.jetbrains.kotlin.js.inline.util.collectors;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsObjectLiteral;
import org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor;

/* JADX INFO: compiled from: InstanceCollector.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\u001b\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002H\u0014J\u0010\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u0018H\u0016R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/util/collectors/InstanceCollector;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "Lorg/jetbrains/kotlin/js/backend/ast/RecursiveJsVisitor;", "klass", "Ljava/lang/Class;", "visitNestedDeclarations", "", "(Ljava/lang/Class;Z)V", "collected", "", "getCollected", "()Ljava/util/List;", "getKlass", "()Ljava/lang/Class;", "getVisitNestedDeclarations", "()Z", "visitElement", "", "node", "visitFunction", "x", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "visitObjectLiteral", "Lorg/jetbrains/kotlin/js/backend/ast/JsObjectLiteral;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InstanceCollector<T extends JsNode> extends RecursiveJsVisitor {
    private final List<T> collected;
    private final Class<T> klass;
    private final boolean visitNestedDeclarations;

    public InstanceCollector(Class<T> klass, boolean z) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        this.klass = klass;
        this.visitNestedDeclarations = z;
        this.collected = new ArrayList();
    }

    public final Class<T> getKlass() {
        return this.klass;
    }

    public final boolean getVisitNestedDeclarations() {
        return this.visitNestedDeclarations;
    }

    public final List<T> getCollected() {
        return this.collected;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitFunction(JsFunction x) {
        Intrinsics.checkNotNullParameter(x, "x");
        if (this.visitNestedDeclarations) {
            visitElement(x);
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitor
    public void visitObjectLiteral(JsObjectLiteral x) {
        Intrinsics.checkNotNullParameter(x, "x");
        if (this.visitNestedDeclarations) {
            visitElement(x);
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.RecursiveJsVisitor, org.jetbrains.kotlin.js.backend.ast.JsVisitor
    protected void visitElement(JsNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        if (this.klass.isInstance(node)) {
            List<T> list = this.collected;
            T tCast = this.klass.cast(node);
            Intrinsics.checkNotNull(tCast);
            list.add(tCast);
        }
        super.visitElement(node);
    }
}
