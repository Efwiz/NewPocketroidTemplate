package org.jetbrains.kotlin.backend.common.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols;
import org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper;
import org.jetbrains.kotlin.ir.util.DeepCopyTypeRemapper;
import org.jetbrains.kotlin.ir.util.DescriptorsRemapper;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;

/* JADX INFO: compiled from: InitializersLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\u0002\u0010\bJ\u0018\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000f2\u0006\u0010\u0011\u001a\u00020\u0010H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/InitializersCleanupLowering;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "shouldEraseFieldInitializer", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;Lkotlin/jvm/functions/Function1;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "withLocalDeclarations", "getWithLocalDeclarations", "()Z", "transformFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InitializersCleanupLowering implements DeclarationTransformer {
    private final CommonBackendContext context;
    private final Function1<IrField, Boolean> shouldEraseFieldInitializer;

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public boolean getWithLocalDeclarations() {
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public InitializersCleanupLowering(CommonBackendContext context, Function1<? super IrField, Boolean> shouldEraseFieldInitializer) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(shouldEraseFieldInitializer, "shouldEraseFieldInitializer");
        this.context = context;
        this.shouldEraseFieldInitializer = shouldEraseFieldInitializer;
    }

    public final CommonBackendContext getContext() {
        return this.context;
    }

    public /* synthetic */ InitializersCleanupLowering(CommonBackendContext commonBackendContext, AnonymousClass1 anonymousClass1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(commonBackendContext, (i & 2) != 0 ? new Function1<IrField, Boolean>() { // from class: org.jetbrains.kotlin.backend.common.lower.InitializersCleanupLowering.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrField it) {
                IrProperty owner;
                Intrinsics.checkNotNullParameter(it, "it");
                IrPropertySymbol correspondingPropertySymbol = it.getCorrespondingPropertySymbol();
                boolean z = false;
                if (correspondingPropertySymbol != null && (owner = correspondingPropertySymbol.getOwner()) != null && owner.getIsConst()) {
                    z = true;
                }
                return Boolean.valueOf(!z);
            }
        } : anonymousClass1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (declaration instanceof IrAnonymousInitializer) {
            return CollectionsKt.emptyList();
        }
        DescriptorsRemapper descriptorsRemapper = null;
        Object[] objArr = 0;
        if ((declaration instanceof IrField) && (declaration.getParent() instanceof IrClass)) {
            if (this.shouldEraseFieldInitializer.invoke(declaration).booleanValue()) {
                ((IrField) declaration).setInitializer(null);
            } else {
                IrField irField = (IrField) declaration;
                IrExpressionBody initializer = irField.getInitializer();
                if (initializer != null) {
                    IrFactory irFactory = this.context.getIrFactory();
                    int startOffset = initializer.getStartOffset();
                    int endOffset = initializer.getEndOffset();
                    IrExpression expression = initializer.getExpression();
                    DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(descriptorsRemapper, 1, objArr == true ? 1 : 0);
                    IrVisitorsKt.acceptVoid(expression, deepCopySymbolRemapper);
                    DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
                    IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(expression.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new DeepCopyTypeRemapper(deepCopySymbolRemapper2)), (Object) null), null);
                    if (irElementPatchDeclarationParents != null) {
                        irField.setInitializer(irFactory.createExpressionBody(startOffset, endOffset, (IrExpression) irElementPatchDeclarationParents));
                    } else {
                        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                    }
                }
            }
        }
        return null;
    }
}
