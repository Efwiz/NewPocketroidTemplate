package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilder;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilderKt;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationResolver;

/* JADX INFO: compiled from: ObjectClassLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0011H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\tX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/ObjectClassLowering;", "Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "pendingTransformations", "", "Lkotlin/Function0;", "", "lower", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "process", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "visitClassNew", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ObjectClassLowering extends IrElementTransformerVoidWithContext implements FileLoweringPass {
    private final JvmBackendContext context;
    private List<Function0<Unit>> pendingTransformations;

    public ObjectClassLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.pendingTransformations = new ArrayList();
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        irFile.accept(this, null);
        Iterator<T> it = this.pendingTransformations.iterator();
        while (it.hasNext()) {
            ((Function0) it.next()).invoke();
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
    public IrStatement visitClassNew(IrClass declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        process(declaration);
        return super.visitClassNew(declaration);
    }

    private final void process(IrClass irClass) {
        IrConstructor next;
        if (IrUtilsKt.isObject(irClass)) {
            final IrField fieldForObjectInstance = this.context.getCachedDeclarations().getFieldForObjectInstance(irClass);
            final IrField privateFieldForObjectInstance = this.context.getCachedDeclarations().getPrivateFieldForObjectInstance(irClass);
            Iterator<IrConstructor> it = IrUtilsKt.getConstructors(irClass).iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!next.getIsPrimary());
            IrConstructor irConstructor = next;
            if (irConstructor == null) {
                throw new AssertionError("Object should have a primary constructor: " + irClass.getName());
            }
            if (!Intrinsics.areEqual(privateFieldForObjectInstance, fieldForObjectInstance)) {
                DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, privateFieldForObjectInstance.getSymbol(), 0, 0, 6, (Object) null);
                privateFieldForObjectInstance.setInitializer(ExpressionHelpersKt.irExprBody(declarationIrBuilderCreateIrBuilder$default, ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilderCreateIrBuilder$default, irConstructor.getSymbol())));
                DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default2 = LowerUtilsKt.createIrBuilder$default(this.context, fieldForObjectInstance.getSymbol(), 0, 0, 6, (Object) null);
                fieldForObjectInstance.setInitializer(ExpressionHelpersKt.irExprBody(declarationIrBuilderCreateIrBuilder$default2, ExpressionHelpersKt.irGetField$default(declarationIrBuilderCreateIrBuilder$default2, null, privateFieldForObjectInstance, null, 4, null)));
                this.pendingTransformations.add(new Function0<Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.ObjectClassLowering.process.3
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        IrDeclarationParent parent = privateFieldForObjectInstance.getParent();
                        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer");
                        ((IrDeclarationContainer) parent).getDeclarations().add(0, privateFieldForObjectInstance);
                    }
                });
            } else {
                DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default3 = LowerUtilsKt.createIrBuilder$default(this.context, fieldForObjectInstance.getSymbol(), 0, 0, 6, (Object) null);
                fieldForObjectInstance.setInitializer(ExpressionHelpersKt.irExprBody(declarationIrBuilderCreateIrBuilder$default3, ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilderCreateIrBuilder$default3, irConstructor.getSymbol())));
            }
            if (!this.context.getState().getLanguageVersionSettings().supportsFeature(LanguageFeature.ProperVisibilityForCompanionObjectInstanceField) && (Intrinsics.areEqual(irClass.getVisibility(), DescriptorVisibilities.PRIVATE) || Intrinsics.areEqual(irClass.getVisibility(), DescriptorVisibilities.PROTECTED))) {
                JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder$default = JvmIrBuilderKt.createJvmIrBuilder$default(this.context, irClass.getSymbol(), 0, 0, 6, null);
                fieldForObjectInstance.setAnnotations(CollectionsKt.plus((Collection<? extends IrConstructorCall>) AdditionalIrUtilsKt.filterOutAnnotations(DeprecationResolver.INSTANCE.getJAVA_DEPRECATED(), fieldForObjectInstance.getAnnotations()), ExpressionHelpersKt.irCall((IrBuilderWithScope) jvmIrBuilderCreateJvmIrBuilder$default, jvmIrBuilderCreateJvmIrBuilder$default.getIrSymbols().getJavaLangDeprecatedConstructorWithDeprecatedFlag())));
            }
            this.pendingTransformations.add(new Function0<Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.ObjectClassLowering.process.6
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    IrDeclarationParent parent = fieldForObjectInstance.getParent();
                    Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer");
                    ((IrDeclarationContainer) parent).getDeclarations().add(0, fieldForObjectInstance);
                }
            });
        }
    }
}
