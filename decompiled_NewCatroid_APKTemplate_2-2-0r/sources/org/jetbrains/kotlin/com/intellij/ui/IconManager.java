package org.jetbrains.kotlin.com.intellij.ui;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.function.Function;
import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.openapi.util.Iconable;
import org.jetbrains.kotlin.com.intellij.ui.icons.RowIcon;

/* JADX INFO: loaded from: classes3.dex */
public interface IconManager {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 3 || i == 5 || i == 6) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[(i == 1 || i == 3 || i == 5 || i == 6) ? 3 : 2];
        if (i == 1 || i == 3) {
            objArr[0] = "icon";
        } else if (i == 5) {
            objArr[0] = FirebaseAnalytics.Param.SOURCE;
        } else if (i != 6) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/ui/IconManager";
        } else {
            objArr[0] = TypedValues.Custom.S_COLOR;
        }
        switch (i) {
            case 1:
            case 3:
            case 5:
            case 6:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/ui/IconManager";
                break;
            case 2:
                objArr[1] = "createEmptyIcon";
                break;
            case 4:
                objArr[1] = "createOffsetIcon";
                break;
            case 7:
                objArr[1] = "colorize";
                break;
            case 8:
                objArr[1] = "createRowIcon";
                break;
            default:
                objArr[1] = "getInstance";
                break;
        }
        if (i == 1) {
            objArr[2] = "createEmptyIcon";
        } else if (i == 3) {
            objArr[2] = "createOffsetIcon";
        } else if (i == 5 || i == 6) {
            objArr[2] = "colorize";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 3 && i != 5 && i != 6) {
            throw new IllegalStateException(str2);
        }
        throw new IllegalArgumentException(str2);
    }

    <T> Icon createDeferredIcon(Icon icon, T t, Function<? super T, ? extends Icon> function);

    RowIcon createLayeredIcon(Iconable iconable, Icon icon, int i);

    RowIcon createRowIcon(Icon... iconArr);

    Icon loadRasterizedIcon(String str, ClassLoader classLoader, long j, int i);

    void registerIconLayer(int i, Icon icon);

    Icon tooltipOnlyIfComposite(Icon icon);

    static IconManager getInstance() {
        IconManager iconManager = IconManagerHelper.instance;
        if (iconManager == null) {
            iconManager = DummyIconManager.INSTANCE;
        }
        if (iconManager == null) {
            $$$reportNull$$$0(0);
        }
        return iconManager;
    }

    default Icon createEmptyIcon(Icon icon) {
        if (icon == null) {
            $$$reportNull$$$0(1);
        }
        if (icon == null) {
            $$$reportNull$$$0(2);
        }
        return icon;
    }
}
