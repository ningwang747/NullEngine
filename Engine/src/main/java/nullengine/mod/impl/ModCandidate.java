package nullengine.mod.impl;

import nullengine.mod.DependencyCheckResult;
import nullengine.mod.ModMetadata;

import java.nio.file.Path;
import java.util.Collection;

public class ModCandidate {

    private final Collection<Path> sources;
    private final ModMetadata metadata;

    private DependencyCheckResult dependencyCheckResult;

    public ModCandidate(Collection<Path> sources, ModMetadata metadata) {
        this.sources = sources;
        this.metadata = metadata;
    }

    public Collection<Path> getSources() {
        return sources;
    }

    public ModMetadata getMetadata() {
        return metadata;
    }

    public DependencyCheckResult getDependencyCheckResult() {
        return dependencyCheckResult;
    }

    public void setDependencyCheckResult(DependencyCheckResult dependencyCheckResult) {
        this.dependencyCheckResult = dependencyCheckResult;
    }
}
