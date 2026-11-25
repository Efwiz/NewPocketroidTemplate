package org.jetbrains.kotlin.metadata.jvm.deserialization;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.jvm.JvmModuleProtoBuf;

/* JADX INFO: compiled from: ModuleMapping.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010%\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0003J\u0018\u0010\u0018\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u00032\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003J\u000e\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u001c\u001a\u00020\u001dJ\u0013\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\u0010\u0010!\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0019\u001a\u00020\u0003J$\u0010\"\u001a\u00020#2\u0006\u0010\u001a\u001a\u00020\u00032\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020#0%H\u0002J\b\u0010&\u001a\u00020#H\u0016J\u0011\u0010'\u001a\u00020\u00162\u0006\u0010 \u001a\u00020\u0000H\u0086\u0002J\u000e\u0010(\u001a\u00020\u00162\u0006\u0010)\u001a\u00020\u0003J\b\u0010*\u001a\u00020\u0003H\u0016J \u0010+\u001a\u00020\u0016*\u00020,2\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020#0-H\u0002J6\u0010.\u001a\u00020\u0016*\u00020,2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00030/2\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020#0%2\u0006\u00100\u001a\u00020\u001dH\u0002J.\u00101\u001a\u00020\u0016*\u00020,2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00030/2\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020#0%H\u0002R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR.\u0010\u000b\u001a\"\u0012\u0004\u0012\u00020\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00030\fj\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u0003`\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00030\u00068F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\bR\u0018\u0010\u0010\u001a\u00020\u0003*\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0018\u0010\u0013\u001a\u00020\u0003*\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0012¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/metadata/jvm/deserialization/PackageParts;", "", "packageFqName", "", "(Ljava/lang/String;)V", "metadataParts", "", "getMetadataParts", "()Ljava/util/Set;", "getPackageFqName", "()Ljava/lang/String;", "packageParts", "Ljava/util/LinkedHashMap;", "Lkotlin/collections/LinkedHashMap;", "parts", "getParts", "className", "getClassName", "(Ljava/lang/String;)Ljava/lang/String;", "packageName", "getPackageName", "addMetadataPart", "", "shortName", "addPart", "partInternalName", "facadeInternalName", "addTo", "builder", "Lorg/jetbrains/kotlin/metadata/jvm/JvmModuleProtoBuf$Module$Builder;", Namer.EQUALS_METHOD_NAME, "", "other", "getMultifileFacadeName", "getMultifileFacadeShortNameId", "", "facadeNameToId", "", "hashCode", "plusAssign", "removePart", "internalName", "toString", "writeMultifileFacadeNames", "Lorg/jetbrains/kotlin/metadata/jvm/JvmModuleProtoBuf$PackageParts$Builder;", "", "writePartsOutsidePackage", "", "packageTableBuilder", "writePartsWithinPackage", "metadata.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PackageParts {
    private final Set<String> metadataParts;
    private final String packageFqName;
    private final LinkedHashMap<String, String> packageParts;

    public PackageParts(String packageFqName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        this.packageFqName = packageFqName;
        this.packageParts = new LinkedHashMap<>();
        this.metadataParts = new LinkedHashSet();
    }

    public final String getPackageFqName() {
        return this.packageFqName;
    }

    public final Set<String> getParts() {
        Set<String> setKeySet = this.packageParts.keySet();
        Intrinsics.checkNotNullExpressionValue(setKeySet, "<get-keys>(...)");
        return setKeySet;
    }

    public final Set<String> getMetadataParts() {
        return this.metadataParts;
    }

    public final void addPart(String partInternalName, String facadeInternalName) {
        Intrinsics.checkNotNullParameter(partInternalName, "partInternalName");
        this.packageParts.put(partInternalName, facadeInternalName);
    }

    public final void removePart(String internalName) {
        Intrinsics.checkNotNullParameter(internalName, "internalName");
        this.packageParts.remove(internalName);
    }

    public final void addMetadataPart(String shortName) {
        Intrinsics.checkNotNullParameter(shortName, "shortName");
        Set<String> set = this.metadataParts;
        Intrinsics.checkNotNull(set, "null cannot be cast to non-null type kotlin.collections.MutableSet<kotlin.String>");
        TypeIntrinsics.asMutableSet(set).add(shortName);
    }

    public final void addTo(JvmModuleProtoBuf.Module.Builder builder) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        if (!getParts().isEmpty()) {
            JvmModuleProtoBuf.PackageParts.Builder builderNewBuilder = JvmModuleProtoBuf.PackageParts.newBuilder();
            builderNewBuilder.setPackageFqName(this.packageFqName);
            String packageFqName = builderNewBuilder.getPackageFqName();
            Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
            String strReplace$default = StringsKt.replace$default(packageFqName, '.', '/', false, 4, (Object) null);
            Set<String> parts = getParts();
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : parts) {
                if (Intrinsics.areEqual(getPackageName((String) obj), strReplace$default)) {
                    arrayList.add(obj);
                } else {
                    arrayList2.add(obj);
                }
            }
            Pair pair = new Pair(arrayList, arrayList2);
            List<String> list = (List) pair.component1();
            List<String> list2 = (List) pair.component2();
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            Intrinsics.checkNotNull(builderNewBuilder);
            writePartsWithinPackage(builderNewBuilder, list, linkedHashMap);
            writePartsOutsidePackage(builderNewBuilder, list2, linkedHashMap, builder);
            writeMultifileFacadeNames(builderNewBuilder, linkedHashMap);
            builder.addPackageParts(builderNewBuilder);
        }
        if (this.metadataParts.isEmpty()) {
            return;
        }
        JvmModuleProtoBuf.PackageParts.Builder builderNewBuilder2 = JvmModuleProtoBuf.PackageParts.newBuilder();
        builderNewBuilder2.setPackageFqName(this.packageFqName);
        builderNewBuilder2.addAllShortClassName(CollectionsKt.sorted(this.metadataParts));
        builder.addMetadataParts(builderNewBuilder2);
    }

    private final void writePartsWithinPackage(JvmModuleProtoBuf.PackageParts.Builder builder, List<String> list, Map<String, Integer> map) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : list) {
            String multifileFacadeName = getMultifileFacadeName((String) obj);
            Object obj2 = linkedHashMap.get(multifileFacadeName);
            if (obj2 == null) {
                obj2 = (List) new ArrayList();
                linkedHashMap.put(multifileFacadeName, obj2);
            }
            ((List) obj2).add(obj);
        }
        for (Map.Entry entry : MapsKt.toSortedMap(linkedHashMap, ComparisonsKt.nullsLast(ComparisonsKt.naturalOrder())).entrySet()) {
            String str = (String) entry.getKey();
            List list2 = (List) entry.getValue();
            Intrinsics.checkNotNull(list2);
            Iterator it = CollectionsKt.sorted(list2).iterator();
            while (it.hasNext()) {
                builder.addShortClassName(getClassName((String) it.next()));
                if (str != null) {
                    builder.addMultifileFacadeShortNameId(getMultifileFacadeShortNameId(str, map));
                }
            }
        }
    }

    private final void writePartsOutsidePackage(JvmModuleProtoBuf.PackageParts.Builder builder, List<String> list, Map<String, Integer> map, JvmModuleProtoBuf.Module.Builder builder2) {
        ArrayList arrayList = new ArrayList();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : list) {
            String packageName = getPackageName((String) obj);
            Object obj2 = linkedHashMap.get(packageName);
            if (obj2 == null) {
                obj2 = (List) new ArrayList();
                linkedHashMap.put(packageName, obj2);
            }
            ((List) obj2).add(obj);
        }
        for (Map.Entry entry : MapsKt.toSortedMap(linkedHashMap).entrySet()) {
            String str = (String) entry.getKey();
            List list2 = (List) entry.getValue();
            Intrinsics.checkNotNull(str);
            String strReplace$default = StringsKt.replace$default(str, '/', '.', false, 4, (Object) null);
            if (!builder2.getJvmPackageNameList().contains(strReplace$default)) {
                builder2.addJvmPackageName(strReplace$default);
            }
            int iIndexOf = builder2.getJvmPackageNameList().indexOf(strReplace$default);
            Intrinsics.checkNotNull(list2);
            LinkedHashMap linkedHashMap2 = new LinkedHashMap();
            for (Object obj3 : list2) {
                String multifileFacadeName = getMultifileFacadeName((String) obj3);
                Object obj4 = linkedHashMap2.get(multifileFacadeName);
                if (obj4 == null) {
                    obj4 = (List) new ArrayList();
                    linkedHashMap2.put(multifileFacadeName, obj4);
                }
                ((List) obj4).add(obj3);
            }
            for (Map.Entry entry2 : MapsKt.toSortedMap(linkedHashMap2, ComparisonsKt.nullsLast(ComparisonsKt.naturalOrder())).entrySet()) {
                String str2 = (String) entry2.getKey();
                List list3 = (List) entry2.getValue();
                Intrinsics.checkNotNull(list3);
                Iterator it = CollectionsKt.sorted(list3).iterator();
                while (it.hasNext()) {
                    builder.addClassWithJvmPackageNameShortName(getClassName((String) it.next()));
                    if (str2 != null) {
                        builder.addClassWithJvmPackageNameMultifileFacadeShortNameId(getMultifileFacadeShortNameId(str2, map));
                    }
                    arrayList.add(Integer.valueOf(iIndexOf));
                }
            }
        }
        while (arrayList.size() > 1 && ((Number) arrayList.get(arrayList.size() - 1)).intValue() == ((Number) arrayList.get(arrayList.size() - 2)).intValue()) {
            arrayList.remove(arrayList.size() - 1);
        }
        builder.addAllClassWithJvmPackageNamePackageId(arrayList);
    }

    private final int getMultifileFacadeShortNameId(String facadeInternalName, Map<String, Integer> facadeNameToId) {
        String className = getClassName(facadeInternalName);
        Integer numValueOf = facadeNameToId.get(className);
        if (numValueOf == null) {
            numValueOf = Integer.valueOf(facadeNameToId.size());
            facadeNameToId.put(className, numValueOf);
        }
        return numValueOf.intValue() + 1;
    }

    private final void writeMultifileFacadeNames(JvmModuleProtoBuf.PackageParts.Builder builder, Map<String, Integer> map) {
        for (Pair pair : CollectionsKt.sortedWith(CollectionsKt.zip(map.values(), map.keySet()), new Comparator() { // from class: org.jetbrains.kotlin.metadata.jvm.deserialization.PackageParts$writeMultifileFacadeNames$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(Integer.valueOf(((Number) ((Pair) t).getFirst()).intValue()), Integer.valueOf(((Number) ((Pair) t2).getFirst()).intValue()));
            }
        })) {
            ((Number) pair.component1()).intValue();
            String str = (String) pair.component2();
            builder.getMultifileFacadeShortNameCount();
            builder.addMultifileFacadeShortName(str);
        }
    }

    private final String getPackageName(String str) {
        return StringsKt.substringBeforeLast(str, '/', "");
    }

    private final String getClassName(String str) {
        return StringsKt.substringAfterLast$default(str, '/', (String) null, 2, (Object) null);
    }

    public final String getMultifileFacadeName(String partInternalName) {
        Intrinsics.checkNotNullParameter(partInternalName, "partInternalName");
        return this.packageParts.get(partInternalName);
    }

    public final void plusAssign(PackageParts other) {
        Intrinsics.checkNotNullParameter(other, "other");
        for (Map.Entry<String, String> entry : other.packageParts.entrySet()) {
            addPart(entry.getKey(), entry.getValue());
        }
        Iterator<T> it = other.metadataParts.iterator();
        while (it.hasNext()) {
            addMetadataPart((String) it.next());
        }
    }

    public boolean equals(Object other) {
        if (other instanceof PackageParts) {
            PackageParts packageParts = (PackageParts) other;
            if (Intrinsics.areEqual(packageParts.packageFqName, this.packageFqName) && Intrinsics.areEqual(packageParts.packageParts, this.packageParts) && Intrinsics.areEqual(packageParts.metadataParts, this.metadataParts)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (((this.packageFqName.hashCode() * 31) + this.packageParts.hashCode()) * 31) + this.metadataParts.hashCode();
    }

    public String toString() {
        return SetsKt.plus((Set) getParts(), (Iterable) this.metadataParts).toString();
    }
}
