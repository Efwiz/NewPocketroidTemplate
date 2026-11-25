package io.noties.markwon.image.svg;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import io.noties.markwon.image.MediaDecoder;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;

/* JADX INFO: loaded from: classes11.dex */
public class SvgPictureMediaDecoder extends MediaDecoder {
    public static final String CONTENT_TYPE = "image/svg+xml";

    public static SvgPictureMediaDecoder create() {
        return new SvgPictureMediaDecoder();
    }

    @Override // io.noties.markwon.image.MediaDecoder
    public Drawable decode(String str, InputStream inputStream) {
        try {
            return new PictureDrawable(SVG.getFromInputStream(inputStream).renderToPicture());
        } catch (SVGParseException e) {
            throw new IllegalStateException("Exception decoding SVG", e);
        }
    }

    @Override // io.noties.markwon.image.MediaDecoder
    public Collection<String> supportedTypes() {
        return Collections.singleton("image/svg+xml");
    }
}
