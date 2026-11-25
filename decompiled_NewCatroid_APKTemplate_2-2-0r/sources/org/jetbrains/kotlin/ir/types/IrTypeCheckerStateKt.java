package org.jetbrains.kotlin.ir.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.AbstractTypePreparator;
import org.jetbrains.kotlin.types.AbstractTypeRefiner;
import org.jetbrains.kotlin.types.TypeCheckerState;

/* JADX INFO: compiled from: IrTypeCheckerState.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0004"}, d2 = {"createIrTypeCheckerState", "Lorg/jetbrains/kotlin/types/TypeCheckerState;", "typeSystemContext", "Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrTypeCheckerStateKt {
    public static final TypeCheckerState createIrTypeCheckerState(IrTypeSystemContext typeSystemContext) {
        Intrinsics.checkNotNullParameter(typeSystemContext, "typeSystemContext");
        return new TypeCheckerState(false, false, false, typeSystemContext, AbstractTypePreparator.Default.INSTANCE, AbstractTypeRefiner.Default.INSTANCE);
    }
}
