package org.jetbrains.kotlin.backend.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.lower.VisibilityPolicy;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.load.java.JavaDescriptorVisibilities;

/* JADX INFO: compiled from: JvmLower.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0018\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0018\u0010\f\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\u000e\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000bH\u0002¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmVisibilityPolicy;", "Lorg/jetbrains/kotlin/backend/common/lower/VisibilityPolicy;", "()V", "forCapturedField", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "value", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "forClass", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "inInlineFunctionScope", "", "forConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "scopedVisibility", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmVisibilityPolicy implements VisibilityPolicy {
    @Override // org.jetbrains.kotlin.backend.common.lower.VisibilityPolicy
    public DescriptorVisibility forClass(IrClass declaration, boolean inInlineFunctionScope) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (JvmLowerKt.isGeneratedLambdaClass(declaration)) {
            return scopedVisibility(inInlineFunctionScope);
        }
        return declaration.getVisibility();
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.VisibilityPolicy
    public DescriptorVisibility forConstructor(IrConstructor declaration, boolean inInlineFunctionScope) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (IrUtilsKt.isAnonymousObject(IrUtilsKt.getParentAsClass(declaration))) {
            return scopedVisibility(inInlineFunctionScope);
        }
        return declaration.getVisibility();
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.VisibilityPolicy
    public DescriptorVisibility forCapturedField(IrValueSymbol value) {
        Intrinsics.checkNotNullParameter(value, "value");
        DescriptorVisibility PACKAGE_VISIBILITY = JavaDescriptorVisibilities.PACKAGE_VISIBILITY;
        Intrinsics.checkNotNullExpressionValue(PACKAGE_VISIBILITY, "PACKAGE_VISIBILITY");
        return PACKAGE_VISIBILITY;
    }

    private final DescriptorVisibility scopedVisibility(boolean inInlineFunctionScope) {
        DescriptorVisibility descriptorVisibility;
        String str;
        if (inInlineFunctionScope) {
            descriptorVisibility = DescriptorVisibilities.PUBLIC;
            str = "PUBLIC";
        } else {
            descriptorVisibility = JavaDescriptorVisibilities.PACKAGE_VISIBILITY;
            str = "PACKAGE_VISIBILITY";
        }
        Intrinsics.checkNotNullExpressionValue(descriptorVisibility, str);
        return descriptorVisibility;
    }
}
