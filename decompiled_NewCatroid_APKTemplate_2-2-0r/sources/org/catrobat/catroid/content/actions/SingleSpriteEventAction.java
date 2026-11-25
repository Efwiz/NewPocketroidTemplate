package org.catrobat.catroid.content.actions;

import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import org.catrobat.catroid.content.Sprite;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SingleSpriteEventAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SingleSpriteEventAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\n\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R!\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048TX\u0094\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007R\u001c\u0010\n\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/catrobat/catroid/content/actions/SingleSpriteEventAction;", "Lorg/catrobat/catroid/content/actions/EventAction;", "()V", "receivingSprites", "", "Lorg/catrobat/catroid/content/Sprite;", "getReceivingSprites", "()Ljava/util/List;", "receivingSprites$delegate", "Lkotlin/Lazy;", "sprite", "getSprite", "()Lorg/catrobat/catroid/content/Sprite;", "setSprite", "(Lorg/catrobat/catroid/content/Sprite;)V", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class SingleSpriteEventAction extends EventAction {

    /* JADX INFO: renamed from: receivingSprites$delegate, reason: from kotlin metadata */
    private final Lazy receivingSprites = LazyKt.lazy(new Function0<List<? extends Sprite>>() { // from class: org.catrobat.catroid.content.actions.SingleSpriteEventAction$receivingSprites$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final List<? extends Sprite> invoke() {
            List<? extends Sprite> listListOf;
            Sprite sprite = this.this$0.getSprite();
            return (sprite == null || (listListOf = CollectionsKt.listOf(sprite)) == null) ? CollectionsKt.emptyList() : listListOf;
        }
    });
    private Sprite sprite;

    @Override // org.catrobat.catroid.content.actions.EventAction
    protected List<Sprite> getReceivingSprites() {
        return (List) this.receivingSprites.getValue();
    }

    public final Sprite getSprite() {
        return this.sprite;
    }

    public final void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
