package com.reandroid.dex.model;

import com.reandroid.dex.common.AccessFlag;
import com.reandroid.dex.common.IdDefinition;
import com.reandroid.dex.common.Modifier;
import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.TypeKey;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public abstract class DexDeclaration extends Dex implements AnnotatedDex {
    public abstract IdDefinition<?> getDefinition();

    public abstract DexClass getDexClass();

    public abstract IdItem getId();

    public abstract Key getKey();

    @Override // com.reandroid.dex.model.Dex
    public boolean uses(Key key) {
        if (getKey().equals(key)) {
            return false;
        }
        return getId().uses(key);
    }

    public boolean isAccessibleTo(TypeKey typeKey) {
        if (getDefining().equals(typeKey)) {
            return true;
        }
        if (isInternal()) {
            return getPackageName().equals(typeKey.getPackageName());
        }
        return !isPrivate();
    }

    public boolean isAccessibleTo(DexClass dexClass) {
        DexClass dexClass2 = getDexClass();
        TypeKey defining = dexClass.getDefining();
        if (!dexClass2.isAccessibleTo(defining)) {
            return false;
        }
        if (dexClass2.getDefining().equals(defining)) {
            return true;
        }
        return dexClass2 == this || isAccessibleTo(defining);
    }

    public boolean isInternal() {
        return (getAccessFlagsValue() & 7) == 0;
    }

    public boolean isFinal() {
        return AccessFlag.FINAL.isSet(getAccessFlagsValue());
    }

    public boolean isPublic() {
        return AccessFlag.PUBLIC.isSet(getAccessFlagsValue());
    }

    public boolean isProtected() {
        return AccessFlag.PROTECTED.isSet(getAccessFlagsValue());
    }

    public boolean isPrivate() {
        return AccessFlag.PRIVATE.isSet(getAccessFlagsValue());
    }

    public boolean isNative() {
        return AccessFlag.NATIVE.isSet(getAccessFlagsValue());
    }

    public boolean isStatic() {
        return AccessFlag.STATIC.isSet(getAccessFlagsValue());
    }

    public boolean isSynthetic() {
        return AccessFlag.SYNTHETIC.isSet(getAccessFlagsValue());
    }

    public boolean isAbstract() {
        return AccessFlag.ABSTRACT.isSet(getAccessFlagsValue());
    }

    public boolean hasAccessFlag(AccessFlag accessFlag) {
        return accessFlag.isSet(getAccessFlagsValue());
    }

    public boolean hasAccessFlag(AccessFlag accessFlag, AccessFlag accessFlag2) {
        return hasAccessFlag(accessFlag) && hasAccessFlag(accessFlag2);
    }

    public boolean hasAccessFlag(AccessFlag accessFlag, AccessFlag accessFlag2, AccessFlag accessFlag3) {
        return hasAccessFlag(accessFlag) && hasAccessFlag(accessFlag2) && hasAccessFlag(accessFlag3);
    }

    public Iterator<? extends Modifier> getAccessFlags() {
        return getDefinition().getAccessFlags();
    }

    public void addAccessFlag(AccessFlag accessFlag) {
        getDefinition().addAccessFlag(accessFlag);
    }

    public void removeAccessFlag(AccessFlag accessFlag) {
        getDefinition().removeAccessFlag(accessFlag);
    }

    int getAccessFlagsValue() {
        return getDefinition().getAccessFlagsValue();
    }

    public TypeKey getDefining() {
        return getKey().getDeclaring();
    }

    public DexFile getDexFile() {
        if (getClass() == DexClass.class) {
            throw new RuntimeException("getDexFile() must be override for: " + getClass());
        }
        return getDexClass().getDexFile();
    }

    public DexDirectory getDexDirectory() {
        DexFile dexFile = getDexFile();
        if (dexFile != null) {
            return dexFile.getDexDirectory();
        }
        return null;
    }

    @Override // com.reandroid.dex.model.Dex
    public DexClassRepository getClassRepository() {
        DexFile dexFile = getDexFile();
        if (dexFile != null) {
            return dexFile.getClassRepository();
        }
        return null;
    }

    public String getPackageName() {
        return getDefining().getPackageName();
    }

    public boolean isInSameFile(DexDeclaration dexDeclaration) {
        if (dexDeclaration == null) {
            return false;
        }
        if (dexDeclaration == this) {
            return true;
        }
        DexFile dexFile = getDexFile();
        return dexFile != null && dexFile == dexDeclaration.getDexFile();
    }

    public boolean isInSameFile(DexFile dexFile) {
        return dexFile != null && dexFile.getDexLayout() == getDexFile().getDexLayout();
    }

    public boolean isInSameDirectory(DexDeclaration dexDeclaration) {
        if (dexDeclaration == null) {
            return false;
        }
        if (isInSameFile(dexDeclaration)) {
            return true;
        }
        DexDirectory dexDirectory = getDexDirectory();
        return dexDirectory != null && dexDirectory == dexDeclaration.getDexDirectory();
    }

    public boolean isInSameDirectory(DexDirectory dexDirectory) {
        return getDexDirectory() == dexDirectory;
    }

    public int hashCode() {
        Key key = getKey();
        if (key != null) {
            return key.hashCode();
        }
        return 0;
    }

    @Override // com.reandroid.dex.model.Dex
    public String toString() {
        return Modifier.toString(getAccessFlags()) + getKey();
    }
}
