package org.jetbrains.kotlin.codegen.inline;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.AssertCodegenUtilKt;
import org.jetbrains.kotlin.codegen.ClassBuilder;
import org.jetbrains.kotlin.codegen.CodegenUtilKt;
import org.jetbrains.kotlin.codegen.IrExpressionLambda;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.inline.coroutines.CoroutineTransformer;
import org.jetbrains.kotlin.codegen.inline.coroutines.CoroutineTransformerKt;
import org.jetbrains.kotlin.codegen.optimization.ApiVersionCallsPreprocessingMethodTransformer;
import org.jetbrains.kotlin.codegen.optimization.FixStackWithLabelNormalizationMethodTransformer;
import org.jetbrains.kotlin.codegen.optimization.common.ControlFlowGraph;
import org.jetbrains.kotlin.codegen.optimization.common.InsnSequence;
import org.jetbrains.kotlin.codegen.optimization.common.UtilKt;
import org.jetbrains.kotlin.codegen.optimization.fixStack.FastStackAnalyzer;
import org.jetbrains.kotlin.codegen.optimization.fixStack.FixStackInterpreter;
import org.jetbrains.kotlin.codegen.optimization.fixStack.FixStackValue;
import org.jetbrains.kotlin.codegen.optimization.fixStack.StackTransformationUtilsKt;
import org.jetbrains.kotlin.codegen.optimization.nullCheck.RedundantNullCheckMethodTransformerKt;
import org.jetbrains.kotlin.codegen.optimization.temporaryVals.TemporaryVariablesEliminationTransformer;
import org.jetbrains.kotlin.codegen.pseudoInsns.PseudoInsn;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.utils.SmartList;
import org.jetbrains.kotlin.utils.SmartSet;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.commons.LocalVariablesSorter;
import org.jetbrains.org.objectweb.asm.commons.MethodRemapper;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.FieldInsnNode;
import org.jetbrains.org.objectweb.asm.tree.IincInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.InsnNode;
import org.jetbrains.org.objectweb.asm.tree.JumpInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.LdcInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LineNumberNode;
import org.jetbrains.org.objectweb.asm.tree.LocalVariableNode;
import org.jetbrains.org.objectweb.asm.tree.MethodInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.TryCatchBlockNode;
import org.jetbrains.org.objectweb.asm.tree.VarInsnNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.AnalyzerException;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;
import org.jetbrains.org.objectweb.asm.tree.analysis.Frame;
import org.jetbrains.org.objectweb.asm.tree.analysis.Value;

/* JADX INFO: compiled from: MethodInliner.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000æ\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0005\u0018\u0000 _2\u00020\u0001:\u0003_`aBm\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u000b\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u000b\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0015\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0015¢\u0006\u0002\u0010\u0017J<\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\r2\u0006\u0010'\u001a\u00020\r2\u0012\u0010(\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020*0)2\u0006\u0010+\u001a\u00020\u000b2\u0006\u0010,\u001a\u00020\u000bH\u0002J4\u0010-\u001a\u00020!2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u00020\u000b2\u0014\u00103\u001a\u0010\u0012\u0004\u0012\u00020\r\u0012\u0006\u0012\u0004\u0018\u0001040)J>\u0010-\u001a\u00020!2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u00020\u000b2\u0014\u00103\u001a\u0010\u0012\u0004\u0012\u00020\r\u0012\u0006\u0012\u0004\u0018\u0001040)2\u0006\u00105\u001a\u00020\u0015H\u0002J\u0010\u0010-\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u0017\u00106\u001a\u0004\u0018\u00010*2\u0006\u00107\u001a\u00020\u0015H\u0000¢\u0006\u0002\b8J\u0017\u00106\u001a\u0004\u0018\u00010*2\u0006\u00109\u001a\u00020:H\u0000¢\u0006\u0002\b8J\u0010\u0010;\u001a\u00020\u000b2\u0006\u0010<\u001a\u00020\rH\u0002J\u0012\u0010=\u001a\u00020\u000b2\b\u0010>\u001a\u0004\u0018\u00010?H\u0002J\u0012\u0010@\u001a\u00020\u000b2\b\u0010A\u001a\u0004\u0018\u00010BH\u0002J3\u0010C\u001a\b\u0012\u0004\u0012\u00020B0D2\u0006\u0010E\u001a\u00020F2\u0016\u0010G\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0004\u0012\u00020J\u0018\u00010I0HH\u0002¢\u0006\u0002\u0010KJ.\u0010L\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u00032\u0014\u00103\u001a\u0010\u0012\u0004\u0012\u00020\r\u0012\u0006\u0012\u0004\u0018\u0001040)2\u0006\u00105\u001a\u00020\u0015H\u0002J\u0018\u0010M\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u00105\u001a\u00020\u0015H\u0002J&\u0010N\u001a\u00020O2\u0006\u0010\u0002\u001a\u00020\u00032\u0014\u00103\u001a\u0010\u0012\u0004\u0012\u00020\r\u0012\u0006\u0012\u0004\u0018\u0001040)H\u0002J\u0010\u0010P\u001a\u00020O2\u0006\u0010Q\u001a\u00020#H\u0002J\u0010\u0010R\u001a\u00020O2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u0010\u0010S\u001a\u00020O2\u0006\u0010T\u001a\u00020\u0003H\u0002J\u001e\u0010U\u001a\u00020O2\f\u0010V\u001a\b\u0012\u0004\u0012\u00020B0W2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u0010\u0010X\u001a\u00020O2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J$\u0010Y\u001a\u00060Zj\u0002`[2\u0006\u0010\\\u001a\u00020]2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010^\u001a\u00020\rH\u0002R\u001c\u0010\u0018\u001a\u0010\u0012\u0004\u0012\u00020\r\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\u001bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/MethodInliner;", "", "node", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "parameters", "Lorg/jetbrains/kotlin/codegen/inline/Parameters;", "inliningContext", "Lorg/jetbrains/kotlin/codegen/inline/InliningContext;", "nodeRemapper", "Lorg/jetbrains/kotlin/codegen/inline/FieldRemapper;", "isSameModule", "", "errorPrefix", "", "sourceMapper", "Lorg/jetbrains/kotlin/codegen/inline/SourceMapCopier;", "inlineCallSiteInfo", "Lorg/jetbrains/kotlin/codegen/inline/InlineCallSiteInfo;", "isInlineOnlyMethod", "shouldPreprocessApiVersionCalls", "defaultMaskStart", "", "defaultMaskEnd", "(Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;Lorg/jetbrains/kotlin/codegen/inline/Parameters;Lorg/jetbrains/kotlin/codegen/inline/InliningContext;Lorg/jetbrains/kotlin/codegen/inline/FieldRemapper;ZLjava/lang/String;Lorg/jetbrains/kotlin/codegen/inline/SourceMapCopier;Lorg/jetbrains/kotlin/codegen/inline/InlineCallSiteInfo;ZZII)V", "currentTypeMapping", "Ljava/util/HashMap;", "invokeCalls", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/codegen/inline/InvokeCall;", "lambdasFinallyBlocks", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "result", "Lorg/jetbrains/kotlin/codegen/inline/InlineResult;", "transformations", "Lorg/jetbrains/kotlin/codegen/inline/TransformationInfo;", "buildConstructorInvocation", "Lorg/jetbrains/kotlin/codegen/inline/AnonymousObjectTransformationInfo;", "anonymousType", "desc", "lambdaMapping", "", "Lorg/jetbrains/kotlin/codegen/inline/FunctionalArgument;", "needReification", "capturesAnonymousObjectThatMustBeRegenerated", "doInline", "adapter", "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "remapper", "Lorg/jetbrains/kotlin/codegen/inline/LocalVarRemapper;", "remapReturn", "returnLabels", "Lorg/jetbrains/org/objectweb/asm/Label;", "finallyDeepShift", "getFunctionalArgumentIfExists", "varIndex", "getFunctionalArgumentIfExists$backend", "insnNode", "Lorg/jetbrains/org/objectweb/asm/tree/FieldInsnNode;", "isAlreadyRegenerated", "owner", "isAnonymousClassThatMustBeRegenerated", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "isLambdaCall", "invoke", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "markObsoleteInstruction", "Lorg/jetbrains/kotlin/utils/SmartSet;", "instructions", "Lorg/jetbrains/org/objectweb/asm/tree/InsnList;", "sources", "", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", "(Lorg/jetbrains/org/objectweb/asm/tree/InsnList;[Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;)Lorg/jetbrains/kotlin/utils/SmartSet;", "markPlacesForInlineAndRemoveInlinable", "prepareNode", "preprocessNodeBeforeInline", "", "recordTransformation", CompilerOptions.INFO, "removeFakeVariablesInitializationIfPresent", "replaceContinuationAccessesWithFakeContinuationsIfNeeded", "processingNode", "replaceContinuationsWithFakeOnes", "continuations", "", "transformCaptured", "wrapException", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", "originalException", "", "errorSuffix", "Companion", "LocalReturnsNormalizer", "PointForExternalFinallyBlocks", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MethodInliner {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final HashMap<String, String> currentTypeMapping;
    private final int defaultMaskEnd;
    private final int defaultMaskStart;
    private final String errorPrefix;
    private final InlineCallSiteInfo inlineCallSiteInfo;
    private final InliningContext inliningContext;
    private final ArrayList<InvokeCall> invokeCalls;
    private final boolean isInlineOnlyMethod;
    private final boolean isSameModule;
    private int lambdasFinallyBlocks;
    private final LanguageVersionSettings languageVersionSettings;
    private final MethodNode node;
    private final FieldRemapper nodeRemapper;
    private final Parameters parameters;
    private final InlineResult result;
    private final boolean shouldPreprocessApiVersionCalls;
    private final SourceMapCopier sourceMapper;
    private final ArrayList<TransformationInfo> transformations;

    @JvmStatic
    public static final CapturedParamInfo findCapturedField(FieldInsnNode fieldInsnNode, FieldRemapper fieldRemapper) {
        return INSTANCE.findCapturedField(fieldInsnNode, fieldRemapper);
    }

    @JvmStatic
    public static final List<PointForExternalFinallyBlocks> processReturns(MethodNode methodNode, Map<String, ? extends Label> map, Label label) {
        return INSTANCE.processReturns(methodNode, map, label);
    }

    public MethodInliner(MethodNode node, Parameters parameters, InliningContext inliningContext, FieldRemapper nodeRemapper, boolean z, String errorPrefix, SourceMapCopier sourceMapper, InlineCallSiteInfo inlineCallSiteInfo, boolean z2, boolean z3, int i, int i2) {
        Intrinsics.checkNotNullParameter(node, "node");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        Intrinsics.checkNotNullParameter(inliningContext, "inliningContext");
        Intrinsics.checkNotNullParameter(nodeRemapper, "nodeRemapper");
        Intrinsics.checkNotNullParameter(errorPrefix, "errorPrefix");
        Intrinsics.checkNotNullParameter(sourceMapper, "sourceMapper");
        Intrinsics.checkNotNullParameter(inlineCallSiteInfo, "inlineCallSiteInfo");
        this.node = node;
        this.parameters = parameters;
        this.inliningContext = inliningContext;
        this.nodeRemapper = nodeRemapper;
        this.isSameModule = z;
        this.errorPrefix = errorPrefix;
        this.sourceMapper = sourceMapper;
        this.inlineCallSiteInfo = inlineCallSiteInfo;
        this.isInlineOnlyMethod = z2;
        this.shouldPreprocessApiVersionCalls = z3;
        this.defaultMaskStart = i;
        this.defaultMaskEnd = i2;
        this.languageVersionSettings = inliningContext.getState().getLanguageVersionSettings();
        this.invokeCalls = new ArrayList<>();
        this.transformations = new ArrayList<>();
        this.currentTypeMapping = new HashMap<>();
        this.result = InlineResult.INSTANCE.create();
    }

    public /* synthetic */ MethodInliner(MethodNode methodNode, Parameters parameters, InliningContext inliningContext, FieldRemapper fieldRemapper, boolean z, String str, SourceMapCopier sourceMapCopier, InlineCallSiteInfo inlineCallSiteInfo, boolean z2, boolean z3, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(methodNode, parameters, inliningContext, fieldRemapper, z, str, sourceMapCopier, inlineCallSiteInfo, (i3 & 256) != 0 ? false : z2, (i3 & 512) != 0 ? false : z3, (i3 & 1024) != 0 ? -1 : i, (i3 & 2048) != 0 ? -1 : i2);
    }

    public final InlineResult doInline(MethodVisitor adapter, LocalVarRemapper remapper, boolean remapReturn, Map<String, ? extends Label> returnLabels) {
        Intrinsics.checkNotNullParameter(adapter, "adapter");
        Intrinsics.checkNotNullParameter(remapper, "remapper");
        Intrinsics.checkNotNullParameter(returnLabels, "returnLabels");
        return doInline(adapter, remapper, remapReturn, returnLabels, 0);
    }

    private final void recordTransformation(TransformationInfo info) {
        if (!this.inliningContext.isInliningLambda()) {
            this.inliningContext.getRoot().getState().getGlobalInlineContext().recordTypeFromInlineFunction(info.getOldClassName());
        }
        if (info.shouldRegenerate(this.isSameModule)) {
            this.inliningContext.recordRegeneratedAnonymousObject(info.getOldClassName());
        }
        this.transformations.add(info);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final InlineResult doInline(MethodVisitor adapter, LocalVarRemapper remapper, boolean remapReturn, Map<String, ? extends Label> returnLabels, int finallyDeepShift) throws AnalyzerException {
        MethodBodyVisitor methodBodyVisitor;
        MethodNode methodNodeMarkPlacesForInlineAndRemoveInlinable = markPlacesForInlineAndRemoveInlinable(this.node, returnLabels, finallyDeepShift);
        Label labelLinkedLabel = CodegenUtilKt.linkedLabel();
        boolean z = this.nodeRemapper instanceof RegeneratedLambdaFieldRemapper;
        MethodNode methodNodeDoInline = doInline(methodNodeMarkPlacesForInlineAndRemoveInlinable);
        if (!z) {
            INSTANCE.removeClosureAssertions(methodNodeDoInline);
        }
        methodNodeDoInline.instructions.resetLabels();
        int i = methodNodeDoInline.access;
        String str = methodNodeDoInline.f1016name;
        String str2 = methodNodeDoInline.desc;
        String str3 = methodNodeDoInline.signature;
        List<String> list = methodNodeDoInline.exceptions;
        MethodNode methodNode = new MethodNode(589824, i, str, str2, str3, list != null ? (String[]) list.toArray(new String[0]) : null);
        RemapVisitor remapVisitor = new RemapVisitor(methodNode, remapper, this.nodeRemapper);
        try {
            if (z) {
                methodBodyVisitor = remapVisitor;
            } else {
                methodBodyVisitor = new MethodBodyVisitor(remapVisitor);
            }
            methodNodeDoInline.accept(methodBodyVisitor);
            methodNode.visitLabel(labelLinkedLabel);
            if (this.inliningContext.isRoot()) {
                StackValue stackValue = remapper.remap(this.parameters.getArgsSizeOnStack() + 1).value;
                int i2 = this.lambdasFinallyBlocks;
                Intrinsics.checkNotNull(stackValue, "null cannot be cast to non-null type org.jetbrains.kotlin.codegen.StackValue.Local");
                InternalFinallyBlockInliner.processInlineFunFinallyBlocks(methodNode, i2, ((StackValue.Local) stackValue).index, this.languageVersionSettings.supportsFeature(LanguageFeature.ProperFinally));
            }
            if (remapReturn) {
                INSTANCE.processReturns(methodNode, returnLabels, labelLinkedLabel);
            }
            methodNode.accept(new SkipMaxAndEndVisitor(adapter));
            return this.result;
        } catch (Throwable th) {
            throw wrapException(th, methodNodeDoInline, "couldn't inline method call");
        }
    }

    private final MethodNode doInline(MethodNode node) {
        int line;
        final LinkedList linkedList = new LinkedList(this.invokeCalls);
        final MethodNode methodNode = new MethodNode(node.access, node.f1016name, node.desc, node.signature, null);
        final Iterator<TransformationInfo> it = this.transformations.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        final TypeRemapper typeRemapperCreateFrom = TypeRemapper.INSTANCE.createFrom(this.currentTypeMapping);
        final LocalVariablesSorter localVariablesSorter = new LocalVariablesSorter(methodNode.access, methodNode.desc, InlineCodegenUtilsKt.wrapWithMaxLocalCalc(methodNode));
        final MethodRemapper methodRemapper = new MethodRemapper(localVariablesSorter, new AsmTypeRemapper(typeRemapperCreateFrom, this.result));
        final String strFindFakeContinuationConstructorClassName = CoroutineTransformer.INSTANCE.findFakeContinuationConstructorClassName(node);
        final int iCalcMarkerShift = InlineCodegenUtilsKt.calcMarkerShift(this.parameters, node);
        final Ref.IntRef intRef = new Ref.IntRef();
        if (this.isInlineOnlyMethod) {
            SourcePosition callSite = this.sourceMapper.getCallSite();
            Intrinsics.checkNotNull(callSite);
            line = callSite.getLine();
        } else {
            line = -1;
        }
        intRef.element = line;
        final int argsSizeOnStack = this.parameters.getArgsSizeOnStack();
        final SourceMapCopier sourceMapCopier = this.sourceMapper;
        node.accept(new InlineAdapter(methodRemapper, this, intRef, it, typeRemapperCreateFrom, strFindFakeContinuationConstructorClassName, linkedList, iCalcMarkerShift, localVariablesSorter, methodNode, argsSizeOnStack, sourceMapCopier) { // from class: org.jetbrains.kotlin.codegen.inline.MethodInliner$doInline$lambdaInliner$1
            final /* synthetic */ LinkedList<InvokeCall> $currentInvokes;
            final /* synthetic */ Ref.IntRef $currentLineNumber;
            final /* synthetic */ String $fakeContinuationName;
            final /* synthetic */ Iterator<TransformationInfo> $iterator;
            final /* synthetic */ LocalVariablesSorter $localVariablesSorter;
            final /* synthetic */ int $markerShift;
            final /* synthetic */ TypeRemapper $remapper;
            final /* synthetic */ MethodNode $resultNode;
            final /* synthetic */ MethodInliner this$0;
            private TransformationInfo transformationInfo;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(methodRemapper, argsSizeOnStack, sourceMapCopier);
                this.this$0 = this;
                this.$currentLineNumber = intRef;
                this.$iterator = it;
                this.$remapper = typeRemapperCreateFrom;
                this.$fakeContinuationName = strFindFakeContinuationConstructorClassName;
                this.$currentInvokes = linkedList;
                this.$markerShift = iCalcMarkerShift;
                this.$localVariablesSorter = localVariablesSorter;
                this.$resultNode = methodNode;
            }

            @Override // org.jetbrains.kotlin.codegen.inline.InlineAdapter, org.jetbrains.org.objectweb.asm.MethodVisitor
            public void visitLineNumber(int line2, Label start) {
                Intrinsics.checkNotNullParameter(start, "start");
                if (!this.this$0.isInlineOnlyMethod) {
                    this.$currentLineNumber.element = line2;
                }
                super.visitLineNumber(line2, start);
            }

            private final void handleAnonymousObjectRegeneration() {
                TransformationInfo transformationInfo;
                TransformationInfo next = this.$iterator.next();
                this.transformationInfo = next;
                Intrinsics.checkNotNull(next);
                String oldClassName = next.getOldClassName();
                InliningContext parent = this.this$0.inliningContext.getParent();
                if (Intrinsics.areEqual((parent == null || (transformationInfo = parent.getTransformationInfo()) == null) ? null : transformationInfo.getOldClassName(), oldClassName)) {
                    return;
                }
                TransformationInfo transformationInfo2 = this.transformationInfo;
                Intrinsics.checkNotNull(transformationInfo2);
                if (!transformationInfo2.shouldRegenerate(this.this$0.isSameModule)) {
                    this.this$0.result.addNotChangedClass(oldClassName);
                    return;
                }
                TransformationInfo transformationInfo3 = this.transformationInfo;
                Intrinsics.checkNotNull(transformationInfo3);
                String newClassName = transformationInfo3.getNewClassName();
                this.$remapper.addMapping(oldClassName, newClassName);
                InliningContext inliningContext = this.this$0.inliningContext;
                NameGenerator nameGenerator = this.this$0.inliningContext.getNameGenerator();
                HashMap map = this.this$0.currentTypeMapping;
                InlineCallSiteInfo inlineCallSiteInfo = this.this$0.inlineCallSiteInfo;
                TransformationInfo transformationInfo4 = this.transformationInfo;
                Intrinsics.checkNotNull(transformationInfo4);
                RegeneratedClassContext regeneratedClassContextSubInlineWithClassRegeneration = inliningContext.subInlineWithClassRegeneration(nameGenerator, map, inlineCallSiteInfo, transformationInfo4);
                TransformationInfo transformationInfo5 = this.transformationInfo;
                Intrinsics.checkNotNull(transformationInfo5);
                InlineResult inlineResultDoTransform = transformationInfo5.createTransformer(regeneratedClassContextSubInlineWithClassRegeneration, this.this$0.isSameModule, this.$fakeContinuationName).doTransform(this.this$0.nodeRemapper);
                this.this$0.result.merge(inlineResultDoTransform);
                this.this$0.result.addChangedType(oldClassName, newClassName);
                if (this.this$0.inliningContext.isInliningLambda() && !(this.this$0.inliningContext.getLambdaInfo() instanceof DefaultLambda)) {
                    TransformationInfo transformationInfo6 = this.transformationInfo;
                    Intrinsics.checkNotNull(transformationInfo6);
                    if (transformationInfo6.canRemoveAfterTransformation() && !this.this$0.inliningContext.getRoot().getState().getGlobalInlineContext().isTypeFromInlineFunction(oldClassName)) {
                        this.this$0.result.addClassToRemove(oldClassName);
                    }
                }
                if (inlineResultDoTransform.getReifiedTypeParametersUsages().wereUsedReifiedParameters()) {
                    ReifiedTypeInliner.Companion companion = ReifiedTypeInliner.INSTANCE;
                    MethodVisitor mv = this.mv;
                    Intrinsics.checkNotNullExpressionValue(mv, "mv");
                    companion.putNeedClassReificationMarker(mv);
                    this.this$0.result.getReifiedTypeParametersUsages().mergeAll(inlineResultDoTransform.getReifiedTypeParametersUsages());
                }
                Iterator<ClassBuilder> it2 = regeneratedClassContextSubInlineWithClassRegeneration.getContinuationBuilders().values().iterator();
                while (it2.hasNext()) {
                    it2.next().done(this.this$0.inliningContext.getState().getConfig().getGenerateSmapCopyToAnnotation());
                }
            }

            /* JADX WARN: Code duplicated, block: B:6:0x0021  */
            @Override // org.jetbrains.org.objectweb.asm.commons.InstructionAdapter
            public void anew(Type type) {
                Intrinsics.checkNotNullParameter(type, "type");
                String internalName = type.getInternalName();
                Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
                if (InlineCodegenUtilsKt.isSamWrapper(internalName)) {
                    handleAnonymousObjectRegeneration();
                } else {
                    String internalName2 = type.getInternalName();
                    Intrinsics.checkNotNullExpressionValue(internalName2, "getInternalName(...)");
                    if (InlineCodegenUtilsKt.isAnonymousClass(internalName2)) {
                        handleAnonymousObjectRegeneration();
                    }
                }
                super.anew(type);
            }

            @Override // org.jetbrains.org.objectweb.asm.commons.InstructionAdapter, org.jetbrains.org.objectweb.asm.MethodVisitor
            public void visitMethodInsn(int opcode, String owner, String name2, String desc, boolean itf) throws AnalyzerException {
                TransformationInfo transformationInfo;
                Intrinsics.checkNotNullParameter(owner, "owner");
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(desc, "desc");
                if (InlineCodegenUtilsKt.isInvokeOnLambda(owner, name2)) {
                    this.$currentInvokes.isEmpty();
                    InvokeCall invokeCallRemove = this.$currentInvokes.remove();
                    FunctionalArgument functionalArgument = invokeCallRemove.functionalArgument;
                    if (functionalArgument instanceof LambdaInfo) {
                        SimpleType nullableAnyType = this.this$0.inliningContext.getState().getModule().getBuiltIns().getNullableAnyType();
                        Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getNullableAnyType(...)");
                        LambdaInfo lambdaInfo = (LambdaInfo) functionalArgument;
                        Type[] argumentTypes = lambdaInfo.getInvokeMethod().getArgumentTypes();
                        List<KotlinType> invokeMethodParameters = lambdaInfo.getInvokeMethodParameters();
                        int length = Type.getArgumentTypes(desc).length;
                        if ((functionalArgument instanceof PsiExpressionLambda) && ((PsiExpressionLambda) functionalArgument).getInvokeMethodDescriptor().isSuspend() && length < argumentTypes.length) {
                            InlineCodegenUtilsKt.addFakeContinuationMarker(this);
                            length++;
                        }
                        if (length == argumentTypes.length) {
                            invokeMethodParameters.size();
                        }
                        int iMax = Math.max(getNextLocalIndex(), this.$markerShift);
                        Intrinsics.checkNotNull(argumentTypes);
                        int size = 0;
                        for (Type type : argumentTypes) {
                            size += type.getSize();
                        }
                        int size2 = iMax + size;
                        for (int i = length - 1; -1 < i; i--) {
                            Type type2 = argumentTypes[i];
                            StackValue.coerce(AsmTypes.OBJECT_TYPE, nullableAnyType, type2, invokeMethodParameters.get(i), this);
                            size2 -= type2.getSize();
                            store(size2, type2);
                        }
                        if (argumentTypes.length == 0) {
                            nop();
                        }
                        InsnList instructions = lambdaInfo.getNode().getNode().instructions;
                        Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
                        LineNumberNode lineNumberNode = (LineNumberNode) SequencesKt.firstOrNull(SequencesKt.mapNotNull(UtilKt.asSequence(instructions), new Function1<AbstractInsnNode, LineNumberNode>() { // from class: org.jetbrains.kotlin.codegen.inline.MethodInliner$doInline$lambdaInliner$1$visitMethodInsn$firstLine$1
                            @Override // kotlin.jvm.functions.Function1
                            public final LineNumberNode invoke(AbstractInsnNode it2) {
                                Intrinsics.checkNotNullParameter(it2, "it");
                                if (it2 instanceof LineNumberNode) {
                                    return (LineNumberNode) it2;
                                }
                                return null;
                            }
                        }));
                        int i2 = lineNumberNode != null ? lineNumberNode.line : -1;
                        boolean z = functionalArgument instanceof DefaultLambda;
                        if (z != this.this$0.isInlineOnlyMethod && this.$currentLineNumber.element >= 0 && i2 == this.$currentLineNumber.element) {
                            Label label = new Label();
                            int iMapSyntheticLineNumber = this.this$0.sourceMapper.getParent().mapSyntheticLineNumber(1);
                            this.mv.visitLabel(label);
                            this.mv.visitLineNumber(iMapSyntheticLineNumber, label);
                        }
                        MethodInliner$doInline$lambdaInliner$1 methodInliner$doInline$lambdaInliner$1 = this;
                        InlineCodegenUtilsKt.addInlineMarker(methodInliner$doInline$lambdaInliner$1, true);
                        Parameters parametersAddAllParameters = lambdaInfo.addAllParameters(this.this$0.nodeRemapper);
                        String internalName = lambdaInfo.getLambdaClassType().getInternalName();
                        Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
                        InlinedLambdaRemapper inlinedLambdaRemapper = new InlinedLambdaRemapper(internalName, this.this$0.nodeRemapper, parametersAddAllParameters, z && ((DefaultLambda) functionalArgument).getIsBoundCallableReference());
                        setLambdaInlining(true);
                        SourcePosition callSite2 = this.this$0.sourceMapper.getCallSite();
                        if (!z) {
                            callSite2 = null;
                        }
                        InlineResult inlineResultDoInline = new MethodInliner(lambdaInfo.getNode().getNode(), parametersAddAllParameters, this.this$0.inliningContext.subInlineLambda(lambdaInfo), inlinedLambdaRemapper, z ? this.this$0.isSameModule : true, "Lambda inlining " + lambdaInfo.getLambdaClassType().getInternalName(), new SourceMapCopier(this.this$0.sourceMapper.getParent(), lambdaInfo.getNode().getClassSMAP(), callSite2), this.this$0.inlineCallSiteInfo, false, false, 0, 0, 3584, null).doInline(this.$localVariablesSorter, new LocalVarRemapper(parametersAddAllParameters, size2), true, lambdaInfo.getReturnLabels(), invokeCallRemove.finallyDepthShift);
                        this.this$0.result.mergeWithNotChangeInfo(inlineResultDoInline);
                        this.this$0.result.getReifiedTypeParametersUsages().mergeAll(inlineResultDoInline.getReifiedTypeParametersUsages());
                        this.this$0.result.getReifiedTypeParametersUsages().mergeAll(lambdaInfo.getReifiedTypeParametersUsages());
                        StackValue.coerce(lambdaInfo.getInvokeMethod().getReturnType(), lambdaInfo.getInvokeMethodReturnType(), InlineAdapter.OBJECT_TYPE, nullableAnyType, methodInliner$doInline$lambdaInliner$1);
                        setLambdaInlining(false);
                        InlineCodegenUtilsKt.addInlineMarker(methodInliner$doInline$lambdaInliner$1, false);
                        if (this.$currentLineNumber.element != -1) {
                            Label label2 = new Label();
                            this.mv.visitLabel(label2);
                            if (this.this$0.isInlineOnlyMethod) {
                                this.mv.visitLineNumber(this.$currentLineNumber.element, label2);
                                return;
                            } else {
                                super.visitLineNumber(this.$currentLineNumber.element, label2);
                                return;
                            }
                        }
                        return;
                    }
                    MethodVisitor mv = this.mv;
                    Intrinsics.checkNotNullExpressionValue(mv, "mv");
                    CoroutineTransformerKt.markNoinlineLambdaIfSuspend(mv, functionalArgument);
                    super.visitMethodInsn(opcode, owner, name2, desc, itf);
                    return;
                }
                if (InlineCodegenUtilsKt.isAnonymousConstructorCall(owner, name2)) {
                    TransformationInfo transformationInfo2 = this.transformationInfo;
                    AnonymousObjectTransformationInfo anonymousObjectTransformationInfo = transformationInfo2 instanceof AnonymousObjectTransformationInfo ? (AnonymousObjectTransformationInfo) transformationInfo2 : null;
                    if (anonymousObjectTransformationInfo != null) {
                        InliningContext parent = this.this$0.inliningContext.getParent();
                        if (parent == null || (transformationInfo = parent.getTransformationInfo()) == null || !Intrinsics.areEqual(transformationInfo.getOldClassName(), anonymousObjectTransformationInfo.getOldClassName())) {
                            transformationInfo = null;
                        }
                        AnonymousObjectTransformationInfo anonymousObjectTransformationInfo2 = (AnonymousObjectTransformationInfo) transformationInfo;
                        AnonymousObjectTransformationInfo anonymousObjectTransformationInfo3 = anonymousObjectTransformationInfo2 == null ? anonymousObjectTransformationInfo : anonymousObjectTransformationInfo2;
                        if (anonymousObjectTransformationInfo3.shouldRegenerate(this.this$0.isSameModule)) {
                            for (CapturedParamDesc capturedParamDesc : anonymousObjectTransformationInfo3.getAllRecapturedParameters()) {
                                if (anonymousObjectTransformationInfo2 != null && Intrinsics.areEqual(capturedParamDesc.getFieldName(), "this")) {
                                    Type objectType = Type.getObjectType(owner);
                                    Intrinsics.checkNotNullExpressionValue(objectType, "getObjectType(...)");
                                    capturedParamDesc = new CapturedParamDesc(objectType, AsmUtil.CAPTURED_THIS_FIELD, capturedParamDesc.getType(), false, 8, null);
                                }
                                String containingLambdaName = capturedParamDesc.getContainingLambdaName();
                                String strFoldName = FieldRemapper.INSTANCE.foldName(capturedParamDesc.getFieldName());
                                String descriptor = capturedParamDesc.getType().getDescriptor();
                                Intrinsics.checkNotNullExpressionValue(descriptor, "getDescriptor(...)");
                                visitFieldInsn(178, containingLambdaName, strFoldName, descriptor);
                            }
                            super.visitMethodInsn(opcode, anonymousObjectTransformationInfo3.getNewClassName(), name2, anonymousObjectTransformationInfo3.getNewConstructorDescriptor(), itf);
                            if (this.this$0.inliningContext.getParent() instanceof RegeneratedClassContext) {
                                this.this$0.inliningContext.getParent().getTypeRemapper().addAdditionalMappings(anonymousObjectTransformationInfo3.getOldClassName(), anonymousObjectTransformationInfo3.getNewClassName());
                            }
                            this.transformationInfo = null;
                            return;
                        }
                        super.visitMethodInsn(opcode, owner, name2, desc, itf);
                        return;
                    }
                    throw new AssertionError("<init> call doesn't correspond to object transformation info for '" + owner + '.' + name2 + "': " + this.transformationInfo);
                }
                if (ReifiedTypeInliner.INSTANCE.isNeedClassReificationMarker(new MethodInsnNode(opcode, owner, name2, desc, false))) {
                    if (this.this$0.inliningContext.getShouldReifyTypeParametersInObjects()) {
                        return;
                    }
                    super.visitMethodInsn(opcode, owner, name2, desc, itf);
                    return;
                }
                super.visitMethodInsn(opcode, owner, name2, desc, itf);
            }

            @Override // org.jetbrains.org.objectweb.asm.commons.InstructionAdapter, org.jetbrains.org.objectweb.asm.MethodVisitor
            public void visitFieldInsn(int opcode, String owner, String name2, String desc) {
                Intrinsics.checkNotNullParameter(owner, "owner");
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(desc, "desc");
                if (opcode == 178 && (InlineCodegenUtilsKt.isAnonymousSingletonLoad(owner, name2) || InlineCodegenUtilsKt.isWhenMappingAccess(owner, name2))) {
                    handleAnonymousObjectRegeneration();
                }
                super.visitFieldInsn(opcode, owner, name2, desc);
            }

            @Override // org.jetbrains.kotlin.codegen.inline.InlineAdapter, org.jetbrains.org.objectweb.asm.MethodVisitor
            public void visitMaxs(int stack, int locals) {
                this.this$0.lambdasFinallyBlocks = this.$resultNode.tryCatchBlocks.size();
                super.visitMaxs(stack, locals);
            }
        });
        CoroutineTransformerKt.surroundInvokesWithSuspendMarkersIfNeeded(methodNode);
        return methodNode;
    }

    private final MethodNode prepareNode(MethodNode node, int finallyDeepShift) {
        Type[] argumentTypes;
        ArrayList arrayListListOf;
        InliningContext parent;
        node.instructions.resetLabels();
        final int capturedParametersSizeOnStack = this.parameters.getCapturedParametersSizeOnStack();
        final int realParametersSizeOnStack = this.parameters.getRealParametersSizeOnStack();
        final boolean z = this.inliningContext.isInliningLambda() && (parent = this.inliningContext.getParent()) != null && !parent.isInliningLambda() && (this.inliningContext.getLambdaInfo() instanceof IrExpressionLambda);
        if (z) {
            LambdaInfo lambdaInfo = this.inliningContext.getLambdaInfo();
            Intrinsics.checkNotNull(lambdaInfo);
            argumentTypes = Type.getArgumentTypes(lambdaInfo.getInvokeMethod().getDescriptor());
        } else {
            argumentTypes = Type.getArgumentTypes(node.desc);
        }
        Intrinsics.checkNotNull(argumentTypes);
        int iValueOf = 0;
        if (argumentTypes.length == 0) {
            arrayListListOf = CollectionsKt.listOf(0);
        } else {
            ArrayList arrayList = new ArrayList(argumentTypes.length + 1);
            arrayList.add(0);
            for (Type type : argumentTypes) {
                iValueOf = Integer.valueOf(iValueOf.intValue() + type.getSize());
                arrayList.add(iValueOf);
            }
            arrayListListOf = arrayList;
        }
        ArrayList arrayList2 = new ArrayList();
        int length = argumentTypes.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            Type type2 = argumentTypes[i];
            int i3 = i2 + 1;
            int i4 = this.defaultMaskStart;
            int i5 = this.defaultMaskEnd;
            int iIntValue = ((Number) arrayListListOf.get(i2)).intValue();
            if (!(i4 <= iIntValue && iIntValue <= i5)) {
                arrayList2.add(type2);
            }
            i++;
            i2 = i3;
        }
        Type[] typeArr = (Type[]) ArraysKt.plus(arrayList2.toArray(new Type[0]), (Collection) this.parameters.getCapturedTypes());
        int i6 = node.access;
        String str = node.f1016name;
        String methodDescriptor = Type.getMethodDescriptor(Type.getReturnType(node.desc), (Type[]) Arrays.copyOf(typeArr, typeArr.length));
        String str2 = node.signature;
        List<String> list = node.exceptions;
        final MethodNode methodNode = new MethodNode(589824, i6, str, methodDescriptor, str2, list != null ? (String[]) list.toArray(new String[0]) : null);
        node.accept(new InlineMethodInstructionAdapter(methodNode, z, capturedParametersSizeOnStack, realParametersSizeOnStack) { // from class: org.jetbrains.kotlin.codegen.inline.MethodInliner$prepareNode$transformationVisitor$1
            final /* synthetic */ int $capturedParamsSize;
            final /* synthetic */ int $realParametersSize;
            final /* synthetic */ boolean $reorderIrLambdaParameters;
            private final boolean GENERATE_DEBUG_INFO;
            private final boolean isInliningLambda;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(methodNode);
                this.$reorderIrLambdaParameters = z;
                this.$capturedParamsSize = capturedParametersSizeOnStack;
                this.$realParametersSize = realParametersSizeOnStack;
                this.GENERATE_DEBUG_INFO = !this.this$0.isInlineOnlyMethod;
                this.isInliningLambda = this.this$0.nodeRemapper.getIsInsideInliningLambda();
            }

            /* JADX WARN: Multi-variable type inference failed */
            private final int getNewIndex(int var) {
                LambdaInfo lambdaInfo2 = this.this$0.inliningContext.getLambdaInfo();
                if (!this.$reorderIrLambdaParameters || !(lambdaInfo2 instanceof IrExpressionLambda)) {
                    return var + (var >= this.$realParametersSize ? this.$capturedParamsSize : 0);
                }
                int size = ((IrExpressionLambda) lambdaInfo2).isExtensionLambda() ? lambdaInfo2.getInvokeMethod().getArgumentTypes()[0].getSize() : 0;
                if (var >= this.this$0.parameters.getArgsSizeOnStack()) {
                    return var;
                }
                int i7 = this.$capturedParamsSize;
                if (var >= size + i7) {
                    return var - i7;
                }
                return var >= size ? (var + this.$realParametersSize) - size : var;
            }

            @Override // org.jetbrains.org.objectweb.asm.commons.InstructionAdapter, org.jetbrains.org.objectweb.asm.MethodVisitor
            public void visitVarInsn(int opcode, int var) {
                super.visitVarInsn(opcode, getNewIndex(var));
            }

            @Override // org.jetbrains.org.objectweb.asm.commons.InstructionAdapter, org.jetbrains.org.objectweb.asm.MethodVisitor
            public void visitIincInsn(int var, int increment) {
                super.visitIincInsn(getNewIndex(var), increment);
            }

            @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
            public void visitMaxs(int maxStack, int maxLocals) {
                super.visitMaxs(maxStack, maxLocals + this.$capturedParamsSize);
            }

            @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
            public void visitLineNumber(int line, Label start) {
                Intrinsics.checkNotNullParameter(start, "start");
                if (this.isInliningLambda || this.GENERATE_DEBUG_INFO) {
                    super.visitLineNumber(line, start);
                }
            }

            @Override // org.jetbrains.org.objectweb.asm.commons.InstructionAdapter, org.jetbrains.org.objectweb.asm.MethodVisitor
            public void visitMethodInsn(int opcode, String owner, String name2, String desc, boolean itf) {
                Intrinsics.checkNotNullParameter(owner, "owner");
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(desc, "desc");
                if (Intrinsics.areEqual(InlineCodegenUtilsKt.DEFAULT_LAMBDA_FAKE_CALL, owner)) {
                    FunctionalArgument functionalArgumentIfExists$backend = this.this$0.getFunctionalArgumentIfExists$backend(Integer.parseInt(StringsKt.substringAfter$default(name2, InlineCodegenUtilsKt.DEFAULT_LAMBDA_FAKE_CALL, (String) null, 2, (Object) null)));
                    Intrinsics.checkNotNull(functionalArgumentIfExists$backend, "null cannot be cast to non-null type org.jetbrains.kotlin.codegen.inline.DefaultLambda");
                    DefaultLambda defaultLambda = (DefaultLambda) functionalArgumentIfExists$backend;
                    for (CapturedParamDesc capturedParamDesc : CollectionsKt.asReversed(defaultLambda.getCapturedVars())) {
                        Type originalBoundReceiverType = defaultLambda.getOriginalBoundReceiverType();
                        if (originalBoundReceiverType != null) {
                            StackValue.onStack(originalBoundReceiverType).put(capturedParamDesc.getType(), new InstructionAdapter(this));
                        }
                        super.visitFieldInsn(179, capturedParamDesc.getContainingLambdaName(), InlineCodegenUtilsKt.CAPTURED_FIELD_FOLD_PREFIX + capturedParamDesc.getFieldName(), capturedParamDesc.getType().getDescriptor());
                    }
                    return;
                }
                super.visitMethodInsn(opcode, owner, name2, desc, itf);
            }

            @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
            public void visitLocalVariable(String name2, String desc, String signature, Label start, Label end, int index) {
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(desc, "desc");
                Intrinsics.checkNotNullParameter(start, "start");
                Intrinsics.checkNotNullParameter(end, "end");
                if (this.isInliningLambda || this.GENERATE_DEBUG_INFO) {
                    boolean zStartsWith$default = StringsKt.startsWith$default(name2, JvmAbi.LOCAL_VARIABLE_NAME_PREFIX_INLINE_FUNCTION, false, 2, (Object) null);
                    if (this.this$0.inliningContext.isRoot() && !zStartsWith$default) {
                        if (Intrinsics.areEqual(name2, "this")) {
                            name2 = "this_";
                        }
                        name2 = name2 + InlineCodegenUtilsKt.INLINE_FUN_VAR_SUFFIX;
                    }
                    super.visitLocalVariable(name2, desc, signature, start, end, getNewIndex(index));
                }
            }
        });
        transformCaptured(methodNode);
        INSTANCE.transformFinallyDeepIndex(methodNode, finallyDeepShift);
        return methodNode;
    }

    private final MethodNode markPlacesForInlineAndRemoveInlinable(MethodNode node, Map<String, ? extends Label> returnLabels, int finallyDeepShift) throws AnalyzerException {
        Frame<BasicValue>[] frameArr;
        InsnList insnList;
        Iterator<AbstractInsnNode> it;
        Object functionalArgument;
        InliningContext parent;
        int i;
        Iterator<AbstractInsnNode> it2;
        MethodNode methodNodePrepareNode = prepareNode(node, finallyDeepShift);
        preprocessNodeBeforeInline(methodNodePrepareNode, returnLabels);
        replaceContinuationAccessesWithFakeContinuationsIfNeeded(methodNodePrepareNode);
        Frame<BasicValue>[] frameArrAnalyzeMethodNodeWithInterpreter = MethodInlinerUtilKt.analyzeMethodNodeWithInterpreter(methodNodePrepareNode, new FunctionalArgumentInterpreter(this));
        InsnList insnList2 = methodNodePrepareNode.instructions;
        Intrinsics.checkNotNull(insnList2);
        SmartSet<AbstractInsnNode> smartSetMarkObsoleteInstruction = markObsoleteInstruction(insnList2, frameArrAnalyzeMethodNodeWithInterpreter);
        Iterator<AbstractInsnNode> it3 = new InsnSequence(insnList2).iterator();
        boolean z = false;
        int constant = 0;
        while (it3.hasNext()) {
            AbstractInsnNode next = it3.next();
            Frame<BasicValue> frame = frameArrAnalyzeMethodNodeWithInterpreter[insnList2.indexOf(next)];
            if (frame != null) {
                if (ReifiedTypeInliner.INSTANCE.isNeedClassReificationMarker(next)) {
                    z = true;
                } else if (next instanceof MethodInsnNode) {
                    if (InlineCodegenUtilsKt.isFinallyStart(next)) {
                        AbstractInsnNode previous = ((MethodInsnNode) next).getPrevious();
                        Intrinsics.checkNotNullExpressionValue(previous, "getPrevious(...)");
                        constant = InlineCodegenUtilsKt.getConstant(previous);
                    }
                    MethodInsnNode methodInsnNode = (MethodInsnNode) next;
                    String str = methodInsnNode.owner;
                    String str2 = methodInsnNode.f1015name;
                    Type[] argumentTypes = Type.getArgumentTypes(methodInsnNode.desc);
                    int length = argumentTypes.length + 1;
                    int stackSize = frame.getStackSize() - length;
                    Intrinsics.checkNotNull(str);
                    Intrinsics.checkNotNull(str2);
                    if (InlineCodegenUtilsKt.isInvokeOnLambda(str, str2)) {
                        this.invokeCalls.add(new InvokeCall(MethodInlinerUtilKt.getFunctionalArgument((BasicValue) frame.getStack(stackSize)), constant));
                    } else if (InlineCodegenUtilsKt.isSamWrapperConstructorCall(str, str2)) {
                        recordTransformation(new SamWrapperTransformationInfo(str, this.inliningContext, isAlreadyRegenerated(str)));
                    } else if (InlineCodegenUtilsKt.isAnonymousConstructorCall(str, str2)) {
                        HashMap map = new HashMap();
                        frameArr = frameArrAnalyzeMethodNodeWithInterpreter;
                        int i2 = 0;
                        boolean z2 = false;
                        int size = 0;
                        while (i2 < length) {
                            InsnList insnList3 = insnList2;
                            FunctionalArgument functionalArgument2 = MethodInlinerUtilKt.getFunctionalArgument((BasicValue) frame.getStack(stackSize + i2));
                            if (functionalArgument2 != null) {
                                i = stackSize;
                                it2 = it3;
                                map.put(Integer.valueOf(size), functionalArgument2);
                            } else {
                                i = stackSize;
                                it2 = it3;
                                if (i2 < argumentTypes.length && isAnonymousClassThatMustBeRegenerated(argumentTypes[i2])) {
                                    z2 = true;
                                }
                            }
                            size += i2 == 0 ? 1 : argumentTypes[i2 - 1].getSize();
                            i2++;
                            insnList2 = insnList3;
                            stackSize = i;
                            it3 = it2;
                        }
                        insnList = insnList2;
                        it = it3;
                        String desc = methodInsnNode.desc;
                        Intrinsics.checkNotNullExpressionValue(desc, "desc");
                        recordTransformation(buildConstructorInvocation(str, desc, map, z, z2));
                        z = false;
                    } else {
                        frameArr = frameArrAnalyzeMethodNodeWithInterpreter;
                        insnList = insnList2;
                        it = it3;
                        if (this.inliningContext.isInliningLambda() && ReifiedTypeInliner.INSTANCE.isOperationReifiedMarker(next)) {
                            ReificationArgument reificationArgument = ReifiedTypeInlinerKt.getReificationArgument(methodInsnNode);
                            Intrinsics.checkNotNull(reificationArgument);
                            this.result.getReifiedTypeParametersUsages().addUsedReifiedParameter(reificationArgument.getParameterName());
                        }
                    }
                } else {
                    frameArr = frameArrAnalyzeMethodNodeWithInterpreter;
                    insnList = insnList2;
                    it = it3;
                    if (next.getOpcode() == 178) {
                        FieldInsnNode fieldInsnNode = (FieldInsnNode) next;
                        Intrinsics.checkNotNull(fieldInsnNode);
                        String str3 = fieldInsnNode.owner;
                        Intrinsics.checkNotNull(str3);
                        String name2 = fieldInsnNode.f1010name;
                        Intrinsics.checkNotNullExpressionValue(name2, "name");
                        if (InlineCodegenUtilsKt.isAnonymousSingletonLoad(str3, name2)) {
                            recordTransformation(new AnonymousObjectTransformationInfo(str3, z, isAlreadyRegenerated(str3), true, this.inliningContext.getNameGenerator()));
                            z = false;
                        } else {
                            String name3 = fieldInsnNode.f1010name;
                            Intrinsics.checkNotNullExpressionValue(name3, "name");
                            if (InlineCodegenUtilsKt.isWhenMappingAccess(str3, name3)) {
                                recordTransformation(new WhenMappingTransformationInfo(str3, this.inliningContext.getNameGenerator(), isAlreadyRegenerated(str3), fieldInsnNode));
                            } else if (AssertCodegenUtilKt.isCheckAssertionsStatus(fieldInsnNode)) {
                                fieldInsnNode.owner = this.inlineCallSiteInfo.getOwnerClassName();
                                if (this.inliningContext.getParent() == null) {
                                    parent = this.inliningContext;
                                } else if ((this.inliningContext.getParent() instanceof RegeneratedClassContext) || this.inliningContext.getParent().getParent() == null) {
                                    parent = this.inliningContext.getParent();
                                } else {
                                    Object parent2 = this.inliningContext.getParent().getParent();
                                    functionalArgument = parent2 instanceof RegeneratedClassContext ? (RegeneratedClassContext) parent2 : null;
                                    if (functionalArgument != null) {
                                        parent = (InliningContext) functionalArgument;
                                    } else {
                                        throw new AssertionError("couldn't find class for $assertionsDisabled (context = " + this.inliningContext + ')');
                                    }
                                }
                                parent.setGenerateAssertField(true);
                            } else {
                                continue;
                            }
                        }
                    } else if (next.getOpcode() == 87) {
                        if (MethodInlinerUtilKt.getFunctionalArgument((BasicValue) StackTransformationUtilsKt.top(frame)) instanceof LambdaInfo) {
                            smartSetMarkObsoleteInstruction.add(next);
                        }
                    } else if (next.getOpcode() == 181) {
                        Intrinsics.checkNotNull(next, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.FieldInsnNode");
                        FieldInsnNode fieldInsnNode2 = (FieldInsnNode) next;
                        String name4 = fieldInsnNode2.f1010name;
                        Intrinsics.checkNotNullExpressionValue(name4, "name");
                        if (InlineCodegenUtilsKt.isCapturedFieldName(name4)) {
                            FieldRemapper fieldRemapper = this.nodeRemapper;
                            if ((fieldRemapper instanceof InlinedLambdaRemapper) && Intrinsics.areEqual(fieldRemapper.getOriginalLambdaInternalName(), fieldInsnNode2.owner)) {
                                LinkedHashSet linkedHashSet = new LinkedHashSet();
                                BasicValue basicValue = (BasicValue) StackTransformationUtilsKt.peek(frame, 1);
                                functionalArgument = basicValue != null ? MethodInlinerUtilKt.getFunctionalArgument(basicValue) : null;
                                if (functionalArgument instanceof LambdaInfo) {
                                    LinkedHashSet linkedHashSet2 = linkedHashSet;
                                    if ((linkedHashSet2 instanceof Collection) && linkedHashSet2.isEmpty()) {
                                        Intrinsics.areEqual(((LambdaInfo) functionalArgument).getLambdaClassType().getInternalName(), this.nodeRemapper.getOriginalLambdaInternalName());
                                        FieldRemapper.Companion companion = FieldRemapper.INSTANCE;
                                        String name5 = fieldInsnNode2.f1010name;
                                        Intrinsics.checkNotNullExpressionValue(name5, "name");
                                        fieldInsnNode2.f1010name = companion.foldName(name5);
                                        fieldInsnNode2.setOpcode(179);
                                        smartSetMarkObsoleteInstruction.addAll(linkedHashSet);
                                        break;
                                        break;
                                    }
                                    Iterator it4 = linkedHashSet2.iterator();
                                    do {
                                        if (!it4.hasNext()) {
                                            Intrinsics.areEqual(((LambdaInfo) functionalArgument).getLambdaClassType().getInternalName(), this.nodeRemapper.getOriginalLambdaInternalName());
                                            FieldRemapper.Companion companion2 = FieldRemapper.INSTANCE;
                                            String name6 = fieldInsnNode2.f1010name;
                                            Intrinsics.checkNotNullExpressionValue(name6, "name");
                                            fieldInsnNode2.f1010name = companion2.foldName(name6);
                                            fieldInsnNode2.setOpcode(179);
                                            smartSetMarkObsoleteInstruction.addAll(linkedHashSet);
                                            break;
                                        }
                                    } while (((AbstractInsnNode) it4.next()) instanceof VarInsnNode);
                                }
                            }
                        }
                    }
                }
                frameArr = frameArrAnalyzeMethodNodeWithInterpreter;
                insnList = insnList2;
                it = it3;
            } else {
                frameArr = frameArrAnalyzeMethodNodeWithInterpreter;
                insnList = insnList2;
                it = it3;
                if (next.getType() != 8) {
                    smartSetMarkObsoleteInstruction.add(next);
                }
            }
            frameArrAnalyzeMethodNodeWithInterpreter = frameArr;
            insnList2 = insnList;
            it3 = it;
        }
        MethodInlinerUtilKt.remove(methodNodePrepareNode, smartSetMarkObsoleteInstruction);
        List<TryCatchBlockNode> list = methodNodePrepareNode.tryCatchBlocks;
        final AnonymousClass2 anonymousClass2 = new Function1<TryCatchBlockNode, Boolean>() { // from class: org.jetbrains.kotlin.codegen.inline.MethodInliner.markPlacesForInlineAndRemoveInlinable.2
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(TryCatchBlockNode tryCatchBlockNode) {
                Intrinsics.checkNotNull(tryCatchBlockNode);
                return Boolean.valueOf(CoveringTryCatchNodeProcessorKt.isMeaningless(tryCatchBlockNode));
            }
        };
        list.removeIf(new Predicate() { // from class: org.jetbrains.kotlin.codegen.inline.MethodInliner$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return MethodInliner.markPlacesForInlineAndRemoveInlinable$lambda$5(anonymousClass2, obj);
            }
        });
        return methodNodePrepareNode;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean markPlacesForInlineAndRemoveInlinable$lambda$5(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return ((Boolean) tmp0.invoke(obj)).booleanValue();
    }

    /* JADX WARN: Code duplicated, block: B:30:0x006c  */
    /* JADX WARN: Code duplicated, block: B:32:0x0070  */
    /* JADX WARN: Code duplicated, block: B:34:0x007e  */
    /* JADX WARN: Code duplicated, block: B:36:0x0082  */
    private final SmartSet<AbstractInsnNode> markObsoleteInstruction(InsnList instructions, Frame<BasicValue>[] sources) {
        boolean z;
        SmartSet<AbstractInsnNode> smartSetCreate = SmartSet.INSTANCE.create();
        int i = 0;
        for (AbstractInsnNode abstractInsnNode : instructions) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            AbstractInsnNode abstractInsnNode2 = abstractInsnNode;
            Intrinsics.checkNotNull(abstractInsnNode2);
            if (!MethodInlinerUtilKt.isAloadBeforeCheckParameterIsNotNull(abstractInsnNode2)) {
                int opcode = abstractInsnNode2.getOpcode();
                if (opcode == 25) {
                    Frame<BasicValue> frame = sources[i2];
                    z = MethodInlinerUtilKt.getFunctionalArgument(frame != null ? (BasicValue) StackTransformationUtilsKt.top(frame) : null) instanceof LambdaInfo;
                } else if (opcode == 58) {
                    Frame<BasicValue> frame2 = sources[i];
                    z = MethodInlinerUtilKt.getFunctionalArgument(frame2 != null ? (BasicValue) StackTransformationUtilsKt.top(frame2) : null) instanceof LambdaInfo;
                } else {
                    if (opcode != 95) {
                        switch (opcode) {
                            case 178:
                            case 180:
                                Frame<BasicValue> frame3 = sources[i2];
                                z = MethodInlinerUtilKt.getFunctionalArgument(frame3 != null ? (BasicValue) StackTransformationUtilsKt.top(frame3) : null) instanceof LambdaInfo;
                                break;
                            case 179:
                            case 181:
                                Frame<BasicValue> frame4 = sources[i];
                                z = MethodInlinerUtilKt.getFunctionalArgument(frame4 != null ? (BasicValue) StackTransformationUtilsKt.top(frame4) : null) instanceof LambdaInfo;
                                break;
                        }
                    } else {
                        Frame<BasicValue> frame5 = sources[i];
                        z = true;
                        if (!(MethodInlinerUtilKt.getFunctionalArgument(frame5 != null ? (BasicValue) StackTransformationUtilsKt.peek(frame5, 0) : null) instanceof LambdaInfo)) {
                            Frame<BasicValue> frame6 = sources[i];
                            if (!(MethodInlinerUtilKt.getFunctionalArgument(frame6 != null ? (BasicValue) StackTransformationUtilsKt.peek(frame6, 1) : null) instanceof LambdaInfo)) {
                            }
                        }
                    }
                    z = false;
                }
                if (z) {
                    smartSetCreate.add(abstractInsnNode);
                }
            }
            i = i2;
        }
        return smartSetCreate;
    }

    private final void replaceContinuationAccessesWithFakeContinuationsIfNeeded(final MethodNode processingNode) {
        Set<Integer> indices;
        LambdaInfo lambdaInfo = this.inliningContext.getLambdaInfo();
        if (lambdaInfo != null && (lambdaInfo instanceof PsiExpressionLambda) && ((PsiExpressionLambda) lambdaInfo).getInvokeMethodDescriptor().isSuspend()) {
            Frame<BasicValue>[] frameArrAnalyzeMethodNodeWithInterpreter = MethodInlinerUtilKt.analyzeMethodNodeWithInterpreter(processingNode, new Aload0Interpreter(processingNode));
            final ControlFlowGraph controlFlowGraphBuild$default = ControlFlowGraph.Companion.build$default(ControlFlowGraph.INSTANCE, processingNode, false, 2, null);
            InsnList instructions = processingNode.instructions;
            Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
            Sequence sequenceFilter = SequencesKt.filter(UtilKt.asSequence(instructions), new Function1<AbstractInsnNode, Boolean>() { // from class: org.jetbrains.kotlin.codegen.inline.MethodInliner$replaceContinuationAccessesWithFakeContinuationsIfNeeded$aload0s$1
                /* JADX WARN: Code duplicated, block: B:12:0x001d  */
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(AbstractInsnNode it) {
                    boolean z;
                    Intrinsics.checkNotNullParameter(it, "it");
                    if (it.getOpcode() != 25) {
                        z = false;
                    } else {
                        VarInsnNode varInsnNode = it instanceof VarInsnNode ? (VarInsnNode) it : null;
                        if (varInsnNode == null || varInsnNode.var != 0) {
                            z = false;
                        } else {
                            z = true;
                        }
                    }
                    return Boolean.valueOf(z);
                }
            });
            final HashSet hashSet = new HashSet();
            InsnList instructions2 = processingNode.instructions;
            Intrinsics.checkNotNullExpressionValue(instructions2, "instructions");
            Sequence<AbstractInsnNode> sequenceFilter2 = SequencesKt.filter(SequencesKt.flatMap(SequencesKt.filter(UtilKt.asSequence(instructions2), new Function1<AbstractInsnNode, Boolean>() { // from class: org.jetbrains.kotlin.codegen.inline.MethodInliner$replaceContinuationAccessesWithFakeContinuationsIfNeeded$suspensionPoints$1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(AbstractInsnNode it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(InlineCodegenUtilsKt.isBeforeSuspendMarker(it));
                }
            }), new Function1<AbstractInsnNode, Sequence<? extends AbstractInsnNode>>() { // from class: org.jetbrains.kotlin.codegen.inline.MethodInliner$replaceContinuationAccessesWithFakeContinuationsIfNeeded$suspensionPoints$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Sequence<AbstractInsnNode> invoke(AbstractInsnNode it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return CollectionsKt.asSequence(MethodInliner.replaceContinuationAccessesWithFakeContinuationsIfNeeded$findMeaningfulSuccs(hashSet, controlFlowGraphBuild$default, processingNode, it));
                }
            }), new Function1<AbstractInsnNode, Boolean>() { // from class: org.jetbrains.kotlin.codegen.inline.MethodInliner$replaceContinuationAccessesWithFakeContinuationsIfNeeded$suspensionPoints$3
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(AbstractInsnNode it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(it instanceof MethodInsnNode);
                }
            });
            HashSet hashSet2 = new HashSet();
            for (AbstractInsnNode abstractInsnNode : sequenceFilter2) {
                boolean z = abstractInsnNode instanceof MethodInsnNode;
                Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.MethodInsnNode");
                MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
                Intrinsics.areEqual(Type.getReturnType(methodInsnNode.desc), AsmTypes.OBJECT_TYPE);
                Frame<BasicValue> frame = frameArrAnalyzeMethodNodeWithInterpreter[processingNode.instructions.indexOf(abstractInsnNode)];
                if (frame != null) {
                    Type[] argumentTypes = Type.getArgumentTypes(methodInsnNode.desc);
                    String name2 = methodInsnNode.f1015name;
                    Intrinsics.checkNotNullExpressionValue(name2, "name");
                    if (StringsKt.endsWith$default(name2, "$default", false, 2, (Object) null)) {
                        int length = argumentTypes.length;
                    } else {
                        Intrinsics.checkNotNull(argumentTypes);
                        int length2 = argumentTypes.length;
                    }
                    Intrinsics.checkNotNull(argumentTypes);
                    List listReversed = ArraysKt.reversed(argumentTypes);
                    int size = listReversed.size();
                    for (int i = 0; i < size; i++) {
                        Type type = (Type) listReversed.get(i);
                        if (Intrinsics.areEqual(type, CoroutineCodegenUtilKt.CONTINUATION_ASM_TYPE) || Intrinsics.areEqual(type, AsmTypes.OBJECT_TYPE)) {
                            Value stack = frame.getStack((frame.getStackSize() - i) - 1);
                            Aload0BasicValue aload0BasicValue = stack instanceof Aload0BasicValue ? (Aload0BasicValue) stack : null;
                            if (aload0BasicValue != null && (indices = aload0BasicValue.getIndices()) != null) {
                                Iterator<Integer> it = indices.iterator();
                                while (it.hasNext()) {
                                    AbstractInsnNode abstractInsnNode2 = processingNode.instructions.get(it.next().intValue());
                                    if (SequencesKt.contains(sequenceFilter, abstractInsnNode2)) {
                                        hashSet2.add(abstractInsnNode2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            HashSet hashSet3 = hashSet2;
            CollectionsKt.addAll(hashSet3, SequencesKt.filter(sequenceFilter, new Function1<AbstractInsnNode, Boolean>() { // from class: org.jetbrains.kotlin.codegen.inline.MethodInliner.replaceContinuationAccessesWithFakeContinuationsIfNeeded.5
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(AbstractInsnNode it2) {
                    Intrinsics.checkNotNullParameter(it2, "it");
                    AbstractInsnNode next = it2.getNext();
                    boolean z2 = false;
                    if (next != null && next.getOpcode() == 58) {
                        z2 = true;
                    }
                    return Boolean.valueOf(z2);
                }
            }));
            CollectionsKt.addAll(hashSet3, SequencesKt.filter(sequenceFilter, new Function1<AbstractInsnNode, Boolean>() { // from class: org.jetbrains.kotlin.codegen.inline.MethodInliner.replaceContinuationAccessesWithFakeContinuationsIfNeeded.6
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(AbstractInsnNode it2) {
                    Intrinsics.checkNotNullParameter(it2, "it");
                    return Boolean.valueOf(MethodInliner.this.isLambdaCall(it2.getNext()));
                }
            }));
            replaceContinuationsWithFakeOnes(hashSet3, processingNode);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Collection<AbstractInsnNode> replaceContinuationAccessesWithFakeContinuationsIfNeeded$findMeaningfulSuccs(HashSet<AbstractInsnNode> hashSet, ControlFlowGraph controlFlowGraph, MethodNode methodNode, AbstractInsnNode abstractInsnNode) {
        if (!hashSet.add(abstractInsnNode)) {
            return SetsKt.emptySet();
        }
        HashSet hashSet2 = new HashSet();
        Iterator<Integer> it = controlFlowGraph.getSuccessorsIndices(abstractInsnNode).iterator();
        while (it.hasNext()) {
            AbstractInsnNode abstractInsnNode2 = methodNode.instructions.get(it.next().intValue());
            Intrinsics.checkNotNull(abstractInsnNode2);
            if (UtilKt.isMeaningful(abstractInsnNode2)) {
                hashSet2.add(abstractInsnNode2);
            } else {
                hashSet2.addAll(replaceContinuationAccessesWithFakeContinuationsIfNeeded$findMeaningfulSuccs(hashSet, controlFlowGraph, methodNode, abstractInsnNode2));
            }
        }
        return hashSet2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isLambdaCall(AbstractInsnNode invoke) {
        if (invoke == null || invoke.getOpcode() != 185) {
            return false;
        }
        Intrinsics.checkNotNull(invoke, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.MethodInsnNode");
        MethodInsnNode methodInsnNode = (MethodInsnNode) invoke;
        String owner = methodInsnNode.owner;
        Intrinsics.checkNotNullExpressionValue(owner, "owner");
        if (!StringsKt.startsWith$default(owner, InlineCodegenUtilsKt.NUMBERED_FUNCTION_PREFIX, false, 2, (Object) null) || !Intrinsics.areEqual(methodInsnNode.f1015name, "invoke") || !Intrinsics.areEqual(Type.getReturnType(methodInsnNode.desc), AsmTypes.OBJECT_TYPE)) {
            return false;
        }
        Type[] argumentTypes = Type.getArgumentTypes(methodInsnNode.desc);
        Intrinsics.checkNotNull(argumentTypes);
        return !(argumentTypes.length == 0) && Intrinsics.areEqual(ArraysKt.last(argumentTypes), AsmTypes.OBJECT_TYPE);
    }

    private final void replaceContinuationsWithFakeOnes(Collection<? extends AbstractInsnNode> continuations, MethodNode node) {
        for (AbstractInsnNode abstractInsnNode : continuations) {
            InlineCodegenUtilsKt.insertNodeBefore(InlineCodegenUtilsKt.createFakeContinuationMethodNodeForInline(), node, abstractInsnNode);
            node.instructions.remove(abstractInsnNode);
        }
    }

    private final void preprocessNodeBeforeInline(MethodNode node, Map<String, ? extends Label> returnLabels) throws AnalyzerException {
        try {
            new InplaceArgumentsMethodTransformer().transform("fake", node);
            new FixStackWithLabelNormalizationMethodTransformer().transform("fake", node);
            new TemporaryVariablesEliminationTransformer(this.inliningContext.getState()).transform("fake", node);
            if (this.shouldPreprocessApiVersionCalls) {
                new ApiVersionCallsPreprocessingMethodTransformer(this.inliningContext.getState().getLanguageVersionSettings().getApiVersion()).transform("fake", node);
            }
            removeFakeVariablesInitializationIfPresent(node);
            Frame<FixStackValue>[] frameArrAnalyze = new FastStackAnalyzer("<fake>", node, new FixStackInterpreter()).analyze();
            LocalReturnsNormalizer localReturnsNormalizer = new LocalReturnsNormalizer();
            AbstractInsnNode[] array = node.instructions.toArray();
            Intrinsics.checkNotNullExpressionValue(array, "toArray(...)");
            int length = array.length;
            for (int i = 0; i < length; i++) {
                AbstractInsnNode abstractInsnNode = array[i];
                Frame<FixStackValue> frame = frameArrAnalyze[i];
                if (frame != null && InlineCodegenUtilsKt.isReturnOpcode(abstractInsnNode.getOpcode())) {
                    Intrinsics.checkNotNull(abstractInsnNode);
                    String markedReturnLabelOrNull = InlineCodegenUtilsKt.getMarkedReturnLabelOrNull(abstractInsnNode);
                    if (markedReturnLabelOrNull == null) {
                        localReturnsNormalizer.addLocalReturnToTransform(abstractInsnNode, abstractInsnNode, frame);
                    } else if (returnLabels.containsKey(markedReturnLabelOrNull)) {
                        AbstractInsnNode previous = abstractInsnNode.getPrevious();
                        Intrinsics.checkNotNullExpressionValue(previous, "getPrevious(...)");
                        localReturnsNormalizer.addLocalReturnToTransform(abstractInsnNode, previous, frame);
                    }
                }
            }
            localReturnsNormalizer.transform(node);
        } catch (Throwable th) {
            throw wrapException(th, node, "couldn't inline method call");
        }
    }

    private final void removeFakeVariablesInitializationIfPresent(MethodNode node) {
        AbstractInsnNode[] array = node.instructions.toArray();
        boolean[] zArr = new boolean[node.maxLocals];
        Intrinsics.checkNotNull(array);
        for (AbstractInsnNode abstractInsnNode : array) {
            if (abstractInsnNode.getType() == 2 && abstractInsnNode.getOpcode() == 21) {
                Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.VarInsnNode");
                zArr[((VarInsnNode) abstractInsnNode).var] = true;
            } else if (abstractInsnNode.getType() == 10) {
                Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.IincInsnNode");
                zArr[((IincInsnNode) abstractInsnNode).var] = true;
            }
        }
        for (LocalVariableNode localVariableNode : node.localVariables) {
            char cCharAt = localVariableNode.desc.charAt(0);
            if (cCharAt == 'B' || cCharAt == 'C' || cCharAt == 'S' || cCharAt == 'I' || cCharAt == 'Z') {
                zArr[localVariableNode.index] = true;
            }
        }
        boolean z = false;
        for (AbstractInsnNode abstractInsnNode2 : array) {
            if (abstractInsnNode2.getOpcode() == 3) {
                AbstractInsnNode next = abstractInsnNode2.getNext();
                if (next == null) {
                    break;
                }
                if (next.getOpcode() != 54) {
                    continue;
                } else {
                    AbstractInsnNode next2 = next.getNext();
                    if (next2 == null) {
                        break;
                    }
                    if (next2.getType() == 8 && !zArr[((VarInsnNode) next).var]) {
                        node.instructions.remove(abstractInsnNode2);
                        node.instructions.remove(next);
                        z = true;
                    }
                }
            }
        }
        if (z) {
            UtilKt.removeEmptyCatchBlocks(node);
        }
    }

    private final boolean isAnonymousClassThatMustBeRegenerated(Type type) {
        if (type == null || type.getSort() != 10) {
            return false;
        }
        InliningContext inliningContext = this.inliningContext;
        String internalName = type.getInternalName();
        Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
        return inliningContext.isRegeneratedAnonymousObject(internalName);
    }

    private final AnonymousObjectTransformationInfo buildConstructorInvocation(String anonymousType, String desc, Map<Integer, ? extends FunctionalArgument> lambdaMapping, boolean needReification, boolean capturesAnonymousObjectThatMustBeRegenerated) {
        return new AnonymousObjectTransformationInfo(anonymousType, needReification && !(this.inliningContext.isInliningLambda() && !(this.inliningContext.getLambdaInfo() instanceof DefaultLambda)), lambdaMapping, this.inliningContext.getClassRegeneration(), isAlreadyRegenerated(anonymousType), desc, false, this.inliningContext.getNameGenerator(), capturesAnonymousObjectThatMustBeRegenerated);
    }

    private final boolean isAlreadyRegenerated(String owner) {
        return this.inliningContext.getTypeRemapper().hasNoAdditionalMapping(owner);
    }

    public final FunctionalArgument getFunctionalArgumentIfExists$backend(FieldInsnNode insnNode) {
        Intrinsics.checkNotNullParameter(insnNode, "insnNode");
        String name2 = insnNode.f1010name;
        Intrinsics.checkNotNullExpressionValue(name2, "name");
        if (StringsKt.startsWith$default(name2, InlineCodegenUtilsKt.CAPTURED_FIELD_FOLD_PREFIX, false, 2, (Object) null)) {
            return INSTANCE.findCapturedField(insnNode, this.nodeRemapper).getFunctionalArgument();
        }
        SourceCompilerForInline sourceCompilerForInline = this.inliningContext.getRoot().getSourceCompilerForInline();
        String name3 = insnNode.f1010name;
        Intrinsics.checkNotNullExpressionValue(name3, "name");
        if (sourceCompilerForInline.isSuspendLambdaCapturedByOuterObjectOrLambda(name3)) {
            return NonInlineArgumentForInlineSuspendParameter.INLINE_LAMBDA_AS_VARIABLE;
        }
        return null;
    }

    public final FunctionalArgument getFunctionalArgumentIfExists$backend(int varIndex) {
        if (varIndex < this.parameters.getArgsSizeOnStack()) {
            return this.parameters.getParameterByDeclarationSlot(varIndex).getFunctionalArgument();
        }
        return null;
    }

    private final void transformCaptured(MethodNode node) {
        VarInsnNode varInsnNode;
        int opcode;
        if (this.nodeRemapper.getIsRoot()) {
            return;
        }
        if (this.inliningContext.isInliningLambda() && (this.inliningContext.getLambdaInfo() instanceof IrExpressionLambda)) {
            InliningContext parent = this.inliningContext.getParent();
            Intrinsics.checkNotNull(parent);
            if (!parent.isInliningLambda()) {
                List<CapturedParamDesc> capturedVars = this.inliningContext.getLambdaInfo().getCapturedVars();
                int realParametersSizeOnStack = this.parameters.getRealParametersSizeOnStack();
                List<CapturedParamDesc> list = capturedVars;
                LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
                for (CapturedParamDesc capturedParamDesc : list) {
                    Integer numValueOf = Integer.valueOf(realParametersSizeOnStack);
                    realParametersSizeOnStack += capturedParamDesc.getType().getSize();
                    Pair pair = TuplesKt.to(numValueOf, capturedParamDesc);
                    linkedHashMap.put(pair.getFirst(), pair.getSecond());
                }
                AbstractInsnNode first = node.instructions.getFirst();
                while (first != null) {
                    if ((first instanceof VarInsnNode) && 21 <= (opcode = (varInsnNode = (VarInsnNode) first).getOpcode()) && opcode < 26 && linkedHashMap.containsKey(Integer.valueOf(varInsnNode.var))) {
                        Object obj = linkedHashMap.get(Integer.valueOf(varInsnNode.var));
                        Intrinsics.checkNotNull(obj);
                        CapturedParamDesc capturedParamDesc2 = (CapturedParamDesc) obj;
                        FieldInsnNode fieldInsnNode = new FieldInsnNode(178, capturedParamDesc2.getContainingLambdaName(), FieldRemapper.INSTANCE.foldName(capturedParamDesc2.getFieldName()), capturedParamDesc2.getType().getDescriptor());
                        node.instructions.insertBefore(first, fieldInsnNode);
                        node.instructions.remove(first);
                        first = fieldInsnNode;
                    }
                    first = first.getNext();
                }
            }
        }
        AbstractInsnNode first2 = node.instructions.getFirst();
        while (first2 != null) {
            if (first2 instanceof VarInsnNode) {
                VarInsnNode varInsnNode2 = (VarInsnNode) first2;
                if (varInsnNode2.getOpcode() == 25 && (varInsnNode2.var == 0 || this.nodeRemapper.getIsConstructor())) {
                    AbstractInsnNode abstractInsnNodeFoldFieldAccessChainIfNeeded = this.nodeRemapper.foldFieldAccessChainIfNeeded(INSTANCE.getCapturedFieldAccessChain(varInsnNode2), node);
                    if (abstractInsnNodeFoldFieldAccessChainIfNeeded != null) {
                        first2 = abstractInsnNodeFoldFieldAccessChainIfNeeded;
                    }
                }
            }
            first2 = first2.getNext();
        }
    }

    private final RuntimeException wrapException(Throwable originalException, MethodNode node, String errorSuffix) {
        if (originalException instanceof InlineException) {
            return new InlineException(this.errorPrefix + ": " + errorSuffix, originalException);
        }
        return new InlineException(this.errorPrefix + ": " + errorSuffix + "\nCause: " + InlineCodegenUtilsKt.getNodeText(node), originalException);
    }

    /* JADX INFO: compiled from: MethodInliner.kt */
    @Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0014B\u0005¢\u0006\u0002\u0010\u0002J$\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fJ\u000e\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u0013R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/MethodInliner$LocalReturnsNormalizer;", "", "()V", "localReturns", "Lorg/jetbrains/kotlin/utils/SmartList;", "Lorg/jetbrains/kotlin/codegen/inline/MethodInliner$LocalReturnsNormalizer$LocalReturn;", "returnOpcode", "", "returnVariableSize", "addLocalReturnToTransform", "", "returnInsn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "insertBeforeInsn", "sourceValueFrame", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;", "Lorg/jetbrains/kotlin/codegen/optimization/fixStack/FixStackValue;", "transform", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "LocalReturn", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class LocalReturnsNormalizer {
        private final SmartList<LocalReturn> localReturns = new SmartList<>();
        private int returnOpcode = -1;
        private int returnVariableSize;

        /* JADX INFO: compiled from: MethodInliner.kt */
        @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/MethodInliner$LocalReturnsNormalizer$LocalReturn;", "", "returnInsn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "insertBeforeInsn", TypedValues.AttributesType.S_FRAME, "Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;", "Lorg/jetbrains/kotlin/codegen/optimization/fixStack/FixStackValue;", "(Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;Lorg/jetbrains/org/objectweb/asm/tree/analysis/Frame;)V", "transform", "", "insnList", "Lorg/jetbrains/org/objectweb/asm/tree/InsnList;", "returnVariableIndex", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
        private static final class LocalReturn {
            private final Frame<FixStackValue> frame;
            private final AbstractInsnNode insertBeforeInsn;
            private final AbstractInsnNode returnInsn;

            public LocalReturn(AbstractInsnNode returnInsn, AbstractInsnNode insertBeforeInsn, Frame<FixStackValue> frame) {
                Intrinsics.checkNotNullParameter(returnInsn, "returnInsn");
                Intrinsics.checkNotNullParameter(insertBeforeInsn, "insertBeforeInsn");
                Intrinsics.checkNotNullParameter(frame, "frame");
                this.returnInsn = returnInsn;
                this.insertBeforeInsn = insertBeforeInsn;
                this.frame = frame;
            }

            public final void transform(InsnList insnList, int returnVariableIndex) {
                Intrinsics.checkNotNullParameter(insnList, "insnList");
                int i = this.returnInsn.getOpcode() != 177 ? 1 : 0;
                int stackSize = this.frame.getStackSize();
                if (i == stackSize) {
                    return;
                }
                FixStackValue fixStackValue = (FixStackValue) this.frame.getStack(stackSize - 1);
                if (i != 0) {
                    insnList.insertBefore(this.insertBeforeInsn, new VarInsnNode(fixStackValue.getStoreOpcode(), returnVariableIndex));
                    stackSize--;
                }
                while (stackSize > 0) {
                    insnList.insertBefore(this.insertBeforeInsn, new InsnNode(((FixStackValue) this.frame.getStack(stackSize - 1)).getPopOpcode()));
                    stackSize--;
                }
                if (i != 0) {
                    insnList.insertBefore(this.insertBeforeInsn, new VarInsnNode(fixStackValue.getLoadOpcode(), returnVariableIndex));
                }
            }
        }

        public final void addLocalReturnToTransform(AbstractInsnNode returnInsn, AbstractInsnNode insertBeforeInsn, Frame<FixStackValue> sourceValueFrame) {
            Intrinsics.checkNotNullParameter(returnInsn, "returnInsn");
            Intrinsics.checkNotNullParameter(insertBeforeInsn, "insertBeforeInsn");
            Intrinsics.checkNotNullParameter(sourceValueFrame, "sourceValueFrame");
            InlineCodegenUtilsKt.isReturnOpcode(returnInsn.getOpcode());
            if (this.returnOpcode >= 0) {
                returnInsn.getOpcode();
            }
            this.returnOpcode = returnInsn.getOpcode();
            this.localReturns.add(new LocalReturn(returnInsn, insertBeforeInsn, sourceValueFrame));
            if (returnInsn.getOpcode() != 177) {
                this.returnVariableSize = (returnInsn.getOpcode() == 173 || returnInsn.getOpcode() == 175) ? 2 : 1;
            }
        }

        public final void transform(MethodNode methodNode) {
            int i;
            Intrinsics.checkNotNullParameter(methodNode, "methodNode");
            if (this.returnVariableSize > 0) {
                i = methodNode.maxLocals;
                methodNode.maxLocals += this.returnVariableSize;
            } else {
                i = -1;
            }
            for (LocalReturn localReturn : this.localReturns) {
                InsnList instructions = methodNode.instructions;
                Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
                localReturn.transform(instructions, i);
            }
        }
    }

    /* JADX INFO: compiled from: MethodInliner.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nR\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\b\u001a\u0004\u0018\u00010\t8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/MethodInliner$PointForExternalFinallyBlocks;", "", "beforeIns", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "returnType", "Lorg/jetbrains/org/objectweb/asm/Type;", "finallyIntervalEnd", "Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;", "jumpTarget", "Lorg/jetbrains/org/objectweb/asm/Label;", "(Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;Lorg/jetbrains/org/objectweb/asm/Type;Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;Lorg/jetbrains/org/objectweb/asm/Label;)V", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class PointForExternalFinallyBlocks {
        public final AbstractInsnNode beforeIns;
        public final LabelNode finallyIntervalEnd;
        public final Label jumpTarget;
        public final Type returnType;

        public PointForExternalFinallyBlocks(AbstractInsnNode beforeIns, Type returnType, LabelNode finallyIntervalEnd, Label label) {
            Intrinsics.checkNotNullParameter(beforeIns, "beforeIns");
            Intrinsics.checkNotNullParameter(returnType, "returnType");
            Intrinsics.checkNotNullParameter(finallyIntervalEnd, "finallyIntervalEnd");
            this.beforeIns = beforeIns;
            this.returnType = returnType;
            this.finallyIntervalEnd = finallyIntervalEnd;
            this.jumpTarget = label;
        }
    }

    /* JADX INFO: compiled from: MethodInliner.kt */
    @Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0016\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\rH\u0002J6\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\n2\u0006\u0010\u0005\u001a\u00020\u00102\u0014\u0010\u0011\u001a\u0010\u0012\u0004\u0012\u00020\u0013\u0012\u0006\u0012\u0004\u0018\u00010\u00140\u00122\b\u0010\u0015\u001a\u0004\u0018\u00010\u0014H\u0007J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0005\u001a\u00020\u0010H\u0002J\u0018\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0005\u001a\u00020\u00102\u0006\u0010\u0019\u001a\u00020\u001aH\u0002¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/MethodInliner$Companion;", "", "()V", "findCapturedField", "Lorg/jetbrains/kotlin/codegen/inline/CapturedParamInfo;", "node", "Lorg/jetbrains/org/objectweb/asm/tree/FieldInsnNode;", "fieldRemapper", "Lorg/jetbrains/kotlin/codegen/inline/FieldRemapper;", "getCapturedFieldAccessChain", "", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "aload0", "Lorg/jetbrains/org/objectweb/asm/tree/VarInsnNode;", "processReturns", "Lorg/jetbrains/kotlin/codegen/inline/MethodInliner$PointForExternalFinallyBlocks;", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "returnLabels", "", "", "Lorg/jetbrains/org/objectweb/asm/Label;", "endLabel", "removeClosureAssertions", "", "transformFinallyDeepIndex", "finallyDeepShift", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final CapturedParamInfo findCapturedField(FieldInsnNode node, FieldRemapper fieldRemapper) {
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(fieldRemapper, "fieldRemapper");
            String name2 = node.f1010name;
            Intrinsics.checkNotNullExpressionValue(name2, "name");
            StringsKt.startsWith$default(name2, InlineCodegenUtilsKt.CAPTURED_FIELD_FOLD_PREFIX, false, 2, (Object) null);
            int opcode = node.getOpcode();
            String str = node.owner;
            String name3 = node.f1010name;
            Intrinsics.checkNotNullExpressionValue(name3, "name");
            String strSubstring = name3.substring(3);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            CapturedParamInfo capturedParamInfoFindField$default = FieldRemapper.findField$default(fieldRemapper, new FieldInsnNode(opcode, str, strSubstring, node.desc), null, 2, null);
            if (capturedParamInfoFindField$default != null) {
                return capturedParamInfoFindField$default;
            }
            throw new IllegalStateException("Couldn't find captured field " + node.owner + '.' + node.f1010name + " in " + fieldRemapper.getOriginalLambdaInternalName());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void removeClosureAssertions(MethodNode node) {
            ArrayList arrayList = new ArrayList();
            InsnList instructions = node.instructions;
            Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
            Sequence<MethodInsnNode> sequenceFilter = SequencesKt.filter(new InsnSequence(instructions), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.codegen.inline.MethodInliner$Companion$removeClosureAssertions$$inlined$filterIsInstance$1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(Object obj) {
                    return Boolean.valueOf(obj instanceof MethodInsnNode);
                }
            });
            Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
            for (MethodInsnNode methodInsnNode : sequenceFilter) {
                if (RedundantNullCheckMethodTransformerKt.isCheckParameterIsNotNull(methodInsnNode)) {
                    AbstractInsnNode previous = methodInsnNode.getPrevious();
                    if (previous != null) {
                        previous.getOpcode();
                    }
                    AbstractInsnNode previous2 = methodInsnNode.getPrevious().getPrevious();
                    if (previous2 != null) {
                        previous2.getOpcode();
                    }
                    arrayList.add(previous2);
                    arrayList.add(previous);
                    arrayList.add(methodInsnNode);
                }
            }
            MethodInlinerUtilKt.remove(node, arrayList);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void transformFinallyDeepIndex(MethodNode node, int finallyDeepShift) {
            if (finallyDeepShift == 0) {
                return;
            }
            for (AbstractInsnNode first = node.instructions.getFirst(); first != null; first = first.getNext()) {
                if ((first instanceof MethodInsnNode) && InlineCodegenUtilsKt.isFinallyMarker(first)) {
                    AbstractInsnNode previous = ((MethodInsnNode) first).getPrevious();
                    Intrinsics.checkNotNull(previous);
                    node.instructions.insert(previous, new LdcInsnNode(Integer.valueOf(InlineCodegenUtilsKt.getConstant(previous) + finallyDeepShift)));
                    node.instructions.remove(previous);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final List<AbstractInsnNode> getCapturedFieldAccessChain(VarInsnNode aload0) {
            List<AbstractInsnNode> listMutableListOf = CollectionsKt.mutableListOf(aload0);
            AbstractInsnNode next = aload0.getNext();
            Intrinsics.checkNotNullExpressionValue(next, "getNext(...)");
            listMutableListOf.addAll(SequencesKt.toList(SequencesKt.takeWhile(SequencesKt.filter(new InsnSequence(next, null), new Function1<AbstractInsnNode, Boolean>() { // from class: org.jetbrains.kotlin.codegen.inline.MethodInliner$Companion$getCapturedFieldAccessChain$lambdaAccessChain$1$1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(AbstractInsnNode it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return Boolean.valueOf(UtilKt.isMeaningful(it));
                }
            }), new Function1<AbstractInsnNode, Boolean>() { // from class: org.jetbrains.kotlin.codegen.inline.MethodInliner$Companion$getCapturedFieldAccessChain$lambdaAccessChain$1$2
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(AbstractInsnNode insnNode) {
                    Intrinsics.checkNotNullParameter(insnNode, "insnNode");
                    return Boolean.valueOf((insnNode instanceof FieldInsnNode) && Intrinsics.areEqual(AsmUtil.CAPTURED_THIS_FIELD, ((FieldInsnNode) insnNode).f1010name));
                }
            })));
            AbstractInsnNode nextMeaningful = MethodInlinerUtilKt.getNextMeaningful((AbstractInsnNode) CollectionsKt.last((List) listMutableListOf));
            AbstractInsnNode abstractInsnNode = nextMeaningful instanceof FieldInsnNode ? nextMeaningful : null;
            if (abstractInsnNode != null) {
                listMutableListOf.add(abstractInsnNode);
            }
            return listMutableListOf;
        }

        @JvmStatic
        public final List<PointForExternalFinallyBlocks> processReturns(MethodNode node, Map<String, ? extends Label> returnLabels, Label endLabel) {
            Intrinsics.checkNotNullParameter(node, "node");
            Intrinsics.checkNotNullParameter(returnLabels, "returnLabels");
            ArrayList arrayList = new ArrayList();
            InsnList insnList = node.instructions;
            AbstractInsnNode first = insnList.getFirst();
            while (first != null) {
                if (InlineCodegenUtilsKt.isReturnOpcode(first.getOpcode())) {
                    String markedReturnLabelOrNull = InlineCodegenUtilsKt.getMarkedReturnLabelOrNull(first);
                    Type returnType = InlineCodegenUtilsKt.getReturnType(first.getOpcode());
                    boolean z = markedReturnLabelOrNull == null || returnLabels.containsKey(markedReturnLabelOrNull);
                    Label label = returnLabels.get(markedReturnLabelOrNull);
                    if (label == null) {
                        label = endLabel;
                    }
                    if (z && markedReturnLabelOrNull != null) {
                        insnList.remove(first.getPrevious());
                    }
                    if (z && label != null) {
                        Object obj = label.info;
                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.LabelNode");
                        JumpInsnNode jumpInsnNode = new JumpInsnNode(167, (LabelNode) obj);
                        insnList.insertBefore(first, new InsnNode(0));
                        if (!Intrinsics.areEqual(label, endLabel)) {
                            insnList.insertBefore(first, PseudoInsn.FIX_STACK_BEFORE_JUMP.createInsnNode());
                        }
                        JumpInsnNode jumpInsnNode2 = jumpInsnNode;
                        insnList.insertBefore(first, jumpInsnNode2);
                        insnList.remove(first);
                        first = jumpInsnNode2;
                    }
                    LabelNode labelNode = new LabelNode();
                    insnList.insert(first, labelNode);
                    AbstractInsnNode previous = (z && Intrinsics.areEqual(label, endLabel)) ? first : first.getPrevious();
                    Intrinsics.checkNotNull(previous);
                    arrayList.add(new PointForExternalFinallyBlocks(previous, returnType, labelNode, label));
                }
                first = first.getNext();
            }
            return arrayList;
        }
    }
}
