package org.catrobat.catroid.pocketmusic.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.pocketmusic.note.NoteName;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/ui/PianoView.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PianoView extends ViewGroup {
    private static final int BLACK_KEY_COUNT = 5;
    private static final ButtonHeight[] HEIGHT_DISTRIBUTION = {ButtonHeight.oneAndAHalfButtonHeight, ButtonHeight.doubleButtonHeight, ButtonHeight.oneAndAHalfButtonHeight, ButtonHeight.oneAndAHalfButtonHeight, ButtonHeight.doubleButtonHeight, ButtonHeight.doubleButtonHeight, ButtonHeight.oneAndAHalfButtonHeight, ButtonHeight.singleButtonHeight};
    private static final int WHITE_KEY_COUNT = 8;
    private List<TextView> blackPianoKeys;
    private int currentHeight;
    private int margin;
    private List<TextView> whitePianoKeys;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/ui/PianoView$ButtonHeight.class
      classes14.dex
     */
    enum ButtonHeight {
        singleButtonHeight,
        oneAndAHalfButtonHeight,
        doubleButtonHeight
    }

    private int round(float floatValue) {
        return (int) (floatValue + 0.5f);
    }

    public PianoView(Context context) {
        this(context, null);
    }

    public PianoView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.whitePianoKeys = new ArrayList();
        this.blackPianoKeys = new ArrayList();
        this.margin = getResources().getDimensionPixelSize(R.dimen.pocketmusic_trackrow_margin);
        for (int i = 0; i < 8; i++) {
            TextView textView = new TextView(context);
            textView.setBackgroundColor(ContextCompat.getColor(context, R.color.solid_white));
            textView.setTextAlignment(6);
            textView.setTextColor(ContextCompat.getColor(context, R.color.solid_black));
            textView.setText(String.valueOf(TrackRowView.getMidiValueForRow(TrackView.WHITE_KEY_INDICES[i])));
            this.whitePianoKeys.add(textView);
            addView(textView);
        }
        for (int i2 = 0; i2 < 5; i2++) {
            TextView textView2 = new TextView(context);
            textView2.setBackgroundColor(ContextCompat.getColor(context, R.color.solid_black));
            textView2.setTextAlignment(6);
            textView2.setTextColor(ContextCompat.getColor(context, R.color.solid_white));
            textView2.setText(String.valueOf(TrackRowView.getMidiValueForRow(TrackView.BLACK_KEY_INDICES[i2])));
            this.blackPianoKeys.add(textView2);
            addView(textView2);
        }
        this.currentHeight = 0;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (changed) {
            int measuredHeight = getMeasuredHeight();
            int i = this.margin;
            int iRound = measuredHeight - (i * 26);
            this.currentHeight = i;
            int measuredWidth = getMeasuredWidth() - (this.margin * 4);
            float f = 0.0f;
            for (int i2 = 0; i2 < 8; i2++) {
                int iRound2 = round(iRound / (13.0f - f));
                float f2 = iRound2;
                float f3 = f2 * 1.5f;
                int i3 = this.margin;
                float f4 = i3 + f3;
                int i4 = iRound2 * 2;
                int i5 = i4 + (i3 * 2);
                int i6 = AnonymousClass1.$SwitchMap$org$catrobat$catroid$pocketmusic$ui$PianoView$ButtonHeight[HEIGHT_DISTRIBUTION[i2].ordinal()];
                if (i6 == 1) {
                    TextView textView = this.whitePianoKeys.get(i2);
                    int i7 = this.margin;
                    int i8 = this.currentHeight;
                    textView.layout(i7, i8, measuredWidth, i8 + iRound2);
                    this.currentHeight += iRound2;
                    iRound -= round(f2);
                    f += 1.0f;
                    this.whitePianoKeys.get(i2).setPadding(0, (iRound2 / 2) - (round(this.whitePianoKeys.get(i2).getTextSize()) / 2), 0, 0);
                } else if (i6 == 2) {
                    TextView textView2 = this.whitePianoKeys.get(i2);
                    int i9 = this.margin;
                    int i10 = this.currentHeight;
                    textView2.layout(i9, i10, measuredWidth, round(f4) + i10);
                    this.currentHeight += round(f4);
                    iRound -= round(f3);
                    f += 1.5f;
                    if (i2 == 0 || i2 == 3) {
                        this.whitePianoKeys.get(i2).setPadding(0, (iRound2 / 2) - (round(this.whitePianoKeys.get(i2).getTextSize()) / 2), 0, 0);
                    } else {
                        this.whitePianoKeys.get(i2).setPadding(0, ((round(f4) / 3) * 2) - (round(this.whitePianoKeys.get(i2).getTextSize()) / 2), 0, 0);
                    }
                } else if (i6 == 3) {
                    TextView textView3 = this.whitePianoKeys.get(i2);
                    int i11 = this.margin;
                    int i12 = this.currentHeight;
                    textView3.layout(i11, i12, measuredWidth, i12 + i5);
                    this.currentHeight += i5;
                    iRound -= i4;
                    f += 2.0f;
                    this.whitePianoKeys.get(i2).setPadding(0, (i5 / 2) - (round(this.whitePianoKeys.get(i2).getTextSize()) / 2), 0, 0);
                }
                this.currentHeight += this.margin * 2;
            }
            int measuredHeight2 = getMeasuredHeight() - (this.margin * 26);
            int iRoundUp = roundUp(measuredHeight2 / 13.0f);
            int i13 = measuredHeight2 - iRoundUp;
            this.currentHeight = iRoundUp + this.margin;
            float f5 = 1.0f;
            for (int i14 = 0; i14 < 5; i14++) {
                int iRoundUp2 = roundUp(i13 / (13.0f - f5));
                TextView textView4 = this.blackPianoKeys.get(i14);
                int measuredWidth2 = (int) (getMeasuredWidth() * 0.42f);
                int i15 = this.currentHeight;
                textView4.layout(measuredWidth2, i15, measuredWidth, i15 + iRoundUp2 + (this.margin * 4));
                this.blackPianoKeys.get(i14).setPadding(0, (iRoundUp2 / 2) - (round(this.blackPianoKeys.get(i14).getTextSize()) / 2), 0, 0);
                int i16 = this.currentHeight;
                int i17 = iRoundUp2 * 2;
                int i18 = this.margin;
                int i19 = i16 + (i18 * 4) + i17;
                this.currentHeight = i19;
                i13 -= i17;
                f5 += 2.0f;
                if (i14 == 1) {
                    this.currentHeight = i19 + (i18 * 2) + iRoundUp2;
                    i13 -= iRoundUp2;
                    f5 += 1.0f;
                }
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/ui/PianoView$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.pocketmusic.ui.PianoView$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$pocketmusic$ui$PianoView$ButtonHeight;

        static {
            int[] iArr = new int[ButtonHeight.values().length];
            $SwitchMap$org$catrobat$catroid$pocketmusic$ui$PianoView$ButtonHeight = iArr;
            try {
                iArr[ButtonHeight.singleButtonHeight.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$pocketmusic$ui$PianoView$ButtonHeight[ButtonHeight.oneAndAHalfButtonHeight.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$pocketmusic$ui$PianoView$ButtonHeight[ButtonHeight.doubleButtonHeight.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public void setKeyColor(NoteName note, boolean active) {
        TextView textView;
        int i = 0;
        while (i <= 13 && !note.equals(NoteName.getNoteNameFromMidiValue(TrackRowView.getMidiValueForRow(i)))) {
            i++;
        }
        Boolean bool = false;
        int iBinarySearch = Arrays.binarySearch(TrackView.BLACK_KEY_INDICES, i);
        if (iBinarySearch < 0) {
            int iBinarySearch2 = Arrays.binarySearch(TrackView.WHITE_KEY_INDICES, i);
            if (iBinarySearch2 < 0) {
                return;
            } else {
                textView = this.whitePianoKeys.get(iBinarySearch2);
            }
        } else {
            textView = this.blackPianoKeys.get(iBinarySearch);
            bool = true;
        }
        if (active) {
            textView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));
        } else {
            textView.setBackgroundColor(ContextCompat.getColor(getContext(), bool.booleanValue() ? R.color.solid_black : R.color.solid_white));
        }
    }

    private int roundUp(float floatValue) {
        return (int) Math.ceil(floatValue);
    }

    public List<TextView> getWhitePianoKeys() {
        return this.whitePianoKeys;
    }

    public List<TextView> getBlackPianoKeys() {
        return this.blackPianoKeys;
    }
}
