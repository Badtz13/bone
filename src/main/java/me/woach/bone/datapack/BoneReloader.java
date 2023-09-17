package me.woach.bone.datapack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.woach.bone.Bone;
import me.woach.bone.datapack.deserializer.BoneDeserializer;
import me.woach.bone.items.GenericBone;
import me.woach.bone.registries.BoneRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class BoneReloader {
    private static final String RESOURCE_FOLDER = "bones";

    public void registerReloadListener() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(
                new SimpleSynchronousResourceReloadListener() {
                    @Override
                    public Identifier getFabricId() {
                        return Bone.getId("bones");
                    }

                    @Override
                    public void reload(ResourceManager manager) {
                        BoneRegistry.empty();
                        Gson gson = new GsonBuilder().registerTypeAdapter(GenericBone.class, new BoneDeserializer()).create();

                        for (Identifier id : manager.findResources("bones", path -> path.toString().endsWith(".json"))) {
                            continue;
                        }
//                                GenericBone bone = gson.fromJson(r, GenericBone.class);
//                                BoneRegistry.register(id, bone);
//                                Bone.LOGGER.error("Error occured while trying to load quest-line, " + id + ", skipping: " + e.getMessage());
                    }
                }
        );
    }
}
