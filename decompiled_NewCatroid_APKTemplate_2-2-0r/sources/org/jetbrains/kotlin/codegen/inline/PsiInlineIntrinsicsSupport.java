package org.jetbrains.kotlin.codegen.inline;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMap;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.CallableReferenceUtilKt;
import org.jetbrains.kotlin.codegen.CodegenUtilKt;
import org.jetbrains.kotlin.codegen.DescriptorAsmUtil;
import org.jetbrains.kotlin.codegen.MemberCodegen;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ErrorsJvm;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.tree.FieldInsnNode;

/* JADX INFO: compiled from: PsiInlineIntrinsicsSupport.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0012\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\u0002H\u0016J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0018\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\f\u001a\u00020\u0002H\u0016J\u0018\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\u0002H\u0016J\u0010\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u000eH\u0016J\u0010\u0010\u001d\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\u0002H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/PsiInlineIntrinsicsSupport;", "Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeInliner$IntrinsicsSupport;", "Lorg/jetbrains/kotlin/types/KotlinType;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "reportErrorsOn", "Lorg/jetbrains/kotlin/psi/KtElement;", "(Lorg/jetbrains/kotlin/codegen/state/GenerationState;Lorg/jetbrains/kotlin/psi/KtElement;)V", "getState", "()Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "generateExternalEntriesForEnumTypeIfNeeded", "Lorg/jetbrains/org/objectweb/asm/tree/FieldInsnNode;", ModuleXmlParser.TYPE, "generateFunctionReference", "", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "generateTypeParameterContainer", "typeParameter", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "isMutableCollectionType", "", "putClassInstance", "reportNonReifiedTypeParameterWithRecursiveBoundUnsupported", "typeParameterName", "Lorg/jetbrains/kotlin/name/Name;", "reportSuspendTypeUnsupported", "toKotlinType", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PsiInlineIntrinsicsSupport implements ReifiedTypeInliner.IntrinsicsSupport<KotlinType> {
    private final KtElement reportErrorsOn;
    private final GenerationState state;

    @Override // org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner.IntrinsicsSupport
    public KotlinType toKotlinType(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return type;
    }

    public PsiInlineIntrinsicsSupport(GenerationState state, KtElement reportErrorsOn) {
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(reportErrorsOn, "reportErrorsOn");
        this.state = state;
        this.reportErrorsOn = reportErrorsOn;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner.IntrinsicsSupport
    public GenerationState getState() {
        return this.state;
    }

    @Override // org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner.IntrinsicsSupport
    public void putClassInstance(InstructionAdapter v, KotlinType type) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(type, "type");
        DescriptorAsmUtil.putJavaLangClassInstance(v, KotlinTypeMapper.mapType$default(getState().getTypeMapper(), type, null, null, 6, null), type, getState().getTypeMapper());
    }

    @Override // org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner.IntrinsicsSupport
    public void generateTypeParameterContainer(InstructionAdapter v, TypeParameterMarker typeParameter) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        if (!(typeParameter instanceof TypeParameterDescriptor)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) typeParameter;
        DeclarationDescriptor containingDeclaration = typeParameterDescriptor.getContainingModule();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        if (containingDeclaration instanceof ClassDescriptor) {
            SimpleType defaultType = ((ClassDescriptor) containingDeclaration).getDefaultType();
            Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
            putClassInstance(v, (KotlinType) defaultType);
            Unit unit = Unit.INSTANCE;
            AsmUtil.wrapJavaClassIntoKClass(v);
            return;
        }
        if (containingDeclaration instanceof FunctionDescriptor) {
            generateFunctionReference(v, (FunctionDescriptor) containingDeclaration);
        } else {
            if (!(containingDeclaration instanceof PropertyDescriptor)) {
                throw new IllegalStateException(("Unknown container of type parameter: " + containingDeclaration + " (" + typeParameterDescriptor.getShortName() + ')').toString());
            }
            MemberCodegen.generatePropertyReference(v, (VariableDescriptorWithAccessors) containingDeclaration, getState());
        }
    }

    private final void generateFunctionReference(InstructionAdapter v, FunctionDescriptor descriptor) {
        if (!getState().getConfig().getGenerateOptimizedCallableReferenceSuperClasses()) {
            throw new IllegalStateException(("typeOf() of a non-reified type parameter is only allowed if optimized callable references are enabled.\nPlease make sure API version is set to 1.4, and -Xno-optimized-callable-references is NOT used.\nContainer: " + descriptor).toString());
        }
        v.anew(AsmTypes.FUNCTION_REFERENCE_IMPL);
        v.dup();
        FunctionDescriptor functionDescriptor = descriptor;
        v.aconst(Integer.valueOf(CodegenUtilKt.getArity(functionDescriptor)));
        CallableReferenceUtilKt.generateCallableReferenceDeclarationContainerClass(v, functionDescriptor, getState());
        v.aconst(descriptor.getShortName().asString());
        CallableReferenceUtilKt.generateFunctionReferenceSignature(v, functionDescriptor, getState());
        v.aconst(Integer.valueOf(CallableReferenceUtilKt.getCallableReferenceTopLevelFlag(functionDescriptor)));
        v.invokespecial(AsmTypes.FUNCTION_REFERENCE_IMPL.getInternalName(), "<init>", Type.getMethodDescriptor(Type.VOID_TYPE, Type.INT_TYPE, AsmTypes.JAVA_CLASS_TYPE, AsmTypes.JAVA_STRING_TYPE, AsmTypes.JAVA_STRING_TYPE, Type.INT_TYPE), false);
    }

    @Override // org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner.IntrinsicsSupport
    public boolean isMutableCollectionType(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        ClassifierDescriptor this$0 = type.getConstructor().getThis$0();
        return (this$0 instanceof ClassDescriptor) && JavaToKotlinClassMap.INSTANCE.isMutable(DescriptorUtilsKt.getFqNameUnsafe(this$0));
    }

    @Override // org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner.IntrinsicsSupport
    public FieldInsnNode generateExternalEntriesForEnumTypeIfNeeded(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        throw new IllegalStateException("Not supported in the old JVM backend".toString());
    }

    @Override // org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner.IntrinsicsSupport
    public void reportSuspendTypeUnsupported() {
        getState().getDiagnostics().report(ErrorsJvm.TYPEOF_SUSPEND_TYPE.on(this.reportErrorsOn));
    }

    @Override // org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner.IntrinsicsSupport
    public void reportNonReifiedTypeParameterWithRecursiveBoundUnsupported(Name typeParameterName) {
        Intrinsics.checkNotNullParameter(typeParameterName, "typeParameterName");
        getState().getDiagnostics().report(ErrorsJvm.TYPEOF_NON_REIFIED_TYPE_PARAMETER_WITH_RECURSIVE_BOUND.on(this.reportErrorsOn, typeParameterName.asString()));
    }
}
