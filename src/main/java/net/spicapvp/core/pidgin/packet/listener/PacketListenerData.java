package net.spicapvp.core.pidgin.packet.listener;

import java.lang.reflect.Method;

import net.spicapvp.core.pidgin.packet.Packet;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PacketListenerData {

	private Object instance;
	private Method method;
	private Class packetClass;

	public boolean matches(Packet packet) {
		return this.packetClass == packet.getClass();
	}

}
