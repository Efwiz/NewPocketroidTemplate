package org.jetbrains.kotlin.js.descriptorUtils;

import com.huawei.hms.framework.common.ContainerUtils;
import java.util.Collection;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.descriptors.EffectiveVisibilityUtilsKt;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.impl.AnonymousFunctionDescriptor;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.js.config.JsConfig;
import org.jetbrains.kotlin.js.translate.utils.AnnotationsUtils;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeProjection;

/* JADX INFO: compiled from: descriptorUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\t\u001a\u00020\n*\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0001H\u0007\u001a\u0012\u0010\f\u001a\u00020\n*\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0001\u001a\n\u0010\r\u001a\u00020\u0001*\u00020\u000e\u001a\u0012\u0010\u000f\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u0011\u001a\u0014\u0010\u000f\u001a\u00020\u0001*\u00020\u00122\u0006\u0010\u0010\u001a\u00020\u0011H\u0002\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u0017\u0010\u0004\u001a\u0004\u0018\u00010\u0005*\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u0013"}, d2 = {"isCoroutineLambda", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;)Z", "nameIfStandardType", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/types/KotlinType;", "getNameIfStandardType", "(Lorg/jetbrains/kotlin/types/KotlinType;)Lorg/jetbrains/kotlin/name/Name;", "getJetTypeFqName", "", "printTypeArguments", "getKotlinTypeFqName", "hasPrimaryConstructor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "shouldBeExported", "config", "Lorg/jetbrains/kotlin/js/config/JsConfig;", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "js.frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DescriptorUtilsKt {
    public static final Name getNameIfStandardType(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        if (this$0 == null) {
            return null;
        }
        if (!KotlinBuiltIns.isBuiltIn(this$0)) {
            this$0 = null;
        }
        if (this$0 != null) {
            return this$0.getShortName();
        }
        return null;
    }

    @Deprecated(message = "Use getKotlinTypeFqName(Boolean) instead")
    public static final String getJetTypeFqName(KotlinType kotlinType, boolean z) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return getKotlinTypeFqName(kotlinType, z);
    }

    public static final String getKotlinTypeFqName(KotlinType kotlinType, final boolean z) {
        String str;
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        if (this$0 == null) {
            throw new IllegalArgumentException(("declarationDescriptor is null for constructor = " + kotlinType.getConstructor() + " with " + kotlinType.getConstructor().getClass()).toString());
        }
        Intrinsics.checkNotNullExpressionValue(this$0, "requireNotNull(...)");
        if (this$0 instanceof TypeParameterDescriptor) {
            List<KotlinType> upperBounds = ((TypeParameterDescriptor) this$0).getUpperBounds();
            final Function1<KotlinType, String> function1 = new Function1<KotlinType, String>() { // from class: org.jetbrains.kotlin.js.descriptorUtils.DescriptorUtilsKt.getKotlinTypeFqName.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final String invoke(KotlinType kotlinType2) {
                    Intrinsics.checkNotNull(kotlinType2);
                    return DescriptorUtilsKt.getKotlinTypeFqName(kotlinType2, z);
                }
            };
            String strJoin = StringUtil.join((Collection) upperBounds, new Function() { // from class: org.jetbrains.kotlin.js.descriptorUtils.DescriptorUtilsKt$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.util.Function
                public final Object fun(Object obj) {
                    return DescriptorUtilsKt.getKotlinTypeFqName$lambda$1(function1, obj);
                }
            }, ContainerUtils.FIELD_DELIMITER);
            Intrinsics.checkNotNullExpressionValue(strJoin, "join(...)");
            return strJoin;
        }
        List<TypeProjection> arguments = kotlinType.getArguments();
        if (z && !arguments.isEmpty()) {
            final DescriptorUtilsKt$getKotlinTypeFqName$typeArgumentsAsString$joinedTypeArguments$1 descriptorUtilsKt$getKotlinTypeFqName$typeArgumentsAsString$joinedTypeArguments$1 = new Function1<TypeProjection, String>() { // from class: org.jetbrains.kotlin.js.descriptorUtils.DescriptorUtilsKt$getKotlinTypeFqName$typeArgumentsAsString$joinedTypeArguments$1
                @Override // kotlin.jvm.functions.Function1
                public final String invoke(TypeProjection typeProjection) {
                    KotlinType nullableAnyType = typeProjection.getNullableAnyType();
                    Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
                    return DescriptorUtilsKt.getKotlinTypeFqName(nullableAnyType, false);
                }
            };
            String strJoin2 = StringUtil.join((Collection) arguments, new Function() { // from class: org.jetbrains.kotlin.js.descriptorUtils.DescriptorUtilsKt$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.com.intellij.util.Function
                public final Object fun(Object obj) {
                    return DescriptorUtilsKt.getKotlinTypeFqName$lambda$2(descriptorUtilsKt$getKotlinTypeFqName$typeArgumentsAsString$joinedTypeArguments$1, obj);
                }
            }, ", ");
            Intrinsics.checkNotNullExpressionValue(strJoin2, "join(...)");
            str = "<" + strJoin2 + '>';
        } else {
            str = "";
        }
        return DescriptorUtils.getFqName(this$0).asString() + str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String getKotlinTypeFqName$lambda$1(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (String) tmp0.invoke(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String getKotlinTypeFqName$lambda$2(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (String) tmp0.invoke(obj);
    }

    public static final boolean hasPrimaryConstructor(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
        return classDescriptor.mo6347getUnsubstitutedPrimaryConstructor() != null;
    }

    public static final boolean isCoroutineLambda(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        return (declarationDescriptor instanceof AnonymousFunctionDescriptor) && ((AnonymousFunctionDescriptor) declarationDescriptor).isSuspend();
    }

    public static final boolean shouldBeExported(DeclarationDescriptor declarationDescriptor, JsConfig config) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(config, "config");
        if (!(declarationDescriptor instanceof DeclarationDescriptorWithVisibility)) {
            return true;
        }
        DeclarationDescriptorWithVisibility declarationDescriptorWithVisibility = (DeclarationDescriptorWithVisibility) declarationDescriptor;
        DescriptorVisibility visibility = declarationDescriptorWithVisibility.getVisibilityImpl();
        Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
        return shouldBeExported(EffectiveVisibilityUtilsKt.effectiveVisibility(declarationDescriptorWithVisibility, visibility, true), config) || AnnotationsUtils.getJsNameAnnotation(declarationDescriptor) != null;
    }

    private static final boolean shouldBeExported(EffectiveVisibility effectiveVisibility, JsConfig jsConfig) {
        if (effectiveVisibility.getPublicApi()) {
            return true;
        }
        if (jsConfig.getConfiguration().getBoolean(JSConfigurationKeys.FRIEND_PATHS_DISABLED)) {
            return false;
        }
        return Intrinsics.areEqual(effectiveVisibility.toVisibility(), Visibilities.Internal.INSTANCE);
    }
}
