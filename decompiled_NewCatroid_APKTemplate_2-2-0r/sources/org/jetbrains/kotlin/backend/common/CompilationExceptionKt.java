package org.jetbrains.kotlin.backend.common;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: CompilationException.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u001a\u0016\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\b\u001a\u0004\u0018\u00010\t\u001a(\u0010\n\u001a\u00060\u000bj\u0002`\f*\u00020\r2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¨\u0006\u0010"}, d2 = {"compilationException", "", CrashHianalyticsData.MESSAGE, "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "wrapWithCompilationException", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", "", "file", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "ir.backend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CompilationExceptionKt {
    public static final Void compilationException(String message, IrElement element) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(element, "element");
        throw new CompilationException(message, null, element, null, 8, null);
    }

    public static final Void compilationException(String message, IrType irType) {
        Intrinsics.checkNotNullParameter(message, "message");
        throw new CompilationException(message, null, irType, null, 8, null);
    }

    public static final Void compilationException(String message, IrDeclaration declaration) {
        IrFile fileOrNull;
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        try {
            fileOrNull = IrUtilsKt.getFileOrNull(declaration);
        } catch (Throwable unused) {
            fileOrNull = null;
        }
        throw new CompilationException(message, fileOrNull, declaration, null, 8, null);
    }

    public static final RuntimeException wrapWithCompilationException(Throwable th, String message, IrFile file, IrElement irElement) {
        Intrinsics.checkNotNullParameter(th, "<this>");
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(file, "file");
        if (th instanceof ProcessCanceledException) {
            return (RuntimeException) th;
        }
        CompilationException compilationException = new CompilationException(message + ": " + Reflection.getOrCreateKotlinClass(th.getClass()).getQualifiedName() + ": " + th.getMessage(), file, irElement, th);
        compilationException.setStackTrace(th.getStackTrace());
        return compilationException;
    }
}
