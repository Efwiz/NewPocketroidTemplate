package org.eclipse.jdt.internal.compiler.batch;

import java.io.File;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.env.AccessRestriction;
import org.eclipse.jdt.internal.compiler.env.AccessRuleSet;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;

/* JADX INFO: loaded from: classes14.dex */
public abstract class ClasspathLocation implements FileSystem.Classpath, SuffixConstants {
    public static final int BINARY = 2;
    public static final int SOURCE = 1;
    public AccessRuleSet accessRuleSet;
    public String destinationPath;
    char[] normalizedPath;
    String path;

    public int getMode() {
        return 3;
    }

    protected ClasspathLocation(AccessRuleSet accessRuleSet, String str) {
        this.accessRuleSet = accessRuleSet;
        this.destinationPath = str;
    }

    protected AccessRestriction fetchAccessRestriction(String str) {
        if (this.accessRuleSet == null) {
            return null;
        }
        char[] charArray = str.substring(0, str.length() - SUFFIX_CLASS.length).toCharArray();
        if (File.separatorChar == '\\') {
            CharOperation.replace(charArray, File.separatorChar, '/');
        }
        return this.accessRuleSet.getViolatedRestriction(charArray);
    }

    public int hashCode() {
        int mode = (getMode() + 31) * 31;
        String str = this.path;
        return mode + (str == null ? 0 : str.hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ClasspathLocation classpathLocation = (ClasspathLocation) obj;
        String path = getPath();
        String path2 = classpathLocation.getPath();
        if (path == null) {
            if (path2 != null) {
                return false;
            }
        } else if (!path.equals(path2)) {
            return false;
        }
        return getMode() == classpathLocation.getMode();
    }

    @Override // org.eclipse.jdt.internal.compiler.batch.FileSystem.Classpath
    public String getPath() {
        return this.path;
    }
}
