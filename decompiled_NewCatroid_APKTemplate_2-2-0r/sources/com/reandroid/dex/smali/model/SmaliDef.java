package com.reandroid.dex.smali.model;

import com.reandroid.dex.common.AccessFlag;
import com.reandroid.dex.common.Modifier;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.smali.SmaliDirective;
import com.reandroid.dex.smali.SmaliReader;
import com.reandroid.dex.smali.SmaliRegion;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public abstract class SmaliDef extends Smali implements SmaliRegion {
    private AccessFlag[] accessFlags;
    private SmaliAnnotationSet annotation;
    private TypeKey defining;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f254name;

    public abstract Key getKey();

    public SmaliDirective getSmaliDirective() {
        return null;
    }

    public AccessFlag[] getAccessFlags() {
        return this.accessFlags;
    }

    public void setAccessFlags(AccessFlag[] accessFlagArr) {
        this.accessFlags = accessFlagArr;
    }

    public int getAccessFlagsValue() {
        return Modifier.combineValues(getAccessFlags());
    }

    public String getName() {
        return this.f254name;
    }

    public void setName(String str) {
        this.f254name = str;
    }

    public TypeKey getDefining() {
        SmaliDefSet<?> defSet;
        TypeKey typeKey = this.defining;
        return (typeKey != null || (defSet = getDefSet()) == null) ? typeKey : defSet.getDefining();
    }

    public void setDefining(TypeKey typeKey) {
        this.defining = typeKey;
    }

    public SmaliAnnotationSet getAnnotation() {
        return this.annotation;
    }

    public SmaliAnnotationSet getOrCreateAnnotation() {
        SmaliAnnotationSet annotation = getAnnotation();
        if (annotation != null) {
            return annotation;
        }
        SmaliAnnotationSet smaliAnnotationSet = new SmaliAnnotationSet();
        setAnnotation(smaliAnnotationSet);
        return smaliAnnotationSet;
    }

    public void setAnnotation(SmaliAnnotationSet smaliAnnotationSet) {
        this.annotation = smaliAnnotationSet;
        if (smaliAnnotationSet != null) {
            smaliAnnotationSet.setParent(this);
        }
    }

    public boolean hasAnnotation() {
        SmaliAnnotationSet annotation = getAnnotation();
        return (annotation == null || annotation.isEmpty()) ? false : true;
    }

    public boolean isStatic() {
        return Modifier.contains(getAccessFlags(), AccessFlag.STATIC);
    }

    public boolean isFinal() {
        return Modifier.contains(getAccessFlags(), AccessFlag.FINAL);
    }

    public boolean isPrivate() {
        return Modifier.contains(getAccessFlags(), AccessFlag.PRIVATE);
    }

    private SmaliDefSet<?> getDefSet() {
        return (SmaliDefSet) getParentInstance(SmaliDefSet.class);
    }

    public SmaliClass getSmaliClass() {
        if (getClass().isInstance(SmaliClass.class)) {
            return (SmaliClass) this;
        }
        return (SmaliClass) getParentInstance(SmaliClass.class);
    }

    public SmaliAnnotationItem parseAnnotation(SmaliReader smaliReader) throws IOException {
        return getOrCreateAnnotation().parseNext(smaliReader);
    }
}
