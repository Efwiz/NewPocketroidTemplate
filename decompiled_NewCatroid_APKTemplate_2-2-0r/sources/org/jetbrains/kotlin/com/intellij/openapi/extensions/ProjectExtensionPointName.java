package org.jetbrains.kotlin.com.intellij.openapi.extensions;

import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import java.util.List;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionPointImpl;

/* JADX INFO: loaded from: classes3.dex */
public final class ProjectExtensionPointName<T> extends BaseExtensionPointName<T> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 4 || i == 6 || i == 11 || i == 24) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 4 || i == 6 || i == 11 || i == 24) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
            case 5:
            case 8:
            case 10:
            case 12:
            case 13:
            case 15:
            case 17:
            case 19:
            case 21:
            case 23:
                objArr[0] = "areaInstance";
                break;
            case 2:
            case 4:
            case 6:
            case 11:
            case 24:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/ProjectExtensionPointName";
                break;
            case 7:
            case 9:
                objArr[0] = "instanceOf";
                break;
            case 14:
                objArr[0] = "predicate";
                break;
            case 16:
                objArr[0] = "processor";
                break;
            case 18:
            case 20:
                objArr[0] = ServiceSpecificExtraArgs.CastExtraArgs.LISTENER;
                break;
            case 22:
                objArr[0] = "consumer";
                break;
            default:
                objArr[0] = "name";
                break;
        }
        if (i == 2) {
            objArr[1] = "getPoint";
        } else if (i == 4) {
            objArr[1] = "getExtensions";
        } else if (i == 6) {
            objArr[1] = "extensions";
        } else if (i == 11) {
            objArr[1] = "findExtensionOrFail";
        } else if (i != 24) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/extensions/ProjectExtensionPointName";
        } else {
            objArr[1] = "getIterable";
        }
        switch (i) {
            case 1:
                objArr[2] = "getPoint";
                break;
            case 2:
            case 4:
            case 6:
            case 11:
            case 24:
                break;
            case 3:
                objArr[2] = "getExtensions";
                break;
            case 5:
                objArr[2] = "extensions";
                break;
            case 7:
            case 8:
                objArr[2] = "findExtension";
                break;
            case 9:
            case 10:
                objArr[2] = "findExtensionOrFail";
                break;
            case 12:
                objArr[2] = "hasAnyExtensions";
                break;
            case 13:
            case 14:
                objArr[2] = "findFirstSafe";
                break;
            case 15:
            case 16:
                objArr[2] = "computeSafeIfAny";
                break;
            case 17:
            case 18:
                objArr[2] = "addExtensionPointListener";
                break;
            case 19:
            case 20:
                objArr[2] = "addChangeListener";
                break;
            case 21:
            case 22:
                objArr[2] = "processWithPluginDescriptor";
                break;
            case 23:
                objArr[2] = "getIterable";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 4 && i != 6 && i != 11 && i != 24) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ProjectExtensionPointName(String str) {
        super(str);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    public ExtensionPoint<T> getPoint(AreaInstance areaInstance) {
        if (areaInstance == null) {
            $$$reportNull$$$0(1);
        }
        ExtensionPointImpl<T> pointImpl = getPointImpl(areaInstance);
        if (pointImpl == null) {
            $$$reportNull$$$0(2);
        }
        return pointImpl;
    }

    public List<T> getExtensions(AreaInstance areaInstance) {
        if (areaInstance == null) {
            $$$reportNull$$$0(3);
        }
        List<T> extensionList = getPointImpl(areaInstance).getExtensionList();
        if (extensionList == null) {
            $$$reportNull$$$0(4);
        }
        return extensionList;
    }
}
