package org.jetbrains.kotlin.load.java.structure;

import java.util.Collection;
import kotlin.Metadata;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: javaElements.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\bf\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003J\u0012\u0010,\u001a\u0004\u0018\u00010\u00002\u0006\u0010-\u001a\u00020\u0011H&J\b\u0010.\u001a\u00020\u0014H&R\u0018\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0018\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\bR\u0014\u0010\f\u001a\u0004\u0018\u00010\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0018\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\bR\u0012\u0010\u0013\u001a\u00020\u0014X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0015R\u0012\u0010\u0016\u001a\u00020\u0014X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0015R\u0012\u0010\u0017\u001a\u00020\u0014X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0015R\u0012\u0010\u0018\u001a\u00020\u0014X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0015R\u0012\u0010\u0019\u001a\u00020\u0014X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0015R\u0014\u0010\u001a\u001a\u0004\u0018\u00010\u001bX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0018\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b \u0010\bR\u0014\u0010!\u001a\u0004\u0018\u00010\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u0018\u0010$\u001a\b\u0012\u0004\u0012\u00020%0\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b&\u0010\bR\u0018\u0010'\u001a\b\u0012\u0004\u0012\u00020(0\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b)\u0010\bR\u0018\u0010*\u001a\b\u0012\u0004\u0012\u00020%0\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b+\u0010\bø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006/À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifier;", "Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameterListOwner;", "Lorg/jetbrains/kotlin/load/java/structure/JavaModifierListOwner;", "constructors", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaConstructor;", "getConstructors", "()Ljava/util/Collection;", "fields", "Lorg/jetbrains/kotlin/load/java/structure/JavaField;", "getFields", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "innerClassNames", "Lorg/jetbrains/kotlin/name/Name;", "getInnerClassNames", "isAnnotationType", "", "()Z", "isEnum", "isInterface", "isRecord", "isSealed", "lightClassOriginKind", "Lorg/jetbrains/kotlin/load/java/structure/LightClassOriginKind;", "getLightClassOriginKind", "()Lorg/jetbrains/kotlin/load/java/structure/LightClassOriginKind;", "methods", "Lorg/jetbrains/kotlin/load/java/structure/JavaMethod;", "getMethods", "outerClass", "getOuterClass", "()Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "permittedTypes", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifierType;", "getPermittedTypes", "recordComponents", "Lorg/jetbrains/kotlin/load/java/structure/JavaRecordComponent;", "getRecordComponents", "supertypes", "getSupertypes", "findInnerClass", "name", "hasDefaultConstructor", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface JavaClass extends JavaClassifier, JavaModifierListOwner, JavaTypeParameterListOwner {
    JavaClass findInnerClass(Name name2);

    /* JADX INFO: renamed from: getConstructors */
    Collection<JavaConstructor> mo7253getConstructors();

    /* JADX INFO: renamed from: getFields */
    Collection<JavaField> mo7254getFields();

    FqName getFqName();

    Collection<Name> getInnerClassNames();

    LightClassOriginKind getLightClassOriginKind();

    /* JADX INFO: renamed from: getMethods */
    Collection<JavaMethod> mo7255getMethods();

    /* JADX INFO: renamed from: getOuterClass */
    JavaClass mo7256getOuterClass();

    Collection<JavaClassifierType> getPermittedTypes();

    /* JADX INFO: renamed from: getRecordComponents */
    Collection<JavaRecordComponent> mo7257getRecordComponents();

    Collection<JavaClassifierType> getSupertypes();

    /* JADX INFO: renamed from: hasDefaultConstructor */
    boolean mo7259hasDefaultConstructor();

    /* JADX INFO: renamed from: isAnnotationType */
    boolean mo7261isAnnotationType();

    /* JADX INFO: renamed from: isEnum */
    boolean mo7263isEnum();

    /* JADX INFO: renamed from: isInterface */
    boolean mo7265isInterface();

    /* JADX INFO: renamed from: isRecord */
    boolean mo7266isRecord();

    boolean isSealed();
}
