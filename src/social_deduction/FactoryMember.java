package social_deduction;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

/**
 * Annotation that decorates a game message
 * 
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface FactoryMember {
	/**
	 * Return type or name of the factory
	 */
	public Class returnType();
	/**
	 * The string hint to recognize that this
	 * @return
	 */
	public String nameHint();
	public String[] alias() default {};
}

