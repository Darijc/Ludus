package bxt.unilectures.vorkurs.visitenkarte;

import java.io.InputStream;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang.StringUtils;

public class CliInputFactory {
	/**
	 * German indefinite articles in dative case by gender
	 */
	private static final Map<LanguageGender.Gender, String> gIndefArticlesDaGen=new HashMap<LanguageGender.Gender, String>(4);
    static {
        gIndefArticlesDaGen.put(LanguageGender.Gender.OTHER, "einen/eine/ein");
        gIndefArticlesDaGen.put(LanguageGender.Gender.MALE, "einen");
        gIndefArticlesDaGen.put(LanguageGender.Gender.FEMALE, "eine");
        gIndefArticlesDaGen.put(LanguageGender.Gender.NEUTRUM, "ein");
    }
    private InputStream in=null;
    private PrintStream out=null;
	CliInputFactory () {
		this(System.in,System.out);
	}
	CliInputFactory(InputStream in, PrintStream out) {
		this.in=in;
		this.out=out;
	}
	public <T> T create(Class<T> type) {
		return create(type,"Bitte geben sie ein(e) %s ein: ");
	}
	public <T> T create(Class<T> type,String message) {
		out.println();
		if(type.getAnnotation(CliPrompt.class) != null) {
			message=type.getAnnotation(CliPrompt.class).value();
		}
		out.printf(message,type.getSimpleName());
		try {
			if (type.isAssignableFrom(String.class)) {
				Scanner sc = new Scanner(in);
				String created= sc.next();
				return type.cast(created);
			} else if (type.isAssignableFrom(Integer.class)) {
				Scanner sc = new Scanner(in);
				Integer created= sc.nextInt();
				return type.cast(created);
			} else if (type.isAssignableFrom(Double.class)) {
				Scanner sc = new Scanner(in);
				Double created= sc.nextDouble();
				return type.cast(created);
			} else {
				T created = type.newInstance();
				createChildren(created);
				return created;
			}
		} catch (InstantiationException e) {
			e.printStackTrace(out);
		} catch (IllegalAccessException e) {
			e.printStackTrace(out);
		}
		return null;
	}
	private <T> void createChildren(T object) {
		Method[] methods = object.getClass().getMethods();
		for(Method method : methods){
			String methodName = method.getName();
			String name=methodName.substring(3);
			boolean hasSetterName = methodName.startsWith("set");
			boolean hasOneAndOnlyOneArgument = 1 == method.getParameterTypes().length;
			if(hasSetterName && hasOneAndOnlyOneArgument) {
				LanguageGender.Gender gender=LanguageGender.Gender.OTHER;
				try {
					Field field = object.getClass().getDeclaredField(StringUtils.lowerCase(name));
					if(field!=null) {
						Annotation fieldAnnotation=field.getAnnotation(LanguageGender.class);
						if( fieldAnnotation != null ) {
							gender=object.getClass().getDeclaredField(StringUtils.lowerCase(name)).getAnnotation(LanguageGender.class).value();
						}
					}
				} catch (SecurityException e1) {
					e1.printStackTrace(out);
				} catch (NoSuchFieldException e1) {
				}
				Object[] params={create(method.getParameterTypes()[0],"Bitte geben sie "+gIndefArticlesDaGen.get(gender)+" "+name+" (%s) ein: ")};
				try {
					method.invoke(object, params);
				} catch (IllegalArgumentException e) {
					e.printStackTrace(out);
				} catch (IllegalAccessException e) {
					e.printStackTrace(out);
				} catch (InvocationTargetException e) {
					e.printStackTrace(out);
				}
			}
		}
	}
}