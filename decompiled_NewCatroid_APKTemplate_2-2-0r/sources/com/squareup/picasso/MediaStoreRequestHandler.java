package com.squareup.picasso;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import com.android.apksig.internal.apk.AndroidBinXmlParser;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.arsc.value.ResConfigBase;
import java.io.IOException;
import okio.Okio;

/* JADX INFO: loaded from: classes11.dex */
class MediaStoreRequestHandler extends ContentStreamRequestHandler {
    private static final String[] CONTENT_ORIENTATION = {ResConfigBase.NAME_orientation};

    MediaStoreRequestHandler(Context context) {
        super(context);
    }

    @Override // com.squareup.picasso.ContentStreamRequestHandler, com.squareup.picasso.RequestHandler
    public boolean canHandleRequest(Request request) {
        Uri uri = request.uri;
        return FirebaseAnalytics.Param.CONTENT.equals(uri.getScheme()) && "media".equals(uri.getAuthority());
    }

    @Override // com.squareup.picasso.ContentStreamRequestHandler, com.squareup.picasso.RequestHandler
    public RequestHandler.Result load(Request request, int i) throws IOException {
        Bitmap thumbnail;
        ContentResolver contentResolver = this.context.getContentResolver();
        int exifOrientation = getExifOrientation(contentResolver, request.uri);
        String type = contentResolver.getType(request.uri);
        boolean z = type != null && type.startsWith("video/");
        if (request.hasSize()) {
            PicassoKind picassoKind = getPicassoKind(request.targetWidth, request.targetHeight);
            if (!z && picassoKind == PicassoKind.FULL) {
                return new RequestHandler.Result(null, Okio.source(getInputStream(request)), Picasso.LoadedFrom.DISK, exifOrientation);
            }
            long id2 = ContentUris.parseId(request.uri);
            BitmapFactory.Options optionsCreateBitmapOptions = createBitmapOptions(request);
            optionsCreateBitmapOptions.inJustDecodeBounds = true;
            calculateInSampleSize(request.targetWidth, request.targetHeight, picassoKind.width, picassoKind.height, optionsCreateBitmapOptions, request);
            if (z) {
                thumbnail = MediaStore.Video.Thumbnails.getThumbnail(contentResolver, id2, picassoKind == PicassoKind.FULL ? 1 : picassoKind.androidKind, optionsCreateBitmapOptions);
            } else {
                thumbnail = MediaStore.Images.Thumbnails.getThumbnail(contentResolver, id2, picassoKind.androidKind, optionsCreateBitmapOptions);
            }
            if (thumbnail != null) {
                return new RequestHandler.Result(thumbnail, null, Picasso.LoadedFrom.DISK, exifOrientation);
            }
        }
        return new RequestHandler.Result(null, Okio.source(getInputStream(request)), Picasso.LoadedFrom.DISK, exifOrientation);
    }

    static PicassoKind getPicassoKind(int i, int i2) {
        if (i <= PicassoKind.MICRO.width && i2 <= PicassoKind.MICRO.height) {
            return PicassoKind.MICRO;
        }
        if (i <= PicassoKind.MINI.width && i2 <= PicassoKind.MINI.height) {
            return PicassoKind.MINI;
        }
        return PicassoKind.FULL;
    }

    static int getExifOrientation(ContentResolver contentResolver, Uri uri) {
        Cursor cursorQuery = null;
        try {
            cursorQuery = contentResolver.query(uri, CONTENT_ORIENTATION, null, null, null);
            if (cursorQuery != null && cursorQuery.moveToFirst()) {
                return cursorQuery.getInt(0);
            }
            return 0;
        } catch (RuntimeException unused) {
            return 0;
        } finally {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
    }

    enum PicassoKind {
        MICRO(3, 96, 96),
        MINI(1, 512, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP),
        FULL(2, -1, -1);

        final int androidKind;
        final int height;
        final int width;

        PicassoKind(int i, int i2, int i3) {
            this.androidKind = i;
            this.width = i2;
            this.height = i3;
        }
    }
}
