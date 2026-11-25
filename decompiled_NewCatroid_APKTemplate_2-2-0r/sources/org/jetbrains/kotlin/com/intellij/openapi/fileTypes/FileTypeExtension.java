package org.jetbrains.kotlin.com.intellij.openapi.fileTypes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.openapi.util.KeyedExtensionCollector;
import org.jetbrains.kotlin.com.intellij.util.KeyedLazyInstance;

/* JADX INFO: loaded from: classes3.dex */
public class FileTypeExtension<T> extends KeyedExtensionCollector<T, FileType> {
    /* JADX WARN: Code duplicated, block: B:19:0x0029  */
    /* JADX WARN: Code duplicated, block: B:20:0x002e  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 4) ? 2 : 3];
        if (i == 1) {
            objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
        } else if (i == 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/fileTypes/FileTypeExtension";
        } else if (i == 3) {
            objArr[0] = "t";
        } else if (i == 4) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/fileTypes/FileTypeExtension";
        } else if (i != 5) {
            objArr[0] = "epName";
        } else {
            objArr[0] = "t";
        }
        if (i == 2) {
            objArr[1] = "keyToString";
        } else if (i != 4) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/fileTypes/FileTypeExtension";
        } else {
            objArr[1] = "allForFileType";
        }
        if (i == 1) {
            objArr[2] = "keyToString";
        } else if (i != 2) {
            if (i == 3) {
                objArr[2] = "allForFileType";
            } else if (i != 4) {
                if (i != 5) {
                    objArr[2] = "<init>";
                } else {
                    objArr[2] = "forFileType";
                }
            }
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 4) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    public FileTypeExtension(String str) {
        super(str);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FileTypeExtension(ExtensionPointName<KeyedLazyInstance<T>> extensionPointName) {
        super(extensionPointName);
        if (extensionPointName == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.KeyedExtensionCollector
    public String keyToString(FileType fileType) {
        if (fileType == null) {
            $$$reportNull$$$0(1);
        }
        String name2 = fileType.getName();
        if (name2 == null) {
            $$$reportNull$$$0(2);
        }
        return name2;
    }

    public List<T> allForFileType(FileType fileType) {
        if (fileType == null) {
            $$$reportNull$$$0(3);
        }
        List<T> listForKey = forKey(fileType);
        if (listForKey == null) {
            $$$reportNull$$$0(4);
        }
        return listForKey;
    }

    public T forFileType(FileType fileType) {
        if (fileType == null) {
            $$$reportNull$$$0(5);
        }
        List<T> listAllForFileType = allForFileType(fileType);
        if (listAllForFileType.isEmpty()) {
            return null;
        }
        return listAllForFileType.get(0);
    }

    public Map<FileType, T> getAllRegisteredExtensions() {
        List<KeyedLazyInstance<T>> extensions = getExtensions();
        HashMap map = new HashMap();
        for (KeyedLazyInstance<T> keyedLazyInstance : extensions) {
            FileType fileTypeFindFileTypeByName = FileTypeRegistry.getInstance().findFileTypeByName(keyedLazyInstance.getKey());
            if (fileTypeFindFileTypeByName != null) {
                map.put(fileTypeFindFileTypeByName, keyedLazyInstance.getInstance());
            }
        }
        return map;
    }
}
