package org.jetbrains.kotlin.com.intellij.util.indexing;

import com.huawei.hms.ml.language.common.utils.Constant;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;

/* JADX INFO: loaded from: classes3.dex */
public class IndexedFileImpl extends UserDataHolderBase implements IndexedFile {
    protected final VirtualFile myFile;
    private String myFileName;
    private volatile Project myProject;
    private FileType mySubstituteFileType;
    private final FileType myType;

    /* JADX WARN: Code duplicated, block: B:21:0x002f  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 4 || i == 5) ? 2 : 3];
        if (i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/indexing/IndexedFileImpl";
        } else if (i == 3) {
            objArr[0] = "substituteFileType";
        } else if (i == 4 || i == 5) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/indexing/IndexedFileImpl";
        } else {
            objArr[0] = "file";
        }
        if (i == 2) {
            objArr[1] = "getFileType";
        } else if (i == 4) {
            objArr[1] = "getFile";
        } else if (i != 5) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/indexing/IndexedFileImpl";
        } else {
            objArr[1] = "getFileName";
        }
        if (i != 2) {
            if (i == 3) {
                objArr[2] = "setSubstituteFileType";
            } else if (i != 4 && i != 5) {
                objArr[2] = "<init>";
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 4 && i != 5) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public IndexedFileImpl(VirtualFile virtualFile, FileType fileType, Project project) {
        if (virtualFile == null) {
            $$$reportNull$$$0(1);
        }
        this.myFile = virtualFile;
        this.myProject = project;
        this.myType = fileType;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.indexing.IndexedFile
    public FileType getFileType() {
        if (this.mySubstituteFileType == null) {
            VirtualFile virtualFile = this.myFile;
            FileType fileType = this.myType;
            if (fileType == null) {
                fileType = virtualFile.getFileType();
            }
            this.mySubstituteFileType = SubstitutedFileType.substituteFileType(virtualFile, fileType, getProject());
        }
        FileType fileType2 = this.mySubstituteFileType;
        if (fileType2 == null) {
            $$$reportNull$$$0(2);
        }
        return fileType2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.indexing.IndexedFile
    public VirtualFile getFile() {
        VirtualFile virtualFile = this.myFile;
        if (virtualFile == null) {
            $$$reportNull$$$0(4);
        }
        return virtualFile;
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.indexing.IndexedFile
    public String getFileName() {
        if (this.myFileName == null) {
            this.myFileName = this.myFile.getName();
        }
        String str = this.myFileName;
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        return str;
    }

    public Project getProject() {
        return this.myProject;
    }

    public void setProject(Project project) {
        this.myProject = project;
    }

    @Override // java.util.concurrent.atomic.AtomicReference
    public String toString() {
        return "IndexedFileImpl(" + getFileName() + Constant.AFTER_QUTO;
    }
}
