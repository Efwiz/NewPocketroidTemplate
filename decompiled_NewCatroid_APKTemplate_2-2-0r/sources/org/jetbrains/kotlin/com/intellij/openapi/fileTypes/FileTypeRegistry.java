package org.jetbrains.kotlin.com.intellij.openapi.fileTypes;

import java.util.Objects;
import java.util.function.Supplier;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.components.ComponentManagerEx;
import org.jetbrains.kotlin.com.intellij.openapi.util.Getter;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;

/* JADX INFO: loaded from: classes3.dex */
public abstract class FileTypeRegistry {

    @Deprecated
    public static Getter<FileTypeRegistry> ourInstanceGetter = new Getter() { // from class: org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileTypeRegistry$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
            return FileTypeRegistry.lambda$static$0();
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 5 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 5 || i == 7) ? 2 : 3];
        switch (i) {
            case 1:
            case 4:
                objArr[0] = "file";
                break;
            case 2:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 3:
                objArr[0] = "language";
                break;
            case 5:
            case 7:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/fileTypes/FileTypeRegistry";
                break;
            case 6:
                objArr[0] = "fileNameSeq";
                break;
            default:
                objArr[0] = "supplier";
                break;
        }
        if (i == 5) {
            objArr[1] = "getFileTypeByFile";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/fileTypes/FileTypeRegistry";
        } else {
            objArr[1] = "getFileTypeByFileName";
        }
        switch (i) {
            case 1:
            case 2:
                objArr[2] = "isFileOfType";
                break;
            case 3:
                objArr[2] = "findFileTypeByLanguage";
                break;
            case 4:
                objArr[2] = "getFileTypeByFile";
                break;
            case 5:
            case 7:
                break;
            case 6:
                objArr[2] = "getFileTypeByFileName";
                break;
            default:
                objArr[2] = "setInstanceSupplier";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 5 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public abstract FileType findFileTypeByName(String str);

    public abstract FileType getFileTypeByFile(VirtualFile virtualFile);

    public abstract FileType getFileTypeByFileName(String str);

    public abstract FileType[] getRegisteredFileTypes();

    public abstract boolean isFileIgnored(VirtualFile virtualFile);

    static /* synthetic */ FileTypeRegistry lambda$static$0() {
        return (FileTypeRegistry) ((ComponentManagerEx) ApplicationManager.getApplication()).getServiceByClassName("org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileTypeManager");
    }

    public static void setInstanceSupplier(final Supplier<? extends FileTypeRegistry> supplier) {
        if (supplier == null) {
            $$$reportNull$$$0(0);
        }
        Objects.requireNonNull(supplier);
        ourInstanceGetter = new Getter() { // from class: org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileTypeRegistry$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return (FileTypeRegistry) supplier.get();
            }
        };
    }

    public boolean isFileOfType(VirtualFile virtualFile, FileType fileType) {
        if (virtualFile == null) {
            $$$reportNull$$$0(1);
        }
        if (fileType == null) {
            $$$reportNull$$$0(2);
        }
        return virtualFile.getFileType() == fileType;
    }

    public LanguageFileType findFileTypeByLanguage(Language language) {
        if (language == null) {
            $$$reportNull$$$0(3);
        }
        return language.findMyFileType(getRegisteredFileTypes());
    }

    public static FileTypeRegistry getInstance() {
        return ourInstanceGetter.get();
    }

    public FileType getFileTypeByFile(VirtualFile virtualFile, byte[] bArr) {
        if (virtualFile == null) {
            $$$reportNull$$$0(4);
        }
        FileType fileTypeByFile = getFileTypeByFile(virtualFile);
        if (fileTypeByFile == null) {
            $$$reportNull$$$0(5);
        }
        return fileTypeByFile;
    }

    public FileType getFileTypeByFileName(CharSequence charSequence) {
        if (charSequence == null) {
            $$$reportNull$$$0(6);
        }
        FileType fileTypeByFileName = getFileTypeByFileName(charSequence.toString());
        if (fileTypeByFileName == null) {
            $$$reportNull$$$0(7);
        }
        return fileTypeByFileName;
    }
}
