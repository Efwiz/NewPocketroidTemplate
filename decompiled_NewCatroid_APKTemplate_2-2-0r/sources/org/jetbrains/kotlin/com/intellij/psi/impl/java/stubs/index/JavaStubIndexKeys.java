package org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.index;

import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnonymousClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaModule;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubIndexKey;

/* JADX INFO: loaded from: classes3.dex */
public final class JavaStubIndexKeys {
    public static final StubIndexKey<String, PsiAnnotation> ANNOTATIONS = StubIndexKey.createIndexKey("java.annotations");
    public static final StubIndexKey<String, PsiReferenceList> SUPER_CLASSES = StubIndexKey.createIndexKey("java.class.extlist");
    public static final StubIndexKey<String, PsiField> FIELDS = StubIndexKey.createIndexKey("java.field.name");
    public static final StubIndexKey<String, PsiMethod> METHODS = StubIndexKey.createIndexKey("java.method.name");
    public static final StubIndexKey<String, PsiMember> JVM_STATIC_MEMBERS_NAMES = StubIndexKey.createIndexKey("jvm.static.member.name");
    public static final StubIndexKey<String, PsiMember> JVM_STATIC_MEMBERS_TYPES = StubIndexKey.createIndexKey("jvm.static.member.type");
    public static final StubIndexKey<String, PsiAnonymousClass> ANONYMOUS_BASEREF = StubIndexKey.createIndexKey("java.anonymous.baseref");
    public static final StubIndexKey<String, PsiMethod> METHOD_TYPES = StubIndexKey.createIndexKey("java.method.parameter.types");
    public static final StubIndexKey<String, PsiClass> CLASS_SHORT_NAMES = StubIndexKey.createIndexKey("java.class.shortname");
    public static final StubIndexKey<Integer, PsiClass> CLASS_FQN = StubIndexKey.createIndexKey("java.class.fqn");
    public static final StubIndexKey<String, PsiJavaModule> MODULE_NAMES = StubIndexKey.createIndexKey("java.module.name");

    private JavaStubIndexKeys() {
    }
}
