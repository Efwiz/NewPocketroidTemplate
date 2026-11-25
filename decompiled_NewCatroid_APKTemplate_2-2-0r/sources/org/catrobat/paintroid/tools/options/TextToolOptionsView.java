package org.catrobat.paintroid.tools.options;

import android.view.View;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import kotlin.Metadata;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.paintroid.tools.FontType;

/* JADX INFO: compiled from: TextToolOptionsView.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001:\u0001\u0016J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&J\b\u0010\u0005\u001a\u00020\u0006H&J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH&J8\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H&J\b\u0010\u0015\u001a\u00020\u0006H&¨\u0006\u0017"}, d2 = {"Lorg/catrobat/paintroid/tools/options/TextToolOptionsView;", "", "getBottomLayout", "Landroid/view/View;", "getTopLayout", "hideKeyboard", "", "setCallback", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Lorg/catrobat/paintroid/tools/options/TextToolOptionsView$Callback;", "setState", "bold", "", "italic", "underlined", "text", "", StageActivity.STYLE_TEXT_SIZE, "", "fontType", "Lorg/catrobat/paintroid/tools/FontType;", "showKeyboard", "Callback", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface TextToolOptionsView {

    /* JADX INFO: compiled from: TextToolOptionsView.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0010\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH&J\u0010\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\u0006H&J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000eH&J\u0010\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u0011H&J\u0010\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0006H&¨\u0006\u0014"}, d2 = {"Lorg/catrobat/paintroid/tools/options/TextToolOptionsView$Callback;", "", "hideToolOptions", "", "setBold", "bold", "", "setFont", "fontType", "Lorg/catrobat/paintroid/tools/FontType;", "setItalic", "italic", "setText", "text", "", "setTextSize", "size", "", "setUnderlined", "underlined", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface Callback {
        void hideToolOptions();

        void setBold(boolean bold);

        void setFont(FontType fontType);

        void setItalic(boolean italic);

        void setText(String text);

        void setTextSize(int size);

        void setUnderlined(boolean underlined);
    }

    View getBottomLayout();

    View getTopLayout();

    void hideKeyboard();

    void setCallback(Callback listener);

    void setState(boolean bold, boolean italic, boolean underlined, String text, int textSize, FontType fontType);

    void showKeyboard();
}
