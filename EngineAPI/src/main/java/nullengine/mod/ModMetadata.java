package nullengine.mod;

import com.google.gson.JsonElement;
import nullengine.util.versioning.Version;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ModMetadata {

    @Nonnull
    String getId();

    @Nonnull
    Version getVersion();

    @Nonnull
    String getMainClass();

    String getName();

    @Nonnull
    InstallationType getInstallationType();

    String getDescription();

    String getLicense();

    String getUrl();

    String getLogoFile();

    List<String> getAuthors();

    List<String> getPermissions();

    List<ModDependencyItem> getDependencies();

    Map<String, JsonElement> getCustomElements();

    Optional<JsonElement> getCustomElement(String key);
}
