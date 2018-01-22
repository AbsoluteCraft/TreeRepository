package com.boveybrawlers.TreeRepository.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Pagination {

    private int page;
    private int perPage;
    private int from;
    private int to;
    private int listSize;

    public Pagination(int page, int perPage, int listSize) {
        this.page = page;
        this.perPage = perPage;
        this.from = page * perPage - perPage;
        this.to = page * perPage - 1;
        this.listSize = listSize;
    }

    public static Pagination parseArgs(String[] args, int argIndex, int perPage, int listSize) {
        int page = 1;
        if(args.length > argIndex) {
            try {
                page = Integer.parseInt(args[argIndex]);
            } catch(NumberFormatException ex) {
                return null;
            }
        }

        return new Pagination(page, perPage, listSize);
    }

    public int getPage() {
        return this.page;
    }

    public boolean hasNextPage() {
        return this.listSize > this.page * this.perPage;
    }

    public int getFrom() {
        return this.from;
    }

    public int getTo() {
        return this.to;
    }

    public TextComponent create(String prevCmd, String nextCmd) {
        TextComponent actionPrev = new TextComponent("[<- Previous]");
        TextComponent actionNext = new TextComponent("[Next ->]");

        prevCmd = prevCmd.replace("{prev}", this.page - 1 + "");
        nextCmd = nextCmd.replace("{next}", this.page + 1 + "");

        if(this.page > 1) {
            actionPrev.setColor(ChatColor.WHITE);
            actionPrev.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, prevCmd));
            actionPrev.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Go to the previous page").create()));
        } else {
            actionPrev.setColor(net.md_5.bungee.api.ChatColor.DARK_GRAY);
        }

        if(this.hasNextPage()) {
            actionNext.setColor(ChatColor.WHITE);
            actionNext.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, nextCmd));
            actionNext.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Go to the next page").create()));
        } else {
            actionNext.setColor(net.md_5.bungee.api.ChatColor.DARK_GRAY);
        }

        // Generate spacing to right align the Next button
        int chars = actionPrev.toString().length() + actionNext.toString().length();
        String spacing = Str.generateSpacing(32 - chars);
        actionPrev.addExtra(spacing);

        actionPrev.addExtra(actionNext);

        return actionPrev;
    }

}
