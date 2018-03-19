package com.stanjg.ptero4j.entities.objects.server.creation;

import org.json.JSONObject;

public class CreationServerLimits {

    private int disk, memory, swap, io, cpu;

    public CreationServerLimits() { }

    public CreationServerLimits(int disk, int memory, int swap, int io, int cpu) {
        this.disk = disk;
        this.memory = memory;
        this.swap = swap;
        this.io = io;
        this.cpu = cpu;
    }

    public JSONObject getAsJSON() {
        return new JSONObject()
                .put("memory", memory)
                .put("swap", swap)
                .put("disk", disk)
                .put("io", io)
                .put("cpu", cpu);
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

    public void setDisk(int disk) {
        this.disk = disk;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public void setSwap(int swap) {
        this.swap = swap;
    }

    public void setIo(int io) {
        this.io = io;
    }

    public void setCpu(int cpu) {
        this.cpu = cpu;
    }
}
