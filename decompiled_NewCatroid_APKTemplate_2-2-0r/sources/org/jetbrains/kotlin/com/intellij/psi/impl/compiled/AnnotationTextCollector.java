package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import org.jetbrains.kotlin.com.intellij.util.Consumer;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: loaded from: classes3.dex */
final class AnnotationTextCollector extends AnnotationVisitor {
    private boolean hasParams;
    private boolean hasPrefix;
    private final StringBuilder myBuilder;
    private final Consumer<? super String> myCallback;
    private final Function<? super String, String> myMapping;

    AnnotationTextCollector(String str, Function<? super String, String> function, Consumer<? super String> consumer) {
        super(589824);
        StringBuilder sb = new StringBuilder();
        this.myBuilder = sb;
        this.myMapping = function;
        this.myCallback = consumer;
        if (str != null) {
            this.hasPrefix = true;
            sb.append('@').append(StubBuildingVisitor.toJavaType(Type.getType(str), function));
        }
    }

    @Override // org.jetbrains.org.objectweb.asm.AnnotationVisitor
    public void visit(String str, Object obj) {
        valuePairPrefix(str);
        this.myBuilder.append(StubBuildingVisitor.constToString(obj, null, true, this.myMapping));
    }

    @Override // org.jetbrains.org.objectweb.asm.AnnotationVisitor
    public void visitEnum(String str, String str2, String str3) {
        valuePairPrefix(str);
        this.myBuilder.append(StubBuildingVisitor.toJavaType(Type.getType(str2), this.myMapping)).append('.').append(str3);
    }

    private void valuePairPrefix(String str) {
        if (!this.hasParams) {
            this.hasParams = true;
            if (this.hasPrefix) {
                this.myBuilder.append('(');
            }
        } else {
            this.myBuilder.append(',');
        }
        if (str != null) {
            this.myBuilder.append(str).append('=');
        }
    }

    @Override // org.jetbrains.org.objectweb.asm.AnnotationVisitor
    public AnnotationVisitor visitAnnotation(String str, String str2) {
        valuePairPrefix(str);
        return new AnnotationTextCollector(str2, this.myMapping, new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.AnnotationTextCollector$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.Consumer
            public final void consume(Object obj) {
                this.f$0.m6580x1ce92b62((String) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$visitAnnotation$0$org-jetbrains-kotlin-com-intellij-psi-impl-compiled-AnnotationTextCollector, reason: not valid java name */
    /* synthetic */ void m6580x1ce92b62(String str) {
        this.myBuilder.append(str);
    }

    @Override // org.jetbrains.org.objectweb.asm.AnnotationVisitor
    public AnnotationVisitor visitArray(String str) {
        valuePairPrefix(str);
        this.myBuilder.append('{');
        return new AnnotationTextCollector(null, this.myMapping, new Consumer() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.AnnotationTextCollector$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Consumer
            public final void consume(Object obj) {
                this.f$0.m6581xdbc6b631((String) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$visitArray$1$org-jetbrains-kotlin-com-intellij-psi-impl-compiled-AnnotationTextCollector, reason: not valid java name */
    /* synthetic */ void m6581xdbc6b631(String str) {
        this.myBuilder.append(str).append('}');
    }

    @Override // org.jetbrains.org.objectweb.asm.AnnotationVisitor
    public void visitEnd() {
        if (this.hasPrefix && this.hasParams) {
            this.myBuilder.append(')');
        }
        this.myCallback.consume(this.myBuilder.toString());
    }
}
