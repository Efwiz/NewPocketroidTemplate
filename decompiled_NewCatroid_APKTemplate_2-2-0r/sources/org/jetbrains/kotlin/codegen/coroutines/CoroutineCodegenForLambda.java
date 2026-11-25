package org.jetbrains.kotlin.codegen.coroutines;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CodegenUtil;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.ClassBuilder;
import org.jetbrains.kotlin.codegen.ClosureCodegen;
import org.jetbrains.kotlin.codegen.CodegenUtilKt;
import org.jetbrains.kotlin.codegen.DescriptorAsmUtil;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.FieldInfo;
import org.jetbrains.kotlin.codegen.FrameMap;
import org.jetbrains.kotlin.codegen.FunctionCodegen;
import org.jetbrains.kotlin.codegen.FunctionGenerationStrategy;
import org.jetbrains.kotlin.codegen.JvmCodegenUtil;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.VariableAsmNameManglingUtils;
import org.jetbrains.kotlin.codegen.binding.CodegenBinding;
import org.jetbrains.kotlin.codegen.context.ClosureContext;
import org.jetbrains.kotlin.codegen.context.MethodContext;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.codegen.serialization.JvmSerializationBindings;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorUtilKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.AnonymousFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.impl.SimpleFunctionDescriptorImpl;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtDeclarationWithBody;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtPureElement;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.KtDescriptorUtilKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKt;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterSignature;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: CoroutineCodegen.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0098\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 G2\u00020\u0001:\u0001GB7\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ\u000e\u0010-\u001a\b\u0012\u0004\u0012\u00020\"0.H\u0002J\u0014\u0010/\u001a\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020#0!H\u0002J\u0018\u00100\u001a\u00020#2\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u000204H\u0002J\b\u00105\u001a\u000206H\u0014J\b\u00107\u001a\u000206H\u0014J\b\u00108\u001a\u000206H\u0014J\b\u00109\u001a\u00020\u0012H\u0014J\u0010\u0010:\u001a\u0002062\u0006\u0010;\u001a\u00020\u0003H\u0002J\b\u0010<\u001a\u000206H\u0002J\b\u0010=\u001a\u000206H\u0002J\b\u0010>\u001a\u00020\u0014H\u0002J\b\u0010?\u001a\u00020\u0014H\u0002J\u001c\u0010@\u001a\u000206*\u00020A2\u0006\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020\u000bH\u0002J\f\u0010E\u001a\u000206*\u00020\u0003H\u0002J\f\u0010F\u001a\u00020\r*\u00020\"H\u0002R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\u0013\u001a\u00020\u00148BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0019\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u001c\u001a\u00020\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001f\u0010\u0018\u001a\u0004\b\u001d\u0010\u001eR\u001a\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020#0!X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010%\u001a\u00020\r8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b&\u0010'R*\u0010(\u001a\u001e\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020+0)j\u000e\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020+`,X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006H"}, d2 = {"Lorg/jetbrains/kotlin/codegen/coroutines/CoroutineCodegenForLambda;", "Lorg/jetbrains/kotlin/codegen/coroutines/AbstractCoroutineCodegen;", "outerExpressionCodegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "closureContext", "Lorg/jetbrains/kotlin/codegen/context/ClosureContext;", "classBuilder", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "originalSuspendFunctionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "forInline", "", "(Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/codegen/context/ClosureContext;Lorg/jetbrains/kotlin/codegen/ClassBuilder;Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;Z)V", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "constructorToUseFromInvoke", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "createCoroutineDescriptor", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "getCreateCoroutineDescriptor", "()Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "createCoroutineDescriptor$delegate", "Lkotlin/Lazy;", "doNotGenerateInvokeBridge", "endLabel", "Lorg/jetbrains/org/objectweb/asm/Label;", "erasedInvokeFunction", "getErasedInvokeFunction", "()Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "erasedInvokeFunction$delegate", "fieldsForParameters", "", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "Lorg/jetbrains/kotlin/codegen/FieldInfo;", "generateErasedCreate", "passArityToSuperClass", "getPassArityToSuperClass", "()Z", "varsCountByType", "Ljava/util/HashMap;", "Lorg/jetbrains/org/objectweb/asm/Type;", "", "Lkotlin/collections/HashMap;", "allFunctionParameters", "", "createFieldsForParameters", "createHiddenFieldInfo", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "name", "", "generateBody", "", "generateBridges", "generateClosureBody", "generateConstructor", "generateCreateCoroutineMethod", "codegen", "generateResumeImpl", "generateUntypedInvokeMethod", "getCreateFunction", "getErasedCreateFunction", "generateInvokeMethod", "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "signature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "descriptor", "initializeCoroutineParameters", "isUnused", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CoroutineCodegenForLambda extends AbstractCoroutineCodegen {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final KotlinBuiltIns builtIns;
    private final ClosureContext closureContext;
    private Method constructorToUseFromInvoke;

    /* JADX INFO: renamed from: createCoroutineDescriptor$delegate, reason: from kotlin metadata */
    private final Lazy createCoroutineDescriptor;
    private final boolean doNotGenerateInvokeBridge;
    private final Label endLabel;

    /* JADX INFO: renamed from: erasedInvokeFunction$delegate, reason: from kotlin metadata */
    private final Lazy erasedInvokeFunction;
    private final Map<ParameterDescriptor, FieldInfo> fieldsForParameters;
    private final boolean forInline;
    private final boolean generateErasedCreate;
    private final FunctionDescriptor originalSuspendFunctionDescriptor;
    private final HashMap<Type, Integer> varsCountByType;

    public /* synthetic */ CoroutineCodegenForLambda(ExpressionCodegen expressionCodegen, KtElement ktElement, ClosureContext closureContext, ClassBuilder classBuilder, FunctionDescriptor functionDescriptor, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(expressionCodegen, ktElement, closureContext, classBuilder, functionDescriptor, z);
    }

    @JvmStatic
    public static final ClosureCodegen create(ExpressionCodegen expressionCodegen, FunctionDescriptor functionDescriptor, KtElement ktElement, ClassBuilder classBuilder) {
        return INSTANCE.create(expressionCodegen, functionDescriptor, ktElement, classBuilder);
    }

    @Override // org.jetbrains.kotlin.codegen.coroutines.AbstractCoroutineCodegen
    protected boolean getPassArityToSuperClass() {
        return true;
    }

    private CoroutineCodegenForLambda(ExpressionCodegen expressionCodegen, KtElement ktElement, ClosureContext closureContext, ClassBuilder classBuilder, FunctionDescriptor functionDescriptor, boolean z) {
        super(expressionCodegen, ktElement, closureContext, classBuilder, MapsKt.mapOf(TuplesKt.to(CoroutineCodegenUtilKt.INITIAL_SUSPEND_DESCRIPTOR_FOR_INVOKE_SUSPEND, functionDescriptor)));
        this.closureContext = closureContext;
        this.originalSuspendFunctionDescriptor = functionDescriptor;
        this.forInline = z;
        FunctionDescriptor funDescriptor = this.funDescriptor;
        Intrinsics.checkNotNullExpressionValue(funDescriptor, "funDescriptor");
        this.builtIns = DescriptorUtilsKt.getBuiltIns(funDescriptor);
        this.erasedInvokeFunction = LazyKt.lazy(new Function0<FunctionDescriptor>() { // from class: org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenForLambda$erasedInvokeFunction$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final FunctionDescriptor invoke() {
                return CoroutineCodegenUtilKt.createCustomCopy(ClosureCodegen.getErasedInvokeFunction(this.this$0.funDescriptor), new Function2<FunctionDescriptor.CopyBuilder<? extends FunctionDescriptor>, FunctionDescriptor, FunctionDescriptor.CopyBuilder<? extends FunctionDescriptor>>() { // from class: org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenForLambda$erasedInvokeFunction$2.1
                    @Override // kotlin.jvm.functions.Function2
                    public final FunctionDescriptor.CopyBuilder<? extends FunctionDescriptor> invoke(FunctionDescriptor.CopyBuilder<? extends FunctionDescriptor> createCustomCopy, FunctionDescriptor it) {
                        Intrinsics.checkNotNullParameter(createCustomCopy, "$this$createCustomCopy");
                        Intrinsics.checkNotNullParameter(it, "it");
                        FunctionDescriptor.CopyBuilder modality = createCustomCopy.setModality(Modality.FINAL);
                        Intrinsics.checkNotNullExpressionValue(modality, "setModality(...)");
                        return modality;
                    }
                });
            }
        });
        this.createCoroutineDescriptor = LazyKt.lazy(new Function0<SimpleFunctionDescriptor>() { // from class: org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenForLambda$createCoroutineDescriptor$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final SimpleFunctionDescriptor invoke() {
                return this.this$0.generateErasedCreate ? this.this$0.getErasedCreateFunction() : this.this$0.getCreateFunction();
            }
        });
        this.endLabel = new Label();
        this.varsCountByType = new HashMap<>();
        this.fieldsForParameters = createFieldsForParameters();
        this.generateErasedCreate = allFunctionParameters().size() <= 1;
        this.doNotGenerateInvokeBridge = !CoroutineCodegenUtilKt.isLocalSuspendFunctionNotSuspendLambda(functionDescriptor);
    }

    private final FunctionDescriptor getErasedInvokeFunction() {
        return (FunctionDescriptor) this.erasedInvokeFunction.getValue();
    }

    private final SimpleFunctionDescriptor getCreateCoroutineDescriptor() {
        return (SimpleFunctionDescriptor) this.createCoroutineDescriptor.getValue();
    }

    private final Map<ParameterDescriptor, FieldInfo> createFieldsForParameters() {
        HashMap map = new HashMap();
        for (ParameterDescriptor parameterDescriptor : allFunctionParameters()) {
            if (!isUnused(parameterDescriptor)) {
                KotlinTypeMapper typeMapper = this.state.getTypeMapper();
                KotlinType type = parameterDescriptor.getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                Type typeNormalize = CoroutineTransformerMethodVisitorKt.normalize(KotlinTypeMapper.mapType$default(typeMapper, type, null, null, 6, null));
                Integer num = this.varsCountByType.get(typeNormalize);
                int iIntValue = num != null ? num.intValue() + 1 : 0;
                this.varsCountByType.put(typeNormalize, Integer.valueOf(iIntValue));
                KotlinType type2 = parameterDescriptor.getType();
                Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                map.put(parameterDescriptor, createHiddenFieldInfo(type2, new StringBuilder().append(typeNormalize.getDescriptor().charAt(0)).append('$').append(iIntValue).toString()));
            }
        }
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final SimpleFunctionDescriptor getCreateFunction() {
        SimpleFunctionDescriptorImpl simpleFunctionDescriptorImplCreate = SimpleFunctionDescriptorImpl.create(this.funDescriptor.getContainingModule(), Annotations.INSTANCE.getEMPTY(), Name.identifier(CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME), this.funDescriptor.getKind(), this.funDescriptor.getSourceElement());
        ReceiverParameterDescriptor extensionReceiverParameter = this.funDescriptor.getExtensionReceiverParameter();
        ReceiverParameterDescriptor receiverParameterDescriptorMo7203copy = extensionReceiverParameter != null ? extensionReceiverParameter.mo7203copy(simpleFunctionDescriptorImplCreate) : null;
        ReceiverParameterDescriptor dispatchReceiverParameter = this.funDescriptor.mo6753getDispatchReceiverParameter();
        List<ReceiverParameterDescriptor> contextReceiverParameters = this.funDescriptor.getContextReceiverParameters();
        Intrinsics.checkNotNullExpressionValue(contextReceiverParameters, "getContextReceiverParameters(...)");
        List<ReceiverParameterDescriptor> list = contextReceiverParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((ReceiverParameterDescriptor) it.next()).mo7203copy(simpleFunctionDescriptorImplCreate));
        }
        ArrayList arrayList2 = arrayList;
        List<TypeParameterDescriptor> typeParameters = this.funDescriptor.getTypeParameters();
        List<ValueParameterDescriptor> valueParameters = this.funDescriptor.getValueParameters();
        FunctionDescriptor funDescriptor = this.funDescriptor;
        Intrinsics.checkNotNullExpressionValue(funDescriptor, "funDescriptor");
        ModuleDescriptor module = DescriptorUtilsKt.getModule(funDescriptor);
        SimpleType unitType = this.builtIns.getUnitType();
        Intrinsics.checkNotNullExpressionValue(unitType, "getUnitType(...)");
        simpleFunctionDescriptorImplCreate.initialize(receiverParameterDescriptorMo7203copy, dispatchReceiverParameter, (List<ReceiverParameterDescriptor>) arrayList2, (List<? extends TypeParameterDescriptor>) typeParameters, valueParameters, (KotlinType) DescriptorUtilKt.getContinuationOfTypeOrAny(module, unitType), this.funDescriptor.getModality(), DescriptorVisibilities.PUBLIC);
        Intrinsics.checkNotNullExpressionValue(simpleFunctionDescriptorImplCreate, "also(...)");
        return simpleFunctionDescriptorImplCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final SimpleFunctionDescriptor getErasedCreateFunction() {
        SimpleFunctionDescriptor createFunction = getCreateFunction();
        KotlinTypeMapper typeMapper = this.typeMapper;
        Intrinsics.checkNotNullExpressionValue(typeMapper, "typeMapper");
        Object obj = null;
        int length = KotlinTypeMapper.mapSignatureSkipGeneric$default(typeMapper, createFunction, null, 2, null).getAsmMethod().getArgumentTypes().length;
        ModuleDescriptor module = DescriptorUtilsKt.getModule(createFunction);
        FqName fqNameChild = StandardNames.COROUTINES_JVM_INTERNAL_PACKAGE_FQ_NAME.child(Name.identifier("BaseContinuationImpl"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        ClassDescriptor classDescriptorResolveClassByFqName = DescriptorUtilKt.resolveClassByFqName(module, fqNameChild, NoLookupLocation.FROM_BACKEND);
        if (classDescriptorResolveClassByFqName != null) {
            MemberScope memberScope = classDescriptorResolveClassByFqName.getDefaultType().getMemberScope();
            Name name2 = createFunction.getShortName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            for (Object obj2 : memberScope.getContributedFunctions(name2, NoLookupLocation.FROM_BACKEND)) {
                if (((SimpleFunctionDescriptor) obj2).getValueParameters().size() == length) {
                    obj = obj2;
                    break;
                }
            }
            if (obj != null) {
                return (SimpleFunctionDescriptor) CoroutineCodegenUtilKt.createCustomCopy((FunctionDescriptor) obj, new Function2<FunctionDescriptor.CopyBuilder<? extends FunctionDescriptor>, FunctionDescriptor, FunctionDescriptor.CopyBuilder<? extends FunctionDescriptor>>() { // from class: org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenForLambda.getErasedCreateFunction.6
                    @Override // kotlin.jvm.functions.Function2
                    public final FunctionDescriptor.CopyBuilder<? extends FunctionDescriptor> invoke(FunctionDescriptor.CopyBuilder<? extends FunctionDescriptor> createCustomCopy, FunctionDescriptor it) {
                        Intrinsics.checkNotNullParameter(createCustomCopy, "$this$createCustomCopy");
                        Intrinsics.checkNotNullParameter(it, "it");
                        FunctionDescriptor.CopyBuilder modality = createCustomCopy.setModality(Modality.FINAL);
                        Intrinsics.checkNotNullExpressionValue(modality, "setModality(...)");
                        return modality;
                    }
                });
            }
            throw new AssertionError("erased parent of " + createFunction + " is not found");
        }
        throw new AssertionError("BaseContinuationImpl is not found");
    }

    @Override // org.jetbrains.kotlin.codegen.ClosureCodegen
    protected void generateClosureBody() {
        for (ParameterDescriptor parameterDescriptor : allFunctionParameters()) {
            FieldInfo fieldInfo = this.fieldsForParameters.get(parameterDescriptor);
            if (fieldInfo != null) {
                this.v.newField(JvmDeclarationOriginKt.OtherOrigin(parameterDescriptor), 4098, fieldInfo.getFieldName(), fieldInfo.getFieldType().getDescriptor(), null, null);
            }
        }
        generateResumeImpl();
    }

    private final boolean isUnused(ParameterDescriptor parameterDescriptor) {
        return (this.originalSuspendFunctionDescriptor instanceof AnonymousFunctionDescriptor) && !Intrinsics.areEqual(this.bindingContext.get(BindingContext.SUSPEND_LAMBDA_PARAMETER_USED, TuplesKt.to(this.originalSuspendFunctionDescriptor, Integer.valueOf(KtDescriptorUtilKt.indexOrMinusOne(parameterDescriptor)))), (Object) true);
    }

    @Override // org.jetbrains.kotlin.codegen.ClosureCodegen, org.jetbrains.kotlin.codegen.MemberCodegen
    /* JADX INFO: renamed from: generateBody */
    protected void mo6419generateBody() {
        super.mo6419generateBody();
        if (this.doNotGenerateInvokeBridge) {
            JvmSerializationBindings serializationBindings = this.v.getSerializationBindings();
            JvmSerializationBindings.SerializationMappingSlice<FunctionDescriptor, Method> serializationMappingSlice = JvmSerializationBindings.METHOD_FOR_FUNCTION;
            FunctionDescriptor functionDescriptor = this.originalSuspendFunctionDescriptor;
            KotlinTypeMapper kotlinTypeMapper = this.typeMapper;
            FunctionDescriptor erasedInvokeFunction = getErasedInvokeFunction();
            Intrinsics.checkNotNullExpressionValue(erasedInvokeFunction, "<get-erasedInvokeFunction>(...)");
            serializationBindings.put(serializationMappingSlice, functionDescriptor, kotlinTypeMapper.mapAsmMethod(erasedInvokeFunction));
        }
        this.functionCodegen.generateMethod(JvmDeclarationOrigin.NO_ORIGIN, getCreateCoroutineDescriptor(), new FunctionGenerationStrategy.CodegenBased(this.state) { // from class: org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenForLambda.generateBody.1
            @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy.CodegenBased
            public void doGenerateBody(ExpressionCodegen codegen, JvmMethodSignature signature) {
                Intrinsics.checkNotNullParameter(codegen, "codegen");
                Intrinsics.checkNotNullParameter(signature, "signature");
                CoroutineCodegenForLambda.this.generateCreateCoroutineMethod(codegen);
            }
        });
        if (this.doNotGenerateInvokeBridge) {
            generateUntypedInvokeMethod();
        } else {
            this.functionCodegen.generateMethod(JvmDeclarationOrigin.NO_ORIGIN, this.funDescriptor, new FunctionGenerationStrategy.CodegenBased(this.state) { // from class: org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenForLambda.generateBody.2
                @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy.CodegenBased
                public void doGenerateBody(ExpressionCodegen codegen, JvmMethodSignature signature) {
                    Intrinsics.checkNotNullParameter(codegen, "codegen");
                    Intrinsics.checkNotNullParameter(signature, "signature");
                    CoroutineCodegenForLambda coroutineCodegenForLambda = CoroutineCodegenForLambda.this;
                    InstructionAdapter v = codegen.v;
                    Intrinsics.checkNotNullExpressionValue(v, "v");
                    FunctionDescriptor functionDescriptor2 = CoroutineCodegenForLambda.this.funDescriptor;
                    Intrinsics.checkNotNullExpressionValue(functionDescriptor2, "access$getFunDescriptor$p$s1681327557(...)");
                    coroutineCodegenForLambda.generateInvokeMethod(v, signature, functionDescriptor2);
                }
            });
        }
    }

    @Override // org.jetbrains.kotlin.codegen.ClosureCodegen
    protected void generateBridges() {
        if (this.doNotGenerateInvokeBridge) {
            return;
        }
        super.generateBridges();
    }

    private final void generateUntypedInvokeMethod() {
        FunctionDescriptor erasedInvokeFunction = ClosureCodegen.getErasedInvokeFunction(this.funDescriptor);
        Intrinsics.checkNotNullExpressionValue(erasedInvokeFunction, "getErasedInvokeFunction(...)");
        Method methodMapAsmMethod = this.typeMapper.mapAsmMethod(erasedInvokeFunction);
        KotlinTypeMapper typeMapper = this.typeMapper;
        Intrinsics.checkNotNullExpressionValue(typeMapper, "typeMapper");
        JvmMethodSignature jvmMethodSignatureMapSignatureSkipGeneric$default = KotlinTypeMapper.mapSignatureSkipGeneric$default(typeMapper, erasedInvokeFunction, null, 2, null);
        MethodVisitor methodVisitorNewMethod = this.v.newMethod(JvmDeclarationOriginKt.OtherOrigin((PsiElement) this.element, this.funDescriptor), DescriptorAsmUtil.getVisibilityAccessFlag(erasedInvokeFunction) | 16, methodMapAsmMethod.getName(), methodMapAsmMethod.getDescriptor(), null, ArrayUtil.EMPTY_STRING_ARRAY);
        Intrinsics.checkNotNullExpressionValue(methodVisitorNewMethod, "newMethod(...)");
        methodVisitorNewMethod.visitCode();
        generateInvokeMethod(new InstructionAdapter(methodVisitorNewMethod), jvmMethodSignatureMapSignatureSkipGeneric$default, erasedInvokeFunction);
        FunctionCodegen.endVisit(methodVisitorNewMethod, "invoke", (KtElement) this.element);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void generateInvokeMethod(InstructionAdapter instructionAdapter, JvmMethodSignature jvmMethodSignature, FunctionDescriptor functionDescriptor) {
        List listAsList;
        ArrayList arrayListAllValueParameterTypes;
        instructionAdapter.load(0, AsmTypes.OBJECT_TYPE);
        List<JvmMethodParameterSignature> valueParameters = jvmMethodSignature.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<JvmMethodParameterSignature> list = valueParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((JvmMethodParameterSignature) it.next()).getAsmType());
        }
        ArrayList arrayList2 = arrayList;
        if (this.generateErasedCreate || this.doNotGenerateInvokeBridge) {
            Type[] argumentTypes = this.typeMapper.mapAsmMethod(getCreateCoroutineDescriptor()).getArgumentTypes();
            Intrinsics.checkNotNullExpressionValue(argumentTypes, "getArgumentTypes(...)");
            listAsList = ArraysKt.asList(argumentTypes);
        } else {
            listAsList = arrayList2;
        }
        if (arrayList2.size() == 22 && listAsList.size() == 1) {
            instructionAdapter.iconst(22);
            instructionAdapter.newarray(AsmTypes.OBJECT_TYPE);
            instructionAdapter.store(23, AsmTypes.OBJECT_TYPE);
            for (Pair<Integer, Type> pair : CodegenUtilKt.withVariableIndices(arrayList2)) {
                int iIntValue = pair.component1().intValue();
                Type typeComponent2 = pair.component2();
                instructionAdapter.load(23, AsmTypes.OBJECT_TYPE);
                instructionAdapter.iconst(iIntValue);
                instructionAdapter.load(iIntValue + 1, typeComponent2);
                StackValue.coerce(typeComponent2, AsmTypes.OBJECT_TYPE, instructionAdapter);
                instructionAdapter.astore(AsmTypes.OBJECT_TYPE);
            }
            instructionAdapter.load(23, AsmTypes.OBJECT_TYPE);
        } else {
            if (!this.generateErasedCreate && this.doNotGenerateInvokeBridge) {
                FunctionDescriptor funDescriptor = this.funDescriptor;
                Intrinsics.checkNotNullExpressionValue(funDescriptor, "funDescriptor");
                arrayListAllValueParameterTypes = CoroutineCodegenKt.allValueParameterTypes(funDescriptor);
            } else {
                FunctionDescriptor funDescriptor2 = this.funDescriptor;
                Intrinsics.checkNotNullExpressionValue(funDescriptor2, "funDescriptor");
                List<KotlinType> listAllValueParameterTypes = CoroutineCodegenKt.allValueParameterTypes(funDescriptor2);
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listAllValueParameterTypes, 10));
                for (KotlinType kotlinType : listAllValueParameterTypes) {
                    FunctionDescriptor funDescriptor3 = this.funDescriptor;
                    Intrinsics.checkNotNullExpressionValue(funDescriptor3, "funDescriptor");
                    arrayList3.add(DescriptorUtilsKt.getModule(funDescriptor3).getBuiltIns().getNullableAnyType());
                }
                arrayListAllValueParameterTypes = arrayList3;
            }
            List listAllValueParameterTypes2 = (this.generateErasedCreate || !this.doNotGenerateInvokeBridge) ? CoroutineCodegenKt.allValueParameterTypes(functionDescriptor) : CoroutineCodegenKt.allValueParameterTypes(getCreateCoroutineDescriptor());
            Iterator<T> it2 = CodegenUtilKt.withVariableIndices(arrayList2).iterator();
            int i = 0;
            while (it2.hasNext()) {
                Pair pair2 = (Pair) it2.next();
                int iIntValue2 = ((Number) pair2.component1()).intValue();
                Type type = (Type) pair2.component2();
                instructionAdapter.load(iIntValue2 + 1, type);
                StackValue.coerce(type, (KotlinType) arrayListAllValueParameterTypes.get(i), (Type) listAsList.get(i), (KotlinType) listAllValueParameterTypes2.get(i), instructionAdapter);
                i++;
            }
        }
        String thisName = this.v.getThisName();
        String strMapFunctionName = this.typeMapper.mapFunctionName(getCreateCoroutineDescriptor(), null);
        Type type2 = CoroutineCodegenUtilKt.CONTINUATION_ASM_TYPE;
        Type[] typeArr = (Type[]) listAsList.toArray(new Type[0]);
        instructionAdapter.invokevirtual(thisName, strMapFunctionName, Type.getMethodDescriptor(type2, (Type[]) Arrays.copyOf(typeArr, typeArr.length)), false);
        instructionAdapter.checkcast(Type.getObjectType(this.v.getThisName()));
        String thisName2 = this.v.getThisName();
        Intrinsics.checkNotNullExpressionValue(thisName2, "getThisName(...)");
        CoroutineCodegenUtilKt.invokeInvokeSuspendWithUnit(instructionAdapter, thisName2);
        instructionAdapter.areturn(AsmTypes.OBJECT_TYPE);
    }

    @Override // org.jetbrains.kotlin.codegen.coroutines.AbstractCoroutineCodegen, org.jetbrains.kotlin.codegen.ClosureCodegen
    protected Method generateConstructor() {
        Method methodGenerateConstructor = super.generateConstructor();
        this.constructorToUseFromInvoke = methodGenerateConstructor;
        if (methodGenerateConstructor != null) {
            return methodGenerateConstructor;
        }
        Intrinsics.throwUninitializedPropertyAccessException("constructorToUseFromInvoke");
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void generateCreateCoroutineMethod(ExpressionCodegen codegen) {
        int size;
        T contextDescriptor = this.closureContext.getContextDescriptor();
        Intrinsics.checkNotNullExpressionValue(contextDescriptor, "getContextDescriptor(...)");
        ClassDescriptor classDescriptor = (ClassDescriptor) contextDescriptor;
        Type typeMapClass = this.typeMapper.mapClass(classDescriptor);
        StackValue stackValueThisOrOuter = StackValue.thisOrOuter(codegen, classDescriptor, false, false);
        Intrinsics.checkNotNullExpressionValue(stackValueThisOrOuter, "thisOrOuter(...)");
        boolean zIsDeclarationOfBigArityCreateCoroutineMethod = JvmCodegenUtil.isDeclarationOfBigArityCreateCoroutineMethod(getCreateCoroutineDescriptor());
        InstructionAdapter instructionAdapter = codegen.v;
        instructionAdapter.anew(typeMapClass);
        instructionAdapter.dup();
        List<FieldInfo> listCalculateConstructorParameters = ClosureCodegen.calculateConstructorParameters(this.typeMapper, getLanguageVersionSettings(), this.closure, typeMapClass);
        Intrinsics.checkNotNullExpressionValue(listCalculateConstructorParameters, "calculateConstructorParameters(...)");
        for (FieldInfo fieldInfo : listCalculateConstructorParameters) {
            StackValue.field(fieldInfo, stackValueThisOrOuter).put(fieldInfo.getFieldType(), fieldInfo.getFieldKotlinType(), instructionAdapter);
        }
        if (zIsDeclarationOfBigArityCreateCoroutineMethod) {
            instructionAdapter.load(1, AsmTypes.OBJECT_TYPE);
            instructionAdapter.iconst(allFunctionParameters().size());
            instructionAdapter.aload(AsmTypes.OBJECT_TYPE);
        } else if (this.generateErasedCreate) {
            instructionAdapter.load(allFunctionParameters().size() + 1, AsmTypes.OBJECT_TYPE);
        } else {
            int size2 = 0;
            for (ParameterDescriptor parameterDescriptor : allFunctionParameters()) {
                KotlinTypeMapper typeMapper = this.typeMapper;
                Intrinsics.checkNotNullExpressionValue(typeMapper, "typeMapper");
                KotlinType type = parameterDescriptor.getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                size2 += KotlinTypeMapper.mapType$default(typeMapper, type, null, null, 6, null).getSize();
            }
            instructionAdapter.load(size2 + 1, AsmTypes.OBJECT_TYPE);
        }
        String internalName = typeMapClass.getInternalName();
        Method method = this.constructorToUseFromInvoke;
        Method method2 = null;
        if (method == null) {
            Intrinsics.throwUninitializedPropertyAccessException("constructorToUseFromInvoke");
            method = null;
        }
        String name2 = method.getName();
        Method method3 = this.constructorToUseFromInvoke;
        if (method3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("constructorToUseFromInvoke");
        } else {
            method2 = method3;
        }
        instructionAdapter.invokespecial(internalName, name2, method2.getDescriptor(), false);
        FrameMap frameMap = codegen.getFrameMap();
        Type OBJECT_TYPE = AsmTypes.OBJECT_TYPE;
        Intrinsics.checkNotNullExpressionValue(OBJECT_TYPE, "OBJECT_TYPE");
        int iEnterTemp = frameMap.enterTemp(OBJECT_TYPE);
        instructionAdapter.store(iEnterTemp, AsmTypes.OBJECT_TYPE);
        int i = 1;
        for (ParameterDescriptor parameterDescriptor2 : allFunctionParameters()) {
            FieldInfo fieldInfo2 = this.fieldsForParameters.get(parameterDescriptor2);
            if (fieldInfo2 != null) {
                if (zIsDeclarationOfBigArityCreateCoroutineMethod) {
                    instructionAdapter.load(iEnterTemp, fieldInfo2.getOwnerType());
                    instructionAdapter.load(1, AsmTypes.OBJECT_TYPE);
                    instructionAdapter.iconst(i - 1);
                    instructionAdapter.aload(AsmTypes.OBJECT_TYPE);
                    StackValue.coerce(AsmTypes.OBJECT_TYPE, this.builtIns.getNullableAnyType(), fieldInfo2.getFieldType(), fieldInfo2.getFieldKotlinType(), instructionAdapter);
                    instructionAdapter.putfield(fieldInfo2.getOwnerInternalName(), fieldInfo2.getFieldName(), fieldInfo2.getFieldType().getDescriptor());
                } else {
                    if (this.generateErasedCreate) {
                        KotlinType type2 = parameterDescriptor2.getType();
                        Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                        if (InlineClassesUtilsKt.isInlineClassType(type2)) {
                            instructionAdapter.load(iEnterTemp, fieldInfo2.getOwnerType());
                            instructionAdapter.load(i, AsmTypes.OBJECT_TYPE);
                            StackValue.unboxInlineClass(AsmTypes.OBJECT_TYPE, parameterDescriptor2.getType(), instructionAdapter, this.typeMapper);
                            instructionAdapter.putfield(fieldInfo2.getOwnerInternalName(), fieldInfo2.getFieldName(), fieldInfo2.getFieldType().getDescriptor());
                        } else {
                            instructionAdapter.load(i, AsmTypes.OBJECT_TYPE);
                            StackValue.coerce(AsmTypes.OBJECT_TYPE, this.builtIns.getNullableAnyType(), fieldInfo2.getFieldType(), fieldInfo2.getFieldKotlinType(), instructionAdapter);
                        }
                    } else {
                        instructionAdapter.load(i, fieldInfo2.getFieldType());
                    }
                    DescriptorAsmUtil.genAssignInstanceFieldFromParam(fieldInfo2, i, instructionAdapter, iEnterTemp, this.generateErasedCreate);
                }
            }
            if (zIsDeclarationOfBigArityCreateCoroutineMethod || this.generateErasedCreate) {
                size = 1;
            } else {
                KotlinTypeMapper typeMapper2 = this.state.getTypeMapper();
                KotlinType type3 = parameterDescriptor2.getType();
                Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
                size = KotlinTypeMapper.mapType$default(typeMapper2, type3, null, null, 6, null).getSize();
            }
            i += size;
        }
        instructionAdapter.load(iEnterTemp, AsmTypes.OBJECT_TYPE);
        instructionAdapter.areturn(AsmTypes.OBJECT_TYPE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initializeCoroutineParameters(ExpressionCodegen expressionCodegen) {
        String str;
        String strAsString;
        for (ParameterDescriptor parameterDescriptor : allFunctionParameters()) {
            FieldInfo fieldInfo = this.fieldsForParameters.get(parameterDescriptor);
            if (fieldInfo != null) {
                StackValue.Field field = StackValue.field(fieldInfo, expressionCodegen.generateThisOrOuter(expressionCodegen.context.getThisDescriptor(), false));
                Intrinsics.checkNotNullExpressionValue(field, "field(...)");
                KotlinTypeMapper typeMapper = expressionCodegen.getTypeMapper();
                Intrinsics.checkNotNullExpressionValue(typeMapper, "getTypeMapper(...)");
                KotlinType type = parameterDescriptor.getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                Type typeMapType$default = KotlinTypeMapper.mapType$default(typeMapper, type, null, null, 6, null);
                Type typeNormalize = CoroutineTransformerMethodVisitorKt.normalize(typeMapType$default);
                field.put(typeNormalize, expressionCodegen.v);
                int iEnter = expressionCodegen.myFrameMap.enter(parameterDescriptor, typeMapType$default);
                StackValue.coerce(typeNormalize, typeMapType$default, expressionCodegen.v);
                expressionCodegen.v.store(iEnter, typeMapType$default);
                if (parameterDescriptor instanceof ReceiverParameterDescriptor) {
                    strAsString = DescriptorAsmUtil.getNameForReceiverParameter(this.originalSuspendFunctionDescriptor, expressionCodegen.getBindingContext(), getLanguageVersionSettings());
                } else {
                    Intrinsics.checkNotNull(parameterDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ValueParameterDescriptor");
                    ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) parameterDescriptor;
                    String nameForDestructuredParameterOrNull = VariableAsmNameManglingUtils.getNameForDestructuredParameterOrNull(valueParameterDescriptor);
                    if (nameForDestructuredParameterOrNull == null) {
                        strAsString = valueParameterDescriptor.getShortName().asString();
                        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                    } else {
                        str = nameForDestructuredParameterOrNull;
                    }
                    Intrinsics.checkNotNull(str);
                    Label label = new Label();
                    expressionCodegen.v.mark(label);
                    expressionCodegen.v.visitLocalVariable(str, typeMapType$default.getDescriptor(), null, label, this.endLabel, iEnter);
                }
                str = strAsString;
                Intrinsics.checkNotNull(str);
                Label label2 = new Label();
                expressionCodegen.v.mark(label2);
                expressionCodegen.v.visitLocalVariable(str, typeMapType$default.getDescriptor(), null, label2, this.endLabel, iEnter);
            }
        }
        List<ValueParameterDescriptor> valueParameters = this.originalSuspendFunctionDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : valueParameters) {
            ValueParameterDescriptor valueParameterDescriptor2 = (ValueParameterDescriptor) obj;
            Intrinsics.checkNotNull(valueParameterDescriptor2);
            if (!isUnused(valueParameterDescriptor2)) {
                arrayList.add(obj);
            }
        }
        CodegenUtilKt.initializeVariablesForDestructuredLambdaParameters(expressionCodegen, arrayList, this.endLabel);
    }

    private final List<ParameterDescriptor> allFunctionParameters() {
        List listListOfNotNull = CollectionsKt.listOfNotNull(this.originalSuspendFunctionDescriptor.getExtensionReceiverParameter());
        List<ValueParameterDescriptor> valueParameters = this.originalSuspendFunctionDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        return CollectionsKt.plus((Collection) listListOfNotNull, (Iterable) valueParameters);
    }

    private final FieldInfo createHiddenFieldInfo(KotlinType type, String name2) {
        KotlinTypeMapper kotlinTypeMapper = this.typeMapper;
        ClassDescriptor thisDescriptor = this.closureContext.getThisDescriptor();
        Intrinsics.checkNotNullExpressionValue(thisDescriptor, "getThisDescriptor(...)");
        Type typeMapClass = kotlinTypeMapper.mapClass(thisDescriptor);
        KotlinTypeMapper typeMapper = this.typeMapper;
        Intrinsics.checkNotNullExpressionValue(typeMapper, "typeMapper");
        FieldInfo fieldInfoCreateForHiddenField = FieldInfo.createForHiddenField(typeMapClass, CoroutineTransformerMethodVisitorKt.normalize(KotlinTypeMapper.mapType$default(typeMapper, type, null, null, 6, null)), type, name2);
        Intrinsics.checkNotNullExpressionValue(fieldInfoCreateForHiddenField, "createForHiddenField(...)");
        return fieldInfoCreateForHiddenField;
    }

    private final void generateResumeImpl() {
        this.functionCodegen.generateMethod(JvmDeclarationOriginKt.OtherOrigin$default((PsiElement) this.element, null, 2, null), getMethodToImplement(), new C43801(this.state, (KtElement) this.element));
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenForLambda$generateResumeImpl$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: CoroutineCodegen.kt */
    @Metadata(d1 = {"\u00003\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J(\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eH\u0016¨\u0006\u0010"}, d2 = {"org/jetbrains/kotlin/codegen/coroutines/CoroutineCodegenForLambda$generateResumeImpl$1", "Lorg/jetbrains/kotlin/codegen/FunctionGenerationStrategy$FunctionDefault;", "doGenerateBody", "", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "signature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "wrapMethodVisitor", "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", JvmAnnotationNames.METADATA_VERSION_FIELD_NAME, "access", "", "name", "", "desc", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class C43801 extends FunctionGenerationStrategy.FunctionDefault {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C43801(GenerationState generationState, KtElement ktElement) {
            super(generationState, (KtDeclarationWithBody) ktElement);
            Intrinsics.checkNotNull(ktElement, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtDeclarationWithBody");
        }

        @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy
        public MethodVisitor wrapMethodVisitor(MethodVisitor mv, int access, String name2, String desc) {
            CoroutineTransformerMethodVisitor suspendForInlineCopyingMethodVisitor;
            Intrinsics.checkNotNullParameter(mv, "mv");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(desc, "desc");
            String thisName = CoroutineCodegenForLambda.this.v.getThisName();
            Intrinsics.checkNotNullExpressionValue(thisName, "getThisName(...)");
            final CoroutineCodegenForLambda coroutineCodegenForLambda = CoroutineCodegenForLambda.this;
            Function0<ClassBuilder> function0 = new Function0<ClassBuilder>() { // from class: org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenForLambda$generateResumeImpl$1$wrapMethodVisitor$stateMachineBuilder$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final ClassBuilder invoke() {
                    ClassBuilder v = coroutineCodegenForLambda.v;
                    Intrinsics.checkNotNullExpressionValue(v, "v");
                    return v;
                }
            };
            final CoroutineCodegenForLambda coroutineCodegenForLambda2 = CoroutineCodegenForLambda.this;
            Function1<String, Unit> function1 = new Function1<String, Unit>() { // from class: org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenForLambda$generateResumeImpl$1$wrapMethodVisitor$stateMachineBuilder$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(String str) {
                    invoke2(str);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(String it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    KtPureElement ktPureElement = coroutineCodegenForLambda2.element;
                    Intrinsics.checkNotNullExpressionValue(ktPureElement, "access$getElement$p$s1681327557(...)");
                    GenerationState generationState = this.state;
                    Intrinsics.checkNotNullExpressionValue(generationState, "access$getState$p$s-1623866647(...)");
                    CoroutineCodegenKt.reportSuspensionPointInsideMonitor((KtElement) ktPureElement, generationState, it);
                }
            };
            KtPureElement ktPureElement = CoroutineCodegenForLambda.this.element;
            Intrinsics.checkNotNullExpressionValue(ktPureElement, "access$getElement$p$s1681327557(...)");
            Integer lineNumberForElement = CodegenUtil.getLineNumberForElement((PsiElement) ktPureElement, false);
            int iIntValue = lineNumberForElement != null ? lineNumberForElement.intValue() : 0;
            String name3 = ((KtElement) CoroutineCodegenForLambda.this.element).getContainingKtFile().get$name();
            Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
            CoroutineTransformerMethodVisitor coroutineTransformerMethodVisitor = new CoroutineTransformerMethodVisitor(mv, access, name2, desc, null, null, thisName, function0, false, false, function1, iIntValue, name3, false, null, false, CoroutineCodegenForLambda.this.varsCountByType, false, 188416, null);
            if (CoroutineCodegenForLambda.this.forInline) {
                FunctionCodegen functionCodegen = CoroutineCodegenForLambda.this.functionCodegen;
                Intrinsics.checkNotNullExpressionValue(functionCodegen, "functionCodegen");
                suspendForInlineCopyingMethodVisitor = new SuspendForInlineCopyingMethodVisitor(coroutineTransformerMethodVisitor, access, name2, desc, new CoroutineCodegenForLambda$generateResumeImpl$1$wrapMethodVisitor$maybeWithForInline$1(functionCodegen), false, 32, null);
            } else {
                suspendForInlineCopyingMethodVisitor = coroutineTransformerMethodVisitor;
            }
            return new AddEndLabelMethodVisitor(suspendForInlineCopyingMethodVisitor, access, name2, desc, CoroutineCodegenForLambda.this.endLabel);
        }

        @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy.FunctionDefault, org.jetbrains.kotlin.codegen.FunctionGenerationStrategy.CodegenBased
        public void doGenerateBody(ExpressionCodegen codegen, JvmMethodSignature signature) {
            Intrinsics.checkNotNullParameter(codegen, "codegen");
            Intrinsics.checkNotNullParameter(signature, "signature");
            if (CoroutineCodegenForLambda.this.element instanceof KtFunctionLiteral) {
                KtPureElement ktPureElement = CoroutineCodegenForLambda.this.element;
                Intrinsics.checkNotNullExpressionValue(ktPureElement, "access$getElement$p$s1681327557(...)");
                CodegenUtilKt.recordCallLabelForLambdaArgument((KtFunctionLiteral) ktPureElement, this.state.getBindingTrace());
            }
            CoroutineCodegenForLambda.this.initializeCoroutineParameters(codegen);
            super.doGenerateBody(codegen, signature);
        }
    }

    /* JADX INFO: compiled from: CoroutineCodegen.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J*\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/codegen/coroutines/CoroutineCodegenForLambda$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/codegen/ClosureCodegen;", "expressionCodegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "originalSuspendLambdaDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "declaration", "Lorg/jetbrains/kotlin/psi/KtElement;", "classBuilder", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final ClosureCodegen create(ExpressionCodegen expressionCodegen, FunctionDescriptor originalSuspendLambdaDescriptor, KtElement declaration, ClassBuilder classBuilder) {
            Intrinsics.checkNotNullParameter(expressionCodegen, "expressionCodegen");
            Intrinsics.checkNotNullParameter(originalSuspendLambdaDescriptor, "originalSuspendLambdaDescriptor");
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(classBuilder, "classBuilder");
            if (!CoroutineCodegenUtilKt.isSuspendLambdaOrLocalFunction(originalSuspendLambdaDescriptor) || (declaration instanceof KtCallableReferenceExpression)) {
                return null;
            }
            MethodContext methodContext = expressionCodegen.context;
            GenerationState state = expressionCodegen.getState();
            Intrinsics.checkNotNullExpressionValue(state, "getState(...)");
            ClosureContext closureContextIntoCoroutineClosure = methodContext.intoCoroutineClosure(CoroutineCodegenUtilKt.getOrCreateJvmSuspendFunctionView(originalSuspendLambdaDescriptor, state), originalSuspendLambdaDescriptor, expressionCodegen, expressionCodegen.getState().getTypeMapper());
            Intrinsics.checkNotNullExpressionValue(closureContextIntoCoroutineClosure, "intoCoroutineClosure(...)");
            return new CoroutineCodegenForLambda(expressionCodegen, declaration, closureContextIntoCoroutineClosure, classBuilder, originalSuspendLambdaDescriptor, Intrinsics.areEqual(expressionCodegen.getBindingContext().get(CodegenBinding.CAPTURES_CROSSINLINE_LAMBDA, originalSuspendLambdaDescriptor), (Object) true), null);
        }
    }
}
