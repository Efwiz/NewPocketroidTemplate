package com.android.tools.r8.utils;

import com.android.tools.r8.CompilationFailedException;
import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.ResourceException;
import com.android.tools.r8.StringConsumer;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.internal.XJ;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.origin.PathOrigin;
import com.android.tools.r8.position.Position;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.FileSystemException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class R0 {

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public interface a {
        void run() throws ResourceException, IOException, C1367Tb;
    }

    public static String a() {
        return (String) Thread.getAllStackTraces().entrySet().stream().filter(new Predicate() { // from class: com.android.tools.r8.utils.R0$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return R0.a((Map.Entry) obj);
            }
        }).map(new Function() { // from class: com.android.tools.r8.utils.R0$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return R0.b((Map.Entry) obj);
            }
        }).flatMap(new Function() { // from class: com.android.tools.r8.utils.R0$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Stream.of((Object[]) ((StackTraceElement[]) obj));
            }
        }).map(new Function() { // from class: com.android.tools.r8.utils.R0$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((StackTraceElement) obj).toString();
            }
        }).collect(Collectors.joining(System.lineSeparator()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ StackTraceElement[] b(Map.Entry entry) {
        return (StackTraceElement[]) entry.getValue();
    }

    public static void c(O2 o2, a aVar) {
        a(o2, aVar);
    }

    public static void b(O2 o2, a aVar) throws CompilationFailedException {
        a(o2, aVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Map.Entry entry) {
        return ((Thread) entry.getKey()).getName().equals("main");
    }

    public static void a(O2 o2, final StringConsumer stringConsumer, final String str) {
        new Consumer() { // from class: com.android.tools.r8.utils.R0$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                stringConsumer.accept(str, (DiagnosticsHandler) obj);
            }
        }.accept(o2);
        o2.a();
    }

    public static void a(O2 o2, final StringConsumer stringConsumer) {
        Objects.requireNonNull(stringConsumer);
        new Consumer() { // from class: com.android.tools.r8.utils.R0$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                stringConsumer.finished((DiagnosticsHandler) obj);
            }
        }.accept(o2);
        o2.a();
    }

    public static void a(O2 o2, Consumer consumer) {
        consumer.accept(o2);
        o2.a();
    }

    public static void a(O2 o2, a aVar) {
        try {
            aVar.run();
            o2.a();
        } catch (Throwable th) {
            throw a(o2, th);
        }
    }

    private static CompilationFailedException a(O2 o2, Throwable th) {
        return (CompilationFailedException) a(o2, th, new G3() { // from class: com.android.tools.r8.utils.R0$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.utils.G3
            public final Object a(Object obj, Object obj2, Object obj3) {
                return com.android.tools.r8.M.a((String) obj, (Throwable) obj2, ((Boolean) obj3).booleanValue());
            }
        }, C3870b.class);
    }

    public static Exception a(DiagnosticsHandler diagnosticsHandler, Throwable th, G3 g3, Class cls) {
        Position positionB;
        Position position;
        Origin originUnknown = Origin.unknown();
        Position position2 = Position.UNKNOWN;
        ArrayList arrayList = new ArrayList();
        Throwable cause = th;
        boolean zIsAssignableFrom = false;
        boolean z = false;
        while (true) {
            z |= cause instanceof P;
            zIsAssignableFrom |= cls.isAssignableFrom(cause.getClass());
            Origin originA = a(cause);
            if (originA != Origin.unknown()) {
                originUnknown = originA;
            }
            if (cause instanceof C1367Tb) {
                positionB = ((C1367Tb) cause).b();
            } else if (cause instanceof T0) {
                positionB = ((T0) cause).b;
            } else if (cause instanceof C3870b) {
                positionB = ((C3870b) cause).b();
            } else {
                positionB = Position.UNKNOWN;
            }
            position = Position.UNKNOWN;
            if (positionB != position) {
                position2 = positionB;
            }
            if (cause.getCause() == null || arrayList.contains(cause)) {
                break;
            }
            arrayList.add(cause);
            cause = cause.getCause();
        }
        if (th != cause) {
            cause.addSuppressed(th);
        }
        if (!z && !zIsAssignableFrom) {
            diagnosticsHandler.error(new ExceptionDiagnostic(cause, originUnknown, position2));
        }
        StringBuilder sb = new StringBuilder("Compilation failed to complete");
        if (position2 != position) {
            sb.append(", position: ").append(position2);
        }
        if (originUnknown != Origin.unknown()) {
            sb.append(", origin: ").append(originUnknown);
        }
        Exception exc = (Exception) g3.a(sb.toString(), cause, Boolean.valueOf(z));
        StackTraceElement stackTraceElement = new StackTraceElement("Version", "fakeStackEntry", "Version_8.1.56.java", 0);
        StackTraceElement[] stackTrace = exc.getStackTrace();
        Object[] objArrB = XJ.b(stackTrace.length + 1, stackTrace);
        objArrB[0] = stackTraceElement;
        System.arraycopy(stackTrace, 0, objArrB, 1, stackTrace.length);
        exc.setStackTrace((StackTraceElement[]) objArrB);
        return exc;
    }

    private static Origin a(Throwable th) {
        if (th instanceof IOException) {
            IOException iOException = (IOException) th;
            if (iOException instanceof FileSystemException) {
                FileSystemException fileSystemException = (FileSystemException) iOException;
                if (fileSystemException.getFile() != null && !fileSystemException.getFile().isEmpty()) {
                    return new PathOrigin(Paths.get(fileSystemException.getFile(), new String[0]));
                }
            }
            return Origin.unknown();
        }
        if (th instanceof C1367Tb) {
            return ((C1367Tb) th).a();
        }
        if (th instanceof ResourceException) {
            return ((ResourceException) th).getOrigin();
        }
        if (th instanceof T0) {
            return ((T0) th).a;
        }
        if (th instanceof C3870b) {
            return ((C3870b) th).a();
        }
        return Origin.unknown();
    }

    public static void a(S0 s0) {
        String str = "Compilation failed";
        try {
            s0.run();
        } catch (CompilationFailedException e) {
            Throwable cause = e.getCause();
            PrintStream printStream = System.err;
            if (!(cause instanceof C1367Tb) && !(cause instanceof C3870b)) {
                str = "Compilation failed with an internal error.";
            }
            printStream.println(str);
            throw new RuntimeException(e);
        } catch (RuntimeException e2) {
            PrintStream printStream2 = System.err;
            if (!(e2 instanceof C1367Tb) && !(e2 instanceof C3870b)) {
                str = "Compilation failed with an internal error.";
            }
            printStream2.println(str);
            throw e2;
        }
    }

    public static RuntimeException a(ExecutionException executionException) {
        return new RuntimeException(executionException);
    }

    public static void a(Origin origin, Position position, final Runnable runnable) {
        a(origin, position, new Supplier() { // from class: com.android.tools.r8.utils.R0$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return R0.a(runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object a(Runnable runnable) {
        runnable.run();
        return null;
    }

    public static Object a(Origin origin, Position position, Supplier supplier) {
        Position positionB;
        try {
            return supplier.get();
        } catch (P e) {
            throw e;
        } catch (RuntimeException e2) {
            int i = T0.c;
            if (origin != Origin.unknown() || position != Position.UNKNOWN) {
                Origin originA = a(e2);
                if (e2 instanceof C1367Tb) {
                    positionB = ((C1367Tb) e2).b();
                } else if (e2 instanceof T0) {
                    positionB = ((T0) e2).b;
                } else if (e2 instanceof C3870b) {
                    positionB = ((C3870b) e2).b();
                } else {
                    positionB = Position.UNKNOWN;
                }
                if (origin != originA || position != positionB) {
                    throw new T0(e2, origin, position);
                }
                throw e2;
            }
            throw e2;
        }
    }
}
