package org.jetbrains.kotlin.cfg.variable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javaslang.Tuple2;
import javaslang.collection.HashSet;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import net.bytebuddy.implementation.MethodDelegation;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.cfg.ReadOnlyControlFlowInfo;
import org.jetbrains.kotlin.cfg.pseudocode.Pseudocode;
import org.jetbrains.kotlin.cfg.pseudocode.PseudocodeUtil;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.BlockScope;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.Instruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.AccessValueInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.MagicInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.MagicKind;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.ReadValueInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.eval.WriteValueInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.LocalFunctionDeclarationInstruction;
import org.jetbrains.kotlin.cfg.pseudocode.instructions.special.VariableDeclarationInstruction;
import org.jetbrains.kotlin.cfg.pseudocodeTraverser.Edges;
import org.jetbrains.kotlin.cfg.pseudocodeTraverser.PseudocodeTraverserKt;
import org.jetbrains.kotlin.cfg.pseudocodeTraverser.TraversalOrder;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtVariableDeclaration;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;
import org.jetbrains.kotlin.util.javaslang.JavaslangAdaptersKt;

/* JADX INFO: compiled from: PseudocodeVariablesData.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000¦\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010#\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 G2\u00020\u0001:\u0004GHIJB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J \u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020\u001c2\u0006\u0010+\u001a\u00020)2\u0006\u0010\u0007\u001a\u00020\bH\u0002J,\u0010,\u001a\u00020-2\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010.\u001a\b\u0012\u0004\u0012\u00020$0/2\f\u00100\u001a\b\u0012\u0004\u0012\u00020$0/H\u0002J\u0010\u00101\u001a\u00020\u000f2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u001e\u00102\u001a\u0018\u0012\u0004\u0012\u00020\u001c\u0012\u000e\u0012\f\u0012\b\u0012\u000603R\u00020\u00000\u001d0\u001bH\u0002J\u0012\u00104\u001a\f\u0012\b\u0012\u000605R\u00020\u00000\u001dH\u0002J\u001a\u00106\u001a\u0014\u0012\u0004\u0012\u00020\u001c\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0\u001d0\u001bH\u0002J\u0012\u00107\u001a\u0004\u0018\u00010$2\u0006\u0010*\u001a\u00020\u001cH\u0002J\u0018\u00108\u001a\u00020\u000f2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u00109\u001a\u00020\fH\u0002J\u001c\u0010:\u001a\b\u0012\u0004\u0012\u00020$0;2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u00109\u001a\u00020\fJ\u0010\u0010<\u001a\u00020\u000f2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u0018\u0010=\u001a\u00020\f2\u0006\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020$H\u0002J\u000e\u0010A\u001a\u00020\f2\u0006\u0010B\u001a\u00020$J\f\u0010C\u001a\u00020\f*\u00020$H\u0002J\f\u0010D\u001a\u00020\f*\u00020EH\u0002J\u0014\u0010A\u001a\u00020\f*\u00020F2\u0006\u0010@\u001a\u00020$H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0007\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\r\u001a\u001e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u000f0\u000ej\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u000f`\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0015\u001a\u00020\u000f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u0016\u0010\u0017R-\u0010\u001a\u001a\u0014\u0012\u0004\u0012\u00020\u001c\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0\u001d0\u001b8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b!\u0010\u0019\u001a\u0004\b\u001f\u0010 R3\u0010\"\u001a$\u0012\u0004\u0012\u00020\u001c\u0012\u001a\u0012\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020%0#j\u0002`&0\u001d0\u001b8F¢\u0006\u0006\u001a\u0004\b'\u0010 ¨\u0006K"}, d2 = {"Lorg/jetbrains/kotlin/cfg/variable/PseudocodeVariablesData;", "", "pseudocode", "Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "(Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;Lorg/jetbrains/kotlin/resolve/BindingContext;)V", "blockScopeVariableInfo", "Lorg/jetbrains/kotlin/cfg/variable/BlockScopeVariableInfo;", "getBlockScopeVariableInfo", "()Lorg/jetbrains/kotlin/cfg/variable/BlockScopeVariableInfo;", "containsDoWhile", "", "declaredVariablesForDeclaration", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/cfg/variable/PseudocodeVariablesData$VariablesForDeclaration;", "Lkotlin/collections/HashMap;", "getPseudocode", "()Lorg/jetbrains/kotlin/cfg/pseudocode/Pseudocode;", "pseudocodeVariableDataCollector", "Lorg/jetbrains/kotlin/cfg/variable/PseudocodeVariableDataCollector;", "rootVariables", "getRootVariables", "()Lorg/jetbrains/kotlin/cfg/variable/PseudocodeVariablesData$VariablesForDeclaration;", "rootVariables$delegate", "Lkotlin/Lazy;", "variableInitializers", "", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;", "Lorg/jetbrains/kotlin/cfg/pseudocodeTraverser/Edges;", "Lorg/jetbrains/kotlin/cfg/variable/VariableInitReadOnlyControlFlowInfo;", "getVariableInitializers", "()Ljava/util/Map;", "variableInitializers$delegate", "variableUseStatusData", "Lorg/jetbrains/kotlin/cfg/ReadOnlyControlFlowInfo;", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "Lorg/jetbrains/kotlin/cfg/variable/VariableUseState;", "Lorg/jetbrains/kotlin/cfg/variable/VariableUsageReadOnlyControlInfo;", "getVariableUseStatusData", "addVariableInitStateFromCurrentInstructionIfAny", "Lorg/jetbrains/kotlin/cfg/variable/VariableInitControlFlowInfo;", "instruction", "enterInstructionData", "addVariablesFromPseudocode", "", "nonTrivialVariables", "", "valsWithTrivialInitializer", "computeDeclaredVariablesForPseudocode", "computeInitInfoForTrivialVals", "Lorg/jetbrains/kotlin/cfg/variable/PseudocodeVariablesData$ReadOnlyInitVariableControlFlowInfoImpl;", "computeUseInfoForTrivialVals", "Lorg/jetbrains/kotlin/cfg/variable/PseudocodeVariablesData$ReadOnlyUseControlFlowInfoImpl;", "computeVariableInitializers", "extractValWithTrivialInitializer", "getAllDeclaredVariables", "includeInsideLocalDeclarations", "getDeclaredVariables", "", "getUpperLevelDeclaredVariables", "isValWithTrivialInitializer", "variableDeclarationElement", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "isVariableWithTrivialInitializer", "variableDescriptor", "isPropertyWithoutBackingField", "isTrivialInitializer", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/eval/WriteValueInstruction;", "Lorg/jetbrains/kotlin/psi/KtVariableDeclaration;", "Companion", "ReadOnlyInitVariableControlFlowInfoImpl", "ReadOnlyUseControlFlowInfoImpl", "VariablesForDeclaration", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PseudocodeVariablesData {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final VariableInitControlFlowInfo EMPTY_INIT_CONTROL_FLOW_INFO = new VariableInitControlFlowInfo(null, 1, null);
    private final BindingContext bindingContext;
    private final boolean containsDoWhile;
    private final HashMap<Pseudocode, VariablesForDeclaration> declaredVariablesForDeclaration;
    private final Pseudocode pseudocode;
    private final PseudocodeVariableDataCollector pseudocodeVariableDataCollector;

    /* JADX INFO: renamed from: rootVariables$delegate, reason: from kotlin metadata */
    private final Lazy rootVariables;

    /* JADX INFO: renamed from: variableInitializers$delegate, reason: from kotlin metadata */
    private final Lazy variableInitializers;

    @JvmStatic
    public static final VariableControlFlowState getDefaultValueForInitializers(VariableDescriptor variableDescriptor, Instruction instruction, BlockScopeVariableInfo blockScopeVariableInfo) {
        return INSTANCE.getDefaultValueForInitializers(variableDescriptor, instruction, blockScopeVariableInfo);
    }

    public PseudocodeVariablesData(Pseudocode pseudocode, BindingContext bindingContext) {
        Intrinsics.checkNotNullParameter(pseudocode, "pseudocode");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        this.pseudocode = pseudocode;
        this.bindingContext = bindingContext;
        this.containsDoWhile = pseudocode.getRootPseudocode().getContainsDoWhile();
        this.pseudocodeVariableDataCollector = new PseudocodeVariableDataCollector(bindingContext, pseudocode);
        this.declaredVariablesForDeclaration = new HashMap<>();
        this.rootVariables = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<VariablesForDeclaration>() { // from class: org.jetbrains.kotlin.cfg.variable.PseudocodeVariablesData$rootVariables$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PseudocodeVariablesData.VariablesForDeclaration invoke() {
                PseudocodeVariablesData pseudocodeVariablesData = this.this$0;
                return pseudocodeVariablesData.getAllDeclaredVariables(pseudocodeVariablesData.getPseudocode(), true);
            }
        });
        this.variableInitializers = LazyKt.lazy(new Function0<Map<Instruction, ? extends Edges<? extends VariableInitReadOnlyControlFlowInfo>>>() { // from class: org.jetbrains.kotlin.cfg.variable.PseudocodeVariablesData$variableInitializers$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<Instruction, ? extends Edges<? extends VariableInitReadOnlyControlFlowInfo>> invoke() {
                return this.this$0.computeVariableInitializers();
            }
        });
    }

    public final Pseudocode getPseudocode() {
        return this.pseudocode;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: PseudocodeVariablesData.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\b\b\u0002\u0018\u00002\u00020\u0001B!\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0006R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\t¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/cfg/variable/PseudocodeVariablesData$VariablesForDeclaration;", "", "valsWithTrivialInitializer", "", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "nonTrivialVariables", "(Ljava/util/Set;Ljava/util/Set;)V", "allVars", "getAllVars", "()Ljava/util/Set;", "getNonTrivialVariables", "getValsWithTrivialInitializer", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class VariablesForDeclaration {
        private final Set<VariableDescriptor> allVars;
        private final Set<VariableDescriptor> nonTrivialVariables;
        private final Set<VariableDescriptor> valsWithTrivialInitializer;

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v0, types: [java.lang.Object, java.util.Set<? extends org.jetbrains.kotlin.descriptors.VariableDescriptor>, java.util.Set<org.jetbrains.kotlin.descriptors.VariableDescriptor>] */
        /* JADX WARN: Type inference failed for: r2v1, types: [java.util.Set<org.jetbrains.kotlin.descriptors.VariableDescriptor>] */
        /* JADX WARN: Type inference failed for: r2v5 */
        /* JADX WARN: Type inference failed for: r2v6 */
        /* JADX WARN: Type inference failed for: r3v0, types: [java.lang.Object, java.util.Set, java.util.Set<? extends org.jetbrains.kotlin.descriptors.VariableDescriptor>, java.util.Set<org.jetbrains.kotlin.descriptors.VariableDescriptor>] */
        public VariablesForDeclaration(Set<? extends VariableDescriptor> valsWithTrivialInitializer, Set<? extends VariableDescriptor> nonTrivialVariables) {
            Intrinsics.checkNotNullParameter(valsWithTrivialInitializer, "valsWithTrivialInitializer");
            Intrinsics.checkNotNullParameter(nonTrivialVariables, "nonTrivialVariables");
            this.valsWithTrivialInitializer = valsWithTrivialInitializer;
            this.nonTrivialVariables = nonTrivialVariables;
            ?? r2 = valsWithTrivialInitializer;
            if (!nonTrivialVariables.isEmpty()) {
                LinkedHashSet linkedHashSet = new LinkedHashSet((Collection) valsWithTrivialInitializer);
                linkedHashSet.addAll((Collection) nonTrivialVariables);
                r2 = linkedHashSet;
            }
            this.allVars = r2;
        }

        public final Set<VariableDescriptor> getValsWithTrivialInitializer() {
            return this.valsWithTrivialInitializer;
        }

        public final Set<VariableDescriptor> getNonTrivialVariables() {
            return this.nonTrivialVariables;
        }

        public final Set<VariableDescriptor> getAllVars() {
            return this.allVars;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final VariablesForDeclaration getRootVariables() {
        return (VariablesForDeclaration) this.rootVariables.getValue();
    }

    public final Map<Instruction, Edges<VariableInitReadOnlyControlFlowInfo>> getVariableInitializers() {
        return (Map) this.variableInitializers.getValue();
    }

    public final BlockScopeVariableInfo getBlockScopeVariableInfo() {
        return this.pseudocodeVariableDataCollector.getBlockScopeVariableInfo();
    }

    public final Set<VariableDescriptor> getDeclaredVariables(Pseudocode pseudocode, boolean includeInsideLocalDeclarations) {
        Intrinsics.checkNotNullParameter(pseudocode, "pseudocode");
        return getAllDeclaredVariables(pseudocode, includeInsideLocalDeclarations).getAllVars();
    }

    public final boolean isVariableWithTrivialInitializer(VariableDescriptor variableDescriptor) {
        Intrinsics.checkNotNullParameter(variableDescriptor, "variableDescriptor");
        return getRootVariables().getValsWithTrivialInitializer().contains(variableDescriptor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final VariablesForDeclaration getAllDeclaredVariables(Pseudocode pseudocode, boolean includeInsideLocalDeclarations) {
        if (!includeInsideLocalDeclarations) {
            return getUpperLevelDeclaredVariables(pseudocode);
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        addVariablesFromPseudocode(pseudocode, linkedHashSet, linkedHashSet2);
        Iterator<LocalFunctionDeclarationInstruction> it = pseudocode.getLocalDeclarations().iterator();
        while (it.hasNext()) {
            addVariablesFromPseudocode(it.next().getBody(), linkedHashSet, linkedHashSet2);
        }
        return new VariablesForDeclaration(linkedHashSet2, linkedHashSet);
    }

    private final void addVariablesFromPseudocode(Pseudocode pseudocode, Set<VariableDescriptor> nonTrivialVariables, Set<VariableDescriptor> valsWithTrivialInitializer) {
        VariablesForDeclaration upperLevelDeclaredVariables = getUpperLevelDeclaredVariables(pseudocode);
        nonTrivialVariables.addAll(upperLevelDeclaredVariables.getNonTrivialVariables());
        valsWithTrivialInitializer.addAll(upperLevelDeclaredVariables.getValsWithTrivialInitializer());
    }

    private final VariablesForDeclaration getUpperLevelDeclaredVariables(Pseudocode pseudocode) {
        HashMap<Pseudocode, VariablesForDeclaration> map = this.declaredVariablesForDeclaration;
        VariablesForDeclaration variablesForDeclarationComputeDeclaredVariablesForPseudocode = map.get(pseudocode);
        if (variablesForDeclarationComputeDeclaredVariablesForPseudocode == null) {
            variablesForDeclarationComputeDeclaredVariablesForPseudocode = computeDeclaredVariablesForPseudocode(pseudocode);
            map.put(pseudocode, variablesForDeclarationComputeDeclaredVariablesForPseudocode);
        }
        return variablesForDeclarationComputeDeclaredVariablesForPseudocode;
    }

    private final VariablesForDeclaration computeDeclaredVariablesForPseudocode(Pseudocode pseudocode) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        for (Instruction instruction : pseudocode.getInstructions()) {
            if (instruction instanceof VariableDeclarationInstruction) {
                KtDeclaration variableDeclarationElement = ((VariableDeclarationInstruction) instruction).getVariableDeclarationElement();
                VariableDescriptor variableDescriptorVariableDescriptorForDeclaration = BindingContextUtils.variableDescriptorForDeclaration((DeclarationDescriptor) this.bindingContext.get(BindingContext.DECLARATION_TO_DESCRIPTOR, variableDeclarationElement));
                if (variableDescriptorVariableDescriptorForDeclaration != null) {
                    if (!this.containsDoWhile && isValWithTrivialInitializer(variableDeclarationElement, variableDescriptorVariableDescriptorForDeclaration)) {
                        linkedHashSet.add(variableDescriptorVariableDescriptorForDeclaration);
                    } else {
                        linkedHashSet2.add(variableDescriptorVariableDescriptorForDeclaration);
                    }
                }
            }
        }
        return new VariablesForDeclaration(linkedHashSet, linkedHashSet2);
    }

    private final boolean isValWithTrivialInitializer(KtDeclaration variableDeclarationElement, VariableDescriptor descriptor) {
        if ((variableDeclarationElement instanceof KtParameter) || (variableDeclarationElement instanceof KtObjectDeclaration)) {
            return true;
        }
        KtVariableDeclaration ktVariableDeclaration = variableDeclarationElement instanceof KtVariableDeclaration ? (KtVariableDeclaration) variableDeclarationElement : null;
        return ktVariableDeclaration != null && isVariableWithTrivialInitializer(ktVariableDeclaration, descriptor);
    }

    private final boolean isVariableWithTrivialInitializer(KtVariableDeclaration ktVariableDeclaration, VariableDescriptor variableDescriptor) {
        if (isPropertyWithoutBackingField(variableDescriptor)) {
            return true;
        }
        if (ktVariableDeclaration.isVar()) {
            return false;
        }
        if (ktVariableDeclaration.getInitializer() != null) {
            return true;
        }
        KtProperty ktProperty = ktVariableDeclaration instanceof KtProperty ? (KtProperty) ktVariableDeclaration : null;
        return (ktProperty != null ? ktProperty.getDelegate() : null) != null || (ktVariableDeclaration instanceof KtDestructuringDeclarationEntry);
    }

    private final boolean isPropertyWithoutBackingField(VariableDescriptor variableDescriptor) {
        if (variableDescriptor instanceof PropertyDescriptor) {
            return !Intrinsics.areEqual(this.bindingContext.get(BindingContext.BACKING_FIELD_REQUIRED, variableDescriptor), (Object) true);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Map<Instruction, Edges<VariableInitReadOnlyControlFlowInfo>> computeVariableInitializers() {
        final BlockScopeVariableInfo blockScopeVariableInfo = this.pseudocodeVariableDataCollector.getBlockScopeVariableInfo();
        Map<Instruction, Edges<ReadOnlyInitVariableControlFlowInfoImpl>> mapComputeInitInfoForTrivialVals = computeInitInfoForTrivialVals();
        if (getRootVariables().getNonTrivialVariables().isEmpty()) {
            return mapComputeInitInfoForTrivialVals;
        }
        Map mapCollectData = this.pseudocodeVariableDataCollector.collectData(TraversalOrder.FORWARD, new VariableInitControlFlowInfo(null, 1, null), new Function2<Instruction, Collection<? extends VariableInitControlFlowInfo>, Edges<? extends VariableInitControlFlowInfo>>() { // from class: org.jetbrains.kotlin.cfg.variable.PseudocodeVariablesData.computeVariableInitializers.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Edges<? extends VariableInitControlFlowInfo> invoke(Instruction instruction, Collection<? extends VariableInitControlFlowInfo> collection) {
                return invoke2(instruction, (Collection<VariableInitControlFlowInfo>) collection);
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final Edges<VariableInitControlFlowInfo> invoke2(Instruction instruction, Collection<VariableInitControlFlowInfo> incomingEdgesData) {
                Intrinsics.checkNotNullParameter(instruction, "instruction");
                Intrinsics.checkNotNullParameter(incomingEdgesData, "incomingEdgesData");
                VariableInitControlFlowInfo variableInitControlFlowInfoMergeIncomingEdgesDataForInitializers = PseudocodeVariablesData.INSTANCE.mergeIncomingEdgesDataForInitializers(instruction, incomingEdgesData, blockScopeVariableInfo);
                return new Edges<>(variableInitControlFlowInfoMergeIncomingEdgesDataForInitializers, this.addVariableInitStateFromCurrentInstructionIfAny(instruction, variableInitControlFlowInfoMergeIncomingEdgesDataForInitializers, blockScopeVariableInfo));
            }
        });
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(mapCollectData.size()));
        for (Map.Entry entry : mapCollectData.entrySet()) {
            Object key = entry.getKey();
            Instruction instruction = (Instruction) entry.getKey();
            Edges edges = (Edges) entry.getValue();
            Edges<ReadOnlyInitVariableControlFlowInfoImpl> edges2 = mapComputeInitInfoForTrivialVals.get(instruction);
            Intrinsics.checkNotNull(edges2);
            Edges<ReadOnlyInitVariableControlFlowInfoImpl> edges3 = edges2;
            linkedHashMap.put(key, new Edges(edges3.getIncoming().replaceDelegate((VariableInitReadOnlyControlFlowInfo) edges.getIncoming()), edges3.getOutgoing().replaceDelegate((VariableInitReadOnlyControlFlowInfo) edges.getOutgoing())));
        }
        return linkedHashMap;
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [T, javaslang.collection.HashSet] */
    /* JADX WARN: Type inference failed for: r3v0, types: [T, javaslang.collection.HashSet] */
    private final Map<Instruction, Edges<ReadOnlyInitVariableControlFlowInfoImpl>> computeInitInfoForTrivialVals() {
        final HashMap map = new HashMap();
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = HashSet.empty();
        final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
        objectRef2.element = HashSet.empty();
        PseudocodeTraverserKt.traverse(this.pseudocode, TraversalOrder.FORWARD, new Function1<Instruction, Unit>() { // from class: org.jetbrains.kotlin.cfg.variable.PseudocodeVariablesData.computeInitInfoForTrivialVals.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Instruction instruction) {
                invoke2(instruction);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Type inference failed for: r1v5, types: [T, javaslang.collection.HashSet] */
            /* JADX WARN: Type inference failed for: r1v9, types: [T, javaslang.collection.HashSet] */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Instruction instruction) {
                VariableDescriptor variableDescriptorExtractValWithTrivialInitializer;
                Intrinsics.checkNotNullParameter(instruction, "instruction");
                PseudocodeVariablesData pseudocodeVariablesData = PseudocodeVariablesData.this;
                HashSet<VariableDescriptor> element = objectRef.element;
                Intrinsics.checkNotNullExpressionValue(element, "element");
                HashSet<VariableDescriptor> element2 = objectRef2.element;
                Intrinsics.checkNotNullExpressionValue(element2, "element");
                ReadOnlyInitVariableControlFlowInfoImpl readOnlyInitVariableControlFlowInfoImpl = new ReadOnlyInitVariableControlFlowInfoImpl(pseudocodeVariablesData, element, element2, null);
                if (instruction instanceof VariableDeclarationInstruction) {
                    VariableDescriptor variableDescriptorExtractValWithTrivialInitializer2 = PseudocodeVariablesData.this.extractValWithTrivialInitializer(instruction);
                    if (variableDescriptorExtractValWithTrivialInitializer2 != null) {
                        Ref.ObjectRef<HashSet<VariableDescriptor>> objectRef3 = objectRef;
                        objectRef3.element = objectRef3.element.add(variableDescriptorExtractValWithTrivialInitializer2);
                    }
                } else if ((instruction instanceof WriteValueInstruction) && (variableDescriptorExtractValWithTrivialInitializer = PseudocodeVariablesData.this.extractValWithTrivialInitializer(instruction)) != null && PseudocodeVariablesData.this.isTrivialInitializer((WriteValueInstruction) instruction)) {
                    Ref.ObjectRef<HashSet<VariableDescriptor>> objectRef4 = objectRef2;
                    objectRef4.element = objectRef4.element.add(variableDescriptorExtractValWithTrivialInitializer);
                }
                PseudocodeVariablesData pseudocodeVariablesData2 = PseudocodeVariablesData.this;
                HashSet<VariableDescriptor> element3 = objectRef.element;
                Intrinsics.checkNotNullExpressionValue(element3, "element");
                HashSet<VariableDescriptor> element4 = objectRef2.element;
                Intrinsics.checkNotNullExpressionValue(element4, "element");
                map.put(instruction, new Edges<>(readOnlyInitVariableControlFlowInfoImpl, new ReadOnlyInitVariableControlFlowInfoImpl(pseudocodeVariablesData2, element3, element4, null)));
            }
        });
        return map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isTrivialInitializer(WriteValueInstruction writeValueInstruction) {
        return writeValueInstruction.getElement() instanceof KtDeclaration;
    }

    /* JADX INFO: compiled from: PseudocodeVariablesData.kt */
    @Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u00020\u0001B?\u0012\u0016\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u00040\u0003j\b\u0012\u0004\u0012\u00020\u0004`\u0005\u0012\u0016\u0010\u0006\u001a\u0012\u0012\u0004\u0012\u00020\u00040\u0003j\b\u0012\u0004\u0012\u00020\u0004`\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\bJ$\u0010\f\u001a\u001e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000e0\rj\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000e`\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0001H\u0016J\u0013\u0010\u0013\u001a\u00020\u00112\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0096\u0002J\u0012\u0010\u0016\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0017\u001a\u00020\u0004H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\u000e\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u001b\u001a\u00020\u0001R!\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u00040\u0003j\b\u0012\u0004\u0012\u00020\u0004`\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010\u0006\u001a\u0012\u0012\u0004\u0012\u00020\u00040\u0003j\b\u0012\u0004\u0012\u00020\u0004`\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\n¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/cfg/variable/PseudocodeVariablesData$ReadOnlyInitVariableControlFlowInfoImpl;", "Lorg/jetbrains/kotlin/cfg/variable/VariableInitReadOnlyControlFlowInfo;", "declaredSet", "Ljavaslang/collection/Set;", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "Lorg/jetbrains/kotlin/util/javaslang/ImmutableSet;", "initSet", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "(Lorg/jetbrains/kotlin/cfg/variable/PseudocodeVariablesData;Ljavaslang/collection/Set;Ljavaslang/collection/Set;Lorg/jetbrains/kotlin/cfg/variable/VariableInitReadOnlyControlFlowInfo;)V", "getDeclaredSet", "()Ljavaslang/collection/Set;", "getInitSet", "asMap", "Ljavaslang/collection/Map;", "Lorg/jetbrains/kotlin/cfg/variable/VariableControlFlowState;", "Lorg/jetbrains/kotlin/util/javaslang/ImmutableMap;", "checkDefiniteInitializationInWhen", "", "merge", Namer.EQUALS_METHOD_NAME, "other", "", "getOrNull", ASTExpr.DEFAULT_MAP_KEY_NAME, "hashCode", "", "replaceDelegate", "newDelegate", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class ReadOnlyInitVariableControlFlowInfoImpl implements VariableInitReadOnlyControlFlowInfo {
        private final javaslang.collection.Set<VariableDescriptor> declaredSet;
        private final VariableInitReadOnlyControlFlowInfo delegate;
        private final javaslang.collection.Set<VariableDescriptor> initSet;
        final /* synthetic */ PseudocodeVariablesData this$0;

        public ReadOnlyInitVariableControlFlowInfoImpl(PseudocodeVariablesData pseudocodeVariablesData, javaslang.collection.Set<VariableDescriptor> declaredSet, javaslang.collection.Set<VariableDescriptor> initSet, VariableInitReadOnlyControlFlowInfo variableInitReadOnlyControlFlowInfo) {
            Intrinsics.checkNotNullParameter(declaredSet, "declaredSet");
            Intrinsics.checkNotNullParameter(initSet, "initSet");
            this.this$0 = pseudocodeVariablesData;
            this.declaredSet = declaredSet;
            this.initSet = initSet;
            this.delegate = variableInitReadOnlyControlFlowInfo;
        }

        public final javaslang.collection.Set<VariableDescriptor> getDeclaredSet() {
            return this.declaredSet;
        }

        public final javaslang.collection.Set<VariableDescriptor> getInitSet() {
            return this.initSet;
        }

        @Override // org.jetbrains.kotlin.cfg.ReadOnlyControlFlowInfo
        public VariableControlFlowState getOrNull(VariableDescriptor key) {
            Intrinsics.checkNotNullParameter(key, "key");
            if (this.declaredSet.contains(key)) {
                return VariableControlFlowState.INSTANCE.create(this.initSet.contains(key), true);
            }
            VariableInitReadOnlyControlFlowInfo variableInitReadOnlyControlFlowInfo = this.delegate;
            if (variableInitReadOnlyControlFlowInfo != null) {
                return variableInitReadOnlyControlFlowInfo.getOrNull(key);
            }
            return null;
        }

        @Override // org.jetbrains.kotlin.cfg.variable.VariableInitReadOnlyControlFlowInfo
        public boolean checkDefiniteInitializationInWhen(VariableInitReadOnlyControlFlowInfo merge) {
            Intrinsics.checkNotNullParameter(merge, "merge");
            VariableInitReadOnlyControlFlowInfo variableInitReadOnlyControlFlowInfo = this.delegate;
            if (variableInitReadOnlyControlFlowInfo != null) {
                return variableInitReadOnlyControlFlowInfo.checkDefiniteInitializationInWhen(merge);
            }
            return false;
        }

        public final VariableInitReadOnlyControlFlowInfo replaceDelegate(VariableInitReadOnlyControlFlowInfo newDelegate) {
            Intrinsics.checkNotNullParameter(newDelegate, "newDelegate");
            return new ReadOnlyInitVariableControlFlowInfoImpl(this.this$0, this.declaredSet, this.initSet, newDelegate);
        }

        @Override // org.jetbrains.kotlin.cfg.ReadOnlyControlFlowInfo
        public javaslang.collection.Map<VariableDescriptor, VariableControlFlowState> asMap() {
            javaslang.collection.HashMap hashMapEmpty;
            VariableInitReadOnlyControlFlowInfo variableInitReadOnlyControlFlowInfo = this.delegate;
            if (variableInitReadOnlyControlFlowInfo == null || (hashMapEmpty = variableInitReadOnlyControlFlowInfo.asMap()) == null) {
                hashMapEmpty = javaslang.collection.HashMap.empty();
            }
            for (VariableDescriptor variableDescriptor : this.declaredSet) {
                Intrinsics.checkNotNull(variableDescriptor);
                VariableControlFlowState orNull = getOrNull(variableDescriptor);
                Intrinsics.checkNotNull(orNull);
                hashMapEmpty = hashMapEmpty.put(variableDescriptor, orNull);
            }
            Intrinsics.checkNotNullExpressionValue(hashMapEmpty, "fold(...)");
            return hashMapEmpty;
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
                return false;
            }
            Intrinsics.checkNotNull(other, "null cannot be cast to non-null type org.jetbrains.kotlin.cfg.variable.PseudocodeVariablesData.ReadOnlyInitVariableControlFlowInfoImpl");
            ReadOnlyInitVariableControlFlowInfoImpl readOnlyInitVariableControlFlowInfoImpl = (ReadOnlyInitVariableControlFlowInfoImpl) other;
            return Intrinsics.areEqual(this.declaredSet, readOnlyInitVariableControlFlowInfoImpl.declaredSet) && Intrinsics.areEqual(this.initSet, readOnlyInitVariableControlFlowInfoImpl.initSet) && Intrinsics.areEqual(this.delegate, readOnlyInitVariableControlFlowInfoImpl.delegate);
        }

        public int hashCode() {
            int iHashCode = ((this.declaredSet.hashCode() * 31) + this.initSet.hashCode()) * 31;
            VariableInitReadOnlyControlFlowInfo variableInitReadOnlyControlFlowInfo = this.delegate;
            return iHashCode + (variableInitReadOnlyControlFlowInfo != null ? variableInitReadOnlyControlFlowInfo.hashCode() : 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final VariableInitControlFlowInfo addVariableInitStateFromCurrentInstructionIfAny(Instruction instruction, VariableInitControlFlowInfo enterInstructionData, BlockScopeVariableInfo blockScopeVariableInfo) {
        VariableDescriptor variableDescriptorExtractVariableDescriptorIfAny;
        if ((instruction instanceof MagicInstruction) && ((MagicInstruction) instruction).getKind() == MagicKind.EXHAUSTIVE_WHEN_ELSE) {
            javaslang.collection.Iterator<Tuple2> it = enterInstructionData.iterator();
            Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
            for (Tuple2 tuple2 : it) {
                Intrinsics.checkNotNull(tuple2);
                VariableDescriptor variableDescriptor = (VariableDescriptor) JavaslangAdaptersKt.component1(tuple2);
                VariableControlFlowState variableControlFlowState = (VariableControlFlowState) JavaslangAdaptersKt.component2(tuple2);
                if (!variableControlFlowState.definitelyInitialized()) {
                    Intrinsics.checkNotNull(variableDescriptor);
                    enterInstructionData = (VariableInitControlFlowInfo) enterInstructionData.put((Object) variableDescriptor, (Object) VariableControlFlowState.INSTANCE.createInitializedExhaustively(variableControlFlowState.getIsDeclared()));
                }
            }
            return enterInstructionData;
        }
        boolean z = instruction instanceof WriteValueInstruction;
        if ((!z && !(instruction instanceof VariableDeclarationInstruction)) || (variableDescriptorExtractVariableDescriptorIfAny = PseudocodeUtil.extractVariableDescriptorIfAny(instruction, this.bindingContext)) == null) {
            return enterInstructionData;
        }
        if (!getRootVariables().getNonTrivialVariables().contains(variableDescriptorExtractVariableDescriptorIfAny)) {
            variableDescriptorExtractVariableDescriptorIfAny = null;
        }
        if (variableDescriptorExtractVariableDescriptorIfAny == null) {
            return enterInstructionData;
        }
        if (z) {
            if (!PseudocodeUtil.isThisOrNoDispatchReceiver((AccessValueInstruction) instruction, this.bindingContext)) {
                return enterInstructionData;
            }
            VariableControlFlowState orNull = enterInstructionData.getOrNull(variableDescriptorExtractVariableDescriptorIfAny);
            return enterInstructionData.put(variableDescriptorExtractVariableDescriptorIfAny, VariableControlFlowState.INSTANCE.create(((WriteValueInstruction) instruction).getElement() instanceof KtProperty, orNull), orNull);
        }
        VariableControlFlowState orNull2 = enterInstructionData.getOrNull(variableDescriptorExtractVariableDescriptorIfAny);
        if (orNull2 == null) {
            orNull2 = INSTANCE.getDefaultValueForInitializers(variableDescriptorExtractVariableDescriptorIfAny, instruction, blockScopeVariableInfo);
        }
        return (orNull2.mayBeInitialized() && orNull2.getIsDeclared()) ? enterInstructionData : enterInstructionData.put(variableDescriptorExtractVariableDescriptorIfAny, VariableControlFlowState.INSTANCE.create(orNull2.getInitState(), true), orNull2);
    }

    public final Map<Instruction, Edges<ReadOnlyControlFlowInfo<VariableDescriptor, VariableUseState>>> getVariableUseStatusData() {
        final Edges<ReadOnlyUseControlFlowInfoImpl> edgesComputeUseInfoForTrivialVals = computeUseInfoForTrivialVals();
        if (getRootVariables().getNonTrivialVariables().isEmpty()) {
            final HashMap map = new HashMap();
            PseudocodeTraverserKt.traverse(this.pseudocode, TraversalOrder.FORWARD, new Function1<Instruction, Unit>() { // from class: org.jetbrains.kotlin.cfg.variable.PseudocodeVariablesData$variableUseStatusData$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(Instruction instruction) {
                    invoke2(instruction);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(Instruction instruction) {
                    Intrinsics.checkNotNullParameter(instruction, "instruction");
                    map.put(instruction, edgesComputeUseInfoForTrivialVals);
                }
            });
            return map;
        }
        Map mapCollectData = this.pseudocodeVariableDataCollector.collectData(TraversalOrder.BACKWARD, new UsageVariableControlFlowInfo(null, 1, null), new Function2<Instruction, Collection<? extends UsageVariableControlFlowInfo>, Edges<? extends UsageVariableControlFlowInfo>>() { // from class: org.jetbrains.kotlin.cfg.variable.PseudocodeVariablesData$variableUseStatusData$2

            /* JADX INFO: compiled from: PseudocodeVariablesData.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            public /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[VariableUseState.values().length];
                    try {
                        iArr[VariableUseState.UNUSED.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[VariableUseState.ONLY_WRITTEN_NEVER_READ.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    try {
                        iArr[VariableUseState.WRITTEN_AFTER_READ.ordinal()] = 3;
                    } catch (NoSuchFieldError unused3) {
                    }
                    try {
                        iArr[VariableUseState.READ.ordinal()] = 4;
                    } catch (NoSuchFieldError unused4) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Edges<? extends UsageVariableControlFlowInfo> invoke(Instruction instruction, Collection<? extends UsageVariableControlFlowInfo> collection) {
                return invoke2(instruction, (Collection<UsageVariableControlFlowInfo>) collection);
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final Edges<UsageVariableControlFlowInfo> invoke2(Instruction instruction, Collection<UsageVariableControlFlowInfo> incomingEdgesData) {
                UsageVariableControlFlowInfo usageVariableControlFlowInfo;
                boolean z;
                UsageVariableControlFlowInfo usageVariableControlFlowInfo2;
                Intrinsics.checkNotNullParameter(instruction, "instruction");
                Intrinsics.checkNotNullParameter(incomingEdgesData, "incomingEdgesData");
                VariableDescriptor variableDescriptor = null;
                if (incomingEdgesData.size() == 1) {
                    usageVariableControlFlowInfo = (UsageVariableControlFlowInfo) CollectionsKt.single(incomingEdgesData);
                } else {
                    UsageVariableControlFlowInfo usageVariableControlFlowInfo3 = new UsageVariableControlFlowInfo(null, 1, null);
                    Iterator<T> it = incomingEdgesData.iterator();
                    while (it.hasNext()) {
                        javaslang.collection.Iterator<Tuple2> it2 = ((UsageVariableControlFlowInfo) it.next()).iterator();
                        Intrinsics.checkNotNullExpressionValue(it2, "iterator(...)");
                        for (Tuple2 tuple2 : it2) {
                            Intrinsics.checkNotNull(tuple2);
                            VariableDescriptor variableDescriptor2 = (VariableDescriptor) JavaslangAdaptersKt.component1(tuple2);
                            VariableUseState variableUseState = (VariableUseState) JavaslangAdaptersKt.component2(tuple2);
                            Intrinsics.checkNotNull(variableDescriptor2);
                            usageVariableControlFlowInfo3 = (UsageVariableControlFlowInfo) usageVariableControlFlowInfo3.put((Object) variableDescriptor2, (Object) variableUseState.merge(usageVariableControlFlowInfo3.getOrNull(variableDescriptor2)));
                        }
                    }
                    usageVariableControlFlowInfo = usageVariableControlFlowInfo3;
                }
                VariableDescriptor variableDescriptorExtractVariableDescriptorFromReference = PseudocodeUtil.extractVariableDescriptorFromReference(instruction, this.this$0.bindingContext);
                if (variableDescriptorExtractVariableDescriptorFromReference != null && this.this$0.getRootVariables().getNonTrivialVariables().contains(variableDescriptorExtractVariableDescriptorFromReference)) {
                    variableDescriptor = variableDescriptorExtractVariableDescriptorFromReference;
                }
                if (variableDescriptor == null || (!((z = instruction instanceof ReadValueInstruction)) && !(instruction instanceof WriteValueInstruction))) {
                    return new Edges<>(usageVariableControlFlowInfo, usageVariableControlFlowInfo);
                }
                if (z) {
                    usageVariableControlFlowInfo2 = (UsageVariableControlFlowInfo) usageVariableControlFlowInfo.put((Object) variableDescriptor, (Object) VariableUseState.READ);
                } else {
                    VariableUseState orNull = usageVariableControlFlowInfo.getOrNull(variableDescriptor);
                    if (orNull == null) {
                        orNull = VariableUseState.UNUSED;
                    }
                    int i = WhenMappings.$EnumSwitchMapping$0[orNull.ordinal()];
                    if (i == 1 || i == 2) {
                        usageVariableControlFlowInfo2 = (UsageVariableControlFlowInfo) usageVariableControlFlowInfo.put((Object) variableDescriptor, (Object) VariableUseState.ONLY_WRITTEN_NEVER_READ);
                    } else {
                        if (i != 3 && i != 4) {
                            throw new NoWhenBranchMatchedException();
                        }
                        usageVariableControlFlowInfo2 = (UsageVariableControlFlowInfo) usageVariableControlFlowInfo.put((Object) variableDescriptor, (Object) VariableUseState.WRITTEN_AFTER_READ);
                    }
                }
                return new Edges<>(usageVariableControlFlowInfo, usageVariableControlFlowInfo2);
            }
        });
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(mapCollectData.size()));
        for (Map.Entry entry : mapCollectData.entrySet()) {
            Object key = entry.getKey();
            Edges edges = (Edges) entry.getValue();
            linkedHashMap.put(key, new Edges(edgesComputeUseInfoForTrivialVals.getIncoming().replaceDelegate((ReadOnlyControlFlowInfo) edges.getIncoming()), edgesComputeUseInfoForTrivialVals.getOutgoing().replaceDelegate((ReadOnlyControlFlowInfo) edges.getOutgoing())));
        }
        return linkedHashMap;
    }

    private final Edges<ReadOnlyUseControlFlowInfoImpl> computeUseInfoForTrivialVals() {
        final java.util.HashSet hashSet = new java.util.HashSet();
        PseudocodeTraverserKt.traverse(this.pseudocode, TraversalOrder.FORWARD, new Function1<Instruction, Unit>() { // from class: org.jetbrains.kotlin.cfg.variable.PseudocodeVariablesData.computeUseInfoForTrivialVals.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Instruction instruction) {
                invoke2(instruction);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Instruction instruction) {
                VariableDescriptor variableDescriptorExtractValWithTrivialInitializer;
                Intrinsics.checkNotNullParameter(instruction, "instruction");
                if (!(instruction instanceof ReadValueInstruction) || (variableDescriptorExtractValWithTrivialInitializer = PseudocodeVariablesData.this.extractValWithTrivialInitializer(instruction)) == null) {
                    return;
                }
                hashSet.add(variableDescriptorExtractValWithTrivialInitializer);
            }
        });
        ReadOnlyUseControlFlowInfoImpl readOnlyUseControlFlowInfoImpl = new ReadOnlyUseControlFlowInfoImpl(this, hashSet, null);
        return new Edges<>(readOnlyUseControlFlowInfoImpl, readOnlyUseControlFlowInfoImpl);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final VariableDescriptor extractValWithTrivialInitializer(Instruction instruction) {
        VariableDescriptor variableDescriptorExtractVariableDescriptorIfAny = PseudocodeUtil.extractVariableDescriptorIfAny(instruction, this.bindingContext);
        if (variableDescriptorExtractVariableDescriptorIfAny == null || !getRootVariables().getValsWithTrivialInitializer().contains(variableDescriptorExtractVariableDescriptorIfAny)) {
            return null;
        }
        return variableDescriptorExtractVariableDescriptorIfAny;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: PseudocodeVariablesData.kt */
    @Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u0002`\u0004B/\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0006\u0012\u001a\u0010\u0007\u001a\u0016\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0001j\u0004\u0018\u0001`\u0004¢\u0006\u0002\u0010\bJ$\u0010\r\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u000ej\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u000fH\u0016J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0096\u0002J\u0012\u0010\u0014\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0015\u001a\u00020\u0002H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J.\u0010\u0018\u001a\u0012\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u0002`\u00042\u0016\u0010\u0019\u001a\u0012\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u0002`\u0004R%\u0010\u0007\u001a\u0016\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0001j\u0004\u0018\u0001`\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/cfg/variable/PseudocodeVariablesData$ReadOnlyUseControlFlowInfoImpl;", "Lorg/jetbrains/kotlin/cfg/ReadOnlyControlFlowInfo;", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "Lorg/jetbrains/kotlin/cfg/variable/VariableUseState;", "Lorg/jetbrains/kotlin/cfg/variable/VariableUsageReadOnlyControlInfo;", "used", "", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "(Lorg/jetbrains/kotlin/cfg/variable/PseudocodeVariablesData;Ljava/util/Set;Lorg/jetbrains/kotlin/cfg/ReadOnlyControlFlowInfo;)V", "getDelegate", "()Lorg/jetbrains/kotlin/cfg/ReadOnlyControlFlowInfo;", "getUsed", "()Ljava/util/Set;", "asMap", "Ljavaslang/collection/Map;", "Lorg/jetbrains/kotlin/util/javaslang/ImmutableMap;", Namer.EQUALS_METHOD_NAME, "", "other", "", "getOrNull", ASTExpr.DEFAULT_MAP_KEY_NAME, "hashCode", "", "replaceDelegate", "newDelegate", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class ReadOnlyUseControlFlowInfoImpl implements ReadOnlyControlFlowInfo<VariableDescriptor, VariableUseState> {
        private final ReadOnlyControlFlowInfo<VariableDescriptor, VariableUseState> delegate;
        final /* synthetic */ PseudocodeVariablesData this$0;
        private final Set<VariableDescriptor> used;

        /* JADX WARN: Multi-variable type inference failed */
        public ReadOnlyUseControlFlowInfoImpl(PseudocodeVariablesData pseudocodeVariablesData, Set<? extends VariableDescriptor> used, ReadOnlyControlFlowInfo<VariableDescriptor, VariableUseState> readOnlyControlFlowInfo) {
            Intrinsics.checkNotNullParameter(used, "used");
            this.this$0 = pseudocodeVariablesData;
            this.used = used;
            this.delegate = readOnlyControlFlowInfo;
        }

        public final Set<VariableDescriptor> getUsed() {
            return this.used;
        }

        public final ReadOnlyControlFlowInfo<VariableDescriptor, VariableUseState> getDelegate() {
            return this.delegate;
        }

        @Override // org.jetbrains.kotlin.cfg.ReadOnlyControlFlowInfo
        public VariableUseState getOrNull(VariableDescriptor key) {
            Intrinsics.checkNotNullParameter(key, "key");
            if (this.used.contains(key)) {
                return VariableUseState.READ;
            }
            ReadOnlyControlFlowInfo<VariableDescriptor, VariableUseState> readOnlyControlFlowInfo = this.delegate;
            if (readOnlyControlFlowInfo != null) {
                return readOnlyControlFlowInfo.getOrNull(key);
            }
            return null;
        }

        public final ReadOnlyControlFlowInfo<VariableDescriptor, VariableUseState> replaceDelegate(ReadOnlyControlFlowInfo<VariableDescriptor, VariableUseState> newDelegate) {
            Intrinsics.checkNotNullParameter(newDelegate, "newDelegate");
            return new ReadOnlyUseControlFlowInfoImpl(this.this$0, this.used, newDelegate);
        }

        @Override // org.jetbrains.kotlin.cfg.ReadOnlyControlFlowInfo
        public javaslang.collection.Map<VariableDescriptor, VariableUseState> asMap() {
            javaslang.collection.HashMap hashMapEmpty;
            ReadOnlyControlFlowInfo<VariableDescriptor, VariableUseState> readOnlyControlFlowInfo = this.delegate;
            if (readOnlyControlFlowInfo == null || (hashMapEmpty = readOnlyControlFlowInfo.asMap()) == null) {
                hashMapEmpty = javaslang.collection.HashMap.empty();
            }
            for (VariableDescriptor variableDescriptor : this.used) {
                VariableUseState orNull = getOrNull(variableDescriptor);
                Intrinsics.checkNotNull(orNull);
                hashMapEmpty = hashMapEmpty.put(variableDescriptor, orNull);
            }
            Intrinsics.checkNotNullExpressionValue(hashMapEmpty, "fold(...)");
            return hashMapEmpty;
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
                return false;
            }
            Intrinsics.checkNotNull(other, "null cannot be cast to non-null type org.jetbrains.kotlin.cfg.variable.PseudocodeVariablesData.ReadOnlyUseControlFlowInfoImpl");
            ReadOnlyUseControlFlowInfoImpl readOnlyUseControlFlowInfoImpl = (ReadOnlyUseControlFlowInfoImpl) other;
            return Intrinsics.areEqual(this.used, readOnlyUseControlFlowInfoImpl.used) && Intrinsics.areEqual(this.delegate, readOnlyUseControlFlowInfoImpl.delegate);
        }

        public int hashCode() {
            int iHashCode = this.used.hashCode() * 31;
            ReadOnlyControlFlowInfo<VariableDescriptor, VariableUseState> readOnlyControlFlowInfo = this.delegate;
            return iHashCode + (readOnlyControlFlowInfo != null ? readOnlyControlFlowInfo.hashCode() : 0);
        }
    }

    /* JADX INFO: compiled from: PseudocodeVariablesData.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007J&\u0010\r\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\u000f2\u0006\u0010\u000b\u001a\u00020\fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/cfg/variable/PseudocodeVariablesData$Companion;", "", "()V", "EMPTY_INIT_CONTROL_FLOW_INFO", "Lorg/jetbrains/kotlin/cfg/variable/VariableInitControlFlowInfo;", "getDefaultValueForInitializers", "Lorg/jetbrains/kotlin/cfg/variable/VariableControlFlowState;", "variable", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "instruction", "Lorg/jetbrains/kotlin/cfg/pseudocode/instructions/Instruction;", "blockScopeVariableInfo", "Lorg/jetbrains/kotlin/cfg/variable/BlockScopeVariableInfo;", "mergeIncomingEdgesDataForInitializers", "incomingEdgesData", "", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final VariableControlFlowState getDefaultValueForInitializers(VariableDescriptor variable, Instruction instruction, BlockScopeVariableInfo blockScopeVariableInfo) {
            Intrinsics.checkNotNullParameter(variable, "variable");
            Intrinsics.checkNotNullParameter(instruction, "instruction");
            Intrinsics.checkNotNullParameter(blockScopeVariableInfo, "blockScopeVariableInfo");
            BlockScope blockScope = blockScopeVariableInfo.getDeclaredIn().get(variable);
            return VariableControlFlowState.Companion.create$default(VariableControlFlowState.INSTANCE, blockScope == null || !Intrinsics.areEqual(blockScope.getBlockScopeForContainingDeclaration(), instruction.getBlockScope().getBlockScopeForContainingDeclaration()), false, 2, null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final VariableInitControlFlowInfo mergeIncomingEdgesDataForInitializers(Instruction instruction, Collection<VariableInitControlFlowInfo> incomingEdgesData, BlockScopeVariableInfo blockScopeVariableInfo) {
            if (incomingEdgesData.size() == 1) {
                return (VariableInitControlFlowInfo) CollectionsKt.single(incomingEdgesData);
            }
            if (incomingEdgesData.isEmpty()) {
                return PseudocodeVariablesData.EMPTY_INIT_CONTROL_FLOW_INFO;
            }
            LinkedHashSet<VariableDescriptor> linkedHashSet = new LinkedHashSet();
            Iterator<VariableInitControlFlowInfo> it = incomingEdgesData.iterator();
            while (it.hasNext()) {
                javaslang.collection.Set<VariableDescriptor> setKeySet = it.next().keySet();
                Intrinsics.checkNotNullExpressionValue(setKeySet, "keySet(...)");
                CollectionsKt.addAll(linkedHashSet, setKeySet);
            }
            VariableInitControlFlowInfo variableInitControlFlowInfo = PseudocodeVariablesData.EMPTY_INIT_CONTROL_FLOW_INFO;
            VariableInitControlFlowInfo variableInitControlFlowInfo2 = variableInitControlFlowInfo;
            for (VariableDescriptor variableDescriptor : linkedHashSet) {
                Iterator<VariableInitControlFlowInfo> it2 = incomingEdgesData.iterator();
                InitState initState = null;
                boolean z = true;
                while (it2.hasNext()) {
                    VariableControlFlowState orNull = it2.next().getOrNull(variableDescriptor);
                    if (orNull == null) {
                        orNull = PseudocodeVariablesData.INSTANCE.getDefaultValueForInitializers(variableDescriptor, instruction, blockScopeVariableInfo);
                    }
                    if (initState == null || (initState = initState.merge(orNull.getInitState())) == null) {
                        initState = orNull.getInitState();
                    }
                    if (!orNull.getIsDeclared()) {
                        z = false;
                    }
                }
                if (initState == null) {
                    throw new AssertionError("An empty set of incoming edges data");
                }
                variableInitControlFlowInfo2 = (VariableInitControlFlowInfo) variableInitControlFlowInfo2.put((Object) variableDescriptor, VariableControlFlowState.INSTANCE.create(initState, z));
            }
            return variableInitControlFlowInfo2;
        }
    }
}
