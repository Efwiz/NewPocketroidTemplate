package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.Gdx;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.ui.ProjectUploadActivityKt;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/GltfToGlbConverter.class
  classes14.dex
 */
/* JADX INFO: compiled from: Export3dObjectToGlbAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0002J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007H\u0002J\u001e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\fJ\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0002¨\u0006\u0012"}, d2 = {"Lorg/catrobat/catroid/content/actions/GltfToGlbConverter;", "", "()V", "cleanJsonArray", "Lorg/json/JSONArray;", "array", "cleanJsonObject", "Lorg/json/JSONObject;", "obj", "convert", "", "gltfFile", "Ljava/io/File;", "glbFile", ProjectUploadActivityKt.PROJECT_DIR, "padLength", "", "len", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class GltfToGlbConverter {
    public static final GltfToGlbConverter INSTANCE = new GltfToGlbConverter();

    private GltfToGlbConverter() {
    }

    /* JADX WARN: Code duplicated, block: B:13:0x007e  */
    public final boolean convert(File gltfFile, File glbFile, File projectDir) {
        int iPadLength;
        JSONArray jSONArray;
        JSONObject jSONObject;
        JSONArray jSONArray2;
        JSONArray jSONArray3;
        File file;
        File projectDir2 = projectDir;
        Intrinsics.checkNotNullParameter(gltfFile, "gltfFile");
        Intrinsics.checkNotNullParameter(glbFile, "glbFile");
        Intrinsics.checkNotNullParameter(projectDir2, "projectDir");
        try {
            File parentFile = gltfFile.getParentFile();
            JSONObject jSONObject2 = new JSONObject(FilesKt.readText(gltfFile, Charsets.UTF_8));
            ArrayList arrayList = new ArrayList();
            JSONArray jSONArrayOptJSONArray = jSONObject2.optJSONArray("buffers");
            if (jSONArrayOptJSONArray == null || jSONArrayOptJSONArray.length() <= 0) {
                iPadLength = 0;
            } else {
                JSONObject jSONObject3 = jSONArrayOptJSONArray.getJSONObject(0);
                String strOptString = jSONObject3.optString("uri");
                Intrinsics.checkNotNull(strOptString);
                if (strOptString.length() > 0) {
                    File file2 = new File(parentFile, strOptString);
                    if (file2.exists()) {
                        byte[] bytes = FilesKt.readBytes(file2);
                        iPadLength = padLength(bytes.length);
                        byte[] bArrCopyOf = Arrays.copyOf(bytes, iPadLength);
                        Intrinsics.checkNotNullExpressionValue(bArrCopyOf, "copyOf(...)");
                        arrayList.add(bArrCopyOf);
                        jSONObject3.remove("uri");
                        jSONObject3.put("byteLength", iPadLength);
                    } else {
                        iPadLength = 0;
                    }
                } else {
                    iPadLength = 0;
                }
            }
            JSONArray jSONArrayOptJSONArray2 = jSONObject2.optJSONArray(Constants.IMAGE_DIRECTORY_NAME);
            if (jSONArrayOptJSONArray2 != null) {
                JSONArray jSONArrayOptJSONArray3 = jSONObject2.optJSONArray("bufferViews");
                if (jSONArrayOptJSONArray3 == null) {
                    jSONArray = new JSONArray();
                    jSONObject2.put("bufferViews", jSONArray);
                } else {
                    jSONArray = jSONArrayOptJSONArray3;
                }
                int length = jSONArrayOptJSONArray2.length();
                int i = 0;
                while (i < length) {
                    JSONObject jSONObject4 = jSONArrayOptJSONArray2.getJSONObject(i);
                    int i2 = length;
                    String strOptString2 = jSONObject4.optString("uri");
                    Intrinsics.checkNotNull(strOptString2);
                    if (strOptString2.length() > 0) {
                        jSONArray3 = jSONArrayOptJSONArray2;
                        jSONObject = jSONObject2;
                        jSONArray2 = jSONArrayOptJSONArray;
                        if (!StringsKt.startsWith$default(strOptString2, "data:", false, 2, (Object) null)) {
                            if (StringsKt.startsWith$default(strOptString2, "/", false, 2, (Object) null)) {
                                file = new File(strOptString2);
                            } else {
                                file = new File(parentFile, strOptString2);
                                if (!file.exists()) {
                                    file = new File(projectDir2, strOptString2);
                                }
                            }
                            if (file.exists()) {
                                byte[] bytes2 = FilesKt.readBytes(file);
                                int iPadLength2 = padLength(bytes2.length);
                                byte[] bArrCopyOf2 = Arrays.copyOf(bytes2, iPadLength2);
                                Intrinsics.checkNotNullExpressionValue(bArrCopyOf2, "copyOf(...)");
                                arrayList.add(bArrCopyOf2);
                                int length2 = jSONArray.length();
                                JSONObject jSONObject5 = new JSONObject();
                                jSONObject5.put("buffer", 0);
                                jSONObject5.put("byteOffset", iPadLength);
                                jSONObject5.put("byteLength", bytes2.length);
                                jSONArray.put(jSONObject5);
                                jSONObject4.remove("uri");
                                jSONObject4.put("bufferView", length2);
                                jSONObject4.put("mimeType", StringsKt.endsWith(strOptString2, Constants.DEFAULT_IMAGE_EXTENSION, true) ? "image/png" : "image/jpeg");
                                iPadLength += iPadLength2;
                            } else {
                                Gdx.f82app.error("GLB_Export", "Texture file NOT found during packaging: " + file.getAbsolutePath());
                            }
                        }
                    } else {
                        jSONObject = jSONObject2;
                        jSONArray2 = jSONArrayOptJSONArray;
                        jSONArray3 = jSONArrayOptJSONArray2;
                    }
                    i++;
                    projectDir2 = projectDir;
                    length = i2;
                    jSONArrayOptJSONArray2 = jSONArray3;
                    jSONObject2 = jSONObject;
                    jSONArrayOptJSONArray = jSONArray2;
                    parentFile = parentFile;
                }
            }
            JSONObject jSONObject6 = jSONObject2;
            JSONArray jSONArray4 = jSONArrayOptJSONArray;
            if (jSONArray4 != null && jSONArray4.length() > 0) {
                jSONArray4.getJSONObject(0).put("byteLength", iPadLength);
            }
            String string = cleanJsonObject(jSONObject6).toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            byte[] bytes3 = string.getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes3, "getBytes(...)");
            int iPadLength3 = padLength(bytes3.length);
            byte[] bArr = new byte[iPadLength3];
            for (int i3 = 0; i3 < iPadLength3; i3++) {
                bArr[i3] = 32;
            }
            System.arraycopy(bytes3, 0, bArr, 0, bytes3.length);
            FileOutputStream fileOutputStream = new FileOutputStream(glbFile);
            try {
                FileOutputStream fileOutputStream2 = fileOutputStream;
                ByteBuffer byteBufferOrder = ByteBuffer.allocate(12).order(ByteOrder.LITTLE_ENDIAN);
                byteBufferOrder.putInt(1179937895);
                byteBufferOrder.putInt(2);
                byteBufferOrder.putInt(iPadLength3 + 20 + (iPadLength > 0 ? iPadLength + 8 : 0));
                fileOutputStream2.write(byteBufferOrder.array());
                ByteBuffer byteBufferOrder2 = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
                byteBufferOrder2.putInt(iPadLength3);
                byteBufferOrder2.putInt(1313821514);
                fileOutputStream2.write(byteBufferOrder2.array());
                fileOutputStream2.write(bArr);
                if (iPadLength > 0) {
                    ByteBuffer byteBufferOrder3 = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);
                    byteBufferOrder3.putInt(iPadLength);
                    byteBufferOrder3.putInt(5130562);
                    fileOutputStream2.write(byteBufferOrder3.array());
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        fileOutputStream2.write((byte[]) it.next());
                    }
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(fileOutputStream, null);
                return true;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(fileOutputStream, th);
                    throw th2;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private final int padLength(int len) {
        int i = len % 4;
        return i == 0 ? len : len + (4 - i);
    }

    private final JSONObject cleanJsonObject(JSONObject obj) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        Iterator<String> itKeys = obj.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            Intrinsics.checkNotNull(next);
            if (!StringsKt.startsWith$default(next, PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT, false, 2, (Object) null)) {
                Object obj2 = obj.get(next);
                if (obj2 instanceof JSONObject) {
                    jSONObject.put(next, cleanJsonObject((JSONObject) obj2));
                } else if (obj2 instanceof JSONArray) {
                    jSONObject.put(next, cleanJsonArray((JSONArray) obj2));
                } else {
                    jSONObject.put(next, obj2);
                }
            }
        }
        return jSONObject;
    }

    private final JSONArray cleanJsonArray(JSONArray array) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        int length = array.length();
        for (int i = 0; i < length; i++) {
            Object obj = array.get(i);
            if (obj instanceof JSONObject) {
                jSONArray.put(cleanJsonObject((JSONObject) obj));
            } else if (obj instanceof JSONArray) {
                jSONArray.put(cleanJsonArray((JSONArray) obj));
            } else {
                jSONArray.put(obj);
            }
        }
        return jSONArray;
    }
}
