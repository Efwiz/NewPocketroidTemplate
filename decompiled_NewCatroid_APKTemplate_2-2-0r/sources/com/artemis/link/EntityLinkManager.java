package com.artemis.link;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.Component;
import com.artemis.ComponentType;
import com.artemis.ComponentTypeFactory;
import com.artemis.annotations.SkipWire;
import com.artemis.utils.Bag;
import com.artemis.utils.reflect.ClassReflection;
import com.artemis.utils.reflect.Field;
import com.artemis.utils.reflect.ReflectionException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes8.dex */
@SkipWire
public class EntityLinkManager extends BaseEntitySystem {
    final Bag<LinkSite> decoratedLinkSites;
    private final boolean fireEventsOnRegistration;
    final Bag<LinkSite> linkSites;
    private final boolean requireListener;

    public EntityLinkManager(boolean z, boolean z2) {
        super(Aspect.all());
        this.linkSites = new Bag<>();
        this.decoratedLinkSites = new Bag<>();
        this.requireListener = !z;
        this.fireEventsOnRegistration = z2;
    }

    public EntityLinkManager() {
        this(true, true);
    }

    @Override // com.artemis.BaseSystem
    protected void initialize() {
        this.world.getComponentManager().getTypeFactory().register(new LinkCreateListener(this));
    }

    @Override // com.artemis.BaseSystem
    protected void processSystem() {
        if (this.requireListener) {
            process(this.decoratedLinkSites);
        } else {
            process(this.linkSites);
        }
    }

    private void process(Bag<LinkSite> bag) {
        Iterator<LinkSite> it = bag.iterator();
        while (it.hasNext()) {
            it.next().process();
        }
    }

    public void register(Class<? extends Component> cls, LinkListener linkListener) {
        register(cls, null, linkListener);
    }

    public void register(Class<? extends Component> cls, String str, LinkListener linkListener) {
        Field declaredField;
        this.world.inject(linkListener);
        if (str != null) {
            try {
                declaredField = ClassReflection.getDeclaredField(cls, str);
            } catch (ReflectionException e) {
                throw new RuntimeException(e);
            }
        } else {
            declaredField = null;
        }
        ComponentType typeFor = this.world.getComponentManager().getTypeFactory().getTypeFor(cls);
        for (LinkSite linkSite : this.linkSites) {
            if (typeFor.equals(linkSite.type) && (declaredField == null || linkSite.field.equals(declaredField))) {
                linkSite.listener = linkListener;
                if (!this.decoratedLinkSites.contains(linkSite)) {
                    this.decoratedLinkSites.add(linkSite);
                }
                if (this.fireEventsOnRegistration) {
                    linkSite.inserted(linkSite.subscription.getEntities());
                }
            }
        }
    }

    private static class LinkCreateListener implements ComponentTypeFactory.ComponentTypeListener {
        private final EntityLinkManager elm;
        private final LinkFactory linkFactory;

        public LinkCreateListener(EntityLinkManager entityLinkManager) {
            this.elm = entityLinkManager;
            this.linkFactory = new LinkFactory(entityLinkManager.getWorld());
        }

        @Override // com.artemis.ComponentTypeFactory.ComponentTypeListener
        public void initialize(Bag<ComponentType> bag) {
            int size = bag.size();
            for (int i = 0; size > i; i++) {
                onCreated(bag.get(i));
            }
        }

        @Override // com.artemis.ComponentTypeFactory.ComponentTypeListener
        public void onCreated(ComponentType componentType) {
            Bag<LinkSite> bagCreate = this.linkFactory.create(componentType);
            if (bagCreate.isEmpty()) {
                return;
            }
            int size = bagCreate.size();
            for (int i = 0; size > i; i++) {
                this.elm.linkSites.add(bagCreate.get(i));
            }
        }
    }
}
