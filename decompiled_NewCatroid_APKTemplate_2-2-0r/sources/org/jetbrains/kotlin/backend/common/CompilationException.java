package org.jetbrains.kotlin.backend.common;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrFileEntry;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DumpKotlinLikeKt;
import org.jetbrains.kotlin.ir.util.KotlinLikeDumpOptions;
import org.jetbrains.kotlin.library.KotlinLibraryLayoutKt;

/* JADX INFO: compiled from: CompilationException.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0014\u0018\u00002\u00060\u0001j\u0002`\u0002B-\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000bR\u0011\u0010\f\u001a\u00020\r8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u00048F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0016\u0010\u0019\u001a\u0004\u0018\u00010\r8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u001c\u001a\u00020\r8F¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u000fR\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u0012R\u0013\u0010\u001f\u001a\u0004\u0018\u00010\u00048F¢\u0006\u0006\u001a\u0004\b \u0010\u0012¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/CompilationException;", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", CrashHianalyticsData.MESSAGE, "", "file", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", KotlinLibraryLayoutKt.KLIB_IR_FOLDER_NAME, "", "cause", "", "(Ljava/lang/String;Lorg/jetbrains/kotlin/ir/declarations/IrFile;Ljava/lang/Object;Ljava/lang/Throwable;)V", "column", "", "getColumn", "()I", FirebaseAnalytics.Param.CONTENT, "getContent", "()Ljava/lang/String;", "getFile", "()Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "setFile", "(Lorg/jetbrains/kotlin/ir/declarations/IrFile;)V", "getIr", "()Ljava/lang/Object;", "irStartOffset", "getIrStartOffset", "()Ljava/lang/Integer;", Constant.LINE, "getLine", "getMessage", "path", "getPath", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompilationException extends RuntimeException {
    private IrFile file;
    private final Object ir;

    public /* synthetic */ CompilationException(String str, IrFile irFile, Object obj, Throwable th, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, irFile, obj, (i & 8) != 0 ? null : th);
    }

    public final IrFile getFile() {
        return this.file;
    }

    public final void setFile(IrFile irFile) {
        this.file = irFile;
    }

    public final Object getIr() {
        return this.ir;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CompilationException(String message, IrFile irFile, Object obj, Throwable th) {
        super(message, th);
        Intrinsics.checkNotNullParameter(message, "message");
        this.file = irFile;
        this.ir = obj;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        try {
            StringBuilder sb = new StringBuilder();
            StringBuilder sbAppend = sb.append("Back-end: Please report this problem https://kotl.in/issue");
            Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
            Intrinsics.checkNotNullExpressionValue(sbAppend.append('\n'), "append(...)");
            String path = getPath();
            if (path != null) {
                StringBuilder sbAppend2 = sb.append(path + ':' + getLine() + ':' + getColumn());
                Intrinsics.checkNotNullExpressionValue(sbAppend2, "append(...)");
                Intrinsics.checkNotNullExpressionValue(sbAppend2.append('\n'), "append(...)");
            }
            String content = getContent();
            if (content != null) {
                StringBuilder sbAppend3 = sb.append("Problem with `" + content + '`');
                Intrinsics.checkNotNullExpressionValue(sbAppend3, "append(...)");
                Intrinsics.checkNotNullExpressionValue(sbAppend3.append('\n'), "append(...)");
            }
            sb.append("Details: " + super.getMessage());
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return string;
        } catch (Throwable unused) {
            IllegalStateException illegalStateException = new IllegalStateException("Problem with constructing exception message");
            illegalStateException.setStackTrace(getStackTrace());
            throw illegalStateException;
        }
    }

    public final int getLine() {
        int iIntValue;
        IrFile irFile;
        IrFileEntry fileEntry;
        Integer irStartOffset = getIrStartOffset();
        if (irStartOffset == null || (iIntValue = irStartOffset.intValue()) == -1 || (irFile = this.file) == null || (fileEntry = irFile.getFileEntry()) == null) {
            return -1;
        }
        return fileEntry.getLineNumber(iIntValue) + 1;
    }

    public final int getColumn() {
        int iIntValue;
        IrFile irFile;
        IrFileEntry fileEntry;
        Integer irStartOffset = getIrStartOffset();
        if (irStartOffset == null || (iIntValue = irStartOffset.intValue()) == -1 || (irFile = this.file) == null || (fileEntry = irFile.getFileEntry()) == null) {
            return -1;
        }
        return fileEntry.getColumnNumber(iIntValue) + 1;
    }

    private final Integer getIrStartOffset() {
        Object obj = this.ir;
        IrElement irElement = obj instanceof IrElement ? (IrElement) obj : null;
        if (irElement != null) {
            return Integer.valueOf(irElement.getStartOffset());
        }
        return null;
    }

    public final String getPath() {
        IrFile irFile = this.file;
        if (irFile != null) {
            return IrDeclarationsKt.getPath(irFile);
        }
        return null;
    }

    public final String getContent() {
        Object obj = this.ir;
        if (obj instanceof IrElement) {
            return DumpKotlinLikeKt.dumpKotlinLike((IrElement) obj, (1 & 1) != 0 ? new KotlinLikeDumpOptions(null, false, false, false, false, null, null, null, false, false, false, 2047, null) : null);
        }
        if (obj instanceof IrType) {
            return DumpKotlinLikeKt.dumpKotlinLike((IrType) obj);
        }
        return null;
    }
}
