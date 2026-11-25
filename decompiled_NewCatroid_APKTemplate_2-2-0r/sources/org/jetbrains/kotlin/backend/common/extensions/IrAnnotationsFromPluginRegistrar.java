package org.jetbrains.kotlin.backend.common.extensions;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;

/* JADX INFO: compiled from: IrAnnotationsFromPluginRegistrar.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J'\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0007\u001a\n\u0012\u0006\b\u0001\u0012\u00020\t0\b\"\u00020\t¢\u0006\u0002\u0010\nJ\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\u000bH&¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/extensions/IrAnnotationsFromPluginRegistrar;", "", "()V", "addMetadataVisibleAnnotationsToElement", "", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "annotations", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;[Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;)V", "", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrAnnotationsFromPluginRegistrar {
    public abstract void addMetadataVisibleAnnotationsToElement(IrDeclaration declaration, List<? extends IrConstructorCall> annotations);

    public final void addMetadataVisibleAnnotationsToElement(IrDeclaration declaration, IrConstructorCall... annotations) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        addMetadataVisibleAnnotationsToElement(declaration, ArraysKt.toList(annotations));
    }
}
