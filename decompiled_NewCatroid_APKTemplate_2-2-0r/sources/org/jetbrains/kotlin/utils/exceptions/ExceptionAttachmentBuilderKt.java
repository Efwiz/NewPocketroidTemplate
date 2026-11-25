package org.jetbrains.kotlin.utils.exceptions;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;

/* JADX INFO: compiled from: ExceptionAttachmentBuilder.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000N\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001aK\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0010\b\u0002\u0010\u0004\u001a\n\u0018\u00010\u0005j\u0004\u0018\u0001`\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\u0019\b\u0002\u0010\b\u001a\u0013\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t¢\u0006\u0002\b\fH\u0086\bø\u0001\u0000\u001aR\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00030\u00102\b\b\u0002\u0010\u0007\u001a\u00020\u00032\u0019\b\u0002\u0010\b\u001a\u0013\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t¢\u0006\u0002\b\fH\u0086\bø\u0001\u0000\u0082\u0002\b\n\u0006\b\u0000\u001a\u0002\u0010\u0001\u001aK\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u00032\u0010\b\u0002\u0010\u0004\u001a\n\u0018\u00010\u0005j\u0004\u0018\u0001`\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\u0019\b\u0002\u0010\b\u001a\u0013\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t¢\u0006\u0002\b\fH\u0086\bø\u0001\u0000\u001aR\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00030\u00102\b\b\u0002\u0010\u0007\u001a\u00020\u00032\u0019\b\u0002\u0010\b\u001a\u0013\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t¢\u0006\u0002\b\fH\u0086\bø\u0001\u0000\u0082\u0002\b\n\u0006\b\u0000\u001a\u0002\u0010\u0001\u001aE\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u00032\n\u0010\u0015\u001a\u00060\u0005j\u0002`\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\u0019\b\u0002\u0010\b\u001a\u0013\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t¢\u0006\u0002\b\fH\u0086\bø\u0001\u0000\u001a3\u0010\b\u001a\u00020\u0016*\u00020\u00162\b\b\u0002\u0010\u0017\u001a\u00020\u00032\u0017\u0010\u0018\u001a\u0013\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t¢\u0006\u0002\b\fH\u0086\bø\u0001\u0000\u001aO\u0010\u0019\u001a\u00020\u000b*\u00020\u001a2\u0006\u0010\u0002\u001a\u00020\u00032\u0010\b\u0002\u0010\u0004\u001a\n\u0018\u00010\u0005j\u0004\u0018\u0001`\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\u0019\b\u0002\u0010\b\u001a\u0013\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t¢\u0006\u0002\b\fH\u0086\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u001b"}, d2 = {"buildErrorWithAttachment", "", CrashHianalyticsData.MESSAGE, "", "cause", "Ljava/lang/Exception;", "Lkotlin/Exception;", "attachmentName", "buildAttachment", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/utils/exceptions/ExceptionAttachmentBuilder;", "", "Lkotlin/ExtensionFunctionType;", "checkWithAttachment", "condition", "", "Lkotlin/Function0;", "errorWithAttachment", "", "requireWithAttachment", "rethrowExceptionWithDetails", K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_EXCEPTION, "Lorg/jetbrains/kotlin/utils/exceptions/KotlinExceptionWithAttachments;", "name", "buildContent", "logErrorWithAttachment", "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/Logger;", "util"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ExceptionAttachmentBuilderKt {
    public static /* synthetic */ KotlinExceptionWithAttachments buildAttachment$default(KotlinExceptionWithAttachments kotlinExceptionWithAttachments, String name2, Function1 buildContent, int i, Object obj) {
        if ((i & 1) != 0) {
            name2 = "info.txt";
        }
        Intrinsics.checkNotNullParameter(kotlinExceptionWithAttachments, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(buildContent, "buildContent");
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        buildContent.invoke(exceptionAttachmentBuilder);
        return kotlinExceptionWithAttachments.withAttachment(name2, exceptionAttachmentBuilder.buildString());
    }

    public static final KotlinExceptionWithAttachments buildAttachment(KotlinExceptionWithAttachments kotlinExceptionWithAttachments, String name2, Function1<? super ExceptionAttachmentBuilder, Unit> buildContent) {
        Intrinsics.checkNotNullParameter(kotlinExceptionWithAttachments, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(buildContent, "buildContent");
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        buildContent.invoke(exceptionAttachmentBuilder);
        return kotlinExceptionWithAttachments.withAttachment(name2, exceptionAttachmentBuilder.buildString());
    }

    public static /* synthetic */ void logErrorWithAttachment$default(Logger logger, String message, Exception exc, String attachmentName, Function1 buildAttachment, int i, Object obj) {
        if ((i & 2) != 0) {
            exc = null;
        }
        if ((i & 4) != 0) {
            attachmentName = "info.txt";
        }
        if ((i & 8) != 0) {
            buildAttachment = new Function1<ExceptionAttachmentBuilder, Unit>() { // from class: org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilderKt.logErrorWithAttachment.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ExceptionAttachmentBuilder exceptionAttachmentBuilder) {
                    Intrinsics.checkNotNullParameter(exceptionAttachmentBuilder, "$this$null");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ExceptionAttachmentBuilder exceptionAttachmentBuilder) {
                    invoke2(exceptionAttachmentBuilder);
                    return Unit.INSTANCE;
                }
            };
        }
        Intrinsics.checkNotNullParameter(logger, "<this>");
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(attachmentName, "attachmentName");
        Intrinsics.checkNotNullParameter(buildAttachment, "buildAttachment");
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments(message, exc);
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        buildAttachment.invoke(exceptionAttachmentBuilder);
        Unit unit = Unit.INSTANCE;
        kotlinIllegalArgumentExceptionWithAttachments.withAttachment(attachmentName, exceptionAttachmentBuilder.buildString());
        logger.error((Throwable) kotlinIllegalArgumentExceptionWithAttachments);
    }

    public static /* synthetic */ Throwable buildErrorWithAttachment$default(String message, Exception exc, String attachmentName, Function1 buildAttachment, int i, Object obj) {
        if ((i & 2) != 0) {
            exc = null;
        }
        if ((i & 4) != 0) {
            attachmentName = "info.txt";
        }
        if ((i & 8) != 0) {
            buildAttachment = new Function1<ExceptionAttachmentBuilder, Unit>() { // from class: org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilderKt.buildErrorWithAttachment.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ExceptionAttachmentBuilder exceptionAttachmentBuilder) {
                    Intrinsics.checkNotNullParameter(exceptionAttachmentBuilder, "$this$null");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ExceptionAttachmentBuilder exceptionAttachmentBuilder) {
                    invoke2(exceptionAttachmentBuilder);
                    return Unit.INSTANCE;
                }
            };
        }
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(attachmentName, "attachmentName");
        Intrinsics.checkNotNullParameter(buildAttachment, "buildAttachment");
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments(message, exc);
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        buildAttachment.invoke(exceptionAttachmentBuilder);
        Unit unit = Unit.INSTANCE;
        kotlinIllegalArgumentExceptionWithAttachments.withAttachment(attachmentName, exceptionAttachmentBuilder.buildString());
        return kotlinIllegalArgumentExceptionWithAttachments;
    }

    public static final Throwable buildErrorWithAttachment(String message, Exception exc, String attachmentName, Function1<? super ExceptionAttachmentBuilder, Unit> buildAttachment) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(attachmentName, "attachmentName");
        Intrinsics.checkNotNullParameter(buildAttachment, "buildAttachment");
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments(message, exc);
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        buildAttachment.invoke(exceptionAttachmentBuilder);
        Unit unit = Unit.INSTANCE;
        kotlinIllegalArgumentExceptionWithAttachments.withAttachment(attachmentName, exceptionAttachmentBuilder.buildString());
        return kotlinIllegalArgumentExceptionWithAttachments;
    }

    public static /* synthetic */ Void errorWithAttachment$default(String message, Exception exc, String attachmentName, Function1 buildAttachment, int i, Object obj) {
        if ((i & 2) != 0) {
            exc = null;
        }
        if ((i & 4) != 0) {
            attachmentName = "info.txt";
        }
        if ((i & 8) != 0) {
            buildAttachment = new Function1<ExceptionAttachmentBuilder, Unit>() { // from class: org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilderKt.errorWithAttachment.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ExceptionAttachmentBuilder exceptionAttachmentBuilder) {
                    Intrinsics.checkNotNullParameter(exceptionAttachmentBuilder, "$this$null");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ExceptionAttachmentBuilder exceptionAttachmentBuilder) {
                    invoke2(exceptionAttachmentBuilder);
                    return Unit.INSTANCE;
                }
            };
        }
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(attachmentName, "attachmentName");
        Intrinsics.checkNotNullParameter(buildAttachment, "buildAttachment");
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments(message, exc);
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        buildAttachment.invoke(exceptionAttachmentBuilder);
        Unit unit = Unit.INSTANCE;
        kotlinIllegalArgumentExceptionWithAttachments.withAttachment(attachmentName, exceptionAttachmentBuilder.buildString());
        throw kotlinIllegalArgumentExceptionWithAttachments;
    }

    public static /* synthetic */ Void rethrowExceptionWithDetails$default(String message, Exception exception, String attachmentName, Function1 buildAttachment, int i, Object obj) throws Exception {
        if ((i & 4) != 0) {
            attachmentName = "info.txt";
        }
        if ((i & 8) != 0) {
            buildAttachment = new Function1<ExceptionAttachmentBuilder, Unit>() { // from class: org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilderKt.rethrowExceptionWithDetails.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ExceptionAttachmentBuilder exceptionAttachmentBuilder) {
                    Intrinsics.checkNotNullParameter(exceptionAttachmentBuilder, "$this$null");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ExceptionAttachmentBuilder exceptionAttachmentBuilder) {
                    invoke2(exceptionAttachmentBuilder);
                    return Unit.INSTANCE;
                }
            };
        }
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(exception, "exception");
        Intrinsics.checkNotNullParameter(attachmentName, "attachmentName");
        Intrinsics.checkNotNullParameter(buildAttachment, "buildAttachment");
        Exception exc = exception;
        if (PlatformExceptionUtilsKt.shouldIjPlatformExceptionBeRethrown(exc)) {
            throw exception;
        }
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments(message, exc);
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        buildAttachment.invoke(exceptionAttachmentBuilder);
        Unit unit = Unit.INSTANCE;
        kotlinIllegalArgumentExceptionWithAttachments.withAttachment(attachmentName, exceptionAttachmentBuilder.buildString());
        throw kotlinIllegalArgumentExceptionWithAttachments;
    }

    public static final Void rethrowExceptionWithDetails(String message, Exception exception, String attachmentName, Function1<? super ExceptionAttachmentBuilder, Unit> buildAttachment) throws Exception {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(exception, "exception");
        Intrinsics.checkNotNullParameter(attachmentName, "attachmentName");
        Intrinsics.checkNotNullParameter(buildAttachment, "buildAttachment");
        Exception exc = exception;
        if (PlatformExceptionUtilsKt.shouldIjPlatformExceptionBeRethrown(exc)) {
            throw exception;
        }
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments(message, exc);
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        buildAttachment.invoke(exceptionAttachmentBuilder);
        Unit unit = Unit.INSTANCE;
        kotlinIllegalArgumentExceptionWithAttachments.withAttachment(attachmentName, exceptionAttachmentBuilder.buildString());
        throw kotlinIllegalArgumentExceptionWithAttachments;
    }

    public static /* synthetic */ void checkWithAttachment$default(boolean z, Function0 message, String attachmentName, Function1 buildAttachment, int i, Object obj) {
        if ((i & 4) != 0) {
            attachmentName = "info.txt";
        }
        if ((i & 8) != 0) {
            buildAttachment = new Function1<ExceptionAttachmentBuilder, Unit>() { // from class: org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilderKt.checkWithAttachment.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ExceptionAttachmentBuilder exceptionAttachmentBuilder) {
                    Intrinsics.checkNotNullParameter(exceptionAttachmentBuilder, "$this$null");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ExceptionAttachmentBuilder exceptionAttachmentBuilder) {
                    invoke2(exceptionAttachmentBuilder);
                    return Unit.INSTANCE;
                }
            };
        }
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(attachmentName, "attachmentName");
        Intrinsics.checkNotNullParameter(buildAttachment, "buildAttachment");
        if (z) {
            return;
        }
        KotlinIllegalStateExceptionWithAttachments kotlinIllegalStateExceptionWithAttachments = new KotlinIllegalStateExceptionWithAttachments((String) message.invoke());
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        buildAttachment.invoke(exceptionAttachmentBuilder);
        Unit unit = Unit.INSTANCE;
        kotlinIllegalStateExceptionWithAttachments.withAttachment(attachmentName, exceptionAttachmentBuilder.buildString());
        throw kotlinIllegalStateExceptionWithAttachments;
    }

    public static final void checkWithAttachment(boolean z, Function0<String> message, String attachmentName, Function1<? super ExceptionAttachmentBuilder, Unit> buildAttachment) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(attachmentName, "attachmentName");
        Intrinsics.checkNotNullParameter(buildAttachment, "buildAttachment");
        if (z) {
            return;
        }
        KotlinIllegalStateExceptionWithAttachments kotlinIllegalStateExceptionWithAttachments = new KotlinIllegalStateExceptionWithAttachments(message.invoke());
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        buildAttachment.invoke(exceptionAttachmentBuilder);
        Unit unit = Unit.INSTANCE;
        kotlinIllegalStateExceptionWithAttachments.withAttachment(attachmentName, exceptionAttachmentBuilder.buildString());
        throw kotlinIllegalStateExceptionWithAttachments;
    }

    public static /* synthetic */ void requireWithAttachment$default(boolean z, Function0 message, String attachmentName, Function1 buildAttachment, int i, Object obj) {
        if ((i & 4) != 0) {
            attachmentName = "info.txt";
        }
        if ((i & 8) != 0) {
            buildAttachment = new Function1<ExceptionAttachmentBuilder, Unit>() { // from class: org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilderKt.requireWithAttachment.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ExceptionAttachmentBuilder exceptionAttachmentBuilder) {
                    Intrinsics.checkNotNullParameter(exceptionAttachmentBuilder, "$this$null");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ExceptionAttachmentBuilder exceptionAttachmentBuilder) {
                    invoke2(exceptionAttachmentBuilder);
                    return Unit.INSTANCE;
                }
            };
        }
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(attachmentName, "attachmentName");
        Intrinsics.checkNotNullParameter(buildAttachment, "buildAttachment");
        if (z) {
            return;
        }
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments((String) message.invoke());
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        buildAttachment.invoke(exceptionAttachmentBuilder);
        Unit unit = Unit.INSTANCE;
        kotlinIllegalArgumentExceptionWithAttachments.withAttachment(attachmentName, exceptionAttachmentBuilder.buildString());
        throw kotlinIllegalArgumentExceptionWithAttachments;
    }

    public static final void requireWithAttachment(boolean z, Function0<String> message, String attachmentName, Function1<? super ExceptionAttachmentBuilder, Unit> buildAttachment) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(attachmentName, "attachmentName");
        Intrinsics.checkNotNullParameter(buildAttachment, "buildAttachment");
        if (z) {
            return;
        }
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments(message.invoke());
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        buildAttachment.invoke(exceptionAttachmentBuilder);
        Unit unit = Unit.INSTANCE;
        kotlinIllegalArgumentExceptionWithAttachments.withAttachment(attachmentName, exceptionAttachmentBuilder.buildString());
        throw kotlinIllegalArgumentExceptionWithAttachments;
    }

    public static final void logErrorWithAttachment(Logger logger, String message, Exception exc, String attachmentName, Function1<? super ExceptionAttachmentBuilder, Unit> buildAttachment) {
        Intrinsics.checkNotNullParameter(logger, "<this>");
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(attachmentName, "attachmentName");
        Intrinsics.checkNotNullParameter(buildAttachment, "buildAttachment");
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments(message, exc);
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        buildAttachment.invoke(exceptionAttachmentBuilder);
        Unit unit = Unit.INSTANCE;
        kotlinIllegalArgumentExceptionWithAttachments.withAttachment(attachmentName, exceptionAttachmentBuilder.buildString());
        logger.error((Throwable) kotlinIllegalArgumentExceptionWithAttachments);
    }

    public static final Void errorWithAttachment(String message, Exception exc, String attachmentName, Function1<? super ExceptionAttachmentBuilder, Unit> buildAttachment) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(attachmentName, "attachmentName");
        Intrinsics.checkNotNullParameter(buildAttachment, "buildAttachment");
        KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments(message, exc);
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        buildAttachment.invoke(exceptionAttachmentBuilder);
        Unit unit = Unit.INSTANCE;
        kotlinIllegalArgumentExceptionWithAttachments.withAttachment(attachmentName, exceptionAttachmentBuilder.buildString());
        throw kotlinIllegalArgumentExceptionWithAttachments;
    }
}
