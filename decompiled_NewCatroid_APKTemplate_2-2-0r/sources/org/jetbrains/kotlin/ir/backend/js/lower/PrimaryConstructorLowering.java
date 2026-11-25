package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference2Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.backend.common.Mapping;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOriginImpl;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.expressions.impl.IrInstanceInitializerCallImpl;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: PrimaryConstructorLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0019B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\fH\u0002J\u0018\u0010\u0015\u001a\n\u0012\u0004\u0012\u00020\u0017\u0018\u00010\u00162\u0006\u0010\u0018\u001a\u00020\u0017H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R3\u0010\u000b\u001a\u0004\u0018\u00010\n*\u00020\f2\b\u0010\t\u001a\u0004\u0018\u00010\n8B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/PrimaryConstructorLowering;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "unitType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "<set-?>", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "syntheticPrimaryConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getSyntheticPrimaryConstructor", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "setSyntheticPrimaryConstructor", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;)V", "syntheticPrimaryConstructor$delegate", "Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "createPrimaryConstructor", "irClass", "transformFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "SYNTHETIC_PRIMARY_CONSTRUCTOR", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PrimaryConstructorLowering implements DeclarationTransformer {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty2(new MutablePropertyReference2Impl(Reflection.getOrCreateKotlinClass(PrimaryConstructorLowering.class), "syntheticPrimaryConstructor", "getSyntheticPrimaryConstructor(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;"))};
    private final JsCommonBackendContext context;

    /* JADX INFO: renamed from: syntheticPrimaryConstructor$delegate, reason: from kotlin metadata */
    private final Mapping.Delegate syntheticPrimaryConstructor;
    private final IrType unitType;

    public PrimaryConstructorLowering(JsCommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.syntheticPrimaryConstructor = context.getMapping().getClassToSyntheticPrimaryConstructor();
        this.unitType = context.getIrBuiltIns().getUnitType();
    }

    public final JsCommonBackendContext getContext() {
        return this.context;
    }

    private final void setSyntheticPrimaryConstructor(IrClass irClass, IrConstructor irConstructor) {
        this.syntheticPrimaryConstructor.setValue(irClass, $$delegatedProperties[0], irConstructor);
    }

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (declaration instanceof IrClass) {
            IrClass irClass = (IrClass) declaration;
            if (irClass.getKind() != ClassKind.INTERFACE) {
                Iterator<IrConstructor> it = IrUtilsKt.getConstructors(irClass).iterator();
                while (it.hasNext()) {
                    if (it.next().isPrimary()) {
                        return null;
                    }
                }
                setSyntheticPrimaryConstructor(irClass, createPrimaryConstructor(irClass));
            }
        }
        return null;
    }

    /* JADX INFO: compiled from: PrimaryConstructorLowering.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/PrimaryConstructorLowering$SYNTHETIC_PRIMARY_CONSTRUCTOR;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SYNTHETIC_PRIMARY_CONSTRUCTOR extends IrDeclarationOriginImpl {
        public static final SYNTHETIC_PRIMARY_CONSTRUCTOR INSTANCE = new SYNTHETIC_PRIMARY_CONSTRUCTOR();

        private SYNTHETIC_PRIMARY_CONSTRUCTOR() {
            super("SYNTHETIC_PRIMARY_CONSTRUCTOR", false, 2, null);
        }
    }

    private final IrConstructor createPrimaryConstructor(IrClass irClass) {
        IrFactory factory = irClass.getFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.setOrigin(SYNTHETIC_PRIMARY_CONSTRUCTOR.INSTANCE);
        irFunctionBuilder.setPrimary(true);
        DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
        Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
        irFunctionBuilder.setVisibility(PRIVATE);
        irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(irClass));
        IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(factory, irFunctionBuilder);
        irClass.getDeclarations().add(irConstructorBuildConstructor);
        irConstructorBuildConstructor.setParent(irClass);
        irConstructorBuildConstructor.setBody(IrFactoryHelpersKt.createBlockBody(irClass.getFactory(), irClass.getStartOffset(), irClass.getEndOffset(), (List<? extends IrStatement>) CollectionsKt.listOf(new IrInstanceInitializerCallImpl(irClass.getStartOffset(), irClass.getEndOffset(), irClass.getSymbol(), this.unitType))));
        return irConstructorBuildConstructor;
    }
}
