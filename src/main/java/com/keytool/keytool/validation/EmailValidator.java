package com.keytool.keytool.validation;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import java.util.Set;

@AutoService(Process.class)
@SupportedSourceVersion(SourceVersion.RELEASE_21)
@SupportedAnnotationTypes("com.keytool.keytool.validation")
public class EmailValidator extends AbstractProcessor {


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Elements elements=processingEnv.getElementUtils();
        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);

            for (Element element : annotatedElements) {
                String fieldName = element.getSimpleName().toString();
                String type = element.asType().toString();


                if (!type.equals("java.lang.String")) {
                    processingEnv.getMessager().printMessage(
                            Diagnostic.Kind.ERROR,
                            "Field '" + fieldName + "' annotated with @EmailValidate must be of type String"
                    );
                    continue;
                }


                Object defaultValue = elements.getConstantExpression(element);
                if (defaultValue instanceof String emailValue) {
                    if (!emailValue.endsWith("@megadev")) {
                        processingEnv.getMessager().printMessage(
                                Diagnostic.Kind.ERROR,
                                "The email value '" + emailValue + "' in field '" + fieldName + "' must end with '@megadev'"
                        );
                    }
                }
            }
        }
        return true;
    }
}

