package org.jetbrains.kotlin.container;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Singletons.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u0003\u001a\u00020\u0004H\u0002J\u0010\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u001eH$J\b\u0010\u001f\u001a\u00020\u001bH\u0002J\b\u0010 \u001a\u00020\u000fH\u0016J\u0010\u0010!\u001a\u00020\u001b2\u0006\u0010\"\u001a\u00020\u0002H\u0004R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R!\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\t8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000bR\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u0015X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/container/SingletonDescriptor;", "Lorg/jetbrains/kotlin/container/ComponentDescriptor;", "Ljava/io/Closeable;", "container", "Lorg/jetbrains/kotlin/container/ComponentContainer;", "(Lorg/jetbrains/kotlin/container/ComponentContainer;)V", "getContainer", "()Lorg/jetbrains/kotlin/container/ComponentContainer;", "disposableObjects", "Ljava/util/ArrayList;", "getDisposableObjects", "()Ljava/util/ArrayList;", "disposableObjects$delegate", "Lkotlin/Lazy;", "instance", "", "shouldInjectProperties", "", "getShouldInjectProperties", "()Z", "state", "Lorg/jetbrains/kotlin/container/ComponentState;", "getState", "()Lorg/jetbrains/kotlin/container/ComponentState;", "setState", "(Lorg/jetbrains/kotlin/container/ComponentState;)V", "close", "", "createInstance", "context", "Lorg/jetbrains/kotlin/container/ValueResolveContext;", "disposeImpl", "getValue", "registerDisposableObject", "ownedObject"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class SingletonDescriptor implements Closeable, ComponentDescriptor {
    private final ComponentContainer container;

    /* JADX INFO: renamed from: disposableObjects$delegate, reason: from kotlin metadata */
    private final Lazy disposableObjects;
    private Object instance;
    private ComponentState state;

    /* JADX INFO: compiled from: Singletons.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ComponentState.values().length];
            try {
                iArr[ComponentState.Null.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ComponentState.Initializing.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ComponentState.Initialized.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ComponentState.Corrupted.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[ComponentState.Disposing.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[ComponentState.Disposed.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    protected abstract Object createInstance(ValueResolveContext context);

    @Override // org.jetbrains.kotlin.container.ComponentDescriptor
    public boolean getShouldInjectProperties() {
        return true;
    }

    public SingletonDescriptor(ComponentContainer container) {
        Intrinsics.checkNotNullParameter(container, "container");
        this.container = container;
        this.state = ComponentState.Null;
        this.disposableObjects = LazyKt.lazy(new Function0<ArrayList<Closeable>>() { // from class: org.jetbrains.kotlin.container.SingletonDescriptor$disposableObjects$2
            @Override // kotlin.jvm.functions.Function0
            public final ArrayList<Closeable> invoke() {
                return new ArrayList<>();
            }
        });
    }

    public final ComponentContainer getContainer() {
        return this.container;
    }

    protected final ComponentState getState() {
        return this.state;
    }

    protected final void setState(ComponentState componentState) {
        Intrinsics.checkNotNullParameter(componentState, "<set-?>");
        this.state = componentState;
    }

    private final ArrayList<Closeable> getDisposableObjects() {
        return (ArrayList) this.disposableObjects.getValue();
    }

    @Override // org.jetbrains.kotlin.container.ValueDescriptor
    public Object getValue() throws ContainerConsistencyException, IOException {
        if (this.state == ComponentState.Corrupted) {
            throw new ContainerConsistencyException("Component descriptor " + this + " is corrupted and cannot be accessed");
        }
        if (this.state == ComponentState.Disposed) {
            throw new ContainerConsistencyException("Component descriptor " + this + " is disposed and cannot be accessed");
        }
        if (this.instance == null) {
            createInstance(this.container);
        }
        Object obj = this.instance;
        Intrinsics.checkNotNull(obj);
        return obj;
    }

    protected final void registerDisposableObject(Closeable ownedObject) {
        Intrinsics.checkNotNullParameter(ownedObject, "ownedObject");
        getDisposableObjects().add(ownedObject);
    }

    private final void createInstance(ComponentContainer container) throws ContainerConsistencyException, IOException {
        switch (WhenMappings.$EnumSwitchMapping$0[this.state.ordinal()]) {
            case 1:
                try {
                    this.instance = createInstance(container.createResolveContext(this));
                    return;
                } catch (Throwable th) {
                    this.state = ComponentState.Corrupted;
                    Iterator<Closeable> it = getDisposableObjects().iterator();
                    while (it.hasNext()) {
                        it.next().close();
                    }
                    throw th;
                }
            case 2:
                throw new ContainerConsistencyException("Could not create the component " + this + " because it is being initialized. Do we have undetected circular dependency?");
            case 3:
                throw new ContainerConsistencyException("Could not get the component " + this + ". Instance is null in Initialized state");
            case 4:
                throw new ContainerConsistencyException("Could not get the component " + this + " because it is corrupted");
            case 5:
                throw new ContainerConsistencyException("Could not get the component " + this + " because it is being disposed");
            case 6:
                throw new ContainerConsistencyException("Could not get the component " + this + " because it is already disposed");
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    private final void disposeImpl() {
        Object obj = this.instance;
        this.state = ComponentState.Disposing;
        this.instance = null;
        try {
            if (obj instanceof Closeable) {
                ((Closeable) obj).close();
            }
            Iterator<Closeable> it = getDisposableObjects().iterator();
            while (it.hasNext()) {
                it.next().close();
            }
            this.state = ComponentState.Disposed;
        } catch (Throwable th) {
            this.state = ComponentState.Corrupted;
            throw th;
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws ContainerConsistencyException {
        int i = WhenMappings.$EnumSwitchMapping$0[this.state.ordinal()];
        if (i == 2) {
            throw new ContainerConsistencyException("The component is being initialized and cannot be disposed.");
        }
        if (i == 3) {
            disposeImpl();
        } else {
            if (i == 5) {
                throw new ContainerConsistencyException("The component is already in disposing state.");
            }
            if (i == 6) {
                throw new ContainerConsistencyException("The component has already been destroyed.");
            }
        }
    }
}
