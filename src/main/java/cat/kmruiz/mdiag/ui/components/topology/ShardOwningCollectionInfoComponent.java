package cat.kmruiz.mdiag.ui.components.topology;

import cat.kmruiz.mdiag.overview.topology.sharded.Shard;
import cat.kmruiz.mdiag.ui.Css;
import cat.kmruiz.mdiag.ui.viewmodel.CollectionViewModel;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ShardOwningCollectionInfoComponent extends TableView<CollectionViewModel> {
    private final ObservableList<CollectionViewModel> collectionList;

    public ShardOwningCollectionInfoComponent(Shard shard) {
        Css.apply(this);

        this.collectionList = CollectionViewModel.fromModel(shard.owningCollections());

        final var dbName = new TableColumn<CollectionViewModel, String>("Database");
        final var collName = new TableColumn<CollectionViewModel, String>("Collection");
        final var collCmpDataSize = new TableColumn<CollectionViewModel, Integer>("Compressed Data Size");
        final var collDataSize = new TableColumn<CollectionViewModel, Integer>("Data Size");
        final var collIndexSize = new TableColumn<CollectionViewModel, Integer>("Index Size");
        final var collCount = new TableColumn<CollectionViewModel, Integer>("Estimated Document Count");

        dbName.setCellValueFactory(new PropertyValueFactory<>("database"));
        collName.setCellValueFactory(new PropertyValueFactory<>("collection"));
        collCmpDataSize.setCellValueFactory(new PropertyValueFactory<>("compressedDataSize"));
        collDataSize.setCellValueFactory(new PropertyValueFactory<>("dataSize"));
        collIndexSize.setCellValueFactory(new PropertyValueFactory<>("indexSize"));
        collCount.setCellValueFactory(new PropertyValueFactory<>("estimatedDocumentCount"));

        this.getColumns().addAll(
                dbName,
                collName,
                collCmpDataSize,
                collDataSize,
                collIndexSize,
                collCount
        );

        this.setItems(collectionList);

    }
}
