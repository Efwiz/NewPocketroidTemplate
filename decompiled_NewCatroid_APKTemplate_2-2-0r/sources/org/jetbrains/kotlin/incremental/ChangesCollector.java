package org.jetbrains.kotlin.incremental;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.protobuf.MessageLite;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;

/* JADX INFO: compiled from: ChangesCollector.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u009a\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00052\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00050\u0016H\u0002J\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018J\u0018\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u00020\nH\u0002J\u001e\u0010\u001d\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u00052\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\n0\u0016H\u0002J*\u0010\u001f\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u00020\n2\b\u0010 \u001a\u0004\u0018\u00010\u00012\b\u0010!\u001a\u0004\u0018\u00010\u0001J0\u0010\"\u001a\u00020\u00132\b\u0010#\u001a\u0004\u0018\u00010\u00112\b\u0010$\u001a\u0004\u0018\u00010\u00112\b\b\u0002\u0010%\u001a\u00020\u00062\n\b\u0002\u0010&\u001a\u0004\u0018\u00010\nJ\u0018\u0010'\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u00020\nH\u0002J\u001e\u0010(\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u00052\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\n0\u0016H\u0002J\u0018\u0010)\u001a\u00020\u00132\u0006\u0010*\u001a\u00020+2\u0006\u0010\u0003\u001a\u00020\u0006H\u0002J\u0016\u0010)\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0006J\u0012\u0010,\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00110-J\f\u0010.\u001a\b\u0012\u0004\u0012\u00020\u00050\u0018J&\u0010/\u001a\u00020\u0013*\u00020\u00112\u0006\u00100\u001a\u00020\u00062\u0006\u00101\u001a\u00020\u00062\b\b\u0002\u0010%\u001a\u00020\u0006H\u0002J&\u00102\u001a\u00020\u0013*\u00020+2\u0006\u00100\u001a\u00020\u00062\u0006\u00101\u001a\u00020\u00062\b\b\u0002\u0010%\u001a\u00020\u0006H\u0002J\u0014\u00103\u001a\u00020\u0013*\u0002042\u0006\u00100\u001a\u00020\u0006H\u0002J\"\u00105\u001a\u00020\u0013*\u00020+2\u0006\u0010\u0014\u001a\u00020\u00052\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002060\u0016H\u0002Je\u00107\u001a\b\u0012\u0004\u0012\u00020\n08\"\u0004\b\u0000\u00109*\u0002H92\u0006\u0010:\u001a\u00020;2@\u0010<\u001a!\u0012\u001d\b\u0001\u0012\u0019\u0012\u0004\u0012\u0002H9\u0012\n\u0012\b\u0012\u0004\u0012\u00020?0\u00180>¢\u0006\u0002\b@0=\"\u0019\u0012\u0004\u0012\u0002H9\u0012\n\u0012\b\u0012\u0004\u0012\u00020?0\u00180>¢\u0006\u0002\b@¢\u0006\u0002\u0010AJ=\u0010B\u001a\b\u0012\u0004\u0012\u0002HC0\t\"\u0004\b\u0000\u00109\"\u0004\b\u0001\u0010C*\u0014\u0012\u0004\u0012\u0002H9\u0012\n\u0012\b\u0012\u0004\u0012\u0002HC0\t0D2\u0006\u0010E\u001a\u0002H9H\u0002¢\u0006\u0002\u0010FR*\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R6\u0010\b\u001a*\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0004j\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R6\u0010\u000b\u001a*\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\u0004j\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\f\u001a\u0012\u0012\u0004\u0012\u00020\u00050\rj\b\u0012\u0004\u0012\u00020\u0005`\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R6\u0010\u000f\u001a*\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0004j\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u0010\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00110\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0011`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006G"}, d2 = {"Lorg/jetbrains/kotlin/incremental/ChangesCollector;", "", "()V", "areSubclassesAffected", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/name/FqName;", "", "Lkotlin/collections/HashMap;", "changedMembers", "", "", "changedParents", "removed", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "removedMembers", "storage", "Lorg/jetbrains/kotlin/incremental/ProtoData;", "addChangedParents", "", "fqName", "parents", "", "changes", "", "Lorg/jetbrains/kotlin/incremental/ChangeInfo;", "collectChangedMember", "scope", "name", "collectChangedMembers", "names", "collectMemberIfValueWasChanged", "oldValue", "newValue", "collectProtoChanges", "oldData", "newData", "collectAllMembersForNewClass", "packageProtoKey", "collectRemovedMember", "collectRemovedMembers", "collectSignature", "classData", "Lorg/jetbrains/kotlin/incremental/ClassProtoData;", "protoDataChanges", "", "protoDataRemoved", "collectAll", "isRemoved", "isAdded", "collectAllFromClass", "collectAllFromPackage", "Lorg/jetbrains/kotlin/incremental/PackagePartProtoData;", "collectChangedParents", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "getNonPrivateNames", "", ExifInterface.GPS_DIRECTION_TRUE, "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "members", "", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/protobuf/MessageLite;", "Lkotlin/ExtensionFunctionType;", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;[Lkotlin/jvm/functions/Function1;)Ljava/util/Set;", "getSet", "R", "", ASTExpr.DEFAULT_MAP_KEY_NAME, "(Ljava/util/Map;Ljava/lang/Object;)Ljava/util/Set;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ChangesCollector {
    private final HashMap<FqName, Set<String>> removedMembers = new HashMap<>();
    private final HashMap<FqName, Set<FqName>> changedParents = new HashMap<>();
    private final HashMap<FqName, Set<String>> changedMembers = new HashMap<>();
    private final HashMap<FqName, Boolean> areSubclassesAffected = new HashMap<>();
    private final HashMap<FqName, ProtoData> storage = new HashMap<>();
    private final ArrayList<FqName> removed = new ArrayList<>();

    public final Map<FqName, ProtoData> protoDataChanges() {
        return this.storage;
    }

    public final List<FqName> protoDataRemoved() {
        return this.removed;
    }

    public final List<ChangeInfo> changes() {
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<FqName, Set<String>> entry : this.removedMembers.entrySet()) {
            FqName key = entry.getKey();
            Set<String> value = entry.getValue();
            if (!value.isEmpty()) {
                arrayList.add(new ChangeInfo.Removed(key, value));
            }
        }
        for (Map.Entry<FqName, Set<String>> entry2 : this.changedMembers.entrySet()) {
            FqName key2 = entry2.getKey();
            Set<String> value2 = entry2.getValue();
            if (!value2.isEmpty()) {
                arrayList.add(new ChangeInfo.MembersChanged(key2, value2));
            }
        }
        for (Map.Entry<FqName, Boolean> entry3 : this.areSubclassesAffected.entrySet()) {
            arrayList.add(new ChangeInfo.SignatureChanged(entry3.getKey(), entry3.getValue().booleanValue()));
        }
        for (Map.Entry<FqName, Set<FqName>> entry4 : this.changedParents.entrySet()) {
            arrayList.add(new ChangeInfo.ParentsChanged(entry4.getKey(), entry4.getValue()));
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void collectChangedMember(FqName scope, String name2) {
        getSet(this.changedMembers, scope).add(name2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void collectRemovedMember(FqName scope, String name2) {
        getSet(this.removedMembers, scope).add(name2);
    }

    private final void collectChangedMembers(FqName scope, Collection<String> names) {
        if (names.isEmpty()) {
            return;
        }
        getSet(this.changedMembers, scope).addAll(names);
    }

    private final void collectRemovedMembers(FqName scope, Collection<String> names) {
        if (names.isEmpty()) {
            return;
        }
        getSet(this.removedMembers, scope).addAll(names);
    }

    public static /* synthetic */ void collectProtoChanges$default(ChangesCollector changesCollector, ProtoData protoData, ProtoData protoData2, boolean z, String str, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        if ((i & 8) != 0) {
            str = null;
        }
        changesCollector.collectProtoChanges(protoData, protoData2, z, str);
    }

    public final void collectProtoChanges(ProtoData oldData, ProtoData newData, boolean collectAllMembersForNewClass, String packageProtoKey) {
        if (oldData == null && newData == null) {
            throw new IllegalStateException("Old and new value are null");
        }
        if (newData != null) {
            if (newData instanceof ClassProtoData) {
                ClassProtoData classProtoData = (ClassProtoData) newData;
                FqName fqNameAsSingleFqName = NameResolverUtilKt.getClassId(classProtoData.getNameResolver(), classProtoData.getProto().getFqName()).asSingleFqName();
                Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
                this.storage.put(fqNameAsSingleFqName, newData);
            } else if (newData instanceof PackagePartProtoData) {
                FqName packageFqName = ((PackagePartProtoData) newData).getPackageFqName();
                HashMap<FqName, ProtoData> map = this.storage;
                if (packageProtoKey != null) {
                    packageFqName = new FqName(packageProtoKey);
                }
                map.put(packageFqName, newData);
            }
        } else if (oldData != null) {
            if (oldData instanceof ClassProtoData) {
                ClassProtoData classProtoData2 = (ClassProtoData) oldData;
                this.removed.add(NameResolverUtilKt.getClassId(classProtoData2.getNameResolver(), classProtoData2.getProto().getFqName()).asSingleFqName());
            } else if (oldData instanceof PackagePartProtoData) {
                this.removed.add(packageProtoKey != null ? new FqName(packageProtoKey) : ((PackagePartProtoData) oldData).getPackageFqName());
            }
        }
        if (oldData == null) {
            Intrinsics.checkNotNull(newData);
            collectAll(newData, false, true, collectAllMembersForNewClass);
            return;
        }
        if (newData == null) {
            collectAll$default(this, oldData, true, false, false, 4, null);
            return;
        }
        if (oldData instanceof ClassProtoData) {
            if (newData instanceof ClassProtoData) {
                ClassProtoData classProtoData3 = (ClassProtoData) oldData;
                FqName fqNameAsSingleFqName2 = NameResolverUtilKt.getClassId(classProtoData3.getNameResolver(), classProtoData3.getProto().getFqName()).asSingleFqName();
                Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName2, "asSingleFqName(...)");
                Difference difference = new DifferenceCalculatorForClass(classProtoData3, (ClassProtoData) newData).difference();
                if (difference.isClassAffected()) {
                    collectSignature(classProtoData3, difference.getAreSubclassesAffected());
                }
                collectChangedMembers(fqNameAsSingleFqName2, difference.getChangedMembersNames());
                addChangedParents(fqNameAsSingleFqName2, difference.getChangedSupertypes());
                return;
            }
            if (newData instanceof PackagePartProtoData) {
                collectSignature((ClassProtoData) oldData, true);
                return;
            }
            return;
        }
        if (oldData instanceof PackagePartProtoData) {
            if (newData instanceof ClassProtoData) {
                collectSignature((ClassProtoData) newData, false);
            } else if (newData instanceof PackagePartProtoData) {
                PackagePartProtoData packagePartProtoData = (PackagePartProtoData) oldData;
                collectChangedMembers(packagePartProtoData.getPackageFqName(), new DifferenceCalculatorForPackageFacade(packagePartProtoData, (PackagePartProtoData) newData).difference().getChangedMembersNames());
            }
        }
    }

    static /* synthetic */ void collectAll$default(ChangesCollector changesCollector, ProtoData protoData, boolean z, boolean z2, boolean z3, int i, Object obj) {
        if ((i & 4) != 0) {
            z3 = false;
        }
        changesCollector.collectAll(protoData, z, z2, z3);
    }

    private final void collectAll(ProtoData protoData, boolean z, boolean z2, boolean z3) {
        if (protoData instanceof PackagePartProtoData) {
            collectAllFromPackage((PackagePartProtoData) protoData, z);
        } else {
            if (!(protoData instanceof ClassProtoData)) {
                throw new NoWhenBranchMatchedException();
            }
            collectAllFromClass((ClassProtoData) protoData, z, z2, z3);
        }
    }

    private final void collectAllFromPackage(PackagePartProtoData packagePartProtoData, boolean z) {
        List<String> nonPrivateMembers = DifferenceCalculatorForPackageFacade.INSTANCE.getNonPrivateMembers(packagePartProtoData);
        if (z) {
            collectRemovedMembers(packagePartProtoData.getPackageFqName(), nonPrivateMembers);
        } else {
            collectChangedMembers(packagePartProtoData.getPackageFqName(), nonPrivateMembers);
        }
    }

    private final void collectAllFromClass(ClassProtoData classProtoData, boolean z, boolean z2, boolean z3) {
        FqName fqNameAsSingleFqName = NameResolverUtilKt.getClassId(classProtoData.getNameResolver(), classProtoData.getProto().getFqName()).asSingleFqName();
        Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
        if (ProtoDifferenceUtilsKt.isCompanionObject(classProtoData.getProto())) {
            List<String> nonPrivateMembers = DifferenceCalculatorForClass.INSTANCE.getNonPrivateMembers(classProtoData);
            Function2 function2 = (Function2) (z ? new ChangesCollector$collectAllFromClass$collectMember$1(this) : new ChangesCollector$collectAllFromClass$collectMember$2(this));
            FqName fqNameParent = fqNameAsSingleFqName.parent();
            Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
            String strAsString = fqNameAsSingleFqName.shortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            function2.invoke(fqNameParent, strAsString);
            Iterator<T> it = nonPrivateMembers.iterator();
            while (it.hasNext()) {
                function2.invoke(fqNameAsSingleFqName, (String) it.next());
            }
        } else {
            if (!z && z3) {
                Iterator<T> it2 = DifferenceCalculatorForClass.INSTANCE.getNonPrivateMembers(classProtoData).iterator();
                while (it2.hasNext()) {
                    collectChangedMember(fqNameAsSingleFqName, (String) it2.next());
                }
            }
            collectSignature(fqNameAsSingleFqName, true);
        }
        if (z || z2) {
            List<ProtoBuf.Type> supertypeList = classProtoData.getProto().getSupertypeList();
            Intrinsics.checkNotNullExpressionValue(supertypeList, "getSupertypeList(...)");
            collectChangedParents(classProtoData, fqNameAsSingleFqName, supertypeList);
        }
    }

    private final void addChangedParents(FqName fqName, Collection<FqName> parents) {
        if (parents.isEmpty()) {
            return;
        }
        HashMap<FqName, Set<FqName>> map = this.changedParents;
        HashSet hashSet = map.get(fqName);
        if (hashSet == null) {
            hashSet = new HashSet();
            map.put(fqName, hashSet);
        }
        hashSet.addAll(parents);
    }

    private final void collectChangedParents(ClassProtoData classProtoData, FqName fqName, Collection<ProtoBuf.Type> collection) {
        Collection<ProtoBuf.Type> collection2 = collection;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection2, 10));
        Iterator<T> it = collection2.iterator();
        while (it.hasNext()) {
            arrayList.add(NameResolverUtilKt.getClassId(classProtoData.getNameResolver(), ((ProtoBuf.Type) it.next()).getClassName()).asSingleFqName());
        }
        addChangedParents(fqName, arrayList);
    }

    public final void collectMemberIfValueWasChanged(FqName scope, String name2, Object oldValue, Object newValue) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(name2, "name");
        if (oldValue == null && newValue == null) {
            throw new IllegalStateException("Old and new value are null for " + scope + '#' + name2);
        }
        if (oldValue != null && newValue == null) {
            collectRemovedMember(scope, name2);
        } else {
            if (Intrinsics.areEqual(oldValue, newValue)) {
                return;
            }
            collectChangedMember(scope, name2);
        }
    }

    private final void collectSignature(ClassProtoData classData, boolean areSubclassesAffected) {
        FqName fqNameAsSingleFqName = NameResolverUtilKt.getClassId(classData.getNameResolver(), classData.getProto().getFqName()).asSingleFqName();
        Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
        collectSignature(fqNameAsSingleFqName, areSubclassesAffected);
    }

    public final void collectSignature(FqName fqName, boolean areSubclassesAffected) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Boolean bool = this.areSubclassesAffected.get(fqName);
        if (bool == null) {
            bool = false;
        }
        this.areSubclassesAffected.put(fqName, Boolean.valueOf(bool.booleanValue() || areSubclassesAffected));
    }

    private final <T, R> Set<R> getSet(Map<T, Set<R>> map, T t) {
        HashSet hashSet = map.get(t);
        if (hashSet == null) {
            hashSet = new HashSet();
            map.put(t, hashSet);
        }
        return hashSet;
    }

    public final <T> Set<String> getNonPrivateNames(T t, NameResolver nameResolver, Function1<? super T, ? extends List<? extends MessageLite>>... members) {
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(members, "members");
        ArrayList arrayList = new ArrayList();
        for (Function1<? super T, ? extends List<? extends MessageLite>> function1 : members) {
            List<? extends MessageLite> listInvoke = function1.invoke(t);
            ArrayList arrayList2 = new ArrayList();
            for (T t2 : listInvoke) {
                if (!ProtoDifferenceUtilsKt.isPrivate((MessageLite) t2)) {
                    arrayList2.add(t2);
                }
            }
            CollectionsKt.addAll(arrayList, ProtoDifferenceUtilsKt.names(arrayList2, nameResolver));
        }
        return CollectionsKt.toSet(arrayList);
    }
}
