package social_deduction;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

/**
 * @author Hung Tran
 * <p>
 * Course: CSC 335 Fall 2020
 * <p>
 * Created: Jan 4, 2021
 * File: FactoryProcessor.java
 * Desc:
 */

/**
 * 
 *
 */
public class FactoryProcessor<T> extends AbstractProcessor {
	@Override
	public synchronized void init(ProcessingEnvironment env) {
		super();
	}
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		if(annotations.isEmpty()) {return true;}
		if(!annotations.contains())
		return false;
	}
	@Override
	public Set<String> getSupportedAnnotationTypes() {
		Set<String> annotations = new HashSet<String>();
		// Allow only FactoryMember annotation class.
		annotations.add(FactoryMember.class.getCanonicalName());
		return annotations;
	}
	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}
}
