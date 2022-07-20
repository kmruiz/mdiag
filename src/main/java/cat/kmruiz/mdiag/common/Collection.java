package cat.kmruiz.mdiag.common;

import cat.kmruiz.mdiag.DataSize;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.OptionalLong;

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
