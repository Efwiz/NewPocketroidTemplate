package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.lower.DefaultArgumentFunctionFactory;
import org.jetbrains.kotlin.ir.backend.js.JsLoweredDeclarationOrigin;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrValueParameterBuilder;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: JsDefaultArgumentFunctionFactory.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001c\u0010\u0005\u001a\u00020\u0006*\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\nH\u0014J\f\u0010\u000b\u001a\u00020\n*\u00020\fH\u0014J\f\u0010\r\u001a\u00020\u000e*\u00020\u0007H\u0002¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/JsDefaultArgumentFunctionFactory;", "Lorg/jetbrains/kotlin/backend/common/lower/DefaultArgumentFunctionFactory;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "generateDefaultArgumentStubFrom", "", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "original", "useConstructorMarker", "", "hasNullAsUndefinedValue", "Lorg/jetbrains/kotlin/ir/types/IrType;", "introduceContextParam", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsDefaultArgumentFunctionFactory extends DefaultArgumentFunctionFactory {
    @Override // org.jetbrains.kotlin.backend.common.lower.DefaultArgumentFunctionFactory
    protected boolean hasNullAsUndefinedValue(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsDefaultArgumentFunctionFactory(CommonBackendContext context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.DefaultArgumentFunctionFactory
    protected void generateDefaultArgumentStubFrom(IrFunction irFunction, IrFunction original, boolean z) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Intrinsics.checkNotNullParameter(original, "original");
        copyAttributesFrom(irFunction, original);
        IrUtilsKt.copyTypeParametersFrom$default(irFunction, original, null, null, 6, null);
        copyReturnTypeFrom(irFunction, original);
        copyReceiversFrom(irFunction, original);
        copyValueParametersFrom(irFunction, original);
        if (IrUtilsKt.isTopLevel(original)) {
            return;
        }
        introduceContextParam(irFunction);
    }

    private final IrValueParameter introduceContextParam(IrFunction irFunction) {
        IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
        Name nameIdentifier = Name.identifier("$super");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irValueParameterBuilder.setName(nameIdentifier);
        irValueParameterBuilder.setType(IrTypesKt.makeNullable(IrUtilsKt.getDefaultType(IrUtilsKt.getParentAsClass(irFunction))));
        irValueParameterBuilder.setOrigin(JsLoweredDeclarationOrigin.JS_SUPER_CONTEXT_PARAMETER.INSTANCE);
        if (irValueParameterBuilder.getIndex() == -1) {
            irValueParameterBuilder.setIndex(irFunction.getValueParameters().size());
        }
        IrValueParameter irValueParameterBuildValueParameter = DeclarationBuildersKt.buildValueParameter(irFunction.getFactory(), irValueParameterBuilder, irFunction);
        irFunction.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irFunction.getValueParameters(), irValueParameterBuildValueParameter));
        return irValueParameterBuildValueParameter;
    }
}
