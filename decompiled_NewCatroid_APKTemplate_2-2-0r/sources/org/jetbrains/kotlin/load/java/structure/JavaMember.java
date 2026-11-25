package org.jetbrains.kotlin.load.java.structure;

import kotlin.Metadata;

/* JADX INFO: compiled from: javaElements.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003R\u0012\u0010\u0004\u001a\u00020\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/JavaMember;", "Lorg/jetbrains/kotlin/load/java/structure/JavaModifierListOwner;", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationOwner;", "Lorg/jetbrains/kotlin/load/java/structure/JavaNamedElement;", "containingClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "getContainingClass", "()Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface JavaMember extends JavaAnnotationOwner, JavaModifierListOwner, JavaNamedElement {
    /* JADX INFO: renamed from: getContainingClass */
    JavaClass mo7272getContainingClass();
}
