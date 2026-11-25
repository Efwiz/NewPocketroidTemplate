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
import org.jetbrains.kotlin.metadata.js.JsProtoBuf;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.protobuf.GeneratedMessageLite;
import org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;
import org.jetbrains.kotlin.serialization.js.JsSerializerProtocol;
import org.jetbrains.kotlin.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;

/* JADX INFO: compiled from: IncrementalJsCache.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\u001a\"\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\b"}, d2 = {"getProtoData", "", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/incremental/ProtoData;", "sourceFile", "Ljava/io/File;", "metadata", "", "kotlin-build-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IncrementalJsCacheKt {
    public static final Map<ClassId, ProtoData> getProtoData(File sourceFile, byte[] metadata) throws InvalidProtocolBufferException {
        String packageFqName;
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        Intrinsics.checkNotNullParameter(metadata, "metadata");
        HashMap map = new HashMap();
        ProtoBuf.PackageFragment from = ProtoBuf.PackageFragment.parseFrom(metadata, JsSerializerProtocol.INSTANCE.getExtensionRegistry());
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
        ProtoBuf.Package r9 = from.getPackage();
        Intrinsics.checkNotNull(r9);
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Package, Integer> packageFqName2 = JsProtoBuf.packageFqName;
        Intrinsics.checkNotNullExpressionValue(packageFqName2, "packageFqName");
        Integer num = (Integer) ProtoBufUtilKt.getExtensionOrNull(r9, packageFqName2);
        FqName fqName = (num == null || (packageFqName = nameResolverImpl.getPackageFqName(num.intValue())) == null) ? FqName.ROOT : new FqName(packageFqName);
        ClassId classId2 = new ClassId(fqName, Name.identifier(CapitalizeDecapitalizeKt.capitalizeAsciiOnly(FilesKt.getNameWithoutExtension(sourceFile)) + "Kt"));
        HashMap map2 = map;
        Intrinsics.checkNotNull(fqName);
        map2.put(classId2, new PackagePartProtoData(r9, nameResolverImpl, fqName));
        return map2;
    }
}
