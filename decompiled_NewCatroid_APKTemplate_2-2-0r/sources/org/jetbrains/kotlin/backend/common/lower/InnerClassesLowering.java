package org.jetbrains.kotlin.backend.common.lower;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BackendContext;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrInstanceInitializerCall;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.util.DumpIrTreeKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: InnerClassesLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eH\u0002J\u0018\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u00112\u0006\u0010\u0013\u001a\u00020\u0012H\u0016J\f\u0010\u0014\u001a\u00020\n*\u00020\u000eH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/InnerClassesLowering;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "context", "Lorg/jetbrains/kotlin/backend/common/BackendContext;", "innerClassesSupport", "Lorg/jetbrains/kotlin/backend/common/lower/InnerClassesSupport;", "(Lorg/jetbrains/kotlin/backend/common/BackendContext;Lorg/jetbrains/kotlin/backend/common/lower/InnerClassesSupport;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/common/BackendContext;", "withLocalDeclarations", "", "getWithLocalDeclarations", "()Z", "lowerConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "irConstructor", "transformFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "shouldInitializeOuterThis", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InnerClassesLowering implements DeclarationTransformer {
    private final BackendContext context;
    private final InnerClassesSupport innerClassesSupport;

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public boolean getWithLocalDeclarations() {
        return true;
    }

    public InnerClassesLowering(BackendContext context, InnerClassesSupport innerClassesSupport) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(innerClassesSupport, "innerClassesSupport");
        this.context = context;
        this.innerClassesSupport = innerClassesSupport;
    }

    public final BackendContext getContext() {
        return this.context;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0024  */
    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (declaration instanceof IrClass) {
            IrClass irClass = (IrClass) declaration;
            if (irClass.getIsInner()) {
                irClass.getDeclarations().add(this.innerClassesSupport.getOuterThisField(irClass));
            } else {
                if ((declaration instanceof IrConstructor) || !IrUtilsKt.getParentAsClass(declaration).getIsInner()) {
                    return null;
                }
                IrConstructor irConstructor = (IrConstructor) declaration;
                IrConstructor irConstructorLowerConstructor = lowerConstructor(irConstructor);
                Map mapPrimaryConstructorParameterMap = InnerClassesLoweringKt.primaryConstructorParameterMap(this.innerClassesSupport, irConstructor);
                VariableRemapper variableRemapper = new VariableRemapper(mapPrimaryConstructorParameterMap);
                for (Map.Entry entry : mapPrimaryConstructorParameterMap.entrySet()) {
                    IrValueParameter irValueParameter = (IrValueParameter) entry.getKey();
                    IrValueParameter irValueParameter2 = (IrValueParameter) entry.getValue();
                    IrExpressionBody defaultValue = irValueParameter.getDefaultValue();
                    irValueParameter2.setDefaultValue(defaultValue != null ? this.context.getIrFactory().createExpressionBody(defaultValue.getStartOffset(), defaultValue.getEndOffset(), (IrExpression) PatchDeclarationParentsKt.patchDeclarationParents(defaultValue.getExpression().transform((IrElementTransformer<? super Object>) variableRemapper, (Object) null), irConstructorLowerConstructor)) : null);
                }
                return CollectionsKt.listOf(irConstructorLowerConstructor);
            }
        } else if (declaration instanceof IrConstructor) {
            return null;
        }
        return null;
    }

    private final IrConstructor lowerConstructor(final IrConstructor irConstructor) {
        final IrConstructor innerClassConstructorWithOuterThisParameter = this.innerClassesSupport.getInnerClassConstructorWithOuterThisParameter(irConstructor);
        final IrValueParameter irValueParameter = innerClassConstructorWithOuterThisParameter.getValueParameters().get(0);
        final IrClass parentAsClass = IrUtilsKt.getParentAsClass(irConstructor);
        final IrField outerThisField = this.innerClassesSupport.getOuterThisField(parentAsClass);
        final IrBody body = irConstructor.getBody();
        if (body != null) {
            if (!(body instanceof IrBlockBody)) {
                throw new AssertionError("Unexpected constructor body: " + irConstructor.getBody());
            }
            innerClassConstructorWithOuterThisParameter.setBody(IrFactoryHelpersKt.createBlockBody(this.context.getIrFactory(), body.getStartOffset(), body.getEndOffset(), new Function1<IrBlockBody, Unit>() { // from class: org.jetbrains.kotlin.backend.common.lower.InnerClassesLowering$lowerConstructor$1$1
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
                    Object next;
                    Object next2;
                    Intrinsics.checkNotNullParameter(createBlockBody, "$this$createBlockBody");
                    if (this.this$0.shouldInitializeOuterThis(irConstructor)) {
                        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(this.this$0.getContext(), irConstructor.getSymbol(), irConstructor.getStartOffset(), irConstructor.getEndOffset());
                        IrClass irClass = parentAsClass;
                        IrField irField = outerThisField;
                        IrValueParameter irValueParameter2 = irValueParameter;
                        List<IrStatement> statements = createBlockBody.getStatements();
                        DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder;
                        IrValueParameter thisReceiver = irClass.getThisReceiver();
                        Intrinsics.checkNotNull(thisReceiver);
                        statements.add(0, ExpressionHelpersKt.irSetField$default(declarationIrBuilder, ExpressionHelpersKt.irGet(declarationIrBuilder, thisReceiver), irField, ExpressionHelpersKt.irGet(declarationIrBuilder, irValueParameter2), null, 8, null));
                    }
                    createBlockBody.getStatements().addAll(((IrBlockBody) body).getStatements());
                    Iterator<T> it = createBlockBody.getStatements().iterator();
                    do {
                        if (!it.hasNext()) {
                            next = null;
                            break;
                        }
                        next = it.next();
                    } while (!(((IrStatement) next) instanceof IrInstanceInitializerCall));
                    if (next == null) {
                        Iterator<T> it2 = createBlockBody.getStatements().iterator();
                        do {
                            if (!it2.hasNext()) {
                                next2 = null;
                                break;
                            }
                            next2 = it2.next();
                        } while (!(((IrStatement) next2) instanceof IrDelegatingConstructorCall));
                        IrDelegatingConstructorCall irDelegatingConstructorCall = (IrDelegatingConstructorCall) next2;
                        if (irDelegatingConstructorCall == null) {
                            throw new AssertionError("Delegating constructor call expected: " + DumpIrTreeKt.dump$default(irConstructor, null, 1, null));
                        }
                        irDelegatingConstructorCall.setDispatchReceiver(new IrGetValueImpl(irDelegatingConstructorCall.getStartOffset(), irDelegatingConstructorCall.getEndOffset(), irValueParameter.getSymbol(), null, 8, null));
                    }
                    IrBlockBody irBlockBody = createBlockBody;
                    PatchDeclarationParentsKt.patchDeclarationParents(irBlockBody, innerClassConstructorWithOuterThisParameter);
                    IrElementTransformerVoidKt.transformChildrenVoid(irBlockBody, new VariableRemapper(InnerClassesLoweringKt.primaryConstructorParameterMap(this.this$0.innerClassesSupport, irConstructor)));
                }
            }));
        }
        return innerClassConstructorWithOuterThisParameter;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean shouldInitializeOuterThis(IrConstructor irConstructor) {
        IrBody body = irConstructor.getBody();
        IrBlockBody irBlockBody = body instanceof IrBlockBody ? (IrBlockBody) body : null;
        if (irBlockBody == null) {
            return false;
        }
        List<IrStatement> statements = irBlockBody.getStatements();
        if ((statements instanceof Collection) && statements.isEmpty()) {
            return false;
        }
        Iterator<T> it = statements.iterator();
        while (it.hasNext()) {
            if (((IrStatement) it.next()) instanceof IrInstanceInitializerCall) {
                return true;
            }
        }
        return false;
    }
}
