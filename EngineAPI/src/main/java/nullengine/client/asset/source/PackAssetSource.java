package nullengine.client.asset.source;

import nullengine.util.JsonUtils;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

public class PackAssetSource extends FileSystemAssetSource {

    @Nonnull
    public static PackAssetSource create(@Nonnull Path path) throws IOException {
        requireNonNull(path);

        FileSystem fileSystem;
        Path root;
        if (Files.isDirectory(path)) {
            fileSystem = FileSystems.getDefault();
            root = path.resolve("assets");
        } else {
            fileSystem = FileSystems.newFileSystem(path, PackAssetSource.class.getClassLoader());
            root = fileSystem.getPath("assets");
        }

        Path assetMetadataPath = root.resolve("assetpack.json");
        AssetMetadata metadata;
        try (Reader reader = new InputStreamReader(Files.newInputStream(assetMetadataPath))) {
            metadata = AssetMetadata.fromJson(JsonUtils.DEFAULT_JSON_PARSER.parse(reader).getAsJsonObject());
        }
        return new PackAssetSource(fileSystem, root.toString(), path, metadata);
    }

    private final Path source;
    private final AssetMetadata metadata;

    public PackAssetSource(@Nonnull FileSystem fileSystem, String root, Path source, AssetMetadata metadata) {
        super(fileSystem, root);
        this.source = source;
        this.metadata = metadata;
    }

    public Path getSource() {
        return source;
    }

    public AssetMetadata getMetadata() {
        return metadata;
    }
}
