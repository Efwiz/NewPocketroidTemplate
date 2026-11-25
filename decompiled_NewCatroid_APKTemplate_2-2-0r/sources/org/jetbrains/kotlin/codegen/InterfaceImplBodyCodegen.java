package org.jetbrains.kotlin.codegen;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.codegen.context.ClassContext;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.JavaMethodDescriptor;
import org.jetbrains.kotlin.psi.KtPureClassOrObject;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKind;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterSignature;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.util.DeclarationUtilKt;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: InterfaceImplBodyCodegen.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001:\u0001\u001eB3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\f\u0010\n\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000b¢\u0006\u0002\u0010\fJ\n\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0014J\b\u0010\u0014\u001a\u00020\u0015H\u0014J\b\u0010\u0016\u001a\u00020\u0015H\u0014J\u0018\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019H\u0002J\b\u0010\u001b\u001a\u00020\u0015H\u0014J\b\u0010\u001c\u001a\u00020\u0015H\u0014J\b\u0010\u001d\u001a\u00020\u0015H\u0014R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000f\u001a\u00020\u00108BX\u0082\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0011¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/InterfaceImplBodyCodegen;", "Lorg/jetbrains/kotlin/codegen/ClassBodyCodegen;", "aClass", "Lorg/jetbrains/kotlin/psi/KtPureClassOrObject;", "context", "Lorg/jetbrains/kotlin/codegen/context/ClassContext;", RegisterSpec.PREFIX, "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "parentCodegen", "Lorg/jetbrains/kotlin/codegen/MemberCodegen;", "(Lorg/jetbrains/kotlin/psi/KtPureClassOrObject;Lorg/jetbrains/kotlin/codegen/context/ClassContext;Lorg/jetbrains/kotlin/codegen/ClassBuilder;Lorg/jetbrains/kotlin/codegen/state/GenerationState;Lorg/jetbrains/kotlin/codegen/MemberCodegen;)V", "defaultImplType", "Lorg/jetbrains/org/objectweb/asm/Type;", "isAnythingGenerated", "", "()Z", "classForInnerClassRecord", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", ES6Iterator.DONE_PROPERTY, "", "generateDeclaration", "generateDelegationToSuperDefaultImpls", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "implementation", "generateKotlinMetadataAnnotation", "generateSyntheticPartsAfterBody", "generateSyntheticPartsBeforeBody", "InterfaceImplClassBuilder", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InterfaceImplBodyCodegen extends ClassBodyCodegen {
    private final Type defaultImplType;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InterfaceImplBodyCodegen(KtPureClassOrObject aClass, ClassContext context, ClassBuilder v, GenerationState state, MemberCodegen<?> memberCodegen) {
        super(aClass, context, new InterfaceImplClassBuilder(v), state, memberCodegen);
        Intrinsics.checkNotNullParameter(aClass, "aClass");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(state, "state");
        KotlinTypeMapper kotlinTypeMapper = this.typeMapper;
        ClassDescriptor descriptor = this.descriptor;
        Intrinsics.checkNotNullExpressionValue(descriptor, "descriptor");
        this.defaultImplType = kotlinTypeMapper.mapDefaultImpls(descriptor);
    }

    private final boolean isAnythingGenerated() {
        ClassBuilder classBuilder = this.v;
        Intrinsics.checkNotNull(classBuilder, "null cannot be cast to non-null type org.jetbrains.kotlin.codegen.InterfaceImplBodyCodegen.InterfaceImplClassBuilder");
        return ((InterfaceImplClassBuilder) classBuilder).getIsAnythingGenerated();
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    /* JADX INFO: renamed from: generateDeclaration */
    protected void mo6420generateDeclaration() {
        this.v.defineClass(this.myClass.getPsiOrParent(), this.state.getConfig().getClassFileVersion(), Intrinsics.areEqual(this.state.getClassBuilderMode(), ClassBuilderMode.LIGHT_CLASSES) ? 57 : 49, this.defaultImplType.getInternalName(), null, TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME, ArrayUtil.EMPTY_STRING_ARRAY);
        this.v.visitSource(this.myClass.getContainingKtFile().get$name(), null);
    }

    @Override // org.jetbrains.kotlin.codegen.ClassBodyCodegen, org.jetbrains.kotlin.codegen.MemberCodegen
    protected ClassDescriptor classForInnerClassRecord() {
        if (!isAnythingGenerated()) {
            return null;
        }
        InnerClassConsumer.Companion companion = InnerClassConsumer.INSTANCE;
        ClassDescriptor descriptor = this.descriptor;
        Intrinsics.checkNotNullExpressionValue(descriptor, "descriptor");
        return companion.classForInnerClassRecord(descriptor, true);
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    protected void generateSyntheticPartsAfterBody() {
        CallableMemberDescriptor callableMemberDescriptorFindImplementationFromInterface;
        for (DeclarationDescriptor declarationDescriptor : ResolutionScope.DefaultImpls.getContributedDescriptors$default(this.descriptor.getDefaultType().getMemberScope(), null, null, 3, null)) {
            if (declarationDescriptor instanceof CallableMemberDescriptor) {
                CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) declarationDescriptor;
                if (!callableMemberDescriptor.getKind().isReal() && !Intrinsics.areEqual(callableMemberDescriptor.getVisibility(), DescriptorVisibilities.INVISIBLE_FAKE) && callableMemberDescriptor.getModality() != Modality.ABSTRACT && (callableMemberDescriptorFindImplementationFromInterface = DeclarationUtilKt.findImplementationFromInterface(callableMemberDescriptor)) != null && !CodegenUtilKt.isDefinitelyNotDefaultImplsMethod(callableMemberDescriptorFindImplementationFromInterface)) {
                    if (declarationDescriptor instanceof FunctionDescriptor) {
                        generateDelegationToSuperDefaultImpls((FunctionDescriptor) declarationDescriptor, (FunctionDescriptor) callableMemberDescriptorFindImplementationFromInterface);
                    } else if (declarationDescriptor instanceof PropertyDescriptor) {
                        PropertyDescriptor propertyDescriptor = (PropertyDescriptor) callableMemberDescriptorFindImplementationFromInterface;
                        PropertyDescriptor propertyDescriptor2 = (PropertyDescriptor) declarationDescriptor;
                        PropertyGetterDescriptor getter = propertyDescriptor2.getGetter();
                        PropertyGetterDescriptor getter2 = propertyDescriptor.getGetter();
                        if (getter != null && getter2 != null) {
                            generateDelegationToSuperDefaultImpls(getter, getter2);
                        }
                        PropertySetterDescriptor setter = propertyDescriptor2.getSetter();
                        PropertySetterDescriptor setter2 = propertyDescriptor.getSetter();
                        if (setter != null && setter2 != null) {
                            generateDelegationToSuperDefaultImpls(setter, setter2);
                        }
                    }
                }
            }
        }
        generateSyntheticAccessors();
    }

    private final void generateDelegationToSuperDefaultImpls(final FunctionDescriptor descriptor, FunctionDescriptor implementation) {
        final FunctionDescriptor functionDescriptor = (FunctionDescriptor) DeclarationUtilKt.firstSuperMethodFromKotlin(descriptor, implementation);
        if (functionDescriptor == null || (functionDescriptor instanceof JavaMethodDescriptor)) {
            return;
        }
        FunctionDescriptor functionDescriptor2 = descriptor;
        this.functionCodegen.generateMethod(new JvmDeclarationOrigin(JvmDeclarationOriginKind.DEFAULT_IMPL_DELEGATION_TO_SUPERINTERFACE_DEFAULT_IMPL, DescriptorToSourceUtils.descriptorToDeclaration(functionDescriptor2), functionDescriptor2, null, 8, null), descriptor, new FunctionGenerationStrategy.CodegenBased(this.state) { // from class: org.jetbrains.kotlin.codegen.InterfaceImplBodyCodegen.generateDelegationToSuperDefaultImpls.1
            @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy.CodegenBased
            public void doGenerateBody(ExpressionCodegen codegen, JvmMethodSignature signature) {
                Intrinsics.checkNotNullParameter(codegen, "codegen");
                Intrinsics.checkNotNullParameter(signature, "signature");
                InstructionAdapter instructionAdapter = codegen.v;
                KotlinTypeMapper typeMapper = InterfaceImplBodyCodegen.this.typeMapper;
                Intrinsics.checkNotNullExpressionValue(typeMapper, "typeMapper");
                CallableMethod callableMethodMapToCallableMethod$default = KotlinTypeMapper.mapToCallableMethod$default(typeMapper, functionDescriptor, true, null, null, 12, null);
                List<JvmMethodParameterSignature> valueParameters = signature.getValueParameters();
                Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                List<JvmMethodParameterSignature> valueParameters2 = callableMethodMapToCallableMethod$default.getValueParameters();
                if (valueParameters.size() != valueParameters2.size()) {
                    StringBuilder sb = new StringBuilder("Method from super interface has a different signature.\n");
                    String str = String.format("This method:\n%s\n%s\n%s\nSuper method:\n%s\n%s\n%s", Arrays.copyOf(new Object[]{descriptor, signature, valueParameters, functionDescriptor, callableMethodMapToCallableMethod$default, valueParameters2}, 6));
                    Intrinsics.checkNotNullExpressionValue(str, "format(...)");
                    throw new AssertionError(sb.append(str).toString());
                }
                Iterator<JvmMethodParameterSignature> it = valueParameters2.iterator();
                Iterator<JvmMethodParameterSignature> it2 = valueParameters.iterator();
                int size = 0;
                while (it2.hasNext()) {
                    Type asmType = it2.next().getAsmType();
                    Intrinsics.checkNotNullExpressionValue(asmType, "getAsmType(...)");
                    StackValue.local(size, asmType).put(it.next().getAsmType(), instructionAdapter);
                    size += asmType.getSize();
                }
                Intrinsics.checkNotNull(instructionAdapter);
                callableMethodMapToCallableMethod$default.genInvokeInstruction(instructionAdapter);
                StackValue.coerce(callableMethodMapToCallableMethod$default.getReturnType(), signature.getReturnType(), instructionAdapter);
                instructionAdapter.areturn(signature.getReturnType());
            }
        });
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    /* JADX INFO: renamed from: generateKotlinMetadataAnnotation */
    protected void mo6421generateKotlinMetadataAnnotation() {
        ClassBuilder classBuilder = this.v;
        Intrinsics.checkNotNull(classBuilder, "null cannot be cast to non-null type org.jetbrains.kotlin.codegen.InterfaceImplBodyCodegen.InterfaceImplClassBuilder");
        ((InterfaceImplClassBuilder) classBuilder).stopCounting();
        ClassBuilder v = this.v;
        Intrinsics.checkNotNullExpressionValue(v, "v");
        GenerationState state = this.state;
        Intrinsics.checkNotNullExpressionValue(state, "state");
        WriteAnnotationUtilKt.writeSyntheticClassMetadata(v, state, false);
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    protected void done() {
        super.done();
        if (isAnythingGenerated()) {
            return;
        }
        this.state.getFactory().removeClasses(SetsKt.setOf(this.defaultImplType.getInternalName()));
    }

    /* JADX INFO: compiled from: InterfaceImplBodyCodegen.kt */
    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\n\u001a\u00020\u0003H\u0014JI\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\b\u0010\u0014\u001a\u0004\u0018\u00010\u00122\u0010\u0010\u0015\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0012\u0018\u00010\u0016H\u0016¢\u0006\u0002\u0010\u0017J\u0006\u0010\u0018\u001a\u00020\u0019R\u001e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/codegen/InterfaceImplBodyCodegen$InterfaceImplClassBuilder;", "Lorg/jetbrains/kotlin/codegen/DelegatingClassBuilder;", RegisterSpec.PREFIX, "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "(Lorg/jetbrains/kotlin/codegen/ClassBuilder;)V", "<set-?>", "", "isAnythingGenerated", "()Z", "shouldCount", "getDelegate", "newMethod", "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "origin", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;", "access", "", "name", "", "desc", "signature", "exceptions", "", "(Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "stopCounting", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class InterfaceImplClassBuilder extends DelegatingClassBuilder {
        private boolean isAnythingGenerated;
        private boolean shouldCount;
        private final ClassBuilder v;

        public InterfaceImplClassBuilder(ClassBuilder v) {
            Intrinsics.checkNotNullParameter(v, "v");
            this.v = v;
            this.shouldCount = true;
        }

        /* JADX INFO: renamed from: isAnythingGenerated, reason: from getter */
        public final boolean getIsAnythingGenerated() {
            return this.isAnythingGenerated;
        }

        public final void stopCounting() {
            this.shouldCount = false;
        }

        @Override // org.jetbrains.kotlin.codegen.DelegatingClassBuilder
        /* JADX INFO: renamed from: getDelegate, reason: from getter */
        protected ClassBuilder get_delegate() {
            return this.v;
        }

        @Override // org.jetbrains.kotlin.codegen.DelegatingClassBuilder, org.jetbrains.kotlin.codegen.ClassBuilder
        public MethodVisitor newMethod(JvmDeclarationOrigin origin, int access, String name2, String desc, String signature, String[] exceptions) {
            Intrinsics.checkNotNullParameter(origin, "origin");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(desc, "desc");
            if (this.shouldCount) {
                this.isAnythingGenerated = true;
            }
            MethodVisitor methodVisitorNewMethod = super.newMethod(origin, access, name2, desc, signature, exceptions);
            Intrinsics.checkNotNullExpressionValue(methodVisitorNewMethod, "newMethod(...)");
            return methodVisitorNewMethod;
        }
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    protected void generateSyntheticPartsBeforeBody() {
        generatePropertyMetadataArrayFieldIfNeeded(this.defaultImplType);
    }
}
