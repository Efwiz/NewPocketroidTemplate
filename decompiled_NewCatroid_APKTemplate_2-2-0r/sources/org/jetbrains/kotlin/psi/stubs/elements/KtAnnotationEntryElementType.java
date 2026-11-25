package org.jetbrains.kotlin.psi.stubs.elements;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IndexSink;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.com.intellij.util.io.StringRef;
import org.jetbrains.kotlin.constant.ConstantValue;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtValueArgumentList;
import org.jetbrains.kotlin.psi.stubs.KotlinAnnotationEntryStub;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinAnnotationEntryStubImpl;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinConstantValueKt;

/* JADX INFO: loaded from: classes6.dex */
public class KtAnnotationEntryElementType extends KtStubElementType<KotlinAnnotationEntryStub, KtAnnotationEntry> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "psi";
                break;
            case 2:
            case 5:
                objArr[0] = "stub";
                break;
            case 3:
            case 4:
                objArr[0] = "dataStream";
                break;
            case 6:
                objArr[0] = "sink";
                break;
            default:
                objArr[0] = "debugName";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/psi/stubs/elements/KtAnnotationEntryElementType";
        switch (i) {
            case 1:
                objArr[2] = "createStub";
                break;
            case 2:
            case 3:
                objArr[2] = "serialize";
                break;
            case 4:
                objArr[2] = "deserialize";
                break;
            case 5:
            case 6:
                objArr[2] = "indexStub";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtAnnotationEntryElementType(String str) {
        super(str, KtAnnotationEntry.class, KotlinAnnotationEntryStub.class);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public KotlinAnnotationEntryStub createStub(KtAnnotationEntry ktAnnotationEntry, StubElement stubElement) {
        if (ktAnnotationEntry == null) {
            $$$reportNull$$$0(1);
        }
        Name shortName = ktAnnotationEntry.getShortName();
        String strAsString = shortName != null ? shortName.asString() : null;
        KtValueArgumentList valueArgumentList = ktAnnotationEntry.getValueArgumentList();
        return new KotlinAnnotationEntryStubImpl(stubElement, StringRef.fromString(strAsString), (valueArgumentList == null || valueArgumentList.getArguments().isEmpty()) ? false : true, null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(KotlinAnnotationEntryStub kotlinAnnotationEntryStub, StubOutputStream stubOutputStream) throws IOException {
        if (kotlinAnnotationEntryStub == null) {
            $$$reportNull$$$0(2);
        }
        if (stubOutputStream == null) {
            $$$reportNull$$$0(3);
        }
        stubOutputStream.writeName(kotlinAnnotationEntryStub.getShortName());
        stubOutputStream.writeBoolean(kotlinAnnotationEntryStub.getHasValueArguments());
        if (kotlinAnnotationEntryStub instanceof KotlinAnnotationEntryStubImpl) {
            Map<Name, ConstantValue<?>> valueArguments = ((KotlinAnnotationEntryStubImpl) kotlinAnnotationEntryStub).getValueArguments();
            stubOutputStream.writeInt(valueArguments != null ? valueArguments.size() : 0);
            if (valueArguments != null) {
                for (Map.Entry<Name, ConstantValue<?>> entry : valueArguments.entrySet()) {
                    stubOutputStream.writeName(entry.getKey().asString());
                    KotlinConstantValueKt.serialize(entry.getValue(), stubOutputStream);
                }
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public KotlinAnnotationEntryStub deserialize(StubInputStream stubInputStream, StubElement stubElement) throws IOException {
        if (stubInputStream == null) {
            $$$reportNull$$$0(4);
        }
        StringRef name2 = stubInputStream.readName();
        boolean z = stubInputStream.readBoolean();
        int i = stubInputStream.readInt();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (int i2 = 0; i2 < i; i2++) {
            linkedHashMap.put(Name.identifier((String) Objects.requireNonNull(stubInputStream.readNameString())), KotlinConstantValueKt.createConstantValue(stubInputStream));
        }
        if (linkedHashMap.isEmpty()) {
            linkedHashMap = null;
        }
        return new KotlinAnnotationEntryStubImpl(stubElement, name2, z, linkedHashMap);
    }

    @Override // org.jetbrains.kotlin.psi.stubs.elements.KtStubElementType
    public void indexStub(KotlinAnnotationEntryStub kotlinAnnotationEntryStub, IndexSink indexSink) {
        if (kotlinAnnotationEntryStub == null) {
            $$$reportNull$$$0(5);
        }
        if (indexSink == null) {
            $$$reportNull$$$0(6);
        }
        StubIndexService.getInstance().indexAnnotation(kotlinAnnotationEntryStub, indexSink);
    }
}
