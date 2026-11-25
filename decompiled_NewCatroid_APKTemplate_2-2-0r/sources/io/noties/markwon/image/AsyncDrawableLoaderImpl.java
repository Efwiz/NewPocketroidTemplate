package io.noties.markwon.image;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/* JADX INFO: loaded from: classes11.dex */
class AsyncDrawableLoaderImpl extends AsyncDrawableLoader {
    private final MediaDecoder defaultMediaDecoder;
    private final ImagesPlugin.ErrorHandler errorHandler;
    private final ExecutorService executorService;
    private final Handler handler;
    private final Map<String, MediaDecoder> mediaDecoders;
    private final ImagesPlugin.PlaceholderProvider placeholderProvider;
    private final Map<AsyncDrawable, Future<?>> requests;
    private final Map<String, SchemeHandler> schemeHandlers;

    AsyncDrawableLoaderImpl(AsyncDrawableLoaderBuilder asyncDrawableLoaderBuilder) {
        this(asyncDrawableLoaderBuilder, new Handler(Looper.getMainLooper()));
    }

    AsyncDrawableLoaderImpl(AsyncDrawableLoaderBuilder asyncDrawableLoaderBuilder, Handler handler) {
        this.requests = new HashMap(2);
        this.executorService = asyncDrawableLoaderBuilder.executorService;
        this.schemeHandlers = asyncDrawableLoaderBuilder.schemeHandlers;
        this.mediaDecoders = asyncDrawableLoaderBuilder.mediaDecoders;
        this.defaultMediaDecoder = asyncDrawableLoaderBuilder.defaultMediaDecoder;
        this.placeholderProvider = asyncDrawableLoaderBuilder.placeholderProvider;
        this.errorHandler = asyncDrawableLoaderBuilder.errorHandler;
        this.handler = handler;
    }

    @Override // io.noties.markwon.image.AsyncDrawableLoader
    public void load(AsyncDrawable asyncDrawable) {
        if (this.requests.get(asyncDrawable) == null) {
            this.requests.put(asyncDrawable, execute(asyncDrawable));
        }
    }

    @Override // io.noties.markwon.image.AsyncDrawableLoader
    public void cancel(AsyncDrawable asyncDrawable) {
        Future<?> futureRemove = this.requests.remove(asyncDrawable);
        if (futureRemove != null) {
            futureRemove.cancel(true);
        }
        this.handler.removeCallbacksAndMessages(asyncDrawable);
    }

    @Override // io.noties.markwon.image.AsyncDrawableLoader
    public Drawable placeholder(AsyncDrawable asyncDrawable) {
        ImagesPlugin.PlaceholderProvider placeholderProvider = this.placeholderProvider;
        if (placeholderProvider != null) {
            return placeholderProvider.providePlaceholder(asyncDrawable);
        }
        return null;
    }

    private Future<?> execute(final AsyncDrawable asyncDrawable) {
        return this.executorService.submit(new Runnable() { // from class: io.noties.markwon.image.AsyncDrawableLoaderImpl.1
            @Override // java.lang.Runnable
            public void run() {
                Rect bounds;
                String destination = asyncDrawable.getDestination();
                Uri uri = Uri.parse(destination);
                final Drawable drawableHandleError = null;
                try {
                    String scheme = uri.getScheme();
                    if (scheme == null || scheme.length() == 0) {
                        throw new IllegalStateException("No scheme is found: " + destination);
                    }
                    SchemeHandler schemeHandler = (SchemeHandler) AsyncDrawableLoaderImpl.this.schemeHandlers.get(scheme);
                    if (schemeHandler == null) {
                        throw new IllegalStateException("No scheme-handler is found: " + destination);
                    }
                    ImageItem imageItemHandle = schemeHandler.handle(destination, uri);
                    if (imageItemHandle.hasDecodingNeeded()) {
                        ImageItem.WithDecodingNeeded asWithDecodingNeeded = imageItemHandle.getAsWithDecodingNeeded();
                        try {
                            MediaDecoder mediaDecoder = (MediaDecoder) AsyncDrawableLoaderImpl.this.mediaDecoders.get(asWithDecodingNeeded.contentType());
                            if (mediaDecoder == null) {
                                mediaDecoder = AsyncDrawableLoaderImpl.this.defaultMediaDecoder;
                            }
                            if (mediaDecoder == null) {
                                throw new IllegalStateException("No media-decoder is found: " + destination);
                            }
                            drawableHandleError = mediaDecoder.decode(asWithDecodingNeeded.contentType(), asWithDecodingNeeded.inputStream());
                            try {
                                asWithDecodingNeeded.inputStream().close();
                            } catch (IOException e) {
                                Log.e("MARKWON-IMAGE", "Error closing inputStream", e);
                            }
                        } catch (Throwable th) {
                            try {
                                asWithDecodingNeeded.inputStream().close();
                            } catch (IOException e2) {
                                Log.e("MARKWON-IMAGE", "Error closing inputStream", e2);
                            }
                            throw th;
                        }
                    } else {
                        drawableHandleError = imageItemHandle.getAsWithResult().result();
                    }
                } catch (Throwable th2) {
                    if (AsyncDrawableLoaderImpl.this.errorHandler != null) {
                        drawableHandleError = AsyncDrawableLoaderImpl.this.errorHandler.handleError(destination, th2);
                    } else {
                        Log.e("MARKWON-IMAGE", "Error loading image: " + destination, th2);
                    }
                }
                if (drawableHandleError != null && ((bounds = drawableHandleError.getBounds()) == null || bounds.isEmpty())) {
                    DrawableUtils.applyIntrinsicBounds(drawableHandleError);
                }
                AsyncDrawableLoaderImpl.this.handler.postAtTime(new Runnable() { // from class: io.noties.markwon.image.AsyncDrawableLoaderImpl.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (((Future) AsyncDrawableLoaderImpl.this.requests.remove(asyncDrawable)) == null || drawableHandleError == null || !asyncDrawable.isAttached()) {
                            return;
                        }
                        asyncDrawable.setResult(drawableHandleError);
                    }
                }, asyncDrawable, SystemClock.uptimeMillis());
            }
        });
    }
}
