package org.jetbrains.kotlin.incremental;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

/* JADX INFO: compiled from: CompilationTransaction.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u001aE\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u0001*\u00020\u00022\u0014\b\u0002\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u00010\u00042\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u00010\u0004H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u0007\u001a \u0010\b\u001a\u00020\t*\u00020\u00022\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\r\u001a\u001a\u0010\u000e\u001a\u00020\t*\u00020\u00022\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u0010\u001a\u001a\u0010\u0011\u001a\u00020\t*\u00020\u00022\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u0013\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0014"}, d2 = {"runWithin", "R", "Lorg/jetbrains/kotlin/incremental/CompilationTransaction;", "exceptionTransformer", "Lkotlin/Function1;", "", "body", "(Lorg/jetbrains/kotlin/incremental/CompilationTransaction;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "write", "", "file", "Ljava/nio/file/Path;", "writeAction", "Lkotlin/Function0;", "writeBytes", "array", "", "writeText", "text", "", "kotlin-build-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CompilationTransactionKt {
    public static final void write(CompilationTransaction compilationTransaction, Path file, Function0<Unit> writeAction) {
        Intrinsics.checkNotNullParameter(compilationTransaction, "<this>");
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(writeAction, "writeAction");
        compilationTransaction.registerAddedOrChangedFile(file);
        writeAction.invoke();
    }

    public static final void writeText(CompilationTransaction compilationTransaction, Path file, String text) {
        Intrinsics.checkNotNullParameter(compilationTransaction, "<this>");
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(text, "text");
        byte[] bytes = text.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        writeBytes(compilationTransaction, file, bytes);
    }

    public static final void writeBytes(CompilationTransaction compilationTransaction, final Path file, final byte[] array) {
        Intrinsics.checkNotNullParameter(compilationTransaction, "<this>");
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(array, "array");
        write(compilationTransaction, file, new Function0<Unit>() { // from class: org.jetbrains.kotlin.incremental.CompilationTransactionKt.writeBytes.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() throws IOException {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() throws IOException {
                if (!Files.exists(file.getParent(), new LinkOption[0])) {
                    Files.createDirectories(file.getParent(), new FileAttribute[0]);
                }
                Files.write(file, array, new OpenOption[0]);
            }
        });
    }

    /* JADX WARN: Code duplicated, block: B:25:0x0047 A[Catch: all -> 0x004e, TryCatch #4 {all -> 0x004e, blocks: (B:6:0x0017, B:10:0x0024, B:23:0x0042, B:25:0x0047, B:26:0x004a, B:27:0x004d), top: B:41:0x0017 }] */
    public static /* synthetic */ Object runWithin$default(CompilationTransaction compilationTransaction, Function1 exceptionTransformer, Function1 body, int i, Object obj) throws Throwable {
        Object objM2898constructorimpl;
        if ((i & 1) != 0) {
            exceptionTransformer = new Function1() { // from class: org.jetbrains.kotlin.incremental.CompilationTransactionKt.runWithin.1
                @Override // kotlin.jvm.functions.Function1
                public final Void invoke(Throwable it) throws Throwable {
                    Intrinsics.checkNotNullParameter(it, "it");
                    throw it;
                }
            };
        }
        Intrinsics.checkNotNullParameter(compilationTransaction, "<this>");
        Intrinsics.checkNotNullParameter(exceptionTransformer, "exceptionTransformer");
        Intrinsics.checkNotNullParameter(body, "body");
        try {
            Result.Companion companion = Result.INSTANCE;
            CompilationTransaction compilationTransaction2 = compilationTransaction;
            boolean z = false;
            try {
                try {
                    Object objInvoke = body.invoke(compilationTransaction);
                    InlineMarker.finallyStart(1);
                    compilationTransaction2.close();
                    InlineMarker.finallyEnd(1);
                    objM2898constructorimpl = Result.m2898constructorimpl(objInvoke);
                    Throwable thM2901exceptionOrNullimpl = Result.m2901exceptionOrNullimpl(objM2898constructorimpl);
                    if (thM2901exceptionOrNullimpl != null) {
                        Result.Companion companion2 = Result.INSTANCE;
                        objM2898constructorimpl = Result.m2898constructorimpl(exceptionTransformer.invoke(thM2901exceptionOrNullimpl));
                    }
                    ResultKt.throwOnFailure(objM2898constructorimpl);
                    return objM2898constructorimpl;
                } catch (Throwable th) {
                    compilationTransaction.setExecutionThrowable(th);
                    throw th;
                }
            } catch (Exception e) {
                try {
                    try {
                        compilationTransaction2.close();
                    } catch (Throwable th2) {
                        th = th2;
                        z = true;
                        InlineMarker.finallyStart(1);
                        if (!z) {
                            compilationTransaction2.close();
                        }
                        InlineMarker.finallyEnd(1);
                        throw th;
                    }
                } catch (Exception unused) {
                }
                throw e;
            } catch (Throwable th3) {
                th = th3;
                InlineMarker.finallyStart(1);
                if (!z) {
                    compilationTransaction2.close();
                }
                InlineMarker.finallyEnd(1);
                throw th;
            }
        } catch (Throwable th4) {
            Result.Companion companion3 = Result.INSTANCE;
            objM2898constructorimpl = Result.m2898constructorimpl(ResultKt.createFailure(th4));
        }
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0040 A[Catch: all -> 0x0047, TryCatch #3 {all -> 0x0047, blocks: (B:3:0x000f, B:7:0x001d, B:20:0x003b, B:22:0x0040, B:23:0x0043, B:24:0x0046), top: B:37:0x000f }] */
    public static final <R> R runWithin(CompilationTransaction compilationTransaction, Function1<? super Throwable, ? extends R> exceptionTransformer, Function1<? super CompilationTransaction, ? extends R> body) throws Throwable {
        Object objM2898constructorimpl;
        Intrinsics.checkNotNullParameter(compilationTransaction, "<this>");
        Intrinsics.checkNotNullParameter(exceptionTransformer, "exceptionTransformer");
        Intrinsics.checkNotNullParameter(body, "body");
        try {
            Result.Companion companion = Result.INSTANCE;
            CompilationTransaction compilationTransaction2 = compilationTransaction;
            boolean z = false;
            try {
                try {
                    R rInvoke = body.invoke(compilationTransaction);
                    InlineMarker.finallyStart(1);
                    compilationTransaction2.close();
                    InlineMarker.finallyEnd(1);
                    objM2898constructorimpl = (R) Result.m2898constructorimpl(rInvoke);
                    Throwable thM2901exceptionOrNullimpl = Result.m2901exceptionOrNullimpl(objM2898constructorimpl);
                    if (thM2901exceptionOrNullimpl != null) {
                        Result.Companion companion2 = Result.INSTANCE;
                        objM2898constructorimpl = (R) Result.m2898constructorimpl(exceptionTransformer.invoke(thM2901exceptionOrNullimpl));
                    }
                    ResultKt.throwOnFailure(objM2898constructorimpl);
                    return (R) objM2898constructorimpl;
                } catch (Throwable th) {
                    compilationTransaction.setExecutionThrowable(th);
                    throw th;
                }
            } catch (Exception e) {
                try {
                    try {
                        compilationTransaction2.close();
                    } catch (Exception unused) {
                    }
                    throw e;
                } catch (Throwable th2) {
                    th = th2;
                    z = true;
                    InlineMarker.finallyStart(1);
                    if (!z) {
                        compilationTransaction2.close();
                    }
                    InlineMarker.finallyEnd(1);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                InlineMarker.finallyStart(1);
                if (!z) {
                    compilationTransaction2.close();
                }
                InlineMarker.finallyEnd(1);
                throw th;
            }
        } catch (Throwable th4) {
            Result.Companion companion3 = Result.INSTANCE;
            objM2898constructorimpl = Result.m2898constructorimpl(ResultKt.createFailure(th4));
        }
    }
}
