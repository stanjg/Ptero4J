package com.stanjg.ptero4j.entities.objects.server;

import org.json.JSONObject;

public class ServerLimits {

    private int disk, memory, swap, io, cpu;

    public ServerLimits(JSONObject json) {
        this(
                json.getInt("disk"),
                json.getInt("memory"),
                json.getInt("swap"),
                json.getInt("io"),
                json.getInt("cpu")
        );
    }

    private ServerLimits(int disk, int memory, int swap, int io, int cpu) {
        this.disk = disk;
        this.memory = memory;
        this.swap = swap;
        this.io = io;
        this.cpu = cpu;
    }

    public int getDisk() {
        return disk;
    }

    public int getMemory() {
        return memory;
    }

    public int getSwap() {
        return swap;
    }

    public int getIo() {
        return io;
    }

    public int getCpu() {
        return cpu;
    }
}
