package org.jetbrains.kotlin.com.intellij.psi.tree;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.kotlin.com.intellij.diagnostic.LoadingState;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;

/* JADX INFO: loaded from: classes3.dex */
public class IElementType {
    public static final IElementType[] EMPTY_ARRAY;
    public static final short FIRST_TOKEN_INDEX = 1;
    private static volatile IElementType[] ourRegistry;
    private static short size;
    private final String myDebugName;
    private final short myIndex;
    private final Language myLanguage;
    private static final Logger LOG = Logger.getInstance((Class<?>) IElementType.class);
    public static final ArrayFactory<IElementType> ARRAY_FACTORY = new ArrayFactory() { // from class: org.jetbrains.kotlin.com.intellij.psi.tree.IElementType$$ExternalSyntheticLambda0
        @Override // org.jetbrains.kotlin.com.intellij.util.ArrayFactory
        public final Object[] create(int i) {
            return IElementType.lambda$static$0(i);
        }
    };
    public static final Predicate TRUE = new Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.tree.IElementType$$ExternalSyntheticLambda1
        @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IElementType.Predicate
        public final boolean matches(IElementType iElementType) {
            return IElementType.lambda$static$1(iElementType);
        }
    };
    private static final Object lock = new String("registry lock");

    @FunctionalInterface
    public interface Predicate {
        boolean matches(IElementType iElementType);
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 1 || i == 9 || i == 6 || i == 7) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 1 || i == 9 || i == 6 || i == 7) ? 2 : 3];
        switch (i) {
            case 1:
            case 6:
            case 7:
            case 9:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/tree/IElementType";
                break;
            case 2:
                objArr[0] = "loader";
                break;
            case 3:
                objArr[0] = "language";
                break;
            case 4:
            case 5:
                objArr[0] = "debugName";
                break;
            case 8:
                objArr[0] = "p";
                break;
            default:
                objArr[0] = SpecTypePair.NAME_types;
                break;
        }
        if (i == 1) {
            objArr[1] = "push";
        } else if (i == 9) {
            objArr[1] = "enumerate";
        } else if (i == 6) {
            objArr[1] = "getLanguage";
        } else if (i != 7) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/tree/IElementType";
        } else {
            objArr[1] = "getDebugName";
        }
        switch (i) {
            case 1:
            case 6:
            case 7:
            case 9:
                break;
            case 2:
            case 3:
                objArr[2] = "unregisterElementTypes";
                break;
            case 4:
            case 5:
                objArr[2] = "<init>";
                break;
            case 8:
                objArr[2] = "enumerate";
                break;
            default:
                objArr[2] = "push";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 9 && i != 6 && i != 7) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static /* synthetic */ boolean lambda$static$1(IElementType iElementType) {
        return true;
    }

    public boolean isLeftBound() {
        return false;
    }

    static {
        IElementType[] iElementTypeArr = new IElementType[0];
        EMPTY_ARRAY = iElementTypeArr;
        ourRegistry = iElementTypeArr;
        IElementType[] iElementTypeArr2 = new IElementType[137];
        iElementTypeArr2[0] = new IElementType("NULL", Language.ANY, false);
        push(iElementTypeArr2);
    }

    static /* synthetic */ IElementType[] lambda$static$0(int i) {
        return i == 0 ? EMPTY_ARRAY : new IElementType[i];
    }

    static IElementType[] push(IElementType[] iElementTypeArr) {
        IElementType[] iElementTypeArr2;
        if (iElementTypeArr == null) {
            $$$reportNull$$$0(0);
        }
        synchronized (lock) {
            iElementTypeArr2 = ourRegistry;
            ourRegistry = iElementTypeArr;
            size = (short) ContainerUtil.skipNulls(Arrays.asList(ourRegistry)).size();
        }
        if (iElementTypeArr2 == null) {
            $$$reportNull$$$0(1);
        }
        return iElementTypeArr2;
    }

    public static void unregisterElementTypes(ClassLoader classLoader) {
        if (classLoader == null) {
            $$$reportNull$$$0(2);
        }
        for (int i = 0; i < ourRegistry.length; i++) {
            IElementType iElementType = ourRegistry[i];
            if (iElementType != null && iElementType.getClass().getClassLoader() == classLoader) {
                ourRegistry[i] = new TombstoneElementType("tombstone of " + iElementType.myDebugName);
            }
        }
    }

    public static void unregisterElementTypes(Language language) {
        if (language == null) {
            $$$reportNull$$$0(3);
        }
        if (language == Language.ANY) {
            throw new IllegalArgumentException("Trying to unregister Language.ANY");
        }
        for (int i = 0; i < ourRegistry.length; i++) {
            IElementType iElementType = ourRegistry[i];
            if (iElementType != null && iElementType.getLanguage().equals(language)) {
                ourRegistry[i] = new TombstoneElementType("tombstone of " + iElementType.myDebugName);
            }
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public IElementType(String str, Language language) {
        this(str, language, true);
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        if (this instanceof IFileElementType) {
            return;
        }
        LoadingState.COMPONENTS_LOADED.checkOccurred();
    }

    protected IElementType(String str, Language language, boolean z) {
        if (str == null) {
            $$$reportNull$$$0(5);
        }
        this.myDebugName = str;
        this.myLanguage = language == null ? Language.ANY : language;
        if (z) {
            synchronized (lock) {
                short s = size;
                size = (short) (s + 1);
                this.myIndex = s;
                if (s >= 15000) {
                    Map.Entry entry = (Map.Entry) Collections.max(((Map) Stream.of((Object[]) ourRegistry).filter(new java.util.function.Predicate() { // from class: org.jetbrains.kotlin.com.intellij.psi.tree.IElementType$$ExternalSyntheticLambda2
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            return Objects.nonNull((IElementType) obj);
                        }
                    }).collect(Collectors.groupingBy(new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.tree.IElementType$$ExternalSyntheticLambda3
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return ((IElementType) obj).myLanguage;
                        }
                    }))).entrySet(), Comparator.comparingInt(new ToIntFunction() { // from class: org.jetbrains.kotlin.com.intellij.psi.tree.IElementType$$ExternalSyntheticLambda4
                        @Override // java.util.function.ToIntFunction
                        public final int applyAsInt(Object obj) {
                            return ((List) ((Map.Entry) obj).getValue()).size();
                        }
                    }));
                    List list = (List) entry.getValue();
                    LOG.error("Too many element types registered. Out of (short) range. Most of element types (" + list.size() + ") were registered for '" + entry.getKey() + "': " + StringUtil.first(StringUtil.join((Iterable<?>) list, ", "), 300, true));
                }
                IElementType[] iElementTypeArr = s >= ourRegistry.length ? (IElementType[]) ArrayUtil.realloc(ourRegistry, ((ourRegistry.length * 3) / 2) + 1, ARRAY_FACTORY) : ourRegistry;
                iElementTypeArr[s] = this;
                ourRegistry = iElementTypeArr;
            }
            return;
        }
        this.myIndex = (short) -1;
    }

    public Language getLanguage() {
        Language language = this.myLanguage;
        if (language == null) {
            $$$reportNull$$$0(6);
        }
        return language;
    }

    public final short getIndex() {
        return this.myIndex;
    }

    public int hashCode() {
        short s = this.myIndex;
        return s >= 0 ? s : super.hashCode();
    }

    public String toString() {
        return getDebugName();
    }

    public String getDebugName() {
        String str = this.myDebugName;
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        return str;
    }

    public static IElementType find(short s) {
        IElementType iElementType = ourRegistry[s];
        if (iElementType instanceof TombstoneElementType) {
            throw new IllegalArgumentException("Trying to access element type from unloaded plugin: " + iElementType.myDebugName);
        }
        return iElementType;
    }

    public static IElementType[] enumerate(Predicate predicate) {
        if (predicate == null) {
            $$$reportNull$$$0(8);
        }
        ArrayList arrayList = new ArrayList();
        for (IElementType iElementType : ourRegistry) {
            if (iElementType != null && predicate.matches(iElementType)) {
                arrayList.add(iElementType);
            }
        }
        IElementType[] iElementTypeArr = (IElementType[]) arrayList.toArray(new IElementType[0]);
        if (iElementTypeArr == null) {
            $$$reportNull$$$0(9);
        }
        return iElementTypeArr;
    }

    public static final class TombstoneElementType extends IElementType {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "debugName", "org/jetbrains/kotlin/com/intellij/psi/tree/IElementType$TombstoneElementType", "<init>"));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public TombstoneElementType(String str) {
            super(str, Language.ANY);
            if (str == null) {
                $$$reportNull$$$0(0);
            }
        }
    }
}
