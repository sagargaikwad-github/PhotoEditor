package com.example.photoeditor.tools;

public class ToolsData {
    int tool_icon;
    String tool_name;

    public ToolsData(int tool_icon, String tool_name) {
        this.tool_icon = tool_icon;
        this.tool_name = tool_name;
    }

    public int getTool_icon() {
        return tool_icon;
    }

    public void setTool_icon(int tool_icon) {
        this.tool_icon = tool_icon;
    }

    public String getTool_name() {
        return tool_name;
    }

    public void setTool_name(String tool_name) {
        this.tool_name = tool_name;
    }
}
