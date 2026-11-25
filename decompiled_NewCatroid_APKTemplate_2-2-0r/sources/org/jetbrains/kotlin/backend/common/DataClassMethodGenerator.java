package org.jetbrains.kotlin.backend.common;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.resolve.BindingContext;

/* JADX INFO: compiled from: DataClassMethodGenerator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016J\u0018\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH$J\b\u0010\u000e\u001a\u00020\bH\u0002J\u001e\u0010\u000f\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H$J\u0016\u0010\u0013\u001a\u00020\b2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0002¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/DataClassMethodGenerator;", "Lorg/jetbrains/kotlin/backend/common/FunctionsFromAnyGenerator;", "declaration", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "(Lorg/jetbrains/kotlin/psi/KtClassOrObject;Lorg/jetbrains/kotlin/resolve/BindingContext;)V", CompilerOptions.GENERATE, "", "generateComponentFunction", "function", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "parameter", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "generateComponentFunctionsForDataClasses", "generateCopyFunction", "constructorParameters", "", "Lorg/jetbrains/kotlin/psi/KtParameter;", "generateCopyFunctionForDataClasses", "backend-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DataClassMethodGenerator extends FunctionsFromAnyGenerator {
    protected abstract void generateComponentFunction(FunctionDescriptor function, ValueParameterDescriptor parameter);

    protected abstract void generateCopyFunction(FunctionDescriptor function, List<? extends KtParameter> constructorParameters);

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataClassMethodGenerator(KtClassOrObject declaration, BindingContext bindingContext) {
        super(declaration, bindingContext);
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
    }

    @Override // org.jetbrains.kotlin.backend.common.FunctionsFromAnyGenerator
    public void generate() {
        if (!(getDeclaration() instanceof KtObjectDeclaration)) {
            generateComponentFunctionsForDataClasses();
            generateCopyFunctionForDataClasses(getPrimaryConstructorParameters());
        }
        super.generate();
    }

    private final void generateComponentFunctionsForDataClasses() {
        ClassConstructorDescriptor classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor = getClassDescriptor().mo6347getUnsubstitutedPrimaryConstructor();
        if (classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor == null) {
            return;
        }
        for (ValueParameterDescriptor valueParameterDescriptor : classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor.getValueParameters()) {
            FunctionDescriptor functionDescriptor = (FunctionDescriptor) getBindingContext().get(BindingContext.DATA_CLASS_COMPONENT_FUNCTION, valueParameterDescriptor);
            if (functionDescriptor != null) {
                Intrinsics.checkNotNull(valueParameterDescriptor);
                generateComponentFunction(functionDescriptor, valueParameterDescriptor);
            }
        }
    }

    private final void generateCopyFunctionForDataClasses(List<? extends KtParameter> constructorParameters) {
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) getBindingContext().get(BindingContext.DATA_CLASS_COPY_FUNCTION, getClassDescriptor());
        if (functionDescriptor == null) {
            return;
        }
        generateCopyFunction(functionDescriptor, constructorParameters);
    }
}
