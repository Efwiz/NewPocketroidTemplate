package org.jetbrains.kotlin.com.intellij.ui;

import java.awt.Component;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.function.Function;
import javax.swing.Icon;
import org.jetbrains.kotlin.com.intellij.openapi.util.Iconable;
import org.jetbrains.kotlin.com.intellij.ui.icons.RowIcon;

/* JADX INFO: compiled from: IconManager.java */
/* JADX INFO: loaded from: classes3.dex */
final class DummyIconManager implements IconManager {
    static final IconManager INSTANCE = new DummyIconManager();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
            case 11:
            case 12:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
            case 8:
            case 10:
            default:
                str = "@NotNull method %s.%s must not return null";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
            case 11:
            case 12:
                i2 = 3;
                break;
            case 8:
            case 10:
            default:
                i2 = 2;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 3:
                objArr[0] = "path";
                break;
            case 2:
                objArr[0] = "aClass";
                break;
            case 4:
                objArr[0] = "classLoader";
                break;
            case 5:
                objArr[0] = "instance";
                break;
            case 6:
            case 7:
                objArr[0] = "icon";
                break;
            case 8:
            case 10:
            default:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/ui/DummyIconManager";
                break;
            case 9:
                objArr[0] = "iconProducer";
                break;
            case 11:
            case 12:
                objArr[0] = "icons";
                break;
        }
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
            case 11:
            case 12:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/ui/DummyIconManager";
                break;
            case 8:
                objArr[1] = "tooltipOnlyIfComposite";
                break;
            case 10:
                objArr[1] = "createDeferredIcon";
                break;
            default:
                objArr[1] = "getStubIcon";
                break;
        }
        switch (i) {
            case 1:
            case 2:
                objArr[2] = "getIcon";
                break;
            case 3:
            case 4:
                objArr[2] = "loadRasterizedIcon";
                break;
            case 5:
                objArr[2] = "createLayeredIcon";
                break;
            case 6:
                objArr[2] = "registerIconLayer";
                break;
            case 7:
                objArr[2] = "tooltipOnlyIfComposite";
                break;
            case 9:
                objArr[2] = "createDeferredIcon";
                break;
            case 11:
                objArr[2] = "createLayered";
                break;
            case 12:
                objArr[2] = "createRowIcon";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
            case 11:
            case 12:
                throw new IllegalArgumentException(str2);
            case 8:
            case 10:
            default:
                throw new IllegalStateException(str2);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.ui.IconManager
    public void registerIconLayer(int i, Icon icon) {
        if (icon == null) {
            $$$reportNull$$$0(6);
        }
    }

    private DummyIconManager() {
    }

    @Override // org.jetbrains.kotlin.com.intellij.ui.IconManager
    public Icon loadRasterizedIcon(String str, ClassLoader classLoader, long j, int i) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        if (classLoader == null) {
            $$$reportNull$$$0(4);
        }
        return new DummyIcon(str);
    }

    @Override // org.jetbrains.kotlin.com.intellij.ui.IconManager
    public RowIcon createLayeredIcon(Iconable iconable, Icon icon, int i) {
        if (iconable == null) {
            $$$reportNull$$$0(5);
        }
        Icon[] iconArr = new Icon[2];
        iconArr[0] = icon;
        return new DummyRowIcon(iconArr);
    }

    @Override // org.jetbrains.kotlin.com.intellij.ui.IconManager
    public Icon tooltipOnlyIfComposite(Icon icon) {
        if (icon == null) {
            $$$reportNull$$$0(7);
        }
        if (icon == null) {
            $$$reportNull$$$0(8);
        }
        return icon;
    }

    @Override // org.jetbrains.kotlin.com.intellij.ui.IconManager
    public <T> Icon createDeferredIcon(Icon icon, T t, Function<? super T, ? extends Icon> function) {
        if (function == null) {
            $$$reportNull$$$0(9);
        }
        if (icon == null) {
            $$$reportNull$$$0(10);
        }
        return icon;
    }

    @Override // org.jetbrains.kotlin.com.intellij.ui.IconManager
    public RowIcon createRowIcon(Icon... iconArr) {
        if (iconArr == null) {
            $$$reportNull$$$0(12);
        }
        return new DummyRowIcon(iconArr);
    }

    /* JADX INFO: compiled from: IconManager.java */
    private static class DummyIcon implements Icon {
        static final DummyIcon INSTANCE = new DummyIcon("<DummyIcon>");
        private final String path;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "path", "org/jetbrains/kotlin/com/intellij/ui/DummyIconManager$DummyIcon", "<init>"));
        }

        public int getIconHeight() {
            return 16;
        }

        public int getIconWidth() {
            return 16;
        }

        public void paintIcon(Component component, Graphics graphics, int i, int i2) {
        }

        private DummyIcon(String str) {
            if (str == null) {
                $$$reportNull$$$0(0);
            }
            this.path = str;
        }

        public int hashCode() {
            return this.path.hashCode();
        }

        public boolean equals(Object obj) {
            return this == obj || ((obj instanceof DummyIcon) && ((DummyIcon) obj).path == this.path);
        }

        public String toString() {
            return this.path;
        }
    }

    /* JADX INFO: compiled from: IconManager.java */
    private static final class DummyRowIcon extends DummyIcon implements RowIcon {
        private Icon[] icons;

        DummyRowIcon(Icon[] iconArr) {
            super("<DummyRowIcon>");
            this.icons = iconArr;
        }

        @Override // org.jetbrains.kotlin.com.intellij.ui.icons.RowIcon
        public void setIcon(Icon icon, int i) {
            if (this.icons == null) {
                this.icons = new Icon[4];
            }
            this.icons[i] = icon;
        }

        @Override // org.jetbrains.kotlin.com.intellij.ui.DummyIconManager.DummyIcon
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof DummyRowIcon) && Arrays.equals(this.icons, ((DummyRowIcon) obj).icons);
        }

        @Override // org.jetbrains.kotlin.com.intellij.ui.DummyIconManager.DummyIcon
        public int hashCode() {
            Icon[] iconArr = this.icons;
            if (iconArr.length > 0) {
                return iconArr[0].hashCode();
            }
            return 0;
        }

        @Override // org.jetbrains.kotlin.com.intellij.ui.DummyIconManager.DummyIcon
        public String toString() {
            return "Row icon. myIcons=" + Arrays.asList(this.icons);
        }
    }
}
