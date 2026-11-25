package org.jetbrains.kotlin.js.naming;

import com.beust.jcommander.Parameters;
import com.huawei.hms.framework.common.ContainerUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import net.bytebuddy.description.type.TypeDescription;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.UtilsKt;
import org.jetbrains.kotlin.types.Variance;
import org.slf4j.Marker;

/* JADX INFO: compiled from: encodeSignature.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000B\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001c\u0010\u0000\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\u00010\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0002\u001a\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0007\u001a\u0018\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0002\u001a\u001c\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\n2\u0006\u0010\u0003\u001a\u00020\u0004H\u0002\u001a0\u0010\u000b\u001a\u00060\fj\u0002`\r*\u00060\fj\u0002`\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0011H\u0002\u001a8\u0010\u000b\u001a\u00060\fj\u0002`\r*\u00060\fj\u0002`\r2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00022\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0011H\u0002¨\u0006\u0015"}, d2 = {"collectTypeParameters", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "encodeSignature", "", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "getOwnTypeParameters", "nameTypeParameters", "", "encodeForSignature", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "typeParameterNamer", "Lkotlin/Function1;", "projection", "Lorg/jetbrains/kotlin/types/TypeProjection;", "parameter", "js.frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class EncodeSignatureKt {

    /* JADX INFO: compiled from: encodeSignature.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Variance.values().length];
            try {
                iArr[Variance.IN_VARIANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Variance.OUT_VARIANCE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Variance.INVARIANT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final String encodeSignature(final CallableDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        StringBuilder sb = new StringBuilder();
        final Map<TypeParameterDescriptor, String> mapNameTypeParameters = nameTypeParameters(descriptor);
        List<TypeParameterDescriptor> typeParameters = descriptor.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : typeParameters) {
            if (!((TypeParameterDescriptor) obj).isCapturedFromOuterDeclaration()) {
                arrayList.add(obj);
            }
        }
        Set set = CollectionsKt.toSet(arrayList);
        final Set mutableSet = CollectionsKt.toMutableSet(set);
        Function1<TypeParameterDescriptor, String> function1 = new Function1<TypeParameterDescriptor, String>() { // from class: org.jetbrains.kotlin.js.naming.EncodeSignatureKt$encodeSignature$typeParameterNamer$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final String invoke(TypeParameterDescriptor typeParameter) {
                Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
                mutableSet.add(typeParameter.getOriginal());
                String str = mapNameTypeParameters.get(typeParameter.getOriginal());
                if (str != null) {
                    return str;
                }
                throw new IllegalStateException((typeParameter.getOriginal() + " is not found when encode the signature of " + descriptor + '.').toString());
            }
        };
        List<ReceiverParameterDescriptor> contextReceiverParameters = descriptor.getContextReceiverParameters();
        Intrinsics.checkNotNullExpressionValue(contextReceiverParameters, "getContextReceiverParameters(...)");
        if (!contextReceiverParameters.isEmpty()) {
            Iterator<ReceiverParameterDescriptor> it = contextReceiverParameters.iterator();
            while (it.hasNext()) {
                KotlinType type = it.next().getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                encodeForSignature(sb, type, function1).append(',');
            }
            sb.append('\\');
        }
        ReceiverParameterDescriptor extensionReceiverParameter = descriptor.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            KotlinType type2 = extensionReceiverParameter.getType();
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            encodeForSignature(sb, type2, function1).append('/');
        }
        for (ValueParameterDescriptor valueParameterDescriptor : descriptor.getValueParameters()) {
            if (valueParameterDescriptor.getIndex() > 0) {
                sb.append(",");
            }
            if (valueParameterDescriptor.getVarargElementType() != null) {
                sb.append("*");
            }
            KotlinType type3 = valueParameterDescriptor.getType();
            Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
            encodeForSignature(sb, type3, function1);
        }
        boolean z = true;
        for (TypeParameterDescriptor typeParameterDescriptor : SequencesKt.filter(CollectionsKt.asSequence(mapNameTypeParameters.keySet()), new Function1<TypeParameterDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.js.naming.EncodeSignatureKt.encodeSignature.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(TypeParameterDescriptor it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return Boolean.valueOf(mutableSet.contains(it2));
            }
        })) {
            List<KotlinType> upperBounds = typeParameterDescriptor.getUpperBounds();
            Intrinsics.checkNotNullExpressionValue(upperBounds, "getUpperBounds(...)");
            ArrayList arrayList2 = new ArrayList();
            for (Object obj2 : upperBounds) {
                if (!KotlinBuiltIns.isNullableAny((KotlinType) obj2)) {
                    arrayList2.add(obj2);
                }
            }
            ArrayList<KotlinType> arrayList3 = arrayList2;
            if (!arrayList3.isEmpty() || set.contains(typeParameterDescriptor)) {
                sb.append(z ? "|" : ",").append(mapNameTypeParameters.get(typeParameterDescriptor));
                if (!arrayList3.isEmpty()) {
                    sb.append("<:");
                    int i = 0;
                    for (KotlinType kotlinType : arrayList3) {
                        int i2 = i + 1;
                        if (i > 0) {
                            sb.append(ContainerUtils.FIELD_DELIMITER);
                        }
                        Intrinsics.checkNotNull(kotlinType);
                        encodeForSignature(sb, kotlinType, function1);
                        i = i2;
                    }
                }
                z = false;
            }
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    private static final StringBuilder encodeForSignature(StringBuilder sb, KotlinType kotlinType, Function1<? super TypeParameterDescriptor, String> function1) {
        ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = kotlinType.getConstructor().getThis$0();
        Intrinsics.checkNotNull(classifierDescriptorMo7434getDeclarationDescriptor);
        if (classifierDescriptorMo7434getDeclarationDescriptor instanceof TypeParameterDescriptor) {
            StringBuilder sbAppend = sb.append(function1.invoke(classifierDescriptorMo7434getDeclarationDescriptor));
            Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
            return sbAppend;
        }
        if (FunctionTypesKt.isSuspendFunctionType(kotlinType)) {
            String strAsString = DescriptorUtils.getFqName(classifierDescriptorMo7434getDeclarationDescriptor).asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            sb.append(StringsKt.replace$default(strAsString, "kotlin.coroutines.SuspendFunction", "kotlin.SuspendFunction", false, 4, (Object) null));
        } else {
            sb.append(DescriptorUtils.getFqName(classifierDescriptorMo7434getDeclarationDescriptor).asString());
        }
        List<TypeParameterDescriptor> parameters = classifierDescriptorMo7434getDeclarationDescriptor.getTypeConstructor().getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        if (!kotlinType.getArguments().isEmpty() && !parameters.isEmpty()) {
            sb.append("<");
            for (Pair pair : CollectionsKt.zip(kotlinType.getArguments(), parameters)) {
                TypeProjection typeProjection = (TypeProjection) pair.component1();
                TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) pair.component2();
                if (typeParameterDescriptor.getIndex() > 0) {
                    sb.append(",");
                }
                Intrinsics.checkNotNull(typeParameterDescriptor);
                encodeForSignature(sb, typeProjection, typeParameterDescriptor, function1);
            }
            sb.append(">");
        }
        if (kotlinType.getIsMarkedNullable()) {
            sb.append(TypeDescription.Generic.OfWildcardType.SYMBOL);
        }
        return sb;
    }

    private static final StringBuilder encodeForSignature(StringBuilder sb, TypeProjection typeProjection, TypeParameterDescriptor typeParameterDescriptor, Function1<? super TypeParameterDescriptor, String> function1) {
        if (typeProjection.isStarProjection()) {
            StringBuilder sbAppend = sb.append("*");
            Intrinsics.checkNotNull(sbAppend);
            return sbAppend;
        }
        Variance variance = typeParameterDescriptor.getVariance();
        Intrinsics.checkNotNullExpressionValue(variance, "getVariance(...)");
        Variance projectionKind = typeProjection.getProjectionKind();
        Intrinsics.checkNotNullExpressionValue(projectionKind, "getProjectionKind(...)");
        int i = WhenMappings.$EnumSwitchMapping$0[UtilsKt.getEffectiveVariance(variance, projectionKind).ordinal()];
        if (i == 1) {
            sb.append(Parameters.DEFAULT_OPTION_PREFIXES);
        } else if (i == 2) {
            sb.append(Marker.ANY_NON_NULL_MARKER);
        }
        KotlinType type = typeProjection.getNullableAnyType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return encodeForSignature(sb, type, function1);
    }

    private static final Map<TypeParameterDescriptor, String> nameTypeParameters(DeclarationDescriptor declarationDescriptor) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator<List<TypeParameterDescriptor>> it = collectTypeParameters(declarationDescriptor).iterator();
        int i = 0;
        while (it.hasNext()) {
            int i2 = i + 1;
            Iterator<TypeParameterDescriptor> it2 = it.next().iterator();
            int i3 = 0;
            while (it2.hasNext()) {
                linkedHashMap.put(it2.next(), new StringBuilder().append(i).append(':').append(i3).toString());
                i3++;
            }
            i = i2;
        }
        return linkedHashMap;
    }

    private static final List<List<TypeParameterDescriptor>> collectTypeParameters(DeclarationDescriptor declarationDescriptor) {
        ArrayList arrayList = new ArrayList();
        while (declarationDescriptor != null) {
            List<TypeParameterDescriptor> ownTypeParameters = getOwnTypeParameters(declarationDescriptor);
            if (ownTypeParameters != null) {
                arrayList.add(ownTypeParameters);
            }
            if (declarationDescriptor instanceof ConstructorDescriptor) {
                declarationDescriptor = ((ConstructorDescriptor) declarationDescriptor).getConstructedClass().getDeclarationDescriptor();
            } else {
                declarationDescriptor = declarationDescriptor.getDeclarationDescriptor();
            }
        }
        return arrayList;
    }

    private static final List<TypeParameterDescriptor> getOwnTypeParameters(DeclarationDescriptor declarationDescriptor) {
        if (!(declarationDescriptor instanceof ClassDescriptor)) {
            if (declarationDescriptor instanceof PropertyAccessorDescriptor) {
                PropertyDescriptor correspondingProperty = ((PropertyAccessorDescriptor) declarationDescriptor).getCorrespondingProperty();
                Intrinsics.checkNotNullExpressionValue(correspondingProperty, "getCorrespondingProperty(...)");
                return getOwnTypeParameters(correspondingProperty);
            }
            if (!(declarationDescriptor instanceof CallableDescriptor)) {
                return null;
            }
            List<TypeParameterDescriptor> typeParameters = ((CallableDescriptor) declarationDescriptor).getTypeParameters();
            Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
            ArrayList arrayList = new ArrayList();
            for (Object obj : typeParameters) {
                if (!((TypeParameterDescriptor) obj).isCapturedFromOuterDeclaration()) {
                    arrayList.add(obj);
                }
            }
            return arrayList;
        }
        List<TypeParameterDescriptor> declaredTypeParameters = ((ClassDescriptor) declarationDescriptor).getDeclaredTypeParameters();
        Intrinsics.checkNotNullExpressionValue(declaredTypeParameters, "getDeclaredTypeParameters(...)");
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : declaredTypeParameters) {
            if (!((TypeParameterDescriptor) obj2).isCapturedFromOuterDeclaration()) {
                arrayList2.add(obj2);
            }
        }
        return arrayList2;
    }
}
