package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.codegen.context.CodegenContext;
import org.jetbrains.kotlin.codegen.context.MethodContext;
import org.jetbrains.kotlin.codegen.context.ScriptContext;
import org.jetbrains.kotlin.codegen.serialization.JvmSerializationBindings;
import org.jetbrains.kotlin.codegen.serialization.JvmSerializerExtension;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKt;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.kotlin.serialization.DescriptorSerializer;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: ScriptCodegen.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 \u001c2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u001cB'\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ \u0010\u000f\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0010\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\tH\u0002J\b\u0010\u0015\u001a\u00020\u0010H\u0002J\b\u0010\u0016\u001a\u00020\u0010H\u0002J\b\u0010\u0017\u001a\u00020\u0010H\u0014J\b\u0010\u0018\u001a\u00020\u0010H\u0014J\b\u0010\u0019\u001a\u00020\u0010H\u0014J\b\u0010\u001a\u001a\u00020\u0010H\u0014J\b\u0010\u001b\u001a\u00020\u0010H\u0014R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0002X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/codegen/ScriptCodegen;", "Lorg/jetbrains/kotlin/codegen/MemberCodegen;", "Lorg/jetbrains/kotlin/psi/KtScript;", "scriptDeclaration", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "scriptContext", "Lorg/jetbrains/kotlin/codegen/context/ScriptContext;", "builder", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "(Lorg/jetbrains/kotlin/psi/KtScript;Lorg/jetbrains/kotlin/codegen/state/GenerationState;Lorg/jetbrains/kotlin/codegen/context/ScriptContext;Lorg/jetbrains/kotlin/codegen/ClassBuilder;)V", "classAsmType", "Lorg/jetbrains/org/objectweb/asm/Type;", "scriptDescriptor", "Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;", "genConstructor", "", "classBuilder", "methodContext", "Lorg/jetbrains/kotlin/codegen/context/MethodContext;", "genFieldsForParameters", "genMain", "genMembers", "generateBody", "generateDeclaration", "generateKotlinMetadataAnnotation", "generateSyntheticPartsAfterBody", "generateSyntheticPartsBeforeBody", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScriptCodegen extends MemberCodegen<KtScript> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Type classAsmType;
    private final ScriptContext scriptContext;
    private final KtScript scriptDeclaration;
    private final ScriptDescriptor scriptDescriptor;

    public /* synthetic */ ScriptCodegen(KtScript ktScript, GenerationState generationState, ScriptContext scriptContext, ClassBuilder classBuilder, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktScript, generationState, scriptContext, classBuilder);
    }

    @JvmStatic
    public static final ScriptCodegen createScriptCodegen(KtScript ktScript, GenerationState generationState, CodegenContext<?> codegenContext) {
        return INSTANCE.createScriptCodegen(ktScript, generationState, codegenContext);
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    protected void generateSyntheticPartsAfterBody() {
    }

    private ScriptCodegen(KtScript ktScript, GenerationState generationState, ScriptContext scriptContext, ClassBuilder classBuilder) {
        super(generationState, null, scriptContext, ktScript, classBuilder);
        this.scriptDeclaration = ktScript;
        this.scriptContext = scriptContext;
        this.scriptDescriptor = scriptContext.getScriptDescriptor();
        KotlinTypeMapper kotlinTypeMapper = this.typeMapper;
        T contextDescriptor = scriptContext.getContextDescriptor();
        Intrinsics.checkNotNullExpressionValue(contextDescriptor, "getContextDescriptor(...)");
        this.classAsmType = kotlinTypeMapper.mapClass((ClassifierDescriptor) contextDescriptor);
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    /* JADX INFO: renamed from: generateDeclaration */
    protected void mo6420generateDeclaration() {
        ClassBuilder classBuilder = this.v;
        KtScript ktScript = this.scriptDeclaration;
        int classFileVersion = this.state.getConfig().getClassFileVersion();
        String internalName = this.classAsmType.getInternalName();
        KotlinTypeMapper kotlinTypeMapper = this.typeMapper;
        SimpleType defaultType = DescriptorUtilsKt.getSuperClassOrAny(this.scriptDescriptor).getDefaultType();
        Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
        String internalName2 = kotlinTypeMapper.mapSupertype(defaultType, null).getInternalName();
        List<ClassDescriptor> superInterfaces = DescriptorUtilsKt.getSuperInterfaces(this.scriptDescriptor);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(superInterfaces, 10));
        for (ClassDescriptor classDescriptor : superInterfaces) {
            KotlinTypeMapper kotlinTypeMapper2 = this.typeMapper;
            SimpleType defaultType2 = classDescriptor.getDefaultType();
            Intrinsics.checkNotNullExpressionValue(defaultType2, "getDefaultType(...)");
            arrayList.add(kotlinTypeMapper2.mapSupertype(defaultType2, null).getInternalName());
        }
        classBuilder.defineClass(ktScript, classFileVersion, 33, internalName, null, internalName2, (String[]) arrayList.toArray(new String[0]));
        this.v.visitSource(this.scriptDeclaration.getContainingKtFile().get$name(), null);
        AnnotationCodegen.forClass(this.v.getVisitor(), this, this.state).genAnnotations(this.scriptDescriptor, null, null);
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    /* JADX INFO: renamed from: generateBody */
    protected void mo6419generateBody() {
        genMembers();
        ClassBuilder v = this.v;
        Intrinsics.checkNotNullExpressionValue(v, "v");
        genFieldsForParameters(v);
        ScriptDescriptor scriptDescriptor = this.scriptDescriptor;
        ClassBuilder v2 = this.v;
        Intrinsics.checkNotNullExpressionValue(v2, "v");
        MethodContext methodContextIntoFunction = this.scriptContext.intoFunction(this.scriptDescriptor.mo6347getUnsubstitutedPrimaryConstructor());
        Intrinsics.checkNotNullExpressionValue(methodContextIntoFunction, "intoFunction(...)");
        genConstructor(scriptDescriptor, v2, methodContextIntoFunction);
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    protected void generateSyntheticPartsBeforeBody() {
        generatePropertyMetadataArrayFieldIfNeeded(this.classAsmType);
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    /* JADX INFO: renamed from: generateKotlinMetadataAnnotation */
    protected void mo6421generateKotlinMetadataAnnotation() {
        DescriptorSerializer.Companion companion = DescriptorSerializer.INSTANCE;
        ScriptDescriptor scriptDescriptor = this.scriptDescriptor;
        JvmSerializationBindings serializationBindings = this.v.getSerializationBindings();
        Intrinsics.checkNotNullExpressionValue(serializationBindings, "getSerializationBindings(...)");
        GenerationState state = this.state;
        Intrinsics.checkNotNullExpressionValue(state, "state");
        final DescriptorSerializer descriptorSerializerCreate$default = DescriptorSerializer.Companion.create$default(companion, scriptDescriptor, new JvmSerializerExtension(serializationBindings, state, null, 4, null), null, CommonConfigurationKeysKt.getLanguageVersionSettings(this.state.getConfiguration()), null, 16, null);
        final ProtoBuf.Class classBuild = descriptorSerializerCreate$default.classProto(this.scriptDescriptor).build();
        ClassBuilder v = this.v;
        Intrinsics.checkNotNullExpressionValue(v, "v");
        GenerationState state2 = this.state;
        Intrinsics.checkNotNullExpressionValue(state2, "state");
        WriteAnnotationUtilKt.writeKotlinMetadata(v, state2, KotlinClassHeader.Kind.CLASS, false, 4, new Function1<AnnotationVisitor, Unit>() { // from class: org.jetbrains.kotlin.codegen.ScriptCodegen.generateKotlinMetadataAnnotation.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(AnnotationVisitor annotationVisitor) {
                invoke2(annotationVisitor);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(AnnotationVisitor av) {
                Intrinsics.checkNotNullParameter(av, "av");
                DescriptorAsmUtil.writeAnnotationData(av, descriptorSerializerCreate$default, classBuild);
            }
        });
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    private final void genConstructor(ScriptDescriptor scriptDescriptor, ClassBuilder classBuilder, MethodContext methodContext) {
        int i;
        ValueParameterDescriptor valueParameterDescriptor;
        List<ValueParameterDescriptor> list;
        Iterator<ValueParameterDescriptor> it;
        JvmMethodSignature jvmMethodSignatureMapScriptSignature = this.typeMapper.mapScriptSignature(scriptDescriptor);
        Method asmMethod = jvmMethodSignatureMapScriptSignature.getAsmMethod();
        Intrinsics.checkNotNullExpressionValue(asmMethod, "getAsmMethod(...)");
        classBuilder.getSerializationBindings().put(JvmSerializationBindings.METHOD_FOR_FUNCTION, scriptDescriptor.mo6347getUnsubstitutedPrimaryConstructor(), asmMethod);
        FieldInfo resultFieldInfo = this.scriptContext.getResultFieldInfo();
        if (resultFieldInfo != null) {
            classBuilder.newField(JvmDeclarationOrigin.NO_ORIGIN, 17, resultFieldInfo.getFieldName(), resultFieldInfo.getFieldType().getDescriptor(), null, null);
        }
        MethodVisitor methodVisitorNewMethod = classBuilder.newMethod(JvmDeclarationOriginKt.OtherOrigin(this.scriptDeclaration, scriptDescriptor.mo6347getUnsubstitutedPrimaryConstructor()), 1, jvmMethodSignatureMapScriptSignature.getAsmMethod().getName(), jvmMethodSignatureMapScriptSignature.getAsmMethod().getDescriptor(), null, null);
        Intrinsics.checkNotNullExpressionValue(methodVisitorNewMethod, "newMethod(...)");
        AnnotationCodegen.forMethod(methodVisitorNewMethod, this, this.state).genAnnotations(scriptDescriptor.mo6347getUnsubstitutedPrimaryConstructor(), asmMethod.getReturnType(), null);
        if (this.state.getClassBuilderMode().generateBodies) {
            methodVisitorNewMethod.visitCode();
            InstructionAdapter instructionAdapter = new InstructionAdapter(methodVisitorNewMethod);
            Type typeMapType = this.typeMapper.mapType(scriptDescriptor);
            ClassDescriptor superClassNotAny = DescriptorUtilsKt.getSuperClassNotAny(scriptDescriptor);
            FrameMap frameMap = new FrameMap();
            Type OBJECT_TYPE = AsmTypes.OBJECT_TYPE;
            Intrinsics.checkNotNullExpressionValue(OBJECT_TYPE, "OBJECT_TYPE");
            frameMap.enterTemp(OBJECT_TYPE);
            boolean z = false;
            if (this.scriptContext.getScriptDescriptor().getIsReplScript()) {
                Type arrayType = AsmUtil.getArrayType(AsmTypes.OBJECT_TYPE);
                Intrinsics.checkNotNullExpressionValue(arrayType, "getArrayType(...)");
                int iEnterTemp = frameMap.enterTemp(arrayType);
                int i2 = 0;
                for (Object obj : this.scriptContext.getEarlierScripts()) {
                    int i3 = i2 + 1;
                    if (i2 < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    ScriptDescriptor scriptDescriptor2 = (ScriptDescriptor) obj;
                    genConstructor$genFieldFromArrayElement(this, typeMapType, instructionAdapter, scriptDescriptor2, iEnterTemp, i2, this.scriptContext.getScriptFieldName(scriptDescriptor2));
                    z = z;
                    i2 = i3;
                }
            }
            boolean z2 = z;
            if (superClassNotAny == null) {
                instructionAdapter.load(z2 ? 1 : 0, typeMapType);
                instructionAdapter.invokespecial(TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME, "<init>", "()V", z2);
                i = z2 ? 1 : 0;
            } else {
                ClassConstructorDescriptor classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor = superClassNotAny.mo6347getUnsubstitutedPrimaryConstructor();
                if (classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor == null) {
                    throw new RuntimeException("Primary constructor not found for script template " + superClassNotAny);
                }
                instructionAdapter.load(z2 ? 1 : 0, typeMapType);
                List<ValueParameterDescriptor> valueParameters = scriptDescriptor.mo6347getUnsubstitutedPrimaryConstructor().getValueParameters();
                Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                Iterator<ValueParameterDescriptor> it2 = classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor.getValueParameters().iterator();
                while (it2.hasNext()) {
                    ValueParameterDescriptor next = it2.next();
                    Iterator<T> it3 = valueParameters.iterator();
                    while (true) {
                        if (it3.hasNext()) {
                            valueParameterDescriptor = (ValueParameterDescriptor) it3.next();
                            list = valueParameters;
                            it = it2;
                            if (Intrinsics.areEqual(valueParameterDescriptor.getShortName(), next.getShortName())) {
                                break;
                            }
                            valueParameters = list;
                            it2 = it;
                        } else {
                            throw new NoSuchElementException("Collection contains no element matching the predicate.");
                        }
                    }
                    KotlinTypeMapper typeMapper = this.typeMapper;
                    Intrinsics.checkNotNullExpressionValue(typeMapper, "typeMapper");
                    KotlinType type = valueParameterDescriptor.getType();
                    Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                    Type typeMapType$default = KotlinTypeMapper.mapType$default(typeMapper, type, null, null, 6, null);
                    Intrinsics.checkNotNull(valueParameterDescriptor);
                    instructionAdapter.load(frameMap.enter(valueParameterDescriptor, typeMapType$default), typeMapType$default);
                    valueParameters = list;
                    it2 = it;
                }
                KotlinTypeMapper typeMapper2 = this.typeMapper;
                Intrinsics.checkNotNullExpressionValue(typeMapper2, "typeMapper");
                String descriptor = KotlinTypeMapper.mapToCallableMethod$default(typeMapper2, classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor, false, null, null, 12, null).getAsmMethod().getDescriptor();
                KotlinTypeMapper kotlinTypeMapper = this.typeMapper;
                SimpleType defaultType = superClassNotAny.getDefaultType();
                Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
                String internalName = kotlinTypeMapper.mapSupertype(defaultType, null).getInternalName();
                i = 0;
                instructionAdapter.invokespecial(internalName, "<init>", descriptor, false);
            }
            instructionAdapter.load(i, typeMapType);
            List<ClassDescriptor> implicitReceivers = scriptDescriptor.getImplicitReceivers();
            Intrinsics.checkNotNullExpressionValue(implicitReceivers, "getImplicitReceivers(...)");
            int i4 = i;
            for (Object obj2 : implicitReceivers) {
                int i5 = i4 + 1;
                if (i4 < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                ClassDescriptor classDescriptor = (ClassDescriptor) obj2;
                Intrinsics.checkNotNull(classDescriptor);
                Type arrayType2 = AsmUtil.getArrayType(AsmTypes.OBJECT_TYPE);
                Intrinsics.checkNotNullExpressionValue(arrayType2, "getArrayType(...)");
                genConstructor$genFieldFromParam(typeMapType, instructionAdapter, this.typeMapper.mapClass(classDescriptor), frameMap.enter(classDescriptor, arrayType2), this.scriptContext.getImplicitReceiverName(i4));
                i4 = i5;
            }
            List<PropertyDescriptor> scriptProvidedProperties = scriptDescriptor.getScriptProvidedProperties();
            Intrinsics.checkNotNullExpressionValue(scriptProvidedProperties, "getScriptProvidedProperties(...)");
            int i6 = i;
            for (Object obj3 : scriptProvidedProperties) {
                int i7 = i6 + 1;
                if (i6 < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                PropertyDescriptor propertyDescriptor = (PropertyDescriptor) obj3;
                KotlinTypeMapper kotlinTypeMapper2 = this.typeMapper;
                Intrinsics.checkNotNull(propertyDescriptor);
                Type typeMapType2 = kotlinTypeMapper2.mapType(propertyDescriptor);
                genConstructor$genFieldFromParam(typeMapType, instructionAdapter, typeMapType2, frameMap.enter(propertyDescriptor, typeMapType2), this.scriptContext.getProvidedPropertyName(i6));
                i6 = i7;
            }
            final ExpressionCodegen expressionCodegen = new ExpressionCodegen(methodVisitorNewMethod, frameMap, Type.VOID_TYPE, methodContext, this.state, this);
            generateInitializers(new Function0<ExpressionCodegen>() { // from class: org.jetbrains.kotlin.codegen.ScriptCodegen.genConstructor.5
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final ExpressionCodegen invoke() {
                    return expressionCodegen;
                }
            });
            instructionAdapter.areturn(Type.VOID_TYPE);
        }
        methodVisitorNewMethod.visitMaxs(-1, -1);
        methodVisitorNewMethod.visitEnd();
    }

    private static final void genConstructor$genFieldFromArrayElement(ScriptCodegen scriptCodegen, Type type, InstructionAdapter instructionAdapter, ClassDescriptor classDescriptor, int i, int i2, String str) {
        Type typeMapClass = scriptCodegen.typeMapper.mapClass(classDescriptor);
        StackValue.Local local = StackValue.local(i, AsmUtil.getArrayType(AsmTypes.OBJECT_TYPE));
        Intrinsics.checkNotNullExpressionValue(local, "local(...)");
        StackValue stackValueArrayElement = StackValue.arrayElement(AsmTypes.OBJECT_TYPE, null, local, StackValue.constant(Integer.valueOf(i2), Type.INT_TYPE));
        Intrinsics.checkNotNullExpressionValue(stackValueArrayElement, "arrayElement(...)");
        StackValue.Field field = StackValue.field(typeMapClass, type, str, false, StackValue.local(0, type));
        Intrinsics.checkNotNullExpressionValue(field, "field(...)");
        field.store(stackValueArrayElement, instructionAdapter);
    }

    private static final void genConstructor$genFieldFromParam(Type type, InstructionAdapter instructionAdapter, Type type2, int i, String str) {
        StackValue.Local local = StackValue.local(i, type2);
        Intrinsics.checkNotNullExpressionValue(local, "local(...)");
        StackValue.Field field = StackValue.field(type2, type, str, false, StackValue.local(0, type));
        Intrinsics.checkNotNullExpressionValue(field, "field(...)");
        field.store(local, instructionAdapter);
    }

    private final void genFieldsForParameters(ClassBuilder classBuilder) {
        for (ScriptDescriptor scriptDescriptor : this.scriptContext.getEarlierScripts()) {
            classBuilder.newField(JvmDeclarationOrigin.NO_ORIGIN, 17, this.scriptContext.getScriptFieldName(scriptDescriptor), this.typeMapper.mapType(scriptDescriptor).getDescriptor(), null, null);
        }
        int size = this.scriptContext.getReceiverDescriptors().size();
        for (int i = 0; i < size; i++) {
            JvmDeclarationOrigin jvmDeclarationOrigin = JvmDeclarationOrigin.NO_ORIGIN;
            String implicitReceiverName = this.scriptContext.getImplicitReceiverName(i);
            Type implicitReceiverType = this.scriptContext.getImplicitReceiverType(i);
            Intrinsics.checkNotNull(implicitReceiverType);
            classBuilder.newField(jvmDeclarationOrigin, 17, implicitReceiverName, implicitReceiverType.getDescriptor(), null, null);
        }
        int size2 = this.scriptDescriptor.getScriptProvidedProperties().size();
        for (int i2 = 0; i2 < size2; i2++) {
            classBuilder.newField(JvmDeclarationOrigin.NO_ORIGIN, 17, this.scriptContext.getProvidedPropertyName(i2), this.scriptContext.getProvidedPropertyType(i2).getDescriptor(), null, null);
        }
    }

    private final void genMembers() {
        boolean z = false;
        for (KtDeclaration ktDeclaration : this.scriptDeclaration.getDeclarations()) {
            if (ktDeclaration instanceof KtNamedFunction) {
                genSimpleMember(ktDeclaration);
                if (Intrinsics.areEqual(((KtNamedFunction) ktDeclaration).get$name(), "main")) {
                    z = true;
                }
            } else if (ktDeclaration instanceof KtProperty ? true : ktDeclaration instanceof KtTypeAlias) {
                genSimpleMember(ktDeclaration);
            } else if (ktDeclaration instanceof KtClassOrObject) {
                genClassOrObject((KtClassOrObject) ktDeclaration);
            } else if (ktDeclaration instanceof KtDestructuringDeclaration) {
                Iterator<KtDestructuringDeclarationEntry> it = ((KtDestructuringDeclaration) ktDeclaration).getEntries().iterator();
                while (it.hasNext()) {
                    genSimpleMember(it.next());
                }
            }
        }
        if (z) {
            return;
        }
        genMain();
    }

    private final void genMain() {
        Type arrayType = AsmUtil.getArrayType(AsmTypes.JAVA_STRING_TYPE);
        Intrinsics.checkNotNullExpressionValue(arrayType, "getArrayType(...)");
        String methodDescriptor = Type.getMethodDescriptor(Type.VOID_TYPE, arrayType);
        String methodDescriptor2 = Type.getMethodDescriptor(Type.VOID_TYPE, AsmTypes.JAVA_CLASS_TYPE, arrayType);
        InstructionAdapter instructionAdapter = new InstructionAdapter(this.v.newMethod(JvmDeclarationOrigin.NO_ORIGIN, 25, "main", methodDescriptor, null, null));
        instructionAdapter.visitCode();
        instructionAdapter.visitLdcInsn(this.classAsmType);
        instructionAdapter.load(0, arrayType);
        instructionAdapter.visitMethodInsn(184, "kotlin/script/experimental/jvm/RunnerKt", "runCompiledScript", methodDescriptor2, false);
        instructionAdapter.areturn(Type.VOID_TYPE);
        instructionAdapter.visitEnd();
    }

    /* JADX INFO: compiled from: ScriptCodegen.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\nH\u0007¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/ScriptCodegen$Companion;", "", "()V", "createScriptCodegen", "Lorg/jetbrains/kotlin/codegen/ScriptCodegen;", "declaration", "Lorg/jetbrains/kotlin/psi/KtScript;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "parentContext", "Lorg/jetbrains/kotlin/codegen/context/CodegenContext;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final ScriptCodegen createScriptCodegen(KtScript declaration, GenerationState state, CodegenContext<?> parentContext) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(state, "state");
            Intrinsics.checkNotNullParameter(parentContext, "parentContext");
            Object obj = state.getBindingContext().get(BindingContext.SCRIPT, declaration);
            Intrinsics.checkNotNull(obj);
            ScriptDescriptor scriptDescriptor = (ScriptDescriptor) obj;
            ClassBuilder classBuilderNewVisitor = state.getFactory().newVisitor(JvmDeclarationOriginKt.OtherOrigin(declaration, scriptDescriptor), state.getTypeMapper().mapType(scriptDescriptor), declaration.getContainingFile());
            Intrinsics.checkNotNullExpressionValue(classBuilderNewVisitor, "newVisitor(...)");
            List<ScriptDescriptor> earlierScriptsForReplInterpreter = state.getScriptSpecific().getEarlierScriptsForReplInterpreter();
            if (earlierScriptsForReplInterpreter == null) {
                earlierScriptsForReplInterpreter = CollectionsKt.emptyList();
            }
            ScriptContext scriptContextIntoScript = parentContext.intoScript(scriptDescriptor, earlierScriptsForReplInterpreter, scriptDescriptor, state.getTypeMapper());
            Intrinsics.checkNotNullExpressionValue(scriptContextIntoScript, "intoScript(...)");
            return new ScriptCodegen(declaration, state, scriptContextIntoScript, classBuilderNewVisitor, null);
        }
    }
}
