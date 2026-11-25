package org.jetbrains.kotlin.com.intellij.psi.tree;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.ToIntFunction;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.util.AtomicClearableLazyValue;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.StubBuilder;
import org.jetbrains.kotlin.com.intellij.psi.stubs.DefaultStubBuilder;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IndexSink;
import org.jetbrains.kotlin.com.intellij.psi.stubs.PsiFileStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.PsiFileStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubInputStream;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.kotlin.com.intellij.psi.templateLanguages.TemplateLanguage;
import org.jetbrains.kotlin.com.intellij.util.ReflectionUtil;

/* JADX INFO: loaded from: classes3.dex */
public class IStubFileElementType<T extends PsiFileStub> extends StubFileElementType<T> {
    private static final AtomicClearableLazyValue<Integer> TEMPLATE_STUB_BASE_VERSION = new AtomicClearableLazyValue<Integer>() { // from class: org.jetbrains.kotlin.com.intellij.psi.tree.IStubFileElementType.1
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/tree/IStubFileElementType$1", "compute"));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ClearableLazyValue
        public Integer compute() {
            Integer numValueOf = Integer.valueOf(IStubFileElementType.calcTemplateStubBaseVersion());
            if (numValueOf == null) {
                $$$reportNull$$$0(0);
            }
            return numValueOf;
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1 || i == 2) {
            objArr[0] = "dataStream";
        } else if (i != 4) {
            objArr[0] = "stub";
        } else {
            objArr[0] = "sink";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/tree/IStubFileElementType";
        if (i == 2) {
            objArr[2] = "deserialize";
        } else if (i == 3 || i == 4) {
            objArr[2] = "indexStub";
        } else {
            objArr[2] = "serialize";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void indexStub(PsiFileStub psiFileStub, IndexSink indexSink) {
        if (psiFileStub == null) {
            $$$reportNull$$$0(3);
        }
        if (indexSink == null) {
            $$$reportNull$$$0(4);
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public void serialize(T t, StubOutputStream stubOutputStream) throws IOException {
        if (t == null) {
            $$$reportNull$$$0(0);
        }
        if (stubOutputStream == null) {
            $$$reportNull$$$0(1);
        }
    }

    public boolean shouldBuildStubFor(VirtualFile virtualFile) {
        return true;
    }

    public IStubFileElementType(Language language) {
        super(language);
    }

    public IStubFileElementType(String str, Language language) {
        super(str, language);
        if (hasNonTrivialExternalId() && !isOutOfOurControl()) {
            IStubElementType.checkNotInstantiatedTooLate(getClass());
        }
        dropTemplateStubBaseVersion();
    }

    private boolean hasNonTrivialExternalId() {
        return ReflectionUtil.getMethodDeclaringClass(getClass(), "getExternalId", new Class[0]) != IStubFileElementType.class;
    }

    private boolean isOutOfOurControl() {
        return getClass().getName().contains(".kotlin.");
    }

    public int getStubVersion() {
        if (getLanguage() instanceof TemplateLanguage) {
            return TEMPLATE_STUB_BASE_VERSION.getValue().intValue();
        }
        return 0;
    }

    public StubBuilder getBuilder() {
        return new DefaultStubBuilder();
    }

    public String getExternalId() {
        return StubFileElementType.DEFAULT_EXTERNAL_ID;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.stubs.ObjectStubSerializer
    public T deserialize(StubInputStream stubInputStream, StubElement stubElement) throws IOException {
        if (stubInputStream == null) {
            $$$reportNull$$$0(2);
        }
        return new PsiFileStubImpl(null);
    }

    public static int getTemplateStubBaseVersion() {
        return TEMPLATE_STUB_BASE_VERSION.getValue().intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int calcTemplateStubBaseVersion() {
        return Arrays.stream(IElementType.enumerate(new IElementType.Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.tree.IStubFileElementType$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IElementType.Predicate
            public final boolean matches(IElementType iElementType) {
                return IStubFileElementType.lambda$calcTemplateStubBaseVersion$0(iElementType);
            }
        })).mapToInt(new ToIntFunction() { // from class: org.jetbrains.kotlin.com.intellij.psi.tree.IStubFileElementType$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((IStubFileElementType) ((IElementType) obj)).getStubVersion();
            }
        }).sum();
    }

    static /* synthetic */ boolean lambda$calcTemplateStubBaseVersion$0(IElementType iElementType) {
        return (iElementType instanceof IStubFileElementType) && !(iElementType.getLanguage() instanceof TemplateLanguage);
    }

    public static void dropTemplateStubBaseVersion() {
        TEMPLATE_STUB_BASE_VERSION.drop();
    }
}
