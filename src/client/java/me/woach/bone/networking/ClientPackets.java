package me.woach.bone.networking;

import me.woach.bone.networking.packet.ItemStackSyncServer2ClientPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import static me.woach.bone.networking.Packets.ITEM_SYNC;

public class ClientPackets {

    public static void RegisterServer2ClientPackets() {
        ClientPlayNetworking.registerGlobalReceiver(ITEM_SYNC,
                ItemStackSyncServer2ClientPacket::receive);
    }
}
