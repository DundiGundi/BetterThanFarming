package dundigundi.betterthanfarming.render;

import dundigundi.betterthanfarming.block.entity.TileEntityStove;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.tileentity.TileEntityRenderer;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class TileEntityRendererStove extends TileEntityRenderer<TileEntityStove> {
	private List<EntityItem> entityItems = new ArrayList<EntityItem>();
	public void doRenderItem(EntityItem entityItem, double d, double d1, double d2, float f, float f1) {
		ItemStack itemStack = entityItem.item;
		if (itemStack == null) {
			return;
		}

		Item item = itemStack.getItem();
		if (item == null) {
			return;
		}

		GL11.glPushMatrix();
		GL11.glEnable(32826);

		int k = Item.itemsList[itemStack.itemID].getColorFromDamage(itemStack.getMetadata());
		float f15 = (float)(k >> 16 & 0xFF) / 255.0f;
		float f17 = (float)(k >> 8 & 0xFF) / 255.0f;
		float f19 = (float)(k & 0xFF) / 255.0f;
		float f21 = 1.0f;
		GL11.glColor4f(f15 * f21, f17 * f21, f19 * f21, 1.0f);

		GL11.glTranslatef((float) d + 0.07f, (float) d1 + 1, (float) d2 + 0.15f);
		GL11.glRotatef(90, 1.0f, 0, 0);
		GL11.glScaled(0.3f, 0.3f, 0.3f);
		EntityRenderDispatcher.instance.itemRenderer.renderItem(entityItem, itemStack, false);

		GL11.glDisable(32826);
		GL11.glPopMatrix();
	}

	@Override
	public void doRender(TileEntityStove tileEntity, double x, double y, double z, float g) {
		float offsetX = tileEntity.itemRenderOffsetX;
		float offsetZ = tileEntity.itemRenderOffsetZ;
		float relativeX = tileEntity.itemRenderRelativeX;
		float relativeZ = tileEntity.itemRenderRelativeZ;
		/* we need this shape:
			#	#
			#	#
			#	#
		 */
		for (int i = 0; i < tileEntity.contentsToCook.size(); i++){
			if (relativeX < offsetX * 3) {
				if (entityItems.size() < i + 1){
					entityItems.add(new EntityItem(tileEntity.worldObj, x, y, z, new ItemStack(tileEntity.contentsToCook.get(i), 1)));
					tileEntity.worldObj.entityJoinedWorld(entityItems.get(i));
				}else {
					entityItems.set(i, new EntityItem(tileEntity.worldObj, x, y, z, new ItemStack(tileEntity.contentsToCook.get(i), 1)));
				}
				this.doRenderItem(entityItems.get(i), x + relativeX, y, z + relativeZ, 0, 0);
				relativeX += offsetX;
				if (relativeX == offsetX * 3) {
					relativeX = 0;
					if (relativeZ < offsetZ * 2) {
						relativeZ += offsetZ;
					}
				}
			}
		}
	}
}
