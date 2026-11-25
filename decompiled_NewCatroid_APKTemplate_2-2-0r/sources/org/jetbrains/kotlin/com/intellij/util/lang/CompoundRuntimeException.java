package org.jetbrains.kotlin.com.intellij.util.lang;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import org.jetbrains.kotlin.com.intellij.util.ExceptionUtil;

/* JADX INFO: loaded from: classes3.dex */
public final class CompoundRuntimeException extends RuntimeException {
    private final List<? extends Throwable> exceptions;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 4 || i == 5) ? 2 : 3];
        if (i == 1 || i == 2) {
            objArr[0] = "s";
        } else if (i == 3) {
            objArr[0] = "exceptionProcessor";
        } else if (i == 4 || i == 5) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/lang/CompoundRuntimeException";
        } else {
            objArr[0] = "throwables";
        }
        if (i == 4 || i == 5) {
            objArr[1] = "processAll";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/lang/CompoundRuntimeException";
        }
        if (i == 1 || i == 2) {
            objArr[2] = "printStackTrace";
        } else if (i == 3) {
            objArr[2] = "processAll";
        } else if (i != 4 && i != 5) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i != 4 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public CompoundRuntimeException(List<? extends Throwable> list) {
        if (list == null) {
            $$$reportNull$$$0(0);
        }
        this.exceptions = list;
    }

    @Override // java.lang.Throwable
    public synchronized Throwable getCause() {
        return this.exceptions.isEmpty() ? null : this.exceptions.get(0);
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return processAll(new Function() { // from class: org.jetbrains.kotlin.com.intellij.util.lang.CompoundRuntimeException$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Throwable) obj).getMessage();
            }
        }, null).toString();
    }

    @Override // java.lang.Throwable
    public String getLocalizedMessage() {
        return processAll(new Function() { // from class: org.jetbrains.kotlin.com.intellij.util.lang.CompoundRuntimeException$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Throwable) obj).getLocalizedMessage();
            }
        }, null).toString();
    }

    @Override // java.lang.Throwable
    public String toString() {
        return processAll(new Function() { // from class: org.jetbrains.kotlin.com.intellij.util.lang.CompoundRuntimeException$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Throwable) obj).toString();
            }
        }, null).toString();
    }

    @Override // java.lang.Throwable
    public void printStackTrace(final PrintStream printStream) {
        if (printStream == null) {
            $$$reportNull$$$0(1);
        }
        Function<? super Throwable, String> function = new Function() { // from class: org.jetbrains.kotlin.com.intellij.util.lang.CompoundRuntimeException$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CompoundRuntimeException.lambda$printStackTrace$0(printStream, (Throwable) obj);
            }
        };
        Objects.requireNonNull(printStream);
        processAll(function, new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.util.lang.CompoundRuntimeException$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                printStream.print((String) obj);
            }
        });
    }

    static /* synthetic */ String lambda$printStackTrace$0(PrintStream printStream, Throwable th) {
        th.printStackTrace(printStream);
        return "";
    }

    @Override // java.lang.Throwable
    public void printStackTrace(final PrintWriter printWriter) {
        if (printWriter == null) {
            $$$reportNull$$$0(2);
        }
        Function<? super Throwable, String> function = new Function() { // from class: org.jetbrains.kotlin.com.intellij.util.lang.CompoundRuntimeException$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CompoundRuntimeException.lambda$printStackTrace$1(printWriter, (Throwable) obj);
            }
        };
        Objects.requireNonNull(printWriter);
        processAll(function, new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.util.lang.CompoundRuntimeException$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                printWriter.print((String) obj);
            }
        });
    }

    static /* synthetic */ String lambda$printStackTrace$1(PrintWriter printWriter, Throwable th) {
        th.printStackTrace(printWriter);
        return "";
    }

    private CharSequence processAll(Function<? super Throwable, String> function, Consumer<? super String> consumer) {
        if (function == null) {
            $$$reportNull$$$0(3);
        }
        int i = 0;
        if (this.exceptions.size() == 1) {
            String strApply = function.apply(this.exceptions.get(0));
            if (consumer != null) {
                consumer.accept(strApply);
            }
            if (strApply == null) {
                $$$reportNull$$$0(4);
            }
            return strApply;
        }
        StringBuilder sb = new StringBuilder();
        String str = "CompositeException (" + this.exceptions.size() + " nested):\n------------------------------\n";
        if (consumer != null) {
            consumer.accept(str);
        }
        sb.append(str);
        while (i < this.exceptions.size()) {
            Throwable th = this.exceptions.get(i);
            i++;
            String str2 = "[" + i + "]: ";
            if (consumer != null) {
                consumer.accept(str2);
            }
            sb.append(str2);
            String strApply2 = function.apply(th);
            if (strApply2 == null) {
                strApply2 = "null\n";
            } else if (!strApply2.endsWith("\n")) {
                strApply2 = strApply2 + '\n';
            }
            if (consumer != null) {
                consumer.accept(strApply2);
            }
            sb.append(strApply2);
        }
        if (consumer != null) {
            consumer.accept("------------------------------\n");
        }
        sb.append("------------------------------\n");
        return sb;
    }

    public static void throwIfNotEmpty(List<? extends Throwable> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (list.size() == 1) {
            ExceptionUtil.rethrow(list.get(0));
            return;
        }
        throw new CompoundRuntimeException(list);
    }
}
