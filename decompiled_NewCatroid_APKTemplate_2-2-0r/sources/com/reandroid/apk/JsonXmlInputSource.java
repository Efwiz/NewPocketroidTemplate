package com.reandroid.apk;

import com.reandroid.archive.FileInputSource;
import com.reandroid.archive.InputSource;
import com.reandroid.arsc.chunk.xml.ResXmlDocument;
import com.reandroid.json.JSONException;
import com.reandroid.json.JSONObject;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes10.dex */
public class JsonXmlInputSource extends InputSource {
    private APKLogger apkLogger;
    private final InputSource inputSource;

    public JsonXmlInputSource(InputSource inputSource) {
        super(inputSource.getAlias());
        this.inputSource = inputSource;
    }

    @Override // com.reandroid.archive.InputSource
    public long write(OutputStream outputStream) throws IOException {
        return getResXmlBlock().writeBytes(outputStream);
    }

    @Override // com.reandroid.archive.InputSource
    public InputStream openStream() throws IOException {
        return new ByteArrayInputStream(getResXmlBlock().getBytes());
    }

    @Override // com.reandroid.archive.InputSource
    public long getLength() throws IOException {
        return getResXmlBlock().countBytes();
    }

    private ResXmlDocument getResXmlBlock() throws IOException {
        logVerbose("From json: " + getAlias());
        ResXmlDocument resXmlDocumentNewInstance = newInstance();
        try {
            resXmlDocumentNewInstance.fromJson(new JSONObject(this.inputSource.openStream()));
            return resXmlDocumentNewInstance;
        } catch (JSONException e) {
            throw new IOException(this.inputSource.getAlias() + ": " + e.getMessage(), e);
        }
    }

    ResXmlDocument newInstance() {
        return new ResXmlDocument();
    }

    void setAPKLogger(APKLogger aPKLogger) {
        this.apkLogger = aPKLogger;
    }

    void logMessage(String str) {
        APKLogger aPKLogger = this.apkLogger;
        if (aPKLogger != null) {
            aPKLogger.logMessage(str);
        }
    }

    private void logError(String str, Throwable th) {
        APKLogger aPKLogger = this.apkLogger;
        if (aPKLogger != null) {
            aPKLogger.logError(str, th);
        }
    }

    private void logVerbose(String str) {
        APKLogger aPKLogger = this.apkLogger;
        if (aPKLogger != null) {
            aPKLogger.logVerbose(str);
        }
    }

    public static JsonXmlInputSource fromFile(File file, File file2) {
        return new JsonXmlInputSource(new FileInputSource(file2, ApkUtil.jsonToArchiveResourcePath(file, file2)));
    }
}
