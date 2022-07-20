package cat.kmruiz.mdiag.common;

public record Collection (
        String shard,
        String database,
        String collection,
        DataSize compressedDataSize,
        DataSize dataSize,
        DataSize indexSize,
        int estimatedDocumentCount,
        String sampleDocument
) {
}
