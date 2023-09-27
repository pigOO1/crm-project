package com.powernode.crm.workbench.domain;

public class FunnelVo {
    private String name;
    private int value;

    @Override
    public String toString() {
        return "FunnelVo{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public FunnelVo(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public FunnelVo() {
    }
}
