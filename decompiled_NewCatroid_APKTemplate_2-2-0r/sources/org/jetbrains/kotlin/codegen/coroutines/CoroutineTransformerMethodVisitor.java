package org.jetbrains.kotlin.codegen.coroutines;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SpreadBuilder;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KDeclarationContainer;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import net.bytebuddy.implementation.MethodDelegation;
import org.antlr.stringtemplate.language.ASTExpr;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.ClassBuilder;
import org.jetbrains.kotlin.codegen.CodegenUtilKt;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.TransformationMethodVisitor;
import org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt;
import org.jetbrains.kotlin.codegen.inline.InplaceArgumentsMethodTransformer;
import org.jetbrains.kotlin.codegen.optimization.common.ControlFlowGraph;
import org.jetbrains.kotlin.codegen.optimization.common.InsnSequence;
import org.jetbrains.kotlin.codegen.optimization.common.StrictBasicValue;
import org.jetbrains.kotlin.codegen.optimization.common.UtilKt;
import org.jetbrains.kotlin.codegen.optimization.common.VariableLivenessFrame;
import org.jetbrains.kotlin.codegen.optimization.common.VariableLivenessKt;
import org.jetbrains.kotlin.codegen.optimization.fixStack.FixStackMethodTransformer;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.InsnNode;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.LineNumberNode;
import org.jetbrains.org.objectweb.asm.tree.LocalVariableNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.TableSwitchInsnNode;
import org.jetbrains.org.objectweb.asm.tree.TryCatchBlockNode;
import org.jetbrains.org.objectweb.asm.tree.VarInsnNode;
import org.jetbrains.org.objectweb.asm.tree.analysis.AnalyzerException;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;
import org.jetbrains.org.objectweb.asm.tree.analysis.Frame;

/* JADX INFO: compiled from: CoroutineTransformerMethodVisitor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001TBË\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0007\u0012\u0010\u0010\n\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0007\u0018\u00010\u000b\u0012\u0006\u0010\f\u001a\u00020\u0007\u0012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0011\u0012\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00150\u0014\u0012\u0006\u0010\u0016\u001a\u00020\u0005\u0012\u0006\u0010\u0017\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0011\u0012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u0011\u0012\u0014\b\u0002\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u00050\u001c\u0012\b\b\u0002\u0010\u001e\u001a\u00020\u0011¢\u0006\u0002\u0010\u001fJ\u0010\u0010,\u001a\u00020\u00152\u0006\u0010-\u001a\u00020.H\u0002J \u0010/\u001a\u00020\u00152\u0006\u0010-\u001a\u00020.2\u0006\u00100\u001a\u0002012\u0006\u00102\u001a\u000201H\u0002J\u001e\u00103\u001a\u00020\u00152\u0006\u0010-\u001a\u00020.2\f\u00104\u001a\b\u0012\u0004\u0012\u00020)05H\u0002J\u0016\u00106\u001a\b\u0012\u0004\u0012\u00020)052\u0006\u0010-\u001a\u00020.H\u0002J\u0010\u00107\u001a\u00020\u00152\u0006\u0010-\u001a\u00020.H\u0002J\u001e\u00108\u001a\u00020\u00152\u0006\u0010-\u001a\u00020.2\f\u00104\u001a\b\u0012\u0004\u0012\u00020)05H\u0002J\u0012\u00109\u001a\u0004\u0018\u00010:2\u0006\u0010;\u001a\u00020)H\u0002J\u001e\u0010<\u001a\u00020\u00152\u0006\u0010-\u001a\u00020.2\f\u0010=\u001a\b\u0012\u0004\u0012\u00020(05H\u0002J\"\u0010>\u001a\u0004\u0018\u00010\u00072\u0006\u0010-\u001a\u00020.2\u0006\u0010?\u001a\u00020\u00052\u0006\u0010@\u001a\u00020\u0005H\u0002J\u0012\u0010A\u001a\u0004\u0018\u00010:2\u0006\u0010B\u001a\u00020)H\u0002J\u0010\u0010C\u001a\u00020\u00152\u0006\u0010-\u001a\u00020.H\u0014J\u0010\u0010D\u001a\u00020\u00152\u0006\u0010-\u001a\u00020.H\u0002J\u0010\u0010E\u001a\u00020\u00152\u0006\u0010-\u001a\u00020.H\u0002J\u0010\u0010F\u001a\u00020\u00152\u0006\u0010-\u001a\u00020.H\u0002J*\u0010G\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020H05052\f\u00104\u001a\b\u0012\u0004\u0012\u00020)052\u0006\u0010-\u001a\u00020.H\u0002J\u0018\u0010I\u001a\u00020\u00152\u0006\u0010-\u001a\u00020.2\u0006\u0010;\u001a\u00020)H\u0002J2\u0010J\u001a\u00020(2\u0006\u0010K\u001a\u00020\u00052\u0006\u0010B\u001a\u00020)2\u0006\u0010-\u001a\u00020.2\u0006\u0010L\u001a\u00020\u00052\b\u0010M\u001a\u0004\u0018\u00010:H\u0002J4\u0010N\u001a\u00020\u00152\u0006\u0010-\u001a\u00020.2\u000e\u0010O\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010:052\u0012\u0010P\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020H0505H\u0002J\f\u0010Q\u001a\u00020\u0015*\u00020RH\u0002J\f\u0010S\u001a\u00020\u0015*\u00020RH\u0002R\u001b\u0010 \u001a\u00020\u000f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b#\u0010$\u001a\u0004\b!\u0010\"R\u000e\u0010\f\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u00050\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00150\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010'\u001a\u00020(*\u00020)8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b*\u0010+¨\u0006U"}, d2 = {"Lorg/jetbrains/kotlin/codegen/coroutines/CoroutineTransformerMethodVisitor;", "Lorg/jetbrains/kotlin/codegen/TransformationMethodVisitor;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "access", "", "name", "", "desc", "signature", "exceptions", "", "containingClassInternalName", "obtainClassBuilderForCoroutineState", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "isForNamedFunction", "", "disableTailCallOptimizationForFunctionReturningUnit", "reportSuspensionPointInsideMonitor", "Lkotlin/Function1;", "", "lineNumber", "sourceFile", "needDispatchReceiver", "internalNameForDispatchReceiver", "putContinuationParameterToLvt", "initialVarsCountByType", "", "Lorg/jetbrains/org/objectweb/asm/Type;", "shouldOptimiseUnusedVariables", "(Lorg/jetbrains/org/objectweb/asm/MethodVisitor;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Lkotlin/jvm/functions/Function0;ZZLkotlin/jvm/functions/Function1;ILjava/lang/String;ZLjava/lang/String;ZLjava/util/Map;Z)V", "classBuilderForCoroutineState", "getClassBuilderForCoroutineState", "()Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "classBuilderForCoroutineState$delegate", "Lkotlin/Lazy;", "continuationIndex", "dataIndex", "tryCatchBlockEndLabelAfterSuspensionCall", "Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;", "Lorg/jetbrains/kotlin/codegen/coroutines/SuspensionPoint;", "getTryCatchBlockEndLabelAfterSuspensionCall", "(Lorg/jetbrains/kotlin/codegen/coroutines/SuspensionPoint;)Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;", "addCompletionParameterToLVT", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "addContinuationAndResultToLvt", "startLabel", "Lorg/jetbrains/org/objectweb/asm/Label;", "resultStartLabel", "checkForSuspensionPointInsideMonitor", "suspensionPoints", "", "collectSuspensionPoints", "dropSuspensionMarkers", "dropUnboxInlineClassMarkers", "findSuspensionPointLineNumber", "Lorg/jetbrains/org/objectweb/asm/tree/LineNumberNode;", "suspensionPoint", "initializeFakeInlinerVariables", "stateLabels", "localVariableName", "index", "suspensionCallIndex", "nextDefinitelyHitLineNumber", "suspension", "performTransformations", "prepareMethodNodePreludeForNamedFunction", "removeFakeContinuationConstructorCall", "replaceReturnsUnitMarkersWithPushingUnitOnStack", "spillVariables", "Lorg/jetbrains/kotlin/codegen/coroutines/CoroutineTransformerMethodVisitor$SpilledVariableAndField;", "splitTryCatchBlocksContainingSuspensionPoint", "transformCallAndReturnStateLabel", "id", "suspendMarkerVarIndex", "suspendPointLineNumber", "writeDebugMetadata", "suspensionPointLineNumbers", "spilledToLocalMapping", "getLabel", "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "setLabel", "SpilledVariableAndField", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CoroutineTransformerMethodVisitor extends TransformationMethodVisitor {

    /* JADX INFO: renamed from: classBuilderForCoroutineState$delegate, reason: from kotlin metadata */
    private final Lazy classBuilderForCoroutineState;
    private final String containingClassInternalName;
    private int continuationIndex;
    private int dataIndex;
    private final boolean disableTailCallOptimizationForFunctionReturningUnit;
    private final Map<Type, Integer> initialVarsCountByType;
    private final String internalNameForDispatchReceiver;
    private final boolean isForNamedFunction;
    private final int lineNumber;
    private final boolean needDispatchReceiver;
    private final boolean putContinuationParameterToLvt;
    private final Function1<String, Unit> reportSuspensionPointInsideMonitor;
    private final boolean shouldOptimiseUnusedVariables;
    private final String sourceFile;

    public /* synthetic */ CoroutineTransformerMethodVisitor(MethodVisitor methodVisitor, int i, String str, String str2, String str3, String[] strArr, String str4, Function0 function0, boolean z, boolean z2, Function1 function1, int i2, String str5, boolean z3, String str6, boolean z4, Map map, boolean z5, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(methodVisitor, i, str, str2, str3, strArr, str4, function0, z, z2, function1, i2, str5, (i3 & 8192) != 0 ? false : z3, (i3 & 16384) != 0 ? null : str6, (32768 & i3) != 0 ? true : z4, (65536 & i3) != 0 ? MapsKt.emptyMap() : map, (i3 & 131072) != 0 ? true : z5);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public CoroutineTransformerMethodVisitor(MethodVisitor delegate, int i, String name2, String desc, String str, String[] strArr, String containingClassInternalName, Function0<? extends ClassBuilder> obtainClassBuilderForCoroutineState, boolean z, boolean z2, Function1<? super String, Unit> reportSuspensionPointInsideMonitor, int i2, String sourceFile, boolean z3, String str2, boolean z4, Map<Type, Integer> initialVarsCountByType, boolean z5) {
        super(delegate, i, name2, desc, str, strArr, 0, 64, null);
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(desc, "desc");
        Intrinsics.checkNotNullParameter(containingClassInternalName, "containingClassInternalName");
        Intrinsics.checkNotNullParameter(obtainClassBuilderForCoroutineState, "obtainClassBuilderForCoroutineState");
        Intrinsics.checkNotNullParameter(reportSuspensionPointInsideMonitor, "reportSuspensionPointInsideMonitor");
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        Intrinsics.checkNotNullParameter(initialVarsCountByType, "initialVarsCountByType");
        this.containingClassInternalName = containingClassInternalName;
        this.isForNamedFunction = z;
        this.disableTailCallOptimizationForFunctionReturningUnit = z2;
        this.reportSuspensionPointInsideMonitor = reportSuspensionPointInsideMonitor;
        this.lineNumber = i2;
        this.sourceFile = sourceFile;
        this.needDispatchReceiver = z3;
        this.internalNameForDispatchReceiver = str2;
        this.putContinuationParameterToLvt = z4;
        this.initialVarsCountByType = initialVarsCountByType;
        this.shouldOptimiseUnusedVariables = z5;
        this.classBuilderForCoroutineState = LazyKt.lazy(obtainClassBuilderForCoroutineState);
        this.continuationIndex = z ? -1 : 0;
        this.dataIndex = z ? -1 : 1;
    }

    private final ClassBuilder getClassBuilderForCoroutineState() {
        return (ClassBuilder) this.classBuilderForCoroutineState.getValue();
    }

    @Override // org.jetbrains.kotlin.codegen.TransformationMethodVisitor
    protected void performTransformations(MethodNode methodNode) throws AnalyzerException {
        int lastParameterIndex;
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        removeFakeContinuationConstructorCall(methodNode);
        replaceReturnsUnitMarkersWithPushingUnitOnStack(methodNode);
        if (this.isForNamedFunction) {
            String desc = methodNode.desc;
            Intrinsics.checkNotNullExpressionValue(desc, "desc");
            lastParameterIndex = CoroutineTransformerMethodVisitorKt.getLastParameterIndex(desc, methodNode.access);
        } else {
            lastParameterIndex = 0;
        }
        CoroutineTransformerMethodVisitorKt.replaceFakeContinuationsWithRealOnes(methodNode, lastParameterIndex);
        new InplaceArgumentsMethodTransformer().transform(this.containingClassInternalName, methodNode);
        new FixStackMethodTransformer().transform(this.containingClassInternalName, methodNode);
        List<SuspensionPoint> listCollectSuspensionPoints = collectSuspensionPoints(methodNode);
        new RedundantLocalsEliminationMethodTransformer(listCollectSuspensionPoints).transform(this.containingClassInternalName, methodNode);
        ChangeBoxingMethodTransformer.INSTANCE.transform(this.containingClassInternalName, methodNode);
        UtilKt.updateMaxStack(methodNode);
        checkForSuspensionPointInsideMonitor(methodNode, listCollectSuspensionPoints);
        AbstractInsnNode first = methodNode.instructions.getFirst();
        if (this.isForNamedFunction) {
            if (this.putContinuationParameterToLvt) {
                addCompletionParameterToLVT(methodNode);
            }
            if (TailCallOptimizationKt.allSuspensionPointsAreTailCalls(methodNode, listCollectSuspensionPoints, !this.disableTailCallOptimizationForFunctionReturningUnit)) {
                TailCallOptimizationKt.addCoroutineSuspendedChecks(methodNode, listCollectSuspensionPoints);
                dropSuspensionMarkers(methodNode);
                dropUnboxInlineClassMarkers(methodNode, listCollectSuspensionPoints);
                return;
            } else {
                int i = methodNode.maxLocals;
                methodNode.maxLocals = i + 1;
                this.dataIndex = i;
                int i2 = methodNode.maxLocals;
                methodNode.maxLocals = i2 + 1;
                this.continuationIndex = i2;
                prepareMethodNodePreludeForNamedFunction(methodNode);
            }
        }
        Iterator<SuspensionPoint> it = listCollectSuspensionPoints.iterator();
        while (it.hasNext()) {
            splitTryCatchBlocksContainingSuspensionPoint(methodNode, it.next());
        }
        UtilKt.updateMaxStack(methodNode);
        new UninitializedStoresProcessor(methodNode).run();
        List<List<SpilledVariableAndField>> listSpillVariables = spillVariables(listCollectSuspensionPoints, methodNode);
        int i3 = methodNode.maxLocals;
        methodNode.maxLocals = i3 + 1;
        List<SuspensionPoint> list = listCollectSuspensionPoints;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it2 = list.iterator();
        while (it2.hasNext()) {
            arrayList.add(findSuspensionPointLineNumber((SuspensionPoint) it2.next()));
        }
        ArrayList arrayList2 = arrayList;
        Iterable<IndexedValue> iterableWithIndex = CollectionsKt.withIndex(list);
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterableWithIndex, 10));
        for (IndexedValue indexedValue : iterableWithIndex) {
            arrayList3.add(transformCallAndReturnStateLabel(indexedValue.getIndex() + 1, (SuspensionPoint) indexedValue.getValue(), methodNode, i3, arrayList2.get(indexedValue.getIndex())));
        }
        ArrayList arrayList4 = arrayList3;
        InsnList insnList = methodNode.instructions;
        LabelNode labelNode = new LabelNode();
        LabelNode labelNode2 = new LabelNode();
        LabelNode labelNode3 = new LabelNode();
        SpreadBuilder spreadBuilder = new SpreadBuilder(8);
        MethodNode methodNode2 = new MethodNode();
        CoroutineCodegenUtilKt.loadCoroutineSuspendedMarker(new InstructionAdapter(methodNode2));
        Unit unit = Unit.INSTANCE;
        InsnList instructions = methodNode2.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
        AbstractInsnNode[] array = instructions.toArray();
        Intrinsics.checkNotNullExpressionValue(array, "toArray(...)");
        spreadBuilder.addSpread(array);
        spreadBuilder.add(labelNode);
        spreadBuilder.add(new LineNumberNode(this.lineNumber, labelNode));
        spreadBuilder.add(new VarInsnNode(58, i3));
        spreadBuilder.add(new VarInsnNode(25, this.continuationIndex));
        MethodNode methodNode3 = new MethodNode();
        getLabel(new InstructionAdapter(methodNode3));
        Unit unit2 = Unit.INSTANCE;
        InsnList instructions2 = methodNode3.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions2, "instructions");
        AbstractInsnNode[] array2 = instructions2.toArray();
        Intrinsics.checkNotNullExpressionValue(array2, "toArray(...)");
        spreadBuilder.addSpread(array2);
        int size = listCollectSuspensionPoints.size();
        SpreadBuilder spreadBuilder2 = new SpreadBuilder(2);
        spreadBuilder2.add(labelNode2);
        spreadBuilder2.addSpread(arrayList4.toArray(new LabelNode[0]));
        spreadBuilder.add(new TableSwitchInsnNode(0, size, labelNode3, (LabelNode[]) spreadBuilder2.toArray(new LabelNode[spreadBuilder2.size()])));
        spreadBuilder.add(labelNode2);
        insnList.insertBefore(first, UtilKt.insnListOf((AbstractInsnNode[]) spreadBuilder.toArray(new AbstractInsnNode[spreadBuilder.size()])));
        MethodNode methodNode4 = new MethodNode();
        CoroutineTransformerMethodVisitorKt.generateResumeWithExceptionCheck(new InstructionAdapter(methodNode4), this.dataIndex);
        Unit unit3 = Unit.INSTANCE;
        InsnList instructions3 = methodNode4.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions3, "instructions");
        insnList.insert(labelNode2, instructions3);
        insnList.insert(insnList.getLast(), labelNode3);
        insnList.insert(insnList.getLast(), new LineNumberNode(this.lineNumber, labelNode3));
        AbstractInsnNode last = insnList.getLast();
        MethodNode methodNode5 = new MethodNode();
        InstructionAdapter instructionAdapter = new InstructionAdapter(methodNode5);
        AsmUtil.genThrow(instructionAdapter, "java/lang/IllegalStateException", CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
        instructionAdapter.areturn(Type.VOID_TYPE);
        Unit unit4 = Unit.INSTANCE;
        InsnList instructions4 = methodNode5.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions4, "instructions");
        insnList.insert(last, instructions4);
        initializeFakeInlinerVariables(methodNode, arrayList4);
        dropSuspensionMarkers(methodNode);
        dropUnboxInlineClassMarkers(methodNode, listCollectSuspensionPoints);
        UtilKt.removeEmptyCatchBlocks(methodNode);
        if (this.shouldOptimiseUnusedVariables) {
            CoroutineTransformerMethodVisitorKt.updateLvtAccordingToLiveness(methodNode, this.isForNamedFunction, arrayList4);
        }
        writeDebugMetadata(methodNode, arrayList2, listSpillVariables);
    }

    private final void initializeFakeInlinerVariables(MethodNode methodNode, List<? extends LabelNode> stateLabels) {
        for (LabelNode labelNode : stateLabels) {
            ArrayList arrayList = new ArrayList();
            for (LocalVariableNode localVariableNode : methodNode.localVariables) {
                String name2 = localVariableNode.f1014name;
                Intrinsics.checkNotNullExpressionValue(name2, "name");
                if (InlineCodegenUtilsKt.isFakeLocalVariableForInline(name2)) {
                    LabelNode labelNode2 = labelNode;
                    if (methodNode.instructions.indexOf(localVariableNode.start) < methodNode.instructions.indexOf(labelNode2) && methodNode.instructions.indexOf(labelNode2) < methodNode.instructions.indexOf(localVariableNode.end)) {
                        LabelNode labelNode3 = localVariableNode.end;
                        LabelNode labelNode4 = new LabelNode();
                        localVariableNode.end = labelNode;
                        InsnList insnList = methodNode.instructions;
                        MethodNode methodNode2 = new MethodNode();
                        InstructionAdapter instructionAdapter = new InstructionAdapter(methodNode2);
                        instructionAdapter.iconst(0);
                        instructionAdapter.store(localVariableNode.index, Type.INT_TYPE);
                        Unit unit = Unit.INSTANCE;
                        InsnList instructions = methodNode2.instructions;
                        Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
                        instructions.add(labelNode4);
                        Unit unit2 = Unit.INSTANCE;
                        insnList.insert(labelNode2, instructions);
                        arrayList.add(new LocalVariableNode(localVariableNode.f1014name, localVariableNode.desc, localVariableNode.signature, labelNode4, labelNode3, localVariableNode.index));
                    }
                }
            }
            methodNode.localVariables.addAll(arrayList);
        }
    }

    private final void addCompletionParameterToLVT(MethodNode methodNode) {
        LabelNode labelNode;
        LabelNode labelNode2;
        Type[] argumentTypes = Type.getMethodType(methodNode.desc).getArgumentTypes();
        Intrinsics.checkNotNullExpressionValue(argumentTypes, "getArgumentTypes(...)");
        int size = 0;
        for (Type type : argumentTypes) {
            size += type.getSize();
        }
        int i = (size + (!CoroutineTransformerMethodVisitorKt.isStatic(methodNode.access) ? 1 : 0)) - 1;
        InsnList insnList = methodNode.instructions;
        if (insnList.getFirst() instanceof LabelNode) {
            AbstractInsnNode first = insnList.getFirst();
            Intrinsics.checkNotNull(first, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.LabelNode");
            labelNode = (LabelNode) first;
        } else {
            LabelNode labelNode3 = new LabelNode();
            insnList.insertBefore(insnList.getFirst(), labelNode3);
            labelNode = labelNode3;
        }
        InsnList insnList2 = methodNode.instructions;
        if (insnList2.getLast() instanceof LabelNode) {
            AbstractInsnNode last = insnList2.getLast();
            Intrinsics.checkNotNull(last, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.LabelNode");
            labelNode2 = (LabelNode) last;
        } else {
            LabelNode labelNode4 = new LabelNode();
            insnList2.insert(insnList2.getLast(), labelNode4);
            labelNode2 = labelNode4;
        }
        List<LocalVariableNode> localVariables = methodNode.localVariables;
        Intrinsics.checkNotNullExpressionValue(localVariables, "localVariables");
        List<LocalVariableNode> list = localVariables;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            for (LocalVariableNode localVariableNode : list) {
                if (Intrinsics.areEqual(localVariableNode.f1014name, CoroutineConstantsKt.SUSPEND_FUNCTION_COMPLETION_PARAMETER_NAME)) {
                    LabelNode start = localVariableNode.start;
                    Intrinsics.checkNotNullExpressionValue(start, "start");
                    if (InlineCodegenUtilsKt.areLabelsBeforeSameInsn(labelNode, start)) {
                        LabelNode end = localVariableNode.end;
                        Intrinsics.checkNotNullExpressionValue(end, "end");
                        if (InlineCodegenUtilsKt.areLabelsBeforeSameInsn(labelNode2, end)) {
                            return;
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
        methodNode.localVariables.add(new LocalVariableNode(CoroutineConstantsKt.SUSPEND_FUNCTION_COMPLETION_PARAMETER_NAME, CoroutineCodegenUtilKt.CONTINUATION_ASM_TYPE.getDescriptor(), null, labelNode, labelNode2, i));
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.codegen.coroutines.CoroutineTransformerMethodVisitor$replaceReturnsUnitMarkersWithPushingUnitOnStack$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: CoroutineTransformerMethodVisitor.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class C43851 extends FunctionReference implements Function1<AbstractInsnNode, Boolean> {
        public static final C43851 INSTANCE = new C43851();

        C43851() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "isReturnsUnitMarker(Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;)Z";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "isReturnsUnitMarker";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(InlineCodegenUtilsKt.class, Marker.BACKEND);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Boolean invoke(AbstractInsnNode p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return Boolean.valueOf(InlineCodegenUtilsKt.isReturnsUnitMarker(p0));
        }
    }

    private final void replaceReturnsUnitMarkersWithPushingUnitOnStack(MethodNode methodNode) {
        AbstractInsnNode next;
        InsnList instructions = methodNode.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
        for (AbstractInsnNode abstractInsnNode : SequencesKt.toList(SequencesKt.filter(UtilKt.asSequence(instructions), C43851.INSTANCE))) {
            AbstractInsnNode next2 = abstractInsnNode.getNext();
            if (next2 != null && (next = next2.getNext()) != null) {
                InlineCodegenUtilsKt.isAfterSuspendMarker(next);
            }
            InsnList insnList = methodNode.instructions;
            AbstractInsnNode next3 = abstractInsnNode.getNext().getNext();
            MethodNode methodNode2 = new MethodNode();
            InstructionAdapter instructionAdapter = new InstructionAdapter(methodNode2);
            instructionAdapter.pop();
            instructionAdapter.getstatic("kotlin/Unit", JvmAbi.INSTANCE_FIELD, "Lkotlin/Unit;");
            Unit unit = Unit.INSTANCE;
            InsnList instructions2 = methodNode2.instructions;
            Intrinsics.checkNotNullExpressionValue(instructions2, "instructions");
            insnList.insert(next3, instructions2);
            InsnList instructions3 = methodNode.instructions;
            Intrinsics.checkNotNullExpressionValue(instructions3, "instructions");
            UtilKt.removeAll(instructions3, CollectionsKt.listOf((Object[]) new AbstractInsnNode[]{abstractInsnNode.getPrevious(), abstractInsnNode}));
        }
    }

    private final LineNumberNode findSuspensionPointLineNumber(SuspensionPoint suspensionPoint) {
        AbstractInsnNode previous = suspensionPoint.getSuspensionCallBegin().getPrevious();
        while (previous != null && !(previous instanceof LineNumberNode)) {
            previous = previous.getPrevious();
        }
        return (LineNumberNode) previous;
    }

    private final void checkForSuspensionPointInsideMonitor(MethodNode methodNode, List<SuspensionPoint> suspensionPoints) {
        InsnList instructions = methodNode.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
        Iterator<AbstractInsnNode> it = UtilKt.asSequence(instructions).iterator();
        while (it.hasNext()) {
            if (it.next().getOpcode() == 194) {
                ControlFlowGraph controlFlowGraphBuild$default = ControlFlowGraph.Companion.build$default(ControlFlowGraph.INSTANCE, methodNode, false, 2, null);
                HashMap map = new HashMap();
                checkForSuspensionPointInsideMonitor$addMonitorDepthToSuccs(methodNode, map, controlFlowGraphBuild$default, 0, 0);
                for (SuspensionPoint suspensionPoint : suspensionPoints) {
                    Integer num = (Integer) map.get(suspensionPoint.getSuspensionCallBegin());
                    if (num != null && num.intValue() > 0) {
                        String str = this.containingClassInternalName;
                        String str2 = methodNode.f1016name;
                        String str3 = this.sourceFile;
                        LineNumberNode lineNumberNodeFindSuspensionPointLineNumber = findSuspensionPointLineNumber(suspensionPoint);
                        this.reportSuspensionPointInsideMonitor.invoke(String.valueOf(new StackTraceElement(str, str2, str3, lineNumberNodeFindSuspensionPointLineNumber != null ? lineNumberNodeFindSuspensionPointLineNumber.line : -1)));
                        return;
                    }
                }
                return;
            }
        }
    }

    private static final void checkForSuspensionPointInsideMonitor$addMonitorDepthToSuccs(MethodNode methodNode, HashMap<AbstractInsnNode, Integer> map, ControlFlowGraph controlFlowGraph, int i, int i2) {
        AbstractInsnNode abstractInsnNode = methodNode.instructions.get(i);
        Integer numValueOf = Integer.valueOf(i2);
        Intrinsics.checkNotNull(abstractInsnNode);
        map.put(abstractInsnNode, numValueOf);
        int opcode = abstractInsnNode.getOpcode();
        if (opcode == 194) {
            i2++;
        } else if (opcode == 195) {
            i2--;
        }
        Iterator<Integer> it = controlFlowGraph.getSuccessorsIndices(i).iterator();
        while (it.hasNext()) {
            int iIntValue = it.next().intValue();
            if (map.get(methodNode.instructions.get(iIntValue)) == null) {
                checkForSuspensionPointInsideMonitor$addMonitorDepthToSuccs(methodNode, map, controlFlowGraph, iIntValue, i2);
            }
        }
    }

    private final void writeDebugMetadata(MethodNode methodNode, List<? extends LineNumberNode> suspensionPointLineNumbers, List<? extends List<SpilledVariableAndField>> spilledToLocalMapping) {
        List<? extends LineNumberNode> list = suspensionPointLineNumbers;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (LineNumberNode lineNumberNode : list) {
            arrayList.add(Integer.valueOf(lineNumberNode != null ? lineNumberNode.line : -1));
        }
        AnnotationVisitor annotationVisitorNewAnnotation = getClassBuilderForCoroutineState().newAnnotation(CoroutineCodegenUtilKt.getDEBUG_METADATA_ANNOTATION_ASM_TYPE().getDescriptor(), true);
        Intrinsics.checkNotNullExpressionValue(annotationVisitorNewAnnotation, "newAnnotation(...)");
        annotationVisitorNewAnnotation.visit("f", this.sourceFile);
        annotationVisitorNewAnnotation.visit("l", CollectionsKt.toIntArray(arrayList));
        List<? extends List<SpilledVariableAndField>> list2 = spilledToLocalMapping;
        Iterable<IndexedValue> iterableWithIndex = CollectionsKt.withIndex(list2);
        ArrayList arrayList2 = new ArrayList();
        for (IndexedValue indexedValue : iterableWithIndex) {
            int index = indexedValue.getIndex();
            List<SpilledVariableAndField> list3 = (List) indexedValue.component2();
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
            for (SpilledVariableAndField spilledVariableAndField : list3) {
                arrayList3.add(Integer.valueOf(index));
            }
            CollectionsKt.addAll(arrayList2, arrayList3);
        }
        List listFlatten = CollectionsKt.flatten(list2);
        annotationVisitorNewAnnotation.visit(ASTExpr.DEFAULT_INDEX_VARIABLE_NAME, CollectionsKt.toIntArray(arrayList2));
        AnnotationVisitor annotationVisitorVisitArray = annotationVisitorNewAnnotation.visitArray("s");
        List list4 = listFlatten;
        Iterator it = list4.iterator();
        while (it.hasNext()) {
            annotationVisitorVisitArray.visit(null, ((SpilledVariableAndField) it.next()).getFieldName());
        }
        annotationVisitorVisitArray.visitEnd();
        AnnotationVisitor annotationVisitorVisitArray2 = annotationVisitorNewAnnotation.visitArray("n");
        Iterator it2 = list4.iterator();
        while (it2.hasNext()) {
            annotationVisitorVisitArray2.visit(null, ((SpilledVariableAndField) it2.next()).getVariableName());
        }
        annotationVisitorVisitArray2.visitEnd();
        annotationVisitorNewAnnotation.visit("m", methodNode.f1016name);
        annotationVisitorNewAnnotation.visit("c", Type.getObjectType(this.containingClassInternalName).getClassName());
        annotationVisitorNewAnnotation.visitEnd();
    }

    private final void addContinuationAndResultToLvt(MethodNode methodNode, Label startLabel, Label resultStartLabel) {
        Label label = new Label();
        InsnList insnList = methodNode.instructions;
        MethodNode methodNode2 = new MethodNode();
        new InstructionAdapter(methodNode2).mark(label);
        InsnList instructions = methodNode2.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
        insnList.add(instructions);
        methodNode.visitLocalVariable(CoroutineCodegenUtilKt.CONTINUATION_VARIABLE_NAME, CoroutineCodegenUtilKt.CONTINUATION_ASM_TYPE.getDescriptor(), null, startLabel, label, this.continuationIndex);
        methodNode.visitLocalVariable(CoroutineConstantsKt.SUSPEND_CALL_RESULT_NAME, AsmTypes.OBJECT_TYPE.getDescriptor(), null, resultStartLabel, label, this.dataIndex);
    }

    private final void removeFakeContinuationConstructorCall(MethodNode methodNode) {
        AbstractInsnNode abstractInsnNode;
        AbstractInsnNode next;
        InsnList instructions = methodNode.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
        Sequence<AbstractInsnNode> sequenceAsSequence = UtilKt.asSequence(instructions);
        Iterator<AbstractInsnNode> it = sequenceAsSequence.iterator();
        do {
            abstractInsnNode = null;
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!InlineCodegenUtilsKt.isBeforeFakeContinuationConstructorCallMarker(next));
        AbstractInsnNode abstractInsnNode2 = next;
        AbstractInsnNode previous = abstractInsnNode2 != null ? abstractInsnNode2.getPrevious() : null;
        if (previous == null) {
            return;
        }
        for (AbstractInsnNode abstractInsnNode3 : sequenceAsSequence) {
            if (InlineCodegenUtilsKt.isAfterFakeContinuationConstructorCallMarker(abstractInsnNode3)) {
                abstractInsnNode = abstractInsnNode3;
                break;
            }
        }
        if (abstractInsnNode != null) {
            AbstractInsnNode abstractInsnNode4 = abstractInsnNode;
            List list = SequencesKt.toList(new InsnSequence(previous, abstractInsnNode4));
            InsnList instructions2 = methodNode.instructions;
            Intrinsics.checkNotNullExpressionValue(instructions2, "instructions");
            UtilKt.removeAll(instructions2, list);
            methodNode.instructions.set(abstractInsnNode4, new InsnNode(1));
            return;
        }
        throw new AssertionError("BeforeFakeContinuationConstructorCallMarker without AfterFakeContinuationConstructorCallMarker");
    }

    private final void getLabel(InstructionAdapter instructionAdapter) {
        instructionAdapter.getfield(Type.getObjectType(getClassBuilderForCoroutineState().getThisName()).getInternalName(), CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, Type.INT_TYPE.getDescriptor());
    }

    private final void setLabel(InstructionAdapter instructionAdapter) {
        instructionAdapter.putfield(Type.getObjectType(getClassBuilderForCoroutineState().getThisName()).getInternalName(), CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, Type.INT_TYPE.getDescriptor());
    }

    private final void prepareMethodNodePreludeForNamedFunction(MethodNode methodNode) {
        Type objectType = Type.getObjectType(getClassBuilderForCoroutineState().getThisName());
        String desc = methodNode.desc;
        Intrinsics.checkNotNullExpressionValue(desc, "desc");
        int lastParameterIndex = CoroutineTransformerMethodVisitorKt.getLastParameterIndex(desc, methodNode.access);
        InsnList instructions = methodNode.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
        Sequence<VarInsnNode> sequenceFilter = SequencesKt.filter(UtilKt.asSequence(instructions), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.codegen.coroutines.CoroutineTransformerMethodVisitor$prepareMethodNodePreludeForNamedFunction$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof VarInsnNode);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        for (VarInsnNode varInsnNode : sequenceFilter) {
            if (varInsnNode.var == lastParameterIndex) {
                varInsnNode.getOpcode();
                varInsnNode.var = this.continuationIndex;
            }
        }
        InsnList insnList = methodNode.instructions;
        MethodNode methodNode2 = new MethodNode();
        InstructionAdapter instructionAdapter = new InstructionAdapter(methodNode2);
        Label label = new Label();
        Label label2 = new Label();
        instructionAdapter.visitVarInsn(25, lastParameterIndex);
        instructionAdapter.instanceOf(objectType);
        instructionAdapter.ifeq(label);
        instructionAdapter.visitVarInsn(25, lastParameterIndex);
        instructionAdapter.checkcast(objectType);
        instructionAdapter.visitVarInsn(58, this.continuationIndex);
        instructionAdapter.visitVarInsn(25, this.continuationIndex);
        getLabel(instructionAdapter);
        instructionAdapter.iconst(Integer.MIN_VALUE);
        instructionAdapter.and(Type.INT_TYPE);
        instructionAdapter.ifeq(label);
        instructionAdapter.visitVarInsn(25, this.continuationIndex);
        instructionAdapter.dup();
        getLabel(instructionAdapter);
        instructionAdapter.iconst(Integer.MIN_VALUE);
        instructionAdapter.sub(Type.INT_TYPE);
        setLabel(instructionAdapter);
        instructionAdapter.goTo(label2);
        instructionAdapter.visitLabel(label);
        CoroutineTransformerMethodVisitorKt.generateContinuationConstructorCall(instructionAdapter, objectType, methodNode, this.needDispatchReceiver, this.internalNameForDispatchReceiver, this.containingClassInternalName, getClassBuilderForCoroutineState());
        instructionAdapter.visitVarInsn(58, this.continuationIndex);
        instructionAdapter.visitLabel(label2);
        instructionAdapter.visitVarInsn(25, this.continuationIndex);
        instructionAdapter.getfield(getClassBuilderForCoroutineState().getThisName(), "result", AsmTypes.OBJECT_TYPE.getDescriptor());
        instructionAdapter.visitVarInsn(58, this.dataIndex);
        Label label3 = new Label();
        instructionAdapter.visitLabel(label3);
        addContinuationAndResultToLvt(methodNode, label2, label3);
        InsnList instructions2 = methodNode2.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions2, "instructions");
        insnList.insert(instructions2);
    }

    private final List<SuspensionPoint> collectSuspensionPoints(final MethodNode methodNode) {
        final ControlFlowGraph controlFlowGraphBuild = ControlFlowGraph.INSTANCE.build(methodNode, false);
        InsnList instructions = methodNode.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
        return SequencesKt.toList(SequencesKt.mapNotNull(SequencesKt.filter(UtilKt.asSequence(instructions), new Function1<AbstractInsnNode, Boolean>() { // from class: org.jetbrains.kotlin.codegen.coroutines.CoroutineTransformerMethodVisitor.collectSuspensionPoints.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(AbstractInsnNode it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(InlineCodegenUtilsKt.isBeforeSuspendMarker(it));
            }
        }), new Function1<AbstractInsnNode, SuspensionPoint>() { // from class: org.jetbrains.kotlin.codegen.coroutines.CoroutineTransformerMethodVisitor.collectSuspensionPoints.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final SuspensionPoint invoke(AbstractInsnNode start) {
                Object next;
                Intrinsics.checkNotNullParameter(start, "start");
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                if (CoroutineTransformerMethodVisitor.collectSuspensionPoints$collectSuspensionPointEnds(controlFlowGraphBuild, methodNode, start, new LinkedHashSet(), linkedHashSet)) {
                    return null;
                }
                Iterator it = linkedHashSet.iterator();
                do {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                } while (!InlineCodegenUtilsKt.isAfterSuspendMarker((AbstractInsnNode) next));
                AbstractInsnNode abstractInsnNode = (AbstractInsnNode) next;
                if (abstractInsnNode == null) {
                    return null;
                }
                AbstractInsnNode previous = start.getPrevious();
                Intrinsics.checkNotNullExpressionValue(previous, "getPrevious(...)");
                return new SuspensionPoint(previous, abstractInsnNode);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean collectSuspensionPoints$collectSuspensionPointEnds(ControlFlowGraph controlFlowGraph, MethodNode methodNode, AbstractInsnNode abstractInsnNode, Set<AbstractInsnNode> set, Set<AbstractInsnNode> set2) {
        if (!set.add(abstractInsnNode)) {
            return false;
        }
        if (abstractInsnNode.getOpcode() == 176 || abstractInsnNode.getOpcode() == 191 || InlineCodegenUtilsKt.isAfterSuspendMarker(abstractInsnNode)) {
            set2.add(abstractInsnNode);
        } else {
            Iterator<Integer> it = controlFlowGraph.getSuccessorsIndices(abstractInsnNode).iterator();
            while (it.hasNext()) {
                AbstractInsnNode abstractInsnNode2 = methodNode.instructions.get(it.next().intValue());
                Intrinsics.checkNotNull(abstractInsnNode2);
                if (InlineCodegenUtilsKt.isBeforeSuspendMarker(abstractInsnNode2) || collectSuspensionPoints$collectSuspensionPointEnds(controlFlowGraph, methodNode, abstractInsnNode2, set, set2)) {
                    return true;
                }
            }
        }
        return false;
    }

    private final void dropSuspensionMarkers(MethodNode methodNode) {
        InsnList instructions = methodNode.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
        for (AbstractInsnNode abstractInsnNode : SequencesKt.toList(SequencesKt.filter(UtilKt.asSequence(instructions), new Function1<AbstractInsnNode, Boolean>() { // from class: org.jetbrains.kotlin.codegen.coroutines.CoroutineTransformerMethodVisitor.dropSuspensionMarkers.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(AbstractInsnNode it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(InlineCodegenUtilsKt.isBeforeSuspendMarker(it) || InlineCodegenUtilsKt.isAfterSuspendMarker(it));
            }
        }))) {
            InsnList instructions2 = methodNode.instructions;
            Intrinsics.checkNotNullExpressionValue(instructions2, "instructions");
            UtilKt.removeAll(instructions2, CollectionsKt.listOf((Object[]) new AbstractInsnNode[]{abstractInsnNode.getPrevious(), abstractInsnNode}));
        }
    }

    private final void dropUnboxInlineClassMarkers(MethodNode methodNode, List<SuspensionPoint> suspensionPoints) {
        InsnList instructions = methodNode.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
        for (AbstractInsnNode abstractInsnNode : SequencesKt.toList(SequencesKt.filter(UtilKt.asSequence(instructions), new Function1<AbstractInsnNode, Boolean>() { // from class: org.jetbrains.kotlin.codegen.coroutines.CoroutineTransformerMethodVisitor.dropUnboxInlineClassMarkers.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(AbstractInsnNode it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(InlineCodegenUtilsKt.isBeforeUnboxInlineClassMarker(it));
            }
        }))) {
            InsnList instructions2 = methodNode.instructions;
            Intrinsics.checkNotNullExpressionValue(instructions2, "instructions");
            UtilKt.removeAll(instructions2, CollectionsKt.listOf((Object[]) new AbstractInsnNode[]{abstractInsnNode.getPrevious(), abstractInsnNode}));
        }
        InsnList instructions3 = methodNode.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions3, "instructions");
        for (AbstractInsnNode abstractInsnNode2 : SequencesKt.toList(SequencesKt.filter(UtilKt.asSequence(instructions3), new Function1<AbstractInsnNode, Boolean>() { // from class: org.jetbrains.kotlin.codegen.coroutines.CoroutineTransformerMethodVisitor.dropUnboxInlineClassMarkers.2
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(AbstractInsnNode it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(InlineCodegenUtilsKt.isAfterUnboxInlineClassMarker(it));
            }
        }))) {
            InsnList instructions4 = methodNode.instructions;
            Intrinsics.checkNotNullExpressionValue(instructions4, "instructions");
            UtilKt.removeAll(instructions4, CollectionsKt.listOf((Object[]) new AbstractInsnNode[]{abstractInsnNode2.getPrevious().getPrevious(), abstractInsnNode2.getPrevious(), abstractInsnNode2}));
        }
        for (SuspensionPoint suspensionPoint : suspensionPoints) {
            InsnList instructions5 = methodNode.instructions;
            Intrinsics.checkNotNullExpressionValue(instructions5, "instructions");
            UtilKt.removeAll(instructions5, suspensionPoint.getUnboxInlineClassInstructions());
        }
    }

    /* JADX WARN: Code duplicated, block: B:42:0x0164  */
    private final List<List<SpilledVariableAndField>> spillVariables(List<SuspensionPoint> suspensionPoints, MethodNode methodNode) throws AnalyzerException {
        int iSpillVariables$countVariablesToSpill;
        Iterator<SuspensionPoint> it;
        int i;
        CoroutineTransformerMethodVisitor coroutineTransformerMethodVisitor = this;
        InsnList insnList = methodNode.instructions;
        Frame<BasicValue>[] frameArrPerformSpilledVariableFieldTypesAnalysis = SpilledVariableFieldTypesAnalysisKt.performSpilledVariableFieldTypesAnalysis(methodNode, coroutineTransformerMethodVisitor.containingClassInternalName);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        int i2 = 0;
        for (Map.Entry<Type, Integer> entry : coroutineTransformerMethodVisitor.initialVarsCountByType.entrySet()) {
            Type key = entry.getKey();
            int iIntValue = entry.getValue().intValue();
            if (Intrinsics.areEqual(key, AsmTypes.OBJECT_TYPE)) {
                i2 = iIntValue;
            }
            linkedHashMap.put(key, Integer.valueOf(iIntValue));
        }
        List<VariableLivenessFrame> listAnalyzeLiveness = VariableLivenessKt.analyzeLiveness(methodNode);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Iterator<SuspensionPoint> it2 = suspensionPoints.iterator();
        while (it2.hasNext()) {
            SuspensionPoint next = it2.next();
            AbstractInsnNode suspensionCallBegin = next.getSuspensionCallBegin();
            AbstractInsnNode next2 = next.getSuspensionCallEnd().getNext();
            Intrinsics.checkNotNullExpressionValue(next2, "getNext(...)");
            Frame<BasicValue> frame = frameArrPerformSpilledVariableFieldTypesAnalysis[spillVariables$index(next2, insnList)];
            if (frame != null) {
                frame.getStackSize();
            }
            Frame<BasicValue> frame2 = frameArrPerformSpilledVariableFieldTypesAnalysis[spillVariables$index(suspensionCallBegin, insnList)];
            if (frame2 != null) {
                int locals = frame2.getLocals();
                LinkedHashMap linkedHashMap2 = new LinkedHashMap();
                VariableLivenessFrame variableLivenessFrame = listAnalyzeLiveness.get(spillVariables$index(suspensionCallBegin, insnList));
                ArrayList arrayList3 = new ArrayList();
                Frame<BasicValue>[] frameArr = frameArrPerformSpilledVariableFieldTypesAnalysis;
                ArrayList arrayList4 = new ArrayList();
                List<VariableLivenessFrame> list = listAnalyzeLiveness;
                int i3 = 0;
                while (i3 < locals) {
                    int i4 = i2;
                    if (i3 == coroutineTransformerMethodVisitor.continuationIndex || i3 == coroutineTransformerMethodVisitor.dataIndex) {
                        it = it2;
                        variableLivenessFrame = variableLivenessFrame;
                        i = locals;
                    } else {
                        BasicValue basicValue = (BasicValue) frame2.getLocal(i3);
                        if (basicValue.getType() != null) {
                            it = it2;
                            if (!coroutineTransformerMethodVisitor.shouldOptimiseUnusedVariables || variableLivenessFrame.isAlive(i3)) {
                                if (Intrinsics.areEqual(basicValue, StrictBasicValue.NULL_VALUE)) {
                                    arrayList3.add(TuplesKt.to(Integer.valueOf(i3), null));
                                } else {
                                    frame2 = frame2;
                                    Type type = basicValue.getType();
                                    Intrinsics.checkNotNull(type);
                                    Type typeNormalize = CoroutineTransformerMethodVisitorKt.normalize(type);
                                    Integer num = (Integer) linkedHashMap2.get(typeNormalize);
                                    int iIntValue2 = num != null ? num.intValue() + 1 : 0;
                                    linkedHashMap2.put(typeNormalize, Integer.valueOf(iIntValue2));
                                    String strFieldNameForVar = CoroutineTransformerMethodVisitorKt.fieldNameForVar(typeNormalize, iIntValue2);
                                    i = locals;
                                    if (Intrinsics.areEqual(typeNormalize, AsmTypes.OBJECT_TYPE)) {
                                        Integer numValueOf = Integer.valueOf(i3);
                                        Intrinsics.checkNotNull(basicValue);
                                        arrayList3.add(TuplesKt.to(numValueOf, new SpillableVariable(basicValue, type, typeNormalize, strFieldNameForVar)));
                                    } else {
                                        Integer numValueOf2 = Integer.valueOf(i3);
                                        Intrinsics.checkNotNull(basicValue);
                                        arrayList4.add(TuplesKt.to(numValueOf2, new SpillableVariable(basicValue, type, typeNormalize, strFieldNameForVar)));
                                    }
                                }
                            }
                            variableLivenessFrame = variableLivenessFrame;
                            i = locals;
                        } else {
                            it = it2;
                        }
                        variableLivenessFrame = variableLivenessFrame;
                        i = locals;
                    }
                    i3++;
                    coroutineTransformerMethodVisitor = this;
                    i2 = i4;
                    it2 = it;
                    frame2 = frame2;
                    locals = i;
                    variableLivenessFrame = variableLivenessFrame;
                    insnList = insnList;
                }
                int i5 = i2;
                Iterator<SuspensionPoint> it3 = it2;
                InsnList insnList2 = insnList;
                arrayList.add(arrayList3);
                arrayList2.add(arrayList4);
                for (Map.Entry entry2 : linkedHashMap2.entrySet()) {
                    Type type2 = (Type) entry2.getKey();
                    int iIntValue3 = ((Number) entry2.getValue()).intValue();
                    Integer num2 = (Integer) linkedHashMap.get(type2);
                    linkedHashMap.put(type2, Integer.valueOf(Math.max(num2 != null ? num2.intValue() : 0, iIntValue3)));
                }
                coroutineTransformerMethodVisitor = this;
                frameArrPerformSpilledVariableFieldTypesAnalysis = frameArr;
                listAnalyzeLiveness = list;
                i2 = i5;
                it2 = it3;
                insnList = insnList2;
            } else {
                throw new AssertionError("Suspension points containing in dead code must be removed");
            }
        }
        int i6 = i2;
        InsnList insnList3 = insnList;
        ControlFlowGraph controlFlowGraphBuild$default = ControlFlowGraph.Companion.build$default(ControlFlowGraph.INSTANCE, methodNode, false, 2, null);
        List<SuspensionPoint> list2 = suspensionPoints;
        LinkedHashMap linkedHashMap3 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list2, 10)), 16));
        for (Object obj : list2) {
            linkedHashMap3.put(((SuspensionPoint) obj).getSuspensionCallEnd(), obj);
        }
        LinkedHashMap linkedHashMap4 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list2, 10)), 16));
        for (Object obj2 : list2) {
            linkedHashMap4.put(obj2, spillVariables$findSuspensionPointPredecessors(linkedHashMap3, controlFlowGraphBuild$default, insnList3, (SuspensionPoint) obj2));
        }
        LinkedHashMap linkedHashMap5 = linkedHashMap4;
        ArrayList arrayList5 = new ArrayList();
        int size = suspensionPoints.size();
        for (int i7 = 0; i7 < size; i7++) {
            SuspensionPoint suspensionPoint = suspensionPoints.get(i7);
            int iSpillVariables$countVariablesToSpill2 = spillVariables$countVariablesToSpill(arrayList, i7);
            List list3 = (List) linkedHashMap5.get(suspensionPoint);
            List list4 = list3;
            if (list4 == null || list4.isEmpty()) {
                iSpillVariables$countVariablesToSpill = i6;
            } else {
                Iterator it4 = list3.iterator();
                if (!it4.hasNext()) {
                    throw new NoSuchElementException();
                }
                iSpillVariables$countVariablesToSpill = spillVariables$countVariablesToSpill(arrayList, suspensionPoints.indexOf((SuspensionPoint) it4.next()));
                while (it4.hasNext()) {
                    int iSpillVariables$countVariablesToSpill3 = spillVariables$countVariablesToSpill(arrayList, suspensionPoints.indexOf((SuspensionPoint) it4.next()));
                    if (iSpillVariables$countVariablesToSpill < iSpillVariables$countVariablesToSpill3) {
                        iSpillVariables$countVariablesToSpill = iSpillVariables$countVariablesToSpill3;
                    }
                }
            }
            arrayList5.add(TuplesKt.to(Integer.valueOf(iSpillVariables$countVariablesToSpill2), Integer.valueOf(iSpillVariables$countVariablesToSpill)));
        }
        ArrayList arrayList6 = new ArrayList();
        int size2 = suspensionPoints.size();
        int i8 = 0;
        while (i8 < size2) {
            SuspensionPoint suspensionPoint2 = suspensionPoints.get(i8);
            ArrayList arrayList7 = new ArrayList();
            Object obj3 = arrayList.get(i8);
            String str = "get(...)";
            Intrinsics.checkNotNullExpressionValue(obj3, "get(...)");
            for (Pair pair : (Iterable) obj3) {
                String str2 = str;
                ArrayList arrayList8 = arrayList7;
                int i9 = size2;
                int i10 = i8;
                SpilledVariableAndField spilledVariableAndFieldSpillVariables$calculateSpilledVariableAndField = spillVariables$calculateSpilledVariableAndField(this, methodNode, insnList3, suspensionPoint2, ((Number) pair.component1()).intValue(), (SpillableVariable) pair.component2());
                if (spilledVariableAndFieldSpillVariables$calculateSpilledVariableAndField != null) {
                    arrayList8.add(spilledVariableAndFieldSpillVariables$calculateSpilledVariableAndField);
                }
                str = str2;
                arrayList7 = arrayList8;
                i8 = i10;
                size2 = i9;
            }
            ArrayList arrayList9 = arrayList7;
            int i11 = size2;
            int i12 = i8;
            ArrayList arrayList10 = arrayList9;
            Object obj4 = arrayList2.get(i12);
            Intrinsics.checkNotNullExpressionValue(obj4, str);
            Iterator it5 = ((Iterable) obj4).iterator();
            while (it5.hasNext()) {
                Pair pair2 = (Pair) it5.next();
                Iterator it6 = it5;
                ArrayList arrayList11 = arrayList10;
                SpilledVariableAndField spilledVariableAndFieldSpillVariables$calculateSpilledVariableAndField2 = spillVariables$calculateSpilledVariableAndField(this, methodNode, insnList3, suspensionPoint2, ((Number) pair2.component1()).intValue(), (SpillableVariable) pair2.component2());
                if (spilledVariableAndFieldSpillVariables$calculateSpilledVariableAndField2 != null) {
                    arrayList11.add(spilledVariableAndFieldSpillVariables$calculateSpilledVariableAndField2);
                }
                arrayList10 = arrayList11;
                it5 = it6;
            }
            arrayList6.add(arrayList9);
            i8 = i12 + 1;
            size2 = i11;
        }
        int size3 = suspensionPoints.size();
        int i13 = 0;
        while (i13 < size3) {
            SuspensionPoint suspensionPoint3 = suspensionPoints.get(i13);
            for (Pair pair3 : (List) arrayList.get(i13)) {
                spillVariables$generateSpillAndUnspill(methodNode, this, insnList3, suspensionPoint3, ((Number) pair3.component1()).intValue(), (SpillableVariable) pair3.component2());
            }
            CoroutineTransformerMethodVisitor coroutineTransformerMethodVisitor2 = this;
            if (coroutineTransformerMethodVisitor2.shouldOptimiseUnusedVariables) {
                Pair pair4 = (Pair) arrayList5.get(i13);
                int iIntValue4 = ((Number) pair4.component1()).intValue();
                int iIntValue5 = ((Number) pair4.component2()).intValue();
                if (iIntValue5 > iIntValue4) {
                    while (iIntValue4 < iIntValue5) {
                        spillVariables$cleanUpField(insnList3, coroutineTransformerMethodVisitor2, suspensionPoint3, iIntValue4);
                        iIntValue4++;
                    }
                }
            }
            for (Pair pair5 : (List) arrayList2.get(i13)) {
                spillVariables$generateSpillAndUnspill(methodNode, this, insnList3, suspensionPoint3, ((Number) pair5.component1()).intValue(), (SpillableVariable) pair5.component2());
                coroutineTransformerMethodVisitor2 = coroutineTransformerMethodVisitor2;
                size3 = size3;
            }
            i13++;
            size3 = size3;
        }
        for (Map.Entry entry3 : linkedHashMap.entrySet()) {
            Type type3 = (Type) entry3.getKey();
            int iIntValue6 = ((Number) entry3.getValue()).intValue();
            Integer num3 = this.initialVarsCountByType.get(type3);
            int iIntValue7 = num3 != null ? num3.intValue() + 1 : 0;
            if (iIntValue7 <= iIntValue6) {
                while (true) {
                    getClassBuilderForCoroutineState().newField(JvmDeclarationOrigin.NO_ORIGIN, 0, CoroutineTransformerMethodVisitorKt.fieldNameForVar(type3, iIntValue7), type3.getDescriptor(), null, null);
                    if (iIntValue7 != iIntValue6) {
                        iIntValue7++;
                    }
                }
            }
        }
        return arrayList6;
    }

    private static final int spillVariables$index(AbstractInsnNode abstractInsnNode, InsnList insnList) {
        return insnList.indexOf(abstractInsnNode);
    }

    private static final List<SuspensionPoint> spillVariables$findSuspensionPointPredecessors(Map<AbstractInsnNode, SuspensionPoint> map, ControlFlowGraph controlFlowGraph, InsnList insnList, SuspensionPoint suspensionPoint) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        List listMutableListOf = CollectionsKt.mutableListOf(suspensionPoint.getSuspensionCallBegin());
        ArrayList arrayList = new ArrayList();
        while (!listMutableListOf.isEmpty()) {
            AbstractInsnNode abstractInsnNode = (AbstractInsnNode) AddToStdlibKt.popLast(listMutableListOf);
            if (linkedHashSet.add(abstractInsnNode)) {
                SuspensionPoint suspensionPoint2 = map.get(abstractInsnNode);
                if (suspensionPoint2 != null) {
                    arrayList.add(suspensionPoint2);
                } else {
                    List<Integer> predecessorsIndices = controlFlowGraph.getPredecessorsIndices(abstractInsnNode);
                    ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(predecessorsIndices, 10));
                    Iterator<T> it = predecessorsIndices.iterator();
                    while (it.hasNext()) {
                        arrayList2.add(insnList.get(((Number) it.next()).intValue()));
                    }
                    listMutableListOf.addAll(arrayList2);
                }
            }
        }
        return arrayList;
    }

    private static final int spillVariables$countVariablesToSpill(ArrayList<List<Pair<Integer, SpillableVariable>>> arrayList, int i) {
        List<Pair<Integer, SpillableVariable>> list = arrayList.get(i);
        Intrinsics.checkNotNullExpressionValue(list, "get(...)");
        List<Pair<Integer, SpillableVariable>> list2 = list;
        int i2 = 0;
        if (!(list2 instanceof Collection) || !list2.isEmpty()) {
            Iterator<T> it = list2.iterator();
            while (it.hasNext()) {
                if (((SpillableVariable) ((Pair) it.next()).component2()) != null && (i2 = i2 + 1) < 0) {
                    CollectionsKt.throwCountOverflow();
                }
            }
        }
        return i2;
    }

    private static final SpilledVariableAndField spillVariables$calculateSpilledVariableAndField(CoroutineTransformerMethodVisitor coroutineTransformerMethodVisitor, MethodNode methodNode, InsnList insnList, SuspensionPoint suspensionPoint, int i, SpillableVariable spillableVariable) {
        String strLocalVariableName;
        if (spillableVariable == null || (strLocalVariableName = coroutineTransformerMethodVisitor.localVariableName(methodNode, i, spillVariables$index(suspensionPoint.getSuspensionCallBegin(), insnList))) == null) {
            return null;
        }
        return new SpilledVariableAndField(spillableVariable.getFieldName(), strLocalVariableName);
    }

    private static final void spillVariables$generateSpillAndUnspill$splitLvtRecord(SuspensionPoint suspensionPoint, MethodNode methodNode, LocalVariableNode localVariableNode, LabelNode labelNode) {
        if (localVariableNode != null) {
            LabelNode labelNode2 = localVariableNode.end;
            localVariableNode.end = suspensionPoint.getStateLabel();
            methodNode.localVariables.add(localVariableNode);
            methodNode.localVariables.add(new LocalVariableNode(localVariableNode.f1014name, localVariableNode.desc, localVariableNode.signature, labelNode, labelNode2, localVariableNode.index));
        }
    }

    private static final void spillVariables$generateSpillAndUnspill(MethodNode methodNode, CoroutineTransformerMethodVisitor coroutineTransformerMethodVisitor, InsnList insnList, SuspensionPoint suspensionPoint, int i, SpillableVariable spillableVariable) {
        LocalVariableNode next;
        ListIterator<LocalVariableNode> listIterator = methodNode.localVariables.listIterator();
        while (true) {
            if (!listIterator.hasNext()) {
                next = null;
                break;
            }
            next = listIterator.next();
            if (next.index == i && methodNode.instructions.indexOf(next.start) <= methodNode.instructions.indexOf(suspensionPoint.getSuspensionCallBegin()) && methodNode.instructions.indexOf(next.end) > methodNode.instructions.indexOf(coroutineTransformerMethodVisitor.getTryCatchBlockEndLabelAfterSuspensionCall(suspensionPoint))) {
                listIterator.remove();
                break;
            }
        }
        if (spillableVariable == null) {
            LabelNode tryCatchBlockEndLabelAfterSuspensionCall = coroutineTransformerMethodVisitor.getTryCatchBlockEndLabelAfterSuspensionCall(suspensionPoint);
            MethodNode methodNode2 = new MethodNode();
            InstructionAdapter instructionAdapter = new InstructionAdapter(methodNode2);
            instructionAdapter.aconst(null);
            instructionAdapter.store(i, AsmTypes.OBJECT_TYPE);
            Unit unit = Unit.INSTANCE;
            InsnList instructions = methodNode2.instructions;
            Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
            insnList.insert(tryCatchBlockEndLabelAfterSuspensionCall, instructions);
            AbstractInsnNode next2 = suspensionPoint.getTryCatchBlocksContinuationLabel().getNext();
            while (next2 != null && !(next2 instanceof LabelNode)) {
                next2 = next2.getNext();
            }
            LabelNode labelNode = next2 instanceof LabelNode ? (LabelNode) next2 : null;
            if (labelNode == null) {
                return;
            }
            spillVariables$generateSpillAndUnspill$splitLvtRecord(suspensionPoint, methodNode, next, labelNode);
            return;
        }
        LabelNode labelNodeLinkWithLabel = CodegenUtilKt.linkWithLabel(new LabelNode());
        AbstractInsnNode suspensionCallBegin = suspensionPoint.getSuspensionCallBegin();
        MethodNode methodNode3 = new MethodNode();
        InstructionAdapter instructionAdapter2 = new InstructionAdapter(methodNode3);
        instructionAdapter2.load(coroutineTransformerMethodVisitor.continuationIndex, AsmTypes.OBJECT_TYPE);
        instructionAdapter2.load(i, spillableVariable.getType());
        StackValue.coerce(spillableVariable.getType(), spillableVariable.getNormalizedType(), instructionAdapter2);
        instructionAdapter2.putfield(coroutineTransformerMethodVisitor.getClassBuilderForCoroutineState().getThisName(), spillableVariable.getFieldName(), spillableVariable.getNormalizedType().getDescriptor());
        Unit unit2 = Unit.INSTANCE;
        InsnList instructions2 = methodNode3.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions2, "instructions");
        insnList.insertBefore(suspensionCallBegin, instructions2);
        LabelNode tryCatchBlockEndLabelAfterSuspensionCall2 = coroutineTransformerMethodVisitor.getTryCatchBlockEndLabelAfterSuspensionCall(suspensionPoint);
        MethodNode methodNode4 = new MethodNode();
        InstructionAdapter instructionAdapter3 = new InstructionAdapter(methodNode4);
        instructionAdapter3.load(coroutineTransformerMethodVisitor.continuationIndex, AsmTypes.OBJECT_TYPE);
        instructionAdapter3.getfield(coroutineTransformerMethodVisitor.getClassBuilderForCoroutineState().getThisName(), spillableVariable.getFieldName(), spillableVariable.getNormalizedType().getDescriptor());
        StackValue.coerce(spillableVariable.getNormalizedType(), spillableVariable.getType(), instructionAdapter3);
        instructionAdapter3.store(i, spillableVariable.getType());
        if (next != null) {
            instructionAdapter3.visitLabel(labelNodeLinkWithLabel.getLabel());
        }
        Unit unit3 = Unit.INSTANCE;
        InsnList instructions3 = methodNode4.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions3, "instructions");
        insnList.insert(tryCatchBlockEndLabelAfterSuspensionCall2, instructions3);
        spillVariables$generateSpillAndUnspill$splitLvtRecord(suspensionPoint, methodNode, next, labelNodeLinkWithLabel);
    }

    private static final void spillVariables$cleanUpField(InsnList insnList, CoroutineTransformerMethodVisitor coroutineTransformerMethodVisitor, SuspensionPoint suspensionPoint, int i) {
        AbstractInsnNode suspensionCallBegin = suspensionPoint.getSuspensionCallBegin();
        MethodNode methodNode = new MethodNode();
        InstructionAdapter instructionAdapter = new InstructionAdapter(methodNode);
        instructionAdapter.load(coroutineTransformerMethodVisitor.continuationIndex, AsmTypes.OBJECT_TYPE);
        instructionAdapter.aconst(null);
        instructionAdapter.putfield(coroutineTransformerMethodVisitor.getClassBuilderForCoroutineState().getThisName(), "L$" + i, AsmTypes.OBJECT_TYPE.getDescriptor());
        Unit unit = Unit.INSTANCE;
        InsnList instructions = methodNode.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
        insnList.insertBefore(suspensionCallBegin, instructions);
    }

    private final String localVariableName(MethodNode methodNode, int index, int suspensionCallIndex) {
        Object next;
        List<LocalVariableNode> localVariables = methodNode.localVariables;
        Intrinsics.checkNotNullExpressionValue(localVariables, "localVariables");
        Iterator<T> it = localVariables.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            LocalVariableNode localVariableNode = (LocalVariableNode) next;
            if (index == localVariableNode.index && methodNode.instructions.indexOf(localVariableNode.start) <= suspensionCallIndex && suspensionCallIndex < methodNode.instructions.indexOf(localVariableNode.end)) {
                break;
            }
        }
        LocalVariableNode localVariableNode2 = (LocalVariableNode) next;
        if (localVariableNode2 != null) {
            return localVariableNode2.f1014name;
        }
        return null;
    }

    private final LabelNode getTryCatchBlockEndLabelAfterSuspensionCall(SuspensionPoint suspensionPoint) {
        boolean z = suspensionPoint.getSuspensionCallEnd().getNext() instanceof LabelNode;
        AbstractInsnNode next = suspensionPoint.getSuspensionCallEnd().getNext();
        Intrinsics.checkNotNull(next, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.LabelNode");
        return (LabelNode) next;
    }

    private final LabelNode transformCallAndReturnStateLabel(int id2, SuspensionPoint suspension, MethodNode methodNode, int suspendMarkerVarIndex, LineNumberNode suspendPointLineNumber) {
        int opcode;
        LabelNode labelNode = new LabelNode();
        int i = this.lineNumber;
        LineNumberNode lineNumberNodeNextDefinitelyHitLineNumber = nextDefinitelyHitLineNumber(suspension);
        InsnList insnList = methodNode.instructions;
        AbstractInsnNode suspensionCallBegin = suspension.getSuspensionCallBegin();
        MethodNode methodNode2 = new MethodNode();
        InstructionAdapter instructionAdapter = new InstructionAdapter(methodNode2);
        instructionAdapter.visitVarInsn(25, this.continuationIndex);
        instructionAdapter.iconst(id2);
        setLabel(instructionAdapter);
        Unit unit = Unit.INSTANCE;
        InsnList instructions = methodNode2.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
        insnList.insertBefore(suspensionCallBegin, instructions);
        LabelNode tryCatchBlockEndLabelAfterSuspensionCall = getTryCatchBlockEndLabelAfterSuspensionCall(suspension);
        MethodNode methodNode3 = new MethodNode();
        InstructionAdapter instructionAdapter2 = new InstructionAdapter(methodNode3);
        instructionAdapter2.dup();
        instructionAdapter2.load(suspendMarkerVarIndex, AsmTypes.OBJECT_TYPE);
        instructionAdapter2.ifacmpne(labelNode.getLabel());
        LabelNode labelNode2 = new LabelNode();
        instructionAdapter2.visitLabel(labelNode2.getLabel());
        instructionAdapter2.visitLineNumber(i, labelNode2.getLabel());
        instructionAdapter2.load(suspendMarkerVarIndex, AsmTypes.OBJECT_TYPE);
        instructionAdapter2.areturn(AsmTypes.OBJECT_TYPE);
        instructionAdapter2.visitLabel(suspension.getStateLabel().getLabel());
        Unit unit2 = Unit.INSTANCE;
        InsnList instructions2 = methodNode3.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions2, "instructions");
        insnList.insert(tryCatchBlockEndLabelAfterSuspensionCall, instructions2);
        LabelNode tryCatchBlocksContinuationLabel = suspension.getTryCatchBlocksContinuationLabel();
        tryCatchBlocksContinuationLabel.getPrevious().getOpcode();
        insnList.remove(tryCatchBlocksContinuationLabel.getPrevious());
        LabelNode labelNode3 = tryCatchBlocksContinuationLabel;
        MethodNode methodNode4 = new MethodNode();
        InstructionAdapter instructionAdapter3 = new InstructionAdapter(methodNode4);
        instructionAdapter3.nop();
        CoroutineTransformerMethodVisitorKt.generateResumeWithExceptionCheck(instructionAdapter3, this.dataIndex);
        instructionAdapter3.load(this.dataIndex, AsmTypes.OBJECT_TYPE);
        Iterator<AbstractInsnNode> it = suspension.getUnboxInlineClassInstructions().iterator();
        while (it.hasNext()) {
            it.next().accept(instructionAdapter3);
        }
        instructionAdapter3.visitLabel(labelNode.getLabel());
        if (lineNumberNodeNextDefinitelyHitLineNumber != null) {
            AbstractInsnNode next = tryCatchBlocksContinuationLabel.getNext();
            if (next == null || (opcode = next.getOpcode()) == 58 || opcode == 192 || opcode == 184 || opcode == 182 || opcode == 185) {
                lineNumberNodeNextDefinitelyHitLineNumber = null;
            } else {
                instructionAdapter3.visitLineNumber(lineNumberNodeNextDefinitelyHitLineNumber.line, labelNode.getLabel());
            }
        } else if (suspendPointLineNumber != null) {
            instructionAdapter3.visitLineNumber(suspendPointLineNumber.line, labelNode.getLabel());
        }
        Unit unit3 = Unit.INSTANCE;
        InsnList instructions3 = methodNode4.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions3, "instructions");
        insnList.insert(labelNode3, instructions3);
        if (lineNumberNodeNextDefinitelyHitLineNumber != null) {
            insnList.remove(lineNumberNodeNextDefinitelyHitLineNumber);
        }
        return suspension.getStateLabel();
    }

    private final LineNumberNode nextDefinitelyHitLineNumber(SuspensionPoint suspension) {
        for (AbstractInsnNode next = suspension.getSuspensionCallEnd().getNext(); next != null && !UtilKt.isBranchOrCall(next); next = next.getNext()) {
            if (next instanceof LineNumberNode) {
                return (LineNumberNode) next;
            }
        }
        return null;
    }

    private final void splitTryCatchBlocksContainingSuspensionPoint(MethodNode methodNode, SuspensionPoint suspensionPoint) {
        List listListOf;
        InsnList insnList = methodNode.instructions;
        int iIndexOf = insnList.indexOf(suspensionPoint.getSuspensionCallBegin());
        int iIndexOf2 = insnList.indexOf(suspensionPoint.getSuspensionCallEnd());
        LabelNode labelNode = new LabelNode();
        LabelNode labelNode2 = new LabelNode();
        LabelNode labelNode3 = labelNode;
        insnList.insert(suspensionPoint.getSuspensionCallEnd(), labelNode3);
        insnList.insert(labelNode3, new InsnNode(0));
        insnList.insert(labelNode.getNext(), labelNode2);
        List<TryCatchBlockNode> tryCatchBlocks = methodNode.tryCatchBlocks;
        Intrinsics.checkNotNullExpressionValue(tryCatchBlocks, "tryCatchBlocks");
        ArrayList arrayList = new ArrayList();
        for (TryCatchBlockNode tryCatchBlockNode : tryCatchBlocks) {
            if (insnList.indexOf(tryCatchBlockNode.start) < iIndexOf && iIndexOf < insnList.indexOf(tryCatchBlockNode.end)) {
                if (insnList.indexOf(tryCatchBlockNode.start) < iIndexOf2) {
                    insnList.indexOf(tryCatchBlockNode.end);
                }
                listListOf = CollectionsKt.listOf((Object[]) new TryCatchBlockNode[]{new TryCatchBlockNode(tryCatchBlockNode.start, labelNode, tryCatchBlockNode.handler, tryCatchBlockNode.type), new TryCatchBlockNode(labelNode2, tryCatchBlockNode.end, tryCatchBlockNode.handler, tryCatchBlockNode.type)});
            } else {
                listListOf = CollectionsKt.listOf(tryCatchBlockNode);
            }
            CollectionsKt.addAll(arrayList, listListOf);
        }
        methodNode.tryCatchBlocks = arrayList;
        suspensionPoint.setTryCatchBlocksContinuationLabel(labelNode2);
    }

    /* JADX INFO: compiled from: CoroutineTransformerMethodVisitor.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/codegen/coroutines/CoroutineTransformerMethodVisitor$SpilledVariableAndField;", "", "fieldName", "", "variableName", "(Ljava/lang/String;Ljava/lang/String;)V", "getFieldName", "()Ljava/lang/String;", "getVariableName", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class SpilledVariableAndField {
        private final String fieldName;
        private final String variableName;

        public static /* synthetic */ SpilledVariableAndField copy$default(SpilledVariableAndField spilledVariableAndField, String str, String str2, int i, Object obj) {
            if ((i & 1) != 0) {
                str = spilledVariableAndField.fieldName;
            }
            if ((i & 2) != 0) {
                str2 = spilledVariableAndField.variableName;
            }
            return spilledVariableAndField.copy(str, str2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getFieldName() {
            return this.fieldName;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final String getVariableName() {
            return this.variableName;
        }

        public final SpilledVariableAndField copy(String fieldName, String variableName) {
            Intrinsics.checkNotNullParameter(fieldName, "fieldName");
            Intrinsics.checkNotNullParameter(variableName, "variableName");
            return new SpilledVariableAndField(fieldName, variableName);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof SpilledVariableAndField)) {
                return false;
            }
            SpilledVariableAndField spilledVariableAndField = (SpilledVariableAndField) other;
            return Intrinsics.areEqual(this.fieldName, spilledVariableAndField.fieldName) && Intrinsics.areEqual(this.variableName, spilledVariableAndField.variableName);
        }

        public int hashCode() {
            return (this.fieldName.hashCode() * 31) + this.variableName.hashCode();
        }

        public String toString() {
            return "SpilledVariableAndField(fieldName=" + this.fieldName + ", variableName=" + this.variableName + ')';
        }

        public SpilledVariableAndField(String fieldName, String variableName) {
            Intrinsics.checkNotNullParameter(fieldName, "fieldName");
            Intrinsics.checkNotNullParameter(variableName, "variableName");
            this.fieldName = fieldName;
            this.variableName = variableName;
        }

        public final String getFieldName() {
            return this.fieldName;
        }

        public final String getVariableName() {
            return this.variableName;
        }
    }
}
