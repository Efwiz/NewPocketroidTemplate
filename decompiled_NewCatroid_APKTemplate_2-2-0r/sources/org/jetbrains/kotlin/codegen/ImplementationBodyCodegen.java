package org.jetbrains.kotlin.codegen;

import com.android.dx.rop.code.RegisterSpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.backend.common.DataClassMethodGenerator;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMap;
import org.jetbrains.kotlin.codegen.binding.CodegenBinding;
import org.jetbrains.kotlin.codegen.binding.MutableClosure;
import org.jetbrains.kotlin.codegen.context.ClassContext;
import org.jetbrains.kotlin.codegen.context.EnclosedValueDescriptor;
import org.jetbrains.kotlin.codegen.context.FieldOwnerContext;
import org.jetbrains.kotlin.codegen.context.MethodContext;
import org.jetbrains.kotlin.codegen.extensions.ExpressionCodegenExtension;
import org.jetbrains.kotlin.codegen.serialization.JvmSerializerExtension;
import org.jetbrains.kotlin.codegen.signature.BothSignatureWriter;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassOrPackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.InlineClassRepresentation;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.load.java.DescriptorsJvmAbiUtil;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.load.java.descriptors.JavaClassDescriptor;
import org.jetbrains.kotlin.load.kotlin.TypeMappingMode;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtAnonymousInitializer;
import org.jetbrains.kotlin.psi.KtArrayAccessExpression;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDelegatedSuperTypeEntry;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtEnumEntry;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPureClassOrObject;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtSuperExpression;
import org.jetbrains.kotlin.psi.KtSuperTypeCallEntry;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.psi.KtThisExpression;
import org.jetbrains.kotlin.psi.KtVisitorVoid;
import org.jetbrains.kotlin.psi.synthetics.SyntheticClassOrObjectDescriptor;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;
import org.jetbrains.kotlin.resolve.DelegationResolver;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.InlineClassDescriptorResolver;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.inline.InlineUtil;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.annotations.JvmAnnotationUtilKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKt;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmClassSignature;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExtensionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ImplicitReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.serialization.DescriptorSerializer;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingUtils;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.FieldVisitor;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: loaded from: classes3.dex */
public class ImplementationBodyCodegen extends ClassBodyCodegen {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String ENUM_VALUES_FIELD_NAME = "$VALUES";
    private static final Map<FqName, String> KOTLIN_MARKER_INTERFACES = new HashMap();
    private final List<Function2<ImplementationBodyCodegen, ClassBuilder, Unit>> additionalTasks;
    private final Type classAsmType;
    private List<PropertyAndDefaultValue> companionObjectPropertiesToCopy;
    private final ConstructorCodegen constructorCodegen;
    private final DelegationFieldsInfo delegationFieldsInfo;
    private final boolean isLocal;
    private final DescriptorSerializer serializer;
    private Type superClassAsmType;
    private SuperClassInfo superClassInfo;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 10 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 10 ? 3 : 2];
        switch (i) {
            case 1:
                objArr[0] = "context";
                break;
            case 2:
                objArr[0] = RegisterSpec.PREFIX;
                break;
            case 3:
                objArr[0] = "state";
                break;
            case 4:
            case 17:
                objArr[0] = "descriptor";
                break;
            case 5:
                objArr[0] = "classAsmType";
                break;
            case 6:
                objArr[0] = "superClassInfo";
                break;
            case 7:
                objArr[0] = "typeMapper";
                break;
            case 8:
                objArr[0] = "propertyDescriptor";
                break;
            case 9:
                objArr[0] = "classDescriptor";
                break;
            case 10:
                objArr[0] = "org/jetbrains/kotlin/codegen/ImplementationBodyCodegen";
                break;
            case 11:
                objArr[0] = "declarationDescriptor";
                break;
            case 12:
                objArr[0] = "codegen";
                break;
            case 13:
            case 14:
                objArr[0] = "enumEntries";
                break;
            case 15:
                objArr[0] = "toInterface";
                break;
            case 16:
                objArr[0] = "field";
                break;
            default:
                objArr[0] = "aClass";
                break;
        }
        if (i != 10) {
            objArr[1] = "org/jetbrains/kotlin/codegen/ImplementationBodyCodegen";
        } else {
            objArr[1] = "getPrimaryConstructorOfDataClass";
        }
        switch (i) {
            case 4:
            case 5:
            case 6:
            case 7:
                objArr[2] = "signature";
                break;
            case 8:
                objArr[2] = "genPropertyOnStack";
                break;
            case 9:
                objArr[2] = "getPrimaryConstructorOfDataClass";
                break;
            case 10:
                break;
            case 11:
                objArr[2] = "isInterfaceCompanionWithBackingFieldsInOuter";
                break;
            case 12:
                objArr[2] = "generateInitializers";
                break;
            case 13:
                objArr[2] = "initializeEnumConstants";
                break;
            case 14:
                objArr[2] = "initializeEnumConstant";
                break;
            case 15:
            case 16:
                objArr[2] = "generateDelegates";
                break;
            case 17:
                objArr[2] = "addCompanionObjectPropertyToCopy";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i == 10) {
            throw new IllegalStateException(str2);
        }
    }

    static /* synthetic */ ExpressionCodegen lambda$generateInitializers$3(ExpressionCodegen expressionCodegen) {
        return expressionCodegen;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ImplementationBodyCodegen(KtPureClassOrObject ktPureClassOrObject, ClassContext classContext, ClassBuilder classBuilder, GenerationState generationState, MemberCodegen<?> memberCodegen, boolean z) {
        super(ktPureClassOrObject, classContext, classBuilder, generationState, memberCodegen);
        if (ktPureClassOrObject == null) {
            $$$reportNull$$$0(0);
        }
        if (classContext == null) {
            $$$reportNull$$$0(1);
        }
        if (classBuilder == null) {
            $$$reportNull$$$0(2);
        }
        if (generationState == null) {
            $$$reportNull$$$0(3);
        }
        this.additionalTasks = new ArrayList();
        Type objectType = Type.getObjectType(this.typeMapper.classInternalName(this.descriptor));
        this.classAsmType = objectType;
        this.isLocal = z;
        this.delegationFieldsInfo = new DelegationFieldsInfo(objectType, this.descriptor, generationState, this.bindingContext).getDelegationFieldsInfo(this.myClass.getSuperTypeListEntries());
        JvmSerializerExtension jvmSerializerExtension = new JvmSerializerExtension(classBuilder.getSerializationBindings(), generationState);
        this.serializer = DescriptorSerializer.create(this.descriptor, jvmSerializerExtension, memberCodegen instanceof ImplementationBodyCodegen ? ((ImplementationBodyCodegen) memberCodegen).serializer : DescriptorSerializer.createTopLevel(jvmSerializerExtension, generationState.getLanguageVersionSettings(), null), generationState.getLanguageVersionSettings(), generationState.getProject());
        this.constructorCodegen = new ConstructorCodegen(this.descriptor, classContext, this.functionCodegen, this, this, generationState, this.kind, classBuilder, objectType, this.myClass, this.bindingContext);
    }

    /* JADX WARN: Code duplicated, block: B:39:0x008f  */
    /* JADX WARN: Code duplicated, block: B:41:0x0097  */
    /* JADX WARN: Code duplicated, block: B:43:0x009b  */
    /* JADX WARN: Code duplicated, block: B:44:0x009e  */
    /* JADX WARN: Code duplicated, block: B:46:0x00a2  */
    /* JADX WARN: Code duplicated, block: B:48:0x00a6  */
    /* JADX WARN: Code duplicated, block: B:51:0x00b0  */
    /* JADX WARN: Code duplicated, block: B:53:0x00b5  */
    /* JADX WARN: Code duplicated, block: B:56:0x00c5  */
    /* JADX WARN: Code duplicated, block: B:64:0x00e6  */
    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    /* JADX INFO: renamed from: generateDeclaration */
    protected void mo6420generateDeclaration() {
        boolean z;
        boolean z2;
        boolean z3;
        int visibilityAccessFlagForClass;
        int i;
        SuperClassInfo superClassInfo = SuperClassInfo.getSuperClassInfo(this.descriptor, this.typeMapper);
        this.superClassInfo = superClassInfo;
        this.superClassAsmType = superClassInfo.getType();
        JvmClassSignature jvmClassSignatureSignature = signature();
        ClassKind kind = this.descriptor.getClassKind();
        Modality modality = this.descriptor.getModality();
        boolean z4 = true;
        boolean z5 = false;
        boolean zHasAbstractMembers = modality == Modality.ABSTRACT || modality == Modality.SEALED;
        if (kind != ClassKind.INTERFACE) {
            if (kind == ClassKind.ANNOTATION_CLASS) {
                zHasAbstractMembers = true;
                z = true;
                z2 = true;
                z3 = false;
            } else if (kind == ClassKind.ENUM_CLASS) {
                zHasAbstractMembers = DescriptorUtils.hasAbstractMembers(this.descriptor);
                z3 = true;
                z = false;
                z2 = false;
            } else {
                z = false;
                z2 = false;
            }
            if (modality != Modality.OPEN && !zHasAbstractMembers) {
                if (kind != ClassKind.OBJECT && kind == ClassKind.ENUM_CLASS && !this.state.getClassBuilderMode().generateBodies) {
                    z4 = false;
                }
                z5 = z4;
            }
            if (this.state.getClassBuilderMode() != ClassBuilderMode.LIGHT_CLASSES && !DescriptorUtils.isTopLevelDeclaration(this.descriptor)) {
                visibilityAccessFlagForClass = DescriptorAsmUtil.getVisibilityAccessFlag(this.descriptor);
                if (!this.descriptor.getIsInner()) {
                    visibilityAccessFlagForClass |= 8;
                }
            } else {
                visibilityAccessFlagForClass = DescriptorAsmUtil.getVisibilityAccessFlagForClass(this.descriptor);
            }
            if (zHasAbstractMembers) {
                visibilityAccessFlagForClass |= 1024;
            }
            if (z) {
                i = visibilityAccessFlagForClass | 512;
            } else {
                i = visibilityAccessFlagForClass | 32;
            }
            if (z5) {
                i |= 16;
            }
            if (z2) {
                i |= 8192;
            }
            if (KotlinBuiltIns.isDeprecated(this.descriptor)) {
                i |= 131072;
            }
            if (z3) {
                for (KtDeclaration ktDeclaration : this.myClass.getDeclarations()) {
                    if (!(ktDeclaration instanceof KtEnumEntry) && CodegenBinding.enumEntryNeedSubclass(this.bindingContext, (KtEnumEntry) ktDeclaration)) {
                        i &= -17;
                    }
                }
                i |= 16384;
            }
            if (JvmAnnotationUtilKt.isJvmRecord(this.descriptor)) {
                i |= 65536;
            }
            this.v.defineClass(this.myClass.getPsiOrParent(), this.state.getConfig().getClassFileVersion(), i, jvmClassSignatureSignature.getName(), jvmClassSignatureSignature.getJavaGenericSignature(), jvmClassSignatureSignature.getSuperclassName(), ArrayUtil.toStringArray(jvmClassSignatureSignature.getInterfaces()));
            this.v.visitSource(this.myClass.getContainingKtFile().get$name(), null);
            initDefaultSourceMappingIfNeeded();
            writeEnclosingMethod();
            AnnotationCodegen.forClass(this.v.getVisitor(), this, this.state).genAnnotations(this.descriptor, null, null);
            generateEnumEntries();
        }
        zHasAbstractMembers = true;
        z = true;
        z2 = false;
        z3 = z2;
        if (modality != Modality.OPEN) {
            if (kind != ClassKind.OBJECT) {
                z4 = false;
            }
            z5 = z4;
        }
        if (this.state.getClassBuilderMode() != ClassBuilderMode.LIGHT_CLASSES) {
            visibilityAccessFlagForClass = DescriptorAsmUtil.getVisibilityAccessFlagForClass(this.descriptor);
        } else {
            visibilityAccessFlagForClass = DescriptorAsmUtil.getVisibilityAccessFlagForClass(this.descriptor);
        }
        if (zHasAbstractMembers) {
            visibilityAccessFlagForClass |= 1024;
        }
        if (z) {
            i = visibilityAccessFlagForClass | 512;
        } else {
            i = visibilityAccessFlagForClass | 32;
        }
        if (z5) {
            i |= 16;
        }
        if (z2) {
            i |= 8192;
        }
        if (KotlinBuiltIns.isDeprecated(this.descriptor)) {
            i |= 131072;
        }
        if (z3) {
            while (r2.hasNext()) {
                if (!(ktDeclaration instanceof KtEnumEntry)) {
                }
            }
            i |= 16384;
        }
        if (JvmAnnotationUtilKt.isJvmRecord(this.descriptor)) {
            i |= 65536;
        }
        this.v.defineClass(this.myClass.getPsiOrParent(), this.state.getConfig().getClassFileVersion(), i, jvmClassSignatureSignature.getName(), jvmClassSignatureSignature.getJavaGenericSignature(), jvmClassSignatureSignature.getSuperclassName(), ArrayUtil.toStringArray(jvmClassSignatureSignature.getInterfaces()));
        this.v.visitSource(this.myClass.getContainingKtFile().get$name(), null);
        initDefaultSourceMappingIfNeeded();
        writeEnclosingMethod();
        AnnotationCodegen.forClass(this.v.getVisitor(), this, this.state).genAnnotations(this.descriptor, null, null);
        generateEnumEntries();
    }

    @Override // org.jetbrains.kotlin.codegen.ClassBodyCodegen
    protected void generateDefaultImplsIfNeeded() {
        if (!DescriptorUtils.isInterface(this.descriptor) || this.isLocal) {
            return;
        }
        ClassBuilder classBuilderNewVisitor = this.state.getFactory().newVisitor(JvmDeclarationOriginKt.DefaultImpls(this.myClass.getPsiOrParent(), this.descriptor), this.state.getTypeMapper().mapDefaultImpls(this.descriptor), this.myClass.getContainingKtFile());
        new InterfaceImplBodyCodegen(this.myClass, this.context.getParentContext().intoDefaultImplsClass(this.descriptor, (ClassContext) this.context, this.state), classBuilderNewVisitor, this.state, this).generate();
    }

    @Override // org.jetbrains.kotlin.codegen.ClassBodyCodegen
    protected void generateErasedInlineClassIfNeeded() {
        if ((this.myClass instanceof KtClass) && InlineClassesUtilsKt.isInlineClass(this.descriptor)) {
            new ErasedInlineClassBodyCodegen((KtClass) this.myClass, this.context.intoWrapperForErasedInlineClass(this.descriptor, this.state), this.v, this.state, this).generate();
        }
    }

    @Override // org.jetbrains.kotlin.codegen.ClassBodyCodegen
    protected void generateUnboxMethodForInlineClass() {
        final InlineClassRepresentation<SimpleType> inlineClassRepresentation;
        if ((this.myClass instanceof KtClass) && (inlineClassRepresentation = DescriptorUtilsKt.getInlineClassRepresentation(this.descriptor)) != null) {
            final Type typeMapClass = this.typeMapper.mapClass(this.descriptor);
            final Type typeMapType = this.typeMapper.mapType((KotlinType) inlineClassRepresentation.getUnderlyingType());
            SimpleFunctionDescriptor simpleFunctionDescriptorCreateUnboxFunctionDescriptor = InlineClassDescriptorResolver.createUnboxFunctionDescriptor(this.descriptor);
            this.functionCodegen.generateMethod(JvmDeclarationOriginKt.UnboxMethodOfInlineClass(simpleFunctionDescriptorCreateUnboxFunctionDescriptor), simpleFunctionDescriptorCreateUnboxFunctionDescriptor, new FunctionGenerationStrategy.CodegenBased(this.state) { // from class: org.jetbrains.kotlin.codegen.ImplementationBodyCodegen.1
                private static /* synthetic */ void $$$reportNull$$$0(int i) {
                    Object[] objArr = new Object[3];
                    if (i != 1) {
                        objArr[0] = "codegen";
                    } else {
                        objArr[0] = "signature";
                    }
                    objArr[1] = "org/jetbrains/kotlin/codegen/ImplementationBodyCodegen$1";
                    objArr[2] = "doGenerateBody";
                    throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
                }

                @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy.CodegenBased
                public void doGenerateBody(ExpressionCodegen expressionCodegen, JvmMethodSignature jvmMethodSignature) {
                    if (expressionCodegen == null) {
                        $$$reportNull$$$0(0);
                    }
                    if (jvmMethodSignature == null) {
                        $$$reportNull$$$0(1);
                    }
                    InstructionAdapter instructionAdapter = expressionCodegen.v;
                    instructionAdapter.load(0, AsmTypes.OBJECT_TYPE);
                    instructionAdapter.getfield(typeMapClass.getInternalName(), inlineClassRepresentation.getUnderlyingPropertyName().asString(), typeMapType.getDescriptor());
                    instructionAdapter.areturn(typeMapType);
                }
            });
        }
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    /* JADX INFO: renamed from: generateKotlinMetadataAnnotation */
    protected void mo6421generateKotlinMetadataAnnotation() {
        final ProtoBuf.Class classBuild = this.serializer.classProto(this.descriptor).build();
        WriteAnnotationUtilKt.writeKotlinMetadata(this.v, this.state, KotlinClassHeader.Kind.CLASS, InlineUtil.isInPublicInlineScope(this.descriptor), 0, new Function1() { // from class: org.jetbrains.kotlin.codegen.ImplementationBodyCodegen$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m6417x333c9560(classBuild, (AnnotationVisitor) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$generateKotlinMetadataAnnotation$0$org-jetbrains-kotlin-codegen-ImplementationBodyCodegen, reason: not valid java name */
    /* synthetic */ Unit m6417x333c9560(ProtoBuf.Class r2, AnnotationVisitor annotationVisitor) {
        DescriptorAsmUtil.writeAnnotationData(annotationVisitor, this.serializer, r2);
        return Unit.INSTANCE;
    }

    private void writeEnclosingMethod() {
        if (this.state.getClassBuilderMode().generateBodies) {
            if (DescriptorUtils.isAnonymousObject(this.descriptor) || !(this.descriptor.getContainingModule() instanceof ClassOrPackageFragmentDescriptor)) {
                writeOuterClassAndEnclosingMethod();
            }
        }
    }

    static {
        for (JavaToKotlinClassMap.PlatformMutabilityMapping platformMutabilityMapping : JavaToKotlinClassMap.INSTANCE.getMutabilityMappings()) {
            Map<FqName, String> map = KOTLIN_MARKER_INTERFACES;
            map.put(platformMutabilityMapping.getKotlinReadOnly().asSingleFqName(), "kotlin/jvm/internal/markers/KMappedMarker");
            ClassId kotlinMutable = platformMutabilityMapping.getKotlinMutable();
            map.put(kotlinMutable.asSingleFqName(), "kotlin/jvm/internal/markers/K" + kotlinMutable.getRelativeClassName().asString().replace("MutableEntry", "Entry").replace(".", "$"));
        }
    }

    private JvmClassSignature signature() {
        return signature(this.descriptor, this.classAsmType, this.superClassInfo, this.typeMapper);
    }

    public static JvmClassSignature signature(ClassDescriptor classDescriptor, Type type, SuperClassInfo superClassInfo, KotlinTypeMapper kotlinTypeMapper) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(4);
        }
        if (type == null) {
            $$$reportNull$$$0(5);
        }
        if (superClassInfo == null) {
            $$$reportNull$$$0(6);
        }
        if (kotlinTypeMapper == null) {
            $$$reportNull$$$0(7);
        }
        BothSignatureWriter bothSignatureWriter = new BothSignatureWriter(BothSignatureWriter.Mode.CLASS);
        kotlinTypeMapper.writeFormalTypeParameters(classDescriptor.getDeclaredTypeParameters(), bothSignatureWriter);
        bothSignatureWriter.writeSuperclass();
        if (superClassInfo.getKotlinType() == null) {
            bothSignatureWriter.writeClassBegin(superClassInfo.getType());
            bothSignatureWriter.writeClassEnd();
        } else {
            kotlinTypeMapper.mapSupertype(superClassInfo.getKotlinType(), bothSignatureWriter);
        }
        bothSignatureWriter.writeSuperclassEnd();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        LinkedHashSet<String> linkedHashSet2 = new LinkedHashSet();
        for (KotlinType kotlinType : classDescriptor.getTypeConstructor().mo7438getSupertypes()) {
            if (JvmCodegenUtil.isJvmInterface(kotlinType.getConstructor().getThis$0())) {
                FqName safe = DescriptorUtils.getFqName(kotlinType.getConstructor().getThis$0()).toSafe();
                bothSignatureWriter.writeInterface();
                Type typeMapSupertype = kotlinTypeMapper.mapSupertype(kotlinType, bothSignatureWriter);
                bothSignatureWriter.writeInterfaceEnd();
                linkedHashSet.add(typeMapSupertype.getInternalName());
                String str = KOTLIN_MARKER_INTERFACES.get(safe);
                if (str != null) {
                    if (kotlinTypeMapper.getClassBuilderMode() == ClassBuilderMode.LIGHT_CLASSES) {
                        bothSignatureWriter.writeInterface();
                        Type typeMapType = kotlinTypeMapper.mapType(kotlinType, bothSignatureWriter, TypeMappingMode.SUPER_TYPE_KOTLIN_COLLECTIONS_AS_IS);
                        bothSignatureWriter.writeInterfaceEnd();
                        linkedHashSet.add(typeMapType.getInternalName());
                    }
                    linkedHashSet2.add(str);
                }
            }
        }
        for (String str2 : linkedHashSet2) {
            bothSignatureWriter.writeInterface();
            bothSignatureWriter.writeAsmType(Type.getObjectType(str2));
            bothSignatureWriter.writeInterfaceEnd();
        }
        linkedHashSet.addAll(linkedHashSet2);
        return new JvmClassSignature(type.getInternalName(), superClassInfo.getType().getInternalName(), new ArrayList(linkedHashSet), bothSignatureWriter.makeJavaGenericSignature());
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    protected void generateSyntheticPartsBeforeBody() {
        generatePropertyMetadataArrayFieldIfNeeded(this.classAsmType);
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    protected void generateSyntheticPartsAfterBody() {
        generateFieldForSingleton();
        initializeObjects();
        generateCompanionObjectBackingFieldCopies();
        generateDelegatesToDefaultImpl();
        generateDelegates(this.delegationFieldsInfo);
        generateSyntheticAccessors();
        generateEnumMethods();
        generateFunctionsForDataClasses();
        generateFunctionsFromAnyForInlineClasses();
        if (this.state.getClassBuilderMode() != ClassBuilderMode.LIGHT_CLASSES) {
            new CollectionStubMethodGenerator(this.typeMapper, this.descriptor).generate(this.functionCodegen, this.v);
            generateToArray();
        }
        if (this.context.closure != null) {
            DescriptorAsmUtil.genClosureFields(this.context.closure, this.v, this.typeMapper, this.state.getLanguageVersionSettings());
        }
        for (ExpressionCodegenExtension expressionCodegenExtension : ExpressionCodegenExtension.INSTANCE.getInstances(this.state.getProject())) {
            if (this.state.getClassBuilderMode() != ClassBuilderMode.LIGHT_CLASSES || expressionCodegenExtension.getShouldGenerateClassSyntheticPartsInLightClassesMode()) {
                expressionCodegenExtension.generateClassSyntheticParts(this);
            }
        }
    }

    @Override // org.jetbrains.kotlin.codegen.ClassBodyCodegen
    protected void generateConstructors() {
        try {
            lookupConstructorExpressionsInClosureIfPresent();
            this.constructorCodegen.generatePrimaryConstructor(this.delegationFieldsInfo, this.superClassAsmType);
            if (InlineClassesUtilsKt.isInlineClass(this.descriptor) || (this.descriptor instanceof SyntheticClassOrObjectDescriptor)) {
                return;
            }
            Iterator<ClassConstructorDescriptor> it = DescriptorUtilsKt.getSecondaryConstructors(this.descriptor).iterator();
            while (it.hasNext()) {
                this.constructorCodegen.generateSecondaryConstructor(it.next(), this.superClassAsmType);
            }
        } catch (CompilationException e) {
            throw e;
        } catch (ProcessCanceledException e2) {
        } catch (RuntimeException e3) {
            throw new RuntimeException("Error generating constructors of class " + this.myClass.get_name() + " with kind " + this.kind, e3);
        }
    }

    private void generateToArray() {
        if (this.descriptor.getClassKind() == ClassKind.INTERFACE) {
            return;
        }
        final KotlinBuiltIns builtIns = DescriptorUtilsKt.getBuiltIns(this.descriptor);
        if (DescriptorUtils.isSubclass(this.descriptor, builtIns.getCollection()) && !CollectionsKt.any(DescriptorUtilsKt.getAllSuperclassesWithoutAny(this.descriptor), new Function1() { // from class: org.jetbrains.kotlin.codegen.ImplementationBodyCodegen$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                ClassDescriptor classDescriptor = (ClassDescriptor) obj;
                return Boolean.valueOf(!(classDescriptor instanceof JavaClassDescriptor) && DescriptorUtils.isSubclass(classDescriptor, builtIns.getCollection()));
            }
        })) {
            boolean zIsNonGenericToArray = false;
            boolean zIsGenericToArray = false;
            for (SimpleFunctionDescriptor simpleFunctionDescriptor : this.descriptor.getDefaultType().getMemberScope().getContributedFunctions(Name.identifier("toArray"), NoLookupLocation.FROM_BACKEND)) {
                zIsGenericToArray |= CodegenUtilKt.isGenericToArray(simpleFunctionDescriptor);
                zIsNonGenericToArray |= CodegenUtilKt.isNonGenericToArray(simpleFunctionDescriptor);
            }
            if (!zIsNonGenericToArray) {
                MethodVisitor methodVisitorNewMethod = this.v.newMethod(JvmDeclarationOrigin.NO_ORIGIN, 1, "toArray", "()[Ljava/lang/Object;", null, null);
                InstructionAdapter instructionAdapter = new InstructionAdapter(methodVisitorNewMethod);
                methodVisitorNewMethod.visitCode();
                instructionAdapter.load(0, this.classAsmType);
                instructionAdapter.invokestatic("kotlin/jvm/internal/CollectionToArray", "toArray", "(Ljava/util/Collection;)[Ljava/lang/Object;", false);
                instructionAdapter.areturn(Type.getType("[Ljava/lang/Object;"));
                FunctionCodegen.endVisit(methodVisitorNewMethod, "toArray", this.myClass);
            }
            if (zIsGenericToArray) {
                return;
            }
            MethodVisitor methodVisitorNewMethod2 = this.v.newMethod(JvmDeclarationOrigin.NO_ORIGIN, 1, "toArray", "([Ljava/lang/Object;)[Ljava/lang/Object;", "<T:Ljava/lang/Object;>([TT;)[TT;", null);
            InstructionAdapter instructionAdapter2 = new InstructionAdapter(methodVisitorNewMethod2);
            methodVisitorNewMethod2.visitCode();
            instructionAdapter2.load(0, this.classAsmType);
            instructionAdapter2.load(1, Type.getType("[Ljava/lang/Object;"));
            instructionAdapter2.invokestatic("kotlin/jvm/internal/CollectionToArray", "toArray", "(Ljava/util/Collection;[Ljava/lang/Object;)[Ljava/lang/Object;", false);
            instructionAdapter2.areturn(Type.getType("[Ljava/lang/Object;"));
            FunctionCodegen.endVisit(methodVisitorNewMethod2, "toArray", this.myClass);
        }
    }

    public static JvmKotlinType genPropertyOnStack(InstructionAdapter instructionAdapter, MethodContext methodContext, PropertyDescriptor propertyDescriptor, Type type, int i, GenerationState generationState) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(8);
        }
        instructionAdapter.load(i, type);
        if (JvmCodegenUtil.couldUseDirectAccessToProperty(propertyDescriptor, true, false, methodContext, generationState.getConfig().getShouldInlineConstVals())) {
            KotlinType type2 = propertyDescriptor.getType();
            Type typeMapType = generationState.getTypeMapper().mapType(type2);
            instructionAdapter.getfield(type.getInternalName(), ((FieldOwnerContext) methodContext.getParentContext()).getFieldName(propertyDescriptor, false), typeMapType.getDescriptor());
            return new JvmKotlinType(typeMapType, type2);
        }
        PropertyGetterDescriptor getter = propertyDescriptor.getGetter();
        Method methodMapAsmMethod = generationState.getTypeMapper().mapAsmMethod(getter);
        instructionAdapter.invokevirtual(type.getInternalName(), methodMapAsmMethod.getName(), methodMapAsmMethod.getDescriptor(), false);
        return new JvmKotlinType(methodMapAsmMethod.getReturnType(), getter.getReturnType());
    }

    private void generateFunctionsForDataClasses() {
        if (this.descriptor.isData() && (this.myClass instanceof KtClassOrObject)) {
            new DataClassMethodGeneratorImpl((KtClassOrObject) this.myClass, this.bindingContext).generate();
        }
    }

    private void generateFunctionsFromAnyForInlineClasses() {
        if (InlineClassesUtilsKt.isInlineClass(this.descriptor) && (this.myClass instanceof KtClassOrObject)) {
            new FunctionsFromAnyGeneratorImpl((KtClassOrObject) this.myClass, this.bindingContext, this.descriptor, this.classAsmType, this.context, this.v, this.state).generate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class DataClassMethodGeneratorImpl extends DataClassMethodGenerator {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final FunctionsFromAnyGeneratorImpl functionsFromAnyGenerator;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1 || i == 3 || i == 5) {
                objArr[0] = "properties";
            } else if (i == 7) {
                objArr[0] = "parameter";
            } else if (i != 9) {
                objArr[0] = "function";
            } else {
                objArr[0] = "constructorParameters";
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/ImplementationBodyCodegen$DataClassMethodGeneratorImpl";
            switch (i) {
                case 2:
                case 3:
                    objArr[2] = "generateHashCodeMethod";
                    break;
                case 4:
                case 5:
                    objArr[2] = "generateToStringMethod";
                    break;
                case 6:
                case 7:
                    objArr[2] = "generateComponentFunction";
                    break;
                case 8:
                case 9:
                    objArr[2] = "generateCopyFunction";
                    break;
                default:
                    objArr[2] = "generateEqualsMethod";
                    break;
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        DataClassMethodGeneratorImpl(KtClassOrObject ktClassOrObject, BindingContext bindingContext) {
            super(ktClassOrObject, bindingContext);
            this.functionsFromAnyGenerator = new FunctionsFromAnyGeneratorImpl(ktClassOrObject, bindingContext, ImplementationBodyCodegen.this.descriptor, ImplementationBodyCodegen.this.classAsmType, ImplementationBodyCodegen.this.context, ImplementationBodyCodegen.this.v, ImplementationBodyCodegen.this.state);
        }

        @Override // org.jetbrains.kotlin.backend.common.FunctionsFromAnyGenerator
        public void generateEqualsMethod(FunctionDescriptor functionDescriptor, List<? extends PropertyDescriptor> list) {
            if (functionDescriptor == null) {
                $$$reportNull$$$0(0);
            }
            if (list == null) {
                $$$reportNull$$$0(1);
            }
            this.functionsFromAnyGenerator.generateEqualsMethod(functionDescriptor, list);
        }

        @Override // org.jetbrains.kotlin.backend.common.FunctionsFromAnyGenerator
        public void generateHashCodeMethod(FunctionDescriptor functionDescriptor, List<? extends PropertyDescriptor> list) {
            if (functionDescriptor == null) {
                $$$reportNull$$$0(2);
            }
            if (list == null) {
                $$$reportNull$$$0(3);
            }
            this.functionsFromAnyGenerator.generateHashCodeMethod(functionDescriptor, list);
        }

        @Override // org.jetbrains.kotlin.backend.common.FunctionsFromAnyGenerator
        public void generateToStringMethod(FunctionDescriptor functionDescriptor, List<? extends PropertyDescriptor> list) {
            if (functionDescriptor == null) {
                $$$reportNull$$$0(4);
            }
            if (list == null) {
                $$$reportNull$$$0(5);
            }
            this.functionsFromAnyGenerator.generateToStringMethod(functionDescriptor, list);
        }

        @Override // org.jetbrains.kotlin.backend.common.DataClassMethodGenerator
        public void generateComponentFunction(FunctionDescriptor functionDescriptor, final ValueParameterDescriptor valueParameterDescriptor) {
            if (functionDescriptor == null) {
                $$$reportNull$$$0(6);
            }
            if (valueParameterDescriptor == null) {
                $$$reportNull$$$0(7);
            }
            ImplementationBodyCodegen.this.functionCodegen.generateMethod(JvmDeclarationOriginKt.OtherOrigin(DescriptorToSourceUtils.descriptorToDeclaration(valueParameterDescriptor), functionDescriptor), functionDescriptor, new FunctionGenerationStrategy() { // from class: org.jetbrains.kotlin.codegen.ImplementationBodyCodegen.DataClassMethodGeneratorImpl.1
                static final /* synthetic */ boolean $assertionsDisabled = false;

                private static /* synthetic */ void $$$reportNull$$$0(int i) {
                    Object[] objArr = new Object[3];
                    if (i == 1) {
                        objArr[0] = "frameMap";
                    } else if (i == 2) {
                        objArr[0] = "signature";
                    } else if (i == 3) {
                        objArr[0] = "context";
                    } else if (i != 4) {
                        objArr[0] = JvmAnnotationNames.METADATA_VERSION_FIELD_NAME;
                    } else {
                        objArr[0] = "parentCodegen";
                    }
                    objArr[1] = "org/jetbrains/kotlin/codegen/ImplementationBodyCodegen$DataClassMethodGeneratorImpl$1";
                    objArr[2] = "generateBody";
                    throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
                }

                @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy
                public boolean skipNotNullAssertionsForParameters() {
                    return false;
                }

                @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy
                public void generateBody(MethodVisitor methodVisitor, FrameMap frameMap, JvmMethodSignature jvmMethodSignature, MethodContext methodContext, MemberCodegen<?> memberCodegen) {
                    if (methodVisitor == null) {
                        $$$reportNull$$$0(0);
                    }
                    if (frameMap == null) {
                        $$$reportNull$$$0(1);
                    }
                    if (jvmMethodSignature == null) {
                        $$$reportNull$$$0(2);
                    }
                    if (methodContext == null) {
                        $$$reportNull$$$0(3);
                    }
                    if (memberCodegen == null) {
                        $$$reportNull$$$0(4);
                    }
                    Type returnType = jvmMethodSignature.getReturnType();
                    InstructionAdapter instructionAdapter = new InstructionAdapter(methodVisitor);
                    if (!returnType.equals(Type.VOID_TYPE)) {
                        StackValue.coerce(ImplementationBodyCodegen.genPropertyOnStack(instructionAdapter, methodContext, (PropertyDescriptor) ImplementationBodyCodegen.this.bindingContext.get(BindingContext.PRIMARY_CONSTRUCTOR_PARAMETER, DescriptorToSourceUtils.descriptorToDeclaration(valueParameterDescriptor)), ImplementationBodyCodegen.this.classAsmType, 0, ImplementationBodyCodegen.this.state).getType(), returnType, instructionAdapter);
                    }
                    instructionAdapter.areturn(returnType);
                }
            });
        }

        @Override // org.jetbrains.kotlin.backend.common.DataClassMethodGenerator
        public void generateCopyFunction(final FunctionDescriptor functionDescriptor, List<? extends KtParameter> list) {
            if (functionDescriptor == null) {
                $$$reportNull$$$0(8);
            }
            if (list == null) {
                $$$reportNull$$$0(9);
            }
            final Type typeMapType = ImplementationBodyCodegen.this.typeMapper.mapType(ImplementationBodyCodegen.this.descriptor);
            ImplementationBodyCodegen.this.functionCodegen.generateMethod(JvmDeclarationOriginKt.OtherOriginFromPure(ImplementationBodyCodegen.this.myClass, functionDescriptor), functionDescriptor, new FunctionGenerationStrategy() { // from class: org.jetbrains.kotlin.codegen.ImplementationBodyCodegen.DataClassMethodGeneratorImpl.2
                static final /* synthetic */ boolean $assertionsDisabled = false;

                private static /* synthetic */ void $$$reportNull$$$0(int i) {
                    Object[] objArr = new Object[3];
                    if (i == 1) {
                        objArr[0] = "frameMap";
                    } else if (i == 2) {
                        objArr[0] = "signature";
                    } else if (i == 3) {
                        objArr[0] = "context";
                    } else if (i != 4) {
                        objArr[0] = JvmAnnotationNames.METADATA_VERSION_FIELD_NAME;
                    } else {
                        objArr[0] = "parentCodegen";
                    }
                    objArr[1] = "org/jetbrains/kotlin/codegen/ImplementationBodyCodegen$DataClassMethodGeneratorImpl$2";
                    objArr[2] = "generateBody";
                    throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
                }

                @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy
                public boolean skipNotNullAssertionsForParameters() {
                    return false;
                }

                @Override // org.jetbrains.kotlin.codegen.FunctionGenerationStrategy
                public void generateBody(MethodVisitor methodVisitor, FrameMap frameMap, JvmMethodSignature jvmMethodSignature, MethodContext methodContext, MemberCodegen<?> memberCodegen) {
                    if (methodVisitor == null) {
                        $$$reportNull$$$0(0);
                    }
                    int size = 1;
                    if (frameMap == null) {
                        $$$reportNull$$$0(1);
                    }
                    if (jvmMethodSignature == null) {
                        $$$reportNull$$$0(2);
                    }
                    if (methodContext == null) {
                        $$$reportNull$$$0(3);
                    }
                    if (memberCodegen == null) {
                        $$$reportNull$$$0(4);
                    }
                    InstructionAdapter instructionAdapter = new InstructionAdapter(methodVisitor);
                    instructionAdapter.anew(typeMapType);
                    instructionAdapter.dup();
                    ConstructorDescriptor primaryConstructorOfDataClass = ImplementationBodyCodegen.getPrimaryConstructorOfDataClass(ImplementationBodyCodegen.this.descriptor);
                    MutableClosure mutableClosure = ImplementationBodyCodegen.this.context.closure;
                    if (mutableClosure != null) {
                        pushCapturedFieldsOnStack(instructionAdapter, mutableClosure);
                    }
                    Iterator<ValueParameterDescriptor> it = functionDescriptor.getValueParameters().iterator();
                    while (it.hasNext()) {
                        Type typeMapType2 = ImplementationBodyCodegen.this.typeMapper.mapType(it.next().getType());
                        instructionAdapter.load(size, typeMapType2);
                        size += typeMapType2.getSize();
                    }
                    instructionAdapter.invokespecial(typeMapType.getInternalName(), "<init>", ImplementationBodyCodegen.this.typeMapper.mapAsmMethod(primaryConstructorOfDataClass).getDescriptor(), false);
                    instructionAdapter.areturn(typeMapType);
                }

                private void pushCapturedFieldsOnStack(InstructionAdapter instructionAdapter, MutableClosure mutableClosure) {
                    ClassDescriptor capturedOuterClassDescriptor = mutableClosure.getCapturedOuterClassDescriptor();
                    if (capturedOuterClassDescriptor != null) {
                        instructionAdapter.load(0, ImplementationBodyCodegen.this.classAsmType);
                        instructionAdapter.getfield(ImplementationBodyCodegen.this.classAsmType.getInternalName(), AsmUtil.CAPTURED_THIS_FIELD, ImplementationBodyCodegen.this.typeMapper.mapType(capturedOuterClassDescriptor).getDescriptor());
                    }
                    KotlinType capturedReceiverFromOuterContext = mutableClosure.getCapturedReceiverFromOuterContext();
                    if (capturedReceiverFromOuterContext != null) {
                        instructionAdapter.load(0, ImplementationBodyCodegen.this.classAsmType);
                        Type typeMapType2 = ImplementationBodyCodegen.this.typeMapper.mapType(capturedReceiverFromOuterContext);
                        instructionAdapter.getfield(ImplementationBodyCodegen.this.classAsmType.getInternalName(), mutableClosure.getCapturedReceiverFieldName(ImplementationBodyCodegen.this.bindingContext, ImplementationBodyCodegen.this.state.getLanguageVersionSettings()), typeMapType2.getDescriptor());
                    }
                    for (Map.Entry<DeclarationDescriptor, EnclosedValueDescriptor> entry : mutableClosure.getCaptureVariables().entrySet()) {
                        DeclarationDescriptor key = entry.getKey();
                        StackValue instanceValue = entry.getValue().getInstanceValue();
                        Type sharedVarType = ImplementationBodyCodegen.this.typeMapper.getSharedVarType(key);
                        if (sharedVarType == null) {
                            sharedVarType = ImplementationBodyCodegen.this.typeMapper.mapType((VariableDescriptor) key);
                        }
                        instanceValue.put(sharedVarType, instructionAdapter);
                    }
                }
            });
            ImplementationBodyCodegen.this.functionCodegen.generateDefaultIfNeeded(ImplementationBodyCodegen.this.context.intoFunction(functionDescriptor), functionDescriptor, OwnerKind.IMPLEMENTATION, new DefaultParameterValueLoader() { // from class: org.jetbrains.kotlin.codegen.ImplementationBodyCodegen$DataClassMethodGeneratorImpl$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.codegen.DefaultParameterValueLoader
                public final StackValue genValue(ValueParameterDescriptor valueParameterDescriptor, ExpressionCodegen expressionCodegen) {
                    return this.f$0.m6418xcc64b696(functionDescriptor, valueParameterDescriptor, expressionCodegen);
                }
            }, null);
        }

        /* JADX INFO: renamed from: lambda$generateCopyFunction$0$org-jetbrains-kotlin-codegen-ImplementationBodyCodegen$DataClassMethodGeneratorImpl, reason: not valid java name */
        /* synthetic */ StackValue m6418xcc64b696(FunctionDescriptor functionDescriptor, ValueParameterDescriptor valueParameterDescriptor, ExpressionCodegen expressionCodegen) {
            return expressionCodegen.intermediateValueForProperty((PropertyDescriptor) ImplementationBodyCodegen.this.bindingContext.get(BindingContext.VALUE_PARAMETER_AS_PROPERTY, valueParameterDescriptor), false, null, StackValue.LOCAL_0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ConstructorDescriptor getPrimaryConstructorOfDataClass(ClassDescriptor classDescriptor) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(9);
        }
        ClassConstructorDescriptor classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor = classDescriptor.mo6347getUnsubstitutedPrimaryConstructor();
        if (classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor == null) {
            $$$reportNull$$$0(10);
        }
        return classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor;
    }

    private void generateEnumMethods() {
        if (DescriptorUtils.isEnumClass(this.descriptor)) {
            generateEnumValuesMethod();
            generateEnumValueOfMethod();
        }
    }

    private void generateEnumValuesMethod() {
        Type typeMapType = this.typeMapper.mapType(DescriptorUtilsKt.getBuiltIns(this.descriptor).getArrayType(Variance.INVARIANT, this.descriptor.getDefaultType()));
        MethodVisitor methodVisitorNewMethod = this.v.newMethod(JvmDeclarationOriginKt.OtherOriginFromPure(this.myClass, (FunctionDescriptor) CollectionsKt.single(this.descriptor.getStaticScope().getContributedFunctions(StandardNames.ENUM_VALUES, NoLookupLocation.FROM_BACKEND))), 9, StandardNames.ENUM_VALUES.asString(), "()" + typeMapType.getDescriptor(), null, null);
        if (this.state.getClassBuilderMode().generateBodies) {
            methodVisitorNewMethod.visitCode();
            methodVisitorNewMethod.visitFieldInsn(178, this.classAsmType.getInternalName(), ENUM_VALUES_FIELD_NAME, typeMapType.getDescriptor());
            methodVisitorNewMethod.visitMethodInsn(182, typeMapType.getInternalName(), "clone", "()Ljava/lang/Object;", false);
            methodVisitorNewMethod.visitTypeInsn(192, typeMapType.getInternalName());
            methodVisitorNewMethod.visitInsn(176);
            FunctionCodegen.endVisit(methodVisitorNewMethod, "values()", this.myClass);
        }
    }

    private void generateEnumValueOfMethod() {
        MethodVisitor methodVisitorNewMethod = this.v.newMethod(JvmDeclarationOriginKt.OtherOriginFromPure(this.myClass, (FunctionDescriptor) CollectionsKt.single(this.descriptor.getStaticScope().getContributedFunctions(StandardNames.ENUM_VALUE_OF, NoLookupLocation.FROM_BACKEND), new Function1() { // from class: org.jetbrains.kotlin.codegen.ImplementationBodyCodegen$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(DescriptorUtilsKt.isEnumValueOfMethod((SimpleFunctionDescriptor) obj));
            }
        })), 9, StandardNames.ENUM_VALUE_OF.asString(), "(Ljava/lang/String;)" + this.classAsmType.getDescriptor(), null, null);
        if (this.state.getClassBuilderMode().generateBodies) {
            methodVisitorNewMethod.visitCode();
            methodVisitorNewMethod.visitLdcInsn(this.classAsmType);
            methodVisitorNewMethod.visitVarInsn(25, 0);
            methodVisitorNewMethod.visitMethodInsn(184, "java/lang/Enum", "valueOf", "(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;", false);
            methodVisitorNewMethod.visitTypeInsn(192, this.classAsmType.getInternalName());
            methodVisitorNewMethod.visitInsn(176);
            FunctionCodegen.endVisit(methodVisitorNewMethod, "valueOf()", this.myClass);
        }
    }

    private void generateFieldForSingleton() {
        if (JvmCodegenUtil.isCompanionObjectInInterfaceNotIntrinsic(this.descriptor)) {
            StackValue.Field fieldCreateSingletonViaInstance = StackValue.createSingletonViaInstance(this.descriptor, this.typeMapper, JvmAbi.HIDDEN_INSTANCE_FIELD);
            this.v.newField(JvmDeclarationOriginKt.OtherOrigin(this.descriptor), 4120, fieldCreateSingletonViaInstance.f697name, fieldCreateSingletonViaInstance.type.getDescriptor(), null, null);
        }
        if (DescriptorUtils.isEnumEntry(this.descriptor) || DescriptorUtils.isCompanionObject(this.descriptor)) {
            return;
        }
        if (DescriptorUtils.isNonCompanionObject(this.descriptor)) {
            StackValue.Field fieldCreateSingletonViaInstance2 = StackValue.createSingletonViaInstance(this.descriptor, this.typeMapper, JvmAbi.INSTANCE_FIELD);
            AnnotationCodegen.forField(this.v.newField(JvmDeclarationOriginKt.OtherOriginFromPure(this.myClass), 25, fieldCreateSingletonViaInstance2.f697name, fieldCreateSingletonViaInstance2.type.getDescriptor(), null, null), this, this.state).visitAnnotation(Type.getDescriptor(NotNull.class), false).visitEnd();
            return;
        }
        ClassDescriptor classDescriptorMo6346getCompanionObjectDescriptor = this.descriptor.mo6346getCompanionObjectDescriptor();
        if (classDescriptorMo6346getCompanionObjectDescriptor == null) {
            return;
        }
        KtElement psiOrParent = (KtObjectDeclaration) CollectionsKt.firstOrNull((List) this.myClass.getCompanionObjects());
        int visibilityAccessFlag = DescriptorAsmUtil.getVisibilityAccessFlag(classDescriptorMo6346getCompanionObjectDescriptor);
        boolean zSupportsFeature = this.state.getLanguageVersionSettings().supportsFeature(LanguageFeature.DeprecatedFieldForInvisibleCompanionObject);
        boolean zSupportsFeature2 = this.state.getLanguageVersionSettings().supportsFeature(LanguageFeature.ProperVisibilityForCompanionObjectInstanceField);
        boolean z = zSupportsFeature && !zSupportsFeature2 && (((visibilityAccessFlag & 6) != 0) || ((visibilityAccessFlag & 7) == 0) || JvmCodegenUtil.isNonIntrinsicPrivateCompanionObjectInInterface(classDescriptorMo6346getCompanionObjectDescriptor));
        int i = JvmCodegenUtil.isJvmInterface(this.descriptor) || !zSupportsFeature2 ? 25 : visibilityAccessFlag | 24;
        if (z) {
            i |= 131072;
        }
        if (zSupportsFeature2 && JvmCodegenUtil.isCompanionObjectInInterfaceNotIntrinsic(classDescriptorMo6346getCompanionObjectDescriptor) && DescriptorVisibilities.isPrivate(classDescriptorMo6346getCompanionObjectDescriptor.getVisibility())) {
            i |= 4096;
        }
        int i2 = i;
        StackValue.Field fieldSingleton = StackValue.singleton(classDescriptorMo6346getCompanionObjectDescriptor, this.typeMapper);
        ClassBuilder classBuilder = this.v;
        if (psiOrParent == null) {
            psiOrParent = this.myClass.getPsiOrParent();
        }
        FieldVisitor fieldVisitorNewField = classBuilder.newField(JvmDeclarationOriginKt.OtherOrigin(psiOrParent), i2, fieldSingleton.f697name, fieldSingleton.type.getDescriptor(), null, null);
        AnnotationCodegen.forField(fieldVisitorNewField, this, this.state).visitAnnotation(Type.getDescriptor(NotNull.class), false).visitEnd();
        if (z) {
            AnnotationCodegen.forField(fieldVisitorNewField, this, this.state).visitAnnotation(Type.getDescriptor(Deprecated.class), true).visitEnd();
        }
    }

    private void initializeObjects() {
        if (DescriptorUtils.isObject(this.descriptor) && this.state.getClassBuilderMode().generateBodies) {
            boolean zIsNonCompanionObject = DescriptorUtils.isNonCompanionObject(this.descriptor);
            boolean zIsCompanionObjectInInterfaceNotIntrinsic = JvmCodegenUtil.isCompanionObjectInInterfaceNotIntrinsic(this.descriptor);
            boolean zIsInterfaceCompanionWithBackingFieldsInOuter = isInterfaceCompanionWithBackingFieldsInOuter(this.descriptor);
            boolean zIsMappedIntrinsicCompanionObject = DescriptorsJvmAbiUtil.isMappedIntrinsicCompanionObject(this.descriptor);
            boolean zIsClassCompanionObjectWithBackingFieldsInOuter = DescriptorsJvmAbiUtil.isClassCompanionObjectWithBackingFieldsInOuter(this.descriptor);
            if (!zIsNonCompanionObject && ((!zIsCompanionObjectInInterfaceNotIntrinsic || zIsInterfaceCompanionWithBackingFieldsInOuter) && !zIsMappedIntrinsicCompanionObject)) {
                if (zIsClassCompanionObjectWithBackingFieldsInOuter || zIsInterfaceCompanionWithBackingFieldsInOuter) {
                    ImplementationBodyCodegen implementationBodyCodegen = (ImplementationBodyCodegen) getParentCodegen();
                    ExpressionCodegen expressionCodegenCreateOrGetClInitCodegen = implementationBodyCodegen.createOrGetClInitCodegen();
                    InstructionAdapter instructionAdapter = expressionCodegenCreateOrGetClInitCodegen.v;
                    implementationBodyCodegen.generateMethodCallTo((FunctionDescriptor) implementationBodyCodegen.context.accessibleDescriptor((CallableMemberDescriptor) CollectionsKt.single(this.descriptor.getConstructors()), null), null, instructionAdapter);
                    StackValue.singleton(this.descriptor, implementationBodyCodegen.typeMapper).store(StackValue.onStack(implementationBodyCodegen.typeMapper.mapClass(this.descriptor)), instructionAdapter, true);
                    generateInitializers(expressionCodegenCreateOrGetClInitCodegen);
                    return;
                }
                return;
            }
            ExpressionCodegen expressionCodegenCreateOrGetClInitCodegen2 = createOrGetClInitCodegen();
            InstructionAdapter instructionAdapter2 = expressionCodegenCreateOrGetClInitCodegen2.v;
            markLineNumberForElement(((KtPureClassOrObject) this.element).getPsiOrParent(), instructionAdapter2);
            instructionAdapter2.anew(this.classAsmType);
            instructionAdapter2.dup();
            instructionAdapter2.invokespecial(this.classAsmType.getInternalName(), "<init>", "()V", false);
            expressionCodegenCreateOrGetClInitCodegen2.getFrameMap().enterTemp(this.classAsmType);
            StackValue.Local local = StackValue.local(0, this.classAsmType);
            local.store(StackValue.onStack(this.classAsmType), expressionCodegenCreateOrGetClInitCodegen2.v);
            StackValue.Field fieldCreateSingletonViaInstance = StackValue.createSingletonViaInstance(this.descriptor, this.typeMapper, zIsCompanionObjectInInterfaceNotIntrinsic ? JvmAbi.HIDDEN_INSTANCE_FIELD : JvmAbi.INSTANCE_FIELD);
            fieldCreateSingletonViaInstance.store(local, expressionCodegenCreateOrGetClInitCodegen2.v);
            generateInitializers(expressionCodegenCreateOrGetClInitCodegen2);
            if (zIsCompanionObjectInInterfaceNotIntrinsic) {
                StackValue.singleton(this.descriptor, this.typeMapper).store(fieldCreateSingletonViaInstance, getParentCodegen().createOrGetClInitCodegen().v, true);
            }
        }
    }

    private static boolean isInterfaceCompanionWithBackingFieldsInOuter(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor == null) {
            $$$reportNull$$$0(11);
        }
        DeclarationDescriptor containingDeclaration = declarationDescriptor.getContainingModule();
        if (DescriptorUtils.isCompanionObject(declarationDescriptor) && JvmCodegenUtil.isJvmInterface(containingDeclaration)) {
            return CollectionsKt.any(((ClassDescriptor) declarationDescriptor).getUnsubstitutedMemberScope().getContributedDescriptors(DescriptorKindFilter.ALL, MemberScope.INSTANCE.getALL_NAME_FILTER()), new Function1() { // from class: org.jetbrains.kotlin.codegen.ImplementationBodyCodegen$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    DeclarationDescriptor declarationDescriptor2 = (DeclarationDescriptor) obj;
                    return Boolean.valueOf((declarationDescriptor2 instanceof PropertyDescriptor) && DescriptorsJvmAbiUtil.hasJvmFieldAnnotation((PropertyDescriptor) declarationDescriptor2));
                }
            });
        }
        return false;
    }

    private void generateCompanionObjectBackingFieldCopies() {
        List<PropertyAndDefaultValue> list = this.companionObjectPropertiesToCopy;
        if (list == null || list.isEmpty()) {
            return;
        }
        boolean zIsPrivate = DescriptorVisibilities.isPrivate(((ClassDescriptor) this.companionObjectPropertiesToCopy.get(0).descriptor.getContainingModule()).getVisibility());
        int i = (zIsPrivate ? 131072 : 0) | 25;
        List<String> listSingletonList = zIsPrivate ? Collections.singletonList(CodegenUtilKt.JAVA_LANG_DEPRECATED) : Collections.emptyList();
        for (PropertyAndDefaultValue propertyAndDefaultValue : this.companionObjectPropertiesToCopy) {
            PropertyDescriptor propertyDescriptor = propertyAndDefaultValue.descriptor;
            Type typeMapType = this.typeMapper.mapType(propertyDescriptor);
            AnnotationCodegen.forField(this.v.newField(JvmDeclarationOriginKt.Synthetic(DescriptorToSourceUtils.descriptorToDeclaration(propertyDescriptor), propertyDescriptor), i, this.context.getFieldName(propertyDescriptor, false), typeMapType.getDescriptor(), this.typeMapper.mapFieldSignature(propertyDescriptor.getType(), propertyDescriptor), propertyAndDefaultValue.defaultValue), this, this.state).genAnnotations(propertyDescriptor, typeMapType, null, null, listSingletonList);
            if (this.state.getClassBuilderMode().generateBodies && propertyAndDefaultValue.defaultValue == null) {
                ExpressionCodegen expressionCodegenCreateOrGetClInitCodegen = createOrGetClInitCodegen();
                StackValue.local(putCompanionObjectInLocalVar(expressionCodegenCreateOrGetClInitCodegen), AsmTypes.OBJECT_TYPE).put(expressionCodegenCreateOrGetClInitCodegen.v);
                copyFieldFromCompanionObject(propertyDescriptor);
            }
        }
    }

    private int putCompanionObjectInLocalVar(ExpressionCodegen expressionCodegen) {
        FrameMap frameMap = expressionCodegen.myFrameMap;
        ClassDescriptor classDescriptorMo6346getCompanionObjectDescriptor = this.descriptor.mo6346getCompanionObjectDescriptor();
        int index = frameMap.getIndex(classDescriptorMo6346getCompanionObjectDescriptor);
        if (index != -1) {
            return index;
        }
        int iEnter = frameMap.enter(classDescriptorMo6346getCompanionObjectDescriptor, AsmTypes.OBJECT_TYPE);
        StackValue.Field fieldSingleton = StackValue.singleton(classDescriptorMo6346getCompanionObjectDescriptor, this.typeMapper);
        StackValue.local(iEnter, fieldSingleton.type).store(fieldSingleton, expressionCodegen.v);
        return iEnter;
    }

    private void copyFieldFromCompanionObject(PropertyDescriptor propertyDescriptor) {
        ExpressionCodegen expressionCodegenCreateOrGetClInitCodegen = createOrGetClInitCodegen();
        StackValue.Property propertyIntermediateValueForProperty = expressionCodegenCreateOrGetClInitCodegen.intermediateValueForProperty(propertyDescriptor, false, null, StackValue.none());
        StackValue.field(propertyIntermediateValueForProperty.type, propertyIntermediateValueForProperty.kotlinType, this.classAsmType, propertyDescriptor.getShortName().asString(), true, StackValue.none(), propertyDescriptor).store(propertyIntermediateValueForProperty, expressionCodegenCreateOrGetClInitCodegen.v);
    }

    public void generateInitializers(final ExpressionCodegen expressionCodegen) {
        if (expressionCodegen == null) {
            $$$reportNull$$$0(12);
        }
        generateInitializers(new Function0() { // from class: org.jetbrains.kotlin.codegen.ImplementationBodyCodegen$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return ImplementationBodyCodegen.lambda$generateInitializers$3(expressionCodegen);
            }
        });
    }

    private void lookupConstructorExpressionsInClosureIfPresent() {
        if (!this.state.getClassBuilderMode().generateBodies || this.descriptor.getConstructors().isEmpty()) {
            return;
        }
        KtVisitorVoid ktVisitorVoid = new KtVisitorVoid() { // from class: org.jetbrains.kotlin.codegen.ImplementationBodyCodegen.2
            static final /* synthetic */ boolean $assertionsDisabled = false;

            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[3];
                switch (i) {
                    case 1:
                        objArr[0] = "expr";
                        break;
                    case 2:
                        objArr[0] = "call";
                        break;
                    case 3:
                        objArr[0] = "toLookup";
                        break;
                    case 4:
                    case 5:
                    case 6:
                        objArr[0] = "expression";
                        break;
                    default:
                        objArr[0] = "e";
                        break;
                }
                objArr[1] = "org/jetbrains/kotlin/codegen/ImplementationBodyCodegen$2";
                switch (i) {
                    case 1:
                        objArr[2] = "visitSimpleNameExpression";
                        break;
                    case 2:
                        objArr[2] = "lookupReceivers";
                        break;
                    case 3:
                        objArr[2] = "lookupInContext";
                        break;
                    case 4:
                        objArr[2] = "visitThisExpression";
                        break;
                    case 5:
                        objArr[2] = "visitSuperExpression";
                        break;
                    case 6:
                        objArr[2] = "visitArrayAccessExpression";
                        break;
                    default:
                        objArr[2] = "visitKtElement";
                        break;
                }
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitKtElement(KtElement ktElement) {
                if (ktElement == null) {
                    $$$reportNull$$$0(0);
                }
                ktElement.acceptChildren(this);
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitSimpleNameExpression(KtSimpleNameExpression ktSimpleNameExpression) {
                if (ktSimpleNameExpression == null) {
                    $$$reportNull$$$0(1);
                }
                DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) ImplementationBodyCodegen.this.bindingContext.get(BindingContext.REFERENCE_TARGET, ktSimpleNameExpression);
                if (ExpressionTypingUtils.isLocalFunction(declarationDescriptor)) {
                    lookupInContext(declarationDescriptor);
                    return;
                }
                if (declarationDescriptor instanceof CallableMemberDescriptor) {
                    ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(ktSimpleNameExpression, ImplementationBodyCodegen.this.bindingContext);
                    if (resolvedCall != null) {
                        lookupReceivers(resolvedCall);
                    }
                    if (resolvedCall instanceof VariableAsFunctionResolvedCall) {
                        lookupReceivers(((VariableAsFunctionResolvedCall) resolvedCall).getVariableCall());
                        return;
                    }
                    return;
                }
                if (declarationDescriptor instanceof VariableDescriptor) {
                    DeclarationDescriptor containingDeclaration = declarationDescriptor.getContainingModule();
                    if (!((containingDeclaration instanceof ConstructorDescriptor) && ((ConstructorDescriptor) containingDeclaration).getConstructedClass() == ImplementationBodyCodegen.this.descriptor) && lookupInContext(declarationDescriptor) && JvmCodegenUtil.isDelegatedLocalVariable(declarationDescriptor)) {
                        lookupInContext(CodegenBinding.getDelegatedLocalVariableMetadata((VariableDescriptor) declarationDescriptor, ImplementationBodyCodegen.this.bindingContext));
                    }
                }
            }

            private void lookupReceivers(ResolvedCall<? extends CallableDescriptor> resolvedCall) {
                if (resolvedCall == null) {
                    $$$reportNull$$$0(2);
                }
                lookupReceiver(resolvedCall.getDispatchReceiver());
                lookupReceiver(resolvedCall.getExtensionReceiver());
            }

            private void lookupReceiver(ReceiverValue receiverValue) {
                if (receiverValue instanceof ImplicitReceiver) {
                    if (receiverValue instanceof ExtensionReceiver) {
                        lookupInContext(((ExtensionReceiver) receiverValue).getDeclarationDescriptor().getExtensionReceiverParameter());
                    } else {
                        lookupInContext(((ImplicitReceiver) receiverValue).getDeclarationDescriptor());
                    }
                }
            }

            private boolean lookupInContext(DeclarationDescriptor declarationDescriptor) {
                if (declarationDescriptor == null) {
                    $$$reportNull$$$0(3);
                }
                return ImplementationBodyCodegen.this.context.lookupInContext(declarationDescriptor, StackValue.LOCAL_0, ImplementationBodyCodegen.this.state, true) != null;
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitThisExpression(KtThisExpression ktThisExpression) {
                ReceiverParameterDescriptor extensionReceiverParameter;
                if (ktThisExpression == null) {
                    $$$reportNull$$$0(4);
                }
                DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) ImplementationBodyCodegen.this.bindingContext.get(BindingContext.REFERENCE_TARGET, ktThisExpression.getInstanceReference());
                if (declarationDescriptor instanceof ClassDescriptor) {
                    lookupInContext(declarationDescriptor);
                }
                if (!(declarationDescriptor instanceof CallableDescriptor) || (extensionReceiverParameter = ((CallableDescriptor) declarationDescriptor).getExtensionReceiverParameter()) == null) {
                    return;
                }
                lookupInContext(extensionReceiverParameter);
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitSuperExpression(KtSuperExpression ktSuperExpression) {
                if (ktSuperExpression == null) {
                    $$$reportNull$$$0(5);
                }
                lookupInContext(ExpressionCodegen.getSuperCallLabelTarget(ImplementationBodyCodegen.this.context, ktSuperExpression));
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitArrayAccessExpression(KtArrayAccessExpression ktArrayAccessExpression) {
                if (ktArrayAccessExpression == null) {
                    $$$reportNull$$$0(6);
                }
                ResolvedCall resolvedCall = (ResolvedCall) ImplementationBodyCodegen.this.bindingContext.get(BindingContext.INDEXED_LVALUE_GET, ktArrayAccessExpression);
                if (resolvedCall != null) {
                    lookupReceiver(resolvedCall.getDispatchReceiver());
                }
                ResolvedCall resolvedCall2 = (ResolvedCall) ImplementationBodyCodegen.this.bindingContext.get(BindingContext.INDEXED_LVALUE_SET, ktArrayAccessExpression);
                if (resolvedCall2 != null) {
                    lookupReceiver(resolvedCall2.getDispatchReceiver());
                }
                super.visitArrayAccessExpression(ktArrayAccessExpression);
            }
        };
        for (KtDeclaration ktDeclaration : this.myClass.getDeclarations()) {
            if (ktDeclaration instanceof KtProperty) {
                KtExpression delegateExpressionOrInitializer = ((KtProperty) ktDeclaration).getDelegateExpressionOrInitializer();
                if (delegateExpressionOrInitializer != null) {
                    delegateExpressionOrInitializer.accept(ktVisitorVoid);
                }
            } else if (ktDeclaration instanceof KtAnonymousInitializer) {
                ((KtAnonymousInitializer) ktDeclaration).accept(ktVisitorVoid);
            } else if (ktDeclaration instanceof KtSecondaryConstructor) {
                ((KtSecondaryConstructor) ktDeclaration).accept(ktVisitorVoid);
            }
        }
        for (KtSuperTypeListEntry ktSuperTypeListEntry : this.myClass.getSuperTypeListEntries()) {
            if (ktSuperTypeListEntry instanceof KtDelegatedSuperTypeEntry) {
                ((KtDelegatedSuperTypeEntry) ktSuperTypeListEntry).getDelegateExpression().accept(ktVisitorVoid);
            } else if (ktSuperTypeListEntry instanceof KtSuperTypeCallEntry) {
                ktSuperTypeListEntry.accept(ktVisitorVoid);
            }
        }
    }

    private void generateEnumEntries() {
        if (this.descriptor.getClassKind() != ClassKind.ENUM_CLASS) {
            return;
        }
        List<KtEnumEntry> listFilterIsInstance = CollectionsKt.filterIsInstance(((KtPureClassOrObject) this.element).getDeclarations(), KtEnumEntry.class);
        for (KtEnumEntry ktEnumEntry : listFilterIsInstance) {
            ClassDescriptor classDescriptor = (ClassDescriptor) BindingContextUtils.getNotNull(this.bindingContext, BindingContext.CLASS, ktEnumEntry);
            AnnotationCodegen.forField(this.v.newField(JvmDeclarationOriginKt.OtherOrigin(ktEnumEntry, classDescriptor), (KotlinBuiltIns.isDeprecated(classDescriptor) ? 131072 : 0) | 16409, classDescriptor.getShortName().asString(), this.classAsmType.getDescriptor(), null, null), this, this.state).genAnnotations(classDescriptor, null, null);
        }
        initializeEnumConstants(listFilterIsInstance);
    }

    private void initializeEnumConstants(List<KtEnumEntry> list) {
        if (list == null) {
            $$$reportNull$$$0(13);
        }
        if (this.state.getClassBuilderMode().generateBodies) {
            InstructionAdapter instructionAdapter = createOrGetClInitCodegen().v;
            Type typeMapType = this.typeMapper.mapType(DescriptorUtilsKt.getBuiltIns(this.descriptor).getArrayType(Variance.INVARIANT, this.descriptor.getDefaultType()));
            this.v.newField(JvmDeclarationOriginKt.OtherOriginFromPure(this.myClass), 4122, ENUM_VALUES_FIELD_NAME, typeMapType.getDescriptor(), null, null);
            instructionAdapter.iconst(list.size());
            instructionAdapter.newarray(this.classAsmType);
            if (!list.isEmpty()) {
                instructionAdapter.dup();
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    initializeEnumConstant(list, i);
                }
            }
            instructionAdapter.putstatic(this.classAsmType.getInternalName(), ENUM_VALUES_FIELD_NAME, typeMapType.getDescriptor());
        }
    }

    private void initializeEnumConstant(List<KtEnumEntry> list, int i) {
        if (list == null) {
            $$$reportNull$$$0(14);
        }
        ExpressionCodegen expressionCodegenCreateOrGetClInitCodegen = createOrGetClInitCodegen();
        InstructionAdapter instructionAdapter = expressionCodegenCreateOrGetClInitCodegen.v;
        KtEnumEntry ktEnumEntry = list.get(i);
        instructionAdapter.dup();
        instructionAdapter.iconst(i);
        ClassDescriptor classDescriptor = (ClassDescriptor) BindingContextUtils.getNotNull(this.bindingContext, BindingContext.CLASS, ktEnumEntry);
        Type typeMapClass = this.typeMapper.mapClass(classDescriptor);
        instructionAdapter.anew(typeMapClass);
        instructionAdapter.dup();
        instructionAdapter.aconst(ktEnumEntry.get$name());
        instructionAdapter.iconst(i);
        List<KtSuperTypeListEntry> superTypeListEntries = ktEnumEntry.getSuperTypeListEntries();
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(ktEnumEntry, this.bindingContext);
        boolean zEnumEntryNeedSubclass = CodegenBinding.enumEntryNeedSubclass(this.bindingContext, classDescriptor);
        if (superTypeListEntries.size() == 1 && !CodegenBinding.enumEntryNeedSubclass(this.bindingContext, ktEnumEntry)) {
            ResolvedCall<? extends CallableDescriptor> resolvedCallWithAssert = CallUtilKt.getResolvedCallWithAssert(superTypeListEntries.get(0), this.bindingContext);
            expressionCodegenCreateOrGetClInitCodegen.invokeMethodWithArguments(this.typeMapper.mapToCallableMethod((ConstructorDescriptor) resolvedCallWithAssert.getResultingDescriptor(), false), resolvedCallWithAssert, StackValue.none());
        } else if (resolvedCall != null && !zEnumEntryNeedSubclass) {
            expressionCodegenCreateOrGetClInitCodegen.invokeFunction(resolvedCall, StackValue.none()).put(Type.VOID_TYPE, instructionAdapter);
        } else {
            instructionAdapter.invokespecial(typeMapClass.getInternalName(), "<init>", "(Ljava/lang/String;I)V", false);
        }
        instructionAdapter.dup();
        instructionAdapter.putstatic(this.classAsmType.getInternalName(), ktEnumEntry.get$name(), this.classAsmType.getDescriptor());
        instructionAdapter.astore(AsmTypes.OBJECT_TYPE);
    }

    private void generateDelegates(DelegationFieldsInfo delegationFieldsInfo) {
        KtDelegatedSuperTypeEntry ktDelegatedSuperTypeEntry;
        DelegationFieldsInfo.Field info;
        for (KtSuperTypeListEntry ktSuperTypeListEntry : this.myClass.getSuperTypeListEntries()) {
            if ((ktSuperTypeListEntry instanceof KtDelegatedSuperTypeEntry) && (info = delegationFieldsInfo.getInfo((ktDelegatedSuperTypeEntry = (KtDelegatedSuperTypeEntry) ktSuperTypeListEntry))) != null) {
                generateDelegateField(info);
                KtExpression delegateExpression = ktDelegatedSuperTypeEntry.getDelegateExpression();
                KotlinType type = delegateExpression != null ? this.bindingContext.getType(delegateExpression) : null;
                ClassDescriptor superClass = JvmCodegenUtil.getSuperClass(ktSuperTypeListEntry, this.state, this.bindingContext);
                if (superClass != null) {
                    generateDelegates(superClass, type, info);
                }
            }
        }
    }

    private void generateDelegateField(DelegationFieldsInfo.Field field) {
        if (field.generateField) {
            this.v.newField(JvmDeclarationOrigin.NO_ORIGIN, 4114, field.f696name, field.type.getDescriptor(), field.genericSignature, null);
        }
    }

    private void generateDelegates(ClassDescriptor classDescriptor, KotlinType kotlinType, DelegationFieldsInfo.Field field) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(15);
        }
        if (field == null) {
            $$$reportNull$$$0(16);
        }
        for (Map.Entry<CallableMemberDescriptor, CallableMemberDescriptor> entry : DelegationResolver.INSTANCE.getDelegates(this.descriptor, classDescriptor, kotlinType).entrySet()) {
            CallableMemberDescriptor key = entry.getKey();
            CallableMemberDescriptor value = entry.getValue();
            if (key instanceof PropertyDescriptor) {
                this.propertyCodegen.genDelegate((PropertyDescriptor) key, (PropertyDescriptor) value, field.getStackValue());
            } else if (key instanceof FunctionDescriptor) {
                this.functionCodegen.genDelegate((FunctionDescriptor) key, (FunctionDescriptor) value, field.getStackValue());
            }
        }
    }

    public void addCompanionObjectPropertyToCopy(PropertyDescriptor propertyDescriptor, Object obj) {
        if (propertyDescriptor == null) {
            $$$reportNull$$$0(17);
        }
        if (this.companionObjectPropertiesToCopy == null) {
            this.companionObjectPropertiesToCopy = new ArrayList();
        }
        this.companionObjectPropertiesToCopy.add(new PropertyAndDefaultValue(propertyDescriptor, obj));
    }

    @Override // org.jetbrains.kotlin.codegen.MemberCodegen
    protected void done() {
        Iterator<Function2<ImplementationBodyCodegen, ClassBuilder, Unit>> it = this.additionalTasks.iterator();
        while (it.hasNext()) {
            it.next().invoke(this, this.v);
        }
        super.done();
    }

    private static class PropertyAndDefaultValue {
        public final Object defaultValue;
        public final PropertyDescriptor descriptor;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "descriptor", "org/jetbrains/kotlin/codegen/ImplementationBodyCodegen$PropertyAndDefaultValue", "<init>"));
        }

        public PropertyAndDefaultValue(PropertyDescriptor propertyDescriptor, Object obj) {
            if (propertyDescriptor == null) {
                $$$reportNull$$$0(0);
            }
            this.descriptor = propertyDescriptor;
            this.defaultValue = obj;
        }
    }

    public void addAdditionalTask(Function2<ImplementationBodyCodegen, ClassBuilder, Unit> function2) {
        this.additionalTasks.add(function2);
    }
}
