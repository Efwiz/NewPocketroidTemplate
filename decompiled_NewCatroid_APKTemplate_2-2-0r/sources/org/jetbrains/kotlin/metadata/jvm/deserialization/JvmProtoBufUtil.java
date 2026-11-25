package org.jetbrains.kotlin.metadata.jvm.deserialization;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.deserialization.ProtoBufUtilKt;
import org.jetbrains.kotlin.metadata.deserialization.ProtoTypeTableUtilKt;
import org.jetbrains.kotlin.metadata.deserialization.TypeTable;
import org.jetbrains.kotlin.metadata.jvm.JvmProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.serialization.JvmStringTable;
import org.jetbrains.kotlin.protobuf.ExtensionRegistryLite;
import org.jetbrains.kotlin.protobuf.GeneratedMessageLite;
import org.jetbrains.kotlin.protobuf.MessageLite;

/* JADX INFO: compiled from: JvmProtoBufUtil.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0090\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J*\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\f\u001a\u00020\u00142\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0015\u001a\u00020\u0016J \u0010\u0017\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\u00182\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u0010\u0010\u0019\u001a\u00020\u00162\u0006\u0010\f\u001a\u00020\u0014H\u0007J\u0010\u0010\u001a\u001a\u00020\u00162\u0006\u0010\f\u001a\u00020\u001bH\u0007J\u001a\u0010\u001c\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J5\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\u001b0 2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00040#2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00040#H\u0007¢\u0006\u0002\u0010%J/\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\u001b0 2\u0006\u0010&\u001a\u00020'2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00040#H\u0007¢\u0006\u0002\u0010(J5\u0010)\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\u00180 2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00040#2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00040#H\u0007¢\u0006\u0002\u0010%J5\u0010*\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020+0 2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00040#2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00040#H\u0007¢\u0006\u0002\u0010%J/\u0010*\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020+0 2\u0006\u0010&\u001a\u00020'2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00040#H\u0007¢\u0006\u0002\u0010(J#\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00040#2\u0006\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u000200H\u0007¢\u0006\u0002\u00101J \u00102\u001a\n 3*\u0004\u0018\u00010'0'2\u0006\u0010/\u001a\u0002002\u0006\u0010-\u001a\u00020.H\u0007J\u001f\u00104\u001a\u00020!*\u0002052\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00040#H\u0002¢\u0006\u0002\u00106R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u00067"}, d2 = {"Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmProtoBufUtil;", "", "()V", "DEFAULT_MODULE_NAME", "", "EXTENSION_REGISTRY", "Lorg/jetbrains/kotlin/protobuf/ExtensionRegistryLite;", "getEXTENSION_REGISTRY", "()Lorg/jetbrains/kotlin/protobuf/ExtensionRegistryLite;", "PLATFORM_TYPE_ID", "getJvmConstructorSignature", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMemberSignature$Method;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Constructor;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "typeTable", "Lorg/jetbrains/kotlin/metadata/deserialization/TypeTable;", "getJvmFieldSignature", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMemberSignature$Field;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;", "requireHasFieldFlag", "", "getJvmMethodSignature", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function;", "isMovedFromInterfaceCompanion", "isNewPlaceForBodyGeneration", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "mapTypeDefault", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type;", "readClassDataFrom", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmNameResolver;", "data", "", "strings", "([Ljava/lang/String;[Ljava/lang/String;)Lkotlin/Pair;", "bytes", "", "([B[Ljava/lang/String;)Lkotlin/Pair;", "readFunctionDataFrom", "readPackageDataFrom", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Package;", "writeData", CrashHianalyticsData.MESSAGE, "Lorg/jetbrains/kotlin/protobuf/MessageLite;", "stringTable", "Lorg/jetbrains/kotlin/metadata/jvm/serialization/JvmStringTable;", "(Lorg/jetbrains/kotlin/protobuf/MessageLite;Lorg/jetbrains/kotlin/metadata/jvm/serialization/JvmStringTable;)[Ljava/lang/String;", "writeDataBytes", JvmProtoBufUtil.PLATFORM_TYPE_ID, "readNameResolver", "Ljava/io/InputStream;", "(Ljava/io/InputStream;[Ljava/lang/String;)Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmNameResolver;", "metadata.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmProtoBufUtil {
    public static final String DEFAULT_MODULE_NAME = "main";
    private static final ExtensionRegistryLite EXTENSION_REGISTRY;
    public static final JvmProtoBufUtil INSTANCE = new JvmProtoBufUtil();
    public static final String PLATFORM_TYPE_ID = "kotlin.jvm.PlatformType";

    private JvmProtoBufUtil() {
    }

    static {
        ExtensionRegistryLite extensionRegistryLiteNewInstance = ExtensionRegistryLite.newInstance();
        JvmProtoBuf.registerAllExtensions(extensionRegistryLiteNewInstance);
        Intrinsics.checkNotNullExpressionValue(extensionRegistryLiteNewInstance, "apply(...)");
        EXTENSION_REGISTRY = extensionRegistryLiteNewInstance;
    }

    public final ExtensionRegistryLite getEXTENSION_REGISTRY() {
        return EXTENSION_REGISTRY;
    }

    @JvmStatic
    public static final Pair<JvmNameResolver, ProtoBuf.Class> readClassDataFrom(String[] data, String[] strings) {
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(strings, "strings");
        byte[] bArrDecodeBytes = BitEncoding.decodeBytes(data);
        Intrinsics.checkNotNullExpressionValue(bArrDecodeBytes, "decodeBytes(...)");
        return readClassDataFrom(bArrDecodeBytes, strings);
    }

    @JvmStatic
    public static final Pair<JvmNameResolver, ProtoBuf.Class> readClassDataFrom(byte[] bytes, String[] strings) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        Intrinsics.checkNotNullParameter(strings, "strings");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        return new Pair<>(INSTANCE.readNameResolver(byteArrayInputStream, strings), ProtoBuf.Class.parseFrom(byteArrayInputStream, EXTENSION_REGISTRY));
    }

    @JvmStatic
    public static final Pair<JvmNameResolver, ProtoBuf.Package> readPackageDataFrom(String[] data, String[] strings) {
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(strings, "strings");
        byte[] bArrDecodeBytes = BitEncoding.decodeBytes(data);
        Intrinsics.checkNotNullExpressionValue(bArrDecodeBytes, "decodeBytes(...)");
        return readPackageDataFrom(bArrDecodeBytes, strings);
    }

    @JvmStatic
    public static final Pair<JvmNameResolver, ProtoBuf.Package> readPackageDataFrom(byte[] bytes, String[] strings) {
        Intrinsics.checkNotNullParameter(bytes, "bytes");
        Intrinsics.checkNotNullParameter(strings, "strings");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        return new Pair<>(INSTANCE.readNameResolver(byteArrayInputStream, strings), ProtoBuf.Package.parseFrom(byteArrayInputStream, EXTENSION_REGISTRY));
    }

    @JvmStatic
    public static final Pair<JvmNameResolver, ProtoBuf.Function> readFunctionDataFrom(String[] data, String[] strings) {
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(strings, "strings");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(BitEncoding.decodeBytes(data));
        return new Pair<>(INSTANCE.readNameResolver(byteArrayInputStream, strings), ProtoBuf.Function.parseFrom(byteArrayInputStream, EXTENSION_REGISTRY));
    }

    private final JvmNameResolver readNameResolver(InputStream inputStream, String[] strArr) throws IOException {
        JvmProtoBuf.StringTableTypes delimitedFrom = JvmProtoBuf.StringTableTypes.parseDelimitedFrom(inputStream, EXTENSION_REGISTRY);
        Intrinsics.checkNotNullExpressionValue(delimitedFrom, "parseDelimitedFrom(...)");
        return new JvmNameResolver(delimitedFrom, strArr);
    }

    @JvmStatic
    public static final String[] writeData(MessageLite message, JvmStringTable stringTable) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(stringTable, "stringTable");
        String[] strArrEncodeBytes = BitEncoding.encodeBytes(writeDataBytes(stringTable, message));
        Intrinsics.checkNotNullExpressionValue(strArrEncodeBytes, "encodeBytes(...)");
        return strArrEncodeBytes;
    }

    @JvmStatic
    public static final byte[] writeDataBytes(JvmStringTable stringTable, MessageLite message) throws IOException {
        Intrinsics.checkNotNullParameter(stringTable, "stringTable");
        Intrinsics.checkNotNullParameter(message, "message");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ByteArrayOutputStream byteArrayOutputStream2 = byteArrayOutputStream;
        stringTable.serializeTo(byteArrayOutputStream2);
        message.writeTo(byteArrayOutputStream2);
        return byteArrayOutputStream.toByteArray();
    }

    public final JvmMemberSignature.Method getJvmMethodSignature(ProtoBuf.Function proto, NameResolver nameResolver, TypeTable typeTable) {
        String string;
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, JvmProtoBuf.JvmMethodSignature> methodSignature = JvmProtoBuf.methodSignature;
        Intrinsics.checkNotNullExpressionValue(methodSignature, "methodSignature");
        JvmProtoBuf.JvmMethodSignature jvmMethodSignature = (JvmProtoBuf.JvmMethodSignature) ProtoBufUtilKt.getExtensionOrNull(proto, methodSignature);
        int name2 = (jvmMethodSignature == null || !jvmMethodSignature.hasName()) ? proto.getName() : jvmMethodSignature.getName();
        if (jvmMethodSignature != null && jvmMethodSignature.hasDesc()) {
            string = nameResolver.getString(jvmMethodSignature.getDesc());
        } else {
            List listListOfNotNull = CollectionsKt.listOfNotNull(ProtoTypeTableUtilKt.receiverType(proto, typeTable));
            List<ProtoBuf.ValueParameter> valueParameterList = proto.getValueParameterList();
            Intrinsics.checkNotNullExpressionValue(valueParameterList, "getValueParameterList(...)");
            List<ProtoBuf.ValueParameter> list = valueParameterList;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            for (ProtoBuf.ValueParameter valueParameter : list) {
                Intrinsics.checkNotNull(valueParameter);
                arrayList.add(ProtoTypeTableUtilKt.type(valueParameter, typeTable));
            }
            List listPlus = CollectionsKt.plus((Collection) listListOfNotNull, (Iterable) arrayList);
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listPlus, 10));
            Iterator it = listPlus.iterator();
            while (it.hasNext()) {
                String strMapTypeDefault = INSTANCE.mapTypeDefault((ProtoBuf.Type) it.next(), nameResolver);
                if (strMapTypeDefault == null) {
                    return null;
                }
                arrayList2.add(strMapTypeDefault);
            }
            ArrayList arrayList3 = arrayList2;
            String strMapTypeDefault2 = mapTypeDefault(ProtoTypeTableUtilKt.returnType(proto, typeTable), nameResolver);
            if (strMapTypeDefault2 == null) {
                return null;
            }
            string = CollectionsKt.joinToString$default(arrayList3, "", "(", Constant.AFTER_QUTO, 0, null, null, 56, null) + strMapTypeDefault2;
        }
        return new JvmMemberSignature.Method(nameResolver.getString(name2), string);
    }

    public final JvmMemberSignature.Method getJvmConstructorSignature(ProtoBuf.Constructor proto, NameResolver nameResolver, TypeTable typeTable) {
        String string;
        String strJoinToString$default;
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Constructor, JvmProtoBuf.JvmMethodSignature> constructorSignature = JvmProtoBuf.constructorSignature;
        Intrinsics.checkNotNullExpressionValue(constructorSignature, "constructorSignature");
        JvmProtoBuf.JvmMethodSignature jvmMethodSignature = (JvmProtoBuf.JvmMethodSignature) ProtoBufUtilKt.getExtensionOrNull(proto, constructorSignature);
        if (jvmMethodSignature != null && jvmMethodSignature.hasName()) {
            string = nameResolver.getString(jvmMethodSignature.getName());
        } else {
            string = "<init>";
        }
        if (jvmMethodSignature != null && jvmMethodSignature.hasDesc()) {
            strJoinToString$default = nameResolver.getString(jvmMethodSignature.getDesc());
        } else {
            List<ProtoBuf.ValueParameter> valueParameterList = proto.getValueParameterList();
            Intrinsics.checkNotNullExpressionValue(valueParameterList, "getValueParameterList(...)");
            List<ProtoBuf.ValueParameter> list = valueParameterList;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            for (ProtoBuf.ValueParameter valueParameter : list) {
                JvmProtoBufUtil jvmProtoBufUtil = INSTANCE;
                Intrinsics.checkNotNull(valueParameter);
                String strMapTypeDefault = jvmProtoBufUtil.mapTypeDefault(ProtoTypeTableUtilKt.type(valueParameter, typeTable), nameResolver);
                if (strMapTypeDefault == null) {
                    return null;
                }
                arrayList.add(strMapTypeDefault);
            }
            strJoinToString$default = CollectionsKt.joinToString$default(arrayList, "", "(", ")V", 0, null, null, 56, null);
        }
        return new JvmMemberSignature.Method(string, strJoinToString$default);
    }

    public static /* synthetic */ JvmMemberSignature.Field getJvmFieldSignature$default(JvmProtoBufUtil jvmProtoBufUtil, ProtoBuf.Property property, NameResolver nameResolver, TypeTable typeTable, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = true;
        }
        return jvmProtoBufUtil.getJvmFieldSignature(property, nameResolver, typeTable, z);
    }

    public final JvmMemberSignature.Field getJvmFieldSignature(ProtoBuf.Property proto, NameResolver nameResolver, TypeTable typeTable, boolean requireHasFieldFlag) {
        String strMapTypeDefault;
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(typeTable, "typeTable");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, JvmProtoBuf.JvmPropertySignature> propertySignature = JvmProtoBuf.propertySignature;
        Intrinsics.checkNotNullExpressionValue(propertySignature, "propertySignature");
        JvmProtoBuf.JvmPropertySignature jvmPropertySignature = (JvmProtoBuf.JvmPropertySignature) ProtoBufUtilKt.getExtensionOrNull(proto, propertySignature);
        if (jvmPropertySignature == null) {
            return null;
        }
        JvmProtoBuf.JvmFieldSignature field = jvmPropertySignature.hasField() ? jvmPropertySignature.getField() : null;
        if (field == null && requireHasFieldFlag) {
            return null;
        }
        int name2 = (field == null || !field.hasName()) ? proto.getName() : field.getName();
        if (field == null || !field.hasDesc()) {
            strMapTypeDefault = mapTypeDefault(ProtoTypeTableUtilKt.returnType(proto, typeTable), nameResolver);
            if (strMapTypeDefault == null) {
                return null;
            }
        } else {
            strMapTypeDefault = nameResolver.getString(field.getDesc());
        }
        return new JvmMemberSignature.Field(nameResolver.getString(name2), strMapTypeDefault);
    }

    private final String mapTypeDefault(ProtoBuf.Type type, NameResolver nameResolver) {
        if (type.hasClassName()) {
            return ClassMapperLite.mapClass(nameResolver.getQualifiedClassName(type.getClassName()));
        }
        return null;
    }

    @JvmStatic
    public static final boolean isMovedFromInterfaceCompanion(ProtoBuf.Property proto) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        Flags.BooleanFlagField is_moved_from_interface_companion = JvmFlags.INSTANCE.getIS_MOVED_FROM_INTERFACE_COMPANION();
        Object extension = proto.getExtension(JvmProtoBuf.flags);
        Intrinsics.checkNotNullExpressionValue(extension, "getExtension(...)");
        Boolean bool = is_moved_from_interface_companion.get(((Number) extension).intValue());
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }

    @JvmStatic
    public static final boolean isNewPlaceForBodyGeneration(ProtoBuf.Class proto) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        Flags.BooleanFlagField is_compiled_in_jvm_default_mode = JvmFlags.INSTANCE.getIS_COMPILED_IN_JVM_DEFAULT_MODE();
        Object extension = proto.getExtension(JvmProtoBuf.jvmClassFlags);
        Intrinsics.checkNotNullExpressionValue(extension, "getExtension(...)");
        Boolean bool = is_compiled_in_jvm_default_mode.get(((Number) extension).intValue());
        Intrinsics.checkNotNullExpressionValue(bool, "get(...)");
        return bool.booleanValue();
    }
}
