package org.jetbrains.kotlin.codegen.optimization.temporaryVals;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.common.UtilKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.utils.SmartSet;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.IincInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.LocalVariableNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.TryCatchBlockNode;
import org.jetbrains.org.objectweb.asm.tree.VarInsnNode;

/* JADX INFO: compiled from: TemporaryVals.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0003\n\u000b\fB\u0005¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/TemporaryValsAnalyzer;", "", "()V", "analyze", "", "Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/TemporaryVal;", "internalClassName", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "StoreData", "StoreTrackingInterpreter", "StoredValue", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TemporaryValsAnalyzer {
    public final List<TemporaryVal> analyze(String internalClassName, MethodNode methodNode) {
        int opcode;
        Intrinsics.checkNotNullParameter(internalClassName, "internalClassName");
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        final InsnList insnList = methodNode.instructions;
        AbstractInsnNode[] array = insnList.toArray();
        Intrinsics.checkNotNull(insnList);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (AbstractInsnNode abstractInsnNode : insnList) {
            AbstractInsnNode abstractInsnNode2 = abstractInsnNode;
            Intrinsics.checkNotNull(abstractInsnNode2);
            if (UtilKt.isStoreOperation(abstractInsnNode2)) {
                linkedHashSet.add(abstractInsnNode);
            }
        }
        LinkedHashSet linkedHashSet2 = linkedHashSet;
        for (LocalVariableNode localVariableNode : methodNode.localVariables) {
            int iIndexOf = insnList.indexOf(localVariableNode.end);
            for (int iIndexOf2 = insnList.indexOf(localVariableNode.start); iIndexOf2 < iIndexOf; iIndexOf2++) {
                AbstractInsnNode abstractInsnNode3 = array[iIndexOf2];
                Intrinsics.checkNotNull(abstractInsnNode3);
                if (UtilKt.isStoreOperation(abstractInsnNode3) && ((VarInsnNode) abstractInsnNode3).var == localVariableNode.index) {
                    linkedHashSet2.remove(abstractInsnNode3);
                }
            }
            for (AbstractInsnNode previous = localVariableNode.start.getPrevious(); previous != null; previous = previous.getPrevious()) {
                if (UtilKt.isStoreOperation(previous) && ((VarInsnNode) previous).var == localVariableNode.index) {
                    linkedHashSet2.remove(previous);
                    break;
                }
                if (previous.getType() == 8 || ((172 <= (opcode = previous.getOpcode()) && opcode < 178) || previous.getOpcode() == 167 || previous.getOpcode() == 191)) {
                    break;
                }
            }
        }
        for (TryCatchBlockNode tryCatchBlockNode : methodNode.tryCatchBlocks) {
            LabelNode next = tryCatchBlockNode.handler;
            while (next != null && !UtilKt.isMeaningful(next)) {
                next = next.getNext();
            }
            if (next != null && next.getOpcode() == 58) {
                linkedHashSet2.remove(next);
            }
            for (AbstractInsnNode previous2 = tryCatchBlockNode.start.getPrevious(); previous2 != null && UtilKt.isStoreOperation(previous2); previous2 = previous2.getPrevious()) {
                linkedHashSet2.remove(previous2);
            }
        }
        if (linkedHashSet2.isEmpty()) {
            return CollectionsKt.emptyList();
        }
        if (((((long) methodNode.instructions.size()) * ((long) methodNode.localVariables.size())) * ((long) linkedHashSet2.size())) / ((long) 1048576) > 50) {
            return CollectionsKt.emptyList();
        }
        LinkedHashSet linkedHashSet3 = linkedHashSet2;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(linkedHashSet3, 10)), 16));
        for (Object obj : linkedHashSet3) {
            AbstractInsnNode abstractInsnNode4 = (AbstractInsnNode) obj;
            Intrinsics.checkNotNull(abstractInsnNode4);
            linkedHashMap.put(obj, new StoreData(abstractInsnNode4));
        }
        LinkedHashMap linkedHashMap2 = linkedHashMap;
        StoreLoadFrame[] storeLoadFrameArrAnalyze = new FastStoreLoadAnalyzer(internalClassName, methodNode, new StoreTrackingInterpreter(linkedHashMap2)).analyze();
        for (LocalVariableNode localVariableNode2 : methodNode.localVariables) {
            StoreLoadFrame storeLoadFrame = storeLoadFrameArrAnalyze[insnList.indexOf(localVariableNode2.start)];
            if (storeLoadFrame != null) {
                StoredValue storedValue = (StoredValue) storeLoadFrame.get(localVariableNode2.index);
                if (storedValue instanceof StoredValue.Store) {
                    ((StoredValue.Store) storedValue).getTemporaryVal().setDirty(true);
                } else if (!(storedValue instanceof StoredValue.DirtyStore)) {
                    Intrinsics.areEqual(storedValue, StoredValue.Unknown.INSTANCE);
                } else {
                    Iterator<T> it = ((StoredValue.DirtyStore) storedValue).getTemporaryVals().iterator();
                    while (it.hasNext()) {
                        ((StoreData) it.next()).setDirty(true);
                    }
                }
            }
        }
        Collection collectionValues = linkedHashMap2.values();
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : collectionValues) {
            if (!((StoreData) obj2).getIsDirty()) {
                arrayList.add(obj2);
            }
        }
        ArrayList<StoreData> arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        for (StoreData storeData : arrayList2) {
            AbstractInsnNode storeInsn = storeData.getStoreInsn();
            Intrinsics.checkNotNull(storeInsn, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.VarInsnNode");
            VarInsnNode varInsnNode = (VarInsnNode) storeInsn;
            LinkedHashSet<AbstractInsnNode> loads = storeData.getLoads();
            ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(loads, 10));
            for (AbstractInsnNode abstractInsnNode5 : loads) {
                Intrinsics.checkNotNull(abstractInsnNode5, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.VarInsnNode");
                arrayList4.add((VarInsnNode) abstractInsnNode5);
            }
            arrayList3.add(new TemporaryVal(varInsnNode.var, varInsnNode, arrayList4));
        }
        return CollectionsKt.sortedWith(arrayList3, new Comparator() { // from class: org.jetbrains.kotlin.codegen.optimization.temporaryVals.TemporaryValsAnalyzer$analyze$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(Integer.valueOf(insnList.indexOf(((TemporaryVal) t).getStoreInsn())), Integer.valueOf(insnList.indexOf(((TemporaryVal) t2).getStoreInsn())));
            }
        });
    }

    /* JADX INFO: compiled from: TemporaryVals.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0007\"\u0004\b\b\u0010\tR!\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\u00030\u000bj\b\u0012\u0004\u0012\u00020\u0003`\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/TemporaryValsAnalyzer$StoreData;", "", "storeInsn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "(Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;)V", "isDirty", "", "()Z", "setDirty", "(Z)V", "loads", "Ljava/util/LinkedHashSet;", "Lkotlin/collections/LinkedHashSet;", "getLoads", "()Ljava/util/LinkedHashSet;", "getStoreInsn", "()Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "value", "Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/TemporaryValsAnalyzer$StoredValue$Store;", "getValue", "()Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/TemporaryValsAnalyzer$StoredValue$Store;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class StoreData {
        private boolean isDirty;
        private final LinkedHashSet<AbstractInsnNode> loads;
        private final AbstractInsnNode storeInsn;
        private final StoredValue.Store value;

        public StoreData(AbstractInsnNode storeInsn) {
            Intrinsics.checkNotNullParameter(storeInsn, "storeInsn");
            this.storeInsn = storeInsn;
            this.value = new StoredValue.Store(this);
            this.loads = new LinkedHashSet<>();
        }

        public final AbstractInsnNode getStoreInsn() {
            return this.storeInsn;
        }

        /* JADX INFO: renamed from: isDirty, reason: from getter */
        public final boolean getIsDirty() {
            return this.isDirty;
        }

        public final void setDirty(boolean z) {
            this.isDirty = z;
        }

        public final StoredValue.Store getValue() {
            return this.value;
        }

        public final LinkedHashSet<AbstractInsnNode> getLoads() {
            return this.loads;
        }
    }

    /* JADX INFO: compiled from: TemporaryVals.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b2\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/TemporaryValsAnalyzer$StoredValue;", "Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/StoreLoadValue;", "()V", "DirtyStore", "Store", "Unknown", "Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/TemporaryValsAnalyzer$StoredValue$DirtyStore;", "Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/TemporaryValsAnalyzer$StoredValue$Store;", "Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/TemporaryValsAnalyzer$StoredValue$Unknown;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static abstract class StoredValue implements StoreLoadValue {
        public /* synthetic */ StoredValue(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private StoredValue() {
        }

        /* JADX INFO: compiled from: TemporaryVals.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/TemporaryValsAnalyzer$StoredValue$Unknown;", "Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/TemporaryValsAnalyzer$StoredValue;", "()V", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Unknown extends StoredValue {
            public static final Unknown INSTANCE = new Unknown();

            private Unknown() {
                super(null);
            }
        }

        /* JADX INFO: compiled from: TemporaryVals.kt */
        @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0096\u0002J\b\u0010\u000b\u001a\u00020\fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/TemporaryValsAnalyzer$StoredValue$Store;", "Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/TemporaryValsAnalyzer$StoredValue;", "temporaryVal", "Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/TemporaryValsAnalyzer$StoreData;", "(Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/TemporaryValsAnalyzer$StoreData;)V", "getTemporaryVal", "()Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/TemporaryValsAnalyzer$StoreData;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Store extends StoredValue {
            private final StoreData temporaryVal;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Store(StoreData temporaryVal) {
                super(null);
                Intrinsics.checkNotNullParameter(temporaryVal, "temporaryVal");
                this.temporaryVal = temporaryVal;
            }

            public final StoreData getTemporaryVal() {
                return this.temporaryVal;
            }

            public boolean equals(Object other) {
                return (other instanceof Store) && ((Store) other).temporaryVal == this.temporaryVal;
            }

            public int hashCode() {
                return this.temporaryVal.hashCode();
            }
        }

        /* JADX INFO: compiled from: TemporaryVals.kt */
        @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0013\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0096\u0002J\b\u0010\f\u001a\u00020\rH\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/TemporaryValsAnalyzer$StoredValue$DirtyStore;", "Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/TemporaryValsAnalyzer$StoredValue;", "temporaryVals", "", "Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/TemporaryValsAnalyzer$StoreData;", "(Ljava/util/Collection;)V", "getTemporaryVals", "()Ljava/util/Collection;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class DirtyStore extends StoredValue {
            private final Collection<StoreData> temporaryVals;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public DirtyStore(Collection<StoreData> temporaryVals) {
                super(null);
                Intrinsics.checkNotNullParameter(temporaryVals, "temporaryVals");
                this.temporaryVals = temporaryVals;
            }

            public final Collection<StoreData> getTemporaryVals() {
                return this.temporaryVals;
            }

            public boolean equals(Object other) {
                return (other instanceof DirtyStore) && Intrinsics.areEqual(((DirtyStore) other).temporaryVals, this.temporaryVals);
            }

            public int hashCode() {
                return this.temporaryVals.hashCode();
            }
        }
    }

    /* JADX INFO: compiled from: TemporaryVals.kt */
    @Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0019\u0012\u0012\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\u0002\u0010\u0007J\u0018\u0010\b\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0016J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\u0002H\u0016J\u0018\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u0002H\u0016J\u0010\u0010\u0012\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\u000eH\u0016J\b\u0010\u0013\u001a\u00020\u0002H\u0016J\u0010\u0010\u0014\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0012\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00060\u0018*\u00020\u0002H\u0002R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/TemporaryValsAnalyzer$StoreTrackingInterpreter;", "Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/StoreLoadInterpreter;", "Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/TemporaryValsAnalyzer$StoredValue;", "storeInsnToStoreData", "", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "Lorg/jetbrains/kotlin/codegen/optimization/temporaryVals/TemporaryValsAnalyzer$StoreData;", "(Ljava/util/Map;)V", "iinc", "insn", "Lorg/jetbrains/org/objectweb/asm/tree/IincInsnNode;", "value", "load", "", "Lorg/jetbrains/org/objectweb/asm/tree/VarInsnNode;", "merge", "a", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, "store", "uninitialized", "valueParameter", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "temporaryVals", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class StoreTrackingInterpreter implements StoreLoadInterpreter<StoredValue> {
        private final Map<AbstractInsnNode, StoreData> storeInsnToStoreData;

        public StoreTrackingInterpreter(Map<AbstractInsnNode, StoreData> storeInsnToStoreData) {
            Intrinsics.checkNotNullParameter(storeInsnToStoreData, "storeInsnToStoreData");
            this.storeInsnToStoreData = storeInsnToStoreData;
        }

        @Override // org.jetbrains.kotlin.codegen.optimization.temporaryVals.StoreLoadInterpreter
        public StoredValue uninitialized() {
            return StoredValue.Unknown.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.codegen.optimization.temporaryVals.StoreLoadInterpreter
        public StoredValue valueParameter(Type type) {
            Intrinsics.checkNotNullParameter(type, "type");
            return StoredValue.Unknown.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.codegen.optimization.temporaryVals.StoreLoadInterpreter
        public StoredValue store(VarInsnNode insn) {
            Intrinsics.checkNotNullParameter(insn, "insn");
            StoreData storeData = this.storeInsnToStoreData.get(insn);
            if (storeData != null) {
                return storeData.getValue();
            }
            return StoredValue.Unknown.INSTANCE;
        }

        @Override // org.jetbrains.kotlin.codegen.optimization.temporaryVals.StoreLoadInterpreter
        public void load(VarInsnNode insn, StoredValue value) {
            Intrinsics.checkNotNullParameter(insn, "insn");
            Intrinsics.checkNotNullParameter(value, "value");
            if (!(value instanceof StoredValue.DirtyStore)) {
                if (value instanceof StoredValue.Store) {
                    ((StoredValue.Store) value).getTemporaryVal().getLoads().add(insn);
                }
            } else {
                Iterator<T> it = ((StoredValue.DirtyStore) value).getTemporaryVals().iterator();
                while (it.hasNext()) {
                    ((StoreData) it.next()).setDirty(true);
                }
            }
        }

        @Override // org.jetbrains.kotlin.codegen.optimization.temporaryVals.StoreLoadInterpreter
        public StoredValue iinc(IincInsnNode insn, StoredValue value) {
            Intrinsics.checkNotNullParameter(insn, "insn");
            Intrinsics.checkNotNullParameter(value, "value");
            if (value instanceof StoredValue.Store) {
                ((StoredValue.Store) value).getTemporaryVal().setDirty(true);
            } else if (value instanceof StoredValue.DirtyStore) {
                Iterator<T> it = ((StoredValue.DirtyStore) value).getTemporaryVals().iterator();
                while (it.hasNext()) {
                    ((StoreData) it.next()).setDirty(true);
                }
            }
            return value;
        }

        @Override // org.jetbrains.kotlin.codegen.optimization.temporaryVals.StoreLoadInterpreter
        public StoredValue merge(StoredValue a, StoredValue b) {
            Intrinsics.checkNotNullParameter(a, "a");
            Intrinsics.checkNotNullParameter(b, "b");
            if (a == b) {
                return a;
            }
            if ((a instanceof StoredValue.Store) || (a instanceof StoredValue.DirtyStore) || (b instanceof StoredValue.Store) || (b instanceof StoredValue.DirtyStore)) {
                SmartSet smartSetCreate = SmartSet.INSTANCE.create(temporaryVals(a));
                smartSetCreate.addAll(temporaryVals(b));
                return new StoredValue.DirtyStore(smartSetCreate);
            }
            return StoredValue.Unknown.INSTANCE;
        }

        private final Collection<StoreData> temporaryVals(StoredValue storedValue) {
            if (storedValue instanceof StoredValue.Store) {
                return SetsKt.setOf(((StoredValue.Store) storedValue).getTemporaryVal());
            }
            return storedValue instanceof StoredValue.DirtyStore ? ((StoredValue.DirtyStore) storedValue).getTemporaryVals() : SetsKt.emptySet();
        }
    }
}
