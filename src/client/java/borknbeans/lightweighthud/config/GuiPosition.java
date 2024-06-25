package borknbeans.lightweighthud.config;

public enum GuiPosition {
    TOP_LEFT,
    TOP_RIGHT,
    BOTTOM_RIGHT,
    BOTTOM_LEFT;

    private final int PADDING = 1;

    public int[] getCoordinates(int screenWidth, int screenHeight, int xOffset, int yOffset) {
        int x = 0, y = 0;

        switch (this) {
            case TOP_LEFT:
                x = PADDING + xOffset;
                y = PADDING + yOffset;
                break;
            case TOP_RIGHT:
                x = screenWidth - 16 - PADDING + xOffset;
                y = PADDING + yOffset;
                break;
            case BOTTOM_RIGHT:
                x = screenWidth - 16 - PADDING  + xOffset;
                y = screenHeight - 16 - PADDING + yOffset;
                break;
            case BOTTOM_LEFT:
                x = PADDING  + xOffset;
                y = screenHeight - 16 - PADDING + yOffset;
                break;
        }

        return new int[] {x, y};
    }
}
