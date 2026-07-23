import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.util.List;
import java.util.stream.Collectors;

@DynamoDbBean
class InternCertificate {

    private String internId;
    private String certificateType;
    private String issueDate;
    private String status;

    @DynamoDbPartitionKey
    public String getInternId() { return internId; }
    public void setInternId(String internId) { this.internId = internId; }

    @DynamoDbSortKey
    public String getCertificateType() { return certificateType; }
    public void setCertificateType(String certificateType) { this.certificateType = certificateType; }

    public String getIssueDate() { return issueDate; }
    public void setIssueDate(String issueDate) { this.issueDate = issueDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String toString() {
        return "InternCertificate{internId=" + internId
            + ", type=" + certificateType
            + ", issueDate=" + issueDate
            + ", status=" + status + "}";
    }
}

class InternCertificateRepository {

    private final DynamoDbTable<InternCertificate> table;

    public InternCertificateRepository(String tableName, String region) {
        DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
            .region(Region.of(region))
            .build();

        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
            .dynamoDbClient(dynamoDbClient)
            .build();

        this.table = enhancedClient.table(tableName, TableSchema.fromBean(InternCertificate.class));
    }

    public void save(InternCertificate certificate) {
        table.putItem(certificate);
        System.out.println("Saved certificate for intern: " + certificate.getInternId());
    }

    public InternCertificate findByIdAndType(String internId, String certificateType) {
        Key key = Key.builder()
            .partitionValue(internId)
            .sortValue(certificateType)
            .build();

        return table.getItem(key);
    }

    public List<InternCertificate> findAllByInternId(String internId) {
        QueryConditional queryConditional = QueryConditional
            .keyEqualTo(Key.builder().partitionValue(internId).build());

        return table.query(queryConditional)
            .items()
            .stream()
            .collect(Collectors.toList());
    }

    public void delete(String internId, String certificateType) {
        Key key = Key.builder()
            .partitionValue(internId)
            .sortValue(certificateType)
            .build();

        table.deleteItem(key);
        System.out.println("Deleted certificate: " + internId + " / " + certificateType);
    }
}

public class DynamoDBCrudOperationsDemo {
    public static void main(String[] args) {
        InternCertificateRepository repository =
            new InternCertificateRepository("InternCertificates", "ap-south-1");

        InternCertificate cert1 = new InternCertificate();
        cert1.setInternId("INT-2026-001");
        cert1.setCertificateType("COMPLETION");
        cert1.setIssueDate("2026-07-20");
        cert1.setStatus("ISSUED");

        InternCertificate cert2 = new InternCertificate();
        cert2.setInternId("INT-2026-001");
        cert2.setCertificateType("EXCELLENCE");
        cert2.setIssueDate("2026-07-22");
        cert2.setStatus("ISSUED");

        repository.save(cert1);
        repository.save(cert2);

        InternCertificate found = repository.findByIdAndType("INT-2026-001", "COMPLETION");
        System.out.println("Found: " + found);

        List<InternCertificate> allCerts = repository.findAllByInternId("INT-2026-001");
        System.out.println("All certificates for intern:");
        for (InternCertificate c : allCerts) {
            System.out.println("  " + c);
        }

        repository.delete("INT-2026-001", "EXCELLENCE");
    }
}
