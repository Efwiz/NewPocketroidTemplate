package io.github.rosemoe.sora.lang.styling.line;

import androidx.exifinterface.media.ExifInterface;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: LineStyles.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\u0001J\u001e\u0010\f\u001a\u00020\u0003\"\b\b\u0000\u0010\r*\u00020\u00012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\r0\u000fJ$\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u0011\"\b\b\u0000\u0010\r*\u00020\u00012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\r0\u000fJ%\u0010\u0012\u001a\u0004\u0018\u0001H\r\"\b\b\u0000\u0010\r*\u00020\u00012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\r0\u000f¢\u0006\u0002\u0010\u0013J\u000e\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u0003J\u0006\u0010\u0016\u001a\u00020\u0003J\u0014\u0010\u0017\u001a\u00020\u00032\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00180\u000fJ\u0006\u0010\u0019\u001a\u00020\u001aR\u001a\u0010\u0002\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lio/github/rosemoe/sora/lang/styling/line/LineStyles;", "Lio/github/rosemoe/sora/lang/styling/line/LineAnchorStyle;", Constant.LINE, "", "(I)V", "getLine", "()I", "setLine", "styles", "", "addStyle", "style", "eraseStyle", ExifInterface.GPS_DIRECTION_TRUE, ModuleXmlParser.TYPE, "Ljava/lang/Class;", "findAll", "", "findOne", "(Ljava/lang/Class;)Lio/github/rosemoe/sora/lang/styling/line/LineAnchorStyle;", "getElementAt", "index", "getElementCount", "typedElementCount", "", "updateElements", "", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LineStyles extends LineAnchorStyle {
    private int line;
    private final List<LineAnchorStyle> styles;

    public LineStyles(int i) {
        super(i);
        this.line = i;
        this.styles = new ArrayList();
    }

    @Override // io.github.rosemoe.sora.lang.styling.line.LineAnchorStyle
    public int getLine() {
        return this.line;
    }

    @Override // io.github.rosemoe.sora.lang.styling.line.LineAnchorStyle
    public void setLine(int i) {
        this.line = i;
    }

    public final int addStyle(LineAnchorStyle style) {
        int i;
        Intrinsics.checkNotNullParameter(style, "style");
        if (style instanceof LineStyles) {
            throw new IllegalArgumentException("Can not add LineStyles object");
        }
        if (style.getLine() != getLine()) {
            throw new IllegalArgumentException("target line differs from this object");
        }
        if (findOne(style.getClass()) != null) {
            eraseStyle(style.getClass());
            i = 0;
        } else {
            i = 1;
        }
        this.styles.add(style);
        return i;
    }

    public final <T extends LineAnchorStyle> int eraseStyle(Class<T> type) {
        Intrinsics.checkNotNullParameter(type, "type");
        List<LineAnchorStyle> listFindAll = findAll(type);
        this.styles.removeAll(listFindAll);
        return listFindAll.size();
    }

    public final void updateElements() {
        Iterator<T> it = this.styles.iterator();
        while (it.hasNext()) {
            ((LineAnchorStyle) it.next()).setLine(getLine());
        }
    }

    public final int getElementCount() {
        return this.styles.size();
    }

    public final LineAnchorStyle getElementAt(int index) {
        return this.styles.get(index);
    }

    public final <T extends LineAnchorStyle> T findOne(Class<T> type) {
        Object next;
        Intrinsics.checkNotNullParameter(type, "type");
        Iterator<T> it = this.styles.iterator();
        while (it.hasNext()) {
            next = it.next();
            if (type.isInstance((LineAnchorStyle) next)) {
                return (T) next;
            }
        }
        next = null;
        return (T) next;
    }

    public final <T extends LineAnchorStyle> List<LineAnchorStyle> findAll(Class<T> type) {
        Intrinsics.checkNotNullParameter(type, "type");
        List<LineAnchorStyle> list = this.styles;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (type.isInstance((LineAnchorStyle) obj)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public final int typedElementCount(Class<Object> type) {
        Intrinsics.checkNotNullParameter(type, "type");
        List<LineAnchorStyle> list = this.styles;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (type.isInstance((LineAnchorStyle) obj)) {
                arrayList.add(obj);
            }
        }
        return arrayList.size();
    }
}
