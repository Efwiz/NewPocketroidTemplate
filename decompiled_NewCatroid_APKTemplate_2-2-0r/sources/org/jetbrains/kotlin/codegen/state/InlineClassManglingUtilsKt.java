package org.jetbrains.kotlin.codegen.state;

import com.beust.jcommander.Parameters;
import com.reandroid.dex.sections.Marker;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.load.java.descriptors.JavaMethodDescriptor;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.InlineClassDescriptorResolver;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.InlineClassManglingRulesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: inlineClassManglingUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a2\u0010\u0002\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u000e\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u00072\b\u0010\t\u001a\u0004\u0018\u00010\b\u001a\u0012\u0010\n\u001a\u0004\u0018\u00010\b2\u0006\u0010\u000b\u001a\u00020\fH\u0002\u001a \u0010\r\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u000e\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0012\u001a\u00020\u0001\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"NOT_INLINE_CLASS_PARAMETER_PLACEHOLDER", "", "collectFunctionSignatureForManglingSuffix", "useOldManglingRules", "", "requiresFunctionNameManglingForParameterTypes", "fqNamesForMangling", "", "Lorg/jetbrains/kotlin/codegen/state/InfoForMangling;", "returnTypeInfo", "getInfoForMangling", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "getManglingSuffixBasedOnKotlinSignature", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "shouldMangleByReturnType", "md5base64", "signatureForMangling", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class InlineClassManglingUtilsKt {
    public static final String NOT_INLINE_CLASS_PARAMETER_PLACEHOLDER = "_";

    /* JADX INFO: Access modifiers changed from: private */
    public static final String collectFunctionSignatureForManglingSuffix$getSignatureElementForMangling(boolean z, InfoForMangling infoForMangling) {
        StringBuilder sb = new StringBuilder();
        if (infoForMangling == null) {
            return "";
        }
        if (z || infoForMangling.getIsValue()) {
            sb.append('L');
            sb.append(infoForMangling.getFqName());
            if (infoForMangling.getIsNullable()) {
                sb.append(MangleConstant.Q_MARK);
            }
            sb.append(';');
        } else {
            sb.append("_");
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    private static final String collectFunctionSignatureForManglingSuffix$collectSignatureForMangling(List<InfoForMangling> list, final boolean z) {
        return CollectionsKt.joinToString$default(list, z ? ", " : "", null, null, 0, null, new Function1<InfoForMangling, CharSequence>() { // from class: org.jetbrains.kotlin.codegen.state.InlineClassManglingUtilsKt$collectFunctionSignatureForManglingSuffix$collectSignatureForMangling$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(InfoForMangling infoForMangling) {
                return InlineClassManglingUtilsKt.collectFunctionSignatureForManglingSuffix$getSignatureElementForMangling(z, infoForMangling);
            }
        }, 30, null);
    }

    public static final String collectFunctionSignatureForManglingSuffix(boolean z, boolean z2, List<InfoForMangling> fqNamesForMangling, InfoForMangling infoForMangling) {
        String str;
        Intrinsics.checkNotNullParameter(fqNamesForMangling, "fqNamesForMangling");
        if (z) {
            if (z2) {
                return collectFunctionSignatureForManglingSuffix$collectSignatureForMangling(fqNamesForMangling, z);
            }
            if (infoForMangling != null) {
                return ":" + collectFunctionSignatureForManglingSuffix$getSignatureElementForMangling(z, infoForMangling);
            }
            return null;
        }
        if (!z2 && infoForMangling == null) {
            return null;
        }
        StringBuilder sbAppend = new StringBuilder().append(collectFunctionSignatureForManglingSuffix$collectSignatureForMangling(fqNamesForMangling, z));
        if (infoForMangling != null) {
            str = ":" + collectFunctionSignatureForManglingSuffix$getSignatureElementForMangling(z, infoForMangling);
        } else {
            str = "";
        }
        return sbAppend.append(str).toString();
    }

    public static final String getManglingSuffixBasedOnKotlinSignature(CallableMemberDescriptor descriptor, boolean z, boolean z2) {
        InfoForMangling infoForMangling;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (!(descriptor instanceof FunctionDescriptor) || (descriptor instanceof ConstructorDescriptor) || (descriptor instanceof JavaMethodDescriptor) || InlineClassDescriptorResolver.isSynthesizedBoxOrUnboxMethod(descriptor) || DescriptorUtils.hasJvmNameAnnotation(descriptor)) {
            return null;
        }
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) CoroutineCodegenUtilKt.unwrapInitialDescriptorForSuspendFunction(descriptor);
        boolean zRequiresFunctionNameManglingForParameterTypes = InlineClassManglingRulesKt.requiresFunctionNameManglingForParameterTypes(descriptor);
        FunctionDescriptor functionDescriptor2 = (FunctionDescriptor) descriptor;
        ReceiverParameterDescriptor extensionReceiverParameter = functionDescriptor2.getExtensionReceiverParameter();
        List listListOfNotNull = CollectionsKt.listOfNotNull(extensionReceiverParameter != null ? extensionReceiverParameter.getType() : null);
        List<ValueParameterDescriptor> valueParameters = functionDescriptor2.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<ValueParameterDescriptor> list = valueParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((ValueParameterDescriptor) it.next()).getType());
        }
        List<KotlinType> listPlus = CollectionsKt.plus((Collection) listListOfNotNull, (Iterable) arrayList);
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listPlus, 10));
        for (KotlinType kotlinType : listPlus) {
            Intrinsics.checkNotNull(kotlinType);
            arrayList2.add(getInfoForMangling(kotlinType));
        }
        ArrayList arrayList3 = arrayList2;
        if (z && InlineClassManglingRulesKt.requiresFunctionNameManglingForReturnType(functionDescriptor)) {
            KotlinType returnType = functionDescriptor.getReturnType();
            Intrinsics.checkNotNull(returnType);
            infoForMangling = getInfoForMangling(returnType);
        } else {
            infoForMangling = null;
        }
        String strCollectFunctionSignatureForManglingSuffix = collectFunctionSignatureForManglingSuffix(z2, zRequiresFunctionNameManglingForParameterTypes, arrayList3, infoForMangling);
        if (strCollectFunctionSignatureForManglingSuffix != null) {
            return Parameters.DEFAULT_OPTION_PREFIXES + md5base64(strCollectFunctionSignatureForManglingSuffix);
        }
        return null;
    }

    private static final InfoForMangling getInfoForMangling(KotlinType kotlinType) {
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        if (this$0 == null) {
            return null;
        }
        if (this$0 instanceof ClassDescriptor) {
            ClassifierDescriptor classifierDescriptor = this$0;
            return new InfoForMangling(DescriptorUtilsKt.getFqNameUnsafe(classifierDescriptor), InlineClassesUtilsKt.isValueClass(classifierDescriptor), kotlinType.getIsMarkedNullable());
        }
        if (this$0 instanceof TypeParameterDescriptor) {
            return getInfoForMangling(TypeUtilsKt.getRepresentativeUpperBound((TypeParameterDescriptor) this$0));
        }
        return null;
    }

    public static final String md5base64(String signatureForMangling) throws NoSuchAlgorithmException {
        Intrinsics.checkNotNullParameter(signatureForMangling, "signatureForMangling");
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] bytes = signatureForMangling.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        byte[] bArrDigest = messageDigest.digest(bytes);
        Intrinsics.checkNotNullExpressionValue(bArrDigest, "digest(...)");
        String strEncodeToString = Base64.getUrlEncoder().withoutPadding().encodeToString(ArraysKt.copyOfRange(bArrDigest, 0, 5));
        Intrinsics.checkNotNullExpressionValue(strEncodeToString, "encodeToString(...)");
        return strEncodeToString;
    }
}
