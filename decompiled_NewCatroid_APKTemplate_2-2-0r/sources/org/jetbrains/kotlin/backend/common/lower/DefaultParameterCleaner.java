package org.jetbrains.kotlin.backend.common.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.backend.common.Mapping;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.impl.IrErrorExpressionImpl;

/* JADX INFO: compiled from: DefaultArgumentStubGenerator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u000e2\u0006\u0010\u0010\u001a\u00020\u000fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\n¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/DefaultParameterCleaner;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "replaceDefaultValuesWithStubs", "", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;Z)V", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "getReplaceDefaultValuesWithStubs", "()Z", "withLocalDeclarations", "getWithLocalDeclarations", "transformFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DefaultParameterCleaner implements DeclarationTransformer {
    private final CommonBackendContext context;
    private final boolean replaceDefaultValuesWithStubs;

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public boolean getWithLocalDeclarations() {
        return true;
    }

    public DefaultParameterCleaner(CommonBackendContext context, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.replaceDefaultValuesWithStubs = z;
    }

    public /* synthetic */ DefaultParameterCleaner(CommonBackendContext commonBackendContext, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(commonBackendContext, (i & 2) != 0 ? false : z);
    }

    public final CommonBackendContext getContext() {
        return this.context;
    }

    public final boolean getReplaceDefaultValuesWithStubs() {
        return this.replaceDefaultValuesWithStubs;
    }

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (declaration instanceof IrValueParameter) {
            IrValueParameter irValueParameter = (IrValueParameter) declaration;
            if (irValueParameter.getDefaultValue() != null) {
                if (this.replaceDefaultValuesWithStubs) {
                    Mapping.Delegate<IrFunction, IrFunction> defaultArgumentsOriginalFunction = this.context.getMapping().getDefaultArgumentsOriginalFunction();
                    IrDeclarationParent parent = declaration.getParent();
                    Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrFunction");
                    if (defaultArgumentsOriginalFunction.get((IrFunction) parent) == null) {
                        IrFactory irFactory = this.context.getIrFactory();
                        IrErrorExpressionImpl irErrorExpressionImpl = new IrErrorExpressionImpl(-1, -1, irValueParameter.getType(), "Default Stub");
                        IrExpressionBody defaultValue = irValueParameter.getDefaultValue();
                        Intrinsics.checkNotNull(defaultValue);
                        irErrorExpressionImpl.setAttributeOwnerId(defaultValue.getExpression());
                        Unit unit = Unit.INSTANCE;
                        irValueParameter.setDefaultValue(IrFactoryHelpersKt.createExpressionBody(irFactory, irErrorExpressionImpl));
                    }
                } else {
                    irValueParameter.setDefaultValue(null);
                }
            }
        }
        return null;
    }
}
