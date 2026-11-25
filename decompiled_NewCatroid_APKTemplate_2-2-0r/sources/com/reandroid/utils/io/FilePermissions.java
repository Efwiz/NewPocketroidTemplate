package com.reandroid.utils.io;

import com.beust.jcommander.Parameters;
import io.netty.util.internal.StringUtil;
import java.io.File;

/* JADX INFO: loaded from: classes11.dex */
public class FilePermissions {
    private int value;

    public boolean apply(File file) {
        Permission permissionOwner = owner();
        Permission permissionGroup = group();
        Permission permissionOthers = others();
        return file.setReadable(permissionOwner.read(), (!permissionOwner.read() || permissionGroup.read() || permissionOthers.read()) ? false : true) | file.setExecutable(permissionOwner.execute(), (!permissionOwner.execute() || permissionGroup.execute() || permissionOthers.execute()) ? false : true) | file.setWritable(permissionOwner.write(), (!permissionOwner.write() || permissionGroup.write() || permissionOthers.write()) ? false : true);
    }

    public int get() {
        return this.value;
    }

    public void set(int i) {
        if (((-65536) & i) != 0) {
            throw new NumberFormatException("Value out of range: " + i);
        }
        this.value = i;
    }

    public int high() {
        return get() / 512;
    }

    public void high(int i) {
        if (i < 0 || i > 511) {
            throw new NumberFormatException("High value out of range: " + i);
        }
        set((i * 512) + permissions());
    }

    public int permissions() {
        return (owner().get() * 64) + (group().get() * 8) + others().get();
    }

    public void permissions(int i) {
        if (i < 0 || i > 511) {
            throw new NumberFormatException("Permissions value out of range: " + i);
        }
        set((high() * 512) + i);
    }

    public Permission owner() {
        return new Permission(this, 2);
    }

    public Permission group() {
        return new Permission(this, 1);
    }

    public Permission others() {
        return new Permission(this, 0);
    }

    public String octal() {
        return "0" + Integer.toOctalString(get());
    }

    public String permissionsOctal() {
        return "0" + Integer.toOctalString(permissions());
    }

    public String permissionsString() {
        return Parameters.DEFAULT_OPTION_PREFIXES + owner().getString() + group().getString() + others().getString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && get() == ((FilePermissions) obj).get();
    }

    public int hashCode() {
        return get();
    }

    public String toString() {
        return octal() + StringUtil.SPACE + permissionsString();
    }

    public static FilePermissions of(int i) {
        FilePermissions filePermissions = new FilePermissions();
        filePermissions.set(i);
        return filePermissions;
    }

    public static class Permission {
        private final int index;
        private final FilePermissions permissions;

        Permission(FilePermissions filePermissions, int i) {
            this.permissions = filePermissions;
            this.index = i;
        }

        public boolean execute() {
            return (get() & 1) != 0;
        }

        public void execute(boolean z) {
            int i = get();
            set(z ? i | 1 : i & 14);
        }

        public boolean write() {
            return (get() & 2) != 0;
        }

        public void write(boolean z) {
            int i = get();
            set(z ? i | 2 : i & 13);
        }

        public boolean read() {
            return (get() & 4) != 0;
        }

        public void read(boolean z) {
            int i = get();
            set(z ? i | 4 : i & 11);
        }

        public int get() {
            return getFor(this.index);
        }

        public void set(int i) {
            if (i < 0 || i > 7) {
                throw new NumberFormatException("Permission triad out of range: " + i);
            }
            int i2 = this.index;
            int i3 = 0;
            int i4 = 1;
            int i5 = 0;
            while (i3 < 6) {
                i5 += (i3 == i2 ? i : getFor(i3)) * i4;
                i4 *= 8;
                i3++;
            }
            this.permissions.set(i5);
        }

        private int getFor(int i) {
            int i2 = 1;
            for (int i3 = 0; i3 < i; i3++) {
                i2 *= 8;
            }
            return (this.permissions.get() % (i2 * 8)) / i2;
        }

        public String getString() {
            int i = get();
            return new String(new byte[]{(byte) ((i & 4) != 0 ? 114 : 45), (byte) ((i & 2) != 0 ? 119 : 45), (byte) ((i & 1) != 0 ? 120 : 45)});
        }

        public String getName() {
            int i = this.index;
            if (i == 0) {
                return "others";
            }
            if (i == 1) {
                return "group";
            }
            if (i == 2) {
                return "owner";
            }
            return "";
        }

        public String toString() {
            return getName() + StringUtil.SPACE + getString();
        }
    }
}
