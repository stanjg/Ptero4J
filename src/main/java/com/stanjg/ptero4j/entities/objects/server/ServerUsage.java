package com.stanjg.ptero4j.entities.objects.server;

public class ServerUsage {
	private int disk,cpu,memory;
	public ServerUsage(int cpu, int memory, int disk) {
		this.disk = disk;
		this.cpu = cpu;
		this.memory = memory;
	}
	/**
	* @return CPU usage in percent
	*/
	public int getCpuUsage() {
		return cpu;
	}
	/**
	* @return Memory usage in MB
	*/
	public int getMemoryUsage() {
		return memory;
	}
	/**
	* @return Disk usage in MB
	*/
	public int getDiskUsage() {
		return disk;
	}
}
