package org.jetbrains.kotlin.library.metadata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.serialization.SerializableStringTable;

/* JADX INFO: compiled from: KlibMetadataSerializationUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a@\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0018\u0010\u0004\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u00060\u00052\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e¨\u0006\u000f"}, d2 = {"buildKlibPackageFragment", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "packageProto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Package;", "classesProto", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "isEmpty", "", "stringTable", "Lorg/jetbrains/kotlin/serialization/SerializableStringTable;", "kotlin-util-klib-metadata"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KlibMetadataSerializationUtilsKt {
    public static final ProtoBuf.PackageFragment buildKlibPackageFragment(ProtoBuf.Package packageProto, List<Pair<ProtoBuf.Class, Integer>> classesProto, FqName fqName, boolean z, SerializableStringTable stringTable) {
        Intrinsics.checkNotNullParameter(packageProto, "packageProto");
        Intrinsics.checkNotNullParameter(classesProto, "classesProto");
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(stringTable, "stringTable");
        Pair<ProtoBuf.StringTable, ProtoBuf.QualifiedNameTable> pairBuildProto = stringTable.buildProto();
        ProtoBuf.StringTable stringTableComponent1 = pairBuildProto.component1();
        ProtoBuf.QualifiedNameTable qualifiedNameTableComponent2 = pairBuildProto.component2();
        ProtoBuf.PackageFragment.Builder builder = ProtoBuf.PackageFragment.newBuilder().setPackage(packageProto);
        List<Pair<ProtoBuf.Class, Integer>> list = classesProto;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add((ProtoBuf.Class) ((Pair) it.next()).getFirst());
        }
        ProtoBuf.PackageFragment.Builder qualifiedNames = builder.addAllClass_(arrayList).setStrings(stringTableComponent1).setQualifiedNames(qualifiedNameTableComponent2);
        Iterator<T> it2 = list.iterator();
        while (it2.hasNext()) {
            qualifiedNames.addExtension(KlibMetadataProtoBuf.className, ((Pair) it2.next()).getSecond());
        }
        qualifiedNames.setExtension(KlibMetadataProtoBuf.isEmpty, Boolean.valueOf(z));
        qualifiedNames.setExtension(KlibMetadataProtoBuf.fqName, fqName.asString());
        ProtoBuf.PackageFragment packageFragmentBuild = qualifiedNames.build();
        Intrinsics.checkNotNullExpressionValue(packageFragmentBuild, "build(...)");
        return packageFragmentBuild;
    }
}
