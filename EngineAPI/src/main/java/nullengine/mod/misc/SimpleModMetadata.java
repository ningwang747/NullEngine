package nullengine.mod.misc;

import com.google.gson.JsonElement;
import nullengine.mod.InstallationType;
import nullengine.mod.ModDependencyItem;
import nullengine.mod.ModMetadata;
import nullengine.util.versioning.Version;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static nullengine.mod.InstallationType.CLIENT_REQUIRED;

public class SimpleModMetadata implements ModMetadata {

    public static final Version DEFAULT_VERSION = new Version("1.0.0");

    private final String id;
    private final Version version;
    private final String mainClass;
    private final String name;
    private final InstallationType installationType;
    private final String description;
    private final String license;
    private final String url;
    private final String logo;
    private final List<String> authors;
    private final List<String> permissions;
    private final List<ModDependencyItem> dependencies;
    private final Map<String, JsonElement> elements;

    protected SimpleModMetadata(String id, Version version, String mainClass, String name, InstallationType installationType, String description, String license, String url, String logo, List<String> authors, List<String> permissions, List<ModDependencyItem> dependencies, Map<String, JsonElement> elements) {
        this.id = id;
        this.version = version;
        this.mainClass = mainClass;
        this.name = name;
        this.installationType = installationType;
        this.description = description;
        this.license = license;
        this.url = url;
        this.logo = logo;
        this.authors = authors;
        this.permissions = permissions;
        this.dependencies = dependencies;
        this.elements = elements;
    }

    @Nonnull
    @Override
    public String getId() {
        return id;
    }

    @Nonnull
    @Override
    public Version getVersion() {
        return version;
    }

    @Nonnull
    @Override
    public String getMainClass() {
        return mainClass;
    }

    @Override
    public String getName() {
        return name;
    }

    @Nonnull
    @Override
    public InstallationType getInstallationType() {
        return installationType;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getLicense() {
        return license;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public List<String> getAuthors() {
        return authors;
    }

    @Override
    public List<String> getPermissions() {
        return permissions;
    }

    @Override
    public String getLogoFile() {
        return logo;
    }

    @Override
    public List<ModDependencyItem> getDependencies() {
        return dependencies;
    }

    @Override
    public Map<String, JsonElement> getCustomElements() {
        return elements;
    }

    @Override
    public Optional<JsonElement> getCustomElement(String key) {
        return Optional.ofNullable(elements.get(key));
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id = "";
        private Version version = DEFAULT_VERSION;
        private String mainClass = "";
        private String name = "";
        private InstallationType installationType = CLIENT_REQUIRED;
        private String description = "";
        private String license = "";
        private String url = "";
        private String logo = "";
        private List<String> authors = Collections.emptyList();
        private List<String> permissions = Collections.emptyList();
        private List<ModDependencyItem> dependencies = Collections.emptyList();
        private Map<String, JsonElement> elements = Collections.emptyMap();

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder version(Version version) {
            this.version = version;
            return this;
        }

        public Builder version(String version) {
            this.version = new Version(version);
            return this;
        }

        public Builder mainClass(String mainClass) {
            this.mainClass = mainClass;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder installationType(InstallationType installationType) {
            this.installationType = installationType;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder license(String license) {
            this.license = license;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder logo(String logo) {
            this.logo = logo;
            return this;
        }

        public Builder authors(List<String> authors) {
            this.authors = authors;
            return this;
        }

        public Builder permissions(List<String> permissions) {
            this.permissions = permissions;
            return this;
        }

        public Builder elements(Map<String, JsonElement> elements) {
            this.elements = elements;
            return this;
        }

        public Builder dependencies(List<ModDependencyItem> dependencies) {
            this.dependencies = dependencies;
            return this;
        }

        public SimpleModMetadata build() {
            return new SimpleModMetadata(id, version, mainClass, name, installationType, description, license, url, logo, authors, permissions, dependencies, elements);
        }
    }
}
