package borknbeans.lightweighthud;

public enum GuiPosition {
    TOP_LEFT,
    TOP_RIGHT,
    BOTTOM_RIGHT,
    BOTTOM_LEFT;

    private final int PADDING = 1;

    public int[] getCoordinates(int screenWidth, int screenHeight) {
        int x = 0, y = 0;

        switch (this) {
            case TOP_LEFT:
                x = 0 + PADDING;
                y = 0 + PADDING;
                break;
            case TOP_RIGHT:
                x = screenWidth - 16 - PADDING;
                y = 0 + PADDING;
                break;
            case BOTTOM_RIGHT:
                x = screenWidth - 16 - PADDING;
                y = screenHeight - 16 - PADDING;
                break;
            case BOTTOM_LEFT:
                x = 0 + PADDING;
                y = screenHeight - 16 - PADDING;
                break;
        }

        return new int[] {x, y};
    }
}
