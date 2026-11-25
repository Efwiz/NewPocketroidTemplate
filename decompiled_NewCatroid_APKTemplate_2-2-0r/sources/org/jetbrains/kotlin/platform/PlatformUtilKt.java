package org.jetbrains.kotlin.platform;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

/* JADX INFO: compiled from: platformUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u001a\u001b\u0010\u0007\u001a\u00020\b\"\n\b\u0000\u0010\t\u0018\u0001*\u00020\n*\u0004\u0018\u00010\u0002H\u0086\b\u001a\u0018\u0010\u0007\u001a\u00020\b*\u0004\u0018\u00010\u00022\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\f\u001a\u001f\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\t0\u000e\"\n\b\u0000\u0010\t\u0018\u0001*\u00020\n*\u00020\u0002H\u0086\b\u001a$\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\t0\u000e\"\u0004\b\u0000\u0010\t*\u00020\u00022\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\t0\u000f\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0004¨\u0006\u0010"}, d2 = {"oldFashionedDescription", "", "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "getOldFashionedDescription", "(Lorg/jetbrains/kotlin/platform/TargetPlatform;)Ljava/lang/String;", "presentableDescription", "getPresentableDescription", "has", "", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/platform/SimplePlatform;", "klass", "Lkotlin/reflect/KClass;", "subplatformsOfType", "", "Ljava/lang/Class;", "compiler.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PlatformUtilKt {
    public static final /* synthetic */ <T extends SimplePlatform> List<T> subplatformsOfType(TargetPlatform targetPlatform) {
        Intrinsics.checkNotNullParameter(targetPlatform, "<this>");
        Set<SimplePlatform> componentPlatforms = targetPlatform.getComponentPlatforms();
        ArrayList arrayList = new ArrayList();
        for (Object obj : componentPlatforms) {
            Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
            if (obj instanceof Object) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public static final <T> List<T> subplatformsOfType(TargetPlatform targetPlatform, Class<T> klass) {
        Intrinsics.checkNotNullParameter(targetPlatform, "<this>");
        Intrinsics.checkNotNullParameter(klass, "klass");
        return CollectionsKt.filterIsInstance(targetPlatform.getComponentPlatforms(), klass);
    }

    public static final boolean has(TargetPlatform targetPlatform, KClass<?> klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        return (targetPlatform == null || subplatformsOfType(targetPlatform, JvmClassMappingKt.getJavaClass((KClass) klass)).isEmpty()) ? false : true;
    }

    public static final String getOldFashionedDescription(TargetPlatform targetPlatform) {
        String oldFashionedDescription;
        Intrinsics.checkNotNullParameter(targetPlatform, "<this>");
        SimplePlatform simplePlatform = (SimplePlatform) CollectionsKt.singleOrNull(targetPlatform);
        return (simplePlatform == null || (oldFashionedDescription = simplePlatform.getOldFashionedDescription()) == null) ? "Common (experimental) " : oldFashionedDescription;
    }

    public static final String getPresentableDescription(TargetPlatform targetPlatform) {
        Intrinsics.checkNotNullParameter(targetPlatform, "<this>");
        return CollectionsKt.joinToString$default(targetPlatform.getComponentPlatforms(), "/", null, null, 0, null, null, 62, null);
    }

    public static final /* synthetic */ <T extends SimplePlatform> boolean has(TargetPlatform targetPlatform) {
        if (targetPlatform != null) {
            Set<SimplePlatform> componentPlatforms = targetPlatform.getComponentPlatforms();
            ArrayList arrayList = new ArrayList();
            for (Object obj : componentPlatforms) {
                Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
                if (obj instanceof Object) {
                    arrayList.add(obj);
                }
            }
            if (!arrayList.isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
