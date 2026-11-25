package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.containers.MultiMap;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.ConflictingJvmDeclarationsData;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.MemberKind;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.RawSignature;
import org.jetbrains.org.objectweb.asm.FieldVisitor;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: SignatureCollectingClassBuilderFactory.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001:\u0001\u0019B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\u0002\u0010\bJ\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH$J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0006H\u0016J,\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u00152\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00060\u0017H$R\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/codegen/SignatureCollectingClassBuilderFactory;", "Lorg/jetbrains/kotlin/codegen/DelegatingClassBuilderFactory;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/codegen/ClassBuilderFactory;", "shouldGenerate", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;", "", "(Lorg/jetbrains/kotlin/codegen/ClassBuilderFactory;Lkotlin/jvm/functions/Function1;)V", "getShouldGenerate", "()Lkotlin/jvm/functions/Function1;", "handleClashingSignatures", "", "data", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/ConflictingJvmDeclarationsData;", "newClassBuilder", "Lorg/jetbrains/kotlin/codegen/DelegatingClassBuilder;", "origin", "onClassDone", "classOrigin", "classInternalName", "", "signatures", "Lorg/jetbrains/kotlin/com/intellij/util/containers/MultiMap;", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/RawSignature;", "SignatureCollectingClassBuilder", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class SignatureCollectingClassBuilderFactory extends DelegatingClassBuilderFactory {
    private final Function1<JvmDeclarationOrigin, Boolean> shouldGenerate;

    protected abstract void handleClashingSignatures(ConflictingJvmDeclarationsData data);

    protected abstract void onClassDone(JvmDeclarationOrigin classOrigin, String classInternalName, MultiMap<RawSignature, JvmDeclarationOrigin> signatures);

    public final Function1<JvmDeclarationOrigin, Boolean> getShouldGenerate() {
        return this.shouldGenerate;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public SignatureCollectingClassBuilderFactory(ClassBuilderFactory delegate, Function1<? super JvmDeclarationOrigin, Boolean> shouldGenerate) {
        super(delegate);
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        Intrinsics.checkNotNullParameter(shouldGenerate, "shouldGenerate");
        this.shouldGenerate = shouldGenerate;
    }

    @Override // org.jetbrains.kotlin.codegen.ClassBuilderFactory
    public DelegatingClassBuilder newClassBuilder(JvmDeclarationOrigin origin) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        ClassBuilder classBuilderNewClassBuilder = getDelegate().newClassBuilder(origin);
        Intrinsics.checkNotNullExpressionValue(classBuilderNewClassBuilder, "newClassBuilder(...)");
        return new SignatureCollectingClassBuilder(this, origin, classBuilderNewClassBuilder);
    }

    /* JADX INFO: compiled from: SignatureCollectingClassBuilderFactory.kt */
    @Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006JQ\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\n2\b\u0010\u0018\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0019\u001a\u00020\n2\u000e\u0010\u001a\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\u001bH\u0016¢\u0006\u0002\u0010\u001cJ\u0010\u0010\u001d\u001a\u00020\u00112\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\b\u0010 \u001a\u00020\u0005H\u0014J<\u0010!\u001a\u00020\"2\u0006\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\n2\u0006\u0010#\u001a\u00020\n2\b\u0010\u0018\u001a\u0004\u0018\u00010\n2\b\u0010$\u001a\u0004\u0018\u00010%H\u0016JI\u0010&\u001a\u00020'2\u0006\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\n2\u0006\u0010#\u001a\u00020\n2\b\u0010\u0018\u001a\u0004\u0018\u00010\n2\u0010\u0010(\u001a\f\u0012\u0006\b\u0001\u0012\u00020\n\u0018\u00010\u001bH\u0016¢\u0006\u0002\u0010)R\u0014\u0010\u0004\u001a\u00020\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R/\u0010\u000b\u001a#\u0012\f\u0012\n \u000e*\u0004\u0018\u00010\r0\r\u0012\f\u0012\n \u000e*\u0004\u0018\u00010\u00030\u00030\f¢\u0006\u0002\b\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/codegen/SignatureCollectingClassBuilderFactory$SignatureCollectingClassBuilder;", "Lorg/jetbrains/kotlin/codegen/DelegatingClassBuilder;", "classCreatedFor", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;", "_delegate", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "(Lorg/jetbrains/kotlin/codegen/SignatureCollectingClassBuilderFactory;Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;Lorg/jetbrains/kotlin/codegen/ClassBuilder;)V", "get_delegate$backend", "()Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "classInternalName", "", "signatures", "Lorg/jetbrains/kotlin/com/intellij/util/containers/MultiMap;", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/RawSignature;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "Lorg/jetbrains/annotations/NotNull;", "defineClass", "", "origin", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "version", "", "access", "name", "signature", "superName", Namer.METADATA_SUPERTYPES, "", "(Lcom/intellij/psi/PsiElement;IILjava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)V", ES6Iterator.DONE_PROPERTY, "generateSmapCopyToAnnotation", "", "getDelegate", "newField", "Lorg/jetbrains/org/objectweb/asm/FieldVisitor;", "desc", "value", "", "newMethod", "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "exceptions", "(Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class SignatureCollectingClassBuilder extends DelegatingClassBuilder {
        private final ClassBuilder _delegate;
        private final JvmDeclarationOrigin classCreatedFor;
        private String classInternalName;
        private final MultiMap<RawSignature, JvmDeclarationOrigin> signatures;
        final /* synthetic */ SignatureCollectingClassBuilderFactory this$0;

        public SignatureCollectingClassBuilder(SignatureCollectingClassBuilderFactory signatureCollectingClassBuilderFactory, JvmDeclarationOrigin classCreatedFor, ClassBuilder _delegate) {
            Intrinsics.checkNotNullParameter(classCreatedFor, "classCreatedFor");
            Intrinsics.checkNotNullParameter(_delegate, "_delegate");
            this.this$0 = signatureCollectingClassBuilderFactory;
            this.classCreatedFor = classCreatedFor;
            this._delegate = _delegate;
            MultiMap<RawSignature, JvmDeclarationOrigin> multiMapCreateLinked = MultiMap.createLinked();
            Intrinsics.checkNotNullExpressionValue(multiMapCreateLinked, "createLinked(...)");
            this.signatures = multiMapCreateLinked;
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
        public FieldVisitor newField(JvmDeclarationOrigin origin, int access, String name2, String desc, String signature, Object value) {
            Intrinsics.checkNotNullParameter(origin, "origin");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(desc, "desc");
            this.signatures.putValue(new RawSignature(name2, desc, MemberKind.FIELD), origin);
            if (!this.this$0.getShouldGenerate().invoke(origin).booleanValue()) {
                FieldVisitor EMPTY_FIELD_VISITOR = AbstractClassBuilder.EMPTY_FIELD_VISITOR;
                Intrinsics.checkNotNullExpressionValue(EMPTY_FIELD_VISITOR, "EMPTY_FIELD_VISITOR");
                return EMPTY_FIELD_VISITOR;
            }
            FieldVisitor fieldVisitorNewField = super.newField(origin, access, name2, desc, signature, value);
            Intrinsics.checkNotNullExpressionValue(fieldVisitorNewField, "newField(...)");
            return fieldVisitorNewField;
        }

        @Override // org.jetbrains.kotlin.codegen.DelegatingClassBuilder, org.jetbrains.kotlin.codegen.ClassBuilder
        public MethodVisitor newMethod(JvmDeclarationOrigin origin, int access, String name2, String desc, String signature, String[] exceptions) {
            Intrinsics.checkNotNullParameter(origin, "origin");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(desc, "desc");
            this.signatures.putValue(new RawSignature(name2, desc, MemberKind.METHOD), origin);
            if (!this.this$0.getShouldGenerate().invoke(origin).booleanValue()) {
                MethodVisitor EMPTY_METHOD_VISITOR = AbstractClassBuilder.EMPTY_METHOD_VISITOR;
                Intrinsics.checkNotNullExpressionValue(EMPTY_METHOD_VISITOR, "EMPTY_METHOD_VISITOR");
                return EMPTY_METHOD_VISITOR;
            }
            MethodVisitor methodVisitorNewMethod = super.newMethod(origin, access, name2, desc, signature, exceptions);
            Intrinsics.checkNotNullExpressionValue(methodVisitorNewMethod, "newMethod(...)");
            return methodVisitorNewMethod;
        }

        @Override // org.jetbrains.kotlin.codegen.DelegatingClassBuilder, org.jetbrains.kotlin.codegen.ClassBuilder
        public void done(boolean generateSmapCopyToAnnotation) {
            String str;
            String str2;
            Iterator<Map.Entry<RawSignature, Collection<JvmDeclarationOrigin>>> it = this.signatures.entrySet().iterator();
            while (true) {
                str = null;
                if (!it.hasNext()) {
                    break;
                }
                Map.Entry<RawSignature, Collection<JvmDeclarationOrigin>> next = it.next();
                Intrinsics.checkNotNull(next);
                RawSignature key = next.getKey();
                Collection<JvmDeclarationOrigin> value = next.getValue();
                if (value.size() != 1) {
                    SignatureCollectingClassBuilderFactory signatureCollectingClassBuilderFactory = this.this$0;
                    String str3 = this.classInternalName;
                    if (str3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("classInternalName");
                        str2 = null;
                    } else {
                        str2 = str3;
                    }
                    JvmDeclarationOrigin jvmDeclarationOrigin = this.classCreatedFor;
                    Intrinsics.checkNotNull(key);
                    Intrinsics.checkNotNull(value);
                    ArrayList arrayList = new ArrayList();
                    Iterator<T> it2 = value.iterator();
                    while (it2.hasNext()) {
                        DeclarationDescriptor descriptor = ((JvmDeclarationOrigin) it2.next()).getDescriptor();
                        if (descriptor != null) {
                            arrayList.add(descriptor);
                        }
                    }
                    signatureCollectingClassBuilderFactory.handleClashingSignatures(new ConflictingJvmDeclarationsData(str2, jvmDeclarationOrigin, key, value, arrayList));
                }
            }
            SignatureCollectingClassBuilderFactory signatureCollectingClassBuilderFactory2 = this.this$0;
            JvmDeclarationOrigin jvmDeclarationOrigin2 = this.classCreatedFor;
            String str4 = this.classInternalName;
            if (str4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("classInternalName");
            } else {
                str = str4;
            }
            signatureCollectingClassBuilderFactory2.onClassDone(jvmDeclarationOrigin2, str, this.signatures);
            super.done(generateSmapCopyToAnnotation);
        }
    }
}
