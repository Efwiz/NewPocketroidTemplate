package org.jetbrains.kotlin.incremental;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.incremental.storage.ProtoMapValue;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmNameResolver;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.protobuf.MessageLite;
import org.jetbrains.kotlin.serialization.deserialization.ProtoEnumFlags;
import org.jetbrains.kotlin.serialization.deserialization.ProtoEnumFlagsUtilsKt;

/* JADX INFO: compiled from: protoDifferenceUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000P\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u000e\u001a\u0004\u0018\u00010\u000f*\u00020\u0010H\u0000\u001a\u0012\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0012*\u00020\u0010H\u0000\u001a\u0014\u0010\u0013\u001a\u00020\u000f*\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u0015H\u0002\u001a \u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0012*\b\u0012\u0004\u0012\u00020\u00050\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0000\u001a\u0012\u0010\u0017\u001a\u00020\u0018*\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u0018\u0010\u0004\u001a\u00020\u0001*\u00020\u00058@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0006\"\u0018\u0010\u0007\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0003\"\u0017\u0010\b\u001a\u0004\u0018\u00010\t*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b\"\u0017\u0010\b\u001a\u0004\u0018\u00010\t*\u00020\f8F¢\u0006\u0006\u001a\u0004\b\n\u0010\r¨\u0006\u001c"}, d2 = {"isCompanionObject", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "(Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;)Z", "isPrivate", "Lorg/jetbrains/kotlin/protobuf/MessageLite;", "(Lorg/jetbrains/kotlin/protobuf/MessageLite;)Z", "isSealed", "typeTableOrNull", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeTable;", "getTypeTableOrNull", "(Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;)Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeTable;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Package;", "(Lorg/jetbrains/kotlin/metadata/ProtoBuf$Package;)Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeTable;", "getCompanionObjectName", "", "Lorg/jetbrains/kotlin/incremental/ClassProtoData;", "getConstants", "", "name", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "names", "toProtoData", "Lorg/jetbrains/kotlin/incremental/ProtoData;", "Lorg/jetbrains/kotlin/incremental/storage/ProtoMapValue;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "kotlin-build-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ProtoDifferenceUtilsKt {
    public static final ProtoData toProtoData(ProtoMapValue protoMapValue, FqName packageFqName) {
        Intrinsics.checkNotNullParameter(protoMapValue, "<this>");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        if (protoMapValue.isPackageFacade()) {
            Pair<JvmNameResolver, ProtoBuf.Package> packageDataFrom = JvmProtoBufUtil.readPackageDataFrom(protoMapValue.getBytes(), protoMapValue.getStrings());
            return new PackagePartProtoData(packageDataFrom.component2(), packageDataFrom.component1(), packageFqName);
        }
        Pair<JvmNameResolver, ProtoBuf.Class> classDataFrom = JvmProtoBufUtil.readClassDataFrom(protoMapValue.getBytes(), protoMapValue.getStrings());
        return new ClassProtoData(classDataFrom.component2(), classDataFrom.component1());
    }

    public static final boolean isPrivate(MessageLite messageLite) {
        ProtoBuf.Visibility visibility;
        Intrinsics.checkNotNullParameter(messageLite, "<this>");
        if (messageLite instanceof ProtoBuf.Constructor) {
            visibility = Flags.VISIBILITY.get(((ProtoBuf.Constructor) messageLite).getFlags());
        } else if (messageLite instanceof ProtoBuf.Function) {
            visibility = Flags.VISIBILITY.get(((ProtoBuf.Function) messageLite).getFlags());
        } else if (messageLite instanceof ProtoBuf.Property) {
            visibility = Flags.VISIBILITY.get(((ProtoBuf.Property) messageLite).getFlags());
        } else {
            if (!(messageLite instanceof ProtoBuf.TypeAlias)) {
                if (messageLite instanceof ProtoBuf.EnumEntry) {
                    return false;
                }
                throw new IllegalStateException(("Unknown message: " + messageLite).toString());
            }
            visibility = Flags.VISIBILITY.get(((ProtoBuf.TypeAlias) messageLite).getFlags());
        }
        return DescriptorVisibilities.isPrivate(ProtoEnumFlagsUtilsKt.descriptorVisibility(ProtoEnumFlags.INSTANCE, visibility));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String name(MessageLite messageLite, NameResolver nameResolver) {
        if (messageLite instanceof ProtoBuf.Constructor) {
            return "<init>";
        }
        if (messageLite instanceof ProtoBuf.Function) {
            return nameResolver.getString(((ProtoBuf.Function) messageLite).getName());
        }
        if (messageLite instanceof ProtoBuf.Property) {
            return nameResolver.getString(((ProtoBuf.Property) messageLite).getName());
        }
        if (messageLite instanceof ProtoBuf.TypeAlias) {
            return nameResolver.getString(((ProtoBuf.TypeAlias) messageLite).getName());
        }
        if (messageLite instanceof ProtoBuf.EnumEntry) {
            return nameResolver.getString(((ProtoBuf.EnumEntry) messageLite).getName());
        }
        throw new IllegalStateException(("Unknown message: " + messageLite).toString());
    }

    public static final List<String> names(List<? extends MessageLite> list, NameResolver nameResolver) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        List<? extends MessageLite> list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(name((MessageLite) it.next(), nameResolver));
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isSealed(ProtoBuf.Class r2) {
        return ProtoBuf.Modality.SEALED == Flags.MODALITY.get(r2.getFlags());
    }

    public static final boolean isCompanionObject(ProtoBuf.Class r2) {
        Intrinsics.checkNotNullParameter(r2, "<this>");
        return ProtoBuf.Class.Kind.COMPANION_OBJECT == Flags.CLASS_KIND.get(r2.getFlags());
    }

    public static final ProtoBuf.TypeTable getTypeTableOrNull(ProtoBuf.Class r1) {
        Intrinsics.checkNotNullParameter(r1, "<this>");
        if (r1.hasTypeTable()) {
            return r1.getTypeTable();
        }
        return null;
    }

    public static final ProtoBuf.TypeTable getTypeTableOrNull(ProtoBuf.Package r1) {
        Intrinsics.checkNotNullParameter(r1, "<this>");
        if (r1.hasTypeTable()) {
            return r1.getTypeTable();
        }
        return null;
    }

    public static final String getCompanionObjectName(ClassProtoData classProtoData) {
        Intrinsics.checkNotNullParameter(classProtoData, "<this>");
        if (classProtoData.getProto().hasCompanionObjectName()) {
            return classProtoData.getNameResolver().getString(classProtoData.getProto().getCompanionObjectName());
        }
        return null;
    }

    public static final List<String> getConstants(ClassProtoData classProtoData) {
        Intrinsics.checkNotNullParameter(classProtoData, "<this>");
        List<ProtoBuf.Property> propertyList = classProtoData.getProto().getPropertyList();
        Intrinsics.checkNotNullExpressionValue(propertyList, "getPropertyList(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : propertyList) {
            Boolean bool = Flags.IS_CONST.get(((ProtoBuf.Property) obj).getFlags());
            Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
            if (bool.booleanValue()) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(classProtoData.getNameResolver().getString(((ProtoBuf.Property) it.next()).getName()));
        }
        return arrayList3;
    }
}
