package org.jetbrains.kotlin.psi.stubs.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.contracts.description.KtContractDescriptionElement;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.stubs.KotlinFunctionStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtFunctionElementType;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: compiled from: KotlinFunctionStubImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 &2\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003:\u0001&B\u0083\u0001\u0012\u0010\u0010\u0004\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0006\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\u0006\u0010\r\u001a\u00020\n\u0012\u0006\u0010\u000e\u001a\u00020\n\u0012\u0006\u0010\u000f\u001a\u00020\n\u0012\u0006\u0010\u0010\u001a\u00020\n\u0012\u0006\u0010\u0011\u001a\u00020\n\u0012\u001c\u0010\u0012\u001a\u0018\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0015\u0012\u0006\u0012\u0004\u0018\u00010\u00160\u0014\u0018\u00010\u0013\u0012\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018¢\u0006\u0002\u0010\u0019J\n\u0010\u001e\u001a\u0004\u0018\u00010\fH\u0016J\u0010\u0010\u001f\u001a\n !*\u0004\u0018\u00010 0 H\u0016J\b\u0010\u000e\u001a\u00020\nH\u0016J\b\u0010\u000f\u001a\u00020\nH\u0016J\b\u0010\u0010\u001a\u00020\nH\u0016J\b\u0010\r\u001a\u00020\nH\u0016J\b\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u0011\u001a\u00020\nH\u0016J\u000e\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%R'\u0010\u0012\u001a\u0018\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0015\u0012\u0006\u0012\u0004\u0018\u00010\u00160\u0014\u0018\u00010\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u0017\u001a\u0004\u0018\u00010\u0018¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001d¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinFunctionStubImpl;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubBaseImpl;", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinFunctionStub;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "nameRef", "Lorg/jetbrains/kotlin/com/intellij/util/io/StringRef;", "isTopLevel", "", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "isExtension", "hasBlockBody", "hasBody", "hasTypeParameterListBeforeFunctionName", "mayHaveContract", "contract", "", "Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionElement;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinTypeBean;", "", "origin", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubOrigin;", "(Lcom/intellij/psi/stubs/StubElement;Lcom/intellij/util/io/StringRef;ZLorg/jetbrains/kotlin/name/FqName;ZZZZZLjava/util/List;Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubOrigin;)V", "getContract", "()Ljava/util/List;", "getOrigin", "()Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinStubOrigin;", "getFqName", "getName", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "serializeContract", "", "dataStream", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubOutputStream;", "Companion", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinFunctionStubImpl extends KotlinStubBaseImpl<KtNamedFunction> implements KotlinFunctionStub {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final List<KtContractDescriptionElement> contract;
    private final FqName fqName;
    private final boolean hasBlockBody;
    private final boolean hasBody;
    private final boolean hasTypeParameterListBeforeFunctionName;
    private final boolean isExtension;
    private final boolean isTopLevel;
    private final boolean mayHaveContract;
    private final StringRef nameRef;
    private final KotlinStubOrigin origin;

    public final List<KtContractDescriptionElement> getContract() {
        return this.contract;
    }

    public final KotlinStubOrigin getOrigin() {
        return this.origin;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /* JADX WARN: Multi-variable type inference failed */
    public KotlinFunctionStubImpl(StubElement<? extends PsiElement> stubElement, StringRef stringRef, boolean z, FqName fqName, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, List<? extends KtContractDescriptionElement> list, KotlinStubOrigin kotlinStubOrigin) {
        KtFunctionElementType FUNCTION = KtStubElementTypes.FUNCTION;
        Intrinsics.checkNotNullExpressionValue(FUNCTION, "FUNCTION");
        super(stubElement, FUNCTION);
        this.nameRef = stringRef;
        this.isTopLevel = z;
        this.fqName = fqName;
        this.isExtension = z2;
        this.hasBlockBody = z3;
        this.hasBody = z4;
        this.hasTypeParameterListBeforeFunctionName = z5;
        this.mayHaveContract = z6;
        this.contract = list;
        this.origin = kotlinStubOrigin;
        if (z && fqName == null) {
            throw new IllegalArgumentException("fqName shouldn't be null for top level functions");
        }
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinStubWithFqName
    public FqName getFqName() {
        return this.fqName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.NamedStub
    public String getName() {
        return StringRef.toString(this.nameRef);
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinCallableStubBase
    /* JADX INFO: renamed from: isTopLevel, reason: from getter */
    public boolean getIsTopLevel() {
        return this.isTopLevel;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinCallableStubBase
    /* JADX INFO: renamed from: isExtension, reason: from getter */
    public boolean getIsExtension() {
        return this.isExtension;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinFunctionStub
    /* JADX INFO: renamed from: hasBlockBody, reason: from getter */
    public boolean getHasBlockBody() {
        return this.hasBlockBody;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinFunctionStub
    /* JADX INFO: renamed from: hasBody, reason: from getter */
    public boolean getHasBody() {
        return this.hasBody;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinFunctionStub
    /* JADX INFO: renamed from: hasTypeParameterListBeforeFunctionName, reason: from getter */
    public boolean getHasTypeParameterListBeforeFunctionName() {
        return this.hasTypeParameterListBeforeFunctionName;
    }

    @Override // org.jetbrains.kotlin.psi.stubs.KotlinFunctionStub
    /* JADX INFO: renamed from: mayHaveContract, reason: from getter */
    public boolean getMayHaveContract() {
        return this.mayHaveContract;
    }

    public final void serializeContract(StubOutputStream dataStream) throws IOException {
        Intrinsics.checkNotNullParameter(dataStream, "dataStream");
        List<KtContractDescriptionElement> list = this.contract;
        dataStream.writeInt(list != null ? list.size() : 0);
        KotlinContractSerializationVisitor kotlinContractSerializationVisitor = new KotlinContractSerializationVisitor(dataStream);
        if (list != null) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                ((KtContractDescriptionElement) it.next()).accept(kotlinContractSerializationVisitor, null);
            }
        }
    }

    /* JADX INFO: compiled from: KotlinFunctionStubImpl.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00050\u00042\u0006\u0010\b\u001a\u00020\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinFunctionStubImpl$Companion;", "", "()V", "deserializeContract", "", "Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionElement;", "Lorg/jetbrains/kotlin/psi/stubs/impl/KotlinTypeBean;", "", "dataStream", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubInputStream;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final List<KtContractDescriptionElement> deserializeContract(StubInputStream dataStream) {
            Intrinsics.checkNotNullParameter(dataStream, "dataStream");
            ArrayList arrayList = new ArrayList();
            int i = dataStream.readInt();
            for (int i2 = 0; i2 < i; i2++) {
                arrayList.add(KotlinContractEffectType.values()[dataStream.readInt()].deserialize(dataStream));
            }
            return arrayList;
        }
    }
}
