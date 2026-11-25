package org.catrobat.paintroid.tools;

import java.util.EnumSet;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.ui.SpriteActivity;
import org.catrobat.paintroid.R;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'PIPETTE' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:485)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByField(EnumVisitor.java:399)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByWrappedInsn(EnumVisitor.java:364)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:349)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:284)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInvoke(EnumVisitor.java:315)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:288)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:153)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:102)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: compiled from: ToolType.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u001e\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001BE\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\bR\u0013\u0010\u0005\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u0004\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0013\u0010\u0002\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u0013\u0010\n\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\t\u001a\u00020\u00038\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000fj\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001dj\u0002\b\u001ej\u0002\b\u001fj\u0002\b j\u0002\b!j\u0002\b\"j\u0002\b#j\u0002\b$j\u0002\b%j\u0002\b&j\u0002\b'j\u0002\b(j\u0002\b)¨\u0006*"}, d2 = {"Lorg/catrobat/paintroid/tools/ToolType;", "", "nameResource", "", "helpTextResource", "drawableResource", "stateChangeBehaviour", "Ljava/util/EnumSet;", "Lorg/catrobat/paintroid/tools/Tool$StateChange;", "toolButtonID", "overlayDrawableResource", "hasOptions", "", "(Ljava/lang/String;IIIILjava/util/EnumSet;IIZ)V", "getDrawableResource", "()I", "getHelpTextResource", "getNameResource", "getOverlayDrawableResource", "getToolButtonID", "shouldReactToStateChange", "stateChange", "PIPETTE", "BRUSH", "UNDO", "REDO", "FILL", "CLIPBOARD", "LINE", "CURSOR", "IMPORTPNG", "TRANSFORM", "ERASER", "SHAPE", SpriteActivity.EXTRA_TEXT, "LAYER", "COLORCHOOSER", "HAND", "SPRAY", "WATERCOLOR", "SMUDGE", "CLIP", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class ToolType {
    private static final /* synthetic */ ToolType[] $VALUES;
    public static final ToolType BRUSH;
    public static final ToolType CLIP;
    public static final ToolType CLIPBOARD;
    public static final ToolType COLORCHOOSER;
    public static final ToolType CURSOR;
    public static final ToolType ERASER;
    public static final ToolType FILL;
    public static final ToolType HAND;
    public static final ToolType IMPORTPNG;
    public static final ToolType LAYER;
    public static final ToolType LINE;
    public static final ToolType PIPETTE;
    public static final ToolType REDO;
    public static final ToolType SHAPE;
    public static final ToolType SMUDGE;
    public static final ToolType SPRAY;
    public static final ToolType TEXT;
    public static final ToolType TRANSFORM;
    public static final ToolType UNDO;
    public static final ToolType WATERCOLOR;
    private final int drawableResource;
    private final boolean hasOptions;
    private final int helpTextResource;
    private final int nameResource;
    private final int overlayDrawableResource;
    private final EnumSet<Tool.StateChange> stateChangeBehaviour;
    private final int toolButtonID;

    private static final /* synthetic */ ToolType[] $values() {
        return new ToolType[]{PIPETTE, BRUSH, UNDO, REDO, FILL, CLIPBOARD, LINE, CURSOR, IMPORTPNG, TRANSFORM, ERASER, SHAPE, TEXT, LAYER, COLORCHOOSER, HAND, SPRAY, WATERCOLOR, SMUDGE, CLIP};
    }

    public static ToolType valueOf(String str) {
        return (ToolType) Enum.valueOf(ToolType.class, str);
    }

    public static ToolType[] values() {
        return (ToolType[]) $VALUES.clone();
    }

    private ToolType(String str, int i, int i2, int i3, int i4, EnumSet enumSet, int i5, int i6, boolean z) {
        super(str, i);
        this.nameResource = i2;
        this.helpTextResource = i3;
        this.drawableResource = i4;
        this.stateChangeBehaviour = enumSet;
        this.toolButtonID = i5;
        this.overlayDrawableResource = i6;
        this.hasOptions = z;
    }

    public final int getNameResource() {
        return this.nameResource;
    }

    public final int getHelpTextResource() {
        return this.helpTextResource;
    }

    public final int getDrawableResource() {
        return this.drawableResource;
    }

    public final int getToolButtonID() {
        return this.toolButtonID;
    }

    public final int getOverlayDrawableResource() {
        return this.overlayDrawableResource;
    }

    static {
        int i = R.string.button_pipette;
        int i2 = R.string.help_content_eyedropper;
        int i3 = R.drawable.ic_pocketpaint_tool_pipette;
        EnumSet enumSetOf = EnumSet.of(Tool.StateChange.ALL);
        Intrinsics.checkNotNullExpressionValue(enumSetOf, "of(StateChange.ALL)");
        PIPETTE = new ToolType("PIPETTE", 0, i, i2, i3, enumSetOf, R.id.pocketpaint_tools_pipette, 0, false);
        int i4 = R.string.button_brush;
        int i5 = R.string.help_content_brush;
        int i6 = R.drawable.ic_pocketpaint_tool_brush;
        EnumSet enumSetOf2 = EnumSet.of(Tool.StateChange.ALL);
        Intrinsics.checkNotNullExpressionValue(enumSetOf2, "of(StateChange.ALL)");
        BRUSH = new ToolType("BRUSH", 1, i4, i5, i6, enumSetOf2, R.id.pocketpaint_tools_brush, 0, true);
        int i7 = R.string.button_undo;
        int i8 = R.string.help_content_undo;
        int i9 = R.drawable.ic_pocketpaint_undo;
        EnumSet enumSetOf3 = EnumSet.of(Tool.StateChange.ALL);
        Intrinsics.checkNotNullExpressionValue(enumSetOf3, "of(StateChange.ALL)");
        UNDO = new ToolType("UNDO", 2, i7, i8, i9, enumSetOf3, R.id.pocketpaint_btn_top_undo, 0, false);
        int i10 = R.string.button_redo;
        int i11 = R.string.help_content_redo;
        int i12 = R.drawable.ic_pocketpaint_redo;
        EnumSet enumSetOf4 = EnumSet.of(Tool.StateChange.ALL);
        Intrinsics.checkNotNullExpressionValue(enumSetOf4, "of(StateChange.ALL)");
        REDO = new ToolType("REDO", 3, i10, i11, i12, enumSetOf4, R.id.pocketpaint_btn_top_redo, 0, false);
        int i13 = R.string.button_fill;
        int i14 = R.string.help_content_fill;
        int i15 = R.drawable.ic_pocketpaint_tool_fill;
        EnumSet enumSetOf5 = EnumSet.of(Tool.StateChange.ALL);
        Intrinsics.checkNotNullExpressionValue(enumSetOf5, "of(StateChange.ALL)");
        FILL = new ToolType("FILL", 4, i13, i14, i15, enumSetOf5, R.id.pocketpaint_tools_fill, 0, true);
        int i16 = R.string.button_clipboard;
        int i17 = R.string.help_content_clipboard;
        int i18 = R.drawable.ic_pocketpaint_tool_clipboard;
        EnumSet enumSetOf6 = EnumSet.of(Tool.StateChange.ALL);
        Intrinsics.checkNotNullExpressionValue(enumSetOf6, "of(StateChange.ALL)");
        CLIPBOARD = new ToolType("CLIPBOARD", 5, i16, i17, i18, enumSetOf6, R.id.pocketpaint_tools_stamp, 0, true);
        int i19 = R.string.button_line;
        int i20 = R.string.help_content_line;
        int i21 = R.drawable.ic_pocketpaint_tool_line;
        EnumSet enumSetOf7 = EnumSet.of(Tool.StateChange.ALL);
        Intrinsics.checkNotNullExpressionValue(enumSetOf7, "of(StateChange.ALL)");
        LINE = new ToolType("LINE", 6, i19, i20, i21, enumSetOf7, R.id.pocketpaint_tools_line, 0, true);
        int i22 = R.string.button_cursor;
        int i23 = R.string.help_content_cursor;
        int i24 = R.drawable.ic_pocketpaint_tool_cursor;
        EnumSet enumSetOf8 = EnumSet.of(Tool.StateChange.ALL);
        Intrinsics.checkNotNullExpressionValue(enumSetOf8, "of(StateChange.ALL)");
        CURSOR = new ToolType("CURSOR", 7, i22, i23, i24, enumSetOf8, R.id.pocketpaint_tools_cursor, 0, true);
        int i25 = R.string.button_import_image;
        int i26 = R.string.help_content_import_png;
        int i27 = R.drawable.ic_pocketpaint_tool_import;
        EnumSet enumSetOf9 = EnumSet.of(Tool.StateChange.ALL);
        Intrinsics.checkNotNullExpressionValue(enumSetOf9, "of(StateChange.ALL)");
        IMPORTPNG = new ToolType("IMPORTPNG", 8, i25, i26, i27, enumSetOf9, R.id.pocketpaint_tools_import, 0, false);
        int i28 = R.string.button_transform;
        int i29 = R.string.help_content_transform;
        int i30 = R.drawable.ic_pocketpaint_tool_transform;
        EnumSet enumSetOf10 = EnumSet.of(Tool.StateChange.RESET_INTERNAL_STATE, Tool.StateChange.NEW_IMAGE_LOADED);
        Intrinsics.checkNotNullExpressionValue(enumSetOf10, "of(StateChange.RESET_INT…eChange.NEW_IMAGE_LOADED)");
        TRANSFORM = new ToolType("TRANSFORM", 9, i28, i29, i30, enumSetOf10, R.id.pocketpaint_tools_transform, 0, true);
        int i31 = R.string.button_eraser;
        int i32 = R.string.help_content_eraser;
        int i33 = R.drawable.ic_pocketpaint_tool_eraser;
        EnumSet enumSetOf11 = EnumSet.of(Tool.StateChange.ALL);
        Intrinsics.checkNotNullExpressionValue(enumSetOf11, "of(StateChange.ALL)");
        ERASER = new ToolType("ERASER", 10, i31, i32, i33, enumSetOf11, R.id.pocketpaint_tools_eraser, 0, true);
        int i34 = R.string.button_shape;
        int i35 = R.string.help_content_shape;
        int i36 = R.drawable.ic_pocketpaint_tool_rectangle;
        EnumSet enumSetOf12 = EnumSet.of(Tool.StateChange.ALL);
        Intrinsics.checkNotNullExpressionValue(enumSetOf12, "of(StateChange.ALL)");
        SHAPE = new ToolType("SHAPE", 11, i34, i35, i36, enumSetOf12, R.id.pocketpaint_tools_rectangle, 0, true);
        int i37 = R.string.button_text;
        int i38 = R.string.help_content_text;
        int i39 = R.drawable.ic_pocketpaint_tool_text;
        EnumSet enumSetOf13 = EnumSet.of(Tool.StateChange.ALL);
        Intrinsics.checkNotNullExpressionValue(enumSetOf13, "of(StateChange.ALL)");
        TEXT = new ToolType(SpriteActivity.EXTRA_TEXT, 12, i37, i38, i39, enumSetOf13, R.id.pocketpaint_tools_text, 0, true);
        int i40 = R.string.layers_title;
        int i41 = R.string.help_content_layer;
        int i42 = R.drawable.ic_pocketpaint_layers;
        EnumSet enumSetOf14 = EnumSet.of(Tool.StateChange.ALL);
        Intrinsics.checkNotNullExpressionValue(enumSetOf14, "of(StateChange.ALL)");
        LAYER = new ToolType("LAYER", 13, i40, i41, i42, enumSetOf14, 0, 0, false);
        int i43 = R.string.color_picker_title;
        int i44 = R.string.help_content_color_chooser;
        int i45 = R.drawable.ic_pocketpaint_color_palette;
        EnumSet enumSetOf15 = EnumSet.of(Tool.StateChange.ALL);
        Intrinsics.checkNotNullExpressionValue(enumSetOf15, "of(StateChange.ALL)");
        COLORCHOOSER = new ToolType("COLORCHOOSER", 14, i43, i44, i45, enumSetOf15, 0, 0, false);
        int i46 = R.string.button_hand;
        int i47 = R.string.help_content_hand;
        int i48 = R.drawable.ic_pocketpaint_tool_hand;
        EnumSet enumSetOf16 = EnumSet.of(Tool.StateChange.ALL);
        Intrinsics.checkNotNullExpressionValue(enumSetOf16, "of(StateChange.ALL)");
        HAND = new ToolType("HAND", 15, i46, i47, i48, enumSetOf16, R.id.pocketpaint_tools_hand, 0, false);
        int i49 = R.string.button_spray_can;
        int i50 = R.string.help_content_spray_can;
        int i51 = R.drawable.ic_pocketpaint_tool_spray_can;
        EnumSet enumSetOf17 = EnumSet.of(Tool.StateChange.ALL);
        Intrinsics.checkNotNullExpressionValue(enumSetOf17, "of(StateChange.ALL)");
        SPRAY = new ToolType("SPRAY", 16, i49, i50, i51, enumSetOf17, R.id.pocketpaint_tools_spray_can, 0, true);
        int i52 = R.string.button_watercolor;
        int i53 = R.string.help_content_watercolor;
        int i54 = R.drawable.ic_pocketpaint_tool_watercolor;
        EnumSet enumSetOf18 = EnumSet.of(Tool.StateChange.ALL);
        Intrinsics.checkNotNullExpressionValue(enumSetOf18, "of(StateChange.ALL)");
        WATERCOLOR = new ToolType("WATERCOLOR", 17, i52, i53, i54, enumSetOf18, R.id.pocketpaint_tools_watercolor, 0, true);
        int i55 = R.string.button_smudge;
        int i56 = R.string.help_content_smudge;
        int i57 = R.drawable.ic_pocketpaint_tool_smudge;
        EnumSet enumSetOf19 = EnumSet.of(Tool.StateChange.ALL);
        Intrinsics.checkNotNullExpressionValue(enumSetOf19, "of(StateChange.ALL)");
        SMUDGE = new ToolType("SMUDGE", 18, i55, i56, i57, enumSetOf19, R.id.pocketpaint_tools_smudge, 0, true);
        int i58 = R.string.button_clip;
        int i59 = R.string.help_content_clip;
        int i60 = R.drawable.ic_pocketpaint_tool_clipping;
        EnumSet enumSetOf20 = EnumSet.of(Tool.StateChange.RESET_INTERNAL_STATE);
        Intrinsics.checkNotNullExpressionValue(enumSetOf20, "of(StateChange.RESET_INTERNAL_STATE)");
        CLIP = new ToolType("CLIP", 19, i58, i59, i60, enumSetOf20, R.id.pocketpaint_tools_clipping, 0, true);
        $VALUES = $values();
    }

    public final boolean shouldReactToStateChange(Tool.StateChange stateChange) {
        Intrinsics.checkNotNullParameter(stateChange, "stateChange");
        return this.stateChangeBehaviour.contains(Tool.StateChange.ALL) || this.stateChangeBehaviour.contains(stateChange);
    }

    /* JADX INFO: renamed from: hasOptions, reason: from getter */
    public final boolean getHasOptions() {
        return this.hasOptions;
    }
}
