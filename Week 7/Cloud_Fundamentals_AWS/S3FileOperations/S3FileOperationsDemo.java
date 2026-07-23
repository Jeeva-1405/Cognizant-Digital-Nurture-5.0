import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.nio.file.Path;
import java.nio.file.Paths;

class S3FileService {

    private final S3Client s3Client;
    private final String bucketName;

    public S3FileService(String bucketName, String region) {
        this.bucketName = bucketName;
        this.s3Client = S3Client.builder()
            .region(Region.of(region))
            .build();
    }

    public void uploadFile(String key, Path filePath) {
        PutObjectRequest request = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .build();

        s3Client.putObject(request, RequestBody.fromFile(filePath));
        System.out.println("Uploaded " + filePath + " to s3://" + bucketName + "/" + key);
    }

    public void downloadFile(String key, Path destination) {
        GetObjectRequest request = GetObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .build();

        s3Client.getObject(request, destination);
        System.out.println("Downloaded s3://" + bucketName + "/" + key + " to " + destination);
    }

    public void listFiles(String prefix) {
        ListObjectsV2Request request = ListObjectsV2Request.builder()
            .bucket(bucketName)
            .prefix(prefix)
            .build();

        ListObjectsV2Response response = s3Client.listObjectsV2(request);

        System.out.println("Files under prefix '" + prefix + "':");
        for (S3Object object : response.contents()) {
            System.out.println("  " + object.key() + " (" + object.size() + " bytes)");
        }
    }

    public void deleteFile(String key) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .build();

        s3Client.deleteObject(request);
        System.out.println("Deleted s3://" + bucketName + "/" + key);
    }

    public void close() {
        s3Client.close();
    }
}

public class S3FileOperationsDemo {
    public static void main(String[] args) {
        S3FileService service = new S3FileService("framecape-intern-certificates", "ap-south-1");

        service.uploadFile(
            "certificates/jeeva-elango-cert.pdf",
            Paths.get("local/certificates/jeeva-elango-cert.pdf")
        );

        service.listFiles("certificates/");

        service.downloadFile(
            "certificates/jeeva-elango-cert.pdf",
            Paths.get("downloads/jeeva-elango-cert.pdf")
        );

        service.deleteFile("certificates/jeeva-elango-cert.pdf");

        service.close();
    }
}
