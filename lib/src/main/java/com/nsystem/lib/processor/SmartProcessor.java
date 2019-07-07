package com.nsystem.lib.processor;

import com.nsystem.lib.annotation.Getter;
import com.nsystem.lib.annotation.Setter;
import com.nsystem.lib.annotation.Smart;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

@SupportedAnnotationTypes("com.nsystem.lib.annotation.Smart")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class SmartProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println("Tes");
        String className;
        String sourceClassName;
        final Set<? extends Element> smarts = roundEnvironment.getElementsAnnotatedWith(Smart.class);

        if (smarts.size() <= 0)
            return false;

        for (Element classElement : smarts) {
            TypeElement currentClass = (TypeElement) classElement;
            String fullName = currentClass.getQualifiedName().toString();
            String currentPackage = fullName.substring(0, fullName.lastIndexOf("."));
            sourceClassName = currentClass.getSimpleName().toString();
            className = "Smart" + sourceClassName;

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append(
                    code(
                        "/**",
                        "* Generated code, DO NOT MODIFY",
                        "*/",
                        "\n",
                        "public class " + className + " extends " + sourceClassName + " {\n"
                    )
            );

            List<Element> getFields = getAnnotatedFields(currentClass, Getter.class);
            List<Element> setFields = getAnnotatedFields(currentClass, Setter.class);

            String getter = writeGet(getFields);
            String setter = writeSet(setFields);

            stringBuilder.append(code(getter, setter, "}"));

            try {
                JavaFileObject source = processingEnv.getFiler().createSourceFile(currentPackage + "." + className);

                try (PrintWriter out = new PrintWriter(source.openWriter())) {
                    out.println("package " + currentPackage + ";\n");
                    out.println(stringBuilder.toString());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return true;
    }

    private List<Element> getAnnotatedFields(Element typeElement, Class<? extends Annotation> annotation) {
        List<Element> annotatedList = new ArrayList<>();

        final List<? extends Element> enclosedElements = typeElement.getEnclosedElements();
        for (Element element : enclosedElements) {
            if (element.getAnnotation(annotation) == null)
                continue;

            annotatedList.add(element);
        }

        return annotatedList;
    }

    private String writeGet(List<Element> fields) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Element element : fields) {
            stringBuilder.append(code(
                "public " + element.asType() + " get" + element.toString() + "() {",
                "return " + element.getSimpleName() + ";",
                "}\n"
            ));
        }

        return stringBuilder.toString();
    }

    private String writeSet(List<Element> fields) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Element element : fields) {
            stringBuilder.append(code(
               "public void set" + element.toString() + " (" + element.asType() + " " + element.getSimpleName() + ") {",
                    "this." + element.getSimpleName() + " = " + element.getSimpleName() + ";",
                    "}\n"
            ));
        }

        return stringBuilder.toString();
    }

    private String code(String... statements) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String statement : statements) {
            stringBuilder.append(statement);
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
