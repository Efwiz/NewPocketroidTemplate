package org.jetbrains.kotlin.asJava.classes;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;

/* JADX INFO: compiled from: KotlinClassInnerStuffCache.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a'\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\u0000\u0010\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005H\u0002¢\u0006\u0002\u0010\b\u001a\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003¨\u0006\r"}, d2 = {"isAnonymous", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "(Lcom/intellij/psi/PsiClass;)Z", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "", ExifInterface.GPS_DIRECTION_TRUE, "value", "([Ljava/lang/Object;)[Ljava/lang/Object;", "getEnumEntriesPsiMethod", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMethod;", "enumClass", "Lorg/jetbrains/kotlin/asJava/classes/KtExtensibleLightClass;", "light-classes-base"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KotlinClassInnerStuffCacheKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isAnonymous(PsiClass psiClass) {
        return psiClass.getName() == null || (psiClass instanceof PsiAnonymousClass);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final <T> T[] copy(T[] tArr) {
        return tArr.length == 0 ? tArr : (T[]) ((Object[]) tArr.clone());
    }

    public static final PsiMethod getEnumEntriesPsiMethod(KtExtensibleLightClass enumClass) {
        Intrinsics.checkNotNullParameter(enumClass, "enumClass");
        return new KotlinEnumSyntheticMethod(enumClass, KotlinEnumSyntheticMethod.Kind.ENTRIES);
    }
}
