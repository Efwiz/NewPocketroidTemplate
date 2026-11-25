package io.github.rosemoe.sora.widget.schemes;

import android.util.SparseIntArray;
import androidx.core.view.InputDeviceCompat;
import io.github.rosemoe.sora.widget.CodeEditor;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes11.dex */
public class EditorColorScheme {
    public static final int ANNOTATION = 28;
    public static final int ATTRIBUTE_NAME = 33;
    public static final int ATTRIBUTE_VALUE = 34;
    private static final int BACKGROUND_COLOR_DARK = -14606047;
    private static final int BACKGROUND_COLOR_LIGHT = -65794;
    public static final int BLOCK_LINE = 14;
    public static final int BLOCK_LINE_CURRENT = 15;
    public static final int COMMENT = 22;
    public static final int COMPLETION_WND_BACKGROUND = 19;
    public static final int COMPLETION_WND_CORNER = 20;
    public static final int COMPLETION_WND_ITEM_CURRENT = 44;
    public static final int COMPLETION_WND_TEXT_PRIMARY = 42;
    public static final int COMPLETION_WND_TEXT_SECONDARY = 43;
    public static final int CURRENT_LINE = 9;
    public static final int DIAGNOSTIC_TOOLTIP_ACTION = 56;
    public static final int DIAGNOSTIC_TOOLTIP_BACKGROUND = 53;
    public static final int DIAGNOSTIC_TOOLTIP_BRIEF_MSG = 54;
    public static final int DIAGNOSTIC_TOOLTIP_DETAILED_MSG = 55;
    protected static final int END_COLOR_ID = 64;
    public static final int FUNCTION_CHAR_BACKGROUND_STROKE = 52;
    public static final int FUNCTION_NAME = 27;
    public static final int HARD_WRAP_MARKER = 51;
    public static final int HIGHLIGHTED_DELIMITERS_BACKGROUND = 41;
    public static final int HIGHLIGHTED_DELIMITERS_FOREGROUND = 39;
    public static final int HIGHLIGHTED_DELIMITERS_UNDERLINE = 40;
    public static final int HTML_TAG = 32;
    public static final int IDENTIFIER_NAME = 26;
    public static final int IDENTIFIER_VAR = 25;
    public static final int KEYWORD = 21;
    public static final int LINE_BLOCK_LABEL = 18;
    public static final int LINE_DIVIDER = 1;
    public static final int LINE_NUMBER = 2;
    public static final int LINE_NUMBER_BACKGROUND = 3;
    public static final int LINE_NUMBER_CURRENT = 45;
    public static final int LINE_NUMBER_PANEL = 16;
    public static final int LINE_NUMBER_PANEL_TEXT = 17;
    public static final int LITERAL = 24;
    public static final int MATCHED_TEXT_BACKGROUND = 29;
    public static final int NON_PRINTABLE_CHAR = 31;
    public static final int OPERATOR = 23;
    private static final int PRIMARY_TEXT_COLOR_DEFAULT_DARK = -657931;
    private static final int PRIMARY_TEXT_COLOR_DEFAULT_LIGHT = -12434878;
    public static final int PROBLEM_ERROR = 35;
    public static final int PROBLEM_TYPO = 37;
    public static final int PROBLEM_WARNING = 36;
    public static final int SCROLL_BAR_THUMB = 11;
    public static final int SCROLL_BAR_THUMB_PRESSED = 12;
    public static final int SCROLL_BAR_TRACK = 13;
    private static final int SECONDARY_TEXT_COLOR_DARK = -1118482;
    private static final int SECONDARY_TEXT_COLOR_LIGHT = -10395295;
    public static final int SELECTED_TEXT_BACKGROUND = 6;
    public static final int SELECTION_HANDLE = 8;
    public static final int SELECTION_INSERT = 7;
    public static final int SIDE_BLOCK_LINE = 38;
    public static final int SIGNATURE_BACKGROUND = 60;
    public static final int SIGNATURE_TEXT_HIGHLIGHTED_PARAMETER = 59;
    public static final int SIGNATURE_TEXT_NORMAL = 58;
    public static final int SNIPPET_BACKGROUND_EDITING = 48;
    public static final int SNIPPET_BACKGROUND_INACTIVE = 46;
    public static final int SNIPPET_BACKGROUND_RELATED = 47;
    protected static final int START_COLOR_ID = 1;
    public static final int STATIC_SPAN_BACKGROUND = 63;
    public static final int STATIC_SPAN_FOREGROUND = 64;
    public static final int STICKY_SCROLL_DIVIDER = 62;
    public static final int STRIKETHROUGH = 57;
    public static final int STRIKE_THROUGH = 57;
    public static final int TEXT_INLAY_HINT_BACKGROUND = 49;
    public static final int TEXT_INLAY_HINT_FOREGROUND = 50;
    public static final int TEXT_NORMAL = 5;
    public static final int TEXT_SELECTED = 30;
    public static final int UNDERLINE = 10;
    public static final int WHOLE_BACKGROUND = 4;
    private static EditorColorScheme globalDefault = new EditorColorScheme();
    protected final SparseIntArray colors;
    private final boolean dark;
    private final List<WeakReference<CodeEditor>> editors;

    public EditorColorScheme(CodeEditor codeEditor) {
        this();
        attachEditor(codeEditor);
    }

    public EditorColorScheme() {
        this(false);
    }

    protected EditorColorScheme(boolean z) {
        this.colors = new SparseIntArray();
        this.editors = new ArrayList();
        this.dark = z;
        applyDefault();
    }

    public void attachEditor(CodeEditor codeEditor) {
        Objects.requireNonNull(codeEditor);
        Iterator<WeakReference<CodeEditor>> it = this.editors.iterator();
        while (it.hasNext()) {
            if (it.next().get() == codeEditor) {
                return;
            }
        }
        this.editors.add(new WeakReference<>(codeEditor));
        codeEditor.onColorFullUpdate();
    }

    public void detachEditor(CodeEditor codeEditor) {
        Iterator<WeakReference<CodeEditor>> it = this.editors.iterator();
        while (it.hasNext()) {
            if (it.next().get() == codeEditor) {
                it.remove();
                return;
            }
        }
    }

    public void applyDefault() {
        for (int i = 1; i <= 64; i++) {
            applyDefault(i);
        }
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0055  */
    /* JADX WARN: Code duplicated, block: B:37:0x0083  */
    private void applyDefault(int i) {
        int i2 = this.colors.get(i);
        switch (i) {
            case 1:
            case 3:
                i2 = -286331154;
                break;
            case 2:
            case 45:
                i2 = -11513776;
                break;
            case 4:
            case 17:
            case 19:
            case 20:
            case 63:
                if (!isDark()) {
                    i2 = -1;
                } else {
                    i2 = BACKGROUND_COLOR_DARK;
                }
                break;
            case 5:
            case 64:
                i2 = -13421773;
                break;
            case 6:
            case 52:
                i2 = 759124405;
                break;
            case 7:
                i2 = -581734914;
                break;
            case 8:
                i2 = -11309570;
                break;
            case 9:
                i2 = 268435456;
                break;
            case 10:
                i2 = -16777216;
                break;
            case 11:
                i2 = -2565928;
                break;
            case 12:
                i2 = -14210774;
                break;
            case 13:
            case 18:
            case 30:
            case 57:
                i2 = 0;
                break;
            case 14:
                i2 = -2236963;
                break;
            case 15:
            case 38:
                i2 = -6710887;
                break;
            case 16:
            case 39:
                i2 = -587202560;
                break;
            case 21:
                i2 = -14575885;
                break;
            case 22:
                i2 = -5723992;
                break;
            case 23:
                i2 = -16750890;
                break;
            case 24:
                i2 = -16744320;
                break;
            case 25:
                i2 = -11243910;
                break;
            case 26:
            case 28:
            case 59:
                i2 = -16537100;
                break;
            case 27:
                i2 = -2080517;
                break;
            case 29:
                i2 = InputDeviceCompat.SOURCE_ANY;
                break;
            case 31:
                i2 = -288568116;
                break;
            case 35:
                i2 = -1426128896;
                break;
            case 36:
                i2 = -1426067200;
                break;
            case 37:
                i2 = 1711341329;
                break;
            case 40:
                i2 = -12627531;
                break;
            case 41:
                i2 = 486539264;
                break;
            case 42:
            case 43:
            case 50:
                if (!isDark()) {
                    i2 = -16777216;
                } else {
                    i2 = -1;
                }
                break;
            case 44:
                i2 = SECONDARY_TEXT_COLOR_DARK;
                break;
            case 46:
                i2 = 1725816285;
                break;
            case 47:
                i2 = -1428300323;
                break;
            case 48:
                i2 = -3355444;
                break;
            case 49:
                i2 = !isDark() ? 486539264 : SECONDARY_TEXT_COLOR_DARK;
                break;
            case 51:
                i2 = !isDark() ? SECONDARY_TEXT_COLOR_DARK : 486539264;
                break;
            case 53:
            case 60:
                if (!isDark()) {
                    i2 = BACKGROUND_COLOR_LIGHT;
                } else {
                    i2 = BACKGROUND_COLOR_DARK;
                }
                break;
            case 54:
                i2 = !isDark() ? PRIMARY_TEXT_COLOR_DEFAULT_LIGHT : PRIMARY_TEXT_COLOR_DEFAULT_DARK;
                break;
            case 55:
                i2 = !isDark() ? SECONDARY_TEXT_COLOR_LIGHT : SECONDARY_TEXT_COLOR_DARK;
                break;
            case 56:
                i2 = -12409355;
                break;
            case 58:
                i2 = !isDark() ? -16777216 : SECONDARY_TEXT_COLOR_DARK;
                break;
            case 62:
                i2 = !isDark() ? -1728053248 : -1712394514;
                break;
        }
        setColor(i, i2);
    }

    public void setColor(int i, int i2) {
        if (getColor(i) == i2) {
            return;
        }
        this.colors.put(i, i2);
        Iterator<WeakReference<CodeEditor>> it = this.editors.iterator();
        while (it.hasNext()) {
            CodeEditor codeEditor = it.next().get();
            if (codeEditor == null) {
                it.remove();
            } else {
                codeEditor.onColorUpdated(i);
            }
        }
    }

    public int getColor(int i) {
        return this.colors.get(i);
    }

    public boolean isDark() {
        return this.dark;
    }

    public static EditorColorScheme getDefault() {
        return globalDefault;
    }

    public static void setDefault(EditorColorScheme editorColorScheme) {
        setDefault(editorColorScheme, false);
    }

    public static void setDefault(EditorColorScheme editorColorScheme, boolean z) {
        if (editorColorScheme == null) {
            editorColorScheme = new EditorColorScheme();
        }
        if (z) {
            for (WeakReference weakReference : (WeakReference[]) globalDefault.editors.toArray(new WeakReference[0])) {
                CodeEditor codeEditor = (CodeEditor) weakReference.get();
                if (codeEditor != null) {
                    codeEditor.setColorScheme(editorColorScheme);
                }
            }
        }
        globalDefault = editorColorScheme;
    }
}
