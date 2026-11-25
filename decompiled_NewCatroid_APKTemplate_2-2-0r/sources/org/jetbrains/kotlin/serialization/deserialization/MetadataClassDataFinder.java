package org.jetbrains.kotlin.serialization.deserialization;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.builtins.BuiltInsBinaryVersion;
import org.jetbrains.kotlin.metadata.deserialization.NameResolverImpl;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: MetadataUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/MetadataClassDataFinder;", "Lorg/jetbrains/kotlin/serialization/deserialization/ClassDataFinder;", "finder", "Lorg/jetbrains/kotlin/serialization/deserialization/KotlinMetadataFinder;", "(Lorg/jetbrains/kotlin/serialization/deserialization/KotlinMetadataFinder;)V", "getFinder", "()Lorg/jetbrains/kotlin/serialization/deserialization/KotlinMetadataFinder;", "findClassData", "Lorg/jetbrains/kotlin/serialization/deserialization/ClassData;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "deserialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MetadataClassDataFinder implements ClassDataFinder {
    private final KotlinMetadataFinder finder;

    public MetadataClassDataFinder(KotlinMetadataFinder finder) {
        Intrinsics.checkNotNullParameter(finder, "finder");
        this.finder = finder;
    }

    public final KotlinMetadataFinder getFinder() {
        return this.finder;
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.ClassDataFinder
    public ClassData findClassData(ClassId classId) throws IOException {
        Object next;
        Intrinsics.checkNotNullParameter(classId, "classId");
        InputStream inputStreamFindMetadata = this.finder.findMetadata((ClassId) SequencesKt.last(SequencesKt.generateSequence(classId, MetadataClassDataFinder$findClassData$topLevelClassId$1.INSTANCE)));
        if (inputStreamFindMetadata == null) {
            return null;
        }
        Triple<ProtoBuf.PackageFragment, NameResolverImpl, BuiltInsBinaryVersion> proto = MetadataUtilKt.readProto(inputStreamFindMetadata);
        ProtoBuf.PackageFragment packageFragmentComponent1 = proto.component1();
        NameResolverImpl nameResolverImplComponent2 = proto.component2();
        BuiltInsBinaryVersion builtInsBinaryVersionComponent3 = proto.component3();
        List<ProtoBuf.Class> class_List = packageFragmentComponent1.getClass_List();
        Intrinsics.checkNotNullExpressionValue(class_List, "getClass_List(...)");
        Iterator<T> it = class_List.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!Intrinsics.areEqual(NameResolverUtilKt.getClassId(nameResolverImplComponent2, ((ProtoBuf.Class) next).getFqName()), classId));
        ProtoBuf.Class r4 = (ProtoBuf.Class) next;
        if (r4 == null) {
            return null;
        }
        SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
        Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
        return new ClassData(nameResolverImplComponent2, r4, builtInsBinaryVersionComponent3, NO_SOURCE);
    }
}
