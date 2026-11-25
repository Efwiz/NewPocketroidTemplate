package org.jetbrains.kotlin.serialization.deserialization;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: ProtoBasedClassDataFinder.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0014\b\u0002\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t¢\u0006\u0002\u0010\fJ\u0012\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\nH\u0016R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\n0\u000e8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\"\u0010\u0011\u001a\u0016\u0012\u0004\u0012\u00020\n\u0012\f\u0012\n \u0014*\u0004\u0018\u00010\u00130\u00130\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/ProtoBasedClassDataFinder;", "Lorg/jetbrains/kotlin/serialization/deserialization/ClassDataFinder;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "metadataVersion", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "classSource", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "(Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;Lkotlin/jvm/functions/Function1;)V", "allClassIds", "", "getAllClassIds", "()Ljava/util/Collection;", "classIdToProto", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "findClassData", "Lorg/jetbrains/kotlin/serialization/deserialization/ClassData;", "classId", "deserialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProtoBasedClassDataFinder implements ClassDataFinder {
    private final Map<ClassId, ProtoBuf.Class> classIdToProto;
    private final Function1<ClassId, SourceElement> classSource;
    private final BinaryVersion metadataVersion;
    private final NameResolver nameResolver;

    /* JADX WARN: Multi-variable type inference failed */
    public ProtoBasedClassDataFinder(ProtoBuf.PackageFragment proto, NameResolver nameResolver, BinaryVersion metadataVersion, Function1<? super ClassId, ? extends SourceElement> classSource) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        Intrinsics.checkNotNullParameter(classSource, "classSource");
        this.nameResolver = nameResolver;
        this.metadataVersion = metadataVersion;
        this.classSource = classSource;
        List<ProtoBuf.Class> class_List = proto.getClass_List();
        Intrinsics.checkNotNullExpressionValue(class_List, "getClass_List(...)");
        List<ProtoBuf.Class> list = class_List;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
        for (Object obj : list) {
            linkedHashMap.put(NameResolverUtilKt.getClassId(this.nameResolver, ((ProtoBuf.Class) obj).getFqName()), obj);
        }
        this.classIdToProto = linkedHashMap;
    }

    public /* synthetic */ ProtoBasedClassDataFinder(ProtoBuf.PackageFragment packageFragment, NameResolver nameResolver, BinaryVersion binaryVersion, AnonymousClass1 anonymousClass1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(packageFragment, nameResolver, binaryVersion, (i & 8) != 0 ? new Function1<ClassId, SourceElement>() { // from class: org.jetbrains.kotlin.serialization.deserialization.ProtoBasedClassDataFinder.1
            @Override // kotlin.jvm.functions.Function1
            public final SourceElement invoke(ClassId it) {
                Intrinsics.checkNotNullParameter(it, "it");
                SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
                Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
                return NO_SOURCE;
            }
        } : anonymousClass1);
    }

    public final Collection<ClassId> getAllClassIds() {
        return this.classIdToProto.keySet();
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.ClassDataFinder
    public ClassData findClassData(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        ProtoBuf.Class r0 = this.classIdToProto.get(classId);
        if (r0 == null) {
            return null;
        }
        return new ClassData(this.nameResolver, r0, this.metadataVersion, this.classSource.invoke(classId));
    }
}
