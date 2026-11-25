package org.jetbrains.kotlin.ir.backend.js.lower;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.MappingsKt;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.JsLoweredDeclarationOrigin;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ObjectLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0002¨\u0006\u0005"}, d2 = {"getOrCreateGetInstanceFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "obj", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ObjectLoweringKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final IrSimpleFunction getOrCreateGetInstanceFunction(final JsCommonBackendContext jsCommonBackendContext, final IrClass irClass) {
        return (IrSimpleFunction) MappingsKt.getOrPut(jsCommonBackendContext.getMapping().getObjectToGetInstanceFunction(), irClass, new Function0<IrSimpleFunction>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.ObjectLoweringKt.getOrCreateGetInstanceFunction.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrSimpleFunction invoke() {
                IrFactory irFactory = jsCommonBackendContext.getIrFactory();
                IrClass irClass2 = irClass;
                IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
                Name nameIdentifier = Name.identifier(irClass2.getName().asString() + Namer.OBJECT_INSTANCE_FUNCTION_SUFFIX);
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                irFunctionBuilder.setName(nameIdentifier);
                irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(irClass2));
                irFunctionBuilder.setOrigin(JsLoweredDeclarationOrigin.OBJECT_GET_INSTANCE_FUNCTION.INSTANCE);
                irFunctionBuilder.setVisibility(irClass2.getVisibility());
                IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
                irSimpleFunctionBuildFunction.setParent(irClass.getParent());
                return irSimpleFunctionBuildFunction;
            }
        });
    }
}
