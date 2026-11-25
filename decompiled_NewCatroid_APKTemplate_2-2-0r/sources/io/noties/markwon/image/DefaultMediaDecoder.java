package io.noties.markwon.image;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;

/* JADX INFO: loaded from: classes11.dex */
public class DefaultMediaDecoder extends MediaDecoder {
    private final Resources resources;

    public static DefaultMediaDecoder create() {
        return new DefaultMediaDecoder(Resources.getSystem());
    }

    public static DefaultMediaDecoder create(Resources resources) {
        return new DefaultMediaDecoder(resources);
    }

    DefaultMediaDecoder(Resources resources) {
        this.resources = resources;
    }

    @Override // io.noties.markwon.image.MediaDecoder
    public Drawable decode(String str, InputStream inputStream) {
        try {
            return new BitmapDrawable(this.resources, BitmapFactory.decodeStream(inputStream));
        } catch (Throwable th) {
            throw new IllegalStateException("Exception decoding input-stream", th);
        }
    }

    @Override // io.noties.markwon.image.MediaDecoder
    public Collection<String> supportedTypes() {
        return Collections.emptySet();
    }
}
