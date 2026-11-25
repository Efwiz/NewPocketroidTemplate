package org.eclipse.jgit.internal.storage.file;

import java.io.File;
import java.text.MessageFormat;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.pack.PackExt;
import org.eclipse.jgit.lib.ObjectId;

/* JADX INFO: loaded from: classes13.dex */
public class PackFile extends File {
    private static final String PREFIX = "pack-";
    private static final long serialVersionUID = 1;
    private final String base;
    private final boolean hasOldPrefix;

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    private final String f649id;
    private final PackExt packExt;

    private static String createName(String str, PackExt packExt) {
        return PREFIX + str + '.' + packExt.getExtension();
    }

    public PackFile(File file) {
        this(file.getParentFile(), file.getName());
    }

    public PackFile(File file, ObjectId objectId, PackExt packExt) {
        this(file, objectId.name(), packExt);
    }

    public PackFile(File file, String str, PackExt packExt) {
        this(file, createName(str, packExt));
    }

    public PackFile(File file, String str) {
        super(file, str);
        int iLastIndexOf = str.lastIndexOf(46);
        if (iLastIndexOf < 0) {
            this.base = str;
            this.hasOldPrefix = false;
            this.packExt = null;
        } else {
            this.base = str.substring(0, iLastIndexOf);
            String strSubstring = str.substring(iLastIndexOf + 1);
            this.packExt = getPackExt(strSubstring);
            this.hasOldPrefix = strSubstring.substring(0, strSubstring.length() - getExtension().length()).equals(getExtPrefix(true));
        }
        this.f649id = this.base.startsWith(PREFIX) ? this.base.substring(PREFIX.length()) : this.base;
    }

    public String getId() {
        return this.f649id;
    }

    public PackExt getPackExt() {
        return this.packExt;
    }

    public PackFile create(PackExt packExt) {
        return new PackFile(getParentFile(), getName(packExt));
    }

    public PackFile createForDirectory(File file) {
        return new PackFile(file, getName(false));
    }

    public PackFile createPreservedForDirectory(File file) {
        return new PackFile(file, getName(true));
    }

    private String getName(PackExt packExt) {
        return String.valueOf(this.base) + '.' + getExtPrefix(this.hasOldPrefix) + packExt.getExtension();
    }

    private String getName(boolean z) {
        return String.valueOf(this.base) + '.' + getExtPrefix(z) + getExtension();
    }

    private String getExtension() {
        PackExt packExt = this.packExt;
        return packExt == null ? "" : packExt.getExtension();
    }

    private static String getExtPrefix(boolean z) {
        return z ? "old-" : "";
    }

    private static PackExt getPackExt(String str) {
        for (PackExt packExt : PackExt.valuesCustom()) {
            if (str.endsWith(packExt.getExtension())) {
                return packExt;
            }
        }
        throw new IllegalArgumentException(MessageFormat.format(JGitText.get().unrecognizedPackExtension, str));
    }
}
