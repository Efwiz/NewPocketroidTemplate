package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.ClassLoweringPass;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmCachedDeclarations;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilder;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilderKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: InheritedDefaultMethodsOnClassesLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\bH\u0002J\u0018\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\bH\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/InheritedDefaultMethodsOnClassesLowering;", "Lorg/jetbrains/kotlin/backend/common/ClassLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "generateCloneImplementation", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "fakeOverride", "cloneFun", "generateDelegationToDefaultImpl", "interfaceImplementation", "classOverride", "lower", "", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "transformMemberDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class InheritedDefaultMethodsOnClassesLowering implements ClassLoweringPass {
    private final JvmBackendContext context;

    public InheritedDefaultMethodsOnClassesLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.ClassLoweringPass
    public void lower(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        if (JvmIrUtilsKt.isJvmInterface(irClass)) {
            return;
        }
        List<IrDeclaration> declarations = irClass.getDeclarations();
        int size = declarations.size();
        for (int i = 0; i < size; i++) {
            IrDeclaration irDeclarationTransformMemberDeclaration = transformMemberDeclaration(declarations.get(i));
            if (irDeclarationTransformMemberDeclaration == null) {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
            }
            declarations.set(i, irDeclarationTransformMemberDeclaration);
        }
    }

    private final IrDeclaration transformMemberDeclaration(IrDeclaration declaration) {
        Object next;
        IrClass parentAsClass;
        FqName safe;
        if (!(declaration instanceof IrSimpleFunction)) {
            return declaration;
        }
        IrSimpleFunction irSimpleFunction = (IrSimpleFunction) declaration;
        if (irSimpleFunction.getIsFakeOverride() && Intrinsics.areEqual(irSimpleFunction.getName().asString(), "clone")) {
            List listAllOverridden = IrUtilsKt.allOverridden((IrOverridableDeclaration) declaration, false);
            Iterator it = listAllOverridden.iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                parentAsClass = IrUtilsKt.getParentAsClass((IrSimpleFunction) next);
                safe = StandardNames.FqNames.cloneable.toSafe();
                Intrinsics.checkNotNullExpressionValue(safe, "toSafe(...)");
            } while (!AdditionalIrUtilsKt.hasEqualFqName(parentAsClass, safe));
            IrSimpleFunction irSimpleFunction2 = (IrSimpleFunction) next;
            if (irSimpleFunction2 != null) {
                if (!(listAllOverridden instanceof Collection) || !listAllOverridden.isEmpty()) {
                    Iterator it2 = listAllOverridden.iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            IrSimpleFunction irSimpleFunction3 = (IrSimpleFunction) it2.next();
                            if (!irSimpleFunction3.getIsFakeOverride() && !Intrinsics.areEqual(irSimpleFunction3, irSimpleFunction2)) {
                                break;
                            }
                        }
                    }
                }
                return generateCloneImplementation(irSimpleFunction, irSimpleFunction2);
            }
        }
        IrSimpleFunction irSimpleFunctionFindInterfaceImplementation = InheritedDefaultMethodsOnClassesLoweringKt.findInterfaceImplementation(irSimpleFunction, this.context.getConfig().getJvmDefaultMode());
        return irSimpleFunctionFindInterfaceImplementation == null ? declaration : generateDelegationToDefaultImpl(irSimpleFunctionFindInterfaceImplementation, irSimpleFunction);
    }

    private final IrSimpleFunction generateCloneImplementation(IrSimpleFunction fakeOverride, IrSimpleFunction cloneFun) {
        fakeOverride.getIsFakeOverride();
        IrSimpleFunction defaultImplsRedirection = this.context.getCachedDeclarations().getDefaultImplsRedirection(fakeOverride);
        int startOffset = IrUtilsKt.getParentAsClass(fakeOverride).getStartOffset();
        JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder = JvmIrBuilderKt.createJvmIrBuilder(this.context, defaultImplsRedirection.getSymbol(), startOffset, startOffset);
        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(jvmIrBuilderCreateJvmIrBuilder.getContext(), jvmIrBuilderCreateJvmIrBuilder.getScope(), jvmIrBuilderCreateJvmIrBuilder.getStartOffset(), jvmIrBuilderCreateJvmIrBuilder.getEndOffset());
        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
        IrCall irCall = ExpressionHelpersKt.irCall(irBlockBodyBuilder2, cloneFun, (IrStatementOrigin) null, IrUtilsKt.getParentAsClass(cloneFun).getSymbol());
        IrValueParameter dispatchReceiverParameter = defaultImplsRedirection.getDispatchReceiverParameter();
        Intrinsics.checkNotNull(dispatchReceiverParameter);
        irCall.setDispatchReceiver(ExpressionHelpersKt.irGet(irBlockBodyBuilder2, dispatchReceiverParameter));
        Unit unit = Unit.INSTANCE;
        irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturn(irBlockBodyBuilder2, irCall));
        defaultImplsRedirection.setBody(irBlockBodyBuilder.getIrBlockBody());
        return defaultImplsRedirection;
    }

    private final IrSimpleFunction generateDelegationToDefaultImpl(IrSimpleFunction interfaceImplementation, IrSimpleFunction classOverride) {
        final IrSimpleFunction defaultImplsRedirection = this.context.getCachedDeclarations().getDefaultImplsRedirection(classOverride);
        IrSimpleFunction owner = JvmIrUtilsKt.firstSuperMethodFromKotlin(defaultImplsRedirection, interfaceImplementation).getOwner();
        IrSimpleFunction irSimpleFunction = owner;
        final IrSimpleType defaultType = IrUtilsKt.getDefaultType(IrUtilsKt.getParentAsClass(irSimpleFunction));
        IrSimpleFunction defaultImplsFunction$default = JvmCachedDeclarations.getDefaultImplsFunction$default(this.context.getCachedDeclarations(), owner, false, 2, null);
        int startOffset = IrUtilsKt.getParentAsClass(classOverride).getStartOffset();
        JvmBackendContext jvmBackendContext = this.context;
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(jvmBackendContext, defaultImplsRedirection.getSymbol(), startOffset, startOffset);
        final IrBlockBuilder irBlockBuilder = new IrBlockBuilder(declarationIrBuilderCreateIrBuilder.getContext(), declarationIrBuilderCreateIrBuilder.getScope(), declarationIrBuilderCreateIrBuilder.getStartOffset(), declarationIrBuilderCreateIrBuilder.getEndOffset(), null, null, false, 64, null);
        Map<IrValueParameter, IrExpression> mapMapFunctionMfvcStructures = jvmBackendContext.getMultiFieldValueClassReplacements().mapFunctionMfvcStructures(irBlockBuilder, defaultImplsFunction$default, defaultImplsRedirection, new Function2<IrValueParameter, IrType, IrExpression>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.InheritedDefaultMethodsOnClassesLowering$generateDelegationToDefaultImpl$1$1$parameter2arguments$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final IrExpression invoke(IrValueParameter sourceParameter, IrType irType) {
                Intrinsics.checkNotNullParameter(sourceParameter, "sourceParameter");
                Intrinsics.checkNotNullParameter(irType, "<anonymous parameter 1>");
                IrGetValueImpl irGetValueImplIrGet = ExpressionHelpersKt.irGet(irBlockBuilder, sourceParameter);
                return !Intrinsics.areEqual(sourceParameter, defaultImplsRedirection.getDispatchReceiverParameter()) ? irGetValueImplIrGet : InheritedDefaultMethodsOnClassesLoweringKt.reinterpretAsDispatchReceiverOfType(irGetValueImplIrGet, defaultType);
            }
        });
        IrCall irCallIrCall$default = ExpressionHelpersKt.irCall$default(irBlockBuilder, defaultImplsFunction$default.getSymbol(), defaultImplsRedirection.getReturnType(), 0, 0, null, 28, null);
        int size = IrUtilsKt.getParentAsClass(irSimpleFunction).getTypeParameters().size();
        for (int i = 0; i < size; i++) {
            irCallIrCall$default.putTypeArgument(i, JvmIrUtilsKt.createPlaceholderAnyNType(irBlockBuilder.getContext().getIrBuiltIns()));
        }
        IrCall irCall = irCallIrCall$default;
        IrUtilsKt.passTypeArgumentsFrom(irCall, defaultImplsRedirection, IrUtilsKt.getParentAsClass(irSimpleFunction).getTypeParameters().size());
        for (Map.Entry<IrValueParameter, IrExpression> entry : mapMapFunctionMfvcStructures.entrySet()) {
            IrValueParameter key = entry.getKey();
            IrExpression value = entry.getValue();
            if (value != null) {
                IrExpressionsKt.putArgument(irCall, key, value);
            }
        }
        irBlockBuilder.unaryPlus(irCallIrCall$default);
        Unit unit = Unit.INSTANCE;
        defaultImplsRedirection.setBody(ExpressionHelpersKt.irExprBody(declarationIrBuilderCreateIrBuilder, irBlockBuilder.getIrBlockBody()));
        return defaultImplsRedirection;
    }
}
