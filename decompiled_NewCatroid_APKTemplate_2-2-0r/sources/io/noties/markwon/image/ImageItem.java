package io.noties.markwon.image;

import android.graphics.drawable.Drawable;
import java.io.InputStream;

/* JADX INFO: loaded from: classes11.dex */
public abstract class ImageItem {
    public abstract WithDecodingNeeded getAsWithDecodingNeeded();

    public abstract WithResult getAsWithResult();

    public abstract boolean hasDecodingNeeded();

    public abstract boolean hasResult();

    public static ImageItem withResult(Drawable drawable) {
        return new WithResult(drawable);
    }

    public static ImageItem withDecodingNeeded(String str, InputStream inputStream) {
        return new WithDecodingNeeded(str, inputStream);
    }

    private ImageItem() {
    }

    public static class WithResult extends ImageItem {
        private final Drawable result;

        @Override // io.noties.markwon.image.ImageItem
        public WithResult getAsWithResult() {
            return this;
        }

        @Override // io.noties.markwon.image.ImageItem
        public boolean hasDecodingNeeded() {
            return false;
        }

        @Override // io.noties.markwon.image.ImageItem
        public boolean hasResult() {
            return true;
        }

        private WithResult(Drawable drawable) {
            super();
            this.result = drawable;
        }

        public Drawable result() {
            return this.result;
        }

        @Override // io.noties.markwon.image.ImageItem
        public WithDecodingNeeded getAsWithDecodingNeeded() {
            throw new IllegalStateException();
        }
    }

    public static class WithDecodingNeeded extends ImageItem {
        private final String contentType;
        private final InputStream inputStream;

        @Override // io.noties.markwon.image.ImageItem
        public WithDecodingNeeded getAsWithDecodingNeeded() {
            return this;
        }

        @Override // io.noties.markwon.image.ImageItem
        public boolean hasDecodingNeeded() {
            return true;
        }

        @Override // io.noties.markwon.image.ImageItem
        public boolean hasResult() {
            return false;
        }

        private WithDecodingNeeded(String str, InputStream inputStream) {
            super();
            this.contentType = str;
            this.inputStream = inputStream;
        }

        public String contentType() {
            return this.contentType;
        }

        public InputStream inputStream() {
            return this.inputStream;
        }

        @Override // io.noties.markwon.image.ImageItem
        public WithResult getAsWithResult() {
            throw new IllegalStateException();
        }
    }
}
