package io.github.rosemoe.sora.lang.styling.color;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: EditorColor.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lio/github/rosemoe/sora/lang/styling/color/EditorColor;", "Lio/github/rosemoe/sora/lang/styling/color/ResolvableColor;", "colorId", "", "(I)V", "resolve", LanguageCodeUtil.CS, "Lio/github/rosemoe/sora/widget/schemes/EditorColorScheme;", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EditorColor implements ResolvableColor {
    private final int colorId;

    public EditorColor(int i) {
        this.colorId = i;
    }

    @Override // io.github.rosemoe.sora.lang.styling.color.ResolvableColor
    public int resolve(EditorColorScheme cs) {
        Intrinsics.checkNotNullParameter(cs, "cs");
        return cs.getColor(this.colorId);
    }
}
