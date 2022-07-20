package cat.kmruiz.mdiag.common;

import java.util.OptionalInt;

public record Collection (
        String database,
        String collection,
        DataSize compressedDataSize,
        DataSize dataSize,
        DataSize indexSize,
        OptionalInt estimatedDocumentCount,
        String sampleDocument
) {
}
