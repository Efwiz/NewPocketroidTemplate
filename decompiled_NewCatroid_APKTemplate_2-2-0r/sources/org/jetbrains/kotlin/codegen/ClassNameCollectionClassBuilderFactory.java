package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: ClassNameCollectionClassBuilderFactory.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001:\u0001\rB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH$J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/codegen/ClassNameCollectionClassBuilderFactory;", "Lorg/jetbrains/kotlin/codegen/DelegatingClassBuilderFactory;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/codegen/ClassBuilderFactory;", "(Lorg/jetbrains/kotlin/codegen/ClassBuilderFactory;)V", "handleClashingNames", "", "internalName", "", "origin", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;", "newClassBuilder", "Lorg/jetbrains/kotlin/codegen/DelegatingClassBuilder;", "ClassNameCollectionClassBuilder", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ClassNameCollectionClassBuilderFactory extends DelegatingClassBuilderFactory {
    protected abstract void handleClashingNames(String internalName, JvmDeclarationOrigin origin);

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClassNameCollectionClassBuilderFactory(ClassBuilderFactory delegate) {
        super(delegate);
        Intrinsics.checkNotNullParameter(delegate, "delegate");
    }

    @Override // org.jetbrains.kotlin.codegen.ClassBuilderFactory
    public DelegatingClassBuilder newClassBuilder(JvmDeclarationOrigin origin) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        ClassBuilder classBuilderNewClassBuilder = getDelegate().newClassBuilder(origin);
        Intrinsics.checkNotNullExpressionValue(classBuilderNewClassBuilder, "newClassBuilder(...)");
        return new ClassNameCollectionClassBuilder(this, origin, classBuilderNewClassBuilder);
    }

    /* JADX INFO: compiled from: ClassNameCollectionClassBuilderFactory.kt */
    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006JQ\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\n2\b\u0010\u0013\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0014\u001a\u00020\n2\u000e\u0010\u0015\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\u0016H\u0016¢\u0006\u0002\u0010\u0017J\u0010\u0010\u0018\u001a\u00020\f2\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u0005H\u0014R\u0014\u0010\u0004\u001a\u00020\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/codegen/ClassNameCollectionClassBuilderFactory$ClassNameCollectionClassBuilder;", "Lorg/jetbrains/kotlin/codegen/DelegatingClassBuilder;", "classCreatedFor", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;", "_delegate", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "(Lorg/jetbrains/kotlin/codegen/ClassNameCollectionClassBuilderFactory;Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;Lorg/jetbrains/kotlin/codegen/ClassBuilder;)V", "get_delegate$backend", "()Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "classInternalName", "", "defineClass", "", "origin", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "version", "", "access", "name", "signature", "superName", Namer.METADATA_SUPERTYPES, "", "(Lcom/intellij/psi/PsiElement;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)V", ES6Iterator.DONE_PROPERTY, "generateSmapCopyToAnnotation", "", "getDelegate", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class ClassNameCollectionClassBuilder extends DelegatingClassBuilder {
        private final ClassBuilder _delegate;
        private final JvmDeclarationOrigin classCreatedFor;
        private String classInternalName;
        final /* synthetic */ ClassNameCollectionClassBuilderFactory this$0;

        public ClassNameCollectionClassBuilder(ClassNameCollectionClassBuilderFactory classNameCollectionClassBuilderFactory, JvmDeclarationOrigin classCreatedFor, ClassBuilder _delegate) {
            Intrinsics.checkNotNullParameter(classCreatedFor, "classCreatedFor");
            Intrinsics.checkNotNullParameter(_delegate, "_delegate");
            this.this$0 = classNameCollectionClassBuilderFactory;
            this.classCreatedFor = classCreatedFor;
            this._delegate = _delegate;
        }

        public final ClassBuilder get_delegate$backend() {
            return this._delegate;
        }

        @Override // org.jetbrains.kotlin.codegen.DelegatingClassBuilder
        /* JADX INFO: renamed from: getDelegate, reason: from getter */
        protected ClassBuilder get_delegate() {
            return this._delegate;
        }

        @Override // org.jetbrains.kotlin.codegen.DelegatingClassBuilder, org.jetbrains.kotlin.codegen.ClassBuilder
        public void defineClass(PsiElement psiElement, int i, int i2, String name2, String str, String superName, String[] interfaces) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(superName, "superName");
            Intrinsics.checkNotNullParameter(interfaces, "interfaces");
            this.classInternalName = name2;
            super.defineClass(psiElement, i, i2, name2, str, superName, interfaces);
        }

        @Override // org.jetbrains.kotlin.codegen.DelegatingClassBuilder, org.jetbrains.kotlin.codegen.ClassBuilder
        public void done(boolean generateSmapCopyToAnnotation) {
            ClassNameCollectionClassBuilderFactory classNameCollectionClassBuilderFactory = this.this$0;
            String str = this.classInternalName;
            if (str == null) {
                Intrinsics.throwUninitializedPropertyAccessException("classInternalName");
                str = null;
            }
            classNameCollectionClassBuilderFactory.handleClashingNames(str, this.classCreatedFor);
            super.done(generateSmapCopyToAnnotation);
        }
    }
}
