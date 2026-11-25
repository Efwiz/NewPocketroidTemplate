package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.ReflectionTypes;
import org.jetbrains.kotlin.codegen.binding.CodegenBinding;
import org.jetbrains.kotlin.codegen.binding.MutableClosure;
import org.jetbrains.kotlin.codegen.context.ClassContext;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.PropertyImportedFromObject;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.inline.InlineUtil;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeSubstitution;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: PropertyReferenceCodegen.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0098\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 82\b\u0012\u0004\u0012\u00020\u00020\u0001:\u000289BK\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0001\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0002\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\f\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\u0002\u0010\u0010J\b\u0010%\u001a\u00020&H\u0002J\b\u0010'\u001a\u00020&H\u0014J\b\u0010(\u001a\u00020&H\u0002J\b\u0010)\u001a\u00020&H\u0014J\b\u0010*\u001a\u00020&H\u0014J9\u0010+\u001a\u00020&2\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u00020\u00192\u0017\u00101\u001a\u0013\u0012\u0004\u0012\u000203\u0012\u0004\u0012\u00020&02¢\u0006\u0002\b4H\u0002J\u0010\u00105\u001a\u0002062\b\u00107\u001a\u0004\u0018\u000106R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R0\u0010\u001a\u001a$\u0012\f\u0012\n \u001d*\u0004\u0018\u00010\u001c0\u001c \u001d*\u0010\u0012\f\u0012\n \u001d*\u0004\u0018\u00010\u001c0\u001c0\u001e0\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\"X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006:"}, d2 = {"Lorg/jetbrains/kotlin/codegen/PropertyReferenceCodegen;", "Lorg/jetbrains/kotlin/codegen/MemberCodegen;", "Lorg/jetbrains/kotlin/psi/KtElement;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "parentCodegen", "context", "Lorg/jetbrains/kotlin/codegen/context/ClassContext;", "expression", "classBuilder", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "localVariableDescriptorForReference", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "target", "boundReceiverJvmKotlinType", "Lorg/jetbrains/kotlin/codegen/JvmKotlinType;", "(Lorg/jetbrains/kotlin/codegen/state/GenerationState;Lorg/jetbrains/kotlin/codegen/MemberCodegen;Lorg/jetbrains/kotlin/codegen/context/ClassContext;Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/codegen/ClassBuilder;Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;Lorg/jetbrains/kotlin/codegen/JvmKotlinType;)V", "asmType", "Lorg/jetbrains/org/objectweb/asm/Type;", "boundReceiverType", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "closure", "Lorg/jetbrains/kotlin/codegen/binding/MutableClosure;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "constructorArgs", "", "Lorg/jetbrains/kotlin/codegen/FieldInfo;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "", "getFunction", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "isLocalDelegatedProperty", "", "superAsmType", "wrapperMethod", "generateAccessors", "", "generateBody", "generateConstructor", "generateDeclaration", "generateKotlinMetadataAnnotation", "generateMethod", "debugString", "", "access", "", "method", CompilerOptions.GENERATE, "Lkotlin/Function1;", "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "Lkotlin/ExtensionFunctionType;", "putInstanceOnStack", "Lorg/jetbrains/kotlin/codegen/StackValue;", "receiverValue", "Companion", "PropertyReferenceGenerationStrategy", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PropertyReferenceCodegen extends MemberCodegen<KtElement> {
    public static final TypeSubstitutor ANY_SUBSTITUTOR;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Type asmType;
    private final JvmKotlinType boundReceiverJvmKotlinType;
    private final Type boundReceiverType;
    private final ClassDescriptor classDescriptor;
    private final MutableClosure closure;
    private final Method constructor;
    private final List<FieldInfo> constructorArgs;
    private final FunctionDescriptor getFunction;
    private final boolean isLocalDelegatedProperty;
    private final VariableDescriptor localVariableDescriptorForReference;
    private final Type superAsmType;
    private final VariableDescriptor target;
    private final Method wrapperMethod;

    @JvmStatic
    public static final FunctionDescriptor createFakeOpenDescriptor(FunctionDescriptor functionDescriptor, ClassDescriptor classDescriptor) {
        return INSTANCE.createFakeOpenDescriptor(functionDescriptor, classDescriptor);
    }

    @JvmStatic
    public static final SimpleFunctionDescriptor findGetFunction(VariableDescriptor variableDescriptor) {
        return INSTANCE.findGetFunction(variableDescriptor);
    }

    @JvmStatic
    public static final Method getWrapperMethodForPropertyReference(VariableDescriptor variableDescriptor, int i) {
        return INSTANCE.getWrapperMethodForPropertyReference(variableDescriptor, i);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PropertyReferenceCodegen(GenerationState state, MemberCodegen<?> parentCodegen, ClassContext context, KtElement expression, ClassBuilder classBuilder, VariableDescriptor localVariableDescriptorForReference, VariableDescriptor target, JvmKotlinType jvmKotlinType) {
        SimpleFunctionDescriptor simpleFunctionDescriptor;
        super(state, parentCodegen, context, expression, classBuilder);
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(parentCodegen, "parentCodegen");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(classBuilder, "classBuilder");
        Intrinsics.checkNotNullParameter(localVariableDescriptorForReference, "localVariableDescriptorForReference");
        Intrinsics.checkNotNullParameter(target, "target");
        this.localVariableDescriptorForReference = localVariableDescriptorForReference;
        this.target = target;
        this.boundReceiverJvmKotlinType = jvmKotlinType;
        this.boundReceiverType = jvmKotlinType != null ? jvmKotlinType.getType() : null;
        T contextDescriptor = context.getContextDescriptor();
        Intrinsics.checkNotNullExpressionValue(contextDescriptor, "getContextDescriptor(...)");
        ClassDescriptor classDescriptor = (ClassDescriptor) contextDescriptor;
        this.classDescriptor = classDescriptor;
        Type typeMapClass = this.typeMapper.mapClass(classDescriptor);
        this.asmType = typeMapClass;
        KotlinTypeMapper kotlinTypeMapper = this.typeMapper;
        ClassDescriptor superClassNotAny = DescriptorUtilsKt.getSuperClassNotAny(classDescriptor);
        if (superClassNotAny != null) {
            this.superAsmType = kotlinTypeMapper.mapClass(superClassNotAny);
            boolean z = target instanceof LocalVariableDescriptor;
            this.isLocalDelegatedProperty = z;
            if (z) {
                Intrinsics.checkNotNull(localVariableDescriptorForReference, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors");
                VariableAccessorDescriptor getter = ((VariableDescriptorWithAccessors) localVariableDescriptorForReference).getGetter();
                Intrinsics.checkNotNull(getter);
                simpleFunctionDescriptor = getter;
            } else {
                SimpleFunctionDescriptor original = INSTANCE.findGetFunction(localVariableDescriptorForReference).getOriginal();
                Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
                simpleFunctionDescriptor = original;
            }
            this.getFunction = simpleFunctionDescriptor;
            this.wrapperMethod = INSTANCE.getWrapperMethodForPropertyReference(target, simpleFunctionDescriptor.getValueParameters().size());
            Object obj = this.bindingContext.get(CodegenBinding.CLOSURE, classDescriptor);
            Intrinsics.checkNotNull(obj);
            MutableClosure mutableClosure = (MutableClosure) obj;
            mutableClosure.getCapturedReceiverFromOuterContext();
            Intrinsics.checkNotNullExpressionValue(obj, "apply(...)");
            this.closure = mutableClosure;
            List<FieldInfo> listCalculateConstructorParameters = ClosureCodegen.calculateConstructorParameters(this.typeMapper, state.getLanguageVersionSettings(), mutableClosure, typeMapClass);
            listCalculateConstructorParameters.size();
            Intrinsics.checkNotNullExpressionValue(listCalculateConstructorParameters, "apply(...)");
            this.constructorArgs = listCalculateConstructorParameters;
            Type type = Type.VOID_TYPE;
            List<FieldInfo> list = listCalculateConstructorParameters;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((FieldInfo) it.next()).getFieldType());
            }
            Type[] typeArr = (Type[]) arrayList.toArray(new Type[0]);
            Method method = AsmUtil.method("<init>", type, (Type[]) Arrays.copyOf(typeArr, typeArr.length));
            Intrinsics.checkNotNullExpressionValue(method, "method(...)");
            this.constructor = method;
            return;
        }
        throw new AssertionError("No super class for " + classDescriptor);
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    /* JADX INFO: renamed from: generateDeclaration */
    protected void mo6420generateDeclaration() {
        this.v.defineClass((PsiElement) this.element, this.state.getConfig().getClassFileVersion(), DescriptorAsmUtil.getVisibilityAccessFlagForClass(this.classDescriptor) | 48 | DescriptorAsmUtil.getSyntheticAccessFlagForLambdaClass(this.classDescriptor), this.asmType.getInternalName(), null, this.superAsmType.getInternalName(), new String[0]);
        this.v.visitSource(((KtElement) this.element).getContainingFile().get$name(), null);
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    /* JADX INFO: renamed from: generateBody */
    protected void mo6419generateBody() {
        if (JvmCodegenUtil.isConst(this.closure)) {
            generateConstInstance(this.asmType, this.wrapperMethod.getReturnType());
        } else {
            DescriptorAsmUtil.genClosureFields(this.closure, this.v, this.typeMapper, this.state.getLanguageVersionSettings());
        }
        generateConstructor();
        if (!AsmTypes.isOptimizedPropertyReferenceSupertype(this.superAsmType)) {
            Method method = AsmUtil.method("getName", AsmTypes.JAVA_STRING_TYPE, new Type[0]);
            Intrinsics.checkNotNullExpressionValue(method, "method(...)");
            generateMethod("property reference getName", 1, method, new Function1<InstructionAdapter, Unit>() { // from class: org.jetbrains.kotlin.codegen.PropertyReferenceCodegen.generateBody.1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(InstructionAdapter instructionAdapter) {
                    invoke2(instructionAdapter);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(InstructionAdapter generateMethod) {
                    Intrinsics.checkNotNullParameter(generateMethod, "$this$generateMethod");
                    generateMethod.aconst(PropertyReferenceCodegen.this.target.getShortName().asString());
                }
            });
            Method method2 = AsmUtil.method("getSignature", AsmTypes.JAVA_STRING_TYPE, new Type[0]);
            Intrinsics.checkNotNullExpressionValue(method2, "method(...)");
            generateMethod("property reference getSignature", 1, method2, new Function1<InstructionAdapter, Unit>() { // from class: org.jetbrains.kotlin.codegen.PropertyReferenceCodegen.generateBody.2
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(InstructionAdapter instructionAdapter) {
                    invoke2(instructionAdapter);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(InstructionAdapter generateMethod) {
                    Intrinsics.checkNotNullParameter(generateMethod, "$this$generateMethod");
                    VariableDescriptor variableDescriptor = PropertyReferenceCodegen.this.target;
                    GenerationState state = PropertyReferenceCodegen.this.state;
                    Intrinsics.checkNotNullExpressionValue(state, "state");
                    CallableReferenceUtilKt.generatePropertyReferenceSignature(generateMethod, variableDescriptor, state);
                }
            });
            Method method3 = AsmUtil.method("getOwner", AsmTypes.K_DECLARATION_CONTAINER_TYPE, new Type[0]);
            Intrinsics.checkNotNullExpressionValue(method3, "method(...)");
            generateMethod("property reference getOwner", 1, method3, new Function1<InstructionAdapter, Unit>() { // from class: org.jetbrains.kotlin.codegen.PropertyReferenceCodegen.generateBody.3
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(InstructionAdapter instructionAdapter) {
                    invoke2(instructionAdapter);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(InstructionAdapter generateMethod) {
                    Intrinsics.checkNotNullParameter(generateMethod, "$this$generateMethod");
                    VariableDescriptor variableDescriptor = PropertyReferenceCodegen.this.target;
                    GenerationState state = PropertyReferenceCodegen.this.state;
                    Intrinsics.checkNotNullExpressionValue(state, "state");
                    CallableReferenceUtilKt.generateCallableReferenceDeclarationContainer(generateMethod, variableDescriptor, state);
                }
            });
        }
        if (this.isLocalDelegatedProperty) {
            return;
        }
        generateAccessors();
    }

    private final void generateConstructor() {
        generateMethod("property reference init", 0, this.constructor, new Function1<InstructionAdapter, Unit>() { // from class: org.jetbrains.kotlin.codegen.PropertyReferenceCodegen.generateConstructor.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(InstructionAdapter instructionAdapter) {
                invoke2(instructionAdapter);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(InstructionAdapter generateMethod) {
                Intrinsics.checkNotNullParameter(generateMethod, "$this$generateMethod");
                CallableReferenceUtilKt.isForBoundCallableReference(PropertyReferenceCodegen.this.closure);
                Pair<Integer, FieldInfo> pairGenerateClosureFieldsInitializationFromParameters = CallableReferenceUtilKt.generateClosureFieldsInitializationFromParameters(generateMethod, PropertyReferenceCodegen.this.closure, PropertyReferenceCodegen.this.constructorArgs);
                generateMethod.load(0, AsmTypes.OBJECT_TYPE);
                ArrayList arrayList = new ArrayList();
                if (pairGenerateClosureFieldsInitializationFromParameters != null) {
                    int iIntValue = pairGenerateClosureFieldsInitializationFromParameters.component1().intValue();
                    FieldInfo fieldInfoComponent2 = pairGenerateClosureFieldsInitializationFromParameters.component2();
                    Type fieldType = fieldInfoComponent2.getFieldType();
                    Intrinsics.checkNotNullExpressionValue(fieldType, "getFieldType(...)");
                    CallableReferenceUtilKt.loadBoundReferenceReceiverParameter(generateMethod, iIntValue, fieldType, fieldInfoComponent2.getFieldKotlinType());
                    Type OBJECT_TYPE = AsmTypes.OBJECT_TYPE;
                    Intrinsics.checkNotNullExpressionValue(OBJECT_TYPE, "OBJECT_TYPE");
                    arrayList.add(OBJECT_TYPE);
                }
                if (AsmTypes.isOptimizedPropertyReferenceSupertype(PropertyReferenceCodegen.this.superAsmType)) {
                    VariableDescriptor variableDescriptor = PropertyReferenceCodegen.this.target;
                    GenerationState state = PropertyReferenceCodegen.this.state;
                    Intrinsics.checkNotNullExpressionValue(state, "state");
                    CallableReferenceUtilKt.generateCallableReferenceDeclarationContainerClass(generateMethod, variableDescriptor, state);
                    generateMethod.aconst(PropertyReferenceCodegen.this.target.getShortName().asString());
                    VariableDescriptor variableDescriptor2 = PropertyReferenceCodegen.this.target;
                    GenerationState state2 = PropertyReferenceCodegen.this.state;
                    Intrinsics.checkNotNullExpressionValue(state2, "state");
                    CallableReferenceUtilKt.generatePropertyReferenceSignature(generateMethod, variableDescriptor2, state2);
                    generateMethod.aconst(Integer.valueOf(CallableReferenceUtilKt.getCallableReferenceTopLevelFlag(PropertyReferenceCodegen.this.target)));
                    Type JAVA_CLASS_TYPE = AsmTypes.JAVA_CLASS_TYPE;
                    Intrinsics.checkNotNullExpressionValue(JAVA_CLASS_TYPE, "JAVA_CLASS_TYPE");
                    arrayList.add(JAVA_CLASS_TYPE);
                    Type JAVA_STRING_TYPE = AsmTypes.JAVA_STRING_TYPE;
                    Intrinsics.checkNotNullExpressionValue(JAVA_STRING_TYPE, "JAVA_STRING_TYPE");
                    arrayList.add(JAVA_STRING_TYPE);
                    Type JAVA_STRING_TYPE2 = AsmTypes.JAVA_STRING_TYPE;
                    Intrinsics.checkNotNullExpressionValue(JAVA_STRING_TYPE2, "JAVA_STRING_TYPE");
                    arrayList.add(JAVA_STRING_TYPE2);
                    Type INT_TYPE = Type.INT_TYPE;
                    Intrinsics.checkNotNullExpressionValue(INT_TYPE, "INT_TYPE");
                    arrayList.add(INT_TYPE);
                }
                String internalName = PropertyReferenceCodegen.this.superAsmType.getInternalName();
                Type type = Type.VOID_TYPE;
                Type[] typeArr = (Type[]) arrayList.toArray(new Type[0]);
                generateMethod.invokespecial(internalName, "<init>", Type.getMethodDescriptor(type, (Type[]) Arrays.copyOf(typeArr, typeArr.length)), false);
            }
        });
    }

    private final void generateAccessors() {
        Companion companion = INSTANCE;
        SimpleFunctionDescriptor simpleFunctionDescriptorFindGetFunction = companion.findGetFunction(this.localVariableDescriptorForReference);
        FunctionDescriptor functionDescriptorCreateFakeOpenDescriptor = companion.createFakeOpenDescriptor(simpleFunctionDescriptorFindGetFunction, this.classDescriptor);
        FunctionCodegen functionCodegen = this.functionCodegen;
        JvmDeclarationOrigin jvmDeclarationOrigin = JvmDeclarationOrigin.NO_ORIGIN;
        VariableDescriptor variableDescriptor = this.target;
        Type type = this.asmType;
        JvmKotlinType jvmKotlinType = this.boundReceiverJvmKotlinType;
        T element = this.element;
        Intrinsics.checkNotNullExpressionValue(element, "element");
        GenerationState state = this.state;
        Intrinsics.checkNotNullExpressionValue(state, "state");
        functionCodegen.generateMethod(jvmDeclarationOrigin, functionDescriptorCreateFakeOpenDescriptor, new PropertyReferenceGenerationStrategy(true, simpleFunctionDescriptorFindGetFunction, variableDescriptor, type, jvmKotlinType, (KtElement) element, state, false));
        ReflectionTypes.Companion companion2 = ReflectionTypes.INSTANCE;
        KotlinType type2 = this.localVariableDescriptorForReference.getType();
        Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
        if (companion2.isNumberedKMutablePropertyType(type2)) {
            SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) CollectionsKt.single(this.localVariableDescriptorForReference.getType().getMemberScope().getContributedFunctions(OperatorNameConventions.SET, NoLookupLocation.FROM_BACKEND));
            FunctionDescriptor functionDescriptorCreateFakeOpenDescriptor2 = companion.createFakeOpenDescriptor(simpleFunctionDescriptor, this.classDescriptor);
            FunctionCodegen functionCodegen2 = this.functionCodegen;
            JvmDeclarationOrigin jvmDeclarationOrigin2 = JvmDeclarationOrigin.NO_ORIGIN;
            VariableDescriptor variableDescriptor2 = this.target;
            Type type3 = this.asmType;
            JvmKotlinType jvmKotlinType2 = this.boundReceiverJvmKotlinType;
            T element2 = this.element;
            Intrinsics.checkNotNullExpressionValue(element2, "element");
            GenerationState state2 = this.state;
            Intrinsics.checkNotNullExpressionValue(state2, "state");
            functionCodegen2.generateMethod(jvmDeclarationOrigin2, functionDescriptorCreateFakeOpenDescriptor2, new PropertyReferenceGenerationStrategy(false, simpleFunctionDescriptor, variableDescriptor2, type3, jvmKotlinType2, (KtElement) element2, state2, false));
        }
    }

    private final void generateMethod(String debugString, int access, Method method, Function1<? super InstructionAdapter, Unit> generate) {
        ClassBuilder v = this.v;
        Intrinsics.checkNotNullExpressionValue(v, "v");
        PsiElement psiElement = (PsiElement) this.element;
        JvmDeclarationOrigin jvmDeclarationOrigin = JvmDeclarationOrigin.NO_ORIGIN;
        GenerationState state = this.state;
        Intrinsics.checkNotNullExpressionValue(state, "state");
        CodegenUtilKt.generateMethod(v, debugString, access, method, psiElement, jvmDeclarationOrigin, state, generate);
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    /* JADX INFO: renamed from: generateKotlinMetadataAnnotation */
    protected void mo6421generateKotlinMetadataAnnotation() {
        ClassBuilder v = this.v;
        Intrinsics.checkNotNullExpressionValue(v, "v");
        GenerationState state = this.state;
        Intrinsics.checkNotNullExpressionValue(state, "state");
        WriteAnnotationUtilKt.writeSyntheticClassMetadata(v, state, InlineUtil.isInPublicInlineScope(this.classDescriptor));
    }

    public final StackValue putInstanceOnStack(final StackValue receiverValue) {
        StackValue stackValueOperation = StackValue.operation(this.wrapperMethod.getReturnType(), new Function1<InstructionAdapter, Unit>() { // from class: org.jetbrains.kotlin.codegen.PropertyReferenceCodegen.putInstanceOnStack.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(InstructionAdapter instructionAdapter) {
                invoke2(instructionAdapter);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(InstructionAdapter instructionAdapter) {
                if (JvmCodegenUtil.isConst(PropertyReferenceCodegen.this.closure)) {
                    instructionAdapter.getstatic(PropertyReferenceCodegen.this.asmType.getInternalName(), JvmAbi.INSTANCE_FIELD, PropertyReferenceCodegen.this.wrapperMethod.getReturnType().getDescriptor());
                    return;
                }
                instructionAdapter.anew(PropertyReferenceCodegen.this.asmType);
                instructionAdapter.dup();
                StackValue stackValue = receiverValue;
                Intrinsics.checkNotNull(stackValue);
                stackValue.put(receiverValue.type, receiverValue.kotlinType, instructionAdapter);
                instructionAdapter.invokespecial(PropertyReferenceCodegen.this.asmType.getInternalName(), "<init>", PropertyReferenceCodegen.this.constructor.getDescriptor(), false);
            }
        });
        Intrinsics.checkNotNullExpressionValue(stackValueOperation, "operation(...)");
        return stackValueOperation;
    }

    /* JADX INFO: compiled from: PropertyReferenceCodegen.kt */
    @Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0007J\u0015\u0010\n\u001a\u00070\u000b¢\u0006\u0002\b\f2\u0006\u0010\r\u001a\u00020\u000eH\u0007J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0013H\u0007R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/codegen/PropertyReferenceCodegen$Companion;", "", "()V", "ANY_SUBSTITUTOR", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "createFakeOpenDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getFunction", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "findGetFunction", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "Lkotlin/jvm/JvmWildcard;", "localVariableDescriptorForReference", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "getWrapperMethodForPropertyReference", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "property", "receiverCount", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final Method getWrapperMethodForPropertyReference(VariableDescriptor property, int receiverCount) {
            Method method;
            Intrinsics.checkNotNullParameter(property, "property");
            if (receiverCount == 1) {
                method = property.isVar() ? AsmUtil.method("mutableProperty1", AsmTypes.K_MUTABLE_PROPERTY1_TYPE, AsmTypes.MUTABLE_PROPERTY_REFERENCE1) : AsmUtil.method("property1", AsmTypes.K_PROPERTY1_TYPE, AsmTypes.PROPERTY_REFERENCE1);
                Intrinsics.checkNotNull(method);
            } else if (receiverCount == 2) {
                method = property.isVar() ? AsmUtil.method("mutableProperty2", AsmTypes.K_MUTABLE_PROPERTY2_TYPE, AsmTypes.MUTABLE_PROPERTY_REFERENCE2) : AsmUtil.method("property2", AsmTypes.K_PROPERTY2_TYPE, AsmTypes.PROPERTY_REFERENCE2);
                Intrinsics.checkNotNull(method);
            } else {
                method = property.isVar() ? AsmUtil.method("mutableProperty0", AsmTypes.K_MUTABLE_PROPERTY0_TYPE, AsmTypes.MUTABLE_PROPERTY_REFERENCE0) : AsmUtil.method("property0", AsmTypes.K_PROPERTY0_TYPE, AsmTypes.PROPERTY_REFERENCE0);
                Intrinsics.checkNotNull(method);
            }
            return method;
        }

        @JvmStatic
        public final FunctionDescriptor createFakeOpenDescriptor(FunctionDescriptor getFunction, ClassDescriptor classDescriptor) {
            Intrinsics.checkNotNullParameter(getFunction, "getFunction");
            Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
            FunctionDescriptor functionDescriptorCopy = getFunction.getOriginal().copy((DeclarationDescriptor) classDescriptor, Modality.OPEN, getFunction.getVisibility(), getFunction.getKind(), false);
            Intrinsics.checkNotNullExpressionValue(functionDescriptorCopy, "copy(...)");
            FunctionDescriptor functionDescriptorSubstitute = functionDescriptorCopy.substitute(PropertyReferenceCodegen.ANY_SUBSTITUTOR);
            Intrinsics.checkNotNull(functionDescriptorSubstitute);
            return functionDescriptorSubstitute;
        }

        @JvmStatic
        public final SimpleFunctionDescriptor findGetFunction(VariableDescriptor localVariableDescriptorForReference) {
            Intrinsics.checkNotNullParameter(localVariableDescriptorForReference, "localVariableDescriptorForReference");
            return (SimpleFunctionDescriptor) CollectionsKt.single(localVariableDescriptorForReference.getType().getMemberScope().getContributedFunctions(OperatorNameConventions.GET, NoLookupLocation.FROM_BACKEND));
        }
    }

    static {
        TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create(new TypeSubstitution() { // from class: org.jetbrains.kotlin.codegen.PropertyReferenceCodegen$Companion$ANY_SUBSTITUTOR$1
            @Override // org.jetbrains.kotlin.types.TypeSubstitution
            /* JADX INFO: renamed from: get */
            public TypeProjection mo7475get(KotlinType key) {
                Intrinsics.checkNotNullParameter(key, "key");
                if (KotlinBuiltIns.isUnit(key)) {
                    return new TypeProjectionImpl(key);
                }
                return new TypeProjectionImpl(TypeUtilsKt.getBuiltIns(key).getNullableAnyType());
            }
        });
        Intrinsics.checkNotNullExpressionValue(typeSubstitutorCreate, "create(...)");
        ANY_SUBSTITUTOR = typeSubstitutorCreate;
    }

    /* JADX INFO: compiled from: PropertyReferenceCodegen.kt */
    @Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001BG\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0003¢\u0006\u0002\u0010\u0011J\u0018\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/codegen/PropertyReferenceCodegen$PropertyReferenceGenerationStrategy;", "Lorg/jetbrains/kotlin/codegen/FunctionGenerationStrategy$CodegenBased;", "isGetter", "", "originalFunctionDesc", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "target", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "asmType", "Lorg/jetbrains/org/objectweb/asm/Type;", "boundReceiverJvmKotlinType", "Lorg/jetbrains/kotlin/codegen/JvmKotlinType;", "expression", "Lorg/jetbrains/kotlin/psi/KtElement;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "isInliningStrategy", "(ZLorg/jetbrains/kotlin/descriptors/FunctionDescriptor;Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/kotlin/codegen/JvmKotlinType;Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/codegen/state/GenerationState;Z)V", "boundReceiverKotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "boundReceiverType", "expectedReceiverKotlinType", "expectedReceiverType", "doGenerateBody", "", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "signature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class PropertyReferenceGenerationStrategy extends FunctionGenerationStrategy.CodegenBased {
        private final Type asmType;
        private final KotlinType boundReceiverKotlinType;
        private final Type boundReceiverType;
        private final KotlinType expectedReceiverKotlinType;
        private final Type expectedReceiverType;
        private final KtElement expression;
        private final boolean isGetter;
        private final boolean isInliningStrategy;
        private final FunctionDescriptor originalFunctionDesc;
        private final VariableDescriptor target;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public PropertyReferenceGenerationStrategy(boolean z, FunctionDescriptor originalFunctionDesc, VariableDescriptor target, Type asmType, JvmKotlinType jvmKotlinType, KtElement expression, GenerationState state, boolean z2) {
            SimpleType defaultType;
            super(state);
            Intrinsics.checkNotNullParameter(originalFunctionDesc, "originalFunctionDesc");
            Intrinsics.checkNotNullParameter(target, "target");
            Intrinsics.checkNotNullParameter(asmType, "asmType");
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(state, "state");
            this.isGetter = z;
            this.originalFunctionDesc = originalFunctionDesc;
            this.target = target;
            this.asmType = asmType;
            this.expression = expression;
            this.isInliningStrategy = z2;
            this.boundReceiverType = jvmKotlinType != null ? jvmKotlinType.getType() : null;
            this.boundReceiverKotlinType = jvmKotlinType != null ? jvmKotlinType.getKotlinType() : null;
            ReceiverParameterDescriptor extensionReceiverParameter = target.getExtensionReceiverParameter();
            if (extensionReceiverParameter == null || (defaultType = extensionReceiverParameter.getType()) == null) {
                DeclarationDescriptor containingDeclaration = target.getContainingModule();
                ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
                defaultType = classDescriptor != null ? classDescriptor.getDefaultType() : null;
            }
            KotlinType kotlinType = defaultType;
            this.expectedReceiverKotlinType = kotlinType;
            this.expectedReceiverType = kotlinType != null ? KotlinTypeMapper.mapType$default(state.getTypeMapper(), kotlinType, null, null, 6, null) : null;
        }

        @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy.CodegenBased
        public void doGenerateBody(ExpressionCodegen codegen, JvmMethodSignature signature) {
            StackValue.Property propertyIntermediateValueForProperty;
            StackValue stackValueNone;
            Intrinsics.checkNotNullParameter(codegen, "codegen");
            Intrinsics.checkNotNullParameter(signature, "signature");
            InstructionAdapter instructionAdapter = codegen.v;
            KotlinTypeMapper typeMapper = this.state.getTypeMapper();
            KotlinType type = this.target.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            VariableDescriptor variableDescriptor = this.target;
            if (variableDescriptor instanceof PropertyImportedFromObject) {
                ClassDescriptor containingObject = ((PropertyImportedFromObject) variableDescriptor).getContainingObject();
                StackValue.singleton(containingObject, typeMapper).put(typeMapper.mapClass(containingObject), containingObject.getDefaultType(), instructionAdapter);
            }
            Type type2 = this.boundReceiverType;
            if (type2 != null) {
                StackValue stackValueCapturedBoundReferenceReceiver = CallableReferenceUtilKt.capturedBoundReferenceReceiver(this.asmType, type2, this.boundReceiverKotlinType, this.isInliningStrategy);
                Type type3 = this.expectedReceiverType;
                Intrinsics.checkNotNull(type3);
                stackValueCapturedBoundReferenceReceiver.put(type3, this.expectedReceiverKotlinType, instructionAdapter);
            } else {
                List<ValueParameterDescriptor> valueParameters = this.originalFunctionDesc.getValueParameters();
                Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                int i = 0;
                for (Object obj : CollectionsKt.dropLast(valueParameters, !this.isGetter ? 1 : 0)) {
                    int i2 = i + 1;
                    if (i < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) obj;
                    Intrinsics.checkNotNull(valueParameterDescriptor);
                    SimpleType nullableAnyType = DescriptorUtilsKt.getBuiltIns(valueParameterDescriptor).getNullableAnyType();
                    Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getNullableAnyType(...)");
                    StackValue.local(i2, AsmTypes.OBJECT_TYPE, nullableAnyType).put(typeMapper.mapType(valueParameterDescriptor), valueParameterDescriptor.getType(), instructionAdapter);
                    i = i2;
                }
            }
            VariableDescriptor variableDescriptor2 = this.target;
            if (variableDescriptor2 instanceof LocalVariableDescriptor) {
                propertyIntermediateValueForProperty = codegen.findLocalOrCapturedValue(variableDescriptor2);
                Intrinsics.checkNotNull(propertyIntermediateValueForProperty);
            } else if (InlineClassesUtilsKt.isUnderlyingPropertyOfInlineClass(variableDescriptor2)) {
                Type type4 = this.expectedReceiverType;
                if (type4 == null) {
                    throw new AssertionError(this.target + ": boundReceiverType=" + this.boundReceiverType + ", expectedReceiverType is null");
                }
                if (this.boundReceiverType != null) {
                    stackValueNone = StackValue.onStack(type4, this.expectedReceiverKotlinType);
                } else {
                    stackValueNone = StackValue.none();
                }
                propertyIntermediateValueForProperty = StackValue.underlyingValueOfInlineClass(KotlinTypeMapper.mapType$default(typeMapper, type, null, null, 6, null), type, stackValueNone);
            } else {
                VariableDescriptor variableDescriptor3 = this.target;
                Intrinsics.checkNotNull(variableDescriptor3, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.PropertyDescriptor");
                propertyIntermediateValueForProperty = codegen.intermediateValueForProperty((PropertyDescriptor) variableDescriptor3, false, null, StackValue.none());
            }
            codegen.markStartLineNumber(this.expression);
            if (this.isGetter) {
                propertyIntermediateValueForProperty.put(AsmTypes.OBJECT_TYPE, type, instructionAdapter);
            } else {
                FrameMap frameMap = codegen.getFrameMap();
                List<ValueParameterDescriptor> valueParameters2 = codegen.context.getFunctionDescriptor().getValueParameters();
                Intrinsics.checkNotNullExpressionValue(valueParameters2, "getValueParameters(...)");
                Object objLast = CollectionsKt.last((List<? extends Object>) valueParameters2);
                Intrinsics.checkNotNullExpressionValue(objLast, "last(...)");
                propertyIntermediateValueForProperty.store(StackValue.local(frameMap.getIndex(objLast), AsmTypes.OBJECT_TYPE, type), instructionAdapter);
            }
            instructionAdapter.areturn(signature.getReturnType());
        }
    }
}
