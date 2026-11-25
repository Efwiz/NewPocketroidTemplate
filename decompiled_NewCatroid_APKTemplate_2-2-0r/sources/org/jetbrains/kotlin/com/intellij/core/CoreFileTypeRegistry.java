package org.jetbrains.kotlin.com.intellij.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileTypeRegistry;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.UnknownFileType;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtilRt;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFile;
import org.jetbrains.kotlin.com.intellij.util.containers.CollectionFactory;

/* JADX INFO: loaded from: classes3.dex */
public final class CoreFileTypeRegistry extends FileTypeRegistry {
    private final List<FileType> myAllFileTypes;
    private final Map<String, FileType> myExtensionsMap = CollectionFactory.createFilePathMap();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 6 || i == 8 || i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 6 || i == 8 || i == 3 || i == 4) ? 2 : 3];
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 6:
            case 8:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/core/CoreFileTypeRegistry";
                break;
            case 2:
            default:
                objArr[0] = "file";
                break;
            case 5:
                objArr[0] = PsiTreeChangeEvent.PROP_FILE_NAME;
                break;
            case 7:
            case 10:
                objArr[0] = SchemaSymbols.ATTVAL_EXTENSION;
                break;
            case 9:
                objArr[0] = "fileType";
                break;
            case 11:
                objArr[0] = "fileTypeName";
                break;
        }
        if (i == 1) {
            objArr[1] = "getRegisteredFileTypes";
        } else if (i == 6) {
            objArr[1] = "getFileTypeByFileName";
        } else if (i == 8) {
            objArr[1] = "getFileTypeByExtension";
        } else if (i == 3 || i == 4) {
            objArr[1] = "getFileTypeByFile";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/core/CoreFileTypeRegistry";
        }
        switch (i) {
            case 1:
            case 3:
            case 4:
            case 6:
            case 8:
                break;
            case 2:
                objArr[2] = "getFileTypeByFile";
                break;
            case 5:
                objArr[2] = "getFileTypeByFileName";
                break;
            case 7:
                objArr[2] = "getFileTypeByExtension";
                break;
            case 9:
            case 10:
                objArr[2] = "registerFileType";
                break;
            case 11:
                objArr[2] = "findFileTypeByName";
                break;
            default:
                objArr[2] = "isFileIgnored";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 6 && i != 8 && i != 3 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileTypeRegistry
    public boolean isFileIgnored(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(0);
        }
        return false;
    }

    public CoreFileTypeRegistry() {
        ArrayList arrayList = new ArrayList();
        this.myAllFileTypes = arrayList;
        arrayList.add(UnknownFileType.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileTypeRegistry
    public FileType[] getRegisteredFileTypes() {
        FileType[] fileTypeArr = (FileType[]) this.myAllFileTypes.toArray(FileType.EMPTY_ARRAY);
        if (fileTypeArr == null) {
            $$$reportNull$$$0(1);
        }
        return fileTypeArr;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileTypeRegistry
    public FileType getFileTypeByFile(VirtualFile virtualFile) {
        FileType assignedFileType;
        if (virtualFile == null) {
            $$$reportNull$$$0(2);
        }
        if ((virtualFile instanceof LightVirtualFile) && (assignedFileType = ((LightVirtualFile) virtualFile).getAssignedFileType()) != null) {
            if (assignedFileType == null) {
                $$$reportNull$$$0(3);
            }
            return assignedFileType;
        }
        FileType fileTypeByFileName = getFileTypeByFileName(virtualFile.getNameSequence());
        if (fileTypeByFileName == null) {
            $$$reportNull$$$0(4);
        }
        return fileTypeByFileName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileTypeRegistry
    public FileType getFileTypeByFileName(String str) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        FileType fileTypeByExtension = getFileTypeByExtension(FileUtilRt.getExtension(str));
        if (fileTypeByExtension == null) {
            $$$reportNull$$$0(6);
        }
        return fileTypeByExtension;
    }

    public FileType getFileTypeByExtension(String str) {
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        FileType fileType = this.myExtensionsMap.get(str);
        if (fileType == null) {
            fileType = UnknownFileType.INSTANCE;
        }
        if (fileType == null) {
            $$$reportNull$$$0(8);
        }
        return fileType;
    }

    public void registerFileType(FileType fileType, String str) {
        if (fileType == null) {
            $$$reportNull$$$0(9);
        }
        if (str == null) {
            $$$reportNull$$$0(10);
        }
        this.myAllFileTypes.add(fileType);
        for (String str2 : str.split(";")) {
            this.myExtensionsMap.put(str2, fileType);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileTypeRegistry
    public FileType findFileTypeByName(String str) {
        if (str == null) {
            $$$reportNull$$$0(11);
        }
        for (FileType fileType : this.myAllFileTypes) {
            if (fileType.getName().equals(str)) {
                return fileType;
            }
        }
        return null;
    }
}
