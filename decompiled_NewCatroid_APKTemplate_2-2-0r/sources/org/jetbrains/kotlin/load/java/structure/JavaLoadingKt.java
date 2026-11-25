package org.jetbrains.kotlin.load.java.structure;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: javaLoading.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a\u0010\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a\n\u0010\u0005\u001a\u00020\u0001*\u00020\u0006¨\u0006\u0007"}, d2 = {"isMethodWithOneObjectParameter", "", "method", "Lorg/jetbrains/kotlin/load/java/structure/JavaMethod;", "isObjectMethod", "isObjectMethodInInterface", "Lorg/jetbrains/kotlin/load/java/structure/JavaMember;", "compiler.common.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JavaLoadingKt {
    public static final boolean isObjectMethodInInterface(JavaMember javaMember) {
        Intrinsics.checkNotNullParameter(javaMember, "<this>");
        return javaMember.getContainingClass().mo7265isInterface() && (javaMember instanceof JavaMethod) && isObjectMethod((JavaMethod) javaMember);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0022, code lost:
    
        if (r0.equals("hashCode") == false) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0039, code lost:
    
        if (r0.equals("toString") != false) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:?, code lost:
    
        return r3.getValueParameters().isEmpty();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static final boolean isObjectMethod(org.jetbrains.kotlin.load.java.structure.JavaMethod r3) {
        /*
            org.jetbrains.kotlin.name.Name r0 = r3.getName()
            java.lang.String r0 = r0.asString()
            int r1 = r0.hashCode()
            r2 = -1776922004(0xffffffff9616526c, float:-1.2142911E-25)
            if (r1 == r2) goto L33
            r2 = -1295482945(0xffffffffb2c87fbf, float:-2.3341157E-8)
            if (r1 == r2) goto L25
            r2 = 147696667(0x8cdac1b, float:1.23784505E-33)
            if (r1 == r2) goto L1c
            goto L44
        L1c:
            java.lang.String r1 = "hashCode"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L3b
            goto L44
        L25:
            java.lang.String r1 = "equals"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L2e
            goto L44
        L2e:
            boolean r3 = isMethodWithOneObjectParameter(r3)
            goto L45
        L33:
            java.lang.String r1 = "toString"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L44
        L3b:
            java.util.List r3 = r3.getValueParameters()
            boolean r3 = r3.isEmpty()
            goto L45
        L44:
            r3 = 0
        L45:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jetbrains.kotlin.load.java.structure.JavaLoadingKt.isObjectMethod(org.jetbrains.kotlin.load.java.structure.JavaMethod):boolean");
    }

    private static final boolean isMethodWithOneObjectParameter(JavaMethod javaMethod) {
        FqName fqName;
        JavaValueParameter javaValueParameter = (JavaValueParameter) CollectionsKt.singleOrNull((List) javaMethod.getValueParameters());
        JavaType type = javaValueParameter != null ? javaValueParameter.getType() : null;
        JavaClassifierType javaClassifierType = type instanceof JavaClassifierType ? (JavaClassifierType) type : null;
        if (javaClassifierType == null) {
            return false;
        }
        JavaClassifier classifier = javaClassifierType.getClassifier();
        return (classifier instanceof JavaClass) && (fqName = ((JavaClass) classifier).getFqName()) != null && Intrinsics.areEqual(fqName.asString(), CommonClassNames.JAVA_LANG_OBJECT);
    }
}
