package emi.testing_mod.block.entity.renderer;

import emi.testing_mod.Testing_mod;
import net.minecraft.client.render.VertexConsumer;
import org.joml.Matrix4f;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ObjFileReader {


    //file reading requirements
    File objFile;
    Scanner scanner;

    //object values
    private ArrayList<float[]> vertices;
    private ArrayList<int[]> faces;


    boolean extracted;

    //add texture/shader later
    public ObjFileReader(File file) throws FileNotFoundException {

        this.objFile = file;

        this.scanner = new Scanner(objFile);

        vertices = new ArrayList<float[]>();
        faces = new ArrayList<int[]>();

        Testing_mod.LOGGER.info("File reader created for " + Testing_mod.MOD_ID);

        extracted = false;
    }

    public void render(Matrix4f transform, VertexConsumer verticesCreator)
    {
        if(!extracted)
        {
            Testing_mod.LOGGER.info("Reading file for " + Testing_mod.MOD_ID);
            readObj();

            for(int i = 0; i < vertices.size(); i++){
                Testing_mod.LOGGER.info(vertices.get(i)[0] + " " + vertices.get(i)[1] + " " + vertices.get(i)[2]);
            }

        }

        //go through each face
        for(int i = 0; i < faces.size(); i++)
        {
            renderFace(i, verticesCreator, transform);
        }


    }

    public void readObj(){


        while (scanner.hasNext()) {
            //read line
            String line = scanner.nextLine();

            //determine what to do with info

            if(line.startsWith("v "))
            {
                // extract vertices

                line = line.substring(2);
                String[] coords = line.split(" ");
                float x = Float.parseFloat(coords[0]);
                float y = Float.parseFloat(coords[1]);
                float z = Float.parseFloat(coords[2]);

                vertices.add(new float[]{x,y,z});


            } else if(line.startsWith("f "))
            {
                line = line.substring(2);
                String[] face = line.split(" ");

                int v1 = Integer.parseInt(face[0].split("/")[0]);
                int v2 = Integer.parseInt(face[1].split("/")[0]);
                int v3 = Integer.parseInt(face[2].split("/")[0]);
                int v4 = -1;

                if(face.length == 4)
                {
                    v4 = Integer.parseInt(face[3].split("/")[0]);
                }

                faces.add(new int[]{v1,v2,v3,v4});

            } else if(line.startsWith("vt "))
            {
                //vertex texture code here
            }
        }

        extracted = true;
    }

    public void renderFace(int faceIndex, VertexConsumer verts, Matrix4f transformMatrix){

        int[] face = faces.get(faceIndex);
        float[] pos;
        for(int j = 0; j < face.length; j++) {

            if(face[j] > 0) {
                pos = vertices.get(face[j] - 1);
                verts.vertex(transformMatrix, pos[0], pos[1], pos[2]).next();

            } else {
                pos = vertices.get(face[j-1] - 1);
                verts.vertex(transformMatrix, pos[0], pos[1], pos[2]).next();

            }


        }
    }




}
