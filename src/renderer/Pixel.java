package renderer;

/**
 * A record representing a pixel in an image, defined by its row and column.
 *
 * @param row the row
 * @param col the column
 */
record Pixel(int row, int col) {
    /**
     * The maximum number of rows in the image
     */
    private static int maxRows = 0;
    /**
     * The maximum number of columns in the image
     */
    private static int maxCols = 0;
    /**
     * The total number of pixels in the image (maxRows * maxCols)
     */
    private static long totalPixels = 0L;
    /**
     * The current row being processed (volatile for thread safety)
     */
    private static volatile int cRow = 0;
    /**
     * The current column being processed (volatile for thread safety)
     */
    private static volatile int cCol = -1;
    /**
     * The number of pixels processed so far (volatile for thread safety)
     */
    private static volatile long pixels = 0L;
    /**
     * The last printed percentage of completion (volatile for thread safety)
     */
    private static volatile int lastPrinted = 0;
    /**
     * Flag indicating whether to print progress updates
     */
    private static boolean print = false;
    /**
     * The interval at which progress is printed (in tenths of a percent)
     */
    private static long printInterval = 100L;
    /**
     * The format string used for printing progress updates
     */
    private static final String PRINT_FORMAT = "%5.1f%%\r";
    /**
     * Mutex for synchronizing access to the next pixel generation
     */
    private static final Object mutexNext = new Object();
    /**
     * Mutex for synchronizing access to pixel processing counters
     */
    private static final Object mutexPixels = new Object();

    /**
     * function that initializes the values
     *
     * @param maxRows  the maximum values of rows
     * @param maxCols  the maximum values of columns
     * @param interval the print interval
     */
    static void initialize(int maxRows, int maxCols, double interval) {
        Pixel.maxRows = maxRows;
        Pixel.maxCols = maxCols;
        Pixel.totalPixels = (long) maxRows * maxCols;
        printInterval = (int) (interval * 10);
        if (print = (printInterval != 0))
            System.out.printf(PRINT_FORMAT, 0d);
    }


    /**
     * a function that creates the next pixel that need to be colored
     *
     * @return the next pixel or null
     */
    static Pixel nextPixel() {
        synchronized (mutexNext) {
            if (cRow == maxRows) return null;
            ++cCol;
            if (cCol < maxCols) return new Pixel(cRow, cCol);
            cCol = 0;
            ++cRow;
            if (cRow < maxRows) return new Pixel(cRow, cCol);
            cRow = 0;
            pixels = 0;
        }
        return null;
    }

//    /**
//     * Internal function for thread-safe manipulating of main follow up Pixel object - this function is
//     * critical section for all the threads, and main Pixel object data is the shared data of this critical* section.<br/>
//     * The function provides next pixel number each call.
//     * @param target target secondary Pixel object to copy the row/column of the next pixel
//     * @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is* finished, any other value - the progress percentage (only when it changes)
//     */
//    private synchronized int nextP(Pixel target) {
//        ++cCol; ++counter;
//        if (col < maxCols) {
//            target.cRow = this.row; target.cCol = this.col;
//            if (print && counter == nextCounter) {
//                ++percents;
//                nextCounter = pixels * (percents + 1) / 100;
//                return percents;}
//            return 0;
//        }
//        ++cRow;
//        if (row < maxRows) {
//            cCol = 0;
//            if (print && counter == nextCounter) {
//                ++percents;
//                nextCounter = pixels * (percents + 1) / 100;
//                return percents;}
//            return 0;
//        }
//        return -1;
//    }

    /**
     * function that prints the percent if the pixel is done and continue
     * if the work is not done yet
     */
    static void pixelDone() {
        boolean flag = false;
        int percentage = 0;
        synchronized (mutexPixels) {
            ++pixels;
            if (print) {
                percentage = (int) (1000l * pixels / totalPixels);
                if (percentage - lastPrinted >= printInterval) {
                    lastPrinted = percentage;
                    flag = true;
                }
            }
        }
        if (flag) System.out.printf(PRINT_FORMAT, percentage / 10d);
    }
}
