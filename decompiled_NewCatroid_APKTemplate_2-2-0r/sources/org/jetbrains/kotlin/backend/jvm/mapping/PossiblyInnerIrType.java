package org.jetbrains.kotlin.backend.jvm.mapping;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;

/* JADX INFO: compiled from: IrTypeMapping.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\b\u0000\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0000¢\u0006\u0002\u0010\bJ\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00000\u0005R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0000X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/mapping/PossiblyInnerIrType;", "", "classifier", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "arguments", "", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "outerType", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Ljava/util/List;Lorg/jetbrains/kotlin/backend/jvm/mapping/PossiblyInnerIrType;)V", "getArguments", "()Ljava/util/List;", "getClassifier", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "segments", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PossiblyInnerIrType {
    private final List<IrTypeArgument> arguments;
    private final IrClass classifier;
    private final PossiblyInnerIrType outerType;

    /* JADX WARN: Multi-variable type inference failed */
    public PossiblyInnerIrType(IrClass classifier, List<? extends IrTypeArgument> arguments, PossiblyInnerIrType possiblyInnerIrType) {
        Intrinsics.checkNotNullParameter(classifier, "classifier");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        this.classifier = classifier;
        this.arguments = arguments;
        this.outerType = possiblyInnerIrType;
    }

    public final IrClass getClassifier() {
        return this.classifier;
    }

    public final List<IrTypeArgument> getArguments() {
        return this.arguments;
    }

    public final List<PossiblyInnerIrType> segments() {
        PossiblyInnerIrType possiblyInnerIrType = this.outerType;
        List<PossiblyInnerIrType> listSegments = possiblyInnerIrType != null ? possiblyInnerIrType.segments() : null;
        if (listSegments == null) {
            listSegments = CollectionsKt.emptyList();
        }
        return CollectionsKt.plus((Collection<? extends PossiblyInnerIrType>) listSegments, this);
    }
}
