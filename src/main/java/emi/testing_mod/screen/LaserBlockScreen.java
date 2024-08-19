package emi.testing_mod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import emi.testing_mod.Testing_mod;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class LaserBlockScreen extends HandledScreen<LaserBlockScreenHandler> {

    private static final Identifier TEXTURE = new Identifier(Testing_mod.MOD_ID,"textures/gui/laser_block_gui.png");

    public LaserBlockScreen(LaserBlockScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    //change shader values to learn what they do later
    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1,1,1,1);
        RenderSystem.setShaderTexture(0,TEXTURE);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(TEXTURE,x,y,0,0,backgroundWidth,backgroundHeight);

        renderProgressArrow(context,x,y);
    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if(handler.isCrafting()){
            context.drawTexture(TEXTURE,x+85,y+30,176,0,8,handler.getScaledProgress());
        }
    }

    //changes where the titles are placed
    //temp values, change later
    @Override
    protected void init() {
        super.init();
        titleX = 60;

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context,mouseX,mouseY,delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context,mouseX,mouseY);
    }
}
