package org.jetbrains.kotlin.psi;

import gnu.trove.TIntArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.LiteralTextEscaper;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;

/* JADX INFO: compiled from: KotlinStringLiteralTextEscaper.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004J\u001c\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\n\u0010\u000b\u001a\u00060\fj\u0002`\rH\u0016J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u0011\u001a\u00020\nH\u0016J\b\u0010\u0012\u001a\u00020\bH\u0016R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/psi/KotlinStringLiteralTextEscaper;", "Lorg/jetbrains/kotlin/com/intellij/psi/LiteralTextEscaper;", "Lorg/jetbrains/kotlin/psi/KtStringTemplateExpression;", "host", "(Lorg/jetbrains/kotlin/psi/KtStringTemplateExpression;)V", "sourceOffsets", "", "decode", "", "rangeInsideHost", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "outChars", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "getOffsetInHost", "", "offsetInDecoded", "getRelevantTextRange", "isOneLine", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinStringLiteralTextEscaper extends LiteralTextEscaper<KtStringTemplateExpression> {
    private int[] sourceOffsets;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinStringLiteralTextEscaper(KtStringTemplateExpression host) {
        super(host);
        Intrinsics.checkNotNullParameter(host, "host");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.LiteralTextEscaper
    public boolean decode(TextRange rangeInsideHost, StringBuilder outChars) {
        Intrinsics.checkNotNullParameter(rangeInsideHost, "rangeInsideHost");
        Intrinsics.checkNotNullParameter(outChars, "outChars");
        TIntArrayList tIntArrayList = new TIntArrayList();
        KtStringTemplateEntry[] entries = ((KtStringTemplateExpression) this.myHost).getEntries();
        Intrinsics.checkNotNullExpressionValue(entries, "getEntries(...)");
        int textLength = 0;
        for (KtStringTemplateEntry ktStringTemplateEntry : entries) {
            TextRange textRangeFrom = TextRange.from(ktStringTemplateEntry.getStartOffsetInParent(), ktStringTemplateEntry.getTextLength());
            Intrinsics.checkNotNullExpressionValue(textRangeFrom, "from(...)");
            if (rangeInsideHost.getEndOffset() <= textRangeFrom.getStartOffset()) {
                break;
            }
            if (textRangeFrom.getEndOffset() > rangeInsideHost.getStartOffset()) {
                if (ktStringTemplateEntry instanceof KtEscapeStringTemplateEntry) {
                    if (!rangeInsideHost.contains(textRangeFrom)) {
                        tIntArrayList.add(textLength);
                        this.sourceOffsets = tIntArrayList.toNativeArray();
                        return false;
                    }
                    String unescapedValue = ((KtEscapeStringTemplateEntry) ktStringTemplateEntry).getUnescapedValue();
                    outChars.append(unescapedValue);
                    int length = unescapedValue.length();
                    for (int i = 0; i < length; i++) {
                        tIntArrayList.add(textLength);
                    }
                    textLength += ktStringTemplateEntry.getTextLength();
                } else {
                    TextRange textRangeIntersection = rangeInsideHost.intersection(textRangeFrom);
                    Intrinsics.checkNotNull(textRangeIntersection);
                    TextRange textRangeShiftRight = textRangeIntersection.shiftRight(-textRangeFrom.getStartOffset());
                    Intrinsics.checkNotNullExpressionValue(textRangeShiftRight, "shiftRight(...)");
                    outChars.append((CharSequence) ktStringTemplateEntry.getText(), textRangeShiftRight.getStartOffset(), textRangeShiftRight.getEndOffset());
                    int length2 = textRangeShiftRight.getLength();
                    int i2 = 0;
                    while (i2 < length2) {
                        tIntArrayList.add(textLength);
                        i2++;
                        textLength++;
                    }
                }
            }
        }
        tIntArrayList.add(textLength);
        this.sourceOffsets = tIntArrayList.toNativeArray();
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.LiteralTextEscaper
    public int getOffsetInHost(int offsetInDecoded, TextRange rangeInsideHost) {
        Intrinsics.checkNotNullParameter(rangeInsideHost, "rangeInsideHost");
        int[] iArr = this.sourceOffsets;
        if (iArr == null || offsetInDecoded >= iArr.length) {
            return -1;
        }
        return Math.min(iArr[offsetInDecoded], rangeInsideHost.getLength()) + rangeInsideHost.getStartOffset();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.LiteralTextEscaper
    public TextRange getRelevantTextRange() {
        T myHost = this.myHost;
        Intrinsics.checkNotNullExpressionValue(myHost, "myHost");
        return KtPsiUtilKt.getContentRange((KtStringTemplateExpression) myHost);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.LiteralTextEscaper
    public boolean isOneLine() {
        T myHost = this.myHost;
        Intrinsics.checkNotNullExpressionValue(myHost, "myHost");
        return KtPsiUtilKt.isSingleQuoted((KtStringTemplateExpression) myHost);
    }
}
