package dundigundi.betterthanfarming.gui;

import dundigundi.betterthanfarming.block.entity.TileEntityStove;
import net.minecraft.client.gui.GuiContainer;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.player.inventory.InventoryPlayer;
import org.lwjgl.opengl.GL11;

public class GuiStove extends GuiContainer {
	private final TileEntityStove tileEntity;

	public GuiStove(InventoryPlayer inventory, TileEntityStove tileEntity) {
		super(new ContainerStove(inventory, tileEntity));
		this.xSize = 176;
		this.tileEntity = tileEntity;
	}

	@Override
	protected void drawGuiContainerForegroundLayer() {
		I18n i18n = I18n.getInstance();
		this.fontRenderer.drawString(i18n.translateKey("gui.betterthanfarming.stove.label.stove"), 75, 4, 4210752);
		this.fontRenderer.drawString(i18n.translateKey("gui.betterthanfarming.stove.label.inventory"), 8, this.ySize - 96 + 4, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f) {
		int texture = mc.renderEngine.getTexture("/assets/betterthanfarming/gui/stove.png");
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.renderEngine.bindTexture(texture);

		int scrnX = (width - xSize) / 2;
		int scrnY = (height - ySize) / 2;
		drawTexturedModalRect(scrnX, scrnY, 0, 0, xSize, ySize);

		if (tileEntity.isBurning()){
			int burnTime = tileEntity.getBurnTimeRemainingScaled(12);
			this.drawTexturedModalRect(scrnX + 80, scrnY + 24 + 12 - burnTime, 176, 12 - burnTime, 14, burnTime + 2);
		}
	}
}
