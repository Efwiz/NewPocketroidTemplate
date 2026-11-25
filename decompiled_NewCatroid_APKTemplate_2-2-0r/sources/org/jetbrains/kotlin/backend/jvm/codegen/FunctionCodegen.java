package org.jetbrains.kotlin.backend.jvm.codegen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrInlineUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMapper;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMappingKt;
import org.jetbrains.kotlin.codegen.Asm7UtilKt;
import org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt;
import org.jetbrains.kotlin.codegen.inline.MaxStackFrameSizeAndLocalsCalculator;
import org.jetbrains.kotlin.codegen.inline.MethodBodyVisitor;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeParametersUsages;
import org.jetbrains.kotlin.codegen.inline.SMAP;
import org.jetbrains.kotlin.codegen.inline.SMAPAndMethodNode;
import org.jetbrains.kotlin.codegen.inline.SourceMapper;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOriginImpl;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.descriptors.IrBasedDescriptorsKt;
import org.jetbrains.kotlin.ir.expressions.IrClassReference;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.util.DumpIrTreeKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.load.java.JavaDescriptorVisibilities;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.JvmNames;
import org.jetbrains.kotlin.resolve.annotations.ThrowUtilKt;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodGenericSignature;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterKind;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodParameterSignature;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.TypePath;
import org.jetbrains.org.objectweb.asm.TypeReference;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: FunctionCodegen.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 '2\u00020\u0001:\u0001'B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0010\u0010\r\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\fJ\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J2\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0016\u001a\u00020\u0017H\u0002J\n\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0002J\u0018\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010\u001b2\u0006\u0010\u001d\u001a\u00020\u0003H\u0002J\b\u0010\u001e\u001a\u00020\u0017H\u0002J\f\u0010\u001f\u001a\u00020 *\u00020\u0003H\u0002J\f\u0010!\u001a\u00020\"*\u00020\u0003H\u0002J\f\u0010#\u001a\u00020 *\u00020\u0003H\u0002J\f\u0010$\u001a\u00020\u0017*\u00020\u0003H\u0002J\f\u0010%\u001a\u00020\u0017*\u00020&H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/FunctionCodegen;", "", "irFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "classCodegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen;", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen;)V", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "doGenerate", "Lorg/jetbrains/kotlin/codegen/inline/SMAPAndMethodNode;", "reifiedTypeParameters", "Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeParametersUsages;", CompilerOptions.GENERATE, "generateAnnotationDefaultValueIfNeeded", "", "methodVisitor", "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "generateParameterAnnotations", JvmAnnotationNames.METADATA_VERSION_FIELD_NAME, "jvmSignature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "skipNullabilityAnnotations", "", "getAnnotationDefaultValueExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getThrownExceptions", "", "", "function", "shouldGenerateAnnotationsOnValueParameters", "calculateMethodFlags", "", "createFrameMapWithReceivers", "Lorg/jetbrains/kotlin/backend/jvm/codegen/IrFrameMap;", "getVisibilityForDefaultArgumentStub", "isDeprecatedHidden", "shouldNotGenerateConstructorParameterAnnotations", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Companion", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunctionCodegen {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Set<IrDeclarationOriginImpl> methodOriginsWithoutAnnotations = SetsKt.setOf((Object[]) new IrDeclarationOriginImpl[]{IrDeclarationOrigin.FUNCTION_FOR_DEFAULT_PARAMETER.INSTANCE, JvmLoweredDeclarationOrigin.SYNTHETIC_ACCESSOR.INSTANCE, IrDeclarationOrigin.GENERATED_SINGLE_FIELD_VALUE_CLASS_MEMBER.INSTANCE, IrDeclarationOrigin.BRIDGE.INSTANCE, IrDeclarationOrigin.BRIDGE_SPECIAL.INSTANCE, JvmLoweredDeclarationOrigin.ABSTRACT_BRIDGE_STUB.INSTANCE, JvmLoweredDeclarationOrigin.TO_ARRAY.INSTANCE, IrDeclarationOrigin.IR_BUILTINS_STUB.INSTANCE, IrDeclarationOrigin.PROPERTY_DELEGATE.INSTANCE});
    private final ClassCodegen classCodegen;
    private final JvmBackendContext context;
    private final IrFunction irFunction;

    /* JADX INFO: compiled from: FunctionCodegen.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[Modality.values().length];
            try {
                iArr[Modality.FINAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Modality.ABSTRACT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[JvmMethodParameterKind.values().length];
            try {
                iArr2[JvmMethodParameterKind.RECEIVER.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public FunctionCodegen(IrFunction irFunction, ClassCodegen classCodegen) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(classCodegen, "classCodegen");
        this.irFunction = irFunction;
        this.classCodegen = classCodegen;
        this.context = classCodegen.getContext();
    }

    public static /* synthetic */ SMAPAndMethodNode generate$default(FunctionCodegen functionCodegen, ReifiedTypeParametersUsages reifiedTypeParametersUsages, int i, Object obj) {
        if ((i & 1) != 0) {
            reifiedTypeParametersUsages = functionCodegen.classCodegen.getReifiedTypeParametersUsages();
        }
        return functionCodegen.generate(reifiedTypeParametersUsages);
    }

    public final SMAPAndMethodNode generate(ReifiedTypeParametersUsages reifiedTypeParameters) {
        Intrinsics.checkNotNullParameter(reifiedTypeParameters, "reifiedTypeParameters");
        try {
            return doGenerate(reifiedTypeParameters);
        } catch (ProcessCanceledException e) {
            throw e;
        } catch (Throwable th) {
            throw new RuntimeException("Exception while generating code for:\n" + DumpIrTreeKt.dump$default(this.irFunction, null, 1, null), th);
        }
    }

    /* JADX WARN: Code duplicated, block: B:36:0x010d  */
    private final SMAPAndMethodNode doGenerate(ReifiedTypeParametersUsages reifiedTypeParameters) {
        SMAP smap;
        JvmMethodGenericSignature jvmMethodGenericSignatureMapSignatureWithGeneric = this.classCodegen.getMethodSignatureMapper().mapSignatureWithGeneric(this.irFunction);
        int iCalculateMethodFlags = calculateMethodFlags(this.irFunction);
        int i = iCalculateMethodFlags & 4096;
        boolean z = i != 0;
        String name2 = jvmMethodGenericSignatureMapSignatureWithGeneric.getAsmMethod().getName();
        String descriptor = jvmMethodGenericSignatureMapSignatureWithGeneric.getAsmMethod().getDescriptor();
        String genericsSignature = ((!this.irFunction.getIsInline() || Intrinsics.areEqual(this.irFunction.getOrigin(), IrDeclarationOrigin.FUNCTION_FOR_DEFAULT_PARAMETER.INSTANCE)) && (z || Intrinsics.areEqual(this.irFunction.getOrigin(), IrDeclarationOrigin.LOCAL_FUNCTION_FOR_LAMBDA.INSTANCE)) && !Intrinsics.areEqual(this.irFunction.getOrigin(), JvmLoweredDeclarationOrigin.SUSPEND_IMPL_STATIC_FUNCTION.INSTANCE)) ? null : jvmMethodGenericSignatureMapSignatureWithGeneric.getGenericsSignature();
        List<String> thrownExceptions = getThrownExceptions(this.irFunction);
        MethodNode methodNode = new MethodNode(589824, iCalculateMethodFlags, name2, descriptor, genericsSignature, thrownExceptions != null ? (String[]) thrownExceptions.toArray(new String[0]) : null);
        final MaxStackFrameSizeAndLocalsCalculator maxStackFrameSizeAndLocalsCalculatorWrapWithMaxLocalCalc = InlineCodegenUtilsKt.wrapWithMaxLocalCalc(methodNode);
        if (this.context.getConfig().getGenerateParametersMetadata() && !z) {
            FunctionCodegenKt.generateParameterNames(this.irFunction, maxStackFrameSizeAndLocalsCalculatorWrapWithMaxLocalCalc, this.context.getState());
        }
        if (INSTANCE.isWithAnnotations(this.irFunction)) {
            boolean z2 = ((iCalculateMethodFlags & 2) == 0 && i == 0) ? false : true;
            new AnnotationCodegen(z2, this.classCodegen) { // from class: org.jetbrains.kotlin.backend.jvm.codegen.FunctionCodegen.doGenerate.1
                @Override // org.jetbrains.kotlin.backend.jvm.codegen.AnnotationCodegen
                public AnnotationVisitor visitAnnotation(String descr, boolean visible) {
                    Intrinsics.checkNotNullParameter(descr, "descr");
                    AnnotationVisitor annotationVisitorVisitAnnotation = maxStackFrameSizeAndLocalsCalculatorWrapWithMaxLocalCalc.visitAnnotation(descr, visible);
                    Intrinsics.checkNotNullExpressionValue(annotationVisitorVisitAnnotation, "visitAnnotation(...)");
                    return annotationVisitorVisitAnnotation;
                }

                @Override // org.jetbrains.kotlin.backend.jvm.codegen.AnnotationCodegen
                public AnnotationVisitor visitTypeAnnotation(String descr, TypePath path, boolean visible) {
                    Intrinsics.checkNotNullParameter(descr, "descr");
                    AnnotationVisitor annotationVisitorVisitTypeAnnotation = maxStackFrameSizeAndLocalsCalculatorWrapWithMaxLocalCalc.visitTypeAnnotation(TypeReference.newTypeReference(20).getValue(), path, descr, visible);
                    Intrinsics.checkNotNullExpressionValue(annotationVisitorVisitTypeAnnotation, "visitTypeAnnotation(...)");
                    return annotationVisitorVisitTypeAnnotation;
                }
            }.genAnnotations(this.irFunction, jvmMethodGenericSignatureMapSignatureWithGeneric.getAsmMethod().getReturnType(), this.irFunction.getReturnType());
            AnnotationCodegen.INSTANCE.genAnnotationsOnTypeParametersAndBounds(this.context, this.irFunction, this.classCodegen, 1, 18, new Function4<Integer, TypePath, String, Boolean, AnnotationVisitor>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.FunctionCodegen.doGenerate.2
                {
                    super(4);
                }

                @Override // kotlin.jvm.functions.Function4
                public /* bridge */ /* synthetic */ AnnotationVisitor invoke(Integer num, TypePath typePath, String str, Boolean bool) {
                    return invoke(num.intValue(), typePath, str, bool.booleanValue());
                }

                public final AnnotationVisitor invoke(int i2, TypePath typePath, String descriptor2, boolean z3) {
                    Intrinsics.checkNotNullParameter(descriptor2, "descriptor");
                    AnnotationVisitor annotationVisitorVisitTypeAnnotation = maxStackFrameSizeAndLocalsCalculatorWrapWithMaxLocalCalc.visitTypeAnnotation(i2, typePath, descriptor2, z3);
                    Intrinsics.checkNotNullExpressionValue(annotationVisitorVisitTypeAnnotation, "visitTypeAnnotation(...)");
                    return annotationVisitorVisitTypeAnnotation;
                }
            });
            if (shouldGenerateAnnotationsOnValueParameters()) {
                generateParameterAnnotations(this.irFunction, maxStackFrameSizeAndLocalsCalculatorWrapWithMaxLocalCalc, jvmMethodGenericSignatureMapSignatureWithGeneric, this.classCodegen, z2);
            }
        }
        IrSimpleFunction irSimpleFunctionSuspendForInlineToOriginal = CoroutineCodegenKt.suspendForInlineToOriginal(this.irFunction);
        if ((iCalculateMethodFlags & 1024) != 0 || this.irFunction.getIsExternal()) {
            generateAnnotationDefaultValueIfNeeded(maxStackFrameSizeAndLocalsCalculatorWrapWithMaxLocalCalc);
            smap = new SMAP(CollectionsKt.emptyList());
        } else if (irSimpleFunctionSuspendForInlineToOriginal != null) {
            SMAPAndMethodNode sMAPAndMethodNodeGenerateMethodNode = this.classCodegen.generateMethodNode(irSimpleFunctionSuspendForInlineToOriginal);
            MethodNode node = sMAPAndMethodNodeGenerateMethodNode.getNode();
            smap = sMAPAndMethodNodeGenerateMethodNode.getClassSMAP();
            node.accept(new MethodBodyVisitor(maxStackFrameSizeAndLocalsCalculatorWrapWithMaxLocalCalc));
        } else {
            SourceMapper sourceMapper = IrCodegenUtilsKt.getSourceMapper(this.context, this.classCodegen.getIrClass());
            IrFrameMap irFrameMapCreateFrameMapWithReceivers = createFrameMapWithReceivers(this.irFunction);
            this.context.getState().getGlobalInlineContext().enterDeclaration(IrBasedDescriptorsKt.toIrBasedDescriptor(JvmIrUtilsKt.suspendFunctionOriginal(this.irFunction)));
            try {
                new ExpressionCodegen(this.irFunction, jvmMethodGenericSignatureMapSignatureWithGeneric, irFrameMapCreateFrameMapWithReceivers, new InstructionAdapter(maxStackFrameSizeAndLocalsCalculatorWrapWithMaxLocalCalc), this.classCodegen, sourceMapper, reifiedTypeParameters).generate();
                this.context.getState().getGlobalInlineContext().exitDeclaration();
                maxStackFrameSizeAndLocalsCalculatorWrapWithMaxLocalCalc.visitMaxs(-1, -1);
                smap = new SMAP(sourceMapper.getResultMappings());
            } catch (Throwable th) {
                this.context.getState().getGlobalInlineContext().exitDeclaration();
                throw th;
            }
        }
        maxStackFrameSizeAndLocalsCalculatorWrapWithMaxLocalCalc.visitEnd();
        return new SMAPAndMethodNode(methodNode, smap);
    }

    private final boolean shouldGenerateAnnotationsOnValueParameters() {
        if (Intrinsics.areEqual(this.irFunction.getOrigin(), JvmLoweredDeclarationOrigin.SYNTHETIC_METHOD_FOR_PROPERTY_OR_TYPEALIAS_ANNOTATIONS.INSTANCE)) {
            return false;
        }
        IrFunction irFunction = this.irFunction;
        return ((irFunction instanceof IrConstructor) && shouldNotGenerateConstructorParameterAnnotations(IrUtilsKt.getParentAsClass(irFunction))) ? false : true;
    }

    private final boolean shouldNotGenerateConstructorParameterAnnotations(IrClass irClass) {
        return IrUtilsKt.isAnonymousObject(irClass) || Intrinsics.areEqual(irClass.getOrigin(), JvmLoweredDeclarationOrigin.CONTINUATION_CLASS.INSTANCE) || Intrinsics.areEqual(irClass.getOrigin(), JvmLoweredDeclarationOrigin.SUSPEND_LAMBDA.INSTANCE);
    }

    private final int getVisibilityForDefaultArgumentStub(IrFunction irFunction) {
        if (Intrinsics.areEqual(irFunction.getVisibility(), DescriptorVisibilities.PUBLIC) || JvmIrUtilsKt.isJvmInterface(IrUtilsKt.getParentAsClass(irFunction))) {
            return 1;
        }
        if (Intrinsics.areEqual(irFunction.getVisibility(), JavaDescriptorVisibilities.PACKAGE_VISIBILITY)) {
            return 0;
        }
        throw new IllegalStateException("Default argument stub should be either public or package private: " + IrUtilsKt.ir2string(irFunction));
    }

    /* JADX WARN: Code duplicated, block: B:27:0x005f  */
    private final int calculateMethodFlags(IrFunction irFunction) {
        if (Intrinsics.areEqual(irFunction.getOrigin(), IrDeclarationOrigin.FUNCTION_FOR_DEFAULT_PARAMETER.INSTANCE)) {
            return getVisibilityForDefaultArgumentStub(irFunction) | 4096 | (IrCodegenUtilsKt.isDeprecatedFunction(irFunction, this.context) ? 131072 : 0) | (irFunction instanceof IrConstructor ? 0 : 8);
        }
        IrValueParameter irValueParameter = (IrValueParameter) CollectionsKt.lastOrNull((List) irFunction.getValueParameters());
        boolean z = ((irValueParameter != null ? irValueParameter.getVarargElementType() : null) == null || JvmIrUtilsKt.isBridge(irFunction)) ? false : true;
        IrFunction irFunction2 = irFunction;
        int i = 1024;
        if (IrUtilsKt.isAnnotationClass(IrUtilsKt.getParentAsClass(irFunction2))) {
            if (IrCodegenUtilsKt.isStatic(irFunction)) {
                i = 0;
            }
        } else {
            IrSimpleFunction irSimpleFunction = irFunction instanceof IrSimpleFunction ? (IrSimpleFunction) irFunction : null;
            Modality modality = irSimpleFunction != null ? irSimpleFunction.getModality() : null;
            int i2 = modality == null ? -1 : WhenMappings.$EnumSwitchMapping$0[modality.ordinal()];
            if (i2 == 1) {
                if (Intrinsics.areEqual(irFunction.getOrigin(), JvmLoweredDeclarationOrigin.CLASS_STATIC_INITIALIZER.INSTANCE) || Intrinsics.areEqual(irFunction.getOrigin(), IrDeclarationOrigin.ENUM_CLASS_SPECIAL_MEMBER.INSTANCE) || (IrUtilsKt.isInterface(IrUtilsKt.getParentAsClass(irFunction2)) && irFunction.getBody() != null)) {
                    i = 0;
                } else {
                    i = 16;
                }
            } else if (i2 != 2 && (!JvmIrUtilsKt.isJvmInterface(IrUtilsKt.getParentAsClass(irFunction2)) || irFunction.getBody() != null)) {
                i = 0;
            }
        }
        IrFunction irFunction3 = irFunction;
        return (JvmIrUtilsKt.isBridge(irFunction) ? 64 : 0) | (z ? 128 : 0) | (IrCodegenUtilsKt.isStatic(irFunction) ? 8 : 0) | (IrCodegenUtilsKt.isDeprecatedFunction(irFunction, this.context) ? 131072 : 0) | IrCodegenUtilsKt.getVisibilityAccessFlag$default(irFunction, null, 1, null) | i | (irFunction.getIsExternal() ? 256 : 0) | (irFunction.getOrigin().getIsSynthetic() || IrUtilsKt.hasAnnotation(irFunction, JvmNames.JVM_SYNTHETIC_ANNOTATION_FQ_NAME) || JvmIrInlineUtilsKt.isReifiable(irFunction) || isDeprecatedHidden(irFunction) ? 4096 : 0) | (IrUtilsKt.hasAnnotation(irFunction3, JvmNames.STRICTFP_ANNOTATION_FQ_NAME) && !Intrinsics.areEqual(irFunction.getOrigin(), JvmLoweredDeclarationOrigin.JVM_OVERLOADS_WRAPPER.INSTANCE) ? 2048 : 0) | (IrUtilsKt.hasAnnotation(irFunction3, JvmNames.SYNCHRONIZED_ANNOTATION_FQ_NAME) && !Intrinsics.areEqual(irFunction.getOrigin(), JvmLoweredDeclarationOrigin.JVM_OVERLOADS_WRAPPER.INSTANCE) ? 32 : 0);
    }

    /* JADX WARN: Code duplicated, block: B:25:0x0055  */
    /* JADX WARN: Multi-variable type inference failed */
    private final boolean isDeprecatedHidden(IrFunction irFunction) {
        boolean zIsAnnotatedWithDeprecated;
        boolean z;
        IrProperty owner;
        if (irFunction instanceof IrSimpleFunction) {
            List listAllOverridden = IrUtilsKt.allOverridden((IrOverridableDeclaration) irFunction, true);
            if (!(listAllOverridden instanceof Collection) || !listAllOverridden.isEmpty()) {
                Iterator it = listAllOverridden.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        zIsAnnotatedWithDeprecated = false;
                        break;
                    }
                    IrSimpleFunction irSimpleFunction = (IrSimpleFunction) it.next();
                    if (IrCodegenUtilsKt.isAnnotatedWithDeprecated(irSimpleFunction)) {
                        z = true;
                    } else {
                        IrPropertySymbol correspondingPropertySymbol = irSimpleFunction.getCorrespondingPropertySymbol();
                        if ((correspondingPropertySymbol == null || (owner = correspondingPropertySymbol.getOwner()) == null || !IrCodegenUtilsKt.isAnnotatedWithDeprecated(owner)) ? false : true) {
                            z = true;
                        } else {
                            z = false;
                        }
                    }
                    if (z) {
                        zIsAnnotatedWithDeprecated = true;
                        break;
                    }
                }
            } else {
                zIsAnnotatedWithDeprecated = false;
                break;
            }
        } else {
            zIsAnnotatedWithDeprecated = IrCodegenUtilsKt.isAnnotatedWithDeprecated(irFunction);
        }
        return zIsAnnotatedWithDeprecated && this.context.getState().getDeprecationProvider().isDeprecatedHidden(IrBasedDescriptorsKt.toIrBasedDescriptor(irFunction));
    }

    private final List<String> getThrownExceptions(IrFunction function) {
        IrConstructorCall annotation;
        IrExpression valueArgument;
        if ((this.context.getState().getLanguageVersionSettings().supportsFeature(LanguageFeature.DoNotGenerateThrowsForDelegatedKotlinMembers) && Intrinsics.areEqual(function.getOrigin(), IrDeclarationOrigin.DELEGATED_MEMBER.INSTANCE)) || (annotation = IrUtilsKt.getAnnotation(function, ThrowUtilKt.JVM_THROWS_ANNOTATION_FQ_NAME)) == null || (valueArgument = annotation.getValueArgument(0)) == null) {
            return null;
        }
        List<IrVarargElement> elements = ((IrVararg) valueArgument).getElements();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(elements, 10));
        for (IrVarargElement irVarargElement : elements) {
            IrTypeMapper typeMapper = this.classCodegen.getTypeMapper();
            Intrinsics.checkNotNull(irVarargElement, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrClassReference");
            arrayList.add(IrTypeMapper.mapType$default(typeMapper, ((IrClassReference) irVarargElement).getClassType(), null, null, false, 14, null).getInternalName());
        }
        return arrayList;
    }

    private final void generateAnnotationDefaultValueIfNeeded(final MethodVisitor methodVisitor) {
        IrExpression annotationDefaultValueExpression = getAnnotationDefaultValueExpression();
        if (annotationDefaultValueExpression != null) {
            final ClassCodegen classCodegen = this.classCodegen;
            new AnnotationCodegen(classCodegen) { // from class: org.jetbrains.kotlin.backend.jvm.codegen.FunctionCodegen$generateAnnotationDefaultValueIfNeeded$1$annotationCodegen$1
                @Override // org.jetbrains.kotlin.backend.jvm.codegen.AnnotationCodegen
                public AnnotationVisitor visitAnnotation(String descr, boolean visible) {
                    Intrinsics.checkNotNullParameter(descr, "descr");
                    AnnotationVisitor annotationVisitorVisitAnnotationDefault = methodVisitor.visitAnnotationDefault();
                    Intrinsics.checkNotNullExpressionValue(annotationVisitorVisitAnnotationDefault, "visitAnnotationDefault(...)");
                    return annotationVisitorVisitAnnotationDefault;
                }
            }.generateAnnotationDefaultValue(annotationDefaultValueExpression);
        }
    }

    private final IrExpression getAnnotationDefaultValueExpression() {
        IrExpressionBody defaultValue;
        IrValueSymbol symbol;
        IrExpressionBody initializer;
        IrPropertySymbol correspondingPropertySymbol;
        IrProperty owner;
        if (!IrUtilsKt.isAnnotationClass(this.classCodegen.getIrClass())) {
            return null;
        }
        IrFunction irFunction = this.irFunction;
        IrSimpleFunction irSimpleFunction = irFunction instanceof IrSimpleFunction ? (IrSimpleFunction) irFunction : null;
        IrField backingField = (irSimpleFunction == null || (correspondingPropertySymbol = irSimpleFunction.getCorrespondingPropertySymbol()) == null || (owner = correspondingPropertySymbol.getOwner()) == null) ? null : owner.getBackingField();
        IrExpression expression = (backingField == null || (initializer = backingField.getInitializer()) == null) ? null : initializer.getExpression();
        IrGetValue irGetValue = expression instanceof IrGetValue ? (IrGetValue) expression : null;
        IrValueDeclaration owner2 = (irGetValue == null || (symbol = irGetValue.getSymbol()) == null) ? null : symbol.getOwner();
        IrValueParameter irValueParameter = owner2 instanceof IrValueParameter ? (IrValueParameter) owner2 : null;
        if (irValueParameter == null || (defaultValue = irValueParameter.getDefaultValue()) == null) {
            return null;
        }
        return defaultValue.getExpression();
    }

    private final IrFrameMap createFrameMapWithReceivers(IrFunction irFunction) {
        IrFrameMap irFrameMap = new IrFrameMap();
        IrValueParameter thisReceiver = irFunction instanceof IrConstructor ? IrUtilsKt.getParentAsClass(irFunction).getThisReceiver() : irFunction.getDispatchReceiverParameter();
        if (thisReceiver != null) {
            IrCodegenUtilsKt.enter(irFrameMap, thisReceiver, IrTypeMappingKt.mapTypeAsDeclaration(this.classCodegen.getTypeMapper(), thisReceiver.getType()));
        }
        for (IrValueParameter irValueParameter : irFunction.getValueParameters().subList(0, irFunction.getContextReceiverParametersCount())) {
            IrCodegenUtilsKt.enter(irFrameMap, irValueParameter, IrTypeMapper.mapType$default(this.classCodegen.getTypeMapper(), irValueParameter.getType(), null, null, false, 14, null));
        }
        IrValueParameter extensionReceiverParameter = irFunction.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            IrCodegenUtilsKt.enter(irFrameMap, extensionReceiverParameter, IrTypeMappingKt.mapType(this.classCodegen.getTypeMapper(), extensionReceiverParameter));
        }
        for (IrValueParameter irValueParameter2 : irFunction.getValueParameters().subList(irFunction.getContextReceiverParametersCount(), irFunction.getValueParameters().size())) {
            IrCodegenUtilsKt.enter(irFrameMap, irValueParameter2, IrTypeMapper.mapType$default(this.classCodegen.getTypeMapper(), irValueParameter2.getType(), null, null, false, 14, null));
        }
        return irFrameMap;
    }

    private final void generateParameterAnnotations(IrFunction irFunction, final MethodVisitor mv, JvmMethodSignature jvmSignature, final ClassCodegen classCodegen, final boolean skipNullabilityAnnotations) {
        int i;
        IrValueParameter next;
        Iterator<IrValueParameter> it = irFunction.getValueParameters().iterator();
        List<JvmMethodParameterSignature> valueParameters = jvmSignature.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<JvmMethodParameterSignature> list = valueParameters;
        int i2 = 0;
        if ((list instanceof Collection) && list.isEmpty()) {
            i = 0;
        } else {
            Iterator<T> it2 = list.iterator();
            i = 0;
            while (it2.hasNext()) {
                if (((JvmMethodParameterSignature) it2.next()).getKind().isSkippedInGenericSignature() && (i = i + 1) < 0) {
                    CollectionsKt.throwCountOverflow();
                }
            }
        }
        Asm7UtilKt.visitAnnotableParameterCount(mv, valueParameters.size() - i);
        Iterator<T> it3 = list.iterator();
        while (true) {
            final int i3 = i2;
            if (!it3.hasNext()) {
                return;
            }
            Object next2 = it3.next();
            i2 = i3 + 1;
            if (i3 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            JvmMethodParameterSignature jvmMethodParameterSignature = (JvmMethodParameterSignature) next2;
            JvmMethodParameterKind kind = jvmMethodParameterSignature.getKind();
            Intrinsics.checkNotNullExpressionValue(kind, "getKind(...)");
            if (WhenMappings.$EnumSwitchMapping$1[kind.ordinal()] == 1) {
                next = irFunction.getExtensionReceiverParameter();
            } else {
                next = it.next();
            }
            IrValueParameter irValueParameter = next;
            if (irValueParameter != null && !kind.isSkippedInGenericSignature() && !FunctionCodegenKt.isSyntheticMarkerParameter(irValueParameter)) {
                final int i4 = i;
                new AnnotationCodegen(classCodegen, skipNullabilityAnnotations) { // from class: org.jetbrains.kotlin.backend.jvm.codegen.FunctionCodegen$generateParameterAnnotations$1$1
                    @Override // org.jetbrains.kotlin.backend.jvm.codegen.AnnotationCodegen
                    public AnnotationVisitor visitAnnotation(String descr, boolean visible) {
                        Intrinsics.checkNotNullParameter(descr, "descr");
                        AnnotationVisitor annotationVisitorVisitParameterAnnotation = mv.visitParameterAnnotation(i3 - i4, descr, visible);
                        Intrinsics.checkNotNullExpressionValue(annotationVisitorVisitParameterAnnotation, "visitParameterAnnotation(...)");
                        return annotationVisitorVisitParameterAnnotation;
                    }

                    @Override // org.jetbrains.kotlin.backend.jvm.codegen.AnnotationCodegen
                    public AnnotationVisitor visitTypeAnnotation(String descr, TypePath path, boolean visible) {
                        Intrinsics.checkNotNullParameter(descr, "descr");
                        AnnotationVisitor annotationVisitorVisitTypeAnnotation = mv.visitTypeAnnotation(TypeReference.newFormalParameterReference(i3 - i4).getValue(), path, descr, visible);
                        Intrinsics.checkNotNullExpressionValue(annotationVisitorVisitTypeAnnotation, "visitTypeAnnotation(...)");
                        return annotationVisitorVisitTypeAnnotation;
                    }
                }.genAnnotations(irValueParameter, jvmMethodParameterSignature.getAsmType(), irValueParameter.getType());
            }
        }
    }

    /* JADX INFO: compiled from: FunctionCodegen.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0006\u001a\u00020\u0007*\u00020\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/FunctionCodegen$Companion;", "", "()V", "methodOriginsWithoutAnnotations", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "isWithAnnotations", "", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Z", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isWithAnnotations(IrFunction irFunction) {
            IrDeclarationOrigin origin = irFunction.getOrigin();
            if (CollectionsKt.contains(FunctionCodegen.methodOriginsWithoutAnnotations, origin)) {
                return false;
            }
            if (Intrinsics.areEqual(origin, IrDeclarationOrigin.ENUM_CLASS_SPECIAL_MEMBER.INSTANCE)) {
                return Intrinsics.areEqual(irFunction.getName().asString(), "<get-entries>");
            }
            return true;
        }
    }
}
