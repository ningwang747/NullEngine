package nullengine.entity.item;

import nullengine.entity.BaseEntity;
import nullengine.item.ItemStack;
import nullengine.world.World;
import org.joml.Vector3dc;

public class ItemEntity extends BaseEntity {

    private ItemStack itemStack;

    public ItemEntity(int id, World world, ItemStack itemStack) {
        super(id, world);
        this.itemStack = itemStack;
    }

    public ItemEntity(int id, World world, Vector3dc position, ItemStack itemStack) {
        super(id, world, position);
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public void tick() {
        super.tick();
    }
}
