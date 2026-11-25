package org.jetbrains.kotlin.backend.common.linkage.issues;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer;

/* JADX INFO: compiled from: IrDeserializationExceptions.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u000e\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/issues/IrDisallowedErrorNode;", "Lorg/jetbrains/kotlin/backend/common/linkage/issues/IrDeserializationException;", "clazz", "Ljava/lang/Class;", "Lorg/jetbrains/kotlin/ir/declarations/IrAnnotationContainer;", "(Ljava/lang/Class;)V", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrDisallowedErrorNode extends IrDeserializationException {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrDisallowedErrorNode(Class<? extends IrAnnotationContainer> clazz) {
        super(clazz.getClass().getSimpleName() + " found but error nodes are not allowed.", null);
        Intrinsics.checkNotNullParameter(clazz, "clazz");
    }
}
