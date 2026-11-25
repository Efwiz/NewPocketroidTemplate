package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrInlineUtilsKt;
import org.jetbrains.kotlin.backend.jvm.mapping.MethodSignatureMapper;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrAttributeContainer;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: FakeLocalVariablesForBytecodeInlinerLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003J\u0019\u0010\b\u001a\u00020\t*\u00028\u00002\u0006\u0010\n\u001a\u00020\u000bH&¢\u0006\u0002\u0010\fJ\u0019\u0010\r\u001a\u00020\t*\u00028\u00002\u0006\u0010\u000e\u001a\u00020\u000fH\u0016¢\u0006\u0002\u0010\u0010J!\u0010\u0011\u001a\u00020\t*\u00028\u00002\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016¢\u0006\u0002\u0010\u0016R\u0012\u0010\u0004\u001a\u00020\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0017À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/FakeInliningLocalVariables;", JvmAbi.REPEATABLE_ANNOTATION_CONTAINER_NAME, "Lorg/jetbrains/kotlin/ir/IrElement;", "", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "addFakeLocalVariable", "", "name", "", "(Lorg/jetbrains/kotlin/ir/IrElement;Ljava/lang/String;)V", "addFakeLocalVariableForFun", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "(Lorg/jetbrains/kotlin/ir/IrElement;Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)V", "addFakeLocalVariableForLambda", "argument", "Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;", "callee", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "(Lorg/jetbrains/kotlin/ir/IrElement;Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)V", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface FakeInliningLocalVariables<Container extends IrElement> {
    void addFakeLocalVariable(Container container, String str);

    JvmBackendContext getContext();

    default void addFakeLocalVariableForFun(Container container, IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(container, "<this>");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (declaration instanceof IrFunction) {
            IrFunction irFunction = (IrFunction) declaration;
            if (!irFunction.getIsInline() || declaration.getOrigin().getIsSynthetic() || irFunction.getBody() == null || JvmIrInlineUtilsKt.isInlineOnly(declaration)) {
                return;
            }
            addFakeLocalVariable(container, JvmAbi.LOCAL_VARIABLE_NAME_PREFIX_INLINE_FUNCTION + MethodSignatureMapper.mapFunctionName$default(getContext().getDefaultMethodSignatureMapper(), irFunction, false, 2, null));
        }
    }

    default void addFakeLocalVariableForLambda(Container container, IrAttributeContainer argument, IrFunction callee) {
        Intrinsics.checkNotNullParameter(container, "<this>");
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(callee, "callee");
        String strMapFunctionName$default = MethodSignatureMapper.mapFunctionName$default(getContext().getDefaultMethodSignatureMapper(), callee, false, 2, null);
        Type localClassType = getContext().getLocalClassType(argument);
        Intrinsics.checkNotNull(localClassType);
        String internalName = localClassType.getInternalName();
        Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
        addFakeLocalVariable(container, "$i$a$-" + strMapFunctionName$default + '-' + StringsKt.substringAfterLast$default(internalName, "/", (String) null, 2, (Object) null));
    }
}
