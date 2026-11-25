package org.jf.smali;

import java.util.Comparator;
import java.util.Set;
import org.jf.dexlib2.base.BaseMethodParameter;
import org.jf.dexlib2.iface.Annotation;
import org.jf.util.collection.EmptySet;

/* JADX INFO: loaded from: classes6.dex */
public class SmaliMethodParameter extends BaseMethodParameter implements WithRegister {
    public static final Comparator<WithRegister> COMPARATOR = new Comparator<WithRegister>() { // from class: org.jf.smali.SmaliMethodParameter.1
        @Override // java.util.Comparator
        public int compare(WithRegister withRegister, WithRegister withRegister2) {
            return Integer.compare(withRegister.getRegister(), withRegister2.getRegister());
        }
    };
    public Set<? extends Annotation> annotations = EmptySet.of();

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    public String f1039name;
    public final int register;
    public final String type;

    @Override // org.jf.dexlib2.base.BaseMethodParameter, org.jf.dexlib2.iface.MethodParameter, org.jf.dexlib2.iface.debug.LocalInfo
    public String getSignature() {
        return null;
    }

    public SmaliMethodParameter(int i, String str) {
        this.register = i;
        this.type = str;
    }

    @Override // org.jf.smali.WithRegister
    public int getRegister() {
        return this.register;
    }

    @Override // org.jf.dexlib2.iface.reference.TypeReference, org.jf.dexlib2.iface.ClassDef
    public String getType() {
        return this.type;
    }

    @Override // org.jf.dexlib2.iface.MethodParameter
    public Set<? extends Annotation> getAnnotations() {
        return this.annotations;
    }

    @Override // org.jf.dexlib2.iface.MethodParameter, org.jf.dexlib2.iface.debug.LocalInfo
    public String getName() {
        return this.f1039name;
    }
}
