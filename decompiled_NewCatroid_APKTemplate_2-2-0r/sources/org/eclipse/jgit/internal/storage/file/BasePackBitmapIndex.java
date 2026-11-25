package org.eclipse.jgit.internal.storage.file;

import com.googlecode.javaewah.EWAHCompressedBitmap;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectIdOwnerMap;

/* JADX INFO: loaded from: classes13.dex */
abstract class BasePackBitmapIndex extends PackBitmapIndex {
    private final ObjectIdOwnerMap<StoredBitmap> bitmaps;

    BasePackBitmapIndex(ObjectIdOwnerMap<StoredBitmap> objectIdOwnerMap) {
        this.bitmaps = objectIdOwnerMap;
    }

    @Override // org.eclipse.jgit.internal.storage.file.PackBitmapIndex
    public EWAHCompressedBitmap getBitmap(AnyObjectId anyObjectId) {
        StoredBitmap storedBitmap = (StoredBitmap) this.bitmaps.get(anyObjectId);
        if (storedBitmap != null) {
            return storedBitmap.getBitmap();
        }
        return null;
    }

    ObjectIdOwnerMap<StoredBitmap> getBitmaps() {
        return this.bitmaps;
    }

    static final class StoredBitmap extends ObjectIdOwnerMap.Entry {
        private volatile Object bitmapContainer;
        private final int flags;

        StoredBitmap(AnyObjectId anyObjectId, EWAHCompressedBitmap eWAHCompressedBitmap, StoredBitmap storedBitmap, int i) {
            super(anyObjectId);
            this.bitmapContainer = storedBitmap != null ? new XorCompressedBitmap(eWAHCompressedBitmap, storedBitmap) : eWAHCompressedBitmap;
            this.flags = i;
        }

        EWAHCompressedBitmap getBitmap() {
            EWAHCompressedBitmap bitmapWithoutCaching = getBitmapWithoutCaching();
            this.bitmapContainer = bitmapWithoutCaching;
            return bitmapWithoutCaching;
        }

        EWAHCompressedBitmap getBitmapWithoutCaching() {
            Object obj = this.bitmapContainer;
            if (obj instanceof EWAHCompressedBitmap) {
                return (EWAHCompressedBitmap) obj;
            }
            XorCompressedBitmap xorCompressedBitmap = (XorCompressedBitmap) obj;
            EWAHCompressedBitmap eWAHCompressedBitmapXor = xorCompressedBitmap.bitmap;
            while (true) {
                Object obj2 = xorCompressedBitmap.xorBitmap.bitmapContainer;
                if (obj2 instanceof EWAHCompressedBitmap) {
                    EWAHCompressedBitmap eWAHCompressedBitmapXor2 = eWAHCompressedBitmapXor.xor((EWAHCompressedBitmap) obj2);
                    eWAHCompressedBitmapXor2.trim();
                    return eWAHCompressedBitmapXor2;
                }
                xorCompressedBitmap = (XorCompressedBitmap) obj2;
                eWAHCompressedBitmapXor = eWAHCompressedBitmapXor.xor(xorCompressedBitmap.bitmap);
            }
        }

        int getFlags() {
            return this.flags;
        }
    }

    private static final class XorCompressedBitmap {
        final EWAHCompressedBitmap bitmap;
        final StoredBitmap xorBitmap;

        XorCompressedBitmap(EWAHCompressedBitmap eWAHCompressedBitmap, StoredBitmap storedBitmap) {
            this.bitmap = eWAHCompressedBitmap;
            this.xorBitmap = storedBitmap;
        }
    }
}
