package com.android.tools.r8.relocator;

import com.android.tools.r8.CompilationFailedException;
import com.android.tools.r8.Version;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class a {
    static void a(String[] strArr) throws CompilationFailedException {
        RelocatorCommand relocatorCommandBuild = RelocatorCommand.Builder.parse(strArr, com.android.tools.r8.origin.a.e).build();
        if (relocatorCommandBuild.isPrintHelp()) {
            System.out.println(RelocatorCommand.j);
        } else {
            if (!relocatorCommandBuild.isPrintVersion()) {
                Relocator.run(relocatorCommandBuild);
                return;
            }
            System.out.println("Relocator " + Version.getVersionString());
        }
    }
}
