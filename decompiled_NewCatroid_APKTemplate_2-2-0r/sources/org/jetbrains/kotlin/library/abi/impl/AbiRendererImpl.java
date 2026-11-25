package org.jetbrains.kotlin.library.abi.impl;

import androidx.exifinterface.media.ExifInterface;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.arsc.container.SpecTypePair;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.xml.transform.OutputKeys;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.UInt;
import kotlin.Unit;
import kotlin.UnsignedKt;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.library.abi.AbiClass;
import org.jetbrains.kotlin.library.abi.AbiClassKind;
import org.jetbrains.kotlin.library.abi.AbiClassifierReference;
import org.jetbrains.kotlin.library.abi.AbiCompoundName;
import org.jetbrains.kotlin.library.abi.AbiDeclaration;
import org.jetbrains.kotlin.library.abi.AbiDeclarationContainer;
import org.jetbrains.kotlin.library.abi.AbiDeclarationWithModality;
import org.jetbrains.kotlin.library.abi.AbiEnumEntry;
import org.jetbrains.kotlin.library.abi.AbiFunction;
import org.jetbrains.kotlin.library.abi.AbiModality;
import org.jetbrains.kotlin.library.abi.AbiProperty;
import org.jetbrains.kotlin.library.abi.AbiPropertyKind;
import org.jetbrains.kotlin.library.abi.AbiRenderingSettings;
import org.jetbrains.kotlin.library.abi.AbiSimpleName;
import org.jetbrains.kotlin.library.abi.AbiTopLevelDeclarations;
import org.jetbrains.kotlin.library.abi.AbiType;
import org.jetbrains.kotlin.library.abi.AbiTypeArgument;
import org.jetbrains.kotlin.library.abi.AbiTypeNullability;
import org.jetbrains.kotlin.library.abi.AbiTypeParameter;
import org.jetbrains.kotlin.library.abi.AbiTypeParametersContainer;
import org.jetbrains.kotlin.library.abi.AbiValueParameter;
import org.jetbrains.kotlin.library.abi.AbiVariance;
import org.jetbrains.kotlin.library.abi.ExperimentalLibraryAbiReader;
import org.jetbrains.kotlin.library.abi.LibraryAbi;
import org.jetbrains.kotlin.library.abi.LibraryManifest;

/* JADX INFO: compiled from: LibraryAbiRendererImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u000b\b\u0001\u0018\u0000 \r2\u00020\u0001:\t\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\u0010\u0006\u001a\u00060\u0007j\u0002`\b¢\u0006\u0002\u0010\tJ\b\u0010\n\u001a\u00020\u000bH\u0002J\u0006\u0010\f\u001a\u00020\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0006\u001a\u00060\u0007j\u0002`\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl;", "", "libraryAbi", "Lorg/jetbrains/kotlin/library/abi/LibraryAbi;", "settings", "Lorg/jetbrains/kotlin/library/abi/AbiRenderingSettings;", "output", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "(Lorg/jetbrains/kotlin/library/abi/LibraryAbi;Lorg/jetbrains/kotlin/library/abi/AbiRenderingSettings;Ljava/lang/Appendable;)V", "printHeader", "", "render", "Companion", "Printer", "RenderedClass", "RenderedDeclaration", "RenderedDeclarationContainerKind", "RenderedEnumEntry", "RenderedFunction", "RenderedProperty", "RenderedTopLevelDeclarations", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
@ExperimentalLibraryAbiReader
public final class AbiRendererImpl {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final LibraryAbi libraryAbi;
    private final Appendable output;
    private final AbiRenderingSettings settings;

    public AbiRendererImpl(LibraryAbi libraryAbi, AbiRenderingSettings settings, Appendable output) {
        Intrinsics.checkNotNullParameter(libraryAbi, "libraryAbi");
        Intrinsics.checkNotNullParameter(settings, "settings");
        Intrinsics.checkNotNullParameter(output, "output");
        this.libraryAbi = libraryAbi;
        this.settings = settings;
        this.output = output;
    }

    public final void render() throws IOException {
        printHeader();
        if (this.settings.getRenderDeclarations()) {
            RenderedTopLevelDeclarations.INSTANCE.printNestedDeclarationsInProperOrder(this.libraryAbi.getTopLevelDeclarations(), new Printer(this.output, this.settings));
        }
    }

    private final void printHeader() throws IOException {
        Appendable appendableAppend = this.output.append(StringsKt.trimIndent("\n                // Rendering settings:\n                // - Signature version: " + this.settings.getRenderedSignatureVersion().getVersionNumber() + "\n                // - Show manifest properties: " + this.settings.getRenderManifest() + "\n                // - Show declarations: " + this.settings.getRenderDeclarations() + "\n               \n                // Library unique name: <" + this.libraryAbi.getUniqueName() + ">\n            "));
        Intrinsics.checkNotNullExpressionValue(appendableAppend, "append(...)");
        Intrinsics.checkNotNullExpressionValue(appendableAppend.append('\n'), "append(...)");
        if (this.settings.getRenderManifest()) {
            LibraryManifest manifest = this.libraryAbi.getManifest();
            Pair[] pairArr = new Pair[6];
            String platform = manifest.getPlatform();
            pairArr[0] = platform != null ? TuplesKt.to("Platform", platform) : null;
            List<String> nativeTargets = manifest.getNativeTargets();
            if (nativeTargets.isEmpty()) {
                nativeTargets = null;
            }
            pairArr[1] = nativeTargets != null ? TuplesKt.to("Native targets", CollectionsKt.joinToString$default(nativeTargets, ", ", null, null, 0, null, null, 62, null)) : null;
            String compilerVersion = manifest.getCompilerVersion();
            pairArr[2] = compilerVersion != null ? TuplesKt.to("Compiler version", compilerVersion) : null;
            String abiVersion = manifest.getAbiVersion();
            pairArr[3] = abiVersion != null ? TuplesKt.to("ABI version", abiVersion) : null;
            String libraryVersion = manifest.getLibraryVersion();
            pairArr[4] = libraryVersion != null ? TuplesKt.to("Library version", libraryVersion) : null;
            String irProviderName = manifest.getIrProviderName();
            pairArr[5] = irProviderName != null ? TuplesKt.to("IR provider", irProviderName) : null;
            for (Pair pair : CollectionsKt.listOfNotNull((Object[]) pairArr)) {
                String str = (String) pair.component1();
                String str2 = (String) pair.component2();
                Appendable appendableAppend2 = this.output.append("// ").append(str).append(": ");
                Intrinsics.checkNotNullExpressionValue(appendableAppend2, "append(...)");
                Appendable appendableAppend3 = appendableAppend2.append(str2);
                Intrinsics.checkNotNullExpressionValue(appendableAppend3, "append(...)");
                Intrinsics.checkNotNullExpressionValue(appendableAppend3.append('\n'), "append(...)");
            }
        }
    }

    /* JADX INFO: compiled from: LibraryAbiRendererImpl.kt */
    @Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\u000b\u001a\u00020\fH\u0002J\u0010\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u001a\u0010\u0010\u001a\u00020\f2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\f0\u0012H\u0086\bø\u0001\u0002J\u0006\u0010\u0013\u001a\u00020\fJ\u001c\u0010\u0014\u001a\u00020\f2\n\u0010\u0015\u001a\u0006\u0012\u0002\b\u00030\u00162\b\b\u0002\u0010\u0017\u001a\u00020\u0018R\u0016\u0010\b\u001a\u00020\tX\u0082\u000eø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\nR\u0012\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0012\n\u0005\b¡\u001e0\u0001\n\u0002\b!\n\u0005\b\u009920\u0001¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$Printer;", "", "output", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", "settings", "Lorg/jetbrains/kotlin/library/abi/AbiRenderingSettings;", "(Ljava/lang/Appendable;Lorg/jetbrains/kotlin/library/abi/AbiRenderingSettings;)V", OutputKeys.INDENT, "Lkotlin/UInt;", "I", "appendIndent", "", "appendSignature", "declaration", "Lorg/jetbrains/kotlin/library/abi/AbiDeclaration;", "indented", "block", "Lkotlin/Function0;", "printClosingBrace", "printDeclaration", "renderedDeclaration", "Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$RenderedDeclaration;", "printOpeningBrace", "", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Printer {
        private int indent;
        private final Appendable output;
        private final AbiRenderingSettings settings;

        public Printer(Appendable output, AbiRenderingSettings settings) {
            Intrinsics.checkNotNullParameter(output, "output");
            Intrinsics.checkNotNullParameter(settings, "settings");
            this.output = output;
            this.settings = settings;
        }

        public final void indented(Function0<Unit> block) {
            Intrinsics.checkNotNullParameter(block, "block");
            this.indent = UInt.m2993constructorimpl(this.indent + 1);
            try {
                block.invoke();
            } finally {
                InlineMarker.finallyStart(1);
                this.indent = UInt.m2993constructorimpl(this.indent - 1);
                InlineMarker.finallyEnd(1);
            }
        }

        public static /* synthetic */ void printDeclaration$default(Printer printer, RenderedDeclaration renderedDeclaration, boolean z, int i, Object obj) throws IOException {
            if ((i & 2) != 0) {
                z = false;
            }
            printer.printDeclaration(renderedDeclaration, z);
        }

        public final void printDeclaration(RenderedDeclaration<?> renderedDeclaration, boolean printOpeningBrace) throws IOException {
            Intrinsics.checkNotNullParameter(renderedDeclaration, "renderedDeclaration");
            Appendable appendable = this.output;
            appendIndent();
            appendable.append(renderedDeclaration.getText());
            if (printOpeningBrace) {
                appendable.append(" {");
            }
            appendSignature(renderedDeclaration.getDeclaration());
            Intrinsics.checkNotNullExpressionValue(appendable.append('\n'), "append(...)");
        }

        public final void printClosingBrace() throws IOException {
            Appendable appendable = this.output;
            appendIndent();
            Appendable appendableAppend = appendable.append('}');
            Intrinsics.checkNotNullExpressionValue(appendableAppend, "append(...)");
            Intrinsics.checkNotNullExpressionValue(appendableAppend.append('\n'), "append(...)");
        }

        private final void appendIndent() throws IOException {
            int i = this.indent;
            for (int i2 = 0; UnsignedKt.uintCompare(i2, i) < 0; i2++) {
                this.output.append(this.settings.getIndentationString());
            }
        }

        private final void appendSignature(AbiDeclaration declaration) throws IOException {
            this.output.append(" // ");
            Appendable appendable = this.output;
            String strInvoke = declaration.getSignatures().get(this.settings.getRenderedSignatureVersion());
            if (strInvoke == null) {
                strInvoke = this.settings.getWhenSignatureNotFound().invoke(declaration, this.settings.getRenderedSignatureVersion());
            }
            appendable.append(strInvoke);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: LibraryAbiRendererImpl.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\"\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J\u0014\u0010\u0005\u001a\u00020\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\bH$J\u001b\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00028\u00002\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$RenderedDeclarationContainerKind;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/library/abi/AbiDeclarationContainer;", "", "()V", "orderByDeclarationKind", "", "renderedDeclaration", "Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$RenderedDeclaration;", "printNestedDeclarationsInProperOrder", "", "container", "printer", "Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$Printer;", "(Lorg/jetbrains/kotlin/library/abi/AbiDeclarationContainer;Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$Printer;)V", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static abstract class RenderedDeclarationContainerKind<T extends AbiDeclarationContainer> {
        protected abstract int orderByDeclarationKind(RenderedDeclaration<?> renderedDeclaration);

        public final void printNestedDeclarationsInProperOrder(T container, Printer printer) {
            List listEmptyList;
            Intrinsics.checkNotNullParameter(container, "container");
            Intrinsics.checkNotNullParameter(printer, "printer");
            Companion companion = AbiRendererImpl.INSTANCE;
            List<AbiDeclaration> declarations = container.getDeclarations();
            Comparator comparatorCompareBy = ComparisonsKt.compareBy(new AbiRendererImpl$RenderedDeclarationContainerKind$printNestedDeclarationsInProperOrder$1(this), new Function1<RenderedDeclaration<?>, Comparable<?>>() { // from class: org.jetbrains.kotlin.library.abi.impl.AbiRendererImpl$RenderedDeclarationContainerKind$printNestedDeclarationsInProperOrder$2
                @Override // kotlin.jvm.functions.Function1
                public final Comparable<?> invoke(AbiRendererImpl.RenderedDeclaration<?> it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return it.getDeclaration().getQualifiedName();
                }
            }, new PropertyReference1() { // from class: org.jetbrains.kotlin.library.abi.impl.AbiRendererImpl$RenderedDeclarationContainerKind$printNestedDeclarationsInProperOrder$3
                @Override // kotlin.jvm.internal.CallableReference
                public String getSignature() {
                    return "getAdditionalOrderingFactor1()I";
                }

                @Override // kotlin.reflect.KProperty1
                public Object get(Object obj) {
                    return Integer.valueOf(((AbiRendererImpl.RenderedDeclaration) obj).getAdditionalOrderingFactor1());
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public String getName() {
                    return "additionalOrderingFactor1";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinClass(AbiRendererImpl.RenderedDeclaration.class);
                }
            }, new PropertyReference1() { // from class: org.jetbrains.kotlin.library.abi.impl.AbiRendererImpl$RenderedDeclarationContainerKind$printNestedDeclarationsInProperOrder$4
                @Override // kotlin.jvm.internal.CallableReference
                public String getSignature() {
                    return "getText()Ljava/lang/String;";
                }

                @Override // kotlin.reflect.KProperty1
                public Object get(Object obj) {
                    return ((AbiRendererImpl.RenderedDeclaration) obj).getText();
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public String getName() {
                    return "text";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinClass(AbiRendererImpl.RenderedDeclaration.class);
                }
            }, new PropertyReference1() { // from class: org.jetbrains.kotlin.library.abi.impl.AbiRendererImpl$RenderedDeclarationContainerKind$printNestedDeclarationsInProperOrder$5
                @Override // kotlin.jvm.internal.CallableReference
                public String getSignature() {
                    return "getAdditionalOrderingFactor2()Ljava/lang/String;";
                }

                @Override // kotlin.reflect.KProperty1
                public Object get(Object obj) {
                    return ((AbiRendererImpl.RenderedDeclaration) obj).getAdditionalOrderingFactor2();
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public String getName() {
                    return "additionalOrderingFactor2";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinClass(AbiRendererImpl.RenderedDeclaration.class);
                }
            });
            RenderedDeclaration.Companion companion2 = RenderedDeclaration.INSTANCE;
            if (declarations.isEmpty()) {
                listEmptyList = CollectionsKt.emptyList();
            } else {
                List arrayList = new ArrayList(declarations.size());
                Iterator<T> it = declarations.iterator();
                while (it.hasNext()) {
                    arrayList.add(companion2.createFor((AbiDeclaration) it.next()));
                }
                listEmptyList = arrayList;
                CollectionsKt.sortWith(listEmptyList, comparatorCompareBy);
            }
            Iterator it2 = listEmptyList.iterator();
            while (it2.hasNext()) {
                ((RenderedDeclaration) it2.next()).print(printer);
            }
        }
    }

    /* JADX INFO: compiled from: LibraryAbiRendererImpl.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÂ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0014\u0010\u0004\u001a\u00020\u00052\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0007H\u0014¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$RenderedTopLevelDeclarations;", "Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$RenderedDeclarationContainerKind;", "Lorg/jetbrains/kotlin/library/abi/AbiTopLevelDeclarations;", "()V", "orderByDeclarationKind", "", "renderedDeclaration", "Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$RenderedDeclaration;", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class RenderedTopLevelDeclarations extends RenderedDeclarationContainerKind<AbiTopLevelDeclarations> {
        public static final RenderedTopLevelDeclarations INSTANCE = new RenderedTopLevelDeclarations();

        private RenderedTopLevelDeclarations() {
        }

        @Override // org.jetbrains.kotlin.library.abi.impl.AbiRendererImpl.RenderedDeclarationContainerKind
        protected int orderByDeclarationKind(RenderedDeclaration<?> renderedDeclaration) {
            Intrinsics.checkNotNullParameter(renderedDeclaration, "renderedDeclaration");
            AbiDeclaration declaration = renderedDeclaration.getDeclaration();
            if (declaration instanceof AbiClass) {
                return 1;
            }
            if (declaration instanceof AbiProperty) {
                return 2;
            }
            return declaration instanceof AbiFunction ? 3 : 4;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: LibraryAbiRendererImpl.kt */
    @Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b2\u0018\u0000 \u0017*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003:\u0001\u0017B\u0017\b\u0004\u0012\u0006\u0010\u0004\u001a\u00028\u0000\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H&R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u0011\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000e\u0082\u0001\u0004\u0018\u0019\u001a\u001b¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$RenderedDeclaration;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/library/abi/AbiDeclaration;", "", "declaration", "text", "", "(Lorg/jetbrains/kotlin/library/abi/AbiDeclaration;Ljava/lang/String;)V", "additionalOrderingFactor1", "", "getAdditionalOrderingFactor1", "()I", "additionalOrderingFactor2", "getAdditionalOrderingFactor2", "()Ljava/lang/String;", "getDeclaration", "()Lorg/jetbrains/kotlin/library/abi/AbiDeclaration;", "Lorg/jetbrains/kotlin/library/abi/AbiDeclaration;", "getText", "print", "", "printer", "Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$Printer;", "Companion", "Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$RenderedClass;", "Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$RenderedEnumEntry;", "Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$RenderedFunction;", "Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$RenderedProperty;", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static abstract class RenderedDeclaration<T extends AbiDeclaration> {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private final T declaration;
        private final String text;

        public /* synthetic */ RenderedDeclaration(AbiDeclaration abiDeclaration, String str, DefaultConstructorMarker defaultConstructorMarker) {
            this(abiDeclaration, str);
        }

        public int getAdditionalOrderingFactor1() {
            return 0;
        }

        public abstract void print(Printer printer);

        private RenderedDeclaration(T t, String str) {
            this.declaration = t;
            this.text = str;
        }

        public final T getDeclaration() {
            return this.declaration;
        }

        public final String getText() {
            return this.text;
        }

        public String getAdditionalOrderingFactor2() {
            return "";
        }

        /* JADX INFO: compiled from: LibraryAbiRendererImpl.kt */
        @Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\u0007\u001a\u00020\b*\u00060\tj\u0002`\n2\u0006\u0010\u0005\u001a\u00020\u000bJ\u0016\u0010\f\u001a\u00020\b*\u00060\tj\u0002`\n2\u0006\u0010\u0005\u001a\u00020\u0006J4\u0010\r\u001a\u00020\b*\u00060\tj\u0002`\n2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0012J\u0016\u0010\u0015\u001a\u00020\b*\u00060\tj\u0002`\n2\u0006\u0010\u0016\u001a\u00020\u0010J\u0018\u0010\u0017\u001a\u00020\u0012*\u00060\tj\u0002`\n2\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0018\u0010\u001a\u001a\u00020\u0012*\u00060\tj\u0002`\n2\u0006\u0010\u001b\u001a\u00020\u001cH\u0002J\u0016\u0010\u001d\u001a\u00020\b*\u00060\tj\u0002`\n2\u0006\u0010\u001e\u001a\u00020\u001fJ\u0018\u0010 \u001a\u00020\b*\u00060\tj\u0002`\n2\u0006\u0010!\u001a\u00020\"H\u0002¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$RenderedDeclaration$Companion;", "", "()V", "createFor", "Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$RenderedDeclaration;", "declaration", "Lorg/jetbrains/kotlin/library/abi/AbiDeclaration;", "appendModalityOf", "", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "Lorg/jetbrains/kotlin/library/abi/AbiDeclarationWithModality;", "appendNameOf", "appendSortedTypes", SpecTypePair.NAME_types, "", "Lorg/jetbrains/kotlin/library/abi/AbiType;", "separator", "", "prefix", "postfix", "appendType", ModuleXmlParser.TYPE, "appendTypeArgument", "typeArgument", "Lorg/jetbrains/kotlin/library/abi/AbiTypeArgument;", "appendTypeParameter", "typeParameter", "Lorg/jetbrains/kotlin/library/abi/AbiTypeParameter;", "appendTypeParametersOf", "container", "Lorg/jetbrains/kotlin/library/abi/AbiTypeParametersContainer;", "appendVariance", "variance", "Lorg/jetbrains/kotlin/library/abi/AbiVariance;", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {

            /* JADX INFO: compiled from: LibraryAbiRendererImpl.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            public /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;
                public static final /* synthetic */ int[] $EnumSwitchMapping$1;

                static {
                    int[] iArr = new int[AbiTypeNullability.values().length];
                    try {
                        iArr[AbiTypeNullability.MARKED_NULLABLE.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[AbiTypeNullability.NOT_SPECIFIED.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    try {
                        iArr[AbiTypeNullability.DEFINITELY_NOT_NULL.ordinal()] = 3;
                    } catch (NoSuchFieldError unused3) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                    int[] iArr2 = new int[AbiVariance.values().length];
                    try {
                        iArr2[AbiVariance.INVARIANT.ordinal()] = 1;
                    } catch (NoSuchFieldError unused4) {
                    }
                    try {
                        iArr2[AbiVariance.IN.ordinal()] = 2;
                    } catch (NoSuchFieldError unused5) {
                    }
                    try {
                        iArr2[AbiVariance.OUT.ordinal()] = 3;
                    } catch (NoSuchFieldError unused6) {
                    }
                    $EnumSwitchMapping$1 = iArr2;
                }
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final RenderedDeclaration<?> createFor(AbiDeclaration declaration) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                if (declaration instanceof AbiFunction) {
                    return new RenderedFunction((AbiFunction) declaration);
                }
                if (declaration instanceof AbiProperty) {
                    return new RenderedProperty((AbiProperty) declaration);
                }
                if (declaration instanceof AbiClass) {
                    return new RenderedClass((AbiClass) declaration);
                }
                if (declaration instanceof AbiEnumEntry) {
                    return new RenderedEnumEntry((AbiEnumEntry) declaration);
                }
                throw new NoWhenBranchMatchedException();
            }

            public final void appendModalityOf(StringBuilder sb, AbiDeclarationWithModality declaration) {
                Intrinsics.checkNotNullParameter(sb, "<this>");
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                String lowerCase = declaration.getModality().name().toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                sb.append(lowerCase).append(StringUtil.SPACE);
            }

            public final void appendNameOf(StringBuilder sb, AbiDeclaration declaration) {
                Intrinsics.checkNotNullParameter(sb, "<this>");
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                sb.append(AbiCompoundName.m7314getNameSegmentsCountimpl(declaration.getQualifiedName().m7326getRelativeNamel9qHIQ()) == 1 ? declaration.getQualifiedName() : AbiSimpleName.m7328boximpl(AbiCompoundName.m7315getSimpleNamet3xSXQ(declaration.getQualifiedName().m7326getRelativeNamel9qHIQ())));
            }

            public final void appendTypeParametersOf(final StringBuilder sb, AbiTypeParametersContainer container) {
                Intrinsics.checkNotNullParameter(sb, "<this>");
                Intrinsics.checkNotNullParameter(container, "container");
                if (container.getTypeParameters().isEmpty()) {
                    return;
                }
                CollectionsKt.joinTo(container.getTypeParameters(), sb, (112 & 2) != 0 ? ", " : ", ", (112 & 4) != 0 ? "" : "<", (112 & 8) != 0 ? "" : ">", (112 & 16) != 0 ? -1 : 0, (112 & 32) != 0 ? "..." : null, (112 & 64) != 0 ? null : new Function1<AbiTypeParameter, CharSequence>() { // from class: org.jetbrains.kotlin.library.abi.impl.AbiRendererImpl$RenderedDeclaration$Companion$appendTypeParametersOf$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(AbiTypeParameter typeParameter) {
                        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
                        return AbiRendererImpl.RenderedDeclaration.INSTANCE.appendTypeParameter(sb, typeParameter);
                    }
                });
                sb.append(StringUtil.SPACE);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final String appendTypeParameter(StringBuilder sb, AbiTypeParameter abiTypeParameter) {
                sb.append('#').append(abiTypeParameter.getTag()).append(": ");
                if (abiTypeParameter.getIsReified()) {
                    sb.append("reified ");
                }
                appendVariance(sb, abiTypeParameter.getVariance());
                int size = abiTypeParameter.getUpperBounds().size();
                if (size == 0) {
                    sb.append("kotlin/Any?");
                    return "";
                }
                if (size == 1) {
                    appendType(sb, abiTypeParameter.getUpperBounds().get(0));
                    return "";
                }
                appendSortedTypes(sb, abiTypeParameter.getUpperBounds(), " & ", "", "");
                return "";
            }

            public final void appendType(final StringBuilder sb, AbiType type) {
                Intrinsics.checkNotNullParameter(sb, "<this>");
                Intrinsics.checkNotNullParameter(type, "type");
                if (type instanceof AbiType.Simple) {
                    AbiType.Simple simple = (AbiType.Simple) type;
                    AbiClassifierReference classifierReference = simple.getClassifierReference();
                    if (classifierReference instanceof AbiClassifierReference.ClassReference) {
                        sb.append(((AbiClassifierReference.ClassReference) classifierReference).getClassName());
                        if (!simple.getArguments().isEmpty()) {
                            CollectionsKt.joinTo(simple.getArguments(), sb, (112 & 2) != 0 ? ", " : ", ", (112 & 4) != 0 ? "" : "<", (112 & 8) != 0 ? "" : ">", (112 & 16) != 0 ? -1 : 0, (112 & 32) != 0 ? "..." : null, (112 & 64) != 0 ? null : new Function1<AbiTypeArgument, CharSequence>() { // from class: org.jetbrains.kotlin.library.abi.impl.AbiRendererImpl$RenderedDeclaration$Companion$appendType$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                public final CharSequence invoke(AbiTypeArgument typeArgument) {
                                    Intrinsics.checkNotNullParameter(typeArgument, "typeArgument");
                                    return AbiRendererImpl.RenderedDeclaration.INSTANCE.appendTypeArgument(sb, typeArgument);
                                }
                            });
                        }
                        if (simple.getNullability() == AbiTypeNullability.MARKED_NULLABLE) {
                            sb.append(MangleConstant.Q_MARK);
                            return;
                        }
                        return;
                    }
                    if (classifierReference instanceof AbiClassifierReference.TypeParameterReference) {
                        sb.append('#').append(((AbiClassifierReference.TypeParameterReference) classifierReference).getTag());
                        int i = WhenMappings.$EnumSwitchMapping$0[simple.getNullability().ordinal()];
                        if (i == 1) {
                            sb.append(MangleConstant.Q_MARK);
                            return;
                        } else {
                            if (i != 3) {
                                return;
                            }
                            sb.append("!!");
                            return;
                        }
                    }
                    return;
                }
                if (type instanceof AbiType.Dynamic) {
                    sb.append("dynamic");
                } else if (type instanceof AbiType.Error) {
                    sb.append("error");
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final String appendTypeArgument(StringBuilder sb, AbiTypeArgument abiTypeArgument) {
                if (!(abiTypeArgument instanceof AbiTypeArgument.StarProjection)) {
                    if (abiTypeArgument instanceof AbiTypeArgument.TypeProjection) {
                        AbiTypeArgument.TypeProjection typeProjection = (AbiTypeArgument.TypeProjection) abiTypeArgument;
                        appendVariance(sb, typeProjection.getVariance());
                        appendType(sb, typeProjection.getType());
                        return "";
                    }
                    return "";
                }
                sb.append('*');
                return "";
            }

            private final void appendVariance(StringBuilder sb, AbiVariance abiVariance) {
                int i = WhenMappings.$EnumSwitchMapping$1[abiVariance.ordinal()];
                if (i == 2) {
                    sb.append("in ");
                } else {
                    if (i != 3) {
                        return;
                    }
                    sb.append("out ");
                }
            }

            public final void appendSortedTypes(StringBuilder sb, List<? extends AbiType> types, String separator, String prefix, String postfix) {
                List listEmptyList;
                Intrinsics.checkNotNullParameter(sb, "<this>");
                Intrinsics.checkNotNullParameter(types, "types");
                Intrinsics.checkNotNullParameter(separator, "separator");
                Intrinsics.checkNotNullParameter(prefix, "prefix");
                Intrinsics.checkNotNullParameter(postfix, "postfix");
                Companion companion = AbiRendererImpl.INSTANCE;
                Comparator comparatorNaturalOrder = ComparisonsKt.naturalOrder();
                if (types.isEmpty()) {
                    listEmptyList = CollectionsKt.emptyList();
                } else {
                    List arrayList = new ArrayList(types.size());
                    for (AbiType abiType : types) {
                        StringBuilder sb2 = new StringBuilder();
                        RenderedDeclaration.INSTANCE.appendType(sb2, abiType);
                        String string = sb2.toString();
                        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                        arrayList.add(string);
                    }
                    listEmptyList = arrayList;
                    CollectionsKt.sortWith(listEmptyList, comparatorNaturalOrder);
                }
                CollectionsKt.joinTo(listEmptyList, sb, (112 & 2) != 0 ? ", " : separator, (112 & 4) != 0 ? "" : prefix, (112 & 8) != 0 ? "" : postfix, (112 & 16) != 0 ? -1 : 0, (112 & 32) != 0 ? "..." : null, (112 & 64) != 0 ? null : null);
            }
        }
    }

    /* JADX INFO: compiled from: LibraryAbiRendererImpl.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000 \t2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\tB\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$RenderedClass;", "Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$RenderedDeclaration;", "Lorg/jetbrains/kotlin/library/abi/AbiClass;", "declaration", "(Lorg/jetbrains/kotlin/library/abi/AbiClass;)V", "print", "", "printer", "Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$Printer;", "Companion", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class RenderedClass extends RenderedDeclaration<AbiClass> {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);

        public RenderedClass(AbiClass declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            AbiClass abiClass = declaration;
            StringBuilder sb = new StringBuilder();
            RenderedDeclaration.INSTANCE.appendModalityOf(sb, declaration);
            if (declaration.isInner()) {
                sb.append("inner ");
            }
            if (declaration.isValue()) {
                sb.append("value ");
            }
            if (declaration.isFunction()) {
                sb.append("fun ");
            }
            INSTANCE.appendClassKind(sb, declaration.getKind());
            RenderedDeclaration.INSTANCE.appendTypeParametersOf(sb, declaration);
            RenderedDeclaration.INSTANCE.appendNameOf(sb, abiClass);
            if (!declaration.getSuperTypes().isEmpty()) {
                RenderedDeclaration.INSTANCE.appendSortedTypes(sb, declaration.getSuperTypes(), ", ", " : ", "");
            }
            Unit unit = Unit.INSTANCE;
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            super(abiClass, string, null);
        }

        @Override // org.jetbrains.kotlin.library.abi.impl.AbiRendererImpl.RenderedDeclaration
        public void print(Printer printer) throws IOException {
            Intrinsics.checkNotNullParameter(printer, "printer");
            boolean zIsEmpty = getDeclaration().getDeclarations().isEmpty();
            printer.printDeclaration(this, !zIsEmpty);
            if (zIsEmpty) {
                return;
            }
            printer.indent = UInt.m2993constructorimpl(printer.indent + 1);
            try {
                INSTANCE.printNestedDeclarationsInProperOrder(getDeclaration(), printer);
                printer.indent = UInt.m2993constructorimpl(printer.indent - 1);
                printer.printClosingBrace();
            } catch (Throwable th) {
                printer.indent = UInt.m2993constructorimpl(printer.indent - 1);
                throw th;
            }
        }

        /* JADX INFO: compiled from: LibraryAbiRendererImpl.kt */
        @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0014\u0010\u0004\u001a\u00020\u00052\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0007H\u0014J\u0018\u0010\b\u001a\u00020\t*\u00060\nj\u0002`\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$RenderedClass$Companion;", "Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$RenderedDeclarationContainerKind;", "Lorg/jetbrains/kotlin/library/abi/AbiClass;", "()V", "orderByDeclarationKind", "", "renderedDeclaration", "Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$RenderedDeclaration;", "appendClassKind", "", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "classKind", "Lorg/jetbrains/kotlin/library/abi/AbiClassKind;", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion extends RenderedDeclarationContainerKind<AbiClass> {

            /* JADX INFO: compiled from: LibraryAbiRendererImpl.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            public /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[AbiClassKind.values().length];
                    try {
                        iArr[AbiClassKind.CLASS.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[AbiClassKind.INTERFACE.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    try {
                        iArr[AbiClassKind.OBJECT.ordinal()] = 3;
                    } catch (NoSuchFieldError unused3) {
                    }
                    try {
                        iArr[AbiClassKind.ENUM_CLASS.ordinal()] = 4;
                    } catch (NoSuchFieldError unused4) {
                    }
                    try {
                        iArr[AbiClassKind.ANNOTATION_CLASS.ordinal()] = 5;
                    } catch (NoSuchFieldError unused5) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final void appendClassKind(StringBuilder sb, AbiClassKind abiClassKind) {
                String str;
                int i = WhenMappings.$EnumSwitchMapping$0[abiClassKind.ordinal()];
                if (i == 1) {
                    str = "class";
                } else if (i == 2) {
                    str = PsiKeyword.INTERFACE;
                } else if (i == 3) {
                    str = "object";
                } else if (i == 4) {
                    str = "enum class";
                } else {
                    if (i != 5) {
                        throw new NoWhenBranchMatchedException();
                    }
                    str = "annotation class";
                }
                sb.append(str).append(StringUtil.SPACE);
            }

            @Override // org.jetbrains.kotlin.library.abi.impl.AbiRendererImpl.RenderedDeclarationContainerKind
            protected int orderByDeclarationKind(RenderedDeclaration<?> renderedDeclaration) {
                Intrinsics.checkNotNullParameter(renderedDeclaration, "renderedDeclaration");
                AbiDeclaration declaration = renderedDeclaration.getDeclaration();
                if (declaration instanceof AbiProperty) {
                    return 1;
                }
                if (declaration instanceof AbiFunction) {
                    return ((AbiFunction) declaration).isConstructor() ? 2 : 3;
                }
                if (declaration instanceof AbiClass) {
                    return 4;
                }
                if (declaration instanceof AbiEnumEntry) {
                    return 5;
                }
                throw new NoWhenBranchMatchedException();
            }
        }
    }

    /* JADX INFO: compiled from: LibraryAbiRendererImpl.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$RenderedEnumEntry;", "Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$RenderedDeclaration;", "Lorg/jetbrains/kotlin/library/abi/AbiEnumEntry;", "declaration", "(Lorg/jetbrains/kotlin/library/abi/AbiEnumEntry;)V", "print", "", "printer", "Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$Printer;", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class RenderedEnumEntry extends RenderedDeclaration<AbiEnumEntry> {
        public RenderedEnumEntry(AbiEnumEntry declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            AbiEnumEntry abiEnumEntry = declaration;
            StringBuilder sb = new StringBuilder();
            sb.append("enum entry ");
            RenderedDeclaration.INSTANCE.appendNameOf(sb, abiEnumEntry);
            Unit unit = Unit.INSTANCE;
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            super(abiEnumEntry, string, null);
        }

        @Override // org.jetbrains.kotlin.library.abi.impl.AbiRendererImpl.RenderedDeclaration
        public void print(Printer printer) throws IOException {
            Intrinsics.checkNotNullParameter(printer, "printer");
            Printer.printDeclaration$default(printer, this, false, 2, null);
        }
    }

    /* JADX INFO: compiled from: LibraryAbiRendererImpl.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000 \u00142\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0014B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$RenderedProperty;", "Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$RenderedDeclaration;", "Lorg/jetbrains/kotlin/library/abi/AbiProperty;", "declaration", "(Lorg/jetbrains/kotlin/library/abi/AbiProperty;)V", "additionalOrderingFactor1", "", "getAdditionalOrderingFactor1", "()I", "additionalOrderingFactor2", "", "getAdditionalOrderingFactor2", "()Ljava/lang/String;", "getter", "Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$RenderedFunction;", "setter", "print", "", "printer", "Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$Printer;", "Companion", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class RenderedProperty extends RenderedDeclaration<AbiProperty> {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private final RenderedFunction getter;
        private final RenderedFunction setter;

        /* JADX WARN: Multi-variable type inference failed */
        public RenderedProperty(AbiProperty declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            AbiProperty abiProperty = declaration;
            StringBuilder sb = new StringBuilder();
            RenderedDeclaration.INSTANCE.appendModalityOf(sb, declaration);
            INSTANCE.appendPropertyKind(sb, declaration.getKind());
            RenderedDeclaration.INSTANCE.appendNameOf(sb, abiProperty);
            Unit unit = Unit.INSTANCE;
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            super(abiProperty, string, 0 == true ? 1 : 0);
            AbiFunction getter = declaration.getGetter();
            this.getter = getter != null ? new RenderedFunction(getter) : null;
            AbiFunction setter = declaration.getSetter();
            this.setter = setter != null ? new RenderedFunction(setter) : null;
        }

        @Override // org.jetbrains.kotlin.library.abi.impl.AbiRendererImpl.RenderedDeclaration
        public int getAdditionalOrderingFactor1() {
            RenderedFunction renderedFunction = this.getter;
            if (renderedFunction == null) {
                renderedFunction = this.setter;
            }
            if (renderedFunction != null) {
                return renderedFunction.getAdditionalOrderingFactor1();
            }
            return 0;
        }

        @Override // org.jetbrains.kotlin.library.abi.impl.AbiRendererImpl.RenderedDeclaration
        public String getAdditionalOrderingFactor2() {
            RenderedFunction renderedFunction = this.getter;
            if (renderedFunction == null) {
                renderedFunction = this.setter;
            }
            String text = renderedFunction != null ? renderedFunction.getText() : null;
            return text == null ? "" : text;
        }

        @Override // org.jetbrains.kotlin.library.abi.impl.AbiRendererImpl.RenderedDeclaration
        public void print(Printer printer) throws IOException {
            Intrinsics.checkNotNullParameter(printer, "printer");
            Printer.printDeclaration$default(printer, this, false, 2, null);
            printer.indent = UInt.m2993constructorimpl(printer.indent + 1);
            try {
                RenderedFunction renderedFunction = this.getter;
                if (renderedFunction != null) {
                    renderedFunction.print(printer);
                }
                RenderedFunction renderedFunction2 = this.setter;
                if (renderedFunction2 != null) {
                    renderedFunction2.print(printer);
                }
            } finally {
                printer.indent = UInt.m2993constructorimpl(printer.indent - 1);
            }
        }

        /* JADX INFO: compiled from: LibraryAbiRendererImpl.kt */
        @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u0004*\u00060\u0005j\u0002`\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$RenderedProperty$Companion;", "", "()V", "appendPropertyKind", "", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "propertyKind", "Lorg/jetbrains/kotlin/library/abi/AbiPropertyKind;", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {

            /* JADX INFO: compiled from: LibraryAbiRendererImpl.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            public /* synthetic */ class WhenMappings {
                public static final /* synthetic */ int[] $EnumSwitchMapping$0;

                static {
                    int[] iArr = new int[AbiPropertyKind.values().length];
                    try {
                        iArr[AbiPropertyKind.VAL.ordinal()] = 1;
                    } catch (NoSuchFieldError unused) {
                    }
                    try {
                        iArr[AbiPropertyKind.CONST_VAL.ordinal()] = 2;
                    } catch (NoSuchFieldError unused2) {
                    }
                    try {
                        iArr[AbiPropertyKind.VAR.ordinal()] = 3;
                    } catch (NoSuchFieldError unused3) {
                    }
                    $EnumSwitchMapping$0 = iArr;
                }
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final void appendPropertyKind(StringBuilder sb, AbiPropertyKind abiPropertyKind) {
                String str;
                int i = WhenMappings.$EnumSwitchMapping$0[abiPropertyKind.ordinal()];
                if (i == 1) {
                    str = "val";
                } else if (i == 2) {
                    str = "const val";
                } else {
                    if (i != 3) {
                        throw new NoWhenBranchMatchedException();
                    }
                    str = PsiKeyword.VAR;
                }
                sb.append(str).append(StringUtil.SPACE);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: LibraryAbiRendererImpl.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000 \r2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\rB\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$RenderedFunction;", "Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$RenderedDeclaration;", "Lorg/jetbrains/kotlin/library/abi/AbiFunction;", "declaration", "(Lorg/jetbrains/kotlin/library/abi/AbiFunction;)V", "additionalOrderingFactor1", "", "getAdditionalOrderingFactor1", "()I", "print", "", "printer", "Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$Printer;", "Companion", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class RenderedFunction extends RenderedDeclaration<AbiFunction> {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);

        public RenderedFunction(AbiFunction declaration) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            AbiFunction abiFunction = declaration;
            StringBuilder sb = new StringBuilder();
            if (!declaration.isConstructor() || declaration.getModality() != AbiModality.FINAL) {
                RenderedDeclaration.INSTANCE.appendModalityOf(sb, declaration);
            }
            if (declaration.isSuspend()) {
                sb.append("suspend ");
            }
            if (declaration.isInline()) {
                sb.append("inline ");
            }
            sb.append(declaration.isConstructor() ? "constructor " : "fun ");
            RenderedDeclaration.INSTANCE.appendTypeParametersOf(sb, declaration);
            Companion companion = INSTANCE;
            companion.appendIrregularValueParametersOf(sb, declaration);
            RenderedDeclaration.INSTANCE.appendNameOf(sb, abiFunction);
            companion.appendRegularValueParametersOf(sb, declaration);
            companion.appendReturnTypeOf(sb, declaration);
            Unit unit = Unit.INSTANCE;
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            super(abiFunction, string, null);
        }

        @Override // org.jetbrains.kotlin.library.abi.impl.AbiRendererImpl.RenderedDeclaration
        public int getAdditionalOrderingFactor1() {
            boolean hasExtensionReceiverParameter = getDeclaration().getHasExtensionReceiverParameter();
            int contextReceiverParametersCount = getDeclaration().getContextReceiverParametersCount();
            return (((hasExtensionReceiverParameter ? 1 : 0) | (contextReceiverParametersCount << 1)) << 8) | ((getDeclaration().getValueParameters().size() - (hasExtensionReceiverParameter ? 1 : 0)) - contextReceiverParametersCount);
        }

        @Override // org.jetbrains.kotlin.library.abi.impl.AbiRendererImpl.RenderedDeclaration
        public void print(Printer printer) throws IOException {
            Intrinsics.checkNotNullParameter(printer, "printer");
            Printer.printDeclaration$default(printer, this, false, 2, null);
        }

        /* JADX INFO: compiled from: LibraryAbiRendererImpl.kt */
        @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u0004*\u00060\u0005j\u0002`\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0018\u0010\t\u001a\u00020\u0004*\u00060\u0005j\u0002`\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0018\u0010\n\u001a\u00020\u0004*\u00060\u0005j\u0002`\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0018\u0010\u000b\u001a\u00020\f*\u00060\u0005j\u0002`\u00062\u0006\u0010\r\u001a\u00020\u000eH\u0002¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$RenderedFunction$Companion;", "", "()V", "appendIrregularValueParametersOf", "", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "function", "Lorg/jetbrains/kotlin/library/abi/AbiFunction;", "appendRegularValueParametersOf", "appendReturnTypeOf", "appendValueParameter", "", "valueParameter", "Lorg/jetbrains/kotlin/library/abi/AbiValueParameter;", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final void appendIrregularValueParametersOf(final StringBuilder sb, AbiFunction abiFunction) {
                if (abiFunction.getContextReceiverParametersCount() > 0) {
                    Sequence sequenceAsSequence = CollectionsKt.asSequence(abiFunction.getValueParameters());
                    if (abiFunction.getHasExtensionReceiverParameter()) {
                        SequencesKt.drop(sequenceAsSequence, 1);
                    }
                    SequencesKt.joinTo(SequencesKt.take(sequenceAsSequence, abiFunction.getContextReceiverParametersCount()), sb, (48 & 2) != 0 ? ", " : ", ", (48 & 4) != 0 ? "" : "context(", (48 & 8) != 0 ? "" : ") ", (48 & 16) != 0 ? -1 : 0, (48 & 32) != 0 ? "..." : null, (48 & 64) != 0 ? null : new Function1<AbiValueParameter, CharSequence>() { // from class: org.jetbrains.kotlin.library.abi.impl.AbiRendererImpl$RenderedFunction$Companion$appendIrregularValueParametersOf$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final CharSequence invoke(AbiValueParameter valueParameter) {
                            Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
                            return AbiRendererImpl.RenderedFunction.INSTANCE.appendValueParameter(sb, valueParameter);
                        }
                    });
                }
                if (abiFunction.getHasExtensionReceiverParameter()) {
                    sb.append('(');
                    appendValueParameter(sb, abiFunction.getValueParameters().get(0));
                    sb.append(").");
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final void appendRegularValueParametersOf(final StringBuilder sb, AbiFunction abiFunction) {
                boolean hasExtensionReceiverParameter = abiFunction.getHasExtensionReceiverParameter();
                SequencesKt.joinTo(SequencesKt.drop(CollectionsKt.asSequence(abiFunction.getValueParameters()), (hasExtensionReceiverParameter ? 1 : 0) + abiFunction.getContextReceiverParametersCount()), sb, (48 & 2) != 0 ? ", " : ", ", (48 & 4) != 0 ? "" : "(", (48 & 8) != 0 ? "" : Constant.AFTER_QUTO, (48 & 16) != 0 ? -1 : 0, (48 & 32) != 0 ? "..." : null, (48 & 64) != 0 ? null : new Function1<AbiValueParameter, CharSequence>() { // from class: org.jetbrains.kotlin.library.abi.impl.AbiRendererImpl$RenderedFunction$Companion$appendRegularValueParametersOf$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(AbiValueParameter valueParameter) {
                        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
                        return AbiRendererImpl.RenderedFunction.INSTANCE.appendValueParameter(sb, valueParameter);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final String appendValueParameter(StringBuilder sb, AbiValueParameter abiValueParameter) {
                if (abiValueParameter.isNoinline()) {
                    sb.append("noinline ");
                }
                if (abiValueParameter.isCrossinline()) {
                    sb.append("crossinline ");
                }
                RenderedDeclaration.INSTANCE.appendType(sb, abiValueParameter.getType());
                if (abiValueParameter.isVararg()) {
                    sb.append("...");
                }
                if (abiValueParameter.getHasDefaultArg()) {
                    sb.append(" =...");
                    return "";
                }
                return "";
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final void appendReturnTypeOf(StringBuilder sb, AbiFunction abiFunction) {
                AbiType returnType = abiFunction.getReturnType();
                if (returnType != null) {
                    RenderedDeclaration.Companion companion = RenderedDeclaration.INSTANCE;
                    StringBuilder sbAppend = sb.append(": ");
                    Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
                    companion.appendType(sbAppend, returnType);
                }
            }
        }
    }

    /* JADX INFO: compiled from: LibraryAbiRendererImpl.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JU\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\u0004\b\u0000\u0010\u0006\"\b\b\u0001\u0010\u0005*\u00020\u0001*\b\u0012\u0004\u0012\u0002H\u00060\u00042\u0016\u0010\u0007\u001a\u0012\u0012\u0004\u0012\u0002H\u00050\bj\b\u0012\u0004\u0012\u0002H\u0005`\t2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u0002H\u0006\u0012\u0004\u0012\u0002H\u00050\u000bH\u0082\b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/AbiRendererImpl$Companion;", "", "()V", "mapAndSort", "", "R", ExifInterface.GPS_DIRECTION_TRUE, "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "transform", "Lkotlin/Function1;", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
