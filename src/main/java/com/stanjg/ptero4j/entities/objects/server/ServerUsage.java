package com.stanjg.ptero4j.entities.objects.server;

public class ServerUsage {
	private int disk,cpu,memory;
	public ServerUsage(int cpu, int memory, int disk) {
		this.disk = disk;
		this.cpu = cpu;
		this.memory = memory;
	}
	public int getCpuUsage() {
		return cpu;
	}
	public int getMemoryUsage() {
		return memory;
	}
	public int getDiskUsage() {
		return disk;
	}
}
