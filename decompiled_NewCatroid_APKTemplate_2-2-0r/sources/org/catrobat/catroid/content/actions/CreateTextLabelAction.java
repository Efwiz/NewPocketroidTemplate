package org.catrobat.catroid.content.actions;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.badlogic.gdx.scenes.scene2d.Action;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.catrobat.catroid.common.NativeViewBindingManager;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/CreateTextLabelAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: CreateTextLabelAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010?\u001a\u00020\u00162\u0006\u0010@\u001a\u00020AH\u0016J\b\u0010B\u001a\u00020CH\u0016J\b\u0010D\u001a\u00020CH\u0016J\b\u0010E\u001a\u00020CH\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000eR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\f\"\u0004\b\u0014\u0010\u000eR\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\u0017\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\f\"\u0004\b\u0019\u0010\u000eR\u001c\u0010\u001a\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\f\"\u0004\b\u001c\u0010\u000eR\u001a\u0010\u001d\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0006\"\u0004\b\u001f\u0010\bR\u001c\u0010 \u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\f\"\u0004\b\"\u0010\u000eR\u001c\u0010#\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\f\"\u0004\b%\u0010\u000eR\u001c\u0010&\u001a\u0004\u0018\u00010'X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u001a\u0010,\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010\u0006\"\u0004\b.\u0010\bR\u000e\u0010/\u001a\u00020\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u00100\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u0010\f\"\u0004\b2\u0010\u000eR\u001c\u00103\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b4\u0010\f\"\u0004\b5\u0010\u000eR\u001c\u00106\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b7\u0010\f\"\u0004\b8\u0010\u000eR\u001c\u00109\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b:\u0010\f\"\u0004\b;\u0010\u000eR\u001c\u0010<\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b=\u0010\f\"\u0004\b>\u0010\u000e¨\u0006F"}, d2 = {"Lorg/catrobat/catroid/content/actions/CreateTextLabelAction;", "Lcom/badlogic/gdx/scenes/scene2d/Action;", "()V", "alignMode", "", "getAlignMode", "()I", "setAlignMode", "(I)V", "bgColor", "Lorg/catrobat/catroid/formulaeditor/Formula;", "getBgColor", "()Lorg/catrobat/catroid/formulaeditor/Formula;", "setBgColor", "(Lorg/catrobat/catroid/formulaeditor/Formula;)V", "colorHex", "getColorHex", "setColorHex", StageActivity.STYLE_CORNER_RADIUS, "getCornerRadius", "setCornerRadius", "finished", "", StageActivity.STYLE_FONT_PATH, "getFontPath", "setFontPath", "fontSize", "getFontSize", "setFontSize", "formatMode", "getFormatMode", "setFormatMode", "height", "getHeight", "setHeight", "lineSpacing", "getLineSpacing", "setLineSpacing", "scope", "Lorg/catrobat/catroid/content/Scope;", "getScope", "()Lorg/catrobat/catroid/content/Scope;", "setScope", "(Lorg/catrobat/catroid/content/Scope;)V", "scrollMode", "getScrollMode", "setScrollMode", "started", "text", "getText", "setText", "viewId", "getViewId", "setViewId", "width", "getWidth", "setWidth", "x", "getX", "setX", "y", "getY", "setY", "act", "delta", "", "reset", "", "restart", "runAsyncCreate", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CreateTextLabelAction extends Action {
    private int alignMode;
    private Formula bgColor;
    private Formula colorHex;
    private Formula cornerRadius;
    private volatile boolean finished;
    private Formula fontPath;
    private Formula fontSize;
    private int formatMode;
    private Formula height;
    private Formula lineSpacing;
    private Scope scope;
    private int scrollMode;
    private boolean started;
    private Formula text;
    private Formula viewId;
    private Formula width;
    private Formula x;
    private Formula y;

    public final Scope getScope() {
        return this.scope;
    }

    public final void setScope(Scope scope) {
        this.scope = scope;
    }

    public final Formula getViewId() {
        return this.viewId;
    }

    public final void setViewId(Formula formula) {
        this.viewId = formula;
    }

    public final Formula getText() {
        return this.text;
    }

    public final void setText(Formula formula) {
        this.text = formula;
    }

    public final Formula getColorHex() {
        return this.colorHex;
    }

    public final void setColorHex(Formula formula) {
        this.colorHex = formula;
    }

    public final Formula getFontSize() {
        return this.fontSize;
    }

    public final void setFontSize(Formula formula) {
        this.fontSize = formula;
    }

    public final Formula getBgColor() {
        return this.bgColor;
    }

    public final void setBgColor(Formula formula) {
        this.bgColor = formula;
    }

    public final Formula getFontPath() {
        return this.fontPath;
    }

    public final void setFontPath(Formula formula) {
        this.fontPath = formula;
    }

    public final Formula getCornerRadius() {
        return this.cornerRadius;
    }

    public final void setCornerRadius(Formula formula) {
        this.cornerRadius = formula;
    }

    public final Formula getLineSpacing() {
        return this.lineSpacing;
    }

    public final void setLineSpacing(Formula formula) {
        this.lineSpacing = formula;
    }

    public final int getFormatMode() {
        return this.formatMode;
    }

    public final void setFormatMode(int i) {
        this.formatMode = i;
    }

    public final int getAlignMode() {
        return this.alignMode;
    }

    public final void setAlignMode(int i) {
        this.alignMode = i;
    }

    public final int getScrollMode() {
        return this.scrollMode;
    }

    public final void setScrollMode(int i) {
        this.scrollMode = i;
    }

    public final Formula getX() {
        return this.x;
    }

    public final void setX(Formula formula) {
        this.x = formula;
    }

    public final Formula getY() {
        return this.y;
    }

    public final void setY(Formula formula) {
        this.y = formula;
    }

    public final Formula getWidth() {
        return this.width;
    }

    public final void setWidth(Formula formula) {
        this.width = formula;
    }

    public final Formula getHeight() {
        return this.height;
    }

    public final void setHeight(Formula formula) {
        this.height = formula;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (!this.started) {
            this.started = true;
            runAsyncCreate();
        }
        return this.finished;
    }

    private final void runAsyncCreate() {
        WeakReference<StageActivity> weakReference = StageActivity.activeStageActivity;
        StageActivity stageActivity = weakReference != null ? weakReference.get() : null;
        if (stageActivity == null) {
            this.finished = true;
            return;
        }
        Formula formula = this.viewId;
        String strInterpretString = formula != null ? formula.interpretString(this.scope) : null;
        final String str = strInterpretString == null ? "" : strInterpretString;
        Formula formula2 = this.text;
        String strInterpretString2 = formula2 != null ? formula2.interpretString(this.scope) : null;
        final String str2 = strInterpretString2 == null ? "" : strInterpretString2;
        Formula formula3 = this.colorHex;
        String strInterpretString3 = formula3 != null ? formula3.interpretString(this.scope) : null;
        if (strInterpretString3 == null) {
            strInterpretString3 = "#FFFFFF";
        }
        final String str3 = strInterpretString3;
        Formula formula4 = this.fontSize;
        Float fInterpretFloat = formula4 != null ? formula4.interpretFloat(this.scope) : null;
        final float fFloatValue = fInterpretFloat == null ? 20.0f : fInterpretFloat.floatValue();
        Formula formula5 = this.bgColor;
        String strInterpretString4 = formula5 != null ? formula5.interpretString(this.scope) : null;
        if (strInterpretString4 == null) {
            strInterpretString4 = "#00000000";
        }
        final String str4 = strInterpretString4;
        Formula formula6 = this.fontPath;
        final String strInterpretString5 = formula6 != null ? formula6.interpretString(this.scope) : null;
        if (strInterpretString5 == null) {
            strInterpretString5 = "";
        }
        Formula formula7 = this.cornerRadius;
        Integer numInterpretInteger = formula7 != null ? formula7.interpretInteger(this.scope) : null;
        final int iIntValue = numInterpretInteger == null ? 0 : numInterpretInteger.intValue();
        Formula formula8 = this.lineSpacing;
        Float fInterpretFloat2 = formula8 != null ? formula8.interpretFloat(this.scope) : null;
        final float fFloatValue2 = fInterpretFloat2 == null ? 1.0f : fInterpretFloat2.floatValue();
        Formula formula9 = this.x;
        Integer numInterpretInteger2 = formula9 != null ? formula9.interpretInteger(this.scope) : null;
        final int iIntValue2 = numInterpretInteger2 == null ? 0 : numInterpretInteger2.intValue();
        Formula formula10 = this.y;
        Integer numInterpretInteger3 = formula10 != null ? formula10.interpretInteger(this.scope) : null;
        int iIntValue3 = numInterpretInteger3 == null ? 0 : numInterpretInteger3.intValue();
        Formula formula11 = this.width;
        Integer numInterpretInteger4 = formula11 != null ? formula11.interpretInteger(this.scope) : null;
        final int iIntValue4 = numInterpretInteger4 == null ? 300 : numInterpretInteger4.intValue();
        Formula formula12 = this.height;
        Integer numInterpretInteger5 = formula12 != null ? formula12.interpretInteger(this.scope) : null;
        final int iIntValue5 = numInterpretInteger5 == null ? 100 : numInterpretInteger5.intValue();
        if (str.length() == 0) {
            this.finished = true;
            return;
        }
        final StageActivity stageActivity2 = stageActivity;
        StageActivity stageActivity3 = stageActivity;
        final int i = iIntValue3;
        stageActivity3.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.content.actions.CreateTextLabelAction$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CreateTextLabelAction.runAsyncCreate$lambda$2(stageActivity2, fFloatValue, str3, fFloatValue2, strInterpretString5, this, str2, iIntValue4, iIntValue5, str, iIntValue, str4, iIntValue2, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:30:0x00b5 A[Catch: all -> 0x017e, TryCatch #3 {all -> 0x017e, blocks: (B:3:0x002b, B:4:0x0037, B:8:0x0043, B:9:0x0057, B:12:0x0062, B:14:0x0070, B:15:0x008a, B:39:0x00d2, B:41:0x00d6, B:43:0x00dc, B:46:0x00e4, B:48:0x00ea, B:52:0x00f4, B:54:0x00fd, B:60:0x010e, B:62:0x0115, B:64:0x0125, B:73:0x015e, B:70:0x012f, B:71:0x0140, B:72:0x0151, B:63:0x011f, B:50:0x00ef, B:51:0x00f2, B:17:0x008e, B:20:0x0097, B:21:0x009a, B:38:0x00cf, B:24:0x00a3, B:30:0x00b5, B:27:0x00ac, B:31:0x00ba, B:34:0x00c3, B:35:0x00c6, B:53:0x00f8, B:11:0x005f, B:7:0x0040), top: B:85:0x002b, inners: #0, #1, #2 }] */
    /* JADX WARN: Code duplicated, block: B:38:0x00cf A[Catch: all -> 0x017e, TryCatch #3 {all -> 0x017e, blocks: (B:3:0x002b, B:4:0x0037, B:8:0x0043, B:9:0x0057, B:12:0x0062, B:14:0x0070, B:15:0x008a, B:39:0x00d2, B:41:0x00d6, B:43:0x00dc, B:46:0x00e4, B:48:0x00ea, B:52:0x00f4, B:54:0x00fd, B:60:0x010e, B:62:0x0115, B:64:0x0125, B:73:0x015e, B:70:0x012f, B:71:0x0140, B:72:0x0151, B:63:0x011f, B:50:0x00ef, B:51:0x00f2, B:17:0x008e, B:20:0x0097, B:21:0x009a, B:38:0x00cf, B:24:0x00a3, B:30:0x00b5, B:27:0x00ac, B:31:0x00ba, B:34:0x00c3, B:35:0x00c6, B:53:0x00f8, B:11:0x005f, B:7:0x0040), top: B:85:0x002b, inners: #0, #1, #2 }] */
    /* JADX WARN: Code duplicated, block: B:41:0x00d6 A[Catch: all -> 0x017e, TryCatch #3 {all -> 0x017e, blocks: (B:3:0x002b, B:4:0x0037, B:8:0x0043, B:9:0x0057, B:12:0x0062, B:14:0x0070, B:15:0x008a, B:39:0x00d2, B:41:0x00d6, B:43:0x00dc, B:46:0x00e4, B:48:0x00ea, B:52:0x00f4, B:54:0x00fd, B:60:0x010e, B:62:0x0115, B:64:0x0125, B:73:0x015e, B:70:0x012f, B:71:0x0140, B:72:0x0151, B:63:0x011f, B:50:0x00ef, B:51:0x00f2, B:17:0x008e, B:20:0x0097, B:21:0x009a, B:38:0x00cf, B:24:0x00a3, B:30:0x00b5, B:27:0x00ac, B:31:0x00ba, B:34:0x00c3, B:35:0x00c6, B:53:0x00f8, B:11:0x005f, B:7:0x0040), top: B:85:0x002b, inners: #0, #1, #2 }] */
    /* JADX WARN: Code duplicated, block: B:44:0x00e1  */
    /* JADX WARN: Code duplicated, block: B:46:0x00e4 A[Catch: all -> 0x017e, TRY_LEAVE, TryCatch #3 {all -> 0x017e, blocks: (B:3:0x002b, B:4:0x0037, B:8:0x0043, B:9:0x0057, B:12:0x0062, B:14:0x0070, B:15:0x008a, B:39:0x00d2, B:41:0x00d6, B:43:0x00dc, B:46:0x00e4, B:48:0x00ea, B:52:0x00f4, B:54:0x00fd, B:60:0x010e, B:62:0x0115, B:64:0x0125, B:73:0x015e, B:70:0x012f, B:71:0x0140, B:72:0x0151, B:63:0x011f, B:50:0x00ef, B:51:0x00f2, B:17:0x008e, B:20:0x0097, B:21:0x009a, B:38:0x00cf, B:24:0x00a3, B:30:0x00b5, B:27:0x00ac, B:31:0x00ba, B:34:0x00c3, B:35:0x00c6, B:53:0x00f8, B:11:0x005f, B:7:0x0040), top: B:85:0x002b, inners: #0, #1, #2 }] */
    /* JADX WARN: Code duplicated, block: B:51:0x00f2 A[Catch: all -> 0x017e, TryCatch #3 {all -> 0x017e, blocks: (B:3:0x002b, B:4:0x0037, B:8:0x0043, B:9:0x0057, B:12:0x0062, B:14:0x0070, B:15:0x008a, B:39:0x00d2, B:41:0x00d6, B:43:0x00dc, B:46:0x00e4, B:48:0x00ea, B:52:0x00f4, B:54:0x00fd, B:60:0x010e, B:62:0x0115, B:64:0x0125, B:73:0x015e, B:70:0x012f, B:71:0x0140, B:72:0x0151, B:63:0x011f, B:50:0x00ef, B:51:0x00f2, B:17:0x008e, B:20:0x0097, B:21:0x009a, B:38:0x00cf, B:24:0x00a3, B:30:0x00b5, B:27:0x00ac, B:31:0x00ba, B:34:0x00c3, B:35:0x00c6, B:53:0x00f8, B:11:0x005f, B:7:0x0040), top: B:85:0x002b, inners: #0, #1, #2 }] */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static final void runAsyncCreate$lambda$2(StageActivity stageActivity, float f, String cHex, float f2, String font, CreateTextLabelAction this$0, String rawText, int i, int i2, String id2, int i3, String bgHex, int i4, int i5) {
        Typeface typefaceDefaultFromStyle;
        Scope scope;
        File file;
        Project project;
        Intrinsics.checkNotNullParameter(cHex, "$cHex");
        Intrinsics.checkNotNullParameter(font, "$font");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(rawText, "$rawText");
        Intrinsics.checkNotNullParameter(id2, "$id");
        Intrinsics.checkNotNullParameter(bgHex, "$bgHex");
        try {
            TextView textView = new TextView(stageActivity);
            textView.setTextSize(f);
            try {
                textView.setTextColor(Color.parseColor(cHex));
            } catch (Exception unused) {
                textView.setTextColor(-1);
            }
            textView.setLineSpacing(0.0f, f2);
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setShape(0);
            gradientDrawable.setCornerRadius(i3);
            try {
                gradientDrawable.setColor(Color.parseColor(bgHex));
            } catch (Exception unused2) {
                gradientDrawable.setColor(0);
            }
            textView.setBackground(gradientDrawable);
            if (font.length() > 0) {
                String lowerCase = font.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                String string = StringsKt.trim((CharSequence) lowerCase).toString();
                switch (string.hashCode()) {
                    case -1536685117:
                        if (string.equals("sans-serif")) {
                            typefaceDefaultFromStyle = Typeface.SANS_SERIF;
                        } else {
                            scope = this$0.scope;
                            if (scope != null || (project = scope.getProject()) == null) {
                                file = null;
                            } else {
                                file = project.getFile(font);
                            }
                            if (file != null && file.exists()) {
                                try {
                                    typefaceDefaultFromStyle = Typeface.createFromFile(file);
                                } catch (Exception unused3) {
                                    typefaceDefaultFromStyle = Typeface.DEFAULT;
                                }
                            } else {
                                typefaceDefaultFromStyle = Typeface.DEFAULT;
                            }
                        }
                        textView.setTypeface(typefaceDefaultFromStyle);
                        break;
                    case -1431958525:
                        if (string.equals("monospace")) {
                            typefaceDefaultFromStyle = Typeface.MONOSPACE;
                        } else {
                            scope = this$0.scope;
                            if (scope != null) {
                                file = null;
                            } else {
                                file = null;
                            }
                            if (file != null) {
                                typefaceDefaultFromStyle = Typeface.DEFAULT;
                            } else {
                                typefaceDefaultFromStyle = Typeface.DEFAULT;
                            }
                        }
                        textView.setTypeface(typefaceDefaultFromStyle);
                        break;
                    case -696957295:
                        if (string.equals("default-bold")) {
                            typefaceDefaultFromStyle = Typeface.defaultFromStyle(1);
                        } else {
                            scope = this$0.scope;
                            if (scope != null) {
                                file = null;
                            } else {
                                file = null;
                            }
                            if (file != null) {
                                typefaceDefaultFromStyle = Typeface.DEFAULT;
                            } else {
                                typefaceDefaultFromStyle = Typeface.DEFAULT;
                            }
                        }
                        textView.setTypeface(typefaceDefaultFromStyle);
                        break;
                    case 3029637:
                        if (string.equals("bold")) {
                            typefaceDefaultFromStyle = Typeface.defaultFromStyle(1);
                        } else {
                            scope = this$0.scope;
                            if (scope != null) {
                                file = null;
                            } else {
                                file = null;
                            }
                            if (file != null) {
                                typefaceDefaultFromStyle = Typeface.DEFAULT;
                            } else {
                                typefaceDefaultFromStyle = Typeface.DEFAULT;
                            }
                        }
                        textView.setTypeface(typefaceDefaultFromStyle);
                        break;
                    case 3522707:
                        if (string.equals("sans")) {
                            typefaceDefaultFromStyle = Typeface.SANS_SERIF;
                        } else {
                            scope = this$0.scope;
                            if (scope != null) {
                                file = null;
                            } else {
                                file = null;
                            }
                            if (file != null) {
                                typefaceDefaultFromStyle = Typeface.DEFAULT;
                            } else {
                                typefaceDefaultFromStyle = Typeface.DEFAULT;
                            }
                        }
                        textView.setTypeface(typefaceDefaultFromStyle);
                        break;
                    case 109326717:
                        if (string.equals("serif")) {
                            typefaceDefaultFromStyle = Typeface.SERIF;
                        } else {
                            scope = this$0.scope;
                            if (scope != null) {
                                file = null;
                            } else {
                                file = null;
                            }
                            if (file != null) {
                                typefaceDefaultFromStyle = Typeface.DEFAULT;
                            } else {
                                typefaceDefaultFromStyle = Typeface.DEFAULT;
                            }
                        }
                        textView.setTypeface(typefaceDefaultFromStyle);
                        break;
                    default:
                        scope = this$0.scope;
                        if (scope != null) {
                            file = null;
                        } else {
                            file = null;
                        }
                        if (file != null) {
                            typefaceDefaultFromStyle = Typeface.DEFAULT;
                            textView.setTypeface(typefaceDefaultFromStyle);
                        } else {
                            typefaceDefaultFromStyle = Typeface.DEFAULT;
                            textView.setTypeface(typefaceDefaultFromStyle);
                        }
                        break;
                }
            } else {
                textView.setTypeface(Typeface.DEFAULT);
            }
            int i6 = this$0.alignMode;
            textView.setGravity(i6 != 1 ? i6 != 2 ? 8388627 : 8388629 : 17);
            if (this$0.formatMode == 1) {
                textView.setText(Html.fromHtml(rawText, 0));
            } else {
                textView.setText(rawText);
            }
            int i7 = this$0.scrollMode;
            if (i7 == 1) {
                textView.setMovementMethod(new ScrollingMovementMethod());
                textView.setVerticalScrollBarEnabled(true);
            } else if (i7 == 2) {
                textView.setSingleLine(true);
                textView.setMovementMethod(new ScrollingMovementMethod());
                textView.setHorizontalScrollBarEnabled(true);
            } else if (i7 == 3) {
                textView.setSingleLine(false);
                textView.setHorizontallyScrolling(true);
                textView.setMovementMethod(new ScrollingMovementMethod());
            }
            textView.setVisibility(NativeViewBindingManager.getDefaultVisibility());
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i, i2);
            layoutParams.leftMargin = i4;
            layoutParams.topMargin = i5;
            stageActivity.addViewToStage(id2, textView, layoutParams);
            this$0.finished = true;
        } catch (Throwable th) {
            this$0.finished = true;
            throw th;
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        super.restart();
        this.started = false;
        this.finished = false;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action, com.badlogic.gdx.utils.Pool.Poolable
    public void reset() {
        super.reset();
        this.started = false;
        this.finished = false;
    }
}
