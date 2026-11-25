package org.jetbrains.kotlin.psi.stubs.elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.psi.KtProjectionKind;
import org.jetbrains.kotlin.psi.KtUserType;
import org.jetbrains.kotlin.psi.stubs.KotlinUserTypeStub;
import org.jetbrains.kotlin.psi.stubs.StubUtils;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinClassTypeBean;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinFlexibleTypeBean;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinTypeArgumentBean;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinTypeBean;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinTypeParameterTypeBean;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinUserTypeStubImpl;

/* JADX INFO: loaded from: classes6.dex */
public class KtUserTypeElementType extends KtStubElementType<KotlinUserTypeStub, KtUserType> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "psi";
                break;
            case 2:
                objArr[0] = "stub";
                break;
            case 3:
            case 4:
            case 5:
            case 6:
                objArr[0] = "dataStream";
                break;
            default:
                objArr[0] = "debugName";
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/psi/stubs/elements/KtUserTypeElementType";
        switch (i) {
            case 1:
                objArr[2] = "createStub";
                break;
            case 2:
            case 3:
                objArr[2] = "serialize";
                break;
            case 4:
                objArr[2] = "serializeType";
                break;
            case 5:
                objArr[2] = "deserialize";
                break;
            case 6:
                objArr[2] = "deserializeType";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtUserTypeElementType(String str) {
        super(str, KtUserType.class, KotlinUserTypeStub.class);
        if (str == null) {
            $$$reportNull$$$0(0);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType
    public KotlinUserTypeStub createStub(KtUserType ktUserType, StubElement stubElement) {
        if (ktUserType == null) {
            $$$reportNull$$$0(1);
        }
        return new KotlinUserTypeStubImpl(stubElement, null);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(KotlinUserTypeStub kotlinUserTypeStub, StubOutputStream stubOutputStream) throws IOException {
        if (kotlinUserTypeStub == null) {
            $$$reportNull$$$0(2);
        }
        if (stubOutputStream == null) {
            $$$reportNull$$$0(3);
        }
        serializeType(stubOutputStream, ((KotlinUserTypeStubImpl) kotlinUserTypeStub).getUpperBound());
    }

    private enum KotlinTypeBeanKind {
        CLASS,
        TYPE_PARAMETER,
        FLEXIBLE,
        NONE;

        static KotlinTypeBeanKind fromBean(KotlinTypeBean kotlinTypeBean) {
            if (kotlinTypeBean == null) {
                return NONE;
            }
            if (kotlinTypeBean instanceof KotlinTypeParameterTypeBean) {
                return TYPE_PARAMETER;
            }
            return kotlinTypeBean instanceof KotlinClassTypeBean ? CLASS : FLEXIBLE;
        }
    }

    public static void serializeType(StubOutputStream stubOutputStream, KotlinTypeBean kotlinTypeBean) throws IOException {
        if (stubOutputStream == null) {
            $$$reportNull$$$0(4);
        }
        stubOutputStream.writeInt(KotlinTypeBeanKind.fromBean(kotlinTypeBean).ordinal());
        if (kotlinTypeBean instanceof KotlinClassTypeBean) {
            KotlinClassTypeBean kotlinClassTypeBean = (KotlinClassTypeBean) kotlinTypeBean;
            StubUtils.serializeClassId(stubOutputStream, kotlinClassTypeBean.getClassId());
            stubOutputStream.writeBoolean(kotlinTypeBean.getNullable());
            List<KotlinTypeArgumentBean> arguments = kotlinClassTypeBean.getArguments();
            stubOutputStream.writeInt(arguments.size());
            for (KotlinTypeArgumentBean kotlinTypeArgumentBean : arguments) {
                KtProjectionKind projectionKind = kotlinTypeArgumentBean.getProjectionKind();
                stubOutputStream.writeInt(projectionKind.ordinal());
                if (projectionKind != KtProjectionKind.STAR) {
                    serializeType(stubOutputStream, kotlinTypeArgumentBean.getType());
                }
            }
            return;
        }
        if (kotlinTypeBean instanceof KotlinTypeParameterTypeBean) {
            KotlinTypeParameterTypeBean kotlinTypeParameterTypeBean = (KotlinTypeParameterTypeBean) kotlinTypeBean;
            stubOutputStream.writeName(kotlinTypeParameterTypeBean.getTypeParameterName());
            stubOutputStream.writeBoolean(kotlinTypeBean.getNullable());
            stubOutputStream.writeBoolean(kotlinTypeParameterTypeBean.getDefinitelyNotNull());
            return;
        }
        if (kotlinTypeBean instanceof KotlinFlexibleTypeBean) {
            KotlinFlexibleTypeBean kotlinFlexibleTypeBean = (KotlinFlexibleTypeBean) kotlinTypeBean;
            serializeType(stubOutputStream, kotlinFlexibleTypeBean.getLowerBound());
            serializeType(stubOutputStream, kotlinFlexibleTypeBean.getUpperBound());
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public KotlinUserTypeStub deserialize(StubInputStream stubInputStream, StubElement stubElement) throws IOException {
        if (stubInputStream == null) {
            $$$reportNull$$$0(5);
        }
        return new KotlinUserTypeStubImpl(stubElement, deserializeType(stubInputStream));
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.psi.stubs.elements.KtUserTypeElementType$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$jetbrains$kotlin$psi$stubs$elements$KtUserTypeElementType$KotlinTypeBeanKind;

        static {
            int[] iArr = new int[KotlinTypeBeanKind.values().length];
            $SwitchMap$org$jetbrains$kotlin$psi$stubs$elements$KtUserTypeElementType$KotlinTypeBeanKind = iArr;
            try {
                iArr[KotlinTypeBeanKind.CLASS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$psi$stubs$elements$KtUserTypeElementType$KotlinTypeBeanKind[KotlinTypeBeanKind.TYPE_PARAMETER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$psi$stubs$elements$KtUserTypeElementType$KotlinTypeBeanKind[KotlinTypeBeanKind.FLEXIBLE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$jetbrains$kotlin$psi$stubs$elements$KtUserTypeElementType$KotlinTypeBeanKind[KotlinTypeBeanKind.NONE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static KotlinTypeBean deserializeType(StubInputStream stubInputStream) throws IOException {
        KotlinTypeArgumentBean kotlinTypeArgumentBean;
        if (stubInputStream == null) {
            $$$reportNull$$$0(6);
        }
        int i = AnonymousClass1.$SwitchMap$org$jetbrains$kotlin$psi$stubs$elements$KtUserTypeElementType$KotlinTypeBeanKind[KotlinTypeBeanKind.values()[stubInputStream.readInt()].ordinal()];
        if (i != 1) {
            if (i == 2) {
                return new KotlinTypeParameterTypeBean((String) Objects.requireNonNull(stubInputStream.readNameString()), stubInputStream.readBoolean(), stubInputStream.readBoolean());
            }
            if (i != 3) {
                return null;
            }
            return new KotlinFlexibleTypeBean((KotlinTypeBean) Objects.requireNonNull(deserializeType(stubInputStream)), (KotlinTypeBean) Objects.requireNonNull(deserializeType(stubInputStream)));
        }
        ClassId classId = (ClassId) Objects.requireNonNull(StubUtils.deserializeClassId(stubInputStream));
        boolean z = stubInputStream.readBoolean();
        int i2 = stubInputStream.readInt();
        ArrayList arrayList = new ArrayList();
        for (int i3 = 0; i3 < i2; i3++) {
            int i4 = stubInputStream.readInt();
            if (i4 != KtProjectionKind.STAR.ordinal()) {
                kotlinTypeArgumentBean = new KotlinTypeArgumentBean(KtProjectionKind.values()[i4], deserializeType(stubInputStream));
            } else {
                kotlinTypeArgumentBean = new KotlinTypeArgumentBean(KtProjectionKind.STAR, null);
            }
            arrayList.add(kotlinTypeArgumentBean);
        }
        return new KotlinClassTypeBean(classId, arrayList, z);
    }
}
