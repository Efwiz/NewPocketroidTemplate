package org.jetbrains.kotlin.com.intellij.openapi.vfs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Set;
import org.jetbrains.kotlin.com.intellij.model.ModelBranchUtil;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.openapi.roots.ContentIterator;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.BufferExposingByteArrayInputStream;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.util.PathUtil;
import org.jetbrains.kotlin.com.intellij.util.Processor;

/* JADX INFO: loaded from: classes3.dex */
public class VfsUtilCore {
    private static final Logger LOG = Logger.getInstance((Class<?>) VfsUtilCore.class);

    /* JADX WARN: Code duplicated, block: B:9:0x0016 A[FALL_THROUGH] */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 51 && i != 52) {
            switch (i) {
                default:
                    switch (i) {
                        default:
                            switch (i) {
                                case 60:
                                case 61:
                                case 62:
                                case 63:
                                case 64:
                                case 65:
                                case 66:
                                    break;
                                default:
                                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                                    break;
                            }
                        case 35:
                        case 36:
                        case 37:
                        case 38:
                        case 39:
                            str = "@NotNull method %s.%s must not return null";
                            break;
                    }
                case 20:
                case 25:
                case 28:
                case 47:
                case 49:
                case 55:
                case 69:
                case 72:
                case 79:
                case 86:
                case 90:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 51 && i != 52) {
            switch (i) {
                case 20:
                case 25:
                case 28:
                case 47:
                case 49:
                case 55:
                case 69:
                case 72:
                case 79:
                case 86:
                case 90:
                    i2 = 2;
                    break;
                default:
                    switch (i) {
                        case 35:
                        case 36:
                        case 37:
                        case 38:
                        case 39:
                            i2 = 2;
                            break;
                        default:
                            switch (i) {
                                case 60:
                                case 61:
                                case 62:
                                case 63:
                                case 64:
                                case 65:
                                case 66:
                                    i2 = 2;
                                    break;
                                default:
                                    i2 = 3;
                                    break;
                            }
                            break;
                    }
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 2:
            case 7:
            case 9:
            case 11:
            case 15:
            case 17:
            case 21:
            case 24:
            case 27:
            case 33:
            case 40:
            case 43:
            case 45:
            case 46:
            case 48:
            case 53:
            case 56:
            case 78:
            case 81:
            case 83:
            case 85:
            case 88:
            case 89:
                objArr[0] = "file";
                break;
            case 3:
            case 58:
            case 59:
            case 67:
            case 68:
                objArr[0] = "url";
                break;
            case 4:
                objArr[0] = "ancestorUrl";
                break;
            case 5:
                objArr[0] = "fileUrl";
                break;
            case 6:
            case 10:
            case 12:
            default:
                objArr[0] = "ancestor";
                break;
            case 8:
            case 29:
            case 31:
            case 74:
                objArr[0] = "root";
                break;
            case 13:
                objArr[0] = "src";
                break;
            case 14:
                objArr[0] = "dst";
                break;
            case 16:
            case 18:
                objArr[0] = "toDir";
                break;
            case 19:
                objArr[0] = "newName";
                break;
            case 20:
            case 25:
            case 28:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 47:
            case 49:
            case 51:
            case 52:
            case 55:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 69:
            case 72:
            case 79:
            case 86:
            case 90:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/VfsUtilCore";
                break;
            case 22:
                objArr[0] = "buf";
                break;
            case 23:
            case 26:
                objArr[0] = "stream";
                break;
            case 30:
            case 32:
                objArr[0] = "iterator";
                break;
            case 34:
            case 41:
                objArr[0] = "visitor";
                break;
            case 42:
                objArr[0] = "eClass";
                break;
            case 44:
                objArr[0] = "link";
                break;
            case 50:
            case 57:
            case 80:
            case 91:
                objArr[0] = "files";
                break;
            case 54:
            case 84:
                objArr[0] = "path";
                break;
            case 70:
                objArr[0] = "vfsUrl";
                break;
            case 71:
                objArr[0] = "ideaUrl";
                break;
            case 73:
                objArr[0] = "uri";
                break;
            case 75:
                objArr[0] = "processor";
                break;
            case 76:
                objArr[0] = "file1";
                break;
            case 77:
                objArr[0] = "file2";
                break;
            case 82:
                objArr[0] = "name";
                break;
            case 87:
                objArr[0] = "ancestorPath";
                break;
        }
        if (i == 51) {
            objArr[1] = "toVirtualFileArray";
        } else if (i != 52) {
            switch (i) {
                case 20:
                    objArr[1] = "copyFile";
                    break;
                case 25:
                    objArr[1] = "inputStreamSkippingBOM";
                    break;
                case 28:
                    objArr[1] = "outputStreamAddingBOM";
                    break;
                case 47:
                    objArr[1] = "loadText";
                    break;
                case 49:
                    objArr[1] = "loadBytes";
                    break;
                case 55:
                    objArr[1] = "pathToUrl";
                    break;
                case 69:
                    objArr[1] = "convertFromUrl";
                    break;
                case 72:
                    objArr[1] = "fixIDEAUrl";
                    break;
                case 79:
                    objArr[1] = "getPathComponents";
                    break;
                case 86:
                    objArr[1] = "getHierarchy";
                    break;
                case 90:
                    objArr[1] = "getRootFile";
                    break;
                default:
                    switch (i) {
                        case 35:
                        case 36:
                        case 37:
                        case 38:
                        case 39:
                            objArr[1] = "visitChildrenRecursively";
                            break;
                        default:
                            switch (i) {
                                case 60:
                                case 61:
                                case 62:
                                case 63:
                                case 64:
                                case 65:
                                case 66:
                                    objArr[1] = "toIdeaUrl";
                                    break;
                                default:
                                    objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/VfsUtilCore";
                                    break;
                            }
                            break;
                    }
                    break;
            }
        } else {
            objArr[1] = "urlToPath";
        }
        switch (i) {
            case 2:
            case 3:
                objArr[2] = "isUnder";
                break;
            case 4:
            case 5:
                objArr[2] = "isEqualOrAncestor";
                break;
            case 6:
            case 7:
            default:
                objArr[2] = "isAncestor";
                break;
            case 8:
                objArr[2] = "getRelativeLocation";
                break;
            case 9:
            case 10:
            case 11:
            case 12:
                objArr[2] = "getRelativePath";
                break;
            case 13:
            case 14:
                objArr[2] = "findRelativePath";
                break;
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
                objArr[2] = "copyFile";
                break;
            case 20:
            case 25:
            case 28:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 47:
            case 49:
            case 51:
            case 52:
            case 55:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 69:
            case 72:
            case 79:
            case 86:
            case 90:
                break;
            case 21:
            case 22:
                objArr[2] = "byteStreamSkippingBOM";
                break;
            case 23:
            case 24:
                objArr[2] = "inputStreamSkippingBOM";
                break;
            case 26:
            case 27:
                objArr[2] = "outputStreamAddingBOM";
                break;
            case 29:
            case 30:
            case 31:
            case 32:
                objArr[2] = "iterateChildrenRecursively";
                break;
            case 33:
            case 34:
            case 40:
            case 41:
            case 42:
                objArr[2] = "visitChildrenRecursively";
                break;
            case 43:
                objArr[2] = "isBrokenLink";
                break;
            case 44:
                objArr[2] = "isInvalidLink";
                break;
            case 45:
            case 46:
                objArr[2] = "loadText";
                break;
            case 48:
                objArr[2] = "loadBytes";
                break;
            case 50:
                objArr[2] = "toVirtualFileArray";
                break;
            case 53:
                objArr[2] = "virtualToIoFile";
                break;
            case 54:
                objArr[2] = "pathToUrl";
                break;
            case 56:
                objArr[2] = "fileToUrl";
                break;
            case 57:
                objArr[2] = "virtualToIoFiles";
                break;
            case 58:
            case 59:
                objArr[2] = "toIdeaUrl";
                break;
            case 67:
                objArr[2] = "fixURLforIDEA";
                break;
            case 68:
                objArr[2] = "convertFromUrl";
                break;
            case 70:
                objArr[2] = "convertToURL";
                break;
            case 71:
                objArr[2] = "fixIDEAUrl";
                break;
            case 73:
                objArr[2] = "findRelativeFile";
                break;
            case 74:
            case 75:
                objArr[2] = "processFilesRecursively";
                break;
            case 76:
            case 77:
                objArr[2] = "getCommonAncestor";
                break;
            case 78:
                objArr[2] = "getPathComponents";
                break;
            case 80:
                objArr[2] = "hasInvalidFiles";
                break;
            case 81:
            case 82:
                objArr[2] = "findContainingDirectory";
                break;
            case 83:
            case 84:
                objArr[2] = "pathEqualsTo";
                break;
            case 85:
                objArr[2] = "getHierarchy";
                break;
            case 87:
            case 88:
                objArr[2] = "isAncestorOrSelf";
                break;
            case 89:
                objArr[2] = "getRootFile";
                break;
            case 91:
                objArr[2] = "createCompactVirtualFileSet";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 51 && i != 52) {
            switch (i) {
                case 20:
                case 25:
                case 28:
                case 47:
                case 49:
                case 55:
                case 69:
                case 72:
                case 79:
                case 86:
                case 90:
                    break;
                default:
                    switch (i) {
                        case 35:
                        case 36:
                        case 37:
                        case 38:
                        case 39:
                            break;
                        default:
                            switch (i) {
                                case 60:
                                case 61:
                                case 62:
                                case 63:
                                case 64:
                                case 65:
                                case 66:
                                    break;
                                default:
                                    throw new IllegalArgumentException(str2);
                            }
                            break;
                    }
                    break;
            }
        }
        throw new IllegalStateException(str2);
    }

    public static boolean isAncestor(VirtualFile virtualFile, VirtualFile virtualFile2, boolean z) {
        if (virtualFile == null) {
            $$$reportNull$$$0(0);
        }
        if (virtualFile2 == null) {
            $$$reportNull$$$0(1);
        }
        if (!virtualFile2.getFileSystem().equals(virtualFile.getFileSystem())) {
            return false;
        }
        if (z) {
            virtualFile2 = virtualFile2.getParent();
        }
        while (virtualFile2 != null) {
            if (virtualFile2.equals(virtualFile)) {
                return true;
            }
            virtualFile2 = virtualFile2.getParent();
        }
        return false;
    }

    public static boolean isUnder(VirtualFile virtualFile, Set<? extends VirtualFile> set) {
        if (virtualFile == null) {
            $$$reportNull$$$0(2);
        }
        if (set != null && !set.isEmpty()) {
            while (virtualFile != null) {
                if (set.contains(virtualFile)) {
                    return true;
                }
                virtualFile = virtualFile.getParent();
            }
        }
        return false;
    }

    public static String getRelativePath(VirtualFile virtualFile, VirtualFile virtualFile2, char c) {
        if (virtualFile == null) {
            $$$reportNull$$$0(11);
        }
        if (virtualFile2 == null) {
            $$$reportNull$$$0(12);
        }
        if (!virtualFile.getFileSystem().equals(virtualFile2.getFileSystem())) {
            ModelBranchUtil.checkSameBranch(virtualFile, virtualFile2);
            return null;
        }
        int length = 0;
        for (VirtualFile parent = virtualFile; parent != null; parent = parent.getParent()) {
            if (!parent.equals(virtualFile2)) {
                if (length > 0) {
                    length++;
                }
                length += parent.getNameSequence().length();
            } else {
                char[] cArr = new char[length];
                int i = length;
                while (!virtualFile.equals(virtualFile2)) {
                    if (i < length) {
                        i--;
                        cArr[i] = c;
                    }
                    CharSequence nameSequence = virtualFile.getNameSequence();
                    for (int length2 = nameSequence.length() - 1; length2 >= 0; length2--) {
                        i--;
                        cArr[i] = nameSequence.charAt(length2);
                    }
                    virtualFile = virtualFile.getParent();
                }
                return new String(cArr);
            }
        }
        return null;
    }

    public static String findRelativePath(VirtualFile virtualFile, VirtualFile virtualFile2, char c) {
        VirtualFile commonAncestor;
        if (virtualFile == null) {
            $$$reportNull$$$0(13);
        }
        if (virtualFile2 == null) {
            $$$reportNull$$$0(14);
        }
        if (!virtualFile.getFileSystem().equals(virtualFile2.getFileSystem())) {
            ModelBranchUtil.checkSameBranch(virtualFile, virtualFile2);
            return null;
        }
        if ((!virtualFile.isDirectory() && (virtualFile = virtualFile.getParent()) == null) || (commonAncestor = getCommonAncestor(virtualFile, virtualFile2)) == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        if (!Comparing.equal(virtualFile, commonAncestor)) {
            while (!Comparing.equal(virtualFile, commonAncestor)) {
                sb.append("..").append(c);
                virtualFile = virtualFile.getParent();
            }
        }
        sb.append(getRelativePath(virtualFile2, commonAncestor, c));
        if (StringUtil.endsWithChar(sb, c)) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    public static VirtualFile getVirtualFileForJar(VirtualFile virtualFile) {
        String path;
        int iIndexOf;
        if (virtualFile != null && (iIndexOf = (path = virtualFile.getPath()).indexOf("!/")) >= 0) {
            return VirtualFileManager.getInstance().findFileByUrl(StandardFileSystems.FILE_PROTOCOL_PREFIX + path.substring(0, iIndexOf));
        }
        return null;
    }

    public static VirtualFile copyFile(Object obj, VirtualFile virtualFile, VirtualFile virtualFile2) throws IOException {
        if (virtualFile == null) {
            $$$reportNull$$$0(15);
        }
        if (virtualFile2 == null) {
            $$$reportNull$$$0(16);
        }
        return copyFile(obj, virtualFile, virtualFile2, virtualFile.getName());
    }

    public static VirtualFile copyFile(Object obj, VirtualFile virtualFile, VirtualFile virtualFile2, String str) throws IOException {
        if (virtualFile == null) {
            $$$reportNull$$$0(17);
        }
        if (virtualFile2 == null) {
            $$$reportNull$$$0(18);
        }
        if (str == null) {
            $$$reportNull$$$0(19);
        }
        VirtualFile virtualFileCreateChildData = virtualFile2.createChildData(obj, str);
        virtualFileCreateChildData.setBinaryContent(virtualFile.contentsToByteArray(), -1L, -1L, obj);
        virtualFileCreateChildData.setBOM(virtualFile.getBOM());
        if (virtualFileCreateChildData == null) {
            $$$reportNull$$$0(20);
        }
        return virtualFileCreateChildData;
    }

    public static InputStream byteStreamSkippingBOM(byte[] bArr, VirtualFile virtualFile) throws IOException {
        if (virtualFile == null) {
            $$$reportNull$$$0(21);
        }
        if (bArr == null) {
            $$$reportNull$$$0(22);
        }
        return inputStreamSkippingBOM(new BufferExposingByteArrayInputStream(bArr), virtualFile);
    }

    public static InputStream inputStreamSkippingBOM(InputStream inputStream, VirtualFile virtualFile) throws IOException {
        if (inputStream == null) {
            $$$reportNull$$$0(23);
        }
        if (virtualFile == null) {
            $$$reportNull$$$0(24);
        }
        InputStream inputStreamInputStreamSkippingBOM = CharsetToolkit.inputStreamSkippingBOM(inputStream);
        if (inputStreamInputStreamSkippingBOM == null) {
            $$$reportNull$$$0(25);
        }
        return inputStreamInputStreamSkippingBOM;
    }

    public static OutputStream outputStreamAddingBOM(OutputStream outputStream, VirtualFile virtualFile) throws IOException {
        if (outputStream == null) {
            $$$reportNull$$$0(26);
        }
        if (virtualFile == null) {
            $$$reportNull$$$0(27);
        }
        byte[] bom = virtualFile.getBOM();
        if (bom != null) {
            outputStream.write(bom);
        }
        if (outputStream == null) {
            $$$reportNull$$$0(28);
        }
        return outputStream;
    }

    public static boolean iterateChildrenRecursively(VirtualFile virtualFile, VirtualFileFilter virtualFileFilter, ContentIterator contentIterator) {
        if (virtualFile == null) {
            $$$reportNull$$$0(29);
        }
        if (contentIterator == null) {
            $$$reportNull$$$0(30);
        }
        return iterateChildrenRecursively(virtualFile, virtualFileFilter, contentIterator, new VirtualFileVisitor.Option[0]);
    }

    public static boolean iterateChildrenRecursively(final VirtualFile virtualFile, final VirtualFileFilter virtualFileFilter, final ContentIterator contentIterator, VirtualFileVisitor.Option... optionArr) {
        if (virtualFile == null) {
            $$$reportNull$$$0(31);
        }
        if (contentIterator == null) {
            $$$reportNull$$$0(32);
        }
        return !Comparing.equal(visitChildrenRecursively(virtualFile, new VirtualFileVisitor<Void>(optionArr) { // from class: org.jetbrains.kotlin.com.intellij.openapi.vfs.VfsUtilCore.1
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                String str = (i == 1 || i == 2 || i == 3) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                Object[] objArr = new Object[(i == 1 || i == 2 || i == 3) ? 2 : 3];
                if (i == 1 || i == 2 || i == 3) {
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/VfsUtilCore$1";
                } else {
                    objArr[0] = "file";
                }
                if (i == 1 || i == 2 || i == 3) {
                    objArr[1] = "visitFileEx";
                } else {
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/VfsUtilCore$1";
                }
                if (i != 1 && i != 2 && i != 3) {
                    objArr[2] = "visitFileEx";
                }
                String str2 = String.format(str, objArr);
                if (i != 1 && i != 2 && i != 3) {
                    throw new IllegalArgumentException(str2);
                }
                throw new IllegalStateException(str2);
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileVisitor
            public VirtualFileVisitor.Result visitFileEx(VirtualFile virtualFile2) {
                if (virtualFile2 == null) {
                    $$$reportNull$$$0(0);
                }
                VirtualFileFilter virtualFileFilter2 = virtualFileFilter;
                if (virtualFileFilter2 != null && !virtualFileFilter2.accept(virtualFile2)) {
                    VirtualFileVisitor.Result result = SKIP_CHILDREN;
                    if (result == null) {
                        $$$reportNull$$$0(1);
                    }
                    return result;
                }
                if (contentIterator.processFile(virtualFile2)) {
                    VirtualFileVisitor.Result result2 = CONTINUE;
                    if (result2 == null) {
                        $$$reportNull$$$0(3);
                    }
                    return result2;
                }
                VirtualFileVisitor.Result resultSkipTo = skipTo(virtualFile);
                if (resultSkipTo == null) {
                    $$$reportNull$$$0(2);
                }
                return resultSkipTo;
            }
        }).skipToParent, virtualFile);
    }

    /* JADX WARN: Code duplicated, block: B:60:0x00b1 A[Catch: all -> 0x00e5, TRY_ENTER, TryCatch #1 {all -> 0x00e5, blocks: (B:8:0x0014, B:10:0x001a, B:16:0x002d, B:18:0x0034, B:20:0x003a, B:22:0x0040, B:24:0x0046, B:29:0x0051, B:60:0x00b1, B:61:0x00b4, B:45:0x0086, B:47:0x0089, B:67:0x00c2), top: B:76:0x0014, inners: #2 }] */
    /* JADX WARN: Code duplicated, block: B:64:0x00bb  */
    public static VirtualFileVisitor.Result visitChildrenRecursively(VirtualFile virtualFile, VirtualFileVisitor<?> virtualFileVisitor) throws Throwable {
        VirtualFile[] children;
        VirtualFileVisitor.Result result;
        if (virtualFile == null) {
            $$$reportNull$$$0(33);
        }
        if (virtualFileVisitor == null) {
            $$$reportNull$$$0(34);
        }
        ProgressManager.checkCanceled();
        boolean z = false;
        z = false;
        boolean z2 = false;
        try {
            boolean zAllowVisitFile = virtualFileVisitor.allowVisitFile(virtualFile);
            if (zAllowVisitFile) {
                VirtualFileVisitor.Result resultVisitFileEx = virtualFileVisitor.visitFileEx(virtualFile);
                if (resultVisitFileEx.skipChildren) {
                    virtualFileVisitor.restoreValue(false);
                    if (resultVisitFileEx == null) {
                        $$$reportNull$$$0(35);
                    }
                    return resultVisitFileEx;
                }
            }
            try {
                Iterable<VirtualFile> iterable = null;
                if (virtualFile.isValid() && virtualFileVisitor.allowVisitChildren(virtualFile) && !virtualFileVisitor.depthLimitReached()) {
                    Iterable<VirtualFile> childrenIterable = virtualFileVisitor.getChildrenIterable(virtualFile);
                    children = childrenIterable == null ? virtualFile.getChildren() : null;
                    iterable = childrenIterable;
                } else {
                    children = null;
                }
                try {
                    if (iterable == null) {
                        if (children != null && children.length != 0) {
                            virtualFileVisitor.saveValue();
                            for (VirtualFile virtualFile2 : children) {
                                VirtualFileVisitor.Result resultVisitChildrenRecursively = visitChildrenRecursively(virtualFile2, virtualFileVisitor);
                                if (resultVisitChildrenRecursively.skipToParent != null && !Comparing.equal(resultVisitChildrenRecursively.skipToParent, virtualFile2)) {
                                    virtualFileVisitor.restoreValue(true);
                                    if (resultVisitChildrenRecursively == null) {
                                        $$$reportNull$$$0(38);
                                    }
                                    return resultVisitChildrenRecursively;
                                }
                            }
                        }
                        if (zAllowVisitFile) {
                            virtualFileVisitor.afterChildrenVisited(virtualFile);
                        }
                        result = VirtualFileVisitor.CONTINUE;
                        virtualFileVisitor.restoreValue(z2);
                        if (result == null) {
                            $$$reportNull$$$0(39);
                        }
                        return result;
                    }
                    virtualFileVisitor.saveValue();
                    for (VirtualFile virtualFile3 : iterable) {
                        VirtualFileVisitor.Result resultVisitChildrenRecursively2 = visitChildrenRecursively(virtualFile3, virtualFileVisitor);
                        if (resultVisitChildrenRecursively2.skipToParent != null && !Comparing.equal(resultVisitChildrenRecursively2.skipToParent, virtualFile3)) {
                            virtualFileVisitor.restoreValue(true);
                            if (resultVisitChildrenRecursively2 == null) {
                                $$$reportNull$$$0(37);
                            }
                            return resultVisitChildrenRecursively2;
                        }
                    }
                    z2 = true;
                    if (zAllowVisitFile) {
                        virtualFileVisitor.afterChildrenVisited(virtualFile);
                    }
                    result = VirtualFileVisitor.CONTINUE;
                    virtualFileVisitor.restoreValue(z2);
                    if (result == null) {
                        $$$reportNull$$$0(39);
                    }
                    return result;
                } catch (Throwable th) {
                    th = th;
                    z = true;
                    virtualFileVisitor.restoreValue(z);
                    throw th;
                }
            } catch (InvalidVirtualFileAccessException e) {
                LOG.info("Ignoring: " + e.getMessage());
                VirtualFileVisitor.Result result2 = VirtualFileVisitor.CONTINUE;
                virtualFileVisitor.restoreValue(false);
                if (result2 == null) {
                    $$$reportNull$$$0(36);
                }
                return result2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static VirtualFile[] toVirtualFileArray(Collection<? extends VirtualFile> collection) {
        if (collection == null) {
            $$$reportNull$$$0(50);
        }
        VirtualFile[] virtualFileArr = collection.isEmpty() ? VirtualFile.EMPTY_ARRAY : (VirtualFile[]) collection.toArray(VirtualFile.EMPTY_ARRAY);
        if (virtualFileArr == null) {
            $$$reportNull$$$0(51);
        }
        return virtualFileArr;
    }

    public static File virtualToIoFile(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(53);
        }
        return new File(PathUtil.toPresentableUrl(virtualFile.getUrl()));
    }

    public static boolean processFilesRecursively(final VirtualFile virtualFile, final Processor<? super VirtualFile> processor) throws Throwable {
        if (virtualFile == null) {
            $$$reportNull$$$0(74);
        }
        if (processor == null) {
            $$$reportNull$$$0(75);
        }
        final Ref ref = new Ref(true);
        visitChildrenRecursively(virtualFile, new VirtualFileVisitor<Void>(new VirtualFileVisitor.Option[0]) { // from class: org.jetbrains.kotlin.com.intellij.openapi.vfs.VfsUtilCore.2
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                String str = (i == 1 || i == 2) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                Object[] objArr = new Object[(i == 1 || i == 2) ? 2 : 3];
                if (i == 1 || i == 2) {
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/VfsUtilCore$2";
                } else {
                    objArr[0] = "file";
                }
                if (i == 1 || i == 2) {
                    objArr[1] = "visitFileEx";
                } else {
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/VfsUtilCore$2";
                }
                if (i != 1 && i != 2) {
                    objArr[2] = "visitFileEx";
                }
                String str2 = String.format(str, objArr);
                if (i != 1 && i != 2) {
                    throw new IllegalArgumentException(str2);
                }
                throw new IllegalStateException(str2);
            }

            @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileVisitor
            public VirtualFileVisitor.Result visitFileEx(VirtualFile virtualFile2) {
                if (virtualFile2 == null) {
                    $$$reportNull$$$0(0);
                }
                if (!processor.process(virtualFile2)) {
                    ref.set(Boolean.FALSE);
                    VirtualFileVisitor.Result resultSkipTo = skipTo(virtualFile);
                    if (resultSkipTo == null) {
                        $$$reportNull$$$0(1);
                    }
                    return resultSkipTo;
                }
                VirtualFileVisitor.Result result = CONTINUE;
                if (result == null) {
                    $$$reportNull$$$0(2);
                }
                return result;
            }
        });
        return ((Boolean) ref.get()).booleanValue();
    }

    public static VirtualFile getCommonAncestor(VirtualFile virtualFile, VirtualFile virtualFile2) {
        if (virtualFile == null) {
            $$$reportNull$$$0(76);
        }
        if (virtualFile2 == null) {
            $$$reportNull$$$0(77);
        }
        if (!virtualFile.getFileSystem().equals(virtualFile2.getFileSystem())) {
            return null;
        }
        if (virtualFile.equals(virtualFile2)) {
            return virtualFile;
        }
        int iDepth = depth(virtualFile);
        int iDepth2 = depth(virtualFile2);
        while (iDepth > iDepth2 && virtualFile != null) {
            virtualFile = virtualFile.getParent();
            iDepth--;
        }
        while (iDepth2 > iDepth && virtualFile2 != null) {
            virtualFile2 = virtualFile2.getParent();
            iDepth2--;
        }
        while (virtualFile != null && virtualFile2 != null && !virtualFile.equals(virtualFile2)) {
            virtualFile = virtualFile.getParent();
            virtualFile2 = virtualFile2.getParent();
        }
        return virtualFile;
    }

    private static int depth(VirtualFile virtualFile) {
        int i = 0;
        while (virtualFile != null) {
            i++;
            virtualFile = virtualFile.getParent();
        }
        return i;
    }

    public static VirtualFileSet createCompactVirtualFileSet() {
        return new CompactVirtualFileSet();
    }

    public static VirtualFileSet createCompactVirtualFileSet(Collection<? extends VirtualFile> collection) {
        if (collection == null) {
            $$$reportNull$$$0(91);
        }
        return new CompactVirtualFileSet(collection);
    }
}
