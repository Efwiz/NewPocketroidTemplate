package org.jetbrains.kotlin.ir.backend.js.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference2Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.Mapping;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrInstanceInitializerCall;
import org.jetbrains.kotlin.ir.expressions.impl.IrDelegatingConstructorCallImpl;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: PrimaryConstructorLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R3\u0010\u0007\u001a\u0004\u0018\u00010\u0006*\u00020\b2\b\u0010\u0005\u001a\u0004\u0018\u00010\u00068B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\r\u0010\u000e\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/DelegateToSyntheticPrimaryConstructor;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;)V", "<set-?>", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "syntheticPrimaryConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getSyntheticPrimaryConstructor", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "setSyntheticPrimaryConstructor", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;)V", "syntheticPrimaryConstructor$delegate", "Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DelegateToSyntheticPrimaryConstructor implements BodyLoweringPass {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty2(new MutablePropertyReference2Impl(Reflection.getOrCreateKotlinClass(DelegateToSyntheticPrimaryConstructor.class), "syntheticPrimaryConstructor", "getSyntheticPrimaryConstructor(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;"))};

    /* JADX INFO: renamed from: syntheticPrimaryConstructor$delegate, reason: from kotlin metadata */
    private final Mapping.Delegate syntheticPrimaryConstructor;

    public DelegateToSyntheticPrimaryConstructor(JsCommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.syntheticPrimaryConstructor = context.getMapping().getClassToSyntheticPrimaryConstructor();
    }

    private final IrConstructor getSyntheticPrimaryConstructor(IrClass irClass) {
        return (IrConstructor) this.syntheticPrimaryConstructor.getValue(irClass, $$delegatedProperties[0]);
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        final IrConstructor syntheticPrimaryConstructor;
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        if (!(container instanceof IrConstructor) || ((IrConstructor) container).getIsPrimary() || (syntheticPrimaryConstructor = getSyntheticPrimaryConstructor(IrUtilsKt.getParentAsClass(container))) == null) {
            return;
        }
        IrElementTransformerVoidKt.transformChildrenVoid(irBody, new IrElementTransformerVoid() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.DelegateToSyntheticPrimaryConstructor$lower$1$initializeTransformer$1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrStatement visitDeclaration(IrDeclarationBase declaration) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                return declaration;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrDelegatingConstructorCallImpl visitInstanceInitializerCall(IrInstanceInitializerCall expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrConstructor irConstructor = syntheticPrimaryConstructor;
                return new IrDelegatingConstructorCallImpl(expression.getStartOffset(), expression.getEndOffset(), expression.getType(), irConstructor.getSymbol(), irConstructor.getTypeParameters().size(), irConstructor.getValueParameters().size());
            }
        });
    }
}
