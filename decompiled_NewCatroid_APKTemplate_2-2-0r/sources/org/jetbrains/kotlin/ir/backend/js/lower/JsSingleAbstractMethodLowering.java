package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.CompilationExceptionKt;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.common.lower.SingleAbstractMethodLowering;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: JsSingleAbstractMethodLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\n\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0014J\u0010\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\nH\u0016J\u001e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016H\u0016J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0018\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u0007H\u0016R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\b\u001a\u00020\t*\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/JsSingleAbstractMethodLowering;", "Lorg/jetbrains/kotlin/backend/common/lower/SingleAbstractMethodLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "enclosingBodyContainer", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "needEqualsHashCodeMethods", "", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getNeedEqualsHashCodeMethods", "(Lorg/jetbrains/kotlin/ir/types/IrType;)Z", "currentScopeSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "getSuperTypeForWrapper", "typeOperand", "getWrapperVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "scopes", "", "Lorg/jetbrains/kotlin/backend/common/ScopeWithIr;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsSingleAbstractMethodLowering extends SingleAbstractMethodLowering implements BodyLoweringPass {
    private IrDeclaration enclosingBodyContainer;

    @Override // org.jetbrains.kotlin.backend.common.lower.SingleAbstractMethodLowering
    public boolean getNeedEqualsHashCodeMethods(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsSingleAbstractMethodLowering(CommonBackendContext context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.SingleAbstractMethodLowering
    public DescriptorVisibility getWrapperVisibility(IrTypeOperatorCall expression, List<? extends ScopeWithIr> scopes) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(scopes, "scopes");
        DescriptorVisibility LOCAL = DescriptorVisibilities.LOCAL;
        Intrinsics.checkNotNullExpressionValue(LOCAL, "LOCAL");
        return LOCAL;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.SingleAbstractMethodLowering, org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        super.lower(irFile);
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        getCachedImplementations().clear();
        getInlineCachedImplementations().clear();
        IrDeclarationContainer parentClassOrNull = IrUtilsKt.getParentClassOrNull(container);
        if (parentClassOrNull == null) {
            parentClassOrNull = IrUtilsKt.getFile(container);
        }
        setEnclosingContainer(parentClassOrNull);
        this.enclosingBodyContainer = container;
        transformChildrenVoid(irBody);
        for (IrClass irClass : CollectionsKt.plus((Collection) getCachedImplementations().values(), (Iterable) getInlineCachedImplementations().values())) {
            IrDeclarationParent parent = irClass.getParent();
            Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer");
            ((IrDeclarationContainer) parent).getDeclarations().add(irClass);
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.SingleAbstractMethodLowering
    protected IrSymbol currentScopeSymbol() {
        IrSymbol irSymbolCurrentScopeSymbol = super.currentScopeSymbol();
        if (irSymbolCurrentScopeSymbol != null) {
            return irSymbolCurrentScopeSymbol;
        }
        IrDeclaration irDeclaration = this.enclosingBodyContainer;
        IrDeclaration irDeclaration2 = irDeclaration instanceof IrSymbolOwner ? irDeclaration : null;
        if (irDeclaration2 != null) {
            return irDeclaration2.getSymbol();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.SingleAbstractMethodLowering
    public IrType getSuperTypeForWrapper(IrType typeOperand) {
        IrType defaultType;
        Intrinsics.checkNotNullParameter(typeOperand, "typeOperand");
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(typeOperand);
        if (classOrNull != null && (defaultType = IrTypesKt.getDefaultType(classOrNull)) != null) {
            return defaultType;
        }
        CompilationExceptionKt.compilationException("Unsupported SAM conversion", typeOperand);
        throw null;
    }
}
