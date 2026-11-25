package com.topjohnwu.utils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

/* JADX INFO: loaded from: classes11.dex */
public class JarMap implements Closeable, AutoCloseable {
    private LinkedHashMap<String, JarEntry> bufMap;
    private JarFile jarFile;
    private JarInputStream jis;
    private Manifest manifest;

    public JarMap(File file) throws IOException {
        this(file, true);
    }

    public JarMap(File file, boolean z) throws IOException {
        this(file, z, 1);
    }

    public JarMap(File file, boolean z, int i) throws IOException {
        JarFile jarFile = new JarFile(file, z, i);
        this.jarFile = jarFile;
        this.manifest = jarFile.getManifest();
    }

    public JarMap(String str) throws IOException {
        this(new File(str));
    }

    public JarMap(String str, boolean z) throws IOException {
        this(new File(str), z);
    }

    public JarMap(InputStream inputStream) throws IOException {
        this(inputStream, true);
    }

    public JarMap(InputStream inputStream, boolean z) throws IOException {
        this.jis = new JarInputStream(inputStream, z);
        this.bufMap = new LinkedHashMap<>();
        while (true) {
            JarEntry nextJarEntry = this.jis.getNextJarEntry();
            if (nextJarEntry != null) {
                this.bufMap.put(nextJarEntry.getName(), new JarMapEntry(nextJarEntry, this.jis));
            } else {
                this.manifest = this.jis.getManifest();
                return;
            }
        }
    }

    public File getFile() {
        if (this.jarFile == null) {
            return null;
        }
        return new File(this.jarFile.getName());
    }

    public Manifest getManifest() {
        return this.manifest;
    }

    public InputStream getInputStream(ZipEntry zipEntry) throws IOException {
        JarMapEntry jarMapEntry;
        LinkedHashMap<String, JarEntry> linkedHashMap = this.bufMap;
        if (linkedHashMap != null && (jarMapEntry = (JarMapEntry) linkedHashMap.get(zipEntry.getName())) != null) {
            return jarMapEntry.data.getInputStream();
        }
        return this.jarFile.getInputStream(zipEntry);
    }

    public OutputStream getOutputStream(ZipEntry zipEntry) {
        this.manifest = null;
        if (this.bufMap == null) {
            this.bufMap = new LinkedHashMap<>();
        }
        JarMapEntry jarMapEntry = new JarMapEntry(zipEntry.getName());
        this.bufMap.put(zipEntry.getName(), jarMapEntry);
        return jarMapEntry.data;
    }

    public byte[] getRawData(ZipEntry zipEntry) throws IOException {
        JarMapEntry jarMapEntry;
        LinkedHashMap<String, JarEntry> linkedHashMap = this.bufMap;
        if (linkedHashMap != null && (jarMapEntry = (JarMapEntry) linkedHashMap.get(zipEntry.getName())) != null) {
            return jarMapEntry.data.toByteArray();
        }
        ByteArrayStream byteArrayStream = new ByteArrayStream();
        byteArrayStream.readFrom(this.jarFile.getInputStream(zipEntry));
        return byteArrayStream.toByteArray();
    }

    public Enumeration<JarEntry> entries() {
        JarFile jarFile = this.jarFile;
        return jarFile == null ? Collections.enumeration(this.bufMap.values()) : jarFile.entries();
    }

    public ZipEntry getEntry(String str) {
        return getJarEntry(str);
    }

    public JarEntry getJarEntry(String str) {
        LinkedHashMap<String, JarEntry> linkedHashMap;
        JarFile jarFile = this.jarFile;
        JarEntry jarEntry = jarFile == null ? this.bufMap.get(str) : jarFile.getJarEntry(str);
        return (jarEntry != null || (linkedHashMap = this.bufMap) == null) ? jarEntry : linkedHashMap.get(str);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        Closeable closeable = this.jarFile;
        if (closeable == null) {
            closeable = this.jis;
        }
        closeable.close();
    }

    private static class JarMapEntry extends JarEntry {
        ByteArrayStream data;

        JarMapEntry(JarEntry jarEntry, InputStream inputStream) {
            super(jarEntry);
            ByteArrayStream byteArrayStream = new ByteArrayStream();
            this.data = byteArrayStream;
            byteArrayStream.readFrom(inputStream);
        }

        JarMapEntry(String str) {
            super(str);
            this.data = new ByteArrayStream();
        }
    }
}
