import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    private double[] LonDPPs;
    private int depth;
    private double unitWidth;
    private double unitHeight;
    private static int maxD = 7;

    public Rasterer() {
        // YOUR CODE HERE
        double rootLonDPP = calLonDPP(MapServer.ROOT_LRLON,
                MapServer.ROOT_ULLON, MapServer.TILE_SIZE);
        LonDPPs = new double[maxD + 1];
        LonDPPs[0] = rootLonDPP;
        for (int i = 1; i <= maxD; i++) {
            LonDPPs[i] = rootLonDPP / Math.pow(2, i);
        }
    }

    private double calLonDPP(double lrlon, double ullon, double size) {
        return (lrlon - ullon) /  size;
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        System.out.println(params);
        double ullon = params.get("ullon");
        double ullat = params.get("ullat");
        double lrlon = params.get("lrlon");
        double lrlat = params.get("lrlat");
        double params_LonDPP = calLonDPP(lrlon, ullon, params.get("w"));
        int depth = calDepth(params_LonDPP);
        setDepth(depth);
        Map<String, Object> results = new HashMap<>();

        if (!checkValidLoc(lrlon, lrlat, ullon, ullat)) {
            results.put("raster_ul_lon", 0.0);
            results.put("raster_lr_lon", 0.0);
            results.put("raster_lr_lat", 0.0);
            results.put("raster_ul_lat", 0.0);
            results.put("render_grid", new String[0][0]);
            results.put("depth", depth);
            results.put("query_success", false);
            return results;
        }
        int lrX = calTheXOrder(lrlon);
        int lrY = calTheYOrder(lrlat);
        int ulX = calTheXOrder(ullon);
        int ulY = calTheYOrder(ullat);
        String[][] render_grid = new String[Math.abs(lrY - ulY) + 1][Math.abs(lrX - ulX) + 1];
        for (int i = 0; i < render_grid.length; i++) {
            for (int j = 0; j < render_grid[i].length; j++) {
                render_grid[i][j] = String.format("d%d_x%d_y%d.png", depth, ulX + j, ulY + i);
            }
        }

        results.put("raster_ul_lon", MapServer.ROOT_ULLON + ulX * unitWidth);
        results.put("raster_lr_lon", MapServer.ROOT_ULLON + (lrX + 1) * unitWidth);
        results.put("raster_lr_lat", MapServer.ROOT_ULLAT - (lrY + 1) * unitHeight);
        results.put("raster_ul_lat", MapServer.ROOT_ULLAT - ulY * unitHeight);
        results.put("render_grid", render_grid);
        results.put("depth", depth);
        results.put("query_success", true);
        return results;

    }

    private boolean checkValidLoc(double lrlon, double lrlat, double ullon, double ullat) {
        if (lrlon < MapServer.ROOT_ULLON || ullon > MapServer.ROOT_LRLON ||
                lrlat > MapServer.ROOT_ULLAT || ullat < MapServer.ROOT_LRLAT) {
            return false;
        }
        return true;
    }

    private int calDepth(double params_LonDPP) {
        int d = maxD;
        for (int i = 0; i < maxD; i++) {
            if (LonDPPs[i] <= params_LonDPP) {
                d = i;
                break;
            }
        }
        return d;
    }

    private void setDepth(int d) {
        double units = Math.pow(2, d);
        depth = d;
        unitWidth = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / units;
        unitHeight = (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / units;
    }

    private int calTheXOrder(double lon) {
        if (lon > MapServer.ROOT_LRLON) {
            return 2 * depth - 1;
        } else if (lon < MapServer.ROOT_ULLON) {
            return 0;
        } else {
            return (int) ((lon - MapServer.ROOT_ULLON) / unitWidth);
        }
    }

    private int calTheYOrder(double lat) {
        if (lat > MapServer.ROOT_ULLAT) {
            return 0;
        } else if (lat < MapServer.ROOT_LRLAT) {
            return 2 * depth - 1;
        } else {
            return (int) Math.abs((lat - MapServer.ROOT_ULLAT) / unitHeight);
        }
    }
}
