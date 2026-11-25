package org.jf.dexlib2.extra;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public class DexMarker {
    public static final String FILE_NAME = "markers.txt";
    public static final String PREFIX_D8 = "~~D8{";
    public static final String PREFIX_R8 = "~~R8{";
    private final String marker;

    private DexMarker(String str) {
        this.marker = str;
    }

    public String getMarker() {
        return this.marker;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.marker.equals(((DexMarker) obj).marker);
    }

    public int hashCode() {
        return this.marker.hashCode();
    }

    public String toString() {
        return this.marker;
    }

    public static List<DexMarker> listMarkers(Iterator<String> it) {
        ArrayList arrayList = new ArrayList();
        while (it.hasNext()) {
            DexMarker dexMarkerOf = of(it.next());
            if (dexMarkerOf != null) {
                arrayList.add(dexMarkerOf);
            }
        }
        return arrayList;
    }

    public static void writeMarkers(List<DexMarker> list, File file) throws IOException {
        StringBuilder sb = new StringBuilder();
        boolean z = false;
        for (DexMarker dexMarker : list) {
            if (z) {
                sb.append('\n');
            }
            sb.append(dexMarker.getMarker());
            z = true;
        }
        byte[] bytes = sb.toString().getBytes(StandardCharsets.UTF_8);
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(bytes, 0, bytes.length);
        fileOutputStream.close();
    }

    public static List<DexMarker> readMarkers(File file) throws IOException {
        return readMarkers(new FileInputStream(file));
    }

    public static List<DexMarker> readMarkers(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            int i = inputStream.read(bArr, 0, 1024);
            if (i != -1) {
                byteArrayOutputStream.write(bArr, 0, i);
            } else {
                inputStream.close();
                byteArrayOutputStream.close();
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                return parseMarkers(new String(byteArray, 0, byteArray.length, StandardCharsets.UTF_8));
            }
        }
    }

    public static List<DexMarker> parseMarkers(String str) {
        ArrayList arrayList = new ArrayList();
        for (String str2 : str.split("\n")) {
            DexMarker dexMarkerOf = of(str2.trim());
            if (dexMarkerOf != null) {
                arrayList.add(dexMarkerOf);
            }
        }
        return arrayList;
    }

    public static DexMarker of(String str) {
        if (isMarker(str)) {
            return new DexMarker(str);
        }
        return null;
    }

    public static boolean isMarker(String str) {
        int length;
        if (str != null && (length = str.length() - 1) >= 5 && str.charAt(0) == '~' && str.charAt(length) == '}') {
            return str.startsWith(PREFIX_D8) || str.startsWith(PREFIX_R8);
        }
        return false;
    }
}
