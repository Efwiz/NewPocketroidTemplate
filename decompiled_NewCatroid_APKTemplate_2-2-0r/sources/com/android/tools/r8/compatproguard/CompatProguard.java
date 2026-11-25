package com.android.tools.r8.compatproguard;

import com.android.dex.DexFormat;
import com.android.tools.r8.CompatProguardCommandBuilder;
import com.android.tools.r8.CompilationFailedException;
import com.android.tools.r8.CompilationMode;
import com.android.tools.r8.MapIdProvider;
import com.android.tools.r8.OutputMode;
import com.android.tools.r8.R8;
import com.android.tools.r8.R8Command;
import com.android.tools.r8.SourceFileProvider;
import com.android.tools.r8.Version;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.internal.C1507Xl;
import com.android.tools.r8.internal.C2399ke;
import com.android.tools.r8.utils.R0;
import com.android.tools.r8.utils.S0;
import com.android.tools.r8.utils.V2;
import com.android.tools.r8.utils.X0;
import com.android.tools.r8.utils.Y1;
import io.netty.util.internal.StringUtil;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class CompatProguard {

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        public final String a;
        CompilationMode b;
        public final int c;
        public final boolean d;
        public final boolean e;
        public final boolean f;
        public final String g;
        public final MapIdProvider h;
        public final SourceFileProvider i;
        public final String j;
        public final List<String> k;
        public boolean l;
        public boolean m;

        a(AbstractC1639am abstractC1639am, String str, CompilationMode compilationMode, int i, boolean z, boolean z2, boolean z3, String str2, MapIdProvider mapIdProvider, SourceFileProvider sourceFileProvider, String str3, boolean z4, boolean z5) {
            this.a = str;
            this.b = compilationMode;
            this.c = i;
            this.d = z2;
            this.e = z3;
            this.f = z;
            this.g = str2;
            this.k = abstractC1639am;
            this.h = mapIdProvider;
            this.i = sourceFileProvider;
            this.j = str3;
            this.l = z4;
            this.m = z5;
        }

        public static a a(String[] strArr) {
            String str;
            CompilationMode compilationMode;
            String str2;
            MapIdProvider mapIdProvider;
            SourceFileProvider sourceFileProvider;
            String str3;
            int i;
            boolean z;
            boolean z2;
            boolean z3;
            boolean z4;
            boolean z5;
            int i2;
            com.android.tools.r8.compatproguard.a aVar = new com.android.tools.r8.compatproguard.a();
            C1507Xl c1507XlG = AbstractC1639am.g();
            String str4 = null;
            int i3 = 0;
            if (strArr.length > 0) {
                StringBuilder sb = new StringBuilder();
                CompilationMode compilationMode2 = null;
                String strSubstring = null;
                MapIdProvider mapIdProviderA = null;
                SourceFileProvider sourceFileProviderA = null;
                String str5 = null;
                int i4 = 0;
                boolean z6 = false;
                boolean z7 = false;
                boolean z8 = false;
                boolean z9 = false;
                int iIntValue = 1;
                boolean z10 = true;
                while (i4 < strArr.length) {
                    String str6 = strArr[i4];
                    String str7 = str4;
                    if (str6.charAt(i3) == '-') {
                        if (str6.equals("-h") || str6.equals("--help")) {
                            str4 = str7;
                            i2 = 1;
                            z8 = true;
                        } else if (str6.equals("--debug")) {
                            if (compilationMode2 == CompilationMode.RELEASE) {
                                throw new C1367Tb("Cannot compile in both --debug and --release mode.");
                            }
                            compilationMode2 = CompilationMode.DEBUG;
                        } else if (str6.equals("--release")) {
                            if (compilationMode2 == CompilationMode.DEBUG) {
                                throw new C1367Tb("Cannot compile in both --debug and --release mode.");
                            }
                            compilationMode2 = CompilationMode.RELEASE;
                        } else if (str6.equals("--min-api")) {
                            i4++;
                            iIntValue = Integer.valueOf(strArr[i4]).intValue();
                        } else if (str6.equals("--force-proguard-compatibility")) {
                            str4 = str7;
                            i2 = 1;
                            z6 = true;
                        } else if (str6.equals("--no-data-resources")) {
                            str4 = str7;
                            i2 = 1;
                            z10 = false;
                        } else if (str6.equals("--output")) {
                            i4++;
                            str4 = strArr[i4];
                            i2 = 1;
                        } else if (str6.equals("--multi-dex")) {
                            str4 = str7;
                            i2 = 1;
                            z7 = true;
                        } else if (str6.equals("--main-dex-list")) {
                            i4++;
                            strSubstring = strArr[i4];
                        } else if (str6.startsWith("--main-dex-list=")) {
                            strSubstring = str6.substring(16);
                        } else if (str6.equals("--map-id-template")) {
                            i4++;
                            mapIdProviderA = Y1.a(strArr[i4], aVar);
                        } else if (str6.equals("--source-file-template")) {
                            i4++;
                            sourceFileProviderA = V2.a(strArr[i4], aVar);
                        } else if (str6.equals("--deps-file")) {
                            i4++;
                            str5 = strArr[i4];
                        } else if (str6.equals("--no-vertical-class-merging")) {
                            str4 = str7;
                            i2 = 1;
                            z9 = true;
                        } else if (!str6.equals("--minimal-main-dex") && !str6.equals("--core-library") && !str6.equals("--no-locals")) {
                            if (str6.equals("-outjars")) {
                                throw new C1367Tb("Proguard argument -outjar is not supported. Use R8 compatible --output flag");
                            }
                            if (sb.length() > 0) {
                                c1507XlG.a(sb.toString());
                            }
                            sb = new StringBuilder(str6);
                        }
                        i4 += i2;
                        i3 = 0;
                    } else {
                        if (sb.length() > 0) {
                            sb.append(StringUtil.SPACE);
                        }
                        sb.append(str6);
                    }
                    str4 = str7;
                    i2 = 1;
                    i4 += i2;
                    i3 = 0;
                }
                String str8 = str4;
                if (sb.length() > 0) {
                    c1507XlG.a(sb.toString());
                }
                compilationMode = compilationMode2;
                str2 = strSubstring;
                mapIdProvider = mapIdProviderA;
                sourceFileProvider = sourceFileProviderA;
                str3 = str5;
                i = iIntValue;
                z3 = z6;
                z = z10;
                z2 = z7;
                z4 = z8;
                z5 = z9;
                str = str8;
            } else {
                str = null;
                compilationMode = null;
                str2 = null;
                mapIdProvider = null;
                sourceFileProvider = null;
                str3 = null;
                i = 1;
                z = true;
                z2 = false;
                z3 = false;
                z4 = false;
                z5 = false;
            }
            return new a(c1507XlG.a(), str, compilationMode, i, z2, z3, z, str2, mapIdProvider, sourceFileProvider, str3, z4, z5);
        }
    }

    private static void a() {
        System.out.println("CompatProguard " + Version.getVersionString());
    }

    public static void main(final String[] strArr) {
        R0.a(new S0() { // from class: com.android.tools.r8.compatproguard.CompatProguard$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.S0
            public final void run() throws CompilationFailedException {
                CompatProguard.a(strArr);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static void a(String[] strArr) throws CompilationFailedException {
        a aVarA = a.a(strArr);
        if (!aVarA.l && aVarA.a != null) {
            CompatProguardCommandBuilder compatProguardCommandBuilder = new CompatProguardCommandBuilder(aVarA.d, aVarA.m);
            compatProguardCommandBuilder.setOutput(Paths.get(aVarA.a, new String[0]), OutputMode.DexIndexed, aVarA.e).addProguardConfiguration(aVarA.k, com.android.tools.r8.origin.a.e).setMinApiLevel(aVarA.c).setMapIdProvider(aVarA.h).setSourceFileProvider(aVarA.i);
            CompilationMode compilationMode = aVarA.b;
            if (compilationMode != null) {
                compatProguardCommandBuilder.setMode(compilationMode);
            }
            String str = aVarA.g;
            if (str != null) {
                compatProguardCommandBuilder.addMainDexListFiles(Paths.get(str, new String[0]));
            }
            if (aVarA.j != null) {
                Path pathResolve = Paths.get(aVarA.a, new String[0]);
                if (!X0.a(pathResolve)) {
                    pathResolve = pathResolve.resolve(DexFormat.DEX_IN_JAR_NAME);
                }
                compatProguardCommandBuilder.setInputDependencyGraphConsumer(new C2399ke(aVarA.j, pathResolve));
            }
            R8.run((R8Command) compatProguardCommandBuilder.build());
            return;
        }
        System.out.println();
        a();
        System.out.println();
        System.out.println("compatproguard [options] --output <dir> <proguard-config>*");
        System.out.println();
        System.out.println("Where options are:");
        System.out.println("-h/--help            : print this help message");
        System.out.println("--release            : compile without debugging information (default).");
        System.out.println("--debug              : compile with debugging information.");
        System.out.println("--min-api n          : specify the targeted min android api level");
        System.out.println("--main-dex-list list : specify main dex list for multi-dexing");
        System.out.println("--minimal-main-dex   : ignored (provided for compatibility)");
        System.out.println("--multi-dex          : ignored (provided for compatibility)");
        System.out.println("--no-locals          : ignored (provided for compatibility)");
        System.out.println("--core-library       : ignored (provided for compatibility)");
        System.out.println("--force-proguard-compatibility : Proguard compatibility mode");
        System.out.println("--no-data-resources  : ignore all data resources");
    }
}
