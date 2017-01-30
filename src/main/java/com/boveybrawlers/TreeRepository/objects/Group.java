package com.boveybrawlers.TreeRepository.objects;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private List<Tree> list = new ArrayList<>();
    private String name;

    public Group(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getNiceName() {
        return this.name.substring(0, 1).toUpperCase() + this.name.substring(1);
    }

    public List<Tree> getList() {
        return this.list;
    }

    public void add(Tree tree) {
        this.list.add(tree);
    }

}
