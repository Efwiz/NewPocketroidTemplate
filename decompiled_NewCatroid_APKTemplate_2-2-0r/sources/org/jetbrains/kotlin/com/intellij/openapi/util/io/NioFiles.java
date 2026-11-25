package org.jetbrains.kotlin.com.intellij.openapi.util.io;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;
import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX INFO: loaded from: classes3.dex */
public final class NioFiles {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 5 || i == 6) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 5 || i == 6) ? 2 : 3];
        switch (i) {
            case 1:
            case 5:
            case 6:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/util/io/NioFiles";
                break;
            case 2:
            default:
                objArr[0] = "path";
                break;
            case 3:
                objArr[0] = "file";
                break;
            case 4:
                objArr[0] = "directory";
                break;
            case 7:
            case 8:
                objArr[0] = "fileOrDirectory";
                break;
            case 9:
                objArr[0] = "callback";
                break;
        }
        if (i == 1) {
            objArr[1] = "createDirectories";
        } else if (i == 5 || i == 6) {
            objArr[1] = SchemaSymbols.ATTVAL_LIST;
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/util/io/NioFiles";
        }
        switch (i) {
            case 1:
            case 5:
            case 6:
                break;
            case 2:
                objArr[2] = "setReadOnly";
                break;
            case 3:
                objArr[2] = "setExecutable";
                break;
            case 4:
                objArr[2] = SchemaSymbols.ATTVAL_LIST;
                break;
            case 7:
            case 8:
            case 9:
                objArr[2] = "deleteRecursively";
                break;
            default:
                objArr[2] = "createDirectories";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 5 && i != 6) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public static Path createDirectories(Path path) throws IOException {
        if (path == null) {
            $$$reportNull$$$0(0);
        }
        try {
            tryCreateDirectory(path);
        } catch (FileAlreadyExistsException e) {
            throw e;
        } catch (IOException e2) {
            Path parent = path.getParent();
            if (parent == null) {
                throw e2;
            }
            createDirectories(parent);
            tryCreateDirectory(path);
        }
        if (path == null) {
            $$$reportNull$$$0(1);
        }
        return path;
    }

    private static void tryCreateDirectory(Path path) throws IOException {
        try {
            Files.createDirectory(path, new FileAttribute[0]);
        } catch (FileAlreadyExistsException e) {
            if (!Files.isDirectory(path, new LinkOption[0])) {
                throw e;
            }
        }
    }

    public static void setExecutable(Path path) throws IOException {
        if (path == null) {
            $$$reportNull$$$0(3);
        }
        PosixFileAttributeView posixFileAttributeView = (PosixFileAttributeView) Files.getFileAttributeView(path, PosixFileAttributeView.class, new LinkOption[0]);
        if (posixFileAttributeView != null) {
            Set<PosixFilePermission> setPermissions = posixFileAttributeView.readAttributes().permissions();
            if (setPermissions.add(PosixFilePermission.OWNER_EXECUTE)) {
                posixFileAttributeView.setPermissions(setPermissions);
            }
        }
    }
}
