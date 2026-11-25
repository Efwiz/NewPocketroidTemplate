package org.jetbrains.kotlin.fir;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceFile;
import org.jetbrains.kotlin.KtSourceFileLinesMapping;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.util.AnalysisExceptionsKt;

/* JADX INFO: compiled from: Utils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0018\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/FirCliExceptionHandler;", "Lorg/jetbrains/kotlin/fir/FirExceptionHandler;", "()V", "handleExceptionOnElementAnalysis", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "throwable", "", "handleExceptionOnFileAnalysis", "file", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirCliExceptionHandler extends FirExceptionHandler {
    public static final FirCliExceptionHandler INSTANCE = new FirCliExceptionHandler();

    private FirCliExceptionHandler() {
    }

    @Override // org.jetbrains.kotlin.fir.FirExceptionHandler
    public Void handleExceptionOnElementAnalysis(FirElement element, Throwable throwable) throws Throwable {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(throwable, "throwable");
        throw AnalysisExceptionsKt.wrapIntoSourceCodeAnalysisExceptionIfNeeded(throwable, element.getSource());
    }

    @Override // org.jetbrains.kotlin.fir.FirExceptionHandler
    public Void handleExceptionOnFileAnalysis(final FirFile file, Throwable throwable) throws Throwable {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(throwable, "throwable");
        KtSourceFile sourceFile = file.getSourceFile();
        throw AnalysisExceptionsKt.wrapIntoFileAnalysisExceptionIfNeeded(throwable, sourceFile != null ? sourceFile.getPath() : null, file.getSource(), new Function1<Integer, Pair<? extends Integer, ? extends Integer>>() { // from class: org.jetbrains.kotlin.fir.FirCliExceptionHandler.handleExceptionOnFileAnalysis.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Pair<? extends Integer, ? extends Integer> invoke(Integer num) {
                return invoke(num.intValue());
            }

            public final Pair<Integer, Integer> invoke(int i) {
                KtSourceFileLinesMapping sourceFileLinesMapping = file.getSourceFileLinesMapping();
                if (sourceFileLinesMapping != null) {
                    return sourceFileLinesMapping.getLineAndColumnByOffset(i);
                }
                return null;
            }
        });
    }
}
