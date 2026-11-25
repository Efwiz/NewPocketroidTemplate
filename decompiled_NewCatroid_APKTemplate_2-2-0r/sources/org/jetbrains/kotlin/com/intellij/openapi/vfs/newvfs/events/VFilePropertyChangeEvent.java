package org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events;

import java.nio.charset.Charset;
import java.util.Objects;
import org.jetbrains.kotlin.com.intellij.openapi.util.Comparing;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileAttributes;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;

/* JADX INFO: loaded from: classes3.dex */
public final class VFilePropertyChangeEvent extends VFileEvent {
    private final VirtualFile myFile;
    private final Object myNewValue;
    private final Object myOldValue;
    private final String myPropertyName;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 2:
                objArr[0] = "propertyName";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/newvfs/events/VFilePropertyChangeEvent";
                break;
            default:
                objArr[0] = "file";
                break;
        }
        switch (i) {
            case 3:
                objArr[1] = "getFile";
                break;
            case 4:
                objArr[1] = "getPropertyName";
                break;
            case 5:
                objArr[1] = "getPath";
                break;
            case 6:
                objArr[1] = "computePath";
                break;
            case 7:
                objArr[1] = "getFileSystem";
                break;
            case 8:
                objArr[1] = "toString";
                break;
            case 9:
            case 10:
            case 11:
                objArr[1] = "getPathWithFileName";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/openapi/vfs/newvfs/events/VFilePropertyChangeEvent";
                break;
        }
        switch (i) {
            case 2:
                objArr[2] = "checkPropertyValuesCorrect";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VFilePropertyChangeEvent(Object obj, VirtualFile virtualFile, String str, Object obj2, Object obj3, boolean z) {
        super(obj, z);
        if (virtualFile == null) {
            $$$reportNull$$$0(0);
        }
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        this.myFile = virtualFile;
        this.myPropertyName = str;
        this.myOldValue = obj2;
        this.myNewValue = obj3;
        checkPropertyValuesCorrect(obj, str, obj2, obj3);
    }

    public static void checkPropertyValuesCorrect(Object obj, String str, Object obj2, Object obj3) {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        if (Comparing.equal(obj2, obj3) && "FileContentUtilCore.saveOrReload" != obj) {
            throw new IllegalArgumentException("Values must be different, got the same: " + obj2);
        }
        str.hashCode();
        switch (str) {
            case "writable":
                if (!(obj2 instanceof Boolean)) {
                    throw new IllegalArgumentException("oldWriteable must be boolean, got " + obj2);
                }
                if (!(obj3 instanceof Boolean)) {
                    throw new IllegalArgumentException("newWriteable must be boolean, got " + obj3);
                }
                return;
            case "symlink":
                if (obj2 != null && !(obj2 instanceof String)) {
                    throw new IllegalArgumentException("oldSymTarget must be String, got " + obj2);
                }
                if (obj3 != null && !(obj3 instanceof String)) {
                    throw new IllegalArgumentException("newSymTarget must be String, got " + obj3);
                }
                return;
            case "name":
                if (obj2 == null) {
                    throw new IllegalArgumentException("oldName must not be null");
                }
                if (!(obj2 instanceof String)) {
                    throw new IllegalArgumentException("oldName must be String, got " + obj2);
                }
                if (obj3 == null) {
                    throw new IllegalArgumentException("newName must not be null");
                }
                if (!(obj3 instanceof String)) {
                    throw new IllegalArgumentException("newName must be String, got " + obj3);
                }
                return;
            case "CHILDREN_CASE_SENSITIVITY":
                if (!(obj2 instanceof FileAttributes.CaseSensitivity)) {
                    throw new IllegalArgumentException("oldValue must be FileAttributes.CaseSensitivity but got " + obj2);
                }
                if (!(obj3 instanceof FileAttributes.CaseSensitivity)) {
                    throw new IllegalArgumentException("newValue must be FileAttributes.CaseSensitivity but got " + obj3);
                }
                if (obj2.equals(obj3)) {
                    throw new IllegalArgumentException("newValue must be different from the oldValue but got " + obj3);
                }
                return;
            case "encoding":
                if (obj2 == null) {
                    throw new IllegalArgumentException("oldCharset must not be null");
                }
                if (!(obj2 instanceof Charset)) {
                    throw new IllegalArgumentException("oldValue must be Charset, got " + obj2);
                }
                return;
            case "HIDDEN":
                if (!(obj2 instanceof Boolean)) {
                    throw new IllegalArgumentException("oldHidden must be boolean, got " + obj2);
                }
                if (!(obj3 instanceof Boolean)) {
                    throw new IllegalArgumentException("newHidden must be boolean, got " + obj3);
                }
                return;
            default:
                throw new IllegalArgumentException("Unknown property name '" + str + "'. Must be one of VirtualFile.{PROP_NAME|PROP_ENCODING|PROP_WRITABLE|PROP_HIDDEN|PROP_SYMLINK_TARGET}");
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events.VFileEvent
    public VirtualFile getFile() {
        VirtualFile virtualFile = this.myFile;
        if (virtualFile == null) {
            $$$reportNull$$$0(3);
        }
        return virtualFile;
    }

    public Object getNewValue() {
        return this.myNewValue;
    }

    public Object getOldValue() {
        return this.myOldValue;
    }

    public String getPropertyName() {
        String str = this.myPropertyName;
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        return str;
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.vfs.newvfs.events.VFileEvent
    public VirtualFileSystem getFileSystem() {
        VirtualFileSystem fileSystem = this.myFile.getFileSystem();
        if (fileSystem == null) {
            $$$reportNull$$$0(7);
        }
        return fileSystem;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        VFilePropertyChangeEvent vFilePropertyChangeEvent = (VFilePropertyChangeEvent) obj;
        return this.myFile.equals(vFilePropertyChangeEvent.myFile) && Objects.equals(this.myNewValue, vFilePropertyChangeEvent.myNewValue) && Objects.equals(this.myOldValue, vFilePropertyChangeEvent.myOldValue) && this.myPropertyName.equals(vFilePropertyChangeEvent.myPropertyName);
    }

    public int hashCode() {
        int iHashCode = ((this.myFile.hashCode() * 31) + this.myPropertyName.hashCode()) * 31;
        Object obj = this.myOldValue;
        int iHashCode2 = (iHashCode + (obj != null ? obj.hashCode() : 0)) * 31;
        Object obj2 = this.myNewValue;
        return iHashCode2 + (obj2 != null ? obj2.hashCode() : 0);
    }

    public String toString() {
        String str = "VfsEvent[property(" + this.myPropertyName + ") changed for '" + this.myFile + "': " + this.myOldValue + " -> " + this.myNewValue + ']';
        if (str == null) {
            $$$reportNull$$$0(8);
        }
        return str;
    }
}
