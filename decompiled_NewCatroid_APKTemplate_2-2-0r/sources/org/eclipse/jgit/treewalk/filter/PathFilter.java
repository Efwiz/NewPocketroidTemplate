package org.eclipse.jgit.treewalk.filter;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.treewalk.TreeWalk;

/* JADX INFO: loaded from: classes14.dex */
public class PathFilter extends TreeFilter {
    final byte[] pathRaw;
    final String pathStr;

    @Override // org.eclipse.jgit.treewalk.filter.TreeFilter
    public PathFilter clone() {
        return this;
    }

    public static PathFilter create(String str) {
        while (str.endsWith("/")) {
            str = str.substring(0, str.length() - 1);
        }
        if (str.length() == 0) {
            throw new IllegalArgumentException(JGitText.get().emptyPathNotPermitted);
        }
        return new PathFilter(str);
    }

    private PathFilter(String str) {
        this.pathStr = str;
        this.pathRaw = Constants.encode(str);
    }

    public String getPath() {
        return this.pathStr;
    }

    @Override // org.eclipse.jgit.treewalk.filter.TreeFilter
    public boolean include(TreeWalk treeWalk) {
        return matchFilter(treeWalk) <= 0;
    }

    @Override // org.eclipse.jgit.treewalk.filter.TreeFilter
    public int matchFilter(TreeWalk treeWalk) {
        byte[] bArr = this.pathRaw;
        return treeWalk.isPathMatch(bArr, bArr.length);
    }

    @Override // org.eclipse.jgit.treewalk.filter.TreeFilter
    public boolean shouldBeRecursive() {
        for (byte b : this.pathRaw) {
            if (b == 47) {
                return true;
            }
        }
        return false;
    }

    @Override // org.eclipse.jgit.treewalk.filter.TreeFilter
    public String toString() {
        return "PATH(\"" + this.pathStr + "\")";
    }

    public boolean isDone(TreeWalk treeWalk) {
        return this.pathRaw.length == treeWalk.getPathLength();
    }
}
