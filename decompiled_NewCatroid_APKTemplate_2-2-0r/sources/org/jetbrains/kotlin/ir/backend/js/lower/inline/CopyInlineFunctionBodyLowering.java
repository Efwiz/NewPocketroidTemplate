package org.jetbrains.kotlin.ir.backend.js.lower.inline;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols;
import org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper;
import org.jetbrains.kotlin.ir.util.DeepCopyTypeRemapper;
import org.jetbrains.kotlin.ir.util.DescriptorsRemapper;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;

/* JADX INFO: compiled from: RemoveInlineDeclarationsWithReifiedTypeParametersLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\b2\u0006\u0010\n\u001a\u00020\tH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/inline/CopyInlineFunctionBodyLowering;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "transformFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CopyInlineFunctionBodyLowering implements DeclarationTransformer {
    private final JsIrBackendContext context;

    public CopyInlineFunctionBodyLowering(JsIrBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JsIrBackendContext getContext() {
        return this.context;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(final IrDeclaration declaration) {
        IrValueParameter irValueParameter;
        IrExpressionBody defaultValue;
        final IrBody body;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (declaration instanceof IrFunction) {
            IrFunction irFunction = (IrFunction) declaration;
            if (irFunction.getIsInline() && (body = irFunction.getBody()) != null) {
                irFunction.setBody(IrFactoryHelpersKt.createBlockBody(this.context.getIrFactory(), body.getStartOffset(), body.getEndOffset(), new Function1<IrBlockBody, Unit>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.inline.CopyInlineFunctionBodyLowering$transformFlat$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(IrBlockBody irBlockBody) {
                        invoke2(irBlockBody);
                        return Unit.INSTANCE;
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(IrBlockBody createBlockBody) {
                        Intrinsics.checkNotNullParameter(createBlockBody, "$this$createBlockBody");
                        List<IrStatement> statements = createBlockBody.getStatements();
                        IrBody irBody = body;
                        IrDeclarationParent irDeclarationParent = (IrDeclarationParent) declaration;
                        DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(null, 1, 0 == true ? 1 : 0);
                        IrVisitorsKt.acceptVoid(irBody, deepCopySymbolRemapper);
                        DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
                        IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(irBody.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new DeepCopyTypeRemapper(deepCopySymbolRemapper2)), (Object) null), irDeclarationParent);
                        if (irElementPatchDeclarationParents != null) {
                            IrBody irBody2 = (IrBody) irElementPatchDeclarationParents;
                            Intrinsics.checkNotNull(irBody2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBlockBody");
                            CollectionsKt.addAll(statements, ((IrBlockBody) irBody2).getStatements());
                            return;
                        }
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBody");
                    }
                }));
            }
        }
        DescriptorsRemapper descriptorsRemapper = null;
        Object[] objArr = 0;
        if (declaration instanceof IrValueParameter) {
            IrDeclarationParent parent = declaration.getParent();
            if ((parent instanceof IrFunction) && ((IrFunction) parent).getIsInline() && (defaultValue = (irValueParameter = (IrValueParameter) declaration).getDefaultValue()) != null) {
                IrFactory irFactory = this.context.getIrFactory();
                int startOffset = defaultValue.getStartOffset();
                int endOffset = defaultValue.getEndOffset();
                IrExpression expression = defaultValue.getExpression();
                IrDeclarationParent parent2 = declaration.getParent();
                DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(descriptorsRemapper, 1, objArr == true ? 1 : 0);
                IrVisitorsKt.acceptVoid(expression, deepCopySymbolRemapper);
                DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
                IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(expression.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new DeepCopyTypeRemapper(deepCopySymbolRemapper2)), (Object) null), parent2);
                if (irElementPatchDeclarationParents != null) {
                    irValueParameter.setDefaultValue(irFactory.createExpressionBody(startOffset, endOffset, (IrExpression) irElementPatchDeclarationParents));
                } else {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                }
            }
        }
        return null;
    }
}
