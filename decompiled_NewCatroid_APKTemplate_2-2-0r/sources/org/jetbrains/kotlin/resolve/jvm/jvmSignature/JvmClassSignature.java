package org.jetbrains.kotlin.resolve.jvm.jvmSignature;

import java.util.List;

/* JADX INFO: loaded from: classes6.dex */
public class JvmClassSignature {
    private final List<String> interfaces;
    private final String javaGenericSignature;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f967name;
    private final String superclassName;

    public JvmClassSignature(String str, String str2, List<String> list, String str3) {
        this.f967name = str;
        this.superclassName = str2;
        this.interfaces = list;
        this.javaGenericSignature = str3;
    }

    public String getName() {
        return this.f967name;
    }

    public String getSuperclassName() {
        return this.superclassName;
    }

    public List<String> getInterfaces() {
        return this.interfaces;
    }

    public String getJavaGenericSignature() {
        return this.javaGenericSignature;
    }
}
