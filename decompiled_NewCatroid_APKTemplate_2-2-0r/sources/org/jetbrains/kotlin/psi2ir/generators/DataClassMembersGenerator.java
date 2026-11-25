package org.jetbrains.kotlin.psi2ir.generators;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.DataClassMethodGenerator;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.util.OverridesKt;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: DataClassMembersGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001:\u0001\u001cB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J(\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0016\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015J\u0016\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015J\u0016\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015J\u0016\u0010\u0018\u001a\u00020\u0011*\u0006\u0012\u0002\b\u00030\u00192\u0006\u0010\u001a\u001a\u00020\u001bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/DataClassMembersGenerator;", "Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationGeneratorExtension;", "declarationGenerator", "Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationGenerator;", "generateBodies", "", "(Lorg/jetbrains/kotlin/psi2ir/generators/DeclarationGenerator;Z)V", "declareSimpleFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "startOffset", "", "endOffset", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "function", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "generateDataClassMembers", "", "ktClassOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "generateMultiFieldValueClassMembers", "generateSingleFieldValueClassMembers", "commitSubstituted", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "MyDataClassMethodGenerator", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DataClassMembersGenerator extends DeclarationGeneratorExtension {
    private final boolean generateBodies;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataClassMembersGenerator(DeclarationGenerator declarationGenerator, boolean z) {
        super(declarationGenerator);
        Intrinsics.checkNotNullParameter(declarationGenerator, "declarationGenerator");
        this.generateBodies = z;
    }

    public final void generateSingleFieldValueClassMembers(KtClassOrObject ktClassOrObject, IrClass irClass) {
        Intrinsics.checkNotNullParameter(ktClassOrObject, "ktClassOrObject");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        new MyDataClassMethodGenerator(this, ktClassOrObject, irClass, IrDeclarationOrigin.GENERATED_SINGLE_FIELD_VALUE_CLASS_MEMBER.INSTANCE).generate();
    }

    public final void generateMultiFieldValueClassMembers(KtClassOrObject ktClassOrObject, IrClass irClass) {
        Intrinsics.checkNotNullParameter(ktClassOrObject, "ktClassOrObject");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        new MyDataClassMethodGenerator(this, ktClassOrObject, irClass, IrDeclarationOrigin.GENERATED_MULTI_FIELD_VALUE_CLASS_MEMBER.INSTANCE).generate();
    }

    public final void generateDataClassMembers(KtClassOrObject ktClassOrObject, IrClass irClass) {
        Intrinsics.checkNotNullParameter(ktClassOrObject, "ktClassOrObject");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        new MyDataClassMethodGenerator(this, ktClassOrObject, irClass, IrDeclarationOrigin.GENERATED_DATA_CLASS_MEMBER.INSTANCE).generate();
    }

    public final void commitSubstituted(IrMemberAccessExpression<?> irMemberAccessExpression, CallableDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(irMemberAccessExpression, "<this>");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        getContext().commitSubstituted(irMemberAccessExpression, descriptor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrSimpleFunction declareSimpleFunction(int startOffset, int endOffset, IrDeclarationOrigin origin, FunctionDescriptor function) {
        IrSimpleFunction irSimpleFunctionDeclareSimpleFunctionWithOverrides = OverridesKt.declareSimpleFunctionWithOverrides(getContext().getSymbolTable(), startOffset, endOffset, origin, function);
        KotlinType returnType = function.getReturnType();
        Intrinsics.checkNotNull(returnType);
        irSimpleFunctionDeclareSimpleFunctionWithOverrides.setReturnType(toIrType(returnType));
        return irSimpleFunctionDeclareSimpleFunctionWithOverrides;
    }

    /* JADX INFO: compiled from: DataClassMembersGenerator.kt */
    @Metadata(d1 = {"\u0000M\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003*\u0001\f\b\u0082\u0004\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0014J\u001e\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018H\u0014J\u001e\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0018H\u0014J\u001e\u0010\u001d\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0018H\u0014J\u001e\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0018H\u0014R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0010\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/DataClassMembersGenerator$MyDataClassMethodGenerator;", "Lorg/jetbrains/kotlin/backend/common/DataClassMethodGenerator;", "ktClassOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "(Lorg/jetbrains/kotlin/psi2ir/generators/DataClassMembersGenerator;Lorg/jetbrains/kotlin/psi/KtClassOrObject;Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;)V", "getIrClass", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "irDataClassMembersGenerator", "org/jetbrains/kotlin/psi2ir/generators/DataClassMembersGenerator$MyDataClassMethodGenerator$irDataClassMembersGenerator$1", "Lorg/jetbrains/kotlin/psi2ir/generators/DataClassMembersGenerator$MyDataClassMethodGenerator$irDataClassMembersGenerator$1;", "getOrigin", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "generateComponentFunction", "", "function", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "parameter", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "generateCopyFunction", "constructorParameters", "", "Lorg/jetbrains/kotlin/psi/KtParameter;", "generateEqualsMethod", "properties", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "generateHashCodeMethod", "generateToStringMethod", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class MyDataClassMethodGenerator extends DataClassMethodGenerator {
        private final IrClass irClass;
        private final DataClassMembersGenerator$MyDataClassMethodGenerator$irDataClassMembersGenerator$1 irDataClassMembersGenerator;
        private final IrDeclarationOrigin origin;
        final /* synthetic */ DataClassMembersGenerator this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MyDataClassMethodGenerator(DataClassMembersGenerator dataClassMembersGenerator, KtClassOrObject ktClassOrObject, IrClass irClass, IrDeclarationOrigin origin) {
            super(ktClassOrObject, dataClassMembersGenerator.getDeclarationGenerator().getContext().getBindingContext());
            Intrinsics.checkNotNullParameter(ktClassOrObject, "ktClassOrObject");
            Intrinsics.checkNotNullParameter(irClass, "irClass");
            Intrinsics.checkNotNullParameter(origin, "origin");
            this.this$0 = dataClassMembersGenerator;
            this.irClass = irClass;
            this.origin = origin;
            this.irDataClassMembersGenerator = new DataClassMembersGenerator$MyDataClassMethodGenerator$irDataClassMembersGenerator$1(dataClassMembersGenerator, dataClassMembersGenerator.getContext(), dataClassMembersGenerator.getContext().getSymbolTable(), irClass, ktClassOrObject.mo7391getFqName(), origin, dataClassMembersGenerator.generateBodies);
        }

        public final IrClass getIrClass() {
            return this.irClass;
        }

        public final IrDeclarationOrigin getOrigin() {
            return this.origin;
        }

        @Override // org.jetbrains.kotlin.backend.common.DataClassMethodGenerator
        protected void generateComponentFunction(FunctionDescriptor function, ValueParameterDescriptor parameter) {
            Intrinsics.checkNotNullParameter(function, "function");
            Intrinsics.checkNotNullParameter(parameter, "parameter");
            if (this.irClass.getIsData()) {
                this.irDataClassMembersGenerator.generateComponentFunction(function, this.irDataClassMembersGenerator.getProperty(parameter));
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.DataClassMethodGenerator
        protected void generateCopyFunction(FunctionDescriptor function, List<? extends KtParameter> constructorParameters) {
            Intrinsics.checkNotNullParameter(function, "function");
            Intrinsics.checkNotNullParameter(constructorParameters, "constructorParameters");
            if (this.irClass.getIsData()) {
                ClassConstructorDescriptor classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor = getClassDescriptor().mo6347getUnsubstitutedPrimaryConstructor();
                if (classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor == null) {
                    throw new AssertionError("Data class should have a primary constructor: " + getClassDescriptor());
                }
                this.irDataClassMembersGenerator.generateCopyFunction(function, this.this$0.getContext().getSymbolTable().getDescriptorExtension().referenceConstructor(classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor));
            }
        }

        @Override // org.jetbrains.kotlin.backend.common.FunctionsFromAnyGenerator
        protected void generateEqualsMethod(FunctionDescriptor function, List<? extends PropertyDescriptor> properties) {
            Intrinsics.checkNotNullParameter(function, "function");
            Intrinsics.checkNotNullParameter(properties, "properties");
            this.irDataClassMembersGenerator.generateEqualsMethod(function, properties);
        }

        @Override // org.jetbrains.kotlin.backend.common.FunctionsFromAnyGenerator
        protected void generateHashCodeMethod(FunctionDescriptor function, List<? extends PropertyDescriptor> properties) {
            Intrinsics.checkNotNullParameter(function, "function");
            Intrinsics.checkNotNullParameter(properties, "properties");
            this.irDataClassMembersGenerator.generateHashCodeMethod(function, properties);
        }

        @Override // org.jetbrains.kotlin.backend.common.FunctionsFromAnyGenerator
        protected void generateToStringMethod(FunctionDescriptor function, List<? extends PropertyDescriptor> properties) {
            Intrinsics.checkNotNullParameter(function, "function");
            Intrinsics.checkNotNullParameter(properties, "properties");
            this.irDataClassMembersGenerator.generateToStringMethod(function, properties);
        }
    }
}
