package org.jetbrains.kotlin;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;

/* JADX INFO: compiled from: KtSourceFileLinesMapping.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001c\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f0\u00142\u0006\u0010\u0015\u001a\u00020\fH\u0016J\u0010\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\fH\u0016J\u0010\u0010\u0017\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\fH\u0016R\u001d\u0010\u0005\u001a\u0004\u0018\u00010\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/KtPsiSourceFileLinesMapping;", "Lorg/jetbrains/kotlin/KtSourceFileLinesMapping;", "psiFile", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "(Lcom/intellij/psi/PsiFile;)V", "document", "Lorg/jetbrains/kotlin/com/intellij/openapi/editor/Document;", "getDocument", "()Lcom/intellij/openapi/editor/Document;", "document$delegate", "Lkotlin/Lazy;", "lastOffset", "", "getLastOffset", "()I", "linesCount", "getLinesCount", "getPsiFile", "()Lcom/intellij/psi/PsiFile;", "getLineAndColumnByOffset", "Lkotlin/Pair;", TypedValues.CycleType.S_WAVE_OFFSET, "getLineByOffset", "getLineStartOffset", Constant.LINE, "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtPsiSourceFileLinesMapping implements KtSourceFileLinesMapping {

    /* JADX INFO: renamed from: document$delegate, reason: from kotlin metadata */
    private final Lazy document;
    private final PsiFile psiFile;

    public KtPsiSourceFileLinesMapping(PsiFile psiFile) {
        Intrinsics.checkNotNullParameter(psiFile, "psiFile");
        this.psiFile = psiFile;
        this.document = LazyKt.lazy(new Function0<Document>() { // from class: org.jetbrains.kotlin.KtPsiSourceFileLinesMapping$document$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Document invoke() {
                return this.this$0.getPsiFile().getViewProvider().getDocument();
            }
        });
    }

    public final PsiFile getPsiFile() {
        return this.psiFile;
    }

    private final Document getDocument() {
        return (Document) this.document.getValue();
    }

    @Override // org.jetbrains.kotlin.KtSourceFileLinesMapping
    public int getLineStartOffset(int line) {
        Document document = getDocument();
        if (document != null) {
            return document.getLineStartOffset(line);
        }
        return -1;
    }

    @Override // org.jetbrains.kotlin.KtSourceFileLinesMapping
    public Pair<Integer, Integer> getLineAndColumnByOffset(int offset) {
        Document document = getDocument();
        if (document != null) {
            int lineNumber = document.getLineNumber(offset);
            Pair<Integer, Integer> pair = TuplesKt.to(Integer.valueOf(lineNumber), Integer.valueOf(offset - document.getLineStartOffset(lineNumber)));
            if (pair != null) {
                return pair;
            }
        }
        return TuplesKt.to(-1, -1);
    }

    @Override // org.jetbrains.kotlin.KtSourceFileLinesMapping
    public int getLineByOffset(int offset) {
        Document document = getDocument();
        if (document != null) {
            return document.getLineNumber(offset);
        }
        return -1;
    }

    @Override // org.jetbrains.kotlin.KtSourceFileLinesMapping
    public int getLastOffset() {
        Document document = getDocument();
        if (document != null) {
            return document.getTextLength();
        }
        return -1;
    }

    @Override // org.jetbrains.kotlin.KtSourceFileLinesMapping
    public int getLinesCount() {
        Document document = getDocument();
        if (document != null) {
            return document.getLineCount();
        }
        return 0;
    }
}
