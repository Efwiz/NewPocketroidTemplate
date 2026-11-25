package com.reandroid.dex.key;

import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.collection.CombiningIterator;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

/* JADX INFO: loaded from: classes11.dex */
public class MethodHandleKey implements Key {

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    private final MethodKey f242id;
    private final MethodKey member;

    public MethodHandleKey(MethodKey methodKey, MethodKey methodKey2) {
        this.f242id = methodKey;
        this.member = methodKey2;
    }

    public MethodKey getId() {
        return this.f242id;
    }

    public MethodKey getMember() {
        return this.member;
    }

    @Override // com.reandroid.dex.key.Key
    public TypeKey getDeclaring() {
        return getId().getDeclaring();
    }

    @Override // com.reandroid.dex.key.Key
    public Iterator<? extends Key> mentionedKeys() {
        return CombiningIterator.two(getId().mentionedKeys(), getMember().mentionedKeys());
    }

    @Override // com.reandroid.dex.key.Key
    public Key replaceKey(Key key, Key key2) {
        if (key.equals(this)) {
            return key2;
        }
        MethodKey methodKey = (MethodKey) getId().replaceKey(key, key2);
        MethodKey methodKey2 = (MethodKey) getMember().replaceKey(key, key2);
        return (methodKey == getId() && methodKey2 == getMember()) ? this : new MethodHandleKey(methodKey, methodKey2);
    }

    @Override // com.reandroid.dex.key.Key, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        getId().append(smaliWriter);
        smaliWriter.append(", ");
        getMember().append(smaliWriter);
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        if (obj == null) {
            return -1;
        }
        MethodHandleKey methodHandleKey = (MethodHandleKey) obj;
        int iCompare = CompareUtil.compare(getId(), methodHandleKey.getId());
        return iCompare != 0 ? iCompare : CompareUtil.compare(getMember(), methodHandleKey.getMember());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MethodHandleKey)) {
            return false;
        }
        MethodHandleKey methodHandleKey = (MethodHandleKey) obj;
        return Objects.equals(getId(), methodHandleKey.getId()) && Objects.equals(getMember(), methodHandleKey.getMember());
    }

    public int hashCode() {
        return getId().hashCode() + (getMember().hashCode() * 31);
    }

    public String toString() {
        return getId() + ", " + getMember();
    }
}
