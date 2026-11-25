package io.github.rosemoe.sora.widget.layout;

import android.view.View;
import io.github.rosemoe.sora.graphics.Paint;
import io.github.rosemoe.sora.graphics.SingleCharacterWidths;
import io.github.rosemoe.sora.text.Content;
import io.github.rosemoe.sora.text.ContentLine;
import io.github.rosemoe.sora.text.bidi.Directions;
import io.github.rosemoe.sora.util.IntPair;
import io.github.rosemoe.sora.util.MutableInt;

/* JADX INFO: loaded from: classes11.dex */
public class ViewMeasureHelper {
    public static long getDesiredSize(int i, int i2, float f, float f2, boolean z, int i3, Content content, final Paint paint) {
        int iMakeMeasureSpec;
        int iMakeMeasureSpec2;
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        final SingleCharacterWidths singleCharacterWidths = new SingleCharacterWidths(i3);
        if (z) {
            if (View.MeasureSpec.getMode(i) != 1073741824) {
                final int[] iArr = View.MeasureSpec.getMode(i2) != 1073741824 ? new int[content.getLineCount()] : null;
                final MutableInt mutableInt = new MutableInt(0);
                content.runReadActionsOnLines(0, content.getLineCount() - 1, new Content.ContentLineConsumer() { // from class: io.github.rosemoe.sora.widget.layout.ViewMeasureHelper$$ExternalSyntheticLambda0
                    @Override // io.github.rosemoe.sora.text.Content.ContentLineConsumer
                    public final void accept(int i4, ContentLine contentLine, Directions directions) {
                        ViewMeasureHelper.lambda$getDesiredSize$0(singleCharacterWidths, paint, mutableInt, iArr, i4, contentLine, directions);
                    }
                });
                int iMin = (int) Math.min(size, mutableInt.value + f);
                int iMakeMeasureSpec3 = View.MeasureSpec.makeMeasureSpec(iMin, 1073741824);
                if (iArr != null) {
                    MutableInt mutableInt2 = new MutableInt(0);
                    int i4 = (int) (iMin - f);
                    if (i4 <= 0) {
                        mutableInt2.value = content.length();
                    } else {
                        for (int i5 : iArr) {
                            mutableInt2.value = (int) (((double) mutableInt2.value) + Math.max(1.0d, Math.ceil((((double) i5) * 1.0d) / ((double) i4))));
                        }
                    }
                    iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(Math.min((int) (mutableInt2.value * f2), size2), 1073741824);
                } else {
                    iMakeMeasureSpec2 = i2;
                }
                iMakeMeasureSpec = iMakeMeasureSpec3;
            } else if (View.MeasureSpec.getMode(i2) != 1073741824) {
                final MutableInt mutableInt3 = new MutableInt(0);
                final int i6 = (int) (size - f);
                if (i6 <= 0) {
                    mutableInt3.value = content.length();
                } else {
                    content.runReadActionsOnLines(0, content.getLineCount() - 1, new Content.ContentLineConsumer() { // from class: io.github.rosemoe.sora.widget.layout.ViewMeasureHelper$$ExternalSyntheticLambda1
                        @Override // io.github.rosemoe.sora.text.Content.ContentLineConsumer
                        public final void accept(int i7, ContentLine contentLine, Directions directions) {
                            SingleCharacterWidths singleCharacterWidths2 = singleCharacterWidths;
                            Paint paint2 = paint;
                            MutableInt mutableInt4 = mutableInt3;
                            mutableInt4.value = (int) (((double) mutableInt4.value) + Math.max(1.0d, Math.ceil((((double) ((int) Math.ceil(singleCharacterWidths2.measureText(contentLine.value, 0, contentLine.length(), paint2)))) * 1.0d) / ((double) i6))));
                        }
                    });
                }
                iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(Math.min((int) (mutableInt3.value * f2), size2), 1073741824);
                iMakeMeasureSpec = i;
            } else {
                iMakeMeasureSpec = i;
            }
        } else {
            if (View.MeasureSpec.getMode(i) != 1073741824) {
                final MutableInt mutableInt4 = new MutableInt(0);
                content.runReadActionsOnLines(0, content.getLineCount() - 1, new Content.ContentLineConsumer() { // from class: io.github.rosemoe.sora.widget.layout.ViewMeasureHelper$$ExternalSyntheticLambda2
                    @Override // io.github.rosemoe.sora.text.Content.ContentLineConsumer
                    public final void accept(int i7, ContentLine contentLine, Directions directions) {
                        ViewMeasureHelper.lambda$getDesiredSize$2(singleCharacterWidths, paint, mutableInt4, i7, contentLine, directions);
                    }
                });
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec((int) Math.min(mutableInt4.value + f, size), 1073741824);
            } else {
                iMakeMeasureSpec = i;
            }
            iMakeMeasureSpec2 = View.MeasureSpec.getSize(i2) != 1073741824 ? View.MeasureSpec.makeMeasureSpec(Math.min(size2, (int) (content.getLineCount() * f2)), 1073741824) : i2;
        }
        return IntPair.pack(iMakeMeasureSpec, iMakeMeasureSpec2);
    }

    static /* synthetic */ void lambda$getDesiredSize$0(SingleCharacterWidths singleCharacterWidths, Paint paint, MutableInt mutableInt, int[] iArr, int i, ContentLine contentLine, Directions directions) {
        int iCeil = (int) Math.ceil(singleCharacterWidths.measureText(contentLine.value, 0, contentLine.length(), paint));
        if (iCeil > mutableInt.value) {
            mutableInt.value = iCeil;
        }
        if (iArr != null) {
            iArr[i] = iCeil;
        }
    }

    static /* synthetic */ void lambda$getDesiredSize$2(SingleCharacterWidths singleCharacterWidths, Paint paint, MutableInt mutableInt, int i, ContentLine contentLine, Directions directions) {
        int iCeil = (int) Math.ceil(singleCharacterWidths.measureText(contentLine.value, 0, contentLine.length(), paint));
        if (iCeil > mutableInt.value) {
            mutableInt.value = iCeil;
        }
    }
}
