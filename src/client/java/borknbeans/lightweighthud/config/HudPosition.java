package borknbeans.lightweighthud.config;

import net.minecraft.client.MinecraftClient;

public enum HudPosition {
    TOP_LEFT,
    TOP_MIDDLE,
    TOP_RIGHT,
    MIDDLE_RIGHT,
    BOTTOM_RIGHT,
    BOTTOM_MIDDLE,
    BOTTOM_LEFT,
    MIDDLE_LEFT;

    public int[] getCoordinates() {
        MinecraftClient client = MinecraftClient.getInstance();
        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        int padding = 1;

        int leftX = padding;
        int middleX = screenWidth / 2;
        int rightX = screenWidth - padding;

        int topY = padding;
        int middleY = screenHeight / 2;
        int bottomY = screenHeight - padding;

        int x = 0, y = 0;
        switch (this) {
            case TOP_LEFT:
                x = leftX;
                y = topY;
                break;
            case TOP_MIDDLE:
                x = middleX;
                y = topY;
                break;
            case TOP_RIGHT:
                x = rightX;
                y = topY;
                break;
            case MIDDLE_RIGHT:
                x = rightX;
                y = middleY;
                break;
            case BOTTOM_RIGHT:
                x = rightX;
                y = bottomY;
                break;
            case BOTTOM_MIDDLE:
                x = middleX;
                y = bottomY;
                break;
            case BOTTOM_LEFT:
                x = leftX;
                y = bottomY;
                break;
            case MIDDLE_LEFT:
                x = leftX;
                y = middleY;
                break;

        }

        return new int[] {x, y};
    }

    public boolean isOnTop() {
        return this == TOP_LEFT || this == TOP_MIDDLE || this == TOP_RIGHT;
    }

    public boolean isOnBottom() {
        return this == BOTTOM_LEFT || this == BOTTOM_MIDDLE || this == BOTTOM_RIGHT;
    }

    public boolean isOnRight() {
        return this == TOP_RIGHT || this == MIDDLE_RIGHT || this == BOTTOM_RIGHT;
    }

    public boolean isOnLeft() {
        return this == TOP_LEFT || this == MIDDLE_LEFT || this == BOTTOM_LEFT;
    }

    public boolean isOnMiddleHorizontal() {
        return this == TOP_MIDDLE || this == BOTTOM_MIDDLE;
    }
}
