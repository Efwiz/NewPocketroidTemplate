package org.jetbrains.kotlin.js.inline.clean;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsContext;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl;

/* JADX INFO: compiled from: NodeRemover.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B'\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ+\u0010\r\u001a\u00020\u000e\"\b\b\u0001\u0010\u0001*\u00020\u000f2\u0006\u0010\u0010\u001a\u0002H\u00012\n\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\u0012H\u0014¢\u0006\u0002\u0010\u0013R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001d\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/js/inline/clean/NodeRemover;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/js/backend/ast/JsVisitorWithContextImpl;", "klass", "Ljava/lang/Class;", "predicate", "Lkotlin/Function1;", "", "(Ljava/lang/Class;Lkotlin/jvm/functions/Function1;)V", "getKlass", "()Ljava/lang/Class;", "getPredicate", "()Lkotlin/jvm/functions/Function1;", "doTraverse", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "node", "ctx", "Lorg/jetbrains/kotlin/js/backend/ast/JsContext;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsNode;Lorg/jetbrains/kotlin/js/backend/ast/JsContext;)V", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NodeRemover<T> extends JsVisitorWithContextImpl {
    private final Class<T> klass;
    private final Function1<T, Boolean> predicate;

    /* JADX WARN: Multi-variable type inference failed */
    public NodeRemover(Class<T> klass, Function1<? super T, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        this.klass = klass;
        this.predicate = predicate;
    }

    public final Class<T> getKlass() {
        return this.klass;
    }

    public final Function1<T, Boolean> getPredicate() {
        return this.predicate;
    }

    /* JADX WARN: Incorrect types in method signature: <T::Lorg/jetbrains/kotlin/js/backend/ast/JsNode;>(TT;Lorg/jetbrains/kotlin/js/backend/ast/JsContext<*>;)V */
    @Override // org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContextImpl, org.jetbrains.kotlin.js.backend.ast.JsVisitorWithContext
    protected void doTraverse(JsNode node, JsContext ctx) {
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        if (this.klass.isInstance(node)) {
            T tCast = this.klass.cast(node);
            Intrinsics.checkNotNull(tCast);
            if (this.predicate.invoke(tCast).booleanValue()) {
                ctx.removeMe();
                return;
            }
        }
        super.doTraverse(node, ctx);
    }
}
