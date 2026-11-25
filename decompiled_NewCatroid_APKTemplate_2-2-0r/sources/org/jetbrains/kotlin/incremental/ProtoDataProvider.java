package org.jetbrains.kotlin.incremental;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolverImpl;
import org.jetbrains.kotlin.metadata.deserialization.ProtoBufUtilKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException;
import org.jetbrains.kotlin.serialization.SerializerExtensionProtocol;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;
import org.jetbrains.kotlin.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;

/* JADX INFO: compiled from: IncrementalJsCache.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J%\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/incremental/ProtoDataProvider;", "", "serializerProtocol", "Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;", "(Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;)V", "invoke", "", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/incremental/ProtoData;", "sourceFile", "Ljava/io/File;", "metadata", "", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ProtoDataProvider {
    private final SerializerExtensionProtocol serializerProtocol;

    public ProtoDataProvider(SerializerExtensionProtocol serializerProtocol) {
        Intrinsics.checkNotNullParameter(serializerProtocol, "serializerProtocol");
        this.serializerProtocol = serializerProtocol;
    }

    public final Map<ClassId, ProtoData> invoke(File sourceFile, byte[] metadata) throws InvalidProtocolBufferException {
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        HashMap map = new HashMap();
        ProtoBuf.PackageFragment from = ProtoBuf.PackageFragment.parseFrom(metadata, this.serializerProtocol.getExtensionRegistry());
        ProtoBuf.StringTable strings = from.getStrings();
        Intrinsics.checkNotNullExpressionValue(strings, "getStrings(...)");
        ProtoBuf.QualifiedNameTable qualifiedNames = from.getQualifiedNames();
        Intrinsics.checkNotNullExpressionValue(qualifiedNames, "getQualifiedNames(...)");
        NameResolverImpl nameResolverImpl = new NameResolverImpl(strings, qualifiedNames);
        List<ProtoBuf.Class> class_List = from.getClass_List();
        Intrinsics.checkNotNullExpressionValue(class_List, "getClass_List(...)");
        for (ProtoBuf.Class r3 : class_List) {
            NameResolverImpl nameResolverImpl2 = nameResolverImpl;
            ClassId classId = NameResolverUtilKt.getClassId(nameResolverImpl2, r3.getFqName());
            Intrinsics.checkNotNull(r3);
            map.put(classId, new ClassProtoData(r3, nameResolverImpl2));
        }
        ProtoBuf.Package r10 = from.getPackage();
        Intrinsics.checkNotNull(r10);
        Integer num = (Integer) ProtoBufUtilKt.getExtensionOrNull(r10, this.serializerProtocol.getPackageFqName());
        FqName fqName = num != null ? new FqName(nameResolverImpl.getPackageFqName(num.intValue())) : FqName.ROOT;
        ClassId classId2 = new ClassId(fqName, Name.identifier(CapitalizeDecapitalizeKt.capitalizeAsciiOnly(FilesKt.getNameWithoutExtension(sourceFile)) + "Kt"));
        HashMap map2 = map;
        Intrinsics.checkNotNull(fqName);
        map2.put(classId2, new PackagePartProtoData(r10, nameResolverImpl, fqName));
        return map2;
    }
}
