package org.jetbrains.kotlin.resolve.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContextDelegate;

/* JADX INFO: compiled from: JvmTypeSpecificityComparatorDelegate.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/JvmTypeSpecificityComparatorDelegate;", "Lorg/jetbrains/kotlin/resolve/jvm/JvmTypeSpecificityComparator;", "context", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContextDelegate;", "(Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContextDelegate;)V", "getContext", "()Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContextDelegate;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmTypeSpecificityComparatorDelegate extends JvmTypeSpecificityComparator {
    private final TypeSystemInferenceExtensionContextDelegate context;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JvmTypeSpecificityComparatorDelegate(TypeSystemInferenceExtensionContextDelegate context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // org.jetbrains.kotlin.resolve.jvm.JvmTypeSpecificityComparator
    public TypeSystemInferenceExtensionContextDelegate getContext() {
        return this.context;
    }
}
