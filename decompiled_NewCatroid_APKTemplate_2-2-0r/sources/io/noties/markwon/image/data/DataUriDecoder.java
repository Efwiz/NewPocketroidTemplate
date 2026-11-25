package io.noties.markwon.image.data;

import android.text.TextUtils;
import android.util.Base64;

/* JADX INFO: loaded from: classes11.dex */
public abstract class DataUriDecoder {
    public abstract byte[] decode(DataUri dataUri) throws Throwable;

    public static DataUriDecoder create() {
        return new Impl();
    }

    static class Impl extends DataUriDecoder {
        private static final String CHARSET = "UTF-8";

        Impl() {
        }

        @Override // io.noties.markwon.image.data.DataUriDecoder
        public byte[] decode(DataUri dataUri) throws Throwable {
            String strData = dataUri.data();
            if (TextUtils.isEmpty(strData)) {
                return null;
            }
            if (dataUri.base64()) {
                return Base64.decode(strData.getBytes("UTF-8"), 0);
            }
            return strData.getBytes("UTF-8");
        }
    }
}
