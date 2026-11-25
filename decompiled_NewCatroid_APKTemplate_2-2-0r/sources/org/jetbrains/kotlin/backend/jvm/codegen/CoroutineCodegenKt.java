package org.jetbrains.kotlin.backend.jvm.codegen;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.InlineClassAbi;
import org.jetbrains.kotlin.backend.jvm.InlineClassAbiKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrCoroutineUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.codegen.ClassBuilder;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineTransformerMethodVisitor;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrAttributeContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmBackendErrors;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: CoroutineCodegen.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000X\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001aF\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\n2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0000\u001a\f\u0010\u0010\u001a\u00020\u0011*\u00020\u0004H\u0002\u001a\u000e\u0010\u0012\u001a\u0004\u0018\u00010\u0013*\u00020\u0004H\u0000\u001a\u000e\u0010\u0014\u001a\u00020\u0011*\u0004\u0018\u00010\u0015H\u0000\u001a\f\u0010\u0016\u001a\u00020\u0011*\u00020\u0004H\u0000\u001a\u000e\u0010\u0017\u001a\u0004\u0018\u00010\u0018*\u00020\u0004H\u0000\u001a\u0014\u0010\u0019\u001a\u00020\u0011*\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0018H\u0002\u001a\u000e\u0010\u001c\u001a\u0004\u0018\u00010\u001a*\u00020\u0004H\u0000¨\u0006\u001d"}, d2 = {"acceptWithStateMachine", "", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "classCodegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen;", "methodVisitor", "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "varsCountByType", "", "Lorg/jetbrains/org/objectweb/asm/Type;", "", "obtainContinuationClassBuilder", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "anyOfOverriddenFunctionsReturnsNonUnit", "", "continuationClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "isReadOfInlineLambda", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "isSuspendCapturingCrossinline", "originalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass", "Lorg/jetbrains/kotlin/ir/types/IrType;", "overridesReturningDifferentType", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "returnType", "suspendForInlineToOriginal", "backend.jvm.codegen"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CoroutineCodegenKt {
    /* JADX WARN: Code duplicated, block: B:12:0x00b5  */
    /* JADX WARN: Code duplicated, block: B:17:0x00cc  */
    /* JADX WARN: Code duplicated, block: B:25:0x00ff  */
    public static final void acceptWithStateMachine(MethodNode methodNode, final IrFunction irFunction, final ClassCodegen classCodegen, MethodVisitor methodVisitor, Map<Type, Integer> varsCountByType, Function0<? extends ClassBuilder> obtainContinuationClassBuilder) {
        int i;
        int lineNumber;
        boolean z;
        boolean z2;
        IrFunction irFunctionSuspendFunctionOriginal;
        Intrinsics.checkNotNullParameter(methodNode, "<this>");
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(classCodegen, "classCodegen");
        Intrinsics.checkNotNullParameter(methodVisitor, "methodVisitor");
        Intrinsics.checkNotNullParameter(varsCountByType, "varsCountByType");
        Intrinsics.checkNotNullParameter(obtainContinuationClassBuilder, "obtainContinuationClassBuilder");
        JvmBackendContext context = classCodegen.getContext();
        context.getState().getLanguageVersionSettings().supportsFeature(LanguageFeature.ReleaseCoroutines);
        if (irFunction.getStartOffset() >= 0) {
            lineNumber = IrUtilsKt.getFile(irFunction).getFileEntry().getLineNumber(irFunction.getStartOffset());
        } else {
            IrClass irClass = classCodegen.getIrClass();
            if (irClass.getStartOffset() >= 0) {
                lineNumber = IrUtilsKt.getFile(irFunction).getFileEntry().getLineNumber(irClass.getStartOffset());
            } else {
                i = 1;
            }
            int i2 = methodNode.access;
            String name2 = methodNode.f1016name;
            Intrinsics.checkNotNullExpressionValue(name2, "name");
            String desc = methodNode.desc;
            Intrinsics.checkNotNullExpressionValue(desc, "desc");
            String str = methodNode.signature;
            List<String> exceptions = methodNode.exceptions;
            Intrinsics.checkNotNullExpressionValue(exceptions, "exceptions");
            String[] strArr = (String[]) exceptions.toArray(new String[0]);
            String internalName = classCodegen.getType().getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            boolean zIsSuspend = AdditionalIrUtilsKt.isSuspend(irFunction);
            if (AdditionalIrUtilsKt.isSuspend(irFunction)) {
                irFunctionSuspendFunctionOriginal = JvmIrUtilsKt.suspendFunctionOriginal(irFunction);
                if (IrTypePredicatesKt.isUnit(irFunctionSuspendFunctionOriginal.getReturnType()) || !anyOfOverriddenFunctionsReturnsNonUnit(irFunctionSuspendFunctionOriginal)) {
                    z = false;
                } else {
                    z = true;
                }
            } else {
                z = false;
            }
            Function1<String, Unit> function1 = new Function1<String, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.CoroutineCodegenKt$acceptWithStateMachine$visitor$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(String str2) {
                    invoke2(str2);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(String it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    classCodegen.getContext().getKtDiagnosticReporter().at(irFunction, classCodegen.getIrClass()).report(JvmBackendErrors.INSTANCE.getSUSPENSION_POINT_INSIDE_MONITOR(), it);
                }
            };
            String name3 = IrDeclarationsKt.getName(IrUtilsKt.getFile(classCodegen.getIrClass()));
            if (AdditionalIrUtilsKt.isSuspend(irFunction) || (irFunction.getDispatchReceiverParameter() == null && !Intrinsics.areEqual(irFunction.getOrigin(), JvmLoweredDeclarationOrigin.SUSPEND_IMPL_STATIC_FUNCTION.INSTANCE))) {
                z2 = false;
            } else {
                z2 = true;
            }
            methodNode.accept(new CoroutineTransformerMethodVisitor(methodVisitor, i2, name2, desc, str, strArr, internalName, obtainContinuationClassBuilder, zIsSuspend, z, function1, i, name3, z2, classCodegen.getType().getInternalName(), false, varsCountByType, !context.getConfiguration().getBoolean(JVMConfigurationKeys.ENABLE_DEBUG_MODE)));
        }
        i = lineNumber + 1;
        int i3 = methodNode.access;
        String name4 = methodNode.f1016name;
        Intrinsics.checkNotNullExpressionValue(name4, "name");
        String desc2 = methodNode.desc;
        Intrinsics.checkNotNullExpressionValue(desc2, "desc");
        String str2 = methodNode.signature;
        List<String> exceptions2 = methodNode.exceptions;
        Intrinsics.checkNotNullExpressionValue(exceptions2, "exceptions");
        String[] strArr2 = (String[]) exceptions2.toArray(new String[0]);
        String internalName2 = classCodegen.getType().getInternalName();
        Intrinsics.checkNotNullExpressionValue(internalName2, "getInternalName(...)");
        boolean zIsSuspend2 = AdditionalIrUtilsKt.isSuspend(irFunction);
        if (AdditionalIrUtilsKt.isSuspend(irFunction)) {
            irFunctionSuspendFunctionOriginal = JvmIrUtilsKt.suspendFunctionOriginal(irFunction);
            if (IrTypePredicatesKt.isUnit(irFunctionSuspendFunctionOriginal.getReturnType())) {
                z = false;
            } else {
                z = false;
            }
        } else {
            z = false;
        }
        Function1<String, Unit> function2 = new Function1<String, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.CoroutineCodegenKt$acceptWithStateMachine$visitor$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(String str3) {
                invoke2(str3);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                classCodegen.getContext().getKtDiagnosticReporter().at(irFunction, classCodegen.getIrClass()).report(JvmBackendErrors.INSTANCE.getSUSPENSION_POINT_INSIDE_MONITOR(), it);
            }
        };
        String name5 = IrDeclarationsKt.getName(IrUtilsKt.getFile(classCodegen.getIrClass()));
        if (AdditionalIrUtilsKt.isSuspend(irFunction)) {
            z2 = false;
        } else {
            z2 = false;
        }
        methodNode.accept(new CoroutineTransformerMethodVisitor(methodVisitor, i3, name4, desc2, str2, strArr2, internalName2, obtainContinuationClassBuilder, zIsSuspend2, z, function2, i, name5, z2, classCodegen.getType().getInternalName(), false, varsCountByType, !context.getConfiguration().getBoolean(JVMConfigurationKeys.ENABLE_DEBUG_MODE)));
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final boolean anyOfOverriddenFunctionsReturnsNonUnit(IrFunction irFunction) {
        if (!(irFunction instanceof IrSimpleFunction)) {
            return false;
        }
        List listAllOverridden$default = IrUtilsKt.allOverridden$default((IrOverridableDeclaration) irFunction, false, 1, null);
        if ((listAllOverridden$default instanceof Collection) && listAllOverridden$default.isEmpty()) {
            return false;
        }
        Iterator it = listAllOverridden$default.iterator();
        while (it.hasNext()) {
            if (!IrTypePredicatesKt.isUnit(((IrSimpleFunction) it.next()).getReturnType())) {
                return true;
            }
        }
        return false;
    }

    public static final IrSimpleFunction suspendForInlineToOriginal(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Object obj = null;
        if (!Intrinsics.areEqual(irFunction.getOrigin(), JvmLoweredDeclarationOrigin.FOR_INLINE_STATE_MACHINE_TEMPLATE.INSTANCE) && !Intrinsics.areEqual(irFunction.getOrigin(), JvmLoweredDeclarationOrigin.FOR_INLINE_STATE_MACHINE_TEMPLATE_CAPTURES_CROSSINLINE.INSTANCE)) {
            return null;
        }
        for (Object obj2 : IrUtilsKt.getParentAsClass(irFunction).getDeclarations()) {
            IrDeclaration irDeclaration = (IrDeclaration) obj2;
            if ((irDeclaration instanceof IrSimpleFunction) && Intrinsics.areEqual(((IrSimpleFunction) irDeclaration).getAttributeOwnerId(), ((IrSimpleFunction) irFunction).getAttributeOwnerId())) {
                obj = obj2;
                break;
            }
        }
        return (IrSimpleFunction) obj;
    }

    public static final boolean isSuspendCapturingCrossinline(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        if ((irFunction instanceof IrSimpleFunction) && JvmIrCoroutineUtilsKt.hasContinuation(irFunction)) {
            List<IrDeclaration> declarations = IrUtilsKt.getParentAsClass(irFunction).getDeclarations();
            if (!(declarations instanceof Collection) || !declarations.isEmpty()) {
                for (IrDeclaration irDeclaration : declarations) {
                    if ((irDeclaration instanceof IrSimpleFunction) && Intrinsics.areEqual(((IrSimpleFunction) irDeclaration).getAttributeOwnerId(), ((IrSimpleFunction) irFunction).getAttributeOwnerId()) && Intrinsics.areEqual(irDeclaration.getOrigin(), JvmLoweredDeclarationOrigin.FOR_INLINE_STATE_MACHINE_TEMPLATE_CAPTURES_CROSSINLINE.INSTANCE)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static final IrClass continuationClass(IrFunction irFunction) {
        List<IrStatement> statements;
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        IrBody body = irFunction.getBody();
        Object obj = null;
        IrBlockBody irBlockBody = body instanceof IrBlockBody ? (IrBlockBody) body : null;
        if (irBlockBody != null && (statements = irBlockBody.getStatements()) != null) {
            for (Object obj2 : statements) {
                IrStatement irStatement = (IrStatement) obj2;
                if ((irStatement instanceof IrClass) && Intrinsics.areEqual(((IrClass) irStatement).getOrigin(), JvmLoweredDeclarationOrigin.CONTINUATION_CLASS.INSTANCE)) {
                    obj = obj2;
                    break;
                }
            }
            obj = (IrStatement) obj;
        }
        return (IrClass) obj;
    }

    public static final boolean isReadOfInlineLambda(IrExpression irExpression) {
        if (!JvmIrCoroutineUtilsKt.isReadOfCrossinline(irExpression)) {
            if (irExpression instanceof IrGetValue) {
                IrGetValue irGetValue = (IrGetValue) irExpression;
                if (Intrinsics.areEqual(irGetValue.getOrigin(), IrStatementOrigin.VARIABLE_AS_FUNCTION.INSTANCE)) {
                    IrValueDeclaration owner = irGetValue.getSymbol().getOwner();
                    IrValueParameter irValueParameter = owner instanceof IrValueParameter ? (IrValueParameter) owner : null;
                    if (irValueParameter == null || irValueParameter.getIsNoinline()) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    public static final IrType originalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        if (irFunction instanceof IrSimpleFunction) {
            IrSimpleFunction irSimpleFunction = (IrSimpleFunction) irFunction;
            if (irSimpleFunction.getIsSuspend()) {
                IrAttributeContainer attributeOwnerId = irSimpleFunction.getAttributeOwnerId();
                Intrinsics.checkNotNull(attributeOwnerId, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
                IrSimpleFunction irSimpleFunction2 = (IrSimpleFunction) attributeOwnerId;
                IrType irTypeUnboxType = InlineClassAbi.INSTANCE.unboxType(irSimpleFunction2.getReturnType());
                if (irTypeUnboxType != null && !IrTypePredicatesKt.isPrimitiveType$default(irTypeUnboxType, false, 1, null) && !overridesReturningDifferentType(irSimpleFunction2, irSimpleFunction2.getReturnType())) {
                    return irSimpleFunction2.getReturnType();
                }
            }
        }
        return null;
    }

    private static final boolean overridesReturningDifferentType(IrSimpleFunction irSimpleFunction, IrType irType) {
        return overridesReturningDifferentType$dfs(new HashSet(), irType, irSimpleFunction);
    }

    private static final boolean overridesReturningDifferentType$dfs(HashSet<IrSimpleFunction> hashSet, IrType irType, IrSimpleFunction irSimpleFunction) {
        if (!hashSet.add(irSimpleFunction)) {
            return false;
        }
        Iterator<IrSimpleFunctionSymbol> it = irSimpleFunction.getOverriddenSymbols().iterator();
        while (it.hasNext()) {
            IrSimpleFunction owner = it.next().getOwner();
            IrType returnType = owner.getReturnType();
            if (!IrDeclarationsKt.isSingleFieldValueClass(JvmIrTypeUtilsKt.getErasedUpperBound(returnType))) {
                return true;
            }
            if ((IrTypeUtilsKt.isNullable(returnType) && IrTypeUtilsKt.isNullable(InlineClassAbiKt.unboxInlineClass(IrTypesKt.makeNotNull(returnType)))) || !Intrinsics.areEqual(IrTypesKt.getClassOrNull(returnType), IrTypesKt.getClassOrNull(irType)) || overridesReturningDifferentType$dfs(hashSet, irType, owner)) {
                return true;
            }
        }
        return false;
    }
}
