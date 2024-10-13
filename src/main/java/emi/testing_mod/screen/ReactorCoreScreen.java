package emi.testing_mod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import emi.testing_mod.Testing_mod;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ReactorCoreScreen extends HandledScreen<ReactorCoreScreenHandler> {

    private static final Identifier TEXTURE = new Identifier(Testing_mod.MOD_ID,"textures/gui/reactor_core_gui.png");

    float delta;

    private static boolean[] invArray;
    public ReactorCoreScreen(ReactorCoreScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);


    }


    //change shader values to learn what they do later
    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1,1,1,1);
        RenderSystem.setShaderTexture(0,TEXTURE);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2 - 20;

        context.drawTexture(TEXTURE,x,y,0,0,backgroundWidth,backgroundHeight + 50);

        renderPower(context,x,y,delta);
        renderHeat(context,x,y,delta);
        renderBoxes(context,x,y);




        if(mouseInBox(mouseX,mouseY,x+11,y+24,x+11+13,y+24 + 79))
            context.drawTooltip(textRenderer,Text.literal("Power: " + handler.getScaledPower() + " gJ"),mouseX,mouseY);

        if(mouseInBox(mouseX,mouseY,x+153,y+24,x+153+13,y+24 + 79))
            context.drawTooltip(textRenderer,Text.literal("Heat: " + ((float)(handler.getScaledHeat()) * 100 + (1000 *delta) + 300) + " K"),mouseX,mouseY);

    }

    private void renderPower(DrawContext context, int x, int y,float delta) {
        if(handler.isPowered()){
            context.drawTexture(TEXTURE,x+11,y+24 + 79 - handler.getScaledPower(),177,79 - handler.getScaledPower(),13,handler.getScaledPower());
        }
    }

    private void renderHeat(DrawContext context, int x, int y,float delta) {
        if(handler.isHeated()){
            context.drawTexture(TEXTURE,x+153,y+24 + 79 - handler.getScaledHeat(),177 + 14,79 - handler.getScaledHeat(),13,handler.getScaledHeat());
        }
    }

    private void renderBoxes(DrawContext context, int x, int y)
    {
        invArray = handler.getInvArray();


        int offset = 4 - y;
        int offsetx = 4 - x;

        if(invArray[0])
            context.drawTexture(TEXTURE,36 - offsetx,56 -offset,177+28,0,23,23);

        if(invArray[1])
            context.drawTexture(TEXTURE,48 - offsetx,22 -offset,177+28,0,23,23);

        if(invArray[2])
            context.drawTexture(TEXTURE,80 - offsetx,11 - offset,177+28,0,23,23);

        if(invArray[3])
            context.drawTexture(TEXTURE,113 - offsetx,22 -offset,177+28,0,23,23);

        if(invArray[4])
            context.drawTexture(TEXTURE,125 - offsetx,56 -offset,177+28,0,23,23);

        if(invArray[5])
            context.drawTexture(TEXTURE,113 - offsetx,89 -offset,177+28,0,23,23);

        if(invArray[6])
            context.drawTexture(TEXTURE,80 - offsetx,100 -offset,177+28,0,23,23);

        if(invArray[7])
            context.drawTexture(TEXTURE,48 - offsetx,89 -offset,177+28,0,23,23);



    }

    //changes where the titles are placed
    //temp values, change later
    @Override
    protected void init() {
        super.init();
        titleX = 60;
        titleY = -20;

    }

    @Override
    protected void drawMouseoverTooltip(DrawContext context, int x, int y) {
        super.drawMouseoverTooltip(context, x, y);
    }


    public boolean mouseInBox(int mouseX, int mouseY, int x, int y, int x2, int y2)
    {
        if( mouseX > x && mouseX < x2 && mouseY > y && mouseY < y2)
            return true;
        return false;
    }


    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context,mouseX,mouseY,delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context,mouseX,mouseY);
    }

}
