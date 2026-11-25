package org.jetbrains.kotlin.codegen.inline;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.BaseExpressionCodegen;
import org.jetbrains.kotlin.codegen.CompilationException;
import org.jetbrains.kotlin.codegen.FrameMapBase;
import org.jetbrains.kotlin.codegen.JvmKotlinType;
import org.jetbrains.kotlin.codegen.SourceInfo;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.ValueKind;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.inline.InlineUtil;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodSignature;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.JumpInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.LocalVariableNode;
import org.jetbrains.org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.TableSwitchInsnNode;
import org.jetbrains.org.objectweb.asm.tree.TryCatchBlockNode;

/* JADX INFO: compiled from: InlineCodegen.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000´\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000 P*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u0003:\u0001PB=\u0012\u0006\u0010\u0004\u001a\u00028\u0000\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000e¢\u0006\u0002\u0010\u000fJ\u0006\u0010$\u001a\u00020%J&\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020,0+2\u0006\u0010-\u001a\u00020\u0014H\u0002J\b\u0010.\u001a\u00020'H$J\u0018\u0010/\u001a\u00020'2\u0006\u00100\u001a\u0002012\u0006\u0010\u0004\u001a\u00020\u0002H\u0004J\u0018\u00102\u001a\u0002032\u0006\u00104\u001a\u00020%2\u0006\u00105\u001a\u000206H\u0002J\b\u00107\u001a\u000206H$J\b\u00108\u001a\u00020'H\u0002J\u0016\u00109\u001a\u00020'2\u0006\u0010:\u001a\u0002062\u0006\u00105\u001a\u000206J\u0018\u0010;\u001a\u00020'2\u0006\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020?H\u0002J(\u0010@\u001a\u00020'2\u0006\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020=2\u0006\u0010D\u001a\u00020\u00142\u0006\u0010>\u001a\u00020?H\u0004J\"\u0010E\u001a\u00020'2\u0006\u0010C\u001a\u00020=2\u0006\u0010F\u001a\u00020G2\b\u0010H\u001a\u0004\u0018\u00010IH\u0004J \u0010J\u001a\u00020'2\u0006\u0010K\u001a\u00020L2\u0006\u0010M\u001a\u00020\u00142\u0006\u0010N\u001a\u00020OH\u0004R\u0016\u0010\u0004\u001a\u00028\u0000X\u0084\u0004¢\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\u00020\u0016X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0014\u0010\u0007\u001a\u00020\bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u000e\u0010\u001b\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u001c\u001a\u0012\u0012\u0004\u0012\u00020\u00140\u001dj\b\u0012\u0004\u0012\u00020\u0014`\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\fX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0014\u0010\u0005\u001a\u00020\u0006X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0012\u0010\t\u001a\u0006\u0012\u0002\b\u00030\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006Q"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/InlineCodegen;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/codegen/BaseExpressionCodegen;", "", "codegen", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "jvmSignature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "typeParameterMappings", "Lorg/jetbrains/kotlin/codegen/inline/TypeParameterMappings;", "sourceCompiler", "Lorg/jetbrains/kotlin/codegen/inline/SourceCompilerForInline;", "reifiedTypeInliner", "Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeInliner;", "(Lorg/jetbrains/kotlin/codegen/BaseExpressionCodegen;Lorg/jetbrains/kotlin/codegen/state/GenerationState;Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;Lorg/jetbrains/kotlin/codegen/inline/TypeParameterMappings;Lorg/jetbrains/kotlin/codegen/inline/SourceCompilerForInline;Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeInliner;)V", "getCodegen", "()Lorg/jetbrains/kotlin/codegen/BaseExpressionCodegen;", "Lorg/jetbrains/kotlin/codegen/BaseExpressionCodegen;", "initialFrameSize", "", "invocationParamBuilder", "Lorg/jetbrains/kotlin/codegen/inline/ParametersBuilder;", "getInvocationParamBuilder", "()Lorg/jetbrains/kotlin/codegen/inline/ParametersBuilder;", "getJvmSignature", "()Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodSignature;", "maskStartIndex", "maskValues", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "methodHandleInDefaultMethodIndex", "getSourceCompiler", "()Lorg/jetbrains/kotlin/codegen/inline/SourceCompilerForInline;", "getState", "()Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "compileInline", "Lorg/jetbrains/kotlin/codegen/inline/SMAPAndMethodNode;", "generateAndInsertFinallyBlocks", "", "intoNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "insertPoints", "", "Lorg/jetbrains/kotlin/codegen/inline/MethodInliner$PointForExternalFinallyBlocks;", "offsetForFinallyLocalVar", "generateAssertField", "generateStub", "text", "", "inlineCall", "Lorg/jetbrains/kotlin/codegen/inline/InlineResult;", "nodeAndSmap", "isInlineOnly", "", "isInlinedToInlineFunInKotlinRuntime", "leaveTemps", "performInline", "registerLineNumberAfterwards", "processDefaultMaskOrMethodHandler", "value", "Lorg/jetbrains/kotlin/codegen/StackValue;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/codegen/ValueKind;", "putArgumentToLocalVal", "jvmKotlinType", "Lorg/jetbrains/kotlin/codegen/JvmKotlinType;", "stackValue", "parameterIndex", "putCapturedToLocalVal", "capturedParam", "Lorg/jetbrains/kotlin/codegen/inline/CapturedParamDesc;", "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "rememberClosure", "parameterType", "Lorg/jetbrains/org/objectweb/asm/Type;", "index", "lambdaInfo", "Lorg/jetbrains/kotlin/codegen/inline/LambdaInfo;", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class InlineCodegen<T extends BaseExpressionCodegen> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final T codegen;
    private final int initialFrameSize;
    private final ParametersBuilder invocationParamBuilder;
    private final JvmMethodSignature jvmSignature;
    private int maskStartIndex;
    private final ArrayList<Integer> maskValues;
    private int methodHandleInDefaultMethodIndex;
    private final ReifiedTypeInliner<?> reifiedTypeInliner;
    private final SourceCompilerForInline sourceCompiler;
    private final GenerationState state;
    private final TypeParameterMappings<?> typeParameterMappings;

    /* JADX INFO: compiled from: InlineCodegen.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ValueKind.values().length];
            try {
                iArr[ValueKind.READ_OF_INLINE_LAMBDA_FOR_INLINE_SUSPEND_PARAMETER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ValueKind.READ_OF_OBJECT_FOR_INLINE_SUSPEND_PARAMETER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ValueKind.DEFAULT_INLINE_PARAMETER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    protected abstract void generateAssertField();

    protected abstract boolean isInlinedToInlineFunInKotlinRuntime();

    public InlineCodegen(T codegen, GenerationState state, JvmMethodSignature jvmSignature, TypeParameterMappings<?> typeParameterMappings, SourceCompilerForInline sourceCompiler, ReifiedTypeInliner<?> reifiedTypeInliner) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(jvmSignature, "jvmSignature");
        Intrinsics.checkNotNullParameter(typeParameterMappings, "typeParameterMappings");
        Intrinsics.checkNotNullParameter(sourceCompiler, "sourceCompiler");
        Intrinsics.checkNotNullParameter(reifiedTypeInliner, "reifiedTypeInliner");
        this.codegen = codegen;
        this.state = state;
        this.jvmSignature = jvmSignature;
        this.typeParameterMappings = typeParameterMappings;
        this.sourceCompiler = sourceCompiler;
        this.reifiedTypeInliner = reifiedTypeInliner;
        this.initialFrameSize = codegen.getFrameMap().getCurrentSize();
        this.invocationParamBuilder = ParametersBuilder.INSTANCE.newBuilder();
        this.maskValues = new ArrayList<>();
        this.maskStartIndex = -1;
        this.methodHandleInDefaultMethodIndex = -1;
    }

    protected final T getCodegen() {
        return this.codegen;
    }

    protected final GenerationState getState() {
        return this.state;
    }

    protected final JvmMethodSignature getJvmSignature() {
        return this.jvmSignature;
    }

    protected final SourceCompilerForInline getSourceCompiler() {
        return this.sourceCompiler;
    }

    protected final ParametersBuilder getInvocationParamBuilder() {
        return this.invocationParamBuilder;
    }

    protected final void generateStub(String text, BaseExpressionCodegen codegen) {
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        leaveTemps();
        AsmUtil.genThrow(codegen.getVisitor(), "java/lang/UnsupportedOperationException", "Call is part of inline cycle: " + text);
    }

    public final SMAPAndMethodNode compileInline() {
        SMAPAndMethodNode sMAPAndMethodNodeCompileInlineFunction = this.sourceCompiler.compileInlineFunction(this.jvmSignature);
        InlineCodegenUtilsKt.preprocessSuspendMarkers(sMAPAndMethodNodeCompileInlineFunction.getNode(), true, false);
        return sMAPAndMethodNodeCompileInlineFunction;
    }

    public final void performInline(boolean registerLineNumberAfterwards, boolean isInlineOnly) {
        SMAPAndMethodNode sMAPAndMethodNodeCompileInline;
        MethodNode node;
        try {
            try {
                sMAPAndMethodNodeCompileInline = compileInline();
                try {
                    InlineResult inlineResultInlineCall = inlineCall(sMAPAndMethodNodeCompileInline, isInlineOnly);
                    leaveTemps();
                    this.codegen.propagateChildReifiedTypeParametersUsages(inlineResultInlineCall.getReifiedTypeParametersUsages());
                    this.codegen.markLineNumberAfterInlineIfNeeded(registerLineNumberAfterwards);
                    this.state.getFactory().removeClasses(inlineResultInlineCall.calcClassesToRemove());
                } catch (Exception e) {
                    e = e;
                    String str = "Couldn't inline method call: " + this.sourceCompiler.getCallElementText() + "\nMethod: " + ((sMAPAndMethodNodeCompileInline == null || (node = sMAPAndMethodNodeCompileInline.getNode()) == null) ? null : InlineCodegenUtilsKt.getNodeText(node));
                    Exception exc = e;
                    Object callElement = this.sourceCompiler.getCallElement();
                    throw new CompilationException(str, exc, callElement instanceof PsiElement ? (PsiElement) callElement : null);
                }
            } catch (Exception e2) {
                e = e2;
                sMAPAndMethodNodeCompileInline = null;
            }
        } catch (CompilationException e3) {
            throw e3;
        } catch (InlineException e4) {
            String str2 = "Couldn't inline method call: " + this.sourceCompiler.getCallElementText();
            InlineException inlineException = e4;
            Object callElement2 = this.sourceCompiler.getCallElement();
            throw new CompilationException(str2, inlineException, callElement2 instanceof PsiElement ? (PsiElement) callElement2 : null);
        }
    }

    private final InlineResult inlineCall(SMAPAndMethodNode nodeAndSmap, boolean isInlineOnly) {
        MethodNode node = nodeAndSmap.getNode();
        if (this.maskStartIndex != -1) {
            Parameters parametersBuildParameters = this.invocationParamBuilder.buildParameters();
            int i = this.maskStartIndex;
            ArrayList<Integer> arrayList = this.maskValues;
            int i2 = this.methodHandleInDefaultMethodIndex;
            List<ParameterInfo> parameters = parametersBuildParameters.getParameters();
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : parameters) {
                if (((ParameterInfo) obj).getFunctionalArgument() == DefaultValueOfInlineParameter.INSTANCE) {
                    arrayList2.add(obj);
                }
            }
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                linkedHashSet.add(Integer.valueOf(parametersBuildParameters.getDeclarationSlot((ParameterInfo) it.next())));
            }
            for (ExtractedDefaultLambda extractedDefaultLambda : DefaultMethodUtilKt.expandMaskConditionsAndUpdateVariableNodes(node, i, arrayList, i2, linkedHashSet)) {
                SourceCompilerForInline sourceCompilerForInline = this.sourceCompiler;
                String name2 = node.f1016name;
                Intrinsics.checkNotNullExpressionValue(name2, "name");
                DefaultLambda defaultLambda = new DefaultLambda(extractedDefaultLambda, sourceCompilerForInline, StringsKt.substringBeforeLast$default(name2, "$default", (String) null, 2, (Object) null));
                parametersBuildParameters.getParameterByDeclarationSlot(extractedDefaultLambda.getOffset()).setFunctionalArgument(defaultLambda);
                if (extractedDefaultLambda.getNeedReification()) {
                    defaultLambda.getReifiedTypeParametersUsages().mergeAll(this.reifiedTypeInliner.reifyInstructions(defaultLambda.getNode().getNode()));
                }
                for (CapturedParamDesc capturedParamDesc : defaultLambda.getCapturedVars()) {
                    CapturedParamInfo capturedParamInfoAddCapturedParam = this.invocationParamBuilder.addCapturedParam(capturedParamDesc, capturedParamDesc.getFieldName(), false);
                    capturedParamInfoAddCapturedParam.setRemapValue(StackValue.local(this.codegen.getFrameMap().enterTemp(capturedParamInfoAddCapturedParam.getType()), capturedParamInfoAddCapturedParam.getType()));
                    capturedParamInfoAddCapturedParam.setSynthetic(true);
                }
            }
        }
        ReifiedTypeParametersUsages reifiedTypeParametersUsagesReifyInstructions = this.reifiedTypeInliner.reifyInstructions(node);
        Parameters parametersBuildParameters2 = this.invocationParamBuilder.buildParameters();
        GenerationState generationState = this.state;
        NameGenerator nameGeneratorSubGenerator = this.codegen.getInlineNameGenerator().subGenerator(this.jvmSignature.getAsmMethod().getName());
        Intrinsics.checkNotNullExpressionValue(nameGeneratorSubGenerator, "subGenerator(...)");
        SourceCompilerForInline sourceCompilerForInline2 = this.sourceCompiler;
        RootInliningContext rootInliningContext = new RootInliningContext(generationState, nameGeneratorSubGenerator, sourceCompilerForInline2, sourceCompilerForInline2.getInlineCallSiteInfo(), this.reifiedTypeInliner, this.typeParameterMappings);
        SourceMapper sourceMapper = this.sourceCompiler.getSourceMapper();
        SourceInfo sourceInfo = sourceMapper.getSourceInfo();
        Intrinsics.checkNotNull(sourceInfo);
        int lastLineNumber = this.codegen.getLastLineNumber();
        String sourceFileName = sourceInfo.getSourceFileName();
        Intrinsics.checkNotNull(sourceFileName);
        SourcePosition sourcePosition = new SourcePosition(lastLineNumber, sourceFileName, sourceInfo.getPathOrCleanFQN());
        FieldRemapper fieldRemapper = new FieldRemapper(null, null, parametersBuildParameters2);
        boolean zIsCallInsideSameModuleAsCallee = this.sourceCompiler.isCallInsideSameModuleAsCallee();
        String str = "Method inlining " + this.sourceCompiler.getCallElementText();
        SourceMapCopier sourceMapCopier = new SourceMapCopier(sourceMapper, nodeAndSmap.getClassSMAP(), sourcePosition);
        InlineCallSiteInfo callSiteInfo = rootInliningContext.getCallSiteInfo();
        boolean z = !isInlinedToInlineFunInKotlinRuntime();
        int i3 = this.maskStartIndex;
        MethodInliner methodInliner = new MethodInliner(node, parametersBuildParameters2, rootInliningContext, fieldRemapper, zIsCallInsideSameModuleAsCallee, str, sourceMapCopier, callSiteInfo, isInlineOnly, z, i3, i3 + this.maskValues.size());
        LocalVarRemapper localVarRemapper = new LocalVarRemapper(parametersBuildParameters2, this.initialFrameSize);
        MethodNode methodNodeCreateEmptyMethodNode = InlineCodegenUtilsKt.createEmptyMethodNode();
        methodNodeCreateEmptyMethodNode.visitInsn(0);
        InlineResult inlineResultDoInline = methodInliner.doInline(methodNodeCreateEmptyMethodNode, localVarRemapper, true, MapsKt.emptyMap());
        inlineResultDoInline.getReifiedTypeParametersUsages().mergeAll(reifiedTypeParametersUsagesReifyInstructions);
        List<MethodInliner.PointForExternalFinallyBlocks> listProcessReturns = MethodInliner.INSTANCE.processReturns(methodNodeCreateEmptyMethodNode, this.sourceCompiler.getContextLabels(), null);
        StackValue stackValue = localVarRemapper.remap(parametersBuildParameters2.getArgsSizeOnStack()).value;
        Intrinsics.checkNotNull(stackValue, "null cannot be cast to non-null type org.jetbrains.kotlin.codegen.StackValue.Local");
        generateAndInsertFinallyBlocks(methodNodeCreateEmptyMethodNode, listProcessReturns, ((StackValue.Local) stackValue).index);
        if (!this.sourceCompiler.isFinallyMarkerRequired()) {
            InlineCodegenUtilsKt.removeFinallyMarkers(methodNodeCreateEmptyMethodNode);
        }
        if (rootInliningContext.getGenerateAssertField()) {
            generateAssertField();
        }
        boolean zRequiresEmptyStackOnEntry = INSTANCE.requiresEmptyStackOnEntry(node);
        if (zRequiresEmptyStackOnEntry) {
            InlineCodegenUtilsKt.addInlineMarker(this.codegen.getVisitor(), true);
        }
        methodNodeCreateEmptyMethodNode.accept(new MethodBodyVisitor(this.codegen.getVisitor()));
        if (zRequiresEmptyStackOnEntry) {
            InlineCodegenUtilsKt.addInlineMarker(this.codegen.getVisitor(), false);
        }
        return inlineResultDoInline;
    }

    private final void generateAndInsertFinallyBlocks(MethodNode intoNode, List<MethodInliner.PointForExternalFinallyBlocks> insertPoints, int offsetForFinallyLocalVar) {
        if (this.sourceCompiler.hasFinallyBlocks()) {
            List<MethodInliner.PointForExternalFinallyBlocks> list = insertPoints;
            LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
            for (Object obj : list) {
                linkedHashMap.put(((MethodInliner.PointForExternalFinallyBlocks) obj).beforeIns, obj);
            }
            DefaultProcessor defaultProcessor = new DefaultProcessor(intoNode, offsetForFinallyLocalVar);
            int constant = 0;
            for (AbstractInsnNode first = intoNode.instructions.getFirst(); first != null; first = first.getNext()) {
                defaultProcessor.processInstruction(first, true);
                if (InlineCodegenUtilsKt.isFinallyStart(first)) {
                    AbstractInsnNode previous = first.getPrevious();
                    Intrinsics.checkNotNullExpressionValue(previous, "getPrevious(...)");
                    constant = InlineCodegenUtilsKt.getConstant(previous);
                }
                MethodInliner.PointForExternalFinallyBlocks pointForExternalFinallyBlocks = (MethodInliner.PointForExternalFinallyBlocks) linkedHashMap.get(first);
                if (pointForExternalFinallyBlocks != null) {
                    int nextFreeLocalIndex = defaultProcessor.getNextFreeLocalIndex();
                    for (T t : defaultProcessor.getLocalVarsMetaInfo().getCurrentIntervals()) {
                        nextFreeLocalIndex = Math.max(t.getNode().index + offsetForFinallyLocalVar + Type.getType(t.getNode().desc).getSize(), nextFreeLocalIndex);
                    }
                    Label label = new Label();
                    MethodNode methodNodeCreateEmptyMethodNode = InlineCodegenUtilsKt.createEmptyMethodNode();
                    methodNodeCreateEmptyMethodNode.visitLabel(label);
                    FrameMapBase<?>.Mark markSkipTo = this.codegen.getFrameMap().skipTo(nextFreeLocalIndex);
                    SourceCompilerForInline sourceCompilerForInline = this.sourceCompiler;
                    Type type = pointForExternalFinallyBlocks.returnType;
                    Label label2 = pointForExternalFinallyBlocks.finallyIntervalEnd.getLabel();
                    Intrinsics.checkNotNullExpressionValue(label2, "getLabel(...)");
                    sourceCompilerForInline.generateFinallyBlocks(methodNodeCreateEmptyMethodNode, constant, type, label2, pointForExternalFinallyBlocks.jumpTarget);
                    markSkipTo.dropTo();
                    InlineCodegenUtilsKt.insertNodeBefore(methodNodeCreateEmptyMethodNode, intoNode, first);
                    Object obj2 = label.info;
                    Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.LabelNode");
                    SimpleInterval simpleInterval = new SimpleInterval((LabelNode) obj2, pointForExternalFinallyBlocks.finallyIntervalEnd);
                    defaultProcessor.getTryBlocksMetaInfo().splitAndRemoveCurrentIntervals(simpleInterval, true);
                    defaultProcessor.getLocalVarsMetaInfo().splitAndRemoveCurrentIntervals(simpleInterval, true);
                    List<LocalVariableNode> localVariables = methodNodeCreateEmptyMethodNode.localVariables;
                    Intrinsics.checkNotNullExpressionValue(localVariables, "localVariables");
                    for (LocalVariableNode localVariableNode : localVariables) {
                        IntervalMetaInfo<LocalVarNodeWrapper> localVarsMetaInfo = defaultProcessor.getLocalVarsMetaInfo();
                        Intrinsics.checkNotNull(localVariableNode);
                        localVarsMetaInfo.addNewInterval(new LocalVarNodeWrapper(localVariableNode));
                    }
                }
            }
            defaultProcessor.substituteTryBlockNodes(intoNode);
            defaultProcessor.substituteLocalVarTable(intoNode);
        }
    }

    protected final void rememberClosure(Type parameterType, int index, LambdaInfo lambdaInfo) {
        Intrinsics.checkNotNullParameter(parameterType, "parameterType");
        Intrinsics.checkNotNullParameter(lambdaInfo, "lambdaInfo");
        this.invocationParamBuilder.addNextValueParameter(parameterType, true, null, index).setFunctionalArgument(lambdaInfo);
    }

    protected final void putCapturedToLocalVal(StackValue stackValue, CapturedParamDesc capturedParam, KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(stackValue, "stackValue");
        Intrinsics.checkNotNullParameter(capturedParam, "capturedParam");
        CapturedParamInfo capturedParamInfoAddCapturedParam = this.invocationParamBuilder.addCapturedParam(capturedParam, capturedParam.getFieldName(), false);
        if (INSTANCE.isLocalWithNoBoxing(stackValue, new JvmKotlinType(capturedParamInfoAddCapturedParam.getType(), kotlinType))) {
            capturedParamInfoAddCapturedParam.setRemapValue(stackValue);
            return;
        }
        stackValue.put(capturedParamInfoAddCapturedParam.getType(), kotlinType, this.codegen.getVisitor());
        StackValue.Local local = StackValue.local(this.codegen.getFrameMap().enterTemp(capturedParamInfoAddCapturedParam.getType()), capturedParamInfoAddCapturedParam.getType());
        Intrinsics.checkNotNullExpressionValue(local, "local(...)");
        local.store(StackValue.onStack(capturedParamInfoAddCapturedParam.getType()), this.codegen.getVisitor());
        capturedParamInfoAddCapturedParam.setRemapValue(local);
        capturedParamInfoAddCapturedParam.setSynthetic(true);
    }

    protected final void putArgumentToLocalVal(JvmKotlinType jvmKotlinType, StackValue stackValue, int parameterIndex, ValueKind kind) {
        ParameterInfo parameterInfoAddNextValueParameter;
        Intrinsics.checkNotNullParameter(jvmKotlinType, "jvmKotlinType");
        Intrinsics.checkNotNullParameter(stackValue, "stackValue");
        Intrinsics.checkNotNullParameter(kind, "kind");
        if (kind == ValueKind.DEFAULT_MASK || kind == ValueKind.METHOD_HANDLE_IN_DEFAULT) {
            processDefaultMaskOrMethodHandler(stackValue, kind);
            return;
        }
        NonInlineArgumentForInlineSuspendParameter nonInlineArgumentForInlineSuspendParameter = null;
        if (parameterIndex == -1) {
            parameterInfoAddNextValueParameter = ParametersBuilder.addNextParameter$default(this.invocationParamBuilder, jvmKotlinType.getType(), false, null, 4, null);
        } else {
            parameterInfoAddNextValueParameter = this.invocationParamBuilder.addNextValueParameter(jvmKotlinType.getType(), false, null, parameterIndex);
        }
        int i = WhenMappings.$EnumSwitchMapping$0[kind.ordinal()];
        if (i == 1) {
            nonInlineArgumentForInlineSuspendParameter = NonInlineArgumentForInlineSuspendParameter.INLINE_LAMBDA_AS_VARIABLE;
        } else if (i == 2) {
            nonInlineArgumentForInlineSuspendParameter = NonInlineArgumentForInlineSuspendParameter.OTHER;
        } else if (i == 3) {
            nonInlineArgumentForInlineSuspendParameter = DefaultValueOfInlineParameter.INSTANCE;
        }
        parameterInfoAddNextValueParameter.setFunctionalArgument(nonInlineArgumentForInlineSuspendParameter);
        if (kind == ValueKind.DEFAULT_PARAMETER || kind == ValueKind.DEFAULT_INLINE_PARAMETER) {
            this.codegen.getFrameMap().enterTemp(parameterInfoAddNextValueParameter.getType());
        } else if (INSTANCE.isLocalWithNoBoxing(stackValue, jvmKotlinType)) {
            parameterInfoAddNextValueParameter.setRemapValue(stackValue);
        } else {
            stackValue.put(parameterInfoAddNextValueParameter.getType(), jvmKotlinType.getKotlinType(), this.codegen.getVisitor());
            this.codegen.getVisitor().store(this.codegen.getFrameMap().enterTemp(parameterInfoAddNextValueParameter.getType()), parameterInfoAddNextValueParameter.getType());
        }
    }

    private final void leaveTemps() {
        for (ParameterInfo parameterInfo : CollectionsKt.asReversed(this.invocationParamBuilder.listAllParams())) {
            if (!parameterInfo.isSkippedOrRemapped() || CapturedParamInfo.INSTANCE.isSynthetic(parameterInfo)) {
                this.codegen.getFrameMap().leaveTemp(parameterInfo.getType());
            }
        }
    }

    private final void processDefaultMaskOrMethodHandler(StackValue value, ValueKind kind) {
        boolean z = value instanceof StackValue.Constant;
        Intrinsics.checkNotNull(value, "null cannot be cast to non-null type org.jetbrains.kotlin.codegen.StackValue.Constant");
        Object obj = ((StackValue.Constant) value).value;
        if (kind == ValueKind.DEFAULT_MASK) {
            boolean z2 = obj instanceof Integer;
            ArrayList<Integer> arrayList = this.maskValues;
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
            arrayList.add((Integer) obj);
            if (this.maskStartIndex == -1) {
                int size = 0;
                for (ParameterInfo parameterInfo : this.invocationParamBuilder.listAllParams()) {
                    size += parameterInfo instanceof CapturedParamInfo ? 0 : parameterInfo.getType().getSize();
                }
                this.maskStartIndex = size;
                return;
            }
            return;
        }
        this.methodHandleInDefaultMethodIndex = this.maskStartIndex + this.maskValues.size();
    }

    /* JADX INFO: compiled from: InlineCodegen.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0002J\u001c\u0010\u0003\u001a\u00020\u0004*\u00020\u00052\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\nH\u0002J\f\u0010\u000b\u001a\u00020\u0004*\u00020\fH\u0002J\u0014\u0010\r\u001a\u00020\u0004*\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\f\u0010\u0010\u001a\u00020\u0004*\u00020\u0005H\u0002¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/InlineCodegen$Companion;", "", "()V", "isBackwardsJump", "", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "insn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", TypedValues.TransitionType.S_FROM, "to", "Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;", "isCapturedInlineParameter", "Lorg/jetbrains/kotlin/codegen/StackValue;", "isLocalWithNoBoxing", "expected", "Lorg/jetbrains/kotlin/codegen/JvmKotlinType;", "requiresEmptyStackOnEntry", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isLocalWithNoBoxing(StackValue stackValue, JvmKotlinType jvmKotlinType) {
            return AsmUtil.isPrimitive(jvmKotlinType.getType()) == AsmUtil.isPrimitive(stackValue.type) && !StackValue.requiresInlineClassBoxingOrUnboxing(stackValue.type, stackValue.kotlinType, jvmKotlinType.getType(), jvmKotlinType.getKotlinType()) && ((stackValue instanceof StackValue.Local) || isCapturedInlineParameter(stackValue));
        }

        private final boolean isCapturedInlineParameter(StackValue stackValue) {
            if (stackValue instanceof StackValue.FieldForSharedVar) {
                stackValue = ((StackValue.FieldForSharedVar) stackValue).receiver;
            }
            Intrinsics.checkNotNull(stackValue);
            if (stackValue instanceof StackValue.Field) {
                StackValue.Field field = (StackValue.Field) stackValue;
                if ((field.descriptor instanceof ParameterDescriptor) && InlineUtil.isInlineParameter((ParameterDescriptor) field.descriptor) && InlineUtil.isInline(((ParameterDescriptor) field.descriptor).getContainingModule())) {
                    return true;
                }
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean requiresEmptyStackOnEntry(MethodNode methodNode) {
            List<TryCatchBlockNode> tryCatchBlocks = methodNode.tryCatchBlocks;
            Intrinsics.checkNotNullExpressionValue(tryCatchBlocks, "tryCatchBlocks");
            if (tryCatchBlocks.isEmpty()) {
                AbstractInsnNode[] array = methodNode.instructions.toArray();
                Intrinsics.checkNotNullExpressionValue(array, "toArray(...)");
                for (AbstractInsnNode abstractInsnNode : array) {
                    Intrinsics.checkNotNull(abstractInsnNode);
                    if (!InlineCodegenUtilsKt.isBeforeSuspendMarker(abstractInsnNode) && !InlineCodegenUtilsKt.isBeforeInlineSuspendMarker(abstractInsnNode) && !InlineCodegen.INSTANCE.isBackwardsJump(methodNode, abstractInsnNode)) {
                    }
                }
                return false;
            }
            return true;
        }

        private final boolean isBackwardsJump(MethodNode methodNode, AbstractInsnNode abstractInsnNode) {
            if (abstractInsnNode instanceof JumpInsnNode) {
                LabelNode label = ((JumpInsnNode) abstractInsnNode).label;
                Intrinsics.checkNotNullExpressionValue(label, "label");
                return isBackwardsJump(methodNode, abstractInsnNode, label);
            }
            if (abstractInsnNode instanceof LookupSwitchInsnNode) {
                LookupSwitchInsnNode lookupSwitchInsnNode = (LookupSwitchInsnNode) abstractInsnNode;
                LabelNode labelNode = lookupSwitchInsnNode.dflt;
                if (labelNode == null || !InlineCodegen.INSTANCE.isBackwardsJump(methodNode, abstractInsnNode, labelNode)) {
                    List<LabelNode> labels = lookupSwitchInsnNode.labels;
                    Intrinsics.checkNotNullExpressionValue(labels, "labels");
                    List<LabelNode> list = labels;
                    if (!(list instanceof Collection) || !list.isEmpty()) {
                        for (LabelNode labelNode2 : list) {
                            Companion companion = InlineCodegen.INSTANCE;
                            Intrinsics.checkNotNull(labelNode2);
                            if (companion.isBackwardsJump(methodNode, abstractInsnNode, labelNode2)) {
                            }
                        }
                    }
                    return false;
                }
                return true;
            }
            if (abstractInsnNode instanceof TableSwitchInsnNode) {
                TableSwitchInsnNode tableSwitchInsnNode = (TableSwitchInsnNode) abstractInsnNode;
                LabelNode labelNode3 = tableSwitchInsnNode.dflt;
                if (labelNode3 == null || !InlineCodegen.INSTANCE.isBackwardsJump(methodNode, abstractInsnNode, labelNode3)) {
                    List<LabelNode> labels2 = tableSwitchInsnNode.labels;
                    Intrinsics.checkNotNullExpressionValue(labels2, "labels");
                    List<LabelNode> list2 = labels2;
                    if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                        for (LabelNode labelNode4 : list2) {
                            Companion companion2 = InlineCodegen.INSTANCE;
                            Intrinsics.checkNotNull(labelNode4);
                            if (companion2.isBackwardsJump(methodNode, abstractInsnNode, labelNode4)) {
                            }
                        }
                    }
                }
                return true;
            }
            return false;
        }

        private final boolean isBackwardsJump(MethodNode methodNode, AbstractInsnNode abstractInsnNode, LabelNode labelNode) {
            return methodNode.instructions.indexOf(labelNode) < methodNode.instructions.indexOf(abstractInsnNode);
        }
    }
}
