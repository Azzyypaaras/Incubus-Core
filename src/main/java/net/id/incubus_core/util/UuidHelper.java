package net.id.incubus_core.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.http.client.HttpResponseException;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class UuidHelper {
    private static final String MOJANG_UUID_API = "https://api.mojang.com/users/profiles/minecraft/";
    private static final int TIMEOUT_IN_SECS = 3;

    private static CompletableFuture<UUID> uuidFuture;

    /**
     * Takes a users name and finds a matching UUID
     *
     * @return Users UUID. Can be null if nothing is found
     * @throws IOException Failed to either connect to the API or format the request
     * @throws HttpResponseException Called if the HTTP response code is anything other than success (200)
     * @throws TimeoutException Response took longer than the TIMEOUT_IN_SECS
     */
    @Environment(EnvType.CLIENT)
    @Nullable
    public static UUID findUuid(String playerName) throws IOException, TimeoutException {
        UUID uuid = null;

        // Asynchronously search for a users UUID
        uuidFuture = CompletableFuture.supplyAsync(() -> UuidHelper.getUuidFromPlayer(playerName));
        try {
            // Get the response or time out if it took to long to retrieve a result
            uuid = uuidFuture.get(TIMEOUT_IN_SECS, TimeUnit.SECONDS);
        }
        catch(ExecutionException ee) {
            if(ee.getCause() instanceof IOException ioe) throw ioe;
        }
        catch(TimeoutException te) {
            throw new TimeoutException("Timed out. Took longer than: " + TIMEOUT_IN_SECS + " seconds to complete");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return uuid;
    }

    private static UUID getUuidFromPlayer(String playerName) {
        UUID uuid = null;
        try {
            URL uuidGetRequest = new URL(MOJANG_UUID_API + playerName);
            var httpURLConnection = (HttpURLConnection) uuidGetRequest.openConnection();
            httpURLConnection.setRequestMethod("GET");

            int responseCode = httpURLConnection.getResponseCode();
            if(responseCode != 200)
                uuidFuture.completeExceptionally(new HttpResponseException(responseCode, "Failed to get a valid UUID for the player"));

            var stringBuilder = new StringBuilder();
            var scanner = new Scanner(uuidGetRequest.openStream());
            while(scanner.hasNext())
                stringBuilder.append(scanner.nextLine());
            scanner.close();

            var uuidObject = (JsonObject) JsonParser.parseString(stringBuilder.toString());
            String id = uuidObject.get("id").getAsString();

            // Format into a proper uuid (accepted) uuid format complete with '-' characters
            String idFormatted = id.substring(0, 8) + "-" + id.substring(8, 12) + "-"
                    + id.substring(12, 16) + "-" + id.substring(16, 20) + "-" + id.substring(20, 32);

            uuid = UUID.fromString(idFormatted);

        }
        catch(IOException ioe) {
            uuidFuture.completeExceptionally(new IOException());
        }
        return uuid;
    }
}
