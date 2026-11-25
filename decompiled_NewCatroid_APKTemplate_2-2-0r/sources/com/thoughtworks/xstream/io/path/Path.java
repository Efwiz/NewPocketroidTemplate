package com.thoughtworks.xstream.io.path;

import com.thoughtworks.xstream.core.util.FastStack;
import java.util.ArrayList;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/path/Path.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public class Path {
    private static final Path DOT = new Path(new String[]{"."});
    private final String[] chunks;
    private transient String pathAsString;
    private transient String pathExplicit;

    public Path(String str) {
        ArrayList arrayList = new ArrayList();
        this.pathAsString = str;
        int i = 0;
        while (true) {
            int iIndexOf = str.indexOf(47, i);
            if (iIndexOf != -1) {
                arrayList.add(normalize(str, i, iIndexOf));
                i = iIndexOf + 1;
            } else {
                arrayList.add(normalize(str, i, str.length()));
                String[] strArr = new String[arrayList.size()];
                arrayList.toArray(strArr);
                this.chunks = strArr;
                return;
            }
        }
    }

    private String normalize(String str, int i, int i2) {
        if (i2 - i > 3) {
            int i3 = i2 - 3;
            if (str.charAt(i3) == '[' && str.charAt(i2 - 2) == '1' && str.charAt(i2 - 1) == ']') {
                this.pathAsString = null;
                return str.substring(i, i3);
            }
        }
        return str.substring(i, i2);
    }

    public Path(String[] strArr) {
        this.chunks = strArr;
    }

    public String toString() {
        if (this.pathAsString == null) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < this.chunks.length; i++) {
                if (i > 0) {
                    stringBuffer.append('/');
                }
                stringBuffer.append(this.chunks[i]);
            }
            this.pathAsString = stringBuffer.toString();
        }
        return this.pathAsString;
    }

    public String explicit() {
        char cCharAt;
        if (this.pathExplicit == null) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < this.chunks.length; i++) {
                if (i > 0) {
                    stringBuffer.append('/');
                }
                String str = this.chunks[i];
                stringBuffer.append(str);
                int length = str.length();
                if (length > 0 && (cCharAt = str.charAt(length - 1)) != ']' && cCharAt != '.') {
                    stringBuffer.append("[1]");
                }
            }
            this.pathExplicit = stringBuffer.toString();
        }
        return this.pathExplicit;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Path)) {
            return false;
        }
        Path path = (Path) obj;
        if (this.chunks.length != path.chunks.length) {
            return false;
        }
        int i = 0;
        while (true) {
            String[] strArr = this.chunks;
            if (i >= strArr.length) {
                return true;
            }
            if (!strArr[i].equals(path.chunks[i])) {
                return false;
            }
            i++;
        }
    }

    public int hashCode() {
        int iHashCode = 543645643;
        int i = 0;
        while (true) {
            String[] strArr = this.chunks;
            if (i >= strArr.length) {
                return iHashCode;
            }
            iHashCode = (iHashCode * 29) + strArr[i].hashCode();
            i++;
        }
    }

    public Path relativeTo(Path path) {
        int iDepthOfPathDivergence = depthOfPathDivergence(this.chunks, path.chunks);
        String[] strArr = new String[(this.chunks.length + path.chunks.length) - (iDepthOfPathDivergence * 2)];
        int i = 0;
        int i2 = iDepthOfPathDivergence;
        while (i2 < this.chunks.length) {
            strArr[i] = "..";
            i2++;
            i++;
        }
        while (true) {
            String[] strArr2 = path.chunks;
            if (iDepthOfPathDivergence >= strArr2.length) {
                break;
            }
            strArr[i] = strArr2[iDepthOfPathDivergence];
            iDepthOfPathDivergence++;
            i++;
        }
        if (i == 0) {
            return DOT;
        }
        return new Path(strArr);
    }

    private int depthOfPathDivergence(String[] strArr, String[] strArr2) {
        int iMin = Math.min(strArr.length, strArr2.length);
        for (int i = 0; i < iMin; i++) {
            if (!strArr[i].equals(strArr2[i])) {
                return i;
            }
        }
        return iMin;
    }

    public Path apply(Path path) {
        FastStack fastStack = new FastStack(16);
        int i = 0;
        while (true) {
            String[] strArr = this.chunks;
            if (i >= strArr.length) {
                break;
            }
            fastStack.push(strArr[i]);
            i++;
        }
        int i2 = 0;
        while (true) {
            String[] strArr2 = path.chunks;
            if (i2 >= strArr2.length) {
                break;
            }
            String str = strArr2[i2];
            if (str.equals("..")) {
                fastStack.pop();
            } else if (!str.equals(".")) {
                fastStack.push(str);
            }
            i2++;
        }
        int size = fastStack.size();
        String[] strArr3 = new String[size];
        for (int i3 = 0; i3 < size; i3++) {
            strArr3[i3] = (String) fastStack.get(i3);
        }
        return new Path(strArr3);
    }

    public boolean isAncestor(Path path) {
        if (path == null || path.chunks.length < this.chunks.length) {
            return false;
        }
        int i = 0;
        while (true) {
            String[] strArr = this.chunks;
            if (i >= strArr.length) {
                return true;
            }
            if (!strArr[i].equals(path.chunks[i])) {
                return false;
            }
            i++;
        }
    }
}
