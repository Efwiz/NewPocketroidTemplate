package org.jetbrains.kotlin.backend.jvm.extensions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.codegen.ClassBuilder;
import org.jetbrains.kotlin.codegen.ClassBuilderFactory;
import org.jetbrains.kotlin.codegen.DelegatingClassBuilder;
import org.jetbrains.kotlin.codegen.DelegatingClassBuilderFactory;
import org.jetbrains.kotlin.codegen.extensions.ClassBuilderInterceptorExtension;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;

/* JADX INFO: compiled from: ClassBuilderExtensionAdapter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/extensions/ExtensionAdapter;", "Lorg/jetbrains/kotlin/codegen/extensions/ClassBuilderInterceptorExtension;", SchemaSymbols.ATTVAL_EXTENSION, "Lorg/jetbrains/kotlin/backend/jvm/extensions/ClassGeneratorExtension;", "(Lorg/jetbrains/kotlin/backend/jvm/extensions/ClassGeneratorExtension;)V", "interceptClassBuilderFactory", "Lorg/jetbrains/kotlin/codegen/ClassBuilderFactory;", "interceptedFactory", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "diagnostics", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink;", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ExtensionAdapter implements ClassBuilderInterceptorExtension {
    private final ClassGeneratorExtension extension;

    public ExtensionAdapter(ClassGeneratorExtension extension) {
        Intrinsics.checkNotNullParameter(extension, "extension");
        this.extension = extension;
    }

    @Override // org.jetbrains.kotlin.codegen.extensions.ClassBuilderInterceptorExtension
    public ClassBuilderFactory interceptClassBuilderFactory(ClassBuilderFactory interceptedFactory, BindingContext bindingContext, DiagnosticSink diagnostics) {
        Intrinsics.checkNotNullParameter(interceptedFactory, "interceptedFactory");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
        return new DelegatingClassBuilderFactory(this) { // from class: org.jetbrains.kotlin.backend.jvm.extensions.ExtensionAdapter.interceptClassBuilderFactory.1
            final /* synthetic */ ExtensionAdapter this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(this.$interceptedFactory);
                this.this$0 = this;
            }

            @Override // org.jetbrains.kotlin.codegen.ClassBuilderFactory
            public DelegatingClassBuilder newClassBuilder(JvmDeclarationOrigin origin) {
                Intrinsics.checkNotNullParameter(origin, "origin");
                ClassBuilder classBuilderNewClassBuilder = this.$interceptedFactory.newClassBuilder(origin);
                Intrinsics.checkNotNullExpressionValue(classBuilderNewClassBuilder, "newClassBuilder(...)");
                JvmIrDeclarationOrigin jvmIrDeclarationOrigin = origin instanceof JvmIrDeclarationOrigin ? (JvmIrDeclarationOrigin) origin : null;
                IrDeclaration declaration = jvmIrDeclarationOrigin != null ? jvmIrDeclarationOrigin.getDeclaration() : null;
                IrClass irClass = (IrClass) (declaration instanceof IrClass ? declaration : null);
                return new DelegatingClassBuilderAdapter(this.this$0.extension.generateClass(new ClassGeneratorAdapter(irClass, classBuilderNewClassBuilder), irClass), classBuilderNewClassBuilder);
            }
        };
    }
}
