package org.jetbrains.kotlin.codegen.coroutines;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.CallableReferenceUtilKt;
import org.jetbrains.kotlin.codegen.ClassBuilder;
import org.jetbrains.kotlin.codegen.ClosureCodegen;
import org.jetbrains.kotlin.codegen.CodegenUtilKt;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.FieldInfo;
import org.jetbrains.kotlin.codegen.FunctionCodegen;
import org.jetbrains.kotlin.codegen.binding.CalculatedClosure;
import org.jetbrains.kotlin.codegen.context.ClosureContext;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.ValueParameterDescriptorImpl;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: CoroutineCodegen.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001BA\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u001a\b\u0002\u0010\n\u001a\u0014\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\f\u0012\u0002\b\u0003\u0018\u00010\u000b¢\u0006\u0002\u0010\rJ\b\u0010\u001e\u001a\u00020\u001fH\u0014R\u0014\u0010\u000e\u001a\u00020\u000fX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u0013X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u0017X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0012\u0010\u001a\u001a\u00020\u001bX¤\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR \u0010\n\u001a\u0014\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\f\u0012\u0002\b\u0003\u0018\u00010\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/codegen/coroutines/AbstractCoroutineCodegen;", "Lorg/jetbrains/kotlin/codegen/ClosureCodegen;", "outerExpressionCodegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "closureContext", "Lorg/jetbrains/kotlin/codegen/context/ClosureContext;", "classBuilder", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "userDataForInvokeSuspend", "", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor$UserDataKey;", "(Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/codegen/context/ClosureContext;Lorg/jetbrains/kotlin/codegen/ClassBuilder;Ljava/util/Map;)V", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getClassDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "methodToImplement", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getMethodToImplement", "()Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "passArityToSuperClass", "", "getPassArityToSuperClass", "()Z", "generateConstructor", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractCoroutineCodegen extends ClosureCodegen {
    private final ClassDescriptor classDescriptor;
    private final LanguageVersionSettings languageVersionSettings;
    private final FunctionDescriptor methodToImplement;
    private final Map<? extends CallableDescriptor.UserDataKey<?>, ?> userDataForInvokeSuspend;

    protected abstract boolean getPassArityToSuperClass();

    public /* synthetic */ AbstractCoroutineCodegen(ExpressionCodegen expressionCodegen, KtElement ktElement, ClosureContext closureContext, ClassBuilder classBuilder, Map map, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(expressionCodegen, ktElement, closureContext, classBuilder, (i & 16) != 0 ? null : map);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractCoroutineCodegen(ExpressionCodegen outerExpressionCodegen, KtElement element, ClosureContext closureContext, ClassBuilder classBuilder, Map<? extends CallableDescriptor.UserDataKey<?>, ?> map) {
        super(outerExpressionCodegen.getState(), element, null, closureContext, null, FailingFunctionGenerationStrategy.INSTANCE, outerExpressionCodegen.getParentCodegen(), classBuilder);
        Intrinsics.checkNotNullParameter(outerExpressionCodegen, "outerExpressionCodegen");
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(closureContext, "closureContext");
        Intrinsics.checkNotNullParameter(classBuilder, "classBuilder");
        this.userDataForInvokeSuspend = map;
        T contextDescriptor = closureContext.getContextDescriptor();
        Intrinsics.checkNotNullExpressionValue(contextDescriptor, "getContextDescriptor(...)");
        ClassDescriptor classDescriptor = (ClassDescriptor) contextDescriptor;
        this.classDescriptor = classDescriptor;
        this.languageVersionSettings = outerExpressionCodegen.getState().getLanguageVersionSettings();
        SimpleFunctionDescriptorImpl simpleFunctionDescriptorImplCreate = SimpleFunctionDescriptorImpl.create(classDescriptor, Annotations.INSTANCE.getEMPTY(), Name.identifier(CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME), CallableMemberDescriptor.Kind.DECLARATION, this.funDescriptor.getSourceElement());
        ReceiverParameterDescriptor thisAsReceiverParameter = classDescriptor.getThisAsReceiverParameter();
        List<ReceiverParameterDescriptor> listEmptyList = CollectionsKt.emptyList();
        List<? extends TypeParameterDescriptor> listEmptyList2 = CollectionsKt.emptyList();
        Intrinsics.checkNotNull(simpleFunctionDescriptorImplCreate);
        Annotations empty = Annotations.INSTANCE.getEMPTY();
        Name nameIdentifier = Name.identifier(CoroutineConstantsKt.SUSPEND_CALL_RESULT_NAME);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        ModuleDescriptor module = DescriptorUtilsKt.getModule(classDescriptor);
        SimpleType anyType = DescriptorUtilsKt.getBuiltIns(classDescriptor).getAnyType();
        Intrinsics.checkNotNullExpressionValue(anyType, "getAnyType(...)");
        SimpleType result = CoroutineCodegenUtilKt.getResult(module, anyType);
        SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
        Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
        simpleFunctionDescriptorImplCreate.initialize(null, thisAsReceiverParameter, listEmptyList, listEmptyList2, CollectionsKt.listOf(new ValueParameterDescriptorImpl(simpleFunctionDescriptorImplCreate, null, 0, empty, nameIdentifier, result, false, false, false, null, NO_SOURCE)), DescriptorUtilsKt.getBuiltIns(simpleFunctionDescriptorImplCreate).getNullableAnyType(), Modality.FINAL, DescriptorVisibilities.PUBLIC, map);
        Intrinsics.checkNotNullExpressionValue(simpleFunctionDescriptorImplCreate, "apply(...)");
        this.methodToImplement = simpleFunctionDescriptorImplCreate;
    }

    protected final ClassDescriptor getClassDescriptor() {
        return this.classDescriptor;
    }

    protected final LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    protected final FunctionDescriptor getMethodToImplement() {
        return this.methodToImplement;
    }

    @Override // org.jetbrains.kotlin.codegen.ClosureCodegen
    protected Method generateConstructor() {
        List<FieldInfo> listCalculateConstructorParameters = ClosureCodegen.calculateConstructorParameters(this.typeMapper, this.languageVersionSettings, this.closure, this.asmType);
        Intrinsics.checkNotNullExpressionValue(listCalculateConstructorParameters, "calculateConstructorParameters(...)");
        List<FieldInfo> list = listCalculateConstructorParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((FieldInfo) it.next()).getFieldType());
        }
        Type[] typeArr = (Type[]) CollectionsKt.plus((Collection<? extends Type>) arrayList, CoroutineCodegenUtilKt.CONTINUATION_ASM_TYPE).toArray(new Type[0]);
        Method method = new Method("<init>", Type.VOID_TYPE, typeArr);
        MethodVisitor methodVisitorNewMethod = this.v.newMethod(JvmDeclarationOriginKt.OtherOrigin((PsiElement) this.element, this.funDescriptor), this.visibilityFlag, "<init>", method.getDescriptor(), null, ArrayUtil.EMPTY_STRING_ARRAY);
        Intrinsics.checkNotNullExpressionValue(methodVisitorNewMethod, "newMethod(...)");
        if (this.state.getClassBuilderMode().generateBodies) {
            methodVisitorNewMethod.visitCode();
            InstructionAdapter instructionAdapter = new InstructionAdapter(methodVisitorNewMethod);
            CalculatedClosure closure = this.closure;
            Intrinsics.checkNotNullExpressionValue(closure, "closure");
            CallableReferenceUtilKt.generateClosureFieldsInitializationFromParameters(instructionAdapter, closure, listCalculateConstructorParameters);
            instructionAdapter.load(0, AsmTypes.OBJECT_TYPE);
            if (getPassArityToSuperClass()) {
                FunctionDescriptor funDescriptor = this.funDescriptor;
                Intrinsics.checkNotNullExpressionValue(funDescriptor, "funDescriptor");
                instructionAdapter.iconst(CodegenUtilKt.getArity(funDescriptor));
            }
            int size = 0;
            for (Type type : typeArr) {
                size += type.getSize();
            }
            instructionAdapter.load(size, AsmTypes.OBJECT_TYPE);
            List listListOf = getPassArityToSuperClass() ? CollectionsKt.listOf((Object[]) new Type[]{Type.INT_TYPE, CoroutineCodegenUtilKt.CONTINUATION_ASM_TYPE}) : CollectionsKt.listOf(CoroutineCodegenUtilKt.CONTINUATION_ASM_TYPE);
            Type type2 = Type.VOID_TYPE;
            Type[] typeArr2 = (Type[]) listListOf.toArray(new Type[0]);
            instructionAdapter.invokespecial(this.superClassAsmType.getInternalName(), "<init>", Type.getMethodDescriptor(type2, (Type[]) Arrays.copyOf(typeArr2, typeArr2.length)), false);
            instructionAdapter.visitInsn(177);
            FunctionCodegen.endVisit((MethodVisitor) instructionAdapter, JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, (KtElement) this.element);
        }
        this.v.newField(JvmDeclarationOrigin.NO_ORIGIN, 0, CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, "I", null, null);
        return method;
    }
}
