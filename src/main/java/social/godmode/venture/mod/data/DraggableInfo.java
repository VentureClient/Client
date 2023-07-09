package social.godmode.venture.mod.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DraggableInfo {
    double x() default 0;
    double y() default 0;
    double width() default 100;
    double height() default 100;
}
