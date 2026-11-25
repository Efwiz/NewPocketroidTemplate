package org.jetbrains.kotlin.library.metadata;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.serialization.deserialization.ClassData;
import org.jetbrains.kotlin.serialization.deserialization.ClassDataFinder;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;

/* JADX INFO: compiled from: KlibMetadataClassDataFinder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u0012\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001f\u0010\t\u001a\u0010\u0012\f\u0012\n \f*\u0004\u0018\u00010\u000b0\u000b0\n¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/library/metadata/KlibMetadataClassDataFinder;", "Lorg/jetbrains/kotlin/serialization/deserialization/ClassDataFinder;", "fragment", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "containerSource", "Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;", "(Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;Lorg/jetbrains/kotlin/serialization/deserialization/descriptors/DeserializedContainerSource;)V", "nameList", "", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getNameList", "()Ljava/util/List;", "findClassData", "Lorg/jetbrains/kotlin/serialization/deserialization/ClassData;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KlibMetadataClassDataFinder implements ClassDataFinder {
    private final DeserializedContainerSource containerSource;
    private final ProtoBuf.PackageFragment fragment;
    private final List<Integer> nameList;
    private final NameResolver nameResolver;

    public KlibMetadataClassDataFinder(ProtoBuf.PackageFragment fragment, NameResolver nameResolver, DeserializedContainerSource deserializedContainerSource) {
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        this.fragment = fragment;
        this.nameResolver = nameResolver;
        this.containerSource = deserializedContainerSource;
        List<Integer> list = (List) fragment.getExtension(KlibMetadataProtoBuf.className);
        this.nameList = list == null ? CollectionsKt.emptyList() : list;
    }

    public /* synthetic */ KlibMetadataClassDataFinder(ProtoBuf.PackageFragment packageFragment, NameResolver nameResolver, DeserializedContainerSource deserializedContainerSource, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(packageFragment, nameResolver, (i & 4) != 0 ? null : deserializedContainerSource);
    }

    public final List<Integer> getNameList() {
        return this.nameList;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.ClassDataFinder
    public ClassData findClassData(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Iterator<Integer> it = this.nameList.iterator();
        int i = 0;
        while (true) {
            if (!it.hasNext()) {
                i = -1;
                break;
            }
            Integer next = it.next();
            NameResolver nameResolver = this.nameResolver;
            Intrinsics.checkNotNull(next);
            if (Intrinsics.areEqual(NameResolverUtilKt.getClassId(nameResolver, next.intValue()), classId)) {
                break;
            }
            i++;
        }
        if (i == -1) {
            return null;
        }
        ProtoBuf.Class class_ = this.fragment.getClass_(i);
        if (class_ == null) {
            throw new IllegalStateException(("Could not find data for serialized class " + classId).toString());
        }
        NameResolver nameResolver2 = this.nameResolver;
        KlibMetadataVersion klibMetadataVersion = KlibMetadataVersion.INSTANCE;
        DeserializedContainerSource deserializedContainerSource = this.containerSource;
        DeserializedContainerSource deserializedContainerSource2 = deserializedContainerSource != null ? deserializedContainerSource : SourceElement.NO_SOURCE;
        Intrinsics.checkNotNull(deserializedContainerSource2);
        return new ClassData(nameResolver2, class_, klibMetadataVersion, deserializedContainerSource2);
    }
}
