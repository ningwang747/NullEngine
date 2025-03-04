package nullengine.client.asset.model.voxel;

import com.google.gson.JsonArray;
import nullengine.client.rendering.texture.TextureAtlasPart;
import org.joml.Vector3fc;
import org.joml.Vector4fc;

import java.util.List;
import java.util.Map;

class ModelData {

    Map<String, String> textures;
    List<Element> elements;
    JsonArray rawElements;

    static class Element {
        static class Cube extends Element {
            Vector3fc from;
            Vector3fc to;
            Face[] faces;

            static class Face {
                String texture;
                TextureAtlasPart _texture;
                Vector4fc uv;
                boolean[] cullFace = new boolean[6];
            }
        }
    }
}
