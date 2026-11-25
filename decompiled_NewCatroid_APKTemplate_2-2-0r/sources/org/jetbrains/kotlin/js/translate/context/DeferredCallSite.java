package org.jetbrains.kotlin.js.translate.context;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: DeferredCallSite.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/context/DeferredCallSite;", "", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;", "invocationArgs", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "(Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;Ljava/util/List;Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;)V", "getConstructor", "()Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;", "getContext", "()Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "getInvocationArgs", "()Ljava/util/List;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeferredCallSite {
    private final ConstructorDescriptor constructor;
    private final TranslationContext context;
    private final List<JsExpression> invocationArgs;

    public DeferredCallSite(ConstructorDescriptor constructor, List<JsExpression> invocationArgs, TranslationContext context) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(invocationArgs, "invocationArgs");
        Intrinsics.checkNotNullParameter(context, "context");
        this.constructor = constructor;
        this.invocationArgs = invocationArgs;
        this.context = context;
    }

    public final ConstructorDescriptor getConstructor() {
        return this.constructor;
    }

    public final List<JsExpression> getInvocationArgs() {
        return this.invocationArgs;
    }

    public final TranslationContext getContext() {
        return this.context;
    }
}
