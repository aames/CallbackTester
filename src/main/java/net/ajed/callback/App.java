package net.ajed.callback;
/**
 * App.class
 * Created by AA
 * Description:
 *  A one-shot Spark web framework application to receive callbacks from web services.
 *  The API documentation is sparse, so we anticipate either of GET and POST and
 *  then simply print everything in the query string (and body for POST) to STD ERR.
 *
 *  Note:
 *  Set Port, below to 80 when hosted - on the assumption that the callback
 *  is limited to port 80.
 *
 *  Usage:
 *      To Start:
 *      Run App.main(), a Jetty server will be launched from within Spark web framework
 *
 *      To Stop:
 *      Kill the process without fear of side effects.
 *
 *  Requirements: Maven (Spark is a dependency), Java 1.8
 *  IDE used: IntelliJ IDEA 14 (comes bundled with Maven 3)
 *  Spark documentation: http://sparkjava.com/documentation.html
 *  Last edited: 2015-11-06
 */
import spark.Request;

import static java.lang.System.err;
import static spark.Spark.*;

public class App {

    public static void main(String[] args) {
        port(8080); // Set to 80
        get("/", (req, res) -> "Nothing to see here!");
        get("/callback", (req, res) -> {
            printToStdErr(req);
            res.status(200);
            return String.format("Query String: %s Query Path: %s",
                    req.raw().getQueryString(), req.pathInfo());
        });
        post("/callback", (req, res) -> {
            printToStdErr(req);
            res.status(200);
            return String.format("Query String: %s Query Path: %s Body: %s",
                    req.raw().getQueryString(), req.pathInfo(), req.body());
        });
        get("/health", (req, res) -> "{}");
    }

    private static void printToStdErr(Request req){
        err.printf("Incoming message\n");
        err.printf("================\n");
        err.printf("Method: " + req.requestMethod() + "\n");
        err.printf("Query String: %s\nQuery Path: %s\nBody: %s\n/EOM%n",
                req.raw().getQueryString(), req.pathInfo(), req.body());
        err.printf("................\n");
    }
}
