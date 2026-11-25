package androidx.core.provider;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.provider.DocumentsContract;
import androidx.annotation.DoNotInline;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.io.FileNotFoundException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/provider/DocumentsContractCompat.class
  classes.dex
 */
/* JADX INFO: loaded from: classes.dex */
public final class DocumentsContractCompat {
    private static final String PATH_TREE = "tree";

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/provider/DocumentsContractCompat$DocumentCompat.class
      classes.dex
     */
    public static final class DocumentCompat {
        public static final int FLAG_VIRTUAL_DOCUMENT = 512;

        private DocumentCompat() {
        }
    }

    public static boolean isDocumentUri(Context context, Uri uri) {
        return DocumentsContract.isDocumentUri(context, uri);
    }

    public static boolean isTreeUri(Uri uri) {
        return DocumentsContractApi24Impl.isTreeUri(uri);
    }

    public static String getDocumentId(Uri uri) {
        return DocumentsContract.getDocumentId(uri);
    }

    public static String getTreeDocumentId(Uri uri) {
        return DocumentsContractApi21Impl.getTreeDocumentId(uri);
    }

    public static Uri buildDocumentUri(String str, String str2) {
        return DocumentsContract.buildDocumentUri(str, str2);
    }

    public static Uri buildDocumentUriUsingTree(Uri uri, String str) {
        return DocumentsContractApi21Impl.buildDocumentUriUsingTree(uri, str);
    }

    public static Uri buildTreeDocumentUri(String str, String str2) {
        return DocumentsContractApi21Impl.buildTreeDocumentUri(str, str2);
    }

    public static Uri buildChildDocumentsUri(String str, String str2) {
        return DocumentsContractApi21Impl.buildChildDocumentsUri(str, str2);
    }

    public static Uri buildChildDocumentsUriUsingTree(Uri uri, String str) {
        return DocumentsContractApi21Impl.buildChildDocumentsUriUsingTree(uri, str);
    }

    public static Uri createDocument(ContentResolver contentResolver, Uri uri, String str, String str2) throws FileNotFoundException {
        return DocumentsContractApi21Impl.createDocument(contentResolver, uri, str, str2);
    }

    public static Uri renameDocument(ContentResolver contentResolver, Uri uri, String str) throws FileNotFoundException {
        return DocumentsContractApi21Impl.renameDocument(contentResolver, uri, str);
    }

    public static boolean removeDocument(ContentResolver contentResolver, Uri uri, Uri uri2) throws FileNotFoundException {
        return DocumentsContractApi24Impl.removeDocument(contentResolver, uri, uri2);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/provider/DocumentsContractCompat$DocumentsContractApi21Impl.class
      classes.dex
     */
    private static class DocumentsContractApi21Impl {
        static String getTreeDocumentId(Uri uri) {
            return DocumentsContract.getTreeDocumentId(uri);
        }

        public static Uri buildTreeDocumentUri(String str, String str2) {
            return DocumentsContract.buildTreeDocumentUri(str, str2);
        }

        static Uri buildDocumentUriUsingTree(Uri uri, String str) {
            return DocumentsContract.buildDocumentUriUsingTree(uri, str);
        }

        static Uri buildChildDocumentsUri(String str, String str2) {
            return DocumentsContract.buildChildDocumentsUri(str, str2);
        }

        static Uri buildChildDocumentsUriUsingTree(Uri uri, String str) {
            return DocumentsContract.buildChildDocumentsUriUsingTree(uri, str);
        }

        static Uri createDocument(ContentResolver contentResolver, Uri uri, String str, String str2) throws FileNotFoundException {
            return DocumentsContract.createDocument(contentResolver, uri, str, str2);
        }

        static Uri renameDocument(ContentResolver contentResolver, Uri uri, String str) throws FileNotFoundException {
            return DocumentsContract.renameDocument(contentResolver, uri, str);
        }

        private DocumentsContractApi21Impl() {
        }
    }

    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/provider/DocumentsContractCompat$DocumentsContractApi19Impl.class */
    @RequiresApi(19)
    private static class DocumentsContractApi19Impl {
        @DoNotInline
        public static Uri buildDocumentUri(String authority, String documentId) {
            return DocumentsContract.buildDocumentUri(authority, documentId);
        }

        @DoNotInline
        static boolean isDocumentUri(Context context, @Nullable Uri uri) {
            return DocumentsContract.isDocumentUri(context, uri);
        }

        @DoNotInline
        static String getDocumentId(Uri documentUri) {
            return DocumentsContract.getDocumentId(documentUri);
        }

        @DoNotInline
        static boolean deleteDocument(ContentResolver content, Uri documentUri) throws FileNotFoundException {
            return DocumentsContract.deleteDocument(content, documentUri);
        }

        private DocumentsContractApi19Impl() {
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/core-1.8.0.aar:classes.jar:androidx/core/provider/DocumentsContractCompat$DocumentsContractApi24Impl.class
      classes.dex
     */
    private static class DocumentsContractApi24Impl {
        static boolean isTreeUri(Uri uri) {
            return DocumentsContract.isTreeUri(uri);
        }

        static boolean removeDocument(ContentResolver contentResolver, Uri uri, Uri uri2) throws FileNotFoundException {
            return DocumentsContract.removeDocument(contentResolver, uri, uri2);
        }

        private DocumentsContractApi24Impl() {
        }
    }

    private DocumentsContractCompat() {
    }
}
