package nullengine.registry;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.Set;

/**
 * The registry of the a type of object
 *
 * @param <T> The managed type
 */
public interface Registry<T extends RegistryEntry<T>> {

    @Nonnull
    Class<T> getEntryType();

    @Nonnull
    String getRegistryName();

    @Nonnull
    T register(@Nonnull T obj) throws RegistrationException;

    default void registerAll(@Nonnull T... objs) throws RegistrationException {
        for (T obj : objs)
            register(obj);
    }

    boolean containsValue(T value);

    /**
     * Get the registered object by the unique name of the object.
     *
     * @param registryName The unique name of the object
     * @return The object
     * @see RegistryEntry#getUniqueName()
     */
    T getValue(String registryName);

    /**
     * Well... this method is not really...
     *
     * @return The unique name of the object
     * @see RegistryEntry#getUniqueName()
     */
    String getKey(T value);

    boolean containsKey(String key);

    Collection<T> getValues();

    Set<String> getKeys();

    int getId(T obj);

    int getId(String key);

    String getKey(int id);

    T getValue(int id);

    Collection<Entry<String, T>> getEntries();
}
