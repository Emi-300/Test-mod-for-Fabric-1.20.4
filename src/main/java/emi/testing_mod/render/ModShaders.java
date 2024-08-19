package emi.testing_mod.render;

import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.RenderPhase;

public class ModShaders extends RenderPhase{
    public static final String LASER_SHADER_ID = "rendertype_energy_orb";
    public static RenderPhase.ShaderProgram laserShaderProgram;

    public ModShaders(String name, Runnable beginAction, Runnable endAction) {
        super(name, beginAction, endAction);
    }

    static{
        laserShaderProgram = new ShaderProgram();
    }
}
