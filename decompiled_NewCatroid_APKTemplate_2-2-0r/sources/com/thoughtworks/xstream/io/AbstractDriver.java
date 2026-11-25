package com.thoughtworks.xstream.io;

import com.thoughtworks.xstream.io.naming.NameCoder;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/xstream.jar:com/thoughtworks/xstream/io/AbstractDriver.class
  classes11.dex
 */
/* JADX INFO: loaded from: classes11.dex */
public abstract class AbstractDriver implements HierarchicalStreamDriver {
    private NameCoder replacer;

    public AbstractDriver() {
        this(new NoNameCoder());
    }

    public AbstractDriver(NameCoder nameCoder) {
        this.replacer = nameCoder;
    }

    protected NameCoder getNameCoder() {
        return this.replacer;
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(URL url) {
        try {
            return createReader(url.openStream());
        } catch (IOException e) {
            throw new StreamException(e);
        }
    }

    @Override // com.thoughtworks.xstream.io.HierarchicalStreamDriver
    public HierarchicalStreamReader createReader(File file) {
        try {
            return createReader(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new StreamException(e);
        }
    }
}
