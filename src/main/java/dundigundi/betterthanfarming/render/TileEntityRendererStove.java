package dundigundi.betterthanfarming.render;

import dundigundi.betterthanfarming.block.entity.TileEntityStove;
import dundigundi.betterthanfarming.item.ItemIngredient;
import net.minecraft.client.render.EntityRenderDispatcher;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TextureFX;
import net.minecraft.client.render.tileentity.TileEntityRenderer;
import net.minecraft.core.Global;
import net.minecraft.core.block.entity.TileEntityMobSpawner;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityDispatcher;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class TileEntityRendererStove extends TileEntityRenderer<TileEntityStove> {
	private Random random = new Random();
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

	//2D RENDERING OF ITEMS

	/*public void doRenderItem(ItemStack itemstack, double d, double d1, double d2, float f, float f1) {
		if (itemstack == null) {
			return;
		}

		Item item = itemstack.getItem();
		if (item == null) {
			return;
		}

		GL11.glPushMatrix();
		GL11.glEnable(32826);

		int tileWidth;

		GL11.glScalef(0.5f, 0.5f, 0.5f);

		int i;
		if (item.id != Item.foodPorkchopRaw.id){
			ItemIngredient ingredient = (ItemIngredient)item;
			i = ingredient.getPlaceOnTexture() - 1;
		}else{
			i = 3;
		}

		this.loadTexture("assets/betterthanfarming/gui/uncooked_foods.png");
		tileWidth = TextureFX.tileWidthTerrain;

		Tessellator tessellator = Tessellator.instance;
		float f6 = (float)(i % Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth) / (float)(Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth);
		float f8 = (float)(i % Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth + tileWidth) / (float)(Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth);
		float f10 = (float)(i / Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth) / (float)(Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth);
		float f11 = (float)(i / Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth + tileWidth) / (float)(Global.TEXTURE_ATLAS_WIDTH_TILES * tileWidth);

		int k = Item.itemsList[itemstack.itemID].getColorFromDamage(itemstack.getMetadata());
		float f15 = (float)(k >> 16 & 0xFF) / 255.0f;
		float f17 = (float)(k >> 8 & 0xFF) / 255.0f;
		float f19 = (float)(k & 0xFF) / 255.0f;
		float f21 = 1.0f;

		GL11.glColor4f(f15 * f21, f17 * f21, f19 * f21, 1.0f);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d + 0.2f, (float) d1 + 1, (float) d2 + 0.3f);
		GL11.glRotatef(180.0f - this.renderDispatcher.viewLerpYaw, 0f, 1.0f, 0.0f);

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0f, 1.0f, 0.0f);
		tessellator.addVertexWithUV(-0.2, -0.1, 0.0, f6, f11);
		tessellator.addVertexWithUV(0.2, -0.1, 0.0, f8, f11);
		tessellator.addVertexWithUV(0.2, 0.3, 0.0, f8, f10);
		tessellator.addVertexWithUV(-0.2, 0.3, 0.0, f6, f10);
		tessellator.draw();

		GL11.glPopMatrix();
		GL11.glDisable(32826);
		GL11.glPopMatrix();
	}*/

	@Override
	public void doRender(TileEntityStove tileEntity, double x, double y, double z, float g) {
		float offsetX = 0.3f;
		float offsetZ = 0.4f;
		float relativeX = 0;
		float relativeZ = 0;
		/* we need this shape:
			#	#
			#	#
			#	#
		 */
		for (int i = 0; i < tileEntity.contentsToCook.size(); i++){
			if (relativeX < offsetX * 3) {
				EntityItem entityItem = new EntityItem(tileEntity.worldObj, x, y, z, new ItemStack(tileEntity.contentsToCook.get(i), 1));
				this.doRenderItem(entityItem, x + relativeX, y, z + relativeZ, 0, 0);
				relativeX += offsetX;
				if (relativeX == offsetX * 3) {
					relativeX = 0;
					if (relativeZ < offsetZ * 2) {
						relativeZ += offsetZ;
					}
				}
				tileEntity.worldObj.entityJoinedWorld(entityItem);
				//TODO entityItem.remove(); give them some order 66 (somehow you should kill the entityItem after the result being dropped
			}
		}
	}
}
