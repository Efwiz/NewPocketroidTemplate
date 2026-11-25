package net.mgsx.gltf.scene3d.utils;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;

/* JADX INFO: loaded from: classes12.dex */
public class ShaderParser {
    private static final String includeAfter = ">";
    private static final String includeBefore = "#include <";

    public static String parse(FileHandle fileHandle) {
        String str = "";
        for (String str2 : fileHandle.readString().split("\n")) {
            String strTrim = str2.trim();
            if (!strTrim.startsWith(includeBefore)) {
                str = str + str2 + "\n";
            } else {
                int iIndexOf = strTrim.indexOf(includeAfter, includeBefore.length());
                if (iIndexOf < 0) {
                    throw new GdxRuntimeException("malformed include: " + strTrim);
                }
                String strSubstring = strTrim.substring(includeBefore.length(), iIndexOf);
                str = (str + "\n//////// " + strSubstring + "\n") + parse(fileHandle.sibling(strSubstring));
            }
        }
        return str;
    }
}
