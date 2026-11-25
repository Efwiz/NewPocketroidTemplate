package com.android.tools.r8;

import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.utils.EnumC3891f;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class S implements ParseFlagInfo {
    static final /* synthetic */ boolean d = true;
    private final String a;
    private final List b;
    private final List c;

    public S(String str, List list, List list2) {
        boolean z = d;
        if (!z && str == null) {
            throw new AssertionError();
        }
        if (!z && list == null) {
            throw new AssertionError();
        }
        if (!z && list2 == null) {
            throw new AssertionError();
        }
        this.a = str;
        this.b = list;
        this.c = list2;
    }

    public static S a(boolean z) {
        String[] strArr = new String[1];
        strArr[0] = "Compile with debugging information" + (z ? " (default)" : "") + ".";
        return a("--debug", strArr);
    }

    public static S b(boolean z) {
        String[] strArr = new String[1];
        strArr[0] = "Compile without debugging information" + (z ? " (default)" : "") + ".";
        return a("--release", strArr);
    }

    public static S c() {
        AbstractC1639am abstractC1639amA = AbstractC1639am.a("<from-level>", "<to-level>");
        List listAsList = Arrays.asList("Map diagnostics of <type> (default any) reported as", "<from-level> to <to-level> where <from-level> and", "<to-level> are one of 'info', 'warning', or 'error'", "and the optional <type> is either the simple or", "fully qualified Java type name of a diagnostic.", "If <type> is unspecified, all diagnostics at ", "<from-level> will be mapped.", "Note that fatal compiler errors cannot be mapped.");
        StringBuilder sb = new StringBuilder("--map-diagnostics[:<type>]");
        Iterator it = abstractC1639amA.iterator();
        while (it.hasNext()) {
            sb.append(" ").append((String) it.next());
        }
        return new S(sb.toString(), Collections.emptyList(), listAsList);
    }

    public static S d() {
        return a("--min-api", "<number>", "Minimum Android API level compatibility (default: " + EnumC3891f.b().d() + ").");
    }

    @Override // com.android.tools.r8.ParseFlagInfo
    public final String getFlagFormat() {
        return this.a;
    }

    @Override // com.android.tools.r8.ParseFlagInfo
    public final List getFlagFormatAlternatives() {
        return this.b;
    }

    @Override // com.android.tools.r8.ParseFlagInfo
    public final List getFlagHelp() {
        return this.c;
    }

    public static S a(String str) {
        return a("--version", "Print the version of " + str + ".");
    }

    public static AbstractC1639am a() {
        return AbstractC1639am.a(new S("--force-enable-assertions[:[<class name>|<package name>...]]", Collections.singletonList("--force-ea[:[<class name>|<package name>...]]"), Arrays.asList("Forcefully enable javac generated assertion code.")), new S("--force-disable-assertions[:[<class name>|<package name>...]]", Collections.singletonList("--force-da[:[<class name>|<package name>...]]"), Arrays.asList("Forcefully disable javac generated assertion code.", "This is the default handling of javac assertion code", "when generating DEX file format.")), new S("--force-passthrough-assertions[:[<class name>|<package name>...]]", Collections.singletonList("--force-pa[:[<class name>|<package name>...]]"), Arrays.asList("Don't change javac generated assertion code. This", "is the default handling of javac assertion code when", "generating class file format.")), new S("--force-assertions-handler:<handler method>[:[<class name>|<package name>...]]", Collections.singletonList("--force-ah:<handler method>[:[<class name>|<package name>...]]"), Arrays.asList("Change javac and kotlinc generated assertion code", "to invoke the method <handler method> with each", "assertion error instead of throwing it.", "The <handler method> is specified as a class name", "followed by a dot and the method name.", "The handler method must take a single argument of", "type java.lang.Throwable and have return type void.")));
    }

    public static S b() {
        return a("--dex", "Compile program to DEX file format (default).");
    }

    public static S a(String str, String... strArr) {
        List listEmptyList = Collections.emptyList();
        List listAsList = Arrays.asList(strArr);
        StringBuilder sb = new StringBuilder(str);
        Iterator it = listEmptyList.iterator();
        while (it.hasNext()) {
            sb.append(" ").append((String) it.next());
        }
        return new S(sb.toString(), Collections.emptyList(), listAsList);
    }

    public static S a(String str, String str2, String... strArr) {
        List listSingletonList = Collections.singletonList(str2);
        List listAsList = Arrays.asList(strArr);
        StringBuilder sb = new StringBuilder(str);
        Iterator it = listSingletonList.iterator();
        while (it.hasNext()) {
            sb.append(" ").append((String) it.next());
        }
        return new S(sb.toString(), Collections.emptyList(), listAsList);
    }
}
