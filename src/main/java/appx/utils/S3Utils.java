package appx.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

public class S3Utils {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String S3CONFIG = System.getenv("S3CONFIG");
    private static AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();

    private static BufferedReader getS3BufferedReader(String bucketName, String keyName) throws IOException {
        S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucketName, keyName));
        return new BufferedReader(new InputStreamReader(s3Object.getObjectContent(), StandardCharsets.UTF_8));
    }

    public static BufferedReader getS3BufferedReader(String keyName) throws IOException {
        return getS3BufferedReader(S3CONFIG, keyName);
    }


    private static String getS3TextFile(String bucketName, String keyName) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = getS3BufferedReader(bucketName, keyName)) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);

            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        return sb.toString();
    }

    public static String getS3TextFile(String keyName) throws IOException {
        return getS3TextFile(S3CONFIG, keyName);
    }

    public static JsonNode getJsonNodeFromS3(String keyName) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = S3Utils.getS3BufferedReader(S3CONFIG, keyName)) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        return new ObjectMapper().readTree(sb.toString());
    }

    public static <T> T load(String keyName, Class<T> clazz) throws IOException {

        String jsonString = getS3TextFile(S3CONFIG, keyName);

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper.readValue(jsonString, clazz);
    }

}
