package com.koushikdutta.async.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/* JADX INFO: loaded from: classes10.dex */
public class FileCache {
    private static String hashAlgorithm = "MD5";
    static MessageDigest messageDigest;
    File directory;
    boolean loadAsync;
    boolean loading;
    long size;
    Random random = new Random();
    long blockSize = 4096;
    Comparator<File> dateCompare = new Comparator<File>() { // from class: com.koushikdutta.async.util.FileCache.1
        @Override // java.util.Comparator
        public int compare(File file, File file2) {
            long jLastModified = file.lastModified();
            long jLastModified2 = file2.lastModified();
            if (jLastModified < jLastModified2) {
                return -1;
            }
            return jLastModified2 > jLastModified ? 1 : 0;
        }
    };
    InternalCache cache = new InternalCache();

    class CacheEntry {
        final long size;

        public CacheEntry(File file) {
            this.size = file.length();
        }
    }

    public static class Snapshot {
        FileInputStream[] fins;
        long[] lens;

        Snapshot(FileInputStream[] fileInputStreamArr, long[] jArr) {
            this.fins = fileInputStreamArr;
            this.lens = jArr;
        }

        public long getLength(int i) {
            return this.lens[i];
        }

        public void close() {
            StreamUtility.closeQuietly(this.fins);
        }
    }

    static {
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            MessageDigest messageDigestFindAlternativeMessageDigest = findAlternativeMessageDigest();
            messageDigest = messageDigestFindAlternativeMessageDigest;
            if (messageDigestFindAlternativeMessageDigest == null) {
                throw new RuntimeException(e);
            }
        }
        try {
            messageDigest = (MessageDigest) messageDigest.clone();
        } catch (CloneNotSupportedException unused) {
        }
    }

    private static MessageDigest findAlternativeMessageDigest() {
        if (!"MD5".equals(hashAlgorithm)) {
            return null;
        }
        for (Provider provider : Security.getProviders()) {
            Iterator<Provider.Service> it = provider.getServices().iterator();
            while (it.hasNext()) {
                String algorithm = it.next().getAlgorithm();
                hashAlgorithm = algorithm;
                try {
                    MessageDigest messageDigest2 = MessageDigest.getInstance(algorithm);
                    if (messageDigest2 != null) {
                        return messageDigest2;
                    }
                } catch (NoSuchAlgorithmException unused) {
                }
            }
        }
        return null;
    }

    public static synchronized String toKeyString(Object... objArr) {
        messageDigest.reset();
        for (Object obj : objArr) {
            messageDigest.update(obj.toString().getBytes());
        }
        return new BigInteger(1, messageDigest.digest()).toString(16);
    }

    public File getTempFile() {
        File file;
        do {
            file = new File(this.directory, new BigInteger(128, this.random).toString(16));
        } while (file.exists());
        return file;
    }

    public File[] getTempFiles(int i) {
        File[] fileArr = new File[i];
        for (int i2 = 0; i2 < i; i2++) {
            fileArr[i2] = getTempFile();
        }
        return fileArr;
    }

    public static void removeFiles(File... fileArr) {
        if (fileArr == null) {
            return;
        }
        for (File file : fileArr) {
            file.delete();
        }
    }

    public void remove(String str) {
        for (int i = 0; this.cache.remove(getPartName(str, i)) != null; i++) {
        }
        removePartFiles(str);
    }

    public boolean exists(String str, int i) {
        return getPartFile(str, i).exists();
    }

    public boolean exists(String str) {
        return getPartFile(str, 0).exists();
    }

    public File touch(File file) {
        this.cache.get(file.getName());
        file.setLastModified(System.currentTimeMillis());
        return file;
    }

    public FileInputStream get(String str) throws IOException {
        return new FileInputStream(touch(getPartFile(str, 0)));
    }

    public File getFile(String str) {
        return touch(getPartFile(str, 0));
    }

    public FileInputStream[] get(String str, int i) throws IOException {
        FileInputStream[] fileInputStreamArr = new FileInputStream[i];
        for (int i2 = 0; i2 < i; i2++) {
            try {
                fileInputStreamArr[i2] = new FileInputStream(touch(getPartFile(str, i2)));
            } catch (IOException e) {
                for (int i3 = 0; i3 < i; i3++) {
                    StreamUtility.closeQuietly(fileInputStreamArr[i3]);
                }
                remove(str);
                throw e;
            }
        }
        return fileInputStreamArr;
    }

    String getPartName(String str, int i) {
        return str + "." + i;
    }

    public void commitTempFiles(String str, File... fileArr) {
        removePartFiles(str);
        for (int i = 0; i < fileArr.length; i++) {
            File file = fileArr[i];
            File partFile = getPartFile(str, i);
            if (!file.renameTo(partFile)) {
                removeFiles(fileArr);
                remove(str);
                return;
            } else {
                remove(file.getName());
                this.cache.put(getPartName(str, i), new CacheEntry(partFile));
            }
        }
    }

    void removePartFiles(String str) {
        int i = 0;
        while (true) {
            File partFile = getPartFile(str, i);
            if (!partFile.exists()) {
                return;
            }
            partFile.delete();
            i++;
        }
    }

    File getPartFile(String str, int i) {
        return new File(this.directory, getPartName(str, i));
    }

    public void setBlockSize(long j) {
        this.blockSize = j;
    }

    class InternalCache extends LruCache<String, CacheEntry> {
        public InternalCache() {
            super(FileCache.this.size);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.koushikdutta.async.util.LruCache
        public long sizeOf(String str, CacheEntry cacheEntry) {
            return Math.max(FileCache.this.blockSize, cacheEntry.size);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.koushikdutta.async.util.LruCache
        public void entryRemoved(boolean z, String str, CacheEntry cacheEntry, CacheEntry cacheEntry2) {
            super.entryRemoved(z, str, cacheEntry, cacheEntry2);
            if (cacheEntry2 == null && !FileCache.this.loading) {
                new File(FileCache.this.directory, str).delete();
            }
        }
    }

    void load() {
        this.loading = true;
        try {
            File[] fileArrListFiles = this.directory.listFiles();
            if (fileArrListFiles != null) {
                ArrayList<File> arrayList = new ArrayList();
                Collections.addAll(arrayList, fileArrListFiles);
                Collections.sort(arrayList, this.dateCompare);
                for (File file : arrayList) {
                    String name2 = file.getName();
                    this.cache.put(name2, new CacheEntry(file));
                    this.cache.get(name2);
                }
                this.loading = false;
                return;
            }
            this.loading = false;
        } catch (Throwable th) {
            this.loading = false;
            throw th;
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.koushikdutta.async.util.FileCache$2] */
    private void doLoad() {
        if (this.loadAsync) {
            new Thread() { // from class: com.koushikdutta.async.util.FileCache.2
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    FileCache.this.load();
                }
            }.start();
        } else {
            load();
        }
    }

    public FileCache(File file, long j, boolean z) {
        this.directory = file;
        this.size = j;
        this.loadAsync = z;
        file.mkdirs();
        doLoad();
    }

    public long size() {
        return this.cache.size();
    }

    public void clear() {
        removeFiles(this.directory.listFiles());
        this.cache.evictAll();
    }

    public Set<String> keySet() {
        HashSet hashSet = new HashSet();
        File[] fileArrListFiles = this.directory.listFiles();
        if (fileArrListFiles == null) {
            return hashSet;
        }
        for (File file : fileArrListFiles) {
            String name2 = file.getName();
            int iLastIndexOf = name2.lastIndexOf(46);
            if (iLastIndexOf != -1) {
                hashSet.add(name2.substring(0, iLastIndexOf));
            }
        }
        return hashSet;
    }

    public void setMaxSize(long j) {
        this.cache.setMaxSize(j);
        doLoad();
    }
}
