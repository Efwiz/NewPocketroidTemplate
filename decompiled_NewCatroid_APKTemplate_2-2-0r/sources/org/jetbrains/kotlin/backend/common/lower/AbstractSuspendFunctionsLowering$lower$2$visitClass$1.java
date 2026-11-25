package org.jetbrains.kotlin.backend.common.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;

/* JADX INFO: compiled from: AbstractSuspendFunctionsLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class AbstractSuspendFunctionsLowering$lower$2$visitClass$1 extends FunctionReference implements Function1<IrElement, List<? extends IrDeclaration>> {
    AbstractSuspendFunctionsLowering$lower$2$visitClass$1(Object obj) {
        super(1, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "tryTransformSuspendFunction(Lorg/jetbrains/kotlin/ir/IrElement;)Ljava/util/List;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "tryTransformSuspendFunction";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(AbstractSuspendFunctionsLowering.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final List<IrDeclaration> invoke(IrElement p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return ((AbstractSuspendFunctionsLowering) this.receiver).tryTransformSuspendFunction(p0);
    }
}
