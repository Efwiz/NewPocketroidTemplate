package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CodegenUtil;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKt;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: JvmStaticInCompanionObjectGenerator.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u00122\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0001:\u0001\u0012B%\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\u0002¢\u0006\u0002\u0010\fJ\u0019\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u0003H\u0096\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/codegen/JvmStaticInCompanionObjectGenerator;", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/codegen/ImplementationBodyCodegen;", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "declarationOrigin", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "parentBodyCodegen", "(Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;Lorg/jetbrains/kotlin/codegen/state/GenerationState;Lorg/jetbrains/kotlin/codegen/ImplementationBodyCodegen;)V", "typeMapper", "Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper;", "invoke", "codegen", "classBuilder", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmStaticInCompanionObjectGenerator implements Function2<ImplementationBodyCodegen, ClassBuilder, Unit> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final JvmDeclarationOrigin declarationOrigin;
    private final FunctionDescriptor descriptor;
    private final GenerationState state;
    private final KotlinTypeMapper typeMapper;

    @JvmStatic
    public static final FunctionDescriptor createStaticFunctionDescriptor(FunctionDescriptor functionDescriptor) {
        return INSTANCE.createStaticFunctionDescriptor(functionDescriptor);
    }

    public JvmStaticInCompanionObjectGenerator(FunctionDescriptor descriptor, JvmDeclarationOrigin declarationOrigin, GenerationState state, ImplementationBodyCodegen parentBodyCodegen) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(declarationOrigin, "declarationOrigin");
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(parentBodyCodegen, "parentBodyCodegen");
        this.descriptor = descriptor;
        this.declarationOrigin = declarationOrigin;
        this.state = state;
        this.typeMapper = state.getTypeMapper();
        parentBodyCodegen.getContext().accessibleDescriptor(JvmCodegenUtil.getDirectMember(descriptor), null);
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Unit invoke(ImplementationBodyCodegen implementationBodyCodegen, ClassBuilder classBuilder) {
        invoke2(implementationBodyCodegen, classBuilder);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public void invoke2(ImplementationBodyCodegen codegen, ClassBuilder classBuilder) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(classBuilder, "classBuilder");
        FunctionDescriptor functionDescriptorCreateStaticFunctionDescriptor = INSTANCE.createStaticFunctionDescriptor(this.descriptor);
        PsiElement element = this.declarationOrigin.getElement();
        codegen.functionCodegen.generateMethod(JvmDeclarationOriginKt.Synthetic(element, functionDescriptorCreateStaticFunctionDescriptor), functionDescriptorCreateStaticFunctionDescriptor, new FunctionGenerationStrategy.CodegenBased(this.state) { // from class: org.jetbrains.kotlin.codegen.JvmStaticInCompanionObjectGenerator.invoke.1
            @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy.CodegenBased, org.jetbrains.kotlin.codegen.FunctionGenerationStrategy
            public boolean skipNotNullAssertionsForParameters() {
                return true;
            }

            @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy.CodegenBased
            public void doGenerateBody(ExpressionCodegen codegen2, JvmMethodSignature signature) {
                Intrinsics.checkNotNullParameter(codegen2, "codegen");
                Intrinsics.checkNotNullParameter(signature, "signature");
                InstructionAdapter instructionAdapter = codegen2.v;
                DeclarationDescriptor containingDeclaration = JvmStaticInCompanionObjectGenerator.this.descriptor.getContainingModule();
                Intrinsics.checkNotNull(containingDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                StackValue.singleton((ClassDescriptor) containingDeclaration, JvmStaticInCompanionObjectGenerator.this.typeMapper).put(instructionAdapter);
                Method asmMethod = signature.getAsmMethod();
                Intrinsics.checkNotNullExpressionValue(asmMethod, "getAsmMethod(...)");
                Type[] argumentTypes = asmMethod.getArgumentTypes();
                Intrinsics.checkNotNullExpressionValue(argumentTypes, "getArgumentTypes(...)");
                int size = 0;
                for (Type type : argumentTypes) {
                    instructionAdapter.load(size, type);
                    size += type.getSize();
                }
                if (JvmStaticInCompanionObjectGenerator.this.descriptor instanceof PropertyAccessorDescriptor) {
                    StackValue.Property propertyIntermediateValueForProperty = codegen2.intermediateValueForProperty(((PropertyAccessorDescriptor) JvmStaticInCompanionObjectGenerator.this.descriptor).getCorrespondingProperty(), false, null, StackValue.none());
                    Intrinsics.checkNotNullExpressionValue(propertyIntermediateValueForProperty, "intermediateValueForProperty(...)");
                    if (JvmStaticInCompanionObjectGenerator.this.descriptor instanceof PropertyGetterDescriptor) {
                        propertyIntermediateValueForProperty.put(signature.getReturnType(), instructionAdapter);
                    } else {
                        propertyIntermediateValueForProperty.store(StackValue.onStack(propertyIntermediateValueForProperty.type, propertyIntermediateValueForProperty.kotlinType), instructionAdapter, true);
                    }
                } else {
                    KotlinTypeMapper kotlinTypeMapper = JvmStaticInCompanionObjectGenerator.this.typeMapper;
                    CallableMemberDescriptor callableMemberDescriptorAccessibleDescriptor = codegen2.context.accessibleDescriptor(JvmStaticInCompanionObjectGenerator.this.descriptor, null);
                    Intrinsics.checkNotNullExpressionValue(callableMemberDescriptorAccessibleDescriptor, "accessibleDescriptor(...)");
                    CallableMethod callableMethodMapToCallableMethod$default = KotlinTypeMapper.mapToCallableMethod$default(kotlinTypeMapper, (FunctionDescriptor) callableMemberDescriptorAccessibleDescriptor, false, null, null, 12, null);
                    Intrinsics.checkNotNull(instructionAdapter);
                    callableMethodMapToCallableMethod$default.genInvokeInstruction(instructionAdapter);
                }
                instructionAdapter.areturn(asmMethod.getReturnType());
            }
        });
        if (element instanceof KtNamedFunction) {
            codegen.functionCodegen.generateOverloadsWithDefaultValues((KtNamedFunction) element, functionDescriptorCreateStaticFunctionDescriptor, this.descriptor);
        }
    }

    /* JADX INFO: compiled from: JvmStaticInCompanionObjectGenerator.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0007¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/codegen/JvmStaticInCompanionObjectGenerator$Companion;", "", "()V", "createStaticFunctionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "descriptor", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final FunctionDescriptor createStaticFunctionDescriptor(FunctionDescriptor descriptor) {
            FunctionDescriptor functionDescriptor;
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            if (descriptor instanceof PropertyAccessorDescriptor) {
                PropertyDescriptor correspondingProperty = ((PropertyAccessorDescriptor) descriptor).getCorrespondingProperty();
                Intrinsics.checkNotNullExpressionValue(correspondingProperty, "getCorrespondingProperty(...)");
                functionDescriptor = correspondingProperty;
            } else {
                functionDescriptor = descriptor;
            }
            CallableMemberDescriptor callableMemberDescriptor = functionDescriptor;
            CodegenUtil codegenUtil = CodegenUtil.INSTANCE;
            DeclarationDescriptor containingDeclaration = descriptor.getContainingModule().getContainingModule();
            Intrinsics.checkNotNull(containingDeclaration);
            Modality modality = descriptor.getModality();
            Intrinsics.checkNotNullExpressionValue(modality, "getModality(...)");
            DescriptorVisibility visibility = descriptor.getVisibility();
            Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
            FunctionDescriptor functionDescriptor2 = codegenUtil.copyFunctions(callableMemberDescriptor, callableMemberDescriptor, containingDeclaration, modality, visibility, CallableMemberDescriptor.Kind.SYNTHESIZED, false).get(descriptor);
            Intrinsics.checkNotNull(functionDescriptor2);
            return functionDescriptor2;
        }
    }
}
