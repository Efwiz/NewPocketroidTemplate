package org.jetbrains.kotlin.contracts.model;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: MutableContextInfo.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0018\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\b\u0002\u0018\u0000 -2\u00020\u0001:\u0001-B}\b\u0002\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0018\u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0006\u0012\u0018\u0010\n\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0006\u0012\u0018\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\b0\u0006\u0012\u0018\u0010\f\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\b0\u0006¢\u0006\u0002\u0010\rJ\u000e\u0010\u0015\u001a\u00020\u00002\u0006\u0010\u0016\u001a\u00020\u0000J\u0016\u0010\u0017\u001a\u00020\u00002\u0006\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u0007J\u000e\u0010\u001a\u001a\u00020\u00002\u0006\u0010\u001b\u001a\u00020\u0004J\u0016\u0010\u001c\u001a\u00020\u00002\u0006\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u0007J\u0016\u0010\u001d\u001a\u00020\u00002\u0006\u0010\u001e\u001a\u00020\u00072\u0006\u0010\u001f\u001a\u00020\tJ\u000e\u0010 \u001a\u00020\u00002\u0006\u0010\u0016\u001a\u00020\u0000J\u0006\u0010!\u001a\u00020\"J\u0016\u0010#\u001a\u00020\u00002\u0006\u0010\u001e\u001a\u00020\u00072\u0006\u0010\u001f\u001a\u00020\tJ9\u0010$\u001a\u00020%\"\u0004\b\u0000\u0010&*\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u0002H&0\b0\u00062\u0006\u0010'\u001a\u00020\u00072\u0006\u0010\u001e\u001a\u0002H&H\u0002¢\u0006\u0002\u0010(JP\u0010)\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u0002H&0\b0\u0006\"\u0004\b\u0000\u0010&*\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u0002H&0\b0\u00062\u0018\u0010*\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u0002H&0\b0\u0006H\u0002JP\u0010+\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u0002H&0\b0\u0006\"\u0004\b\u0000\u0010&*\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u0002H&0\b0,2\u0018\u0010*\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u0002H&0\b0,H\u0002R#\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\b0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R#\u0010\f\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\b0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR#\u0010\n\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000fR#\u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000f¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/contracts/model/MutableContextInfo;", "", "firedEffects", "", "Lorg/jetbrains/kotlin/contracts/model/ESEffect;", "subtypes", "", "Lorg/jetbrains/kotlin/contracts/model/ESValue;", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "notSubtypes", "equalValues", "notEqualValues", "(Ljava/util/List;Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;)V", "getEqualValues", "()Ljava/util/Map;", "getFiredEffects", "()Ljava/util/List;", "getNotEqualValues", "getNotSubtypes", "getSubtypes", "and", "other", "equal", "left", "right", "fire", "effect", "notEqual", "notSubtype", "value", ModuleXmlParser.TYPE, "or", "print", "", "subtype", "initAndAdd", "", "D", ASTExpr.DEFAULT_MAP_KEY_NAME, "(Ljava/util/Map;Lorg/jetbrains/kotlin/contracts/model/ESValue;Ljava/lang/Object;)V", "intersect", "that", SchemaSymbols.ATTVAL_UNION, "", "Companion", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MutableContextInfo {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Map<ESValue, Set<ESValue>> equalValues;
    private final List<ESEffect> firedEffects;
    private final Map<ESValue, Set<ESValue>> notEqualValues;
    private final Map<ESValue, Set<KotlinType>> notSubtypes;
    private final Map<ESValue, Set<KotlinType>> subtypes;

    public /* synthetic */ MutableContextInfo(List list, Map map, Map map2, Map map3, Map map4, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, map, map2, map3, map4);
    }

    private MutableContextInfo(List<ESEffect> list, Map<ESValue, Set<KotlinType>> map, Map<ESValue, Set<KotlinType>> map2, Map<ESValue, Set<ESValue>> map3, Map<ESValue, Set<ESValue>> map4) {
        this.firedEffects = list;
        this.subtypes = map;
        this.notSubtypes = map2;
        this.equalValues = map3;
        this.notEqualValues = map4;
    }

    public final List<ESEffect> getFiredEffects() {
        return this.firedEffects;
    }

    public final Map<ESValue, Set<KotlinType>> getSubtypes() {
        return this.subtypes;
    }

    public final Map<ESValue, Set<KotlinType>> getNotSubtypes() {
        return this.notSubtypes;
    }

    public final Map<ESValue, Set<ESValue>> getEqualValues() {
        return this.equalValues;
    }

    public final Map<ESValue, Set<ESValue>> getNotEqualValues() {
        return this.notEqualValues;
    }

    /* JADX INFO: compiled from: MutableContextInfo.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/contracts/model/MutableContextInfo$Companion;", "", "()V", "EMPTY", "Lorg/jetbrains/kotlin/contracts/model/MutableContextInfo;", "getEMPTY", "()Lorg/jetbrains/kotlin/contracts/model/MutableContextInfo;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final MutableContextInfo getEMPTY() {
            return new MutableContextInfo(new ArrayList(), new LinkedHashMap(), new LinkedHashMap(), new LinkedHashMap(), new LinkedHashMap(), null);
        }
    }

    public final MutableContextInfo subtype(ESValue value, KotlinType type) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(type, "type");
        initAndAdd(this.subtypes, value, type);
        return this;
    }

    public final MutableContextInfo notSubtype(ESValue value, KotlinType type) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(type, "type");
        initAndAdd(this.notSubtypes, value, type);
        return this;
    }

    public final MutableContextInfo equal(ESValue left, ESValue right) {
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        initAndAdd(this.equalValues, left, right);
        initAndAdd(this.equalValues, right, left);
        return this;
    }

    public final MutableContextInfo notEqual(ESValue left, ESValue right) {
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        initAndAdd(this.notEqualValues, left, right);
        initAndAdd(this.notEqualValues, right, left);
        return this;
    }

    public final MutableContextInfo fire(ESEffect effect) {
        Intrinsics.checkNotNullParameter(effect, "effect");
        this.firedEffects.add(effect);
        return this;
    }

    public final MutableContextInfo or(MutableContextInfo other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return new MutableContextInfo(CollectionsKt.toMutableList((Collection) CollectionsKt.intersect(this.firedEffects, other.firedEffects)), intersect(this.subtypes, other.subtypes), intersect(this.notSubtypes, other.notSubtypes), intersect(this.equalValues, other.equalValues), intersect(this.notEqualValues, other.notEqualValues));
    }

    public final MutableContextInfo and(MutableContextInfo other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return new MutableContextInfo(CollectionsKt.toMutableList((Collection) CollectionsKt.union(this.firedEffects, other.firedEffects)), union(this.subtypes, other.subtypes), union(this.notSubtypes, other.notSubtypes), union(this.equalValues, other.equalValues), union(this.notEqualValues, other.notEqualValues));
    }

    private final <D> Map<ESValue, Set<D>> intersect(Map<ESValue, Set<D>> map, Map<ESValue, Set<D>> map2) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (ESValue eSValue : CollectionsKt.intersect(map.keySet(), map2.keySet())) {
            Set<D> set = map.get(eSValue);
            Intrinsics.checkNotNull(set);
            Set<D> set2 = map2.get(eSValue);
            Intrinsics.checkNotNull(set2);
            Set setIntersect = CollectionsKt.intersect(set, set2);
            if (!setIntersect.isEmpty()) {
                linkedHashMap.put(eSValue, CollectionsKt.toMutableSet(setIntersect));
            }
        }
        return linkedHashMap;
    }

    private final <D> Map<ESValue, Set<D>> union(Map<ESValue, ? extends Set<D>> map, Map<ESValue, ? extends Set<D>> map2) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.putAll(map);
        Iterator<T> it = map2.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            ESValue eSValue = (ESValue) entry.getKey();
            Set set = (Set) entry.getValue();
            LinkedHashSet linkedHashSet = (Set) linkedHashMap.get(eSValue);
            if (linkedHashSet == null) {
                linkedHashSet = new LinkedHashSet();
            }
            linkedHashSet.addAll(set);
            linkedHashMap.put(eSValue, linkedHashSet);
        }
        return linkedHashMap;
    }

    private final <D> void initAndAdd(Map<ESValue, Set<D>> map, ESValue eSValue, final D d) {
        final Function2<ESValue, Set<D>, Set<D>> function2 = new Function2<ESValue, Set<D>, Set<D>>() { // from class: org.jetbrains.kotlin.contracts.model.MutableContextInfo.initAndAdd.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Set<D> invoke(ESValue eSValue2, Set<D> set) {
                Intrinsics.checkNotNullParameter(eSValue2, "<anonymous parameter 0>");
                if (set == null) {
                    set = new LinkedHashSet();
                }
                set.add(d);
                return set;
            }
        };
        map.compute(eSValue, new BiFunction() { // from class: org.jetbrains.kotlin.contracts.model.MutableContextInfo$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return MutableContextInfo.initAndAdd$lambda$7(function2, obj, obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Set initAndAdd$lambda$7(Function2 tmp0, Object obj, Object obj2) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (Set) tmp0.invoke(obj, obj2);
    }

    public final String print() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fired effects: ");
        sb.append(CollectionsKt.joinToString$default(this.firedEffects, ", ", null, null, 0, null, null, 62, null));
        StringBuilder sbAppend = sb.append("");
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        Intrinsics.checkNotNullExpressionValue(sbAppend.append('\n'), "append(...)");
        print$lambda$10$printMapEntriesWithSeparator(this.subtypes, sb, LanguageCodeUtil.IS);
        print$lambda$10$printMapEntriesWithSeparator(this.notSubtypes, sb, "!is");
        print$lambda$10$printMapEntriesWithSeparator(this.equalValues, sb, "==");
        print$lambda$10$printMapEntriesWithSeparator(this.notEqualValues, sb, "!=");
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    private static final <D> void print$lambda$10$printMapEntriesWithSeparator(Map<ESValue, ? extends Set<? extends D>> map, StringBuilder sb, String str) {
        Set<Map.Entry<ESValue, ? extends Set<? extends D>>> setEntrySet = map.entrySet();
        ArrayList<Map.Entry> arrayList = new ArrayList();
        for (Object obj : setEntrySet) {
            if (!((Collection) ((Map.Entry) obj).getValue()).isEmpty()) {
                arrayList.add(obj);
            }
        }
        for (Map.Entry entry : arrayList) {
            ESValue eSValue = (ESValue) entry.getKey();
            Set set = (Set) entry.getValue();
            sb.append(eSValue.toString());
            sb.append(" " + str + StringUtil.SPACE);
            StringBuilder sbAppend = sb.append(set.toString());
            Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
            Intrinsics.checkNotNullExpressionValue(sbAppend.append('\n'), "append(...)");
        }
    }
}
