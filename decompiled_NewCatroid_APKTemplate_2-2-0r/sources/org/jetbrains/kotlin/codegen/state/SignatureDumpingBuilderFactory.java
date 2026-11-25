package org.jetbrains.kotlin.codegen.state;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.dex.sections.Marker;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.jetbrains.kotlin.codegen.ClassBuilder;
import org.jetbrains.kotlin.codegen.ClassBuilderFactory;
import org.jetbrains.kotlin.codegen.DelegatingClassBuilder;
import org.jetbrains.kotlin.codegen.DelegatingClassBuilderFactory;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;
import org.jetbrains.kotlin.renderer.DescriptorRendererModifier;
import org.jetbrains.kotlin.renderer.DescriptorRendererOptions;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.MemberKind;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.RawSignature;
import org.jetbrains.org.objectweb.asm.FieldVisitor;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: SignatureDumpingBuilderFactory.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00172\u00020\u0001:\u0002\u0017\u0018B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u000b\u001a\u00020\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000e¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/codegen/state/SignatureDumpingBuilderFactory;", "Lorg/jetbrains/kotlin/codegen/DelegatingClassBuilderFactory;", "builderFactory", "Lorg/jetbrains/kotlin/codegen/ClassBuilderFactory;", FirebaseAnalytics.Param.DESTINATION, "Ljava/io/File;", "(Lorg/jetbrains/kotlin/codegen/ClassBuilderFactory;Ljava/io/File;)V", "getDestination", "()Ljava/io/File;", "firstClassWritten", "", "outputStream", "Ljava/io/BufferedWriter;", "getOutputStream", "()Ljava/io/BufferedWriter;", "outputStream$delegate", "Lkotlin/Lazy;", "close", "", "newClassBuilder", "Lorg/jetbrains/kotlin/codegen/DelegatingClassBuilder;", "origin", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;", "Companion", "SignatureDumpingClassBuilder", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SignatureDumpingBuilderFactory extends DelegatingClassBuilderFactory {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final DescriptorRenderer MEMBER_RENDERER = DescriptorRenderer.INSTANCE.withOptions(new Function1<DescriptorRendererOptions, Unit>() { // from class: org.jetbrains.kotlin.codegen.state.SignatureDumpingBuilderFactory$Companion$MEMBER_RENDERER$1
        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(DescriptorRendererOptions descriptorRendererOptions) {
            invoke2(descriptorRendererOptions);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(DescriptorRendererOptions withOptions) {
            Intrinsics.checkNotNullParameter(withOptions, "$this$withOptions");
            withOptions.setWithDefinedIn(false);
            withOptions.setModifiers(SetsKt.minus(withOptions.getModifiers(), DescriptorRendererModifier.VISIBILITY));
        }
    });
    private static final DescriptorRenderer TYPE_RENDERER = DescriptorRenderer.INSTANCE.withOptions(new Function1<DescriptorRendererOptions, Unit>() { // from class: org.jetbrains.kotlin.codegen.state.SignatureDumpingBuilderFactory$Companion$TYPE_RENDERER$1
        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(DescriptorRendererOptions descriptorRendererOptions) {
            invoke2(descriptorRendererOptions);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(DescriptorRendererOptions withOptions) {
            Intrinsics.checkNotNullParameter(withOptions, "$this$withOptions");
            withOptions.setWithSourceFileForTopLevel(false);
            withOptions.setModifiers(SetsKt.minus(withOptions.getModifiers(), DescriptorRendererModifier.VISIBILITY));
        }
    });
    private final File destination;
    private boolean firstClassWritten;

    /* JADX INFO: renamed from: outputStream$delegate, reason: from kotlin metadata */
    private final Lazy outputStream;

    public final File getDestination() {
        return this.destination;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SignatureDumpingBuilderFactory(ClassBuilderFactory builderFactory, File destination) {
        super(builderFactory);
        Intrinsics.checkNotNullParameter(builderFactory, "builderFactory");
        Intrinsics.checkNotNullParameter(destination, "destination");
        this.destination = destination;
        this.outputStream = LazyKt.lazy(new Function0<BufferedWriter>() { // from class: org.jetbrains.kotlin.codegen.state.SignatureDumpingBuilderFactory$outputStream$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final BufferedWriter invoke() {
                System.out.println((Object) ("[INFO] Dumping signatures to " + this.this$0.getDestination()));
                File parentFile = this.this$0.getDestination().getParentFile();
                if (parentFile != null) {
                    parentFile.mkdirs();
                }
                Writer outputStreamWriter = new OutputStreamWriter(new FileOutputStream(this.this$0.getDestination()), Charsets.UTF_8);
                BufferedWriter bufferedWriter = outputStreamWriter instanceof BufferedWriter ? (BufferedWriter) outputStreamWriter : new BufferedWriter(outputStreamWriter, 8192);
                bufferedWriter.append((CharSequence) "[\n");
                return bufferedWriter;
            }
        });
    }

    /* JADX INFO: compiled from: SignatureDumpingBuilderFactory.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/codegen/state/SignatureDumpingBuilderFactory$Companion;", "", "()V", "MEMBER_RENDERER", "Lorg/jetbrains/kotlin/renderer/DescriptorRenderer;", "getMEMBER_RENDERER", "()Lorg/jetbrains/kotlin/renderer/DescriptorRenderer;", "TYPE_RENDERER", "getTYPE_RENDERER", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final DescriptorRenderer getMEMBER_RENDERER() {
            return SignatureDumpingBuilderFactory.MEMBER_RENDERER;
        }

        public final DescriptorRenderer getTYPE_RENDERER() {
            return SignatureDumpingBuilderFactory.TYPE_RENDERER;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final BufferedWriter getOutputStream() {
        return (BufferedWriter) this.outputStream.getValue();
    }

    @Override // org.jetbrains.kotlin.codegen.DelegatingClassBuilderFactory, org.jetbrains.kotlin.codegen.ClassBuilderFactory
    public void close() throws IOException {
        getOutputStream().append("\n]\n");
        getOutputStream().close();
        super.close();
    }

    @Override // org.jetbrains.kotlin.codegen.ClassBuilderFactory
    public DelegatingClassBuilder newClassBuilder(JvmDeclarationOrigin origin) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        ClassBuilder classBuilderNewClassBuilder = getDelegate().newClassBuilder(origin);
        Intrinsics.checkNotNullExpressionValue(classBuilderNewClassBuilder, "newClassBuilder(...)");
        return new SignatureDumpingClassBuilder(this, origin, classBuilderNewClassBuilder);
    }

    /* JADX INFO: compiled from: SignatureDumpingBuilderFactory.kt */
    @Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006JQ\u0010\u0012\u001a\u00020\u00132\b\u0010\u0002\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\n2\b\u0010\u0019\u001a\u0004\u0018\u00010\n2\u0006\u0010\u001a\u001a\u00020\n2\u000e\u0010\u001b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\u001cH\u0016¢\u0006\u0002\u0010\u001dJ\u0010\u0010\u001e\u001a\u00020\u00132\u0006\u0010\u001f\u001a\u00020 H\u0016J\b\u0010!\u001a\u00020\u0005H\u0014J<\u0010\"\u001a\u00020#2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\n2\u0006\u0010$\u001a\u00020\n2\b\u0010\u0019\u001a\u0004\u0018\u00010\n2\b\u0010%\u001a\u0004\u0018\u00010&H\u0016JI\u0010'\u001a\u00020(2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\n2\u0006\u0010$\u001a\u00020\n2\b\u0010\u0019\u001a\u0004\u0018\u00010\n2\u0010\u0010)\u001a\f\u0012\u0006\b\u0001\u0012\u00020\n\u0018\u00010\u001cH\u0016¢\u0006\u0002\u0010*R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\"\u0010\r\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u000f0\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/codegen/state/SignatureDumpingBuilderFactory$SignatureDumpingClassBuilder;", "Lorg/jetbrains/kotlin/codegen/DelegatingClassBuilder;", "origin", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;", "_delegate", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "(Lorg/jetbrains/kotlin/codegen/state/SignatureDumpingBuilderFactory;Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;Lorg/jetbrains/kotlin/codegen/ClassBuilder;)V", "get_delegate", "()Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "javaClassName", "", "getOrigin", "()Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;", "signatures", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/RawSignature;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "defineClass", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "version", "", "access", "name", "signature", "superName", Namer.METADATA_SUPERTYPES, "", "(Lcom/intellij/psi/PsiElement;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)V", ES6Iterator.DONE_PROPERTY, "generateSmapCopyToAnnotation", "", "getDelegate", "newField", "Lorg/jetbrains/org/objectweb/asm/FieldVisitor;", "desc", "value", "", "newMethod", "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "exceptions", "(Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class SignatureDumpingClassBuilder extends DelegatingClassBuilder {
        private final ClassBuilder _delegate;
        private String javaClassName;
        private final JvmDeclarationOrigin origin;
        private final List<Pair<RawSignature, DeclarationDescriptor>> signatures;
        final /* synthetic */ SignatureDumpingBuilderFactory this$0;

        public SignatureDumpingClassBuilder(SignatureDumpingBuilderFactory signatureDumpingBuilderFactory, JvmDeclarationOrigin origin, ClassBuilder _delegate) {
            Intrinsics.checkNotNullParameter(origin, "origin");
            Intrinsics.checkNotNullParameter(_delegate, "_delegate");
            this.this$0 = signatureDumpingBuilderFactory;
            this.origin = origin;
            this._delegate = _delegate;
            this.signatures = new ArrayList();
        }

        public final JvmDeclarationOrigin getOrigin() {
            return this.origin;
        }

        public final ClassBuilder get_delegate() {
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
            this.javaClassName = name2;
            super.defineClass(psiElement, i, i2, name2, str, superName, interfaces);
        }

        @Override // org.jetbrains.kotlin.codegen.DelegatingClassBuilder, org.jetbrains.kotlin.codegen.ClassBuilder
        public MethodVisitor newMethod(JvmDeclarationOrigin origin, int access, String name2, String desc, String signature, String[] exceptions) {
            Intrinsics.checkNotNullParameter(origin, "origin");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(desc, "desc");
            List<Pair<RawSignature, DeclarationDescriptor>> list = this.signatures;
            RawSignature rawSignature = new RawSignature(name2, desc, MemberKind.METHOD);
            CallableDescriptor descriptor = origin.getDescriptor();
            if (descriptor == null) {
                descriptor = null;
            } else if (descriptor instanceof CallableDescriptor) {
                descriptor = CoroutineCodegenUtilKt.unwrapInitialDescriptorForSuspendFunction((CallableDescriptor) descriptor);
            }
            list.add(TuplesKt.to(rawSignature, descriptor));
            MethodVisitor methodVisitorNewMethod = super.newMethod(origin, access, name2, desc, signature, exceptions);
            Intrinsics.checkNotNullExpressionValue(methodVisitorNewMethod, "newMethod(...)");
            return methodVisitorNewMethod;
        }

        @Override // org.jetbrains.kotlin.codegen.DelegatingClassBuilder, org.jetbrains.kotlin.codegen.ClassBuilder
        public FieldVisitor newField(JvmDeclarationOrigin origin, int access, String name2, String desc, String signature, Object value) {
            Intrinsics.checkNotNullParameter(origin, "origin");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(desc, "desc");
            this.signatures.add(TuplesKt.to(new RawSignature(name2, desc, MemberKind.FIELD), origin.getDescriptor()));
            FieldVisitor fieldVisitorNewField = super.newField(origin, access, name2, desc, signature, value);
            Intrinsics.checkNotNullExpressionValue(fieldVisitorNewField, "newField(...)");
            return fieldVisitorNewField;
        }

        @Override // org.jetbrains.kotlin.codegen.DelegatingClassBuilder, org.jetbrains.kotlin.codegen.ClassBuilder
        public void done(boolean generateSmapCopyToAnnotation) throws IOException {
            DescriptorVisibility visibility;
            if (this.this$0.firstClassWritten) {
                this.this$0.getOutputStream().append((CharSequence) ",\n");
            } else {
                this.this$0.firstClassWritten = true;
            }
            this.this$0.getOutputStream().append((CharSequence) "\t{\n");
            DeclarationDescriptor descriptor = this.origin.getDescriptor();
            String str = null;
            if (descriptor != null) {
                SignatureDumpingBuilderFactory signatureDumpingBuilderFactory = this.this$0;
                Writer writerAppend = signatureDumpingBuilderFactory.getOutputStream().append((CharSequence) "\t\t");
                Intrinsics.checkNotNullExpressionValue(writerAppend, "append(...)");
                SignatureDumpingBuilderFactoryKt.appendNameValue(writerAppend, "declaration", SignatureDumpingBuilderFactory.INSTANCE.getTYPE_RENDERER().render(descriptor)).append(",\n");
                DeclarationDescriptorWithVisibility declarationDescriptorWithVisibility = descriptor instanceof DeclarationDescriptorWithVisibility ? (DeclarationDescriptorWithVisibility) descriptor : null;
                if (declarationDescriptorWithVisibility != null && (visibility = declarationDescriptorWithVisibility.getVisibilityImpl()) != null) {
                    Writer writerAppend2 = signatureDumpingBuilderFactory.getOutputStream().append((CharSequence) "\t\t");
                    Intrinsics.checkNotNullExpressionValue(writerAppend2, "append(...)");
                    SignatureDumpingBuilderFactoryKt.appendNameValue(writerAppend2, "visibility", visibility.getInternalDisplayName()).append(",\n");
                }
            }
            Writer writerAppend3 = this.this$0.getOutputStream().append((CharSequence) "\t\t");
            Intrinsics.checkNotNullExpressionValue(writerAppend3, "append(...)");
            Writer writer = writerAppend3;
            String str2 = this.javaClassName;
            if (str2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("javaClassName");
            } else {
                str = str2;
            }
            SignatureDumpingBuilderFactoryKt.appendNameValue(writer, "class", str).append(",\n");
            Writer writerAppend4 = this.this$0.getOutputStream().append((CharSequence) "\t\t");
            Intrinsics.checkNotNullExpressionValue(writerAppend4, "append(...)");
            SignatureDumpingBuilderFactoryKt.appendQuoted(writerAppend4, "members").append(": [\n");
            CollectionsKt.joinTo(this.signatures, this.this$0.getOutputStream(), (112 & 2) != 0 ? ", " : ",\n", (112 & 4) != 0 ? "" : null, (112 & 8) != 0 ? "" : null, (112 & 16) != 0 ? -1 : 0, (112 & 32) != 0 ? "..." : null, (112 & 64) != 0 ? null : new Function1<Pair<? extends RawSignature, ? extends DeclarationDescriptor>, CharSequence>() { // from class: org.jetbrains.kotlin.codegen.state.SignatureDumpingBuilderFactory$SignatureDumpingClassBuilder$done$2
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final CharSequence invoke2(Pair<RawSignature, ? extends DeclarationDescriptor> it) throws IOException {
                    DescriptorVisibility visibilityImpl;
                    Intrinsics.checkNotNullParameter(it, "it");
                    StringBuilder sb = new StringBuilder();
                    RawSignature rawSignatureComponent1 = it.component1();
                    DeclarationDescriptor declarationDescriptorComponent2 = it.component2();
                    sb.append("\t\t\t{");
                    if (declarationDescriptorComponent2 != null) {
                        DeclarationDescriptorWithVisibility declarationDescriptorWithVisibility2 = declarationDescriptorComponent2 instanceof DeclarationDescriptorWithVisibility ? (DeclarationDescriptorWithVisibility) declarationDescriptorComponent2 : null;
                        if (declarationDescriptorWithVisibility2 != null && (visibilityImpl = declarationDescriptorWithVisibility2.getVisibilityImpl()) != null) {
                            SignatureDumpingBuilderFactoryKt.appendNameValue(sb, "visibility", visibilityImpl.getInternalDisplayName()).append(",\t");
                        }
                        SignatureDumpingBuilderFactoryKt.appendNameValue(sb, "declaration", SignatureDumpingBuilderFactory.INSTANCE.getMEMBER_RENDERER().render(declarationDescriptorComponent2)).append(", ");
                    }
                    StringBuilder sb2 = sb;
                    SignatureDumpingBuilderFactoryKt.appendNameValue(sb2, "name", rawSignatureComponent1.getName()).append(", ");
                    SignatureDumpingBuilderFactoryKt.appendNameValue(sb2, "desc", rawSignatureComponent1.getDesc()).append("}");
                    String string = sb.toString();
                    Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                    return string;
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ CharSequence invoke(Pair<? extends RawSignature, ? extends DeclarationDescriptor> pair) {
                    return invoke2((Pair<RawSignature, ? extends DeclarationDescriptor>) pair);
                }
            });
            this.this$0.getOutputStream().append((CharSequence) "\n\t\t]\n\t}");
            super.done(generateSmapCopyToAnnotation);
        }
    }
}
