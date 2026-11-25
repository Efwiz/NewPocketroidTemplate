package org.eclipse.jdt.core;

import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.eclipse.jdt.core.util.IClassFileReader;
import org.eclipse.jdt.core.util.ICodeAttribute;
import org.eclipse.jdt.core.util.IMethodInfo;
import org.eclipse.jdt.internal.antadapter.AntAdapterMessages;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public final class CheckDebugAttributes extends Task {
    private String file;
    private String property;

    /* JADX INFO: Thrown type has an unknown type hierarchy: org.apache.tools.ant.BuildException */
    public void execute() throws BuildException {
        boolean zCheckClassFile;
        String str = this.file;
        if (str == null) {
            throw new BuildException(AntAdapterMessages.getString("checkDebugAttributes.file.argument.cannot.be.null"));
        }
        if (this.property == null) {
            throw new BuildException(AntAdapterMessages.getString("checkDebugAttributes.property.argument.cannot.be.null"));
        }
        try {
            if (Util.isClassFileName(str)) {
                zCheckClassFile = checkClassFile(ToolFactory.createDefaultClassFileReader(this.file, 65535));
            } else {
                try {
                    ZipFile zipFile = new ZipFile(this.file);
                    zipFile.close();
                    Enumeration<? extends ZipEntry> enumerationEntries = zipFile.entries();
                    boolean zCheckClassFile2 = false;
                    while (!zCheckClassFile2 && enumerationEntries.hasMoreElements()) {
                        ZipEntry zipEntryNextElement = enumerationEntries.nextElement();
                        if (Util.isClassFileName(zipEntryNextElement.getName())) {
                            zCheckClassFile2 = checkClassFile(ToolFactory.createDefaultClassFileReader(this.file, zipEntryNextElement.getName(), 65535));
                        }
                    }
                    zCheckClassFile = zCheckClassFile2;
                } catch (ZipException unused) {
                    throw new BuildException(AntAdapterMessages.getString("checkDebugAttributes.file.argument.must.be.a.classfile.or.a.jarfile"));
                }
            }
            if (zCheckClassFile) {
                getProject().setUserProperty(this.property, "has debug");
            }
        } catch (IOException unused2) {
            throw new BuildException(String.valueOf(AntAdapterMessages.getString("checkDebugAttributes.ioexception.occured")) + this.file);
        }
    }

    private boolean checkClassFile(IClassFileReader iClassFileReader) {
        for (IMethodInfo iMethodInfo : iClassFileReader.getMethodInfos()) {
            ICodeAttribute codeAttribute = iMethodInfo.getCodeAttribute();
            if (codeAttribute != null && codeAttribute.getLineNumberAttribute() != null) {
                return true;
            }
        }
        return false;
    }

    public void setFile(String str) {
        this.file = str;
    }

    public void setProperty(String str) {
        this.property = str;
    }
}
