package emi.testing_mod.block.entity.renderer;

import emi.testing_mod.Testing_mod;
import net.minecraft.client.render.VertexConsumer;
import org.joml.Matrix4f;
import org.joml.Matrix3f;

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
    private ArrayList<float[]> verticesNormals;
    private ArrayList<float[]> vertexTextures;


    private ArrayList<int[]> faces;
    private ArrayList<int[]> faceTextures;
    private ArrayList<int[]> faceNormals;

    int light;
    int overlay;

    boolean extracted;

    //add texture/shader later
    public ObjFileReader(File file) throws FileNotFoundException {

        this.objFile = file;

        this.scanner = new Scanner(objFile);

        vertices = new ArrayList<float[]>();
        verticesNormals = new ArrayList<float[]>();
        vertexTextures = new ArrayList<float[]>();


        faces = new ArrayList<int[]>();
        faceTextures = new ArrayList<int[]>();
        faceNormals = new ArrayList<int[]>();

        Testing_mod.LOGGER.info("File reader created for " + Testing_mod.MOD_ID);

        extracted = false;
    }

    public void render(Matrix4f transform, Matrix3f normal, VertexConsumer verticesCreator, int a, int b)
    {

        light = a;
        overlay = b;

        if(!extracted)
        {
            //Testing_mod.LOGGER.info("Reading file for " + Testing_mod.MOD_ID);
            readObj();

//            for(int i = 0; i < vertexTextures.size(); i++){
//                Testing_mod.LOGGER.info(vertexTextures.get(i)[0] + " " + vertexTextures.get(i)[1]);
//            }

        }

        //go through each face
        for(int i = 0; i < faces.size(); i++)
        {
            renderFace(i, verticesCreator, transform, normal);
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

                int t1 = Integer.parseInt(face[0].split("/")[1]);
                int t2 = Integer.parseInt(face[1].split("/")[1]);
                int t3 = Integer.parseInt(face[2].split("/")[1]);
                int t4 = -1;

                int n1 = Integer.parseInt(face[0].split("/")[2]);
                int n2 = Integer.parseInt(face[1].split("/")[2]);
                int n3 = Integer.parseInt(face[2].split("/")[2]);
                int n4 = -1;



                if(face.length == 4)
                {
                    v4 = Integer.parseInt(face[3].split("/")[0]);
                    t4 = Integer.parseInt(face[3].split("/")[1]);
                    n4 = Integer.parseInt(face[3].split("/")[2]);

                }

                faces.add(new int[]{v1,v2,v3,v4});
                faceTextures.add(new int[]{t1,t2,t3,t4});
                faceNormals.add(new int[]{n1,n2,n3,n4});



            } else if(line.startsWith("vt "))
            {
                //extract vertex textures
                line = line.substring(3);
                String[] textures = line.split(" ");

                float t1 = Float.parseFloat(textures[0]);
                float t2 = Float.parseFloat(textures[1]);

                vertexTextures.add(new float[]{t1,t2});

            } else if(line.startsWith("vn "))
            {
                //extract vertex normal
                line = line.substring(3);
                String[] normal = line.split(" ");
                float n1 = Float.parseFloat(normal[0]);
                float n2 = Float.parseFloat(normal[1]);
                float n3 = Float.parseFloat(normal[2]);
                verticesNormals.add(new float[]{n1,n2,n3});
            }
        }

        extracted = true;
    }

    public void renderFace(int faceIndex, VertexConsumer verts, Matrix4f transformMatrix, Matrix3f normalMatrix){

        int[] face = faces.get(faceIndex);
        int[] faceNormal = faceNormals.get(faceIndex);
        int[] faceTexture = faceTextures.get(faceIndex);
        float[] pos;
        float[] normal;
        float[] texture;
        for(int j = 0; j < face.length; j++) {

            if(face[j] > 0) {
                pos = vertices.get(face[j] - 1);
                normal = verticesNormals.get(faceNormal[j] - 1);
                texture = vertexTextures.get(faceTexture[j] - 1);

            } else {
                pos = vertices.get(face[j-1] - 1);
                normal = verticesNormals.get(faceNormal[j-1] - 1);
                texture = vertexTextures.get(faceTexture[j-1] - 1);

            }

            verts.vertex(transformMatrix, pos[0], pos[1], pos[2]);
            verts.color(1F,1F,1F,1F);
            verts.texture(texture[0],texture[1]);
            verts.overlay(overlay); // got 0 idea what overlay and light is
            verts.light(light);
            verts.normal(normalMatrix,normal[0],normal[1],normal[2]).next();


        }
    }




}
