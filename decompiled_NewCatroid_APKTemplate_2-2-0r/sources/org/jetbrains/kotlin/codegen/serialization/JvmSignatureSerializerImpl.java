package org.jetbrains.kotlin.codegen.serialization;

import androidx.exifinterface.media.ExifInterface;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.dex.sections.Marker;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.metadata.jvm.deserialization.ClassMapperLite;
import org.jetbrains.kotlin.metadata.serialization.StringTable;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: JvmSerializerExtension.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\bH\u0014J\u0018\u0010\u000f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\bH\u0016¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/codegen/serialization/JvmSignatureSerializerImpl;", "Lorg/jetbrains/kotlin/codegen/serialization/JvmSignatureSerializer;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "stringTable", "Lorg/jetbrains/kotlin/metadata/serialization/StringTable;", "(Lorg/jetbrains/kotlin/metadata/serialization/StringTable;)V", "mapTypeDefault", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "requiresFunctionSignature", "", "descriptor", "desc", "requiresPropertySignature", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmSignatureSerializerImpl extends JvmSignatureSerializer<FunctionDescriptor, PropertyDescriptor> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JvmSignatureSerializerImpl(StringTable stringTable) {
        super(stringTable);
        Intrinsics.checkNotNullParameter(stringTable, "stringTable");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.codegen.serialization.JvmSignatureSerializer
    public boolean requiresFunctionSignature(FunctionDescriptor descriptor, String desc) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(desc, "desc");
        StringBuilder sb = new StringBuilder("(");
        ReceiverParameterDescriptor extensionReceiverParameter = descriptor.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            KotlinType type = extensionReceiverParameter.getValue().getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            String strMapTypeDefault = mapTypeDefault(type);
            if (strMapTypeDefault == null) {
                return true;
            }
            sb.append(strMapTypeDefault);
        }
        Iterator<ValueParameterDescriptor> it = descriptor.getValueParameters().iterator();
        while (it.hasNext()) {
            KotlinType type2 = it.next().getType();
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            String strMapTypeDefault2 = mapTypeDefault(type2);
            if (strMapTypeDefault2 == null) {
                return true;
            }
            sb.append(strMapTypeDefault2);
        }
        sb.append(Constant.AFTER_QUTO);
        KotlinType returnType = descriptor.getReturnType();
        String strMapTypeDefault3 = returnType == null ? ExifInterface.GPS_MEASUREMENT_INTERRUPTED : mapTypeDefault(returnType);
        if (strMapTypeDefault3 == null) {
            return true;
        }
        sb.append(strMapTypeDefault3);
        return !Intrinsics.areEqual(sb.toString(), desc);
    }

    @Override // org.jetbrains.kotlin.codegen.serialization.JvmSignatureSerializer
    public boolean requiresPropertySignature(PropertyDescriptor descriptor, String desc) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(desc, "desc");
        KotlinType type = descriptor.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return !Intrinsics.areEqual(desc, mapTypeDefault(type));
    }

    private final String mapTypeDefault(KotlinType type) {
        ClassId classId;
        ClassifierDescriptor this$0 = type.getConstructor().getThis$0();
        ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
        if (classDescriptor == null || (classId = DescriptorUtilsKt.getClassId((ClassifierDescriptor) classDescriptor)) == null) {
            return null;
        }
        String strAsString = classId.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return ClassMapperLite.mapClass(strAsString);
    }
}
