package com.koushikdutta.async.http.body;

import com.koushikdutta.async.http.BasicNameValuePair;
import com.koushikdutta.async.http.NameValuePair;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes10.dex */
public class FilePart extends StreamPart {
    File file;

    public FilePart(String str, File file) {
        super(str, (int) file.length(), new ArrayList<NameValuePair>(file) { // from class: com.koushikdutta.async.http.body.FilePart.1
            final /* synthetic */ File val$file;

            {
                this.val$file = file;
                add(new BasicNameValuePair("filename", file.getName()));
            }
        });
        this.file = file;
    }

    @Override // com.koushikdutta.async.http.body.StreamPart
    protected InputStream getInputStream() throws IOException {
        return new FileInputStream(this.file);
    }
}
