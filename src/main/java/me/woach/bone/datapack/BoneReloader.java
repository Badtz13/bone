package me.woach.bone.datapack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.woach.bone.Bone;
import me.woach.bone.datapack.deserializer.BoneDeserializer;
import me.woach.bone.registries.BoneRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

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
                        Gson gson = new GsonBuilder().registerTypeAdapter(AbstractBone.class, new BoneDeserializer()).create();

                        for (Map.Entry<Identifier, Resource> entry :
                                manager.findResources(RESOURCE_FOLDER, path -> path.toString().endsWith(".json")).entrySet()) {
                            Identifier id = entry.getKey();
                            Resource resource = entry.getValue();
                            try (Reader r = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
                               AbstractBone bone = gson.fromJson(r, AbstractBone.class);
                               bone.info();
                               BoneRegistry.register(id, bone);
                            } catch (Exception e) {
                                Bone.LOGGER.error("Error occured while trying to load bone, " + id + ", skipping: " + e.getMessage());
                            }
                        }
                    }
                }
        );
    }
}
