package org.jetbrains.kotlin.backend.jvm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: MfvcNode.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0015\b\u0004\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0013\u0010&\u001a\u0004\u0018\u00010\u00042\u0006\u0010'\u001a\u00020\u001dH\u0086\u0002R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0018\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\tR\u0012\u0010\f\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0019\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\tR\u0011\u0010\u0012\u001a\u00020\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\tR\u001a\u0010\u0019\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\tR\u001a\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u00040\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00130\u001c¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\tR\u0012\u0010\"\u001a\u00020#X¦\u0004¢\u0006\u0006\u001a\u0004\b$\u0010%\u0082\u0001\u0002()¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/MfvcNodeWithSubnodes;", "Lorg/jetbrains/kotlin/backend/jvm/MfvcNode;", "subnodes", "", "Lorg/jetbrains/kotlin/backend/jvm/NameableMfvcNode;", "(Ljava/util/List;)V", "allInnerUnboxMethods", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getAllInnerUnboxMethods", "()Ljava/util/List;", "allUnboxMethods", "getAllUnboxMethods", "boxMethod", "getBoxMethod", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "fields", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "getFields", "indices", "Lkotlin/ranges/IntRange;", "getIndices", "()Lkotlin/ranges/IntRange;", "leaves", "Lorg/jetbrains/kotlin/backend/jvm/LeafMfvcNode;", "getLeaves", "leavesUnboxMethods", "getLeavesUnboxMethods", "mapping", "", "Lorg/jetbrains/kotlin/name/Name;", "subnodeIndices", "getSubnodeIndices", "()Ljava/util/Map;", "getSubnodes", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "get", "name", "Lorg/jetbrains/kotlin/backend/jvm/IntermediateMfvcNode;", "Lorg/jetbrains/kotlin/backend/jvm/RootMfvcNode;", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class MfvcNodeWithSubnodes implements MfvcNode {
    private final List<IrSimpleFunction> allInnerUnboxMethods;
    private final List<IrField> fields;
    private final IntRange indices;
    private final List<LeafMfvcNode> leaves;
    private final Map<Name, NameableMfvcNode> mapping;
    private final Map<NameableMfvcNode, IntRange> subnodeIndices;
    private final List<NameableMfvcNode> subnodes;

    public /* synthetic */ MfvcNodeWithSubnodes(List list, DefaultConstructorMarker defaultConstructorMarker) {
        this(list);
    }

    public abstract List<IrSimpleFunction> getAllUnboxMethods();

    public abstract IrSimpleFunction getBoxMethod();

    public abstract List<IrSimpleFunction> getLeavesUnboxMethods();

    @Override // org.jetbrains.kotlin.backend.jvm.MfvcNode
    public abstract IrSimpleType getType();

    /* JADX WARN: Multi-variable type inference failed */
    private MfvcNodeWithSubnodes(List<? extends NameableMfvcNode> list) {
        List<IrSimpleFunction> listListOf;
        this.subnodes = list;
        if (list.isEmpty()) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        List<? extends NameableMfvcNode> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(CollectionsKt.dropLast(MfvcNodeKt.getNameParts((NameableMfvcNode) it.next()), 1));
        }
        if (!MfvcNodeKt.allEqual(arrayList)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        List<NameableMfvcNode> list3 = this.subnodes;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list3, 10)), 16));
        for (Object obj : list3) {
            linkedHashMap.put(MfvcNodeKt.getName((NameableMfvcNode) obj), obj);
        }
        if (linkedHashMap.size() == this.subnodes.size()) {
            this.mapping = linkedHashMap;
            this.leaves = MfvcNodeKt.getLeaves(this.subnodes);
            this.fields = MfvcNodeKt.getFields(this.subnodes);
            List<NameableMfvcNode> list4 = this.subnodes;
            ArrayList arrayList2 = new ArrayList();
            for (NameableMfvcNode nameableMfvcNode : list4) {
                if (nameableMfvcNode instanceof MfvcNodeWithSubnodes) {
                    listListOf = ((MfvcNodeWithSubnodes) nameableMfvcNode).getAllUnboxMethods();
                } else {
                    if (!(nameableMfvcNode instanceof LeafMfvcNode)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    listListOf = CollectionsKt.listOf(MfvcNodeKt.getUnboxMethod(nameableMfvcNode));
                }
                CollectionsKt.addAll(arrayList2, listListOf);
            }
            this.allInnerUnboxMethods = arrayList2;
            this.indices = CollectionsKt.getIndices(this.leaves);
            this.subnodeIndices = MfvcNodeKt.getSubnodeIndices(this.subnodes);
            return;
        }
        List<NameableMfvcNode> list5 = this.subnodes;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (Object obj2 : list5) {
            Name name2 = MfvcNodeKt.getName((NameableMfvcNode) obj2);
            Object obj3 = linkedHashMap2.get(name2);
            if (obj3 == null) {
                obj3 = (List) new ArrayList();
                linkedHashMap2.put(name2, obj3);
            }
            ((List) obj3).add(obj2);
        }
        LinkedHashMap linkedHashMap3 = new LinkedHashMap();
        for (Map.Entry entry : linkedHashMap2.entrySet()) {
            if (((List) entry.getValue()).size() > 1) {
                linkedHashMap3.put(entry.getKey(), entry.getValue());
            }
        }
        throw new IllegalArgumentException(CollectionsKt.joinToString$default(linkedHashMap3.entrySet(), null, "Repeating node names found: ", null, 0, null, new Function1<Map.Entry<? extends Name, ? extends List<? extends NameableMfvcNode>>, CharSequence>() { // from class: org.jetbrains.kotlin.backend.jvm.MfvcNodeWithSubnodes$mapping$2$1$3
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final CharSequence invoke2(Map.Entry<Name, ? extends List<? extends NameableMfvcNode>> entry2) {
                Intrinsics.checkNotNullParameter(entry2, "<name for destructuring parameter 0>");
                return entry2.getValue().size() + " nodes with name '" + entry2.getKey() + '\'';
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ CharSequence invoke(Map.Entry<? extends Name, ? extends List<? extends NameableMfvcNode>> entry2) {
                return invoke2((Map.Entry<Name, ? extends List<? extends NameableMfvcNode>>) entry2);
            }
        }, 29, null).toString());
    }

    public final List<NameableMfvcNode> getSubnodes() {
        return this.subnodes;
    }

    public final NameableMfvcNode get(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return this.mapping.get(name2);
    }

    public final List<LeafMfvcNode> getLeaves() {
        return this.leaves;
    }

    public final List<IrField> getFields() {
        return this.fields;
    }

    public final List<IrSimpleFunction> getAllInnerUnboxMethods() {
        return this.allInnerUnboxMethods;
    }

    public final IntRange getIndices() {
        return this.indices;
    }

    public final Map<NameableMfvcNode, IntRange> getSubnodeIndices() {
        return this.subnodeIndices;
    }
}
