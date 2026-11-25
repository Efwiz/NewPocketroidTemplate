package org.jetbrains.kotlin.incremental.classpathDiff;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.org.objectweb.asm.ClassVisitor;

/* JADX INFO: compiled from: BasicClassInfo.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u0006\u0010\u000b\u001a\u00020\u0005J\u0006\u0010\f\u001a\u00020\bJ\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eJI\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\b2\b\u0010\u0015\u001a\u0004\u0018\u00010\b2\b\u0010\u0016\u001a\u0004\u0018\u00010\b2\u000e\u0010\u0017\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u0018H\u0016¢\u0006\u0002\u0010\u0019R\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0006R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/incremental/classpathDiff/BasicClassInfoClassVisitor;", "Lorg/jetbrains/org/objectweb/asm/ClassVisitor;", "cv", "(Lorg/jetbrains/org/objectweb/asm/ClassVisitor;)V", "classAccess", "", "Ljava/lang/Integer;", "className", "", "supertypeNames", "", "getAccessFlags", "getClassName", "getSupertypes", "", "Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", "visit", "", "version", "access", "name", "signature", "superName", Namer.METADATA_SUPERTYPES, "", "(IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)V", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class BasicClassInfoClassVisitor extends ClassVisitor {
    private Integer classAccess;
    private String className;
    private final List<String> supertypeNames;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BasicClassInfoClassVisitor(ClassVisitor cv) {
        super(589824, cv);
        Intrinsics.checkNotNullParameter(cv, "cv");
        this.supertypeNames = new ArrayList();
    }

    @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
    public void visit(int version, int access, String name2, String signature, String superName, String[] interfaces) {
        Intrinsics.checkNotNullParameter(name2, "name");
        this.className = name2;
        this.classAccess = Integer.valueOf(access);
        if (superName != null) {
            this.supertypeNames.add(superName);
        }
        if (interfaces != null) {
            CollectionsKt.addAll(this.supertypeNames, interfaces);
        }
        super.visit(version, access, name2, signature, superName, interfaces);
    }

    public final String getClassName() {
        String str = this.className;
        Intrinsics.checkNotNull(str);
        return str;
    }

    public final int getAccessFlags() {
        Integer num = this.classAccess;
        Intrinsics.checkNotNull(num);
        return num.intValue();
    }

    public final List<JvmClassName> getSupertypes() {
        List<String> list = this.supertypeNames;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(JvmClassName.byInternalName((String) it.next()));
        }
        return arrayList;
    }
}
