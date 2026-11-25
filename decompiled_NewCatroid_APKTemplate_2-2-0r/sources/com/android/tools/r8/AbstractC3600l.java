package com.android.tools.r8;

import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.references.Reference;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.ExceptionDiagnostic;
import com.android.tools.r8.utils.O2;
import com.android.tools.r8.utils.StringDiagnostic;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;

/* JADX INFO: renamed from: com.android.tools.r8.l, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3600l {
    static final /* synthetic */ boolean a = true;

    public static void a(Consumer consumer, String str, String str2, Origin origin, Consumer consumer2) {
        try {
            int i = Integer.parseInt(str2);
            if (i < 1) {
                consumer.accept(new StringDiagnostic("Invalid argument to " + str + ": " + str2, origin));
            } else {
                consumer2.accept(Integer.valueOf(i));
            }
        } catch (NumberFormatException unused) {
            consumer.accept(new StringDiagnostic("Invalid argument to " + str + ": " + str2, origin));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AssertionsConfiguration b(EnumC3599k enumC3599k, MethodReference methodReference, String str, AssertionsConfiguration.Builder builder) {
        AssertionsConfiguration.Builder compileTimeEnable;
        int iOrdinal = enumC3599k.ordinal();
        if (iOrdinal == 0) {
            compileTimeEnable = builder.setCompileTimeEnable();
        } else if (iOrdinal == 1) {
            compileTimeEnable = builder.setCompileTimeDisable();
        } else if (iOrdinal == 2) {
            compileTimeEnable = builder.setPassthrough();
        } else {
            if (iOrdinal != 3) {
                throw new C3405y10();
            }
            compileTimeEnable = builder.setAssertionHandler(methodReference);
        }
        return compileTimeEnable.setScopeClass(str).build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AssertionsConfiguration a(EnumC3599k enumC3599k, MethodReference methodReference, AssertionsConfiguration.Builder builder) {
        AssertionsConfiguration.Builder compileTimeEnable;
        int iOrdinal = enumC3599k.ordinal();
        if (iOrdinal == 0) {
            compileTimeEnable = builder.setCompileTimeEnable();
        } else if (iOrdinal == 1) {
            compileTimeEnable = builder.setCompileTimeDisable();
        } else if (iOrdinal == 2) {
            compileTimeEnable = builder.setPassthrough();
        } else {
            if (iOrdinal != 3) {
                throw new C3405y10();
            }
            compileTimeEnable = builder.setAssertionHandler(methodReference);
        }
        return compileTimeEnable.setScopeAll().build();
    }

    static int b(final BaseCompilerCommand.Builder builder, String str, String[] strArr, int i, Origin origin) {
        Objects.requireNonNull(builder);
        return a(new Consumer() { // from class: com.android.tools.r8.l$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                builder.error((Diagnostic) obj);
            }
        }, builder.b(), str, strArr, i, origin);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AssertionsConfiguration a(EnumC3599k enumC3599k, MethodReference methodReference, String str, AssertionsConfiguration.Builder builder) {
        AssertionsConfiguration.Builder compileTimeEnable;
        int iOrdinal = enumC3599k.ordinal();
        if (iOrdinal == 0) {
            compileTimeEnable = builder.setCompileTimeEnable();
        } else if (iOrdinal == 1) {
            compileTimeEnable = builder.setCompileTimeDisable();
        } else if (iOrdinal == 2) {
            compileTimeEnable = builder.setPassthrough();
        } else {
            if (iOrdinal != 3) {
                throw new C3405y10();
            }
            compileTimeEnable = builder.setAssertionHandler(methodReference);
        }
        return compileTimeEnable.setScopePackage(str.substring(0, str.length() - 3)).build();
    }

    private void a(BaseCompilerCommand.Builder builder, final EnumC3599k enumC3599k, final MethodReference methodReference, final String str) {
        if (str == null) {
            builder.addAssertionsConfiguration(new Function() { // from class: com.android.tools.r8.l$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.a(enumC3599k, methodReference, (AssertionsConfiguration.Builder) obj);
                }
            });
            return;
        }
        if (!a && str.length() <= 0) {
            throw new AssertionError();
        }
        if (str.endsWith("...")) {
            builder.addAssertionsConfiguration(new Function() { // from class: com.android.tools.r8.l$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.a(enumC3599k, methodReference, str, (AssertionsConfiguration.Builder) obj);
                }
            });
        } else {
            builder.addAssertionsConfiguration(new Function() { // from class: com.android.tools.r8.l$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.b(enumC3599k, methodReference, str, (AssertionsConfiguration.Builder) obj);
                }
            });
        }
    }

    final boolean a(BaseCompilerCommand.Builder builder, String str, Origin origin) {
        String strSubstring;
        EnumC3599k enumC3599k;
        MethodReference methodReferenceMethodFromDescriptor;
        if (str.startsWith("--force-enable-assertions")) {
            enumC3599k = EnumC3599k.a;
            strSubstring = str.substring(25);
        } else if (str.startsWith("--force-ea")) {
            enumC3599k = EnumC3599k.a;
            strSubstring = str.substring(10);
        } else if (str.startsWith("--force-disable-assertions")) {
            enumC3599k = EnumC3599k.b;
            strSubstring = str.substring(26);
        } else if (str.startsWith("--force-da")) {
            enumC3599k = EnumC3599k.b;
            strSubstring = str.substring(10);
        } else if (str.startsWith("--force-passthrough-assertions")) {
            enumC3599k = EnumC3599k.c;
            strSubstring = str.substring(30);
        } else if (str.startsWith("--force-pa")) {
            enumC3599k = EnumC3599k.c;
            strSubstring = str.substring(10);
        } else if (str.startsWith("--force-assertions-handler")) {
            enumC3599k = EnumC3599k.d;
            strSubstring = str.substring(26);
        } else if (str.startsWith("--force-ah")) {
            enumC3599k = EnumC3599k.d;
            strSubstring = str.substring(10);
        } else {
            strSubstring = null;
            enumC3599k = null;
        }
        if (enumC3599k != EnumC3599k.d) {
            methodReferenceMethodFromDescriptor = null;
        } else if (strSubstring.length() != 0 && (strSubstring.length() != 1 || strSubstring.charAt(0) != ':')) {
            if (strSubstring.charAt(0) != ':') {
                return false;
            }
            String strSubstring2 = strSubstring.substring(1);
            int iIndexOf = strSubstring2.indexOf(58);
            if (iIndexOf != 0) {
                String strSubstring3 = iIndexOf > 0 ? strSubstring2.substring(0, iIndexOf) : strSubstring2;
                int iLastIndexOf = strSubstring3.lastIndexOf(46);
                if (strSubstring3.length() >= 3 && iLastIndexOf > 0 && iLastIndexOf != strSubstring3.length() - 1 && C3970u0.E(strSubstring3.substring(0, iLastIndexOf))) {
                    methodReferenceMethodFromDescriptor = Reference.methodFromDescriptor(C3970u0.H(strSubstring3.substring(0, iLastIndexOf)), strSubstring3.substring(iLastIndexOf + 1), "(Ljava/lang/Throwable;)V");
                    strSubstring = strSubstring2.substring(strSubstring3.length());
                } else {
                    throw builder.fatalError(new StringDiagnostic("Invalid argument <handler method>: " + strSubstring3, origin));
                }
            } else {
                throw builder.fatalError(new StringDiagnostic("Missing required argument <handler method>", origin));
            }
        } else {
            throw builder.fatalError(new StringDiagnostic("Missing required argument <handler method>", origin));
        }
        if (enumC3599k == null) {
            return false;
        }
        if (strSubstring.length() == 0) {
            a(builder, enumC3599k, methodReferenceMethodFromDescriptor, (String) null);
            return true;
        }
        if (strSubstring.length() == 1 && strSubstring.charAt(0) == ':') {
            throw builder.fatalError(new StringDiagnostic("Missing optional argument", origin));
        }
        if (strSubstring.charAt(0) != ':') {
            return false;
        }
        String strSubstring4 = strSubstring.substring(1);
        if (strSubstring4.contains(";") || strSubstring4.contains("[") || strSubstring4.contains("/")) {
            builder.error(new StringDiagnostic("Illegal assertion scope: " + strSubstring4, origin));
        }
        a(builder, enumC3599k, methodReferenceMethodFromDescriptor, strSubstring.substring(1));
        return true;
    }

    private static DiagnosticsLevel a(Consumer consumer, String str, Origin origin) {
        if (str.equals("error")) {
            return DiagnosticsLevel.ERROR;
        }
        if (str.equals(CompilerOptions.WARNING)) {
            return DiagnosticsLevel.WARNING;
        }
        if (str.equals(CompilerOptions.INFO)) {
            return DiagnosticsLevel.INFO;
        }
        if (str.equals("none")) {
            return DiagnosticsLevel.NONE;
        }
        consumer.accept(new StringDiagnostic("Invalid diagnostics level '" + str + "'. Valid levels are 'error', 'warning', 'info' and 'none'.", origin));
        return null;
    }

    public static int a(Consumer consumer, O2 o2, String str, String[] strArr, int i, Origin origin) {
        String strSubstring;
        if (!str.startsWith("--map-diagnostics")) {
            return -1;
        }
        int i2 = i + 2;
        if (strArr.length <= i2) {
            consumer.accept(new StringDiagnostic("Missing argument(s) for " + str + ".", origin));
            return strArr.length - i;
        }
        String strSubstring2 = str.substring(17);
        if (strSubstring2.length() <= 0) {
            strSubstring = "";
        } else if (strSubstring2.length() != 1 && strSubstring2.charAt(0) == ':') {
            strSubstring = strSubstring2.substring(1);
        } else {
            consumer.accept(new StringDiagnostic("Invalid diagnostics type specification " + str + ".", origin));
            return 0;
        }
        DiagnosticsLevel diagnosticsLevelA = a(consumer, strArr[i + 1], origin);
        DiagnosticsLevel diagnosticsLevelA2 = a(consumer, strArr[i2], origin);
        if (diagnosticsLevelA == null || diagnosticsLevelA2 == null) {
            return 2;
        }
        o2.a(diagnosticsLevelA, diagnosticsLevelA2, strSubstring);
        return 2;
    }

    static int a(BaseCompilerCommand.Builder builder, String str, String[] strArr, int i, Origin origin) {
        if (!str.equals("--dumpinputtofile") && !str.equals("--dumpinputtodirectory")) {
            return -1;
        }
        int i2 = i + 1;
        if (strArr.length <= i2) {
            builder.error(new StringDiagnostic("Missing argument(s) for " + str + ".", origin));
            return strArr.length - i;
        }
        if (str.equals("--dumpinputtofile")) {
            builder.d(Paths.get(strArr[i2], new String[0]));
            return 1;
        }
        if (!a && !str.equals("--dumpinputtodirectory")) {
            throw new AssertionError();
        }
        builder.c(Paths.get(strArr[i2], new String[0]));
        return 1;
    }

    static void a(BaseCommand.Builder builder, Origin origin, String str) {
        Path path = Paths.get(str, new String[0]);
        if (Files.exists(path.resolve("lib").resolve("jrt-fs.jar"), new LinkOption[0]) || Files.exists(path.resolve("jre").resolve("lib").resolve("rt.jar"), new LinkOption[0]) || Files.exists(path.resolve("lib").resolve("rt.jar"), new LinkOption[0])) {
            try {
                builder.addLibraryResourceProvider(JdkClassFileProvider.fromJdkHome(path));
                return;
            } catch (IOException e) {
                builder.error(new ExceptionDiagnostic(e, origin));
                return;
            }
        }
        builder.addLibraryFiles(path);
    }
}
