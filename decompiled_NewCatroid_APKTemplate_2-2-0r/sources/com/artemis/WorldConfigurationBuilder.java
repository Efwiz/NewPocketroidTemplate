package com.artemis;

import com.artemis.injection.CachedInjector;
import com.artemis.injection.ClassType;
import com.artemis.injection.FieldHandler;
import com.artemis.injection.FieldResolver;
import com.artemis.injection.InjectionCache;
import com.artemis.utils.Bag;
import com.artemis.utils.Sort;
import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.ReflectionException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes8.dex */
public class WorldConfigurationBuilder {
    private ArtemisPlugin activePlugin;
    private boolean alwaysDelayComponentRemoval = false;
    private final InjectionCache cache;
    private Bag<ConfigurationElement<? extends FieldResolver>> fieldResolvers;
    private SystemInvocationStrategy invocationStrategy;
    private Bag<ConfigurationElement<? extends ArtemisPlugin>> plugins;
    private Bag<ConfigurationElement<? extends BaseSystem>> systems;

    public static abstract class Priority {
        public static final int HIGH = 10000;
        public static final int HIGHEST = Integer.MAX_VALUE;
        public static final int LOW = -10000;
        public static final int LOWEST = Integer.MIN_VALUE;
        public static final int NORMAL = 0;
        public static final int OPERATIONS = -1000;
    }

    public WorldConfigurationBuilder() {
        reset();
        this.cache = InjectionCache.sharedCache.get();
    }

    public WorldConfiguration build() {
        appendPlugins();
        WorldConfiguration worldConfiguration = new WorldConfiguration();
        registerSystems(worldConfiguration);
        registerFieldResolvers(worldConfiguration);
        registerInvocationStrategies(worldConfiguration);
        worldConfiguration.setAlwaysDelayComponentRemoval(this.alwaysDelayComponentRemoval);
        reset();
        return worldConfiguration;
    }

    private void registerInvocationStrategies(WorldConfiguration worldConfiguration) {
        SystemInvocationStrategy systemInvocationStrategy = this.invocationStrategy;
        if (systemInvocationStrategy != null) {
            worldConfiguration.setInvocationStrategy(systemInvocationStrategy);
        }
    }

    private void appendPlugins() {
        for (int i = 0; i < this.plugins.size(); i++) {
            ArtemisPlugin artemisPlugin = (ArtemisPlugin) this.plugins.get(i).item;
            this.activePlugin = artemisPlugin;
            artemisPlugin.setup(this);
        }
        this.activePlugin = null;
    }

    protected void registerFieldResolvers(WorldConfiguration worldConfiguration) {
        if (this.fieldResolvers.size() > 0) {
            Sort.instance().sort(this.fieldResolvers);
            FieldHandler fieldHandler = new FieldHandler(InjectionCache.sharedCache.get());
            Iterator<ConfigurationElement<? extends FieldResolver>> it = this.fieldResolvers.iterator();
            while (it.hasNext()) {
                fieldHandler.addFieldResolver((FieldResolver) it.next().item);
            }
            worldConfiguration.setInjector(new CachedInjector().setFieldHandler(fieldHandler));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void registerSystems(WorldConfiguration worldConfiguration) {
        Sort.instance().sort(this.systems);
        Iterator<ConfigurationElement<? extends BaseSystem>> it = this.systems.iterator();
        while (it.hasNext()) {
            worldConfiguration.setSystem((BaseSystem) it.next().item);
        }
    }

    private void reset() {
        this.invocationStrategy = null;
        this.systems = new Bag<>();
        this.fieldResolvers = new Bag<>();
        this.plugins = new Bag<>();
        this.alwaysDelayComponentRemoval = false;
    }

    public WorldConfigurationBuilder alwaysDelayComponentRemoval(boolean z) {
        this.alwaysDelayComponentRemoval = z;
        return this;
    }

    public WorldConfigurationBuilder register(FieldResolver... fieldResolverArr) {
        for (FieldResolver fieldResolver : fieldResolverArr) {
            this.fieldResolvers.add(ConfigurationElement.of(fieldResolver));
        }
        return this;
    }

    public WorldConfigurationBuilder register(SystemInvocationStrategy systemInvocationStrategy) {
        this.invocationStrategy = systemInvocationStrategy;
        return this;
    }

    public final WorldConfigurationBuilder dependsOn(Class... clsArr) {
        return dependsOn(0, clsArr);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final WorldConfigurationBuilder dependsOn(int i, Class... clsArr) {
        for (Class cls : clsArr) {
            try {
                if (AnonymousClass1.$SwitchMap$com$artemis$injection$ClassType[this.cache.getFieldClassType(cls).ordinal()] == 1) {
                    dependsOnSystem(i, cls);
                } else {
                    if (!ClassReflection.isAssignableFrom(ArtemisPlugin.class, cls)) {
                        throw new WorldConfigurationException("Unsupported type. Only supports systems.");
                    }
                    if (i != 0) {
                        throw new WorldConfigurationException("Priority not supported on plugins.");
                    }
                    dependsOnPlugin(cls);
                }
            } catch (ReflectionException e) {
                throw new WorldConfigurationException("Unable to instance " + cls + " via reflection.", e);
            }
        }
        return this;
    }

    /* JADX INFO: renamed from: com.artemis.WorldConfigurationBuilder$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$artemis$injection$ClassType;

        static {
            int[] iArr = new int[ClassType.values().length];
            $SwitchMap$com$artemis$injection$ClassType = iArr;
            try {
                iArr[ClassType.SYSTEM.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    protected void dependsOnSystem(int i, Class<? extends BaseSystem> cls) throws ReflectionException {
        if (containsType(this.systems, cls)) {
            return;
        }
        this.systems.add(ConfigurationElement.of(ClassReflection.newInstance(cls), i));
    }

    private void dependsOnPlugin(Class<? extends ArtemisPlugin> cls) throws ReflectionException {
        if (ClassReflection.isAbstractClass(cls)) {
            if (!anyAssignableTo(this.plugins, cls)) {
                throw new WorldConfigurationException("Implementation of " + cls + " expected but not found. Did you forget to include a plugin? (for example: logging-libgdx for logging-api)");
            }
        } else {
            if (containsType(this.plugins, cls)) {
                return;
            }
            this.plugins.add(ConfigurationElement.of(ClassReflection.newInstance(cls)));
        }
    }

    public WorldConfigurationBuilder with(int i, BaseSystem... baseSystemArr) {
        addSystems(i, baseSystemArr);
        return this;
    }

    public WorldConfigurationBuilder with(BaseSystem... baseSystemArr) {
        addSystems(0, baseSystemArr);
        return this;
    }

    public WorldConfigurationBuilder with(ArtemisPlugin... artemisPluginArr) {
        addPlugins(artemisPluginArr);
        return this;
    }

    private void addSystems(int i, BaseSystem[] baseSystemArr) {
        for (BaseSystem baseSystem : baseSystemArr) {
            if (containsType(this.systems, baseSystem.getClass())) {
                throw new WorldConfigurationException("System of type " + baseSystem.getClass() + " registered twice. Only once allowed.");
            }
            this.systems.add(new ConfigurationElement<>(baseSystem, i));
        }
    }

    private boolean containsType(Bag bag, Class cls) {
        Iterator it = bag.iterator();
        while (it.hasNext()) {
            if (((ConfigurationElement) it.next()).itemType == cls) {
                return true;
            }
        }
        return false;
    }

    private boolean anyAssignableTo(Bag bag, Class cls) {
        Iterator it = bag.iterator();
        while (it.hasNext()) {
            if (ClassReflection.isAssignableFrom(cls, ((ConfigurationElement) it.next()).itemType)) {
                return true;
            }
        }
        return false;
    }

    private void addPlugins(ArtemisPlugin[] artemisPluginArr) {
        for (ArtemisPlugin artemisPlugin : artemisPluginArr) {
            if (containsType(this.plugins, artemisPlugin.getClass())) {
                throw new WorldConfigurationException("Plugin of type " + artemisPlugin.getClass() + " registered twice. Only once allowed.");
            }
            this.plugins.add(ConfigurationElement.of(artemisPlugin));
        }
    }
}
