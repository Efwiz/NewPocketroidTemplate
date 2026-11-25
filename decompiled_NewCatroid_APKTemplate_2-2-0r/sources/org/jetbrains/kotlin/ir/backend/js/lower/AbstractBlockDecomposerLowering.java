package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: BlockDecomposerLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H&J\u0014\u0010\u0011\u001a\u00020\u0012*\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/AbstractBlockDecomposerLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;)V", "decomposerTransformer", "Lorg/jetbrains/kotlin/ir/backend/js/lower/BlockDecomposerTransformer;", "nothingType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "unreachableExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "toBlockBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "containingFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractBlockDecomposerLowering implements BodyLoweringPass {
    private final JsCommonBackendContext context;
    private final BlockDecomposerTransformer decomposerTransformer;
    private final IrType nothingType;

    public abstract IrExpression unreachableExpression();

    public AbstractBlockDecomposerLowering(JsCommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.nothingType = context.getIrBuiltIns().getNothingType();
        this.decomposerTransformer = new BlockDecomposerTransformer(context, new AbstractBlockDecomposerLowering$decomposerTransformer$1(this));
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        IrField irField;
        IrExpressionBody initializer;
        IrSimpleFunction irSimpleFunction;
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        if (container instanceof IrFunction) {
            container.accept(this.decomposerTransformer, null);
            PatchDeclarationParentsKt.patchDeclarationParents(irBody, (IrDeclarationParent) container);
            return;
        }
        if (!(container instanceof IrField) || (initializer = (irField = (IrField) container).getInitializer()) == null) {
            return;
        }
        IrFactory irFactory = this.context.getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        Name nameIdentifier = Name.identifier(irField.getName().asString() + "$init$");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFunctionBuilder.setName(nameIdentifier);
        irFunctionBuilder.setReturnType(irField.getType());
        DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
        Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
        irFunctionBuilder.setVisibility(PRIVATE);
        irFunctionBuilder.setOrigin(JsIrBuilder.SYNTHESIZED_DECLARATION.INSTANCE);
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(container.getParent());
        IrBlockBody blockBody = toBlockBody(initializer, irSimpleFunctionBuildFunction);
        PatchDeclarationParentsKt.patchDeclarationParents(blockBody, irSimpleFunctionBuildFunction);
        irSimpleFunctionBuildFunction.setBody(blockBody);
        irSimpleFunctionBuildFunction.accept(this.decomposerTransformer, null);
        IrStatement irStatement = (IrStatement) CollectionsKt.last((List) blockBody.getStatements());
        if (blockBody.getStatements().size() > 1 || !(irStatement instanceof IrReturn) || !Intrinsics.areEqual(((IrReturn) irStatement).getValue(), initializer.getExpression())) {
            initializer.setExpression(JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, irSimpleFunctionBuildFunction.getSymbol(), initializer.getExpression().getType(), null, null, null, 28, null));
            IrDeclarationParent parent = container.getParent();
            Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer");
            ((IrDeclarationContainer) parent).getDeclarations().add(irSimpleFunctionBuildFunction);
            irSimpleFunction = irSimpleFunctionBuildFunction;
        } else {
            irSimpleFunction = container;
        }
        PatchDeclarationParentsKt.patchDeclarationParents(initializer, (IrDeclarationParent) irSimpleFunction);
    }

    private final IrBlockBody toBlockBody(final IrExpressionBody irExpressionBody, final IrFunction irFunction) {
        return IrFactoryHelpersKt.createBlockBody(this.context.getIrFactory(), irExpressionBody.getStartOffset(), irExpressionBody.getEndOffset(), new Function1<IrBlockBody, Unit>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.AbstractBlockDecomposerLowering.toBlockBody.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrBlockBody irBlockBody) {
                invoke2(irBlockBody);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrBlockBody createBlockBody) {
                Intrinsics.checkNotNullParameter(createBlockBody, "$this$createBlockBody");
                PatchDeclarationParentsKt.patchDeclarationParents(irExpressionBody.getExpression(), irFunction);
                createBlockBody.getStatements().add(JsIrBuilder.INSTANCE.buildReturn(irFunction.getSymbol(), irExpressionBody.getExpression(), this.nothingType));
            }
        });
    }
}
