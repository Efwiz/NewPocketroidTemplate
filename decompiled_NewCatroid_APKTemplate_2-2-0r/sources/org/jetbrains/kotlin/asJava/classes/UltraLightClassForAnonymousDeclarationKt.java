package org.jetbrains.kotlin.asJava.classes;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: ultraLightClassForAnonymousDeclaration.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0002¨\u0006\u0003"}, d2 = {"getFirstSupertypeFQNameForAnonymousDeclaration", "", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClassImpl;", "light-classes"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UltraLightClassForAnonymousDeclarationKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final String getFirstSupertypeFQNameForAnonymousDeclaration(KtLightClassImpl ktLightClassImpl) {
        ClassifierDescriptor this$0;
        ClassDescriptor descriptor = ktLightClassImpl.getDescriptor();
        if (descriptor == null) {
            return CommonClassNames.JAVA_LANG_OBJECT;
        }
        Collection<KotlinType> collectionMo7438getSupertypes = descriptor.getTypeConstructor().mo7438getSupertypes();
        Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
        if (collectionMo7438getSupertypes.isEmpty() || (this$0 = collectionMo7438getSupertypes.iterator().next().getConstructor().getThis$0()) == null) {
            return CommonClassNames.JAVA_LANG_OBJECT;
        }
        String strAsString = DescriptorUtils.getFqName(this$0).asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return strAsString;
    }
}
