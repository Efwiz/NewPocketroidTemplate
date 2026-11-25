package org.jetbrains.kotlin.incremental;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolverKt;

/* JADX INFO: compiled from: AbiSnapshotDiffService.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/incremental/AbiSnapshotDiffService;", "", "()V", "Companion", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AbiSnapshotDiffService {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Map<Pair<AbiSnapshot, AbiSnapshot>, DirtyData> diffCache = new LinkedHashMap();

    /* JADX INFO: compiled from: AbiSnapshotDiffService.kt */
    @Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J0\u0010\b\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\t0\u00052\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\nH\u0002J\u001e\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u0013J,\u0010\u0014\u001a\u00020\u00072\u0006\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018J\u001c\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u000e\u001a\u00020\n2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018R&\u0010\u0003\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/incremental/AbiSnapshotDiffService$Companion;", "", "()V", "diffCache", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/incremental/AbiSnapshot;", "Lorg/jetbrains/kotlin/incremental/DirtyData;", "addProtoInfo", "", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/incremental/LookupSymbol;", "protoData", "Lorg/jetbrains/kotlin/incremental/ProtoData;", "fqName", "compareJarsInternal", "oldSnapshot", "newSnapshot", "caches", "Lorg/jetbrains/kotlin/incremental/IncrementalCacheCommon;", "doCompute", "snapshot", "actual", "scopes", "", "", "inScope", "", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final DirtyData compareJarsInternal$lambda$0(Function1 tmp0, Object obj) {
            Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
            return (DirtyData) tmp0.invoke(obj);
        }

        public final DirtyData compareJarsInternal(AbiSnapshot oldSnapshot, AbiSnapshot newSnapshot, final IncrementalCacheCommon caches) {
            Intrinsics.checkNotNullParameter(oldSnapshot, "oldSnapshot");
            Intrinsics.checkNotNullParameter(newSnapshot, "newSnapshot");
            Intrinsics.checkNotNullParameter(caches, "caches");
            Map map = AbiSnapshotDiffService.diffCache;
            Pair pair = new Pair(oldSnapshot, newSnapshot);
            final Function1<Pair<? extends AbiSnapshot, ? extends AbiSnapshot>, DirtyData> function1 = new Function1<Pair<? extends AbiSnapshot, ? extends AbiSnapshot>, DirtyData>() { // from class: org.jetbrains.kotlin.incremental.AbiSnapshotDiffService$Companion$compareJarsInternal$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final DirtyData invoke(Pair<? extends AbiSnapshot, ? extends AbiSnapshot> pair2) {
                    Intrinsics.checkNotNullParameter(pair2, "<name for destructuring parameter 0>");
                    return AbiSnapshotDiffService.INSTANCE.doCompute(pair2.component1(), pair2.component2(), caches, CollectionsKt.emptyList());
                }
            };
            Object objComputeIfAbsent = map.computeIfAbsent(pair, new Function() { // from class: org.jetbrains.kotlin.incremental.AbiSnapshotDiffService$Companion$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return AbiSnapshotDiffService.Companion.compareJarsInternal$lambda$0(function1, obj);
                }
            });
            Intrinsics.checkNotNullExpressionValue(objComputeIfAbsent, "computeIfAbsent(...)");
            return (DirtyData) objComputeIfAbsent;
        }

        public final boolean inScope(FqName fqName, Collection<String> scopes) {
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            Intrinsics.checkNotNullParameter(scopes, "scopes");
            Collection<String> collection = scopes;
            if (collection.isEmpty()) {
                return false;
            }
            for (String str : collection) {
                String string = fqName.toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                if (StringsKt.startsWith$default(string, str, false, 2, (Object) null)) {
                    return true;
                }
            }
            return false;
        }

        public final DirtyData doCompute(AbiSnapshot snapshot, AbiSnapshot actual, IncrementalCacheCommon caches, Collection<String> scopes) {
            Companion companion = this;
            Intrinsics.checkNotNullParameter(snapshot, "snapshot");
            Intrinsics.checkNotNullParameter(actual, "actual");
            Intrinsics.checkNotNullParameter(caches, "caches");
            Intrinsics.checkNotNullParameter(scopes, "scopes");
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (Map.Entry<FqName, ProtoData> entry : snapshot.getProtos().entrySet()) {
                FqName key = entry.getKey();
                ProtoData value = entry.getValue();
                if (companion.inScope(key, scopes)) {
                    ProtoData protoData = actual.getProtos().get(key);
                    if (protoData == null) {
                        Pair<List<FqName>, List<LookupSymbol>> pairAddProtoInfo = companion.addProtoInfo(value, key);
                        List<FqName> listComponent1 = pairAddProtoInfo.component1();
                        List<LookupSymbol> listComponent2 = pairAddProtoInfo.component2();
                        arrayList.addAll(listComponent1);
                        arrayList2.addAll(listComponent2);
                    } else if ((value instanceof ClassProtoData) && (protoData instanceof ClassProtoData)) {
                        ClassProtoData classProtoData = (ClassProtoData) value;
                        ClassProtoData classProtoData2 = (ClassProtoData) protoData;
                        new ProtoCompareGenerated(classProtoData.getNameResolver(), classProtoData2.getNameResolver(), classProtoData.getProto().getTypeTable(), classProtoData2.getProto().getTypeTable());
                        Difference difference = new DifferenceCalculatorForClass(classProtoData, classProtoData2).difference();
                        if (difference.isClassAffected()) {
                            arrayList.add(key);
                            key.isRoot();
                            String strAsString = key.parent().asString();
                            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                            String identifier = key.shortName().getIdentifier();
                            Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
                            arrayList2.add(new LookupSymbol(identifier, strAsString));
                        }
                        for (String str : difference.getChangedMembersNames()) {
                            Set<FqName> setWithSubtypes = BuildUtilKt.withSubtypes(key, CollectionsKt.listOf(caches));
                            arrayList.addAll(setWithSubtypes);
                            for (FqName fqName : setWithSubtypes) {
                                String strAsString2 = fqName.asString();
                                Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
                                arrayList2.add(new LookupSymbol(str, strAsString2));
                                String strAsString3 = SamConversionResolverKt.getSAM_LOOKUP_NAME().asString();
                                Intrinsics.checkNotNullExpressionValue(strAsString3, "asString(...)");
                                String strAsString4 = fqName.asString();
                                Intrinsics.checkNotNullExpressionValue(strAsString4, "asString(...)");
                                arrayList2.add(new LookupSymbol(strAsString3, strAsString4));
                            }
                        }
                    } else if ((value instanceof PackagePartProtoData) && (protoData instanceof PackagePartProtoData)) {
                        for (String str2 : new DifferenceCalculatorForPackageFacade((PackagePartProtoData) value, (PackagePartProtoData) protoData).difference().getChangedMembersNames()) {
                            String strAsString5 = key.asString();
                            Intrinsics.checkNotNullExpressionValue(strAsString5, "asString(...)");
                            arrayList2.add(new LookupSymbol(str2, strAsString5));
                        }
                    } else {
                        throw new IllegalStateException("package proto and class proto have the same fqName: " + key);
                    }
                }
                companion = this;
            }
            ArrayList arrayList3 = arrayList2;
            ArrayList arrayList4 = arrayList;
            new DirtyData(arrayList3, arrayList4, null, 4, null);
            Set<FqName> setKeySet = snapshot.getProtos().keySet();
            Set<FqName> setKeySet2 = actual.getProtos().keySet();
            ArrayList arrayList5 = new ArrayList();
            for (Object obj : setKeySet2) {
                if (!setKeySet.contains((FqName) obj)) {
                    arrayList5.add(obj);
                }
            }
            arrayList.addAll(arrayList5);
            return new DirtyData(arrayList3, arrayList4, null, 4, null);
        }

        private final Pair<List<FqName>, List<LookupSymbol>> addProtoInfo(ProtoData protoData, FqName fqName) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            if (protoData instanceof ClassProtoData) {
                arrayList.add(fqName);
                List<String> nonPrivateMembers = DifferenceCalculatorForClass.INSTANCE.getNonPrivateMembers((ClassProtoData) protoData);
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(nonPrivateMembers, 10));
                for (String str : nonPrivateMembers) {
                    String strAsString = fqName.asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                    arrayList3.add(new LookupSymbol(str, strAsString));
                }
                arrayList2.addAll(arrayList3);
            } else if (protoData instanceof PackagePartProtoData) {
                PackagePartProtoData packagePartProtoData = (PackagePartProtoData) protoData;
                List<? extends ProtoBuf.FunctionOrBuilder> functionOrBuilderList = packagePartProtoData.getProto().getFunctionOrBuilderList();
                Intrinsics.checkNotNullExpressionValue(functionOrBuilderList, "getFunctionOrBuilderList(...)");
                ArrayList arrayList4 = new ArrayList();
                for (Object obj : functionOrBuilderList) {
                    if (Flags.VISIBILITY.get(((ProtoBuf.FunctionOrBuilder) obj).getFlags()) != ProtoBuf.Visibility.PRIVATE) {
                        arrayList4.add(obj);
                    }
                }
                ArrayList arrayList5 = arrayList4;
                ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList5, 10));
                Iterator it = arrayList5.iterator();
                while (it.hasNext()) {
                    String string = packagePartProtoData.getNameResolver().getString(((ProtoBuf.FunctionOrBuilder) it.next()).getName());
                    String strAsString2 = fqName.asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
                    arrayList6.add(new LookupSymbol(string, strAsString2));
                }
                arrayList2.addAll(CollectionsKt.toSet(arrayList6));
            }
            return new Pair<>(arrayList, arrayList2);
        }
    }
}
