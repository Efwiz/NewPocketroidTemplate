package org.jetbrains.kotlin.load.kotlin;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMap;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.load.java.BuiltinMethodsWithSpecialGenericSignature;
import org.jetbrains.kotlin.load.java.SpecialBuiltinMembers;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.kotlin.resolve.jvm.JvmPrimitiveType;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: methodSignatureMapping.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000D\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b\u001a\u0018\u0010\t\u001a\u00020\n*\u00060\u000bj\u0002`\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002\u001a\u001e\u0010\u000f\u001a\u00020\u0001*\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u00062\b\b\u0002\u0010\u0012\u001a\u00020\u0006\u001a\n\u0010\u0013\u001a\u00020\u0001*\u00020\u0014\u001a\u000e\u0010\u0015\u001a\u0004\u0018\u00010\u0001*\u00020\bH\u0000\u001a\f\u0010\u0016\u001a\u00020\u0017*\u00020\u000eH\u0000\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0018"}, d2 = {"internalName", "", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getInternalName", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)Ljava/lang/String;", "forceSingleValueParameterBoxing", "", "f", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "appendErasedType", "", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "computeJvmDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "withReturnType", "withName", "computeJvmDescriptorWithoutName", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "computeJvmSignature", "mapToJvmType", "Lorg/jetbrains/kotlin/load/kotlin/JvmType;", "descriptors.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class MethodSignatureMappingKt {
    public static final String computeJvmDescriptor(FunctionDescriptor functionDescriptor, boolean z, boolean z2) {
        String strAsString;
        Intrinsics.checkNotNullParameter(functionDescriptor, "<this>");
        StringBuilder sb = new StringBuilder();
        if (z2) {
            if (functionDescriptor instanceof ConstructorDescriptor) {
                strAsString = "<init>";
            } else {
                strAsString = functionDescriptor.getName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            }
            sb.append(strAsString);
        }
        sb.append("(");
        ReceiverParameterDescriptor extensionReceiverParameter = functionDescriptor.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            KotlinType type = extensionReceiverParameter.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            appendErasedType(sb, type);
        }
        Iterator<ValueParameterDescriptor> it = functionDescriptor.getValueParameters().iterator();
        while (it.hasNext()) {
            KotlinType type2 = it.next().getType();
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            appendErasedType(sb, type2);
        }
        sb.append(Constant.AFTER_QUTO);
        if (z) {
            if (DescriptorBasedTypeSignatureMappingKt.hasVoidReturnType(functionDescriptor)) {
                sb.append(ExifInterface.GPS_MEASUREMENT_INTERRUPTED);
            } else {
                KotlinType returnType = functionDescriptor.getReturnType();
                Intrinsics.checkNotNull(returnType);
                appendErasedType(sb, returnType);
            }
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public static /* synthetic */ String computeJvmDescriptor$default(FunctionDescriptor functionDescriptor, boolean z, boolean z2, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        if ((i & 2) != 0) {
            z2 = true;
        }
        return computeJvmDescriptor(functionDescriptor, z, z2);
    }

    public static final String computeJvmDescriptorWithoutName(PropertyDescriptor propertyDescriptor) {
        Intrinsics.checkNotNullParameter(propertyDescriptor, "<this>");
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        ReceiverParameterDescriptor extensionReceiverParameter = propertyDescriptor.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            KotlinType type = extensionReceiverParameter.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            appendErasedType(sb, type);
        }
        sb.append(Constant.AFTER_QUTO);
        if (DescriptorBasedTypeSignatureMappingKt.hasVoidReturnType(propertyDescriptor)) {
            sb.append(ExifInterface.GPS_MEASUREMENT_INTERRUPTED);
        } else {
            KotlinType returnType = propertyDescriptor.getReturnType();
            Intrinsics.checkNotNull(returnType);
            appendErasedType(sb, returnType);
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public static final boolean forceSingleValueParameterBoxing(CallableDescriptor f) {
        FunctionDescriptor overriddenBuiltinFunctionWithErasedValueParametersInJava;
        Intrinsics.checkNotNullParameter(f, "f");
        if (!(f instanceof FunctionDescriptor)) {
            return false;
        }
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) f;
        if (!Intrinsics.areEqual(functionDescriptor.getName().asString(), ProductAction.ACTION_REMOVE) || functionDescriptor.getValueParameters().size() != 1 || SpecialBuiltinMembers.isFromJavaOrBuiltins((CallableMemberDescriptor) f)) {
            return false;
        }
        List<ValueParameterDescriptor> valueParameters = functionDescriptor.getOriginal().getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        KotlinType type = ((ValueParameterDescriptor) CollectionsKt.single((List) valueParameters)).getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        JvmType jvmTypeMapToJvmType = mapToJvmType(type);
        JvmType.Primitive primitive = jvmTypeMapToJvmType instanceof JvmType.Primitive ? (JvmType.Primitive) jvmTypeMapToJvmType : null;
        if ((primitive != null ? primitive.getJvmPrimitiveType() : null) != JvmPrimitiveType.INT || (overriddenBuiltinFunctionWithErasedValueParametersInJava = BuiltinMethodsWithSpecialGenericSignature.getOverriddenBuiltinFunctionWithErasedValueParametersInJava(functionDescriptor)) == null) {
            return false;
        }
        List<ValueParameterDescriptor> valueParameters2 = overriddenBuiltinFunctionWithErasedValueParametersInJava.getOriginal().getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters2, "getValueParameters(...)");
        KotlinType type2 = ((ValueParameterDescriptor) CollectionsKt.single((List) valueParameters2)).getType();
        Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
        JvmType jvmTypeMapToJvmType2 = mapToJvmType(type2);
        DeclarationDescriptor moduleDescriptor = overriddenBuiltinFunctionWithErasedValueParametersInJava.getModuleDescriptor();
        Intrinsics.checkNotNullExpressionValue(moduleDescriptor, "getContainingDeclaration(...)");
        return Intrinsics.areEqual(DescriptorUtilsKt.getFqNameUnsafe(moduleDescriptor), StandardNames.FqNames.mutableCollection.toUnsafe()) && (jvmTypeMapToJvmType2 instanceof JvmType.Object) && Intrinsics.areEqual(((JvmType.Object) jvmTypeMapToJvmType2).getInternalName(), TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_INTERNAL_NAME);
    }

    public static final String getInternalName(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
        JavaToKotlinClassMap javaToKotlinClassMap = JavaToKotlinClassMap.INSTANCE;
        FqNameUnsafe unsafe = DescriptorUtilsKt.getFqNameSafe(classDescriptor).toUnsafe();
        Intrinsics.checkNotNullExpressionValue(unsafe, "toUnsafe(...)");
        ClassId classIdMapKotlinToJava = javaToKotlinClassMap.mapKotlinToJava(unsafe);
        if (classIdMapKotlinToJava == null) {
            return DescriptorBasedTypeSignatureMappingKt.computeInternalName$default(classDescriptor, null, 2, null);
        }
        String internalName = JvmClassName.byClassId(classIdMapKotlinToJava).getInternalName();
        Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
        return internalName;
    }

    private static final void appendErasedType(StringBuilder sb, KotlinType kotlinType) {
        sb.append(mapToJvmType(kotlinType));
    }

    public static final JvmType mapToJvmType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return (JvmType) DescriptorBasedTypeSignatureMappingKt.mapType$default(kotlinType, JvmTypeFactoryImpl.INSTANCE, TypeMappingMode.DEFAULT, TypeMappingConfigurationImpl.INSTANCE, null, null, 32, null);
    }

    public static final String computeJvmSignature(CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(callableDescriptor, "<this>");
        SignatureBuildingComponents signatureBuildingComponents = SignatureBuildingComponents.INSTANCE;
        if (DescriptorUtils.isLocal(callableDescriptor)) {
            return null;
        }
        DeclarationDescriptor containingDeclaration = callableDescriptor.getModuleDescriptor();
        ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
        if (classDescriptor == null || classDescriptor.getName().isSpecial()) {
            return null;
        }
        CallableDescriptor original = callableDescriptor.getOriginal();
        SimpleFunctionDescriptor simpleFunctionDescriptor = original instanceof SimpleFunctionDescriptor ? (SimpleFunctionDescriptor) original : null;
        if (simpleFunctionDescriptor == null) {
            return null;
        }
        return MethodSignatureBuildingUtilsKt.signature(signatureBuildingComponents, classDescriptor, computeJvmDescriptor$default(simpleFunctionDescriptor, false, false, 3, null));
    }
}
