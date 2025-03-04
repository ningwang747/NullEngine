package nullengine.mod.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface Element {

    String key();

    /**
     * @return json element
     */
    String value();
}
