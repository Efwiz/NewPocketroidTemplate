package org.catrobat.paintroid.iotasks;

import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.Regex;
import org.catrobat.catroid.common.Constants;
import org.catrobat.paintroid.FileIO;
import org.catrobat.paintroid.MainActivity;
import org.catrobat.paintroid.common.ConstantsKt;
import org.catrobat.paintroid.contract.LayerContracts;
import org.catrobat.paintroid.model.Layer;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/* JADX INFO: compiled from: OpenRasterFileFormatConversion.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lorg/catrobat/paintroid/iotasks/OpenRasterFileFormatConversion;", "", "()V", "Companion", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class OpenRasterFileFormatConversion {
    private static final int COMPRESS_QUALITY = 100;
    private static final int DATE_DIVIDER = 1000;
    private static final String ORA_VERSION = "0.0.2";
    private static final int THUMBNAIL_HEIGHT = 256;
    private static final int THUMBNAIL_WIDTH = 256;
    private static MainActivity mainActivity;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "OpenRasterFileFormatConversion";

    private OpenRasterFileFormatConversion() {
        throw new AssertionError();
    }

    /* JADX INFO: compiled from: OpenRasterFileFormatConversion.kt */
    @Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J2\u0010\u0012\u001a\u0004\u0018\u00010\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\u0006\u0010\u0017\u001a\u00020\u00072\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bJ\u0018\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u0002J\u0018\u0010\u001f\u001a\u00020 2\u0006\u0010\u001a\u001a\u00020\u001b2\b\u0010!\u001a\u0004\u0018\u00010\u0013J:\u0010\"\u001a\u0004\u0018\u00010\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\u0006\u0010!\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u00072\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\n \t*\u0004\u0018\u00010\u00070\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006#"}, d2 = {"Lorg/catrobat/paintroid/iotasks/OpenRasterFileFormatConversion$Companion;", "", "()V", "COMPRESS_QUALITY", "", "DATE_DIVIDER", "ORA_VERSION", "", "TAG", JvmProtoBufUtil.PLATFORM_TYPE_ID, "THUMBNAIL_HEIGHT", "THUMBNAIL_WIDTH", "mainActivity", "Lorg/catrobat/paintroid/MainActivity;", "getMainActivity", "()Lorg/catrobat/paintroid/MainActivity;", "setMainActivity", "(Lorg/catrobat/paintroid/MainActivity;)V", "exportToOraFile", "Landroid/net/Uri;", "layers", "", "Lorg/catrobat/paintroid/contract/LayerContracts$Layer;", PsiTreeChangeEvent.PROP_FILE_NAME, "bitmapAllLayers", "Landroid/graphics/Bitmap;", "resolver", "Landroid/content/ContentResolver;", "getXmlStack", "", "bitmapList", "importOraFile", "Lorg/catrobat/paintroid/iotasks/BitmapReturnValue;", "uri", "saveOraFileToUri", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final MainActivity getMainActivity() {
            return OpenRasterFileFormatConversion.mainActivity;
        }

        public final void setMainActivity(MainActivity mainActivity) {
            OpenRasterFileFormatConversion.mainActivity = mainActivity;
        }

        /* JADX WARN: Code duplicated, block: B:39:0x0143  */
        public final Uri exportToOraFile(List<? extends LayerContracts.Layer> layers, String fileName, Bitmap bitmapAllLayers, ContentResolver resolver) throws IOException {
            OutputStream outputStreamOpenOutputStream;
            byte[] bArr;
            Float fValueOf;
            Context baseContext;
            int i;
            SharedPreferences sharedPreferences;
            Uri uriInsert;
            SharedPreferences.Editor editorEdit;
            SharedPreferences.Editor editorPutLong;
            Intrinsics.checkNotNullParameter(layers, "layers");
            Intrinsics.checkNotNullParameter(fileName, "fileName");
            ContentValues contentValues = new ContentValues();
            Float fValueOf2 = Float.valueOf(0.0f);
            byte[] bytes = "image/openraster".getBytes(Charsets.UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "(this as java.lang.String).getBytes(charset)");
            byte[] xmlStack = getXmlStack(layers);
            Iterator<? extends LayerContracts.Layer> it = layers.iterator();
            while (true) {
                outputStreamOpenOutputStream = null;
                if (!it.hasNext()) {
                    break;
                }
                LayerContracts.Layer next = it.next();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                next.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                Float fValueOf3 = fValueOf2 == null ? null : Float.valueOf(fValueOf2.floatValue() + byteArrayOutputStream.toByteArray().length);
                fValueOf2 = fValueOf3 == null ? null : Float.valueOf(fValueOf3.floatValue() + BigInteger.valueOf(next.getOpacityPercentage()).toByteArray().length);
            }
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
            if (bitmapAllLayers != null) {
                bitmapAllLayers.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream2);
            }
            byte[] byteArray = byteArrayOutputStream2.toByteArray();
            Bitmap bitmapCreateScaledBitmap = bitmapAllLayers == null ? null : Bitmap.createScaledBitmap(bitmapAllLayers, 256, 256, false);
            ByteArrayOutputStream byteArrayOutputStream3 = new ByteArrayOutputStream();
            if (bitmapCreateScaledBitmap != null) {
                bitmapCreateScaledBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream3);
            }
            byte[] byteArray2 = byteArrayOutputStream3.toByteArray();
            if (Build.VERSION.SDK_INT >= 29) {
                contentValues.put("_display_name", fileName);
                contentValues.put("relative_path", Environment.DIRECTORY_DOWNLOADS);
                contentValues.put("mime_type", "application/applefile");
                uriInsert = resolver == null ? null : resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues);
                i = 0;
                bArr = byteArray2;
                byteArray = byteArray;
            } else {
                File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                if (!externalStoragePublicDirectory.exists() && !externalStoragePublicDirectory.mkdirs()) {
                    externalStoragePublicDirectory.mkdirs();
                }
                Uri contentUri = MediaStore.Files.getContentUri("external");
                contentValues.put("_data", externalStoragePublicDirectory.getAbsolutePath() + '/' + fileName);
                contentValues.put("_display_name", fileName);
                contentValues.put("mime_type", "application/zip");
                contentValues.put("media_type", (Integer) 0);
                contentValues.put("date_modified", Long.valueOf(System.currentTimeMillis() / ((long) 1000)));
                if (xmlStack == null) {
                    bArr = byteArray2;
                    fValueOf = null;
                } else {
                    Float fValueOf4 = fValueOf2 == null ? null : Float.valueOf(fValueOf2.floatValue() + Float.valueOf(xmlStack.length).floatValue());
                    if (fValueOf4 == null) {
                        bArr = byteArray2;
                        fValueOf = null;
                    } else {
                        bArr = byteArray2;
                        fValueOf = Float.valueOf(Float.valueOf(fValueOf4.floatValue() + bytes.length).floatValue() + byteArray.length + bArr.length);
                    }
                }
                contentValues.put("_size", fValueOf);
                MainActivity mainActivity = getMainActivity();
                Object systemService = (mainActivity == null || (baseContext = mainActivity.getBaseContext()) == null) ? null : baseContext.getSystemService("download");
                if (systemService == null) {
                    throw new NullPointerException("null cannot be cast to non-null type android.app.DownloadManager");
                }
                Long lValueOf = fValueOf == null ? null : Long.valueOf(((DownloadManager) systemService).addCompletedDownload(fileName, fileName, true, "application/zip", externalStoragePublicDirectory.getAbsolutePath() + '/' + fileName, (long) fValueOf.floatValue(), true));
                Uri uriInsert2 = resolver == null ? null : resolver.insert(contentUri, contentValues);
                MainActivity mainActivity2 = getMainActivity();
                if (mainActivity2 == null) {
                    sharedPreferences = null;
                    i = 0;
                } else {
                    i = 0;
                    sharedPreferences = mainActivity2.getSharedPreferences(ConstantsKt.SPECIFIC_FILETYPE_SHARED_PREFERENCES_NAME, 0);
                }
                if (lValueOf != null) {
                    long jLongValue = lValueOf.longValue();
                    if (sharedPreferences != null && (editorEdit = sharedPreferences.edit()) != null && (editorPutLong = editorEdit.putLong(externalStoragePublicDirectory.getAbsolutePath() + '/' + fileName, jLongValue)) != null) {
                        editorPutLong.apply();
                    }
                }
                uriInsert = uriInsert2;
            }
            Uri uri = (Uri) Objects.requireNonNull(uriInsert);
            if (uri != null && resolver != null) {
                outputStreamOpenOutputStream = resolver.openOutputStream(uri);
            }
            OutputStream outputStream = outputStreamOpenOutputStream;
            ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
            ZipEntry zipEntry = new ZipEntry("mimetype");
            zipEntry.setMethod(8);
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(bytes, i, bytes.length);
            zipOutputStream.closeEntry();
            zipOutputStream.putNextEntry(new ZipEntry("stack.xml"));
            byte[] bArr2 = (byte[]) Objects.requireNonNull(xmlStack);
            if (bArr2 != null) {
                zipOutputStream.write(xmlStack, i, bArr2.length);
            }
            zipOutputStream.closeEntry();
            int i2 = i;
            for (LayerContracts.Layer layer : layers) {
                int i3 = i2 + 1;
                ByteArrayOutputStream byteArrayOutputStream4 = new ByteArrayOutputStream();
                layer.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream4);
                byte[] byteArray3 = byteArrayOutputStream4.toByteArray();
                zipOutputStream.putNextEntry(new ZipEntry("data/layer" + i2 + Constants.DEFAULT_IMAGE_EXTENSION));
                zipOutputStream.write(byteArray3, i, byteArray3.length);
                zipOutputStream.closeEntry();
                zipOutputStream.putNextEntry(new ZipEntry(Intrinsics.stringPlus("alpha/", Integer.valueOf(i2))));
                byte[] byteArray4 = BigInteger.valueOf(layer.getOpacityPercentage()).toByteArray();
                zipOutputStream.write(byteArray4, i, byteArray4.length);
                zipOutputStream.closeEntry();
                i2 = i3;
            }
            zipOutputStream.putNextEntry(new ZipEntry("Thumbnails/thumbnail.png"));
            byte[] bArr3 = bArr;
            zipOutputStream.write(bArr3, i, bArr3.length);
            zipOutputStream.closeEntry();
            zipOutputStream.putNextEntry(new ZipEntry("mergedimage.png"));
            zipOutputStream.write(byteArray, i, byteArray.length);
            zipOutputStream.closeEntry();
            zipOutputStream.close();
            if (outputStream != null) {
                outputStream.close();
            }
            return uriInsert;
        }

        private final byte[] getXmlStack(List<? extends LayerContracts.Layer> bitmapList) {
            try {
                Document documentNewDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
                Element elementCreateElement = documentNewDocument.createElement(Constants.TMP_IMAGE_FILE_NAME);
                Attr attrCreateAttribute = documentNewDocument.createAttribute("version");
                Attr attrCreateAttribute2 = documentNewDocument.createAttribute("w");
                Attr attrCreateAttribute3 = documentNewDocument.createAttribute("h");
                attrCreateAttribute.setValue(OpenRasterFileFormatConversion.ORA_VERSION);
                attrCreateAttribute2.setValue(String.valueOf(bitmapList.get(0).getBitmap().getWidth()));
                attrCreateAttribute3.setValue(String.valueOf(bitmapList.get(0).getBitmap().getHeight()));
                elementCreateElement.setAttributeNode(attrCreateAttribute);
                elementCreateElement.setAttributeNode(attrCreateAttribute2);
                elementCreateElement.setAttributeNode(attrCreateAttribute3);
                documentNewDocument.appendChild(elementCreateElement);
                Element elementCreateElement2 = documentNewDocument.createElement("stack");
                elementCreateElement.appendChild(elementCreateElement2);
                int size = bitmapList.size() - 1;
                if (size >= 0) {
                    while (true) {
                        int i = size - 1;
                        Element elementCreateElement3 = documentNewDocument.createElement("layer");
                        elementCreateElement3.setAttribute("name", Intrinsics.stringPlus("layer", Integer.valueOf(size)));
                        elementCreateElement3.setAttribute("src", "data/layer" + size + Constants.DEFAULT_IMAGE_EXTENSION);
                        elementCreateElement2.appendChild(elementCreateElement3);
                        if (i < 0) {
                            break;
                        }
                        size = i;
                    }
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                TransformerFactory.newInstance().newTransformer().transform(new DOMSource(documentNewDocument), new StreamResult(byteArrayOutputStream));
                return byteArrayOutputStream.toByteArray();
            } catch (ParserConfigurationException unused) {
                Log.e(OpenRasterFileFormatConversion.TAG, "Could not create document.");
                return null;
            } catch (TransformerException unused2) {
                Log.e(OpenRasterFileFormatConversion.TAG, "Could not transform Xml file.");
                return null;
            }
        }

        public final Uri saveOraFileToUri(List<? extends LayerContracts.Layer> layers, Uri uri, String fileName, Bitmap bitmapAllLayers, ContentResolver resolver) throws IOException {
            Context baseContext;
            Intrinsics.checkNotNullParameter(layers, "layers");
            Intrinsics.checkNotNullParameter(uri, "uri");
            Intrinsics.checkNotNullParameter(fileName, "fileName");
            Object systemService = null;
            if (Build.VERSION.SDK_INT >= 29) {
                Cursor cursorQuery = resolver == null ? null : resolver.query(uri, new String[]{"_id"}, null, null, null);
                if (cursorQuery != null && cursorQuery.moveToFirst()) {
                    Uri uriWithAppendedId = ContentUris.withAppendedId(MediaStore.Downloads.EXTERNAL_CONTENT_URI, cursorQuery.getLong(cursorQuery.getColumnIndexOrThrow("_id")));
                    Intrinsics.checkNotNullExpressionValue(uriWithAppendedId, "withAppendedId(MediaStor…EXTERNAL_CONTENT_URI, id)");
                    resolver.delete(uriWithAppendedId, null, null);
                    cursorQuery.close();
                } else {
                    throw new AssertionError("No file to delete was found!");
                }
            } else {
                boolean zDelete = new File(String.valueOf(uri.getPath())).delete();
                MainActivity mainActivity = getMainActivity();
                SharedPreferences sharedPreferences = mainActivity == null ? null : mainActivity.getSharedPreferences(ConstantsKt.SPECIFIC_FILETYPE_SHARED_PREFERENCES_NAME, 0);
                Long lValueOf = sharedPreferences == null ? null : Long.valueOf(sharedPreferences.getLong(uri.getPath(), -1L));
                if (lValueOf != null && lValueOf.longValue() > -1) {
                    MainActivity mainActivity2 = getMainActivity();
                    if (mainActivity2 != null && (baseContext = mainActivity2.getBaseContext()) != null) {
                        systemService = baseContext.getSystemService("download");
                    }
                    if (systemService == null) {
                        throw new NullPointerException("null cannot be cast to non-null type android.app.DownloadManager");
                    }
                    ((DownloadManager) systemService).remove(lValueOf.longValue());
                }
                if (!zDelete) {
                    throw new AssertionError("No file to delete was found!");
                }
            }
            return exportToOraFile(layers, fileName, bitmapAllLayers, resolver);
        }

        public final BitmapReturnValue importOraFile(ContentResolver resolver, Uri uri) throws IOException {
            Intrinsics.checkNotNullParameter(resolver, "resolver");
            BitmapFactory.Options options = new BitmapFactory.Options();
            ZipInputStream zipInputStream = new ZipInputStream(uri == null ? null : resolver.openInputStream(uri));
            ArrayList arrayList = new ArrayList();
            ZipEntry nextEntry = zipInputStream.getNextEntry();
            while (nextEntry != null) {
                String name2 = nextEntry.getName();
                Intrinsics.checkNotNullExpressionValue(name2, "current.name");
                if (new Regex("data/(.*).png").matches(name2)) {
                    Bitmap bitmapEnableAlpha = FileIO.enableAlpha(BitmapFactory.decodeStream(zipInputStream, null, options));
                    if (bitmapEnableAlpha == null) {
                        throw new IOException("Cannot decode stream to bitmap!");
                    }
                    Layer layer = new Layer(bitmapEnableAlpha);
                    nextEntry = zipInputStream.getNextEntry();
                    if (nextEntry != null) {
                        String name3 = nextEntry.getName();
                        Intrinsics.checkNotNullExpressionValue(name3, "current.name");
                        if (new Regex("alpha/(.*)").matches(name3)) {
                            layer.setOpacityPercentage(zipInputStream.read());
                            nextEntry = zipInputStream.getNextEntry();
                        }
                    }
                    arrayList.add(layer);
                } else {
                    nextEntry = zipInputStream.getNextEntry();
                }
            }
            if (arrayList.isEmpty() || arrayList.size() > 100) {
                throw new IOException("Bitmap list is wrong!");
            }
            return new BitmapReturnValue(arrayList, null, false);
        }
    }
}
