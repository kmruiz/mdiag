package cat.kmruiz.mdiag.ui.viewmodel;

import cat.kmruiz.mdiag.common.Collection;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class CollectionViewModel {
    private final SimpleStringProperty database;
    private final SimpleStringProperty collection;
    private final SimpleStringProperty compressedDataSize;
    private final SimpleStringProperty dataSize;
    private final SimpleStringProperty indexSize;
    private final SimpleIntegerProperty estimatedDocumentCount;

    private CollectionViewModel(Collection collection) {
        this.database = new SimpleStringProperty(collection.database());
        this.collection = new SimpleStringProperty(collection.collection());
        this.compressedDataSize = new SimpleStringProperty(collection.compressedDataSize().humanize());
        this.dataSize = new SimpleStringProperty(collection.dataSize().humanize());
        this.indexSize = new SimpleStringProperty(collection.indexSize().humanize());
        this.estimatedDocumentCount = new SimpleIntegerProperty(collection.estimatedDocumentCount().orElse(0));
    }

    public static ObservableList<CollectionViewModel> fromModel(List<Collection> collections) {
        return FXCollections.observableList(
                collections.stream()
                        .map(CollectionViewModel::new)
                        .toList()
        );
    }

    public String getDatabase() {
        return database.get();
    }

    public SimpleStringProperty databaseProperty() {
        return database;
    }

    public String getCollection() {
        return collection.get();
    }

    public SimpleStringProperty collectionProperty() {
        return collection;
    }

    public String getCompressedDataSize() {
        return compressedDataSize.get();
    }

    public SimpleStringProperty compressedDataSizeProperty() {
        return compressedDataSize;
    }

    public String getDataSize() {
        return dataSize.get();
    }

    public SimpleStringProperty dataSizeProperty() {
        return dataSize;
    }

    public String getIndexSize() {
        return indexSize.get();
    }

    public SimpleStringProperty indexSizeProperty() {
        return indexSize;
    }

    public int getEstimatedDocumentCount() {
        return estimatedDocumentCount.get();
    }

    public SimpleIntegerProperty estimatedDocumentCountProperty() {
        return estimatedDocumentCount;
    }
}
